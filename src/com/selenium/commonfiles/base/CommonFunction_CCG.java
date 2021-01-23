package com.selenium.commonfiles.base;

import java.text.SimpleDateFormat;

import org.testng.Assert;

import com.selenium.commonfiles.util.ErrorInTestMethod;
import com.selenium.commonfiles.util.TestUtil;

public class CommonFunction_CCG extends TestBase{

	SimpleDateFormat df = new SimpleDateFormat();
	public int actual_no_of_years=0,err_count=0,trans_error_val=0;
	
public void NewBusinessFlow(String code,String event) throws ErrorInTestMethod{
	String testName = (String)common.NB_excel_data_map.get("Automation Key");
	try{
		
		customAssert.assertTrue(common.StingrayLogin("PEN"),"Unable to login.");
		customAssert.assertTrue(common.checkClient(common.NB_excel_data_map,code,event),"Unable to check Client.");
		customAssert.assertTrue(common.createNewQuote(common.NB_excel_data_map,code,event), "Unable to create new quote.");
		customAssert.assertTrue(common.selectLatestQuote(common.NB_excel_data_map,code,event), "Unable to select quote from table.");
		customAssert.assertTrue(common_CCF.funcPolicyDetails(common.NB_excel_data_map,code,event), "Policy Details function having issue .");
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Previous Claims"),"Issue while Navigating to Previous Claims  . ");
		customAssert.assertTrue(common_CCF.funcPreviousClaims(common.NB_excel_data_map), "Previous claim function is having issue(S) .");
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Covers"),"Issue while Navigating to Covers  . ");
		customAssert.assertTrue(common.funcCovers(common.NB_excel_data_map), "Select covers function is having issue(S) . ");
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Specified Perils"),"Issue while Navigating to Specified Perils  . ");
		customAssert.assertTrue(common.funcSpecifiedPerils(common.NB_excel_data_map), "Select covers function is having issue(S) . ");
		
		//Non-linear cover selection
		customAssert.assertTrue(common_CCF.Cover_Selection_By_Sequence(common.NB_excel_data_map), "Cover selection by sequence function is having issue(S) .");
		
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		Assert.assertTrue(common_PEN.funcPremiumSummary(common.NB_excel_data_map,code,event));
		Assert.assertTrue(common_PEN.funcStatusHandling(common.NB_excel_data_map,code,event));
	
		if(TestBase.businessEvent.equalsIgnoreCase("NB")){
			customAssert.assertEquals(common.final_err_pdf_count,0,"Varification Errors in PDF Documents . ");
			customAssert.assertTrue(common.StingrayLogout(), "Unable to Logout.");
			TestUtil.reportTestCasePassed(testName);
		}
	
	
	}catch(Throwable t){
		TestUtil.reportTestCaseFailed(testName, t);
		throw new ErrorInTestMethod(t.getMessage());
	}
	
}
public void MTAFlow(String code,String event) throws ErrorInTestMethod{
	
	String testName = (String)common.MTA_excel_data_map.get("Automation Key");
	String NavigationBy = CONFIG.getProperty("NavigationBy");
	CommonFunction_PEN.AdjustedTaxDetails.clear();

	if(((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")) {
		customAssert.assertTrue(common_EP.ExistingPolicyAlgorithm_PEN(common.MTA_excel_data_map , (String)common.MTA_excel_data_map.get("MTA_ExistingPolicy_Type"), (String)common.MTA_excel_data_map.get("MTA_ExistingPolicy_Status")), "Existing Policy Algorithm function is having issues. ");
	}else {
		if(!common.currentRunningFlow.equalsIgnoreCase("Renewal")){
			NewBusinessFlow(code,"NB");
		}
		common_HHAZ.CoversDetails_data_list.clear();
		common_PEN.PremiumFlag = false;
	}

	try {
	
	common.currentRunningFlow = "MTA";	
	
	customAssert.assertTrue(common.funcNextNavigateDecesionMaker(NavigationBy, "Premium Summary"),"Issue while Navigating to Premium Summary screen.");
	customAssert.assertTrue(common_POB.funcCreateEndorsement(), "Error in Create Endorsement function .");
	
	customAssert.assertTrue(common_CCF.funcPolicyDetails(common.MTA_excel_data_map,code,event), "Policy Details function having issue .");
	customAssert.assertTrue(common.funcMenuSelection("Navigate","Previous Claims"),"Issue while Navigating to Previous Claims  . ");
	customAssert.assertTrue(common_CCF.funcPreviousClaims(common.MTA_excel_data_map), "Previous claim function is having issue(S) .");
	customAssert.assertTrue(common.funcMenuSelection("Navigate","Covers"),"Issue while Navigating to Covers  . ");
	customAssert.assertTrue(common.funcCovers(common.MTA_excel_data_map), "Select covers function is having issue(S) . ");
	customAssert.assertTrue(common.funcMenuSelection("Navigate","Specified Perils"),"Issue while Navigating to Specified Perils  . ");
	customAssert.assertTrue(common.funcSpecifiedPerils(common.MTA_excel_data_map), "Select covers function is having issue(S) . ");
	
	//Non-linear cover selection
	customAssert.assertTrue(common_CCF.Cover_Selection_By_Sequence(common.MTA_excel_data_map), "Cover selection by sequence function is having issue(S) .");

	customAssert.assertTrue(common.funcNextNavigateDecesionMaker(NavigationBy, "Premium Summary"), "Issue while Navigating to Premium Summary screen . ");
	Assert.assertTrue(common_PEN.funcPremiumSummary_MTA(common.MTA_excel_data_map, code, event));
	
	
	if(!TestBase.businessEvent.equalsIgnoreCase("Renewal")){
		Assert.assertTrue(common_PEN.funcStatusHandling(common.MTA_excel_data_map, code, event));
		customAssert.assertEquals(err_count,0,"Errors in premium calculations . ");
		customAssert.assertEquals(trans_error_val,0,"Errors in Transaction premium calculations . ");
		customAssert.assertEquals(common.final_err_pdf_count,0,"Varification Errors in PDF Documents . ");
		
		customAssert.assertTrue(common.StingrayLogout(), "Unable to Logout.");
	
		TestUtil.reportTestCasePassed(testName);
	}

}catch (ErrorInTestMethod e) {
	//System.out.println("Error in New Business test method for MTA > "+testName);
	throw e;
}catch(Throwable t){
	TestUtil.reportTestCaseFailed(testName, t);
	throw new ErrorInTestMethod(t.getMessage());
}
	
	
}
public void RewindFlow(String code,String event) throws ErrorInTestMethod{
		
		String testName = (String)common.Rewind_excel_data_map.get("Automation Key");
		try{
			
			if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes")) 
			{
				customAssert.assertTrue(common_EP.ExistingPolicyAlgorithm_PEN(common.Rewind_excel_data_map,(String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type"), (String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Status")), "Existing Policy Algorithm function is having issues. ");
			}
			else 
			{
				CommonFunction_HHAZ.AdjustedTaxDetails.clear();
				if(!common.currentRunningFlow.equalsIgnoreCase("Renewal") && !common.currentRunningFlow.equalsIgnoreCase("MTA"))
				{
					NewBusinessFlow(code,"NB");
				}
				common_PEN.PremiumFlag = false;
			}
			
		
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
		
		customAssert.assertTrue(common_CCF.funcPolicyDetails(common.Rewind_excel_data_map,code,event), "Policy Details function having issue .");
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Previous Claims"),"Issue while Navigating to Previous Claims  . ");
		customAssert.assertTrue(common_CCF.funcPreviousClaims(common.Rewind_excel_data_map), "Previous claim function is having issue(S) .");
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Covers"),"Issue while Navigating to Covers  . ");
		customAssert.assertTrue(common.funcCovers(common.Rewind_excel_data_map), "Select covers function is having issue(S) . ");
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Specified Perils"),"Issue while Navigating to Specified Perils  . ");
		customAssert.assertTrue(common.funcSpecifiedPerils(common.Rewind_excel_data_map), "Select covers function is having issue(S) . ");
		
		//Non-linear cover selection
		customAssert.assertTrue(common_CCF.Cover_Selection_By_Sequence(common.Rewind_excel_data_map), "Cover selection by sequence function is having issue(S) .");

		customAssert.assertTrue(common.funcMenuSelection("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		
		if(TestBase.businessEvent.equalsIgnoreCase("MTA")){
			customAssert.assertTrue(common_PEN.funcPremiumSummary_MTA(common.Rewind_excel_data_map, code, event), "Rewind MTA Premium Summary in function is having issue(S) . ");
		}else if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type")).equalsIgnoreCase("Endorsement") && ((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes")){
			customAssert.assertTrue(common_PEN.funcPremiumSummary_MTA(common.Rewind_excel_data_map, code, event), "Rewind MTA Premium Summary in function is having issue(S) . ");
		}else{
			customAssert.assertTrue(common_PEN.funcPremiumSummary(common.Rewind_excel_data_map,code,event), "Premium Summary function is having issue(S) . ");
		}
		if(!TestBase.businessEvent.equalsIgnoreCase("MTA")){ 
			Assert.assertTrue(common_PEN.funcStatusHandling(common.Rewind_excel_data_map,code,event));
		}
		
		
		if(TestBase.businessEvent.equals("Rewind")){
			customAssert.assertEquals(err_count,0,"Errors in premium calculations . ");
			customAssert.assertEquals(trans_error_val,0,"Errors in Transaction premium calculations . ");
			customAssert.assertEquals(common.final_err_pdf_count,0,"Varification Errors in PDF Documents . ");
			customAssert.assertTrue(common.StingrayLogout(), "Unable to Logout.");
			TestUtil.reportTestCasePassed(testName);
			
		} 
			
	}catch(Throwable t){
		TestUtil.reportTestCaseFailed(testName, t);
		throw new ErrorInTestMethod(t.getMessage());
	}
	
	
}

public void RenewalRewindFlow(String code,String event) throws ErrorInTestMethod{
	
	String testName = (String)common.Rewind_excel_data_map.get("Automation Key");
	try{
		
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
		
	
		common.currentRunningFlow="Rewind";
		String navigationBy = CONFIG.getProperty("NavigationBy");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		customAssert.assertTrue(common.funcRewind());
		
	TestUtil.reportStatus("<b> -----------------------Renewal Rewind flow started---------------------- </b>", "Info", false);
		
	customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
	customAssert.assertTrue(common.funcSearchPolicy(common.Renewal_excel_data_map), "Policy Search function is having issue(S) . ");
	customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Rewind_excel_data_map,code,event,"Renewal Submitted (Rewind)"), "Verify Policy Status (Submitted (Rewind)) function is having issue(S) . ");
	
	customAssert.assertTrue(common_CCF.funcPolicyDetails(common.Rewind_excel_data_map,code,event), "Policy Details function having issue .");
	customAssert.assertTrue(common.funcMenuSelection("Navigate","Previous Claims"),"Issue while Navigating to Previous Claims  . ");
	customAssert.assertTrue(common_CCF.funcPreviousClaims(common.Rewind_excel_data_map), "Previous claim function is having issue(S) .");
	customAssert.assertTrue(common.funcMenuSelection("Navigate","Covers"),"Issue while Navigating to Covers  . ");
	customAssert.assertTrue(common.funcCovers(common.Rewind_excel_data_map), "Select covers function is having issue(S) . ");
	customAssert.assertTrue(common.funcMenuSelection("Navigate","Specified Perils"),"Issue while Navigating to Specified Perils  . ");
	customAssert.assertTrue(common.funcSpecifiedPerils(common.Rewind_excel_data_map), "Select covers function is having issue(S) . ");
	
	//Non-linear cover selection
	customAssert.assertTrue(common_CCF.Cover_Selection_By_Sequence(common.Rewind_excel_data_map), "Cover selection by sequence function is having issue(S) .");

	customAssert.assertTrue(common.funcMenuSelection("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
	
	customAssert.assertTrue(common_PEN.funcPremiumSummary(common.Rewind_excel_data_map, code, event), "Rewind MTA Premium Summary in function is having issue(S) . ");
	
	if(TestBase.businessEvent.equals("Rewind")){
		customAssert.assertEquals(err_count,0,"Errors in premium calculations . ");
		customAssert.assertEquals(trans_error_val,0,"Errors in Transaction premium calculations . ");
		customAssert.assertEquals(common.final_err_pdf_count,0,"Varification Errors in PDF Documents . ");
		customAssert.assertTrue(common.StingrayLogout(), "Unable to Logout.");
		TestUtil.reportTestCasePassed(testName);
		
	} 
		
}catch(Throwable t){
	TestUtil.reportTestCaseFailed(testName, t);
	throw new ErrorInTestMethod(t.getMessage());
}


}


public void CancellationFlow(String code,String event) throws ErrorInTestMethod{
	
		common_PEN.cancellationProcess(code,event);	
	
}

public void RenewalFlow(String code,String event) throws ErrorInTestMethod{
	String testName = (String)common.Renewal_excel_data_map.get("Automation Key");
	try{
		
		common.currentRunningFlow="Renewal";
		String navigationBy = CONFIG.getProperty("NavigationBy");
		
		customAssert.assertTrue(common.StingrayLogin("PEN"),"Unable to login.");
		customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
		customAssert.assertTrue(common_CCJ.renewalSearchPolicyNEW(common.Renewal_excel_data_map), "Policy Search function is having issue(S) . ");
		customAssert.assertTrue(common.funcVerifyPolicyStatus_Renewal(common.Renewal_excel_data_map,CommonFunction.product,CommonFunction.businessEvent,"Renewal Pending"), "Verify Policy Status (Renewal Pending)) function is having issue(S) . ");
		
		if(!common_HHAZ.isAssignedToUW){ // This variable is initialized in common_CCJ.renewalSearchPolicyNEW function
			customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"));
			customAssert.assertTrue(common_SPI.funcAssignPolicyToUW());
		}
		
		customAssert.assertTrue(common.funcButtonSelection("Send to Underwriter"));
		customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
		customAssert.assertTrue(common.funcSearchPolicy_Renewal(common.Renewal_excel_data_map), "Policy Search function is having issue(S) . ");
		customAssert.assertTrue(common.funcVerifyPolicyStatus_Renewal(common.Renewal_excel_data_map,CommonFunction.product,CommonFunction.businessEvent,"Renewal Submitted"), "Verify Policy Status (Renewal Submitted) function is having issue(S) . ");
		
		customAssert.assertTrue(common_CCF.funcPolicyDetails(common.Renewal_excel_data_map,code,event), "Policy Details function having issue .");
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Previous Claims"),"Issue while Navigating to Previous Claims  . ");
		customAssert.assertTrue(common_CCF.funcPreviousClaims(common.Renewal_excel_data_map), "Previous claim function is having issue(S) .");
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Covers"),"Issue while Navigating to Covers  . ");
		customAssert.assertTrue(common.funcCovers(common.Renewal_excel_data_map), "Select covers function is having issue(S) . ");
		customAssert.assertTrue(common.funcMenuSelection("Navigate","Specified Perils"),"Issue while Navigating to Specified Perils  . ");
		customAssert.assertTrue(common.funcSpecifiedPerils(common.Renewal_excel_data_map), "Select covers function is having issue(S) . ");
		
		//Non-linear cover selection
		customAssert.assertTrue(common_CCF.Cover_Selection_By_Sequence(common.Renewal_excel_data_map), "Cover selection by sequence function is having issue(S) .");

		customAssert.assertTrue(common.funcMenuSelection("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		customAssert.assertTrue(common_PEN.funcPremiumSummary(common.Renewal_excel_data_map,code,event), "Premium Summary function is having issue(S) . ");
		
		customAssert.assertTrue(common_PEN.funcStatusHandling(common.Renewal_excel_data_map,code,event));
		
		
		TestUtil.reportTestCasePassed(testName);
	
	}catch (ErrorInTestMethod e) {
		System.out.println("Error in New Business test method for MTA > "+testName);
		throw e;
	}catch(Throwable t){
		TestUtil.reportTestCaseFailed(testName, t);
		throw new ErrorInTestMethod(t.getMessage());
	}
	
	
	
}



//End of CF_CCF.java
}
