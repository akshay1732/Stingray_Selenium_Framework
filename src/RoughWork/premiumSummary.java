package RoughWork;


import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.Assertion;

public class premiumSummary {
	
	public static void main(String[] args) {
		
		WebDriver driver;
		WebDriverWait wait;
		String appURL = "http://206.142.241.41/stingray/web/login.jsp";
		FileInputStream fis;
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\com\\selenium\\configuration\\Add-ons\\ChromeDriverServer_Win\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 5);
		Hashtable<String,String> coverlist = new Hashtable<String,String>();
		Hashtable<String,String> premSmryData = new Hashtable<String,String>();
		Hashtable<String,String> transSmryData = new Hashtable<String,String>();
		boolean transPremFlag =false;
		String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
		String[] prem_header = {"GrossPremium","CommissionRate","Commission","NetPremium","GrossIPT","NetIPT"};
				//System.out.println(prem_col(0));
		coverlist.put("Material Damage","md7");
		coverlist.put("Business Interruption","bi2");
		coverlist.put("Employers Liability","el3");
		coverlist.put("Public Liability","pl2");
		coverlist.put("Products Liability","pr1");
		coverlist.put("Specified All Risks","sar");
		coverlist.put("Contractors All Risks","car");
		coverlist.put("Computers and Electronic Risks","it");
		coverlist.put("Money","mn2");
		coverlist.put("Goods In Transit","gt2");
		coverlist.put("Marine Cargo","mar");
		coverlist.put("Cyber and Data Security","cyb");
		coverlist.put("Directors and Officers","do2");
		coverlist.put("Frozen Foods","ff2");
		coverlist.put("Loss of Licence","ll2");
		coverlist.put("Fidelity Guarantee","fg");
		coverlist.put("Terrorism","tr2");
		coverlist.put("Legal Expenses","lg2");
		coverlist.put("Totals","tot");
		
		
		//System.out.println(coverlist.get("Legal Expenses"));
		
		driver.get(appURL);
		driver.findElement(By.xpath("/html/body/div[1]/form/table/tbody/tr[1]/td[2]/input")).sendKeys("Pen Comm Test 7");
		driver.findElement(By.xpath("/html/body/div[1]/form/table/tbody/tr[2]/td[2]/input")).sendKeys("pas5wordA");
		//wait for element to be visible and perform click
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='lk2']/div/a")));
		driver.findElement(By.xpath("//*[@id='lk2']/div/a")).click();
		
		driver.get("http://206.142.241.41/stingray/ctl/web/main.jsp?RID=276709&action=save.prem&THIS=p22&PAGE=p22");
		if (driver.findElement(By.xpath("//*[@id='main']/form/p")).getText().contains("Premium Summary")==false){
			Assert.fail("Premium summary Page couldn't displayed");
		}
		String stDate = driver.findElement(By.xpath("//*[@name='start_date']")).getAttribute("value");
		String endDate = driver.findElement(By.xpath("//*[@name='end_date']")).getAttribute("value");
		String duration = driver.findElement(By.xpath("//*[@id='duration']")).getText();
		System.out.println("Policy Start Date " + stDate );
		System.out.println("Policy End Date " + endDate );
		System.out.println("Policy Duration "+duration);
		
		List<WebElement> col=driver.findElements(By.xpath("html/body/div[3]/form/div/table[2]/tbody/tr[1]/td"));
		//System.out.println(col.size());
		int cCount = col.size();
		
		List<WebElement> row = driver.findElements(By.xpath("html/body/div[3]/form/div/table[2]/tbody/tr"));
		//System.out.println(row.size());
		int rcount = row.size();
		String Status = driver.findElement(By.xpath("//*[@id='headbox']/tbody/tr[1]/td[6]")).getText();
		System.out.println("Policy Status - "+ Status); 
		String tXpath;
		String CellValue;
		switch(Status){
		
		
		case "Submitted":  		// Submitted State
			System.out.println("Covers Found -:"+ (rcount-1));
			 tXpath = "//*[@class='matrix']/tbody/tr";
			for(int i =1;i<=rcount;i++){
				
				String sectionXpath = tXpath+"["+i+"]/td[1]";
				String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
				System.out.print(sec_name+"\t\t\t");
				for(int j=0;j<prem_col.length;j++){
					String xpathVal;
					xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
					
					if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
						xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";}
					//String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
					if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
						System.out.print(" ___ \t ");}
					else{
						CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value");
						System.out.print(CellValue +  "\t");						
						premSmryData.put(sec_name.replaceAll(" ", "")+"_"+prem_col[j], CellValue);
						}
					
						//driver.findElement(By.xpath("//*[@id='bi2_comm']")).getAttribute("value");
					}
				PremiumSummarytableCalculation(premSmryData,sec_name);
				System.out.println("\n___");
			}
			break;
		case "Quoted":  //Quoted State
		
			System.out.println("Covers Found -:"+ (rcount-1));
			tXpath = "//*[@class='matrix']/tbody/tr";
			for(int i =1;i<=rcount;i++){
				String sectionXpath = tXpath+"["+i+"]/td[1]";
				String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
				System.out.print(sec_name+"\t\t\t");
				for(int j = 2; j<=cCount;j++){
					String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
					//System.out.print(driver.findElement(By.xpath(ColumnValues)).getText()+"\t");
					CellValue = driver.findElement(By.xpath(ColumnValues)).getText();
					System.out.print(CellValue + "\t");						
					premSmryData.put(sec_name.replaceAll(" ", "")+"_"+prem_col[j-2], CellValue);
					//driver.findElement(By.xpath("//*[@id='bi2_comm']")).getAttribute("value");
				}
				PremiumSummarytableCalculation(premSmryData,sec_name);
				System.out.println("\n___");
			}
			break;
		case  "On Cover" :  //On Cover State
			
			System.out.println("Covers Found -:"+ (rcount-1));
			tXpath = "//*[@class='matrix']/tbody/tr";
			for(int i =1;i<=rcount;i++){
				String sectionXpath = tXpath+"["+i+"]/td[1]";
				String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
				System.out.print(sec_name+"\t\t\t");
				for(int j = 2; j<=cCount;j++){
					String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
					CellValue = driver.findElement(By.xpath(ColumnValues)).getText();
					System.out.print(CellValue +  "\t");						
					premSmryData.put(sec_name.replaceAll(" ", "")+"_"+prem_col[j-2], CellValue);
					//driver.findElement(By.xpath("//*[@id='bi2_comm']")).getAttribute("value");
					}
				PremiumSummarytableCalculation(premSmryData,sec_name);	
				System.out.println("\n___");
			}
			break;
		}
		
		//Transaction Premium Table
		try{
		String Trax_table = "html/body/div[3]/form/div/table[3]";
		if(driver.findElement(By.xpath(Trax_table)).isDisplayed()){
			System.out.println("Transaction Premium Table exist on premium summary page");
			
			System.out.println("Covers Found -:"+ (rcount-1));
			tXpath =Trax_table+"/tbody/tr";
			for(int i =1;i<=rcount;i++){
				String sectionXpath = tXpath+"["+i+"]/td[1]";
				String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
				System.out.print(sec_name+"\t\t\t");
				for(int j = 2; j<=cCount;j++){
					//transSmryData
					String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
					CellValue = driver.findElement(By.xpath(ColumnValues)).getText();
					System.out.print(CellValue +  "\t");						
					transSmryData.put(sec_name.replaceAll(" ", "")+"_"+prem_col[j-2], CellValue);
				}
				PremiumSummarytableCalculation(transSmryData,sec_name);
				System.out.println("\n___");
			}
		}
		transPremFlag=true;}
		catch(Throwable t ){
			System.out.println("--");
		}
		
		//System.out.println(premSmryData);
		System.out.println("Gross Premium for Material Damage Cover in Annual Premium table is      :"+premSmryData.get("MaterialDamage_gprem"));
		if(transPremFlag){
		System.out.println("Gross Premium for Material Damage Cover in Transaction Premium table is :"+transSmryData.get("MaterialDamage_gprem"));
		}
		
		
		driver.quit();
	}
	
	
public static void PremiumSummarytableCalculation(Hashtable<String,String> tabledata,String section){
	//MaterialDamage_nipt, MaterialDamage_gprem, MaterialDamage_nprem, MaterialDamage_gipt, MaterialDamage_comm,MaterialDamage_comr
	 	if(section.equals("Totals")){ return;}
		DecimalFormat f = new DecimalFormat("00.00");
		String secName = section.replaceAll(" ", "");
		double grossPrem = Double.parseDouble(tabledata.get(secName+"_gprem"));
		double netPrem 	 = Double.parseDouble(tabledata.get(secName+"_nprem"));
		double grossIPT  = Double.parseDouble(tabledata.get(secName+"_gipt"));
		double netIPT    = Double.parseDouble(tabledata.get(secName+"_nipt"));
		double commisn   = Double.parseDouble(tabledata.get(secName+"_comm")); 
		double commRate  = Double.parseDouble(tabledata.get(secName+"_comr"));
		double IPT = 10.00; // Double.parseDouble.mdata.get("IPT");
		System.out.println("\nCalculation Cover Name :"+section);// Added to the reporting & Logfile }
		double denominator = (1.00-(commRate/100));
		double calcltdComm = (netPrem/denominator)*(commRate/100);
		System.out.println("calculated Gross Commission :"+f.format(calcltdComm));// Added to the reporting & Logfile }
		double calcltdGprem = calcltdComm + netPrem;
		System.out.println("calculated Gross Premium :"+f.format(calcltdGprem));// Added to the reporting & Logfile }
		double calcltdGIPT = calcltdGprem *(IPT/100);
		System.out.println("calculated Gross IPT :"+f.format(calcltdGIPT));// Added to the reporting & Logfile }
		double calcltdNIPT = netPrem*(IPT/100);
		System.out.println("calculated Net IPT :"+f.format(calcltdNIPT));// Added to the reporting & Logfile }
			
		compareValues(grossPrem,calcltdGprem,"Gross Premium ");
		compareValues(commisn,calcltdComm,"Gross Commission ");
		compareValues(grossIPT,calcltdGIPT,"Gross IPT value ");
		compareValues(netIPT,calcltdNIPT,"Net IPT Values ");
			
	}
 public static boolean compareValues(double d1, double d2, String val){
	 boolean iret =true;
	 DecimalFormat f = new DecimalFormat("00.00");
	 String df1 = f.format(d1);
	 String df2 = f.format(d2);
	 if (df1.equals(df2))
	 {	System.out.println("Values have been matched for "+val+" Expected:"+df1+" with Actual value :"+df2);
	 // Added to the reporting & Logfile }
	 }
	 else
	 {  System.out.println("Values have not been matched for "+val+" Expected:"+df1+" with Actual value :"+df2);
	// Added to the reporting & Logfile }
	 iret= false;}
	 return iret;	 
 }

}