package emt.ebook.demo.service;

import emt.ebook.demo.model.Role;
import emt.ebook.demo.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findById(String userId);
    User registerUser(User user);
}
