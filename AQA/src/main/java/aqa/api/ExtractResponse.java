package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractResponse {
    private Query query;

    public Query getQuery() {
        return query;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {
        private Map<String, Page> pages;

        public Map<String, Page> getPages() {
            return pages;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Page {
        private int pageid;
        private String title;
        private String extract;

        public int getPageid() {
            return pageid;
        }

        public String getTitle() {
            return title;
        }

        public String getExtract() {
            return extract;
        }
    }
}
