package se.mk.active.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "events")
public final class Event implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Event name can not be empty")
    private String name;
    //TODO: add ZonedDateTime for start and end.

    @JsonBackReference
    @NotNull
    @ManyToOne
    private Venue venue;
}
