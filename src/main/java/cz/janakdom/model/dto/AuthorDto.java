package cz.janakdom.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    private String firstname;
    private String surname;
    private String country;

    public String toString(){
        return firstname + " " + surname + " ["+country.toUpperCase()+"]";
    }

    public String authorName(){
        return firstname + " " + surname;
    }
}
