package com.rws.nitin.user.service.service;
import com.rws.nitin.user.service.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto registerNewUser(UserDto user);

    UserDto createUser (UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);


}
