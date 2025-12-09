package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    private Login login;

    public Login getLogin() { return login; }
    public void setLogin(Login login) { this.login = login; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Login {
        private String result;
        private String token;

        public String getResult() { return result; }
        public void setResult(String result) { this.result = result; }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}
