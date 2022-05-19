package se.mk.active.model;

import lombok.Getter;

@Getter
public final class EventDto {
    private String name;
    private Long venueId;

    public Event toEvent() {
        return Event.builder()
                .name(name)
                .build();
    }
}
