package aqa.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomResponse {
    private Query query;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {
        private List<RandomItem> random;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RandomItem {
        private int id;
        private String title;
        private int ns;
    }
}
