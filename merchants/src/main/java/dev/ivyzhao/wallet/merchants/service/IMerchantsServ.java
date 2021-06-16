package dev.ivyzhao.wallet.merchants.service;

import dev.ivyzhao.wallet.merchants.vo.CreateMerchantsRequest;
import dev.ivyzhao.wallet.merchants.vo.PassTemplate;
import dev.ivyzhao.wallet.merchants.vo.Response;

/**
 * <h1>Merchant Service Interface</h1>
 */
public interface IMerchantsServ {

    /**
     * <h2>Create Merchant Service</h2>
     * @param request {@link CreateMerchantsRequest}
     * @return {@link Response}
     */
    Response createMerchants(CreateMerchantsRequest request);

    /**
     * <h2>Create Merchant info based on id </h2>
     * @param id Merchant id
     * @return {@link Response}
     */
    Response buildMerchantsInfoById(Integer id);

    /**
     * <h2>Release coupon</h2>
     * @param template {@PassTemplate} coupon object
     * @return {link Response}
     */
    Response dropPassTemplate(PassTemplate template);
}
