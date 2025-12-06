package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomResponse {
    private Query query;

    public Query getQuery() {
        return query;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {
        private List<RandomItem> random;

        public List<RandomItem> getRandom() {
            return random;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RandomItem {
        private int id;
        private String title;
        private int ns;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public int getNs() {
            return ns;
        }
    }
}
