package se.mk.active.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.mk.active.model.Provider;
import se.mk.active.model.Role;
import se.mk.active.model.User;
import se.mk.active.repository.ProviderRepository;
import se.mk.active.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {
    private static final String USERNAME = "username@hotmail.com";
    private static final String PASSWORD = "username@hotmail.com";
    private static final Role ROLE = Role.ROLE_USER;

    private User user;

    private Provider provider;

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProviderRepository providerRepository;

    @BeforeEach
    public void beforeEach() {
        this.userService = new UserServiceImpl(userRepository, providerRepository);
        this.user = createUser();
        this.provider = createProvider();
        this.provider.setId(1L);
        this.user.setProvider(this.provider);
    }

    @Test
    public void createUserTest() {
        User user = createUser();
        when(userRepository.save(any())).thenReturn(user);
        when(providerRepository.findById(any())).thenReturn(Optional.ofNullable(provider));

        User result = userService.createUser(user, any());
        assertEquals(user, result);
    }

    private User createUser() {
        return new User(1L, USERNAME, PASSWORD, ROLE, null);
    }

    private Provider createProvider() {
        return new Provider();
    }
}