package cz.janakdom.model.database;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "quote_score")
public class QuoteScore {

    @EmbeddedId
    private QuoteScoreKey id;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("quote_id")
    @JoinColumn(name = "quote_id")
    private Quote quoteId;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User userId;
}
