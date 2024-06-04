package com.personal.project.service;

import com.personal.project.controller.user.dto.CustomUserDetails;
import com.personal.project.entity.UserEntity;
import com.personal.project.common.exception.CustomException;
import com.personal.project.common.exception.ErrorCode;
import com.personal.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (userData != null) {

            return new CustomUserDetails(userData);
        }

        return null;
    }
}
