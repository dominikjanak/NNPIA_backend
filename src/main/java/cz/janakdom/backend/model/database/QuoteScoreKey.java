package cz.janakdom.backend.model.database;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QuoteScoreKey implements Serializable {

    @Getter
    @Setter
    @Column(name = "quote_id")
    private Integer quoteId;

    @Getter
    @Setter
    @Column(name = "user_id")
    private Integer userId;
}
