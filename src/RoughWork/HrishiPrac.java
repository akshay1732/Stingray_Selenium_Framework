package RoughWork;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HrishiPrac {
	
public static boolean verifyPDFContent(String strURL) {
		
		boolean flag = false;
		
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = null;

		try {
			URL url = new URL(strURL);
			BufferedInputStream file = new BufferedInputStream(url.openStream());
			PDFParser parser = new PDFParser(file);
			
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(1);
			
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (MalformedURLException e2) {
			System.err.println("URL string could not be parsed "+e2.getMessage());
		} catch (IOException e) {
			System.err.println("Unable to open PDF Parser. " + e.getMessage());
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		
		System.out.println("+++++++++++++++++");
		System.out.println(parsedText);
		System.out.println("+++++++++++++++++");
		return flag;
}

	public static void main(String[] args) throws IOException {/*
		String fileName = "D:\\LatestNew_WS\\DataDrivenFramework\\src\\com\\selenium\\database\\xls\\OFC_MTA.xlsx";
		 FileInputStream myInput = new FileInputStream(fileName);
		 //POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
		 XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
		 int no_of_sheets = myWorkBook.getNumberOfSheets();
		 //
		 int total_rows =0;
		 for(int i=1;i<no_of_sheets-3;i++){
			 XSSFSheet mySheet = myWorkBook.getSheetAt(i);
			 total_rows = total_rows + mySheet.getLastRowNum() - mySheet.getFirstRowNum();
		 }
		 System.out.println(no_of_sheets);
		
		 Object[][] data = new Object[2][total_rows];
		 XSSFSheet mySheet = myWorkBook.getSheetAt(1);
		 int r = mySheet.getLastRowNum() - mySheet.getFirstRowNum();
		 
		 System.out.println(r);
		 for(int i=1;i<no_of_sheets-3;i++){
			 XSSFSheet mySheet = myWorkBook.getSheetAt(i);
			 total_rows = total_rows + mySheet.getLastRowNum() - mySheet.getFirstRowNum();
		 }
	*/
		System.setProperty("webdriver.chrome.driver", "D:\\LatestNew_WS\\DataDrivenFramework\\src\\com\\selenium\\configuration\\Add-ons\\ChromeDriverServer_Win\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.tutorialspoint.com/selenium/selenium_tutorial.pdf");
		
		 URL nav = new URL(driver.getCurrentUrl());
		   
		 HrishiPrac.verifyPDFContent("https://www.tutorialspoint.com/selenium/selenium_tutorial.pdf");
		
		}

}
