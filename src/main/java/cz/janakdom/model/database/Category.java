package cz.janakdom.model.database;

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
    private String Name;

    @Getter
    @ManyToMany(mappedBy = "quoteCategories")
    private List<Quote> quotes;
}
