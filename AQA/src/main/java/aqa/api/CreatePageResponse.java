package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePageResponse {
    private Edit edit;

    public Edit getEdit() { return edit; }
    public void setEdit(Edit edit) { this.edit = edit; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Edit {
        private String result;
        private int pageid;

        public String getResult() { return result; }
        public void setResult(String result) { this.result = result; }

        public int getPageid() { return pageid; }
        public void setPageid(int pageid) { this.pageid = pageid; }
    }
}
