package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/2/6.
 */

public class TOkenBean {

    /**
     * code : 1000
     * message : 获取oss验证token成功
     * data : {"AccessKeySecret":"E97gRXdkBGWdwXP3jsrARhQ3avPN5FZyxV2PLDDoYtNZ","AccessKeyId":"STS.E8e37A28Yfm1QxkqA7EGjsuik","SecurityToken":"CAISigJ1q6Ft5B2yfSjIpPvQeI313+d40a/aU17alUFiSchGnLDCiTz2IHBFeXJhA+0bt/g0nGxX7PgblrgpE8YVHRacMJErs8oKr17/PNJ7MnsVE/hW5qe+EE2/VjTJvqaLEdibIfrZfvCyESem8gZ43br9cxi7QlWhKufnoJV7b9MRLGbaAD1dH4UUXEgAzvUXLnzML/2gHwf3i27LdipStxF7lHl05NbYoKiV4QGMi0bhmK1H5dazAOD9MZc1ZM4lAo/rg7UmK/GZ6kMKtUgWrpURpbdf5DLKsuuaB1Rs+BicO4LWiIY/fVIiN/JnRvcY96Cgyqcm4/axkJ/sjh1JPOxTTzR1xn5qQ3ZVchqAAXcgI5cxLf1RJbnFhHTsZKvvPhmXwxb9//C0xKwZb8G4cCcZ71cC2SMTjkVbP7z0QhQWCt+684ucDjpt7Nu6R2YhlJcXtKd4FZYjUQNKfaS5gJVrL0M4pnd5c2MjD4adMGLYsic7yLZts+M2sHC+EG652rpkwNmmtIXrSmWvpSuR"}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * AccessKeySecret : E97gRXdkBGWdwXP3jsrARhQ3avPN5FZyxV2PLDDoYtNZ
         * AccessKeyId : STS.E8e37A28Yfm1QxkqA7EGjsuik
         * SecurityToken : CAISigJ1q6Ft5B2yfSjIpPvQeI313+d40a/aU17alUFiSchGnLDCiTz2IHBFeXJhA+0bt/g0nGxX7PgblrgpE8YVHRacMJErs8oKr17/PNJ7MnsVE/hW5qe+EE2/VjTJvqaLEdibIfrZfvCyESem8gZ43br9cxi7QlWhKufnoJV7b9MRLGbaAD1dH4UUXEgAzvUXLnzML/2gHwf3i27LdipStxF7lHl05NbYoKiV4QGMi0bhmK1H5dazAOD9MZc1ZM4lAo/rg7UmK/GZ6kMKtUgWrpURpbdf5DLKsuuaB1Rs+BicO4LWiIY/fVIiN/JnRvcY96Cgyqcm4/axkJ/sjh1JPOxTTzR1xn5qQ3ZVchqAAXcgI5cxLf1RJbnFhHTsZKvvPhmXwxb9//C0xKwZb8G4cCcZ71cC2SMTjkVbP7z0QhQWCt+684ucDjpt7Nu6R2YhlJcXtKd4FZYjUQNKfaS5gJVrL0M4pnd5c2MjD4adMGLYsic7yLZts+M2sHC+EG652rpkwNmmtIXrSmWvpSuR
         */

        private String AccessKeySecret;
        private String AccessKeyId;
        private String SecurityToken;

        public String getAccessKeySecret() {
            return AccessKeySecret;
        }

        public void setAccessKeySecret(String AccessKeySecret) {
            this.AccessKeySecret = AccessKeySecret;
        }

        public String getAccessKeyId() {
            return AccessKeyId;
        }

        public void setAccessKeyId(String AccessKeyId) {
            this.AccessKeyId = AccessKeyId;
        }

        public String getSecurityToken() {
            return SecurityToken;
        }

        public void setSecurityToken(String SecurityToken) {
            this.SecurityToken = SecurityToken;
        }
    }
}
