package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractResponse {
    public Query query;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {
        public Map<String, Page> pages;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Page {
        public int pageid;
        public String title;
        public String extract;
    }
}
