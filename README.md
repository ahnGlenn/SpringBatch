# SpringBatch
Spring Batch를 사용한 대용량 데이터 이관 프로젝트
- Spring Boot                (v3.3.4)
- Spring Batch               (v5)
- h2(inmemory DB)

<br/>
<br/>

# 목표
엑셀의 50만건 데이터를 가공 하여 저장 후 테이블에 데이터를 이관
- 10만건 11초
  
<br/>
<br/>

# 설명
1. 개발기간 : 2024.10.03 ~ 2024.10.15
2. 백엔드 : Java, SpringBoot, JPA, SpringBatch
3. Tool : IntelliJ, Gradle

<br/>
<br/>

# 소프트웨어 아키텍처
![image](https://github.com/user-attachments/assets/3b0624da-0a99-4a28-b532-57ec3b28f3b5)

<br/>
<br/>

# 성능 테스트(jmeter)
구현 후 성능 테스트 예정...

<br/>
<br/>

# 발생 에러 내역
1. 순환 참조 에러
   - BatchConfig.java의 job과 step이 서로의 빈을 요구하여(참조하여) 복잡한 참조 오류 발생
   - Batch(config, JobConfig, StepConfig).java 로 분리하여 명확하게 구성하고, 생성자를 통해 참조를 끊음.
2. h2 db에 배치 메타테이블 생성x에러
   - spring.batch.jdbc.initialize-schema=always 초기화 시 항시 생성
   - 스프링부트 3버전 부터는 @EnableBatchProcessing을 제거 해야 메타테이블이 자동생성됨
3. 앱 실행 시 job1, 스프링스케줄러로 job2실행 시 오류 발생
   -   spring.batch.job.enabled=false를 true로 application.properties에 설정해놨기때문
   -   (불필요한 자동 실행을 방지하여 Job name must be specified in case of multiple jobs 오류를 해결)

<br/>
<br/>
 
# 디렉토리 구조
![image](https://github.com/user-attachments/assets/ed5ab8bd-a876-420c-b693-06d4a63a16b8)
<!-- 
spring-batch/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── batch/
│   │   │               ├── config/
│   │   │               │   ├── BatchConfig.java
│   │   │               │   ├── BatchJobConfig.java
│   │   │               │   └── BatchStepConfig.java
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
-->
