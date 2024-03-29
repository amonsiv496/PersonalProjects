package com.anastacio.PdfToExcelForStatementBalances;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RawTextToRawExcel {
	
	public File processRawTextFileToRawExcel(String pdfText, String fileName) {

		// Create Excel workbook
		Workbook workbook = new XSSFWorkbook();
		// Create Excel Sheet1
		Sheet sheet = workbook.createSheet("Sheet1");

		// Write PDF text to Excel
		String[] lines = pdfText.split("\\r?\\n"); // stores each line in a string array
		int rowNum = 0;
		for (String line : lines) {
			Row row = sheet.createRow(rowNum++);
			String[] cells = line.split("\\s+"); // split by whitespace for cells
			int colNum = 0;
			for (String cell : cells) {
				Cell excelCell = row.createCell(colNum++);
				excelCell.setCellValue(cell);
			}
		}

		// New raw excel file with file name provided
		File excelFile = new File(fileName);
		
		// Write Excel workbook to file
		try {
			FileOutputStream outputStream = new FileOutputStream(excelFile);
			workbook.write(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return excelFile;
	}
}
