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
        passTemplate.setId(19);
        passTemplate.setTitle("title: ivy");
        passTemplate.setSummary("summary: ivy dev");
        passTemplate.setDesc("descriotion: ivy dev");
        passTemplate.setLimit(10000L);
        passTemplate.setHasToken(false);
        passTemplate.setBackground(2);
        passTemplate.setStart(new Date());
        passTemplate.setEnd(DateUtils.addDays(new Date(), 10));
        System.out.println(JSON.toJSONString(
                merchantsServ.dropPassTemplate(passTemplate)
        ));
    }
}
