package com.example.springbatch.config;

import com.example.springbatch.model.Users;
import com.example.springbatch.reader.ExcelReader;
import com.example.springbatch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor //의존성 주입에 있어 @Autowired를 권장하지 않으며 해당 애노테이션을 통해 생성자 주입
public class BatchStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UserRepository userRepository;

    @Bean
    public Step step1(){
        return new StepBuilder("step1",jobRepository)
                .<Users, Users>chunk(1000,transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Step step2(){
        return new StepBuilder("step2",jobRepository)
                .<Users, Users>chunk(1000,transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    /*
    *  배치 작업의 기본 흐름
    *  1. ItemReader: 데이터를 읽어옵니다 (예: CSV 파일, 데이터베이스 등에서).
    *     - ItemReader가 반환하는 값이 없거나 null이면 배치 처리가 종료
    *  2. ItemProcessor (선택 사항/ 생략 가능): 데이터를 가공합니다.
    *     - ItemProcessor가 null을 반환하면 그 데이터는 ItemWriter로 전달되지 않음.
    *  3. ItemWriter: 처리된 데이터를 데이터베이스/파일에 저장(RepositoryItemWriter가 사용됨).
    */

    @Bean
    public ItemReader<Users> itemReader() {

        ExcelReader excelReader = new ExcelReader();
        try {
            // 리소스 경로에 있는 엑셀 파일을 InputStream으로 읽음
            // ClassPathResource resource = new ClassPathResource("data/users.xlsx");
            ClassPathResource resource = new ClassPathResource("data/user_data.xlsx");
            InputStream inputStream = resource.getInputStream(); // InputStream으로 파일 읽기
            List<Users> users = excelReader.readExcelFile(inputStream); // ExcelReader에서 InputStream으로 처리

            // ListItemReader를 사용하여 읽은 데이터를 반환
            return new ListItemReader<>(users);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file", e);
        }
    }

    @Bean
    public ItemProcessor<Users, Users> itemProcessor() {
        return new ItemProcessor<Users, Users>() {
            @Override
            public Users process(Users user) {
                System.out.println("Processing user: " + user);
                // 필요한 데이터 처리 로직 추가
                user.setName(user.getName().toUpperCase()); // 이름을 대문자로 변환
                return user; // 처리 후 User 객체 반환
            }
        };
    }

    @Bean
    public ItemWriter<Users> itemWriter() {
        RepositoryItemWriter<Users> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save"); // UserRepository의 save 메서드를 호출
        return writer;
    }
}
