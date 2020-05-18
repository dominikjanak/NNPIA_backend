package cz.janakdom.backend.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.janakdom.backend.model._base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "author")
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"firstname", "surname"})
})
public class Author extends BaseEntity {

    @Getter
    @Setter
    @Column(nullable = false, length = 50)
    private String firstname;

    @Getter
    @Setter
    @Column(nullable = false, length = 50)
    private String surname;

    @Getter
    @Setter
    @Column(length = 3)
    private String country;

    @Getter
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "author")
    private final List<Quote> quotes = new ArrayList<>();

    public String toString(){
        return surname + " " + firstname;
    }
}
