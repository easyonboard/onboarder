package utilityService;

import dto.UserDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExport{

    public void writeExcel(List<UserDTO> users) {
        Workbook workBook = new HSSFWorkbook();
        Sheet sheet = workBook.createSheet();

        int counter=0;
        Row row=sheet.createRow(counter);
        Cell cell = row.createCell(1);
        cell.setCellValue("Name");
        cell = row.createCell(2);
        cell.setCellValue("Username");
        cell = row.createCell(3);
        cell.setCellValue("Email");

        int column;
        for(int i=0;i<users.size();i++){
            column=0;
             row=sheet.createRow(++counter);
             cell=row.createCell(column++);
             cell.setCellValue(users.get(i).getName());

            cell=row.createCell(column++);
            cell.setCellValue(users.get(i).getUsername());



            cell=row.createCell(column++);
            cell.setCellValue(users.get(i).getEmail());


            try (FileOutputStream outputStream = new FileOutputStream("JavaBooks.xlsx")) {
                workBook.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
