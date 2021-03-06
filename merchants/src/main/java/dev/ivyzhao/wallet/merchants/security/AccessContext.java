package dev.ivyzhao.wallet.merchants.security;

/**
 * <h1> Use ThreadLocal to store every thread's Token info</h1>
 */
public class AccessContext {
    private static final ThreadLocal<String> token = new ThreadLocal<String>();

    public static String getToken(){
        return token.get();
    }

    public static void setToken(String tokenStr) {
        token.set(tokenStr);
    }

    public static void clearAccessKey(){
        token.remove();
    }

}
