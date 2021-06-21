package dev.ivy.wallet.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import dev.ivy.wallet.wallet.constant.Constants;
import dev.ivy.wallet.wallet.constant.PassStatus;
import dev.ivy.wallet.wallet.dao.MerchantsDao;
import dev.ivy.wallet.wallet.entity.Merchants;
import dev.ivy.wallet.wallet.mapper.PassRowMapper;
import dev.ivy.wallet.wallet.service.IUserPassService;
import dev.ivy.wallet.wallet.vo.Pass;
import dev.ivy.wallet.wallet.vo.PassInfo;
import dev.ivy.wallet.wallet.vo.PassTemplate;
import dev.ivy.wallet.wallet.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>User coupon related service implementation </h1>
 */
@Slf4j
@Service
public class UserPassServiceImpl implements IUserPassService {


    private final HbaseTemplate hbaseTemplate;


    private final MerchantsDao merchantsDao;

    @Autowired
    public UserPassServiceImpl(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
    }


    @Override
    public Response getUserPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId, PassStatus.UNUSED);
    }

    @Override
    public Response getUserUsedPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId, PassStatus.USED);
    }

    @Override
    public Response getUserAllPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId, PassStatus.ALL);
    }

    @Override
    public Response userUsePass(Pass pass) {
        // create prefix based on userId
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(
                String.valueOf(pass.getUserId())).reverse().toString());
        Scan scan = new Scan();
        List<Filter> filters = new ArrayList<>();
        filters.add(new PrefixFilter(rowPrefix));
        filters.add(new SingleColumnValueFilter(
                Constants.PassTable.FAMILY_I.getBytes(),
                Constants.PassTable.TEMPLATE_ID.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(pass.getTemplateId())
        ));
        filters.add(new SingleColumnValueFilter(
                Constants.PassTable.FAMILY_I.getBytes(),
                Constants.PassTable.CON_DATE.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("-1")
        ));

        scan.setFilter(new FilterList(filters));

        List<Pass> passes = hbaseTemplate.find(Constants.PassTable.TABLE_NAME,
                scan, new PassRowMapper());

        if (null == passes || passes.size() != 1) {
            log.error("UserUsePass Error: {}", JSON.toJSONString(pass));
            return Response.failure("UserUsePass Error");
        }

        byte[] FAMILY_I = Constants.PassTable.FAMILY_I.getBytes();
        byte[] CON_DATE = Constants.PassTable.CON_DATE.getBytes();

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(passes.get(0).getRowKey().getBytes());
        put.addColumn(FAMILY_I, CON_DATE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date()))
        );
        datas.add(put);

        hbaseTemplate.saveOrUpdates(Constants.PassTable.TABLE_NAME, datas);

        return Response.success();
    }


    /**
     * <h2>Based on pass status get pass info </h2>
     * @param userId User id
     * @param status {@link PassStatus}
     * @return Response {@link Response}
     * @throws Exception
     */
    private Response getPassInfoByStatus(Long userId, PassStatus status) throws Exception {
        //create row predix based on User id
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(userId)).reverse().toString());
        CompareFilter.CompareOp compareOp =
                status == PassStatus.UNUSED ?
                        CompareFilter.CompareOp.EQUAL : CompareFilter.CompareOp.NOT_EQUAL;

        Scan scan = new Scan();

        List<Filter> filters = new ArrayList<>();
        //1.Row filter for finding specific user
        filters.add(new PrefixFilter(rowPrefix));

        //2. Column filter for finding unused coupon.
        //If consumed date = -1, coupon hasn't been consumed.
        if (status != PassStatus.ALL) {
            filters.add(new SingleColumnValueFilter(
                        Constants.PassTable.FAMILY_I.getBytes(),
                        Constants.PassTable.CON_DATE.getBytes(),
                        compareOp, Bytes.toBytes("-1")));
        }

        scan.setFilter(new FilterList(filters));
        List<Pass> passes = hbaseTemplate.find(Constants.PassTable.TABLE_NAME, scan, new PassRowMapper());
        Map<String, PassTemplate> passTemplateMap = buildPassTemplateMap(passes);
        Map<Integer, Merchants> merchantsMap = buildMerchantsMap(new ArrayList<>(passTemplateMap.values()));

        List<PassInfo> result = new ArrayList<>();

        for (Pass pass : passes) {
            PassTemplate passTemplate = passTemplateMap.getOrDefault(
                    pass.getTemplateId(), null);
            if (null == passTemplate) {
                log.error("PassTemplate Null : {}", pass.getTemplateId());
                continue;
            }
            Merchants merchants = merchantsMap.getOrDefault(passTemplate.getId(), null);
            if (null == merchants) {
                log.error("Merchants Null : {}", passTemplate.getId());
                continue;
            }
            result.add(new PassInfo(pass, passTemplate, merchants));
        }
        return new Response(result);
    }

    /**
     * <h2>Build Map use Passes objects</h2>
     * @param passes {@link Pass}
     * @return Map {@link PassTemplate}
     * @throws Exception
     */
    private Map<String, PassTemplate> buildPassTemplateMap(List<Pass> passes) throws Exception {

        String[] patterns = new String[] {"yyyy-MM-dd"};

        byte[] FAMILY_B = Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B);
        byte[] ID = Bytes.toBytes(Constants.PassTemplateTable.ID);
        byte[] TITLE = Bytes.toBytes(Constants.PassTemplateTable.TITLE);
        byte[] SUMMARY = Bytes.toBytes(Constants.PassTemplateTable.SUMMARY);
        byte[] DESC = Bytes.toBytes(Constants.PassTemplateTable.DESC);
        byte[] HAS_TOKEN = Bytes.toBytes(Constants.PassTemplateTable.HAS_TOKEN);
        byte[] BACKGROUND = Bytes.toBytes(Constants.PassTemplateTable.BACKGROUND);

        byte[] FAMILY_C = Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C);
        byte[] LIMIT = Bytes.toBytes(Constants.PassTemplateTable.LIMIT);
        byte[] START = Bytes.toBytes(Constants.PassTemplateTable.START);
        byte[] END = Bytes.toBytes(Constants.PassTemplateTable.END);

        List<String> templateIds = passes.stream().map(
                Pass::getTemplateId
        ).collect(Collectors.toList());

        List<Get> templateGets = new ArrayList<>(templateIds.size());
        templateIds.forEach(t -> templateGets.add(new Get(Bytes.toBytes(t))));

        Result[] templateResults = hbaseTemplate.getConnection()
                .getTable(TableName.valueOf(Constants.PassTemplateTable.TABLE_NAME))
                .get(templateGets);

        //Create PassTemplate -> PassTemplate Object map, for creating PassInfo
        Map<String, PassTemplate> templateId2Object = new HashMap<>();
        for (Result item: templateResults) {
            PassTemplate passTemplate = new PassTemplate();

            passTemplate.setId(Bytes.toInt(item.getValue(FAMILY_B, ID)));
            passTemplate.setTitle(Bytes.toString(item.getValue(FAMILY_B, TITLE)));
            passTemplate.setSummary(Bytes.toString(item.getValue(FAMILY_B, SUMMARY)));
            passTemplate.setDesc(Bytes.toString(item.getValue(FAMILY_B, DESC)));
            passTemplate.setHasToken(Bytes.toBoolean(item.getValue(FAMILY_B, HAS_TOKEN)));
            passTemplate.setBackground(Bytes.toInt(item.getValue(FAMILY_B, BACKGROUND)));

            passTemplate.setLimit(Bytes.toLong(item.getValue(FAMILY_C, LIMIT)));
            passTemplate.setStart(DateUtils.parseDate(
                    Bytes.toString(item.getValue(FAMILY_C, START)), patterns));
            passTemplate.setEnd(DateUtils.parseDate(
                    Bytes.toString(item.getValue(FAMILY_C, END)), patterns
            ));

            templateId2Object.put(Bytes.toString(item.getRow()), passTemplate);
        }
        return templateId2Object;
    }

    /**
     * <h2>Use PassTemplate create Merchants Map</h2>
     * @param passTemplates {@link PassTemplate}
     * @return {@link Merchants}
     * */
    private Map<Integer, Merchants> buildMerchantsMap(List<PassTemplate> passTemplates) {

        Map<Integer, Merchants> merchantsMap = new HashMap<>();
        List<Integer> merchantsIds = passTemplates.stream().map(
                PassTemplate::getId
        ).collect(Collectors.toList());
        List<Merchants> merchants = merchantsDao.findByIdIn(merchantsIds);

        merchants.forEach(m -> merchantsMap.put(m.getId(), m));

        return merchantsMap;
    }
}
