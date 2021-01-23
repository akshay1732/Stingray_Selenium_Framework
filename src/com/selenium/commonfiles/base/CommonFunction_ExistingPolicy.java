package com.selenium.commonfiles.base;

import java.awt.AWTException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.selenium.commonfiles.util.ErrorInTestMethod;
import com.selenium.commonfiles.util.TestUtil;

public class CommonFunction_ExistingPolicy extends TestBase {

	public Map<Object, Object> Rewind_Underlying_data_map = null;
	public Map<Object, Object> Reinstate_data_map = new HashMap<>();
	public boolean isReinstateTablePresent = false;
	public String AP_RP_Cover_Key = null;
	private static boolean isUniCoverPolicy;
	
	public boolean isAPOperation()
	{
		boolean isAPOperation = false;
		String businessEvent = TestBase.businessEvent;
		if(businessEvent.equalsIgnoreCase("MTA"))
		{
			String isMTAExisting = (String)common.MTA_excel_data_map.get("MTA_ExistingPolicy");
			String operation  = (String)common.MTA_excel_data_map.get("MTA_Operation");
			if(isMTAExisting.equalsIgnoreCase("Yes") && operation.equalsIgnoreCase("AP"))
			{
				isAPOperation = true;
			}
		}
		return isAPOperation;
	}
	
	public boolean isRPOperation()
	{
		boolean isRPOperation = false;
		String businessEvent = TestBase.businessEvent;
		if(businessEvent.equalsIgnoreCase("MTA"))
		{
			String isMTAExisting = (String)common.MTA_excel_data_map.get("MTA_ExistingPolicy");
			String operation  = (String)common.MTA_excel_data_map.get("MTA_Operation");
			if(isMTAExisting.equalsIgnoreCase("Yes") && operation.equalsIgnoreCase("RP"))
			{
				isRPOperation = true;
			}
		}
		return isRPOperation;
	}
	
	public boolean ExistingPolicyAlgorithm(Map<Object, Object>data_map , String type , String status)
	{	
		try
		{
			String[] productsToExclude = { "CMA", "DOB", "GTA", "GTB", "GTC", "GTD", "PAA", "PAB", "PAC" };
			isUniCoverPolicy = ArrayUtils.contains(productsToExclude, TestBase.product);
			
			customAssert.assertTrue(searchExistingPolicy(data_map , type,status) , "Search Policy function is having issues.");
									
			if(isUniCoverPolicy)
			{
				customAssert.assertTrue(selectPolicyUniCover(type,status) , "Select Policy function is having issues.");
			}
			else
			{
				customAssert.assertTrue(selectPolicy(type,status) , "Select Policy function is having issues.");
				customAssert.assertTrue(coverDetailsUpdation(type,status) , TestBase.product+" cover details updation function is having issues.");
			}
				
			return true;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean ExistingPolicyAlgorithm_VELA(Map<Object, Object>data_map , String type , String status)
	{
		try
		{
			customAssert.assertTrue(searchExistingPolicy(data_map , type,status) , "Search Policy function is having issues.");
			customAssert.assertTrue(selectPolicy_VELA(type,status) , "Select Policy function is having issues.");
			customAssert.assertTrue(coverDetailsUpdation(type,status) , "Cover details updation function is having issues.");
				
			return true;
		}
		catch(Throwable t)
		{
			return false;
		}
	}
	
	public boolean ExistingPolicyAlgorithm_PEN(Map<Object, Object>data_map , String type , String status)
	{
		try
		{
			customAssert.assertTrue(searchExistingPolicy(data_map , type,status) , "Search Policy function is having issues.");
			customAssert.assertTrue(selectPolicy_PEN(type,status) , "Select Policy function is having issues.");
			customAssert.assertTrue(coverDetailsUpdation(type,status) , "Cover details updation function is having issues.");
				
			return true;
		}
		catch(Throwable t)
		{
			return false;
		}
	}
	
	
	public boolean searchExistingPolicy(Map<Object, Object> eventMap , String type , String status) throws AWTException, InterruptedException, ErrorInTestMethod
	{
		boolean retvalue = true;
		String testName = (String)eventMap.get("Automation Key");
		try
		{
			
			customAssert.assertTrue(common.StingrayLogin("PEN"),"Unable to login.");
			customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
			k.ImplicitWaitOff();
			customAssert.assertTrue(k.DropDownSelection("Renewal_SearchType", type), "Unable to select Type on search page for Renewal Policies.");
			customAssert.assertTrue(k.DropDownSelection("Renewal_SearchStatus", status), "Unable to select Status on search page for Renewal Policies.");
			customAssert.assertTrue(k.DropDownSelection("Renewal_SearchProduct", TestBase.product), "Unable to select Prouct Code on search page for Renewal Policies.");
			k.ImplicitWaitOn();
			customAssert.assertTrue(k.Click("comm_search"), "Unable to click on search button.");
			
			TestUtil.reportStatus("Existing policy successfully searched for further operations.", "Info", false);
			
			return retvalue;
			
		}
		catch(Throwable t)
		{
			TestUtil.reportTestCaseFailed(testName, t);
			throw new ErrorInTestMethod(t.getMessage());
		}
	}
	
	//For VELA Products
	private boolean selectPolicy_VELA(String type , String status) throws Exception
	{
		String praposerName = "";
		String AgencyName = "";
		String policy_type = "";
		String policy_status = "";
		String policyNumber ="";
		String duration="";
		String durationPath = "";
		String policyStartDate="";  
		String sheetName = "";
		String code = "";
		String NetPremiumPath = "";
		int net_premium_td_path = 5;		
		boolean retValue = true;
		boolean flag = false;
		boolean searchAgain = false;
		
		Map<Object, Object> data_map = null;
		Map<Object, Object> Underlying_data_map = null;
			
		AP_RP_Cover_Key = "";
		try
		{
			switch (type) 
			{
				case "Endorsement":
				case "New Business":
					if(TestBase.businessEvent.equalsIgnoreCase("MTA"))
					{
						data_map = common.MTA_excel_data_map;
						Underlying_data_map = common.NB_excel_data_map;
						sheetName = "MTA";
						code = "NB";
					}
					else if(TestBase.businessEvent.equalsIgnoreCase("Rewind"))
					{
						if(type.equalsIgnoreCase("Endorsement"))
						{
							data_map = common.Rewind_excel_data_map;
							Underlying_data_map = common.MTA_excel_data_map;
							Rewind_Underlying_data_map = common.NB_excel_data_map;
							sheetName = "Rewind";
							code = "MTA";
						}
						else
						{
							data_map = common.Rewind_excel_data_map;
							Underlying_data_map = common.NB_excel_data_map;
							sheetName = "Rewind";
							code = "NB";
						}
					}
					else if(TestBase.businessEvent.equalsIgnoreCase("CAN"))
					{
						data_map = common.CAN_excel_data_map;
						Underlying_data_map = common.NB_excel_data_map;
						sheetName = "CAN";
						code = "NB";
					}
					break;
				case "Renewal":
					if(TestBase.businessEvent.equalsIgnoreCase("CAN"))
					{
						data_map = common.CAN_excel_data_map;
						Underlying_data_map = common.Renewal_excel_data_map;
						sheetName = "CAN";
						code = "Renewal";
					}
					else 
					{
						data_map = common.MTA_excel_data_map;
						Underlying_data_map = common.Renewal_excel_data_map;
						sheetName = "MTA";
					}
					break;
			}
			
			String searchTableXPath = "//*[@id='srch-update']//following::table[1]/tbody";
			WebElement searchedPolicyTable = driver.findElement(By.xpath(searchTableXPath));
			List<WebElement> rows = searchedPolicyTable.findElements(By.tagName("tr"));
			
			if(rows.size() >0)
			{
				for(int i = 1; i < rows.size(); i++ ) 
				{
					searchAgain = false;
					JavascriptExecutor j_exe = (JavascriptExecutor) driver;
					j_exe.executeScript("arguments[0].scrollIntoView(true);", searchedPolicyTable.findElement(By.xpath(searchTableXPath+"/tr["+i+"]")));
					
					policyNumber = searchedPolicyTable.findElement(By.xpath(".//tr["+i+"]/td[3]")).getText();
					policy_type = searchedPolicyTable.findElement(By.xpath(".//tr["+i+"]/td[4]")).getText();
					policy_status = searchedPolicyTable.findElement(By.xpath(".//tr["+i+"]/td[5]")).getText();
												
					//Check policy type and status 
					if(policy_type.equalsIgnoreCase(type) && policy_status.equalsIgnoreCase(status)) 
					{
						//open policy
						searchedPolicyTable.findElement(By.xpath(".//tr["+i+"]/td[1]")).click();
						
						//check if policy is reinstated, if yes search again
						if(isReinstatPolicty())
						{
							TestUtil.reportStatus("<p style='color:blue'>Reinstatement event present for policy : <b> ["+policyNumber+" ] </b>. Searching new Policy.</p>", "Info", false);
							searchAgain = true;							
						}
						
						//check if amendment period is correct, if not search again
						if(isValidAmendmentPeriod(data_map))
						{
							customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
							searchedPolicyTable = driver.findElement(By.xpath(searchTableXPath));
							searchedPolicyTable.findElement(By.xpath(".//tr["+i+"]/td[1]")).click();
							break;
						}
						else
						{
							searchAgain = true;							
						}
						
						if(searchAgain)
						{
							customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
							searchedPolicyTable = driver.findElement(By.xpath(searchTableXPath));
							rows = searchedPolicyTable.findElements(By.tagName("tr"));
							continue;
						}
					}
				}
			}
			else
			{
				TestUtil.reportStatus("<p style='color:red'>No policies are present with type as <b> ["+type+" & "+status+"] </b> for product <b> ["+TestBase.product+"] </b>", "Info", false);
				retValue =  false;
			}
			retValue = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			retValue = false;
		}
		return retValue;
	}
	
	private boolean isValidAmendmentPeriod(Map<Object, Object> data_map) throws AWTException, InterruptedException
	{
		boolean isValidAmendment = false;
		
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary.");
		
		int amendment_period = Integer.parseInt((String)data_map.get("MTA_EndorsementPeriod"));
		String onPageAmendmentDays = driver.findElement(By.xpath("//p[text()=' Transaction Details ']//following::P")).getText().split(":")[2].split(" ")[1].trim();
                
		if(amendment_period < Integer.parseInt(onPageAmendmentDays))
		{
			isValidAmendment = true;	
		}
		else
		{
			//if amendment period for policy is greater than duration of policy
			TestUtil.reportStatus("<p style='color:blue'>Amendment period for policy is greater than duration of policy. Searching new policy...</p>", "Info", false);
		}
		
		if(Integer.parseInt(onPageAmendmentDays)<0)
		{
			TestUtil.reportStatus("<p style='color:blue'>Effective days of searched Policy is less than 0. Searching new Policy...</p>", "Info", false);
		}
		
        return isValidAmendment;
	}
	
	private boolean isReinstatPolicty() throws AWTException, InterruptedException
	{
		boolean isReinstat = false;
		
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History.");
		
		WebElement historyTable  = driver.findElement(By.xpath("//*[@id='table0']/tbody"));
		List<WebElement> historyRows = historyTable.findElements(By.tagName("tr"));
		
		for(int j = 1; j < historyRows.size(); j++ )
		{
			String historyType = historyTable.findElement(By.xpath(".//tr["+j+"]/td[1]")).getText();
			if(historyType.contains("Reinstat"))
			{
				isReinstat = true;
				break;
			}
		}
		
		return isReinstat;
	}
	
	//For HiHaz Unicover Products
	private boolean selectPolicyUniCover(String type , String status) throws Exception
	{
		Map<Object, Object> data_map = null;
		Map<Object, Object> Underlying_data_map = null;
		String sheetName = ""; 
		String code = "";
		AP_RP_Cover_Key = "";
		boolean retvalue = false;
		boolean flag = false;
		boolean isPolicyFound = false;
		boolean searchAgain = false;
		String policyNumber ="";
		String duration="";
		String policyStartDate="";
		
		switch (type) 
		{
			case "Endorsement":
			case "New Business":
				if(TestBase.businessEvent.equalsIgnoreCase("MTA"))
				{
					data_map = common.MTA_excel_data_map;
					Underlying_data_map = common.NB_excel_data_map;
					sheetName = "MTA";
					code = "NB";
				}
				else if(TestBase.businessEvent.equalsIgnoreCase("Rewind"))
				{
					if(type.equalsIgnoreCase("Endorsement"))
					{
						data_map = common.Rewind_excel_data_map;
						Underlying_data_map = common.MTA_excel_data_map;
						Rewind_Underlying_data_map = common.NB_excel_data_map;
						sheetName = "Rewind";
						code = "MTA";
					}
					else
					{
						data_map = common.Rewind_excel_data_map;
						Underlying_data_map = common.NB_excel_data_map;
						sheetName = "Rewind";
						code = "NB";
					}
				}
				else if(TestBase.businessEvent.equalsIgnoreCase("CAN"))
				{
					data_map = common.CAN_excel_data_map;
					Underlying_data_map = common.NB_excel_data_map;
					sheetName = "CAN";
					code = "NB";
				}
				break;
			case "Renewal":
				if(TestBase.businessEvent.equalsIgnoreCase("CAN"))
				{
					data_map = common.CAN_excel_data_map;
					Underlying_data_map = common.Renewal_excel_data_map;
					sheetName = "CAN";
					code = "Renewal";
				}
				else 
				{
					data_map = common.MTA_excel_data_map;
					Underlying_data_map = common.Renewal_excel_data_map;
					sheetName = "MTA";
				}
				break;
		}
		
		String searchTableXPath = "//*[@id='srch-update']/following::table[1]";
		WebElement searchedPolicyTable = driver.findElement(By.xpath(searchTableXPath));
		List<WebElement> rows = searchedPolicyTable.findElements(By.tagName("tr"));
		
		if(rows.size() >0)
		{
			for(int i = 1; i < rows.size(); i++ ) 
			{
				searchAgain = false;
				JavascriptExecutor j_exe = (JavascriptExecutor) driver;
				j_exe.executeScript("arguments[0].scrollIntoView(true);", searchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]")));
				
				String praposerName = searchedPolicyTable.findElement(By.xpath("//tbody/tr["+i+"]/td[1]/a[1]")).getText();
				policyNumber = driver.findElement(By.xpath("//*[@id='srch-update']/following::table[1]/tbody/tr["+i+"]/td[3]")).getText();
				String policy_type = searchedPolicyTable.findElement(By.xpath("//tbody/tr["+i+"]/td[4]")).getText();
				String policy_status = searchedPolicyTable.findElement(By.xpath("//tbody/tr["+i+"]/td[5]")).getText();
				String AgencyName = searchedPolicyTable.findElement(By.xpath("//tbody/tr["+i+"]/td[6]")).getText();
				
				if(policy_type.equalsIgnoreCase(type) && policy_status.equalsIgnoreCase(status)) 
				{					
					data_map.put(sheetName+"_ClientName", praposerName);
					data_map.put("QC_AgencyName", AgencyName);
					Underlying_data_map.put(code+"_ClientName", praposerName);
					Underlying_data_map.put("QC_AgencyName", AgencyName);
					
					//open policy
					searchedPolicyTable.findElement(By.xpath("//tr["+i+"]//td[1]/a[1]")).click();
					
					String durationPath = "//td[text()='Duration (days)']//following-sibling::td//div";
					duration = driver.findElement(By.xpath(durationPath)).getText();
					policyNumber = k.getText("PremiumSummary_PolicyNumber");
					policyStartDate = k.getTextByXpath("//*[@id='start_date']");
										
					if(TestBase.businessEvent.equalsIgnoreCase("MTA"))
					{
						//check if policy is reinstated, if yes search again
						if(isReinstatPolicty())
						{
							TestUtil.reportStatus("<p style='color:blue'>Reinstatement event present for policy : <b> ["+policyNumber+" ] </b>. Searching new Policy.</p>", "Info", false);
							searchAgain = true;							
						}
						
						//check if amendment period is correct, if not search again
						if(isValidAmendmentPeriod(data_map))
						{
							customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
							searchedPolicyTable = driver.findElement(By.xpath(searchTableXPath));
							j_exe.executeScript("arguments[0].scrollIntoView(true);", searchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]")));
							searchedPolicyTable.findElement(By.xpath("//tr["+i+"]//td[1]/a[1]")).click();
							isPolicyFound = true;
							break;
						}
						else
						{
							searchAgain = true;
						}
					}
					
					if(TestBase.businessEvent.equalsIgnoreCase("Rewind") && isRewindButtonPresent(type, policyStartDate))
					{
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to History.");
						isPolicyFound = true;
						break;
					}
					
					if(TestBase.businessEvent.equalsIgnoreCase("CAN"))
					{
						if(isReinstatPolicty())
						{
							TestUtil.reportStatus("<p style='color:blue'>Reinstatement event present for policy : <b> ["+policyNumber+" ] </b>. Searching new Policy.</p>", "Info", false);
							searchAgain = true;							
						}
						
						if(!searchAgain)
						{
							customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to History.");
							String endDatePath = "//td[text()='Policy End Date (dd/mm/yyyy) ']//following-sibling::td";
							String endDate = driver.findElement(By.xpath(endDatePath)).getText();
							SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							
							int dateDif = Integer.parseInt((String)common.CAN_excel_data_map.get("CP_AddDifference"));
							Date c_date = df.parse(common.getUKDate());
				    		
							String Cancellation_date = common.daysIncrementWithOutFormation(df.format(c_date), dateDif);
							
							Date end_date = df.parse(endDate);
				            Date cancel_date = df.parse(Cancellation_date);
				            
				            if(cancel_date.after(end_date))
				            {
				            	TestUtil.reportStatus("<p style='color:blue'>Cancellation date <b>[ "+Cancellation_date+" ]</b> should be before policy end date <b>[ "+endDate+" ]</b>.</p>", "Info", false);
				            	searchAgain = true;
				            }
				            else
				            {
				            	isPolicyFound = true;
								break;
				            }
						}
					}
					
					if(!isPolicyFound)
					{
						searchAgain = true;
					}
					
					if(searchAgain)
					{
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						searchedPolicyTable = driver.findElement(By.xpath(searchTableXPath));
						rows = searchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}
				}
			}
			
			if(isPolicyFound)
			{
				TestUtil.reportStatus("Policy has been identified with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ]", "Info", false);
				
				if((String)data_map.get("MTA_Operation")!=null)
				{
					TestUtil.reportStatus("<b>[ "+(String)data_map.get("MTA_Operation")+" ]</b> operation will be performed on the existing policy <b>[ "+policyNumber+" ]</b> as a part of an endorsement.", "Info", true);
				}			
				customAssert.assertTrue(updatePremiumSummaryData(policyNumber,duration,type) , "Update Premium Summary Values function is having issues.");
				retvalue = true;
			}
			else
			{
				retvalue = false;
			}
			return retvalue;
		}
		else
		{
			TestUtil.reportStatus("<p style='color:red'>No policies are present with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ] </b>", "Info", false);
			return false;
		}
	}
	
	private boolean isRewindButtonPresent(String type, String policyStartDate) throws ParseException, AWTException, InterruptedException
	{	
		boolean isValidRewind = false;
		SimpleDateFormat date_f = new SimpleDateFormat("dd/MM/yyyy");
		
		if(type.equalsIgnoreCase("Endorsement"))
		{
			policyStartDate = driver.findElement(By.xpath("//p[text()=' Transaction Details ']//following::P")).getText().split(":")[1].split(",")[0].trim();
		}
		Date policyStart_date = date_f.parse(policyStartDate);
	    
		Date todays_date = date_f.parse(date_f.format(new Date()));
	    long time_diff = todays_date.getTime() - policyStart_date.getTime();
	    
	    long days_diff = TimeUnit.DAYS.convert(time_diff, TimeUnit.MILLISECONDS);
	    int rewindButtonDuration = Integer.parseInt(CONFIG.getProperty("RewindButtonDurration"));
	    
	    if(days_diff <= rewindButtonDuration) 
	    {
	    	isValidRewind = true;
	    }
	    
	    if(driver.findElement(By.xpath("//*[@id='rwd-init']")).isDisplayed())
	    {
	    	isValidRewind = true;
	    }
	    
	    return isValidRewind;
	}
	
public boolean selectPolicy(String type , String status) throws AWTException, InterruptedException, ParseException{
	
	Map<Object, Object> data_map = null;
	Map<Object, Object> Underlying_data_map = null;
	String sheetName = "" , code = "";
	AP_RP_Cover_Key = "";
	switch (type) {
	case "Endorsement":
	case "New Business":
		if(TestBase.businessEvent.equalsIgnoreCase("MTA")){
			data_map = common.MTA_excel_data_map;
			Underlying_data_map = common.NB_excel_data_map;
			sheetName = "MTA";
			code = "NB";
		}else if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
			if(type.equalsIgnoreCase("Endorsement")){
				data_map = common.Rewind_excel_data_map;
				Underlying_data_map = common.MTA_excel_data_map;
				Rewind_Underlying_data_map = common.NB_excel_data_map;
				sheetName = "Rewind";
				code = "MTA";
			}else{
				data_map = common.Rewind_excel_data_map;
				Underlying_data_map = common.NB_excel_data_map;
				sheetName = "Rewind";
				code = "NB";
			}
		}else if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
			data_map = common.CAN_excel_data_map;
			Underlying_data_map = common.NB_excel_data_map;
			sheetName = "CAN";
			code = "NB";
		}
		break;
	case "Renewal":
		if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
			data_map = common.CAN_excel_data_map;
			Underlying_data_map = common.Renewal_excel_data_map;
			sheetName = "CAN";
			code = "Renewal";
		}else {
			data_map = common.MTA_excel_data_map;
			Underlying_data_map = common.Renewal_excel_data_map;
			sheetName = "MTA";
		}
		break;
	}
	
	boolean retvalue = true;
	boolean flag = false;
	String PolicyNumber ="" ,duration="",policyStartDate="";  
	WebElement SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
	List<WebElement> rows = SearchedPolicyTable.findElements(By.tagName("tr"));
	
	if(rows.size() >0){
		for(int i = 1; i < rows.size(); i++ ) {
			
			JavascriptExecutor j_exe = (JavascriptExecutor) driver;
			j_exe.executeScript("arguments[0].scrollIntoView(true);", SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]")));
			
			String praposerName = SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]//td[1]/a[1]")).getText();
			String AgencyName = SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]//td[6]")).getText();
			String policy_type = SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]//td[4]")).getText();
			String policy_status = SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]//td[5]")).getText();
			
			if(policy_type.equalsIgnoreCase(type) && policy_status.equalsIgnoreCase(status)) {
				
				data_map.put(sheetName+"_ClientName", praposerName);
				data_map.put("QC_AgencyName", AgencyName);
				Underlying_data_map.put(code+"_ClientName", praposerName);
				Underlying_data_map.put("QC_AgencyName", AgencyName);
				
				SearchedPolicyTable.findElement(By.xpath("//tr["+i+"]//td[1]/a[1]")).click();
				String durationPath = "//td[text()='Duration (days)']//following-sibling::td//div";
				duration = driver.findElement(By.xpath(durationPath)).getText();
				PolicyNumber = k.getText("PremiumSummary_PolicyNumber");
				policyStartDate = k.getTextByXpath("//*[@id='start_date']");
				
				// Verification between Amendment period and Duration. 
				// Amendment period (Passed from data sheet) should not greate than searched policy duration.
				
				if(TestBase.businessEvent.equalsIgnoreCase("MTA") && type.equalsIgnoreCase("Endorsement")){
					int ammendmet_period = Integer.parseInt((String)data_map.get("MTA_EndorsementPeriod"));
					String transactionDetailsMsg_xpath = "//p[text()=' Transaction Details ']//following-sibling::p";
					WebElement transactionDetails_Msg = null;
					try{
						k.ImplicitWaitOff();
							transactionDetails_Msg = driver.findElement(By.xpath(transactionDetailsMsg_xpath));
						k.ImplicitWaitOn();
					}catch(NoSuchElementException nse){
						String reinstamenet_xpath = "//p[text()='Annual Premium ']//following-sibling::p[1]";
						if(driver.findElement(By.xpath(reinstamenet_xpath)).isDisplayed()){
							if(driver.findElement(By.xpath(reinstamenet_xpath)).getText().contains("Reinstate")){
								TestUtil.reportStatus("<p style='color:blue'>Reinstatement event present for Policy : <b> ["+PolicyNumber+" ] </b> . Searching new Policy.</p>", "Info", false);
								customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
								SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
								rows = SearchedPolicyTable.findElements(By.tagName("tr"));
								k.ImplicitWaitOn();
								continue;
							}
						}
					}
					
					String text = transactionDetails_Msg.getText();
					
					String days[] = text.split(",");
					
					if(ammendmet_period > (Integer.parseInt(days[1].substring(days[1].indexOf(":")+2, days[1].indexOf("days")).trim()))){
						TestUtil.reportStatus("<p style='color:blue'>Amendment period <b> [ "+ammendmet_period+" ] </b> for Policy : <b> ["+PolicyNumber+" ] </b> is greater than duration <b> [ "+duration+" ] </b> of policy. Searched new Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}
					if(Integer.parseInt(days[1].substring(days[1].indexOf(":")+2, days[1].indexOf("days")).trim()) < 0){
						TestUtil.reportStatus("<p style='color:blue'>Effective days of searched Policy : <b> ["+PolicyNumber+" ] </b> is less than 0. Searching new Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}
					
					//For Skipping Reinstatement Policies
					customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
					
					WebElement tableID = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody"));
					List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
					int size = HistoryRows.size();
					boolean isReinstament=false;
					for(int j=1;j<=size;j++){
						
						String EventName = "";
						
						try{
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]//a")).getText();
							k.ImplicitWaitOn();
						}catch(Throwable t){
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]")).getText();
							k.ImplicitWaitOn();
						}
							
						if(EventName.contains("Reinstat")){
							isReinstament=true;
							break;
						}
						
					}
					if(isReinstament){
						TestUtil.reportStatus("<p style='color:blue'>Searched Policy : <b> ["+PolicyNumber+"] </b> has Reinstament event in it . So Searching the next policy . </p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}else{
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}
						
					
					/**
					 * 
					 * Get First selected cover from policy and decide to perform AP/RP
					 */
					
					String first_Selected_cover= get_First_Cover(policy_type, policy_status,"Selected");
					
					try{
						switch((String)common.MTA_excel_data_map.get("MTA_Operation")) {
							case "AP":
								
								AP_RP_Cover_Key = "AP-" + first_Selected_cover ; 
								TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
								if(first_Selected_cover.contains("CommercialVehicle"))
									first_Selected_cover = "CommercialVehicles";
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
								//common.MTA_excel_data_map.put("CD_"+first_Selected_cover,"Yes"); //Need this cover in the policy
								UpdateCoverDetails_To_Map(type , status);
									
								break;
							case "RP":
								
								AP_RP_Cover_Key = "RP-" + first_Selected_cover ;
								TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
								if(first_Selected_cover.contains("CommercialVehicle"))
									first_Selected_cover = "CommercialVehicles";
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
								//common.MTA_excel_data_map.put("CD_"+first_Selected_cover,"Yes"); //Need this cover in the policy
								UpdateCoverDetails_To_Map(type , status);
								
								break;
							case "Policy-level":
								
								UpdateCoverDetails_To_Map(type , status);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
								
								break;
							case "Non-Financial":
								
								TestUtil.reportStatus("<b> [ <i> Non-Financial </i> ] changes will be performed on the existing policy as a part of an endorsement . </b>", "Info", true);
								UpdateCoverDetails_To_Map(type , status);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
								
								break;
							case "":
								System.out.println("MTA Operation is not valid . "); 
								return true;
						
						}
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}catch(NullPointerException npe){
						common.MTA_excel_data_map.put("MTA_Operation","Normal-MTA");
					}
					
					/*if(((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("AP") || ((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("RP")) {
						
						TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
						if(first_Selected_cover.contains("CommercialVehicle"))
							first_Selected_cover = "CommercialVehicles";
						common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
						//common.MTA_excel_data_map.put("CD_"+first_Selected_cover,"Yes"); //Need this cover in the policy
						UpdateCoverDetails_To_Map(type , status);
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
						
					}else if(((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("Policy-level")){
						
						UpdateCoverDetails_To_Map(type , status);
						common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
						
					}else if(((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("Non-Financial")){
						
						TestUtil.reportStatus("<b> [ <i> Non-Financial </i> ] changes will be performed on the existing policy as a part of an endorsement . </b>", "Info", true);
						UpdateCoverDetails_To_Map(type , status);
						common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
						
					}*/
					
					//Get MD Net Net Premium
					String annualPremiumTablePath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr";
					JavascriptExecutor j_exe1 = (JavascriptExecutor) driver;
					j_exe1.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(annualPremiumTablePath)));

					List<WebElement> listOfRows = driver.findElements(By.xpath(annualPremiumTablePath));
					int sizeOfAnnualPremiumTable = listOfRows.size();

					for (int coverDetails = 1; coverDetails <= sizeOfAnnualPremiumTable; coverDetails++) {

						String CoverName = driver.findElement(By.xpath(annualPremiumTablePath + "[" + coverDetails + "]//td[1]")).getText().replaceAll(" ", "");

						if (CoverName.replaceAll(",", "").equalsIgnoreCase("MaterialDamage")) {
								String MaterialDamageNetNetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td[2]";
								JavascriptExecutor j_exe2 = (JavascriptExecutor) driver;
								j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(MaterialDamageNetNetPremiumPath)));
								common_HHAZ.MD_Premium = driver.findElement(By.xpath(MaterialDamageNetNetPremiumPath)).getText();
						}else {
							continue;
						}
						break;
						
					}
						
				}
				if(TestBase.businessEvent.equalsIgnoreCase("MTA") && type.equalsIgnoreCase("New Business")){
					int ammendmet_period = Integer.parseInt((String)data_map.get("MTA_EndorsementPeriod"));
					
					if(ammendmet_period > (Integer.parseInt(duration))){
						TestUtil.reportStatus("<p style='color:blue'>Amendment period <b> [ "+ammendmet_period+" ] </b> for Policy : <b> ["+PolicyNumber+" ] </b> is greater than duration <b> [ "+duration+" ] </b> of policy. Searched new Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}
					
					// Check if rewind button will present or not
					if(((String)data_map.get("MTA_Status")).contains("Rewind")) {
						
						SimpleDateFormat date_f = new SimpleDateFormat("dd/MM/yyyy");
						Date policyStart_date = date_f.parse(policyStartDate);
					    Date todays_date = date_f.parse(date_f.format(new Date()));
					    long time_diff = todays_date.getTime() - policyStart_date.getTime();
					    
					    long days_diff = TimeUnit.DAYS.convert(time_diff, TimeUnit.MILLISECONDS);
					    int rewindButtonDuration = Integer.parseInt(CONFIG.getProperty("RewindButtonDurration"));
					    
					    if(days_diff > rewindButtonDuration) {
							TestUtil.reportStatus("<p style='color:blue'>Difference between policy start date AND todays date is: <b> ["+ days_diff +"] </b> , which is greater than rewind button duration <b> ["+rewindButtonDuration+"] </b>. So Rewind Operation is not possible. Hence Searching the new Policy .</p>", "Info", false);
							customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
							SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
							rows = SearchedPolicyTable.findElements(By.tagName("tr"));
							continue;
				
					    }
					}
					
					//For Skipping Reinstatement Policies
					customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
					
					WebElement tableID = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody"));
					List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
					int size = HistoryRows.size();
					boolean isReinstament=false;
					for(int j=1;j<=size;j++){
						
						String EventName = "";
						
						try{
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]//a")).getText();
							k.ImplicitWaitOn();
						}catch(Throwable t){
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]")).getText();
							k.ImplicitWaitOn();
						}
							
						if(EventName.contains("Reinstat")){
							isReinstament=true;
							break;
						}
						
					}
					if(isReinstament){
						TestUtil.reportStatus("<p style='color:blue'>Searched Policy : <b> ["+PolicyNumber+"] </b> has Reinstament event in it . So Searching the next policy . </p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}else{
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}
					
					/**
					 * 
					 * Get First selected cover from policy and decide to perform AP/RP
					 */
					
					String first_Selected_cover = get_First_Cover(policy_type, policy_status,"Selected");
					
					try{
						switch((String)common.MTA_excel_data_map.get("MTA_Operation")) {
							case "AP":
								
								AP_RP_Cover_Key = "AP-" + first_Selected_cover ; 
								TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
								if(first_Selected_cover.contains("CommercialVehicle"))
									first_Selected_cover = "CommercialVehicles";
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
								//common.MTA_excel_data_map.put("CD_"+first_Selected_cover,"Yes"); //Need this cover in the policy
								UpdateCoverDetails_To_Map(type , status);
									
								break;
							case "RP":
								
								AP_RP_Cover_Key = "RP-" + first_Selected_cover ;
								TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
								if(first_Selected_cover.contains("CommercialVehicle"))
									first_Selected_cover = "CommercialVehicles";
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
								//common.MTA_excel_data_map.put("CD_"+first_Selected_cover,"Yes"); //Need this cover in the policy
								UpdateCoverDetails_To_Map(type , status);
								
								break;
							case "Policy-level":
								
								UpdateCoverDetails_To_Map(type , status);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
								
								break;
							case "Non-Financial":
								
								TestUtil.reportStatus("<b> [ <i> Non-Financial </i> ] changes will be performed on the existing policy as a part of an endorsement . </b>", "Info", true);
								UpdateCoverDetails_To_Map(type , status);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
								
								break;
							case "":
								System.out.println("MTA Operation is not valid . "); 
								return true;
						
						}
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}catch(NullPointerException npe){
						common.MTA_excel_data_map.put("MTA_Operation","Normal-MTA");
					}
					
					/*if(((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("AP") || ((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("RP")) {
						TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
						if(first_Selected_cover.contains("CommercialVehicle"))
							first_Selected_cover = "CommercialVehicles";
						common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
						//common.MTA_excel_data_map.put("CD_"+first_Selected_cover,"Yes"); //Need this cover in the policy
						UpdateCoverDetails_To_Map(type , status);
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
						
					}else if(((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("Policy-level")){
						UpdateCoverDetails_To_Map(type , status);
						common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}else if(((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("Non-Financial")){
						TestUtil.reportStatus("<b> [ <i> Non-Financial </i> ] changes will be performed on the existing policy as a part of an endorsement . </b>", "Info", true);
						UpdateCoverDetails_To_Map(type , status);
						common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}*/
					
					//Get MD Net Net Premium
					String annualPremiumTablePath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr";
					JavascriptExecutor j_exe1 = (JavascriptExecutor) driver;
					j_exe1.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(annualPremiumTablePath)));

					List<WebElement> listOfRows = driver.findElements(By.xpath(annualPremiumTablePath));
					int sizeOfAnnualPremiumTable = listOfRows.size();

					for (int coverDetails = 1; coverDetails <= sizeOfAnnualPremiumTable; coverDetails++) {

						String CoverName = driver.findElement(By.xpath(annualPremiumTablePath + "[" + coverDetails + "]//td[1]")).getText().replaceAll(" ", "");

						if (CoverName.replaceAll(",", "").equalsIgnoreCase("MaterialDamage")) {
								String MaterialDamageNetNetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td[2]";
								JavascriptExecutor j_exe2 = (JavascriptExecutor) driver;
								j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(MaterialDamageNetNetPremiumPath)));
								common_HHAZ.MD_Premium = driver.findElement(By.xpath(MaterialDamageNetNetPremiumPath)).getText();
						}else {
							continue;
						}
						break;
						
					}
						
				}
				if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
					String endDatePath = "//td[text()='Policy End Date (dd/mm/yyyy) ']//following-sibling::td";
					String endDate = driver.findElement(By.xpath(endDatePath)).getText();
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
					int dateDif = Integer.parseInt((String)common.CAN_excel_data_map.get("CP_AddDifference"));
					Date c_date = df.parse(common.getUKDate());
		    		
					String Cancellation_date = common.daysIncrementWithOutFormation(df.format(c_date), dateDif);
					
					Date date1 = df.parse(endDate);
		            Date date2 = df.parse(Cancellation_date);
		            
		            if(date2.after(date1)){
		            	TestUtil.reportStatus("<p style='color:blue'>Cancellation is done on Post inception date <b> [ "+Cancellation_date+" ] </b> for Policy : <b> ["+PolicyNumber+" ] </b> which is greater than Policy end date <b> [ "+endDate+" ] </b>.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
		            }
					
		            //For Skipping Reinstatement Policies
					customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
					
					WebElement tableID = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody"));
					List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
					int size = HistoryRows.size();
					boolean isReinstament=false;
					for(int j=1;j<=size;j++){
						
						String EventName = "";
						
						try{
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]//a")).getText();
							k.ImplicitWaitOn();
						}catch(Throwable t){
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]")).getText();
							k.ImplicitWaitOn();
						}
							
						if(EventName.contains("Reinstat")){
							isReinstament=true;
							break;
						}
						
					}
					if(isReinstament){
						TestUtil.reportStatus("<p style='color:blue'>Searched Policy : <b> ["+PolicyNumber+"] </b> has Reinstament event in it . So Searching the next policy . </p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}else{
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}
				}
				if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
					
					String startDatePath = "//td[text()='Policy Start Date (dd/mm/yyyy) ']//following-sibling::td";
					String startDate = driver.findElement(By.xpath(startDatePath)).getText();
					int RewindButtonCheckDays = Integer.parseInt(CONFIG.get("RewindButtonDurration").toString());
					String FORMAT = "dd/MM/yyyy";
					SimpleDateFormat sd = new SimpleDateFormat(FORMAT);
					Date currentDate = new Date();
					
					int daysDiff = Integer.parseInt(common.DateDiff(sd.format(currentDate), startDate));
					
					if(daysDiff > RewindButtonCheckDays){
						TestUtil.reportStatus("<p style='color:blue'>Rewind button is not present for Policy : <b> ["+PolicyNumber+" ] </b> because the differance between start date <b> [ "+startDate+" ] </b> and today's date <b> [ "+sd.format(currentDate)+" ] </b> is greater than duration <b> [ 55 ] </b> days. Searched new Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
		            }
					
					customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
					boolean isReinstate = false;
					WebElement tableID = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody"));
					List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
					int size = HistoryRows.size();
					
					for(int j=1;j<=size;j++){
						
						String EventName = "";
						
						try{
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]//a")).getText();
							k.ImplicitWaitOn();
						}catch(Throwable t){
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]")).getText();
							k.ImplicitWaitOn();
						}
						
						
						if(EventName.contains("Reinstat")){
							isReinstate=true;
							break;
						}
						
					}
					
					if(isReinstate){
						TestUtil.reportStatus("<p style='color:blue'>Rewind button is not present for Policy : <b> ["+PolicyNumber+" ] </b> because this policy is reinsted Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}else{
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}
					
		        }
				TestUtil.reportStatus("Existing Policy algorithm applied for Product : <b> [ "+TestBase.product+" ] </b> and selected Policy is : <b> [ "+PolicyNumber+" ] </b>.", "Info", false);
				flag = true;
				break;
			}else{
				flag = false;
			}
		}
		// UpdatePremiumSummaryData : This function will update all necessary details from premium summary to NB and MTA map.
		if(flag){
			TestUtil.reportStatus("Policy has been identified with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ]", "Info", false);
			customAssert.assertTrue(updatePremiumSummaryData(PolicyNumber,duration,type) , "Update Premium Summary Values function is having issues.");
			return retvalue;
		}else{
			TestUtil.reportStatus("<p style='color:red'>No policies are present with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ] </b> OR pre-requistis conditions are not matching to proceed further.</p>", "Info", false);
			return false;
		}
	}else if(common_HHAZ.is_Pagination_enabled()){
			WebElement btn_next = driver.findElement(By.xpath("//*[@id='mainpanel']//a[contains(text(),'next')]"));
			btn_next.click(); //for  Pagination purpose
			retvalue = true;
			selectPolicy(type,status);
			
	}else{
		TestUtil.reportStatus("No policies are present with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ] </b>", "Fail", true);
		return false;
	}
	
	return retvalue;
	
    
}

public boolean selectPolicy_PEN(String type , String status) throws AWTException, InterruptedException, ParseException{
	
	Map<Object, Object> data_map = null;
	Map<Object, Object> Underlying_data_map = null;
	String sheetName = "" , code = "";
	int net_premium_td_path = 5;
	String NetPremiumPath = "";
	JavascriptExecutor j_exe2 = (JavascriptExecutor) driver;

	AP_RP_Cover_Key = "";
	switch (type) {
	case "Endorsement":
	case "New Business":
		if(TestBase.businessEvent.equalsIgnoreCase("MTA")){
			data_map = common.MTA_excel_data_map;
			Underlying_data_map = common.NB_excel_data_map;
			sheetName = "MTA";
			code = "NB";
		}else if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
			if(type.equalsIgnoreCase("Endorsement")){
				data_map = common.Rewind_excel_data_map;
				Underlying_data_map = common.MTA_excel_data_map;
				Rewind_Underlying_data_map = common.NB_excel_data_map;
				sheetName = "Rewind";
				code = "MTA";
			}else{
				data_map = common.Rewind_excel_data_map;
				Underlying_data_map = common.NB_excel_data_map;
				sheetName = "Rewind";
				code = "NB";
			}
		}else if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
			data_map = common.CAN_excel_data_map;
			Underlying_data_map = common.NB_excel_data_map;
			sheetName = "CAN";
			code = "NB";
		}
		break;
	case "Renewal":
		if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
			data_map = common.CAN_excel_data_map;
			Underlying_data_map = common.Renewal_excel_data_map;
			sheetName = "CAN";
			code = "Renewal";
		}else {
			data_map = common.MTA_excel_data_map;
			Underlying_data_map = common.Renewal_excel_data_map;
			sheetName = "MTA";
		}
		break;
	}
	
	boolean retvalue = true;
	boolean flag = false;
	String PolicyNumber ="" ,duration="",policyStartDate="";  
	WebElement SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
	List<WebElement> rows = SearchedPolicyTable.findElements(By.tagName("tr"));
	
	if(rows.size() >0){
		for(int i = 1; i < rows.size(); i++ ) {
			
			JavascriptExecutor j_exe = (JavascriptExecutor) driver;
			j_exe.executeScript("arguments[0].scrollIntoView(true);", SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]")));
			
			String praposerName = SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]//td[1]/a[1]")).getText();
			String AgencyName = SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]//td[6]")).getText();
			String policy_type = SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]//td[4]")).getText();
			String policy_status = SearchedPolicyTable.findElement(By.xpath("//tbody//tr["+i+"]//td[5]")).getText();
			
			if(policy_type.equalsIgnoreCase(type) && policy_status.equalsIgnoreCase(status)) {
				
				data_map.put(sheetName+"_ClientName", praposerName);
				data_map.put("QC_AgencyName", AgencyName);
				Underlying_data_map.put(code+"_ClientName", praposerName);
				Underlying_data_map.put("QC_AgencyName", AgencyName);
				
				SearchedPolicyTable.findElement(By.xpath("//tr["+i+"]//td[1]/a[1]")).click();
				String durationPath = "//td[text()='Duration (days)']//following-sibling::td//div";
				duration = driver.findElement(By.xpath(durationPath)).getText();
				PolicyNumber = k.getText("PremiumSummary_PolicyNumber");
				policyStartDate = k.getTextByXpath("//*[@id='start_date']");
				
				// Verification between Amendment period and Duration. 
				// Amendment period (Passed from data sheet) should not greate than searched policy duration.
				
				if(TestBase.businessEvent.equalsIgnoreCase("MTA") && type.equalsIgnoreCase("Endorsement")){
					int ammendmet_period = Integer.parseInt((String)data_map.get("MTA_EndorsementPeriod"));
					String transactionDetailsMsg_xpath = "//p[text()=' Transaction Details ']//following-sibling::p";
					WebElement transactionDetails_Msg = null;
					try{
						k.ImplicitWaitOff();
							transactionDetails_Msg = driver.findElement(By.xpath(transactionDetailsMsg_xpath));
						k.ImplicitWaitOn();
					}catch(NoSuchElementException nse){
						String reinstamenet_xpath = "//p[text()='Annual Premium ']//following-sibling::p[1]";
						if(driver.findElement(By.xpath(reinstamenet_xpath)).isDisplayed()){
							if(driver.findElement(By.xpath(reinstamenet_xpath)).getText().contains("Reinstate")){
								TestUtil.reportStatus("<p style='color:blue'>Reinstatement event present for Policy : <b> ["+PolicyNumber+" ] </b> . Searching new Policy.</p>", "Info", false);
								customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
								SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
								rows = SearchedPolicyTable.findElements(By.tagName("tr"));
								k.ImplicitWaitOn();
								continue;
							}
						}
					}
					
					String text = transactionDetails_Msg.getText();
					
					String days[] = text.split(",");
					
					if(ammendmet_period > (Integer.parseInt(days[1].substring(days[1].indexOf(":")+2, days[1].indexOf("days")).trim()))){
						TestUtil.reportStatus("<p style='color:blue'>Amendment period <b> [ "+ammendmet_period+" ] </b> for Policy : <b> ["+PolicyNumber+" ] </b> is greater than duration <b> [ "+duration+" ] </b> of policy. Searched new Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}
					if(Integer.parseInt(days[1].substring(days[1].indexOf(":")+2, days[1].indexOf("days")).trim()) < 0){
						TestUtil.reportStatus("<p style='color:blue'>Effective days of searched Policy : <b> ["+PolicyNumber+" ] </b> is less than 0. Searching new Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}
					
					//For Skipping Reinstatement Policies
					customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
					
					WebElement tableID = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody"));
					List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
					int size = HistoryRows.size();
					boolean isReinstament=false;
					for(int j=1;j<=size;j++){
						
						String EventName = "";
						
						try{
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]//a")).getText();
							k.ImplicitWaitOn();
						}catch(Throwable t){
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]")).getText();
							k.ImplicitWaitOn();
						}
							
						if(EventName.contains("Reinstat")){
							isReinstament=true;
							break;
						}
						
					}
					if(isReinstament){
						TestUtil.reportStatus("<p style='color:blue'>Searched Policy : <b> ["+PolicyNumber+"] </b> has Reinstament event in it . So Searching the next policy . </p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}else{
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}
						
					
					/**
					 * 
					 * Get First selected cover from policy and decide to perform AP/RP
					 */
					String first_Selected_cover = "";
					if(TestBase.product.equals("XOE")) {
						first_Selected_cover = get_Selected_Cover_For_XOE(policy_type, policy_status);
					}else {
						first_Selected_cover = get_First_Cover(policy_type, policy_status,"Selected");
					}
					
					if(first_Selected_cover.equals("Liability"))
						first_Selected_cover = "EmployersLiability";
					
					try{
						switch((String)common.MTA_excel_data_map.get("MTA_Operation")) {
							case "AP":
								
								AP_RP_Cover_Key = "AP-" + first_Selected_cover ; 
								TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
								UpdateCoverDetails_To_Map_PEN(type , status);
									
								break;
							case "RP":
								
								AP_RP_Cover_Key = "RP-" + first_Selected_cover ;
								TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
								UpdateCoverDetails_To_Map_PEN(type , status);
								
								break;
							case "Policy-level":
								
								UpdateCoverDetails_To_Map_PEN(type , status);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
								
								break;
							case "Non-Financial":
								
								TestUtil.reportStatus("<b> [ <i> Non-Financial </i> ] changes will be performed on the existing policy as a part of an endorsement . </b>", "Info", true);
								UpdateCoverDetails_To_Map_PEN(type , status);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
								
								break;
							case "":
								System.out.println("MTA Operation is not valid . "); 
								return true;
						
						}
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}catch(NullPointerException npe){
						common.MTA_excel_data_map.put("MTA_Operation","Normal-MTA");
					}
										
					//Get MD Net Net Premium
					String annualPremiumTablePath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr";
					JavascriptExecutor j_exe1 = (JavascriptExecutor) driver;
					j_exe1.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(annualPremiumTablePath)));

					List<WebElement> listOfRows = driver.findElements(By.xpath(annualPremiumTablePath));
					int sizeOfAnnualPremiumTable = listOfRows.size();
					
					for (int coverDetails = 1; coverDetails <= sizeOfAnnualPremiumTable; coverDetails++) {

						String CoverName = driver.findElement(By.xpath(annualPremiumTablePath + "[" + coverDetails + "]//td[1]")).getText().replaceAll(" ", "");
						
						switch(CoverName.replaceAll(",", "")) {
						
							case "MaterialDamage":
								NetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td["+net_premium_td_path+"]";
								j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(NetPremiumPath)));
								common_PEN.MD_Premium = driver.findElement(By.xpath(NetPremiumPath)).getText();
							break;
							
							case "BusinessInterruption":
								NetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td["+net_premium_td_path+"]";
								j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(NetPremiumPath)));
								common_PEN.BI_Premium = driver.findElement(By.xpath(NetPremiumPath)).getText();
							break;
							
							case "LossOfRentalIncome": // For POB/POC
								NetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td["+net_premium_td_path+"]";
								j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(NetPremiumPath)));
								common_PEN.LRI_Premium = driver.findElement(By.xpath(NetPremiumPath)).getText();
							break;
							
							case "PropertyExcessofLoss": // For XOE
								NetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td["+net_premium_td_path+"]";
								j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(NetPremiumPath)));
								common_PEN.PEOL_Premium = driver.findElement(By.xpath(NetPremiumPath)).getText();
							break;
							default:
								continue;
								
							
						}
					}
				}
				
				if(TestBase.businessEvent.equalsIgnoreCase("MTA") && type.equalsIgnoreCase("New Business")){
					int ammendmet_period = Integer.parseInt((String)data_map.get("MTA_EndorsementPeriod"));
					
					if(ammendmet_period > (Integer.parseInt(duration))){
						TestUtil.reportStatus("<p style='color:blue'>Amendment period <b> [ "+ammendmet_period+" ] </b> for Policy : <b> ["+PolicyNumber+" ] </b> is greater than duration <b> [ "+duration+" ] </b> of policy. Searched new Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}
					
					// Check if rewind button will present or not
					if(((String)data_map.get("MTA_Status")).contains("Rewind")) {
						
						SimpleDateFormat date_f = new SimpleDateFormat("dd/MM/yyyy");
						Date policyStart_date = date_f.parse(policyStartDate);
					    Date todays_date = date_f.parse(date_f.format(new Date()));
					    long time_diff = todays_date.getTime() - policyStart_date.getTime();
					    
					    long days_diff = TimeUnit.DAYS.convert(time_diff, TimeUnit.MILLISECONDS);
					    int rewindButtonDuration = Integer.parseInt(CONFIG.getProperty("RewindButtonDurration"));
					    
					    if(days_diff > rewindButtonDuration) {
							TestUtil.reportStatus("<p style='color:blue'>Difference between policy start date AND todays date is: <b> ["+ days_diff +"] </b> , which is greater than rewind button duration <b> ["+rewindButtonDuration+"] </b>. So Rewind Operation is not possible. Hence Searching the new Policy .</p>", "Info", false);
							customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
							SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
							rows = SearchedPolicyTable.findElements(By.tagName("tr"));
							continue;
				
					    }
					}
					
					//For Skipping Reinstatement Policies
					customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
					
					WebElement tableID = driver.findElement(By.xpath("//*[@id='L_is_financed']//following::table[1]//tbody"));
					List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
					int size = HistoryRows.size();
					boolean isReinstament=false;
					for(int j=1;j<=size;j++){
						
						String EventName = "";
						
						try{
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='L_is_financed']//following::table[1]//tbody//tr["+j+"]//td[1]//a")).getText();
							k.ImplicitWaitOn();
						}catch(Throwable t){
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='L_is_financed']//following::table[1]//tbody//tr["+j+"]//td[1]")).getText();
							k.ImplicitWaitOn();
						}
							
						if(EventName.contains("Reinstat")){
							isReinstament=true;
							break;
						}
						
					}
					if(isReinstament){
						TestUtil.reportStatus("<p style='color:blue'>Searched Policy : <b> ["+PolicyNumber+"] </b> has Reinstament event in it . So Searching the next policy . </p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}else{
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}
					
					/**
					 * 
					 * Get First selected cover from policy and decide to perform AP/RP
					 */
					
					String first_Selected_cover = "";
					if(TestBase.product.equals("XOE")) {
						first_Selected_cover = get_Selected_Cover_For_XOE(policy_type, policy_status);
					}else {
						first_Selected_cover = get_First_Cover(policy_type, policy_status,"Selected");
					}
					if(first_Selected_cover.equals("Liability"))
						first_Selected_cover = "EmployersLiability";
					
					try{
						switch((String)common.MTA_excel_data_map.get("MTA_Operation")) {
							case "AP":
								
								AP_RP_Cover_Key = "AP-" + first_Selected_cover ; 
								TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
								if(first_Selected_cover.contains("CommercialVehicle"))
									first_Selected_cover = "CommercialVehicles";
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
								//common.MTA_excel_data_map.put("CD_"+first_Selected_cover,"Yes"); //Need this cover in the policy
								UpdateCoverDetails_To_Map_PEN(type , status);
									
								break;
							case "RP":
								
								AP_RP_Cover_Key = "RP-" + first_Selected_cover ;
								TestUtil.reportStatus("<b> Cover level [ <i> "+(String)common.MTA_excel_data_map.get("MTA_Operation")+" </i> ] will be performed for the cover : [ <i>"+first_Selected_cover+"</i> ] on the existing policy as a part of an endorsement . </b>", "Info", true);
								if(first_Selected_cover.contains("CommercialVehicle"))
									first_Selected_cover = "CommercialVehicles";
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision",AP_RP_Cover_Key);
								//common.MTA_excel_data_map.put("CD_"+first_Selected_cover,"Yes"); //Need this cover in the policy
								UpdateCoverDetails_To_Map_PEN(type , status);
								
								break;
							case "Policy-level":
								
								UpdateCoverDetails_To_Map_PEN(type , status);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
								
								break;
							case "Non-Financial":
								
								TestUtil.reportStatus("<b> [ <i> Non-Financial </i> ] changes will be performed on the existing policy as a part of an endorsement . </b>", "Info", true);
								UpdateCoverDetails_To_Map_PEN(type , status);
								common.MTA_excel_data_map.put("CD_AP_RP_CoverSpecific_Decision","");
								
								break;
							case "":
								System.out.println("MTA Operation is not valid . "); 
								return true;
						
						}
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}catch(NullPointerException npe){
						common.MTA_excel_data_map.put("MTA_Operation","Normal-MTA");
					}
					
					//Get MD Net Net Premium
					String annualPremiumTablePath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr";
					JavascriptExecutor j_exe1 = (JavascriptExecutor) driver;
					j_exe1.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(annualPremiumTablePath)));

					List<WebElement> listOfRows = driver.findElements(By.xpath(annualPremiumTablePath));
					int sizeOfAnnualPremiumTable = listOfRows.size();

					for (int coverDetails = 1; coverDetails <= sizeOfAnnualPremiumTable; coverDetails++) {

						String CoverName = driver.findElement(By.xpath(annualPremiumTablePath + "[" + coverDetails + "]//td[1]")).getText().replaceAll(" ", "");

						switch(CoverName.replaceAll(",", "")) {
						
						case "MaterialDamage":
							NetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td["+net_premium_td_path+"]";
							j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(NetPremiumPath)));
							common_PEN.MD_Premium = driver.findElement(By.xpath(NetPremiumPath)).getText();
						break;
						
						case "BusinessInterruption":
							NetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td["+net_premium_td_path+"]";
							j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(NetPremiumPath)));
							common_PEN.BI_Premium = driver.findElement(By.xpath(NetPremiumPath)).getText();
						break;
						
						case "LossOfRentalIncome": // For POB/POC
							NetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td["+net_premium_td_path+"]";
							j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(NetPremiumPath)));
							common_PEN.LRI_Premium = driver.findElement(By.xpath(NetPremiumPath)).getText();
						break;
						
						case "PropertyExcessofLoss": // For XOE
							NetPremiumPath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr["+ coverDetails + "]//td["+net_premium_td_path+"]";
							j_exe2.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(NetPremiumPath)));
							common_PEN.PEOL_Premium = driver.findElement(By.xpath(NetPremiumPath)).getText();
						break;
						
						default:
							continue;
						
						}
					}
						
				}
				
				if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
					String endDatePath = "//td[text()='Policy End Date (dd/mm/yyyy) ']//following-sibling::td";
					String endDate = driver.findElement(By.xpath(endDatePath)).getText();
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
					int dateDif = Integer.parseInt((String)common.CAN_excel_data_map.get("CP_AddDifference"));
					Date c_date = df.parse(common.getUKDate());
		    		
					String Cancellation_date = common.daysIncrementWithOutFormation(df.format(c_date), dateDif);
					
					Date date1 = df.parse(endDate);
		            Date date2 = df.parse(Cancellation_date);
		            
		            if(date2.after(date1)){
		            	TestUtil.reportStatus("<p style='color:blue'>Cancellation is done on Post inception date <b> [ "+Cancellation_date+" ] </b> for Policy : <b> ["+PolicyNumber+" ] </b> which is greater than Policy end date <b> [ "+endDate+" ] </b>.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
		            }
					
		            //For Skipping Reinstatement Policies
					customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
					
					WebElement tableID = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody"));
					List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
					int size = HistoryRows.size();
					boolean isReinstament=false;
					for(int j=1;j<=size;j++){
						
						String EventName = "";
						
						try{
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]//a")).getText();
							k.ImplicitWaitOn();
						}catch(Throwable t){
							k.ImplicitWaitOff();
							EventName = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+j+"]//td[1]")).getText();
							k.ImplicitWaitOn();
						}
							
						if(EventName.contains("Reinstat")){
							isReinstament=true;
							break;
						}
						
					}
					if(isReinstament){
						TestUtil.reportStatus("<p style='color:blue'>Searched Policy : <b> ["+PolicyNumber+"] </b> has Reinstament event in it . So Searching the next policy . </p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}else{
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}
				}
				if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
					
					String startDatePath = "//td[text()='Policy Start Date (dd/mm/yyyy) ']//following-sibling::td";
					String startDate = driver.findElement(By.xpath(startDatePath)).getText();
					int RewindButtonCheckDays = Integer.parseInt(CONFIG.get("RewindButtonDurration").toString());
					String FORMAT = "dd/MM/yyyy";
					SimpleDateFormat sd = new SimpleDateFormat(FORMAT);
					Date currentDate = new Date();
					
					int daysDiff = Integer.parseInt(common.DateDiff(sd.format(currentDate), startDate));
					
					if(daysDiff > RewindButtonCheckDays){
						TestUtil.reportStatus("<p style='color:blue'>Rewind button is not present for Policy : <b> ["+PolicyNumber+" ] </b> because the differance between start date <b> [ "+startDate+" ] </b> and today's date <b> [ "+sd.format(currentDate)+" ] </b> is greater than duration <b> [ 55 ] </b> days. Searched new Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
		            }
					
					customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
					boolean isReinstate = false;
					WebElement tableID = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[2]//tbody"));
					List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
					int size = HistoryRows.size();
					
					for(int j=1;j<=size;j++){
						
						String EventName = "";
						
						try{
							k.ImplicitWaitOff();
							EventName = tableID.findElement(By.xpath("//tr["+j+"]//td[1]//a")).getText();
							k.ImplicitWaitOn();
						}catch(Throwable t){
							k.ImplicitWaitOff();
							EventName = tableID.findElement(By.xpath("//tr["+j+"]//td[1]")).getText();
							k.ImplicitWaitOn();
						}
						
						
						if(EventName.contains("Reinstat")){
							isReinstate=true;
							break;
						}
						
					}
					
					if(isReinstate){
						TestUtil.reportStatus("<p style='color:blue'>Rewind button is not present for Policy : <b> ["+PolicyNumber+" ] </b> because this policy is reinsted Policy.</p>", "Info", false);
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
						SearchedPolicyTable = driver.findElement(By.xpath("//*[@id='srch-update']//following::table[1]"));
						rows = SearchedPolicyTable.findElements(By.tagName("tr"));
						continue;
					}else{
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary . ");
					}
					
		        }
				TestUtil.reportStatus("Existing Policy algorithm applied for Product : <b> [ "+TestBase.product+" ] </b> and selected Policy is : <b> [ "+PolicyNumber+" ] </b>.", "Info", false);
				flag = true;
				break;
			}else{
				flag = false;
			}
		}
		// UpdatePremiumSummaryData : This function will update all necessary details from premium summary to NB and MTA map.
		if(flag){
			TestUtil.reportStatus("Policy has been identified with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ]", "Info", false);
			customAssert.assertTrue(updatePremiumSummaryData_PEN(PolicyNumber,duration,type) , "Update Premium Summary Values function is having issues.");
			return retvalue;
		}else{
			TestUtil.reportStatus("<p style='color:red'>No policies are present with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ] </b> OR pre-requistis conditions are not matching to proceed further.</p>", "Info", false);
			return false;
		}
	}else if(common_HHAZ.is_Pagination_enabled()){
			WebElement btn_next = driver.findElement(By.xpath("//*[@id='mainpanel']//a[contains(text(),'next')]"));
			btn_next.click(); //for  Pagination purpose
			retvalue = true;
			selectPolicy(type,status);
			
	}else{
		TestUtil.reportStatus("No policies are present with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ] </b>", "Fail", true);
		return false;
	}
	
	return retvalue;
	
    
}


public boolean updatePremiumSummaryData(String PolicyNumber , String duration , String type) throws AWTException, InterruptedException{
	
	Map<Object, Object> data_map = null;
	Map<Object, Object> Underlying_data_map = null;
	
	try{
		String sheetName = "" , code = "";
		switch (type) {
		case "Endorsement":
		case "New Business":
			if(TestBase.businessEvent.equalsIgnoreCase("MTA")){
				data_map = common.MTA_excel_data_map;
				Underlying_data_map = common.NB_excel_data_map;
				sheetName = "MTA";
				code="NB";
			}else if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
				if(type.equalsIgnoreCase("Endorsement")){
					data_map = common.Rewind_excel_data_map;
					Underlying_data_map = common.MTA_excel_data_map;
					Rewind_Underlying_data_map = common.NB_excel_data_map;
					sheetName = "Rewind";
					code = "MTA";
				}else{
					data_map = common.Rewind_excel_data_map;
					Underlying_data_map = common.NB_excel_data_map;
					sheetName = "Rewind";
					code = "NB";
				}
			}else if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
				data_map = common.CAN_excel_data_map;
				Underlying_data_map = common.NB_excel_data_map;
				sheetName = "CAN";
				code = "NB";
			}
			break;
		case "Renewal":
			if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
				data_map = common.CAN_excel_data_map;
				Underlying_data_map = common.Renewal_excel_data_map;
				sheetName = "CAN";
				code = "Renewal";
			}else {
				data_map = common.MTA_excel_data_map;
				Underlying_data_map = common.Renewal_excel_data_map;
				sheetName = "MTA";
			}
			break;
		}
		
		boolean retvalue = true;
	   
		String QuoteNumber = k.getText("POF_QuoteNumber");
		String startDatePath = "//td[text()='Policy Start Date (dd/mm/yyyy) ']//following-sibling::td";
		String startDate = driver.findElement(By.xpath(startDatePath)).getText();
		String endDatePath = "//td[text()='Policy End Date (dd/mm/yyyy) ']//following-sibling::td";
		String endDate = driver.findElement(By.xpath(endDatePath)).getText();
		String taxExemptionPath = "";
		
		//For GTB product, Tax Exempt is displayed as non editable textbox - bug id is - PRD-14483 
		if(TestBase.product.contains("GTB")){
			taxExemptionPath = "//td[text()='Tax Exempt?']//following-sibling::td//div";
		}else{
			taxExemptionPath = "//td[text()='Is this policy exempt from insurance tax?']//following-sibling::td//div";
		}
		
		String taxExemption = driver.findElement(By.xpath(taxExemptionPath)).getText();
		String policyFinacePath = "//td[text()='Is the policy financed?']//following-sibling::td//div";
		String policyFinace = driver.findElement(By.xpath(policyFinacePath)).getText();
		String paymentWarrentyPath = "//td[text()='Is this business conducted under Premium Payment Warranty rules?']//following-sibling::td//div";
		String paymentWarrenty = driver.findElement(By.xpath(paymentWarrentyPath)).getText();
		
		if(paymentWarrenty.equalsIgnoreCase("Yes")){
			String paymentWarrentyDueDatePath = "//td[text()='Policy Start Date (dd/mm/yyyy) ']//following-sibling::td";
			String paymentWarrentyDueDate = k.getText("startDatePath");
		}
		
		Underlying_data_map.put("PS_PolicyStartDate", startDate);
		Underlying_data_map.put("PS_PolicyEndDate", endDate);
		Underlying_data_map.put(code+"_PolicyNumber", PolicyNumber);
		data_map.put("MTA_PolicyNumber", PolicyNumber);
		Underlying_data_map.put(code+"_QuoteNumber", QuoteNumber);
		data_map.put("MTA_QuoteNumber", QuoteNumber);
		Underlying_data_map.put("PS_Duration", duration);
		Underlying_data_map.put("PS_TaxExempt", taxExemption);
		Underlying_data_map.put("PS_IsPolicyFinanced", policyFinace);
		Underlying_data_map.put("PS_PaymentWarrantyRules", paymentWarrenty);
		
		/**
		 * 
		 * Store Premium Summary table values :  
		 * 
		 */
		
		String annualPremiumTablePath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr";
		List<WebElement> listOfRows = driver.findElements(By.xpath(annualPremiumTablePath));
		int sizeOfAnnualPremiumTable = listOfRows.size();
		
		for(int coverDetails = 1;coverDetails<=sizeOfAnnualPremiumTable;coverDetails++){
			
			String CoverName = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[1]")).getText().replaceAll(" ", "");
			String NetNetPremium = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[2]")).getText();
			String PenCommRate = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[3]")).getText();
			String PenCommision = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[4]")).getText();
			String NetPremium = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[5]")).getText();
			String BrokerCommRate = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[6]")).getText();
			String BrokerCommision = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[7]")).getText();
			String GrossCommRate = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[8]")).getText();
			String GrossPremium = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[9]")).getText();
			String InsuranceTaxRate = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[10]")).getText();
			String InsuranceTax = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[11]")).getText();
			String TotalPremium = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[12]")).getText();
			
			if(CoverName.equalsIgnoreCase("Totals")){
				Underlying_data_map.put("PS_NetNetPremiumTotal", NetNetPremium);
				Underlying_data_map.put("PS_PenCommTotal", PenCommision);
				Underlying_data_map.put("PS_NetPremiumTotal", NetPremium);
				Underlying_data_map.put("PS_BrokerCommissionTotal", BrokerCommision);
				Underlying_data_map.put("PS_Total_GP", GrossPremium);
				Underlying_data_map.put("PS_Total_GT", InsuranceTax);
				Underlying_data_map.put("PS_TotalPremium", TotalPremium);
			}else{
				if(CoverName.contains("Businesss")){
					CoverName = "BusinessInterruption";
				}
				Underlying_data_map.put("PS_"+CoverName+"_NetNetPremium", NetNetPremium);
				Underlying_data_map.put("PS_"+CoverName+"_PenComm_rate", PenCommRate);
				Underlying_data_map.put("PS_"+CoverName+"_PenComm", PenCommision);
				Underlying_data_map.put("PS_"+CoverName+"_NetPremium", NetPremium);
				Underlying_data_map.put("PS_"+CoverName+"_BrokerComm_rate", BrokerCommRate);
				Underlying_data_map.put("PS_"+CoverName+"_BrokerComm", BrokerCommision);
				Underlying_data_map.put("PS_"+CoverName+"_GrossComm_rate", GrossCommRate);
				Underlying_data_map.put("PS_"+CoverName+"_GP", GrossPremium);
				Underlying_data_map.put("PS_"+CoverName+"_IPT", InsuranceTaxRate);
				Underlying_data_map.put("PS_"+CoverName+"_GT", InsuranceTax);
				Underlying_data_map.put("PS_"+CoverName+"_TotalPremium", TotalPremium);
			
			}
		}
		
		// ---------------------------------------- Start -----------------------------------------------------------
		// Below code will be only be execute if We are doing Rewind on MTA directlly.
		
		if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
			if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type")).equalsIgnoreCase("Endorsement") && 
					((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes")){
				
				
				String Rewind_annualPremiumTablePath = "//p[text()=' Previous Premium']//following-sibling::table[1]//tbody//tr";
				List<WebElement> Rewind_listOfRows = driver.findElements(By.xpath(Rewind_annualPremiumTablePath));
				int Rewind_sizeOfAnnualPremiumTable = Rewind_listOfRows.size();
				common.NB_excel_data_map.put("PS_PolicyStartDate", startDate);
				for(int coverDetails = 1;coverDetails<=Rewind_sizeOfAnnualPremiumTable;coverDetails++){
					
					String CoverName = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[1]")).getText().replaceAll(" ", "");
					String NetNetPremium = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[2]")).getText();
					String PenCommRate = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[3]")).getText();
					String PenCommision = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[4]")).getText();
					String NetPremium = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[5]")).getText();
					String BrokerCommRate = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[6]")).getText();
					String BrokerCommision = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[7]")).getText();
					String GrossCommRate = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[8]")).getText();
					String GrossPremium = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[9]")).getText();
					String InsuranceTaxRate = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[10]")).getText();
					String InsuranceTax = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[11]")).getText();
					String TotalPremium = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[12]")).getText();
					
					if(CoverName.equalsIgnoreCase("Totals")){
						Rewind_Underlying_data_map.put("PS_NetNetPremiumTotal", NetNetPremium);
						Rewind_Underlying_data_map.put("PS_PenCommTotal", PenCommision);
						Rewind_Underlying_data_map.put("PS_NetPremiumTotal", NetPremium);
						Rewind_Underlying_data_map.put("PS_BrokerCommissionTotal", BrokerCommision);
						Rewind_Underlying_data_map.put("PS_Total_GP", GrossPremium);
						Rewind_Underlying_data_map.put("PS_Total_GT", InsuranceTax);
						Rewind_Underlying_data_map.put("PS_TotalPremium", TotalPremium);
					}else{
						if(CoverName.contains("Businesss")){
							CoverName = "BusinessInterruption";
						}
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_NetNetPremium", NetNetPremium);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_PenComm_rate", PenCommRate);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_PenComm", PenCommision);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_NetPremium", NetPremium);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_BrokerComm_rate", BrokerCommRate);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_BrokerComm", BrokerCommision);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_GrossComm_rate", GrossCommRate);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_GP", GrossPremium);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_IPT", InsuranceTaxRate);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_GT", InsuranceTax);
						Rewind_Underlying_data_map.put("PS_"+CoverName+"_TotalPremium", TotalPremium);
						if(CoverName.contains("PersonalAccident")){
							CoverName = "PersonalAccidentStandard";
						}
						if(TestBase.product.equals("GTD")){
							CoverName = "GoodsInTransit";
						}
						Rewind_Underlying_data_map.put("CD_"+CoverName, "Yes");
					}
				}
			}
		}
		
		
			
		// -------------------------------- End -------------------------------------------------
		
		// ---------------------------------------- Start -----------------------------------------------------------
			// Below code will be only be execute if We are doing Rewind on MTA directlly.
			
			if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
				
				try{
					k.ImplicitWaitOff();
					String ReinstateTable = "//p[text()=' Reinstatement Details']//following-sibling::table[1]//tbody//tr";
					try {
						JavascriptExecutor j_exe = (JavascriptExecutor) driver;
						j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(ReinstateTable)));
					
					
					WebElement ReinstateTableCheck = driver.findElement(By.xpath(ReinstateTable));
					k.ImplicitWaitOff();
					if(ReinstateTableCheck.isDisplayed()){
						isReinstateTablePresent = true;
						String Rewind_annualPremiumTablePath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr";
						List<WebElement> Rewind_listOfRows = driver.findElements(By.xpath(Rewind_annualPremiumTablePath));
						int Rewind_sizeOfAnnualPremiumTable = Rewind_listOfRows.size();
						
						for(int coverDetails = 1;coverDetails<=Rewind_sizeOfAnnualPremiumTable;coverDetails++){
							
							String CoverName = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[1]")).getText().replaceAll(" ", "");
							String PenCommRate = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[3]")).getText();
							
							if(!CoverName.equalsIgnoreCase("Totals")){
								if(CoverName.contains("Businesss")){
									CoverName = "BusinessInterruption";
								}
								Underlying_data_map.put("PS_"+CoverName+"_PenComm_rate", PenCommRate);
							}
						
						}
					}
					if(ReinstateTableCheck.isDisplayed()){
						isReinstateTablePresent = true;
						String Rewind_annualPremiumTablePath = "//p[text()=' Reinstatement Details']//following-sibling::table[1]//tbody//tr";
						List<WebElement> Rewind_listOfRows = driver.findElements(By.xpath(Rewind_annualPremiumTablePath));
						int Rewind_sizeOfAnnualPremiumTable = Rewind_listOfRows.size();
						
						for(int coverDetails = 1;coverDetails<=Rewind_sizeOfAnnualPremiumTable;coverDetails++){
							
							String CoverName = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[1]")).getText().replaceAll(" ", "");
							String PenCommRate = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[3]")).getText();
							
							if(!CoverName.equalsIgnoreCase("Totals")){
								if(CoverName.contains("Businesss")){
									CoverName = "BusinessInterruption";
								}
								Reinstate_data_map.put("PS_"+CoverName+"_PenComm_rate", PenCommRate);
							}
						
						}
					}
					}catch(NoSuchElementException nse){
						k.ImplicitWaitOn();
					}
					
				}catch(Throwable t){
					k.ImplicitWaitOn();
				}finally {
					k.ImplicitWaitOn();
				}
				
			}
				
			// -------------------------------- End -------------------------------------------------
		
		if(type.equalsIgnoreCase("Endorsement") && TestBase.businessEvent.equalsIgnoreCase("Rewind")){
			String transactionDetailsMsg_xpath = "//p[text()=' Transaction Details ']//following-sibling::p";
			WebElement transactionDetails_Msg = driver.findElement(By.xpath(transactionDetailsMsg_xpath));
			
			String text = transactionDetails_Msg.getText();
			
			String date[] = text.split(",");
			String days[] = text.split(",");
			
			Underlying_data_map.put("MTA_EffectiveDays",days[1].substring(days[1].indexOf(":")+2, days[1].indexOf("days")));
			Underlying_data_map.put("MTA_EffectiveDate",date[0].substring(date[0].indexOf(":")+2, date[0].length()));
	
		}else{
			String transactionDetailsMsg_xpath = "//p[text()=' Transaction Details ']//following-sibling::p";
			try{
				k.ImplicitWaitOff();
				try{
					WebElement transactionDetails_Msg = driver.findElement(By.xpath(transactionDetailsMsg_xpath));
					k.ImplicitWaitOn();
					String text = transactionDetails_Msg.getText();
					
					String date[] = text.split(",");
					String days[] = text.split(",");
					
					data_map.put("MTA_EffectiveDays",days[1].substring(days[1].indexOf(":")+2, days[1].indexOf("days")));
					data_map.put("MTA_EffectiveDate",date[0].substring(date[0].indexOf(":")+2, date[0].length()));
				}catch(Throwable t){
					
				}
				
			}catch(Throwable t){
				return retvalue;
			}
		}
		
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
		
		WebElement tableID = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody"));
		List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
		int size = HistoryRows.size();
		
		String EventName = tableID.findElement(By.xpath("//tr["+size+"]//td[1]//a")).getText();
		
		if(EventName.equalsIgnoreCase("New Business") || EventName.equalsIgnoreCase("Renewal")){
			
			String effectiveDate = driver.findElement(By.xpath("//*[@id='linkpanels']//following::table[1]//tbody//tr["+size+"]//td[5]")).getText();
			if(type.equalsIgnoreCase("Endorsement") && TestBase.businessEvent.equalsIgnoreCase("Rewind")){
				Rewind_Underlying_data_map.put("EffectiveDate", effectiveDate);
			}else{
				Underlying_data_map.put("EffectiveDate", effectiveDate);
			}
		}
		
		return retvalue;
	}catch(Exception e){
		e.printStackTrace();
		TestUtil.reportStatus("Update Premium Summary Data function is having issue.", "Fail", true);
		return false;
	}
}
public boolean updatePremiumSummaryData_PEN(String PolicyNumber , String duration , String type) throws AWTException, InterruptedException{
	
	Map<Object, Object> data_map = null;
	Map<Object, Object> Underlying_data_map = null;
	
	try{
		String sheetName = "" , code = "";
		switch (type) {
		case "Endorsement":
		case "New Business":
			if(TestBase.businessEvent.equalsIgnoreCase("MTA")){
				data_map = common.MTA_excel_data_map;
				Underlying_data_map = common.NB_excel_data_map;
				sheetName = "MTA";
				code="NB";
			}else if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
				if(type.equalsIgnoreCase("Endorsement")){
					data_map = common.Rewind_excel_data_map;
					Underlying_data_map = common.MTA_excel_data_map;
					Rewind_Underlying_data_map = common.NB_excel_data_map;
					sheetName = "Rewind";
					code = "MTA";
				}else{
					data_map = common.Rewind_excel_data_map;
					Underlying_data_map = common.NB_excel_data_map;
					sheetName = "Rewind";
					code = "NB";
				}
			}else if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
				data_map = common.CAN_excel_data_map;
				Underlying_data_map = common.NB_excel_data_map;
				sheetName = "CAN";
				code = "NB";
			}
			break;
		case "Renewal":
			if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
				data_map = common.CAN_excel_data_map;
				Underlying_data_map = common.Renewal_excel_data_map;
				sheetName = "CAN";
				code = "Renewal";
			}else {
				data_map = common.MTA_excel_data_map;
				Underlying_data_map = common.Renewal_excel_data_map;
				sheetName = "MTA";
			}
			break;
		}
		
		boolean retvalue = true;
	   
		String QuoteNumber = k.getText("POF_QuoteNumber");
		String startDatePath = "//td[text()='Policy Start Date (dd/mm/yyyy) ']//following::*[@id='start_date']";
		String startDate = driver.findElement(By.xpath(startDatePath)).getText();
		String endDatePath = "//td[text()='Policy End Date (dd/mm/yyyy) ']//following-sibling::td";
		String endDate = driver.findElement(By.xpath(endDatePath)).getText();
		String taxExemptionPath = "";
		String searchedPolicyDuration = driver.findElement(By.xpath("//*[@id='duration']")).getText();
		
		common.funcButtonSelection("Insurance Tax");
		taxExemptionPath = "//td[text()='Is this policy exempt from insurance tax?']//following-sibling::td//div";		
		String taxExemption = driver.findElement(By.xpath(taxExemptionPath)).getText();
		k.Click("Tax_adj_BackBtn");
		/*String policyFinacePath = "//td[text()='Is the policy financed?']//following-sibling::td//div";
		String policyFinace = driver.findElement(By.xpath(policyFinacePath)).getText();*/
		String paymentWarrentyPath = "//td[text()='Is this business conducted under Premium Payment Warranty rules?']//following-sibling::td//div";
		String paymentWarrenty = driver.findElement(By.xpath(paymentWarrentyPath)).getText();
		
		if(paymentWarrenty.equalsIgnoreCase("Yes")){
			String paymentWarrentyDueDatePath = "//td[text()='Policy Start Date (dd/mm/yyyy) ']//following-sibling::td";
			String paymentWarrentyDueDate = driver.findElement(By.xpath(startDatePath)).getText();
		//	k.getText(startDatePath)
		}
		
		Underlying_data_map.put("PS_PolicyStartDate", startDate);
		Underlying_data_map.put("PS_PolicyEndDate", endDate);
		Underlying_data_map.put(code+"_PolicyNumber", PolicyNumber);
		data_map.put("MTA_PolicyNumber", PolicyNumber);
		Underlying_data_map.put(code+"_QuoteNumber", QuoteNumber);
		data_map.put("MTA_QuoteNumber", QuoteNumber);
		Underlying_data_map.put("PS_Duration", duration);
		Underlying_data_map.put("PS_InsuranceTaxButton", taxExemption);
	//	Underlying_data_map.put("PS_IsPolicyFinanced", policyFinace);
		Underlying_data_map.put("PS_PaymentWarrantyRules", paymentWarrenty);
		data_map.put("PS_InsuranceTaxButton", taxExemption);
		/**
		 * 
		 * Store Premium Summary table values :  
		 * 
		 */
		
		String annualPremiumTablePath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr";
		List<WebElement> listOfRows = driver.findElements(By.xpath(annualPremiumTablePath));
		int sizeOfAnnualPremiumTable = listOfRows.size();
		
		for(int coverDetails = 1;coverDetails<=sizeOfAnnualPremiumTable;coverDetails++){
			
			String CoverName = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[1]")).getText().replaceAll(" ", "");
			String GrossPremium = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[2]")).getText();
			String CommRate = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[3]")).getText();
			String GrossCommision = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[4]")).getText();
			String NetPremium = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[5]")).getText();
			String GrossIPT = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[6]")).getText();
			String NetIPT = driver.findElement(By.xpath(annualPremiumTablePath+"["+coverDetails+"]//td[7]")).getText();
			
			if(CoverName.equalsIgnoreCase("Totals")){
				Underlying_data_map.put("PS_Total_GP", GrossPremium);
				Underlying_data_map.put("PS_Total_GT", GrossCommision);
				Underlying_data_map.put("PS_Total_NP", NetPremium);
				Underlying_data_map.put("PS_Total_GC", NetIPT);
				Underlying_data_map.put("PS_Total_NPIPT", GrossPremium);
			}else{
				if(CoverName.contains("Businesss")){
					CoverName = "BusinessInterruption";
				}
				if(CoverName.equals("LossofLicence")){
					CoverName = "LossOfLicence";
				}
				Underlying_data_map.put("PS_"+CoverName+"_GP", GrossPremium);
				Underlying_data_map.put("PS_"+CoverName+"_CR", CommRate);
				Underlying_data_map.put("PS_"+CoverName+"_GT", GrossCommision);
				Underlying_data_map.put("PS_"+CoverName+"_NP", NetPremium);
				Underlying_data_map.put("PS_"+CoverName+"_GC", GrossIPT);
				Underlying_data_map.put("PS_"+CoverName+"_NPIPT", NetIPT);
				double Tax_Rate = ((Double.parseDouble(GrossIPT)) / Double.parseDouble(GrossPremium)) * 100.0;
				if(Double.isNaN(Tax_Rate))
					Tax_Rate = 0.0;
				Underlying_data_map.put("PS_"+CoverName+"_IPT", Double.toString(Tax_Rate));
				
			
			}
		}
		
		// ---------------------------------------- Start -----------------------------------------------------------
		// Below code will be only be execute if We are doing Rewind on MTA directlly.
		
		if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
			if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type")).equalsIgnoreCase("Endorsement") && 
					((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes")){
				
				
				String Rewind_annualPremiumTablePath = "//p[text()=' Previous Annual Premium ']//following-sibling::table[1]//tbody//tr";
				List<WebElement> Rewind_listOfRows = driver.findElements(By.xpath(Rewind_annualPremiumTablePath));
				int Rewind_sizeOfAnnualPremiumTable = Rewind_listOfRows.size();
				common.NB_excel_data_map.put("PS_PolicyStartDate", startDate);
				for(int coverDetails = 1;coverDetails<=Rewind_sizeOfAnnualPremiumTable;coverDetails++){
					
					String SectionName = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[1]")).getText().replaceAll(" ", "");
					String NetPremium = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[5]")).getText();
					String ComRate = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[3]")).getText();
					String Commission = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[4]")).getText();
					String GrossPremium = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[2]")).getText();
					String NetIPT = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[7]")).getText();
					String GrossIPT = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[6]")).getText();
					//String TotalPremium = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[12]")).getText();
					String CoverName = "";
					if(SectionName.equalsIgnoreCase("Totals")){
						Rewind_Underlying_data_map.put("PS_Total_NP", NetPremium.replaceAll(",", ""));
						Rewind_Underlying_data_map.put("PS_Total_GC", Commission.replaceAll(",", ""));
						Rewind_Underlying_data_map.put("PS_Total_GP", GrossPremium.replaceAll(",", ""));
						Rewind_Underlying_data_map.put("PS_Total_GT", GrossIPT.replaceAll(",", ""));
						Rewind_Underlying_data_map.put("PS_Total_NPIPT", NetIPT.replaceAll(",", ""));
					}else{
						if(SectionName.contains("Businesss")){
							SectionName = "BusinessInterruption";
						}
						if(SectionName.equals("LossofLicence")){
							SectionName = "LossOfLicence";
						}
								
						Rewind_Underlying_data_map.put("PS_"+SectionName+"_GP", GrossPremium);
						Rewind_Underlying_data_map.put("PS_"+SectionName+"_CR", ComRate);
						Rewind_Underlying_data_map.put("PS_"+SectionName+"_GC", Commission);
						Rewind_Underlying_data_map.put("PS_"+SectionName+"_NP", NetPremium);
						Rewind_Underlying_data_map.put("PS_"+SectionName+"_GT", GrossIPT);
						Rewind_Underlying_data_map.put("PS_"+SectionName+"_NPIPT", NetIPT);
						double Tax_Rate = ((Double.parseDouble(GrossIPT)) / Double.parseDouble(GrossPremium)) * 100.0;
						if(Double.isNaN(Tax_Rate))
							Tax_Rate = 0.0;
						Rewind_Underlying_data_map.put("PS_"+SectionName+"_IPT", Double.toString(Tax_Rate));
						
						if(CoverName.contains("PersonalAccident")){
							CoverName = "PersonalAccidentStandard";
						}
						
						switch(SectionName) {
						
							case "FrozenFoods":
								CoverName = "FrozenFood";
								break;
							default:
								CoverName = SectionName;
								break;
							}
						
						if(!TestBase.product.equals("XOE")) {
							if(CoverName.contains("Liability"))
								CoverName = "Liability";
						}
						Rewind_Underlying_data_map.put("CD_"+CoverName, "Yes");
					}
				}
			}
			common.Rewind_excel_data_map.put("PS_PolicyStartDate", startDate);
			common.Rewind_excel_data_map.put("PS_PolicyEndDate", endDate);
			common.Rewind_excel_data_map.put("PS_InsuranceTaxButton", taxExemption);
		}
		
		
			
		// -------------------------------- End -------------------------------------------------
		
		// ---------------------------------------- Start -----------------------------------------------------------
			// Below code will be only be execute if We are doing Rewind on MTA directlly.
			
			if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
				
				try{
					k.ImplicitWaitOff();
					String ReinstateTable = "//p[text()=' Reinstatement Details']//following-sibling::table[1]//tbody//tr";
					try {
						JavascriptExecutor j_exe = (JavascriptExecutor) driver;
						j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(ReinstateTable)));
					
					
					WebElement ReinstateTableCheck = driver.findElement(By.xpath(ReinstateTable));
					k.ImplicitWaitOff();
					if(ReinstateTableCheck.isDisplayed()){
						isReinstateTablePresent = true;
						String Rewind_annualPremiumTablePath = "//p[text()='Annual Premium ']//following-sibling::table[1]//tbody//tr";
						List<WebElement> Rewind_listOfRows = driver.findElements(By.xpath(Rewind_annualPremiumTablePath));
						int Rewind_sizeOfAnnualPremiumTable = Rewind_listOfRows.size();
						
						for(int coverDetails = 1;coverDetails<=Rewind_sizeOfAnnualPremiumTable;coverDetails++){
							
							String CoverName = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[1]")).getText().replaceAll(" ", "");
							String CommRate = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[3]")).getText();
							
							if(!CoverName.equalsIgnoreCase("Totals")){
								if(CoverName.contains("Businesss")){
									CoverName = "BusinessInterruption";
								}
								if(CoverName.equals("LossofLicence")){
									CoverName = "LossOfLicence";
								}
								Underlying_data_map.put("PS_"+CoverName+"_CR", CommRate);
							}
						
						}
					}
					if(ReinstateTableCheck.isDisplayed()){
						isReinstateTablePresent = true;
						String Rewind_annualPremiumTablePath = "//p[text()=' Reinstatement Details']//following-sibling::table[1]//tbody//tr";
						List<WebElement> Rewind_listOfRows = driver.findElements(By.xpath(Rewind_annualPremiumTablePath));
						int Rewind_sizeOfAnnualPremiumTable = Rewind_listOfRows.size();
						
						for(int coverDetails = 1;coverDetails<=Rewind_sizeOfAnnualPremiumTable;coverDetails++){
							
							String CoverName = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[1]")).getText().replaceAll(" ", "");
							String CommRate = driver.findElement(By.xpath(Rewind_annualPremiumTablePath+"["+coverDetails+"]//td[3]")).getText();
							
							if(!CoverName.equalsIgnoreCase("Totals")){
								if(CoverName.contains("Businesss")){
									CoverName = "BusinessInterruption";
								}
								if(CoverName.equals("LossofLicence")){
									CoverName = "LossOfLicence";
								}
								Reinstate_data_map.put("PS_"+CoverName+"_CR", CommRate);
							}
						
						}
					}
					}catch(NoSuchElementException nse){
						k.ImplicitWaitOn();
					}
					
				}catch(Throwable t){
					k.ImplicitWaitOn();
				}finally {
					k.ImplicitWaitOn();
				}
				
				common.CAN_excel_data_map.put("PS_SearchedPolicyDuration", searchedPolicyDuration);
				
			}
				
			// -------------------------------- End -------------------------------------------------
		
		if(type.equalsIgnoreCase("Endorsement") && TestBase.businessEvent.equalsIgnoreCase("Rewind")){
			String transactionDetailsMsg_xpath = "//p[text()=' Transaction Details ']//following-sibling::p";
			WebElement transactionDetails_Msg = driver.findElement(By.xpath(transactionDetailsMsg_xpath));
			
			String text = transactionDetails_Msg.getText();
			
			String date[] = text.split(",");
			String days[] = text.split(",");
			
			Underlying_data_map.put("MTA_EffectiveDays",days[1].substring(days[1].indexOf(":")+2, days[1].indexOf("days")));
			Underlying_data_map.put("MTA_EffectiveDate",date[0].substring(date[0].indexOf(":")+2, date[0].length()));
	
		}else{
			String transactionDetailsMsg_xpath = "//p[text()=' Transaction Details ']//following-sibling::p";
			try{
				k.ImplicitWaitOff();
				try{
					WebElement transactionDetails_Msg = driver.findElement(By.xpath(transactionDetailsMsg_xpath));
					k.ImplicitWaitOn();
					String text = transactionDetails_Msg.getText();
					
					String date[] = text.split(",");
					String days[] = text.split(",");
					
					data_map.put("MTA_EffectiveDays",days[1].substring(days[1].indexOf(":")+2, days[1].indexOf("days")));
					data_map.put("MTA_EffectiveDate",date[0].substring(date[0].indexOf(":")+2, date[0].length()));
				}catch(Throwable t){
					
				}
				
			}catch(Throwable t){
				return retvalue;
			}
		}
		
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","History"),"Issue while Navigating to History . ");
		
		WebElement tableID = driver.findElement(By.xpath("//*[@id='L_is_financed']//following::table[1]//tbody"));
		List<WebElement> HistoryRows = tableID.findElements(By.tagName("tr"));
		int size = HistoryRows.size();
		
		String EventName = tableID.findElement(By.xpath("//tr["+size+"]//td[1]//a")).getText();
		
		if(EventName.equalsIgnoreCase("New Business") || EventName.equalsIgnoreCase("Renewal")){
			
			String effectiveDate = driver.findElement(By.xpath("//*[@id='L_is_financed']//following::table[1]//tbody//tr["+size+"]//td[5]")).getText();
			if(type.equalsIgnoreCase("Endorsement") && TestBase.businessEvent.equalsIgnoreCase("Rewind")){
				Rewind_Underlying_data_map.put("EffectiveDate", effectiveDate);
			}else{
				Underlying_data_map.put("EffectiveDate", effectiveDate);
			}
		}
		
		return retvalue;
	}catch(Exception e){
		e.printStackTrace();
		TestUtil.reportStatus("Update PEN Premium Summary Data function is having issue.", "Fail", true);
		return false;
	}
	}

	public String doUniCoverAP(String basexpath, int overrideColumn, int premiumColunm)
	{
		String premiumOverRide = null;
		
		try
		{
			List<WebElement> allPremiums = driver.findElements(By.xpath(basexpath));
			premiumOverRide = driver.findElement(By.xpath(basexpath+"["+allPremiums.size()+"]/td["+Integer.toString(premiumColunm)+"]/input")).getAttribute("value");
			
			do_APRP_Operation(basexpath, overrideColumn, premiumColunm, allPremiums.size(), premiumOverRide);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return premiumOverRide;
	}
	
	public String doUniCoverRP(String basexpath, int overrideColumn, int premiumColunm)
	{
		String premiumOverRide = null;
		
		try
		{
			List<WebElement> allPremiums = driver.findElements(By.xpath(basexpath));
			
			premiumOverRide = driver.findElement(By.xpath(basexpath+"["+allPremiums.size()+"]/td["+Integer.toString(premiumColunm)+"]/input")).getAttribute("value");
			premiumOverRide = Double.toString(Double.parseDouble(premiumOverRide)/allPremiums.size());
			
			do_APRP_Operation(basexpath, overrideColumn, premiumColunm, allPremiums.size(), premiumOverRide);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return premiumOverRide;
	}

	private void do_APRP_Operation(String basexpath, int overrideColumn, int premiumColunm, int totalPremiumRows, String premiumOverRide)
	{
		for(int i =1; i< totalPremiumRows; i++)
		{	
			String premiumXpath = basexpath+"["+i+"]/td["+Integer.toString(premiumColunm)+"]/input";
			String premOverrideXpath = basexpath+"["+i+"]/td["+Integer.toString(overrideColumn)+"]/input";
			
			if(!driver.findElement(By.xpath(premiumXpath)).getAttribute("value").equalsIgnoreCase("0"))
			{
				driver.findElement(By.xpath(premOverrideXpath)).clear();
				driver.findElement(By.xpath(premOverrideXpath)).sendKeys(premiumOverRide);
				if(!TestBase.product.equalsIgnoreCase("GTC") && !TestBase.product.equalsIgnoreCase("GTD"))
				{
					driver.findElement(By.xpath("//*[contains(@id,'book-rates')]")).click();
				}
				else
				{
					driver.findElement(By.xpath("//*[contains(@id,'apply-rows-cce')]")).click();
				}
			}
		}
	}

public boolean coverDetailsUpdation(String type , String status) throws AWTException, InterruptedException{
	
	Map<Object, Object> data_map = null;
	Map<Object, Object> Underlying_data_map = null;
	String sheetName = "" , code="";
	switch (type) {
	case "Endorsement":
	case "New Business":
		if(TestBase.businessEvent.equalsIgnoreCase("MTA")){
			data_map = common.MTA_excel_data_map;
			Underlying_data_map = common.NB_excel_data_map;
			sheetName = "MTA";
		}else if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
			if(type.equalsIgnoreCase("Endorsement")){
				data_map = common.Rewind_excel_data_map;
				Underlying_data_map = common.MTA_excel_data_map;
				sheetName = "Rewind";
				code = "MTA";
			}else{
				data_map = common.Rewind_excel_data_map;
				Underlying_data_map = common.NB_excel_data_map;
				sheetName = "Rewind";
				code = "NB";
			}
		}else if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
			data_map = common.CAN_excel_data_map;
			Underlying_data_map = common.NB_excel_data_map;
			sheetName = "CAN";
			code = "NB";
		}
		break;
	case "Renewal":
		if(TestBase.businessEvent.equalsIgnoreCase("CAN")){
			data_map = common.CAN_excel_data_map;
			Underlying_data_map = common.Renewal_excel_data_map;
			sheetName = "CAN";
			code = "Renewal";
		}else {
			data_map = common.MTA_excel_data_map;
			Underlying_data_map = common.Renewal_excel_data_map;
			sheetName = "MTA";
		}
		break;
	}
	
	boolean retvalue = true;
 	
	if(CommonFunction.product.equalsIgnoreCase("XOE")){
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Additional Covers"),"Cover page is having issue(S)");
	}else{
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Covers"),"Issue while Navigating to Covers screen . ");
	}
	k.ImplicitWaitOff();
	try{
		WebElement CoverDetailsTable = driver.findElement(By.xpath("//*[@id='main']/form/div//following::table//tbody"));
		List<WebElement> rows = CoverDetailsTable.findElements(By.tagName("tr"));
		
		if(rows.size()>0){
			
			for(int i = 1; i < rows.size(); i++ ) {
				
				String cover = driver.findElement(By.xpath("//*[@id='main']/form/div//following::table//tbody//tr["+(i+1)+"]//td[1]")).getText();
				if(!cover.equalsIgnoreCase("")){
					String coverValue = CoverDetailsTable.findElement(By.xpath("//tr["+(i+1)+"]//td[2]/img")).getAttribute("alt");
					if(cover.contains("Commercial Vehicle"))
						cover = "Commercial Vehicles";
					Underlying_data_map.put("CD_"+cover.replaceAll(" ", ""), coverValue);
					String coverData = "";
					
					if(TestBase.businessEvent.equalsIgnoreCase("Rewind") && ((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type")).equalsIgnoreCase("Endorsement")){
						coverData = (String)common_EP.Rewind_Underlying_data_map.get("CD_"+cover.replaceAll(" ", ""));
					}
					
					if(coverData==null){
						common_EP.Rewind_Underlying_data_map.put("CD_"+cover.replaceAll(" ", ""), "No");
						if(cover.replaceAll(" ", "").equalsIgnoreCase("MaterialDamage")) {
							if(TestBase.product.equals("CCF") || TestBase.product.equals("CCG") || TestBase.product.equals("CTA")) {
								common_EP.Rewind_Underlying_data_map.put("CD_BusinessInterruption", "No");
							}
						}
					}
				}
				
			}
			
			switch (type) {
			case "Endorsement":
			case "New Business":
				if(TestBase.businessEvent.equalsIgnoreCase("MTA")){
					common.NB_excel_data_map.putAll(Underlying_data_map);
				}else if(TestBase.businessEvent.equalsIgnoreCase("Rewind")){
					if(type.equalsIgnoreCase("Endorsement")){
						common.MTA_excel_data_map.putAll(Underlying_data_map);
					}else{
						common.NB_excel_data_map.putAll(Underlying_data_map);
					}
				}
				break;
			case "Renewal":
				common.Renewal_excel_data_map.putAll(Underlying_data_map);
				break;
			}
			
			TestUtil.reportStatus("Cover details are updated successfully under Coverdetails sheet for underlying event.", "Info", true);
			return retvalue;
		}else{
			TestUtil.reportStatus("No policies are present with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ] </b>", "Fail", true);
			return false;
		}
	}catch(Throwable t){
		TestUtil.reportStatus("Covers updation function is having issue.", "Fail", true);
		return false;
	}
   
}

public String get_First_Cover(String type , String status , String selected_unselected_value) throws AWTException, InterruptedException{
	
	customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Covers"),"Issue while Navigating to Covers screen . ");
	k.ImplicitWaitOff();
	try{
		WebElement CoverDetailsTable = driver.findElement(By.xpath("//*[@id='main']/form/div//following::table//tbody"));
		List<WebElement> rows = CoverDetailsTable.findElements(By.tagName("tr"));
		int sel_count = 0,un_sel_count = 0;
		if(rows.size()>0){
			
			for(int i = 1; i < rows.size(); i++ ) {
				
				String cover = driver.findElement(By.xpath("//*[@id='main']/form/div//following::table//tbody//tr["+(i+1)+"]//td[1]")).getText();
				if(!cover.equalsIgnoreCase("")){
					
					String coverValue = CoverDetailsTable.findElement(By.xpath("//tr["+(i+1)+"]//td[2]/img")).getAttribute("alt");
					
					if(cover.contains("Commercial Vehicle"))
						cover = "Commercial Vehicles";
					
					if(coverValue.equalsIgnoreCase("Yes")) {
						sel_count++;
					}
					if(coverValue.equalsIgnoreCase("No")) {
						un_sel_count++;
					}
					switch(selected_unselected_value) {
					
						case "Selected":
							if(sel_count == 1 && coverValue.equalsIgnoreCase("Yes")) {
								return cover.replaceAll(" ", "");
							}
							break;
						case "Un-Selected":
							if(un_sel_count == 1 && coverValue.equalsIgnoreCase("No")) {
								return cover.replaceAll(" ", "");
							}
							break;
					}
					
					
					
				}
			}
		}
			
	}catch(Throwable t){
		TestUtil.reportStatus("Get First Cover function is having issue while getting first >"+selected_unselected_value+"< cover .", "Fail", true);
		return "Error";
	}
	return "";
  
}

public String get_Selected_Cover_For_XOE(String type , String status){
	
	k.ImplicitWaitOff();
	try{
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker("Navigate","Additional Covers"),"Issue while Navigating to Additional Covers screen . ");
		String[] xoe_covers = {"Property Excess of Loss","Terrorism"};
		int X = (int)(Math.random()*2);
		if(common.funcCheckMenuSubMenuPresent("Navigate","Terrorism")) {
			return xoe_covers[X].replaceAll(" ", "");
		}else {
			return xoe_covers[0].replaceAll(" ", "");
		}
			
	}catch(Throwable t){
		TestUtil.reportStatus("Get selected Cover for XOE is having issue  .", "Fail", true);
		return "Error";
	}
  
}
/**
 * 
 * This method updates cover's Yes/No status for AP/RP operation to MTA data map ."
 * 
 *
 */

public boolean UpdateCoverDetails_To_Map(String type , String status) throws AWTException, InterruptedException{
	
	boolean retvalue = true;
	int selected_cover_count=0;
	String Policy_level_Operation_cover = null;
	
	customAssert.assertTrue(common.funcPageNavigation("Covers", ""),"Covers page navigations issue(S)");
	k.ImplicitWaitOff();
	try{
		WebElement CoverDetailsTable = driver.findElement(By.xpath("//*[@id='main']/form/div//following::table//tbody"));
		List<WebElement> rows = CoverDetailsTable.findElements(By.tagName("tr"));
		
		if(rows.size()>0){
			
			for(int i = 1; i < rows.size(); i++ ) {
				
				String cover = driver.findElement(By.xpath("//*[@id='main']/form/div//following::table//tbody//tr["+(i+1)+"]//td[1]")).getText();
				if(!cover.equalsIgnoreCase("")){
					String coverValue = CoverDetailsTable.findElement(By.xpath("//tr["+(i+1)+"]//td[2]/img")).getAttribute("alt");
					if(cover.contains("Commercial Vehicle"))
						cover = "Commercial Vehicles";
					common.MTA_excel_data_map.put("CD_"+cover.replaceAll(" ", ""), coverValue);
					if(coverValue.equalsIgnoreCase("Yes"))
						selected_cover_count++;
				}
				
			}
			
			if(((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("Policy-level")) {
				
				TestUtil.reportStatus("<b> Policy level AP/RP will be performed on the searched existing policy as a part of an endorsement  . </b>", "Info", true);
				switch(selected_cover_count) {
					case 1:
					case 2:
					case 3:
					case 4:
						Policy_level_Operation_cover = get_First_Cover(type , status,"Un-Selected"); //Get First Un-Selected cover to select
						common.MTA_excel_data_map.put("CD_"+Policy_level_Operation_cover.replaceAll(" ", ""), "Yes");
						TestUtil.reportStatus("Cover: [<b> "+Policy_level_Operation_cover+" </b>] will be added to the policy to perform policy level endorsements .", "Info", true);
						break;
					default:
						String[] mta_operations = {"Add_Cover","Remove_Cover"};
						int X = (int)(Math.random()*2);
						switch(mta_operations[X]) {
							case "Add_Cover":
								Policy_level_Operation_cover = get_First_Cover(type , status,"Un-Selected");
								common.MTA_excel_data_map.put("CD_"+Policy_level_Operation_cover.replaceAll(" ", ""), "Yes");
								TestUtil.reportStatus("Cover: [<b> "+Policy_level_Operation_cover+" </b>] will be added to the policy to perform policy level endorsements .", "Info", true);
								break;
							case "Remove_Cover":
								Policy_level_Operation_cover = get_First_Cover(type , status,"Selected");
								common.MTA_excel_data_map.put("CD_"+Policy_level_Operation_cover.replaceAll(" ", ""), "No");
								if(Policy_level_Operation_cover.replaceAll(" ", "").equalsIgnoreCase("PublicLiability")) {
									common.MTA_excel_data_map.put("CD_PollutionLiability(suddenandunforeseen)", "No");
									common.MTA_excel_data_map.put("CD_ProductsLiability", "No");
								}
								if(Policy_level_Operation_cover.replaceAll(" ", "").equalsIgnoreCase("EmployersLiability")) {
									common.MTA_excel_data_map.put("CD_PersonalAccidentStandard", "No");
									common.MTA_excel_data_map.put("CD_PersonalAccidentOptional", "No");
									common.MTA_excel_data_map.put("CD_LegalExpenses", "Yes");
									
								}
								TestUtil.reportStatus("Cover: [<b> "+Policy_level_Operation_cover+" </b>] will be removed from the policy to perform policy level endorsements .", "Info", true);
								break;
						}
						
						break;
				}
			}
			
				
			//TestUtil.reportStatus("Cover details are updated successfully to data Map .", "Info", true);
			return retvalue;
		}else{
			TestUtil.reportStatus("No policies are present with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ] </b>", "Fail", true);
			return false;
		}
	}catch(Throwable t){
		TestUtil.reportStatus("Covers updation to data Map function is having issue.", "Fail", true);
		return false;
	}
   
}

/**
 * 
 * This method updates cover's Yes/No status for AP/RP operation to MTA data map for PEN products ."
 * 
 *
 */

public boolean UpdateCoverDetails_To_Map_PEN(String type , String status) throws AWTException, InterruptedException{
	
	boolean retvalue = true;
	int selected_cover_count=0;
	String Policy_level_Operation_cover = null;
	
	if(CommonFunction.product.equalsIgnoreCase("XOE")){
		customAssert.assertTrue(common.funcPageNavigation("Additional Covers", ""),"Cover page is having issue(S)");
	}else{
		customAssert.assertTrue(common.funcPageNavigation("Covers", ""),"Covers page navigations issue(S)");
	}
	
	k.ImplicitWaitOff();
	try{
		WebElement CoverDetailsTable = driver.findElement(By.xpath("//*[@id='main']/form/div//following::table//tbody"));
		List<WebElement> rows = CoverDetailsTable.findElements(By.tagName("tr"));
		
		if(rows.size()>0){
			
			for(int i = 1; i < rows.size(); i++ ) {
				
				String cover = driver.findElement(By.xpath("//*[@id='main']/form/div//following::table//tbody//tr["+(i+1)+"]//td[1]")).getText();
				if(!cover.equalsIgnoreCase("")){
					String coverValue = CoverDetailsTable.findElement(By.xpath("//tr["+(i+1)+"]//td[2]/img")).getAttribute("alt");
					
					if(TestBase.product.equalsIgnoreCase("CCF") || TestBase.product.equalsIgnoreCase("CCG") || TestBase.product.equalsIgnoreCase("CTA")) {
						if(cover.equalsIgnoreCase("Material Damage") && coverValue.equalsIgnoreCase("No"))
							common.MTA_excel_data_map.put("CD_BusinessInterruption", "No");
						else
							common.MTA_excel_data_map.put("CD_"+cover.replaceAll(" ", ""), coverValue);
					}else if(TestBase.product.equalsIgnoreCase("POB") || TestBase.product.equalsIgnoreCase("POC")) {
						if(cover.equalsIgnoreCase("Material Damage") && coverValue.equalsIgnoreCase("No"))
							common.MTA_excel_data_map.put("CD_LossOfRentalIncome", "No");
						else
							common.MTA_excel_data_map.put("CD_"+cover.replaceAll(" ", ""), coverValue);
					}else{
						if(TestBase.product.equals("XOE")) {
							common.MTA_excel_data_map.put("CD_"+cover.replaceAll(" ", ""), coverValue);
							common.MTA_excel_data_map.put("CD_PropertyExcessofLoss", "Yes");
						}else {
							common.MTA_excel_data_map.put("CD_"+cover.replaceAll(" ", ""), coverValue);
						}
					}
						
					if(coverValue.equalsIgnoreCase("Yes"))
						selected_cover_count++;
				}
				
			}
			
			if(TestBase.product.equals("XOE") && selected_cover_count!=0)
				selected_cover_count = selected_cover_count - 2;
				
			
			if(((String)common.MTA_excel_data_map.get("MTA_Operation")).equals("Policy-level")) {
				
				switch(selected_cover_count) {
					case 0:
					case 1:
					case 2:
					case 3:
					case 4:
						if(TestBase.product.equals("XOE")) {
							Policy_level_Operation_cover = "Terrorism"; //Get First Un-Selected cover to select
						}else {
							Policy_level_Operation_cover = get_First_Cover(type , status,"Un-Selected"); //Get First Un-Selected cover to select
						}
						common.MTA_excel_data_map.put("CD_"+Policy_level_Operation_cover.replaceAll(" ", ""), "Yes");
						TestUtil.reportStatus("<b> Policy level AP will be performed on the searched existing policy as a part of an endorsement  . </b>", "Info", true);
						TestUtil.reportStatus("Cover: [<b> "+Policy_level_Operation_cover+" </b>] will be added to the policy to perform policy level endorsements .", "Info", true);
			
						break;
					default:
						String[] mta_operations = {"Add_Cover","Remove_Cover"};
						int X = (int)(Math.random()*2);
						
						if(TestBase.product.equals("XOE"))
								mta_operations[X] = "Remove_Cover";
						
						switch(mta_operations[X]) {
							case "Add_Cover":
								Policy_level_Operation_cover = get_First_Cover(type , status,"Un-Selected");
								
								if(Policy_level_Operation_cover.equals("")) { // If All Covers are selected then switch to remove cover logic
									
									Policy_level_Operation_cover = get_First_Cover(type , status,"Selected");
									common.MTA_excel_data_map.put("CD_"+Policy_level_Operation_cover.replaceAll(" ", ""), "No");
									if(Policy_level_Operation_cover.replaceAll(" ", "").equalsIgnoreCase("MaterialDamage") && !TestBase.product.equals("POB") && !TestBase.product.equals("POC")) {
										common.MTA_excel_data_map.put("CD_BusinessInterruption", "No"); // For CCF/CCG/CTA
									}else if(Policy_level_Operation_cover.replaceAll(" ", "").equalsIgnoreCase("MaterialDamage")) { 
										common.MTA_excel_data_map.put("CD_LossOfRentalIncome", "No"); // For POB/POC
									}
									TestUtil.reportStatus("<b> Policy level RP will be performed on the searched existing policy as a part of an endorsement  . </b>", "Info", true);
									TestUtil.reportStatus("Cover: [<b> "+Policy_level_Operation_cover+" </b>] will be removed from the policy to perform policy level endorsements .", "Info", true);
									
								}else {
									
									common.MTA_excel_data_map.put("CD_"+Policy_level_Operation_cover.replaceAll(" ", ""), "Yes");
									TestUtil.reportStatus("<b> Policy level AP will be performed on the searched existing policy as a part of an endorsement  . </b>", "Info", true);
									TestUtil.reportStatus("Cover: [<b> "+Policy_level_Operation_cover+" </b>] will be added to the policy to perform policy level endorsements .", "Info", true);
									
								}
								break;
							case "Remove_Cover":
								
								if(TestBase.product.equals("XOE")) {
									Policy_level_Operation_cover = "Terrorism"; //Remove Terrorism for XOE
									common.MTA_excel_data_map.put("CD_"+Policy_level_Operation_cover.replaceAll(" ", ""), "No");
								}else {
									Policy_level_Operation_cover = get_First_Cover(type , status,"Selected");
									common.MTA_excel_data_map.put("CD_"+Policy_level_Operation_cover.replaceAll(" ", ""), "No");
									if(Policy_level_Operation_cover.replaceAll(" ", "").equalsIgnoreCase("MaterialDamage") && !TestBase.product.equals("POB") && !TestBase.product.equals("POC")) {
										common.MTA_excel_data_map.put("CD_BusinessInterruption", "No"); // For CCF/CCG/CTA
									}else if(Policy_level_Operation_cover.replaceAll(" ", "").equalsIgnoreCase("MaterialDamage")) { 
										common.MTA_excel_data_map.put("CD_LossOfRentalIncome", "No"); // For POB/POC
									}
								}
								TestUtil.reportStatus("<b> Policy level RP will be performed on the searched existing policy as a part of an endorsement  . </b>", "Info", true);
								TestUtil.reportStatus("Cover: [<b> "+Policy_level_Operation_cover+" </b>] will be removed from the policy to perform policy level endorsements .", "Info", true);
			
								break;
						}
						
						break;
				}
			}
			
				
			//TestUtil.reportStatus("Cover details are updated successfully to data Map .", "Info", true);
			return retvalue;
		}else{
			TestUtil.reportStatus("No policies are present with Status as <b> [ "+type+" & "+status+" ] </b> for product <b> [ "+TestBase.product+" ] </b>", "Fail", true);
			return false;
		}
	}catch(Throwable t){
		TestUtil.reportStatus("Covers updation to data Map function is having issue.", "Fail", true);
		return false;
	}
   
}


//End of CommonFunction_Existing_Policy	
}
