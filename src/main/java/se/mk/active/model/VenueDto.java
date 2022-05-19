package se.mk.active.model;


import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public final class VenueDto {
    @NotNull
    private String name;
    @NotNull
    private Long providerId;

    public Venue toVenue() {
        return Venue.builder()
                .name(this.name)
                .build();
    }
}
