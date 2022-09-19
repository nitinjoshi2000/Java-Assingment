package com.rws.nitin.user.service.security;
import com.rws.nitin.user.service.entity.User;
import com.rws.nitin.user.service.exceptions.NotFoundException;
import com.rws.nitin.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Loading User From Database by Email
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(()-> new NotFoundException("User","email: ",username));
        return user;
    }
}
