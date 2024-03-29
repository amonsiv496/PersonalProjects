package com.anastacio.PdfToExcelForStatementBalances;

import java.io.File;



/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		
		// Obtain pdf file
		File pdfFile = new File("C:\\Users\\anast\\Documents\\Online Accounts\\Prosperity Bank\\Account Statements\\2024 Statements\\statement_02112024.pdf");
		//File pdfFile = new File("C:\\Users\\anast\\Documents\\Online Accounts\\Prosperity Bank\\Account Statements\\2018 Statements\\statement_06112018.pdf");
		
		
		// Convert pdf file to .xlsx extension
		String[] pdfFilePathArray = pdfFile.toString().split("\\\\");
		String pdfFileName = pdfFilePathArray[pdfFilePathArray.length - 1].toString();
		String[] pdfFileNameArray = pdfFileName.split("\\.");
		String newRawExcelFileName = pdfFileNameArray[0].toString() + ".xlsx";
		
		// Parse PDF to Text
		PdfParser pdfParser = new PdfParser();
		pdfParser.parsePdfFile(pdfFile);
		String pdfText = pdfParser.getPdfText();
		
		// Parse raw pdf text to raw excel file with new raw excel file name
		RawTextToRawExcel rawTextToRawExcel = new RawTextToRawExcel();
		File rawExcelFile =  rawTextToRawExcel.processRawTextFileToRawExcel(pdfText, newRawExcelFileName);
		
		// Parse raw excel file
		ParseRawExcelFile parseRawExcelFile = new ParseRawExcelFile(rawExcelFile);
		
		// Obtain raw excel file information
		System.out.println("Statement Date: " + parseRawExcelFile.getStatementDate());
		System.out.println("Account Number: " + parseRawExcelFile.getAccountNumber());
		System.out.println("Owner 1: " + parseRawExcelFile.getAccountOwner()[0].toString());
		System.out.println("Owner 2: " + parseRawExcelFile.getAccountOwner()[1].toString());
		System.out.println("Address Name: " + parseRawExcelFile.getAddressName());
		System.out.println("Address City: " + parseRawExcelFile.getAddressCity());
		
		System.out.println(parseRawExcelFile.getStatementSummary().get(0).get(0).toString() + " " + parseRawExcelFile.getStatementSummary().get(0).get(1).toString());
		
		System.out.println(parseRawExcelFile.getStatementSummary().get(1).get(0).toString() 
				+ " " + parseRawExcelFile.getStatementSummary().get(1).get(1).toString()
				+ " " + parseRawExcelFile.getStatementSummary().get(1).get(2).toString()
				);
		
		
	} // end of main
}
