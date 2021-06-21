package dev.ivy.wallet.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import dev.ivy.wallet.wallet.constant.Constants;
import dev.ivy.wallet.wallet.mapper.PassTemplateRowMapper;
import dev.ivy.wallet.wallet.service.IGainPassTemplateService;
import dev.ivy.wallet.wallet.utils.RowKeyGenUtil;
import dev.ivy.wallet.wallet.vo.GainPassTemplateRequest;
import dev.ivy.wallet.wallet.vo.PassTemplate;
import dev.ivy.wallet.wallet.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>User claim coupon service implementation</h1>
 */
@Slf4j
@Service
public class GainPassTemplateServiceImpl implements IGainPassTemplateService {

    /** HBase client */
    private final HbaseTemplate hbaseTemplate;

    /** redis template */
    private final StringRedisTemplate redisTemplate;
    private GainPassTemplateServiceImpl(HbaseTemplate hbaseTemplate,
                                        StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }
    @Override
    public Response gainPassTemplate(GainPassTemplateRequest request) throws Exception {
        PassTemplate passTemplate;
        String passTemplateId = RowKeyGenUtil.genPassTemplateRowKey(request.getPassTemplate());

        try {
            passTemplate = hbaseTemplate.get(
                    Constants.PassTemplateTable.TABLE_NAME,
                    passTemplateId,
                    new PassTemplateRowMapper()
            );
        } catch (Exception ex) {
            log.error("Gain PassTemplate Error: {}", JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("Gain PassTemplate Error!");
        }
        if (passTemplate.getLimit() <= 1 && passTemplate.getLimit() != -1) {
            log.error("PassTemplate Limit Max: {}", JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("PassTemplate Limit Max!");
        }
        Date cur = new Date();
        if (!(cur.getTime() >= passTemplate.getStart().getTime()
            && cur.getTime() < passTemplate.getEnd().getTime())) {
            log.error("PassTemplate ValidTime Error : {}", JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("PasstEMPLATE ValidTime Error!");
        }

        //reduce coupon's quantity limit if its a limited supplied coupon
        if (passTemplate.getLimit() != -1) {
            List<Mutation> datas = new ArrayList<>();
            byte[] FAMILY_C = Constants.PassTemplateTable.FAMILY_C.getBytes();
            byte[] LIMIT = Constants.PassTemplateTable.LIMIT.getBytes();

            Put put = new Put(Bytes.toBytes(passTemplateId));
            put.addColumn(FAMILY_C, LIMIT,
                    Bytes.toBytes(passTemplate.getLimit() - 1));
            datas.add(put);

            hbaseTemplate.saveOrUpdates(Constants.PassTemplateTable.TABLE_NAME,
                    datas);
        }
        //Save coupon to user's coupon table
        if (!addPassForUser(request, passTemplate.getId(), passTemplateId)) {
            return Response.failure("GainPassTemplate Failure!");
        }

        return Response.success();
    }

    /**
     * <h2>Add coupon to user</h2>
     * @param request {@link GainPassTemplateRequest}
     * @param merchantsId merchant id
     * @param passTemplateId coupon id
     * @return true/false
     * @throws Exception
     */
    private boolean addPassForUser(GainPassTemplateRequest request,
                                   Integer merchantsId, String passTemplateId) throws Exception {
        byte[] FAMILY_I = Constants.PassTable.FAMILY_I.getBytes();
        byte[] USER_ID = Constants.PassTable.USER_ID.getBytes();
        byte[] TEMPLATE_ID = Constants.PassTable.TEMPLATE_ID.getBytes();
        byte[] TOKEN = Constants.PassTable.TOKEN.getBytes();
        byte[] ASSIGNED_DATE = Constants.PassTable.ASSIGNED_DATE.getBytes();
        byte[] CON_DATE = Constants.PassTable.CON_DATE.getBytes();

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(Bytes.toBytes(RowKeyGenUtil.genPassRowKey(request)));
        put.addColumn(FAMILY_I, USER_ID, Bytes.toBytes(request.getUserId()));
        put.addColumn(FAMILY_I, TEMPLATE_ID, Bytes.toBytes(passTemplateId));

        if (request.getPassTemplate().getHasToken()) {
            String token = redisTemplate.opsForSet().pop(passTemplateId);
            if (null == token) {
                log.error("Token not exist: {}", passTemplateId);
                return false;
            }
            recordTokenToFile(merchantsId, passTemplateId, token);
            put.addColumn(FAMILY_I, TOKEN, Bytes.toBytes(token));
        } else {
            put.addColumn(FAMILY_I, TOKEN, Bytes.toBytes("-1"));
        }

        put.addColumn(FAMILY_I, ASSIGNED_DATE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())));
        put.addColumn(FAMILY_I, CON_DATE, Bytes.toBytes("-1"));
        datas.add(put);
        hbaseTemplate.saveOrUpdates(Constants.PassTable.TABLE_NAME, datas);
        return true;
    }

    /**
     * <h2>record already claimed token in file</h2>
     * @param merchantsId merchant id
     * @param passTemplateId coupon id
     * @param token claimed coupon
     */
    private void recordTokenToFile(Integer merchantsId, String passTemplateId,
                                   String token) throws Exception{
        Files.write(
                Paths.get(Constants.TOKEN_DIR, String.valueOf(merchantsId),
                        passTemplateId + Constants.USED_TOKEN_SUFFIX),
                (token + "\n").getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND
        );
    }
}
