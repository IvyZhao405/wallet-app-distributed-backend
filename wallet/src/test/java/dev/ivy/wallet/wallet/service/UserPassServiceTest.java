package dev.ivy.wallet.wallet.service;


import com.alibaba.fastjson.JSON;
import dev.ivy.wallet.wallet.vo.Pass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPassServiceTest extends AbstractServiceTest{

    @Autowired
    private IUserPassService userPassService;

    @Test
    public void testGetUserPassInfo() throws Exception {
        System.out.println(JSON.toJSONString(
                userPassService.getUserPassInfo(userId)));
    }

    @Test
    public void testUserUserPass(){
        Pass pass = new Pass();
        pass.setUserId(userId);
        pass.setTemplateId("fde642459cc012c0cc16e70f2c0f456b");

        System.out.println(JSON.toJSONString(
                userPassService.userUsePass(pass)
        ));
    }
}
