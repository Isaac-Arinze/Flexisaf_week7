package com.flexisaf.week6_depen_injection.service.impl;

import com.flexisaf.week6_depen_injection.dto.UserDto;
import com.flexisaf.week6_depen_injection.exception.EmailAlreadyExistsException;
import com.flexisaf.week6_depen_injection.exception.ResourceNotFoundException;
import com.flexisaf.week6_depen_injection.model.User;
import com.flexisaf.week6_depen_injection.repository.UserRepository;
import com.flexisaf.week6_depen_injection.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter // setter injection
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    // Field injection
    @Autowired // field injection
    private UserRepository userRepository;


    @Autowired // field injection
    private ModelMapper modelMapper;

    // Constructor injection (preferred for required dependencies)
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // Setter injection
    @Autowired // Setter injection
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired // Setter injection
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        logger.info("Creating user with email: {}", userDto.getEmail());

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists for user");
        }

        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("existingUs", "id", user.getId()));

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
    @Transactional
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(existingUser);
    }
}
