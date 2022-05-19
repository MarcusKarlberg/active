package se.mk.active.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import se.mk.active.model.Provider;
import se.mk.active.repository.ProviderRepository;
import se.mk.active.sampledata.ProviderData;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProviderServiceImplTest {

    @Mock
    private ProviderRepository providerRepository;

    private ProviderService providerService;

    private Provider provider;

    @BeforeEach
    public void beforeEach() {
        this.provider = ProviderData.createProvider();
        this.providerService = new ProviderServiceImpl(providerRepository);
    }

    @Test
    void createProvider() {
        when(providerRepository.save(any(Provider.class))).thenReturn(this.provider);
        Provider savedProvider = providerService.createProvider(provider);
        assertEquals(savedProvider, this.provider);
        verify(providerRepository, times(1)).save(any(Provider.class));
    }

    @Test
    void getProviderById() {
        when(providerRepository.findById(any())).thenReturn(Optional.ofNullable(this.provider));
        Provider foundProvider = providerService.getProviderById(any());
        assertEquals(foundProvider, this.provider);
        verify(providerRepository, times(1)).findById(any());
    }
}