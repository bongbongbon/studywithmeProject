# 스터디그룹을 만들어주는 서비스

## 기획 동기


- 요즘 개발 스터디, 런닝크루 등등 모임들을 만드는 경우가 많아서 플랫폼으로 만들어보고 싶었음


## 프로젝트 핵심 기능


- jwt로그인 구현 및 redis timeToLive를 통해서 jwt refreshToken 구현
- docker, git action, nginx 를 이용해서 blue, green CICD 무중단 배포
  (프리티어 이기 때문에 cpu 사양이 부족해 swap 메모리 활용)
- 스터디그룹을 만들고 팀을 모집하는 기능
- 팀안에서 질문하고 대답하는 기능

## 프로젝트 구조


![ec2 (1)](https://github.com/bongbongbon/studywithmeProject/assets/106155992/78f3a016-294d-4ad9-a6d3-dca02f09955e)



## ERD CLOUD

<img width="1088" alt="스크린샷 2024-06-05 오후 4 52 17" src="https://github.com/bongbongbon/studywithmeProject/assets/106155992/29980ff0-eab1-4b31-b154-605768814166">


## 기술 스택


- java 17
- springboot 3.3.0
- jpa
- redis
- mysql
- nginx
- github action
- docker
