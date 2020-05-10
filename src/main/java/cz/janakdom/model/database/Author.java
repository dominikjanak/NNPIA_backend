package cz.janakdom.model.database;

import cz.janakdom.model._base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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

    public String toString(){
        return surname + " " + firstname;
    }
}
