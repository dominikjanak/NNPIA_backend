package cz.janakdom.model.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.janakdom.model._base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User extends BaseEntity {

    @Getter
    @Setter
    @Column(nullable = false, length = 35, unique = true)
    private String username;

    @Getter
    @Setter
    @Column(length = 50)
    private String firstname;

    @Getter
    @Setter
    @Column(length = 50)
    private String surname;

    @Getter
    @Setter
    @Column(length = 100)
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Getter
    @Setter
    @Column(length = 100, unique = true)
    private String email;

    @Getter
    @OneToMany(mappedBy = "userId")
    private final List<QuoteScore> ratedQuotes = new ArrayList<>();
}
