package cz.janakdom.model.database;

import cz.janakdom.model._base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "quote")
@EqualsAndHashCode(callSuper = true)
public class Quote extends BaseEntity {

    @Getter
    @Setter
    @Column(nullable = false, length = 35, unique = true)
    private String name;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Getter
    @Setter
    @Column(nullable = false, length = 1000, unique = true)
    private String Quote;

    @Getter
    @Setter
    @Column(nullable = false)
    private Boolean global;

    @Getter
    @OneToMany(mappedBy = "quoteId")
    List<QuoteScore> quoteScores;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "quote_category",
            joinColumns = @JoinColumn(name = "quote_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<Category> quoteCategories;

}
