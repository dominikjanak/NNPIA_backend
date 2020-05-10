package cz.janakdom.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.janakdom.model._base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category")
public class Category extends BaseEntity {

    @Getter
    @Setter
    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Getter
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "quoteCategories")
    private List<Quote> quotes;
}
