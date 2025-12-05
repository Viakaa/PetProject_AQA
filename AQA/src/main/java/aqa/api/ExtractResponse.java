package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractResponse {
    private Query query;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {
        private Map<String, Page> pages;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Page {
        private int pageid;
        private String title;
        private String extract;
    }
}
