package aqa.task16;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCreateOrganizationModel {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("desc")
    private String description;

    @JsonProperty("url")
    private String url;

    @JsonProperty("website")
    private String website;

    @JsonProperty("teamType")
    private String teamType;

    @JsonProperty("logoHash")
    private String logoHash;

    @JsonProperty("logoUrl")
    private String logoUrl;

    @JsonProperty("offering")
    private String offering;

    @JsonProperty("products")
    private List<Object> products; // Список може бути складнішим, але в відповіді - пустий масив

    @JsonProperty("powerUps")
    private List<Object> powerUps; // Аналогічно, пустий масив

    @JsonProperty("idMemberCreator")
    private String idMemberCreator;

    @JsonProperty("limits")
    private Map<String, Object> limits; // Об'єкт limits порожній, тому використовуємо Map


}
