package dev.ivy.wallet.wallet.service;

import com.alibaba.fastjson.JSON;
import dev.ivy.wallet.wallet.constant.FeedbackType;
import dev.ivy.wallet.wallet.vo.Feedback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedBackServiceTest extends AbstractServiceTest{

    @Autowired
    private IFeedbackService feedbackService;

    @Test
    public void testCreateFeedback() {
        Feedback appFeedback = new Feedback();
        appFeedback.setUserId(userId);
        appFeedback.setType(FeedbackType.APP.getCode());
        appFeedback.setTemplateId("-1");
        appFeedback.setComment("Distributed wallet app is awesome!");
        System.out.println(JSON.toJSONString(
                feedbackService.createFeedback(appFeedback)
        ));
        Feedback passFeedback = new Feedback();
        passFeedback.setUserId(userId);
        passFeedback.setType(FeedbackType.PASS.getCode());
        passFeedback.setTemplateId("80bc4c08ac13d7d5e59c8c3ea356d575");
        passFeedback.setComment("Coupon feedback!");

        System.out.println(JSON.toJSONString(
                feedbackService.createFeedback(passFeedback)
        ));
        //{"errorCode":0,"errorMsg":""}
        //{"errorCode":0,"errorMsg":""}
    }

    @Test
    public void testGetFeedback() {
        System.out.println(JSON.toJSONString(
                feedbackService.getFeedback(userId)
        ));
        //{"data":[{"comment":"Coupon feedback!","templateId":"80bc4c08ac13d7d5e59c8c3ea356d575","type":"pass","userId":222611},
        // {"comment":"Distributed wallet app is awesome!","templateId":"-1","type":"app","userId":222611}],
        // "errorCode":0,"errorMsg":""}
    }


}
