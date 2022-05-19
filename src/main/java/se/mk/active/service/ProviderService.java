package se.mk.active.service;

import se.mk.active.model.Provider;

public interface ProviderService {
    Provider createProvider(Provider provider);
    Provider getProviderById(Long id);
}
