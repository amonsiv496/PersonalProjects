package com.anastacio.PdfToExcelForStatementBalances;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

			
			// this is to indicate first iteration when iterating through row cells
			int firstIterationIn = 1;
			
			// flag to indicate if the loop iteration has completed for said row
			int statementSummaryDoneIn = 0;
			int beginningBalanceDoneIn = 0;
			int depositsOtherCreditsDoneIn = 0;
			int depositsOtherDebitsDoneIn = 0;
			
			// This is to store STATEMENT SUMMARY row
			ArrayList<String> statementSummaryTitleRow = null;
			// this is to store what's after "STATEMENT SUMMARY"
			String statementSummaryTitleInfo = null;
			
			
			// This is to store Beginning Balance row
			ArrayList<String> beginningBalanceRow = new ArrayList<>();
			String beginningBalanceDate = null;
			String beginningBalanceText = null;
			String beginningBalanceAmount = null;
			
			// This is to store depositsOtherCredits row
			ArrayList<String> depositsOtherCreditsRow = new ArrayList<>();
			String depositsOtherCreditsText = null;
			String depositsOtherCreditsAmount = null;
			
			// This is to store depositsOtherDebitsRow row
			ArrayList<String> depositsOtherDebitsRow = new ArrayList<>();
			String depositsOtherDebitsRowText = null;
			String depositsOtherDebitsRowAmount = null;
			
			// This is to store Ending Balance row
			ArrayList<String> endingBalanceRow = new ArrayList<>();
			String endingBalanceDate = null;
			String endingBalanceText = null;
			String endingBalanceAmount = null;

			// Iterate through all rows
			for (int rowNumber = 0; rowNumber < sheet.getLastRowNum(); rowNumber++) {
				Row row = sheet.getRow(rowNumber);
				
				
				// Iterate through all cells
				for (int cellNum1 = 0; cellNum1 < row.getLastCellNum(); cellNum1++) {
					
					// Obtain information for STATEMENT SUMMARY row
					if (row.getCell(cellNum1).toString().equals("STATEMENT") && row.getCell(cellNum1 + 1).toString().equals("SUMMARY")) {
						
						statementSummaryTitleRow = new ArrayList<>();
						statementSummaryTitleRow.add(row.getCell(cellNum1).toString() + " " + row.getCell(cellNum1 + 1).toString() + " -------> ");
						
						// Iterate through the rest of STATEMENT SUMMARY row
						for (cellNum1 = cellNum1 + 2; cellNum1 < row.getLastCellNum(); cellNum1++) {
							if (firstIterationIn == 1) {// first iteration to insert first value to statementSummaryRestInfo to avoid first value from being null
								statementSummaryTitleInfo = row.getCell(cellNum1) + " ";
								firstIterationIn = 0;
							} else if (cellNum1 != row.getLastCellNum() - 1) { // keep adding elements with a space in between
								statementSummaryTitleInfo = statementSummaryTitleInfo + row.getCell(cellNum1) + " ";
							} else { // do not add a space if it is the last element
								statementSummaryTitleInfo = statementSummaryTitleInfo + row.getCell(cellNum1);
								statementSummaryTitleRow.add(statementSummaryTitleInfo);
							}
						}
						statementSummaryDoneIn = 1;
						break;
						
					} else if (statementSummaryDoneIn == 1) {
						// Obtain information for Beginning Balance row
						beginningBalanceDate = row.getCell(0).toString();
						beginningBalanceText = row.getCell(1).toString() + " " + row.getCell(2).toString();
						beginningBalanceAmount = row.getCell(3).toString();
						
						beginningBalanceRow.add(beginningBalanceDate);
						beginningBalanceRow.add(beginningBalanceText);
						beginningBalanceRow.add(beginningBalanceAmount);
						
						statementSummaryDoneIn = 0;
						beginningBalanceDoneIn = 1;
						break;
						
					} else if (beginningBalanceDoneIn == 1) {
						
						// Obtain information for Deposits/Other row
						// Iterate through Deposits/Other row
						for (; cellNum1 < row.getLastCellNum() - 1; cellNum1++) {
							if (firstIterationIn == 1) {// first iteration to insert first value to Deposits/Other to avoid first value from being null
								depositsOtherCreditsText = row.getCell(cellNum1) + " ";
								firstIterationIn = 0;
							} else if (cellNum1 != row.getLastCellNum() - 2) { // keep adding elements with a space in between
								depositsOtherCreditsText = depositsOtherCreditsText + row.getCell(cellNum1) + " ";
							} else { // do not add a space if it is the last element
								depositsOtherCreditsText = depositsOtherCreditsText + row.getCell(cellNum1);
							}
						}
						
						// Obtain depositsOtherCreditsAmount
						depositsOtherCreditsAmount = row.getCell(row.getLastCellNum() - 1).toString();
						
						depositsOtherCreditsRow.add(depositsOtherCreditsText);
						depositsOtherCreditsRow.add(depositsOtherCreditsAmount);
						
						beginningBalanceDoneIn = 0;
						depositsOtherCreditsDoneIn = 1;
						break;
						
					} else if (depositsOtherCreditsDoneIn == 1) {
						
						// Obtain information for Checks/Other Debits row
						// Iterate through Checks/Other Debits row
						for (; cellNum1 < row.getLastCellNum() - 1; cellNum1++) {
							if (firstIterationIn == 1) {// first iteration to insert first value to Deposits/Other to avoid first value from being null
								depositsOtherDebitsRowText = row.getCell(cellNum1) + " ";
								firstIterationIn = 0;
							} else if (cellNum1 != row.getLastCellNum() - 2) { // keep adding elements with a space in between
								depositsOtherDebitsRowText = depositsOtherDebitsRowText + row.getCell(cellNum1) + " ";
							} else { // do not add a space if it is the last element
								depositsOtherDebitsRowText = depositsOtherDebitsRowText + row.getCell(cellNum1);
							}
						}
						
						// Obtain depositsOtherCreditsAmount
						depositsOtherDebitsRowAmount = row.getCell(row.getLastCellNum() - 1).toString();
						
						depositsOtherDebitsRow.add(depositsOtherDebitsRowText);
						depositsOtherDebitsRow.add(depositsOtherDebitsRowAmount);
						
						depositsOtherCreditsDoneIn = 0;
						depositsOtherDebitsDoneIn = 1;
						break;
						
					} else if (depositsOtherDebitsDoneIn == 1) {
						
						// Obtain information for Ending Balance row
						// Iterate through Ending Balance row
						for (; cellNum1 < row.getLastCellNum() - 1; cellNum1++) {
							if (firstIterationIn == 1) {// first iteration to insert ending balance date and insert first text iteration to ending balance text
								endingBalanceDate = row.getCell(cellNum1).toString();
								endingBalanceText = row.getCell(++cellNum1).toString() + " ";
								firstIterationIn = 0;
							} else if (cellNum1 != row.getLastCellNum() - 2) { // this is to add ending balance text
								endingBalanceText = endingBalanceText + row.getCell(cellNum1) + " ";
							} else { // do not add a space if it is the last element
								endingBalanceText = endingBalanceText + row.getCell(cellNum1) + " ----------->";
							}
						}
						
						// Obtain endingBalanceAmount
						endingBalanceAmount = row.getCell(row.getLastCellNum() - 1).toString();
						
						endingBalanceRow.add(endingBalanceDate);
						endingBalanceRow.add(endingBalanceText);
						endingBalanceRow.add(endingBalanceAmount);
						
						depositsOtherDebitsDoneIn = 0;
						break;
					}
				}
				firstIterationIn = 1;
			}
			
			// done adding said row
			statementSummary.add(statementSummaryTitleRow);
			statementSummary.add(beginningBalanceRow);
			statementSummary.add(depositsOtherCreditsRow);
			statementSummary.add(depositsOtherDebitsRow);
			statementSummary.add(endingBalanceRow);
						

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return statementSummary;
	}
	
	
	
	
	
	public List<Map<String, String>> getDepositsOtherCreditsSection() {
		Workbook workbook = null;

		// flag to indicate if the loop iteration has completed for said row
		int depositsOtherCreditsTitleRowDoneIn = 0;
		int depositsOtherCreditsColumnNamesRowDoneIn = 0;
		
		List<Map<String, String>> arrayListOfHashMap = new ArrayList<>();
		
		try {
			
			rawExcelFileInput = new FileInputStream(rawExcelFile);
			workbook = WorkbookFactory.create(rawExcelFileInput);

			// get the first sheet of raw excel file
			Sheet sheet = workbook.getSheetAt(0);

			// this is to indicate first iteration when iterating through row cells
			int firstIterationIn = 1;
			
			// This is to store DEPOSITS/OTHER CREDITS title row
			ArrayList<String> depositsOtherCreditsTitleRow = new ArrayList<>();
			// this is to store DEPOSITS/OTHER CREDITS title text
			String depositsOtherCreditsTitleText = null;

			// This is to store columnNamesText for Date, Description, and Amount row
			ArrayList<String> depositsOtherCreditsColumnNamesRow = new ArrayList<>();
			String depositsOtherCreditsColumnNameDate = null;
			String depositsOtherCreditsColumnNameDescription = null;
			String depositsOtherCreditsColumnNameAmount = null;

			// This is to store columnNamesValues for Date, Description, and Amount
			String depositsOtherCreditsColumnNameDateValue = null;
			String depositsOtherCreditsColumnNameDescriptionValue = null;
			String depositsOtherCreditsColumnNameAmountValue = null;
			// Create a HashMap for ColumnName to ColumnValue mapper
        	Map<String, String> depositsOtherCreditsColumnMap = new HashMap<>();
        	// Create an arrayList of HashMap to store all rows with column values
        	arrayListOfHashMap = new ArrayList<>();
        	

			// Iterate through all rows in raw Excel File starting from first row
			for (int rowNumber = 0; rowNumber < sheet.getLastRowNum(); rowNumber++) {
				Row row = sheet.getRow(rowNumber);
				
				// Iterate through all cells in current row
				for (int cellNum1 = 0; cellNum1 < row.getLastCellNum(); cellNum1++) {
					
					// Obtain information for DEPOSITS/OTHER CREDITS row
					if (row.getCell(cellNum1).toString().equals("DEPOSITS/OTHER") && row.getCell(cellNum1 + 1).toString().equals("CREDITS")) {
						
						depositsOtherCreditsTitleText = row.getCell(cellNum1).toString() + " " + row.getCell(cellNum1 + 1).toString();
						depositsOtherCreditsTitleRow.add(depositsOtherCreditsTitleText.toString());

						depositsOtherCreditsTitleRowDoneIn = 1;
						break;
					}

					// Obtain information for DEPOSITS/OTHER CREDITS columnNames row
					if (depositsOtherCreditsTitleRowDoneIn == 1) {

						depositsOtherCreditsColumnNameDate = row.getCell(cellNum1).toString();
						depositsOtherCreditsColumnNameDescription = row.getCell(cellNum1 + 1).toString();
						depositsOtherCreditsColumnNameAmount = row.getCell(cellNum1 + 2).toString();

						depositsOtherCreditsColumnNamesRow.add(depositsOtherCreditsColumnNameDate);
						depositsOtherCreditsColumnNamesRow.add(depositsOtherCreditsColumnNameDescription);
						depositsOtherCreditsColumnNamesRow.add(depositsOtherCreditsColumnNameAmount);

						depositsOtherCreditsTitleRowDoneIn = 0;
						depositsOtherCreditsColumnNamesRowDoneIn = 1;
						break;
					}

					// Obtain all DEPOSITS/OTHER CREDITS transactions [Date], [Description], [Amount]
					if (depositsOtherCreditsColumnNamesRowDoneIn == 1) {
						
						// iterate to through rest of the rows for DEPOSITS/OTHER CREDITS until it encounters an empty cell?
						// TODO: May need to update this condition
						for (int newRowCounter = 0; !row.getCell(0).toString().isEmpty(); newRowCounter++) {
							Row row2 = sheet.getRow(rowNumber);
							
							
							// Iterate through cells in DEPOSITS/OTHER CREDITS transactions row
							for (; cellNum1 < row2.getLastCellNum() - 1; cellNum1++) {
								if (firstIterationIn == 1) {// first iteration to insert DEPOSITS/OTHER CREDITS transactions date and insert first text iteration to depositsOtherCreditsColumnNameDescriptionValue text
									depositsOtherCreditsColumnNameDateValue = row2.getCell(cellNum1).toString();
									depositsOtherCreditsColumnMap.put(depositsOtherCreditsColumnNameDate, depositsOtherCreditsColumnNameDateValue);
									
									depositsOtherCreditsColumnNameDescriptionValue = row2.getCell(++cellNum1).toString() + " ";
									firstIterationIn = 0;
								} else if (cellNum1 != row2.getLastCellNum() - 2) { // keep adding elements with a space in between
									depositsOtherCreditsColumnNameDescriptionValue = depositsOtherCreditsColumnNameDescriptionValue + row2.getCell(cellNum1) + " ";
								} else { // do not add a space if it is the last element
									depositsOtherCreditsColumnNameDescriptionValue = depositsOtherCreditsColumnNameDescriptionValue + row2.getCell(cellNum1);
									depositsOtherCreditsColumnMap.put(depositsOtherCreditsColumnNameDescription, depositsOtherCreditsColumnNameDescriptionValue);
									
									// Obtain depositsOtherCreditsColumnNameAmountValue
									depositsOtherCreditsColumnNameAmountValue = row2.getCell(row2.getLastCellNum() - 1).toString();
									depositsOtherCreditsColumnMap.put(depositsOtherCreditsColumnNameAmount, depositsOtherCreditsColumnNameAmountValue);
								}
							}
							arrayListOfHashMap.add(depositsOtherCreditsColumnMap);
							// reset all values for next row iteration
							depositsOtherCreditsColumnMap = new HashMap<>();
							depositsOtherCreditsColumnNameDateValue = null;
							depositsOtherCreditsColumnNameDescriptionValue = null;
							depositsOtherCreditsColumnNameAmountValue = null;
							firstIterationIn = 1;
							cellNum1 = 0;
							
							// increase row number
							row = sheet.getRow(++rowNumber);
						}
						
						depositsOtherCreditsColumnNamesRowDoneIn = 0;
						break;
					}

				}
			}
						

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arrayListOfHashMap;
	}









	public List<Map<String, String>> getOtherDebitsSection() {
		Workbook workbook = null;

		// flag to indicate if the loop iteration has completed for said row
		int otherDebitsTitleRowDoneIn = 0;
		int otherDebitsColumnNamesRowDoneIn = 0;
		
		List<Map<String, String>> arrayListOfHashMap = new ArrayList<>();
		
		try {
			
			rawExcelFileInput = new FileInputStream(rawExcelFile);
			workbook = WorkbookFactory.create(rawExcelFileInput);

			// get the first sheet of raw excel file
			Sheet sheet = workbook.getSheetAt(0);

			// this is to indicate first iteration when iterating through row cells
			int firstIterationIn = 1;
			
			// This is to store OTHER DEBITS title row
			ArrayList<String> otherDebitsTitleRow = new ArrayList<>();
			// this is to store OTHER DEBITS title text
			String otherDebitsTitleText = null;

			// This is to store columnNamesText for Date, Description, and Amount row
			ArrayList<String> otherDebitsColumnNamesRow = new ArrayList<>();
			String otherDebitsColumnNameDate = null;
			String otherDebitsColumnNameDescription = null;
			String otherDebitsColumnNameAmount = null;

			// This is to store columnNamesValues for Date, Description, and Amount
			String otherDebitsColumnNameDateValue = null;
			String otherDebitsColumnNameDescriptionValue = null;
			String otherDebitsColumnNameAmountValue = null;
			// Create a HashMap for ColumnName to ColumnValue mapper
        	Map<String, String> otherDebitsColumnMap = new HashMap<>();
        	// Create an arrayList of HashMap to store all rows with column values
        	arrayListOfHashMap = new ArrayList<>();
        	

			// Iterate through all rows in raw Excel File starting from first row
			for (int rowNumber = 0; rowNumber < sheet.getLastRowNum(); rowNumber++) {
				Row row = sheet.getRow(rowNumber);
				
				// Iterate through all cells in current row
				for (int cellNum1 = 0; cellNum1 < row.getLastCellNum(); cellNum1++) {
					
					// Obtain information for OTHER DEBITS row
					if (row.getCell(cellNum1).toString().equals("OTHER") && row.getCell(cellNum1 + 1).toString().equals("DEBITS")) {
						
						otherDebitsTitleText = row.getCell(cellNum1).toString() + " " + row.getCell(cellNum1 + 1).toString();
						otherDebitsTitleRow.add(otherDebitsTitleText.toString());

						otherDebitsTitleRowDoneIn = 1;
						break;
					}

					// Obtain information for OTHER DEBITS columnNames row
					if (otherDebitsTitleRowDoneIn == 1) {

						otherDebitsColumnNameDate = row.getCell(cellNum1).toString();
						otherDebitsColumnNameDescription = row.getCell(cellNum1 + 1).toString();
						otherDebitsColumnNameAmount = row.getCell(cellNum1 + 2).toString();

						otherDebitsColumnNamesRow.add(otherDebitsColumnNameDate);
						otherDebitsColumnNamesRow.add(otherDebitsColumnNameDescription);
						otherDebitsColumnNamesRow.add(otherDebitsColumnNameAmount);

						otherDebitsTitleRowDoneIn = 0;
						otherDebitsColumnNamesRowDoneIn = 1;
						break;
					}

					// Obtain all OTHER DEBITS transactions [Date], [Description], [Amount]
					if (otherDebitsColumnNamesRowDoneIn == 1) {
						
						// iterate to through rest of the rows for OTHER DEBITS until it encounters 'MEMBER' value?
						// TODO: May need to update this condition
						for (int newRowCounter = 0; !row.getCell(0).toString().equals("MEMBER"); newRowCounter++) {
							Row row2 = sheet.getRow(rowNumber);
							
							
							// Iterate through cells in OTHER DEBITS transactions row
							for (; cellNum1 < row2.getLastCellNum() - 1; cellNum1++) {
								if (firstIterationIn == 1) {// first iteration to insert OTHER DEBITS transactions date and insert first text iteration to otherDebitsColumnNameDescriptionValue text
									otherDebitsColumnNameDateValue = row2.getCell(cellNum1).toString();
									otherDebitsColumnMap.put(otherDebitsColumnNameDate, otherDebitsColumnNameDateValue);
									
									otherDebitsColumnNameDescriptionValue = row2.getCell(++cellNum1).toString() + " ";
									firstIterationIn = 0;
								} else if (cellNum1 != row2.getLastCellNum() - 2) { // keep adding elements with a space in between
									otherDebitsColumnNameDescriptionValue = otherDebitsColumnNameDescriptionValue + row2.getCell(cellNum1) + " ";
								} else { // do not add a space if it is the last element
									otherDebitsColumnNameDescriptionValue = otherDebitsColumnNameDescriptionValue + row2.getCell(cellNum1);
									otherDebitsColumnMap.put(otherDebitsColumnNameDescription, otherDebitsColumnNameDescriptionValue);
									
									// Obtain otherDebitsColumnNameAmountValue
									otherDebitsColumnNameAmountValue = row2.getCell(row2.getLastCellNum() - 1).toString();
									otherDebitsColumnMap.put(otherDebitsColumnNameAmount, otherDebitsColumnNameAmountValue);
								}
							}
							arrayListOfHashMap.add(otherDebitsColumnMap);
							// reset all values for next row iteration
							otherDebitsColumnMap = new HashMap<>();
							otherDebitsColumnNameDateValue = null;
							otherDebitsColumnNameDescriptionValue = null;
							otherDebitsColumnNameAmountValue = null;
							firstIterationIn = 1;
							cellNum1 = 0;
							
							// increase row number
							row = sheet.getRow(++rowNumber);
							
							// TODO: improve this code to stop second loop iteration for OTHER DEBITS
							if (row.getCell(1).toString().equals("TOTAL") && 
								row.getCell(2).toString().equals("OVERDRAFT") && 
								row.getCell(3).toString().equals("FEES")
							   ) {
								break;
							}
						}
						
						otherDebitsColumnNamesRowDoneIn = 0;
						break;
					}

				}
			}
						

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arrayListOfHashMap;
	}
	
	// TODO: Improve this method to obtain other entities
	String getEntityFullName() {
		return "Prosperity Bank";
	}
	
}

