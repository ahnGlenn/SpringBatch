package com.example.springbatch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@Configuration
@RequiredArgsConstructor //의존성 주입에 있어 @Autowired를 권장하지 않으며 해당 애노테이션을 통해 생성자 주입
public class BatchConfig {

    private final JobLauncher jobLauncher;

    @Lazy // Job 실행을 지연하여 순환 참조 방지
    private final Job job;

    // ApplicationRunner에서 JobLauncher를 이용해 Job 실행
    @Bean
    public ApplicationRunner jobRunner() {
        return args -> {
            jobLauncher.run(job, new JobParameters());
        };
    }

}
