package dev.ivy.wallet.wallet.log;

/**
 * <h1>Log record constants</h1>
 */
public class LogConstants {

    /**
     * <h2>User action names</h2>
     */
    public class ActionName{

        /**User check pass info */
        public static final String USER_PASS_INFO = "UserPassInfo";

        /**User check already used pass info */
        public static final String USER_USED_PASS_INFO = "UserPassInfo";

        /**User use pass info */
        public static final String USER_USE_PASS = "UserUsePass";

        /**User get pass inventory info */
        public static final String INVENTORY_INFO = "InventoryInfo";

        /**User claim inventory info */
        public static final String GAIN_PASS_TEMPLATE = "InventoryInfo";

        /**User create feedback */
        public static final String CREATE_FEEDBACK = "CreateFeedback";

        /**User get feedback */
        public static final String GET_FEEDBACK = "GetFeedback";

        /**Create User */
        public static final String CREATE_USER = "CreateUser";
    }
}
