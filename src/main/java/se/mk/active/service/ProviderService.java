package se.mk.active.service;

import se.mk.active.model.Provider;

import java.util.Optional;

public interface ProviderService {
    Provider createProvider(Provider provider);
    Provider getProviderById(Long id);
    Optional<Provider> getProviderByName(String name);
}
