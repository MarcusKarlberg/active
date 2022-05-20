package se.mk.active.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.mk.active.model.Provider;
import se.mk.active.model.ProviderDto;
import se.mk.active.service.ProviderService;

@RestController
@Tag(name = "Provider/Municipality Endpoint")
@RequestMapping("/api/v1/providers")
public class ProviderController {
    private ProviderService providerService;

    @Autowired
    public  ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<Provider> createProvider(@RequestBody ProviderDto providerDTO) {
        final Provider provider = providerDTO.toProvider();
        return new ResponseEntity<>(providerService.createProvider(provider), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Cacheable("provider-cache")
    public ResponseEntity<Provider> getProviderById(@PathVariable Long id) {
        return new ResponseEntity<>(providerService.getProviderById(id), HttpStatus.OK);
    }
}
