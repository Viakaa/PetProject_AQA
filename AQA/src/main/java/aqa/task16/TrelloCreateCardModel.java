package aqa.task16;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCreateCardModel {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("idList")
    private String idList;

    @JsonProperty("due")
    private String due;
}
