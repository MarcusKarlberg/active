package se.mk.active.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.mk.active.controller.UserController;
import se.mk.active.init.DataInitializer;
import se.mk.active.model.Provider;
import se.mk.active.model.Role;
import se.mk.active.model.User;
import se.mk.active.repository.ProviderRepository;
import se.mk.active.repository.UserRepository;
import se.mk.active.security.JwtBuilderParserImpl;
import se.mk.active.security.SecurityConstants;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    //MockBeans for Spring Security
    @MockBean
    private JwtBuilderParserImpl jwtBuilderParser;

    @MockBean
    private SecurityConstants securityConstants;

    @MockBean
    private UserController userController;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private DataInitializer dataInitializer;

    @MockBean
    private BCryptPasswordEncoder encoder;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void beforeEach() {
        this.userService = new UserServiceImpl(userRepository, providerRepository, passwordEncoder);
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