package dev.ivyzhao.wallet.merchants.vo;

import dev.ivyzhao.wallet.merchants.constant.ErrorCode;
import dev.ivyzhao.wallet.merchants.dao.MerchantsDao;
import dev.ivyzhao.wallet.merchants.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Create Merchant Request Object</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsRequest {

    /** Merchant name */
    private String name;

    /** Merchant logo */
    private String logoUrl;

    /** Merchant business license */
    private String businessLicenseUrl;

    /** Merchant phone number */
    private String phone;

    /**Merchant address */
    private String address;

    /**
     * <h2>Validate request</h2>
     * @param merchantsDao {@link MerchantsDao}
     * @return {@link ErrorCode}
     */
    public ErrorCode validate(MerchantsDao merchantsDao) {
        if (merchantsDao.findByName(this.name) != null) {
            return ErrorCode.DUPLICATE_NAME;
        }
        if (this.logoUrl == null) {
            return ErrorCode.EMPTY_LOGO;
        }
        if(this.businessLicenseUrl == null) {
            return ErrorCode.EMPTY_BUSINESS_LICENSE;
        }
        if(this.address == null) {
            return ErrorCode.EMPTY_ADDRESS;
        }
        if (this.phone == null) {
            return ErrorCode.EMPTY_PHONE;
        }
        return ErrorCode.SUCCESS;
    }

    /**
     * <h2>Convert Request object to Merchant object</h2>
     * @return {@link Merchants}
     */
    public Merchants toMerchants(){
        Merchants merchants = new Merchants();

        merchants.setName(name);
        merchants.setLogoUrl(logoUrl);
        merchants.setBusinessLicenseUrl(businessLicenseUrl);
        merchants.setPhone(phone);
        merchants.setAddress(address);
        return merchants;
    }
}
