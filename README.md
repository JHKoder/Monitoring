소속 모니터링 프로젝트
개발인원:1명
기술: spring boot,java17,h2,jpa,security,jwt

시나리오

* 회원가입 -> 로그인 -> 소속 리스트 보기 -> 소속 연결 상태 확인
* 로그인 -> PC 정보 등록 -> 정보 입력

(그룹)/대/중/소 분류로 제작됨
그룹만 같으면 다른 분류라도 볼수 있다.

```
/메인페이지 (index)
    - 홈페이지 소개                     /
    
/회원가입페이지 (singup)
    - 회원 가입 기능                    /singup
    
/로그인페이지 (login) 
    - 로그인 기능                       /login
    
/내 정보 ( mypage)
    - PC 등록                          /api/pc/create
    
/소속 그룹 페이지 (group)
    - 소속(그룹) 리스트 보기              /api/groups/list
    - 소속(그룹) 등록                    /api/groups/add
    - 소속(그룹) 초대 하기                /api/groups/invit
    - 소속(그룹) 초대 받기                /api/groups/take
    
/소속 페이지 (board) - 소속 - (본부/개발/벡엔드팀) 
    - 소속 리스트 보기                    /api/group/list
    - 소속 등록                          /api/group/add
    - 소속 초대 하기                      /api/group/invit
    - 소속 초대 받기                      /api/group/take
    
    - IP,PORT,URL 연결 가능한 상태 보기    /api/group/domain/status
    - 도메인 등록(ip,port,url)           /api/group/domain
    
```


