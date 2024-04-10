package com.labellecave.user.service;

import com.labellecave.user.exception.ForbiddenException;
import com.labellecave.user.exception.NotFoundException;
import com.labellecave.user.model.User;
import com.labellecave.user.model.dto.UserDto;
import com.labellecave.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto register(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public UserDto getUser(long id) {
        User user = userRepository.findById(id).orElseThrow();
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto updateUser(long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());
        user.setAdmin(userDto.isAdmin());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new ForbiddenException("Invalid password");
        }

        return jwtService.generateToken(user.toMap());
    }
}
