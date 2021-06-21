package dev.ivy.wallet.wallet.service;

import dev.ivy.wallet.wallet.vo.GainPassTemplateRequest;
import dev.ivy.wallet.wallet.vo.Response;

/**
 * <h1>User claim coupon service interface</h1>
 */
public interface IGainPassTemplateService {

    /**
     * <h2>User claim coupon</h2>
     * @param request {@link GainPassTemplateRequest}
     * @return {@link Response}
     * @throws Exception
     */
    Response gainPassTemplate(GainPassTemplateRequest request) throws Exception;
}
