package com.example.springbatch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor //의존성 주입에 있어 @Autowired를 권장하지 않으며 해당 애노테이션을 통해 생성자 주입
@EnableScheduling
public class BatchConfig {

    private final JobLauncher jobLauncher;

    @Lazy // Job 실행을 지연하여 순환 참조 방지
    private final Job job1;
    private final Job job2;

    // ApplicationRunner에서 JobLauncher를 이용해 Job 실행
    @Bean
    public ApplicationRunner jobRunner() {
        return args -> {
            // JobParameters를 고유하게 설정
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())  // 고유한 JobParameters 추가
                    .toJobParameters();
            System.out.println("#### Job executed successfully.");
        };
    }

    // 스케줄러를 통해 실행될 job
    @Scheduled(fixedRate = 60000) // job2를 매 5분마다 실행( 60000/300000ms = 1/5분 )
    public void runJob2() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())  // 고유한 JobParameters 추가
                    .toJobParameters();
            jobLauncher.run(job2, jobParameters);
            System.out.println("####Job2(scheduler) executed successfully.");
        } catch (Exception e) {
            // 실패 시 로깅 및 재실행 로직 구현(알림, 메일 등)
            e.printStackTrace();
        }
    }
}
