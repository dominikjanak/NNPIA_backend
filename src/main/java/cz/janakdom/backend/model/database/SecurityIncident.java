package cz.janakdom.backend.model.database;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "security_incident")
public class SecurityIncident {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false)
    private Boolean checked = false;

    @Getter
    @Setter
    @Column(nullable = false)
    private Boolean crime = false;

    @Getter
    @Setter
    @Column(nullable = false)
    private Boolean police = false;

    @Getter
    @Setter
    @Column(nullable = false, length = 150)
    private String carriage;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "manager", nullable = false)
    private User manager;

    @Getter
    @OneToOne(mappedBy = "securityIncident")
    private Incident incident;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fireIncident_id", referencedColumnName = "id")
    private FireIncident fireIncident = null;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "fire_brigade_unit_in_incident",
            joinColumns = @JoinColumn(name = "security_incident_id"),
            inverseJoinColumns = @JoinColumn(name = "fire_brigade_unit_id"))
    private final List<FireBrigadeUnit> fireBrigadeUnits = new ArrayList<>();

}
