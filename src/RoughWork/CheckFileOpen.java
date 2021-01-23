package RoughWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CheckFileOpen {
	
	private File file = null;
	private FileInputStream inputStream=null;
	private Workbook Workbook = null;
	private String filePath=null;
	private String fileName=null;

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		String workDir = System.getProperty("user.dir");
		String file = workDir + "\\src\\com\\selenium\\database\\xls\\IRS_NB.xlsx";
		File file_ = new File(file);
		File sameFileName = new File(file);
		boolean isFileUnlocked = false;
		
		try {
			org.apache.commons.io.FileUtils.touch(file_);
		    isFileUnlocked = true;
		} catch (IOException e) {
		    isFileUnlocked = false;
		}

		/*if(isFileUnlocked){
		    System.out.println("File is Unlocked . ");
		} else {
			System.out.println("File is Locked . ");
		}*/
		
		if(file_.renameTo(sameFileName)){
	        // if the file is renamed
	        System.out.println("file is closed");    
	    }else{
	        // if the file didnt accept the renaming operation
	        System.out.println("file is opened");
	    }
		
		/*this.fileName = fileName;
		this.filePath=filePath;
		 file = new File(filePath+"\\"+fileName);     //Create an object of FileInputStream class to read excel file     
		 inputStream = new FileInputStream(file);     
		     //Find the file extension by spliting file name in substring and getting only extension name     
		String fileExtensionName = fileName.substring(fileName.indexOf("."));     //Check condition if the file is xlsx file     

		if(fileExtensionName.equals(".xlsx")){     //If it is xlsx file then create object of XSSFWorkbook class     

			Workbook = new XSSFWorkbook(inputStream);     
		}     //Check condition if the file is xls file     
		else if(fileExtensionName.equals(".xls")){         //If it is xls file then create object of XSSFWorkbook class         
			Workbook = new HSSFWorkbook(inputStream);    
		}     //Re
*/
	}
	

	
	//public FileInputStream inputStream = null;
	
	

}
