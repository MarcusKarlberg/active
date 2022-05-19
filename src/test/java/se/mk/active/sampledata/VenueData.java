package se.mk.active.sampledata;

import se.mk.active.model.Provider;
import se.mk.active.model.Venue;

public final class VenueData {
    private VenueData() {}
    public static final String VENUE_NAME = "Festivalen";

    public static Venue createVenue() {
        Venue venue = new Venue();
        venue.setId(1L);
        venue.setName(VENUE_NAME);
        venue.setProvider(ProviderData.createProvider());

        return venue;
    }
}
