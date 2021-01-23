package RoughWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.NetworkMode;
import com.selenium.commonfiles.base.TestBase;

public class ENAScript extends TestBase{
	
	WebDriver driver = null;
	WebDriverWait wait;
	String appURL = "http://206.142.241.41/stingray/web/login.jsp";
	FileInputStream fis;
	ExtentReports report = new ExtentReports("D:\\Execution_report.html",NetworkMode.OFFLINE);
	ExtentTest logger;
	String Scenario=null;
	Throwable t;
	String userName = "Test Underwriter HHaz 1 ";
	String password = "pas5wordA";
	String clientId = "C/193725";
	String quote_Start_Date = null,inception_date=null,deadline_date=null;
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
	Date currentDate = new Date();
	String productCodeCCF = "CCF";
	String productCodeENA = "ENA";
	String workDir = "D:/";
	
	@BeforeTest
	public void openBrowser() throws InterruptedException {
		
		Scenario = this.getClass().getSimpleName();
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\com\\selenium\\configuration\\Add-ons\\ChromeDriverServer_Win\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 5);
		
		driver.navigate().to(appURL);
		driver.findElement(By.xpath("/html/body/div[1]/form/table/tbody/tr[1]/td[2]/input")).sendKeys(userName);
		driver.findElement(By.xpath("/html/body/div[1]/form/table/tbody/tr[2]/td[2]/input")).sendKeys(password);
		//wait for element to be visible and perform click
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='lk2']/div/a")));
		driver.findElement(By.xpath("//*[@id='lk2']/div/a")).click();
		
	}
	
	
	@Test
	public void AgencyVerification() throws InterruptedException{
		
		ArrayList<String> CCFAgency = new ArrayList<>();
		CCFAgency = createAgencyList(productCodeCCF);
		
		ArrayList<String> ENAAgency = new ArrayList<>();
		ENAAgency = createAgencyList(productCodeENA);
		
		//This function will delete if existing file is present.
		deleteFiles();
		
		//This function will verify agency list from CCF to ENA and ENA to CCF
		dataVerification(CCFAgency,ENAAgency);
		
	}
	
	
	@AfterTest
	public void closeBrowser() throws IOException {
		
		//fis.close();
		driver.close();
		driver.quit();
		
	}
	
	
	@SuppressWarnings("unused")
	public ArrayList<String> createAgencyList(String productName) throws InterruptedException {
		
		/**
		 * 
		 * Step 1 : Click on Client Link
		 * Step 2 : Enter Client ID.(Set Client ID in variable "clientId")
		 * Step 3 : Click on Search Client button.
		 * Step 4 : Click on searched client.
		 * Step 5 : Click on "New Quote" link a bottom. 
		 * Step 6 : Enter Inception date.
		 * Step 7 : Enter Deadline date.
		 * Step 8 : Select product from drop down list.(Set Product code in variable "SelectProductName")
		 * Step 9 : Get all agencies listed from agency drop down and add in one ArrayList. 
		 * 
		 */
		
		ArrayList<String> ar = new ArrayList<>();
		
		driver.findElement(By.xpath("//*[@id='nb-menu']/ul/li[4]/a")).click();
		driver.findElement(By.xpath("//*[contains(@id,'clr-')]")).click();
		driver.findElement(By.xpath("//*[contains(@name,'srhcltno')]")).sendKeys(clientId);
		driver.findElement(By.xpath("//*[@id='srch-client']")).click();
		
		boolean displayed = driver.findElement(By.xpath(".//*[@id='table0']/tbody/tr[1]/td[2]/a")).isDisplayed();
		boolean retvalue=true;
   		if(!displayed){
   			retvalue=false;
   		}else{
   			driver.findElement(By.xpath("//*[@id='table0']/tbody/tr/td[2]/a")).click();
   		}
   		
   		inception_date = daysIncrementWithOutFormation(df.format(currentDate), 0);
		deadline_date = daysIncrementWithOutFormation(df.format(currentDate), 0);
   		
		driver.findElement(By.xpath("//*[@id='serverId']/div/div[1]/a")).click();
		driver.findElement(By.xpath("//*[contains(@name,'due_date')]")).click();
		driver.findElement(By.xpath("//*[contains(@name,'due_date')]")).sendKeys(inception_date);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/button[2]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(@name,'required_')]")).click();
		driver.findElement(By.xpath("//*[contains(@name,'required_')]")).sendKeys(deadline_date);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/button[2]")).click();
		
		WebElement SelectProductName = driver.findElement(By.xpath("//*[contains(@name,'product')]"));
		org.openqa.selenium.support.ui.Select selectProduct = new org.openqa.selenium.support.ui.Select(SelectProductName);
		selectProduct.selectByValue(productName);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'agency')]")));
		Thread.sleep(2000);
		if (driver.findElement(By.xpath("//*[contains(@name,'agency')]")).isEnabled()) {
			
			WebElement SelectItem = driver.findElement(By.xpath("//*[contains(@name,'agency')]"));
			org.openqa.selenium.support.ui.Select mySelect = new org.openqa.selenium.support.ui.Select(SelectItem);
			
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			List<WebElement> list = mySelect.getOptions();
			
			for(WebElement abc : list){
				
				ar.add(abc.getText());
			}
			
		}
		return ar;
	}
	
	public void dataVerification(ArrayList<String> agencyList1 , ArrayList<String> agencyList2) {
		
		
		for(String agency : agencyList1) {
			if(!agency.contains("Please Select")){
				if (agencyList2.contains(agency)) {
					writeToTextFile_CCF_TO_ENA(agency,"Present in ENA Agency List");
		         }else{
		        	 writeToTextFile_CCF_TO_ENA(agency,"Not Present in ENA Agency List");
		         }
			}
	    }
		
		for(String agency : agencyList2) {
			if(!agency.contains("Please Select")){
				if (agencyList1.contains(agency)) {
		        	 writeToTextFile_ENA_TO_CCF(agency,"Present in CCF Agency List");
		         }else{
		        	 writeToTextFile_ENA_TO_CCF(agency,"Not Present in CCF Agency List");
		         }
			}
	    }
	  
	}
	
	
	@SuppressWarnings("unused")
	public String daysIncrementWithOutFormation(String date, int days) {

		final String OLD_FORMAT = "dd/MM/yyyy";
		final String NEW_FORMAT = "dd/MMMM/yyyy";
		// August 12, 2010
		// String oldDateString = "09/01/2016";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		sdf.applyPattern(OLD_FORMAT);
		c1.add(Calendar.DATE, days);
		
		return sdf.format(c1.getTime());
	}
	
	public void writeToTextFile_CCF_TO_ENA(String AgencyNumber,String result) {
        
        
         try {
	        	FileWriter writer = new FileWriter(workDir+"AgencyListResultCCF-TO-ENA.csv", true);
		     	writer.write(AgencyNumber+","+result+"\r\n");
		     	writer.close();
	     	
         } catch (IOException e) {
             e.printStackTrace();
         }

     }
	
	public void writeToTextFile_ENA_TO_CCF(String AgencyNumber,String result) {
        
         try {
	        	FileWriter writer = new FileWriter(workDir+"AgencyListResultENA-TO-CCF.csv", true);
		     	writer.write(AgencyNumber+","+result+"\r\n");
		     	writer.close();
	     	
         } catch (IOException e) {
             e.printStackTrace();
         }

     }
	
	public void deleteFiles() {
		
		File f = new File(workDir);
		File[] listOfFiles = f.listFiles();
		int size = listOfFiles.length;
			
		for(int i=0;i<size;i++){
			if (listOfFiles[i].isFile()) {
				if(listOfFiles[i].getName().equalsIgnoreCase("AgencyListResultENA-TO-CCF.csv")){
					listOfFiles[i].delete();
					break;
				}
			}
		}
		
		for(int i=0;i<size;i++){
			if (listOfFiles[i].isFile()) {
				if(listOfFiles[i].getName().equalsIgnoreCase("AgencyListResultCCF-TO-ENA.csv")){
					listOfFiles[i].delete();
					break;
				}
			}
		}
	}
}
