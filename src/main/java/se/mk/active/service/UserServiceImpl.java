package se.mk.active.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.mk.active.model.Provider;
import se.mk.active.model.User;
import se.mk.active.repository.ProviderRepository;
import se.mk.active.repository.UserRepository;
import se.mk.active.service.exception.ResourceNotFoundException;

import java.util.Optional;

import static se.mk.active.service.exception.ErrorMsg.*;

@Service
public final class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final ProviderRepository providerRepository;

    public UserServiceImpl(UserRepository userRepository, ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user, Long providerId) {
        Optional<Provider> provider = providerRepository.findById(providerId);

        if(provider.isPresent()) {
            user.setProvider(provider.get());
            return userRepository.save(user);
        } else {
            throw new ResourceNotFoundException(createErrorMsgAndLog(PROVIDER_NOT_FOUND, providerId, LOG));
        }
    }

    @Override
    public User getUser(Long id) {
        Optional<User> userOpt = this.userRepository.findById(id);

        if(userOpt.isPresent()) {
            return userOpt.get();
        } else {
            throw new ResourceNotFoundException(createErrorMsgAndLog(USER_NOT_FOUND, id, LOG));
        }
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }
}
