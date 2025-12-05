package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomResponse {
    public Query query;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {
        public List<RandomItem> random;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RandomItem {
        public int id;
        public String title;
    }
}
