package dev.ivy.wallet.wallet.service;

import dev.ivy.wallet.wallet.vo.PassTemplate;

/**
 * <h1>Drop Pass to Hbase service</h1>
 */
public interface IHBasePassService {

    /**
     * <h2>Write PassTemplate to HBase</h2>
     * @param passTemplate {@link PassTemplate}
     * @return success/fail
     */
    boolean dropPassTemplateToHBase(PassTemplate passTemplate);
}
