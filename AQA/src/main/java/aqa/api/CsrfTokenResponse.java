package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CsrfTokenResponse {

    private CSRFToken csfr;

    public CSRFToken getCsfr() { return csfr; }
    public void setCsfr(CSRFToken csfr) { this.csfr = csfr; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CSRFToken {
        private String result;
        private String csrftoken;

        public String getResult() { return result; }
        public void setResult(String result) { this.result = result; }

        public String getToken() { return csrftoken; }
        public void setToken(String csrftoken) { this.csrftoken = csrftoken; }
    }
}
