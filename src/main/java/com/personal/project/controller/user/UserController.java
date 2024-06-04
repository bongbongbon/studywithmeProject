package com.personal.project.controller.user;

import com.personal.project.common.response.ApiSuccessResponse;
import com.personal.project.controller.user.dto.request.JoinRequest;
import com.personal.project.controller.user.dto.response.UserResponse;
import com.personal.project.service.JoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ApiSuccessResponse<UserResponse> join(@Valid @RequestBody JoinRequest joinRequest) {

        return ApiSuccessResponse.from(joinService.joinProcess(joinRequest));
    }



}
