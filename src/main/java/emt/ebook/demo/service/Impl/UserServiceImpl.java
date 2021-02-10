package emt.ebook.demo.service.Impl;

import emt.ebook.demo.model.User;
import emt.ebook.demo.model.exceptions.UserAlreadyExistsException;
import emt.ebook.demo.model.exceptions.UserNotFoundException;
import emt.ebook.demo.repository.UserRepository;
import emt.ebook.demo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User registerUser(User user) {
       if(this.userRepository.existsById(user.getUsername())){
           throw new UserAlreadyExistsException(user.getUsername());
       }
       return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findById(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
