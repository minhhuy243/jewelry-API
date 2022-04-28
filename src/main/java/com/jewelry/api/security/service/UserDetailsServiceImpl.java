package com.jewelry.api.security.service;

import com.jewelry.api.role.entity.Role;
import com.jewelry.api.security.dto.UserDetailsDto;
import com.jewelry.api.user.entity.User;
import com.jewelry.api.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email, User.class);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException("Username is invalid!");
        }
        Set<GrantedAuthority> authorities = mapRolesToAuthorities(user.get().getRoles());
        return new UserDetailsDto(user.get().getEmail(), user.get().getPassword(), authorities);
    }

    public Set<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }
}