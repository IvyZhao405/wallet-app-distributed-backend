package dev.ivy.wallet.wallet.service;

import dev.ivy.wallet.wallet.vo.Response;

/**
 * <h1>Get coupon remaining quantity info:
 * only return haven't claimed coupon.
 * Interface
 * </h1>
 */
public interface IInventoryService {

    /**
     * <h2>Get coupon inventory info</h2>
     * @param userId User id
     * @return {@link Response}
     * @throws Exception
     */
    Response getInventoryInfo(Long userId) throws Exception;
}
