package dev.ivyzhao.wallet.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Universal Response object</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /** Error code, correct returns 0 */
    private Integer errorCode = 0;

    /** Error information, correst return empty string*/
    private String errorMessage = "";

    /** response object */
    private Object data;

    /**
     * <h2>Correct response constructor</h2>
     * @param data response object
     */
    public Response(Object data) {
        this.data = data;
    }
}
