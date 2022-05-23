package se.mk.active.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public final class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String username;
    private String password;
    private Role role;

    //provider_id is stored in JWT token payload
    @JsonIgnoreProperties(value = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Provider provider;
}
