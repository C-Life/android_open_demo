package com.het.sdk.demo.event;

/**
 * Created by Administrator on 2017-09-06.
 */

public class ECode {
    public ECode() {
    }

    public static final class Token {
        public static final String EC_TOKEN_OTHERSLOGIN = "othersLogin";
        public static final String EC_TOKEN_REFRESHTOKEN_ERROR = "RefreshTokenError";
        public static final String EC_TOKEN_ACCESSTOKEN_ERROR = "AccessTokenError";
        public static final String EC_TOKEN_TIMESTAMP_ERROR = "timestampError";
        public static final String EC_LOGINOUT = "loginout";
        public static final String EC_NO_NETWORK = "no_network";

        public Token() {
        }
    }

    public static final class Login {
        public static final String EC_LOGIN = "loginSucess";
        public static final String EC_LOGIN_OUT = "loginOut";

        public Login() {
        }
    }

    public static final class Update {
        public static final String VERSION_DIALOG = "version_dialog";

        public Update() {
        }
    }
}
