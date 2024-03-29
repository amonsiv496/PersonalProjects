package com.anastacio.PdfToExcelForStatementBalances;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfParser {
	private PDDocument pdfDocument = null;
	private PDFTextStripper pdfStripper = null;
	private String pdfText = null;

	public void parsePdfFile(File pdfFile) {
		try {
			// Load PDF document
			pdfDocument = PDDocument.load(pdfFile);

			// Instantiate PDFTextStripper class
			pdfStripper = new PDFTextStripper();

			/*
			 * Sorts the extracted text based on its position on the page. This means that
			 * text elements closer to the top-left corner of the page are extracted first,
			 * followed by text elements farther away
			 */
			pdfStripper.setSortByPosition(true);

			// Set start and end page
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(pdfDocument.getNumberOfPages());

			// Extract text from PDF
			pdfText = pdfStripper.getText(pdfDocument);
			
			// Close document
			pdfDocument.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPdfText() {
		return pdfText;
	}
}
