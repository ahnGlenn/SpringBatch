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

public class ExcelReader {
    public List<Users> readExcelFile() {
        String filePath = ResourceUtils.CLASSPATH_URL_PREFIX+"users.xlsx";

        List<Users> users = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 헤더 건너뛰기

                Users user = new Users();
                // user.setId((int) row.getCell(0).getNumericCellValue());
                user.setName(row.getCell(0).getStringCellValue());
                // user.setEmail(row.getCell(2).getStringCellValue());

                users.add(user);
            }
            System.out.println(users);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
