package se.mk.active.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.mk.active.model.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
