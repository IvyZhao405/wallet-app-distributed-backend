package dev.ivyzhao.wallet.merchants.service;

import com.alibaba.fastjson.JSON;
import dev.ivyzhao.wallet.merchants.vo.CreateMerchantsRequest;
import dev.ivyzhao.wallet.merchants.vo.CreateMerchantsResponse;
import dev.ivyzhao.wallet.merchants.vo.PassTemplate;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <h1>Merchant service test class</h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MerchantsServTest {
    @Autowired
    private IMerchantsServ merchantsServ;

    @Test
//    @Transactional
    public void testCreateMerchantServ(){
        CreateMerchantsRequest request = new CreateMerchantsRequest();
        request.setName("IvyDev");
        request.setLogoUrl("www.ivyzhao.dev");
        request.setBusinessLicenseUrl("www.ivyzhao.dev");
        request.setPhone("1234567890");
        request.setAddress("Tampa");
        System.out.println(JSON.toJSONString(merchantsServ.createMerchants(request)));
    }

    /**
     * {"data":{"address":"Tampa",
     * "businessLicenseUrl":"www.ivyzhao.dev","id":19,
     * "logoUrl":"www.ivyzhao.dev","name":"IvyDev","passAudit":false,
     * "phone":"1234567890"},"errorCode":0,"errorMessage":""}
     */
    @Test
    public void testBuildMerchantsInfoById(){
        System.out.println(JSON.toJSONString(merchantsServ.buildMerchantsInfoById(19)));
    }

    /**
     * {"background":2,"desc":"descriotion: ivy dev","end":1624718037899,
     * "hasToken":false,"id":19,"limit":10000,"start":1623854037899,
     * "summary":"summary: ivy dev","title":"title: ivy"}
     */
    @Test
    public void testDropPassTemplate(){
        PassTemplate passTemplate = new PassTemplate();
        passTemplate.setId(20);
        passTemplate.setTitle("ivydev");
        passTemplate.setSummary("summary: ivy dev 3");
        passTemplate.setDesc("descriotion: ivy dev 3");
        passTemplate.setLimit(10000L);
        passTemplate.setHasToken(true);
        passTemplate.setBackground(2);
        passTemplate.setStart(DateUtils.addDays(new Date(), -10));
        passTemplate.setEnd(DateUtils.addDays(new Date(), 10));
        System.out.println(JSON.toJSONString(
                merchantsServ.dropPassTemplate(passTemplate)
        ));
        //{"background":2,"desc":"descriotion: ivy dev 3","end":1625170896647,
        // "hasToken":true,"id":20,"limit":10000,"start":1623442896645,"summary":"summary:
        // ivy dev 3","title":"ivydev"}
    }
}
