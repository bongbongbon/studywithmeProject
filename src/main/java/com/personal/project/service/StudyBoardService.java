package com.personal.project.service;

import com.personal.project.common.exception.CustomException;
import com.personal.project.common.exception.ErrorCode;
import com.personal.project.controller.studyboard.dto.request.StudyBoardRequest;
import com.personal.project.controller.studyboard.dto.response.StudyBoardResponse;
import com.personal.project.entity.StudyBoardEntity;
import com.personal.project.entity.UserEntity;
import com.personal.project.repository.StudyBoardRepository;
import com.personal.project.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyBoardService {

    private final StudyBoardRepository studyBoardRepository;
    private final UserRepository userRepository;

    // 스터디 게시판 만들기
    @Transactional
    public StudyBoardResponse createStudyBoard(String username, StudyBoardRequest studyBoardRequest) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        StudyBoardEntity studyBoard = studyBoardRepository.save(
                StudyBoardEntity.builder()
                        .title(studyBoardRequest.getTitle())
                        .content(studyBoardRequest.getContent())
                        .address(studyBoardRequest.getAddress())
                        .detailAddress(studyBoardRequest.getDetailAddress())
                        .mainCategory(studyBoardRequest.getMainCategory())
                        .subCategory(studyBoardRequest.getSubCategory())
                        .user(user)
                        .build());

        return StudyBoardResponse.fromEntity(studyBoard);
    }


    // 스터디 게시판 클릭시 조회
    @Transactional
    public StudyBoardResponse getOneStudyBoard(Long studyBoardId, HttpServletRequest request, HttpServletResponse response) {

        // 쿠키를 통해서 중복방지 조회수 올리기
        viewCountUp(studyBoardId, request, response);

        return StudyBoardResponse.fromEntity(studyBoardRepository.findById(studyBoardId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_BOARD_NOT_FOUND)));

    }

    // 스터디 게시판 전체 조회
    @Transactional(readOnly = true)
    public List<StudyBoardResponse> getAllStudyBoard(Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return studyBoardRepository.findAll(pageable)
                .stream()
                .map(StudyBoardEntity::toResponse)
                .collect(Collectors.toList());
    }



    // 스터디게시판 삭제
    @Transactional
    public void deleteOneStudyBoard(UserDetails userDetails, Long studyBoardId) {


        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        StudyBoardEntity studyBoard = studyBoardRepository.findById(studyBoardId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_BOARD_NOT_FOUND));

        if (!Objects.equals(user.getId(), studyBoard.getUser().getId())) {
            throw new  CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        studyBoardRepository.deleteByIdAndUser(studyBoardId, user);
    }

    // 스터게시판 수정
    public StudyBoardResponse updateStudyBoard(UserDetails userDetails, Long studyBoardId, StudyBoardRequest studyBoardRequest) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        StudyBoardEntity studyBoard = studyBoardRepository.findById(studyBoardId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_BOARD_NOT_FOUND));

        if (!Objects.equals(user.getId(), studyBoard.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_INFO_NOT_MATCH);
        }

        studyBoard.setTitle(studyBoardRequest.getTitle());
        studyBoard.setContent(studyBoardRequest.getContent());
        studyBoard.setAddress(studyBoardRequest.getAddress());
        studyBoard.setDetailAddress(studyBoardRequest.getDetailAddress());
        studyBoard.setMainCategory(studyBoardRequest.getMainCategory());
        studyBoard.setSubCategory(studyBoardRequest.getMainCategory());


        return StudyBoardResponse.fromEntity(studyBoardRepository.save(studyBoard));
    }


    // studyBoardRepository로 조회수 올리는 로직
    @Transactional
    public int updateViews(Long id) {
        return studyBoardRepository.updateView(id);
    }



    // 쿠키를 통해서 중복방지 조회수 올리는 로직
    public void viewCountUp(Long id, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;

        // postView 이름으로 된 쿠키 찾기
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
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
            Cookie newCookie = new Cookie("postView", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 50 * 24);
            response.addCookie(newCookie);
            System.out.println(newCookie);
        }

    }


}
