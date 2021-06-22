package dev.ivy.wallet.wallet.dao;

import dev.ivy.wallet.wallet.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <h1>Merchants Dao Interface</h1>
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {

    /**
     * <h2>Get Merchant object by name</h2>
     * @param name Merchant id
     * @return {@link Merchants}
     */
    Merchants findByName(String name);

    /**
     * <h2>Get list of Merchants by List of ids</h2>
     * @param ids 商户 ids
     * @return {@link Merchants}
     * */
    List<Merchants> findByIdIn(List<Integer> ids);
}
