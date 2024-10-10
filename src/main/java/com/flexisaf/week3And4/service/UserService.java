package com.flexisaf.week3And4.service;

import com.flexisaf.week3And4.dto.UserDto;


import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto user);

    void deleteUser (Long userId);

}
