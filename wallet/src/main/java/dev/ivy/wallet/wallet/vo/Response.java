package dev.ivy.wallet.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Controller Response </h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /** Error Code: correct returns 0 */
    private Integer errorCode = 0;

    /** Error message, correct returns empty string */
    private String errorMsg = "";

    /** Response body */
    private Object data;

    /**
     * <h2>Correct response constructor</h2>
     * @param data
     */
    public Response(Object data) {
        this.data = data;
    }

    /**
     * <h2>Empty response</h2>
     * @return
     */
    public static Response success(){
        return new Response();
    }

    /**
     * <h2>Error response</h2>
     * @param errorMsg
     * @return
     */
    public static Response failure(String errorMsg) {
        return new Response(-1, errorMsg, null);
    }
}
