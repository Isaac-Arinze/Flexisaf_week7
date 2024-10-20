package com.flexisaf.week6_depen_injection.service;

import com.flexisaf.week6_depen_injection.dto.UserDto;


import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto user);

    void deleteUser (Long userId);

}
