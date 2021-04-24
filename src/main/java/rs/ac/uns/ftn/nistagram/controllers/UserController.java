package rs.ac.uns.ftn.nistagram.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.auth.user.User;
import rs.ac.uns.ftn.nistagram.controllers.DTOs.RegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final ModelMapper mapper;
    private final UserService service;

    public UserController(ModelMapper mapper, UserService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequestDTO registrationRequest) {
        service.register(mapper.map(registrationRequest, User.class), registrationRequest.getPassword());
        return ResponseEntity.ok("User successfully registered");
    }

    @GetMapping("/activate/{uuid}")
    public ResponseEntity<String> activate(@PathVariable String uuid) {
        service.activate(uuid);
        return ResponseEntity.ok("Your account is activated. You may now log in.");
    }

}
