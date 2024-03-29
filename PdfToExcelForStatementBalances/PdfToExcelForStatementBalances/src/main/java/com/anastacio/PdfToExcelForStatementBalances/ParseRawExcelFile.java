package com.anastacio.PdfToExcelForStatementBalances;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ParseRawExcelFile {

	private FileInputStream rawExcelFileInput = null;
	private File rawExcelFile = null;
	private String statementDate = null;
	private String accountNumber = null;
	private String[] owner = new String[2];
	private String addressName = null;
	private String addressCity = null;

	public ParseRawExcelFile(File rawExcelFile) {
		this.rawExcelFile = rawExcelFile;
	}

	public String getStatementDate() {

		Workbook workbook = null;
		try {
			
			rawExcelFileInput = new FileInputStream(rawExcelFile);
			
			workbook = WorkbookFactory.create(rawExcelFileInput);

			// get the first sheet
			Sheet sheet = workbook.getSheetAt(0);

			int rowNum = 0; // First row
			Row row = sheet.getRow(rowNum);

			// Get Statement Date
			statementDate = row.getCell(2).toString();
			
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return statementDate;
	}
	
	
	public String getAccountNumber() {
		Workbook workbook = null;
		try {
			
			rawExcelFileInput = new FileInputStream(rawExcelFile);
			
			workbook = WorkbookFactory.create(rawExcelFileInput);

			// get the first sheet
			Sheet sheet = workbook.getSheetAt(0);

			int rowNum = 1; // Second row
			Row row = sheet.getRow(rowNum);

			// Account Number
			accountNumber = row.getCell(row.getLastCellNum() - 1).toString();
			
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accountNumber;
	}
	
	
	public String[] getAccountOwner() {
		Workbook workbook = null;
		try {
			
			rawExcelFileInput = new FileInputStream(rawExcelFile);
			
			workbook = WorkbookFactory.create(rawExcelFileInput);

			// get the first sheet
			Sheet sheet = workbook.getSheetAt(0);

			// 3rd row
			Row row = sheet.getRow(2);

			// Account Owner 1
			owner[0] = row.getCell(0).toString() 
					+ " " 
					+ row.getCell(1).toString() 
					+ " " 
					+ row.getCell(2).toString();

			// 4th row
			row = sheet.getRow(3);
			// Account Owner 2
			owner[1] = row.getCell(0).toString() 
					+ " " 
					+ row.getCell(1).toString();
			
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return owner;
	}
	
	public String getAddressName() {
		Workbook workbook = null;
		try {
			
			rawExcelFileInput = new FileInputStream(rawExcelFile);
			
			workbook = WorkbookFactory.create(rawExcelFileInput);

			// get the first sheet
			Sheet sheet = workbook.getSheetAt(0);
			
			// fifth row for address name
			Row row = sheet.getRow(4);
			for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
				
				if (cellNum == 0) { // first iteration to insert first value to arrayList to avoid first value from being null
					addressName = row.getCell(cellNum) + " ";
				} else if (cellNum != row.getLastCellNum() - 1) { // keep adding elements with a space
					addressName = addressName + row.getCell(cellNum) + " ";
				} else { // do not add a space if it is the last element
					addressName = addressName + row.getCell(cellNum);
				}
			}

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return addressName;
	}
	
	
	public String getAddressCity() {
		Workbook workbook = null;
		try {
			
			rawExcelFileInput = new FileInputStream(rawExcelFile);
			
			workbook = WorkbookFactory.create(rawExcelFileInput);

			// get the first sheet
			Sheet sheet = workbook.getSheetAt(0);

			// sixth row for address city
			Row row = sheet.getRow(5);
			for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
				
				if (cellNum == 0) { // first iteration to insert first value to addressCity to avoid first value from being null
					addressCity = row.getCell(cellNum) + " ";
				} else if (cellNum != row.getLastCellNum() - 1) { // keep adding elements with a space
					addressCity = addressCity + row.getCell(cellNum) + " ";
				} else { // do not add a space if it is the last element
					addressCity = addressCity + row.getCell(cellNum);
				}
			}
			

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return addressCity;
	}
	
	
	
	
	
	public ArrayList<ArrayList<String>> getStatementSummary() {
		Workbook workbook = null;
		
		// Array list of array list for statement summary information
		ArrayList<ArrayList<String>> statementSummary = new ArrayList<>();
		
		try {
			
			rawExcelFileInput = new FileInputStream(rawExcelFile);
			workbook = WorkbookFactory.create(rawExcelFileInput);

			// get the first sheet of raw excel file
			Sheet sheet = workbook.getSheetAt(0);

			
			// flag to indicate if the loop iteration has it the section for STATEMENT SUMMARY
			int statementSummaryIn = 0;
			
			// This is to store STATEMENT SUMMARY row
			ArrayList<String> statementSummaryTitleRow = null;
			// this is to store what's after "STATEMENT SUMMARY"
			String statementSummaryTitleInfo = null;
			
			// This is to store Beginning Balance row
			ArrayList<String> beginningBalanceRow = new ArrayList<>();
			String beginningBalanceDate = null;
			String beginningBalanceText = null;
			String beginningBalanceAmount = null;
			
			for (int rowNumber = 0; rowNumber < sheet.getLastRowNum(); rowNumber++) {
				Row row = sheet.getRow(rowNumber);
				// Iterate through raw excel file until it finds STATEMENT SUMMARY row
				if (row.getCell(0).toString().equals("STATEMENT") && row.getCell(1).toString().equals("SUMMARY")) {
					statementSummaryIn = 1;
					
					statementSummaryTitleRow = new ArrayList<>();
					statementSummaryTitleRow.add(row.getCell(0).toString() + " " + row.getCell(1).toString() + " -------> ");
					
					// Iterate through the rest of STATEMENT SUMMARY row
					for (int cellNum = 2; cellNum < row.getLastCellNum(); cellNum++) {
						if (cellNum == 2) {// first iteration to insert first value to statementSummaryRestInfo to avoid first value from being null
							statementSummaryTitleInfo = row.getCell(cellNum) + " ";
						} else if (cellNum != row.getLastCellNum() - 1) { // keep adding elements with a space in between
							statementSummaryTitleInfo = statementSummaryTitleInfo + row.getCell(cellNum) + " ";
						} else { // do not add a space if it is the last element
							statementSummaryTitleInfo = statementSummaryTitleInfo + row.getCell(cellNum);
							statementSummaryTitleRow.add(statementSummaryTitleInfo);
						}
					}
					
				} else if (statementSummaryIn == 1) {
					// Obtain information for Beginning Balance row
					beginningBalanceDate = row.getCell(0).toString();
					beginningBalanceText = row.getCell(1).toString() + " " + row.getCell(2).toString();
					beginningBalanceAmount = row.getCell(3).toString();
					
					beginningBalanceRow.add(beginningBalanceDate);
					beginningBalanceRow.add(beginningBalanceText);
					beginningBalanceRow.add(beginningBalanceAmount);
					break;
				}
					
			}
			statementSummary.add(statementSummaryTitleRow); // done adding statement summary title row
			statementSummary.add(beginningBalanceRow); // done adding beginning balance row
						

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return statementSummary;
	}
	
	
	
}

