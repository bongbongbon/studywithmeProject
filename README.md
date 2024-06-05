할 일을 미루지 않게 만들어주는 소셜 투두 서비스
프로젝트 기획 동기
혼자서만 사용하는 투두 서비스는 할 일을 적어 놓고도 귀찮아서 무시하는 경우가 많습니다.
이러한 일을 방지하고자 투두 리스트를 마감 기한을 정해서 공개하여 다른 유저들로부터 응원/잔소리를 들어가면서 할 일을 미루지 않게 해주는 서비스를 기획했습니다.
프로젝트 핵심 기능
nGrinder를 이용하여 상황별로 EC2가 감당 가능한 동시접속자 수와 이 때의 TPS를 측정했습니다.
마감 날짜(==디데이)를 정해서 프라이빗 투두와 공개 투두를 작성할 수 있습니다.
디데이가 오늘 날짜인 공개 투투 아이템들 중에서 자신이 팔로우 한 사람들의 공개 투두 아이템들을 자신의 타임라인에서 확인할 수 있습니다.
다른 유저의 공개 투두 아이템에 응원 또는 잔소리 버튼을 누르거나, 다른 사람을 팔로우 하면 알림이 전송됩니다.
레디스를 사용하여 DB I/O를 줄였습니다.
스프링 @Async를 사용하여 단기간에 들어오는 대량의 응원/잔소리 요청을 비동기로 처리함으로써 사용자가 느끼는 응답 대기 시간을 감소시켰습니다.
