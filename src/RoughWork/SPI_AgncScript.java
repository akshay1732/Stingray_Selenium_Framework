package RoughWork;


import java.io.FileInputStream;

import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.Assertion;

public class SPI_AgncScript {
	
	@SuppressWarnings("null")
	public static void main(String[] args) throws Throwable {
			
		// Login To .231 server :
		 
		WebDriver driver;
		WebDriverWait wait;
		String appURL = "https://206.142.242.231/stingray/web/login.jsp";
		
		FileInputStream fis;
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\com\\selenium\\configuration\\Add-ons\\ChromeDriverServer_Win\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 50);
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		// Login to Stingray :
		
		driver.get(appURL);
		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div[1]/form/table/tbody/tr[1]/td[2]/input")).sendKeys("Test Underwriter HHaz 1");
		driver.findElement(By.xpath("/html/body/div[1]/form/table/tbody/tr[2]/td[2]/input")).sendKeys("pas5wordA");
		driver.findElement(By.xpath("html/body/div[1]/table/tbody/tr/td/div/a")).click();
		Thread.sleep(1000);
		//Create Client :
		
		driver.findElement(By.xpath("/html/body/div[2]/ul/li[4]/a")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("/html/body/div[3]/form/div/div[2]/div[2]/div/a")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div[3]/form/div/table/tbody/tr[2]/td[2]/input")).sendKeys("SPI_AgencyScript");
		driver.findElement(By.xpath("/html/body/div[3]/form/div/table/tbody/tr[5]/td[2]/input")).sendKeys("UK");
		driver.findElement(By.xpath("html/body/div[3]/form/div/table/tbody/tr[11]/td[2]/input")).sendKeys("AB26 0LS");
		driver.findElement(By.xpath("/html/body/div[3]/form/div/div/div/a[2]")).click();
		Thread.sleep(500);
		
		//Create Quote :
		
		driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/a")).click();
		Thread.sleep(500);
		
		
		Select mySelect = new Select(driver.findElement(By.xpath("/html/body/div[3]/form/div/table/tbody/tr[10]/td[2]/select")));
		mySelect.selectByVisibleText("Professional Indemnity - Solicitors");
		Thread.sleep(500);
		
		Select se = new Select(driver.findElement(By.name("agency")));
		List<WebElement> options = se.getOptions();	
		String sVal = null, sValueClick = null;
					
		for(int i = 0; i < options.size(); i++){
			
			if(i == 0){
				sVal = options.get(i).getText();				
			}else{
				sVal = sVal+ ";"+ options.get(i).getText();				
			}			
		}
		
		String AgencyArray[]  = sVal.split(";");
		
		String AgencyName[]  = null;
		
		System.out.println(AgencyArray.length);
		
		driver.findElement(By.xpath("/html/body/div[2]/ul/li[9]/a")).click();
		Thread.sleep(500);
		
		int addedValue_Index = 0;
		
		//Login As a admin : 
				
		driver.findElement(By.xpath("/html/body/div[1]/form/table/tbody/tr[1]/td[2]/input")).sendKeys("Peter Campbell");
		driver.findElement(By.xpath("/html/body/div[1]/form/table/tbody/tr[2]/td[2]/input")).sendKeys("pas5wordA");
		driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td/div/a")).click();
		Thread.sleep(1000);
		
		// Navigate To agencies : 
		
		driver.findElement(By.xpath("/html/body/div[2]/ul/li[2]/a")).click();
		driver.findElement(By.xpath("/html/body/div[2]/ul/li[2]/ul/li[1]/a")).click();
		Thread.sleep(500);					
		
		for(int j = 60; j < AgencyArray.length ; j++ ){
			
			if(j > 0){
				
				// Scroll up on page to check Administration tab :
				
				WebElement element = driver.findElement(By.xpath("/html/body/div[2]/ul/li[2]/a"));
				JavascriptExecutor j_exe = (JavascriptExecutor) driver;
				j_exe.executeScript("arguments[0].scrollIntoView(true);", element);
			
				driver.findElement(By.xpath("/html/body/div[2]/ul/li[2]/a")).click();
				driver.findElement(By.xpath("/html/body/div[2]/ul/li[2]/ul/li[1]/a")).click();
				Thread.sleep(1000);
			}
			
			// Search Agency :
			
			String sValue = AgencyArray[j+1].trim();
		
			if(sValue.contains("Adler Insurance Brokers Ltd") || sValue.contains("M J Touzel (Insurance Brokers) Ltd T/As Island Insurance Services") || sValue.contains("Livingstones Of London Ltd") || sValue.contains("Knight James Associates Ltd T/As Knight James Commercial") || sValue.contains("Jeremy Burgess tas Coversure Insurance Services (Reigate)") || sValue.contains("Deric Cotterill Insurance Brokers") || sValue.contains("City Insurance Services") ){
				sValueClick = sValue+" ";				
			}else if(sValue.contains("MGB Insurance Brokers Ltd") || sValue.contains("Watson Laurie Ltd") || sValue.contains("Wallace Insurance Brokers") || sValue.contains("Premier Insurance Consultants (Bolton) Ltd")){
				sValueClick = sValue+" ";
			}else if(sValue.contains("INCEPTA Risk Management Ltd")){
				sValueClick = "Adler Insurance Brokers Ltd ";
				sValue =  "Adler Insurance Brokers Ltd ";
			}else{
				sValueClick = sValue;
			}
			driver.findElement(By.xpath("/html/body/div[3]/form/div/table[1]/tbody/tr/td[2]/input")).sendKeys(sValue);
			//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[3]/form/div/table[2]/tbody/tr[1]/td[1]/a")));
			
			Thread.sleep(3000);
			
			//driver.findElement(By.xpath("/html/body/div[3]/form/div/table[2]/tbody/tr[1]/td[1]/a")).click();
			
			 boolean result = false;
		        int attempts = 0;
		        while(attempts < 20) {
		            try {
		            	driver.findElement(By.xpath("//*[text()='"+sValueClick+"']")).click();
		                result = true;
		                break;
		            } catch(StaleElementReferenceException e) {
		            }
		            attempts++;
		        }
		   			
			Thread.sleep(1000);
			
			//Get Agency Name :
			
			//AgencyName[j] = driver.findElement(By.xpath("/html/body/div[3]/form/div/table[1]/tbody/tr[3]/td[2]/input")).getAttribute("value");
			
			// Get Agency Number and enter in Agency Payable Number:
			
			String a_number = driver.findElement(By.xpath("/html/body/div[3]/form/div/table[1]/tbody/tr[5]/td[2]/input")).getAttribute("value");
			String aP_number = driver.findElement(By.xpath("/html/body/div[3]/form/div/table[1]/tbody/tr[6]/td[2]/input")).getAttribute("value");
			
			if(a_number.equals(aP_number)){
				System.out.println("agency No "+a_number +" Matching with agency payable number : "+aP_number +" For Agency "+ sValue);
				System.out.println("Verified agency number is : "+addedValue_Index);
			}else if(aP_number.equals("")){
				System.out.println("Agency payable number is blank for agency : "+sValue);				
			}
			
			/* Click on Save :
			driver.findElement(By.xpath("/html/body/div[3]/form/div/div[1]/div/a[1]")).click();
			Thread.sleep(1000);*/
			
			addedValue_Index = addedValue_Index+1;
			
			// Click on Next :
			driver.findElement(By.xpath("/html/body/div[3]/form/div/div[1]/div/a[2]")).click();
			Thread.sleep(1000);
			
			// Scroll down on page to check the check box :
			
			WebElement element = driver.findElement(By.xpath("/html/body/div[3]/form/div/table/tbody/tr[49]/td[2]/input"));
			JavascriptExecutor j_exe = (JavascriptExecutor) driver;
			j_exe.executeScript("arguments[0].scrollIntoView(true);", element);
					
			boolean s_Flag = driver.findElement(By.xpath("/html/body/div[3]/form/div/table/tbody/tr[49]/td[2]/input")).isSelected();
			
			if(s_Flag == false){
				driver.findElement(By.xpath("/html/body/div[3]/form/div/table/tbody/tr[49]/td[2]/input")).click();
				Thread.sleep(1000);
				System.out.println("Agency is checked for SPI2");
			}else{
				System.out.println(sValue +"Agency is already checked");
			}
		}
		
		// Logout As admin :
		driver.findElement(By.xpath("/html/body/div[2]/ul/li[4]/a")).click();
		Thread.sleep(500);
		driver.quit();
	}
	
	
}