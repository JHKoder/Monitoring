# 서버 목적

##### 그룹네 팀원별로 연결상태 확인 하는 모니터링

![개발인원](https://img.shields.io/badge/개발인원-1명-critical)<br>
![개발기간](https://img.shields.io/badge/개발기간-2~3주-lightgrey)<br>
---
[![URL](https://img.shields.io/badge/Open_URL-oineh.monitoring.today-informational)](http://www.oineh-monitoring.today/)<br>
![SpringBoot](https://img.shields.io/badge/SpringBoot-2.7-blue)</br>
![JAVA](https://img.shields.io/badge/java-11-green)
![H2](https://img.shields.io/badge/h2-blue)
![JPA](https://img.shields.io/badge/JPA-red)
![SECURITY](https://img.shields.io/badge/Security-yellow)<br>
![IntelliJ](https://img.shields.io/badge/IDE-intelliJ-blueviolet)
![Server](https://img.shields.io/badge/server-VM_VirtualBox-9cf)
![OS](https://img.shields.io/badge/OS-Ubuntu-red)
---

# 개발 서버 전체 구조

> > 공유기
> > > #### 내 PC
> > > > ### 가상머신 VM virtualBox(Ubuntu) <br>
> > > > - 공유기에서 방화벽 80 포트 허용
> > > > - 가상머신에서 22 포트 허용
> >
> > > #### 개발용 노트북 맥북<br>
> > > - 프로젝트 개발용 노트북
> > > - ssh,scp 사용 하여 가상머신에서 배포
> > > - 스크립트 작성하여 'sh start' 명령어로 서버 실행
***

# API 구조

### POST /api/user/singup

#### 설명

    화원가입

#### 로직

    - 유저 아이디가 이미 등록되어 는지 체크
    - 이메일이 이미 등록되어 있는지 체크
    - 유저 정보 저장

## 마이페이지

### POST /api/pc/create

#### 설명

    pc를 등록해 팀원이 연결상태를 확인할수 있도록 합니다.

#### 로직

    - 회원이 접근한 IP를 구합니다. 
    - 유저에 PC를 등록 합니다.   
    - pc는 Icmp 형태의 초기 연결 방법을 갖습니다.

### POST /api/user/groups/invite

#### 설명

    받은 그룹 초대 리스트 확인

#### 로직

    - 회원이 받은 그룹 초대장을 모두 조회한다.
    - 리스트 형태의 res를 반환 합니다.

### POST /api/user/groups/invite/accept

#### 설명

    그룹 초대 받기

#### 로직

    - 초대장 ID를 조회 합니다.
    - 초대장에 서 그룹을 찾아 그룹 맴버에 등록합니다.
    - 초대장을 지움니다.

### POST /api/user/groups/invite/cancel

#### 설명

    그룹 초대 거부 

#### 로직

    - 초대장에 회원을 지움니다.

### POST /api/user/team/invite

#### 설명

    받은 팀 초대 리스트 확인

#### 로직

    - 회원이 받은 팀 초대장을 모두 조회한다. 

### POST /api/user/team/invite/accept

#### 설명

    팀 초대 수락

#### 로직

    - 초대장에 회원이 있나 체크
    - 있다면 팀에 회원을 등록한다.
    - 초대장을 지운다.

### POST /api/user/team/invite/cancel

#### 설명

    팀 초대 거부

#### 로직

    - 회원이 받은 팀 초대장을 지운다.

***

## group

### POST /api/groups/list

#### 설명

    로그인한 회원이 속한 그룹 리스트들 보기

#### 로직

    - 회원을 찾는다.
    - 회원이 속한 그룹들을 찾아 list로 반환한다. 

### POST /api/groups/add

#### 설명

    그룹 등록 

#### 로직

    - 만든 생성자 유저가 있는 유저인지 체크
    - 그룹을 만든다.
    - 그룹 생성자,맴버에 회원을 넣는다. 

### POST /api/groups/invite

#### 설명

    이메일로 유저를 그룹으로 초대합니다.

#### 로직

    - 초대하는 유저가 그룹원 인지 확인한다.
    - 이메일로 회원은 검색한다.
    - 이미 초대가 되어있나 확인한다.
    - 이상무 라면 초대장을 보낸다.

***

### POST /api/group/room/{groupId}

#### 설명

    그룹에서 부서_팀 리스트 가져와 반환

#### 로직

    - 그룹 Id로 검색
    - 그룹에 회원이 포함되어 있나 확인

### POST /api/group/add/{dept}

#### 설명

    그룹내 부서 생성 

#### 로직

    - 그룹 Id로 검색
    - 그룹 추가 

### POST /api/group/add/{team}

#### 설명

    부서내 팀 생성 

#### 로직

    - 그룹 Id 검색
    - 그룹네 부서가 있는지 확인
    - 팀 생성

***

### POST /api/team/add/url

#### 설명

    모니터링할 URL 을 추가합니다.

#### 로직

    - url 문자중 '://'가 포함 되어 있지 않다면 'http://'를 붙인다.
    - 팀 에 connect를 등록한다.

### POST /api/team/add/ip/port

#### 설명

    모니터링할 IP:PORT 을 추가합니다.

#### 로직

    - AClass,BCalss,CClass,DClass 는 0~255 까지 입력 가능하다.
    - 팀 에 connect를 등록한다.

### POST /api/team/add/ip

#### 설명

    모니터링할 IP 을 추가합니다.

#### 로직

    - AClass,BCalss,CClass,DClass 는 0~255 까지 입력 가능하다. 
    - 팀 에 connect를 등록한다.

### POST /api/team/invite

#### 설명

    이메일로 팀원을 초대 합니다.

#### 로직

    - 이메일로 유저를 검색 
    - 초대를 보내는 유저가 팀원이 맞는지 체크
    - 이미 초대장을 보낸 상대인지 체크 
    - 체크 이상없으면 초대장을 전송(마이페이지에서 확인 가능)

### POST /api/team/find/domain/{teamId}

#### 설명

    URL,IP:PORT,IP 직접 등록한 리스트를 가져올수 있습니다.

#### 로직

    팀내 등록된 URL,IP:PORT,IP 리스트를 가져와 
    이름과 connectId를 반환 합니다.

### POST /api/team/find/domain/{teamId}/{connectId}

#### 설명

    등록된 URL,IP:PORT,IP 연결 상태를 가져올수 있습니다.

#### 로직

    팀내 등록된 URL,IP:PORT,IP 리스트를 가져와 
    connectId가 있나 비교하고 있다면 연결 확인 작업을 실행후
    Res으로 반환 합니다.

### POST /api/team/find/member/{teamId}

#### 설명

    팀에 등록된 유저리스트를 반환합니다.

#### 로직

    팀내 등록된 유저중에서 PC 등록한 것들만 리스트로 반환 합니다. 

### POST /api/team/find/member/{teamId}/{connectId}

#### 설명

    팀내 유저 연결 상태 를 가져올수 있습니다.

#### 로직

    유저가 PC를 등록하고 {connectId} 에 맞는 유저를 가져와 
    연결 확인 작업후 Res로 반환 합니다.
