package com.example.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor //의존성 주입에 있어 @Autowired를 권장하지 않으며 해당 애노테이션을 통해 생성자 주입
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JobLauncher jobLauncher;
    private Job job2;

    /*
    *  afterJob 메소드에서 작업 상태를 확인하여 실패 시 재실행 로직을 정의합니다.
    */
    @Override
    public void afterJob(JobExecution jobExecution) {
        //  JobLauncher를 사용하여 작업을 재실행
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            System.out.println("Job failed! Attempting to restart...");
            try {
                jobLauncher.run(job2, new JobParameters());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Job completed successfully!");
        }
    }

}
