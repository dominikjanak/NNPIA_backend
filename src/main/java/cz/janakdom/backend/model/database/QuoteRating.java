package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "quote_score")
public class QuoteRating {

    @Getter
    @Setter
    @EmbeddedId
    @JsonProperty(access =  JsonProperty.Access.READ_ONLY)
    private QuoteScoreKey id;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("quote_id")
    @JoinColumn(name = "quote_id")
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private Quote quote;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Getter
    @Setter
    @Column(nullable = false)
    private byte score;
}
