package cz.janakdom.backend.model.dto;

import cz.janakdom.backend.model.database.Author;
import cz.janakdom.backend.model.database.Category;
import cz.janakdom.backend.model.database.User;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputQuoteDto implements Serializable {
    private int id;
    private Author author;
    private String quote;
    private Boolean global;
    private User user;
    private double score;
    private int userscore;
    private boolean uservoted;
    private List<Category> categories = new ArrayList<>();
}
