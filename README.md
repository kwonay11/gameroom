# Convention

## Git Commit Convention

```
[이슈번호] 타입/브랜치명 : 행위단위 내용
```

- 타입

  - docs : 문서(리드미 등) 수정
  - fix : 버그 수정
  - feat : 새로운 기능 추가 ( a new feature)
  - refactor : 코드 리팩토링 (코드 정리 ,클린코드)

- 브랜치명

  - master(배포할 때? 마지막에) => 스프린트 끝날 때 사용

  - **develop**

  - feature/FE/큰기능단위/컴포넌트 (각각이 다 브랜치임)

    : 컴포넌트를 여러 사람이 작업하게 될 시 한 depth 더 들어가서 각자 브랜치에서 작업

  - feature/BE

- 행위 단위

  - Add
  - Update
  - Delete
  - Fix(버그 수정)

- 내용

  - 해당 commit에서 어떤 작업을 했는지 작성한다.

## **Jira**

- 깃 커밋에 들어갈 이슈번호는 '스토리' 이슈 단위로 작성한다.
- 회의를 통해 기능 추가
  * Sprint를 시작할 때 이슈의 Story point와 담당자를 정한다.
