package dev.ivy.wallet.wallet.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h1>Inventory service test</h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryServiceTest extends AbstractServiceTest{

    @Autowired
    private IInventoryService inventoryService;

    @Test
    public void testGetInventoryInfo() throws Exception {
        System.out.println(JSON.toJSONString(
                inventoryService.getInventoryInfo(userId),
                SerializerFeature.DisableCircularReferenceDetect
        ));
    }
}
