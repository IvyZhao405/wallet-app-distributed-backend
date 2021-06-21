package dev.ivy.wallet.wallet.service;

import dev.ivy.wallet.wallet.vo.Pass;
import dev.ivy.wallet.wallet.vo.Response;

/**
 * <h1>Get user's coupon info </h1>
 */
public interface IUserPassService {

    /**
     * <h2>Get user's coupon info, "my coupon" service interface</h2>
     * @param userId User id
     * @return {@link Response}
     * @throws Exception
     */
    Response getUserPassInfo(Long userId) throws Exception;

    /**
     * <h2>Get user's used coupon info</h2>
     * @param userId User id
     * @return {@link Response}
     */
    Response getUserUsedPassInfo(Long userId) throws Exception;

    /**
     * <h2>Get user's all coupon</h2>
     * @param userId User Id
     * @return {@link Response}
     * @throws Exception
     */
    Response getUserAllPassInfo(Long userId) throws Exception;

    /**
     * <h2>User use coupon</h2>
     * @param pass {link Pass}
     * @return {@link Response}
     */
    Response userUsePass(Pass pass);
}
