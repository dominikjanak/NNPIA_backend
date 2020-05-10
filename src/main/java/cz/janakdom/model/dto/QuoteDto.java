package cz.janakdom.model.dto;

import cz.janakdom.model.database.Author;
import cz.janakdom.model.database.Category;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {
    private Integer authorId;
    private String quote;
    private Boolean global;
    List<Integer> categories;
}
