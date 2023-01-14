### 그룹 모니터링 프로젝트

개발인원:1명 <br>
---

[![URL](https://img.shields.io/badge/URL-oineh.monitoring.today-green)](oineh-monitoring.today)<br>
![SpringBoot](https://img.shields.io/badge/SpringBoot-2.7-blue)</br>
![JAVA](https://img.shields.io/badge/java-11-green)
![H2](https://img.shields.io/badge/h2-blue)
![JPA](https://img.shields.io/badge/JPA-red)
---

시나리오

* 회원가입 -> 로그인 -> 마이페이지 -> 내pc 등록
* 회원가입 -> 로그인 -> 그룹 이동 -> 부서내 팀 이동 -> 팀내 모든 상황 체크
* 로그인 -> 소속된 그룹 페이지 이동 -> 그룹 생성
* 로그인 -> 그룹 이동 -> 부서 생성
* 로그인 -> 그룹 이동 -> 부서내 팀 생성
* 부서 페이지 -> 그룹 초대
* 팀원 페이지 -> 도메인 입력 , 팀원 초대

---

## API 구조

```

/메인페이지           (/)
/회원가입페이지 
    - 회원 가입 기능   (/singup)
/로그인페이지 
    - 로그인 기능     (/login)
/마이페이지
    - PC 등록        (/api/pc/create)
    - 그룹 초대 리스트 (/api/user/groups/invite)
    - 그룹 초대 받기  (/api/user/groups/invite/accept)
    - 그룹 초대 거부  (/api/user/groups/invite/cencel)
    - 팀 초대 리스트  (/api/user/team/invite)
    - 팀 초대 받기   (/api/user/team/invite/accept)
    - 팀 초대 거부   (/api/user/team/invite/cencel)
    
/소속된 모든 그룹 페이지 
    - 그룹 리스트 보기      (/api/groups/list)
    - 그룹 등록            (/api/groups/add) 
    - 그룹 초대 하기        (/api/groups/invite)

/그룹 페이지
    - 부서&팀 리스트 보기    (api/group/room/{groupId})
    - 부서 등록            (/api/group/add/dept)
    - 부서내 팀 등록        (/api/group/add/team)

/팀 페이지
    - 도메인 연결상태 리스트          (/api/team/find/domain/{teamId})      
    - 팀 연결상태 리스트             (/api/team/find/member/{teamId}      
    - 팀 초대                      (/api/team/invite)
    - 연결 확인용 도메인 URL 입력     (/api/team/add/url)
    - 연결 확인용 도메인 IP:PORT 입력 (/api/team/add/ip/port)
    - 연결 확인용 도메인 IP 입력      (/api/team/add/ip)

/연결 api - 
    - IP,PORT,URL 연결 가능한 상태 보기    /api/net/connects
    
```