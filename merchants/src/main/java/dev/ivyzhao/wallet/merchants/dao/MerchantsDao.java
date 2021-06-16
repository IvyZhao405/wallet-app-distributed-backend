package dev.ivyzhao.wallet.merchants.dao;

import dev.ivyzhao.wallet.merchants.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>Merchants Dao Interface</h1>
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {
    /**
     * <h2>Get Merchant object by id</h2>
     * @param id Merchant id
     * @return {@link Merchants}
     */
    Merchants findById(Integer id);

    /**
     * <h2>Get Merchant object by name</h2>
     * @param name Merchant id
     * @return {@link Merchants}
     */
    Merchants findByName(String name);
}
