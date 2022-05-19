package se.mk.active.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.mk.active.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
}
