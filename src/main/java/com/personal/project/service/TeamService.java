package com.personal.project.service;

import com.personal.project.common.exception.CustomException;
import com.personal.project.common.exception.ErrorCode;
import com.personal.project.controller.studyboard.dto.request.StudyBoardRequest;
import com.personal.project.controller.studyboard.dto.response.StudyBoardResponse;
import com.personal.project.controller.team.dto.request.TeamRequest;
import com.personal.project.controller.team.dto.response.TeamResponse;
import com.personal.project.entity.StudyBoardEntity;
import com.personal.project.entity.TeamEntity;
import com.personal.project.entity.TeamUserEntity;
import com.personal.project.entity.UserEntity;
import com.personal.project.repository.TeamRepository;
import com.personal.project.repository.TeamUserRepository;
import com.personal.project.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamUserRepository teamUserRepository;

    // 팀만들면서 팀유저에서 리더로 만들기
    @Transactional
    public TeamResponse createTeam(String username, TeamRequest teamRequest) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        TeamEntity teamEntity = teamRepository.save(
                TeamEntity.builder()
                        .title(teamRequest.getTitle())
                        .content(teamRequest.getContent())
                        .address(teamRequest.getAddress())
                        .detailAddress(teamRequest.getDetailAddress())
                        .mainCategory(teamRequest.getMainCategory())
                        .subCategory(teamRequest.getSubCategory())
                        .limitTeamUserCount(teamRequest.getLimitTeamUserCount())
                        .status("PROCEEDING")
                        .joinStartDate(teamRequest.getJoinStartDate())
                        .joinEndDate(teamRequest.getJoinEndDate())
                        .user(user)
                        .build());

        // 팀 만들면서 동시에 팀리더로 TeamUser에 Save
        teamUserRepository.save(
                TeamUserEntity.builder()
                        .user(user)
                        .team(teamEntity)
                        .status("COMPLETE")
                        .role("TEAM_LEADER")
                        .build());


        return TeamResponse.fromEntity(teamEntity);
    }

    @Transactional
    public TeamResponse getOneTeam(Long teamId, HttpServletRequest request, HttpServletResponse response) {

        // 쿠키를 통해서 중복방지 조회수 올리기
        viewCountUp(teamId, request, response);


        return TeamResponse.fromEntity(teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND)));
    }

    // 팀 전체 조회
    @Transactional(readOnly = true)
    public List<TeamResponse> getAllTeam(Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return teamRepository.findAll(pageable)
                .stream()
                .map(TeamEntity::toResponse)
                .collect(Collectors.toList());
    }



    // 팀 삭제
    @Transactional
    public void deleteOneTeam(UserDetails userDetails, Long teamId) {


        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        if (!Objects.equals(user.getId(), team.getUser().getId())) {
            throw new  CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        teamRepository.deleteByIdAndUser(teamId, user);
    }

    // 팀 수정
    public TeamResponse updateTeam(UserDetails userDetails, Long teamId, TeamRequest teamRequest) {


        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        if (!Objects.equals(user.getId(), team.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        team.update(teamRequest);

        return TeamResponse.fromEntity(teamRepository.save(team));
    }


    @Transactional
    public TeamResponse changeStatusComplete(UserDetails userDetails, Long teamId) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        // 팀을 만든 사람이 아니면 status를 변경하지 못하게 하는 로직
        if (!team.getUser().getUsername().equals(userDetails.getUsername()) ) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        // 상태가 완료되지 않는 팀유저가 있을 때
        if (team.getTeamUserList().stream()
                .anyMatch(teamUser -> teamUser.getStatus().equals("PROCEEDING"))) {
            throw new CustomException(ErrorCode.EXIST_PROCEEDING_TEAM_USER);
        }


        team.setStatus("COMPLETE");



        if (team.getJoinEndDate().isEqual(LocalDate.now())) {
            teamRepository.save(team);
        }

        return TeamResponse.fromEntity(teamRepository.save(team));
    }


    // teamRepository로 조회수 올리는 로직
    @Transactional
    public int updateViews(Long id) {
        return teamRepository.updateView(id);
    }



    // 쿠키를 통해서 중복방지 조회수 올리는 로직
    public void viewCountUp(Long id, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;

        // postView 이름으로 된 쿠키 찾기
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("teamView")) {
                    oldCookie = cookie;
                }
            }
        }

        // postView의 쿠키가 있을 때 같은 storyBoard 아이디 값이 없을때 조회수 올리고 _[]으로 번호 넣기
        if (oldCookie != null) {
            if(!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                updateViews(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/"); // 경로를 /로 설정하면 해당 쿠키는 웹 애플리케이션 내의 모든 경로에서 사용할 수 있습니다.
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
                System.out.println(oldCookie);
            }
        } else {
            updateViews(id);
            Cookie newCookie = new Cookie("teamView", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 50 * 24);
            response.addCookie(newCookie);
            System.out.println(newCookie);
        }

    }
}
