package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
    public Query query;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {
        public List<SearchItem> search;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchItem {
        public int pageid;
        public String title;
    }
}
