package org.example.restfuldemo.config;

import lombok.RequiredArgsConstructor;
import org.example.restfuldemo.constants.Constants;
import org.example.restfuldemo.entity.User;
import org.example.restfuldemo.exception.ResourceNotFoundException;
import org.example.restfuldemo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(authority);
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassWord(),
                grantedAuthorities
        );
    }
}
