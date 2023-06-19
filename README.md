# yeogiya-backend
여기야 프로젝트의 백엔드 코드 저장소
## 백엔드 기술 스택
- Language: Java 11
- Framework: Spring Boot 2.7.4, Spring Security, Spring Data JPA, Gradle
- API 문서화: Swagger(Springfox)
- DB: MySQL
- 인프라: AWS - EC2, S3, CloudFront, RDS(서치 후 결정)
- CI/CD: Jenkins
- 기타: Jira, Slack
## Commit Convention
작업에 맞는 [gitmoji](https://gitmoji.dev/) + 메시지만으로도 어떤 기능을 개발했는지 명확하게 알 수 있도록 작성<br/>
e.g. ✨ 회원가입 기능 개발, ♻️ 로그인 코드 리팩토링
## Branch Convention
기본적으로 기능 단위로 브랜치 생성
- `main`: 배포 가능한 상태의 코드만을 관리하는 브랜치
- `develop`: 기능 개발이 끝나 테스트가 가능한 브랜치
  - feature 브랜치는 dev 브랜치에만 머지합니다.
- `feature`: 기능 개발용 브랜치
  - feature/일감명_기능명
    - e.g. `feature/YB-1_회원가입`, `feature/YB-2_로그인`
## 코드 리뷰
`develop` ← `feature` 브랜치로 PR을 보내고, 서로를 리뷰어로 지정하여 코드 리뷰 진행 후 develop 브랜치에 머지
- 머지 정책, pull request template 등록 예정
