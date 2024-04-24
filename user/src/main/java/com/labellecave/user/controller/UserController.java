package com.labellecave.user.controller;


import com.labellecave.user.exception.ForbiddenException;
import com.labellecave.user.model.dto.ExceptionDto;
import com.labellecave.user.model.dto.UserDto;
import com.labellecave.user.service.AuthService;
import com.labellecave.user.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User", description = "Specific to users, manages the registration, login, update and deletion of users.")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    @Schema(
            title = "Register a new user",
            description = "There is no need to be authenticated to register a new user."
    )
    @ApiResponse(responseCode = "201", description = "User created")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.register(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Schema(
            title = "Login a user",
            description = "There is no need to be authenticated to login a user. The token returned is used to authenticate the user in the future."
    )
    @ApiResponse(responseCode = "200", description = "User logged in")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    public ResponseEntity<String> login(@Email @RequestParam String email, @RequestParam String password) {
        String token = userService.login(email, password);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Schema(
            title = "Get a user by id",
            description = "The user must be authenticated to get a user by id."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    public ResponseEntity<UserDto> getUser(@PathVariable long id, Authentication authentication) {

        String token = authentication.getPrincipal().toString();
        if (authService.isNotAdminOrSameUser(token, id)) {
            throw new ForbiddenException("You are not allowed to access this resource.");
        } else {
            UserDto user = userService.getUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    @Schema(
            title = "Update a user",
            description = "The user must be authenticated to update a user."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "User updated")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    public ResponseEntity<UserDto> updateUser(@PathVariable long id, @Valid @RequestBody UserDto userDto, Authentication authentication) {
        String token = authentication.getPrincipal().toString();
        if (authService.isNotAdminOrSameUser(token, id)) {
            throw new ForbiddenException("You are not allowed to access this resource.");
        } else {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Schema(
            title = "Delete a user",
            description = "The user must be authenticated to delete a user."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "204", description = "User deleted")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    public ResponseEntity<Void> deleteUser(@PathVariable long id, Authentication authentication) {
        String token = authentication.getPrincipal().toString();
        if (authService.isNotAdminOrSameUser(token, id)) {
            throw new ForbiddenException("You are not allowed to access this resource.");
        } else {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
