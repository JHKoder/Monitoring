소속 모니터링 프로젝트
개발인원:1명
기술: spring boot,java17,h2,jpa,security,jwt

시나리오

* 회원가입 -> 로그인 -> 소속 리스트 보기 -> 소속 연결 상태 확인
* 로그인 -> PC 정보 등록 -> 정보 입력
* 로그인 -> 그룹 리스트 -> 소속 리스트 -> 팀 화면
* 그룹리스트(ㅁ ㅁ ㅁ ㅁ ㅁ )
* 소속리스트(ㅁ...ㅁ...ㅁ...ㅁ...ㅁ.ㅁ..)
* 팀화면(net.net...ent..ent...ent..net)

(그룹)/대/중/소 분류로 제작됨

```
/메인페이지 (index)  - clear
    - 홈페이지 소개                     /
    
/회원가입페이지 (singup) -clear
    - 회원 가입 기능                    /singup
    
/로그인페이지 (login)  -clear
    - 로그인 기능                       /login
    
/내 정보 ( mypage) -clear
    - PC 등록                          /api/pc/create
    
/소속 그룹 페이지 (groups) 유저1:그룹n
    - 소속(그룹) 리스트 보기              /api/groups/list
    - 소속(그룹) 등록                    /api/groups/add               - { String name} 
    - 소속(그룹) 초대 하기                /api/groups/invite(port)      - { Long groupsId,String targetEmail}
    - 소속(그룹) 초대 리스트 보기          /api/groups/invite(get) 
    - 소속(그룹) 초대 받기                /api/groups/take

/소속 페이지 (group) - 소속 - (본부/개발/벡엔드팀)  그룹1:소속n
    - 소속&팀 리스트 보기                  /api/group/room/{groupId}  ( 그룹내 모든 소속 보기)
    - 소속 등록                          /api/group/add/dept  - (name,groupsId)
    - 소속 등록                          /api/group/add/team  - (name,groupsId,deptId)
    - 소속 초대 하기                      /api/group/invite(post)
    - 소속 초대 리스트 보기                /api/group//invite(get)
    - 소속 초대 받기                      /api/group/{}/take

/팀 페이지(team) 
    - 팀 연결 상태 리스트                   /api/team/status
    - 도메인 연결상태 리스트                 /api/team/domain/status
    - 도메인 추가 등록                      /api/team/domain/add

/연결 api - 
    - IP,PORT,URL 연결 가능한 상태 보기    /api/net/connects
    
```