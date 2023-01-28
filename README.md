# 서버 목적

##### 그룹네 팀원별로 연결상태 확인 하는 모니터링

![개발인원](https://img.shields.io/badge/개발인원-1명-critical)<br>
![개발기간](https://img.shields.io/badge/개발기간-4주-lightgrey)<br>
---
[![URL](https://img.shields.io/badge/Open_URL-oineh.monitoring.today-informational)](http://www.oineh-monitoring.today/)<br>
![SpringBoot](https://img.shields.io/badge/SpringBoot-2.7-blue)</br>
![JAVA](https://img.shields.io/badge/java-11-green)
![H2](https://img.shields.io/badge/h2-blue)
![JPA](https://img.shields.io/badge/JPA-red)
![SECURITY](https://img.shields.io/badge/Security-yellow)
[![libTcp](https://img.shields.io/badge/lib-git.oiNeh.tcp_0.1.6-red)](https://github.com/oiNeh/tcp)<br>
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
> > > - 빌드시 배포 자동화
***

# API 구조

| Method | URL    | 설명     |
|----|---|--------|
| POST | /api/user/signup | 회원가입   |

| Method | URL        | 설명    |
|----|------------|-------|
| POST | /api/pc | 내pc 등록 |
| POST | /api/group | 그룹 생성 |
| POST | /api/dept  | 부서 생성 |
| POST | /api/team  | 팀 생성  |

| Method | URL     | 설명               |
|----|---------|------------------|
| GET | /api/groups/{groupId} | 그룹내 모든 부서와 팀 보기  |
| GET | /api/groups |회원이 속한 그룹 리스트들 보기 |

| Method | URL     | 설명            |
|----|---------|---------------|
| GET | /api/group/invite | 받은 그룹 초대 리스트 확인 |
| PATCH | /api/group/invite | 그룹 초대 받기      |
| DELETE | /api/group/invite | 그룹 초대 거부      |
| POST | /api/group/invite | 그룹 초대 하기      |

| Method | URL     | 설명             |
|----|---------|----------------|
| GET | /api/team/invite | 받은 팀 초대 리스트 확인 |
| PATCH | /api/team/invite | 팀 초대 수락        |
| DELETE | /api/team/invite | 팀 초대 거부        |
| POST | /api/team/invite | 팀 초대 하기        |

| Method | URL     | 설명   |
|----|---------|------|
| POST | /api/team/address/url |모니터링할 URL 을 추가합니다.|
| POST | /api/team/address/ip-port |모니터링할 IP:PORT 을 추가합니다.|
| POST | /api/team/address/ip | 모니터링할 IP 을 추가합니다.|

| Method | URL     | 설명                     |
|----|---------|------------------------|
| GET | /api/address/teams/{teamId} | 주소 리스트를 가져올 수 있습니다. |
| GET |/api/address/teams/{teamId}/connects/{connectId} |  주소 연결 상태를 가져올 수 있습니다. |
| GET |/api/member/teams/{teamId} |  유저 리스트를 가져올 수 있습니다. |
| GET | /api/member/teams/{teamId}/connects/{connectId} | 유저 연결 상태를 가져올 수 있습니다.  |

