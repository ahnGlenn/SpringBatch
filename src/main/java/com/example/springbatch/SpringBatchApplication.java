package com.example.springbatch;

import jakarta.annotation.PostConstruct;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 스프링부트 실행 클래스
 * // @EnableBatchProcessing : Spring Batch 기능 활성화
 * // @SpringBootApplication : Spring Application을 설정
 */
@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchApplication {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importUserJob;

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

    // 애플리케이션 실행 후 배치 작업 시작
//    @Override
//    public void run(String... args) throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("time", System.currentTimeMillis())
//                .toJobParameters();
//
//        // 비동기 작업 실행
//        new Thread(() -> {
//            try {
//                jobLauncher.run(importUserJob, jobParameters);
//            } catch (Exception e) {
//                e.printStackTrace(); // 오류 로깅
//            }
//        }).start();
//    }
}
