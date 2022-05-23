package se.mk.active.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.mk.active.model.Provider;
import se.mk.active.model.User;
import se.mk.active.repository.ProviderRepository;
import se.mk.active.repository.UserRepository;
import se.mk.active.service.exception.ResourceNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static se.mk.active.service.exception.ErrorMsg.*;

@Service
public final class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ProviderRepository providerRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProviderRepository providerRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
        this.encoder = encoder;
    }

    @Override
    public User createUser(User user, Long providerId) {
        Optional<Provider> provider = providerRepository.findById(providerId);

        if(provider.isPresent()) {
            user.setProvider(provider.get());
            user.setPassword(encoder.encode(user.getPassword()));
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

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(username);

        if(optUser.isPresent()) {
            User user = optUser.get();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(authority));
        } else {
            throw new ResourceNotFoundException("User: " + username + " not found...");
        }
    }
}
