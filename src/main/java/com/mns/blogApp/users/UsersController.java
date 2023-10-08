package com.mns.blogApp.users;

import com.mns.blogApp.common.dtos.ErrorResponse;
import com.mns.blogApp.users.dtos.CreateUserRequest;
import com.mns.blogApp.users.dtos.LoginUserRequest;
import com.mns.blogApp.users.dtos.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    public UsersController(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/signup")
    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest request) {
        UserEntity savedUser =  usersService.createUser(request);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());

        return ResponseEntity.created(savedUserUri)
                .body(modelMapper.map(savedUser, UserResponse.class));
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request) {
        UserEntity savedUser =  usersService.loginUser(request);
        return ResponseEntity.ok(modelMapper.map(savedUser, UserResponse.class));
    }


    @ExceptionHandler({
            UsersService.UserNotFoundException.class,
            UsersService.InvalidCredentialsException.class
    })
    ResponseEntity<ErrorResponse> handleUserExceptions(Exception ex) {
        String message;
        HttpStatus status;

        if (ex instanceof UsersService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof UsersService.InvalidCredentialsException) {
            message=ex.getMessage();
            status=HttpStatus.UNAUTHORIZED;
        } else {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
