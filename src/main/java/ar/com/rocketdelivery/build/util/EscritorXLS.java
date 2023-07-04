package ar.com.rocketdelivery.build.util;

import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author nlore
 */
public class EscritorXLS {

    protected List<List<String>> filas;
    protected List<String> cabeceros;

    public EscritorXLS(List<List<String>> filas, List<String> cabeceros) {
        this.filas = filas;
        this.cabeceros = cabeceros;
    }

    public Workbook exportar() {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hoja1");

        int index = 0;
        //Headers
        Row headerRow = sheet.createRow(index);
        for (String c : this.cabeceros) {
            headerRow.createCell(index).setCellValue(this.cabeceros.get(index));
            index++;
        }

        for (int i = 0; i < this.filas.size(); i++) {
            List<String> fila = this.filas.get(i);
            Row dataRow = sheet.createRow(i + 1);
            int ind = 0;
            for (String celda : fila) {
                dataRow.createCell(ind).setCellValue(celda);
                ind++;
            }
        }

        return workbook;
    }
}
