package emt.ebook.demo.service.Impl;

import emt.ebook.demo.model.Role;
import emt.ebook.demo.model.User;
import emt.ebook.demo.model.exceptions.InvalidArgumentsException;
import emt.ebook.demo.model.exceptions.InvalidUserCredentialsException;
import emt.ebook.demo.model.exceptions.PasswordsDoNotMatchException;
import emt.ebook.demo.repository.RoleRepository;
import emt.ebook.demo.repository.UserRepository;
import emt.ebook.demo.service.AuthService;
import emt.ebook.demo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthServiceImpl(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String getCurrentUserId() {
        return this.getCurrentUser().getUsername();
    }

    @Override
    public User signUpUser(String username, String password, String repeatedPassword) {
        if(!password.equals(repeatedPassword)){
            throw new PasswordsDoNotMatchException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(userRole));
        return this.userService.registerUser(user);
    }


}
