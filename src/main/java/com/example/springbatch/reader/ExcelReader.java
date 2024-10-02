package com.example.springbatch.reader;

import com.example.springbatch.model.Users;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


/*
* Apache POI 라이브러리를 이용해 엑셀 파일을 읽는다.(엑셀 파일을 실제로 읽어오는 로직을 담당)
*
* ExcelReader, ExcelItemReader 자바 파일이 두개 존재함.
* 차이점은 ExcelReader은 단순 엑셀파일 읽기, SpringBatch ItemReader 인터페이스를 구현.
*/
public class ExcelReader {
    public List<Users> readExcelFile(String filePath) {
        List<Users> users = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트만 처리
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 헤더 건너뛰기

                Users user = new Users();
                // user.setId((int) row.getCell(0).getNumericCellValue());
                user.setName(row.getCell(0).getStringCellValue());
                // user.setEmail(row.getCell(2).getStringCellValue());

                users.add(user);
            }
            System.out.println("엑셀파일 확인 : " + users);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
