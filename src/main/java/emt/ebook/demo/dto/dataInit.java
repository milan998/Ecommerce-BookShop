//package emt.ebook.demo.dto;
//
//import emt.ebook.demo.model.Role;
//import emt.ebook.demo.repository.RoleRepository;
//import emt.ebook.demo.service.UserService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class dataInit {
//
//    public static List<Role> role = new ArrayList<>();
//    public final RoleRepository roleRepository;
//    public final UserService userService;
//    public final PasswordEncoder passwordEncoder;
//
//    public dataInit(RoleRepository roleRepository, UserService userService, PasswordEncoder passwordEncoder) {
//        this.roleRepository = roleRepository;
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @PostConstruct
//        public void init(){
//        role.add(new Role("ROLE_ADMIN"));
//        role.add(new Role("ROLE_USER"));
//        this.roleRepository.saveAll(role);
//    }
//}
