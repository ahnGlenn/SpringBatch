# SpringBatch
Spring Batch를 사용한 대용량 데이터 컨트롤 토이 프로젝트
- Spring Boot                (v3.3.4)
- Spring Batch               (v5)
- h2(inmemory DB)

# 목표
테이블 A에 30만건 데이터를 가공과정을 거쳐 저장 후 테이블 B에 동일 데이터를 이관

# 설명
1. 개발기간 : 2024.10-03 ~ present (실무로 인한 일정 변경)
2. 백엔드 : Java, SpringBoot, JPA, SpringBatch
3. Tool : IntelliJ, Gradle

# 소프트웨어 아키텍처
springBatch 스케줄러의 이미지를 생성 후 붙일 예정.....

# 성능 테스트(jmeter)
구현 후 성능 테스트 예정...

# 발생 에러 내역
1. 순환 참조 에러
   - BatchConfig.java에 job, step을 한번에 작성하여서 복잡한 참조 오류 발생
   - Batch(config, JobConfig, StepConfig).java 로 분리하여 명확하게 구성
2. h2 db에 배치 메타테이블 생성x에러
   - spring.batch.jdbc.initialize-schema=always 초기화 시 항시 생성
   - 스프링부트 3버전 부터는 @EnableBatchProcessing을 제거 해야 메타테이블이 자동생성됨

# 디렉토리 구조
spring-batch-excel/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── batch/
│   │   │               ├── config/
│   │   │               │   └── BatchConfig.java
│   │   │               ├── model/
│   │   │               │   └── User.java
│   │   │               ├── reader/
│   │   │               │   ├── ExcelItemReader.java
│   │   │               │   └── ExcelReader.java
│   │   │               ├── repository/
│   │   │               │   └── UserRepository.java
│   │   │               └── processor/
│   │   │                   └── UserProcessor.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── data/
│   │   │   │   └── sample-users.xlsx
│   │   │   └── schema.sql
│   │   └── webapp/
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── batch/
│                       └── BatchJobTest.java
│
└── build.gradle
