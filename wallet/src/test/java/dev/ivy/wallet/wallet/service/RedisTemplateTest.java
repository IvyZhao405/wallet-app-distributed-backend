package dev.ivy.wallet.wallet.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h1>Redis client test</h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate() {
        //redis flushall
        redisTemplate.execute((RedisCallback<? extends Object>) connection -> {
            connection.flushAll();
            return null;
        });
        assert redisTemplate.opsForValue().get("name") == null;
        redisTemplate.opsForValue().set("name", "wallet");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
}
