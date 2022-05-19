package se.mk.active.sampledata;

import se.mk.active.model.Event;

public final class EventData {
    private EventData() {}

    private static final Long ID = 1L;

    public static final String NAME = "Event 1";

    public static Event createEvent() {
        Event event = new Event();
        event.setId(ID);
        event.setName(NAME);

        return event;
    }
}
