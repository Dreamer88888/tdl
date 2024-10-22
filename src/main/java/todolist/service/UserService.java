package todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todolist.dto.LoginRequestDto;
import todolist.dto.LoginResponseDto;
import todolist.dto.UserRequestDto;
import todolist.dto.UserUpdateDto;
import todolist.entity.EmmaUser;
import todolist.repository.UserRepository;
import todolist.security.UserDetailsImpl;
import todolist.utils.JwtUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public List<EmmaUser> findAll() {
        return userRepository.findAll();
    }

    public EmmaUser findById(UUID id) {
        Optional<EmmaUser> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NoSuchElementException(String.format("User with id %s not found", id));
        }

        return user.get();
    }

    public EmmaUser add(UserRequestDto userRequestDto) {
        EmmaUser user = EmmaUser.builder()
                .name(userRequestDto.getName())
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .createdAt(System.currentTimeMillis())
                .build();

        return userRepository.save(user);
    }

    public EmmaUser update(UserUpdateDto userUpdateDto) {
        EmmaUser oldUser = findById(userUpdateDto.getId());

        oldUser.setName(userUpdateDto.getName());

        return userRepository.save(oldUser);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwt(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        EmmaUser user = findByUsername(loginRequestDto.getUsername());

        return LoginResponseDto.builder()
                .jwt(jwt)
                .userId(userDetails.getId())
                .username(user.getUsername())
                .name(user.getName())
                .roles(roles)
                .build();
    }

    private EmmaUser findByUsername (String username) {
        Optional<EmmaUser> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new NoSuchElementException(String.format("User with username %s not found", username));
        }

        return user.get();
    }

}
