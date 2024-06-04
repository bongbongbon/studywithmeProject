package com.personal.project.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "ALREADY_EXIST_USER", "이미 가입한 회원입니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", "입력값 유효성 검사에 실패하였습니다."),
    STUDY_BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "STUDY_BOARD_NOT_FOUND", "스터디게시판을 찾을 수 없습니다."),
    USER_INFO_NOT_MATCH(HttpStatus.BAD_REQUEST, "USER_INFO_NOT_MATCH", "작성자만 게시물을 수정 및 삭제할 수 있습니다."),
    REPLY_NOT_FOUND(HttpStatus.BAD_REQUEST, "REPLY_NOT_FOUND", "댓글을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "예상치 못한 서버 에러가 발생했습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "EXPIRED_ACCESS_TOKEN", "Access Token이 만료되었습니다. 토큰을 재발급해주세요"),
    TEAM_NOT_FOUND(HttpStatus.BAD_REQUEST, "TEAM_NOT_FOUND", "팀을 찾을 수 없습니다."),
    QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "QUESTION_NOT_FOUND", "질문을 찾을 수 없습니다."),
    TEAM_USER_IS_FULL(HttpStatus.BAD_REQUEST, "TEAM_USER_IS_FULL", "팀원이 다 찼습니다."),
    TEAM_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "TEAM_USER_NOT_FOUND", "팀원을 찾을 수 없습니다."),
    ALREADY_JOIN_THIS_TEAM(HttpStatus.BAD_REQUEST, "ALREADY_JOIN_THIS_TEAM", "이미 이 팀에 참여하였습니다."),
    CAN_NOT_DELETE_TEAM_LEADER(HttpStatus.BAD_REQUEST, "CAN_NOT_DELETE_TEAM_LEADER", "리더는 삭제할 수 없습니다."),
    JOIN_END_DATE_IS_OVER(HttpStatus.BAD_REQUEST, "JOIN_END_DATE_IS_OVER", "팀 참여기간이 끝났습니다."),
    ALREADY_TEAM_IS_COMPLETE(HttpStatus.BAD_REQUEST, "ALREADY_TEAM_IS_COMPLETE", "팀 마감이 끝났습니다."),
    EXIST_PROCEEDING_TEAM_USER(HttpStatus.BAD_REQUEST, "EXIST_PROCEEDING_TEAM_USER", "모집 완료되지 않은 팀원이 있습니다."),
    RE_REPLY_NOT_FOUND(HttpStatus.BAD_REQUEST, "RE_REPLY_NOT_FOUND", "답글을 찾을 수 없습니다."),
    ANSWER_NOT_FOUND(HttpStatus.BAD_REQUEST, "ANSWER_NOT_FOUND", "답글을 찾을 수 없습니다.")
    ;



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
