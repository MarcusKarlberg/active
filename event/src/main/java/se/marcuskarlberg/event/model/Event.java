package se.marcuskarlberg.event.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Event {

    @Id
    private String id;
    private String name;
    private String description;
    private String providerId;

    //TODO: add dates
}
