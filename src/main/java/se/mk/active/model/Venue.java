package se.mk.active.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "venues")
public final class Venue implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    //TODO: add ZonedDateTime for start and end.

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Provider provider;

    @JsonManagedReference
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private Collection<Event> events;

    @JsonManagedReference
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private Collection<File> files;
}
