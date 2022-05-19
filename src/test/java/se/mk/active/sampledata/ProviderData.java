package se.mk.active.sampledata;

import se.mk.active.model.ContactInfo;
import se.mk.active.model.Provider;

public final class ProviderData {
    private ProviderData(){}

    public static final String PROVIDER_NAME = "Solna Kommun";
    public static final String EMAIL = "solna@kommun.se";
    public static final String TOWN = "Solna";

    public static final String P_NUMBER = "0707718396";

    public static final String ZIP = "16962";

    public static final String STREET = "Solgatan 10";

    public static final String URL = "www.solnastad.se";

    public static final String COUNTRY = "SE";

    public static Provider createProvider() {
        Provider provider = new Provider();
        provider.setId(1l);
        provider.setName(PROVIDER_NAME);
        provider.setContactInfo(createContactInfo());

        return provider;
    }

    public static ContactInfo createContactInfo() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(1L);
        contactInfo.setStreetAddress(STREET);
        contactInfo.setCountry(COUNTRY);
        contactInfo.setEmail(EMAIL);
        contactInfo.setPhoneNumber(P_NUMBER);
        contactInfo.setTown(TOWN);
        contactInfo.setZipCode(ZIP);
        contactInfo.setUrl(URL);
        return contactInfo;
    }
}
