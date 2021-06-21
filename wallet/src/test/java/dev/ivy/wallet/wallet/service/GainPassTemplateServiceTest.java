package dev.ivy.wallet.wallet.service;


import com.alibaba.fastjson.JSON;
import dev.ivy.wallet.wallet.vo.GainPassTemplateRequest;
import dev.ivy.wallet.wallet.vo.PassTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GainPassTemplateServiceTest extends AbstractServiceTest{

    @Autowired
    private IGainPassTemplateService gainPassTemplateService;

    @Test
    public void testGainPassTemplate() throws Exception {
        PassTemplate target = new PassTemplate();
        target.setId(20);
        target.setTitle("ivydev");
        target.setHasToken(true);

        System.out.println(JSON.toJSONString(
            gainPassTemplateService.gainPassTemplate(
                    new GainPassTemplateRequest(userId, target)
            )
        ));
    }
}
