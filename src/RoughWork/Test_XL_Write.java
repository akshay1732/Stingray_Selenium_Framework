package RoughWork;

import java.io.IOException;

import com.selenium.commonfiles.util.XLS_Reader;

public class Test_XL_Write {

	public static void main (String args[] ) throws IOException{
		// Reading the excel Data 
		System.out.println("Data 1 "+ReadDataFromXl("IRS","NB","IRS_NB_02","Policy_Number"));
		System.out.println("Data 2 "+ReadDataFromXl("IRS","NB","IRS_NB_03","Policy_Number"));
		System.out.println("Data 3 "+ReadDataFromXl("IRS","NB","IRS_NB_04","Policy_Number"));
		
		// Writing Cell Data to Excel Sheet.
		WriteDataToXl("IRS","NB","IRS_NB_02","Policy_Number","Test Data_2013");
		WriteDataToXl("IRS","NB","IRS_NB_03","Policy_Number","Test Data_2015");
		WriteDataToXl("IRS","NB","IRS_NB_04","Policy_Number","Test Data_2017");
		
		
		// Reading the excel Data 
		System.out.println("Data 1 "+ReadDataFromXl("IRS","NB","IRS_NB_02","Policy_Number"));
		System.out.println("Data 2 "+ReadDataFromXl("IRS","NB","IRS_NB_03","Policy_Number"));
		System.out.println("Data 3 "+ReadDataFromXl("IRS","NB","IRS_NB_04","Policy_Number"));
		
	}
	
	public static boolean WriteDataToXl(String XL_Name,String Sheet_Name,String TestCase_ColName, String FieldName, String DataToWrite){
		boolean xValue = true;
		try{
			XLS_Reader xl = new XLS_Reader(System.getProperty("user.dir")+"\\src\\com\\selenium\\database\\xls",XL_Name+".xlsx");
			int RowNum = xl.getRowIndex(Sheet_Name,FieldName);
			xl.setCellData(Sheet_Name, TestCase_ColName, RowNum, DataToWrite);		
			System.out.println("Data has been written successfully");
		}catch(Throwable t){
			System.out.println("Unable to write data to excel sheet -- " + t.getLocalizedMessage());
			return false;
		}
		
			
		return xValue;
	}
	
	// Read Dynamically Data from Excel Sheet.
	
	public static String ReadDataFromXl(String XL_Name,String Sheet_Name,String TestCase_ColName, String FieldName){
		String ss = null;
		try{
			XLS_Reader xl = new XLS_Reader(System.getProperty("user.dir")+"\\src\\com\\selenium\\database\\xls",XL_Name+".xlsx");
			int RowNum = xl.getRowIndex(Sheet_Name,FieldName);
			//xl.setCellData(Sheet_Name, TestCase_ColName, RowNum, DataToWrite);
			ss= xl.getCellData(Sheet_Name, RowNum, TestCase_ColName);
			//System.out.println("Data has been retrieved successfully");
		}catch(Throwable t){
			System.out.println("Unable to read data from excel sheet -- " + t.getLocalizedMessage());
			return null;
		}
		
		return ss;
	}
	
}
