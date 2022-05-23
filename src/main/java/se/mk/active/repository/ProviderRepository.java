package se.mk.active.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.mk.active.model.Provider;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Optional<Provider> findByName(String name);
}
