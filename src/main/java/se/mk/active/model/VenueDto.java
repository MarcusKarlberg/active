package se.mk.active.model;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public final class VenueDto {
    @NotBlank(message = "Venue name can not be empty")
    private String name;
    @NotNull(message = "Provider ID can not be null")
    private Long providerId;

    public Venue toVenue() {
        return Venue.builder()
                .name(this.name)
                .build();
    }

    public static VenueDto toDto(Venue venue) {
        return VenueDto.builder()
                .name(venue.getName())
                .providerId(venue.getProvider().getId())
                .build();
    }
}
