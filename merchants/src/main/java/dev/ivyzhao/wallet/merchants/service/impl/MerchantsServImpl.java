package dev.ivyzhao.wallet.merchants.service.impl;

import com.alibaba.fastjson.JSON;
import dev.ivyzhao.wallet.merchants.constant.Constants;
import dev.ivyzhao.wallet.merchants.constant.ErrorCode;
import dev.ivyzhao.wallet.merchants.dao.MerchantsDao;
import dev.ivyzhao.wallet.merchants.entity.Merchants;
import dev.ivyzhao.wallet.merchants.service.IMerchantsServ;
import dev.ivyzhao.wallet.merchants.vo.CreateMerchantsRequest;
import dev.ivyzhao.wallet.merchants.vo.CreateMerchantsResponse;
import dev.ivyzhao.wallet.merchants.vo.PassTemplate;
import dev.ivyzhao.wallet.merchants.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * <h1>Merchant service interface implementation</h1>
 */
@Slf4j
@Service
public class MerchantsServImpl implements IMerchantsServ {

    /** Merchants database connector */
    private final MerchantsDao merchantsDao;

    /** kafka client */
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MerchantsServImpl(MerchantsDao merchantsDao,
                             KafkaTemplate<String, String> kafkaTemplate) {
        this.merchantsDao = merchantsDao;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Response createMerchants(CreateMerchantsRequest request) {
        Response response = new Response();
        CreateMerchantsResponse merchantsResponse = new CreateMerchantsResponse();
        ErrorCode errorCode = request.validate(merchantsDao);
        if (errorCode != ErrorCode.SUCCESS) {
            merchantsResponse.setId(-1);
            response.setErrorCode(errorCode.getCode());
            response.setErrorMessage(errorCode.getDesc());
        } else {
            merchantsResponse.setId(merchantsDao.save(request.toMerchants()).getId());
        }
        response.setData(merchantsResponse);
        return response;
    }

    @Override
    public Response buildMerchantsInfoById(Integer id) {
        Response response = new Response();
        Merchants merchants = merchantsDao.findById(id);
        if (null == merchants) {
            response.setErrorCode(ErrorCode.MERCHANTS_NOT_EXISTS.getCode());
            response.setErrorMessage(ErrorCode.MERCHANTS_NOT_EXISTS.getDesc());
        }
        response.setData(merchants);
        return response;
    }

    @Override
    public Response dropPassTemplate(PassTemplate template) {
        Response response = new Response();
        ErrorCode errorCode = template.validate(merchantsDao);

        if (errorCode != ErrorCode.SUCCESS){
            response.setErrorCode(errorCode.getCode());
            response.setErrorMessage(errorCode.getDesc());
        } else {
            String passTemplate = JSON.toJSONString(template);
            kafkaTemplate.send(
                    Constants.TEMPLATE_TOPIC,
                    Constants.TEMPLATE_TOPIC,
                    passTemplate
            );
            log.info("DropPassTemplates: {}", passTemplate);
        }
        return null;
    }
}
