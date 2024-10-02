package com.example.springbatch.reader;

import com.example.springbatch.model.Users;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.util.Iterator;
import java.util.List;


/*
* Spring Batch의 배치 프로세스에서 사용하는 표준 ItemReader 인터페이스를 구현합니다.
* ExcelReader에서 데이터를 미리 읽어 리스트로 만들어 놓고,
* 그 리스트에서 데이터를 하나씩 꺼내어 Spring Batch 프레임워크에 반환하는 역할을 합니다.
*
* [ 전체적인 동작 프로세스 ]
* ExcelItemReader에서 ExcelReader를 호출하여 엑셀 파일(src/main/resources/data/users.xlsx)을 읽는다.
* ExcelReader는 ClassPathResource를 통해 리소스 디렉토리의 엑셀 파일을 열고, Apache POI를 사용하여 데이터를 읽고 User객체 리스트로 변환한다.
* 변환된 리스트는 ExcelItemReader에서 하나씩 read() 메서드를 통해 Spring Batch 처리 흐름으로 전달됩니다.
*/
public class ExcelItemReader implements ItemReader<Users> {

    private final Iterator<Users> userIterator;

    public ExcelItemReader(ExcelReader excelReader) {
        // ExcelReader를 통해 엑셀 데이터를 미리 읽어 리스트로 준비
        // 리소스 경로에 있는 엑셀 파일을 읽음
        String filePath = new ClassPathResource("data/users.xlsx").getPath();
        List<Users> users = excelReader.readExcelFile(filePath);
        this.userIterator = users.iterator(); // Iterator로 변환하여 순차적으로 데이터 반환
    }

    @Override
    public Users read() {
        // 데이터를 하나씩 배치에서 읽어가는 메서드
        if (userIterator.hasNext()) {
            return userIterator.next(); // 다음 User 객체를 반환
        }
        return null; // 더 이상 읽을 데이터가 없으면 null 반환
    }
}
