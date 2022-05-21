package se.mk.active.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.mk.active.model.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
