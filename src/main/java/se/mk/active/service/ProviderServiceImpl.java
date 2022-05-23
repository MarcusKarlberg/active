package se.mk.active.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.mk.active.model.Provider;
import se.mk.active.repository.ProviderRepository;
import se.mk.active.service.exception.ResourceNotFoundException;

import java.util.Optional;

import static se.mk.active.service.exception.ErrorMsg.PROVIDER_NOT_FOUND;
import static se.mk.active.service.exception.ErrorMsg.createErrorMsgAndLog;

@Service
public final class ProviderServiceImpl implements ProviderService {

    private static final Logger LOG = LoggerFactory.getLogger(ProviderServiceImpl.class);
    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Provider getProviderById(Long id) {
        Optional<Provider> result = providerRepository.findById(id);

        if(result.isPresent()) {
            return result.get();
        } else {
            throw new ResourceNotFoundException(createErrorMsgAndLog(PROVIDER_NOT_FOUND, id, LOG));
        }
    }

    @Override
    public Optional<Provider> getProviderByName(String name) {
        return this.providerRepository.findByName(name);
    }
}
