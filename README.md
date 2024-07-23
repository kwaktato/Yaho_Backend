# 야호 프로젝트 백엔드

## Git 규칙


### 포크 및 클론
1. 각 팀원은 원본 저장소를 포크(Fork)
2. 포크한 저장소를 로컬 환경에 클론(Clone)
    ```bash
    git clone https://github.com/{your-username}/{repo-name}.git
    cd {repo-name}
    ```

### 이슈 생성
1. 각 기능별로 이슈를 생성 (라벨 사용)
2. 이슈의 제목은 간단하고 명확하게 작성

### 브랜치 생성
1. 이슈별로 브랜치를 생성
2. 브랜치 이름은 `feature/{이슈번호}` 형식으로 작성
    ```bash
    git checkout -b feature/1
    ```

### 작업 및 커밋
1. 브랜치에서 작업을 진행
2. 작업이 완료되면 커밋. 커밋 메시지는 의미있게 작성
    ```bash
    git add .
    git commit -m "이슈 번호와 함께 작업 내용 작성"
    ```

### 푸시
1. 로컬 작업을 포크한 개인 저장소에 푸시
    ```bash
    git push origin feature/1
    ```

### 풀 리퀘스트(Pull Request)
1. 개인 저장소에서 원본 저장소로 풀 리퀘스트를 생성
2. 풀 리퀘스트의 제목과 설명에 작업한 내용을 명확히 작성

### 코드 리뷰 및 머지
1. 모든 풀 리퀘스트는 팀의 다른 멤버들이 리뷰
2. 리뷰가 완료되면 회의 시간에 해당 풀 리퀘스트에 대해 논의
3. 논의 후 승인된 풀 리퀘스트는 원본 저장소에 머지

### 동기화
1. 원본 저장소의 변경 사항을 로컬 저장소와 개인 저장소에 반영
    ```bash
    git checkout main
    git pull upstream main
    git push origin main
    ```

### 커밋 메시지
- 커밋 메시지는 한글로 작성하며, 작업 내용을 간결하고 명확하게 작성

