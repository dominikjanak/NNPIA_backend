package cz.janakdom.backend.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteScoreDto {
    private Integer quoteId;
    private Integer userId;
    private byte score;
}
