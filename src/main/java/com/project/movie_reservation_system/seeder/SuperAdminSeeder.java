package com.project.movie_reservation_system.seeder;

import com.project.movie_reservation_system.entity.User;
import com.project.movie_reservation_system.enums.Role;
import com.project.movie_reservation_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SuperAdminSeeder(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadSuperAdminUser();
    }

    private void loadSuperAdminUser() {
        User superAdmin = User.builder()
                .role(Role.ROLE_SUPER_ADMIN)
                .username("super_user")
                .password(bCryptPasswordEncoder.encode("super_password"))
                .firstName("super_user_firstname")
                .lastName("super_user_lastname")
                .email("super_user_email")
                .build();

        if(userRepository.findByUsername(superAdmin.getUsername()).isEmpty()){
            userRepository.save(superAdmin);
        }
    }

}
