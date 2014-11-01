package bo.net.tigo.domain;

/**
 * Created by aralco on 10/22/14.
 */
public class Token {
    private final String access_token;
    private final String token_type;
    private final String refresh_token;
    private final int expires_in;

    public Token(String access_token, String token_type, String refresh_token, int expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }
}
