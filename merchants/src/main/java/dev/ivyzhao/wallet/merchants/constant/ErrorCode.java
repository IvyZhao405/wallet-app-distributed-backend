package dev.ivyzhao.wallet.merchants.constant;

/**
 * Error Code Enum
 */
public enum ErrorCode {
    SUCCESS(0, ""),
    DUPLICATE_NAME(1, "Merchant name is duplicated"),
    EMPTY_LOGO(2, "Merchant logo is empty"),
    EMPTY_BUSINESS_LICENSE(3, "Merchant business license is empty"),
    EMPTY_ADDRESS(5, "Merchant address is empty"),
    MERCHANTS_NOT_EXISTS(6, "Merchant doesn't exist");

    /** Error code */
    private Integer code;

    /** Error description */
    private String desc;

    ErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }
}
