package com.flexisaf.week3And4.service.impl;

import com.flexisaf.week3And4.dto.UserDto;
import com.flexisaf.week3And4.exception.EmailAlreadyExistsException;
import com.flexisaf.week3And4.exception.ResourceNotFoundException;
import com.flexisaf.week3And4.model.User;
import com.flexisaf.week3And4.repository.UserRepository;

import com.flexisaf.week3And4.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;


    @Override
    public UserDto createUser(UserDto userDto) {

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("Email Already exists for user");
        }

        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return savedUserDto;
    }
    @Override
    public UserDto getUserById(Long userId) {
        User user =  userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        return modelMapper.map(user, UserDto.class);
    }
    @Override
    public List<UserDto> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users.stream().map((user)-> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
    @Override
    public UserDto updateUser(UserDto user) {

        User existingUser = userRepository.findById(user.getId()).orElseThrow(()->new ResourceNotFoundException("existingUs", "id", user.getId()));

        existingUser.setFirstname(user.getFirstName());
        existingUser.setMiddleName(user.getMiddleName());
        existingUser.setLastname(user.getLastName());
        existingUser.setContactAddress(user.getContactAddress());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        userRepository.deleteById(userId);
    }
}