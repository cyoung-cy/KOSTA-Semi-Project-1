# 🎬 MovieTicket — JAVA 영화 예매 콘솔 프로그램

순수 JAVA(JDK)와 MySQL JDBC만을 활용하여 구현한 **콘솔 기반 영화 예매 시스템**이다. 회원/관리자 권한 분리, 좌석 선택 예매, 영화진흥위원회(KOBIS) Open API 연동, 리뷰·문의·통계 기능 등 실제 멀티플렉스 예매 서비스의 핵심 기능을 콘솔 UI로 구현하였다.

## 📌 프로젝트 개요

- **유형**: KOSTA 교육과정 팀 프로젝트 (Semi Project 1)
- **언어**: Java 100%
- **DB**: MySQL (JDBC 직접 연동, ORM 미사용)
- **외부 API**: 영화진흥위원회(KOBIS) Open API
- **아키텍처**: Layered Architecture (Controller → Service → DAO → DB)

## ✨ 주요 기능

### 일반 회원
- 회원가입 / 로그인 / 로그아웃 / 회원탈퇴
- 선호 장르(최대 3개) 등록 기반 영화 추천
- 좌석 선택형 영화 예매 (좌석 캐싱, 인원 구분 요금 적용)
- 예매한 영화에 대한 리뷰 작성 (비속어 필터링 포함)
- 마이페이지 (예매 내역 조회)
- 1:1 문의 등록

### 관리자
- 회원 관리
- 영화 관리 (KOBIS API 연동을 통한 영화 정보/상영작 수집)
- 문의 관리(답변 처리)
- 통계 대시보드 조회 (주간 통계 등)

### 외부 연동
- KOBIS 일별 박스오피스 API
- KOBIS 영화 상세정보 API
- 개봉 예정작(Upcoming Movie) 조회 API
- 수집한 영화 목록/상영 정보 자동 갱신 배치성 로직

## 🏗️ 아키텍처 & 패키지 구조

레이어드 아키텍처를 기반으로 관심사를 패키지 단위로 명확히 분리하였다.

```
MovieTicket/src
├── Main.java                  # 프로그램 진입점
├── api/                       # KOBIS Open API 연동 (박스오피스/영화정보/개봉예정작)
├── cache/                     # 상영관·좌석 정보 인메모리 캐시 (CinemaCache)
├── common/
│   ├── config/                 # 의존성 초기화(AppConfiguration, 수동 DI)
│   └── jdbc/                   # JDBC 공통 실행기(QueryExecutor), RowMapper 인터페이스
├── controller/                # 화면(View) ↔ 서비스 중개 계층
├── dao/                       # DAO 인터페이스
│   └── impl/                   # DAO 구현체 (JDBC 기반 CRUD)
├── dto/                        # 도메인 모델 (Movie, Member, Reservation 등)
├── exception/                  # 커스텀 예외 (예약 충돌, 미존재 데이터 등)
├── mapper/                     # ResultSet → DTO 매핑 (RowMapper 구현체)
├── records/                    # Java record (SeatPos 등 좌표 표현)
├── service/                    # 비즈니스 로직 계층
├── session/                    # 로그인 세션 관리 (Session, SessionSet)
├── util/                       # 검증/페이징/좌석 변환/비속어 필터 등 유틸
├── view/                       # 콘솔 UI 화면 (StartView, AdminView, ReservationView 등)
└── vo/                         # 화면 전용 값 객체 (Ticket, ReviewVO)
```

### 계층별 책임

| 계층 | 역할 |
|---|---|
| **view** | `Scanner` 기반 콘솔 입출력, 메뉴 분기 처리 |
| **controller** | View의 요청을 Service로 전달, 입력값 1차 가공 |
| **service** | 핵심 비즈니스 로직(예매 가능 여부 검증, 요금 계산 등) 처리, 싱글톤(`getInstance()`) 패턴 사용 |
| **dao / dao.impl** | `QueryExecutor`를 통한 JDBC SQL 실행, ResultSet → DTO 매핑 |
| **dto** | 테이블 1:1 매핑 도메인 객체 |
| **mapper** | `RowMapper` 구현으로 결과셋을 DTO로 변환 |
| **common.config** | `AppConfiguration`에서 DAO/Service 간 의존관계를 수동으로 주입(DI) |

## 🛠️ 기술 스택

- **Language**: Java (JDK)
- **DB Access**: JDBC (MySQL Driver), 공용 `QueryExecutor`로 PreparedStatement 실행 일원화
- **External API**: KOBIS(영화진흥위원회) Open API — `HttpURLConnection` 기반 호출
- **Design Pattern**:
  - Singleton (`getInstance()`) — Service/DAO/Cache 전반
  - DAO Pattern — 인터페이스(`dao`)와 구현체(`dao.impl`) 분리
  - Manual DI — `AppConfiguration`에서 객체 그래프 직접 구성
- **기타**: ANSI 색상 코드를 활용한 콘솔 UI(`ConsoleUI`), Java `record`(`SeatPos`)

## 🗄️ 환경 설정 (`resource/*.properties`)

| 파일 | 용도 |
|---|---|
| `dbinfo.properties` | MySQL 접속 정보 (`driverName`, `url`, `userName`, `userPass`) |
| `api.properties` | KOBIS API 인증키 (`kobis.key`) |
| `sql.properties` | 영화 관련 INSERT/UPDATE 등 외부화된 SQL 쿼리 |

> ⚠️ 본 저장소의 `resource/*.properties`에는 실제 키/접속정보가 평문으로 포함되어 있다. 운영 또는 공개 배포 시 `.gitignore` 처리 및 환경변수/별도 설정 파일로 분리할 것을 권장한다.

## 🚀 실행 방법

1. MySQL에 예매 시스템용 스키마 및 테이블(`MOVIE_TEST` 등)을 생성한다.
2. `MovieTicket/resource/dbinfo.properties`에 본인 환경의 DB 접속 정보를 입력한다.
3. `MovieTicket/resource/api.properties`에 [KOBIS Open API](https://www.kobis.or.kr/kobisopenapi/) 발급 키를 입력한다.
4. `Main.java`를 실행한다.
5. 콘솔에 출력되는 메뉴에 따라 회원가입 → 로그인 후 이용한다.

```
[1] 로그인
[2] 회원가입
[0] 프로그램 종료
```

## 👥 팀 정보

KOSTA 교육과정 내 팀 프로젝트로, 회원/예매/영화/관리자/리뷰 등 기능별로 팀원 간 모듈을 분담하여 개발하였다 (코드 내 주석 기준 `김채영`, `이동혁` 등 작업 기록 확인).

---
*본 README는 저장소 코드 분석을 기반으로 자동 작성되었다.*
