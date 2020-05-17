package cz.janakdom.backend.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {
    private Integer authorId;
    private String quote;
    private Boolean global;
    List<Integer> categories = new ArrayList<>();
}
