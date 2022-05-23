package se.mk.active.service;

import se.mk.active.model.User;

public interface UserService {
    User createUser(User user, Long providerId);
    User getUser(Long id);
    void deleteById(Long id);
    boolean existsByUsername(String username);
}
