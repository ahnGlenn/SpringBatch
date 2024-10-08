package com.example.springbatch.reader;

import com.example.springbatch.model.Users;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/*
* Apache POI 라이브러리를 이용해 엑셀 파일을 읽는다.(엑셀 파일을 실제로 읽어오는 로직을 담당)
*
* ExcelReader, ExcelItemReader 자바 파일이 두개 존재함.
* 차이점은 ExcelReader은 단순 엑셀파일 읽기, SpringBatch ItemReader 인터페이스를 구현.
*/
public class ExcelReader {

    public List<Users> readExcelFile(InputStream inputStream) throws IOException {
        List<Users> users = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 가져옴


        for (Row row : sheet) {
            Users user = new Users();
            // 첫번째 셀이 텍스트여도 소수로 읽는 POI문제점 해결
            String cell_0 = String.valueOf(row.getCell(0).getNumericCellValue()).replaceAll("\\.\\d", "");

            user.setId(cell_0 + row.getCell(1).getStringCellValue());
            user.setName(row.getCell(2).getStringCellValue());
            user.setEmail(row.getCell(3).getStringCellValue());

            users.add(user); // 리스트에 추가
        }
        workbook.close();

        return users;
    }

}
