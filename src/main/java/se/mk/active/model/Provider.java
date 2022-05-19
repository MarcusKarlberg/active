package se.mk.active.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "providers")
public final class Provider implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_info_id", referencedColumnName = "id")
    private ContactInfo contactInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    private Collection<User> users;

    @JsonManagedReference
    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    private Collection<Venue> venues;

}
