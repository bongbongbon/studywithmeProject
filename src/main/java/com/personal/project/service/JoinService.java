package com.personal.project.service;

import com.personal.project.controller.user.dto.request.JoinRequest;
import com.personal.project.controller.user.dto.response.UserResponse;
import com.personal.project.entity.UserEntity;
import com.personal.project.common.exception.CustomException;
import com.personal.project.common.exception.ErrorCode;
import com.personal.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserResponse joinProcess(JoinRequest joinRequest) {

        String username = joinRequest.getUsername();
        String password = joinRequest.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
        }


        UserEntity user = UserEntity.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .nickName(joinRequest.getNickName())
                .address(joinRequest.getAddress())
                .email(joinRequest.getEmail())
                .detailAddress(joinRequest.getDetailAddress())
                .phone(joinRequest.getPhone())
                .build();



        return UserResponse.fromEntity(userRepository.save(user));
    }


}
