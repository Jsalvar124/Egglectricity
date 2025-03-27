package com.egg.egglectricity.services;

import com.egg.egglectricity.entities.UserImpl;
import com.egg.egglectricity.enums.Role;
import com.egg.egglectricity.exceptions.InvalidInputException;
import com.egg.egglectricity.repositories.UserImplRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserImplRepository userImplRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserImpl register(String name, String lastName, String email, String password, String repeatPassword) throws InvalidInputException {
        validateData(name, lastName, email, password, repeatPassword);
        String hashedPassword = passwordEncoder.encode(password);
        UserImpl user = new UserImpl(
                email,
                name,
                lastName,
                hashedPassword,
                Role.USER
        );
        return userImplRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserImpl user = userImplRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        // Spring User Class and Authority list
        List<GrantedAuthority> permits = new ArrayList<>();
        GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+user.getRole().toString());
        permits.add(p);

        // Get HTTP Session
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);

        // Save the user Session "userSession"
        session.setAttribute("userSession", user);
        return new User(user.getEmail(), user.getPassword(), permits); // User from Spring Security Class.
    }

    private void validateData(String name, String lastName, String email, String password, String repeatPassword) throws InvalidInputException {
        if (email == null || email.isBlank()) {
            throw new InvalidInputException("User email cannot be blank.");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { // Regex email validation
            throw new InvalidInputException("Invalid email format.");
        }
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("User name cannot be blank.");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new InvalidInputException("User last name cannot be blank.");
        }
        if (password == null || password.length() < 6) {
            throw new InvalidInputException("User password must have at least 6 characters.");
        }
        if (repeatPassword == null || !repeatPassword.equals(password)) {
            throw new InvalidInputException("Passwords do not match.");
        }
    }
}
