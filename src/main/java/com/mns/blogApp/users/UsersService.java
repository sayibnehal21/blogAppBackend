package com.mns.blogApp.users;

import com.mns.blogApp.users.dtos.CreateUserRequest;
import com.mns.blogApp.users.dtos.LoginUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequest u) {
        UserEntity newUser = modelMapper.map(u, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(u.getPassword()));
        return usersRepository.save(newUser);
    }

    public UserEntity getUser(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity getUser(Long userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserEntity loginUser(LoginUserRequest request) {
        var user = usersRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException(request.getUsername()));
        var passMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passMatch) throw new InvalidCredentialsException();
        return user;
    }


    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("User with username: " + username + " not found");
        }

        public UserNotFoundException(Long userId) {
            super("User with id: " + userId + " not found");
        }
    }

    public static class InvalidCredentialsException extends IllegalArgumentException {
        public InvalidCredentialsException() {
            super("Invalid username or password combination");
        }
    }

}