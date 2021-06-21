package dev.ivy.wallet.wallet.service;

import com.alibaba.fastjson.JSON;
import dev.ivy.wallet.wallet.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h1>User test service</h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testCreatedUser() throws Exception {
        User user = new User();
        user.setBaseInfo(new User.BaseInfo("ivy", 30, "f"));
        user.setOtherInfo(new User.OtherInfo("12321", "st.petersburg fl"));
        //{"data":{"baseInfo":{"age":30,"name":"ivy","sex":"f"},"id":222611,
        // "otherInfo":{"address":"st.petersburg fl","phone":"12321"}},
        // "errorCode":0,"errorMsg":""}
        System.out.println(JSON.toJSONString(userService.createUser(user)));
    }
}
