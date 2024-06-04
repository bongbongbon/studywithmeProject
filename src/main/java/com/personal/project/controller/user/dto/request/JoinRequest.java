package com.personal.project.controller.user.dto.request;

import com.personal.project.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinRequest{

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하의 숫자를 입력하세요")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하의 숫자를 입력하세요")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "다른유저에게 보여질 닉네임을 입력해주세요.")
    private String nickName;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    private String detailAddress;


    public static JoinRequest fromEntity(UserEntity userEntity) {
        return JoinRequest.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .nickName(userEntity.getNickName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .address(userEntity.getAddress())
                .build();
    }

}
