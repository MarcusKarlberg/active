package se.mk.active.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.mk.active.model.Provider;
import se.mk.active.model.Role;
import se.mk.active.model.UserDto;
import se.mk.active.service.ProviderService;
import se.mk.active.service.UserService;

import java.util.Optional;

@Slf4j
@Component
public class DataInitializer {

    private final UserService userService;
    private final ProviderService providerService;

    @Value("${active.data.admin.username}")
    private String adminUsername;
    @Value("${active.data.admin.password}")
    private String adminPassword;
    @Value("${active.data.admin.provider-name}")
    private String adminProviderName;

    @Autowired
    public DataInitializer(UserService userService, ProviderService providerService) {
        this.userService = userService;
        this.providerService = providerService;
    }

    public void initData() {
        log.info("Initializing Data...");
        if(!this.userService.existsByUsername(this.adminUsername)) {
            initAdminUser();
        }
    }

    private void initAdminUser() {

        final Provider provider = getOrCreateAdminProvider();
        userService.createUser(new UserDto(adminUsername, adminPassword, Role.ROLE_ADMIN, provider.getId()).toUser(),
                provider.getId());
        log.info("Admin user created.");
    }

    private Provider getOrCreateAdminProvider() {
        Optional<Provider> optProvider = this.providerService.getProviderByName(adminProviderName);

        if(optProvider.isPresent()) {
            return optProvider.get();
        } else {
            Provider provider = new Provider();
            provider.setName(this.adminProviderName);

            return providerService.createProvider(provider);
        }
    }
}
