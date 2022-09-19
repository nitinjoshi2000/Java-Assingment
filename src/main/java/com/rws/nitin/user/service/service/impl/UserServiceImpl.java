package com.rws.nitin.user.service.service.impl;

import com.rws.nitin.user.service.config.AppConstants;
import com.rws.nitin.user.service.entity.Role;
import com.rws.nitin.user.service.entity.User;
import com.rws.nitin.user.service.exceptions.ResourceNotFoundException;
import com.rws.nitin.user.service.payloads.UserDto;
import com.rws.nitin.user.service.repository.RoleRepository;
import com.rws.nitin.user.service.repository.UserRepository;
import com.rws.nitin.user.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto,User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

//        Roles
        Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepository.save(user);
        return this.modelMapper.map(newUser,UserDto.class);
    }

    @Override
    public UserDto createUser( UserDto userDto) {

        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepository.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

       List<User> users = this.userRepository.findAll();
       List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
       return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        this.userRepository.delete(user);
    }

    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
//        <--Model Mapper->

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
