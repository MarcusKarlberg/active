package se.mk.active.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_info")
public final class ContactInfo implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String streetAddress;
    private String town;
    private String zipCode;
    private String country;
    private String email;
    private String phoneNumber;
    private String url;

    @JsonBackReference
    @OneToOne(mappedBy = "contactInfo")
    private Provider provider;
}
