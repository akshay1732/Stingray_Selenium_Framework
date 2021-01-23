package com.selenium.commonfiles.base;

import static com.selenium.commonfiles.util.TestUtil.WriteDataToXl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.selenium.commonfiles.util.ErrorInTestMethod;
import com.selenium.commonfiles.util.ObjectMap;
import com.selenium.commonfiles.util.TestUtil;


public class CommonFunction_OFC extends TestBase{
	
	SimpleDateFormat df = new SimpleDateFormat();
	Properties OFC_Rater = new Properties();
	public Map<String,Double> Book_rate_Rater_output = new HashMap<>();
	public boolean isMTARewindFlow=false,isFPEntries=false,isMTARewindStarted=false, isNBRewindStarted = false, isNBRquoteStarted = false;
	public int actual_no_of_years=0,err_count=0,trans_error_val=0 , Can_returnP_Error=0;
	public List<String> CoversDetails_data_list = null, MD_Building_Occupancies_list=new ArrayList<>();
	public Map<String,Double> TC_Calculations = new HashMap<>();
	public boolean isIncludingHeatPresent = false,isExcludingHeatPresent = false,isIncludeHeat_10M=false;
	public double cent_work_including_heat = 0.0; 
	Date currentDate = new Date();
	public static int p_Index = 0;
	public static String Environment = null;
	public double JCT_TotalPremium = 0.0, OP_TotalPremium = 0.00, Ter_TotalPremium = 0.00; 
	public String pas_NoOfEmp_AllOthers = "0", pas_NoOfEmp_Clerical = "0", pas_NoOfEmp_Drivers = "0", LE_TotalWegroll = "0";
	public double Ter_BuildingContents_Sum = 0.0;
	public double totalMD = 0.0, Ter_BI_Sum = 0.00;
	public boolean isGrossPremiumReferralCheckDone = false;
	public int at_differenceBetween_PreviousClaim_and_InceptionDate=10;
	public int at_global_no_of_property=0,c_AUTOMATED_CAR_WASH=0;
	public String at_24by7 = "";
	public String at_load = "" ;
	public String at_AlarmDescription = "";
	
	public void NewBusinessFlow(String code,String event) throws ErrorInTestMethod{
		String testName = (String)common.NB_excel_data_map.get("Automation Key");
		String navigationBy = CONFIG.getProperty("NavigationBy");
		common.currentRunningFlow = "NB";
		try{
			customAssert.assertTrue(common.StingrayLogin("OAMPS"),"Unable to login.");
			customAssert.assertTrue(common.checkClient(common.NB_excel_data_map,code,event),"Unable to check Client.");
			customAssert.assertTrue(common.createNewQuote(common.NB_excel_data_map,code,event), "Unable to create new quote.");
			customAssert.assertTrue(common.selectLatestQuote(common.NB_excel_data_map,code,event), "Unable to select quote from table.");
			customAssert.assertTrue(funcPolicyGeneral(common.NB_excel_data_map,code,event), "Policy Details function having issue .");
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Covers"),"Issue while Navigating to Covers  . ");
			customAssert.assertTrue(common_HHAZ.funcCovers(common.NB_excel_data_map), "Select covers function is having issue(S) . ");
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts & Declarations"),"Issue while Navigating to Material Facts and Declarations . ");
			customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Previous Claims"),"Issue while Navigating to Material Facts and Declarations . ");
			customAssert.assertTrue(funcPreviousClaims(common.NB_excel_data_map), "Previous claim function is having issue(S) .");
			
			//Non-linear cover selection
			customAssert.assertTrue(Cover_Selection_By_Sequence(common.NB_excel_data_map), "Cover selection by sequence function is having issue(S) .");
				
			/*customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Endorsements"),"Issue while Navigating to Property Owners Liability screen.");
			customAssert.assertTrue(common_HHAZ.funcEndorsementOperations(common.NB_excel_data_map),"Endorsement Operation is having issue(S).");
			*/customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
			customAssert.assertTrue(common_HHAZ.funcPremiumSummary(common.NB_excel_data_map,code,event), "Premium Summary function is having issue(S) . ");
			Assert.assertTrue(common_HHAZ.funcStatusHandling(common.NB_excel_data_map,code,"NB"));
		
			if(TestBase.businessEvent.equals("NB")){
				
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
	
	
	public boolean funcPolicyGeneral(Map<Object, Object> map_data, String code, String event) {
		boolean retVal = true;
		
		try{
			
			customAssert.assertTrue(common.funcPageNavigation("Policy General", ""),"Policy General page not loaded");
			customAssert.assertTrue(k.Input("COB_PG_InsuredName", (String)map_data.get("PG_InsuredName")),	"Unable to enter value in Insured Name  field .");
			customAssert.assertTrue(!k.getAttributeIsEmpty("COB_PG_InsuredName", "value"),"Insured Name Field Should Contain Valid Name  .");
			
			customAssert.assertTrue(k.Input("OFC_PG_EstdYear", (String)map_data.get("PG_EstdYear")),	"Unable to enter value in Business Established Year field .");
			customAssert.assertTrue(k.Input("COB_PG_TurnOver", (String)map_data.get("PG_TurnOver")),	"Unable to enter value in Turnover field .");
			
			String[] geographical_Limits = ((String)map_data.get("PG_GeoLimit")).split(",");
			List<WebElement> ul_elements = driver.findElements(By.xpath("//ul"));
			for(String geo_limit : geographical_Limits){
				for(WebElement each_ul : ul_elements){
					customAssert.assertTrue(k.Click("OFC_PG_GeoLimit"),"Error while Clicking Geographic Limit List object . ");
					k.waitTwoSeconds();
					if(each_ul.findElement(By.xpath("//li[text()='"+geo_limit+"']")).isDisplayed())
						each_ul.findElement(By.xpath("//li[text()='"+geo_limit+"']")).click();
					else
						continue;
					break;
				}
			}
			
			/*String[] Prof_Bodies = ((String)map_data.get("PG_InsuredDetails_Q1")).split(",");
			List<WebElement> ul_ele = driver.findElements(By.xpath("//ul"));
			for(String pBodies_limit : Prof_Bodies){
				for(WebElement each_ul : ul_ele){
					customAssert.assertTrue(k.Click("CCD_PG_InsuredDetails_Q1"),"Error while Clicking Professional Bodies List object . ");
					k.waitTwoSeconds();
					if(each_ul.findElement(By.xpath("//li[text()='"+pBodies_limit+"']")).isDisplayed())
						each_ul.findElement(By.xpath("//li[text()='"+pBodies_limit+"']")).click();
					else
						continue;
					break;
				}
			}*/
			
			customAssert.assertTrue(k.Input("CCF_Address_CC_Address", (String) map_data.get("PG_Address")),"Unable to enter value in Address field. ");
			customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_Address_CC_Address", "value"),"Address Field Should Contain Valid Address  .");
			customAssert.assertTrue(k.Input("CCF_Address_CC_line2", (String) map_data.get("PG_Line1")),"Unable to enter value in Address field line 1 . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_line3", (String) map_data.get("PG_Line2")),"Unable to enter value in Address field line 2 . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_Town", (String) map_data.get("PG_Town")),"Unable to enter value in Town field . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_County", (String) map_data.get("PG_County")),"Unable to enter value in County  . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_Postcode", (String)map_data.get("PG_Postcode")),"Unable to enter value in PostCode");
			customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_Address_CC_Postcode", "value"),"PostCode Field Should Contain Valid Postcode  .");
			customAssert.assertTrue(common.validatePostCode((String)map_data.get("PG_Postcode")),"Post Code is not in Correct format .");
			
			customAssert.assertTrue(k.DropDownSelection("COB_PG_QuoteValidity", (String)map_data.get("PG_QuoteValidity")),"Unable to select Auote Validity value");
			customAssert.assertTrue(k.Input("COB_PG_BusDesc", (String)map_data.get("PG_BusDesc")),	"Unable to enter value in Provided Details field .");
			
			if(common.currentRunningFlow.equalsIgnoreCase("NB")){
				// Select Trade Code :
				
				String sValue = (String)map_data.get("PG_TCS_TradeCode_Button");
				if(sValue.contains("Yes")){
					customAssert.assertTrue(SelectTradeCode((String)map_data.get("PG_TCS_TradeCode") , "Policy Details" , 0,map_data),"Trade code selection function is having issue(S).");
				}
				
				// check added trade codes
				String allTradeValues = (String)map_data.get("PG_TCS_TradeCode");
				String sUniqueCol ="Group";
				int tableId = 0;
				
				String sTablePath = "html/body/div[3]/form/div/table";
				
				tableId = k.getTableIndex(sUniqueCol,"xpath",sTablePath);
				//sTablePath = "html/body/div[3]/form/div/table["+ tableId +"]";
				sTablePath = "html/body/div[3]/form/div/table[5]";
			
				WebElement s_table= driver.findElement(By.xpath(sTablePath));
				int totalRows = s_table.findElements(By.tagName("tr")).size();
				String val_Trade = "";
				
				for(int i = 0; i<totalRows-1; i++){
					
					if(totalRows > 2){
						//val_Trade = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]");
						val_Trade = k.GetText_DynamicXpathWebDriver(driver, "//*[@id='table0']/tbody/tr["+(i+2)+"]/td[2]");
					}else{
						val_Trade = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr/td[3]");
					}
					
					if(allTradeValues.contains(val_Trade)){
						TestUtil.reportStatus(val_Trade + "Tradecode is added and displayed on Policy general screen", "Info", true);
					}
				}
				
				
			customAssert.assertTrue(common.funcPageNavigation("Policy General", ""),"Policy General page not loaded");	
			}
			
				

			TestUtil.reportStatus("Entered all the details on Policy General page .", "Info", true);
			
			return retVal;
		
		}catch(Throwable t) {
	        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
	        Assert.fail("Unable to to do operation on policy General page. \n", t);
	        return false;
		}
		
	}
	
	//----------------------------------------------------------------//
	//--This function selects cover as per sequence provided in the data sheet--//
	//--------------------------------------------------------------//
	public boolean Cover_Selection_By_Sequence(Map<Object,Object> data_map){
		boolean r_value=false;
		String navigationBy = CONFIG.getProperty("NavigationBy");
		String[] covers = null;
		try{
			try{
				if(((String)data_map.get("CD_IsLinearCoverSelection")).equalsIgnoreCase("Yes")){
					Cover_selection_linear(data_map);
					return true;
				}else{
					covers = ((String)data_map.get("CD_Cover_Selection_Sequence")).split(",");
				}
			}catch(Throwable t){
				Cover_selection_linear(data_map);
			}
			for(String cover : covers){	
				
				switch(cover){ 
				
				case "MaterialDamage":
					if(((String)data_map.get("CD_MaterialDamage")).equals("Yes")){	
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Insured Properties"),"Issue while Navigating to Insured Properties screen .");
						switch(common.currentRunningFlow){
						case "NB":
							common.NB_excel_data_map.put("CD_Stock-RiskItems","Yes");
							break;
						case "Rewind":
							common.Rewind_excel_data_map.put("CD_Stock-RiskItems","Yes");
							break;
						case "MTA":
							common.MTA_excel_data_map.put("CD_Stock-RiskItems","Yes");
							break;
						case "Renewal":
							common.Renewal_excel_data_map.put("CD_Stock-RiskItems","Yes");
							break;
						}
						customAssert.assertTrue(MaterialDamagePage(data_map), "Material DamagePage function is having issue(S) . ");
					}
					else{
						switch(common.currentRunningFlow){
						case "NB":
							common.NB_excel_data_map.put("CD_Stock-RiskItems","No");
							break;
						case "Rewind":
							common.Rewind_excel_data_map.put("CD_Stock-RiskItems","No");
							break;
						case "MTA":
							common.MTA_excel_data_map.put("CD_Stock-RiskItems","No");
							break;
						case "Renewal":
							common.Renewal_excel_data_map.put("CD_Stock-RiskItems","No");
							break;
						}
					}
				break;
				case "BusinessInterruption":
					if(((String)data_map.get("CD_BusinessInterruption")).equals("Yes")){		
						customAssert.assertTrue(common.funcMenuSelection("Navigate","Business Interruption"),"Issue while Navigating to Business Interruption  . ");
						customAssert.assertTrue(funcBusinessInterruption(data_map), "Business Interruption function is having issue(S) . ");
					}
				break;
				case "Money":
					if(((String)data_map.get("CD_Money")).equals("Yes")){	
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Money"),"Issue while Navigating to Money screen.");
						customAssert.assertTrue(MoneyPage(data_map), "Money function is having issue(S) . ");
					}
				break;
				case "Liability":
					if(((String)data_map.get("CD_Liability")).equals("Yes")){	
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Employers Liability"),"Issue while Navigating to Employers Liability screen.");
						switch(common.currentRunningFlow){
						case "NB":
							common.NB_excel_data_map.put("CD_EmployersLiability","Yes");
							break;
						case "Rewind":
							common.Rewind_excel_data_map.put("CD_EmployersLiability","Yes");
							break;
						case "MTA":
							common.MTA_excel_data_map.put("CD_EmployersLiability","Yes");
							break;
						case "Renewal":
							common.Renewal_excel_data_map.put("CD_EmployersLiability","Yes");
							break;
						}
						customAssert.assertTrue(EmployersLiabilityPage(data_map), "Employers LiabilityPage function is having issue(S) . ");
						
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Public and Products Liability"),"Issue while Navigating to Public and Products Liability screen.");
						
						switch(common.currentRunningFlow){
						case "NB":
							common.NB_excel_data_map.put("CD_PublicandProductsLiability","Yes");
							break;
						case "Rewind":
							common.Rewind_excel_data_map.put("CD_PublicandProductsLiability","Yes");
							break;
						case "MTA":
							common.MTA_excel_data_map.put("CD_PublicandProductsLiability","Yes");
							break;
						case "Renewal":
							common.Renewal_excel_data_map.put("CD_PublicandProductsLiability","Yes");
							break;
						}
						customAssert.assertTrue(PublicAndProductsLiabilityPage(data_map), "Public and Products Liability function is having issue(S) . ");
					}
					else{
						switch(common.currentRunningFlow){
						case "NB":
							common.NB_excel_data_map.put("CD_EmployersLiability","No");
							common.NB_excel_data_map.put("CD_PublicandProductsLiability","No");
							break;
						case "Rewind":
							common.Rewind_excel_data_map.put("CD_EmployersLiability","No");
							common.Rewind_excel_data_map.put("CD_PublicandProductsLiability","No");
							break;
						case "MTA":
							common.MTA_excel_data_map.put("CD_EmployersLiability","No");
							common.MTA_excel_data_map.put("CD_PublicandProductsLiability","No");
							break;
						case "Renewal":
							common.Renewal_excel_data_map.put("CD_EmployersLiability","No");
							common.Renewal_excel_data_map.put("CD_PublicandProductsLiability","No");
							break;
						}
					}
				break;
				case "SpecifiedAllRisks":
					if(((String)data_map.get("CD_SpecifiedAllRisks")).equals("Yes")){		
						customAssert.assertTrue(common.funcMenuSelection("Navigate","Specified All Risks"),"Issue while Navigating to Specified All Risks  . ");
						customAssert.assertTrue(funcSpecifiedAllRisks(data_map), "Specified All Risks function is having issue(S) . ");
					}
				break;
				case "GoodsInTransit":
					if(((String)data_map.get("CD_GoodsInTransit")).equals("Yes")){	
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Goods in Transit"),"Issue while Navigating to Goods in Transit screen.");
						customAssert.assertTrue(GoodsinTransitPage(data_map), "Goods in Transit function is having issue(S) . ");
					}
				break;
				case "PersonalAccident":
					if(((String)data_map.get("CD_PersonalAccident")).equals("Yes")){	
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Personal Accident"),"Issue while Navigating to Personal Accident screen.");
						customAssert.assertTrue(PersonalAccidentPage(data_map), "Personal Accident function is having issue(S) . ");
					}
				break;
				case "LegalExpenses":
					if(((String)data_map.get("CD_LegalExpenses")).equals("Yes")){	
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Legal Expenses"),"Issue while Navigating to Legal Expenses screen.");
						customAssert.assertTrue(LegalExpensesPage(data_map), "Legal Expenses function is having issue(S) . ");
					}
				break;
				case "Terrorism":
					if(((String)data_map.get("CD_Terrorism")).equals("Yes")){	
						customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Terrorism"),"Issue while Navigating to Terrorism screen.");
						customAssert.assertTrue(TerrorismPage(data_map), "TerrorismPage function is having issue(S) . ");
				
					}
				break;
				default:
					System.out.println("Cover selection status is 'NO' in the data sheet for cover - "+cover);
				}
			}
			
			r_value=true;
		}catch(Throwable t){
			System.out.println("Error while selecting cover by sequence --> "+t.getLocalizedMessage());
			return false;
		}
		return r_value;
	}
	
	public boolean Cover_selection_linear(Map<Object,Object> data_map){
		
		String navigationBy = CONFIG.getProperty("NavigationBy");
		
		try{
		
			if(((String)data_map.get("CD_MaterialDamage")).equals("Yes")){	
				customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Insured Properties"),"Issue while Navigating to Material Damage screen.");
				customAssert.assertTrue(MaterialDamagePage(data_map), "Material DamagePage function is having issue(S) . ");
			}	
			if(((String)data_map.get("CD_BusinessInterruption")).equals("Yes")){		
				customAssert.assertTrue(common.funcMenuSelection("Navigate","Business Interruption"),"Issue while Navigating to Business Interruption  . ");
				customAssert.assertTrue(funcBusinessInterruption(data_map), "Business Interruption function is having issue(S) . ");
			}
			if(((String)data_map.get("CD_Money")).equals("Yes")){	
				customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Money"),"Issue while Navigating to Money screen.");
				customAssert.assertTrue(MoneyPage(data_map), "Money function is having issue(S) . ");
			}
			if(((String)data_map.get("CD_Liability")).equals("Yes")){	
				customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Employers Liability"),"Issue while Navigating to Employers Liability screen.");
				customAssert.assertTrue(EmployersLiabilityPage(data_map), "Employers LiabilityPage function is having issue(S) . ");
				customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Public and Products Liability"),"Issue while Navigating to Public and Products Liability screen.");
				customAssert.assertTrue(PublicAndProductsLiabilityPage(data_map), "Public and Products Liability function is having issue(S) . ");
			}
			if(((String)data_map.get("CD_SpecifiedAllRisks")).equals("Yes")){		
				customAssert.assertTrue(common.funcMenuSelection("Navigate","Specified All Risks"),"Issue while Navigating to Specified All Risks  . ");
				customAssert.assertTrue(funcSpecifiedAllRisks(data_map), "Specified All Risks function is having issue(S) . ");
			}
			if(((String)data_map.get("CD_GoodsInTransit")).equals("Yes")){	
				customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Goods in Transit"),"Issue while Navigating to Goods in Transit screen.");
				customAssert.assertTrue(GoodsinTransitPage(data_map), "Goods in Transit function is having issue(S) . ");
			}
			if(((String)data_map.get("CD_PersonalAccident")).equals("Yes")){	
				customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Personal Accident"),"Issue while Navigating to Personal Accident screen.");
				customAssert.assertTrue(PersonalAccidentPage(data_map), "Personal Accident function is having issue(S) . ");
			}
			if(((String)data_map.get("CD_LegalExpenses")).equals("Yes")){	
				customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Legal Expenses"),"Issue while Navigating to Legal Expenses screen.");
				customAssert.assertTrue(LegalExpensesPage(data_map), "Legal Expenses function is having issue(S) . ");
			}
			if(((String)data_map.get("CD_Terrorism")).equals("Yes")){	
				customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Terrorism"),"Issue while Navigating to Terrorism screen.");
				customAssert.assertTrue(TerrorismPage(data_map), "TerrorismPage function is having issue(S) . ");
		
			}
		
		}catch(Throwable t){
			System.out.println("Error while selecting covers in linear way - "+t.getMessage());
			return false;
		}
	
		
		return true;
		
	}

	
	public boolean SelectTradeCode(String tradeCodeValue , String pageName , int currentPropertyIndex,Map<Object, Object> map_data) {
		
		try{
			
			String tradeCodeName = "",referralKey = "RM_PolicyGeneral_Trade";
			customAssert.assertTrue(k.Click("OFC_Btn_SelectTradeCode"), "Unable to click on Select Trade Code button in Policy Details .");
			customAssert.assertTrue(common.funcPageNavigation("Trade Code Selection", ""), "Navigation problem to Trade Code Selection page .");
			
			String sVal = tradeCodeValue;
			String[] TradeCodes = tradeCodeValue.split(",");
			
			String a_selectedTradeCode = null;
			String a_selectedTradeCode_desc = null;
			
			for(String s_TradeCode : TradeCodes){
	 			driver.findElement(By.xpath("//*[@id='table0']/tbody/tr[3]/td[1]/a")).click();
	 		}
			//Referral code - Trade Referrals
			/*if(is_Trade_referral_activity(tradeCodeName)){
				tradeCodeName = tradeCodeName.replaceAll(" ", "");
				
				common_HHAZ.referrals_list.add((String)map_data.get(referralKey+tradeCodeName));
			}*/
			
			common.funcButtonSelection("Save");
			common.funcButtonSelection("exit (Save First)");
	 		
	 		return true;
	 		
		}catch(Throwable t){
			return false;
		}
	}
	
	public boolean MaterialFactsDeclerationPage(){
		boolean retValue = true;
		Map<Object, Object> data_map = new HashMap<>();
		switch(common.currentRunningFlow){
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
		try{
			 customAssert.assertTrue(common.funcPageNavigation("Material Facts & Declarations", ""),"Material Facts and Declarations page is having issue(S)");
			 k.ImplicitWaitOff();
			 String q_value = null;
			 
			List<WebElement> elements = driver.findElements(By.className("selectinput"));
			 Select sel = null;
			
			 for(int i = 0;i<elements.size();i++){
				 if(elements.get(i).isDisplayed()){
					 
					 sel = new Select(elements.get(i));
					 try{
						 q_value = (String)data_map.get("MFD_Q"+(i+1));
						 
						 sel.selectByVisibleText(q_value);
						 }
					 catch(Throwable t){
						 
						 try{
							 sel.selectByVisibleText("No");
						 }catch(Throwable t1){
							 
						 }
						 
					 } 
				 }		 
			 }
			 
			 
			 
			 
			 if(k.isDisplayedField("MFD_PLMF")){
				 String[] geographical_Limits = ((String)data_map.get("MFD_PLMF")).split(",");
				 customAssert.assertTrue(k.Click("MFD_PLMF"),"Error while Clicking Geographic Limit List object . ");
				 List<WebElement> _prd_liability_MF = driver.findElements(By.xpath("//html//body//span//span//following::ul//li"));	
				 	for(String geo_limit : geographical_Limits){
						for(WebElement each_ul : _prd_liability_MF){
							//customAssert.assertTrue(k.Click("MFD_PLMF"),"Error while Clicking Geographic Limit List object . ");
							//each_ul.click();
							k.waitTwoSeconds();//*[text()=
							//System.out.println(each_ul.findElement(By.xpath("//*[text()='"+geo_limit+"']")).isDisplayed());
							if(each_ul.findElement(By.xpath("//*[text()='"+geo_limit+"']")).isDisplayed())
								each_ul.findElement(By.xpath("//*[text()='"+geo_limit+"']")).click();
							else
								continue;
							break;
						}
					}
			 	}
			 
					 
			 	List<WebElement> txt_Elements = driver.findElements(By.className("write"));
			 q_value = (String)data_map.get("MFD_T");
			 WebElement ws = null;
			 
			 for(int i = 0;i<txt_Elements.size();i++){
				 ws = txt_Elements.get(i);
				 if(ws.isDisplayed()){
					 ws.sendKeys(q_value);
				 }			 
			 }
			 
			 customAssert.assertTrue(common.funcButtonSelection("Save"), "Unable to click on Save Button on Material Facts and Declarations Screen .");
			 
			 return retValue;
			 
		}catch(Throwable t){
			k.ImplicitWaitOn();
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
	        return false;
			}
		finally{
			 k.ImplicitWaitOn();
		 }
		
	}
	
	public boolean funcClaimsExperience(Map<Object, Object> map_data){

	    boolean retvalue = true;
	    
	      try {    
	    	  	//customAssert.assertTrue(common.funcMenuSelection("Navigate", "Claims Experience"),"Unable To navigate to Claims Experience screen");
				customAssert.assertTrue(common.funcPageNavigation("Claims Experience",""), "Claims Experience Page Navigation issue . ");
				TestUtil.reportStatus("Verified Claims Experience page .", "Info", true);
				//customAssert.assertTrue(common.funcButtonSelection("Next"), "Unable to click on Next Button on Claims Experience .");
				return retvalue;
	             
	      } catch(Throwable t) {
	             String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	          TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
	          Assert.fail("Unable to enter details in ClaimsExperience Page", t);
	          return false;
	      }


	}
	
	public boolean funcPreviousClaims(Map<Object, Object> map_data){

	    boolean retvalue = true;
	    
	      try {    
	    	  	//customAssert.assertTrue(common.funcMenuSelection("Navigate", "Previous Claims"),"Unable To navigate to Previous Claims scren");
				customAssert.assertTrue(common.funcPageNavigation("Previous Claims",""), "Previous Claims Page Navigation issue . ");
				TestUtil.reportStatus("Verified Previous Claims page .", "Info", true);
				//customAssert.assertTrue(common.funcButtonSelection("next"), "Unable to click on Next Button on Previous Claims .");
				return retvalue;
	             
	      } catch(Throwable t) {
	             String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	          TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
	          Assert.fail("Unable to handle Previous Claims Page", t);
	          return false;
	      }


	}
	
	public int getAutoAdjustment(String cover_name)
	{
		
		Map<Object,Object> data_map = new HashMap<>();
		switch(common.currentRunningFlow){
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
		
		int autoAdjustment=0;
		OFC_Rater = OR.getORProperties();
		String key="";
		String diff="";
		try{
		if(at_differenceBetween_PreviousClaim_and_InceptionDate>5)
		{
			diff = "5";
		}
		else if(at_differenceBetween_PreviousClaim_and_InceptionDate>3)
		{
			diff = "3";
		}
		else if(at_differenceBetween_PreviousClaim_and_InceptionDate>1)
		{
			diff = "1";
		}
		else{
			diff = "0";
		}
		key = "OFC_AutoAdjust_ClaimExperience_notin"+diff;
		String no_of_premise="OFC_AutoAdjust_ClaimExperience_notin"+diff;
		autoAdjustment = autoAdjustment + Integer.parseInt(OFC_Rater.getProperty(key));
		if(at_global_no_of_property>4){
			no_of_premise = "more_than4";
		}
		else if(at_global_no_of_property>=2){
			no_of_premise = "between_2and4";
		}
		else{
			no_of_premise = "less_than2";
		}
		key = "OFC_AutoAdjust_Premises_"+no_of_premise; 
		OFC_Rater = OR.getORProperties();
		autoAdjustment = autoAdjustment + Integer.parseInt(OFC_Rater.getProperty(key));
		
		if(((String)data_map.get("CD_MaterialDamage")).equalsIgnoreCase("Yes")) {
			if(cover_name.equalsIgnoreCase("MD") || cover_name.equalsIgnoreCase("BI") || cover_name.equalsIgnoreCase("MONEY")){
				key = "OFC_AutoAdjust_24by7Trading_is_"+at_24by7;
				autoAdjustment = autoAdjustment + Integer.parseInt(OFC_Rater.getProperty(key));
				
				/*if(at_AlarmDescription.contains("NACOSS")){
					key = "OFC_AutoAdjust_Alarm_is_"+"NACOSS";
					autoAdjustment = autoAdjustment + Integer.parseInt(OFC_Rater.getProperty(key));
				}
				else if(at_AlarmDescription.contains("Redcare")){
					key = "OFC_AutoAdjust_Alarm_is_"+"Redcare";
					autoAdjustment = autoAdjustment + Integer.parseInt(OFC_Rater.getProperty(key));
				}*/
			}
		}
			if(cover_name.equalsIgnoreCase("MD") || cover_name.equalsIgnoreCase("EL") || cover_name.equalsIgnoreCase("PPL") || cover_name.equalsIgnoreCase("BI")){
				if(((String)data_map.get("CD_MaterialDamage")).equalsIgnoreCase("Yes")) {
					key = "OFC_AutoAdjust_Postcode_is_"+at_load;
					autoAdjustment = autoAdjustment + Integer.parseInt(OFC_Rater.getProperty(key));
				}
			
			}
		}
		catch(Exception e)
		{
			return 0;
		}
		
		return autoAdjustment;
		
	}
	public boolean MaterialDamagePage(Map<Object, Object> map_data){
		boolean retValue = true;
		
		Map<String, List<Map<String, String>>> internal_data_map = new HashMap<>();
		switch(common.currentRunningFlow){
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
		try{
				customAssert.assertTrue(common.funcPageNavigation("Insured Properties", ""),"Material Damage page is having issue(S)");
			 	int count = 0;
				int noOfProperties = 0;
				
				String[] properties = ((String)map_data.get("IP_AddProperty")).split(";");
	            noOfProperties = properties.length;
	           
	            
				if(!common.currentRunningFlow.equalsIgnoreCase("NB")){
					
					if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
					
					try {	
						
						
					switch((String)map_data.get("MTA_Operation")) {
		    	  	
			    	  case "AP":
			    	  case "RP":
			    		  
			    		  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
			    		  
			     		  if(!_cover.contains("MaterialDamage")) {
			    			  common.MTA_excel_data_map.put("PS_MaterialDamage_NetNetPremium", common_HHAZ.MD_Premium);
			    			  TestUtil.reportStatus("Material Damage Net Net Premium captured successfully . ", "Info", true);
			    			  return true;
			    		  }
			    		 break;
			    		 
			    	  case "Policy-level":
			    		  
			    		  //common.MTA_excel_data_map.put("PS_MaterialDamage_NetNetPremium", common_HHAZ.MD_Premium);
		    			  //TestUtil.reportStatus("Material Damage Net Net Premium captured successfully . ", "Info", true);
			    		  
			    		 break;
			    		 
			    	  case "Non-Financial":
			    		 
			    			  common.MTA_excel_data_map.put("PS_MaterialDamage_NetNetPremium", common_HHAZ.MD_Premium);
			    			  TestUtil.reportStatus("Due to Non-Financial Flow, Only Material Damage Net Net Premium captured  . ", "Info", true);
			    			  return true;
			    		
			    	  }
					}catch(NullPointerException npe) {
						
					}
					}
					
					  customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
			    }
				double md_premium[] = new double[noOfProperties];
				double stock_premium[] = new double[noOfProperties];
				double detStock_premium[] = new double[noOfProperties];
	            totalMD = 0.0;
	            double finalTotalMD = 0.0;
	            double finalTotalStock = 0.0;
	            double finalTotalDetStock = 0.0;
				while(count < noOfProperties ){
					p_Index = count;
					customAssert.assertTrue(k.Click("CCF_Btn_AddProperty"), "Unable to click Add Property Button on Insured Properties .");
					at_global_no_of_property++;
					customAssert.assertTrue(addProperty(map_data,count),"Error while adding insured proprty  .");
					TestUtil.reportStatus("Insured Property  <b>[  "+internal_data_map.get("Property Details").get(count).get("Automation Key")+"  ]</b>  added successfully . ", "Info", true);
					md_premium[count] = Double.parseDouble(k.getAttributeByXpath(".//*[@id='md9_tot']"));
					stock_premium[count] = Double.parseDouble(k.getAttributeByXpath(".//*[@id='st1_tot']"));
					detStock_premium[count] = Double.parseDouble(k.getAttributeByXpath(".//*[@id='st2_tot']"));
					customAssert.assertTrue(k.Click("CCF_Btn_Back"), "Unable to click on Back Button on Property Details .");					
					finalTotalMD = finalTotalMD + md_premium[count];
					finalTotalStock = finalTotalStock + stock_premium[count];
					finalTotalDetStock = finalTotalDetStock + detStock_premium[count];
					count++;
					
				}
				int count1 = 1;
				while((count1<5) && (count1<noOfProperties))
				{
					driver.findElement(By.xpath(".//*[@id='table0']//tbody//tr[1]//a["+count1+"]")).click();
					finalTotalMD = finalTotalMD - md_premium[count1-1];
					finalTotalStock = finalTotalStock - stock_premium[count1-1];
					finalTotalDetStock = finalTotalDetStock - detStock_premium[count1-1];
					customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
					md_premium[count1-1] = Double.parseDouble(k.getAttributeByXpath(".//*[@id='md9_tot']"));
					stock_premium[count1-1] = Double.parseDouble(k.getAttributeByXpath(".//*[@id='st1_tot']"));
					detStock_premium[count1-1] = Double.parseDouble(k.getAttributeByXpath(".//*[@id='st2_tot']"));
					finalTotalMD = finalTotalMD + md_premium[count1-1];
					finalTotalStock = finalTotalStock + stock_premium[count1-1];
					finalTotalDetStock = finalTotalDetStock + detStock_premium[count1-1];
					count1++;
				}
				TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Premium Summary", (String)map_data.get("Automation Key"), "PS_MaterialDamage_NetNetPremium", String.valueOf(finalTotalMD), map_data);
				TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Premium Summary", (String)map_data.get("Automation Key"), "PS_Stock-RiskItems_NetNetPremium", String.valueOf(finalTotalStock), map_data);
				TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Premium Summary", (String)map_data.get("Automation Key"), "PS_Frozen/RefrigeratedStock-RiskItems_NetNetPremium", String.valueOf(finalTotalDetStock), map_data);
				TestUtil.reportStatus("All the specified Insured properties added and verified successfully . ", "Info", true);
				
			 return retValue;
			 
		}catch(Throwable t){
			k.ImplicitWaitOn();
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
	        return false;
			}
		finally{
			 k.ImplicitWaitOn();
		 }
		
		}
	@SuppressWarnings("static-access")
	public boolean addProperty(Map<Object, Object> map_data,int count){
		
		boolean r_value=true;
		
		Map<String, List<Map<String, String>>> internal_data_map = new HashMap<>();
		switch(common.currentRunningFlow){
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
		
		try{
			
			//Property Details
			
			//System.out.println(i);
			String FILENAME = "E:\\test\\filename.txt";
			customAssert.assertTrue(k.Input("CCF_Address_CC_Address", internal_data_map.get("Property Details").get(count).get("PoD_Address")), "Unable to enter Address line 1 on Property Details screen.");
			int i = driver.findElements(By.xpath("//input")).size(); //this will provide the number of elements with mentioned type
			
			List<WebElement> AllInputTags = driver.findElements(By.cssSelector("input[type='text']"));
			for(WebElement e : AllInputTags){
				String p = e.getAttribute("name");
			}
			int j = driver.findElements(By.xpath("//*[@class='selectinput']")).size();
			List<WebElement> AllDropDownSelectionTags = driver.findElements(By.xpath("//*[@class='selectinput']"));
			customAssert.assertTrue(k.Input("CCF_Address_CC_line2", internal_data_map.get("Property Details").get(count).get("PoD_AddressL2")), "Unable to enter Address line 2 on Property Details screen.");
			customAssert.assertTrue(k.Input("CCF_Address_CC_line3", internal_data_map.get("Property Details").get(count).get("PoD_AddressL3")), "Unable to enter Address line 3 on Property Details screen.");
			
			customAssert.assertTrue(k.Input("CCF_Address_CC_Town", internal_data_map.get("Property Details").get(count).get("PoD_Town")), "Unable to enter Town on Property Details screen.");
			customAssert.assertTrue(k.Input("CCF_Address_CC_County", internal_data_map.get("Property Details").get(count).get("PoD_County")), "Unable to enter County on Property Details screen.");
			customAssert.assertTrue(k.Input("CCF_Address_CC_Postcode", internal_data_map.get("Property Details").get(count).get("PoD_Postcode")), "Unable to enter Postcode on Property Details screen.");
			at_load = internal_data_map.get("Property Details").get(count).get("PoD_Postcode");
			if(at_load.contains("ME"))
			{
				at_load = "ME";
			}
			else{
				at_load = "BT";
			}
			//Premises Details
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_24HourTrading", internal_data_map.get("Property Details").get(count).get("Premises_24HourTrading")), "Unable to select 24 Hour Trading on Property Details screen.");
			at_24by7 = internal_data_map.get("Property Details").get(count).get("Premises_24HourTrading");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_Unoccupiedgt30days", internal_data_map.get("Property Details").get(count).get("Premises_Unoccupiedgt30days")), "Unable to select Are buildings Unoccupied for greater than 30 days? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_StandAlone", internal_data_map.get("Property Details").get(count).get("Premises_StandAlone")), "Unable to select Do the premises stand alone? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_ConstructionWalls", internal_data_map.get("Property Details").get(count).get("Premises_ConstructionWalls")), "Unable to select Construction (Walls) on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_ConstructionRoofs", internal_data_map.get("Property Details").get(count).get("Premises_ConstructionRoofs")), "Unable to select Construction (Roof) on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_AutomatedCarWash", internal_data_map.get("Property Details").get(count).get("Premises_AutomatedCarWash")), "Unable to select Is there an Automated Car Wash? on Property Details screen.");
			
			//For PPL Cover Rating
			if(internal_data_map.get("Property Details").get(count).get("Premises_AutomatedCarWash").equalsIgnoreCase("Yes"))
				common_OFC.c_AUTOMATED_CAR_WASH++;
			
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_ATM", internal_data_map.get("Property Details").get(count).get("Premises_ATM")), "Unable to select Is there an ATM? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_DeepFatFrying", internal_data_map.get("Property Details").get(count).get("Premises_DeepFatFrying")), "Unable to select Do you have any Deep Fat Frying at the premises? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_OvergroundTanks", internal_data_map.get("Property Details").get(count).get("Premises_OvergroundTanks")), "Unable to select Are all over ground tanks 100% bunded? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_HSEGuidelines", internal_data_map.get("Property Details").get(count).get("Premises_HSEGuidelines")), "Unable to select Are HSE Guidelines complied with? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_HealthAndSafetyPolicy", internal_data_map.get("Property Details").get(count).get("Premises_HealthAndSafetyPolicy")), "Unable to select Is there a written Health and Safety Policy? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_FireExtinguishers", internal_data_map.get("Property Details").get(count).get("Premises_FireExtinguishers")), "Unable to select Fire extinguishers on maintenance contracts on Property Details screen.");
			
			//Security
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_MinimumStandardsProtection", internal_data_map.get("Property Details").get(count).get("Security_MinimumStandardsProtection")), "Unable to select Does the policyholder comply with the minimum standards of protection? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_CSMA", internal_data_map.get("Property Details").get(count).get("Security_CSMA")), "Unable to select Does the premises have a Central Station Monitor Alarm? on Property Details screen.");
			if(internal_data_map.get("Property Details").get(count).get("Security_CSMA").equalsIgnoreCase("Yes")){
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_LossAlarmDetails", internal_data_map.get("Property Details").get(count).get("Security_LossAlarmInstalledDescription")), "Unable to enter Details of Alarm (REDCARE etc) on Property Details screen.");
				at_AlarmDescription = internal_data_map.get("Property Details").get(count).get("Security_LossAlarmInstalledDescription");
			}
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_LossAlarmInstalled", internal_data_map.get("Property Details").get(count).get("Security_LossAlarmInstalled")), "Unable to select Is a sudden loss alarm installed? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_24HoursCCTV", internal_data_map.get("Property Details").get(count).get("Security_24HoursCCTV")), "Unable to select Is the premises covered by 24 hour CCTV? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_CCTVSystemFullRecorded", internal_data_map.get("Property Details").get(count).get("Security_CCTVSystemFullRecorded")), "Unable to select Is the CCTV system full recorded? on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_TimeRecording", internal_data_map.get("Property Details").get(count).get("Security_TimeRecording")), "Unable to enter How long are recordings kept for? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_ANPR", internal_data_map.get("Property Details").get(count).get("Security_ANPR")), "Unable to select Do you have Automatic Number Plate Recognition (ANPR) in operation? on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_NoOfCameras", internal_data_map.get("Property Details").get(count).get("Security_NoOfCameras")), "Unable to enter How many cameras do you have installed? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_RamRaidBollards", internal_data_map.get("Property Details").get(count).get("Security_RamRaidBollards")), "Unable to select Are there ram raid bollards in place? on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_RollerShutters", internal_data_map.get("Property Details").get(count).get("Security_RollerShutters")), "Unable to select Windows protected by Roller Shutters/Bars on Property Details screen.");
			
			//Material Damage
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_MD_Buildings", internal_data_map.get("Property Details").get(count).get("MD_Buildings(inclCanopy,Polesign,LandlordsFixtures&Fittings&TenantsImprovements)_DeclaredValue")),
					"Unable to enter Buildings (incl Canopy, Pole sign, Landlords Fixtures & Fittings & Tenants Improvements) on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_MD_Unoccupied", internal_data_map.get("Property Details").get(count).get("MD_Unocuppied_DeclaredValue")),
					"Unable to enter Unoccupied on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_MD_MachineryPlant", internal_data_map.get("Property Details").get(count).get("MD_MachineryPlant&AllOtherContents(inclPumps,JetWash&AutomatedCarwash)_DeclaredValue")),
					"Unable to enter Machinery Plant & All Other Contents (incl Pumps, Jet Wash & Automated Carwash) on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_MD_EBE", internal_data_map.get("Property Details").get(count).get("MD_ElectronicBusinessEquipment_DeclaredValue")),
					"Unable to enter Electronic Business Equipment on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_MD_UST", internal_data_map.get("Property Details").get(count).get("MD_UndergroundStorageTanks_DeclaredValue")),
					"Unable to enter Underground Storage Tanks on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_MD_OST", internal_data_map.get("Property Details").get(count).get("MD_OvergroundStorageTanksinclLPG_DeclaredValue")),
					"Unable to enter Overground Storage Tanks incl LPG on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_MD_LORP", internal_data_map.get("Property Details").get(count).get("MD_LossofRentPayable_DeclaredValue")),
					"Unable to enter Loss of Rent Payable on Property Details screen.");
			customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_MD_DeclarationUplift", internal_data_map.get("Property Details").get(count).get("MD_DeclarationUplift")), "Unable to select Declaration Uplift (%) on Property Details screen.");
			func_Add_Bespoke_Sum_Insured("MD",map_data,internal_data_map);
			
			//Stock
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_General", internal_data_map.get("Property Details").get(count).get("Stock_General_DeclaredValue")),
					"Unable to enter General on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_NAS", internal_data_map.get("Property Details").get(count).get("Stock_NonAttractiveStock_DeclaredValue")),
					"Unable to enter Non Attractive Stock on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_LPGCanisters", internal_data_map.get("Property Details").get(count).get("Stock_LPGCanisters&ContentsOver£500_DeclaredValue")),
					"Unable to enter LPG Canisters & Contents Over £500 on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_StockInOpen", internal_data_map.get("Property Details").get(count).get("Stock_StockinOpenover£2,500_DeclaredValue")),
					"Unable to enter Stock in Open over £2,500 on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_TobaccoAndAlcohol", internal_data_map.get("Property Details").get(count).get("Stock_Tobacco&Alcohol_DeclaredValue")),
					"Unable to enter Tobacco & Alcohol on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_FuelUnderground", internal_data_map.get("Property Details").get(count).get("Stock_FuelUnderground_DeclaredValue")),
					"Unable to enter Fuel Underground on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_FuelLPG", internal_data_map.get("Property Details").get(count).get("Stock_FuelLPG_DeclaredValue")),
					"Unable to enter Fuel LPG on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_AGPetrol", internal_data_map.get("Property Details").get(count).get("Stock_Fuelaboveground-Petrol_DeclaredValue")),
					"Unable to enter Fuel above ground - Petrol on Property Details screen.");
			customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_AGDiesel", internal_data_map.get("Property Details").get(count).get("Stock_Fuelaboveground-Diesel_DeclaredValue")),
					"Unable to enter Fuel above ground - Diesel on Property Details screen.");
			
			customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
			
			if(((String)map_data.get("CD_DeteriorationofFrozenRefrigeratedStock")).equals("Yes"))
			{
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_Stock_FrozenStockDeterioration", internal_data_map.get("Property Details").get(count).get("DeteriorationofRefrigerated/FrozenStock_DeclaredValue")),
						"Unable to enter Deterioration of Refrigerated/Frozen Stock on Property Details screen.");
			}
			//Business Interruption
			if(((String)map_data.get("CD_BusinessInterruption")).equals("Yes"))
			{
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_BI_GrossProfit", internal_data_map.get("Property Details").get(count).get("BI_GrossProfit")), "Unable to enter Gross Profit on Property Details screen.");
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_BI_RentRecievable", internal_data_map.get("Property Details").get(count).get("BI_RentRecievable")), "Unable to enter Rent Receivable on Property Details screen.");
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_BI_AdditionalICOW", internal_data_map.get("Property Details").get(count).get("BI_AdditionalICOW")), "Unable to enter Additional ICOW on Property Details screen.");
				customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_BI_DeclarationUplift", internal_data_map.get("Property Details").get(count).get("BI_DeclarationUplift")), "Unable to select Declaration Uplift (%) on Property Details screen.");
				customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_LOLRequired", internal_data_map.get("Property Details").get(count).get("BI_LOLRequired")), "Unable to select Loss of Licence Cover Required on Property Details screen.");
				customAssert.assertTrue(k.SelectRadioBtn("OFC_PropertyDetails_LOLSumInsured", internal_data_map.get("Property Details").get(count).get("BI_LossOfLicence")), "Unable to enter Loss of Licence Sum Insured on Property Details screen.");
			}
			
			//Money
			if(((String)map_data.get("CD_Money")).equals("Yes")) {
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_Money_SelfCarried", internal_data_map.get("Property Details").get(count).get("Money_SelfCarried")), "Unable to enter Self Carried on Property Details screen.");
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_Money_ThirdPartyCarrier", internal_data_map.get("Property Details").get(count).get("Money_ThirdPartyCarrier")), "Unable to enter Third Party Carrier on Property Details screen.");
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_Money_InTransitOnPremises", internal_data_map.get("Property Details").get(count).get("Money_InTransitOnPremises")), "Unable to enter Money in Transit/On Premises on Property Details screen.");
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_Money_LottoInstantsSumInsured", internal_data_map.get("Property Details").get(count).get("Money_LottoInstantsSumInsured")), "Unable to enter Lotto Instants Sum Insured on Property Details screen.");
				customAssert.assertTrue(k.Input("OFC_PropertyDetails_Money_SpecifiedSafe", internal_data_map.get("Property Details").get(count).get("Money_SpecifiedSafe")), "Unable to enter Money in Specified Safe (Outside of business hours) on Property Details screen.");
				customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_Money_SafeMake", internal_data_map.get("Property Details").get(count).get("Money_SafeMake")), "Unable to select Safe - Make on Property Details screen.");
			//	customAssert.assertTrue(k.DropDownSelection("OFC_PropertyDetails_Money_SafeModel", internal_data_map.get("Property Details").get(count).get("Money_SafeModel")), "Unable to select Safe - Model on Property Details screen.");
			}
			customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
			//Add Tech Adjust, Comm Adjust - MD
			customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Material Damage", "MD_"), "issue in Validate_AutoRatedTables function for Material Damage - Risk Items Cover");
			customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Stock - Risk Items", "STOCK_"), "issue in Validate_AutoRatedTables function for Stock - Risk Items Cover");
			
			if(((String)map_data.get("CD_DeteriorationofFrozenRefrigeratedStock")).equals("Yes")){
				customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Frozen/Refrigerated Stock - Risk Items", "DStock_"), "issue in Validate_AutoRatedTables function for Frozen/Refrigerated Stock - Risk Items Cover");
			}
			customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
			
			customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Insured Properties .");
			
			
			
			
			
		
		}catch(Throwable t){
			return false;
		}
		
		return r_value;
	}	

public int getIndexDropdownBasicBICovers(String optionValue) {
	
	HashMap<String, Integer> dropdownMap = new HashMap<>();

	dropdownMap.put("Additional increased costs of working", 0);
	dropdownMap.put("Declaration Linked", 1);
	dropdownMap.put("Flexible Limit of Loss", 2);
	dropdownMap.put("Gross Profit", 3);
	dropdownMap.put("Gross Revenue", 4);
	dropdownMap.put("Increased Cost of Working", 5);
	dropdownMap.put("Rent Receivable", 6);
	if(optionValue.contains("Additional")){return 0;}
	if(optionValue.contains("Declaration")){return 1;}
	return dropdownMap.get(optionValue);
}
public int getIndexDropdownAdditionalExtensions(String optionValue) {
	
	HashMap<String, Integer> dropdownMap = new HashMap<>();
	
	dropdownMap.put("Alternative Accommodation Expenses", 1);
	dropdownMap.put("Customers - Specified", 2);
	dropdownMap.put("Customers - Specified (Outside UK)", 3);
	dropdownMap.put("Customers - Unspecified (Outside UK)", 4);
	dropdownMap.put("Data Reinstatement", 5);
	dropdownMap.put("Denial Of Access (non-damage)", 6);
	dropdownMap.put("Discovery Of Vermin", 7);
	dropdownMap.put("Diseases, Murder, Suicide, Defective Sanitation", 8);
	dropdownMap.put("Exhibition Sites", 9);
	dropdownMap.put("Fines And Damages", 10);
	dropdownMap.put("Food And/or Drink Poisoning", 11);
	dropdownMap.put("Loss Of Attraction", 12);
	dropdownMap.put("Motor Vehicle Manufacturers", 13);
	dropdownMap.put("National Lottery Win", 14);
	dropdownMap.put("Patterns, Moulds, Templates Etc", 15);
	dropdownMap.put("Property In Transit", 16);
	dropdownMap.put("Replacement Of Essential Documents", 17);
	dropdownMap.put("Stored Property", 18);
	dropdownMap.put("Sub Postmasters Salaries", 19);
	dropdownMap.put("Suppliers - Specified", 20);
	dropdownMap.put("Suppliers - Specified (Outside UK)", 21);
	dropdownMap.put("Suppliers - Unspecified (Outside UK)", 22);
	if(optionValue.contains("Alternative Accommodation")){return 1;}
	if(optionValue.contains("Customers") && !optionValue.contains("Outside UK")){return 2;}
	return dropdownMap.get(optionValue);
	
}


public boolean MoneyPage(Map<Object, Object> map_data){
	boolean retValue = true;
	
	try{
			
		
			if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
				
				JavascriptExecutor j_exe = (JavascriptExecutor) driver;
	    		j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[@id='mn3_tot']")));
	    		common_HHAZ.MA_Premium = driver.findElement(By.xpath("//*[@id='mn3_tot']")).getAttribute("value");
				
				if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
				
				try {
				switch((String)map_data.get("MTA_Operation")) {
	    	  	
		    	  case "AP":
		    	  case "RP":
		    		  
		    		  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
		 			  
		    		  if(!_cover.contains("Money")) {
		    			  common.MTA_excel_data_map.put("PS_Money_NetNetPremium", common_HHAZ.MA_Premium);
		    			  TestUtil.reportStatus("Money Net Net Premium captured successfully . ", "Info", true);
		    			  return true;
		    		  }
		    		 break;
		    		 
		    	  case "Policy-level":
		    		  
		      		 break;
		    		 
		    	  case "Non-Financial":
		    		 
		    			  common.MTA_excel_data_map.put("PS_Money_NetNetPremium", common_HHAZ.MA_Premium);
		    			  TestUtil.reportStatus("Due to Non-Financial Flow, Only Money Net Net Premium captured  . ", "Info", true);
		    			  return true;
		    		
		    	  }
				}catch(NullPointerException npe) {
					
				}
				}
		    	  customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
			}
		
			customAssert.assertTrue(common.funcPageNavigation("Money", ""),"Money page is having issue(S)");
			
			customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button."); 
			
			map_data.put("MONEY_SelfCarried", k.getText("OFC_MONEY_SelfCarried").replaceAll(",", ""));
			map_data.put("MONEY_SingleLossLimit", k.getText("OFC_MONEY_MoneyInTransit").replaceAll(",", ""));
			map_data.put("MONEY_LottoInstants", k.getText("OFC_MONEY_LottoInstants").replaceAll(",", ""));
			map_data.put("MONEY_SpecifiedSafe", k.getText("OFC_MONEY_SpecifiedSafe").replaceAll(",", ""));
			
			customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Money", "MA_"), "Issue in Rating table validation for Money Cover . "); 
			TestUtil.reportStatus("All the details on money screen verified successfully . ", "Info", true);
			
		 return retValue;
		 
	}catch(Throwable t){
		k.ImplicitWaitOn();
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
        return false;
		}
	finally{
		 k.ImplicitWaitOn();
	 }
	
	}


public boolean PublicAndProductsLiabilityPage(Map<Object, Object> map_data){
	
	boolean retValue = true;
		
	try{
			customAssert.assertTrue(common.funcPageNavigation("Public and Products Liability", ""),"Public and Products Liability page is having issue(S)");
		 	
			if(!common.currentRunningFlow.equalsIgnoreCase("NB")){
				
					JavascriptExecutor j_exe = (JavascriptExecutor) driver;
					j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[@id='pl4_tot']")));
					common_HHAZ.PL_Premium = driver.findElement(By.xpath("//*[@id='pl4_tot']")).getAttribute("value");
				
				if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
	   			try {
				switch((String)map_data.get("MTA_Operation")) {
	    	  	
		    	  case "AP":
		    	  case "RP":
		    		  
		    		  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
		 			  
		    		  if(!_cover.contains("PublicandProductsLiability")) {
		    			  common.MTA_excel_data_map.put("PS_PublicandProductsLiability_NetNetPremium", common_HHAZ.PL_Premium);
		    			  TestUtil.reportStatus("Public and Products Liability Net Net Premium captured successfully . ", "Info", true);
		    			  return true;
		    		  }
		    		 break;
		    		 
		    	  case "Policy-level":
		    		  
		    		  	/*common.MTA_excel_data_map.put("PS_PublicLiability_NetNetPremium", common_HHAZ.PL_Premium);
		    		  	TestUtil.reportStatus("Public Liability Net Net Premium captured successfully . ", "Info", true);*/
		    		  
		    		 break;
		    		 
		    		 
		    	  case "Non-Financial":
		    		 
		    			 common.MTA_excel_data_map.put("PS_PublicandProductsLiability_NetNetPremium", common_HHAZ.PL_Premium);
		    			 TestUtil.reportStatus("Due to Non-Financial Flow, Only Public and Products Liability Net Net Premium captured  . ", "Info", true);
		    			 return true;
		    		
		    	  }
	   			}catch(NullPointerException npe) {
	   				
	   			}
				}
      		   	customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
		    }
			
			customAssert.assertTrue(k.Input("OFC_PPL_LimitofIndemnity", (String)map_data.get("PPL_LimitofIndemnity")), "Unable to enter Public Liability Indemnity Limit (GBP) on  Public and Products Liability screen.");
			if(((String)map_data.get("PPL_LimitofIndemnity")).equalsIgnoreCase("Other"))
				customAssert.assertTrue(k.Input("OFC_PPL_LOI_Other", (String)map_data.get("PPL_LOI_Other")), "Unable to enter Public Liability Indemnity Limit (GBP) for LOI Other value .");
			
			customAssert.assertTrue(k.Input("OFC_PPL_FuelSales", (String)map_data.get("PPL_FuelSales")), "Unable to enter Wages Pump Attendants/Cashiers Employers Liability screen.");
			customAssert.assertTrue(k.Input("OFC_PPL_RetailSales", (String)map_data.get("PPL_RetailSales")), "Unable to enter wages Other Shop Staff on Employers Liability screen.");
			customAssert.assertTrue(k.Input("OFC_PPL_AllOther", (String)map_data.get("PPL_AllOther")), "Unable to enter wages Working Partners on Employers Liability screen.");
			
			String total_turnover_ppl = k.getText("OFC_PPL_Total");
			map_data.put("PPL_Total", total_turnover_ppl);
			
			customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Insured Properties .");
            customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
            
            customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Public and Products Liability", "PPL_"), "Issue in validating rating table for Public and Products Liability Cover . ");
            
			TestUtil.reportStatus("All activies are added and verified successfully on Public and Products Liability screen. ", "Info", true);
			
		 return retValue;
		 
	}catch(Throwable t){
		k.ImplicitWaitOn();
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
        return false;
		}
	finally{
		 k.ImplicitWaitOn();
	 }
	
	}
public boolean EmployersLiabilityPage(Map<Object, Object> map_data){
	boolean retValue = true;
	
	Map<String, List<Map<String, String>>> internal_data_map = new HashMap<>();
	switch(common.currentRunningFlow){
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
	try{
			customAssert.assertTrue(common.funcPageNavigation("Employers Liability", ""),"Public Liability page is having issue(S)");
		 	
			if(!common.currentRunningFlow.equalsIgnoreCase("NB")){
				
				JavascriptExecutor j_exe = (JavascriptExecutor) driver;
	    		j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[@id='el5_tot']")));
	    		common_HHAZ.EL_Premium = driver.findElement(By.xpath("//*[@id='el5_tot']")).getAttribute("value");
				
				if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
					
				try {	
				switch((String)map_data.get("MTA_Operation")) {
	    	  	
		    	  case "AP":
		    	  case "RP":
		    		  
		    		  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
					  
		    		  if(!_cover.contains("EmployersLiability")) {
		    			  common.MTA_excel_data_map.put("PS_EmployersLiability_NetNetPremium", common_HHAZ.EL_Premium);
		    			  TestUtil.reportStatus("Employers Liability Net Net Premium captured successfully . ", "Info", true);
		    			  return true;
		    		  }
		    		 break;
		    		 
		    	  case "Policy-level":
		    		  
		    		  	/*common.MTA_excel_data_map.put("PS_EmployersLiability_NetNetPremium", common_HHAZ.EL_Premium);
		    		  	TestUtil.reportStatus("Employers Liability Net Net Premium captured successfully . ", "Info", true);*/
		    		  
		    		 break;
		    		 
		    	  case "Non-Financial":
		    		 
		    			 common.MTA_excel_data_map.put("PS_EmployersLiability_NetNetPremium", common_HHAZ.EL_Premium);
		    			 TestUtil.reportStatus("Due to Non-Financial Flow, Only Employers Liability Net Net Premium captured  . ", "Info", true);
		    			 return true;
		    		
		    	  }
				}catch(NullPointerException npe) {
					
				}
				}
        		customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
		    }
			
			customAssert.assertTrue(k.Input("OFC_EL_LimitofIndemnity", (String)map_data.get("EL_IndemnityLimit")), "Unable to enter Indemnity limit (£) on Employers Liability screen.");
			
			//Wages:
			customAssert.assertTrue(k.Input("OFC_EL_Wages_PumpAttendants", (String)map_data.get("EL_Wages_PumpAttendants")), "Unable to enter Wages Pump Attendants/Cashiers Employers Liability screen.");
			customAssert.assertTrue(k.Input("OFC_EL_Wages_OtherShopStaff", (String)map_data.get("EL_Wages_OtherShopStaff")), "Unable to enter wages Other Shop Staff on Employers Liability screen.");
			customAssert.assertTrue(k.Input("OFC_EL_Wages_WorkingPartners", (String)map_data.get("EL_Wages_WorkingPartners")), "Unable to enter wages Working Partners on Employers Liability screen.");
			customAssert.assertTrue(k.Input("OFC_EL_Wages_Handyman", (String)map_data.get("EL_Wages_Handyman")), "Unable to enter wages Handyman on Employers Liability screen.");
            
			//Numbers of Staff:
			customAssert.assertTrue(k.Input("OFC_EL_Staff_PumpAttendants", (String)map_data.get("EL_Staff_PumpAttendants")), "Unable to enter Staff Pump Attendants/Cashiers Employers Liability screen.");
			customAssert.assertTrue(k.Input("OFC_EL_Staff_OtherShopStaff", (String)map_data.get("EL_Staff_OtherShopStaff")), "Unable to enter Staff Other Shop Staff on Employers Liability screen.");
			customAssert.assertTrue(k.Input("OFC_EL_Staff_WorkingPartners", (String)map_data.get("EL_Staff_WorkingPartners")), "Unable to enter Staff Working Partners on Employers Liability screen.");
			customAssert.assertTrue(k.Input("OFC_EL_Staff_Handyman", (String)map_data.get("EL_Staff_Handyman")), "Unable to enter Staff Handyman on Employers Liability screen.");
			
			customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Insured Properties .");
			
			func_Add_Bespoke_Sum_Insured("EL", map_data, internal_data_map);
			
            customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
            customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Employers Liability", "EL_"), "issue in Validate_AutoRatedTables function for Employers Liability Cover");
			TestUtil.reportStatus("All activies are added and verified successfully on Employers Liability screen. ", "Info", true);
			
		 return retValue;
		 
	}catch(Throwable t){
		k.ImplicitWaitOn();
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
        return false;
		}
	finally{
		 k.ImplicitWaitOn();
	 }
	
	}
public boolean funcSpecifiedAllRisks(Map<Object, Object> map_data){
	
	boolean r_value= true;
	
	Map<String, List<Map<String, String>>> internal_data_map = new HashMap<>();
	switch(common.currentRunningFlow){
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
	
	try{
		
		customAssert.assertTrue(common.funcPageNavigation("Specified All Risks", ""),"Specified All Risks page navigations issue(S)");
		if(!common.currentRunningFlow.equalsIgnoreCase("NB")){
	    	  
	    	  JavascriptExecutor j_exe = (JavascriptExecutor) driver;
			  j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[contains(@id,'_tot')]")));
			  common_PEN.SAR_Premium = driver.findElement(By.xpath("//*[contains(@id,'_tot')]")).getAttribute("value");
	    	  
	    	  if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
	   	  
	    	  try {	  
	    		  switch((String)map_data.get("MTA_Operation")) {
	    	  	
	    		  case "AP":
	    		  case "RP":
	    		  
	    			  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
	    		  
	    		 	if(!_cover.contains("SpecifiedAllRisks")) {
	    		 		common.MTA_excel_data_map.put("PS_SpecifiedAllRisks_NP", common_PEN.SAR_Premium);
	    		 		TestUtil.reportStatus("Specified All Risks Net Premium captured successfully . ", "Info", true);
	    		 		return true;
	    		 	}
	    		 	break;
	    		 
	    		  case "Policy-level":
	    		  
	    			  break;
	    		 
	    		  case "Non-Financial":
	   		 
	    			  common.MTA_excel_data_map.put("PS_SpecifiedAllRisks_NP", common_PEN.SAR_Premium);
	    			  TestUtil.reportStatus("Due to Non-Financial Flow, Only Specified All Risks Net Premium captured  . ", "Info", true);
	    			  return true;
	    		
	    		  }
	    	  }catch(NullPointerException npe) {
					
				}
	    	  }
	    	  customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
	      }

		customAssert.assertTrue(k.Input("OFC_SAR_Laptops", (String)map_data.get("SAR_Laptops")), "Unable to enter Laptops on Specified All Risks screen.");
		customAssert.assertTrue(k.Input("OFC_SAR_Mobile", (String)map_data.get("SAR_MobilePhones")), "Unable to enter Mobile Phones on Specified All Risks screen.");
		customAssert.assertTrue(k.Input("OFC_SAR_Miscellaneous", (String)map_data.get("SAR_Miscellaneous")), "Unable to enter Miscellaneous on Specified All Risks screen.");
			
		customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Insured Properties .");
		
		func_Add_Bespoke_Sum_Insured("SAR", map_data, internal_data_map);
		
		customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
		
	    customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Specified All Risks", "SAR_"), "Issue in Validate_AutoRatedTables function for Specified All Risks Cover");
		TestUtil.reportStatus("Specified All Risks details are filled and verified sucessfully . ", "Info", true);
		
	}catch(Throwable t){
		return false;
		
	}
	
	return r_value;
	}
public boolean PersonalAccidentPage(Map<Object, Object> map_data){
	boolean retValue = true;
	
	
	try{
		
		if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
			
			JavascriptExecutor j_exe = (JavascriptExecutor) driver;
    		j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[@id='pa1_tot']")));
    		common_HHAZ.PAS_Premium = driver.findElement(By.xpath("//*[@id='pa1_tot']")).getAttribute("value");
			
			if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
				try {
			switch((String)map_data.get("MTA_Operation")) {
    	  	
	    	  case "AP":
	    	  case "RP":
	    		  
	    		  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
			  
	    		  if(!_cover.contains("PersonalAccident")) {
	    			  common.MTA_excel_data_map.put("PS_PersonalAccident_NetNetPremium", common_HHAZ.PAS_Premium);
	    			  TestUtil.reportStatus("Personal Accident Net Net Premium captured successfully . ", "Info", true);
	    			  return true;
	    		  }
	    		 break;
	    		 
	    	  case "Policy-level":
	    		  
    	  		  
	    		 break;
	    		 
	    	  case "Non-Financial":
	    		 
	    		  	 common.MTA_excel_data_map.put("PS_PersonalAccident_NetNetPremium", common_HHAZ.PAS_Premium);
	    			 TestUtil.reportStatus("Due to Non-Financial Flow, Only Personal Accident Net Net Premium captured  . ", "Info", true);
	    			 return true;
	    		
	    	  }
			}catch(NullPointerException npe) {
				
			}
			}
			customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
		}
		
		customAssert.assertTrue(common.funcPageNavigation("Personal Accident", ""),"Personal Accident page is having issue(S)");
	 	map_data.put("PA_MaximumAnyOneLoss_DV", k.getText("OFC_PA_DeclaredValue").replaceAll(",", ""));
		
	 	if(((String)map_data.get("CD_MaterialDamage")).equals("No")) // If MD is no then PA premium would come to zero, so added this condition
	 		map_data.put("PA_MaximumAnyOneLoss_PremiumOverride", "120"); // to generate non zero premium
           
       customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
       customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Personal Accident", "PA_"), "issue in Validate_AutoRatedTables function for Personal Accident Cover");
       TestUtil.reportStatus("All activies are added and verified successfully on Personal Accident screen. ", "Info", true);
			
       return retValue;
		 
	}catch(Throwable t){
		k.ImplicitWaitOn();
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
        return false;
		}
	finally{
		 k.ImplicitWaitOn();
	 }
	
	}



public boolean GoodsinTransitPage(Map<Object, Object> map_data){
	boolean retValue = true;
	
	try{
		
		if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
			
			JavascriptExecutor j_exe = (JavascriptExecutor) driver;
  			j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[@id='gt3_tot']")));
  		 	common_HHAZ.GIT_Premium = driver.findElement(By.xpath("//*[@id='gt3_tot']")).getAttribute("value");
			
			if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
			try {
			switch((String)map_data.get("MTA_Operation")) {
    	  	
	    	  case "AP":
	    	  case "RP":
	    		  
	    		  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
	    	  
	    		  if(!_cover.contains("GoodsinTransit")) {
	    			  common.MTA_excel_data_map.put("PS_GoodsinTransit_NetNetPremium", common_HHAZ.GIT_Premium);
	    			  TestUtil.reportStatus("Goods in Transit Net Net Premium captured successfully . ", "Info", true);
	    			  return true;
	    		  }
	    		 break;
	    		 
	    	  case "Policy-level":
	    		  
	    		  	/*common.MTA_excel_data_map.put("PS_GoodsinTransit_NetNetPremium", common_HHAZ.GIT_Premium);
	  			 	TestUtil.reportStatus("Goods in Transit Net Net Premium captured successfully . ", "Info", true);*/
	    		  
	    		 break;
	    		 
	    	  case "Non-Financial":
	    		 
	    			 common.MTA_excel_data_map.put("PS_GoodsinTransit_NetNetPremium", common_HHAZ.GIT_Premium);
	    			 TestUtil.reportStatus("Due to Non-Financial Flow, Only Goods in Transit Net Net Premium captured  . ", "Info", true);
	    			 return true;
	    	  }
			}catch(NullPointerException npe) {
				
				}
			}
			customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
		}
		
		customAssert.assertTrue(common.funcPageNavigation("Goods in Transit", ""),"Goods in Transit page is having issue(S)");
	 	
		customAssert.assertTrue(k.Input("OFC_GIT_Stock", (String)map_data.get("GIT_Stock")), "Unable to enter Stock in Trade (Own Vehicle) on  Goods in Transit.");
		customAssert.assertTrue(k.Input("OFC_GIT_NoOfVehicles", (String)map_data.get("GIT_NoOfVehicles")), "Unable to enter Number of vehicles on Goods in Transit.");
          
	    customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Insured Properties .");
        customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
        
         customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Goods in Transit", "GIT_"), "Issue in validating rating table for Goods In Transit Cover . ");
        TestUtil.reportStatus("All details are added and verified successfully on Goods in Transit screen. ", "Info", true);
			
        return retValue;
		 
	}catch(Throwable t){
		k.ImplicitWaitOn();
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
        return false;
		}
	finally{
		 k.ImplicitWaitOn();
	 }
	
	}

public boolean LegalExpensesPage(Map<Object, Object> map_data){
	boolean retValue = true;
	
	try{
		
		if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
			
				JavascriptExecutor j_exe = (JavascriptExecutor) driver;
				j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(".//*[@id='lg3_tot']")));
				common_HHAZ.LE_Premium = driver.findElement(By.xpath(".//*[@id='lg3_tot']")).getAttribute("value");
			
			if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
			try {
			switch((String)map_data.get("MTA_Operation")) {
    	  	
	    	  case "AP":
	    	  case "RP":
	    		  
	    		  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
			  
	    		  if(!_cover.contains("LegalExpenses")) {
	    			  common.MTA_excel_data_map.put("PS_LegalExpenses_NetNetPremium", common_HHAZ.LE_Premium);
	    			  TestUtil.reportStatus("Legal Expenses Net Net Premium captured successfully . ", "Info", true);
	    			  return true;
	    		  }
	    		 break;
	    		 
	    	  case "Policy-level":
	    		  
	    	 	  	/*common.MTA_excel_data_map.put("PS_LegalExpenses_NetNetPremium", common_HHAZ.LE_Premium);
	    	 	  	TestUtil.reportStatus("Legal Expenses Net Net Premium captured successfully . ", "Info", true);*/
	    		  
	    		 break;
	    		 
	    	  case "Non-Financial":
	    		 
	    			 common.MTA_excel_data_map.put("PS_LegalExpenses_NetNetPremium", common_HHAZ.LE_Premium);
	    			 TestUtil.reportStatus("Due to Non-Financial Flow, Only Legal Expenses Net Net Premium captured  . ", "Info", true);
	    			 return true;
	    	  }
			}catch(NullPointerException npe) {
				
			}
			}
			customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
		}
		
		customAssert.assertTrue(common.funcPageNavigation("Legal Expenses", ""),"Legal Expenses page is having issue(S)");
	 	
		map_data.put("LE_Turnover", k.getText("OFC_LE_Turnover").replaceAll(",", ""));
		
		customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
		
		customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Legal Expenses", "LE_"), "Issue in validating rating table for Legal Expenses Cover . ");
		
        TestUtil.reportStatus("All details are verified successfully on Legal Expenses screen . ", "Info", true);
			
        return retValue;
		 
	}catch(Throwable t){
		k.ImplicitWaitOn();
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
        return false;
		}
	finally{
		 k.ImplicitWaitOn();
	 }
	
	}
public boolean TerrorismPage(Map<Object, Object> map_data){
	boolean retValue = true;
	
	Map<String, List<Map<String, String>>> internal_data_map = new HashMap<>();
	switch(common.currentRunningFlow){
		case "NB":
			internal_data_map = common.NB_Structure_of_InnerPagesMaps;
			break;
		case "MTA":
			internal_data_map = common.MTA_Structure_of_InnerPagesMaps;
			break;
		case "Renewal":
			internal_data_map = common.Renewal_Structure_of_InnerPagesMaps;
			break;
		case "Requote":
			internal_data_map = common.Requote_Structure_of_InnerPagesMaps;
			break;
		case "Rewind":
			internal_data_map = common.Rewind_Structure_of_InnerPagesMaps;
			break;
	
	}
	
	try{
		
		if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
			
				JavascriptExecutor j_exe = (JavascriptExecutor) driver;
				j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(".//*[@id='tr5_tot']")));
				common_HHAZ.TER_Premium = driver.findElement(By.xpath(".//*[@id='tr5_tot']")).getAttribute("value");
			
			if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
			try {		
			switch((String)map_data.get("MTA_Operation")) {
    	  	
	    	  case "AP":
	    	  case "RP":
	    		  
	    		  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
	    	  
	    		  if(!_cover.contains("Terrorism")) {
	    			  common.MTA_excel_data_map.put("PS_Terrorism_NetNetPremium", common_HHAZ.TER_Premium);
	    			  TestUtil.reportStatus("Terrorism Net Net Premium captured successfully . ", "Info", true);
	    			  return true;
	    		  }
	    		 break;
	    		 
	    	  case "Policy-level":
	    		  
		    	break;
	    		 
	    	  case "Non-Financial":
	    		 
	    			  common.MTA_excel_data_map.put("PS_Terrorism_NetNetPremium", common_HHAZ.TER_Premium);
	    			  TestUtil.reportStatus("Due to Non-Financial Flow, Only Terrorism Net Net Premium captured  . ", "Info", true);
	    			  return true;
	    	  }
			}catch(NullPointerException npe) {
				
			}
			}
			customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
		}
		
		customAssert.assertTrue(common.funcPageNavigation("Terrorism", ""),"Terrorism page is having issue(S)");
		
		customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
	  
		map_data.put("TER_MaterialDamage", k.getAttribute("OFC_TER_MD_Turnover","value").replaceAll(",", ""));
		map_data.put("TER_BusinessInterruption", k.getAttribute("OFC_TER_BI_Turnover","value").replaceAll(",", ""));
	
	   	customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Terrorism", "TER_"), "Issue in validating rating table for Terrorism Cover . ");
	      
	    TestUtil.reportStatus("All activies are added and verified successfully on Terrorism screen. ", "Info", true);
			
       return retValue;
		 
	}catch(Throwable t){
		k.ImplicitWaitOn();
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
        return false;
		}
	finally{
	 }
	
	}

public boolean funcBusinessInterruption(Map<Object, Object> map_data){
	boolean retValue = true;
		
		Map<String, List<Map<String, String>>> internal_data_map = null;
		switch(common.currentRunningFlow){
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
		try{
			
			customAssert.assertTrue(common.funcPageNavigation("Business Interruption", ""),"Business Interruption page navigations issue(S)");
			
		      if(!common.currentRunningFlow.equalsIgnoreCase("NB")){
		    	  
		    	  JavascriptExecutor j_exe = (JavascriptExecutor) driver;
    			  j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[@id='bi4_tot']")));
    			  common_HHAZ.BI_Premium = driver.findElement(By.xpath("//*[@id='bi4_tot']")).getAttribute("value");
		    	  
		    	  if(TestBase.businessEvent.equalsIgnoreCase("MTA") && ((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")){
		   	  
		    	  try {	  
		    	  switch((String)map_data.get("MTA_Operation")) {
		    	  	
		    	  case "AP":
		    	  case "RP":
		    		  
		    		  String _cover = common_EP.AP_RP_Cover_Key.split("-")[1];
		    		  
		    		  if(!_cover.contains("BusinessInterruption")) {
		    			  common.MTA_excel_data_map.put("PS_BusinessInterruption_NetNetPremium", common_HHAZ.BI_Premium);
		    			  TestUtil.reportStatus("Business Interruption Net Net Premium captured successfully . ", "Info", true);
		    			  return true;
		    		  }
		    		 break;
		    		 
		    	  case "Policy-level":
		    		  
		    		 break;
		    		 
		    	  case "Non-Financial":
		   		 
	    			 common.MTA_excel_data_map.put("PS_BusinessInterruption_NetNetPremium", common_HHAZ.BI_Premium);
		    		 TestUtil.reportStatus("Due to Non-Financial Flow, Only Business Interruption Net Net Premium captured  . ", "Info", true);
		    		 return true;
		    		
		    	  }
		    	  }catch(NullPointerException npe) {
						
					}
		    	  }
		    	  customAssert.assertTrue(common_HHAZ.deleteItems(),"Delete Items function is having issues.");
		      }

		    //Business Interruption
		    customAssert.assertTrue(k.DropDownSelection("OFC_BI_IndemnityPeriod",(String)map_data.get("BI_IndemnityPeriod")), "Unable to BI Indemnity Period .");
		    customAssert.assertTrue(k.Input("OFC_BI_UnspecifiedCustomersExtension", (String)map_data.get("BI_UnspecifiedCustomersExtension")),"Unable to enter value in Unspecified Customers Extension . ");
			customAssert.assertTrue(k.Input("OFC_BI_UnspecifiedSuppliersExtension", (String)map_data.get("BI_UnspecifiedSuppliersExtension")),"Unable to enter value in Unspecified Suppliers Extension . ");
			customAssert.assertTrue(k.Input("OFC_BI_DenialOfAccess", (String)map_data.get("BI_DenialofAccess")),"Unable to enter value in Denial of Access Limit . ");
			customAssert.assertTrue(k.Input("OFC_BI_PublicUtilitiesLimit", (String)map_data.get("BI_PublicUtilitiesLimit")),"Unable to enter value in Public Utilities Limit . ");
			customAssert.assertTrue(k.Input("OFC_BI_BookDebtLimit", (String)map_data.get("BI_BookDebtLimit")),"Unable to enter value in Book Debt Limit . ");
			customAssert.assertTrue(k.Input("OFC_BI_ContractSites", (String)map_data.get("BI_ContractSites")),"Unable to enter value in Contract Sites . ");
			
			func_Add_Bespoke_Sum_Insured("BI",map_data,internal_data_map);
			
			customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Insured Properties .");
            customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");                        				
			
			customAssert.assertTrue(funcValidate_AutoRatedTables(map_data, "Business Interruption", "BI_"), "Issue in Rating table validation for business interruption Cover . ");
			
			TestUtil.reportStatus("Business Interruption details are filled & verified successfully . ", "Info", true);
			return retValue;
			
		}catch(Throwable t){
			return false;
		}
}

public boolean func_Add_Bespoke_Sum_Insured(String coverName,Map<Object, Object> map_data,Map<String, List<Map<String, String>>> internal_data_map) {
	
	try {
		
		//Add Additional cover add bespoke sum insured : 
		String[] bespoke_items = ((String)map_data.get(coverName+"_AddBespSumIns")).split(";");
        int no_of_bespoke = bespoke_items.length;
        int no = 0;
        
        while(no < no_of_bespoke ){
        	
			k.ImplicitWaitOff();
        	WebElement add_bespoke_btn = driver.findElement(By.xpath("//*[text()='Add Bespoke Sum Insured']"));
        	JavascriptExecutor j_exe = (JavascriptExecutor) driver;
			j_exe.executeScript("arguments[0].scrollIntoView(true);", add_bespoke_btn);
        	add_bespoke_btn.click();
        	String key = "AddBespokeSumIns"+coverName;
        	
        	switch(coverName) {
        	
        	case "MD":
        		
       		 	customAssert.assertTrue(k.Input("OFC_PropertyDetails_MD_BSI_Description", (String)internal_data_map.get(key).get(no).get("MD_BSI_Description")),"Unable to enter value MD Bespoke description field . ");
       		 	customAssert.assertTrue(k.Input("OFC_PropertyDetails_MD_BSI_SumInsured", (String)internal_data_map.get(key).get(no).get("MD_BSI_SumInsured")),"Unable to enter value MD Bespoke sum insured field . ");
       		 	break;
       		 	
        	case "BI":
        		
        		 customAssert.assertTrue(k.Input("OFC_BI_BSI_Description", (String)internal_data_map.get(key).get(no).get("BI_BSI_Description")),"Unable to enter value BI Bespoke description field . ");
        		 customAssert.assertTrue(k.Input("OFC_BI_BSI_SumInsured", (String)internal_data_map.get(key).get(no).get("BI_BSI_SumInsured")),"Unable to enter value BI Bespoke sum insured field . ");
        		 customAssert.assertTrue(k.DropDownSelection("OFC_BI_BSI_DeclarationUplift", (String)internal_data_map.get(key).get(no).get("BI_BSI_DeclarationUplift")),"Unable to enter value BI Bespoke declaration uplift field . ");
        		break;
        	case "EL":
        		
        		customAssert.assertTrue(k.Input("OFC_EL_BSI_Description", (String)internal_data_map.get(key).get(no).get("EL_BSI_Description")),"Unable to enter value EL Bespoke description field . ");
        		customAssert.assertTrue(k.Input("OFC_EL_BSI_Details", (String)internal_data_map.get(key).get(no).get("EL_BSI_Details")),"Unable to enter value EL Bespoke Details field . ");
        		customAssert.assertTrue(k.Input("OFC_EL_BSI_NumbersofStaff", (String)internal_data_map.get(key).get(no).get("EL_BSI_NumbersofStaff")),"Unable to enter value EL Bespoke NumbersofStaff field . ");
        		customAssert.assertTrue(k.Input("OFC_EL_BSI_Suminsured", (String)internal_data_map.get(key).get(no).get("EL_BSI_Suminsured")),"Unable to enter value EL Bespoke sum insured field . ");
       		 	break;
        	case "SAR":
        		
        	 	customAssert.assertTrue(k.Input("OFC_SAR_BSI_Description", (String)internal_data_map.get(key).get(no).get("SAR_BSI_Description")),"Unable to enter value SAR Bespoke description field . ");
       		 	customAssert.assertTrue(k.Input("OFC_SAR_BSI_SumInsured", (String)internal_data_map.get(key).get(no).get("SAR_BSI_SumInsured")),"Unable to enter value SAR Bespoke sum insured field . ");
       		 	break;
        	
        	}
            
             customAssert.assertTrue(k.clickInnerButton("Inner_page_locator", "Save"), "Unable to click on Inner button.");
            
            TestUtil.reportStatus("Bespoke Sum Insured <b> [ "+internal_data_map.get("AddBespokeSumInsBI").get(no).get("BI_BSI_Description")+ "] </b> has been added succefully .", "Info", true);
             
            no++;
		}
        
        TestUtil.reportStatus("All the Bespoke sum insured items are added succefully .", "Info", true);
		
		return true;
	}catch(org.openqa.selenium.NoSuchElementException ne) {
		System.out.println("Add Bespoke Sum Insured button is not visible"+ne.getMessage());
		return false;
	}catch(Throwable t) {
		return false;
	}
	finally {
		k.ImplicitWaitOn();
	}
	
	
}



public void RewindFlow(String code,String event) throws ErrorInTestMethod{
	String testName = (String)common.Rewind_excel_data_map.get("Automation Key");
	try{
		
		if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes")) {
			customAssert.assertTrue(common_EP.ExistingPolicyAlgorithm(common.Rewind_excel_data_map,(String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type"), (String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Status")), "Existing Policy Algorithm function is having issues. ");
		}else {
			CommonFunction_HHAZ.AdjustedTaxDetails.clear();
			if(!common.currentRunningFlow.equalsIgnoreCase("Renewal") && !common.currentRunningFlow.equalsIgnoreCase("MTA")){
				NewBusinessFlow(code,"NB");
			}
			common_HHAZ.CoversDetails_data_list.clear();
			common_CCD.MD_Building_Occupancies_list.clear();
			common_HHAZ.PremiumFlag = false;
		}
		
		common.currentRunningFlow="Rewind";
		String navigationBy = CONFIG.getProperty("NavigationBy");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		customAssert.assertTrue(common.funcRewind());
		
		TestUtil.reportStatus("<b> -----------------------Rewind flow started---------------------- </b>", "Info", false);
		
		//Existing Policy search condition for Rewind on MTA or Rewind on NB.
		if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type")).equalsIgnoreCase("Endorsement") && ((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes")){
			customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
			customAssert.assertTrue(common.funcSearchPolicy(common.MTA_excel_data_map), "Policy Search function is having issue(S) . ");
			customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Rewind_excel_data_map,code,event,"Endorsement Submitted (Rewind)"), "Verify Policy Status (Endorsement Submitted (Rewind)) function is having issue(S) . ");
			
		}else{
			customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
			customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
			if(TestBase.businessEvent.equalsIgnoreCase("MTA")){
				customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Rewind_excel_data_map,code,event,"Endorsement Submitted (Rewind)"), "Verify Policy Status (Endorsement Submitted (Rewind)) function is having issue(S) . ");
			}else{
				customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Rewind_excel_data_map,code,event,"Submitted (Rewind)"), "Verify Policy Status (Submitted (Rewind)) function is having issue(S) . ");
			}
		}
			
		customAssert.assertTrue(funcPolicyGeneral(common.Rewind_excel_data_map,code,event), "Policy Details function having issue .");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Covers"),"Issue while Navigating to Covers  . ");
		customAssert.assertTrue(common_HHAZ.funcCovers(common.Rewind_excel_data_map), "Select covers function is having issue(S) . ");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts & Declarations"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
		/*customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Previous Claims"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcPreviousClaims(common.Rewind_excel_data_map), "Previous claim function is having issue(S) .");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Claims Experience"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcClaimsExperience(common.Rewind_excel_data_map), "Previous claim function is having issue(S) .");
		*/
		//Non-linear cover selection
		customAssert.assertTrue(Cover_Selection_By_Sequence(common.Rewind_excel_data_map), "Cover selection by sequence function is having issue(S) .");
		
		
		//customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Endorsements"),"Issue while Navigating to Property Owners Liability screen.");
		//customAssert.assertTrue(common_HHAZ.funcEndorsementOperations(common.Rewind_excel_data_map),"Endorsement Operation is having issue(S).");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		if(TestBase.businessEvent.equalsIgnoreCase("MTA")){
			customAssert.assertTrue(common_HHAZ.funcPremiumSummary_MTA(common.Rewind_excel_data_map,code,event), "Rewind MTA Premium Summary in function is having issue(S) . ");
		}else if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy_Type")).equalsIgnoreCase("Endorsement") && ((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes")){
			customAssert.assertTrue(common_HHAZ.funcPremiumSummary_MTA(common.Rewind_excel_data_map,code,event), "Rewind MTA Premium Summary in function is having issue(S) . ");
		}else{
			customAssert.assertTrue(common_HHAZ.funcPremiumSummary(common.Rewind_excel_data_map,code,event), "Premium Summary function is having issue(S) . ");
		}
		if(!TestBase.businessEvent.equalsIgnoreCase("Renewal") && !TestBase.businessEvent.equalsIgnoreCase("MTA")){
			customAssert.assertTrue(common_HHAZ.funcStatusHandling(common.Rewind_excel_data_map,code,event));
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


public void RequoteFlow(String code,String event) throws ErrorInTestMethod{
	String testName = (String)common.Requote_excel_data_map.get("Automation Key");
	try{
		
		if(!common.currentRunningFlow.equalsIgnoreCase("Renewal")){
			NewBusinessFlow(code,"NB");
		}
		common.currentRunningFlow="Requote";
		common_HHAZ.CoversDetails_data_list.clear();
		String navigationBy = CONFIG.getProperty("NavigationBy");
		common_HHAZ.PremiumFlag = false;
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		customAssert.assertTrue(common.funcButtonSelection("Re-Quote"));
		
		TestUtil.reportStatus("<b> -----------------------Requote flow is started---------------------- </b>", "Info", false);
		
		customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
		customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
		customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Requote_excel_data_map,code,event,"Submitted"), "Verify Policy Status (Submitted (Rewind)) function is having issue(S) . ");
		
		customAssert.assertTrue(funcPolicyGeneral(common.Requote_excel_data_map,code,event), "Policy Details function having issue .");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Covers"),"Issue while Navigating to Covers  . ");
		customAssert.assertTrue(common_HHAZ.funcCovers(common.Requote_excel_data_map), "Select covers function is having issue(S) . ");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts and Declarations"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Previous Claims"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcPreviousClaims(common.Requote_excel_data_map), "Previous claim function is having issue(S) .");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Claims Experience"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcClaimsExperience(common.Requote_excel_data_map), "Previous claim function is having issue(S) .");
		if(((String)common.Requote_excel_data_map.get("CD_MaterialDamage")).equals("Yes")){	
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Damage"),"Issue while Navigating to Material Damage screen.");
			customAssert.assertTrue(MaterialDamagePage(common.Requote_excel_data_map), "Material DamagePage function is having issue(S) . ");
		}	
		if(((String)common.Requote_excel_data_map.get("CD_BusinessInterruption")).equals("Yes")){		
			customAssert.assertTrue(common.funcMenuSelection("Navigate","Business Interruption"),"Issue while Navigating to Business Interruption  . ");
			customAssert.assertTrue(funcBusinessInterruption(common.Requote_excel_data_map), "Business Interruption function is having issue(S) . ");
		}
		if(((String)common.Requote_excel_data_map.get("CD_Money&Assault")).equals("Yes")){	
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Money & Assault"),"Issue while Navigating to Money & Assault screen.");
			customAssert.assertTrue(MoneyPage(common.Requote_excel_data_map), "Material DamagePage function is having issue(S) . ");
		}
		if(((String)common.Requote_excel_data_map.get("CD_EmployersLiability")).equals("Yes")){	
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Employers Liability"),"Issue while Navigating to Employers Liability screen.");
			customAssert.assertTrue(EmployersLiabilityPage(common.Requote_excel_data_map), "EmployersLiabilityPage function is having issue(S) . ");
		}
		if(((String)common.Requote_excel_data_map.get("CD_PublicLiability")).equals("Yes")){	
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Public Liability"),"Issue while Navigating to Public Liability screen.");
			customAssert.assertTrue(PublicAndProductsLiabilityPage(common.Requote_excel_data_map), "Public Liability function is having issue(S) . ");
		}
		if(((String)common.Requote_excel_data_map.get("CD_GoodsInTransit")).equals("Yes")){	
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Goods in Transit"),"Issue while Navigating to Goods in Transit screen.");
			customAssert.assertTrue(GoodsinTransitPage(common.Requote_excel_data_map), "Personal Accident Optional function is having issue(S) . ");
		}
		if(((String)common.Requote_excel_data_map.get("CD_LegalExpenses")).equals("Yes")){	
			customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Legal Expenses"),"Issue while Navigating to Legal Expenses screen.");
			customAssert.assertTrue(LegalExpensesPage(common.Requote_excel_data_map), "Personal Accident Optional function is having issue(S) . ");
		}
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Endorsements"),"Issue while Navigating to Property Owners Liability screen.");
		customAssert.assertTrue(common_HHAZ.funcEndorsementOperations(common.Requote_excel_data_map),"Insurance tax adjustment is having issue(S).");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		customAssert.assertTrue(common_HHAZ.funcPremiumSummary(common.Requote_excel_data_map,code,event), "Premium Summary function is having issue(S) . ");
		customAssert.assertTrue(common_HHAZ.funcStatusHandling(common.Requote_excel_data_map,code,event));
		
	}catch(Throwable t){
		TestUtil.reportTestCaseFailed(testName, t);
		throw new ErrorInTestMethod(t.getMessage());
	}
	
	
	
}


public void RenewalFlow(String code,String fileName) throws ErrorInTestMethod{
	String testName = (String)common.Renewal_excel_data_map.get("Automation Key");
	try{
		
		common.currentRunningFlow="Renewal";
		String navigationBy = CONFIG.getProperty("NavigationBy");
		
		customAssert.assertTrue(common.StingrayLogin("PEN"),"Unable to login.");
		customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
		customAssert.assertTrue(common_CCJ.renewalSearchPolicyNEW(common.Renewal_excel_data_map), "Policy Search function is having issue(S) . ");
		customAssert.assertTrue(common.funcVerifyPolicyStatus_Renewal(common.Renewal_excel_data_map,CommonFunction.product,CommonFunction.businessEvent,"Renewal Pending"), "Verify Policy Status (Submitted (Rewind)) function is having issue(S) . ");
		
		if(!common_HHAZ.isAssignedToUW){ // This variable is initialized in common_CCJ.renewalSearchPolicyNEW function
			customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"));
			customAssert.assertTrue(common_SPI.funcAssignPolicyToUW());
		}
		
		customAssert.assertTrue(common.funcButtonSelection("Send to Underwriter"));
		customAssert.assertTrue(common.funcMenuSelection("Policies", ""),"");
		customAssert.assertTrue(common.funcSearchPolicy_Renewal(common.Renewal_excel_data_map), "Policy Search function is having issue(S) . ");
		customAssert.assertTrue(common.funcVerifyPolicyStatus_Renewal(common.Renewal_excel_data_map,CommonFunction.product,CommonFunction.businessEvent,"Renewal Submitted"), "Verify Policy Status (Renewal Submitted) function is having issue(S) . ");
		
		customAssert.assertTrue(funcPolicyGeneral(common.Renewal_excel_data_map,code,"Renewal"), "Policy Details function having issue .");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Covers"),"Issue while Navigating to Covers  . ");
		customAssert.assertTrue(common_HHAZ.funcCovers(common.Renewal_excel_data_map), "Select covers function is having issue(S) . ");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts & Declarations"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
		/*customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Previous Claims"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcPreviousClaims(common.Renewal_excel_data_map), "Previous claim function is having issue(S) .");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Claims Experience"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcClaimsExperience(common.Renewal_excel_data_map), "Previous claim function is having issue(S) .");
		*/
		//Non-linear cover selection
		customAssert.assertTrue(Cover_Selection_By_Sequence(common.Renewal_excel_data_map), "Cover selection by sequence function is having issue(S) .");
		
		/*customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Endorsements"),"Issue while Navigating to Property Owners Liability screen.");
		customAssert.assertTrue(common.deleteEndorsement(), "Unable to delete endorsement from Endorsement screen.");
		customAssert.assertTrue(common_HHAZ.funcEndorsementOperations(common.Renewal_excel_data_map),"Insurance tax adjustment is having issue(S).");
		*/
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		customAssert.assertTrue(common_HHAZ.funcPremiumSummary(common.Renewal_excel_data_map,code,"Renewal"), "Premium Summary function is having issue(S) . ");
		Assert.assertTrue(common_HHAZ.funcStatusHandling(common.Renewal_excel_data_map,code,"Renewal"));
	
		TestUtil.reportTestCasePassed(testName);
	
	}catch (ErrorInTestMethod e) {
		System.out.println("Error in New Business test method for MTA > "+testName);
		throw e;
	}catch(Throwable t){
		TestUtil.reportTestCaseFailed(testName, t);
		throw new ErrorInTestMethod(t.getMessage());
	}
	
	
	
}


public void RenewalRewindFlow(String code,String event) throws ErrorInTestMethod{
	String testName = (String)common.Rewind_excel_data_map.get("Automation Key");
	try{
		
		CommonFunction_HHAZ.AdjustedTaxDetails.clear();
		common.currentRunningFlow="Rewind";
		common_HHAZ.CoversDetails_data_list.clear();
		String navigationBy = CONFIG.getProperty("NavigationBy");
		common_HHAZ.PremiumFlag = false;
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		customAssert.assertTrue(common.funcRewind());
		
		TestUtil.reportStatus("<b> -----------------------Renewal Rewind flow started---------------------- </b>", "Info", false);
		
		customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
		customAssert.assertTrue(common.funcSearchPolicy(common.Renewal_excel_data_map), "Policy Search function is having issue(S) . ");
		customAssert.assertTrue(common.funcVerifyPolicyStatus(common.Rewind_excel_data_map,code,event,"Renewal Submitted (Rewind)"), "Verify Policy Status (Submitted (Rewind)) function is having issue(S) . ");
		
		customAssert.assertTrue(funcPolicyGeneral(common.Rewind_excel_data_map,code,event), "Policy Details function having issue .");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Covers"),"Issue while Navigating to Covers  . ");
		customAssert.assertTrue(common_HHAZ.funcCovers(common.Rewind_excel_data_map), "Select covers function is having issue(S) . ");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Material Facts & Declarations"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(MaterialFactsDeclerationPage(), "MaterialFactsDeclerationPage function is having issue(S) . ");
		/*customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Previous Claims"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcPreviousClaims(common.Rewind_excel_data_map), "Previous claim function is having issue(S) .");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Claims Experience"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcClaimsExperience(common.Rewind_excel_data_map), "Previous claim function is having issue(S) .");
		*/
		customAssert.assertTrue(Cover_Selection_By_Sequence(common.Rewind_excel_data_map), "Cover selection by sequence function is having issue(S) .");
		//customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Endorsements"),"Issue while Navigating to Property Owners Liability screen.");
		//customAssert.assertTrue(common_HHAZ.funcEndorsementOperations(common.Rewind_excel_data_map),"Insurance tax adjustment is having issue(S).");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		customAssert.assertTrue(common_HHAZ.funcPremiumSummary(common.Rewind_excel_data_map,code,event), "Premium Summary function is having issue(S) . ");
		
		
	}catch(Throwable t){
		TestUtil.reportTestCaseFailed(testName, t);
		throw new ErrorInTestMethod(t.getMessage());
	}
	
	
	
}

//*************************START***********************************//
	//************************************************************//
	//**** CCD Book Rate Calculator Based on QBECC Sheet ********//
	//************************************************************//
	//************************************************************//
	
	public double get_Book_Rate_from_Properties(String _Activity,String DV,String cover){
		
		double r_value=0.0;
		DecimalFormat formatter = new DecimalFormat("###.###");
		//Drain_Rodding_–_incl._repair
		try{
			OFC_Rater = OR.getORProperties();
			String t_activity = _Activity.replaceAll("–", "").replaceAll(" ", "_").replaceAll(",", "").replaceAll("__", "_");
			if(_Activity.contains("Pump "))
				t_activity = "OFC_"+t_activity + "_"+cover+"_BR"+DV;
			else
				t_activity = "OFC_"+t_activity + "_"+cover+"_BR";
			//OFC_Pump_Attendants/Cashiers/Other_Shop_Staff_EL_BR_200k
		
			r_value = Double.parseDouble(OFC_Rater.getProperty(t_activity));
			r_value = Double.valueOf(formatter.format(r_value));
		
		}catch(Throwable t ){
			//System.out.println("Error while getting Book rate for activity > "+_Activity+" < "+t.getMessage());
		}
		return r_value;
			
	}
	
	public double get_Book_Rate_from_Properties_BI(String _Activity,String limit_format){
		
		double r_value=0.0;
		DecimalFormat formatter = new DecimalFormat("###.###");
		try{
			
			OFC_Rater = OR.getORProperties();
		
			String t_activity = _Activity.replaceAll("–", "").replaceAll(" ", "_").replaceAll(",", "").replaceAll("__", "_");
			t_activity = t_activity + "_"+limit_format+"_BI_BR";
		
			r_value = Double.parseDouble(OFC_Rater.getProperty(t_activity));
			r_value = Double.valueOf(formatter.format(r_value));
		
		}catch(Throwable t ){
			System.out.println("Error while getting Book rate for activity > "+_Activity+" < "+t.getMessage());
		}
		return r_value;
			
	}
	
	public double get_Book_Rate_from_Properties_MD_Buildings(String _Activity){
		
		double r_value=0.0;
		DecimalFormat formatter = new DecimalFormat("###.###");
		try{
			
			OFC_Rater = OR.getORProperties();
		
			String t_activity = _Activity.replaceAll("–", "").replaceAll("-", "").replaceAll(" ", "_").replaceAll(",", "").replaceAll("__", "_");
			t_activity = t_activity + "_MD_BuildingRates_BR";
		
			r_value = Double.parseDouble(OFC_Rater.getProperty(t_activity));
			r_value = Double.valueOf(formatter.format(r_value));
		
		}catch(Throwable t ){
			//System.out.println("Error while getting Book rate for activity in get_Book_Rate_from_Properties_MD_Buildings > "+_Activity+" < "+t.getMessage());
		}
		return r_value;
			
	}
	
	public double get_Book_Rate_from_Properties_M_SAR(String _Activity,String cover){
		
		double r_value=0.0;
		DecimalFormat formatter = new DecimalFormat("###.###");
		
		
		try{
			OFC_Rater = OR.getORProperties();
			String t_activity = null;
			t_activity = _Activity.replaceAll("–", "").replaceAll(" ", "_").replaceAll(",", "").replaceAll("__", "_");
			t_activity = "OFC_"+t_activity+"_"+cover+"_BR";
			//OFC_Single_Loss_Limit_MONEY_BR
					
			r_value = Double.parseDouble(OFC_Rater.getProperty(t_activity));
			r_value = Double.valueOf(formatter.format(r_value));
		
		}catch(Throwable t ){
			//System.out.println("Error while getting Book rate for activity > "+_Activity+" < "+t.getMessage());
		}
		return r_value;
			
	}
	
	public double get_Book_Rate_from_Properties_GIT(String _Activity){
		
		double r_value=0.0;
		DecimalFormat formatter = new DecimalFormat("###.###");
		//Drain_Rodding_–_incl._repair
		try{
			OFC_Rater = OR.getORProperties();
			String t_activity = _Activity.replaceAll("–", "").replaceAll(" ", "_").replaceAll(",", "").replaceAll("__", "_").replaceAll("=", "");
			t_activity = "OFC_"+t_activity+ "_GIT_BR";
			
			//OFC_Maximum_Any_One_Loss_(<£2000)_GIT_BR=1.82
		
			r_value = Double.parseDouble(OFC_Rater.getProperty(t_activity));
			r_value = Double.valueOf(formatter.format(r_value));
		
		}catch(Throwable t ){
			System.out.println("Error while getting Book rate for activity > "+_Activity+" < "+t.getMessage());
		}
		return r_value;
			
	}
	
	public double get_Book_Rate_from_Properties_PAS(String _Activity,String isZMF){
		
		double r_value=0.0;
		DecimalFormat formatter = new DecimalFormat("###.###");
		//Drain_Rodding_–_incl._repair
		try{
			OFC_Rater = OR.getORProperties();
			String t_activity = _Activity.replaceAll("–", "").replaceAll(" ", "_").replaceAll(",", "").replaceAll("__", "_");
			if(isZMF.equalsIgnoreCase("Yes"))
				t_activity = t_activity + "_PAS_ZMF_BR";
			else
				t_activity = t_activity + "_PAS_BR";
			
			//Clerical_PAS_ZMF_BR=2.76
			
			r_value = Double.parseDouble(OFC_Rater.getProperty(t_activity));
			r_value = Double.valueOf(formatter.format(r_value));
		
		}catch(Throwable t ){
			//System.out.println("Error while getting Book rate for activity > "+_Activity+" < "+t.getMessage());
		}
		return r_value;
			
	}
	
	public double get_Book_Rate_from_Properties_TER(String _Activity,String cover){
		
		double r_value=0.0;
		DecimalFormat formatter = new DecimalFormat("###.###");
		Map<Object,Object> data_map = new HashMap<>();
		
		switch(common.currentRunningFlow){
		case "NB":
			data_map = common.NB_excel_data_map;
			break;
		case "MTA":
			data_map = common.MTA_excel_data_map;
			break;
		case "Renewal":
			data_map = common.Renewal_excel_data_map;
			break;
		
		}
		//TER_BI_BR=0.0198
		//TER_ZoneA_MD_BR
		try{
			OFC_Rater = OR.getORProperties();
			String t_activity = _Activity.replaceAll("–", "").replaceAll(" ", "_").replaceAll(",", "").replaceAll("__", "_");
			
			if(cover.equalsIgnoreCase("MD")){
				String ter_Zone = (String)data_map.get("PoD_TerrorismArea");
				t_activity = "TER_Zone"+ter_Zone+"_"+cover+ "_BR";
			}else{
				t_activity = "TER_"+cover+ "_BR";
			}
			
			r_value = Double.parseDouble(OFC_Rater.getProperty(t_activity));
			r_value = Double.valueOf(formatter.format(r_value));
		
		}catch(Throwable t ){
			//System.out.println("Error while getting Book rate for activity > "+_Activity+" < "+t.getMessage());
		}
		return r_value;
			
	}
	
	public double get_highest_MD_Buildings_Occupancy_Rate_(){
		
		double occupancy_rate=0.0,max_rate=0.0;
		try{
			
			if(common_CCD.MD_Building_Occupancies_list.size() > 0){
				for(String md_occupancy : common_CCD.MD_Building_Occupancies_list){
					
					occupancy_rate = get_Book_Rate_from_Properties_MD_Buildings(md_occupancy);
					if(occupancy_rate > max_rate)
						max_rate = occupancy_rate;
				}
			}else{
				max_rate = 0.00;
			}
			
			
		}catch(Throwable t){
			System.out.println("Error in function func_get_highest_MD_Buildings_Occupancy_Rate_--> "+t.getMessage());
		}
		
		return max_rate;
		
	}
	
	public double get_BI_Book_Rate(String bi_Activity){
		
		try{
		double book_rate = 0.0;
		DecimalFormat formatter = new DecimalFormat("###.###");
		
		if(is_BI_Additional_Extensions("BI", bi_Activity)) {//For BI - Additional Extensions section
			//System.out.println("Book Rate of "+bi_Activity + " = "+book_rate);
			common_OFC.Book_rate_Rater_output.put("BI"+"_"+bi_Activity, book_rate);
			return 0.0;
		}
		//Basic Business Interruption
		if(bi_Activity.contains("Declaration Linked") || bi_Activity.contains("Flexible Limit of Loss") || bi_Activity.contains("Gross Profit") || 
				bi_Activity.contains("Gross Revenue")){
			//This Rate is depends on MD Buildings Occupancy Rate
			book_rate = get_highest_MD_Buildings_Occupancy_Rate_();
			
			//System.out.println("Book Rate of "+bi_Activity + " = "+book_rate);
			common_OFC.Book_rate_Rater_output.put("BI"+"_"+bi_Activity, book_rate);
			
			return book_rate;
			
		}else if(is_BI_Extensions("BI", bi_Activity)) //For BI - Extensions section
		{ 
			OFC_Rater = OR.getORProperties();
			
			String t_activity = bi_Activity.replaceAll(" – ", " ").replaceAll("-", "").replaceAll(" ", "_").replaceAll(",", "").replaceAll("__", "_");
			t_activity = t_activity  +"_BI_BR";
		
			book_rate = Double.parseDouble(OFC_Rater.getProperty(t_activity));
			book_rate = Double.valueOf(formatter.format(book_rate));
		
			
		}else{
		
			String _sum_insured = get_BI_Limit_format("BI",bi_Activity);
		
			book_rate = get_Book_Rate_from_Properties_BI(bi_Activity,_sum_insured);
		
		}
		
		//System.out.println("Book Rate of "+bi_Activity + " = "+book_rate);
		common_OFC.Book_rate_Rater_output.put("BI"+"_"+bi_Activity, book_rate);
		return book_rate;
		}catch(Throwable t){
			//System.out.println("Error while calculating BI Book rate for activity --> "+bi_Activity);
			return 0;
		}
			
	}
	
			//For EL- Additional Extensions
			public boolean is_EL_Additional_Cover(String cover,String el_Activity)
			{
				
				OFC_Rater = OR.getORProperties();
				int f=0;
				String el_add_cover = OFC_Rater.getProperty("EL_AdditionalCovers_List");
				String[] list_cov = el_add_cover.split(":");
				
				for(String bi_ext : list_cov){
					if(bi_ext.equalsIgnoreCase(el_Activity))
					{
						f=1;
						return true;
					}
				}
				if(f==0){
					return false;
				}
				return false;
					
			}
			
			//For EL- Additional Extensions
			public boolean is_EL_No_Rate_Activity_Refer(String cover,String el_Activity)
			{
				
				OFC_Rater = OR.getORProperties();
				int f=0;
				String el_add_cover = OFC_Rater.getProperty("EL_Refer_No_Rate_Activities");
				String[] list_cov = el_add_cover.split(":");
				
				for(String bi_ext : list_cov){
					if(bi_ext.equalsIgnoreCase(el_Activity))
					{
						f=1;
						return true;
					}
				}
				if(f==0){
					return false;
				}
				return false;
					
			}
			
			//For PL - Refer Activities
			public boolean is_PL_Refer_Activity(String cover,String pl_Activity)
			{
				
				OFC_Rater = OR.getORProperties();
				int f=0;
				String pl_refer = OFC_Rater.getProperty("PL_Refer_No_Rate_Activities");
				String[] list_refer = pl_refer.split(":");
				
				for(String pl_act : list_refer){
					if(pl_act.equalsIgnoreCase(pl_Activity))
					{
						f=1;
						return true;
					}
				}
				if(f==0){
					return false;
				}
				return false;
					
			}

	public double get_EL_Book_Rate(String el_Activity){
		
		double book_rate = 0.0;
		
		
		String _DV = get_DV_format("EL",el_Activity);
		
		book_rate = get_Book_Rate_from_Properties(el_Activity,_DV,"EL");
		
		//System.out.println("Book Rate of "+el_Activity + " = "+book_rate);
		common_OFC.Book_rate_Rater_output.put("EL"+"_"+el_Activity, book_rate);
		return book_rate;
			
	}
	
	public double get_PL_Book_Rate(String pl_Activity,String category){
		
		double book_rate = 0.0;
		String _TO_W=null;
		String _wageRoll_T = null;
		double pl_bonafide_cent = 0.0; 
		OFC_Rater = OR.getORProperties();
		
		_TO_W = get_WageRoll_Turnover_string("PL", pl_Activity);
		if(_TO_W.equalsIgnoreCase("W"))
			_wageRoll_T = get_DV_format("PL",pl_Activity);
		else
			_wageRoll_T = get_Turnover_format("PL",pl_Activity);
		
		if(category.contains("BF")){
			book_rate = get_Book_Rate_from_Properties(pl_Activity,_wageRoll_T,"PL");
			pl_bonafide_cent = Double.parseDouble(OFC_Rater.getProperty("PL_Bonafide_Cent_Rate"));
			book_rate = (book_rate * (pl_bonafide_cent/100));
		}else{
			book_rate = get_Book_Rate_from_Properties(pl_Activity,_wageRoll_T,"PL");
		}
		
		//System.out.println("Book Rate of "+pl_Activity + " = "+book_rate);
		common_OFC.Book_rate_Rater_output.put("PL"+"_"+pl_Activity, book_rate);
		return book_rate;
			
	}
	public double get_PAS_Book_Rate(String pas_Activity){
		
		double book_rate = 0.0;
		
		try{
		
			String isZMFP = k.GetDropDownSelectedValue("CCD_PAS_Zurich_MFP");
			book_rate = get_Book_Rate_from_Properties_PAS(pas_Activity,isZMFP);
		
			//System.out.println("Book Rate of "+pas_Activity + " = "+book_rate);
			common_OFC.Book_rate_Rater_output.put("PAS"+"_"+pas_Activity, book_rate);
			return book_rate;
			
		}catch(Throwable t){
			//System.out.println("Error while calculating PAS_Book_Rate"+ t.getMessage());
			return 0;
		}
			
	}
	public double get_TER_Book_Rate(String ter_Activity){
		
		double book_rate = 0.0;
		
		try{
			switch(ter_Activity){
			
				case "Business Interruption":
					book_rate = get_Book_Rate_from_Properties_TER(ter_Activity,"BI");
				break;
				case "Buildings and Contents":
					book_rate = get_Book_Rate_from_Properties_TER(ter_Activity,"MD");
				break;
				default: 
					//System.out.println("Terrorism Activity is not in scope");
				break;
			}
			
			//System.out.println("Book Rate of "+ter_Activity + " = "+book_rate);
			common_OFC.Book_rate_Rater_output.put("TER_"+ter_Activity, book_rate);
			return book_rate;
			
		}catch(Throwable t){
			//System.out.println("Error while calculating Terrorism book rate"+ t.getMessage());
			return 0;
		}
			
	}
	public double get_GIT_Book_Rate(String git_Activity){
		
		double book_rate = 0.0;
			
		book_rate = get_Book_Rate_from_Properties_GIT(git_Activity);
		
		//System.out.println("Book Rate of "+git_Activity + " = "+book_rate);
		common_OFC.Book_rate_Rater_output.put("GIT"+"_"+git_Activity, book_rate);
		return book_rate;
			
	}
	
	public double get_Money_SAR_Book_Rate(String money_Activity,String cover){
		
		double book_rate = 0.0;
			
		book_rate = get_Book_Rate_from_Properties_M_SAR(money_Activity,cover);
		
		//System.out.println("Book Rate of "+money_Activity + " = "+book_rate);
		common_OFC.Book_rate_Rater_output.put(cover+"_"+money_Activity, book_rate);
		return book_rate;
			
	}
	
	//For PL cover
	public String get_WageRoll_Turnover_string(String cover,String pl_Activity){
		
		try{
		
		OFC_Rater = OR.getORProperties();
		int f=0;
		String pl_act_1 = OFC_Rater.getProperty("PL_TurnOver_Based_Activitis_1");
		String pl_act_2 = OFC_Rater.getProperty("PL_TurnOver_Based_Activitis_2");
		
		String[] list_act_1 = pl_act_1.split(":");
		String[] list_act_2 = pl_act_2.split(":");
		
		
		List<String> abc = new ArrayList<>(Arrays.asList(list_act_1));
		abc.addAll(Arrays.asList(list_act_2));
	
		for(String bi_ext : abc){
			if(bi_ext.equalsIgnoreCase(pl_Activity))
			{
				f=1;
				return "TO";
			}
		}
		if(f==0){
			return "W";
		}
		
		}catch(Throwable t){
			//System.out.println("Error while getting PL Turnover/Wageroll string for activity --> "+pl_Activity);
			return "";
		}
		
		return "";
			
	}
	
		//For BI- Additional Extensions
		public boolean is_BI_Additional_Extensions(String cover,String bi_Activity)
		{
			
			OFC_Rater = OR.getORProperties();
			int f=0;
			String bi_add_ext = OFC_Rater.getProperty("BI_Additional_Extensions");
			String[] list_ext = bi_add_ext.split(":");
			
			for(String bi_ext : list_ext){
				if(bi_ext.equalsIgnoreCase(bi_Activity))
				{
					f=1;
					return true;
				}
			}
			if(f==0){
				return false;
			}
			return false;
				
		}
		
		//For BI- Additional Extensions
			public boolean is_BI_Extensions(String cover,String bi_Activity)
				{
					
					OFC_Rater = OR.getORProperties();
					int f=0;
					String bi_add_ext = OFC_Rater.getProperty("BI_Extensions");
					String[] list_ext = bi_add_ext.split(":");
					
					for(String bi_ext : list_ext){
						if(bi_ext.equalsIgnoreCase(bi_Activity))
						{
							f=1;
							return true;
						}
					}
					if(f==0){
						return false;
					}
					return false;
						
				}
	
	public String get_DV_format(String cover,String el_Activity){
		
		String _DV = null;
		int DV=0;
		DV = get_DV_value(cover, el_Activity);
	
		if(DV < 200000) {
			_DV = "_200k";
		}else{
			_DV = "Above_200k";
		}
		return _DV;	
	}
	
	public String get_BI_Limit_format(String cover,String bi_Activity){
		
		String _limit = null;
		int f_limit=0,_limit_=0;
		_limit_ = get_Sum_Insured_value(cover, bi_Activity);
		
		if(bi_Activity.contains("Book Debts")){
			
			if(_limit_ <= 100000)
				f_limit = 100000;
			else if(_limit_ > 100000 && _limit_ <= 250000)
				f_limit = 250000;
			else if(_limit_ > 250000){
				f_limit = 250000;
				_limit = "Above_"+f_limit/1000 + "k";
				return _limit;
			}
			
			
		}else{
	
			if(_limit_ <= 50000)
				f_limit = 50000;
			else if(_limit_ > 50000 && _limit_ <= 100000)
				f_limit = 100000;
			else if(_limit_ > 100000){
				f_limit = 100000;
				_limit = "Above_"+f_limit/1000 + "k";
				return _limit;
			}
		
		}
		
		_limit = f_limit/1000 + "k";
		
		return _limit;
			
	}
	
	//For PL
	public String get_Turnover_format(String cover,String pl_Activity){
		
		String turnover = null;
		int turnover_=0,_limit_turnover=0;
		turnover_ = get_Turnover_value(cover, pl_Activity);
	
		if(turnover_ <= 500000)
			_limit_turnover = 500000;
		else if(turnover_ > 500000 && turnover_ <= 1000000)
			_limit_turnover = 1000000;
		else if(turnover_ > 1000000 && turnover_ <= 2000000)
			_limit_turnover = 2000000;
		else if(turnover_ > 2000000 && turnover_ <= 5000000){
			_limit_turnover = 5000000;
		}else if(turnover_ > 5000000 && turnover_ <= 10000000){
			_limit_turnover = 10000000;
		}else if(turnover_ > 10000000 && turnover_ <= 20000000){
			_limit_turnover = 20000000;
		}else if(turnover_ > 0000000 && turnover_ <= 50000000){
			_limit_turnover = 50000000;
		}else if(turnover_ > 50000000 && turnover_ <= 100000000){
			_limit_turnover = 100000000;
		}else{
			_limit_turnover = 100000000;
			turnover = "_Above_"+_limit_turnover/1000 + "k";
			return turnover;
		}
		
		turnover = _limit_turnover/1000 + "k";
		
		return turnover;
			
	}
	
	

/**
* 
* This method Calculates Book Rate for CCD Autorated Covers [BI, Money, EL , PL, GIT, PAS, TER].
* 
*/
public boolean calculate_Book_Rate(String coverName){
		
		try{
			
		String desc=null;
		int i_rate=0;
			
		switch(coverName){
		
		case "BI": //Business Interruption
			
			List<String> bi_activites = get_Activities_from_Table("Business Interruption");
			for(String activity : bi_activites){
				if(activity.contains("Contract Sites") || activity.contains("Loss Of Attraction") 
						||  activity.isEmpty()) {
					common_OFC.Book_rate_Rater_output.put(coverName+"_"+activity, 0.0);
					continue;
				}else{
					get_BI_Book_Rate(activity);
					
				}
			}
			TestUtil.reportStatus("Book Rate for Business Interruption Cover calculated Successfully by rater . ", "Info", false);
			//System.out.println(common_OFC.Book_rate_Rater_output);
			break;
		
		case "MONEY": //Money
			
			List<String> money_activites = get_Activities_from_Table("Description");
			
			for(String activity : money_activites){
				if(activity.equalsIgnoreCase("Flat Premium")){
					continue;
				}
				if(activity.isEmpty()) {
					common_OFC.Book_rate_Rater_output.put(coverName+"_"+activity, 0.0);
					continue;
				}else{
					get_Money_SAR_Book_Rate(activity,"MONEY");
					
				}
			}
			TestUtil.reportStatus("Book Rate for Money Cover calculated Successfully by rater . ", "Info", false);
			
			break;
			case "EL": //Employers Liability
			
			List<String> el_activites = get_Activities_from_Table("Description");
			
			for(String activity : el_activites){
				if(activity.equalsIgnoreCase("Flat Premium")){
					continue;
				}
					//is_EL_Additional_Cover(coverName,activity);
				if(activity.isEmpty() || activity.contains("Bespoke")) {
						common_OFC.Book_rate_Rater_output.put(coverName+"_"+activity, 0.0);
					continue;
				}else{
					get_EL_Book_Rate(activity);
					
				}
			}
			TestUtil.reportStatus("Book Rate for Employers Liability Cover calculated Successfully by rater . ", "Info", false);
			
			break;
			
			case "SAR": //Specified All Risks
				
				List<String> sar_activites = get_Activities_from_Table("Description");
				
				for(String activity : sar_activites){
					if(activity.equalsIgnoreCase("Flat Premium")){
						continue;
					}
					if(activity.isEmpty() || activity.contains("Bespoke")) {
						common_OFC.Book_rate_Rater_output.put(coverName+"_"+activity, 0.0);
						continue;
					}else{
						get_Money_SAR_Book_Rate(activity,"SAR");
						
					}
				}
				TestUtil.reportStatus("Book Rate for Specified All Risks Cover calculated Successfully by rater . ", "Info", false);
				
				break;
				
			case "PA": //Personal Accident
				
				List<String> pas_activites = get_Activities_from_Table("Activity");
			
				for(String activity : pas_activites){
					
					if(activity.isEmpty()) {
						common_OFC.Book_rate_Rater_output.put(coverName+"_"+activity, 0.0);
						continue;
					}else{
						get_PAS_Book_Rate(activity);
						
					}
				}
				TestUtil.reportStatus("Book Rate for Personal Accident Standard Cover calculated Successfully by rater . ", "Info", false);
				
				break;
				
			case "GIT": //Goods in Transit
				
				
				List<String> git_activites = get_Activities_from_Table("Description");
				
				for(String activity : git_activites){
					
					if(activity.isEmpty()) {
						common_OFC.Book_rate_Rater_output.put(coverName+"_"+activity, 0.0);
						continue;
					}else{
						get_GIT_Book_Rate(activity);
						
					}
				}
				TestUtil.reportStatus("Book Rate for Goods In Transit Cover calculated Successfully by rater . ", "Info", false);
				
				break;
		
		}	
			
		
		}catch(Throwable t){
			return false;
		}
		
		
		//System.out.println("Book Rate lookup Outputs = "+common_COB.Book_rate_Rater_output);
		return true;
		
	}

//For Legal Expenses
public double calculate_LE_Book_Premium(Map<Object,Object> data_map){
	try{
		
		
		double book_premium=0.0;
		String wage_range= null;
		
		double LE_total_WageRoll = Double.parseDouble((String)data_map.get("LE_TotalWages"));
		
		OFC_Rater = OR.getORProperties();
		
		
		if(LE_total_WageRoll <= 100000)
			wage_range = "100000";
		else if(LE_total_WageRoll > 100000 && LE_total_WageRoll <= 250000)
			wage_range = "250000";
		else if(LE_total_WageRoll > 250000 && LE_total_WageRoll <= 500000)
			wage_range = "500000";
		else if(LE_total_WageRoll > 500000 && LE_total_WageRoll <= 1000000)
			wage_range = "1000000";
		else if(LE_total_WageRoll > 1000000 && LE_total_WageRoll <= 5000000)
			wage_range = "5000000";
		else if(LE_total_WageRoll > 5000000 && LE_total_WageRoll <= 10000000)
			wage_range = "10000000";
		else if(LE_total_WageRoll > 10000000)
			wage_range = "Above_10000000";
		
		book_premium = Double.parseDouble(OFC_Rater.getProperty("LE_"+wage_range+"_BP"));
		
		return book_premium;
		
	}catch(Throwable t){
		//System.out.println("Error while calculating book Premium for Legal E");
		return 0.0;
	}
}

//For EL 
public int get_DV_value(String coverName,String activity){
	
	
	int section_head_index = 0,wageroll_head_index = 0;
	String DV = null;
	boolean isTrue1=false,isTrue2=false;

	try{
		
	//customAssert.assertTrue(common.funcPageNavigation("Employers Liability", ""),"Employers Liability page not loaded");
		
	
	String ratingTable_xpath = "//*[text()='Apply Book Rates']//following::table";
	WebElement ratingTable = driver.findElement(By.xpath(ratingTable_xpath));
	
	k.ScrollInVewWebElement(ratingTable);
	
	int _tble_Headers = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//thead//th")).size();
	for(int row = 1; row < _tble_Headers ;row ++){
		
		if(isTrue1 && isTrue2)
			break;
		
		WebElement sec_Val = driver.findElement(By.xpath(ratingTable_xpath+"//thead//th["+row+"]"));
		if(sec_Val.getText().equalsIgnoreCase("Description")){
			section_head_index = row;
			isTrue1=true;
		}
		if(sec_Val.getText().equalsIgnoreCase("Declared Value")){
			wageroll_head_index = row;
			isTrue2=true;
		}
	}

	int _tble_Rows = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//tbody//tr")).size();
	
	for(int row = 1; row < _tble_Rows ;row ++){
		
		WebElement activity_Val = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+section_head_index+"]"));
		if(activity_Val.getText().equals(activity)){
			WebElement dv_ = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+wageroll_head_index+"]//input"));
			DV = dv_.getAttribute("value");
				break;
		}else{
			continue;
		}
		
	}
	
	
	}catch(Throwable t){
		//System.out.println("Error while getting Wage Roll for cover >"+coverName+"< - "+t);
	}
	return Integer.parseInt(DV.replaceAll(",", ""));
}

//For BI 
public int get_Sum_Insured_value(String coverName,String activity){
	
	
	int section_head_index = 0,sum_head_index = 0;
	String sum = null;
	boolean isTrue1=false,isTrue2=false;

	try{
		
	customAssert.assertTrue(common.funcPageNavigation("Business Interruption", ""),"Business Interruption page not loaded");
		
	
	String ratingTable_xpath = "//*[text()='Apply Book Rates']//following::table";
	WebElement ratingTable = driver.findElement(By.xpath(ratingTable_xpath));
	
	k.ScrollInVewWebElement(ratingTable);
	
	int _tble_Headers = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//thead//th")).size();
	for(int row = 1; row < _tble_Headers ;row ++){
		
		if(isTrue1 && isTrue2)
			break;
		
		WebElement sec_Val = driver.findElement(By.xpath(ratingTable_xpath+"//thead//th["+row+"]"));
		if(sec_Val.getText().equalsIgnoreCase("Business Interruption")){
			section_head_index = row;
			isTrue1=true;
		}
		if(sec_Val.getText().equalsIgnoreCase("Sum Insured")){
			sum_head_index = row;
			isTrue2=true;
		}
	}

	int _tble_Rows = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//tbody//tr")).size();
	
	for(int row = 1; row < _tble_Rows ;row ++){
		
		WebElement activity_Val = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+section_head_index+"]"));
		if(activity_Val.getText().equals(activity)){
			WebElement sum_ = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+sum_head_index+"]"));
			sum = sum_.getText();
				break;
		}else{
			continue;
		}
		
	}
	
	
	}catch(Throwable t){
		//System.out.println("Error while getting Sum Insured for cover >"+coverName+"< - "+t);
	}
	return Integer.parseInt(sum.replaceAll(",", ""));
}


public int get_Turnover_value(String coverName,String activity){
	
	
	int section_head_index = 0,turnover_head_index = 0;
	String turnover = null;
	boolean isTrue1=false,isTrue2=false;

	try{
		
	customAssert.assertTrue(common.funcPageNavigation("Public Liability", ""),"Public Liability page not loaded");
		
	
	String ratingTable_xpath = "//*[text()='Apply Book Rates']//following::table";
	WebElement ratingTable = driver.findElement(By.xpath(ratingTable_xpath));
	
	k.ScrollInVewWebElement(ratingTable);
	
	int _tble_Headers = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//thead//th")).size();
	for(int row = 1; row < _tble_Headers ;row ++){
		
		if(isTrue1 && isTrue2)
			break;
		
		WebElement sec_Val = driver.findElement(By.xpath(ratingTable_xpath+"//thead//th["+row+"]"));
		if(sec_Val.getText().equalsIgnoreCase("Activity")){
			section_head_index = row;
			isTrue1=true;
		}
		if(sec_Val.getText().equalsIgnoreCase("Turnover")){
			turnover_head_index = row;
			isTrue2=true;
		}
	}

	int _tble_Rows = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//tbody//tr")).size();
	
	for(int row = 1; row < _tble_Rows ;row ++){
		
		WebElement activity_Val = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+section_head_index+"]"));
		if(activity_Val.getText().equals(activity)){
			WebElement wage_ = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+turnover_head_index+"]"));
			turnover = wage_.getText();
				
		}else{
			continue;
		}
		
	}
	
	
	}catch(Throwable t){
		//System.out.println("Error while getting turnover for cover >"+coverName+"< - "+t);
	}
	return Integer.parseInt(turnover.replaceAll(",", ""));
}

//MONEY
public int get_Initial_Rate_value(String coverName,String activity){
	
	
	int section_head_index = 0,turnover_head_index = 0;
	String init_Rate = null;
	boolean isTrue1=false,isTrue2=false;

	try{
		
	customAssert.assertTrue(common.funcPageNavigation("Money & Assault", ""),"Money & Assault page not loaded");
		
	
	String ratingTable_xpath = "//*[text()='Apply Book Rates']//following::table";
	WebElement ratingTable = driver.findElement(By.xpath(ratingTable_xpath));
	
	k.ScrollInVewWebElement(ratingTable);
	
	int _tble_Headers = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//thead//th")).size();
	for(int row = 1; row < _tble_Headers ;row ++){
		
		if(isTrue1 && isTrue2)
			break;
		
		WebElement sec_Val = driver.findElement(By.xpath(ratingTable_xpath+"//thead//th["+row+"]"));
		if(sec_Val.getText().equalsIgnoreCase("Description")){
			section_head_index = row;
			isTrue1=true;
		}
		if(sec_Val.getText().equalsIgnoreCase("Initial Rate")){
			turnover_head_index = row;
			isTrue2=true;
		}
	}

	int _tble_Rows = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//tbody//tr")).size();
	
	for(int row = 1; row < _tble_Rows ;row ++){
		
		WebElement activity_Val = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+section_head_index+"]"));
		if(activity_Val.getText().equals(activity)){
			try{
				init_Rate = k.getAttributeByXpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+turnover_head_index+"]//input", "value");
			}catch(Throwable t){
				WebElement rate_ = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+turnover_head_index+"]"));
				init_Rate = rate_.getText();
			}
			
				
		}else{
			continue;
		}
		
	}
	
	
	}catch(Throwable t){
		//System.out.println("Error while getting initial Rate for cover >"+coverName+"< - "+t);
	}
	return Integer.parseInt(init_Rate.replaceAll(",", ""));
}

	
	
	
public String get_Description_from_Table(String activity){
		
		int section_head_index = 0,description_head_index = 0;
		String description = null;
		boolean isTrue1=false,isTrue2=false;

		try{
			
			customAssert.assertTrue(common.funcPageNavigation("Public Liability", ""),"Public Liability page not loaded");
			
		
		String ratingTable_xpath = "//*[text()='Apply Book Rates']//following::table";
		WebElement ratingTable = driver.findElement(By.xpath(ratingTable_xpath));
		
		k.ScrollInVewWebElement(ratingTable);
		
		int _tble_Headers = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//thead//th")).size();
		for(int row = 1; row < _tble_Headers ;row ++){
			
			if(isTrue1 && isTrue2)
				break;
			
			WebElement sec_Val = driver.findElement(By.xpath(ratingTable_xpath+"//thead//th["+row+"]"));
			if(sec_Val.getText().equalsIgnoreCase("Activity")){
				section_head_index = row;
				isTrue1=true;
			}
			if(sec_Val.getText().equalsIgnoreCase("Description")){
				description_head_index = row;
				isTrue2=true;
			}
		}

		int _tble_Rows = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//tbody//tr")).size();
		
		for(int row = 1; row < _tble_Rows ;row ++){
			
			WebElement activity_Val = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+section_head_index+"]"));
			if(activity_Val.getText().equals(activity)){
				WebElement desc_ = driver.findElement(By.xpath(ratingTable_xpath+"//tbody//tr["+row+"]//td["+description_head_index+"]"));
				description = desc_.getText();
				break;	
			}else{
				continue;
			}
			
		}
		
		
		}catch(Throwable t){
			//System.out.println("Error while getting description for activity >"+activity+"< - "+t);
		}
		return description;
		
	}
	

public List<String> get_Activities_from_Table(String col_Name){
		
		List<String> pl_activites = new ArrayList<>();
		int section_head_index = 0;
		
		String bookRate_xpath = "//a[text()='Apply Book Rates']//following::table[@id='table0']";
		WebElement bookRate_Table = driver.findElement(By.xpath(bookRate_xpath));
		
		int _tble_Headers = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//thead//th")).size();
		for(int row = 1; row < _tble_Headers ;row ++){
			
			WebElement sec_Val = driver.findElement(By.xpath(bookRate_xpath+"//thead//th["+row+"]"));
			if(sec_Val.getText().equalsIgnoreCase(col_Name)){
				section_head_index = row;
				break;
			}
		
		}
		
		k.ScrollInVewWebElement(bookRate_Table);
		
		int _tble_Rows = driver.findElements(By.xpath("//a[text()='Apply Book Rates']//following::table[@id='table0']//tbody//tr")).size();
		String sectionValue = null;

		
		for(int row = 1; row < _tble_Rows ;row ++){
		
		
			WebElement sec_Val = driver.findElement(By.xpath(bookRate_xpath+"//tbody//tr["+row+"]//td["+section_head_index+"]"));
			sectionValue = sec_Val.getText();

			pl_activites.add(sectionValue);
		
		}
		
		return pl_activites;
		
}
public List<String> get_GIT_Activities_from_Table(){
		
		List<String> git_activites = new ArrayList<>();
		int section_head_index = 0;
		
		customAssert.assertTrue(common.funcPageNavigation("Goods in Transit", ""),"Goods in Transit page not loaded");
		
		String bookRate_xpath = "//a[text()='Apply Book Rates']//following::table[@id='table0']";
		WebElement bookRate_Table = driver.findElement(By.xpath(bookRate_xpath));
		
		int _tble_Headers = driver.findElements(By.xpath("//*[text()='Apply Book Rates']//following::table//thead//th")).size();
		for(int row = 1; row < _tble_Headers ;row ++){
			
			WebElement sec_Val = driver.findElement(By.xpath(bookRate_xpath+"//thead//th["+row+"]"));
			if(sec_Val.getText().equalsIgnoreCase("Description")){
				section_head_index = row;
				break;
			}
		
		}
		
		k.ScrollInVewWebElement(bookRate_Table);
		
		int _tble_Rows = driver.findElements(By.xpath("//a[text()='Apply Book Rates']//following::table[@id='table0']//tbody//tr")).size();
		String sectionValue = null;

		
		for(int row = 1; row < _tble_Rows ;row ++){
		
		
			WebElement sec_Val = driver.findElement(By.xpath(bookRate_xpath+"//tbody//tr["+row+"]//td["+section_head_index+"]"));
			sectionValue = sec_Val.getText();
			if(!sectionValue.contains("Flat"))
				git_activites.add(sectionValue);
		
		}
		
		return git_activites;
		
}
	
	
public double get_PL_Auto_Adjustment_(String pl_Activity){
		
	double auto_rate = 0.0;
	double PL_Indemnity_limit=0.0;
	
	Map<Object,Object> data_map = null;
	
	switch(common.currentRunningFlow){
		case "NB":
			data_map = common.NB_excel_data_map;
		break;
		case "CAN":
			data_map = common.CAN_excel_data_map;
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
	try{
		
		int PL_LOI = Integer.parseInt((String)data_map.get("PL_IndemnityLimit"));
		PL_Indemnity_limit = PL_LOI;
		OFC_Rater = OR.getORProperties();
		
			if(PL_Indemnity_limit <= 1000000){
				auto_rate = Double.parseDouble(OFC_Rater.getProperty("PL_LOI_LoadDiscount_1000000"));
			}else if(PL_Indemnity_limit > 1000000 && PL_Indemnity_limit <= 2000000){
				auto_rate = Double.parseDouble(OFC_Rater.getProperty("PL_LOI_LoadDiscount_2000000"));
			}else if(PL_Indemnity_limit > 2000000 && PL_Indemnity_limit <= 3000000){
				auto_rate = Double.parseDouble(OFC_Rater.getProperty("PL_LOI_LoadDiscount_3000000"));
			}else if(PL_Indemnity_limit > 3000000 && PL_Indemnity_limit <= 4000000){
				auto_rate = Double.parseDouble(OFC_Rater.getProperty("PL_LOI_LoadDiscount_4000000"));
			}else if(PL_Indemnity_limit > 4000000 && PL_Indemnity_limit <= 5000000){
				auto_rate = Double.parseDouble(OFC_Rater.getProperty("PL_LOI_LoadDiscount_5000000"));
			}else{
				auto_rate = Double.parseDouble(OFC_Rater.getProperty("PL_LOI_LoadDiscount_Above_5000000"));
			}
		}catch(Throwable t){
			//System.out.println("Error while calculating PL Auto Adjustment Rate --> "+t.getMessage());
		}
		
		return auto_rate;
			
}
	
public double get_BI_Auto_Adjustment_(String bi_Activity, int indemnity_period){
		
		double auto_rate = 0.0;
		OFC_Rater = OR.getORProperties();
		
		if(bi_Activity.equalsIgnoreCase("Additional increased costs of working") || bi_Activity.contains("Increased Cost of Working")
				|| bi_Activity.contains("Gross Profit") || bi_Activity.contains("Gross Revenue")){
			
			auto_rate = Integer.parseInt(OFC_Rater.getProperty("IP_discount_BI_"+indemnity_period+"_Months"));
			
		}else{
			auto_rate = 0.0;
		}
		
		return auto_rate;
			
}
	
	//This function is to check " if Book Premium generated is less than Minimum Premium
	//Applicable Covers --> EL , PL
public double func_Check_Minimum_Premium(String coverName,String activityName,double bookPremium){
			
			OFC_Rater = OR.getORProperties();
			double _Min_Premium_=0.0;
			
			try{
				
				_Min_Premium_ = get_Min_Premium_from_Properties(coverName,activityName);
				
				if(bookPremium < _Min_Premium_){ //Minimum Premium Rule
					return _Min_Premium_;
				}else{
					return bookPremium;
				}
					
				
			}catch(Throwable t){
				//System.out.println("Error in func_Check_Minimum_Premium "+t.getMessage());
				return 0.0;
			}
			
			
}
	
public double get_Min_Premium_from_Properties(String coverName,String _Activity){
		
		double r_value=0.0;
		DecimalFormat formatter = new DecimalFormat("###.###");
	
		try{
			OFC_Rater = OR.getORProperties();
			String t_activity = _Activity.replaceAll("–", "").replaceAll(" ", "_").replaceAll(",", "").replaceAll("__", "_");
			t_activity = t_activity + "_"+coverName+"_MP";
			
			r_value = Double.parseDouble(OFC_Rater.getProperty(t_activity));
			r_value = Double.valueOf(formatter.format(r_value));
			
		}catch(Throwable t ){
			//System.out.println("Error while getting Minimum Premium for activity > "+_Activity+" < "+t.getMessage());
		}
		return r_value;
			
}
	
	


	//************************END************************************//
	//************************************************************//
	//**** CCD Book Rate Calculator Based on QBECC Sheet ********//
	//************************************************************//
	//************************************************************//


	// Auto Rated Cover - Calculation table handling :
	
	public boolean funcValidate_AutoRatedTables(Map<Object, Object> map_data, String s_CoverName, String s_Abvr){
		boolean retValue = true;
		String s_Section = null, sVal = null, s_ColName = null, s_InnerSheetName = null, i_abvr = null, s_SheetName = null;
		int totalCols = 0, totalRows = 0, InnerCount = 0;
		String sRater = null;
		
		double s_Wages = 0.00, s_BookRate = 0.00, s_BookP = 0.00, s_TechAdjust = 0.00, s_CommAdjust = 0.00, s_Premium = 0.00;
		double c_Wages = 0.00, c_BookRate = 0.00, c_BookP = 0.00, c_TechAdjust = 0.00, c_CommAdjust = 0.00, c_Premium = 0.00, c_TotalP = 0.00;
		double ad_Rate = 0.00;
		
		try{
			
			Map<String, List<Map<String, String>>> data_map = null;
			
			if(common.currentRunningFlow.contains("MTA")){
				data_map = common.MTA_Structure_of_InnerPagesMaps;
			}else if(common.currentRunningFlow.contains("Rewind")){
				data_map = common.Rewind_Structure_of_InnerPagesMaps;
			}else if(common.currentRunningFlow.contains("Requote")){
				data_map = common.Requote_Structure_of_InnerPagesMaps;
			}else if(common.currentRunningFlow.contains("Renewal")){
				data_map = common.Renewal_Structure_of_InnerPagesMaps;
			}else{
				data_map = common.NB_Structure_of_InnerPagesMaps;
			}
			
			String sTablePath = "//a[text()='Apply Book Rates']//following::table[@id='table0']";
			
			customAssert.assertTrue(func_AddInput_CalTable(sTablePath, s_Abvr, s_CoverName),"Issue in input data to the  premium calculation table");
			customAssert.assertTrue(k.Click("CCD_ApplyBookRate"), "Unable to click on apply book rate button.");
			//	Calculation :
			customAssert.assertTrue(func_AllCovers_Calculation(sTablePath, s_Abvr, s_CoverName),"Issue in input data to the  premium calculation table");
			
			return retValue;
			 
		}catch(Throwable t){
			k.ImplicitWaitOn();
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
	        return false;
			}
		finally{
			 k.ImplicitWaitOn();
		 }
		
		}


	@SuppressWarnings("null")
	public boolean func_AddInput_CalTable(String sTablePath, String s_Abvr, String s_CoverName) {
		
		boolean retVal = true;
		
		int totalCols=0, totalRows = 0, InnerCount = 0, iBespokeIndex = 0, iIndex = 0, iIndex_temp = 0;
		String s_Activity = null, s_ColName = null, sVal = null, s_Description = null, s_InnerSheetName = null, i_abvr = null ;
		String BI_Extensions = null;
		String AP_RP_Flag = "";
		Map<String, List<Map<String, String>>> data_map = null;
		Map<Object, Object> map_data = null;
		
		if(common.currentRunningFlow.contains("MTA")){
			data_map = common.MTA_Structure_of_InnerPagesMaps;
			map_data = common.MTA_excel_data_map;
		}else if(common.currentRunningFlow.contains("Rewind")){
			data_map = common.Rewind_Structure_of_InnerPagesMaps;
			map_data = common.Rewind_excel_data_map;
		}else if(common.currentRunningFlow.contains("Requote")){
			data_map = common.Requote_Structure_of_InnerPagesMaps;
			map_data = common.Requote_excel_data_map;
		}else if(common.currentRunningFlow.contains("Renewal")){
			data_map = common.Renewal_Structure_of_InnerPagesMaps;
			map_data = common.Renewal_excel_data_map;
		}else{
			data_map = common.NB_Structure_of_InnerPagesMaps;
			map_data = common.NB_excel_data_map;
		}
				
		
		/**
		 * 
		 *
		 *  To manage AP(Additional Premium) or RP(Reduced Premium) for MTA flow.
		 * 
		 * 
		 */
		
		
		if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
			
			String AP_RP_Key = (String)map_data.get("CD_AP_RP_CoverSpecific_Decision");
			
			if(!(AP_RP_Key.equalsIgnoreCase(""))){
				String[] AP_RP_Array = AP_RP_Key.split(",");
				
				for(String cover : AP_RP_Array){
					
					String[] splitCoverNameFormat = cover.split("-");
					
					if(splitCoverNameFormat[1].equalsIgnoreCase(s_CoverName.replaceAll(" ", ""))){
						if(splitCoverNameFormat[0].equalsIgnoreCase("AP")){
							AP_RP_Flag = "AP";
							TestUtil.reportStatus("<b>------ Additional Premium for Cover - "+s_CoverName+" -------------</b>", "Info", false);
							break;
						}else if(splitCoverNameFormat[0].equalsIgnoreCase("RP")){
							AP_RP_Flag = "RP";
							TestUtil.reportStatus("<b>------- Reduced Premium for Cover - "+s_CoverName+" -------------</b>", "Info", false);
							break;
						}
					}
					
				}
			}
		}
		
		int index=0;
		switch(s_CoverName)
		{
			case "Material Damage - Risk Items":
				index=0;
				break;
			case "Stock - Risk Items":
				index=1;
				break;
			case "Frozen/Refrigerated Stock - Risk Items":
				index=2;
		}
		
			List<WebElement> s_table= driver.findElements(By.xpath(sTablePath));
		
			WebElement table = s_table.get(index);
			k.ScrollInVewWebElement(table);
		
			totalCols = table.findElements(By.tagName("th")).size(); 
			totalRows = table.findElements(By.tagName("tr")).size();	
		
		
		switch (s_CoverName){
		
		case "Material Damage":
			
			int temp_Count = 0;

		
			for (int i = 0; i < totalRows - 2; i++) {

				s_Description = k.getTextByXpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[1]");
				if (!s_Description.contains("Bespoke")) {
					s_InnerSheetName = "Property Details";
					i_abvr = s_Description.replaceAll(" ","");

					InnerCount = p_Index;

					sVal = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "_TechAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(sVal);

					sVal = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "_CommAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[9]/input")).sendKeys(sVal);

					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */

					if (common.currentRunningFlow.equalsIgnoreCase("MTA")) {
						if (!common_HHAZ.MD_Premium.equalsIgnoreCase("0.00")) {

							if (AP_RP_Flag.equalsIgnoreCase("AP")) {

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride",
								// common_HHAZ.LE_Premium, map_data);
								data_map.get(s_InnerSheetName).get(InnerCount).put(i_abvr + "PremiumOverride",common_HHAZ.MD_Premium);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else if (AP_RP_Flag.equalsIgnoreCase("RP")) {

								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.MD_Premium) / (totalRows - 1)));

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride", RP_PremiumOverride,
								// map_data);
								data_map.get(s_InnerSheetName).get(InnerCount).put(i_abvr + "PremiumOverride",RP_PremiumOverride);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else {
								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount)
										.get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							}

						} else {
							String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "_PremiumOverride");
							if (pOverride.equalsIgnoreCase("")) {
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);
						}
					} else {
						String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount)
								.get(i_abvr + "_PremiumOverride");
						if (pOverride.equalsIgnoreCase("")) {
							pOverride = "0";
						}
						driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[10]/input")).sendKeys(pOverride);

					}

					/**
					 * 
					 * END
					 * ---------------------------------------------------------
					 * -------------------------
					 * 
					 */

					InnerCount = InnerCount + 1;

				} else if (true) {
					s_InnerSheetName = "Property Details";
					i_abvr = "AddSPContent_";

					temp_Count = p_Index;

					sVal = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "TechAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[5]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[5]/input")).sendKeys(sVal);

					sVal = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "CommAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(sVal);

					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */

					if (common.currentRunningFlow.equalsIgnoreCase("MTA")) {
						if (!common_HHAZ.MD_Premium.equalsIgnoreCase("0.00")) {

							if (AP_RP_Flag.equalsIgnoreCase("AP")) {

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride",
								// common_HHAZ.LE_Premium, map_data);
								data_map.get(s_InnerSheetName).get(temp_Count).put(i_abvr + "PremiumOverride",common_HHAZ.MD_Premium);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else if (AP_RP_Flag.equalsIgnoreCase("RP")) {

								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.MD_Premium) / (totalRows - 1)));

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride", RP_PremiumOverride,
								// map_data);
								data_map.get(s_InnerSheetName).get(temp_Count).put(i_abvr + "PremiumOverride",RP_PremiumOverride);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else {
								String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							}

						} else {
							String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
							if (pOverride.equalsIgnoreCase("")) {
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);
						}
					} else {
						String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
						if (pOverride.equalsIgnoreCase("")) {
							pOverride = "0";
						}
						driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

					}

					/**
					 * 
					 * END
					 * ---------------------------------------------------------
					 * -------------------------
					 * 
					 */

					temp_Count = temp_Count + 1;

				}
			}
			
			break;
		case "Stock - Risk Items" :
			String StockTble_xpath = "//p[text()='Stock - Risk Items']//following-sibling::table[1]";
			WebElement Stock_Table = driver.findElement(By.xpath(StockTble_xpath));
			for (int i = 0; i < totalRows - 2; i++) {
				WebElement stock_element = driver.findElement(By.xpath(StockTble_xpath+"//tbody//tr[" + (i + 1) + "]//td[1]"));
				s_Description = k.getTextByXpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[1]");
				//	s_Description = stock_element.getText();
				if (!s_Description.contains("Bespoke")) {
					s_InnerSheetName = "Property Details";
					i_abvr = s_Description.replaceAll(" ","");

					InnerCount = p_Index;

					sVal = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "_TechAdjust");
					driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(sVal);

					sVal = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "_CommAdjust");
					driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[9]/input")).sendKeys(sVal);

					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */

					if (common.currentRunningFlow.equalsIgnoreCase("MTA")) {
						if (!common_HHAZ.MD_Premium.equalsIgnoreCase("0.00")) {

							if (AP_RP_Flag.equalsIgnoreCase("AP")) {

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride",
								// common_HHAZ.LE_Premium, map_data);
								data_map.get(s_InnerSheetName).get(InnerCount).put(i_abvr + "PremiumOverride",common_HHAZ.MD_Premium);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else if (AP_RP_Flag.equalsIgnoreCase("RP")) {

								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.MD_Premium) / (totalRows - 1)));

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride", RP_PremiumOverride,
								// map_data);
								data_map.get(s_InnerSheetName).get(InnerCount).put(i_abvr + "PremiumOverride",RP_PremiumOverride);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else {
								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount)
										.get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							}

						} else {
							String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "_PremiumOverride");
							if (pOverride.equalsIgnoreCase("")) {
								pOverride = "0";
							}
							driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);
						}
					} else {
						String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount)
								.get(i_abvr + "_PremiumOverride");
						if (pOverride.equalsIgnoreCase("")) {
							pOverride = "0";
						}
						driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[10]/input")).sendKeys(pOverride);

					}

					/**
					 * 
					 * END
					 * ---------------------------------------------------------
					 * -------------------------
					 * 
					 */

					InnerCount = InnerCount + 1;

				} else if (true) {
					s_InnerSheetName = "Property Details";
					i_abvr = "AddSPContent_";

					temp_Count = p_Index;

					sVal = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "TechAdjust");
					driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[5]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[5]/input")).sendKeys(sVal);

					sVal = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "CommAdjust");
					driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(sVal);

					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */

					if (common.currentRunningFlow.equalsIgnoreCase("MTA")) {
						if (!common_HHAZ.MD_Premium.equalsIgnoreCase("0.00")) {

							if (AP_RP_Flag.equalsIgnoreCase("AP")) {

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride",
								// common_HHAZ.LE_Premium, map_data);
								data_map.get(s_InnerSheetName).get(temp_Count).put(i_abvr + "PremiumOverride",common_HHAZ.MD_Premium);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else if (AP_RP_Flag.equalsIgnoreCase("RP")) {

								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.MD_Premium) / (totalRows - 1)));

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride", RP_PremiumOverride,
								// map_data);
								data_map.get(s_InnerSheetName).get(temp_Count).put(i_abvr + "PremiumOverride",RP_PremiumOverride);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else {
								String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							}

						} else {
							String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
							if (pOverride.equalsIgnoreCase("")) {
								pOverride = "0";
							}
							driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);
						}
					} else {
						String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
						if (pOverride.equalsIgnoreCase("")) {
							pOverride = "0";
						}
						driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(StockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

					}

					/**
					 * 
					 * END
					 * ---------------------------------------------------------
					 * -------------------------
					 * 
					 */

					temp_Count = temp_Count + 1;

				}
			}
			break;
		case "Frozen/Refrigerated Stock - Risk Items":
			String DetStockTble_xpath = "//p[text()='Frozen/Refrigerated Stock - Risk Items']//following-sibling::table[1]";
			WebElement DetStock_Table = driver.findElement(By.xpath(DetStockTble_xpath));
			for (int i = 0; i < totalRows - 2; i++) {
				WebElement stock_element = driver.findElement(By.xpath(DetStockTble_xpath+"//tbody//tr[" + (i + 1) + "]//td[1]"));
				s_Description = k.getTextByXpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[1]");
				//	s_Description = stock_element.getText();
				if (!s_Description.contains("Bespoke")) {
					s_InnerSheetName = "Property Details";
					i_abvr = s_Description.replaceAll(" ","");

					InnerCount = p_Index;

					sVal = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "_TechAdjust");
					driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(sVal);

					sVal = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "_CommAdjust");
					driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[9]/input")).sendKeys(sVal);

					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */

					if (common.currentRunningFlow.equalsIgnoreCase("MTA")) {
						if (!common_HHAZ.MD_Premium.equalsIgnoreCase("0.00")) {

							if (AP_RP_Flag.equalsIgnoreCase("AP")) {

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride",
								// common_HHAZ.LE_Premium, map_data);
								data_map.get(s_InnerSheetName).get(InnerCount).put(i_abvr + "PremiumOverride",common_HHAZ.MD_Premium);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else if (AP_RP_Flag.equalsIgnoreCase("RP")) {

								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.MD_Premium) / (totalRows - 1)));

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride", RP_PremiumOverride,
								// map_data);
								data_map.get(s_InnerSheetName).get(InnerCount).put(i_abvr + "PremiumOverride",RP_PremiumOverride);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else {
								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount)
										.get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							}

						} else {
							String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "_PremiumOverride");
							if (pOverride.equalsIgnoreCase("")) {
								pOverride = "0";
							}
							driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);
						}
					} else {
						String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount)
								.get(i_abvr + "_PremiumOverride");
						if (pOverride.equalsIgnoreCase("")) {
							pOverride = "0";
						}
						driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[10]/input")).sendKeys(pOverride);

					}

					/**
					 * 
					 * END
					 * ---------------------------------------------------------
					 * -------------------------
					 * 
					 */

					InnerCount = InnerCount + 1;

				} else if (true) {
					s_InnerSheetName = "Property Details";
					i_abvr = "AddSPContent_";

					temp_Count = p_Index;

					sVal = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "TechAdjust");
					driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[5]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[5]/input")).sendKeys(sVal);

					sVal = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "CommAdjust");
					driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[7]/input")).sendKeys(sVal);

					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */

					if (common.currentRunningFlow.equalsIgnoreCase("MTA")) {
						if (!common_HHAZ.MD_Premium.equalsIgnoreCase("0.00")) {

							if (AP_RP_Flag.equalsIgnoreCase("AP")) {

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride",
								// common_HHAZ.LE_Premium, map_data);
								data_map.get(s_InnerSheetName).get(temp_Count).put(i_abvr + "PremiumOverride",common_HHAZ.MD_Premium);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else if (AP_RP_Flag.equalsIgnoreCase("RP")) {

								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.MD_Premium) / (totalRows - 1)));

								// TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow,
								// "Legal Expenses",
								// (String)map_data.get("Automation Key"),
								// i_abvr+"PremiumOverride", RP_PremiumOverride,
								// map_data);
								data_map.get(s_InnerSheetName).get(temp_Count).put(i_abvr + "PremiumOverride",RP_PremiumOverride);

								String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							} else {
								String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
								if (pOverride.equalsIgnoreCase("")) {
									pOverride = "0";
								}
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

							}

						} else {
							String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
							if (pOverride.equalsIgnoreCase("")) {
								pOverride = "0";
							}
							driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);
						}
					} else {
						String pOverride = (String) data_map.get(s_InnerSheetName).get(temp_Count).get(i_abvr + "PremiumOverride");
						if (pOverride.equalsIgnoreCase("")) {
							pOverride = "0";
						}
						driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(DetStockTble_xpath + "/tbody/tr[" + (i + 1) + "]/td[8]/input")).sendKeys(pOverride);

					}

					/**
					 * 
					 * END
					 * ---------------------------------------------------------
					 * -------------------------
					 * 
					 */

					temp_Count = temp_Count + 1;

				}
			}
			break;
			
		case "Business Interruption" :
            
          
            for(int i = 0; i< totalRows-2; i++){
                   s_Description = k.getTextByXpath(sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
                   i_abvr = "BI_"+s_Description.replace(" ","");
                   
                   if (!s_Description.contains("Bespoke")) {
                   sVal = (String)map_data.get(i_abvr+"_TechAdjust");
                   driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
                   driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
                          
                   sVal = (String)map_data.get(i_abvr+"_CommAdjust");
                   driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
                   driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
                          
                         /**
  						 * 
  						 * To manage AP / RP for MTA flow.
  						 *
  						 */
  						
  						if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
  							if(!common_HHAZ.BI_Premium.equalsIgnoreCase("0.00")){
  								
  								if(AP_RP_Flag.equalsIgnoreCase("AP")){
  									
  									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.BI_Premium, data_map.get(s_InnerSheetName).get(iBespokeIndex));
  									data_map.get(s_InnerSheetName).get(iBespokeIndex).put(i_abvr+"PremiumOverride", common_HHAZ.BI_Premium);
  									
  									
  									String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
  									if(pOverride.equalsIgnoreCase("")){
  	  									pOverride = "0";
  	  								}
  									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
  									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
  									
  								}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
  									
  									String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.BI_Premium) / (totalRows-1)));
  									
  									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, data_map.get(s_InnerSheetName).get(iBespokeIndex));
  									data_map.get(s_InnerSheetName).get(iBespokeIndex).put(i_abvr+"PremiumOverride", RP_PremiumOverride);
  									
  									String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
  									if(pOverride.equalsIgnoreCase("")){
  	  									pOverride = "0";
  	  								}
  									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
  									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
  								}else{
  	  								String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
  	  								if(pOverride.equalsIgnoreCase("")){
  	  									pOverride = "0";
  	  								}
  	  								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
  	  								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
  	  							}
  								
  							}else{
  								String pOverride = (String)map_data.get(i_abvr+"_PremiumOverride");
  								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
  								}
  								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
  								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
  							}
  						}else{
  							String pOverride = (String)map_data.get(i_abvr+"_PremiumOverride");
  							if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
							}
  							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
  							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
  						}
  						
  						/**
  						 * 
  						 * END ----------------------------------------------------------------------------------
  						 * 
  						 */
                          
                          
                         /* String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
                          if(!pOverride.equalsIgnoreCase("0")){
                                 driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
                                 driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
                          }*/
                          iBespokeIndex = iBespokeIndex + 1;
                          
                     
                   }else{
                                                            
                          
                          s_InnerSheetName="AddBespokeSumInsBI";
                          i_abvr = "BI_BSI_";
                          sVal = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "TechAdjust");
                          driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
                          driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
                          
                          sVal = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "CommAdjust");
                          driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
                          driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
                          
                          	/**
    						 * 
    						 * To manage AP / RP for MTA flow.
    						 * 
    						 */
    						
    						if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
    							if(!common_HHAZ.BI_Premium.equalsIgnoreCase("0.00")){
    								
    								if(AP_RP_Flag.equalsIgnoreCase("AP")){
    									
    									//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Business Interruption", (String)map_data.get("Automation Key"), "BI_BookDebts_PremiumOverride", common_HHAZ.BI_Premium, map_data);
    									map_data.put("BI_BookDebts_PremiumOverride", common_HHAZ.BI_Premium);
    									
    									String pOverride = (String)map_data.get("BI_BookDebts_PremiumOverride");
    									if(pOverride.equalsIgnoreCase("")){
    	  									pOverride = "0";
    	  								}
    									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
    									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
    									
    								}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
    									
    									String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.BI_Premium) / (totalRows-1)));
    									
    									//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Business Interruption", (String)map_data.get("Automation Key"), "BI_BookDebts_PremiumOverride", RP_PremiumOverride, map_data);
    									map_data.put("BI_BookDebts_PremiumOverride", RP_PremiumOverride);
    									
    									String pOverride = (String)map_data.get("BI_BookDebts_PremiumOverride");
    									if(pOverride.equalsIgnoreCase("")){
    	  									pOverride = "0";
    	  								}
    									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
    									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
    									
    								}else{
    									String pOverride = (String)map_data.get("BI_BookDebts_PremiumOverride");
    	  								if(pOverride.equalsIgnoreCase("")){
    	  									pOverride = "0";
    	  								}
    	  								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
    	  								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
    	  							}
    								
    							}else{
    								String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "PremiumOverride");
    								if(pOverride.equalsIgnoreCase("")){
	  									pOverride = "0";
	  								}
    								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
    								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
    							}
    						}else{
    							i_abvr = "BI_BSI_";
    							String pOverride = (String) data_map.get(s_InnerSheetName).get(InnerCount).get(i_abvr + "PremiumOverride");
    	                          
    							
    							if(pOverride.equalsIgnoreCase("")){
  									pOverride = "0";
  								}
    							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
    							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
    						}
    						
    						/**
    						 * 
    						 * END ----------------------------------------------------------------------------------
    						 * 
    						 */
                          
                          /*String pOverride = (String)map_data.get("BI_BookDebts_PremiumOverride");
                          if(!pOverride.equalsIgnoreCase("0")){
                                 driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
                                 driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[11]/input")).sendKeys(pOverride);
                          }*/
                         InnerCount++;                                          
                   }
            }
            
            break;

				
			case "Money" :
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						
						TestUtil.reportStatus("<p style='color:red'>Flat Premium to be removed from all the cover screens - Defect is opened <b> [ SUP-1544 ] </b> </p>.", "Fail", true);
						continue;
					}
					i_abvr = "MONEY_"+s_Description.replaceAll(" ", "")+"_";
						
					sVal = (String)map_data.get(i_abvr+"TechAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
					
					sVal = (String)map_data.get(i_abvr+"CommAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
					
					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */
					
					if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
						if(!common_HHAZ.MA_Premium.equalsIgnoreCase("0.00")){
							
							if(AP_RP_Flag.equalsIgnoreCase("AP")){
								
								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Money & Assault", (String)map_data.get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.MA_Premium, map_data);
								map_data.put(i_abvr+"PremiumOverride", common_HHAZ.MA_Premium);
								
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(!pOverride.equalsIgnoreCase("0") && !pOverride.equalsIgnoreCase("0.0") && !pOverride.equalsIgnoreCase("")){
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								}
								
							}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
								
								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.MA_Premium) / (totalRows-1)));
								
								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Money & Assault", (String)map_data.get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, map_data);
								map_data.put(i_abvr+"PremiumOverride", RP_PremiumOverride);
								
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							}else{
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							}
							
						}else{
							String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
						}
					}else{
						String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
						if(pOverride.equalsIgnoreCase("")){
							pOverride = "0";
						}
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
					}
					
					/**
					 * 
					 * END ----------------------------------------------------------------------------------
					 * 
					 */
								
				}
				break;
				
			case "Employers Liability" :
				
											
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						
						TestUtil.reportStatus("<p style='color:red'>Flat Premium to be removed from all the cover screens - Defect is opened <b> [ SUP-1544 ] </b> </p>.", "Fail", true);
						continue;
					}
					
					if(!s_Description.contains("Bespoke")){
						i_abvr = "EL_"+s_Description.replaceAll(" ", "")+"_";
						
						sVal = (String)map_data.get(i_abvr+"TechAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
						
						sVal = (String)map_data.get(i_abvr+"CommAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
						
						/**
						 * 
						 * To manage AP / RP for MTA flow.
						 * 
						 */
						
						if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
							if(!common_HHAZ.EL_Premium.equalsIgnoreCase("0.00")){
								
								if(AP_RP_Flag.equalsIgnoreCase("AP")){
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.EL_Premium, data_map.get(s_InnerSheetName).get(iBespokeIndex));
									data_map.get(s_InnerSheetName).get(iBespokeIndex).put(i_abvr+"PremiumOverride", common_HHAZ.EL_Premium);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
									
								}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
									
									String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.EL_Premium) / (totalRows-1)));
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, data_map.get(s_InnerSheetName).get(iBespokeIndex));
									data_map.get(s_InnerSheetName).get(iBespokeIndex).put(i_abvr+"PremiumOverride", RP_PremiumOverride);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								}else{
									String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");;
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								}
								
							}else{
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							}
						}else{
							String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
						}
						
						/**
						 * 
						 * END ----------------------------------------------------------------------------------
						 * 
						 */
						
						iBespokeIndex = iBespokeIndex + 1;
						
						
					}else {
					
						s_InnerSheetName = "AddBespokeSumInsEL";
						i_abvr = "EL_BSI_";
						sVal = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"TechAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
						
						sVal = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"CommAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
						
						/**
						 * 
						 * To manage AP / RP for MTA flow.
						 * 
						 */
						
						if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
							if(!common_HHAZ.EL_Premium.equalsIgnoreCase("0.00")){
								
								if(AP_RP_Flag.equalsIgnoreCase("AP")){
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.EL_Premium, data_map.get(s_InnerSheetName).get(iIndex));
									data_map.get(s_InnerSheetName).get(iIndex).put(i_abvr+"PremiumOverride", common_HHAZ.EL_Premium);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
									
								}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
									
									String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.EL_Premium) / (totalRows-1)));
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, data_map.get(s_InnerSheetName).get(iIndex));
									data_map.get(s_InnerSheetName).get(iIndex).put(i_abvr+"PremiumOverride", RP_PremiumOverride);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								}else{
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								}
								
							}else{
								String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							}
						}else{
							String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
						}
						
						/**
						 * 
						 * END ----------------------------------------------------------------------------------
						 * 
						 */
												
						iIndex = iIndex + 1;
						
					}
				}
				break;
				
			case "Public and Products Liability" :
															
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						
						TestUtil.reportStatus("<p style='color:red'>Flat Premium to be removed from all the cover screens - Defect is opened <b> [ SUP-1544 ] </b> </p>.", "Fail", true);
						continue;
					}
					if(!s_Description.isEmpty()){
						i_abvr = "PPL_"+s_Description.replaceAll(" ", "")+"_";
						
						sVal = (String)map_data.get(i_abvr+"TechAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
						
						sVal = (String)map_data.get(i_abvr+"CommAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
						/**
						 * 
						 * To manage AP / RP for MTA flow.
						 * 
						 */
						
						if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
							if(!common_HHAZ.PL_Premium.equalsIgnoreCase("0.00")){
								
								if(AP_RP_Flag.equalsIgnoreCase("AP")){
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.PL_Premium, data_map.get(s_InnerSheetName).get(iBespokeIndex));
									data_map.get(s_InnerSheetName).get(iBespokeIndex).put(i_abvr+"PremiumOverride", common_HHAZ.PL_Premium);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
									
									
								}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
									
									String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.PL_Premium) / (totalRows-1)));
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, data_map.get(s_InnerSheetName).get(iBespokeIndex));
									data_map.get(s_InnerSheetName).get(iBespokeIndex).put(i_abvr+"PremiumOverride", RP_PremiumOverride);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
									
								}else{
									String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);

								}
								
							}else{
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							}
						}else{
							String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
						}
						
						/**
						 * 
						 * END ----------------------------------------------------------------------------------
						 * 
						 */
						
						iBespokeIndex = iBespokeIndex + 1;
						
						
					}
				}
				break;
				
			case "Specified All Risks" :
				
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						
						TestUtil.reportStatus("<p style='color:red'>Flat Premium to be removed from all the cover screens - Defect is opened <b> [ SUP-1544 ] </b> </p>.", "Fail", true);
						continue;
					}
					
					if(!s_Description.contains("Bespoke")){
						i_abvr = "SAR_"+s_Description.replaceAll(" ", "")+"_";
						
						sVal = (String)map_data.get(i_abvr+"TechAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
						
						sVal = (String)map_data.get(i_abvr+"CommAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
						
						/**
						 * 
						 * To manage AP / RP for MTA flow.
						 * 
						 */
						
						if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
							if(!common_HHAZ.EL_Premium.equalsIgnoreCase("0.00")){
								
								if(AP_RP_Flag.equalsIgnoreCase("AP")){
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.EL_Premium, data_map.get(s_InnerSheetName).get(iBespokeIndex));
									data_map.get(s_InnerSheetName).get(iBespokeIndex).put(i_abvr+"PremiumOverride", common_HHAZ.EL_Premium);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
									
								}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
									
									String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.EL_Premium) / (totalRows-1)));
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, data_map.get(s_InnerSheetName).get(iBespokeIndex));
									data_map.get(s_InnerSheetName).get(iBespokeIndex).put(i_abvr+"PremiumOverride", RP_PremiumOverride);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iBespokeIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								}else{
									String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");;
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								}
								
							}else{
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							}
						}else{
							String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
						}
						
						/**
						 * 
						 * END ----------------------------------------------------------------------------------
						 * 
						 */
						
						iBespokeIndex = iBespokeIndex + 1;
						
						
					}else {
					
						s_InnerSheetName = "AddBespokeSumInsSAR";
						i_abvr = "SAR_BSI_";
						sVal = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"TechAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
						
						sVal = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"CommAdjust");
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
						
						/**
						 * 
						 * To manage AP / RP for MTA flow.
						 * 
						 */
						
						if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
							if(!common_HHAZ.EL_Premium.equalsIgnoreCase("0.00")){
								
								if(AP_RP_Flag.equalsIgnoreCase("AP")){
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.EL_Premium, data_map.get(s_InnerSheetName).get(iIndex));
									data_map.get(s_InnerSheetName).get(iIndex).put(i_abvr+"PremiumOverride", common_HHAZ.EL_Premium);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
									
								}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
									
									String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.EL_Premium) / (totalRows-1)));
									
									//TestUtil.WriteDataToXl_innerSheet(TestBase.product+"_"+common.currentRunningFlow, s_InnerSheetName, data_map.get(s_InnerSheetName).get(iBespokeIndex).get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, data_map.get(s_InnerSheetName).get(iIndex));
									data_map.get(s_InnerSheetName).get(iIndex).put(i_abvr+"PremiumOverride", RP_PremiumOverride);
									
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								}else{
									String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
									if(pOverride.equalsIgnoreCase("")){
										pOverride = "0";
									}
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
									driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								}
								
							}else{
								String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							}
						}else{
							String pOverride = (String)data_map.get(s_InnerSheetName).get(iIndex).get(i_abvr+"PremiumOverride");
							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
						}
						
						/**
						 * 
						 * END ----------------------------------------------------------------------------------
						 * 
						 */
												
						iIndex = iIndex + 1;
						
					}
				}
				break;
				
			case "Personal Accident" :
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						
						TestUtil.reportStatus("<p style='color:red'>Flat Premium to be removed from all the cover screens - Defect is opened <b> [ SUP-1544 ] </b> </p>.", "Fail", true);
						continue;
					}
					i_abvr = "PA_"+s_Description.replaceAll(" ", "")+"_";
					
					sVal = (String)map_data.get(i_abvr+"TechAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
					
					sVal = (String)map_data.get(i_abvr+"CommAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
					
					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */
					
					if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
						if(!common_HHAZ.PAS_Premium.equalsIgnoreCase("0.00")){
							
							if(AP_RP_Flag.equalsIgnoreCase("AP")){
								
								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Personal Accident Standard", (String)map_data.get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.PAS_Premium, map_data);
								map_data.put(i_abvr+"PremiumOverride", common_HHAZ.PAS_Premium);
								
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								
								
							}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
								
								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.PAS_Premium) / (totalRows-1)));
								
								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Personal Accident Standard", (String)map_data.get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, map_data);
								map_data.put(i_abvr+"PremiumOverride", RP_PremiumOverride);
								
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								
							}else{
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								
							}
							
						}else{
							String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							
						}
					}else{
						String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
						if(pOverride.equalsIgnoreCase("")){
							pOverride = "0";
						}
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
						
					}
					
					/**
					 * 
					 * END ----------------------------------------------------------------------------------
					 * 
					 */
					
				}
				break;
				
			case "Goods in Transit" :
				
				For:
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					i_abvr = "GIT_"+s_Description.replaceAll(" ", "")+"_";					
					
				      if(s_Description.equalsIgnoreCase("Flat Premium")){
                          
                          TestUtil.reportStatus("<p style='color:red'>Flat Premium to be removed from all the cover screens - Defect is opened <b> [ SUP-1544 ] </b> </p>.", "Fail", true);
                          continue;
                   }
           		
					sVal = (String)map_data.get(i_abvr+"TechAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
					
					sVal = (String)map_data.get(i_abvr+"CommAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
					
					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */
					
					if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
						if(!common_HHAZ.GIT_Premium.equalsIgnoreCase("0.00")){
							
							if(AP_RP_Flag.equalsIgnoreCase("AP")){
								
								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Goods in Transit", (String)map_data.get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.GIT_Premium, map_data);
								map_data.put(i_abvr+"PremiumOverride", common_HHAZ.GIT_Premium);
								
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								
							}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
								
								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.GIT_Premium) / (totalRows-1)));
								
								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Goods in Transit", (String)map_data.get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, map_data);
								map_data.put(i_abvr+"PremiumOverride", RP_PremiumOverride);
								
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								
							}else{
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								
							}
							
						}else{
							String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							
						}
					}else{
						String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
						if(pOverride.equalsIgnoreCase("")){
							pOverride = "0";
						}
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
						
					}
					
					/**
					 * 
					 * END ----------------------------------------------------------------------------------
					 * 
					 */
					
					}
				break;
				
            case "Terrorism" :
                
                for(int i = 0; i< totalRows-2; i++){
                       
                       s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
                       if(s_Description.equalsIgnoreCase("Flat Premium")){
                              
                              TestUtil.reportStatus("<p style='color:red'>Flat Premium to be removed from all the cover screens - Defect is opened <b> [ SUP-1544 ] </b> </p>.", "Fail", true);
                              continue;
                       }
                       
                       i_abvr = "TER_"+s_Description.replaceAll(" ", "")+"_";
                       
                       sVal = (String)map_data.get(i_abvr+"TechAdjust");
                       driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
                       driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
                       
                       sVal = (String)map_data.get(i_abvr+"CommAdjust");
                       driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
                       driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
                       
                       /**
   					 * 
   					 * To manage AP / RP for MTA flow.
   					 * 
   					 */
   					
   					if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
   						if(!common_HHAZ.TER_Premium.equalsIgnoreCase("0.00")){
   							
   							if(AP_RP_Flag.equalsIgnoreCase("AP")){
   								
   								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Terrorism", (String)map_data.get("Automation Key"), abvrS+"PremiumOverride", common_HHAZ.TER_Premium, map_data);
   								map_data.put(i_abvr+"PremiumOverride", common_HHAZ.TER_Premium);
   								
   								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
   								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
   								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
   								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
   								
   							}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
   								
   								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.TER_Premium) / (totalRows-1)));
   								
   								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Terrorism", (String)map_data.get("Automation Key"), abvrS+"PremiumOverride", RP_PremiumOverride, map_data);
   								map_data.put(i_abvr+"PremiumOverride", RP_PremiumOverride);
   								
   								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
   								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
   								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
   								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
   								
   							}else{
   	   							String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
   	   							if(pOverride.equalsIgnoreCase("")){
   	   								pOverride = "0";
   	   							}
   	   							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
   	   							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
   	   							
   	   						}
   							
   						}else{
   							String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
   							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
   							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
   							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
   							
   						}
   					}else{
   						String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
   						if(pOverride.equalsIgnoreCase("")){
							pOverride = "0";
						}
   						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
   						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
  						
   					}
   					
   					/**
   					 * 
   					 * END ----------------------------------------------------------------------------------
   					 * 
   					 */
                       
                }
                break;

				
			case "Legal Expenses" :
				
				for(int i = 0; i< totalRows-2; i++){
					
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
                    if(s_Description.equalsIgnoreCase("Flat Premium")){
                           
                           TestUtil.reportStatus("<p style='color:red'>Flat Premium to be removed from all the cover screens - Defect is opened <b> [ SUP-1544 ] </b> </p>.", "Fail", true);
                           continue;
                    }
                    
                    i_abvr = "LE_"+s_Description.replaceAll(" ", "")+"_";
					
					sVal = (String)map_data.get(i_abvr+"TechAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[7]/input")).sendKeys(sVal);
					
					sVal = (String)map_data.get(i_abvr+"CommAdjust");
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
					driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[9]/input")).sendKeys(sVal);
					
					/**
					 * 
					 * To manage AP / RP for MTA flow.
					 * 
					 */
					
					if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
						if(!common_HHAZ.LE_Premium.equalsIgnoreCase("0.00")){
							
							if(AP_RP_Flag.equalsIgnoreCase("AP")){
								
								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Legal Expenses", (String)map_data.get("Automation Key"), i_abvr+"PremiumOverride", common_HHAZ.LE_Premium, map_data);
								map_data.put(i_abvr+"PremiumOverride", common_HHAZ.LE_Premium);
								
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								
							}else if(AP_RP_Flag.equalsIgnoreCase("RP")){
								
								String RP_PremiumOverride = Double.toString((Double.parseDouble(common_HHAZ.LE_Premium) / (totalRows-1)));
								
								//TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Legal Expenses", (String)map_data.get("Automation Key"), i_abvr+"PremiumOverride", RP_PremiumOverride, map_data);
								map_data.put(i_abvr+"PremiumOverride", RP_PremiumOverride);
								
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);

							}else{
								String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
								if(pOverride.equalsIgnoreCase("")){
									pOverride = "0";
								}
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
								driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
								
							}
							
						}else{
							String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
							if(pOverride.equalsIgnoreCase("")){
								pOverride = "0";
							}
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
							
						}
					}else{
						String pOverride = (String)map_data.get(i_abvr+"PremiumOverride");
						if(pOverride.equalsIgnoreCase("")){
							pOverride = "0";
						}
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
						driver.findElement(By.xpath(sTablePath + "/tbody/tr["+(i+1)+"]/td[10]/input")).sendKeys(pOverride);
						
					}
					
					/**
					 * 
					 * END ----------------------------------------------------------------------------------
					 * 
					 */
					
				}
				break;
				
			
				
			}
		
		return retVal;
}
	
public double get_BI_GrossProfit_BookPremium(double declaredValue)
{
	Map<Object,Object> data_map = new HashMap<>();
	switch(common.currentRunningFlow){
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
	OFC_Rater = OR.getORProperties();
	double book_premium = 0;
	try{
	String period = (String)data_map.get("BI_IndemnityPeriod");
	switch(period){
	case "24":
		declaredValue*=2;
		break;
	case "36":
		declaredValue*=3;
		break;
	case "48":
		declaredValue*=4;
		break;
	}
	if(declaredValue > 2000000)
	{
		book_premium = book_premium + ((declaredValue - 2000000)*0.00091);
		book_premium = book_premium + Double.parseDouble(OFC_Rater.getProperty("OFC_BI_GrossProfit_upto500000"))
		+ 3*Double.parseDouble(OFC_Rater.getProperty("OFC_BI_GrossProfit_extra500000"));
	}
	else if(declaredValue<500000)
	{
		book_premium = Double.parseDouble(OFC_Rater.getProperty("OFC_BI_GrossProfit_upto500000"));
	}
	else{
		double mult = ((declaredValue-500000)/500000);
		book_premium = Double.parseDouble(OFC_Rater.getProperty("OFC_BI_GrossProfit_upto500000")) +
				((Math.ceil(mult))*Double.parseDouble(OFC_Rater.getProperty("OFC_BI_GrossProfit_extra500000")));
	}
	}
	catch(Exception e)
	{
		return 0;
	}
	return book_premium;
}
public boolean func_AllCovers_Calculation(String sTablePath, String s_Abvr, String s_CoverName) {
		
		boolean retVal = true;
		
		int totalRows = 0;
		String s_Description = null, i_abvr = null ;
		
		String sCol_PremiumSummary = null,internal_data_sheet="";
		
		double s_DeclaredValue = 0.00, s_BookRatePerc = 0.00, s_BookP = 0.00,s_Auto_Adjustemnt = 0.00, s_AutoAdjust = 0.00, s_BookRate = 0.00, s_TechAdjust = 0.00, s_RevisedP = 0.00, s_CommAdjust = 0.00, s_OverrideP = 0.00, s_FinalP = 0.00, s_TotalP = 0.00;
		
		double c_DeclaredValue = 0.00, c_BookRatePerc = 0.00, c_BookP = 0.00, c_AutoAdjust = 0.00, c_BookRate = 0.00, c_TechAdjust = 0.00, c_RevisedP = 0.00, c_CommAdjust = 0.00, c_OverrideP = 0.00, c_FinalP = 0.00, c_TotalP = 0.00;
		double c_Auto_Adjustemnt = 0.00;
		double temp_P = 0.00;
		int internal_data_count = 0;
		OFC_Rater = OR.getORProperties();
		
		Map<String, List<Map<String, String>>> internal_data_map = null;
		Map<Object, Object> map_data = null;
		
		if(common.currentRunningFlow.contains("MTA")){
			internal_data_map = common.MTA_Structure_of_InnerPagesMaps;
			map_data = common.MTA_excel_data_map;
		}else if(common.currentRunningFlow.contains("Rewind")){
			internal_data_map = common.Rewind_Structure_of_InnerPagesMaps;
			map_data = common.Rewind_excel_data_map;
		}else if(common.currentRunningFlow.contains("Requote")){
			internal_data_map = common.Requote_Structure_of_InnerPagesMaps;
			map_data = common.Requote_excel_data_map;
		}else if(common.currentRunningFlow.contains("Renewal")){
			internal_data_map = common.Renewal_Structure_of_InnerPagesMaps;
			map_data = common.Renewal_excel_data_map;
		}else{
			internal_data_map = common.NB_Structure_of_InnerPagesMaps;
			map_data = common.NB_excel_data_map;
		}
			
		try{
		WebElement s_table= driver.findElement(By.xpath(sTablePath));
		k.ScrollInVewWebElement(s_table);
		
		totalRows = s_table.findElements(By.tagName("tr")).size();
		
		switch (s_CoverName){
		
		case "Material Damage" :
			
			 
			totalRows = s_table.findElements(By.tagName("tr")).size();
			internal_data_count = at_global_no_of_property-1;
			for(int i = 0; i< totalRows-2; i++){
				s_Description = k.getTextByXpath(sTablePath+"/tbody/tr["+(i+1)+"]/td[1]").replace(" ","");
				
				s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
				s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
				s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
				s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
				s_BookRate = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[6]/input"));
				s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
				s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
				s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
				s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
				s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
				
				i_abvr = "MD_"+s_Description+"_DeclaredValue";
				c_DeclaredValue = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr));
				
				//CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				String key = s_Description;
				c_BookRatePerc = Double.parseDouble(OFC_Rater.getProperty(key));
				
				//CommonFunction.compareValues(c_BookRatePerc , s_BookRatePerc ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				c_BookP = (c_DeclaredValue*c_BookRatePerc)/100;
				//CommonFunction.compareValues(c_BookP , s_BookP ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				c_Auto_Adjustemnt = getAutoAdjustment("MD");
				c_BookRate = ((100 + c_Auto_Adjustemnt)/100) * c_BookP;
				//CommonFunction.compareValues(c_BookRate , s_BookRate ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				i_abvr = s_Description;
				c_TechAdjust = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr+"_TechAdjust"));
				
				c_RevisedP = ((c_TechAdjust +100)/100) * c_BookRate;
				//CommonFunction.compareValues(c_RevisedP , s_RevisedP ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				c_CommAdjust = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr+"_CommAdjust"));
				c_FinalP = ((c_TechAdjust +100)/100) * c_RevisedP;
				c_OverrideP = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr+"_PremiumOverride"));
			
				if(c_OverrideP != 0.00){
					c_FinalP = c_OverrideP;
				}
			
				// Compare values :
				
				try {
					
					CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +"</i> of cover - "+s_CoverName);										
					CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(c_FinalP==s_FinalP){
               		c_TotalP = c_TotalP + c_FinalP;
					}else{
						c_TotalP = c_TotalP + s_FinalP;
					}
			}
			break;
			
		case "Stock - Risk Items":
			sTablePath="//p[text()='Stock - Risk Items']//following-sibling::table[1]";
			s_table= driver.findElement(By.xpath(sTablePath));
			k.ScrollInVewWebElement(s_table);
			
			 
			totalRows = s_table.findElements(By.tagName("tr")).size();
			internal_data_count = at_global_no_of_property-1;
			for(int i = 0; i< totalRows-2; i++){
				s_Description = k.getTextByXpath(sTablePath+"/tbody/tr["+(i+1)+"]/td[1]").replace(" ","");
				
				s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
				s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
				s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
				s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
				s_BookRate = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[6]/input"));
				s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
				s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
				s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
				s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
				s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
				
				i_abvr = "Stock_"+s_Description+"_DeclaredValue";
				c_DeclaredValue = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr));
				
				//CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				String key = s_Description;
				c_BookRatePerc = Double.parseDouble(OFC_Rater.getProperty(key));
				
				//CommonFunction.compareValues(c_BookRatePerc , s_BookRatePerc ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				c_BookP = (c_DeclaredValue*c_BookRatePerc)/100;
				//CommonFunction.compareValues(c_BookP , s_BookP ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				c_Auto_Adjustemnt = getAutoAdjustment("MD");
				c_BookRate = ((100 + c_Auto_Adjustemnt)/100) * c_BookP;
				//CommonFunction.compareValues(c_BookRate , s_BookRate ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				i_abvr = s_Description;
				c_TechAdjust = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr+"_TechAdjust"));
				
				c_RevisedP = ((c_TechAdjust +100)/100) * c_BookRate;
				//CommonFunction.compareValues(c_RevisedP , s_RevisedP ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				c_CommAdjust = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr+"_CommAdjust"));
				c_FinalP = ((c_TechAdjust +100)/100) * c_RevisedP;
				c_OverrideP = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr+"_PremiumOverride"));
			
				if(c_OverrideP != 0.00){
					c_FinalP = c_OverrideP;
				}
			
				// Compare values :
				
				try {
					
					CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +"</i> of cover - "+s_CoverName);										
					CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(c_FinalP==s_FinalP){
               		c_TotalP = c_TotalP + c_FinalP;
					}else{
						c_TotalP = c_TotalP + s_FinalP;
					}
			}
			break;
			
		case "Frozen/Refrigerated Stock - Risk Items":
			sTablePath="//p[text()='Frozen/Refrigerated Stock - Risk Items']//following-sibling::table[1]";
			s_table= driver.findElement(By.xpath(sTablePath));
			k.ScrollInVewWebElement(s_table);
			 
			totalRows = s_table.findElements(By.tagName("tr")).size();
			internal_data_count = at_global_no_of_property-1;
			for(int i = 0; i< totalRows-2; i++){
				s_Description = k.getTextByXpath(sTablePath+"/tbody/tr["+(i+1)+"]/td[1]").replace(" ","");
				
				s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
				s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
				s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
				s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
				s_BookRate = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[6]/input"));
				s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
				s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
				s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
				s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
				s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
				
				i_abvr = s_Description+"_DeclaredValue";
				c_DeclaredValue = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr));
				
				//CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				String key = s_Description;
				c_BookRatePerc = Double.parseDouble(OFC_Rater.getProperty(key));
				
				//CommonFunction.compareValues(c_BookRatePerc , s_BookRatePerc ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				c_BookP = (c_DeclaredValue*c_BookRatePerc)/100;
				//CommonFunction.compareValues(c_BookP , s_BookP ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				c_Auto_Adjustemnt = getAutoAdjustment("MD");
				c_BookRate = ((100 + c_Auto_Adjustemnt)/100) * c_BookP;
				//CommonFunction.compareValues(c_BookRate , s_BookRate ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				i_abvr = s_Description;
				c_TechAdjust = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr+"_TechAdjust"));
				
				c_RevisedP = ((c_TechAdjust +100)/100) * c_BookRate;
				//CommonFunction.compareValues(c_RevisedP , s_RevisedP ,"Declared Value for Item - <i>"+s_Description +"</i> of cover - "+s_CoverName);
				
				c_CommAdjust = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr+"_CommAdjust"));
				c_FinalP = ((c_TechAdjust +100)/100) * c_RevisedP;
				c_OverrideP = Double.parseDouble(internal_data_map.get("Property Details").get(internal_data_count).get(i_abvr+"_PremiumOverride"));
			
				if(c_OverrideP != 0.00){
					c_FinalP = c_OverrideP;
				}
			
				// Compare values :
				
				try {
					
					CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +"</i> of cover - "+s_CoverName);										
					CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(c_FinalP==s_FinalP){
               		c_TotalP = c_TotalP + c_FinalP;
					}else{
						c_TotalP = c_TotalP + s_FinalP;
					}
			}
			break;
		case "Business Interruption" :
		
			sCol_PremiumSummary = "PS_BusinessInterruption_";
			for(int i = 0; i< totalRows-2; i++){
				s_Description = k.getTextByXpath(sTablePath+"/tbody/tr["+(i+1)+"]/td[1]").replace(" ","");
				
				s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
				s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
				s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
				s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
				s_BookRate = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[6]/input"));
				s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
				s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
				s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
				s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
				s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
				
				c_DeclaredValue = 0;
				double temp = 0;
				String[] properties = ((String)map_data.get("IP_AddProperty")).split(";");
	            int noOfProperties = properties.length;
				int count1=0;
				
	            if(s_Description.equalsIgnoreCase("GrossProfit")){
					while(count1<noOfProperties)
					{
						c_DeclaredValue = c_DeclaredValue + Double.parseDouble(internal_data_map.get("Property Details").get(count1).get("BI_GrossProfit"));
						count1++;
					}
					c_BookRatePerc = 0;
					c_BookP = get_BI_GrossProfit_BookPremium(c_DeclaredValue);
					c_Auto_Adjustemnt = getAutoAdjustment("BI");
				}
	            else if(s_Description.equalsIgnoreCase("RentReceivable")){
	            	while(count1<noOfProperties)
					{
						c_DeclaredValue = c_DeclaredValue + Double.parseDouble(internal_data_map.get("Property Details").get(count1).get("BI_RentRecievable"));
						count1++;
					}
	            	temp = c_DeclaredValue;
	            	String period = (String)map_data.get("BI_IndemnityPeriod");
	            	switch(period){
	            	case "24":
	            		c_DeclaredValue*=2;
	            		break;
	            	case "36":
	            		c_DeclaredValue*=3;
	            		break;
	            	case "48":
	            		c_DeclaredValue*=4;
	            		break;
	            	}
	            	i_abvr = "OFC_BI_"+s_Description;
					c_BookRatePerc = Double.parseDouble(OFC_Rater.getProperty(i_abvr));
	            	c_BookP = (c_DeclaredValue*c_BookRatePerc)/100;
	            	c_DeclaredValue = temp;
	            	c_Auto_Adjustemnt = getAutoAdjustment("BI");
	            }
	            else if(s_Description.equalsIgnoreCase("AdditionalICOW")){
	            	while(count1<noOfProperties)
					{
						c_DeclaredValue = c_DeclaredValue + Double.parseDouble(internal_data_map.get("Property Details").get(count1).get("BI_AdditionalICOW"));
						count1++;
					}
	            	c_BookRatePerc = 0;
	            	c_BookP = 0;
	            	c_Auto_Adjustemnt = 0;
	            }
	            else if(s_Description.equalsIgnoreCase("LossOfLicence")){
	            	while(count1<noOfProperties)
					{
						c_DeclaredValue = c_DeclaredValue + Double.parseDouble(internal_data_map.get("Property Details").get(count1).get("BI_LossOfLicence"));
						count1++;
					}
	            	c_BookRatePerc = 0;
	            	c_BookP = (c_DeclaredValue/50000) * 45.5;
	            	c_Auto_Adjustemnt = getAutoAdjustment("BI");
	            }
	            else if(!s_Description.contains("Bespoke")){
	            	c_DeclaredValue = Double.parseDouble((String)map_data.get("BI_"+s_Description));
	            	c_BookRatePerc = 0;
	            	c_BookP = 0;
	            	c_Auto_Adjustemnt = 0;
	            }
	            else{
	            	c_DeclaredValue = Double.parseDouble((String)internal_data_map.get("AddBespokeSumInsBI").get(internal_data_count).get("BI_BSI_SumInsured"));
	            	c_BookRatePerc = 0;
	            	c_BookP = 0;
	            	c_Auto_Adjustemnt = 0;
	            	
	            }
	    	
				
				c_BookRate = ((100 + c_Auto_Adjustemnt)/100) * c_BookP;
				
				if(!s_Description.contains("Bespoke")){
					i_abvr = "BI_"+s_Description;
					c_TechAdjust = Double.parseDouble((String)map_data.get(i_abvr+"_TechAdjust"));
				
					c_RevisedP = ((c_TechAdjust +100)/100) * c_BookRate;
				
					c_CommAdjust =Double.parseDouble((String)map_data.get(i_abvr+"_CommAdjust"));
				}
				else{
					i_abvr = "BI_BSI_";
					c_TechAdjust = Double.parseDouble((String)internal_data_map.get("AddBespokeSumInsBI").get(internal_data_count).get(i_abvr+"TechAdjust"));
					c_CommAdjust = Double.parseDouble((String)internal_data_map.get("AddBespokeSumInsBI").get(internal_data_count).get(i_abvr+"CommAdjust"));
					c_OverrideP = Double.parseDouble((String)internal_data_map.get("AddBespokeSumInsBI").get(internal_data_count).get(i_abvr+"PremiumOverride"));
					c_RevisedP = 0;
					internal_data_count++;
				}
				
				c_FinalP = ((c_CommAdjust +100)/100) * c_RevisedP;
				if(!s_Description.contains("Bespoke")){
					c_OverrideP = Double.parseDouble((String)map_data.get(i_abvr+"_PremiumOverride"));
				}
				if(c_OverrideP != 0.00){
					c_FinalP = c_OverrideP;
				}
			
				// Compare values :
				
				try {
					
					CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +"</i> of cover - "+s_CoverName);										
					CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
					CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(c_FinalP==s_FinalP){
               		c_TotalP = c_TotalP + c_FinalP;
					}else{
						c_TotalP = c_TotalP + s_FinalP;
					}
			}
			
			break;
				
			case "Money" :
				
				sCol_PremiumSummary = "PS_Money_";
				customAssert.assertTrue(calculate_Book_Rate("MONEY"), "Issue in book Rate calculation for MONEY cover");
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						continue;
					}

					s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
					s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
					s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
					s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
					s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
					s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
					s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
					s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
					s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
					
					i_abvr = "MONEY_"+s_Description.replaceAll(" ", "")+"_";
					
					c_DeclaredValue = Double.parseDouble((String)map_data.get("MONEY_"+s_Description.replaceAll(" ", "")));
					c_TechAdjust = Double.parseDouble((String)map_data.get(i_abvr+"TechAdjust"));
					c_CommAdjust = Double.parseDouble((String)map_data.get(i_abvr+"CommAdjust"));
					c_OverrideP = Double.parseDouble((String)map_data.get(i_abvr+"PremiumOverride"));
					c_BookRatePerc  = common_OFC.Book_rate_Rater_output.get("MONEY"+"_"+s_Description);			
					c_Auto_Adjustemnt  = getAutoAdjustment("MONEY");
					//OFC_SingleLossLimit_MONEY_AUTO_ADJUST\
					double min_premium = 0.0;
					
					//Minimum Premium Rule
					
					try {
						min_premium = Double.parseDouble(OFC_Rater.getProperty("OFC_"+s_Description.replaceAll(" ", "")+"_MONEY_MIN_PREMIUM"));
						
						if((c_DeclaredValue * c_BookRatePerc)/100 < min_premium)
							c_BookP = min_premium;
						else
							c_BookP = ((c_DeclaredValue * c_BookRatePerc)/100);
							
					}catch(NullPointerException npe) {
						c_BookP = ((c_DeclaredValue * c_BookRatePerc)/100);
					}
					
					c_BookRate = c_BookP + ((c_BookP * c_Auto_Adjustemnt)/100);
					c_RevisedP =  c_BookRate + ((c_BookRate * c_TechAdjust)/100);
					temp_P = c_RevisedP + ((c_RevisedP*c_CommAdjust)/100);
				
					if(c_OverrideP == 0.00){
						c_FinalP = temp_P;
					}else{
						c_FinalP = c_OverrideP;
					}
				
					// Compare values :
					
					try {
						
						CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +"</i> of cover - "+s_CoverName);										
						CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item  - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item  - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item  - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item  - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item  - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item  - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item  - "+s_Description +" of cover - "+s_CoverName);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(c_FinalP==s_FinalP){
                   		c_TotalP = c_TotalP + c_FinalP;
   					}else{
   						c_TotalP = c_TotalP + s_FinalP;
   					}
				}
				break;
				
			case "Employers Liability" :
				
				sCol_PremiumSummary = "PS_EmployersLiability_";
				internal_data_sheet = "AddBespokeSumInsEL";
				
				customAssert.assertTrue(calculate_Book_Rate("EL"), "Issue in book Rate calculation for Employers Liability cover");
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						continue;
					}

					s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
					s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
					s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
					s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
					s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
					s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
					s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
					s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
					s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
					
					if(!s_Description.contains("Bespoke"))
						i_abvr = "EL_"+s_Description.replaceAll(" ", "")+"_";
					else
						i_abvr = "EL_BSI_";
					
					if(s_Description.contains("Pump Attendants")) {
						c_DeclaredValue = Double.parseDouble((String)map_data.get("EL_Wages_PumpAttendants")) + Double.parseDouble((String)map_data.get("EL_Wages_OtherShopStaff"));
					}else {
						if(s_Description.contains("Bespoke"))
							c_DeclaredValue = Double.parseDouble((String)internal_data_map.get(internal_data_sheet).get(internal_data_count).get(i_abvr+"Suminsured"));
						else
							c_DeclaredValue = Double.parseDouble((String)map_data.get("EL_Wages_"+s_Description.replaceAll(" ", "")));
					}
					
					if(s_Description.contains("Bespoke")) {
						c_TechAdjust = Double.parseDouble((String)internal_data_map.get(internal_data_sheet).get(internal_data_count).get(i_abvr+"TechAdjust"));
						c_CommAdjust = Double.parseDouble((String)internal_data_map.get(internal_data_sheet).get(internal_data_count).get(i_abvr+"CommAdjust"));
						c_OverrideP = Double.parseDouble((String)internal_data_map.get(internal_data_sheet).get(internal_data_count).get(i_abvr+"PremiumOverride"));
						internal_data_count++;
					}else {
						c_TechAdjust = Double.parseDouble((String)map_data.get(i_abvr+"TechAdjust"));
						c_CommAdjust = Double.parseDouble((String)map_data.get(i_abvr+"CommAdjust"));
						c_OverrideP = Double.parseDouble((String)map_data.get(i_abvr+"PremiumOverride"));
					}
					
					c_BookRatePerc  = common_OFC.Book_rate_Rater_output.get("EL"+"_"+s_Description);			
					c_Auto_Adjustemnt  = getAutoAdjustment("EL");
						
					c_BookP = ((c_DeclaredValue * c_BookRatePerc)/100);
					c_BookRate = c_BookP + ((c_BookP * c_Auto_Adjustemnt)/100);
					c_RevisedP =  c_BookRate + ((c_BookRate * c_TechAdjust)/100);
					temp_P = c_RevisedP + ((c_RevisedP*c_CommAdjust)/100);
				
					if(c_OverrideP == 0.00){
						c_FinalP = temp_P;
					}else{
						c_FinalP = c_OverrideP;
					}
				
					// Compare values :
					
					try {
						
						CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +"</i> of cover - "+s_CoverName);										
						CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(c_FinalP==s_FinalP){
                   		c_TotalP = c_TotalP + c_FinalP;
   					}else{
   						c_TotalP = c_TotalP + s_FinalP;
   					}
					
				}
				break;
				
			case "Public and Products Liability" :
						
				sCol_PremiumSummary = "PS_PublicandProductsLiability_";
				
				//customAssert.assertTrue(calculate_Book_Rate("PPL"), "Issue in book Rate calculation for Public and Products Liability cover");
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						continue;
					}

					s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
					s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
					s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
					s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
					s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
					s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
					s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
					s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
					s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
					
					i_abvr = "PPL_"+s_Description.replaceAll(" ", "")+"_";
					
					if(!((String)map_data.get("PPL_LimitofIndemnity")).equals("Other")){
						c_DeclaredValue = Double.parseDouble((String)map_data.get("PPL_LimitofIndemnity"));
					}
					else{
						c_DeclaredValue = Double.parseDouble((String)map_data.get("PPL_LOI_Other"));
					}
					c_TechAdjust = Double.parseDouble((String)map_data.get(i_abvr+"TechAdjust"));
					c_CommAdjust = Double.parseDouble((String)map_data.get(i_abvr+"CommAdjust"));
					c_OverrideP = Double.parseDouble((String)map_data.get(i_abvr+"PremiumOverride"));
					c_BookRatePerc  = Double.parseDouble(OFC_Rater.getProperty("PPL_PublicLiabilityIndemnityLimit_BR"));			
					c_Auto_Adjustemnt  = getAutoAdjustment("PPL");
					
					c_BookP = get_PPL_BookPremium((String)map_data.get("PPL_LimitofIndemnity"));
					
					c_BookRate = c_BookP + ((c_BookP * c_Auto_Adjustemnt)/100);
					c_RevisedP =  c_BookRate + ((c_BookRate * c_TechAdjust)/100);
					temp_P = c_RevisedP + ((c_RevisedP*c_CommAdjust)/100);
				
					if(c_OverrideP == 0.00){
						c_FinalP = temp_P;
					}else{
						c_FinalP = c_OverrideP;
					}
				
					// Compare values :
					
					try {
						
						CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +"</i> of cover - "+s_CoverName);										
						CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(c_FinalP==s_FinalP){
                   		c_TotalP = c_TotalP + c_FinalP;
   					}else{
   						c_TotalP = c_TotalP + s_FinalP;
   					}
				}
				break;
				
			case "Specified All Risks" :
				
				sCol_PremiumSummary = "PS_SpecifiedAllRisks_";
				internal_data_sheet = "AddBespokeSumInsSAR";
				
				customAssert.assertTrue(calculate_Book_Rate("SAR"), "Issue in book Rate calculation for Specified All Risks cover");
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						continue;
					}

					s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
					s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
					s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
					s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
					s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
					s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
					s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
					s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
					s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
					
					if(!s_Description.contains("Bespoke"))
						i_abvr = "SAR_"+s_Description.replaceAll(" ", "")+"_";
					else
						i_abvr = "SAR_BSI_";
					
					if(s_Description.contains("Bespoke"))
						c_DeclaredValue = Double.parseDouble((String)internal_data_map.get(internal_data_sheet).get(internal_data_count).get(i_abvr+"SumInsured"));
					else
						c_DeclaredValue = Double.parseDouble((String)map_data.get("SAR_"+s_Description.replaceAll(" ", "")));
					
					if(s_Description.contains("Bespoke")) {
						c_TechAdjust = Double.parseDouble((String)internal_data_map.get(internal_data_sheet).get(internal_data_count).get(i_abvr+"TechAdjust"));
						c_CommAdjust = Double.parseDouble((String)internal_data_map.get(internal_data_sheet).get(internal_data_count).get(i_abvr+"CommAdjust"));
						c_OverrideP = Double.parseDouble((String)internal_data_map.get(internal_data_sheet).get(internal_data_count).get(i_abvr+"PremiumOverride"));
						internal_data_count++;
					}else {
						c_TechAdjust = Double.parseDouble((String)map_data.get(i_abvr+"TechAdjust"));
						c_CommAdjust = Double.parseDouble((String)map_data.get(i_abvr+"CommAdjust"));
						c_OverrideP = Double.parseDouble((String)map_data.get(i_abvr+"PremiumOverride"));
					}
					
					c_BookRatePerc  = common_OFC.Book_rate_Rater_output.get("SAR"+"_"+s_Description);			
					c_Auto_Adjustemnt  = getAutoAdjustment("SAR");
						
					c_BookP = ((c_DeclaredValue * c_BookRatePerc)/100);
					c_BookRate = c_BookP + ((c_BookP * c_Auto_Adjustemnt)/100);
					c_RevisedP =  c_BookRate + ((c_BookRate * c_TechAdjust)/100);
					temp_P = c_RevisedP + ((c_RevisedP*c_CommAdjust)/100);
				
					if(c_OverrideP == 0.00){
						c_FinalP = temp_P;
					}else{
						c_FinalP = c_OverrideP;
					}
				
					// Compare values :
					
					try {
						
						CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +" of cover - "+s_CoverName);										
						CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(c_FinalP==s_FinalP){
                   		c_TotalP = c_TotalP + c_FinalP;
   					}else{
   						c_TotalP = c_TotalP + s_FinalP;
   					}
					
				}
				break;
				
			case "Personal Accident" :
				sCol_PremiumSummary = "PS_PersonalAccident_";
				//customAssert.assertTrue(calculate_Book_Rate("PA"), "Issue in book Rate calculation for Personal Accident cover");
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						continue;
					}

					s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
					s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
					s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
					s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
					s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
					s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
					s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
					s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
					s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
					
					i_abvr = "PA_"+s_Description.replaceAll(" ", "")+"_";
					
					c_DeclaredValue = Double.parseDouble((String)map_data.get("PA_"+s_Description.replaceAll(" ", "")+"_DV"));
					
					c_TechAdjust = Double.parseDouble((String)map_data.get(i_abvr+"TechAdjust"));
					c_CommAdjust = Double.parseDouble((String)map_data.get(i_abvr+"CommAdjust"));
					c_OverrideP = Double.parseDouble((String)map_data.get(i_abvr+"PremiumOverride"));
					c_BookRatePerc  = Double.parseDouble(OFC_Rater.getProperty("PA_MaximumAnyOneLoss_BR"));			
					c_Auto_Adjustemnt  = Double.parseDouble(OFC_Rater.getProperty(i_abvr+"AutoAdjustment"));
					//
					double book_premium_per_site = Double.parseDouble(OFC_Rater.getProperty("OFC_Maximum_Any_One_Loss_PA_BookPremium"));
					
					c_BookP = book_premium_per_site * at_global_no_of_property;
					
					c_BookRate = c_BookP + ((c_BookP * c_Auto_Adjustemnt)/100);
					c_RevisedP =  c_BookRate + ((c_BookRate * c_TechAdjust)/100);
					temp_P = c_RevisedP + ((c_RevisedP*c_CommAdjust)/100);
				
					if(c_OverrideP == 0.00){
						c_FinalP = temp_P;
					}else{
						c_FinalP = c_OverrideP;
					}
				
					// Compare values :
					
					try {
						
						CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +" of cover - "+s_CoverName);										
						CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(c_FinalP==s_FinalP){
                   		c_TotalP = c_TotalP + c_FinalP;
   					}else{
   						c_TotalP = c_TotalP + s_FinalP;
   					}
				}
				break;
				
			case "Goods in Transit" :
				
				sCol_PremiumSummary = "PS_GoodsInTransit_";
				customAssert.assertTrue(calculate_Book_Rate("GIT"), "Issue in book Rate calculation for GIT cover");
				
				long DV_less_equal_2000 = 0,DV_greater_2000=0,sum_Stock_Vehicle=0;
				
				
				sum_Stock_Vehicle = Integer.parseInt((String)map_data.get("GIT_Stock")) * Integer.parseInt((String)map_data.get("GIT_NoOfVehicles"));
				
				DV_less_equal_2000 = sum_Stock_Vehicle<2000? sum_Stock_Vehicle : 2000;
				DV_greater_2000 = sum_Stock_Vehicle>2000? sum_Stock_Vehicle-2000 : 0;
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						continue;
					}

					s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
					s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
					s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
					s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
					s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
					s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
					s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
					s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
					s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
					
					i_abvr = "GIT_"+s_Description.replaceAll(" ", "")+"_";
					
					if(s_Description.contains("="))
						c_DeclaredValue = DV_less_equal_2000;
					else
						c_DeclaredValue = DV_greater_2000;
					
					c_TechAdjust = Double.parseDouble((String)map_data.get(i_abvr+"TechAdjust"));
					c_CommAdjust = Double.parseDouble((String)map_data.get(i_abvr+"CommAdjust"));
					c_OverrideP = Double.parseDouble((String)map_data.get(i_abvr+"PremiumOverride"));
					c_BookRatePerc  = common_OFC.Book_rate_Rater_output.get("GIT"+"_"+s_Description);			
					c_Auto_Adjustemnt  = getAutoAdjustment("GIT");
						
					c_BookP = ((c_DeclaredValue * c_BookRatePerc)/100);
					c_BookRate = c_BookP + ((c_BookP * c_Auto_Adjustemnt)/100);
					c_RevisedP =  c_BookRate + ((c_BookRate * c_TechAdjust)/100);
					temp_P = c_RevisedP + ((c_RevisedP*c_CommAdjust)/100);
				
					if(c_OverrideP == 0.00){
						c_FinalP = temp_P;
					}else{
						c_FinalP = c_OverrideP;
					}
				
					// Compare values :
					
					try {
						
						CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +" of cover - "+s_CoverName);										
						CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(c_FinalP==s_FinalP){
                   		c_TotalP = c_TotalP + c_FinalP;
   					}else{
   						c_TotalP = c_TotalP + s_FinalP;
   					}
				}
				break;
				
            case "Terrorism" :
                
                sCol_PremiumSummary = "PS_Terrorism_";
                customAssert.assertTrue(calculate_Book_Rate("TER"), "Issue in book Rate calculation for Terrorism cover");
                                            
                for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						continue;
					}

					s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
					s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
					s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
					s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
					s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
					s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
					s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
					s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
					s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
					
					i_abvr = "TER_"+s_Description.replaceAll(" ", "")+"_";
					
					c_DeclaredValue = Double.parseDouble((String)map_data.get("TER_"+s_Description.replaceAll(" ", "")));
					
					c_TechAdjust = Double.parseDouble((String)map_data.get(i_abvr+"TechAdjust"));
					c_CommAdjust = Double.parseDouble((String)map_data.get(i_abvr+"CommAdjust"));
					c_OverrideP = Double.parseDouble((String)map_data.get(i_abvr+"PremiumOverride"));
					c_BookRatePerc  = Double.parseDouble(OFC_Rater.getProperty("OFC_"+s_Description.replaceAll(" ", "")+"_TER_BR"));
					c_Auto_Adjustemnt  = getAutoAdjustment("TER");
						
					c_BookP = ((c_DeclaredValue * c_BookRatePerc)/100);
					c_BookRate = c_BookP + ((c_BookP * c_Auto_Adjustemnt)/100);
					c_RevisedP =  c_BookRate + ((c_BookRate * c_TechAdjust)/100);
					temp_P = c_RevisedP + ((c_RevisedP*c_CommAdjust)/100);
				
					if(c_OverrideP == 0.00){
						c_FinalP = temp_P;
					}else{
						c_FinalP = c_OverrideP;
					}
				
					// Compare values :
					
					try {
						
						CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +" of cover - "+s_CoverName);										
						CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(c_FinalP==s_FinalP){
                   		c_TotalP = c_TotalP + c_FinalP;
   					}else{
   						c_TotalP = c_TotalP + s_FinalP;
   					}
				}
                break;

				
			case "Legal Expenses" :
				
				sCol_PremiumSummary = "PS_LegalExpenses_";
				
				for(int i = 0; i< totalRows-2; i++){
					s_Description = k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[1]");
					
					if(s_Description.equalsIgnoreCase("Flat Premium")){
						continue;
					}

					s_DeclaredValue = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[2]/input"));
					s_BookRatePerc  = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[3]/input"));
					s_BookP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[4]/input"));
					s_Auto_Adjustemnt = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[5]/input"));
					s_TechAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[7]/input"));
					s_RevisedP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[8]/input"));
					s_CommAdjust = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[9]/input"));
					s_OverrideP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[10]/input"));
					s_FinalP = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, sTablePath+"/tbody/tr["+(i+1)+"]/td[11]/input"));
					
					i_abvr = "LE_"+s_Description.replaceAll(" ", "")+"_";
					
					c_DeclaredValue = Double.parseDouble((String)map_data.get("LE_"+s_Description.replaceAll(" ", "")));
						
					c_TechAdjust = Double.parseDouble((String)map_data.get(i_abvr+"TechAdjust"));
					c_CommAdjust = Double.parseDouble((String)map_data.get(i_abvr+"CommAdjust"));
					c_OverrideP = Double.parseDouble((String)map_data.get(i_abvr+"PremiumOverride"));
					c_BookRatePerc  = Double.parseDouble(OFC_Rater.getProperty("LE_Turnover_BR"));			
					c_Auto_Adjustemnt  = Double.parseDouble(OFC_Rater.getProperty(i_abvr+"AutoAdjustment"));
					
					double book_premium_per_site = Double.parseDouble(OFC_Rater.getProperty("OFC_AggregateRetailTurnoverfromallSites<£2.5M_LE_BookPremium"));
					
					c_BookP = book_premium_per_site * at_global_no_of_property;
						
					c_BookRate = c_BookP + ((c_BookP * c_Auto_Adjustemnt)/100);
					c_RevisedP =  c_BookRate + ((c_BookRate * c_TechAdjust)/100);
					temp_P = c_RevisedP + ((c_RevisedP*c_CommAdjust)/100);
				
					if(c_OverrideP == 0.00){
						c_FinalP = temp_P;
					}else{
						c_FinalP = c_OverrideP;
					}
				
					// Compare values :
					
					try {
						
						CommonFunction.compareValues(c_DeclaredValue , s_DeclaredValue ,"Declared Value for Item - "+s_Description +" of cover - "+s_CoverName);										
						CommonFunction.compareValues(c_BookRatePerc, s_BookRatePerc,"BookRate % for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_BookP, s_BookP,"BookP for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_Auto_Adjustemnt, s_Auto_Adjustemnt,"Auto-Adjustment (%) for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_TechAdjust, s_TechAdjust,"TechAdjust for Item - "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_RevisedP, s_RevisedP,"RevisedP for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_CommAdjust, s_CommAdjust,"CommAdjust for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_OverrideP, s_OverrideP,"PremiumOverride for Item "+s_Description +" of cover - "+s_CoverName);
						CommonFunction.compareValues(c_FinalP, s_FinalP,"FinalPremium for Item "+s_Description +" of cover - "+s_CoverName);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(c_FinalP==s_FinalP){
                   		c_TotalP = c_TotalP + c_FinalP;
   					}else{
   						c_TotalP = c_TotalP + s_FinalP;
   					}
				}
				break;
		}
		}
		catch(Exception e)
		{
			return false;
		}
	  TestUtil.WriteDataToXl(CommonFunction.product+"_"+common.currentRunningFlow, "Premium Summary", (String)map_data.get("Automation Key"), sCol_PremiumSummary+"NetNetPremium", String.valueOf(c_TotalP), map_data);
		return retVal;
	}

/**
 * 
 * This method handles CCD Flat Premium screens scripting.
 * 
 */

public boolean func_Flat_Premiums_(Map<Object, Object> map_data,Map<String, List<Map<String, String>>> internal_data_map){
	
	boolean retvalue = true;
	String isFlatPremium=null;
	try{
		isFlatPremium = (String)map_data.get("FP_isFlatPremium");
		if(isFlatPremium.equalsIgnoreCase("Yes"))
			customAssert.assertTrue(common.funcButtonSelection("Flat Premiums"), "Error while clicking on Flat Premiums button .");
		else
			return true;
		
		customAssert.assertTrue(common.funcPageNavigation("Flat Premiums", ""), "Navigation Problem to Flat Premiums page .");
		
		String[] fp_entries = ((String)map_data.get("FP_FlatPremium_Entries")).split(";");
		int no_of_fp_e = fp_entries.length;
		int[] total_fp_e = new int[no_of_fp_e];
		k.ImplicitWaitOff();
		for(int count=1;count<=no_of_fp_e;count++){
			
			customAssert.assertTrue(k.Click("POF_Add_Flat_P_btn"),"Unable to Click Add Flat Premium button . ");
			customAssert.assertTrue(Verify_FP_Section_Values(),"Error while verifying covers list in flat premium section dropdown . ");

			customAssert.assertTrue(k.DropDownSelection("POF_FP_Section", internal_data_map.get("Flat-Premiums").get(count-1).get("FP_Section")),"Unable to enter FP_Section in Flat Premium page .");
			customAssert.assertTrue(k.Input("POF_FP_Premium", internal_data_map.get("Flat-Premiums").get(count-1).get("FP_Premium")),"Unable to enter FP_Premium in Flat Premium page .");
			customAssert.assertTrue(k.Input("POF_FP_TaxRate", internal_data_map.get("Flat-Premiums").get(count-1).get("FP_TaxRate")),"Unable to enter FP_TaxRate in Flat Premium page .");
			customAssert.assertTrue(k.Input("POF_FP_Description", internal_data_map.get("Flat-Premiums").get(count-1).get("FP_Description")),"Unable to enter FP_Description in Flat Premium page .");
			
			customAssert.assertTrue(k.clickInnerButton("Inner_page_locator", "Save"), "Unable to click Inner Save button on Flat Premiums .");
			
			customAssert.assertTrue(get_Flat_Premium_Entries(count), "Error while reading Flat Premium Entries .");
			common_HHAZ.func_FP_Entries_Verification_MTA(internal_data_map.get("Flat-Premiums").get(count-1).get("FP_Section"),internal_data_map,count);
			
			//For each added entry in FP, cover name will be removed from Section List
			common_HHAZ.CoversDetails_data_list.remove(internal_data_map.get("Flat-Premiums").get(count-1).get("FP_Section").replaceAll(" ", ""));
		}
				
		driver.findElements(By.xpath("//*[@id='ex-back']")).get(0).click();
	
		TestUtil.reportStatus("Entered and Verified all the details on Flat Premiums page .", "Info", true);
		k.ImplicitWaitOn();
		return retvalue;
		
	}catch(Throwable t) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
        Assert.fail("Unable to to do operation on Flat Premium page. \n", t);k.ImplicitWaitOn();
        return false;
 }
}

public boolean get_Flat_Premium_Entries(int row_index){
	
	
	try{
	String testName = (String)common.MTA_excel_data_map.get("Automation Key");
	
	customAssert.assertTrue(common.funcPageNavigation("Flat Premiums", ""),"Flat Premiums page navigations issue(S)");
	
	if(TestBase.businessEvent.equalsIgnoreCase("Renewal")){
		int policy_Duration = Integer.parseInt((String)common.Renewal_excel_data_map.get("PS_Duration"));
	}else{
		int policy_Duration = Integer.parseInt((String)common.NB_excel_data_map.get("PS_Duration"));
	}
	
	
	String FPTble_xpath = "//table[@id='table0']";
	WebElement fp_Table = driver.findElement(By.xpath(FPTble_xpath));
	
	List<WebElement> colms = fp_Table.findElements(By.tagName("th"));
		//Map<SPI,MAP<NNP,12345.67>>
	
	int fp_tble_Rows = fp_Table.findElements(By.tagName("tr")).size();
	int fp_tble_Cols = colms.size();
	
	List<String> sectionNames = new ArrayList<>();
	String sectionName = null;
	String sectionValue = null;
	String headerName = null;
	
		
	if(fp_Table.isDisplayed()){
		
		//For Each Cover Row
		for(int row = row_index; row < fp_tble_Rows ;row ++){
			
			WebElement sec_Name = driver.findElement(By.xpath(FPTble_xpath+"//tbody//tr["+row+"]//td["+1+"]"));
			sectionName = sec_Name.getText();
			
			switch(sectionName){
			
			case "Totals":
				Map<String,Double> fp_Section_Vals_Total = new HashMap<>();
				//For Each Cols
				for(int col = 2; col <= fp_tble_Cols ;col ++){
			
					////p[text()=' Transaction Premium']//following-sibling::table[@id='table0']//thead//th[2]
					WebElement header_Name = driver.findElement(By.xpath(FPTble_xpath+"//thead//th["+col+"]"));
					headerName = header_Name.getText();
				
					if(!headerName.contains("Pen Comm %") && !headerName.contains("Broker Comm %") && !headerName.contains("Gross Comm %")
							&& !headerName.contains("Insurance Tax Rate") ){
						WebElement sec_Val = driver.findElement(By.xpath(FPTble_xpath+"//tbody//tr["+row+"]//td["+col+"]"));
						sectionValue = sec_Val.getText();
						sectionValue = sectionValue.replaceAll(",", "");
						fp_Section_Vals_Total.put(headerName, Double.parseDouble(sectionValue));
						
					}else{
						continue;
					}
					common.transaction_Details_Premium_Values.put(sectionName, fp_Section_Vals_Total);
			}
			
			break;
			
			default:
				Map<String,Double> fp_Section_Vals = new HashMap<>();
				//For Each Cols
				for(int col = 2; col <= fp_tble_Cols-2 ;col ++){
			
					////p[text()=' Transaction Premium']//following-sibling::table[@id='table0']//thead//th[2]
					WebElement header_Name = driver.findElement(By.xpath(FPTble_xpath+"//thead//th["+col+"]"));
					headerName = header_Name.getText();
					
					if(headerName.equalsIgnoreCase("Pen Commm")){
						headerName = "Pen Comm";
					}
				
					WebElement sec_Val = driver.findElement(By.xpath(FPTble_xpath+"//tbody//tr["+row+"]//td["+col+"]"));
					sectionValue = sec_Val.getText();
				
					fp_Section_Vals.put(headerName, Double.parseDouble(sectionValue));
			}
				common.transaction_Details_Premium_Values.put(sectionName+"_FP", fp_Section_Vals);
			
			break;
			
			}
			
		}
	
	}

	
}catch(Throwable t){
	
	//System.out.println("Error while reading Flat Premium Entries . ");
	return false;
	
	
}
	
	return true;
	
}

public boolean Verify_FP_Section_Values(){
	
	try{
		 
		int count = 0 , count_datasheet = 0;
		 String coverName = null , coverName_datasheet = null;
		 
		 	List<WebElement> names = driver.findElements(By.tagName("option"));
			List<String> coversNameList = new ArrayList<>();
			String coverName_withoutSpace =null,key=null;
			
			for(int i=0;i<names.size();i++){
				coverName = names.get(i).getText();
					
				coverName_withoutSpace = coverName.replaceAll(" ", "");
			
				coversNameList.add(coverName_withoutSpace);
				
				if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
						key = "CD_"+coverName_withoutSpace;
								
						if(common.MTA_excel_data_map.get(key).toString().equalsIgnoreCase("Yes")){
							continue;
						}else{
							if(common.MTA_excel_data_map.get("CD_Add_"+coverName_withoutSpace.replaceAll(" ", "")).toString().equalsIgnoreCase("Yes")){
						
							}else{
								TestUtil.reportStatus("Cover Name <b>  ["+coverName+"]  </b> should not present in the flat fremium section dropdown list as This cover is not selected on Cover Details page.", "FAIL", false);
							count++;
						}
					
					}
					}
				} //For loop
		 
			for(int p=0;p<common_HHAZ.CoversDetails_data_list.size();p++){
				coverName_datasheet = common_HHAZ.CoversDetails_data_list.get(p);
				
				if(coversNameList.contains(coverName_datasheet)){
					continue;
				}else{
					TestUtil.reportStatus("Cover Name <b>  ["+coverName_datasheet+"]  </b> is selected as 'NO' in datasheet but still listed in the flat premium section dropdown list.", "FAIL", false);
					count_datasheet++;
				}
			}
		 
		 
	
	}catch(Throwable t){
		return false;
		
	}
	
	return true;	
	
} 

public void MTAFlow(String code,String fileName) throws ErrorInTestMethod{
	String testName = (String)common.MTA_excel_data_map.get("Automation Key");
	CommonFunction_HHAZ.AdjustedTaxDetails.clear();
	try{
		
		if(((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes")) {
			customAssert.assertTrue(common_EP.ExistingPolicyAlgorithm(common.MTA_excel_data_map , (String)common.MTA_excel_data_map.get("MTA_ExistingPolicy_Type"), (String)common.MTA_excel_data_map.get("MTA_ExistingPolicy_Status")), "Existing Policy Algorithm function is having issues. ");
		}else {
			if(!common.currentRunningFlow.equalsIgnoreCase("Renewal")){
				NewBusinessFlow(code,"NB");
			}
			common_CCD.MD_Building_Occupancies_list.clear();
			common_HHAZ.CoversDetails_data_list.clear();
			common_HHAZ.PremiumFlag = false;
		}

		
		common.currentRunningFlow="MTA";
		String navigationBy = CONFIG.getProperty("NavigationBy");
		
		customAssert.assertTrue(common_CCD.funcCreateEndorsement(),"Error in Create Endorsement function . ");
		customAssert.assertTrue(funcPolicyGeneral(common.MTA_excel_data_map,"POF","MTA"),"Error in Policy General for MTA function.");
		
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Cover"),"Issue while Navigating to Covers . ");
		customAssert.assertTrue(common_HHAZ.funcCovers(common.MTA_excel_data_map),"Error in selecting cover for MTA.");
	/*
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Previous Claims"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcPreviousClaims(common.MTA_excel_data_map), "Previous claim function is having issue(S) .");
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Claims Experience"),"Issue while Navigating to Material Facts and Declarations . ");
		customAssert.assertTrue(funcClaimsExperience(common.MTA_excel_data_map), "Previous claim function is having issue(S) .");
	*/
		//Non-linear cover selection
		customAssert.assertTrue(Cover_Selection_By_Sequence(common.MTA_excel_data_map), "Cover selection by sequence function is having issue(S) .");
		
		
		if(!TestBase.businessEvent.equalsIgnoreCase("Renewal")){
			//customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Endorsements"),"Issue while Navigating to Endorsement screen.");
			//customAssert.assertTrue(common_HHAZ.funcEndorsementOperations(common.MTA_excel_data_map),"Endorsement Operations is having issue(S).");
		}
		customAssert.assertTrue(common.funcNextNavigateDecesionMaker(navigationBy,"Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		customAssert.assertTrue(common_HHAZ.funcPremiumSummary_MTA(common.MTA_excel_data_map,code,"MTA"), "Premium Summary function is having issue(S) . ");
		if(!TestBase.businessEvent.equalsIgnoreCase("Renewal")){
			Assert.assertTrue(common_HHAZ.funcStatusHandling(common.MTA_excel_data_map,code,"MTA"));
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

//MTA

public boolean funcCreateEndorsement(){
	
  boolean retvalue = true;
  Date dateobj = null;
  String testName = null;
  df = new SimpleDateFormat("dd/MM/yyyy");
  
  Map<Object,Object> data_map = null;
	
	switch(common.currentRunningFlow){
		case "MTA":
			data_map = common.MTA_excel_data_map;
			break;
		case "Renewal":
			data_map = common.Renewal_excel_data_map;
			break;
	}
	
	testName = (String)data_map.get("Automation Key");
  
  try {
  	customAssert.assertTrue(common.funcMenuSelection("Navigate", "Premium Summary"), "Navigation problem to Premium Summary page .");
  	customAssert.assertTrue(common.funcPageNavigation("Premium Summary", ""),"Premium Summary page is not loaded to perfrm MTA . ");
 
		customAssert.assertTrue(common.funcButtonSelection("Create Endorsement"), "Unable to click on Create Endorsement button .");
		
  	int ammendmet_period = Integer.parseInt((String)data_map.get("MTA_EndorsementPeriod"));
  	
  	if(CommonFunction.businessEvent.equalsIgnoreCase("Renewal")){
  		if(ammendmet_period > Integer.parseInt((String)common.Renewal_excel_data_map.get("PS_Duration"))){
      		TestUtil.reportStatus("Amendement Period Should not be greater than Policy Duration", "Fail", true);
      		return false;
      	}
  	}else{
  		if(ammendmet_period > Integer.parseInt((String)common.NB_excel_data_map.get("PS_Duration"))){
      		TestUtil.reportStatus("Amendement Period Should not be greater than Policy Duration", "Fail", true);
      		return false;
      	}
  	}
  	TimeZone uk_timezone = TimeZone.getTimeZone("Europe/London");
  	Calendar c = Calendar.getInstance(uk_timezone);
  	
  	if(TestBase.businessEvent.equalsIgnoreCase("Renewal")){
  		int d = Integer.parseInt(((String)data_map.get("PS_PolicyStartDate")).substring(0, 2));
  		dateobj = common.addDays(df.parse((String)common.Renewal_excel_data_map.get("PS_PolicyStartDate")), ammendmet_period);
  		//c.add(d,ammendmet_period);
  		//c.add((String)data_map.get("PS_PolicyStartDate"), ammendmet_period);
  	  	//dateobj = df.parse(df.format(c.getTime()));
  	}else{
  		c.add(Calendar.DATE, ammendmet_period);
  	  	dateobj = df.parse(df.format(c.getTime()));
  	}
  	
  	
  	
  
      customAssert.assertTrue(k.Click("SPI_Endorsement_eff_date"), "Unable to enter Endorsement effective Date.");
      customAssert.assertTrue(k.Type("SPI_Endorsement_eff_date", df.format(dateobj)), "Unable to Enter Endorsement effective Date .");
      customAssert.assertTrue(k.Click("calander_btn"), "Unable to click on done button in calander.");
      customAssert.assertTrue(k.Input("SPI_Reson_for_Endors", (String)data_map.get("MTA_Reason_for_Endorsement")),"Unable to Enter Reason for Endorsement");
      
      if(TestBase.businessEvent.equalsIgnoreCase("Renewal")){
      	WriteDataToXl(TestBase.product+"_"+common.currentRunningFlow, "MTA_Endorsement", testName, "MTA_EffectiveDate", k.getAttribute("SPI_Endorsement_eff_date", "value"),data_map);
      }else{
      	WriteDataToXl(TestBase.product+"_"+TestBase.businessEvent, "MTA_Endorsement", testName, "MTA_EffectiveDate", k.getAttribute("SPI_Endorsement_eff_date", "value"),data_map);
      }
      customAssert.assertTrue(common.funcButtonSelection("Create Endorsement"), "Unable to click on Create Endorsement button .");
     	
      //Writing to MTA Excel
  	WriteDataToXl(TestBase.product+"_"+common.currentRunningFlow, "MTA_Endorsement", testName, "MTA_PolicyNumber", k.getText("SPI_MTA_policy_number"),data_map);
  	if(common.currentRunningFlow.equals("MTA")){
  		if(((String)data_map.get("MTA_Status")).equals("Endorsement Rewind"))
  			WriteDataToXl(TestBase.product+"_"+common.currentRunningFlow, "MTA_Endorsement", testName, "MTA_isMTARewind", "Y",data_map);
  		else
  			WriteDataToXl(TestBase.product+"_"+common.currentRunningFlow, "MTA_Endorsement", testName, "MTA_isMTARewind", "N",data_map);
  	}
      
  	TestUtil.reportStatus("Create Endorsement Details filled successfully . ", "Info", true);
		
      return retvalue;
  
		} catch (ParseException e) {
			//System.out.println("Unable to Parse Endorsement Dates - "+e.getMessage());
			return false;
		}
  	catch(Throwable t) {
           String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
           TestUtil.reportFunctionFailed("Failed in "+methodName+" function");  
           k.reportErr("Failed in "+methodName+" function", t);
           return false;
    }  
}
	
	// Cancellation starts here :

public void CancellationFlow(String code,String event) throws ErrorInTestMethod{
	
	common_HHAZ.cancellationProcess(code,event);
	
}


public boolean CancelPolicy(Map<Object, Object> map_data) throws ErrorInTestMethod{
    boolean retVal = true;
    int dateDif = Integer.parseInt((String)map_data.get("CP_AddDifference"));
    df = new SimpleDateFormat("dd/MM/yyyy");
    //String Cancellation_date = common.daysIncrementWithOutFormation(df.format(currentDate), dateDif);
    try{
    	
    		//UK time zone change
    		Date c_date = df.parse(common.getUKDate());
    		String Cancellation_date = common.daysIncrementWithOutFormation(df.format(c_date), dateDif);
    	   
           customAssert.assertTrue(common.funcMenuSelection("Navigate", "Premium Summary"), "Unable to navigate to Premium Summary screen");
           customAssert.assertTrue(common.funcButtonSelection("Cancel Policy"), "Unable to click on Cancel Policy Button");
                        
           customAssert.assertTrue(k.Click("CCD_CP_CancellationDate"), "Unable to enter Cancellation date.");
           customAssert.assertTrue(k.Input("CCD_CP_CancellationDate", Cancellation_date),"Unable to Enter Cancellation date.");
           customAssert.assertTrue(k.Click("SPI_Btn_Calender_Done"), "Unable to click on done button in calander.");
           customAssert.assertTrue(!k.getAttributeIsEmpty("CCD_CP_CancellationDate", "value"),"CCD_CP_CancellationDate Field Should Contain Valid Value on Cancel Policy page .");
           
           common.CAN_excel_data_map.put("Cancellation_date", Cancellation_date);
           common.CAN_excel_data_map.put("CP_CancellationDate", Cancellation_date);
           
           customAssert.assertTrue(k.Input("CCD_CP_CancellationReason", (String)map_data.get("CP_CancellationReason")),"Unable to Enter Cancellation Reason.");
           customAssert.assertTrue(common.funcButtonSelection("Continue"), "Unable to click on Cancel Policy Button");
                        
           // Read Cancellation Return Premium Summary and put values to Map  :
           
           Cancel_RetrunPremiumTable(map_data);
           
           customAssert.assertTrue(k.Click("COB_Btn_FinalCancelPolicy"), "Unable to click on Cancel Polcy button after verifying Cancellation Return Premium");
           customAssert.assertTrue(k.AcceptPopup(), "Unable to handl pop up");
                  
           return retVal;
           
    
    }catch(Throwable t){
          return false;
    }           
}

public boolean Cancel_RetrunPremiumTable(Map<Object, Object> map_data) throws ErrorInTestMethod{
	boolean retVal = true;
	String testName = (String)common.CAN_excel_data_map.get("Automation Key");
	
	try{
					
		int policy_Duration = Integer.parseInt(k.getText("CCD_CAN_Duration"));
		int DaysRemain = Integer.parseInt(k.getText("CCD_CAN_DaysRemain"));
		
		// Read Values From Cancellation return Premium Table and write to Map :
		
			String ReturnP_TablePath = "//p[text()=' Cancellation Return Premium Summary ']//following-sibling::table[@id='table0']";
			WebElement RetunP_Table = driver.findElement(By.xpath(ReturnP_TablePath));
			
			List<WebElement> cols = RetunP_Table.findElements(By.tagName("th"));
						
			int RetunP_Rows = RetunP_Table.findElements(By.tagName("tr")).size();
			int RetunP_Cols = cols.size();
			
			List<String> sectionNames = new ArrayList<>();
			String sectionName = null;
			String sectionValue = null;
			String headerName = null;
		
			if(RetunP_Table.isDisplayed()){
				
				TestUtil.reportStatus("Cancellation Return Premium Table exist on cancel policy page . ", "Info", true);
			
				//For Each Cover Row
				for(int row = 1; row < RetunP_Rows ;row ++){
					
					WebElement sec_Name = driver.findElement(By.xpath(ReturnP_TablePath+"//tbody//tr["+row+"]//td["+1+"]"));
					sectionName = sec_Name.getText();
										
					switch(sectionName){
					
							case "Totals":
									Map<String,Double> ReturnP_Table_TotalVal = new HashMap<>();
									//For Each Cols
									for(int col = 2; col <= RetunP_Cols ;col ++){
								
										////p[text()=' Transaction Premium']//following-sibling::table[@id='table0']//thead//th[2]
										WebElement header_Name = driver.findElement(By.xpath(ReturnP_TablePath+"//thead//th["+col+"]"));
										headerName = header_Name.getText();
									
										if(!headerName.contains("Pen Comm %") && !headerName.contains("Broker Comm %") && !headerName.contains("Gross Comm %")
												&& !headerName.contains("Insurance Tax Rate") ){
											WebElement sec_Val = driver.findElement(By.xpath(ReturnP_TablePath+"//tbody//tr["+row+"]//td["+col+"]"));
											sectionValue = sec_Val.getText();
											sectionValue = sectionValue.replaceAll(",", "");
											ReturnP_Table_TotalVal.put(headerName, Double.parseDouble(sectionValue));
											
										}else{
											continue;
										}
										common_CCD.CAN_CCD_ReturnP_Values_Map.put(sectionName, ReturnP_Table_TotalVal);
									}
								
									break;
						
							default:
								Map<String,Double> ReturnP_Table_CoverVal = new HashMap<>();
								WebElement sec_Val = driver.findElement(By.xpath(ReturnP_TablePath));
								//For Each Cols
								for(int col = 2; col <= RetunP_Cols ;col ++){
							
									////p[text()=' Transaction Premium']//following-sibling::table[@id='table0']//thead//th[2]
									WebElement header_Name = driver.findElement(By.xpath(ReturnP_TablePath+"//thead//th["+col+"]"));
									headerName = header_Name.getText();
								
									if(col == 2){
										sec_Val = driver.findElement(By.xpath(ReturnP_TablePath+"//tbody//tr["+row+"]//td["+col+"]/input"));
										sectionValue = sec_Val.getAttribute("value");
									}else{
										sec_Val = driver.findElement(By.xpath(ReturnP_TablePath+"//tbody//tr["+row+"]//td["+col+"]"));
										sectionValue = sec_Val.getText();
									}
																	
									ReturnP_Table_CoverVal.put(headerName, Double.parseDouble(sectionValue));
								}
								
								common_CCD.CAN_CCD_ReturnP_Values_Map.put(sectionName, ReturnP_Table_CoverVal);
								break;				
					}
					
				}
			}
					
			
			// Calculation and Comparison :
				
				for(int row = 1; row < RetunP_Rows ;row ++){
					WebElement sec_Name = driver.findElement(By.xpath(ReturnP_TablePath+"//tbody//tr["+row+"]//td["+1+"]"));
					sectionNames.add(sec_Name.getText());
				}
				
				for(String s_Name : sectionNames){
					if(s_Name.equals("Totals"))
						Can_returnP_Error = Can_returnP_Error + Can_ReturnP_Total_Validation(sectionNames,common_CCD.CAN_CCD_ReturnP_Values_Map);
					else
						Can_returnP_Error = Can_returnP_Error + CanReturnPTable_CoverSection_Validation(policy_Duration,DaysRemain, s_Name, common_CCD.CAN_CCD_ReturnP_Values_Map);								
				}
						
			    if(Can_returnP_Error == 0){
			    	retVal = true;
			    }else{
			    	retVal = false;
			    }
				
			return retVal;		
	
	}catch(Throwable t){
		TestUtil.reportTestCaseFailed(testName, t);
		throw new ErrorInTestMethod(t.getMessage());
	}
	
}

public int Can_ReturnP_Total_Validation(List<String> sectionNames,Map<String,Map<String,Double>> Can_ReturnP_Values_Map){
	
	try{
	
			TestUtil.reportStatus("---------------Totals-----------------","Info",false);
			
			double exp_value = 0.0;
			for(String section : sectionNames){
				if(!section.contains("Total"))
					exp_value = exp_value + common_CCD.CAN_CCD_ReturnP_Values_Map.get(section).get("Net Net Premium");
			}
			
			String canRP_NetNetP_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get("Totals").get("Net Net Premium"));
			CommonFunction.compareValues(exp_value,Double.parseDouble(canRP_NetNetP_actual)," Net Net Premium");

			exp_value = 0.0;
			for(String section : sectionNames){
				if(!section.contains("Total"))
					exp_value = exp_value + common_CCD.CAN_CCD_ReturnP_Values_Map.get(section).get("Pen Comm");
			}
			String canRP_pc_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get("Totals").get("Pen Comm"));
			CommonFunction.compareValues(exp_value,Double.parseDouble(canRP_pc_actual)," Pen Commission");
	
			exp_value = 0.0;
			for(String section : sectionNames){
				if(!section.contains("Total"))
					exp_value = exp_value + common_CCD.CAN_CCD_ReturnP_Values_Map.get(section).get("Net Premium");
			}
			String canRP_netP_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get("Totals").get("Net Premium"));
			CommonFunction.compareValues(exp_value,Double.parseDouble(canRP_netP_actual),"Net Premium");
	
			exp_value = 0.0;
			for(String section : sectionNames){
				if(!section.contains("Total"))
					exp_value = exp_value + common_CCD.CAN_CCD_ReturnP_Values_Map.get(section).get("Broker Commission");
			}
			String canRP_bc_actual =  Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get("Totals").get("Broker Commission"));
			CommonFunction.compareValues(exp_value,Double.parseDouble(canRP_bc_actual),"Broker Commission");
	
			exp_value = 0.0;
			for(String section : sectionNames){
				if(!section.contains("Total"))
					exp_value = exp_value + common_CCD.CAN_CCD_ReturnP_Values_Map.get(section).get("Gross Premium");
			}
			String canRP_grossP_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get("Totals").get("Gross Premium"));
			CommonFunction.compareValues(exp_value,Double.parseDouble(canRP_grossP_actual)," Gross Premium");
		
			exp_value = 0.0;
			for(String section : sectionNames){
				if(!section.contains("Total"))
					exp_value = exp_value + common_CCD.CAN_CCD_ReturnP_Values_Map.get(section).get("Insurance Tax");
			}
			String canRP_InsuranceTax_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get("Totals").get("Insurance Tax"));
			CommonFunction.compareValues(exp_value,Double.parseDouble(canRP_InsuranceTax_actual),"Insurance Tax");
	
			exp_value = 0.0;
			for(String section : sectionNames){
				if(!section.contains("Total"))
					exp_value = exp_value + common_CCD.CAN_CCD_ReturnP_Values_Map.get(section).get("Total Premium");
			}
			String canRP_p_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get("Totals").get("Total Premium"));
			double premium_diff = exp_value - Double.parseDouble(canRP_p_actual);
	
			if(premium_diff<0.05 && premium_diff>-0.05){
				TestUtil.reportStatus("Total Premium [<b> "+exp_value+" </b>] matches with actual total premium [<b> "+canRP_p_actual+" </b>]as expected for Totals in Cancellation Return Premium table .", "Pass", false);
				return 0;
				
			}else{
				TestUtil.reportStatus("<p style='color:red'> Mismatch in Expected Premium [<b> "+exp_value+"</b>] and Actual Premium [<b> "+canRP_p_actual+"</b>] for Totals in Cancellation Return Premium table . </p>", "Fail", true);
				return 1;
			}
	
	}catch(Throwable t) {
	    String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
	    Assert.fail("Cancellation Return Premium Table -  total Section verification issue.  \n", t);
	    return 1;
	}
}

public int CanReturnPTable_CoverSection_Validation(int policy_Duration,int DaysRemain, String sectionNames, Map<String,Map<String,Double>> Can_ReturnP_Values_Map){

	Map<Object,Object> map_data = common.NB_excel_data_map;
	//String testName = (String)map_data.get("Automation Key");
	String code=null;
		
	code = sectionNames.replaceAll(" ", "");
		
	try{
			
			TestUtil.reportStatus("---------------"+sectionNames+"-----------------","Info",false);
			
			if(code.contains("LegalExpenses")){
				code = "LegalExpenses";
			}
			if(code.contains("BusinesssInterruption")){
				code = "BusinessInterruption";
			}
			double annual_NetNetP = Double.parseDouble((String)map_data.get("PS_"+code+"_NetNetPremium"));
			String canRP_NetNetP_expected = Double.toString((annual_NetNetP/365)*DaysRemain);
			String canRP_NetNetP_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionNames).get("Net Net Premium"));
			CommonFunction.compareValues(Double.parseDouble(canRP_NetNetP_expected),Double.parseDouble(canRP_NetNetP_actual)," Net Net Premium");
			
			//COB CAN Transaction Pen commission Calculation : 
			double canRP_pen_comm = (( Double.parseDouble(canRP_NetNetP_expected) / (1-((Double.parseDouble((String)map_data.get("PS_"+code+"_PenComm_rate")) + Double.parseDouble((String)map_data.get("PS_"+code+"_BrokerComm_rate")))/100)))*((Double.parseDouble((String)map_data.get("PS_"+code+"_PenComm_rate"))/100)));
			String canRP_pc_expected = common.roundedOff(Double.toString(canRP_pen_comm));
			String canRP_pc_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionNames).get("Pen Comm"));
			CommonFunction.compareValues(Double.parseDouble(canRP_pc_expected),Double.parseDouble(canRP_pc_actual)," Pen Commission");
			
			
			//COB CAN Transaction Net Premium verification : 
			double canRP_netP = Double.parseDouble(canRP_pc_expected) + Double.parseDouble(canRP_NetNetP_expected);
			String canRP_netP_expected = common.roundedOff(Double.toString(canRP_netP));
			String canRP_netP_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionNames).get("Net Premium"));
			CommonFunction.compareValues(Double.parseDouble(canRP_netP_expected),Double.parseDouble(canRP_netP_actual),"Net Premium");
			
			
			//COB CAN Transaction Broker commission Calculation : 
			double canRP_broker_comm = ((Double.parseDouble(canRP_NetNetP_expected) / (1-((Double.parseDouble((String)map_data.get("PS_"+code+"_PenComm_rate")) + Double.parseDouble((String)map_data.get("PS_"+code+"_BrokerComm_rate")))/100)))*((Double.parseDouble((String)map_data.get("PS_"+code+"_BrokerComm_rate"))/100)));
			String canRP_bc_expected = common.roundedOff(Double.toString(canRP_broker_comm));
			String canRP_bc_actual =  Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionNames).get("Broker Commission"));
			CommonFunction.compareValues(Double.parseDouble(canRP_bc_expected),Double.parseDouble(canRP_bc_actual),"Broker Commission");
			
			
			//COB CAN Transaction GrossPremium verification : 
			double canRP_grossP = Double.parseDouble(canRP_netP_expected) + Double.parseDouble(canRP_bc_expected);
			String canRP_grossP_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionNames).get("Gross Premium"));
			CommonFunction.compareValues(canRP_grossP,Double.parseDouble(canRP_grossP_actual),sectionNames+" Transaction Gross Premium");
			
			
			double canRP_InsuranceTax = (canRP_grossP * Double.parseDouble((String)map_data.get("PS_"+code+"_IPT")))/100.0;
			canRP_InsuranceTax = Double.parseDouble(common.roundedOff(Double.toString(canRP_InsuranceTax)));
			String canRP_InsuranceTax_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionNames).get("Insurance Tax"));
			CommonFunction.compareValues(canRP_InsuranceTax,Double.parseDouble(canRP_InsuranceTax_actual),"Insurance Tax");
						
			//COB CAN  Transaction Total Premium verification : 
			double canRP_Premium = canRP_grossP + canRP_InsuranceTax;
			String canRP_p_expected = common.roundedOff(Double.toString(canRP_Premium));
			
			String canRP_p_actual = Double.toString(common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionNames).get("Total Premium"));
			
			double premium_diff = Double.parseDouble(canRP_p_expected) - Double.parseDouble(canRP_p_actual);
			
			if(premium_diff<0.10 && premium_diff>-0.10){
				TestUtil.reportStatus("Total Premium [<b> "+canRP_p_expected+" </b>] matches with actual total premium [<b> "+canRP_p_actual+" </b>]as expected for "+sectionNames+" in Cancellation Return Premium table .", "Pass", false);
				return 0;
				
			}else{
				TestUtil.reportStatus("<p style='color:red'> Mismatch in Expected Premium [<b> "+canRP_p_expected+"</b>] and Actual Premium [<b> "+canRP_p_actual+"</b>] for "+sectionNames+" in Cancellation Return Premium table . </p>", "Fail", true);
				return 1;
			}
				
	}catch(Throwable t) {
	    String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
	    Assert.fail("Transaction Premium verification issue.  \n", t);
	    return 1;
	}
		
}

public boolean Cancel_PremiumSummary(Map<Object, Object> map_data) throws ErrorInTestMethod{
	boolean retVal = true;
	String testName = (String)common.CAN_excel_data_map.get("Automation Key");
	double s_PenCommRate = 0.00, s_BrokerCommRate = 0.00, s_GrossCommRate = 0.00, s_InsTRate = 0.00;
	
	try{
	
		customAssert.assertTrue(common.funcMenuSelection("Navigate", "Premium Summary"), "Unable to navigate to Premium Summary screen");
		
		String Cancellation_Date = "//p[text()=' Cancellation Details']//following-sibling::p";
		WebElement Cancel_Date = driver.findElement(By.xpath(Cancellation_Date));
		String C_Date = Cancel_Date.getText();
		
		// Verification of cancellation date :
		
				
		// Read Values From Cancellation Premium Summary Table :
		
			String CancelP_TablePath = "//p[text()=' Cancellation Details']//following-sibling::p//following-sibling::table[@id='table0']";
			WebElement CancelP_Table = driver.findElement(By.xpath(CancelP_TablePath));
			
			List<WebElement> cols = CancelP_Table.findElements(By.tagName("th"));
				
			int CancelP_Rows = CancelP_Table.findElements(By.tagName("tr")).size();
			int CancelP_Cols = cols.size();
			
			List<String> sectionNames = new ArrayList<>();
			String sectionName = null;
			String sectionValue = null;
			String headerName = null;
		
			if(CancelP_Table.isDisplayed()){
				
				TestUtil.reportStatus("Cancellation Premium Summary Table exist on premium summary page . ", "Info", true);
			
				//For Each Cover Row
				for(int row = 1; row < CancelP_Rows ;row ++){
					
					WebElement sec_Name = driver.findElement(By.xpath(CancelP_TablePath+"//tbody//tr["+row+"]//td["+1+"]"));
					sectionName = sec_Name.getText();
					
					double s_NetNetP = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+2+"]").replaceAll(",", "")));
					
					if(!sectionName.equals("Totals")){
						s_PenCommRate = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+3+"]").replaceAll(",", "")));
					}
													
					double s_PenCommAmt = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+4+"]").replaceAll(",", "")));
					
					double s_NetP = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+5+"]").replaceAll(",", "")));
					
					if(!sectionName.equals("Totals")){
						s_BrokerCommRate = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+6+"]").replaceAll(",", "")));						
					}
					double s_BrokerCommAmt = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+7+"]").replaceAll(",", "")));
					
					if(!sectionName.equals("Totals")){
						s_GrossCommRate = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+8+"]").replaceAll(",", "")));
					}					
					double s_GrossCommAmt = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+9+"]").replaceAll(",", "")));
					
					if(!sectionName.equals("Totals")){
						s_InsTRate = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+10+"]").replaceAll(",", "")));
					}					
					double s_InsTAmt = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+11+"]").replaceAll(",", "")));
					double s_TotalP = Math.abs(Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, CancelP_TablePath+"//tbody//tr["+row+"]//td["+12+"]").replaceAll(",", "")));
					
					common.compareValues(s_NetNetP, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Net Net Premium"), "Cancellation Net Net Premium");
					
					if(!sectionName.equals("Totals")){
						common.compareValues(s_PenCommRate,common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Pen Comm %"), "Cancellation Pen Comm Percentage");
					}				
					common.compareValues(s_PenCommAmt, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Pen Comm"), "Cancellation Pen commm Amount");
					common.compareValues(s_NetP, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Net Premium"), "Cancellation Net Premium");
					
					if(!sectionName.equals("Totals")){
						common.compareValues(s_BrokerCommRate, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Broker Comm %"), "Cancellation Broker Comm rate");
					}
					
					common.compareValues(s_BrokerCommAmt, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Broker Commission"), "Cancellation Broker commission amount");
					
					if(!sectionName.equals("Totals")){
						common.compareValues(s_GrossCommRate, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Gross Comm %"), "Cancellation Gross comm rate");
					}
					
					common.compareValues(s_GrossCommAmt, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Gross Premium"), "Cancellation Gross Premium");
					
					if(!sectionName.equals("Totals")){
						common.compareValues(s_InsTRate, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Insurance Tax Rate"), "Cancellation Ins tax rate");
					}
					
					common.compareValues(s_InsTAmt, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Insurance Tax"), "Cancellation Ins tax Premium");
					common.compareValues(s_TotalP, common_CCD.CAN_CCD_ReturnP_Values_Map.get(sectionName).get("Total Premium"), "Cancellation Total Premium");
										
				}	
			}
				
			return retVal;		
	
	}catch(Throwable t){
		TestUtil.reportTestCaseFailed(testName, t);
		throw new ErrorInTestMethod(t.getMessage());
	}
	
}

public boolean funcUpdateCoverDetails_MTA(Map<Object, Object> map_data){
	   
	try {
			customAssert.assertTrue(common.funcPageNavigation("Covers", ""),"Cover page is having issue(S)");
			String coverName = null;
			String c_locator = null;
			common.CoversDetails_data_list = new ArrayList<>();
			k.pressDownKeyonPage();
			String all_cover = ObjectMap.properties.getProperty(CommonFunction.product+"_CD_AllCovers");
			String[] split_all_covers = all_cover.split(",");
			for(String coverWithLocator : split_all_covers){
				String coverWithoutLocator = coverWithLocator.split("__")[0];
				try{
					//CoversDetails_data_list.add(coverWithoutLocator);
					coverName = coverWithLocator.split("__")[0];	
					c_locator = coverWithLocator.split("__")[1];
					k.waitTwoSeconds();
					if(c_locator.equals("md")){
						
						
						if (!driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"')]")).isSelected()){
							if(((String) map_data.get("CD_"+coverName)).equalsIgnoreCase("No"))
								continue;
							else
					 			customAssert.assertTrue(common_Zennor.selectCover(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
						}else{
							if(((String) map_data.get("CD_"+coverName)).equalsIgnoreCase("Yes")){
								if(common.currentRunningFlow.equalsIgnoreCase("MTA"))
									common.CoversDetails_data_list.add(coverName);
								continue;
							}
							else
								customAssert.assertTrue(common_Zennor.deSelectCovers(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
						}
					
					}else if(c_locator.equals("PEL")){
						
					}else{
						if (!driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"')]")).isSelected()){
							JavascriptExecutor j_exe = (JavascriptExecutor) driver;
							j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"')]")));
							
								if(((String) map_data.get("CD_"+coverName)).equalsIgnoreCase("No"))
									continue;
								else
						 			customAssert.assertTrue(common_Zennor.selectCover(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
													
							}else{
								if(((String) map_data.get("CD_"+coverName)).equalsIgnoreCase("Yes")){
									if(common.currentRunningFlow.equalsIgnoreCase("MTA"))
										common.CoversDetails_data_list.add(coverName);
									continue;
								}
								else
									customAssert.assertTrue(common_Zennor.deSelectCovers(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
							 }
					
					}	
					
				}catch(Throwable tt){
					System.out.println("Error while Updating Cover data for MTA - "+coverWithoutLocator);
					break;
				}
	 		}
 	 
	 	  return true;
		} catch (Exception e) {
			return false;
		}
	   
   }

/**
 * 
 * This method gives MF&D pages referrals texts."
 * 
 *
 */
public boolean func_Referrals_MaterialFactsDeclerationPage(){
	
	boolean retValue = true;
	
	Map<Object,Object> map_data=null;
	Properties CCD_referrals = OR.getORProperties();
	
	switch(common.currentRunningFlow){
		case "NB":
			map_data = common.NB_excel_data_map;
			break;
		case "MTA":
			map_data = common.MTA_excel_data_map;
			break;
		case "Renewal":
			map_data = common.Renewal_excel_data_map;
			break;
	}
	
	try{
		 customAssert.assertTrue(common.funcPageNavigation("Material Facts and Declarations", ""),"Material Facts and Declarations page is having issue(S)");
		 k.ImplicitWaitOff();
		 String mfd_q_value = null,mf_key="RM_MaterialFactsandDeclarations_";
		 
		 try{
		 //Have there been any previous claims  in the last 5 years?
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_previous_claims");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"previousClaims"));
		 }catch(Throwable t){
			 }
		 
		 try{
		 //Has any proposer, director or partner of the Trade or Business or its Subsidiary Companies ever, either personally or in any business capacity, had a proposal refused or declined or claim repudiated or ever had an insurance cancelled, renewal refused or had special terms imposed?
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_special_terms_imposed");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"specialTermsImposed"));
		 }catch(Throwable t){
		 }
		 
		 try{
		 //Has any proposer, director or partner of the Trade or Business or its Subsidiary Companies ever, either personally or in any business capacity had any convictions, criminal offences or prosecutions pending other than motor offences?
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_motor_offences");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"motorOffences"));
		 }catch(Throwable t){
		 }
		 
		 try{
		 //Has any proposer, director or partner of the Trade or Business or its Subsidiary Companies ever, either personally or in any business capacity been declared bankrupt or insolvent or been the subject of bankruptcy proceedings or receivership/ insolvency proceedings?
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_bankrupt_or_insolvent");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"bankruptOrInsolvent"));
		 }catch(Throwable t){
		 }
		 
		 try{
		 //Involved in another company within 6 months before receivership/insolvency?
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_6_months_receivership_insolvency");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"6monthsReceivershipInsolvency"));
		 }catch(Throwable t){
		 }
			
		 try{
		/* A director or partner in any business which has been the subject of an individual voluntary 
			 arrangement with creditors, voluntary liquidation, a winding up or administrative order, or 
			 administrative receivership proceedings?*/
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_administrative_receivership_proceedings");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"administrativeReceivershipProceedings"));
		 }catch(Throwable t){
		 }
		
		 try{
		 //Has any proposer, director or partner of the Trade or Business or its Subsidiary Companies ever, either personally or in any business capacity been the owner or director of, or partner in, any business, company or partnership had a county court judgement awarded against them?
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_awarded_against_them");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"awardedAgainstThem"));
		 }catch(Throwable t){
		 }
		
		 try{
		 //Do you use high pressure water jetting equipment?
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_jetting_equipment");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"jettingEquipment"));
		 }catch(Throwable t){
		 }
		
		 try{
		 //Does a senior person have overall reponsibility for health and safety
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_senior_person");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"seniorPerson"));
		 }catch(Throwable t){
		 }
		 
		 try{
		 //Have you appointed a competent person to advise you on health and safety matters?
		 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_health_safety_matters");
		 if(mfd_q_value.equalsIgnoreCase("Yes"))
			 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"healthSafetyMatters"));
		 }catch(Throwable t){
		 }
		 
		 try{
			
			 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_representative_company");
			 if(mfd_q_value.equalsIgnoreCase("Yes"))
				 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"representativeCompany"));
			 }catch(Throwable t){
			 }
		 
		 try{
			
			 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_Risk_Assessments");
			 if(mfd_q_value.equalsIgnoreCase("Yes"))
				 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"RiskAssessments"));
			 }catch(Throwable t){
			 }
		 
		 try{
			 
			 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_safe_working_procedures");
			 if(mfd_q_value.equalsIgnoreCase("Yes"))
				 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"safeWorkingProcedures"));
			 }catch(Throwable t){
			 }
		 
		 try{
			 
			 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_employees_undertake");
			 if(mfd_q_value.equalsIgnoreCase("Yes"))
				 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"employeesUndertake"));
			 }catch(Throwable t){
			 }
		 
		 try{
			
			 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_hazardous_to_health");
			 if(mfd_q_value.equalsIgnoreCase("Yes"))
				 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"hazardousToHealth"));
			 }catch(Throwable t){
			 }
		 
		 try{

			 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_Noise_Assessment");
			 if(mfd_q_value.equalsIgnoreCase("Yes"))
				 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"NoiseAssessment"));
			 }catch(Throwable t){
			 }
		 
		 try{
			 
			 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_third_party_ladders");
			 if(mfd_q_value.equalsIgnoreCase("Yes"))
				 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"thirdPartyLadders"));
			 }catch(Throwable t){
			 }
		 
		 try{
			 
			 mfd_q_value = k.GetDropDownSelectedValueIgnoreError("CCD_MFD_confined_space_work");
			 if(mfd_q_value.equalsIgnoreCase("Yes"))
				 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"confinedSpaceWork"));
			 }catch(Throwable t){
			 }
		 
		 
		 //Do you knowingly - Multi-select list
		 try{
			 List<WebElement> _dyknow_MF = driver.findElements(By.xpath("//*[text()='Do you knowingly']//following::ul[1]//li"));
			 String know_value =null,exp_val=null;
			 for(WebElement know_:_dyknow_MF){
				 try{
					 know_value = know_.getAttribute("title");
				 }catch(Throwable t){
					 know_value="None";
					 
					 }
				 exp_val = CCD_referrals.getProperty("CCD_MFD_DoYouKnow_title");
				 if(know_value.equalsIgnoreCase(exp_val)){
				 	 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"DoYouKnowingly"));
				 }
				 
			 }
		 }catch(Throwable t){
			 
		 }
		
		 
		 // Do you operate? - Multi Select list
		 try{
			 List<WebElement> _Operate = driver.findElements(By.xpath("//*[text()='Do you operate?']//following::ul[1]//li"));
			 String _operate_value =null,exp_val=null;
			 for(WebElement prd_elm:_Operate){
				 try{
					 _operate_value = prd_elm.getAttribute("title");
				 }catch(Throwable t){
					 _operate_value="None";
					 
					 }
				 
				 exp_val = CCD_referrals.getProperty("CCD_MFD_DoYouOperate_title");
				 if(_operate_value.equalsIgnoreCase(exp_val)){
				 	 common_HHAZ.referrals_list.add((String)map_data.get(mf_key+"DoYouOperate"));
				 }
				 
			 }
			
		 }catch(Throwable t){
			 
		 }
		  
		 
		 
		 return retValue;
		 
	}catch(Throwable t){
		k.ImplicitWaitOn();
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
        return false;
		}
	finally{
		 k.ImplicitWaitOn();
	 }
	
	}

//For EL- Additional Extensions
public boolean is_BI_referral_activity(String bi_Activity)
{
	
	OFC_Rater = OR.getORProperties();
	int f=0;
	String bi_act_list = OFC_Rater.getProperty("CCD_BI_Referral_Activities");
	String[] list_act = bi_act_list.split(":");
	
	for(String bi_ext : list_act){
		if(bi_ext.equalsIgnoreCase(bi_Activity))
		{
			f=1;
			return true;
		}
	}
	if(f==0){
		return false;
	}
	return false;
		
}

//For EL- Activities
public boolean is_EL_referral_activity(String el_Activity)
{
	
	OFC_Rater = OR.getORProperties();
	int f=0;
	String el_act_list = OFC_Rater.getProperty("CCD_EL_Referral_Activities");
	String[] list_act = el_act_list.split(":");
	
	for(String el_act : list_act){
		if(el_act.equalsIgnoreCase(el_Activity))
		{
			f=1;
			return true;
		}
	}
	if(f==0){
		return false;
	}
	return false;
		
}

//For PL- Activities
public boolean is_PL_referral_activity(String pl_Activity)
{
	
	OFC_Rater = OR.getORProperties();
	int f=0;
	String pl_act_list = OFC_Rater.getProperty("CCD_PL_Referral_Activities");
	String[] list_act = pl_act_list.split(":");
	
	for(String pl_act : list_act){
		if(pl_act.equalsIgnoreCase(pl_Activity))
		{
			f=1;
			return true;
		}
	}
	if(f==0){
		return false;
	}
	return false;
		
}

//For PL- Activities
public boolean is_Trade_referral_activity(String trade_Activity)
{
	
	OFC_Rater = OR.getORProperties();
	int f=0;
	String pl_act_list = OFC_Rater.getProperty("CCD_Trade_Referral_Activities");
	String[] list_act = pl_act_list.split(":");
	
	for(String pl_act : list_act){
		if(pl_act.equalsIgnoreCase(trade_Activity))
		{
			f=1;
			return true;
		}
	}
	if(f==0){
		return false;
	}
	return false;
		
}

//This function is to check " if Gross Premium generated is less than Minimum Premium
	//Applicable Covers --> MD,BI,money,EL , PL,GIT
public boolean func_Check_Section_Minimum_Gross_Premium(String section,double grossPremium){
	
			
			OFC_Rater = OR.getORProperties();
			double _Min_Premium_=0.0;
			Map<Object,Object> data_map = new HashMap<>();
			
			switch(common.currentRunningFlow){
			case "NB":
				
				data_map = common.NB_excel_data_map;
			break;
			case "CAN":
				
				data_map = common.CAN_excel_data_map;
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
			
			try{
				
				_Min_Premium_ = get_Section_Min_Premium_from_Properties(section);
				
				section = section.replace(" ", "");
				if(grossPremium < _Min_Premium_){ //Section Minimum Premium Rule
					common_HHAZ.referrals_list.add((String)data_map.get("RM_PremiumSummary_Section"+section));
				}
					
				return true;
			}catch(Throwable t){
				
				return false;
			}
			
			
}

public double get_Section_Min_Premium_from_Properties(String section){
	
	double r_value=0.0;
	DecimalFormat formatter = new DecimalFormat("###.##");

	try{
		OFC_Rater = OR.getORProperties();
		String _section = section.replaceAll(" ", "");
		if(section.equals("Policy"))
			_section = "Policy_Minimum_Premium_MP";
		else	
			_section = _section +"_MP";
		
		r_value = Double.parseDouble(OFC_Rater.getProperty(_section));
		r_value = Double.valueOf(formatter.format(r_value));
		
	}catch(Throwable t ){
		System.out.println("Error while getting Section Minimum Premium for Section > "+section+" < "+t.getMessage());
	}
	return r_value;
		
}

public double get_PPL_BookPremium(String PPL_LOI){
	
	double ppl_bp=0.0;
	int no_Of_Sites = common_OFC.c_AUTOMATED_CAR_WASH;
	String property_string = "";

	try{
		OFC_Rater = OR.getORProperties();
		if(no_Of_Sites!=0) {
			switch(PPL_LOI) {
				case "5000000":
					property_string = "OFC_£5M_with_Automated_Car_Wash_BookPremium";
				break;
				case "2000000":
					property_string = "OFC_£2M_with_Automated_Car_Wash_BookPremium";
				break;
				case "Other":
					return 0.0;
				default:
					break;
			}
		}else {
				return 0.0;
			}
		
		
		ppl_bp = Double.parseDouble(OFC_Rater.getProperty(property_string)) * no_Of_Sites;
		
	}catch(Throwable t ){
		System.out.println("Error while getting PPL Book Premium  -  "+t.getMessage());
	}
	return ppl_bp;
		
}

//End of CommonFunction_OFC.java

}
