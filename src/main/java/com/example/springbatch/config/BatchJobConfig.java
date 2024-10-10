package com.example.springbatch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor // 생성자 주입을 사용하여 의존성 주입
public class BatchJobConfig {

    private final JobRepository jobRepository;
    private final Step step1;
    private final Step step2;

    @Bean
    public Job job1(Step step1) {
        return new JobBuilder("job1", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Job job2(Step step2) {
        return new JobBuilder("job2", jobRepository)
                .start(step2)
                .build();
    }

}
