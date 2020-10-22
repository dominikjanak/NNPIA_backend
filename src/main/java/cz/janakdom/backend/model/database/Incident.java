package cz.janakdom.backend.model.database;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "incident")
public class Incident {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false, length = 50, unique = true)
    private Date creationDatetime;

    @Getter
    @Setter
    @Column(nullable = false, length = 50, unique = true)
    private String location;

    @Getter
    @Setter
    @Column(nullable = false, length = 5000)
    private String note;

    @Getter
    @Setter
    @Column(nullable = true, length = 5000)
    private String comment;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}
