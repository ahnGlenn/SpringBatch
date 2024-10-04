package com.example.springbatch.config;

import com.example.springbatch.model.Users;
import com.example.springbatch.reader.ExcelReader;
import com.example.springbatch.reader.ExcelItemReader;
import com.example.springbatch.repository.UserRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job importUserJob(Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemReader<Users> reader,
                      ItemProcessor<Users, Users> processor,
                      ItemWriter<Users> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Users, Users>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
//    @Bean
//    public Step step1(ItemReader<Users> reader,
//                      ItemProcessor<Users, Users> processor,
//                      ItemWriter<Users> writer) {
//        return new StepBuilder("step1", jobRepository)
//                .tasklet(chunkTasklet(reader, processor, writer), transactionManager) // tasklet 방식으로 청크 처리
//                .build();
//    }
//
//    private Tasklet chunkTasklet(ItemReader<Users> reader,
//                                 ItemProcessor<Users, Users> processor,
//                                 ItemWriter<Users> writer) {
//        return (contribution, chunkContext) -> {
//            List<Users> usersChunk = new ArrayList<>();
//            int chunkSize = 100; // 청크 크기
//            for (int i = 0; i < chunkSize; i++) {
//                Users user = reader.read();
//                if (user == null) {
//                    break; // 더 이상 읽을 데이터가 없으면 종료
//                }
//                Users processedUser = processor.process(user);
//                usersChunk.add(processedUser);
//            }
//            if (!usersChunk.isEmpty()) {
//                writer.write((Chunk<? extends Users>) usersChunk); // 청크로 작성
//            }
//            return usersChunk.isEmpty() ? RepeatStatus.FINISHED : RepeatStatus.CONTINUABLE; // 처리할 데이터가 더 있으면 계속 진행
//        };
//    }


    /*
    *  배치 작업의 기본 흐름
    *  1. ItemReader: 데이터를 읽어옵니다 (예: CSV 파일, 데이터베이스 등에서).
    *  2. ItemProcessor (선택 사항): 데이터를 가공합니다.
    *  3. ItemWriter: 처리된 데이터를 데이터베이스에 저장합니다 (RepositoryItemWriter가 사용됩니다).
    */
    @Bean
    public ItemReader<Users> reader() {
        ExcelReader excelReader = new ExcelReader();
        return new ExcelItemReader(excelReader);
    }

    @Bean
    public ItemProcessor<Users, Users> processor() {
        return user -> {
            // 데이터 가공 로직을 여기서 처리할 수 있습니다.
            // user.setEmail(user.getEmail().toLowerCase());
            return user;
        };
    }

    @Bean
    public ItemWriter<Users> writer(UserRepository userRepository) {
        RepositoryItemWriter<Users> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }
}
