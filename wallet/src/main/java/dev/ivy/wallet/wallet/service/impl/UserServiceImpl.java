package dev.ivy.wallet.wallet.service.impl;

import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import dev.ivy.wallet.wallet.constant.Constants;
import dev.ivy.wallet.wallet.service.IUserService;
import dev.ivy.wallet.wallet.vo.Response;
import dev.ivy.wallet.wallet.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Create User Service Implementation </h1>
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    /**HBase client */
    private final HbaseTemplate hbaseTemplate;

    /**Redis client */
    private final StringRedisTemplate redisTemplate;

    public UserServiceImpl(HbaseTemplate hbaseTemplate, StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Response createUser(User user) throws Exception {

        byte[] FAMILY_B = Constants.UserTable.FAMILY_B.getBytes();
        byte[] NAME = Constants.UserTable.NAME.getBytes();
        byte[] AGE = Constants.UserTable.AGE.getBytes();
        byte[] SEX = Constants.UserTable.SEX.getBytes();

        byte[] FAMILY_O = Constants.UserTable.FAMILY_O.getBytes();
        byte[] PHONE = Constants.UserTable.PHONE.getBytes();
        byte[] ADDRESS = Constants.UserTable.ADDRESS.getBytes();

        Long curCount = redisTemplate.opsForValue().increment(Constants.USE_COUNT_REDIS_KEY, 1);
        Long userId = genUserId(curCount);

        List<Mutation> datas = new ArrayList<Mutation>();
        Put put = new Put(Bytes.toBytes(userId));

        put.addColumn(FAMILY_B, NAME, Bytes.toBytes(user.getBaseInfo().getName()));
        put.addColumn(FAMILY_B, AGE, Bytes.toBytes(user.getBaseInfo().getAge()));
        put.addColumn(FAMILY_B, SEX, Bytes.toBytes(user.getBaseInfo().getSex()));

        put.addColumn(FAMILY_O, PHONE, Bytes.toBytes(user.getOtherInfo().getPhone()));
        put.addColumn(FAMILY_O, ADDRESS, Bytes.toBytes(user.getOtherInfo().getAddress()));

        datas.add(put);

        hbaseTemplate.saveOrUpdates(Constants.UserTable.TABLE_NAME, datas);

        user.setId(userId);

        return new Response(user);
    }

    /**
     * <h2>Generate UserId</h2>
     * @param prefix current user count
     * @return User id
     */
    private Long genUserId(Long prefix) {

        String suffix = RandomStringUtils.randomNumeric(5);
        return Long.valueOf(prefix + suffix);
    }
}
