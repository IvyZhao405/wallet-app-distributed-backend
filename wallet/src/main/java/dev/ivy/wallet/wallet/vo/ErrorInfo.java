package dev.ivy.wallet.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Generic error message</h1>
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo<T> {

    /** Generic error code*/
    public static final Integer ERROR = -1;

    /** Specific error code*/
    private Integer code;

    /** Error message */
    private String message;

    /** Request url */
    private String url;

    /** Request returned data */
    private T data;
}
