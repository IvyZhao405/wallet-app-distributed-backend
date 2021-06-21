package dev.ivy.wallet.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <h1>Inventory Response</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    /** User id
     *  need user id to filter out coupons the User has already claimed
     */
    private Long userId;

    /** Coupon Template info */
    private List<PassTemplateInfo> passTemplateInfos;

}
