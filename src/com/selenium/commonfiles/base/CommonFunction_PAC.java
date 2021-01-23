package com.selenium.commonfiles.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.selenium.commonfiles.util.ErrorInTestMethod;
import com.selenium.commonfiles.util.TestUtil;

public class CommonFunction_PAC extends TestBase
{	
	Properties PAC_Rater = new Properties();
	public boolean isMTARewindFlow=false,isFPEntries=false,isMTARewindStarted=false, isNBRewindStarted = false, isNBRquoteStarted = false;
	public int actual_no_of_years=0,err_count=0,trans_error_val=0 , Can_returnP_Error=0;
	
	public void NewBusinessFlow(String code,String event) throws ErrorInTestMethod
	{
		String testName = (String)common.NB_excel_data_map.get("Automation Key");
		String navigationBy = CONFIG.getProperty("NavigationBy");
		common.currentRunningFlow = "NB";
		try
		{			
			customAssert.assertTrue(common.StingrayLogin("PEN"),"Unable to login.");
			customAssert.assertTrue(common.checkClient(common.NB_excel_data_map,code,event),"Unable to check Client.");
			customAssert.assertTrue(common.createNewQuote(common.NB_excel_data_map,code,event), "Unable to create new quote.");
			customAssert.assertTrue(common.selectLatestQuote(common.NB_excel_data_map,code,event), "Unable to select quote from table.");
			
			
			customAssert.assertTrue(funcPolicyGeneral(common.NB_excel_data_map,code,event), "Policy Details function having issue .");
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts & Declarations"),"Issue while Navigating to Material Facts and Declarations . ");
			customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Loss of Licence"),"Issue while Navigating to Loss of Licence Cover page ");
			customAssert.assertTrue(funcLossOfLicence(common.NB_excel_data_map), "Loss of Licence is having issue(S) .");
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
			customAssert.assertTrue(common_HHAZ.funcPremiumSummary(common.NB_excel_data_map,code,event), "Premium Summary function is having issue(S) . ");
			
			Assert.assertTrue(common_HHAZ.funcStatusHandling(common.NB_excel_data_map,code,"NB"));
	
			if(TestBase.businessEvent.equals("NB"))
			{				
				customAssert.assertEquals(err_count,0,"Errors in premium calculations . ");
				customAssert.assertEquals(trans_error_val,0,"Errors in Transaction premium calculations . ");
				customAssert.assertEquals(common.final_err_pdf_count,0,"Verification Errors in PDF Documents . ");
				customAssert.assertTrue(common.StingrayLogout(), "Unable to Logout.");
				TestUtil.reportTestCasePassed(testName);				
			}
			 
		}
		catch(Throwable t)
		{
			TestUtil.reportTestCaseFailed(testName, t);
			throw new ErrorInTestMethod(t.getMessage());
		}		
	}
	
	public void RewindFlow(String code,String event) throws ErrorInTestMethod
	{
		String testName = (String)common.Rewind_excel_data_map.get("Automation Key");
		try
		{			
			if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes")) 
			{
				customAssert.assertTrue(common_EP.ExistingPolicyAlgorithm(common.Rewind_excel_data_map,(String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type"), (String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Status")), "Existing Policy Algorithm function is having issues. ");
			}
			else 
			{
				CommonFunction_HHAZ.AdjustedTaxDetails.clear();
				if(!common.currentRunningFlow.equalsIgnoreCase("Renewal") && !common.currentRunningFlow.equalsIgnoreCase("MTA"))
				{
					NewBusinessFlow(code,"NB");
				}
				common_HHAZ.PremiumFlag = false;
			}
			
			common_HHAZ.CoversDetails_data_list = new ArrayList<String>();
			common_HHAZ.CoversDetails_data_list.add("LossofLicence");
			
			common.currentRunningFlow="Rewind";
			String navigationBy = CONFIG.getProperty("NavigationBy");
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
			customAssert.assertTrue(common.funcRewind());
			
			TestUtil.reportStatus("<b> -----------------------Rewind flow started---------------------- </b>", "Info", false);
			
			if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type")).equalsIgnoreCase("Endorsement") && ((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes"))
			{
				customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
				customAssert.assertTrue(common.funcSearchPolicy(common.MTA_excel_data_map), "Policy Search function is having issue(S) . ");
				customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Rewind_excel_data_map,code,event,"Endorsement Submitted (Rewind)"), "Verify Policy Status (Endorsement Submitted (Rewind)) function is having issue(s).");
			}
			else
			{
				customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
				customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
				if(TestBase.businessEvent.equalsIgnoreCase("MTA"))
				{
					customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Rewind_excel_data_map,code,event,"Endorsement Submitted (Rewind)"), "Verify Policy Status (Endorsement Submitted (Rewind)) function is having issue(s).");
				}
				else
				{
					customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Rewind_excel_data_map,code,event,"Submitted (Rewind)"), "Verify Policy Status (Submitted (Rewind)) function is having issue(s).");
				}
			}
			
			customAssert.assertTrue(funcPolicyGeneral(common.Rewind_excel_data_map,code,event), "Policy Details function having issue .");
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts & Declarations"),"Issue while Navigating to Material Facts and Declarations . ");
			customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
			
			// Loss of Licence page - funcLossOfLicence
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Loss of Licence"),"Issue while Navigating to Loss of Licence Cover page ");
			customAssert.assertTrue(funcLossOfLicence(common.Rewind_excel_data_map), "Loss of Licence is having issue(S) .");
					
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		
			if(TestBase.businessEvent.equalsIgnoreCase("MTA"))
			{
				customAssert.assertTrue(common_HHAZ.funcPremiumSummary_MTA(common.Rewind_excel_data_map,code,event), "Rewind MTA Premium Summary in function is having issue(S) . ");
			}
			else if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type")).equalsIgnoreCase("Endorsement") && ((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes"))
			{
				customAssert.assertTrue(common_HHAZ.funcPremiumSummary_MTA(common.Rewind_excel_data_map,code,event), "Rewind MTA Premium Summary in function is having issue(S) . ");
			}
			else
			{
				customAssert.assertTrue(common_HHAZ.funcPremiumSummary(common.Rewind_excel_data_map,code,event), "Premium Summary function is having issue(S) . ");
			}
			
			if(!TestBase.businessEvent.equalsIgnoreCase("Renewal") && !TestBase.businessEvent.equalsIgnoreCase("MTA"))
			{
				customAssert.assertTrue(common_HHAZ.funcStatusHandling(common.Rewind_excel_data_map,code,event));
			}
			
			if(TestBase.businessEvent.equals("Rewind"))
			{				
				customAssert.assertEquals(err_count,0,"Errors in premium calculations . ");
				customAssert.assertEquals(trans_error_val,0,"Errors in Transaction premium calculations . ");
				customAssert.assertEquals(common.final_err_pdf_count,0,"Varification Errors in PDF Documents . ");
				customAssert.assertTrue(common.StingrayLogout(), "Unable to Logout.");
				TestUtil.reportTestCasePassed(testName);				
			}
		}
		catch(Throwable t)
		{
			TestUtil.reportTestCaseFailed(testName, t);
			throw new ErrorInTestMethod(t.getMessage());
		}
	}

	public void MTAFlow(String code,String fileName) throws ErrorInTestMethod
	{
		String testName = (String)common.MTA_excel_data_map.get("Automation Key");
		try
		{
			CommonFunction_HHAZ.AdjustedTaxDetails.clear();
			if(((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")) 
			{
				common_EP.ExistingPolicyAlgorithm(common.MTA_excel_data_map , (String)common.MTA_excel_data_map.get("MTA_ExistingPolicy_Type"), (String)common.MTA_excel_data_map.get("MTA_ExistingPolicy_Status"));
			}
			else 
			{
				if(!common.currentRunningFlow.equalsIgnoreCase("Renewal"))
				{
					NewBusinessFlow(code,"NB");
				}
				common_CCD.MD_Building_Occupancies_list.clear();
				common_HHAZ.PremiumFlag = false;
			}
			
			common.currentRunningFlow="MTA";
			String navigationBy = CONFIG.getProperty("NavigationBy");
			common_HHAZ.CoversDetails_data_list = new ArrayList<String>();
			common_HHAZ.CoversDetails_data_list.add("LossofLicence");
			
			customAssert.assertTrue(common_CCD.funcCreateEndorsement(),"Error in Create Endorsement function . ");
			
			customAssert.assertTrue(funcPolicyGeneral(common.MTA_excel_data_map,code,"MTA"), "Policy Details function having issue .");
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts & Declarations"),"Issue while Navigating to Material Facts and Declarations . ");
			customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
			
			// Loss of Licence page - funcLossOfLicence
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Loss of Licence"),"Issue while Navigating to Loss of Licence Cover page ");
			customAssert.assertTrue(funcLossOfLicence(common.MTA_excel_data_map), "Loss of Licence is having issue(S) .");
		
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
			customAssert.assertTrue(common_HHAZ.funcPremiumSummary_MTA(common.MTA_excel_data_map,code,"MTA"), "Premium Summary function is having issue(S) . ");
			
			if(!TestBase.businessEvent.equalsIgnoreCase("Renewal"))
			{
				Assert.assertTrue(common_HHAZ.funcStatusHandling(common.MTA_excel_data_map,code,"MTA"));
				customAssert.assertEquals(err_count,0,"Errors in premium calculations . ");
				customAssert.assertEquals(trans_error_val,0,"Errors in Transaction premium calculations . ");
				customAssert.assertEquals(common.final_err_pdf_count,0,"Verification Errors in PDF Documents . ");
				
				customAssert.assertTrue(common.StingrayLogout(), "Unable to Logout.");
			
				TestUtil.reportTestCasePassed(testName);
			}
			
		}
		catch (ErrorInTestMethod e) 
		{
			//System.out.println("Error in New Business test method for MTA > "+testName);
			throw e;
		}
		catch(Throwable t)
		{
			TestUtil.reportTestCaseFailed(testName, t);
			throw new ErrorInTestMethod(t.getMessage());
		}
	}
	
	public void CancellationFlow(String code,String event) throws ErrorInTestMethod
	{
		common_HHAZ.cancellationProcess(code,event);
	}
	
	public void RenewalFlow(String code,String fileName) throws ErrorInTestMethod
	{
		String testName = (String)common.Renewal_excel_data_map.get("Automation Key");
		try
		{
			common.currentRunningFlow="Renewal";
			String navigationBy = CONFIG.getProperty("NavigationBy");
		
			customAssert.assertTrue(common.StingrayLogin("PEN"),"Unable to login.");
			customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
			customAssert.assertTrue(common_POB.renewalSearchPolicyNEW(common.Renewal_excel_data_map), "Policy Search function is having issue(S) . ");
			customAssert.assertTrue(common.funcVerifyPolicyStatus_Renewal(common.Renewal_excel_data_map,CommonFunction.product,CommonFunction.businessEvent,"Renewal Pending"), "Verify Policy Status (Submitted (Rewind)) function is having issue(S) . ");
			customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"));
			customAssert.assertTrue(common.funcAssignUnderWriter((String)common.Renewal_excel_data_map.get("Renewal_AssignUnderwritter")));
			customAssert.assertTrue(common.funcButtonSelection("Send to Underwriter"));
			customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
			customAssert.assertTrue(common.funcSearchPolicy_Renewal(common.Renewal_excel_data_map), "Policy Search function is having issue(S) . ");
			customAssert.assertTrue(common.funcVerifyPolicyStatus_Renewal(common.Renewal_excel_data_map,CommonFunction.product,CommonFunction.businessEvent,"Renewal Submitted"), "Verify Policy Status (Renewal Submitted) function is having issue(S) . ");
			
			
			customAssert.assertTrue(funcPolicyGeneral(common.Renewal_excel_data_map,code,"Renewal"), "Policy Details function having issue .");
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts & Declarations"),"Issue while Navigating to Material Facts and Declarations . ");
			customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
			
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Loss of Licence"),"Issue while Navigating to Loss of Licence Cover page ");
			customAssert.assertTrue(funcLossOfLicence(common.Renewal_excel_data_map), "Loss of Licence function is having issue(S) .");
			
			//  Premium Summary
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
			customAssert.assertTrue(common_HHAZ.funcPremiumSummary(common.Renewal_excel_data_map,code,"Renewal"), "Premium Summary function is having issue(S) . ");
			Assert.assertTrue(common_HHAZ.funcStatusHandling(common.Renewal_excel_data_map,code,"Renewal"));
		
			TestUtil.reportTestCasePassed(testName);
		
		}
		catch (ErrorInTestMethod e) 
		{
			System.out.println("Error in New Business test method for MTA > "+testName);
			throw e;
		}
		catch(Throwable t)
		{
			TestUtil.reportTestCaseFailed(testName, t);
			throw new ErrorInTestMethod(t.getMessage());
		}	
	}
	
	public void RenewalRewindFlow(String code,String event) throws ErrorInTestMethod
	{
		String testName = (String)common.Rewind_excel_data_map.get("Automation Key");
		try
		{			
			common.currentRunningFlow="Rewind";
			//common_HHAZ.CoversDetails_data_list.clear();
			String navigationBy = CONFIG.getProperty("NavigationBy");
			common_HHAZ.PremiumFlag = false;
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
			customAssert.assertTrue(common.funcRewind());
			
			TestUtil.reportStatus("<b> -----------------------Renewal Rewind flow started---------------------- </b>", "Info", false);
			
			customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
			customAssert.assertTrue(common.funcSearchPolicy(common.Renewal_excel_data_map), "Policy Search function is having issue(S) . ");
			customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Rewind_excel_data_map,code,event,"Renewal Submitted (Rewind)"), "Verify Policy Status (Submitted (Rewind)) function is having issue(S) . ");
			
			customAssert.assertTrue(funcPolicyGeneral(common.Rewind_excel_data_map,code,event), "Policy Details function having issue .");
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts & Declarations"),"Issue while Navigating to Material Facts & Declarations.");
			customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
			
			// Loss of Licence page - funcLossOfLicence
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Loss of Licence"),"Issue while Navigating to Loss of Licence Cover page ");
			customAssert.assertTrue(funcLossOfLicence(common.Rewind_excel_data_map), "Loss of Licence is having issue(S) .");
			
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
			customAssert.assertTrue(common_HHAZ.funcPremiumSummary(common.Rewind_excel_data_map,code,event), "Premium Summary function is having issue(S) . ");
			
		}
		catch(Throwable t)
		{
			TestUtil.reportTestCaseFailed(testName, t);
			throw new ErrorInTestMethod(t.getMessage());
		}
	}

	
	public boolean funcPolicyGeneral(Map<Object, Object> map_data, String code, String event) 
	{
		boolean retVal = true;		
		try
		{
			customAssert.assertTrue(common.funcPageNavigation("Policy General", ""),"Policy General page not loaded");
			customAssert.assertTrue(k.Input("COB_PG_InsuredName", (String)map_data.get("PG_InsuredName")),	"Unable to enter value in Insured Name  field .");
			customAssert.assertTrue(!k.getAttributeIsEmpty("COB_PG_InsuredName", "value"),"Insured Name Field Should Contain Valid Name  .");
			
			customAssert.assertTrue(k.Input("DOB_PG_BusinessYear", (String)map_data.get("PG_BusinessYear")), "Unable to enter value in Business Established Year field .");
			customAssert.assertTrue(k.Input("COB_PG_TurnOver", (String)map_data.get("PG_TurnOver")),	"Unable to enter value in Turnover field .");
			
			customAssert.assertTrue(k.Input("CCF_Address_CC_Address", (String) map_data.get("PG_Address")),"Unable to enter value in Address field. ");
			customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_Address_CC_Address", "value"),"Address Field Should Contain Valid Address  .");
			customAssert.assertTrue(k.Input("CCF_Address_CC_line2", (String) map_data.get("PG_Line1")),"Unable to enter value in Address field line 1 . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_line3", (String) map_data.get("PG_Line2")),"Unable to enter value in Address field line 2 . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_Town", (String) map_data.get("PG_Town")),"Unable to enter value in Town field . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_County", (String) map_data.get("PG_County")),"Unable to enter value in County  . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_Postcode", (String)map_data.get("PG_Postcode")),"Unable to enter value in PostCode");
			customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_Address_CC_Postcode", "value"),"PostCode Field Should Contain Valid Postcode  .");
			customAssert.assertTrue(common.validatePostCode((String)map_data.get("PG_Postcode")),"Post Code is not in Correct format .");
			
			customAssert.assertTrue(k.Input("COB_PG_BusDesc", (String)map_data.get("PG_BusDesc")),	"Unable to enter value in Provided Details field .");
			
			if(common.currentRunningFlow.equalsIgnoreCase("NB"))
			{
				// Select Trade Code :				
				String sValue = (String)map_data.get("PG_TCS_TradeCode_Button");
				if(sValue.contains("Yes"))
				{
					customAssert.assertTrue(SelectTradeCode((String)map_data.get("PG_TCS_TradeCode") , "Policy Details" , 0,map_data),"Trade code selection function is having issue(S).");
				}
				
				// check added trade codes
				String ExpectedCode = (String)map_data.get("PG_TCS_TradeCode");
				String ActualCode ;
											
				ActualCode = k.getTextByXpath(".//*[@id='table0']/tbody/tr[2]/td[2]");
				customAssert.assertTrue(ExpectedCode.equals(ActualCode), "Trade code selection failed");
				customAssert.assertTrue(common.funcPageNavigation("Policy General", ""),"Policy General page not loaded");	
			}
			
			TestUtil.reportStatus("Entered all the details on Policy General page .", "Info", true);
			
			return retVal;
		
		}
		catch(Throwable t) 
		{
	        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
	        Assert.fail("Unable to to do operation on policy General page. \n", t);
	        return false;
		}		
	}

	public boolean SelectTradeCode(String tradeCodeValue , String pageName , int currentPropertyIndex,Map<Object, Object> map_data) 
	{		
		try
		{			
			customAssert.assertTrue(k.Click("CMA_Btn_SelectTradeCode"), "Unable to click on Select Trade Code button in Policy Details .");
			customAssert.assertTrue(common.funcPageNavigation("Trade Code Selection", ""), "Navigation problem to Trade Code Selection page .");
			
			String[] TradeCodes = tradeCodeValue.split(",");
			
			for(String s_TradeCode : TradeCodes)
			{
	 			driver.findElement(By.xpath("//*[text()='"+s_TradeCode+"']")).click();
	 		}
			
	 		return true;	 		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean MaterialFactsDeclerationPage()
	{
		boolean retValue = true;
		Map<Object, Object> data_map = new HashMap<>();
		switch(common.currentRunningFlow)
		{
			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "Rewind":
				data_map = common.Rewind_excel_data_map;
				break;
			case "Requote":
				data_map = common.Requote_excel_data_map;
				break;
		}
		
		try
		{
			 customAssert.assertTrue(common.funcPageNavigation("Material Facts & Declarations", ""),"Material Facts and Declarations page is having issue(S)");
			 k.ImplicitWaitOff();
			 String q_value = null;
			 
			 List<WebElement> elements = driver.findElements(By.className("selectinput"));
			 Select sel = null;
			
			 for(int i = 0;i<elements.size();i++)
			 {
				 if(elements.get(i).isDisplayed())
				 {				 
					 sel = new Select(elements.get(i));
					 try
					 {
						 q_value = (String)data_map.get("MFD_Q"+(i+1));					 
						 sel.selectByVisibleText(q_value);
					 }
					 catch(Exception e)
					 {
						 try
						 {
							 sel.selectByVisibleText("No");
						 }
						 catch(Exception q)
						 {
							 q.printStackTrace();
						 }
					 } 
				 }		 
			 }
			 customAssert.assertTrue(common.funcButtonSelection("Save"), "Unable to click on Save Button on Material Facts and Declarations Screen .");
		 
			 return retValue;
		 
		}
		catch(Throwable t)
		{
			k.ImplicitWaitOn();
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
	        return false;
		}
		finally
		{
			k.ImplicitWaitOn();
		}	
	}

	public boolean funcLossOfLicence(Map<Object, Object> mdata)
	{
		try 
		{		
			Map<String, List<Map<String, String>>> internal_data_map = new HashMap<>();
			switch(common.currentRunningFlow)
			{
				case "NB":
					internal_data_map = common.NB_Structure_of_InnerPagesMaps;
					break;
				case "MTA":
					internal_data_map = common.MTA_Structure_of_InnerPagesMaps;
					break;
				case "Renewal":
					internal_data_map = common.Renewal_Structure_of_InnerPagesMaps;
					break;
				case "Rewind":
					internal_data_map = common.Rewind_Structure_of_InnerPagesMaps;
					break;
				case "Requote":
					internal_data_map = common.Requote_Structure_of_InnerPagesMaps;
					break;
			}
		
			customAssert.assertTrue(common.funcPageNavigation("Loss of Licence",""), "Loss of Licence Page Navigation issue.");
			customAssert.assertTrue(k.DropDownSelection("PAC_LOL_CoverBasis",(String) mdata.get("LOL_cover_basis")),"Unable to select Cover Basis");
			customAssert.assertTrue(k.DropDownSelection("PAC_LOL_Limit",(String) mdata.get("LOL_limit")),"Unable to select Limit");
		
			int count = 0;
			String[] employees = ((String)mdata.get("LOL_addEmployee")).split(";");
	        int no_of_employees = employees.length;
        
	        Map<String, List<String>> emp= new HashMap<>();
        
	        if(!common.currentRunningFlow.equalsIgnoreCase("NB"))
	        {
	        	customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
	        }
    			
	        while(count < no_of_employees )
	        {
	        	customAssert.assertTrue(k.Click("PAC_LOL_AddEmployee"), "Unable to click on Add Employee");
	        	customAssert.assertTrue(addEmployee(mdata,count,emp),"Error while adding employee.");
	        	TestUtil.reportStatus("Employee  <b>[  "+internal_data_map.get("Employee").get(count).get("Automation Key")+"  ]</b>  added successfully . ", "Info", true);
	        	customAssert.assertTrue(k.Click("PAC_BackButtonEmployee"), "Unable to click on back button");
	        	count++;	
	        }
		
			customAssert.assertTrue(verifyEmployees(emp,no_of_employees), "Verify Employees function is having issue(s).");
			customAssert.assertTrue(k.Click("PAC_ApplyBookRateButton"), "Unable to click on Apply book rate button");
			
			customAssert.assertTrue(addInputCoverScreen(mdata), "Adding inputs to cover screen function is having issue(s).");		
			customAssert.assertTrue(k.Click("PAC_ApplyBookRateButton"), "Unable to click on Apply book rate button");
			
			customAssert.assertTrue(verifyLOLTable(mdata,(String) mdata.get("LOL_limit")), "Verifying Loss of Licence function is having issue(s).");
			customAssert.assertTrue(common.funcButtonSelection("Save"), "Unable to click on Save Button on Material Facts and Declarations Screen.");
			
			return true;
		}
		catch (Throwable t) 
		{
			String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in " + methodName + " function");
			k.reportErr("Failed in " + methodName + " function", t);
			Assert.fail("Unable to fill the details on Loss of Licence Cover Page \n", t);
			return false;
		}
	}

	public double getBookRate(String limitRaw)
	{
		double r_value=0;
		try 
		{
			String limitFormatted = limitRaw.replace(",","").replace("0","");
			String key="LimitIs"+limitFormatted+"k";
			PAC_Rater = OR.getORProperties();
			return Double.parseDouble(PAC_Rater.getProperty(key));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return r_value;
		}
	}

	public boolean verifyLOLTable(Map<Object, Object> mdata,String limitRaw)
	{
		boolean r_value = true;
		String ageBand="", formatAgeband="";
		int no_ofDrivers;
		String actual_initial_rate="0",actual_book_premium="0",actual_revised_premium="0",actual_premium_override="0",actual_premium="0",actual_total_premium="0";
		double exp_initial_rate = 0,exp_book_premium,exp_revised_premium,exp_total=0;
		
		String tableXPath = "//*[@id='table0'][2]/tbody/tr";
		
		// AP/RP Operation
		String aprp_PremiumOverride = null;
		if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes"))
		{
			switch((String)mdata.get("MTA_Operation")) 
			{
				case "AP":
						aprp_PremiumOverride = common_EP.doUniCoverAP(tableXPath, 10, 11);
						break;
						
				case "RP":
						aprp_PremiumOverride = common_EP.doUniCoverRP(tableXPath, 10 , 11);
						break;
						
				case "Non-Financial":
							TestUtil.reportStatus("Due to Non-Financial Flow, Loss of Licence Cover's Net Net Premium will be captured.", "Info", true);
							break;
			}
		}
		customAssert.assertTrue(k.Click("PAC_ApplyBookRateButton"), "Unable to click on Apply book rate button");
		
		try 
		{
			for(int i=1;i<6;i++)
			{
				ageBand = driver.findElement(By.xpath(tableXPath+"["+i+"]/td[2]")).getText();
				formatAgeband="LOL_"+ageBand.replace(" ","")+"techAdjust";
				no_ofDrivers = countDrivers(ageBand);
				
				TestUtil.reportStatus("----------<b>["+ageBand+"] </b> -------------", "Info", true);
				TestUtil.reportStatus("Number of drivers in <b>[  "+ageBand+"  ]</b> is/are <b>[  "+no_ofDrivers+"  ]</b>. ", "Info", true);
				
								
				if(ageBand.equals("Drivers under 20") || ageBand.equals("Drivers aged over 65 years") || no_ofDrivers == 0)
					exp_initial_rate = 0;
				else 
					exp_initial_rate = getBookRate(limitRaw);
				exp_book_premium=no_ofDrivers*exp_initial_rate;
				exp_revised_premium = (Double.parseDouble((String) mdata.get(formatAgeband))*exp_book_premium/100) + exp_book_premium;			
				
				actual_initial_rate = driver.findElement(By.xpath(tableXPath+"["+i+"]/td[5]/input")).getAttribute("value");
				actual_book_premium = driver.findElement(By.xpath(tableXPath+"["+i+"]/td[7]/input")).getAttribute("value");
				actual_revised_premium = driver.findElement(By.xpath(tableXPath+"["+i+"]/td[9]/input")).getAttribute("value");
				
				actual_premium_override = driver.findElement(By.xpath(tableXPath+"["+i+"]/td[10]/input")).getAttribute("value");
				actual_premium = driver.findElement(By.xpath(tableXPath+"["+i+"]/td[10]/input")).getAttribute("value");
								
				if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes"))
				{
					if(aprp_PremiumOverride!=null)
					{
						actual_premium_override = aprp_PremiumOverride;
					}
				}
				
				if(Double.parseDouble(actual_premium_override)!=0)
					actual_premium = actual_premium_override;
				else
					actual_premium = actual_revised_premium;
				
				exp_total = exp_total + Double.parseDouble(actual_premium);
				
				CommonFunction.compareValues(exp_initial_rate, Double.parseDouble(actual_initial_rate), "Initial Rate");
				CommonFunction.compareValues(exp_book_premium, Double.parseDouble(actual_book_premium), "Book Premium");
				CommonFunction.compareValues(exp_revised_premium, Double.parseDouble(actual_revised_premium), "Revised Premium");
			}
			
			customAssert.assertTrue(k.Click("PAC_ApplyBookRateButton"), "Unable to click on Apply book rate button");
			
			actual_total_premium = k.getAttribute("PAC_LOLTotal", "value");
			CommonFunction.compareValues(exp_total, Double.parseDouble(actual_total_premium), "Total Premium");
			
			mdata.put("PS_LossofLicence_NetNetPremium",Double.toString(exp_total));
			TestUtil.reportStatus("---------- Total Premium is <b>["+actual_total_premium+"] </b> -------------", "Info", true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return r_value;	
	}

	public boolean addInputCoverScreen(Map<Object, Object> mdata)
	{
		String driverAgeBanding="",formatAgeband="";
		String tableXPath = "//*[@id='table0'][2]/tbody/tr";
		try 
		{
			for(int i=1;i<6;i++)
			{
				driverAgeBanding = driver.findElement(By.xpath(tableXPath+"["+i+"]/td[2]")).getText();
				formatAgeband="LOL_"+driverAgeBanding.replace(" ","")+"techAdjust";
				k.InputByXpath(tableXPath+"["+i+"]/td[8]/input", (String) mdata.get(formatAgeband));
				
				TestUtil.reportStatus("Tech Adjust for <b> [ "+driverAgeBanding+" ] </b> added successfully . ", "Info", true);
				
				formatAgeband = formatAgeband.replace("techAdjust","PremiumOverride");
				k.InputByXpath(tableXPath+"["+i+"]/td[10]/input", (String) mdata.get(formatAgeband));
				
				TestUtil.reportStatus("Premium Override for <b> [ "+driverAgeBanding+" ] </b> added successfully . ", "Info", true);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addEmployee(Map<Object,Object> mapdata,int count,Map<String, List<String>> emp)
	{
		boolean r_value=true;
		Map<String, List<Map<String, String>>> internal_data_map = null;
		List<String> emp_info= new ArrayList<>(); 
		String key="";
		
		switch(common.currentRunningFlow)
		{
			case "NB":
				internal_data_map = common.NB_Structure_of_InnerPagesMaps;
				break;
			case "MTA":
				internal_data_map = common.MTA_Structure_of_InnerPagesMaps;
				break;
			case "Renewal":
				internal_data_map = common.Renewal_Structure_of_InnerPagesMaps;
				break;
			case "Rewind":
				internal_data_map = common.Rewind_Structure_of_InnerPagesMaps;
				break;
			case "Requote":
				internal_data_map = common.Requote_Structure_of_InnerPagesMaps;
				break;
		}
		
		try 
		{
			customAssert.assertTrue(k.DropDownSelection("PAC_AddEmployee_Title",(String) internal_data_map.get("Employee").get(count).get("Employee_title")),"Unable to select Title ");
			emp_info.add((String) internal_data_map.get("Employee").get(count).get("Employee_title"));
			
			
			customAssert.assertTrue(k.Input("PAC_AddEmployee_FirstName",(String) internal_data_map.get("Employee").get(count).get("Employee_firstName")),"Unable to select First Name in Employee section ");
			emp_info.add((String) internal_data_map.get("Employee").get(count).get("Employee_firstName"));
		
			
			customAssert.assertTrue(k.Input("PAC_AddEmployee_LastName",(String) internal_data_map.get("Employee").get(count).get("Employee_surname")),"Unable to select Surname in Employee section ");
			emp_info.add((String) internal_data_map.get("Employee").get(count).get("Employee_surname"));
			
			customAssert.assertTrue(k.Input("PAC_AddEmployee_DateofBirth",(String) internal_data_map.get("Employee").get(count).get("Employee_dateOfBirth")),"Unable to select Date of Birth in Employee section ");
			emp_info.add((String) internal_data_map.get("Employee").get(count).get("Employee_dateOfBirth"));
			
			customAssert.assertTrue(k.Click("PAC_SaveEmployeeButton"), "Unable to click on Save Employee");
			String age = driver.findElement(By.xpath(".//*[@id='qpa_lol_age']")).getText();
			mapdata.put("Employee_age",age);
			emp_info.add(age);
			
			String LossofLicenceBenefit = driver.findElement(By.xpath(".//*[@id='qpa_lol_loss_licence_benefit']")).getText();
			mapdata.put("Employee_lolBenefit",LossofLicenceBenefit);
			emp_info.add(LossofLicenceBenefit);
			
			key=(String) internal_data_map.get("Employee").get(count).get("Employee_firstName")+"_"+(String) internal_data_map.get("Employee").get(count).get("Employee_surname");
			
			emp.put(key, emp_info);
			
			customAssert.assertTrue(k.Click("PAC_SaveEmployeeButton"), "Unable to click on Save Employee");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return r_value;
	}

	public int countDrivers(String ageBand)
	{
		String employeeList_xpath="html/body/div[3]/form/div/table[2]";
		int emp_tble_Rows = driver.findElements(By.xpath(employeeList_xpath+"/tbody/tr")).size();
		String age="";
		int count=0;
		for(int i=1;i<=emp_tble_Rows;i++)
		{
			age=driver.findElement(By.xpath("html/body/div[3]/form/div/table[2]/tbody/tr["+i+"]/td[5]")).getText();
			if(Integer.parseInt(age)<20 && ageBand.equals("Drivers under 20"))
				count++;
			else if(Integer.parseInt(age)<55 && Integer.parseInt(age)>=20 && ageBand.equals("Drivers between 20 & 55"))
				count++;
			else if(Integer.parseInt(age)<60 && Integer.parseInt(age)>=55 && ageBand.equals("Drivers between 56 & 60"))
				count++;
			else if(Integer.parseInt(age)<65 && Integer.parseInt(age)>=60 && ageBand.equals("Drivers between 61 & 65"))
				count++;
			else if(Integer.parseInt(age)>=65 && ageBand.equals("Drivers aged over 65 years"))
				count++;
		}
		return count;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean verifyEmployees(Map<String, List<String>> emp,int no_of_employees)
	{
		boolean r_value=true;
		try 
		{			
			String employeeList_xpath="//*[@id='table0'][1]/tbody/tr";
			int emp_tble_Rows = driver.findElements(By.xpath(employeeList_xpath)).size();
			int emp_tble_Cols = driver.findElements(By.xpath(employeeList_xpath+"/th")).size();
			
			String temp="",name="";
			List<String> emp_infocheck= new ArrayList<>();
			Set entrySet = emp.entrySet();
			Iterator it = entrySet.iterator();
			
			if(emp_tble_Rows==no_of_employees)
			{
				for(int i=1;i<=emp_tble_Rows;i++)
				{
					name=driver.findElement(By.xpath(employeeList_xpath+"["+i+"]/td[2]")).getText() +" "+driver.findElement(By.xpath(employeeList_xpath+"["+i+"]/td[3]")).getText();
					temp=driver.findElement(By.xpath(employeeList_xpath+"["+i+"]/td[2]")).getText()+"_"+driver.findElement(By.xpath(employeeList_xpath+"["+i+"]/td[3]")).getText();
					
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry)it.next();
						
						if(me.getKey().equals(temp))
						{
							emp_infocheck=(List<String>) me.getValue();
							Iterator it2 = emp_infocheck.iterator();
							
							for(int j=1;j<emp_tble_Cols;j++)
							{
								customAssert.assertTrue((driver.findElement(By.xpath(employeeList_xpath+"["+i+"]/td["+j+"]")).getText().equals(it2.next())),"False");
							}
						}
					}
					
					TestUtil.reportStatus("Employee with name <b>[  "+name+"  ]</b>  verified successfully . ", "Info", true);
				}
			}
			
			TestUtil.reportStatus("All <b>["+emp_tble_Rows+"]</b> Employees verified successfully. ", "Info", true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return r_value;
	}
}
