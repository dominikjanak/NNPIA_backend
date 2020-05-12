package cz.janakdom.backend.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteRatingDto {
    private Integer quoteId;
    private byte score;
}
