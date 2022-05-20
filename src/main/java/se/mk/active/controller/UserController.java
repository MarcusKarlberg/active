package se.mk.active.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.mk.active.model.User;
import se.mk.active.model.UserDto;
import se.mk.active.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public final class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDTO) {
        final User user = userService.createUser(userDTO.toUser(), userDTO.getProviderId());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
