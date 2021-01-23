package com.selenium.commonfiles.base;

import java.awt.AWTException;

import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.selenium.commonfiles.util.ErrorUtil;
import com.selenium.commonfiles.util.Keywords;
import com.selenium.commonfiles.util.ObjectMap;
import com.selenium.commonfiles.util.TestUtil;
import static com.selenium.commonfiles.util.TestUtil.*;

public class CommonFunction_GUS extends TestBase {
	public static String Environment = null;
	public static String loginUserName;
	public int pdf_count=0,err_count=0,final_err_pdf_count=0;
	
	public static int countOfCovers,countOfTableRows;
	public static int errorVal=0,counter = 0;
	public double totalGrossTax = 0.0,totalGrossTaxMTA = 0.0,totalGrossPremium = 0.0,totalGrossPremiumMTA=0.0,totalNetPremiumTax=0.0,totalNetPremiumTaxMTA=0.0;
	public static double Cover_TotalRate = 0.00, Cover_AdjustedRate = 0.00, Cover_Premium = 0.00, Cover_TotalPremium = 0.00;
	public static WebElement taxTable_tBody;
	public static WebElement objTable;
	public static WebElement taxTable_tHead;
	public static DecimalFormat f = new DecimalFormat("00.00");
	public static double adjustedPremium = 0.0,adjustedTotalPremium=0.0,adjustedTotalPremiumMTA=0.0,adjustedTotalTax=0.0,adjustedTotalTaxMTA=0.0,unAdjustedTotalTax=0.0,unAdjustedTotalTaxMTA=0.0;
	public static double PD_TotalRate = 0.0, PD_AdjustedRate = 0.0, PD_MD_Premium=0.0, PD_BI_Premium=0.0, PD_MD_TotalPremium = 0.00, PD_BI_TotalPremium = 0.00, finalMDPremium = 0.00, finalBIPremium= 0.00;
	public String currentRunningFlow ="NB";
	public static ArrayList<Object> inputarraylist = null;
	public static ArrayList<Object> inputarraylistMTA = null;
	
//	/public static ArrayList<Object> variableTaxAdjustmentIDs = null;
	public static Map<Object, Integer> variableTaxAdjustmentIDs = null;
	public static Map<Object, Integer> variableTaxAdjustmentIDsMTA = null;
	public static Map<Object, Double> grossTaxValues_Map = null;
	public static Map<Object, Map<Object, Object>> variableTaxAdjustmentVerificationMaps = null;
	public static Map<Object, Object> variableTaxAdjustmentDataMaps = null;
	public static Map<Object, Object> variableTaxAdjustmentDataMapsMTA = null;
	public static List<Object> headerNameStorage = null;
	public static List<Object> headerNameStorageMTA = null;
	//Properties properties = new Properties();
	
	public static int size;
	SimpleDateFormat df = new SimpleDateFormat();
	Date currentDate = new Date();
	public Map<Object, Object> NB_excel_data_map = null;
	public Map<Object, Object> MTA_excel_data_map = null;
	public Map<Object, Object> Rewind_excel_data_map = null;
	public Map<Object, Object> Renewal_excel_data_map = null;
	public Map<Object, Object> CAN_excel_data_map = null;
	public List<String> CoversDetails_data_list = null;
	public static Map<String, Double> Adjusted_Premium_map = null;
	
	public Map<String, List<Map<String, String>>> NB_Structure_of_InnerPagesMaps = null;
	public Map<String, List<Map<String, String>>> MTA_Structure_of_InnerPagesMaps = null;
	public Map<String, List<Map<String, String>>> Rewind_Structure_of_InnerPagesMaps = null;
	public Map<String, List<Map<String, String>>> Renewal_Structure_of_InnerPagesMaps = null;
	public Map<String, List<Map<String, String>>> CAN_Structure_of_InnerPagesMaps = null;
	DecimalFormat decim = new DecimalFormat("#.00");
	public Map<String,Integer> no_of_inner_data_sets = new HashMap<>();
	public Hashtable<String,String> GrosspremSmryData = new Hashtable<String,String>();

	public void beforeSuite() {

		System.out.println("** before Suite **");

	}

	public void afterSuite() {

		System.out.println("** after Suite **");
	}

	/**
	 * 
	 * This method calls product + event specific test methods.
	 * 
	 */
	public int funcSelectBusinessEvent(String productCode, String Scenario) {
		int flag = 0;
		try {
			/*
			 * //no paramater
			 * 
			 * @SuppressWarnings("rawtypes") Class noparams[] = {};
			 */

			// String parameter
			@SuppressWarnings("rawtypes")
			Class[] paramString = new Class[2];
			paramString[0] = String.class;
			paramString[1] = String.class;

			// int parameter
			@SuppressWarnings("rawtypes")
			Class[] paramInt = new Class[1];
			paramInt[0] = Integer.class;

			@SuppressWarnings("rawtypes")
			Class[] paramMap = new Class[1];
			paramMap[0] = Map.class;

			Object obj = null;
			@SuppressWarnings("rawtypes")
			Class testBaseClass = null;
			@SuppressWarnings("rawtypes")

			Class commonFunction_ProductClass = null;

			testBaseClass = Class.forName("com.selenium.commonfiles.base.TestBase");
			Field[] fields_In_TestBase = testBaseClass.getDeclaredFields();
			for (Field f : fields_In_TestBase) {
				String field_name = f.toString();
				String class_name = null;
				if (field_name.contains("_")) {
					if (field_name.contains("GT_ABCD")) {
						class_name = "_GT_ABCD";
					} else {
						class_name = field_name.substring(field_name.indexOf("_"), field_name.indexOf("_") + 4);
					}

					// System.out.println(class_name);
					if (class_name.contains(productCode) || productCode.startsWith("GT")) {
						commonFunction_ProductClass = Class
								.forName("com.selenium.commonfiles.base.CommonFunction" + class_name);
						obj = commonFunction_ProductClass.newInstance();
						flag = 1;
						switch (Scenario) {

						case "NB":
							try {
								@SuppressWarnings("unchecked")
								Method NB_method = commonFunction_ProductClass.getDeclaredMethod("NewBusinessFlow",
										paramString[0], paramString[1]);
								NB_method.invoke(obj, productCode, Scenario);
								// System.out.println((Integer)ret_Value);
								// flag=1;
							} catch (Throwable t) {
								return 1;
							}
							break;

						case "MTA":
							try {
								@SuppressWarnings("unchecked")
								Method MTA_method = commonFunction_ProductClass.getDeclaredMethod("MTAFlow",
										paramString[0], paramString[1]);
								MTA_method.invoke(obj, productCode, Scenario);
							} catch (Throwable t) {
								return 1;
							}
							break;
						case "Renewal":
							try {
							@SuppressWarnings("unchecked")
							Method Renewal_method = commonFunction_ProductClass.getDeclaredMethod("RenewalFlow",
									paramString[0], paramString[1]);
							Renewal_method.invoke(obj, productCode, Scenario);
						} catch (Throwable t) {
							return 1;
						}
						break;
						case "CAN":
								try {
								@SuppressWarnings("unchecked")
								Method CAN_method = commonFunction_ProductClass.getDeclaredMethod("CancellationFlow",
										paramString[0], paramString[1]);
								CAN_method.invoke(obj, productCode, Scenario);
							} catch (Throwable t) {
								return 1;
							}
							break;

						case "Rewind":
							try {
								@SuppressWarnings("unchecked")
								Method NB_method_for_Rewind = commonFunction_ProductClass
										.getDeclaredMethod("NewBusinessFlow", paramString[0], paramString[1]);
								NB_method_for_Rewind.invoke(obj, productCode, Scenario);
							} catch (Throwable t) {
								return 1;
							}
							try {
								@SuppressWarnings("unchecked")
								Method Rewind_method = commonFunction_ProductClass.getDeclaredMethod("RewindFlow",
										paramString[0], paramString[1]);
								Rewind_method.invoke(obj, productCode, Scenario);
							} catch (Throwable t) {
								return 1;
							}
							break;

						case "":
							break;

						default:
							System.out.println("****Business Event is not supported****");
							break;

						}

					}
				} else {
					continue;
				}
			} // for loop
		} catch (Throwable e) {

			// String methodName = new
			// Object(){}.getClass().getEnclosingMethod().getName();
			// k.reportErr("Failed in "+methodName+" function", e);
			TestUtil.reportStatus("Error in funcSelectBusinessEvent Method", "Info", false);
			return 1;
		}
		if (flag == 0) {
			TestUtil.reportStatus("CommonFunction_" + productCode + " is not present in TestBase .  ", "Info", false);
			return 1;
		}
		return 0;

	}

	/**
	 * Method returns product name.
	 * 
	 */
	public String getProductName(String current_TestDataID) {
		String[] split_by_Underscore = null;
		String productCode = null;

		if (current_TestDataID != null) {
			split_by_Underscore = current_TestDataID.split("_");
			productCode = split_by_Underscore[0];
		}

		return productCode;
	}

	/**
	 * Method returns scenario [NB/MTA...] name.
	 * 
	 */
	public String getScenario(String current_TestDataID) {

		String[] split_by_Underscore = null;
		String Scenario = null;

		if (current_TestDataID != null) {
			split_by_Underscore = current_TestDataID.split("_");
			Scenario = split_by_Underscore[1];
			// Scenario = token[0];
		}

		return Scenario;
	}

	/**
	 * Method returns business event of test data ID.
	 * 
	 */

	public String getBusinessEvent(String current_TestDataID) {

		String[] split_by_Underscore = null;
		String event = null;

		if (current_TestDataID != null) {
			split_by_Underscore = current_TestDataID.split("_");
			event = split_by_Underscore[1];
			// Scenario = token[0];
		}

		return event;
	}

	/**
	 * 
	 * This method return Legal Expense "Annual Carrier Premium (Excludes IPT)"
	 * value based on DAS-Rules
	 * 
	 *
	 */
	public String getLEAnnualCarrierPremium(String product, String LimitOfLiability, String turnover, String wages) {

		Properties DAS_Rules = new Properties();
		DAS_Rules = OR.getORProperties();
		String v_annual_carrier_p = "0.0";
		String wages_cent_rate = "0.0";
		String Option2_TO_cent_rate = "0.0";
		String das_product = null;
		String LOI = LimitOfLiability.replaceAll(",", "");
		LOI = "_" + Integer.toString(Integer.parseInt(LOI) / 1000) + "k_LOI";
		LimitOfLiability = LimitOfLiability.replaceAll(",", "");
		final int _1M = 1000000, _2_5M = 2500000, _5M = 5000000, _10M = 10000000, _12_5M = 12500000, _15M = 15000000,
				_17_5M = 17500000, _20M = 20000000, _25M = 25000000, _50M = 50000000;
		final int _100K = 100000, _250K = 250000, _500K = 500000;

		switch (product) {

		case "CTA":
			das_product = product + "_";
			break;
		case "CCF":
		case "CCG":
			das_product = "COM_COMBINED" + "_";
			break;
		case "POB":
		case "POC":
			das_product = "PO" + "_";
			break;
		default:
			System.out.println("***..Product is not in scope for LE Annual Carrier Premium Calulcation..**");
			return "";

		}
		if (Integer.parseInt(turnover) < 0) {
			System.out.println("Turnover should be Non-negative.It is >" + turnover + "<");
			return "";
		}
		if (Integer.parseInt(wages) < 0) {
			System.out.println("Wages should be Non-negative.It is >" + wages + "<");
			return "";
		}
		// DAS - Option 1
		if ((Integer.parseInt(turnover) < _25M && das_product.equals("CTA_"))
				|| (Integer.parseInt(turnover) < _25M && das_product.equals("COM_COMBINED_"))) {

			if (Integer.parseInt(turnover) >= 0 && Integer.parseInt(turnover) <= 4999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "0_4,999,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _5M && Integer.parseInt(turnover) <= 9999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "5m_9,999,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _10M && Integer.parseInt(turnover) <= 12499999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "10m_12,499,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _12_5M && Integer.parseInt(turnover) <= 14999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "12.5m_14,999,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _15M && Integer.parseInt(turnover) <= 17499999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "15m_17,499,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _17_5M && Integer.parseInt(turnover) <= 19999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "17.5m_19,999,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _20M && Integer.parseInt(turnover) <= 24999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "20m_24,999,999" + LOI);
			}

			return v_annual_carrier_p;

			// DAS - Option 2
		} else if ((Integer.parseInt(turnover) >= _25M && Integer.parseInt(turnover) <= 49999999)
				&& das_product.equals("COM_COMBINED_")) {

			if (Integer.parseInt(wages) >= 0 && Integer.parseInt(wages) <= 99999) {
				wages_cent_rate = DAS_Rules.getProperty(das_product + "0_99,999" + LOI);
			} else if (Integer.parseInt(wages) >= _100K && Integer.parseInt(wages) <= 249999) {
				wages_cent_rate = DAS_Rules.getProperty(das_product + "100k_249,999" + LOI);
			} else if (Integer.parseInt(wages) >= _250K && Integer.parseInt(wages) <= 499999) {
				wages_cent_rate = DAS_Rules.getProperty(das_product + "250k_499,999" + LOI);
			} else if (Integer.parseInt(wages) >= _500K && Integer.parseInt(wages) <= 999999) {
				wages_cent_rate = DAS_Rules.getProperty(das_product + "500k_999,999" + LOI);
			} else if (Integer.parseInt(wages) >= _1M && Integer.parseInt(wages) <= 2499999) {
				wages_cent_rate = DAS_Rules.getProperty(das_product + "1m_2,499,999" + LOI);
			} else if (Integer.parseInt(wages) >= _2_5M && Integer.parseInt(wages) <= 4999999) {
				wages_cent_rate = DAS_Rules.getProperty(das_product + "2.5m_4,999,999" + LOI);
			} else if (Integer.parseInt(wages) >= _5M && Integer.parseInt(wages) <= 9999999) {
				wages_cent_rate = DAS_Rules.getProperty(das_product + "5m_9,999,999" + LOI);
			}

			// Minimum Premium Rule -->
			// //COM_COMBINED_MIN_P_TO_RATE_25m_49,999,999
			Option2_TO_cent_rate = DAS_Rules.getProperty(das_product + "MIN_P_TO_RATE_25m_49,999,999");

			String premium_before_minimum_rule = Double
					.toString((Integer.parseInt(wages) * Double.parseDouble(wages_cent_rate)) / 100
							+ (Integer.parseInt(turnover) * Double.parseDouble(Option2_TO_cent_rate)) / 100);
			String f_premium_before_minimum_rule = Double
					.toString(k.FMT(Double.parseDouble(premium_before_minimum_rule)));

			// COM_COMBINED_MIN_P_250k_LOI
			String min_premium_250k = DAS_Rules.getProperty(das_product + "MIN_P_250k_LOI");
			String min_premium_500k = DAS_Rules.getProperty(das_product + "MIN_P_500k_LOI");

			if (LimitOfLiability.equals("250000")
					&& (Double.parseDouble(f_premium_before_minimum_rule) <= Double.parseDouble(min_premium_250k))) {
				v_annual_carrier_p = min_premium_250k;
				return v_annual_carrier_p;
			} else if (LimitOfLiability.equals("500000")
					&& (Double.parseDouble(f_premium_before_minimum_rule) <= Double.parseDouble(min_premium_250k))) {
				v_annual_carrier_p = min_premium_500k;
				return v_annual_carrier_p;
			} else {
				return v_annual_carrier_p;
			}

			// DAS - Option 3
		} else if ((Integer.parseInt(turnover) < _25M && das_product.equals("PO_"))) {

			if (Integer.parseInt(turnover) >= 0 && Integer.parseInt(turnover) <= 4999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "0_4,999,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _5M && Integer.parseInt(turnover) <= 9999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "5m_9,999,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _10M && Integer.parseInt(turnover) <= 12499999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "10m_12,499,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _12_5M && Integer.parseInt(turnover) <= 14999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "12.5m_14,999,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _15M && Integer.parseInt(turnover) <= 17499999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "15m_17,499,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _17_5M && Integer.parseInt(turnover) <= 19999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "17.5m_19,999,999" + LOI);
			} else if (Integer.parseInt(turnover) >= _20M && Integer.parseInt(turnover) <= 24999999) {
				v_annual_carrier_p = DAS_Rules.getProperty(das_product + "20m_24,999,999" + LOI);
			}

			return v_annual_carrier_p;
		}

		// DAS - Option 4 = Referral
		if (Integer.parseInt(turnover) >= _25M && das_product.equals("CTA_")
				|| Integer.parseInt(turnover) >= _25M && das_product.equals("PO_")) {
			return "Option 4 - Referral to DAS as turnover = " + turnover + " is Greater than /Equal to 25M";
		}

		if (Integer.parseInt(turnover) >= _50M && das_product.equals("COM_COMBINED_")) {
			return "Option 4 - Referral to DAS as turnover = " + turnover + " is Greater than /Equal to 50M";
		}

		return "";

	}

	// End of CommonFunction.java

	public boolean StingrayLogin(String business_unit) {
		Boolean retvalue = true;
		String testEnvironment = CONFIG.getProperty("testEnvironment");
		Environment = business_unit;
		try {
			k.openBrowser();
			k.Navigate(CONFIG.getProperty("App_URL_" + testEnvironment));
			loginUserName = CONFIG.getProperty("Username_" + testEnvironment + "_" + business_unit);
			customAssert.assertTrue(k.Input("username", CONFIG.getProperty("Username_" + testEnvironment + "_" + business_unit)),
					"Unable to enter value in UserName Field ");
			customAssert.assertTrue(k.Input("passcode", CONFIG.getProperty("Password_" + testEnvironment + "_" + business_unit)),
					"Unable to enter value in Password Field ");
			TestUtil.reportStatus("Login Page", "Info", true);

			customAssert.assertTrue(k.Click("login_btn"), "Unable to click on Login button");
			k.waitTwoSeconds();

			customAssert.assertTrue(k.checkElementPresent("newquote_link"), "Login Failed");
			TestUtil.reportStatus("Login successful.", "PASS", true);
			sysInfo.put("Execution Server", common.Server_name(testEnvironment));
			report.addSystemInfo(sysInfo);
			return retvalue;

		} catch (Throwable t) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in " + methodName + " function");
			k.reportErr("Failed in " + methodName + " function", t);
			Assert.fail("Unable to login to the webpage \n", t);
			return false;
		}

	}

	public String Server_name(String testEnvironment) {

		if (CONFIG.getProperty("App_URL_" + testEnvironment).contains("213.174.205.180")) {
			return CONFIG.getProperty("ServerName_V7_180");
		} else if (CONFIG.getProperty("App_URL_" + testEnvironment).contains("213.174.205.181")) {
			return CONFIG.getProperty("ServerName_BAU");
		} else if (CONFIG.getProperty("App_URL_" + testEnvironment).contains("213.174.205.179")) {
			return CONFIG.getProperty("ServerName_FIX");
		} else if (CONFIG.getProperty("App_URL_" + testEnvironment).contains("206.142.241.41")) {
			return CONFIG.getProperty("ServerName_V7");
		} else if (CONFIG.getProperty("App_URL_" + testEnvironment).contains("206.142.240.56")) {
			return CONFIG.getProperty("ServerName_56");
		} else {
			
			String url = CONFIG.getProperty("App_URL_"+testEnvironment).split("//")[1];
			String _url= url.split("/")[0];
			
			return _url;
		}

	}

	/*
	 * Insurance tax adjustment handling
	 * 
	 */
	
	@SuppressWarnings("static-access")
	public boolean insuranceTaxAdjustmentHandling(String code , String event){
	 	Map<Object, Object> map_to_update=common.NB_excel_data_map;
		try {
			switch(TestBase.businessEvent){
			case "NB":
				map_to_update = common.NB_excel_data_map;
			break;
			case "MTA":
				if(common.currentRunningFlow.equals("NB")){
					map_to_update = common.NB_excel_data_map;
					event = "NB";
					}
				else{
					map_to_update = common.MTA_excel_data_map;
					event = "MTA";	
				}
				
			break;
		}
			common.funcButtonSelection("Insurance Tax");
			customAssert.assertTrue(funcPageNavigation("Tax Adjustment", ""),"Unable to land on Tax adjustment screen.");
			String sectionName;
			
			customAssert.assertTrue(verifyCoverDetails(),"PremiumValue verification is causing issue(S).");
			customAssert.assertTrue(verifyGrossPremiumValues(),"PremiumValue verification is causing issue(S).");
			String policy_status_actual = k.getText("Policy_status_header");
			
			switch ((String)map_to_update.get("PS_InsuranceTaxButton")) {
			case "Yes":
				String value = null;
				List<WebElement> list = k.findElements("insuranceTaxExemptionRadioButton");
				for(int i=0;i<list.size();i++){
					
					boolean selectedStatus =  list.get(i).isSelected();
					if(selectedStatus){
						value = list.get(i).getAttribute("Value");	
					}
				}
				
				if(CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
					if(value.equalsIgnoreCase("No")){
						customAssert.assertTrue(k.SelectRadioBtn("insuranceTaxExemptionRadioButton", (String)map_to_update.get("PS_InsuranceTaxButton")));
						k.waitTwoSeconds();
						customAssert.assertTrue(k.AcceptPopup(), "Unable to accept alert box.");
					}
				}else{
					if(((String)map_to_update.get("PD_TaxExempt")).equalsIgnoreCase("No") && value.equalsIgnoreCase("No")){
						customAssert.assertTrue(k.SelectRadioBtn("insuranceTaxExemptionRadioButton", (String)map_to_update.get("PS_InsuranceTaxButton")));
						k.waitTwoSeconds();
						customAssert.assertTrue(k.AcceptPopup(), "Unable to accept alert box.");
					}
				}
				
				taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY"); 
				List<WebElement> listOfCovers = taxTable_tBody.findElements(By.tagName("tr"));
				countOfCovers = listOfCovers.size();
				
				for(int j=0;j<countOfCovers-1;j++){
					
					sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText().replaceAll(" ", "");
					if(sectionName.contains("ExcessofLoss")){
						sectionName = "PropertyExcessofLoss";
					}
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_"+sectionName+"_GT", common.roundedOff("00.00"), map_to_update);
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_"+sectionName+"_IPT", common.roundedOff("00.00"), map_to_update);
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_"+sectionName+"_NPIPT", common.roundedOff("00.00"), map_to_update);
					totalGrossPremium = totalGrossPremium + Double.parseDouble(common.roundedOff((String)map_to_update.get("PS_"+sectionName+"_GP")));
				}
				
				if(policy_status_actual.contains("Rewind")){
					common.totalGrossPremium = 0.0;
					common.totalGrossTax = 0.0;
					common.totalNetPremiumTax = 0.0;
					
					for(int j=0;j<countOfCovers-1;j++){
						
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						if(sectionName.contains("Excess of Loss")){
							sectionName = "Property Excess of Loss";
						}
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							/*String actGP =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText();
							String actGT =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText();
							*/
							String expGP = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
							String expGT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
							String expNPIT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_NPIPT");
							
							common.totalGrossPremium = common.totalGrossPremium + Double.parseDouble(expGP);
							common.totalGrossTax = common.totalGrossTax + Double.parseDouble(expGT);
							common.totalNetPremiumTax = common.totalNetPremiumTax + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
						countOfCovers = list3.size();
					}
				}
				TestUtil.reportStatus("<b> Tax adjustment operatios is completed. </b>", "Info", false);
				
				TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GP", common.roundedOff(Double.toString(totalGrossPremium)), map_to_update);
				TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GT", common.roundedOff("00.00"), map_to_update);
				TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_NPIPT", common.roundedOff("00.00"), map_to_update);
				TestUtil.reportStatus("<b>Policy Exempt from insurance tax radio button is selected as 'Yes' Hence skipped adjustment operation for all covers.</b>", "Info", false);
				break;
				
			case "No":
				
				TestUtil.reportStatus("<b> Tax adjustment operatios is started. </b>", "Info", false);
				customAssert.assertTrue(k.SelectRadioBtn("insuranceTaxExemptionRadioButton", (String)common.NB_excel_data_map.get("PS_InsuranceTaxButton")));
				//taxTable_tHead = k.getObject("inssuranceTaxMainTableHEAD");
				taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY"); 
				List<WebElement> list2 = taxTable_tBody.findElements(By.tagName("tr"));
				countOfCovers = list2.size();
				
				
				for(int j=0;j<countOfCovers-1;j++){
					
					taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
					sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
					if(sectionName.contains("Excess of Loss")){
						sectionName = "Property Excess of Loss";
					}
					
					String grossPremium =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText();
					//String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[4]")).getText();
					//String grossTax =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText();
					
					if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
						continue;
					}else{
						
						customAssert.assertTrue(adjustInsuranceTax(grossPremium,sectionName,code,event),"Adjust insurance Tax function is causing issue(S).");
						customAssert.assertTrue(verifyAdjustedTaxValues(sectionName,code,event),"Verify adjusted Tax function is having issue(S).");
					}
						
					taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
					List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
					countOfCovers = list3.size();
				}
				if(policy_status_actual.contains("Rewind")){
					common.totalGrossPremium = 0.0;
					common.totalGrossTax = 0.0;
					common.totalNetPremiumTax = 0.0;
					
					for(int j=0;j<countOfCovers-1;j++){
						
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						if(sectionName.contains("Excess of Loss")){
							sectionName = "Property Excess of Loss";
						}
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							/*String actGP =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText();
							String actGT =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText();
							*/
							String expGP = (String)common.NB_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
							String expGT = (String)common.NB_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
							String expNPIT = (String)common.NB_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_NPIPT");
							
							common.totalGrossPremium = common.totalGrossPremium + Double.parseDouble(expGP);
							common.totalGrossTax = common.totalGrossTax + Double.parseDouble(expGT);
							common.totalNetPremiumTax = common.totalNetPremiumTax + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
						countOfCovers = list3.size();
					}
				}
				if(policy_status_actual.contains("Endorsement Submitted")){
					common.totalGrossPremiumMTA = 0.0;
					common.totalGrossTaxMTA = 0.0;
					common.totalNetPremiumTaxMTA = 0.0;
					
					for(int j=0;j<countOfCovers-1;j++){
						
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						if(sectionName.contains("Excess of Loss")){
							sectionName = "Property Excess of Loss";
						}
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							/*String actGP =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText();
							String actGT =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText();
							*/
							String expGP = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
							String expGT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
							String expNPIT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_NPIPT");
							
							common.totalGrossPremiumMTA = common.totalGrossPremiumMTA + Double.parseDouble(expGP);
							common.totalGrossTaxMTA = common.totalGrossTaxMTA + Double.parseDouble(expGT);
							common.totalNetPremiumTaxMTA = common.totalNetPremiumTaxMTA + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
						countOfCovers = list3.size();
					}
				}
				
				TestUtil.reportStatus("<b> Tax adjustment operatios is completed. </b>", "Info", false);
				if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GP", common.roundedOff(Double.toString(totalGrossPremiumMTA)), map_to_update);
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GT", common.roundedOff(Double.toString(totalGrossTaxMTA)), map_to_update);
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_NPIPT", common.roundedOff(Double.toString(totalNetPremiumTaxMTA)), map_to_update);
					
				}else{
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GP", common.roundedOff(Double.toString(totalGrossPremium)), map_to_update);
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GT", common.roundedOff(Double.toString(totalGrossTax)), map_to_update);
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_NPIPT", common.roundedOff(Double.toString(totalNetPremiumTax)), map_to_update);
				}
				break;
			default:
				break;
			}
			
			common.funcPageNavigation("", "Save");
			k.Click("Tax_adj_BackBtn");
				
			return true;
		}catch (Throwable t) {
			k.ImplicitWaitOn();
			return false;
		}
	}
	
	//This function will verify values from system with data sheet data.
	@SuppressWarnings("static-access")
	public static boolean verifyCoverDetails(){
		
		try {
			
			int count = 0 , count_datasheet = 0;
			String coverName = null , coverName_datasheet = null;
			customAssert.assertTrue(k.Click("insuranceTaxAddAdjustmentButton"),"Unable to click on Add adustment button on Insurance Tax screen.");
			List<WebElement> names = driver.findElements(By.tagName("option"));
			List<String> coversNameList = new ArrayList<>();
			String policy_status_actual = k.getText("Policy_status_header");
			String coverName_withoutSpace =null;
			
			for(int i=0;i<names.size();i++){
				coverName = names.get(i).getText();
				
				if(coverName.contains("Liability")){
					coverName_withoutSpace = "Liability";
				}else if(coverName.contains("Frozen Food")){
					coverName_withoutSpace = "FrozenFood";
				}else if(coverName.contains("Licence")){
					coverName_withoutSpace = "LossofLicence";
				}else if(coverName.contains("Excess of Loss")){
					coverName_withoutSpace = "PropertyExcessofLoss";
				}else{
					coverName_withoutSpace = coverName.replaceAll(" ", "");
				}
				coversNameList.add(coverName_withoutSpace);
				if(common.currentRunningFlow.equalsIgnoreCase("NB")){
				
				String key = "CD_"+coverName_withoutSpace;
				if((policy_status_actual).contains("Rewind")){
					key = "CD_Add_"+coverName_withoutSpace;
				}
				
				if(common.NB_excel_data_map.get(key).toString().equalsIgnoreCase("Yes")){
					continue;
				}else{
					if(common.NB_excel_data_map.get("CD_"+coverName_withoutSpace.replaceAll(" ", "")).toString().equalsIgnoreCase("Yes")){
						
					}else{
						TestUtil.reportStatus("Cover Name <b>  ["+coverName+"]  </b> should not present in the dropdown list as This cover is not selected on Cover Details page.", "FAIL", false);
						count++;
					}
					
				}
			}else if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
				String key = "CD_AR"+coverName_withoutSpace;
								
				if(common.MTA_excel_data_map.get(key).toString().equalsIgnoreCase("Yes")){
					continue;
				}else{
					if(common.MTA_excel_data_map.get("CD_"+coverName_withoutSpace.replaceAll(" ", "")).toString().equalsIgnoreCase("Yes")){
						
					}else{
						TestUtil.reportStatus("Cover Name <b>  ["+coverName+"]  </b> should not present in the dropdown list as This cover is not selected on Cover Details page.", "FAIL", false);
						count++;
					}
					
				}
			}
				}
			for(int p=0;p<common.CoversDetails_data_list.size();p++){
				coverName_datasheet = common.CoversDetails_data_list.get(p);
				
				if(coversNameList.contains(coverName_datasheet)){
					continue;
				}else{
					TestUtil.reportStatus("Cover Name <b>  ["+coverName_datasheet+"]  </b> is selected as 'NO' in datasheet but still listed in the dropdown list.", "FAIL", false);
					count_datasheet++;
				}
			}
			
			WebElement adjustmentTax = k.getObject("insuranceTaxAddAdjustmentTable");
			customAssert.assertTrue(k.SelectBtnWebElement(adjustmentTax, "insuranceTaxAddAdjustmentSaveCancleButton", "Cancel"), "Unable to select Cancel button.");
			
			if(count==0 && count_datasheet==0){
				TestUtil.reportStatus("<b> Verified covers present in dropdown list of Adjustment Tax table. </b>", "Info", false);
			}
			
			return true;
		
		}catch (Throwable t) {
			
			return false;
		}
	}
	
	//This function will verify Gross premium values from system with datasheet data.
	@SuppressWarnings("static-access")
	public static boolean verifyGrossPremiumValues(){
		
		try {
			
			int count = 0;
			//taxTable_tHead = k.getObject("inssuranceTaxMainTableHEAD");
			taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY"); 
			List<WebElement> coverNameList = taxTable_tBody.findElements(By.tagName("tr"));
			String policy_status_actual = k.getText("Policy_status_header");
			// Below code will check the covers from the table.
			String coverName_withoutSpace = null;
			Map<Object, Object> map_to_update=common.NB_excel_data_map;
			switch(TestBase.businessEvent){
			case "NB":
				map_to_update = common.NB_excel_data_map;
			break;
			case "MTA":
				if(common.currentRunningFlow.equals("NB")){
					map_to_update = common.NB_excel_data_map;
				}
				else{
					map_to_update = common.MTA_excel_data_map;					
				}
				
			break;
		}
			for(int i=0;i<coverNameList.size()-1;i++){
				
				String sectionName = taxTable_tBody.findElement(By.xpath("tr["+(i+1)+"]/td[1]")).getText();
				
				if(!(sectionName.equalsIgnoreCase(""))){
					if(sectionName.contains("Excess of Loss")){
						sectionName = "Property Excess of Loss";
					}
					
					if(sectionName.contains("Liability")){
						coverName_withoutSpace = "Liability";
					}else if(sectionName.contains("Frozen")){
						coverName_withoutSpace = "FrozenFood";
					}else if(sectionName.contains("Licence")){
						coverName_withoutSpace = "LossofLicence";
					}else if(sectionName.contains("Excess of Loss")){
						coverName_withoutSpace = "PropertyExcessofLoss";
					}else{
						coverName_withoutSpace = sectionName.replaceAll(" ", "");
					}
					
					String key = "CD_"+coverName_withoutSpace;
					String expectedIPTRate = (String)common.NB_excel_data_map.get("PS_IPTRate");
					if((policy_status_actual).contains("Rewind")){
						key = "CD_Add_"+coverName_withoutSpace;
						if(sectionName.contains("Employers")){
							coverName_withoutSpace = "EmployersLiability";
						}
						if(sectionName.contains("Owners")){
							coverName_withoutSpace = "PropertyOwnersLiability";
						}
						if(sectionName.contains("Products")){
							coverName_withoutSpace = "ProductsLiability";
						}
						if(sectionName.contains("Public")){
							coverName_withoutSpace = "PublicLiability";
						}
						if(sectionName.contains("Licence")){
							coverName_withoutSpace = "LossOfLicence";
						}
						if(sectionName.contains("Frozen")){
							coverName_withoutSpace = "FrozenFoods";
						}
						expectedIPTRate = (String)common.NB_excel_data_map.get("PS_"+coverName_withoutSpace+"_IPT");
						
					}else if((policy_status_actual).contains("Endorsement Submitted")){
						key = "CD_AR"+coverName_withoutSpace;
						if(sectionName.contains("Employers")){
							coverName_withoutSpace = "EmployersLiability";
						}
						if(sectionName.contains("Owners")){
							coverName_withoutSpace = "PropertyOwnersLiability";
						}
						if(sectionName.contains("Products")){
							coverName_withoutSpace = "ProductsLiability";
						}
						if(sectionName.contains("Public")){
							coverName_withoutSpace = "PublicLiability";
						}
						if(sectionName.contains("Licence")){
							coverName_withoutSpace = "LossOfLicence";
						}
						if(sectionName.contains("Frozen")){
							coverName_withoutSpace = "FrozenFoods";
						}
						expectedIPTRate = (String)common.NB_excel_data_map.get("PS_"+coverName_withoutSpace+"_IPT");
						
					}else{
						if(sectionName.contains("Employers")){
							coverName_withoutSpace = "EmployersLiability";
						}
						if(sectionName.contains("Owners")){
							coverName_withoutSpace = "PropertyOwnersLiability";
						}
						if(sectionName.contains("Products")){
							coverName_withoutSpace = "ProductsLiability";
						}
						if(sectionName.contains("Public")){
							coverName_withoutSpace = "PublicLiability";
						}
						if(sectionName.contains("Licence")){
							coverName_withoutSpace = "LossOfLicence";
						}
						if(sectionName.contains("Frozen")){
							coverName_withoutSpace = "FrozenFoods";
						}
					}
					
					if(map_to_update.get(key).toString().equalsIgnoreCase("Yes")){
						
						
						String actualGrossPremium =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(i+1)+"]/td[2]")).getText());
						String actualIPTRate =  taxTable_tBody.findElement(By.xpath("tr["+(i+1)+"]/td[4]")).getText();
						String actualGrossTax =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(i+1)+"]/td[5]")).getText());
						
						String expectedGrossPremium = common.roundedOff((String)common.GrosspremSmryData.get("PS_"+coverName_withoutSpace.replaceAll(" ", "")+"_GP"));
						String expectedGrossTax = common.roundedOff(Double.toString(((Double.parseDouble(expectedGrossPremium) * Double.parseDouble(expectedIPTRate)) / 100.0)));
						
						if(verification(actualGrossPremium, expectedGrossPremium, sectionName, "Gross Premium") && 
						   /*verification(actualIPTRate, expectedIPTRate, sectionName, "IPT Rate") &&*/
						   verification(actualGrossTax, expectedGrossTax, sectionName, "Gross Tax")){
							
						}
						continue;
					}else{
						if(sectionName.contains("Liability")){
							coverName_withoutSpace = "Liability";
						}else if(sectionName.contains("Frozen")){
							coverName_withoutSpace = "FrozenFood";
						}else if(sectionName.contains("Licence")){
							coverName_withoutSpace = "LossofLicence";
						}else if(sectionName.contains("Excess of Loss")){
							coverName_withoutSpace = "PropertyExcessofLoss";
						}
						if(common.NB_excel_data_map.get("CD_"+coverName_withoutSpace).toString().equalsIgnoreCase("Yes")){
							
							if(sectionName.contains("Employers")){
								coverName_withoutSpace = "EmployersLiability";
							}
							if(sectionName.contains("Owners")){
								coverName_withoutSpace = "PropertyOwnersLiability";
							}
							if(sectionName.contains("Products")){
								coverName_withoutSpace = "ProductsLiability";
							}
							if(sectionName.contains("Public")){
								coverName_withoutSpace = "PublicLiability";
							}
							if(sectionName.contains("Licence")){
								coverName_withoutSpace = "LossOfLicence";
							}
							if(sectionName.contains("Frozen")){
								coverName_withoutSpace = "FrozenFoods";
							}
							
							String actualGrossPremium =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(i+1)+"]/td[2]")).getText());
							String actualIPTRate =  taxTable_tBody.findElement(By.xpath("tr["+(i+1)+"]/td[4]")).getText();
							String actualGrossTax =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(i+1)+"]/td[5]")).getText());
							
							String expectedGrossPremium = common.roundedOff((String)common.GrosspremSmryData.get("PS_"+coverName_withoutSpace.replaceAll(" ", "")+"_GP"));
							String expectedGrossTax = common.roundedOff(Double.toString(((Double.parseDouble(expectedGrossPremium) * Double.parseDouble(expectedIPTRate)) / 100.0)));
							
							if(verification(actualGrossPremium, expectedGrossPremium, sectionName, "Gross Premium") && 
							   /*verification(actualIPTRate, expectedIPTRate, sectionName, "IPT Rate") &&*/
							   verification(actualGrossTax, expectedGrossTax, sectionName, "Gross Tax")){
								
							}
							
						}else{
							TestUtil.reportStatus("Cover Name <b>  ["+sectionName+"]  </b> should not present in the table as This cover is not selected on Cover Details page.", "FAIL", false);
							count++;
						}
						
						
					}
				}
				
			}
			
			if(count==0){
				TestUtil.reportStatus("<b> Verified Gross Premium/Gross Tax/IPT Rate for each cover present under Tax Adjustment table. </b>", "Info", false);
			}
			
			return true;
		
		}catch (Throwable t) {
			
			return false;
		}
	}
	
	//This function will add 
		@SuppressWarnings("static-access")
		public static boolean adjustInsuranceTax(String grossPremium,String sectionName,String code,String event){
			try{
				variableTaxAdjustmentDataMaps = new LinkedHashMap<>();
				variableTaxAdjustmentVerificationMaps = new LinkedHashMap<>();
				variableTaxAdjustmentIDs = new HashMap<>();
				grossTaxValues_Map = new HashMap<>();
				headerNameStorage = new ArrayList<>();
				inputarraylist = new ArrayList<>();
				inputarraylistMTA = new ArrayList<>();
				Adjusted_Premium_map = new HashMap<>();
				
				adjustedPremium = Double.parseDouble(common.roundedOff((String)common.GrosspremSmryData.get("PS_"+sectionName.replaceAll(" ", "")+"_GP")));
				if(sectionName.contains("Property Excess of Loss")){
					sectionName = "Excess of Loss";
				}
				
				int count = common.no_of_inner_data_sets.get("Variable Tax Adjustment");
						
				int counter = 0;
				String coverName = sectionName;
				String policy_status_actual = k.getText("Policy_status_header");
				for(int l=0;l<count;l++){
					if(policy_status_actual.contains("Rewind")){
						if(sectionName.contains("Liability")){
							coverName = "Liability";
						}
						if(sectionName.contains("Licence")){
							coverName = "Loss of Licence";
						}
						if(sectionName.contains("Frozen")){
							coverName = "Frozen Food";
						}
						if(sectionName.contains("Excess")){
							coverName = "Property Excess of Loss";
						}
						if((((String)common.NB_excel_data_map.get("CD_Add_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes") &&
								((String)common.NB_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("No"))){
							
							if(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName").equalsIgnoreCase(sectionName) && 
									common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key").contains("Rewind")){
									Assert.assertTrue(k.Click("insuranceTaxAddAdjustmentButton"),"Unable to click on Add adustment button on Insurance Tax screen.");
									
									WebElement adjustmentTax = k.getObject("insuranceTaxAddAdjustmentTable");
									Select select = new Select(adjustmentTax.findElement(By.xpath("//*[contains(@name,'section')]")));
									String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
									String description = null;
									description = common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description");
									
									if(!description.contains("_")){
										description = common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description")+"_"+timeStamp;
										TestUtil.WriteDataToXl_innerSheet(code+"_"+event, "Variable Tax Adjustment", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), "VTA_Description", description, common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l));
									}
									select.selectByVisibleText(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
									customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentPremium", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium") ), "Unable to enter Premium on insurance Tax adjustment.");
									customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentTaxRate", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate") ), "Unable to enter Premium on insurance Tax adjustment.");
									customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentDescription", description ), "Unable to enter Premium on insurance Tax adjustment.");
									
									//System.out.println(variableTaxAdjustmentDataMaps);
									
									variableTaxAdjustmentDataMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Section Name", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
									variableTaxAdjustmentDataMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Premium", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
									variableTaxAdjustmentDataMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Tax Rate", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate"));
									variableTaxAdjustmentDataMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Description", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description"));
									
									headerNameStorage.add(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"));
									//variableTaxAdjustmentVerificationMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), variableTaxAdjustmentDataMaps);
									
									List<WebElement> listOfButtons = adjustmentTax.findElements(By.tagName("a"));
									//System.out.println("****************Total present button "+listOfButtons.size()+"********************");
									
									for(int k=0;k<listOfButtons.size();k++){
										String buttonName = listOfButtons.get(k).getText();
										if(buttonName.equalsIgnoreCase("Save")){
											listOfButtons.get(k).click();
											counter++;
											if(!validationOnAdjustedPremium(sectionName,l)){
												break;
											}
											adjustedPremium = adjustedPremium - Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
											break;
										}
									}
								}
							}
						}
					else if(policy_status_actual.contains("Endorsement Submitted")){
						if(sectionName.contains("Liability")){
							coverName = "Liability";
						}
						if(sectionName.contains("Licence")){
							coverName = "Loss of Licence";
						}
						if(sectionName.contains("Frozen")){
							coverName = "Frozen Food";
						}
						if(sectionName.contains("Excess")){
							coverName = "Property Excess of Loss";
						}
						if((((String)common.MTA_excel_data_map.get("CD_Add_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes") &&
								((String)common.NB_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("No"))){
							
							if(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName").equalsIgnoreCase(sectionName) && 
									common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key").contains("Endorsement Submitted")){
									Assert.assertTrue(k.Click("insuranceTaxAddAdjustmentButton"),"Unable to click on Add adustment button on Insurance Tax screen.");
									
									WebElement adjustmentTax = k.getObject("insuranceTaxAddAdjustmentTable");
									Select select = new Select(adjustmentTax.findElement(By.xpath("//*[contains(@name,'section')]")));
									String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
									String description = null;
									description = common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description");
									
									if(!description.contains("_")){
										description = common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description")+"_"+timeStamp;
										TestUtil.WriteDataToXl_innerSheet(code+"_"+event, "Variable Tax Adjustment", common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), "VTA_Description", description, common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l));
									}
									select.selectByVisibleText(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
									customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentPremium", common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium") ), "Unable to enter Premium on insurance Tax adjustment.");
									customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentTaxRate", common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate") ), "Unable to enter Premium on insurance Tax adjustment.");
									customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentDescription", description ), "Unable to enter Premium on insurance Tax adjustment.");
									
									//System.out.println(variableTaxAdjustmentDataMaps);
									
									variableTaxAdjustmentDataMaps.put(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Section Name", common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
									variableTaxAdjustmentDataMaps.put(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Premium", common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
									variableTaxAdjustmentDataMaps.put(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Tax Rate", common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate"));
									variableTaxAdjustmentDataMaps.put(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Description", common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description"));
									
									headerNameStorage.add(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"));
									//variableTaxAdjustmentVerificationMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), variableTaxAdjustmentDataMaps);
									
									List<WebElement> listOfButtons = adjustmentTax.findElements(By.tagName("a"));
									//System.out.println("****************Total present button "+listOfButtons.size()+"********************");
									
									for(int k=0;k<listOfButtons.size();k++){
										String buttonName = listOfButtons.get(k).getText();
										if(buttonName.equalsIgnoreCase("Save")){
											listOfButtons.get(k).click();
											counter++;
											if(!validationOnAdjustedPremium(sectionName,l)){
												break;
											}
											adjustedPremium = adjustedPremium - Double.parseDouble(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
											break;
										}
									}
								}
							}
						}

					else{
						if(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName").equalsIgnoreCase(sectionName)
								&& 
								!(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key").contains("Rewind"))){
							//if(adjustedPremium > 0.0 &&  adjustedPremium!=0.0 && adjustedPremium >= Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"))){
								Assert.assertTrue(k.Click("insuranceTaxAddAdjustmentButton"),"Unable to click on Add adustment button on Insurance Tax screen.");
								
								WebElement adjustmentTax = k.getObject("insuranceTaxAddAdjustmentTable");
								Select select = new Select(adjustmentTax.findElement(By.xpath("//*[contains(@name,'section')]")));
								String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
								String description = null;
								description = common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description");
								
								if(!description.contains("_")){
									description = common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description")+"_"+timeStamp;
									TestUtil.WriteDataToXl_innerSheet(code+"_"+event, "Variable Tax Adjustment", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), "VTA_Description", description, common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l));
								}
								select.selectByVisibleText(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentPremium", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium") ), "Unable to enter Premium on insurance Tax adjustment.");
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentTaxRate", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate") ), "Unable to enter Premium on insurance Tax adjustment.");
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentDescription", description ), "Unable to enter Premium on insurance Tax adjustment.");
								
								//System.out.println(variableTaxAdjustmentDataMaps);
								
								variableTaxAdjustmentDataMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Section Name", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
								variableTaxAdjustmentDataMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Premium", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
								variableTaxAdjustmentDataMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Tax Rate", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate"));
								variableTaxAdjustmentDataMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Description", common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description"));
								
								headerNameStorage.add(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"));
								//variableTaxAdjustmentVerificationMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), variableTaxAdjustmentDataMaps);
								
								List<WebElement> listOfButtons = adjustmentTax.findElements(By.tagName("a"));
								//System.out.println("****************Total present button "+listOfButtons.size()+"********************");
								
								for(int k=0;k<listOfButtons.size();k++){
									String buttonName = listOfButtons.get(k).getText();
									if(buttonName.equalsIgnoreCase("Save")){
										listOfButtons.get(k).click();
										counter++;
										if(!validationOnAdjustedPremium(sectionName,l)){
											break;
										}
										adjustedPremium = adjustedPremium - Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
										//Adjusted_Premium_map.put(sectionName+"_adjustedPremium", adjustedPremium);
										break;
									}
								}
							}
					}
				}
				variableTaxAdjustmentIDs.put(sectionName, counter);
				return true;
				
			}
			catch (Throwable t) {
				
				return false;
			}
		}
	
	public static boolean validationOnAdjustedPremium(String sectionName,int index){
		
		try {
			
			if(Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")) > 0.0 && 
			   Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")) <= adjustedPremium &&
			   Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")) <= 100.0 &&
			   Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")) >= 0.0
					){
				return true;
			}else{
				if(!(Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")) > 0.0)){
					TestUtil.reportStatus("<p style='color:black'> Gross Premium should be <b> > 0.0 </b> .Entered Gross Premium is : <b>[  "+common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")+"  ]</b>. Skipped Tax adjustment for Data ID is : [<b>  "+common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("Automation Key")+"  </b>] AND Cover Name is :  <b>[  "+sectionName+"  ]</b> </p>", "Info", false);
				}else if(!(Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")) <= adjustedPremium)){
					TestUtil.reportStatus("<p style='color:black'> Gross Premium limit is either completed or higher than Gross Premium. Available Gross Premium is : <b>[  "+adjustedPremium+"  ]</b> And Entered Gross Premium is : <b>[  "+common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")+"  ]</b>. Skipped Tax adjustment for Data ID is : [<b>  "+common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("Automation Key")+"  </b>] AND Cover Name is :  <b>[  "+sectionName+"  ]</b> </p>", "Info", false);
				}else if(!(Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")) <= 100.0)){
					TestUtil.reportStatus("<p style='color:black'> Tax rate should be <b> <= 100.0 </b>. Entered Tax rate is : <b>[  "+common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")+"  ]</b>. Skipped Tax adjustment for Data ID is : [<b>  "+common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("Automation Key")+"  </b>] AND Cover Name is :  <b>[  "+sectionName+"  ]</b> </p>", "Info", false);
				}else if(!(Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")) >= 0.0)){
					TestUtil.reportStatus("<p style='color:black'> Tax rate should be <b> >= 0.0 </b>.Entered Tax rate is : <b>[  "+common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")+"  ]</b> Skipped Tax adjustment for Data ID is : [<b>  "+common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("Automation Key")+"  </b>] AND Cover Name is :  <b>[  "+sectionName+"  ]</b> </p>", "Info", false);
				}
				
				@SuppressWarnings("static-access")
				WebElement adjustmentTax = k.getObject("insuranceTaxAddAdjustmentTable");
				customAssert.assertTrue(k.SelectBtnWebElement(adjustmentTax, "insuranceTaxAddAdjustmentSaveCancleButton", "Cancel"), "Unable to select Cancel button.");
				
				/*List<WebElement> listOfButtons = adjustmentTax.findElements(By.tagName("a"));
				for(int k=0;k<listOfButtons.size();k++){
					String buttonName = listOfButtons.get(k).getText();
					if(buttonName.equalsIgnoreCase("Cancel")){
						listOfButtons.get(k).click();
					}
				}*/
				return false;
			}
			
		} catch (Throwable t) {
			
			return false;
		} 

	}
	
	@SuppressWarnings({ "static-access", "unused" })
	public static boolean verifyAdjustedTaxValues(String sectionName,String code , String event){
		
		String coverName = sectionName;
		adjustedTotalTax = 0.0;
		adjustedTotalPremium = 0.0;
		unAdjustedTotalTax = 0.0;
		inputarraylist.clear();
		inputarraylistMTA.clear();
		
		try {
			taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
			List<WebElement> list2 = taxTable_tBody.findElements(By.tagName("tr"));
			countOfTableRows = list2.size();
			//System.out.println("Rows in table unde tBody : "+list2.size());
			
			Outer:
			for(int j=0;j<countOfCovers-1;j++){
				if(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
					//break Outer;
					break Outer;
				}
				if(sectionName.contains("Property Excess of Loss")){
					sectionName = "Excess of Loss";
				}
				String policy_status_actual = k.getText("Policy_status_header");
				if(policy_status_actual.contains("Rewind")){
					if(sectionName.contains("Liability")){
						coverName = "Liability";
					}
					if(sectionName.contains("Frozen")){
						coverName = "FrozenFood";
					}
					if(sectionName.contains("Licence")){
						coverName = "LossofLicence";
					}
					if((((String)common.NB_excel_data_map.get("CD_Add_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes") &&
							((String)common.NB_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("No"))){
						
						if(sectionName.equalsIgnoreCase(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText())){
							Inner :
							for(int m=0;m<=variableTaxAdjustmentIDs.get(sectionName)+1;m++){
								if(taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
									//break Outer;
									break Inner;
								}
								taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
								//Verify Adjusted Values
								errorVal = errorVal + verifyAdjustedTaxCalculation(sectionName,j,m);
							}
							
							//Verify Unadjusted Values
							errorVal = errorVal + verifyUnAdjustedTaxCalculation(sectionName,j);
							//Verify Gross Tax values
							errorVal = errorVal + verifyGrossTaxCalculation(sectionName,j,code,event);
							break;
						}
						
					}
				}else if(policy_status_actual.contains("Endorsement Submitted")){
					if(sectionName.contains("Liability")){
						coverName = "Liability";
					}
					if(sectionName.contains("Frozen")){
						coverName = "FrozenFood";
					}
					if(sectionName.contains("Licence")){
						coverName = "LossofLicence";
					}
					if((((String)common.MTA_excel_data_map.get("CD_Add_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes") &&
							((String)common.NB_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("No"))){
						
						if(sectionName.equalsIgnoreCase(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText())){
							Inner :
							for(int m=0;m<=variableTaxAdjustmentIDs.get(sectionName)+1;m++){
								if(taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
									//break Outer;
									break Inner;
								}
								taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
								//Verify Adjusted Values
								errorVal = errorVal + verifyAdjustedTaxCalculationMTA(sectionName,j,m);
							}
							
							//Verify Unadjusted Values
							errorVal = errorVal + verifyUnAdjustedTaxCalculationMTA(sectionName,j);
							//Verify Gross Tax values
							errorVal = errorVal + verifyGrossTaxCalculationMTA(sectionName,j,code,event);
							break;
						}
						
					}
				}else{
					if(sectionName.equalsIgnoreCase(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText())){
						Inner :
						for(int m=0;m<=variableTaxAdjustmentIDs.get(sectionName)+1;m++){
							if(taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
								//break Outer;
								break Inner;
							}
							taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
							//Verify Adjusted Values
							errorVal = errorVal + verifyAdjustedTaxCalculation(sectionName,j,m);
						}
						
						//Verify Unadjusted Values
						errorVal = errorVal + verifyUnAdjustedTaxCalculation(sectionName,j);
						//Verify Gross Tax values
						errorVal = errorVal + verifyGrossTaxCalculation(sectionName,j,code,event);
						break;
					}
				}
				
			}
		} catch (Throwable t) {
			
			return false;
		} 
		
		return true;
	}
	
public static int verifyAdjustedTaxCalculation(String sectionName,int j,int m){
		
		try{
			String coverName = taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText();
			String adjustedPremium =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[3]")).getText();
			String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[4]")).getText();
			String adjustedTax =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[6]")).getText();
			String description =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[7]")).getText();
			String adjustedBy =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[8]")).getText();
			
			int index = k.getText("BannerName").indexOf("\n");
			String adjustedByName = k.getText("BannerName").substring(0, index);
			//int count = 0;
			int noOfVariableTax = common.no_of_inner_data_sets.get("Variable Tax Adjustment");
			
			if((coverName==null || coverName.isEmpty() || coverName.equalsIgnoreCase("")) && !(description.equalsIgnoreCase("Unadjusted Premium"))){
				for(int p=0;p<variableTaxAdjustmentIDs.get(sectionName);p++){
					if(!inputarraylist.contains(p)){
						int count = 0;
						while(count < noOfVariableTax){
							//int columIndex = NB_Suite_TC_Xls.getColumnIndex("Variable Tax Adjustment",(String)headerNameStorage.get(p));
							//System.out.println("Index of "+(String)headerNameStorage.get(p)+"   is : : "+columIndex);
							
							if(description.equalsIgnoreCase((String) variableTaxAdjustmentDataMaps.get(headerNameStorage.get(p)+"_Description"))){
								if(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(count).get("Automation Key").equalsIgnoreCase((String)headerNameStorage.get(p))){
									
									double adjustedPremium_calc = Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(count).get("VTA_Premium"));
									double iptRate_calc = Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(count).get("VTA_TaxRate"));
									String adjustedTax_calc = common.roundedOff(Double.toString((adjustedPremium_calc * iptRate_calc ) / 100.0));
									
									if(verification(common.roundedOff(adjustedPremium), common.roundedOff((String) variableTaxAdjustmentDataMaps.get(headerNameStorage.get(p)+"_Premium")), sectionName, "Adjusted Premium") &&
									   /*verification(common.roundedOff(iptRate), common.roundedOff((String) variableTaxAdjustmentDataMaps.get(headerNameStorage.get(p)+"_Tax Rate")), sectionName, "IPT Rate") &&*/
									   verification(common.roundedOff(adjustedTax), adjustedTax_calc, sectionName, "Adjusted Tax") &&
									   verification(description, (String) variableTaxAdjustmentDataMaps.get(headerNameStorage.get(p)+"_Description"), sectionName, "Description") &&
									   verification(adjustedBy, adjustedByName, sectionName, "Adjusted By")){
									   
										   inputarraylist.add(p);
										   adjustedTotalTax = adjustedTotalTax + Double.parseDouble(adjustedTax_calc);
										   adjustedTotalPremium = adjustedTotalPremium + adjustedPremium_calc;
										   return 0;
									
									}else{
									   return 1;
									}
								}
								
							}
							count++;
						}
						
					}
				}
			}
			return 0;
		}catch (Throwable t) {
			
			return 1;
		} 
	}
public static int verifyAdjustedTaxCalculationMTA(String sectionName,int j,int m){
	
	try{
		String coverName = taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText();
		String adjustedPremium =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[3]")).getText();
		String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[4]")).getText();
		String adjustedTax =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[6]")).getText();
		String description =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[7]")).getText();
		String adjustedBy =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[8]")).getText();
		
		int index = k.getText("BannerName").indexOf("\n");
		String adjustedByName = k.getText("BannerName").substring(0, index);
		//int count = 0;
		int noOfVariableTax = common.no_of_inner_data_sets.get("Variable Tax Adjustment");
		
		if((coverName==null || coverName.isEmpty() || coverName.equalsIgnoreCase("")) && !(description.equalsIgnoreCase("Unadjusted Premium"))){
			for(int p=0;p<variableTaxAdjustmentIDs.get(sectionName);p++){
				if(!inputarraylistMTA.contains(p)){
					int count = 0;
					while(count < noOfVariableTax){
						//int columIndex = NB_Suite_TC_Xls.getColumnIndex("Variable Tax Adjustment",(String)headerNameStorage.get(p));
						//System.out.println("Index of "+(String)headerNameStorage.get(p)+"   is : : "+columIndex);
						
						if(description.equalsIgnoreCase((String) variableTaxAdjustmentDataMapsMTA.get(headerNameStorageMTA.get(p)+"_Description"))){
							if(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(count).get("Automation Key").equalsIgnoreCase((String)headerNameStorageMTA.get(p))){
								
								double adjustedPremium_calc = Double.parseDouble(common.MTA_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(count).get("VTA_Premium"));
								double iptRate_calc = Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(count).get("VTA_TaxRate"));
								String adjustedTax_calc = common.roundedOff(Double.toString((adjustedPremium_calc * iptRate_calc ) / 100.0));
								
								if(verification(common.roundedOff(adjustedPremium), common.roundedOff((String) variableTaxAdjustmentDataMapsMTA.get(headerNameStorageMTA.get(p)+"_Premium")), sectionName, "Adjusted Premium") &&
								   /*verification(common.roundedOff(iptRate), common.roundedOff((String) variableTaxAdjustmentDataMaps.get(headerNameStorage.get(p)+"_Tax Rate")), sectionName, "IPT Rate") &&*/
								   verification(common.roundedOff(adjustedTax), adjustedTax_calc, sectionName, "Adjusted Tax") &&
								   verification(description, (String) variableTaxAdjustmentDataMapsMTA.get(headerNameStorageMTA.get(p)+"_Description"), sectionName, "Description") &&
								   verification(adjustedBy, adjustedByName, sectionName, "Adjusted By")){
								   
									   inputarraylistMTA.add(p);
									   adjustedTotalTaxMTA = adjustedTotalTaxMTA + Double.parseDouble(adjustedTax_calc);
									   adjustedTotalPremiumMTA = adjustedTotalPremiumMTA + adjustedPremium_calc;
									   return 0;
								
								}else{
								   return 1;
								}
							}
							
						}
						count++;
					}
					
				}
			}
		}
		return 0;
	}catch (Throwable t) {
		
		return 1;
	} 
}

	public static int verifyUnAdjustedTaxCalculation(String sectionName,int j){
		
		try{
			
			for(int m=0;m<=variableTaxAdjustmentIDs.get(sectionName)+1;m++){
				
				if(taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
					//break Outer;
					break;
				}
				
				String adjustedPremium =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[3]")).getText();
				String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[4]")).getText();
				String adjustedTax =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[6]")).getText();
				String description =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[7]")).getText();
				String adjustedBy =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[8]")).getText();
				
				int index = k.getText("BannerName").indexOf("\n");
				String adjustedByName = k.getText("BannerName").substring(0, index);
				
				if(description.equalsIgnoreCase("Unadjusted Premium")){
					if(sectionName.contains("Excess of Loss")){
						sectionName = "Property Excess of Loss";
					}
					String unAdjustedPremium = common.roundedOff(Double.toString(Double.parseDouble((String)common.GrosspremSmryData.get("PS_"+sectionName.replaceAll(" ", "")+"_GP")) - adjustedTotalPremium));
					String iptRate_calc =  common.roundedOff(Double.toString(Double.parseDouble((String)common.NB_excel_data_map.get("PS_IPTRate"))));
					String adjustedTax_calc = common.roundedOff(Double.toString((Double.parseDouble(unAdjustedPremium) * Double.parseDouble(iptRate_calc) ) / 100.0));
					unAdjustedTotalTax = Double.parseDouble(adjustedTax_calc);
					
					if(verification(common.roundedOff(adjustedPremium), unAdjustedPremium, sectionName, "Un Adjusted Premium") &&
					   /*verification(common.roundedOff(iptRate), iptRate_calc, sectionName, "IPT Rate") &&*/
					   verification(common.roundedOff(adjustedTax), adjustedTax_calc, sectionName, "Un Adjusted Tax") &&
					   verification(description, "Unadjusted Premium", sectionName, "Description") &&
					   verification(adjustedByName, adjustedBy, sectionName, "Adjusted By")){
						
						return 0;
					
					}else{
						return 1;
					}
				}
			}
			return 0;
		}catch (Throwable t) {
			
			return 1;
		}
	}
public static int verifyUnAdjustedTaxCalculationMTA(String sectionName,int j){
		
		try{
			
			for(int m=0;m<=variableTaxAdjustmentIDsMTA.get(sectionName)+1;m++){
				
				if(taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
					//break Outer;
					break;
				}
				
				String adjustedPremium =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[3]")).getText();
				String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[4]")).getText();
				String adjustedTax =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[6]")).getText();
				String description =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[7]")).getText();
				String adjustedBy =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[8]")).getText();
				
				int index = k.getText("BannerName").indexOf("\n");
				String adjustedByName = k.getText("BannerName").substring(0, index);
				
				if(description.equalsIgnoreCase("Unadjusted Premium")){
					if(sectionName.contains("Excess of Loss")){
						sectionName = "Property Excess of Loss";
					}
					String unAdjustedPremium = common.roundedOff(Double.toString(Double.parseDouble((String)common.GrosspremSmryData.get("PS_"+sectionName.replaceAll(" ", "")+"_GP")) - adjustedTotalPremium));
					String iptRate_calc =  common.roundedOff(Double.toString(Double.parseDouble((String)common.MTA_excel_data_map.get("PS_IPTRate"))));
					String adjustedTax_calc = common.roundedOff(Double.toString((Double.parseDouble(unAdjustedPremium) * Double.parseDouble(iptRate_calc) ) / 100.0));
					unAdjustedTotalTaxMTA = Double.parseDouble(adjustedTax_calc);
					
					if(verification(common.roundedOff(adjustedPremium), unAdjustedPremium, sectionName, "Un Adjusted Premium") &&
					   /*verification(common.roundedOff(iptRate), iptRate_calc, sectionName, "IPT Rate") &&*/
					   verification(common.roundedOff(adjustedTax), adjustedTax_calc, sectionName, "Un Adjusted Tax") &&
					   verification(description, "Unadjusted Premium", sectionName, "Description") &&
					   verification(adjustedByName, adjustedBy, sectionName, "Adjusted By")){
						
						return 0;
					
					}else{
						return 1;
					}
				}
			}
			return 0;
		}catch (Throwable t) {
			
			return 1;
		}
	}
	public static int verifyGrossTaxCalculation(String sectionName,int j,String code , String event){
		
		
		String grossPremium =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText());
		String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[4]")).getText();
		String grossTax =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText());
		String colName = (String)common.NB_excel_data_map.get("Automation Key");
		if(sectionName.contains("Excess of Loss")){
			sectionName = "Property Excess of Loss";
		}
		String trimmedSectionName = sectionName.replaceAll(" ", "");
		
		String finalGrossPremium = common.roundedOff((String)common.GrosspremSmryData.get("PS_"+trimmedSectionName+"_GP"));
		String finalGrossTax,finalIPTRate,finalNetPremiumTax = null;
		if(inputarraylist.size()!=0){
			finalGrossTax = common.roundedOff(Double.toString(unAdjustedTotalTax +  adjustedTotalTax));
			finalIPTRate = Double.toString(((Double.parseDouble(finalGrossTax) /Double.parseDouble(finalGrossPremium)) * 100.0 ));
		}else{
			finalGrossTax = common.roundedOff(Double.toString((Double.parseDouble((String)common.GrosspremSmryData.get("PS_"+trimmedSectionName+"_GP")) * ((Double.parseDouble((String)common.NB_excel_data_map.get("PS_IPTRate")) / 100.0)))));
			finalIPTRate = (String)common.NB_excel_data_map.get("PS_IPTRate");
		}
		
		common.totalGrossTax = common.totalGrossTax + Double.parseDouble(finalGrossTax);
		common.totalGrossPremium = common.totalGrossPremium + Double.parseDouble(finalGrossPremium);
		
		
		TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",colName, "PS_"+sectionName.replaceAll(" ", "")+"_GT", finalGrossTax, common.NB_excel_data_map);
		TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",colName, "PS_"+sectionName.replaceAll(" ", "")+"_IPT", finalIPTRate, common.NB_excel_data_map);
		
		finalNetPremiumTax = common.roundedOff(Double.toString((Double.parseDouble((String)common.NB_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_NP")) * Double.parseDouble((String)common.NB_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_IPT"))) / 100.0 ));
		
		common.totalNetPremiumTax = common.totalNetPremiumTax + Double.parseDouble(finalNetPremiumTax);
		TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",colName, "PS_"+sectionName.replaceAll(" ", "")+"_NPIPT", finalNetPremiumTax, common.NB_excel_data_map);
		
		grossTaxValues_Map.put(sectionName, Double.parseDouble(finalGrossTax));
		
		if(verification(grossPremium,finalGrossPremium,sectionName,"Gross Premium") && 
				verification(grossTax,finalGrossTax,sectionName,"Gross Tax") 
				/*&& verification(iptRate,finalIPTRate,sectionName,"IPT Rate")*/){
			TestUtil.reportStatus("[<b>  "+sectionName+"  </b>]  cover --- Verified Gross Premium is :  <b>[ "+finalGrossPremium+" ]</b> , IPT Rate is :  <b>[ "+finalIPTRate+" ]</b> , Gross Tax is :  <b>[ "+finalGrossTax+" ]</b> ", "PASS", false);
			return 0;
		}else{
			return 1;
		}
	}
public static int verifyGrossTaxCalculationMTA(String sectionName,int j,String code , String event){
		
		
		String grossPremium =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText());
		String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[4]")).getText();
		String grossTax =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText());
		String colName = (String)common.MTA_excel_data_map.get("Automation Key");
		if(sectionName.contains("Excess of Loss")){
			sectionName = "Property Excess of Loss";
		}
		String trimmedSectionName = sectionName.replaceAll(" ", "");
		
		String finalGrossPremium = common.roundedOff((String)common.GrosspremSmryData.get("PS_"+trimmedSectionName+"_GP"));
		String finalGrossTax,finalIPTRate,finalNetPremiumTax = null;
		if(inputarraylistMTA.size()!=0){
			finalGrossTax = common.roundedOff(Double.toString(unAdjustedTotalTaxMTA +  adjustedTotalTaxMTA));
			finalIPTRate = Double.toString(((Double.parseDouble(finalGrossTax) /Double.parseDouble(finalGrossPremium)) * 100.0 ));
		}else{
			finalGrossTax = common.roundedOff(Double.toString((Double.parseDouble((String)common.GrosspremSmryData.get("PS_"+trimmedSectionName+"_GP")) * ((Double.parseDouble((String)common.MTA_excel_data_map.get("PS_IPTRate")) / 100.0)))));
			finalIPTRate = (String)common.MTA_excel_data_map.get("PS_IPTRate");
		}
		
		common.totalGrossTaxMTA = common.totalGrossTaxMTA + Double.parseDouble(finalGrossTax);
		common.totalGrossPremiumMTA = common.totalGrossPremiumMTA + Double.parseDouble(finalGrossPremium);
		
		
		TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",colName, "PS_"+sectionName.replaceAll(" ", "")+"_GT", finalGrossTax, common.MTA_excel_data_map);
		TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",colName, "PS_"+sectionName.replaceAll(" ", "")+"_IPT", finalIPTRate, common.MTA_excel_data_map);
		
		finalNetPremiumTax = common.roundedOff(Double.toString((Double.parseDouble((String)common.MTA_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_NP")) * Double.parseDouble((String)common.MTA_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_IPT"))) / 100.0 ));
		
		common.totalNetPremiumTaxMTA = common.totalNetPremiumTaxMTA + Double.parseDouble(finalNetPremiumTax);
		TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",colName, "PS_"+sectionName.replaceAll(" ", "")+"_NPIPT", finalNetPremiumTax, common.MTA_excel_data_map);
		
		grossTaxValues_Map.put(sectionName, Double.parseDouble(finalGrossTax));
		
		if(verification(grossPremium,finalGrossPremium,sectionName,"Gross Premium") && 
				verification(grossTax,finalGrossTax,sectionName,"Gross Tax") 
				/*&& verification(iptRate,finalIPTRate,sectionName,"IPT Rate")*/){
			TestUtil.reportStatus("[<b>  "+sectionName+"  </b>]  cover --- Verified Gross Premium is :  <b>[ "+finalGrossPremium+" ]</b> , IPT Rate is :  <b>[ "+finalIPTRate+" ]</b> , Gross Tax is :  <b>[ "+finalGrossTax+" ]</b> ", "PASS", false);
			return 0;
		}else{
			return 1;
		}
	}
	
	public static boolean verification(String actualValue,String expectedValue,String sectionName,String description){
		
		if(description.equalsIgnoreCase("Gross Tax")){
			double actVal = Double.parseDouble(actualValue);
			double expVal = Double.parseDouble(expectedValue);
			double diffrence = actVal - expVal;
			
			if(diffrence<=0.05 && diffrence>=-0.05){
				return true;
			}else{
				TestUtil.reportStatus("Mistmatch in "+description+" for [<b>  "+sectionName+"  </b>]  cover ---  Expected "+description+" is :  <b>[ "+expectedValue+" ]</b> and Actual "+description+" on Stingray application is : <b>[ "+actualValue+" ]</b>", "Fail", false);
				return false;
			}
		}else{
			if(actualValue.equalsIgnoreCase(expectedValue)){
				return true;
			}else{
				TestUtil.reportStatus("Mistmatch in "+description+" for [<b>  "+sectionName+"  </b>]  cover ---  Expected "+description+" is :  <b>[ "+expectedValue+" ]</b> and Actual "+description+" on Stingray application is : <b>[ "+actualValue+" ]</b>", "Fail", false);
				return false;
			}
		}
		
		
	}
	
	
	//Rounded off function.
       public String roundedOff(String number) {
           
    	   if(number.contains(".")){
    		   String replacedString = number.replace(".", ",");
               String[] stringArray = replacedString.split(",");
               if(stringArray[1].length()==2){
                     if(String.valueOf(stringArray[1]).contains("0")){
                            double pasredNumber = Double.parseDouble(number);
                            String pasredNumber_rf = f.format(pasredNumber);
                            return pasredNumber_rf;
                     }
                     else{
                    	 	if(stringArray[0].length()==1){
                    	 		double pasredNumber = Double.parseDouble(number);
                                String pasredNumber_rf = f.format(pasredNumber);
                                return pasredNumber_rf;
                    	 	}else{
                    	 		double pasredNumber = Double.parseDouble(number);
                                return Double.toString(pasredNumber);
                    	 	}
                     }
               }else if(stringArray[1].length()==3){
                     char c = stringArray[1].charAt(stringArray[1].length()-1);
                     if(c=='5'){
                            double formatedNumber = Double.parseDouble(number) + 0.005;
                     String roundedNumber = f.format(formatedNumber);
                     return roundedNumber;
                     }else{
                            String roundedNumber = f.format(Double.parseDouble(number));
                     return roundedNumber;
                     }
               }else{
                   String formatedNumber = f.format(Double.parseDouble(number));
                   return formatedNumber;
               }
    	   }
    	   else{
    		   String formatedNumber = f.format(Double.parseDouble(number));
               return formatedNumber;
    	   }
     }
       
   /**
   	 * This method validates post code pattern for given post code .
   	 * 
   	*/
       public boolean validatePostCode(String postCode){
    	   
    	   boolean result=true;
    	   try{
    		   //ME16 0CD
    		   result = Pattern.matches("[a-zA-Z]{2}[0-9]{2} [0-9]{1}[a-zA-Z]{2}", postCode);
    	   }catch(Throwable t){
    		   System.out.println("Error while validating Post Code"+t.getMessage());
    		   result=false;
    	   }   
    	   
    	   return result;
       }
	
	
     public boolean transactionSummary(String fileName,String testName,String event,String code){
   		Boolean retvalue = true;  
   		try{
   			customAssert.assertTrue(common.funcMenuSelection("Navigate", "Transaction Summary"), "Navigation problem to Transaction Summary page .");
   			
   			Assert.assertEquals(k.getText("Page_Header"),"Transaction Summary", "Not on Transaction Summary Page.");
   			String part1= "//*[@id='table0']/tbody";
   			String covername = null,exit = "";
   			int td=0;
   			WebElement table = driver.findElement(By.xpath(part1));
   			List<WebElement> list = table.findElements(By.tagName("tr"));
   			outer:
   			for(int i=1;i<list.size();i++){
   				String val = driver.findElement(By.xpath(part1+"/tr["+i+"]/td[1]")).getText();
   				double Total =0.00;
   				switch (val) {
   				case "New Business" : 
   					TestUtil.reportStatus("Verification Started on Transaction Summary page New Business . ", "PASS", false);
   					for(int j=i;!exit.equalsIgnoreCase("Total");j++){
   						String transactSumVal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[1]")).getText();
   						exit = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[2]")).getText();
   						if(transactSumVal.isEmpty()){
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[6]")).getText();
   							td=8;
   						}else{ 
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[9]")).getText();
   							td=11;
   						}
   						if(covername.equalsIgnoreCase("Cyber and Data Security")){
   							double CyberAndDataSecurity= calculateCyberTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.equalsIgnoreCase("Terrorism")){
   							double Terrorism = calculateTerrorismTS(code,testName,"New Bussiness",j,td);
   							Total = Total + Terrorism;
   						}
   						else if(covername.equalsIgnoreCase("Marine Cargo")){
   							double MarineCargo = calculateMarineCargoTS(code,testName,"New Bussiness",j,td);
   							Total = Total + MarineCargo;
   						}
   						else if(covername.equalsIgnoreCase("Directors and Officers")){
   							double DirectorsAndOffices = calculateDirectorsOfficersTS(code,testName,"New Bussiness",j,td);
   							Total = Total + DirectorsAndOffices;
   						}
   						else if(covername.equalsIgnoreCase("Legal Expenses")){
   							double LegalExpense = calculateLegalExpensesTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + LegalExpense;
   						}
   						else if(covername.equalsIgnoreCase("Employers Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateEmployersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Property Owners Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePropertyOwnersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Products Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateProductsLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Public Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePublicLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Solicitors PI") && (product.equalsIgnoreCase("SPI"))){
   							double CyberAndDataSecurity= calculate_SPI_TS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.equalsIgnoreCase("Solicitors excess layer") && (product.equalsIgnoreCase("SPI"))){
   							double CyberAndDataSecurity= calculate_SEL_TS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.isEmpty()){
   							double general = calculateOtherTS(testName,code,j,td,event,val);	
   							Total = Total + general;
   						}
   						if(exit.equalsIgnoreCase("Total")){
   						i=j;
   						String actualTotal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[4]")).getText();  
   						compareValues(Double.parseDouble(actualTotal), Double.parseDouble(common.roundedOff(Double.toString(Total))), "Transaction Summary Total");
   						customAssert.assertTrue(WriteDataToXl(event+"_NB", "Transaction Summary", (String)common.NB_excel_data_map.get("Automation Key"), "TS_TransactionSummaryTotal", actualTotal,common.NB_excel_data_map),"Error while writing Transaction Summary data to excel .");

   						break outer;
   						}
   						}
   					
   					break;
   					
   				case "Endorsement":
   					
   					TestUtil.reportStatus("Verification Started on Transaction Summary page after Endorsement (MTA) . ", "PASS", false);
   			   		
   					for(int j=i;!exit.equalsIgnoreCase("Total");j++){
   						String transactSumVal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[1]")).getText();
   						exit = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[2]")).getText();
   						if(transactSumVal.isEmpty()){
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[6]")).getText();
   							td=8;
   						}else{ 
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[9]")).getText();
   							td=11;
   						}
   						if(covername.equalsIgnoreCase("Cyber and Data Security")){
   							double CyberAndDataSecurity= calculateCyberTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.equalsIgnoreCase("Terrorism")){
   							double Terrorism = calculateTerrorismTS(code,testName,"New Bussiness",j,td);
   							Total = Total + Terrorism;
   						}
   						else if(covername.equalsIgnoreCase("Marine Cargo")){
   							double MarineCargo = calculateMarineCargoTS(code,testName,"New Bussiness",j,td);
   							Total = Total + MarineCargo;
   						}
   						else if(covername.equalsIgnoreCase("Directors and Officers")){
   							double DirectorsAndOffices = calculateDirectorsOfficersTS(code,testName,"New Bussiness",j,td);
   							Total = Total + DirectorsAndOffices;
   						}
   						else if(covername.equalsIgnoreCase("Legal Expenses")){
   							double LegalExpense = calculateLegalExpensesTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + LegalExpense;
   						}
   						else if(covername.equalsIgnoreCase("Employers Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateEmployersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Property Owners Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePropertyOwnersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Products Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateProductsLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Public Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePublicLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}else if(covername.equalsIgnoreCase("Solicitors PI") && (product.equalsIgnoreCase("SPI"))){
   							double SPI_PI= calculate_SPI_TS(code,testName,"Endorsement",j,td);	
   							Total = Total + SPI_PI;
   						}
   						else if(covername.equalsIgnoreCase("Solicitors excess layer") && (product.equalsIgnoreCase("SPI"))){
   							double SPI_SEL= calculate_SEL_TS(code,testName,"Endorsement",j,td);	
   							Total = Total + SPI_SEL;
   						}
   						else if(covername.isEmpty()){
   							double general = calculateOtherTS(testName,code,j,td,event,val);	
   							Total = Total + general;
   						}
   						if(exit.equalsIgnoreCase("Total")){
   						i=j;
   						String actualTotal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[4]")).getText();  
   						compareValues(Double.parseDouble(actualTotal), Double.parseDouble(common.roundedOff(Double.toString(Total))), "Transaction Summary Total");
   						customAssert.assertTrue(WriteDataToXl(event+"_MTA", "Transaction Summary", (String)common.MTA_excel_data_map.get("Automation Key"), "TS_TransactionSummaryTotal", actualTotal,common.MTA_excel_data_map),"Error while writing Transaction Summary data to excel for MTA .");

   						break outer;
   						}
   						}
   					break;
   					
   				case "Amended New Business" : 
   					
   					TestUtil.reportStatus("Verification Started on Transaction Summary page for Amended New Business . ", "PASS", false);
   	   			   
   					
   					for(int j=i;!exit.equalsIgnoreCase("Total");j++){
   						String transactSumVal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[1]")).getText();
   						exit = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[2]")).getText();
   						
   						if(transactSumVal.isEmpty()){
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[6]")).getText();
   							td=8;
   						}else{ 
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[9]")).getText();
   							td=11;
   						}
   						//System.out.println("Cover Name is :"+covername);
   						
   						//Cover specific Transaction Summary calculation.
   						if(covername.equalsIgnoreCase("Cyber and Data Security")){
   							double CyberAndDataSecurity= calculateCyberTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.equalsIgnoreCase("Terrorism")){
   							double Terrorism = calculateTerrorismTS(code,testName,"New Bussiness",j,td);
   							Total = Total + Terrorism;
   						}
   						else if(covername.equalsIgnoreCase("Marine Cargo")){
   							double MarineCargo = calculateMarineCargoTS(code,testName,"New Bussiness",j,td);
   							Total = Total + MarineCargo;
   						}
   						else if(covername.equalsIgnoreCase("Directors and Officers")){
   							double DirectorsAndOffices = calculateDirectorsOfficersTS(code,testName,"New Bussiness",j,td);
   							Total = Total + DirectorsAndOffices;
   						}
   						else if(covername.equalsIgnoreCase("Legal Expenses")){
   							double LegalExpense = calculateLegalExpensesTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + LegalExpense;
   						}
   						else if(covername.equalsIgnoreCase("Employers Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateEmployersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Property Owners Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePropertyOwnersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Products Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateProductsLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Public Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePublicLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Solicitors PI") && (product.equalsIgnoreCase("SPI"))){
   							double CyberAndDataSecurity= calculate_SPI_TS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.equalsIgnoreCase("Solicitors excess layer") && (product.equalsIgnoreCase("SPI"))){
   							double CyberAndDataSecurity= calculate_SEL_TS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.isEmpty()){
   							double general = calculateOtherTS(testName,code,j,td,event,val);	
   							Total = Total + general;
   						}
   						
   						if(exit.equalsIgnoreCase("Total")){
	   						i=j;
	   						String actualTotal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[4]")).getText();  
	   						compareValues(Double.parseDouble(actualTotal), Double.parseDouble(common.roundedOff(Double.toString(Total))), "Transaction Summary Total");
	   						customAssert.assertTrue(WriteDataToXl(event+"_NB", "Transaction Summary", (String)common.NB_excel_data_map.get("Automation Key"), "TS_TransactionSummaryTotal_Rewind", actualTotal,common.NB_excel_data_map),"Error while writing Transaction Summary data to excel .");

	   						break outer;
   						
   						}

   					}
   						
   					
   					break;
   					
   					//MTA Rewind
   				case "Amended Endorsement":
   					
   					TestUtil.reportStatus("Verification Started on Transaction Summary page after Rewind Endorsement (MTA) . ", "Info", false);
   			   		
   					for(int j=i;!exit.equalsIgnoreCase("Total");j++){
   						String transactSumVal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[1]")).getText();
   						exit = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[2]")).getText();
   						if(transactSumVal.isEmpty()){
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[6]")).getText();
   							td=8;
   						}else{ 
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[9]")).getText();
   							td=11;
   						}
   						if(covername.equalsIgnoreCase("Cyber and Data Security")){
   							double CyberAndDataSecurity= calculateCyberTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.equalsIgnoreCase("Terrorism")){
   							double Terrorism = calculateTerrorismTS(code,testName,"New Bussiness",j,td);
   							Total = Total + Terrorism;
   						}
   						else if(covername.equalsIgnoreCase("Marine Cargo")){
   							double MarineCargo = calculateMarineCargoTS(code,testName,"New Bussiness",j,td);
   							Total = Total + MarineCargo;
   						}
   						else if(covername.equalsIgnoreCase("Directors and Officers")){
   							double DirectorsAndOffices = calculateDirectorsOfficersTS(code,testName,"New Bussiness",j,td);
   							Total = Total + DirectorsAndOffices;
   						}
   						else if(covername.equalsIgnoreCase("Legal Expenses")){
   							double LegalExpense = calculateLegalExpensesTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + LegalExpense;
   						}
   						else if(covername.equalsIgnoreCase("Employers Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateEmployersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Property Owners Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePropertyOwnersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Products Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateProductsLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Public Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePublicLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}else if(covername.equalsIgnoreCase("Solicitors PI") && (product.equalsIgnoreCase("SPI"))){
   							double SPI_PI= calculate_SPI_TS(code,testName,"Endorsement",j,td);	
   							Total = Total + SPI_PI;
   						}
   						else if(covername.equalsIgnoreCase("Solicitors excess layer") && (product.equalsIgnoreCase("SPI"))){
   							double SPI_SEL= calculate_SEL_TS(code,testName,"Endorsement",j,td);	
   							Total = Total + SPI_SEL;
   						}
   						else if(covername.isEmpty()){
   							double general = calculateOtherTS(testName,code,j,td,event,val);	
   							Total = Total + general;
   						}
   						if(exit.equalsIgnoreCase("Total")){
   						i=j;
   						String actualTotal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[4]")).getText();  
   						compareValues(Double.parseDouble(actualTotal), Double.parseDouble(common.roundedOff(Double.toString(Total))), "Transaction Summary Total");
   						customAssert.assertTrue(WriteDataToXl(event+"_MTA", "Transaction Summary", (String)common.MTA_excel_data_map.get("Automation Key"), "TS_TransactionSummaryTotal", actualTotal,common.MTA_excel_data_map),"Error while writing Transaction Summary data to excel for MTA .");

   						break outer;
   						}
   						}
   					break;
   				case "Renewal" : 
   					
   					TestUtil.reportStatus("Verification Started on Transaction Summary page for Renewal. ", "Info", false);
   	   			   
   					
   					for(int j=i;!exit.equalsIgnoreCase("Total");j++){
   						String transactSumVal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[1]")).getText();
   						exit = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[2]")).getText();
   						
   						if(transactSumVal.isEmpty()){
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[6]")).getText();
   							td=8;
   						}else{ 
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[9]")).getText();
   							td=11;
   						}
   						if(covername.equalsIgnoreCase("Cyber and Data Security")){
   							double CyberAndDataSecurity= calculateCyberTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.equalsIgnoreCase("Terrorism")){
   							double Terrorism = calculateTerrorismTS(code,testName,"New Bussiness",j,td);
   							Total = Total + Terrorism;
   						}
   						else if(covername.equalsIgnoreCase("Marine Cargo")){
   							double MarineCargo = calculateMarineCargoTS(code,testName,"New Bussiness",j,td);
   							Total = Total + MarineCargo;
   						}
   						else if(covername.equalsIgnoreCase("Directors and Officers")){
   							double DirectorsAndOffices = calculateDirectorsOfficersTS(code,testName,"New Bussiness",j,td);
   							Total = Total + DirectorsAndOffices;
   						}
   						else if(covername.equalsIgnoreCase("Legal Expenses")){
   							double LegalExpense = calculateLegalExpensesTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + LegalExpense;
   						}
   						else if(covername.equalsIgnoreCase("Employers Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateEmployersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Property Owners Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePropertyOwnersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Products Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateProductsLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Public Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePublicLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Solicitors PI") && (product.equalsIgnoreCase("SPI"))){
   							double CyberAndDataSecurity= calculate_SPI_TS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.equalsIgnoreCase("Solicitors excess layer") && (product.equalsIgnoreCase("SPI"))){
   							double CyberAndDataSecurity= calculate_SEL_TS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.isEmpty()){
   							double general = calculateOtherTS(testName,code,j,td,event,val);	
   							Total = Total + general;
   						}
   						
   						if(exit.equalsIgnoreCase("Total")){
	   						i=j;
	   						String actualTotal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[4]")).getText();  
	   						compareValues(Double.parseDouble(actualTotal), Double.parseDouble(common.roundedOff(Double.toString(Total))), "Transaction Summary Total");
	   						customAssert.assertTrue(WriteDataToXl(event+"_Renewal", "Transaction Summary", (String)common.Renewal_excel_data_map.get("Automation Key"), "TS_TransactionSummaryTotal_Rewind", actualTotal,common.Renewal_excel_data_map),"Error while writing Transaction Summary data to excel .");

	   						break outer;
   						
   						}

   					}
   						
   					
   					break;	
   					
   				//Renewal Rewind
   				case "Amended Renewal" : 
   					
   					TestUtil.reportStatus("Verification Started on Transaction Summary page for Renewal. ", "Info", false);
   	   			   
   					for(int j=i;!exit.equalsIgnoreCase("Total");j++){
   						String transactSumVal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[1]")).getText();
   						exit = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[2]")).getText();
   						
   						if(transactSumVal.isEmpty()){
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[6]")).getText();
   							td=8;
   						}else{ 
   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[9]")).getText();
   							td=11;
   						}
   						if(covername.equalsIgnoreCase("Cyber and Data Security")){
   							double CyberAndDataSecurity= calculateCyberTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + CyberAndDataSecurity;
   						}
   						else if(covername.equalsIgnoreCase("Terrorism")){
   							double Terrorism = calculateTerrorismTS(code,testName,"New Bussiness",j,td);
   							Total = Total + Terrorism;
   						}
   						else if(covername.equalsIgnoreCase("Marine Cargo")){
   							double MarineCargo = calculateMarineCargoTS(code,testName,"New Bussiness",j,td);
   							Total = Total + MarineCargo;
   						}
   						else if(covername.equalsIgnoreCase("Directors and Officers")){
   							double DirectorsAndOffices = calculateDirectorsOfficersTS(code,testName,"New Bussiness",j,td);
   							Total = Total + DirectorsAndOffices;
   						}
   						else if(covername.equalsIgnoreCase("Legal Expenses")){
   							double LegalExpense = calculateLegalExpensesTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + LegalExpense;
   						}
   						else if(covername.equalsIgnoreCase("Employers Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateEmployersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Property Owners Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePropertyOwnersLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Products Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculateProductsLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}
   						else if(covername.equalsIgnoreCase("Public Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   							double EmployersLiability = calculatePublicLiabilityTS(code,testName,"New Bussiness",j,td);	
   							Total = Total + EmployersLiability;
   						}else if(covername.equalsIgnoreCase("Solicitors PI") && (product.equalsIgnoreCase("SPI"))){
   							double SPI_PI= calculate_SPI_TS(code,testName,"Endorsement",j,td);	
   							Total = Total + SPI_PI;
   						}
   						else if(covername.equalsIgnoreCase("Solicitors excess layer") && (product.equalsIgnoreCase("SPI"))){
   							double SPI_SEL= calculate_SEL_TS(code,testName,"Endorsement",j,td);	
   							Total = Total + SPI_SEL;
   						}
   						else if(covername.isEmpty()){
   							double general = calculateOtherTS(testName,code,j,td,event,val);	
   							Total = Total + general;
   						}
   						if(exit.equalsIgnoreCase("Total")){
	   						i=j;
	   						String actualTotal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[4]")).getText();  
	   						compareValues(Double.parseDouble(actualTotal), Double.parseDouble(common.roundedOff(Double.toString(Total))), "Transaction Summary Total");
	   						customAssert.assertTrue(WriteDataToXl(event+"_Renewal", "Transaction Summary", (String)common.Renewal_excel_data_map.get("Automation Key"), "TS_TransactionSummaryTotal", actualTotal,common.Renewal_excel_data_map),"Error while writing Transaction Summary data to excel for MTA .");

   						break outer;
   						}
   						}
   					break;
   					
   				
   					
   				case "Cancel" :
   					TestUtil.reportStatus("Verification Started on Transaction Summary page for Cancellation . ", "PASS", false);

   								
   	   					for(int j=i;!exit.equalsIgnoreCase("Total");j++){
   	   						String transactSumVal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[1]")).getText();
   	   						exit = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[2]")).getText();
   	   						if(transactSumVal.isEmpty()){
   	   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[6]")).getText();
   	   							td=8;
   	   						}else{ 
   	   							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[9]")).getText();
   	   							td=11;
   	   						}
   	   						if(covername.equalsIgnoreCase("Cyber and Data Security")){
   	   							double CyberAndDataSecurity= calculateCyberTS(code,testName,"New Bussiness",j,td);	
   	   							Total = Total + CyberAndDataSecurity;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Terrorism")){
   	   							double Terrorism = calculateTerrorismTS(code,testName,"New Bussiness",j,td);
   	   							Total = Total + Terrorism;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Marine Cargo")){
   	   							double MarineCargo = calculateMarineCargoTS(code,testName,"New Bussiness",j,td);
   	   							Total = Total + MarineCargo;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Directors and Officers")){
   	   							double DirectorsAndOffices = calculateDirectorsOfficersTS(code,testName,"New Bussiness",j,td);
   	   							Total = Total + DirectorsAndOffices;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Legal Expenses")){
   	   							double LegalExpense = calculateLegalExpensesTS(code,testName,"New Bussiness",j,td);	
   	   							Total = Total + LegalExpense;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Employers Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   	   							double EmployersLiability = calculateEmployersLiabilityTS(code,testName,"New Bussiness",j,td);	
   	   							Total = Total + EmployersLiability;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Property Owners Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   	   							double EmployersLiability = calculatePropertyOwnersLiabilityTS(code,testName,"New Bussiness",j,td);	
   	   							Total = Total + EmployersLiability;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Products Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   	   							double EmployersLiability = calculateProductsLiabilityTS(code,testName,"New Bussiness",j,td);	
   	   							Total = Total + EmployersLiability;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Public Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG"))){
   	   							double EmployersLiability = calculatePublicLiabilityTS(code,testName,"New Bussiness",j,td);	
   	   							Total = Total + EmployersLiability;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Solicitors PI") && (product.equalsIgnoreCase("SPI"))){
   	   							double CyberAndDataSecurity= calculate_SPI_TS(code,testName,"New Bussiness",j,td);	
   	   							Total = Total + CyberAndDataSecurity;
   	   						}
   	   						else if(covername.equalsIgnoreCase("Solicitors excess layer") && (product.equalsIgnoreCase("SPI"))){
   	   							double CyberAndDataSecurity= calculate_SEL_TS(code,testName,"New Bussiness",j,td);	
   	   							Total = Total + CyberAndDataSecurity;
   	   						}
   	   						else if(covername.isEmpty()){
   	   							double general = calculateOtherTS(testName,code,j,td,event,val);	
   	   							Total = Total + general;
   	   						}
   	   						if(exit.equalsIgnoreCase("Total")){
   	   						i=j;
   	   						String actualTotal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[4]")).getText();  
   	   						compareValues(Double.parseDouble(actualTotal), Double.parseDouble(common.roundedOff(Double.toString(Total))), "Transaction Summary Total");
   	   						//customAssert.assertTrue(WriteDataToXl(event+"_NB", "Transaction Summary", (String)common.NB_excel_data_map.get("Automation Key"), "TS_TransactionSummaryTotal", actualTotal,common.NB_excel_data_map),"Error while writing Transaction Summary data to excel .");

   	   						break outer;
   	   						}
   	   						}
   						break;
 			 			

   					
   				default :
   						//System.out.println("In Default");
   				}

   			}
   		}catch(Throwable t) {
   			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
   			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
   			Assert.fail("Failed in Transaction Summary \n", t);
   			return false;
   		}
   		
   		TestUtil.reportStatus("Verification Completed successful on Transaction Summary page . ", "Info", false);
   		
   		return retvalue;

   	}
     public double calculateCyberTS(String fileName,String testName,String code ,int j,int td){
    		try{
    			Map<Object,Object> data_map = null;
    				
    			switch (common.currentRunningFlow) {
    			case "NB":
 				data_map = common.NB_excel_data_map;
 				break;
 			case "MTA":
 				data_map = common.MTA_excel_data_map;
 				break;
 			case "Renewal":
 				data_map = common.Renewal_excel_data_map;
 				break;
 			case "Cancel":
 				data_map = common.CAN_excel_data_map;
 				break;
 			}
    			
    			String cyberPremium = (String)data_map.get("PS_CyberandDataSecurity_NP");
    			String cyberIPT = (String)data_map.get("PS_CyberandDataSecurity_GT");
    			String part1= "//*[@id='table0']/tbody";
    			String actualCyberPremium = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
    			String actualCyberIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
    			String actualCybaerDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
    			compareValues(Double.parseDouble(cyberPremium), Double.parseDouble(actualCyberPremium), "Cyber and data Security Amount");
    			compareValues(Double.parseDouble(cyberIPT), Double.parseDouble(actualCyberIPT), "Cyber and data Security IPT");
    			double directorsDue = Double.parseDouble(cyberPremium)+ Double.parseDouble(cyberIPT);
    			String directorsDueamt= common.roundedOff(Double.toString(directorsDue)) ;
    			compareValues(Double.parseDouble(directorsDueamt), Double.parseDouble(actualCybaerDue), "Cyber and data Security Due ");
    			return directorsDue;

    		}catch(Throwable t) {
    			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
    			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
    			Assert.fail("Failed in Calculate Cyber and Data Security ammount. \n", t);
    			return 0;
    		}
    	}
   	
	public double calculate_SPI_TS(String fileName,String testName,String code ,int j,int td){
   		try{
   			
   				String SI_Premium = null;
				String SI_IPT = null;;
				
			if(common.currentRunningFlow.equals("MTA")){
				
				try{
					SI_Premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors PI").get("Net Net Premium"));
					SI_IPT = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors PI").get("Insurance Tax"));
				}catch(NullPointerException npe){
					SI_Premium = "0.0";
					SI_IPT = "0.0";
				}
				
			}else if(common.currentRunningFlow.equals("NB")){
				
				if(Integer.parseInt((String)common.NB_excel_data_map.get("PS_Duration"))==365){
   			
					SI_Premium = (String)common.NB_excel_data_map.get("PS_PI_NetNetPremium");
					SI_IPT = (String)common.NB_excel_data_map.get("PS_PI_InsuranceTax");
   				
				}else{
					SI_Premium = Double.toString(common.transaction_Premium_Values.get("Solicitors PI").get("Net Net Premium"));
					SI_IPT = Double.toString(common.transaction_Premium_Values.get("Solicitors PI").get("Insurance Tax"));
				}
			}else if(common.currentRunningFlow.equals("CAN")){
				
				SI_Premium = Double.toString(common.Can_ReturnP_Values_Map.get("Solicitors PI").get("Net Net Premium"));
				SI_IPT = Double.toString(common.Can_ReturnP_Values_Map.get("Solicitors PI").get("Insurance Tax"));
			}else if(common.currentRunningFlow.equals("Renewal")){
				
				if(Integer.parseInt((String)common.Renewal_excel_data_map.get("PS_Duration"))==365){
   			
					SI_Premium = (String)common.Renewal_excel_data_map.get("PS_PI_NetNetPremium");
					SI_IPT = (String)common.Renewal_excel_data_map.get("PS_PI_InsuranceTax");
   				
				}else{
					SI_Premium = Double.toString(common.transaction_Premium_Values.get("Solicitors PI").get("Net Net Premium"));
					SI_IPT = Double.toString(common.transaction_Premium_Values.get("Solicitors PI").get("Insurance Tax"));
				}
			}
   			String part1= "//*[@id='table0']/tbody";
   			String actualSI_Premium = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
   			String actualSI_IPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
   			String actualSI_Due = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
   			compareValues(Double.parseDouble(SI_Premium), Math.abs(Double.parseDouble(actualSI_Premium)), "SI Premium Amount");
   			compareValues(Double.parseDouble(SI_IPT), Math.abs(Double.parseDouble(actualSI_IPT)), "SI IPT");
   			double directorsDue = Double.parseDouble(SI_Premium)+ Double.parseDouble(SI_IPT);
   			String directorsDueamt= common.roundedOff(Double.toString(directorsDue)) ;
   			compareValues(Double.parseDouble(directorsDueamt), Math.abs(Double.parseDouble(actualSI_Due)), "SI Due ");
   			return directorsDue;

   		}catch(Throwable t) {
   			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
   			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
   			Assert.fail("Failed in Calculate TS SI ammount. \n", t);
   			return 0;
   		}
   	}
	
	public double calculate_SEL_TS(String fileName,String testName,String code ,int j,int td){
   		try{
   			
   			String SEL_Premium = null;
   			String SEL_IPT = null;
   			
   			if(common.currentRunningFlow.equals("MTA")){
				try{
					SEL_Premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors excess layer").get("Net Net Premium"));
					SEL_IPT = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors excess layer").get("Insurance Tax"));
				}catch(NullPointerException npe){
					
					SEL_Premium = "0.0";
					SEL_IPT = "0.0";
				}
				
			}else if(common.currentRunningFlow.equals("NB")){
   			
				if(Integer.parseInt((String)common.NB_excel_data_map.get("PS_Duration"))==365){
   	   			
					SEL_Premium = (String)common.NB_excel_data_map.get("PS_SEL_NetNetPremium");
					SEL_IPT = (String)common.NB_excel_data_map.get("PS_SEL_InsuranceTax");
   				
				}else{
					SEL_Premium = Double.toString(common.transaction_Premium_Values.get("Solicitors excess layer").get("Net Net Premium"));
					SEL_IPT = Double.toString(common.transaction_Premium_Values.get("Solicitors excess layer").get("Insurance Tax"));
				}
			}else if(common.currentRunningFlow.equals("CAN")){
	   			
				SEL_Premium = Double.toString(common.Can_ReturnP_Values_Map.get("Solicitors excess layer").get("Net Net Premium"));
				SEL_IPT = Double.toString(common.Can_ReturnP_Values_Map.get("Solicitors excess layer").get("Insurance Tax"));
					
			}else if(common.currentRunningFlow.equals("Renewal")){
	   			
				if(Integer.parseInt((String)common.Renewal_excel_data_map.get("PS_Duration"))==365){
	   	   			
					SEL_Premium = (String)common.Renewal_excel_data_map.get("PS_SEL_NetNetPremium");
					SEL_IPT = (String)common.Renewal_excel_data_map.get("PS_SEL_InsuranceTax");
	   				
				}else{
					SEL_Premium = Double.toString(common.transaction_Premium_Values.get("Solicitors excess layer").get("Net Net Premium"));
					SEL_IPT = Double.toString(common.transaction_Premium_Values.get("Solicitors excess layer").get("Insurance Tax"));
				}
			}
   			String part1= "//*[@id='table0']/tbody";
   			String actualSEL_Premium = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
   			String actualSEL_IPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
   			String actualSEL_Due = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
   			compareValues(Double.parseDouble(SEL_Premium), Math.abs(Double.parseDouble(actualSEL_Premium)), "SEL Premium Amount");
   			compareValues(Double.parseDouble(SEL_IPT), Math.abs(Double.parseDouble(actualSEL_IPT)), "SEL IPT");
   			double directorsDue = Double.parseDouble(SEL_Premium)+ Double.parseDouble(SEL_IPT);
   			String directorsDueamt= common.roundedOff(Double.toString(directorsDue)) ;
   			compareValues(Double.parseDouble(directorsDueamt),Math.abs( Double.parseDouble(actualSEL_Due)), "SEL Due ");
   			return directorsDue;

   		}catch(Throwable t) {
   			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
   			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
   			Assert.fail("Failed in Calculate SEL ammount. \n", t);
   			return 0;
   		}
   	}
	
	public double calculateTerrorismTS(String fileName,String testName,String code ,int j,int td){
		try{
			
			Map<Object,Object> data_map = null;
				
   			switch (common.currentRunningFlow) {
   			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "Cancel":
 				data_map = common.CAN_excel_data_map;
 				break;
			}
			
			String terrorPremium = (String)data_map.get("PS_Terrorism_NP");
			String terrorIPT = (String)data_map.get("PS_Terrorism_GT");
			String part1= "//*[@id='table0']/tbody";		
			String recipient = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td-5)+"]")).getText();
			String actualTerrorPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualTerrorIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualerrorDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			String product = common.product;
			
			
			if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")) 
			{
				double[] values = TerrorismCalculation(recipient,terrorPremium, terrorIPT);
				compareValues(Double.parseDouble(actualTerrorPremium), values[0], "Terrorism RSA Amount ");
				compareValues(Double.parseDouble(actualTerrorIPT), values[1], "Terrorism RSA IPT ");
				double terrorRSADue=values[0] + values[1];
				compareValues(Double.parseDouble(actualerrorDue), terrorRSADue, "Terrorism RSA Due ");
	   			return  Double.parseDouble(common.roundedOff(Double.toString(terrorRSADue)));
			}
			else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited"))
			{
				double[] values = TerrorismCalculation(recipient,terrorPremium, terrorIPT);
				compareValues(Double.parseDouble(actualTerrorPremium), values[0], "Terrorism AJG Amount ");
				compareValues(Double.parseDouble(actualTerrorIPT), values[1], "Terrorism AJG IPT ");
				double terrorAJGDue=values[0] + values[1];
				compareValues(Double.parseDouble(actualerrorDue), Double.parseDouble(common.roundedOff(Double.toString(terrorAJGDue))), "Terrorism AJG Amount Due ");
				return  Double.parseDouble(common.roundedOff(Double.toString(terrorAJGDue)));
			}
			else if(recipient.equalsIgnoreCase("AIG Europe Ltd") )
			{
				double[] values = TerrorismCalculation(recipient,terrorPremium, terrorIPT);
				compareValues(Double.parseDouble(actualTerrorPremium), values[0], "Terrorism AIG Amount ");
				compareValues(Double.parseDouble(actualTerrorIPT), values[1], "Terrorism AIG IPT ");
				double terrorAIGDue=values[0] + values[1]; 
				compareValues(Double.parseDouble(actualerrorDue), Double.parseDouble(common.roundedOff(Double.toString(terrorAIGDue))), "Terrorism AIG Amount Due ");
				return  Double.parseDouble(common.roundedOff(Double.toString(terrorAIGDue)));
			}
			if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && 
					(product.equalsIgnoreCase("CCG") || product.equalsIgnoreCase("POC")))
			{

				double terrorRSAPremium =  Double.parseDouble(terrorPremium) * 0.6 * 0.9;
				double terrorRSAIPT =  Double.parseDouble(terrorIPT) * 0.6;
				String TerrorPremium = common.roundedOff(Double.toString(terrorRSAPremium));
				compareValues(Double.parseDouble(actualTerrorPremium), Double.parseDouble(TerrorPremium), "Terrorism RSA Amount");
				compareValues(Double.parseDouble(actualTerrorIPT), Double.parseDouble(common.roundedOff(Double.toString(terrorRSAIPT))), "Terrorism RSA IPT ");
				double terrorRSADue=terrorRSAPremium + terrorRSAIPT;
				compareValues(Double.parseDouble(actualerrorDue), Double.parseDouble(common.roundedOff(Double.toString(terrorRSADue))), "Terrorism RSA Amount Due");
				return  Double.parseDouble(common.roundedOff(Double.toString(terrorRSADue)));
			}
			else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") &&
					(product.equalsIgnoreCase("CCG") || product.equalsIgnoreCase("POC")))
			{
				double terrorAJGPremium = Double.parseDouble(terrorPremium) * 0.4 * 0.9;
				double terrorAJGIPT =  Double.parseDouble(terrorIPT) * 0.4;
				String TerrorPremium = common.roundedOff(Double.toString(terrorAJGPremium)); 
				compareValues(Double.parseDouble(actualTerrorPremium), Double.parseDouble(TerrorPremium), "Terrorism AJG Amount ");
				compareValues(Double.parseDouble(actualTerrorIPT), Double.parseDouble(common.roundedOff(Double.toString(terrorAJGIPT))), "Terrorism AJG IPT ");
				double terrorAJGDue=terrorAJGPremium + terrorAJGIPT;
				compareValues(Double.parseDouble(actualerrorDue), Double.parseDouble(common.roundedOff(Double.toString(terrorAJGDue))), "Terrorism AJG Due Amount ");
				return   Double.parseDouble(common.roundedOff(Double.toString(terrorAJGDue)));
			}
			return 0;
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Terrorisam ammount.  \n", t);
			return 0;
		}

	}
   	public double[] TerrorismCalculation(String split,String premiumAmt, String ipt){
		try{
			
			Map<Object,Object> data_map = null;
				
   			switch (common.currentRunningFlow) {
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
			
			String product = common.product;
			String leadCarrier = (String)data_map.get("PD_CarrierOverride"); 
			String codeCommission = (String)data_map.get("TER_CedeComm"); 
			String splitRate ="1.00";
			String Commission = "1.00";
			if(product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("POB")){
				if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
				if(leadCarrier.equalsIgnoreCase("Yes") && codeCommission.equalsIgnoreCase("Yes"))
				{
					splitRate = (String)data_map.get("TS_TerRSASplitLeadCarCC-A");
					Commission = (String)data_map.get("TS_TerRSACommLeadCarCC-A");
					
				}else if(leadCarrier.equalsIgnoreCase("Yes") && codeCommission.equalsIgnoreCase("No")){
					splitRate = (String)data_map.get("TS_TerRSASplitLeadCar-A");
					Commission = (String)data_map.get("TS_TerRSACommLeadCar-A");
					
				}else if(leadCarrier.equalsIgnoreCase("No") && codeCommission.equalsIgnoreCase("Yes")){
					splitRate = (String)data_map.get("TS_TerRSASplitCC-A");
					Commission = (String)data_map.get("TS_TerRSACommCC-A");
					
				}else if(leadCarrier.equalsIgnoreCase("No") && codeCommission.equalsIgnoreCase("No")){
					splitRate = (String)data_map.get("TS_TerRSASplit_A");
					Commission = (String)data_map.get("TS_TerRSAComm_A");
				}
				}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
					if(leadCarrier.equalsIgnoreCase("Yes") && codeCommission.equalsIgnoreCase("Yes"))
					{
						splitRate = (String)data_map.get("TS_TerAJGSplitLeadCarCC-A");
						Commission = (String)data_map.get("TS_TerRSACommLeadCarCC-A");
						
					}else if(leadCarrier.equalsIgnoreCase("Yes") && codeCommission.equalsIgnoreCase("No")){
						splitRate = (String)data_map.get("TS_TerAJGSplitLeadCar-A");
						Commission = (String)data_map.get("TS_TerRSACommLeadCar-A");
						
					}else if(leadCarrier.equalsIgnoreCase("No") && codeCommission.equalsIgnoreCase("Yes")){
						splitRate = (String)data_map.get("TS_TerAJGSplitCC-A");
						Commission = (String)data_map.get("TS_TerRSACommCC-A");
						
					}else if(leadCarrier.equalsIgnoreCase("No") && codeCommission.equalsIgnoreCase("No")){
						splitRate = (String)data_map.get("TS_TerAJGSplit_A");
						Commission = (String)data_map.get("TS_TerRSAComm_A");
						
					}
				}else if(split.equalsIgnoreCase("AIG Europe Ltd")){
					if(leadCarrier.equalsIgnoreCase("Yes") && codeCommission.equalsIgnoreCase("Yes"))
					{
						splitRate = (String)data_map.get("TS_TerAIGSplitLeadCarCC-A");
						Commission = (String)data_map.get("TS_TerRSACommLeadCarCC-A");
						
					}else if(leadCarrier.equalsIgnoreCase("Yes") && codeCommission.equalsIgnoreCase("No")){
						splitRate = (String)data_map.get("TS_TerAIGSplitLeadCar-A");
						Commission = (String)data_map.get("TS_TerRSACommLeadCar-A");
						
					}else if(leadCarrier.equalsIgnoreCase("No") && codeCommission.equalsIgnoreCase("Yes")){
						splitRate = (String)data_map.get("TS_TerAIGSplitCC-A");
						Commission = (String)data_map.get("TS_TerRSACommCC-A");
						
					}else if(leadCarrier.equalsIgnoreCase("No") && codeCommission.equalsIgnoreCase("No")){
						splitRate = (String)data_map.get("TS_TerAIGSplit_A");
						Commission = (String)data_map.get("TS_TerRSAComm_A");
						
					}
					
				}
				
			}else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
			//	String tradeCode = (String)common.NB_excel_data_map.get("PD_TCS_TradeCode");
				String tradeCodeCat = (String)data_map.get("PD_TCS_TradeCode_HazardGroup");
				//tradeCodeCat ="2";
				switch(tradeCodeCat){
				case "1" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG1RSA");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG1Novae");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}
					break;
				case "2" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG2RSA");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG2Novae");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}
					break;
				case "3" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG3RSA");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG3Novae");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}
					break;
				case "4" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG4RSA");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG4Novae");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}
					break;
				case "5" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG5RSA");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG5Novae");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}
					break;
				case "6" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG6RSA");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG6Novae");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}
					break;
				default : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHGDDefaultRSA");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHGDDefaultNovae");
						Commission = (String)data_map.get("TS_ConsortiumBTerComm");
					}
 break;
				}
				if(codeCommission.equalsIgnoreCase("Yes")){
				Commission = (String)data_map.get("TS_TerRSACommCC-A");
			}
			}

			double Premium =  Double.parseDouble(premiumAmt) * Double.parseDouble(splitRate) * Double.parseDouble(Commission);
			double IPT =  Double.parseDouble(ipt) * Double.parseDouble(splitRate);
			return new double[] {Premium, IPT};			
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Terrorisam ammount according to Split.  \n", t);
			return new double[] {0, 0};
		}
		}
   	public double[] OtherCalculation(String split,String premiumAmt, String ipt){
		try{
			
			Map<Object,Object> data_map = null;
			
   			switch (common.currentRunningFlow) {
   			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "CAN":
				data_map = common.NB_excel_data_map;
				break;
			}
			
			String product = common.product;
			String leadCarrier = (String)data_map.get("PD_CarrierOverride"); 
			String splitRate ="1.00";
			String Commission = "1.00";
			if(product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("POB")){
				if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
				if(leadCarrier.equalsIgnoreCase("Yes"))
				{
					splitRate = (String)data_map.get("TS_RSASplitLeadCarOther-A");
					Commission = (String)data_map.get("TS_RSASplitCC-A");
					
				}else if(leadCarrier.equalsIgnoreCase("No")){
					splitRate = (String)data_map.get("TS_RSASplitNoLeadCarOther-A");
					Commission = (String)data_map.get("TS_RSASplitCC-A");
				}
				}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
					if(leadCarrier.equalsIgnoreCase("Yes"))
					{
						splitRate = (String)data_map.get("TS_AJGSplitLeadCarOther-A");
						Commission = (String)data_map.get("TS_AJGSplitCC-A");
						
					}else if(leadCarrier.equalsIgnoreCase("No")){
						splitRate = (String)data_map.get("TS_AJGSplitNoLeadCarOther-A");
						Commission = (String)data_map.get("TS_AJGSplitCC-A");
						
					}
				}else if(split.equalsIgnoreCase("AIG Europe Ltd")){
					if(leadCarrier.equalsIgnoreCase("Yes") )
					{
						splitRate = (String)data_map.get("TS_AIGSplitLeadCarOther-A");
						Commission = (String)data_map.get("TS_AIGSplitCC-A");
						
					}else if(leadCarrier.equalsIgnoreCase("No")){
						splitRate = (String)data_map.get("TS_AIGSplitNoLeadCarOther-A");
						Commission = (String)data_map.get("TS_AIGSplit_A");	
					}		
				}
				
			}else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
				//String tradeCode = (String)common.NB_excel_data_map.get("PD_TCS_TradeCode");
				String tradeCodeCat = (String)data_map.get("PD_TCS_TradeCode_HazardGroup");
				switch(tradeCodeCat){
				case "1" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG1RSA");
						Commission = (String)data_map.get("TS_ConsortiumBRSA");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG1Novae");
						Commission = (String)data_map.get("TS_ConsortiumBNovae");
					}
					break;
				case "2" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG2RSA");
						Commission = (String)data_map.get("TS_ConsortiumBRSA");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG2Novae");
						Commission = (String)data_map.get("TS_ConsortiumBNovae");
					}break;
				case "3" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG3RSA");
						Commission = (String)data_map.get("TS_ConsortiumBRSA");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG3Novae");
						Commission = (String)data_map.get("TS_ConsortiumBNovae");
					}break;
				case "4" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG4RSA");
						Commission = (String)data_map.get("TS_ConsortiumBRSA");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG4Novae");
						Commission = (String)data_map.get("TS_ConsortiumBNovae");
					}break;
				case "5" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG5RSA");
						Commission = (String)data_map.get("TS_ConsortiumBRSA");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHG5Novae");
						Commission = (String)data_map.get("TS_ConsortiumBNovae");
					}break;
				case "6" : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHG6RSA");
						Commission = (String)data_map.get("TS_ConsortiumBRSA");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("'TS_PHG6Novae");
						Commission = (String)data_map.get("TS_ConsortiumBNovae");
					}break;
				default : 	
					if(split.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc")){
						splitRate = (String)data_map.get("TS_PHGDDefaultRSA");
						Commission = (String)data_map.get("TS_ConsortiumBRSA");
					}else if(split.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
						splitRate = (String)data_map.get("TS_PHGDDefaultNovae");
						Commission = (String)data_map.get("TS_ConsortiumBNovae");
					}break;
 
				}
			}
			
			double Premium =  Double.parseDouble(premiumAmt) * Double.parseDouble(splitRate) * Double.parseDouble(Commission);
			double IPT =  Double.parseDouble(ipt) * Double.parseDouble(splitRate);
			if(common.currentRunningFlow.equals("CAN")){
				return new double[] {-Premium,-IPT};
			}
			return new double[] {Premium, IPT};
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Terrorisam ammount according to Split.  \n", t);
			return new double[] {0, 0};
		}
		}
   	public double calculateDirectorsOfficersTS(String fileName,String testName,String code ,int j,int td){
		try{
			
			Map<Object,Object> data_map = null;
			
   			switch (common.currentRunningFlow) {
   			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "Cancel":
 				data_map = common.CAN_excel_data_map;
 				break;
			}
			
			String DirectorsGrossPremium = (String)data_map.get("PS_DirectorsandOfficers_GP");
			String directorsIPT = data_map.get("PS_DirectorsandOfficers_GT").toString();
			String part1= "//*[@id='table0']/tbody";
			String actualDirectorsPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualDirectorsIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualDirectorsDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			
			double DirectorsAmount =  Double.parseDouble(DirectorsGrossPremium) * 0.64;
			String DirectorsPremium = common.roundedOff(Double.toString(DirectorsAmount));
			compareValues(Double.parseDouble(DirectorsPremium), Double.parseDouble(actualDirectorsPremium), "Directors and Officers Amount ");
			compareValues(Double.parseDouble(actualDirectorsIPT), Double.parseDouble(directorsIPT), "Directors and Officers  IPT ");
			double directorsDue = Double.parseDouble(actualDirectorsPremium)+ Double.parseDouble(directorsIPT);
			compareValues(Double.parseDouble(actualDirectorsDue), Double.parseDouble(common.roundedOff(Double.toString(directorsDue))), "Directors and Officers Due Amount ");
			return Double.parseDouble(common.roundedOff(Double.toString(directorsDue)));	
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Ammout for Diectors and Officers. \n", t);
			return 0;
		}
	}
	
	public double calculateMarineCargoTS(String fileName,String testName,String code ,int j,int td){
		try{
			Map<Object,Object> data_map = null;
			
   			switch (common.currentRunningFlow) {
   			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "Cancel":
 				data_map = common.CAN_excel_data_map;
 				break;
			}
			
			String MarineCargoGrossPremium = (String)data_map.get("PS_MarineCargo_GP");
			String MarineCargoIPT = data_map.get("PS_MarineCargo_GT").toString();
			String part1= "//*[@id='table0']/tbody";
			String actualMarineCargoPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualMarineCargoIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualMarineCargoDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			double MarineCargoAmount =  Double.parseDouble(MarineCargoGrossPremium) * 0.64;
			String MarineCargoPremium = common.roundedOff(Double.toString(MarineCargoAmount));
			compareValues(Double.parseDouble(MarineCargoPremium), Double.parseDouble(actualMarineCargoPremium), "Marine Cargo Amount ");
			compareValues(Double.parseDouble(actualMarineCargoIPT), Double.parseDouble(MarineCargoIPT), "Marine Cargo  IPT ");
			double MarineCargoDue = Double.parseDouble(actualMarineCargoPremium)+ Double.parseDouble(MarineCargoIPT);
			compareValues(Double.parseDouble(actualMarineCargoDue), Double.parseDouble(common.roundedOff(Double.toString(MarineCargoDue))), "Marine Cargo Due Amount ");
			return Double.parseDouble(common.roundedOff(Double.toString(MarineCargoDue)));	
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Ammout for Marine Cargo. \n", t);
			return 0;
		}
	}
	public double calculateXOETS(String recipient,String account,int j, int td){
		try{
			double terrorAmount = 0.0;
			String PropertyExcessofLossGP = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_GP");
			String cedeCommission = (String)common.NB_excel_data_map.get("TER_CedeComm"); 	
			String PenGrossIPT = (String)common.NB_excel_data_map.get("PS_Total_GT"); 
			String TerrorisamNP = common.NB_excel_data_map.get("PS_Terrorism_NP").toString();
			String part1= "//*[@id='table0']/tbody";
			
			String actualXOEPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualXOEIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualXOEDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			
			if(((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{	
			if(cedeCommission.equalsIgnoreCase("No")){terrorAmount = (Double.parseDouble(TerrorisamNP) * 0.9);}
			else{terrorAmount = Double.parseDouble(TerrorisamNP);}
			}
			double GeneralAmount =  (Double.parseDouble(PropertyExcessofLossGP) * 0.64) + terrorAmount;
			compareValues(GeneralAmount, Double.parseDouble(actualXOEPremium), "XOE Amount ");
			compareValues(Double.parseDouble(PenGrossIPT), Double.parseDouble(actualXOEIPT), "XOE  IPT ");
			double XOEDue = GeneralAmount + Double.parseDouble(PenGrossIPT);
			compareValues(Double.parseDouble(actualXOEDue), Double.parseDouble(common.roundedOff(Double.toString(XOEDue))), "XOE Due Amount ");
			return Double.parseDouble(common.roundedOff(Double.toString(XOEDue)));	
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Ammout for Marine Cargo. \n", t);
			return 0;
		}
	}
	public double calculateXOETS_CAN(String recipient,String account,int j, int td){
		try{
			double terrorAmount = 0.0;
			String PropertyExcessofLossGP = (String)common.CAN_excel_data_map.get("PS_PropertyExcessofLoss_GP");
			String cedeCommission = (String)common.NB_excel_data_map.get("TER_CedeComm"); 	
			String PenGrossIPT = (String)common.CAN_excel_data_map.get("PS_Total_GT"); 
			String TerrorisamNP = common.CAN_excel_data_map.get("PS_Terrorism_NP").toString();
			String part1= "//*[@id='table0']/tbody";
			
			String actualXOEPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualXOEIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualXOEDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			
			if(((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{	
			if(cedeCommission.equalsIgnoreCase("No")){terrorAmount = (Double.parseDouble(TerrorisamNP) * 0.9);}
			else{terrorAmount = Double.parseDouble(TerrorisamNP);}
			}
			double GeneralAmount =  (Double.parseDouble(PropertyExcessofLossGP) * 0.64) + terrorAmount;
			compareValues(GeneralAmount, Double.parseDouble(actualXOEPremium), "XOE Amount ");
			compareValues(Double.parseDouble(PenGrossIPT), Double.parseDouble(actualXOEIPT), "XOE  IPT ");
			double XOEDue = GeneralAmount + Double.parseDouble(PenGrossIPT);
			compareValues(Double.parseDouble(actualXOEDue), Double.parseDouble(common.roundedOff(Double.toString(XOEDue))), "XOE Due Amount ");
			return Double.parseDouble(common.roundedOff(Double.toString(XOEDue)));	
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Ammout for Marine Cargo. \n", t);
			return 0;
		}
	}
	public double calculateXOETS_Rewind(String recipient,String account,int j, int td){
		try{
			double terrorAmount = 0.0;
			String PropertyExcessofLossGP = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_GP");
			String cedeCommission = (String)common.NB_excel_data_map.get("TER_CedeComm"); 	
			String PenGrossIPT = (String)common.NB_excel_data_map.get("PS_Total_GT"); 
			String TerrorisamNP = common.NB_excel_data_map.get("PS_Terrorism_NP").toString();
			String part1= "//*[@id='table0']/tbody";
			
			String actualXOEPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualXOEIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualXOEDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			
			/*if(((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{*/	
			if((String)common.NB_excel_data_map.get("CD_Terrorism")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_Terrorism")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_Terrorism")).equals("Yes")))
				{	
				
					if(cedeCommission.equalsIgnoreCase("No")){terrorAmount = (Double.parseDouble(TerrorisamNP) * 0.9);}
					else{terrorAmount = Double.parseDouble(TerrorisamNP);}
				}
			double GeneralAmount =  (Double.parseDouble(PropertyExcessofLossGP) * 0.64) + terrorAmount;
			compareValues(GeneralAmount, Double.parseDouble(actualXOEPremium), "XOE Amount ");
			compareValues(Double.parseDouble(PenGrossIPT), Double.parseDouble(actualXOEIPT), "XOE  IPT ");
			double XOEDue = GeneralAmount + Double.parseDouble(PenGrossIPT);
			compareValues(Double.parseDouble(actualXOEDue), Double.parseDouble(common.roundedOff(Double.toString(XOEDue))), "XOE Due Amount ");
			return Double.parseDouble(common.roundedOff(Double.toString(XOEDue)));	
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Ammout for Marine Cargo. \n", t);
			return 0;
		}
	}
	
	public double calculateLegalExpensesTS(String fileName,String testName,String code ,int j,int td){
		try{
			
			Map<Object,Object> data_map = null;
			
   			switch (common.currentRunningFlow) {
   			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "Cancel":
 				data_map = common.CAN_excel_data_map;
 				break;
			}
			
			String LegalExpenseCarrier = (String)data_map.get("LE_AnnualCarrierPremium");
			String LegalExpenseIPT = data_map.get("PS_LegalExpenses_GT").toString();
			String part1= "//*[@id='table0']/tbody";
			String actualLegalExpense= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualLegalExpenseIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualLegalExpenseDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			compareValues(Double.parseDouble(LegalExpenseCarrier), Double.parseDouble(actualLegalExpense), "Legal Expense Amount ");
			compareValues(Double.parseDouble(actualLegalExpenseIPT), Double.parseDouble(LegalExpenseIPT), "Legal Expense IPT ");
			double LegalExpenseDue = Double.parseDouble(LegalExpenseCarrier)+ Double.parseDouble(LegalExpenseIPT);	
			compareValues(Double.parseDouble(actualLegalExpenseDue), Double.parseDouble(common.roundedOff(Double.toString(LegalExpenseDue))), "Legal Expense Due Amount ");
			return Double.parseDouble(common.roundedOff(Double.toString(LegalExpenseDue)));	

		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Transaction Summary \n", t);
			return 0;
		}
	}
	public double calculateOtherTS(String testName,String code ,int j,int td,String event,String type){
		String part1= "//*[@id='table0']/tbody";
		double BrokerageAmount = 0.00;
		
	try{
			String recipient = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td-5)+"]")).getText();
			String account = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td-4)+"]")).getText();
			if(account.equalsIgnoreCase("R066")||account.equalsIgnoreCase("A324")||account.equalsIgnoreCase("AA750")||account.equalsIgnoreCase("Z906")){
				
				double generalAmount = 0.0;
				switch (type) {
				case "New Business":
					generalAmount = calculateGeneralTS(recipient,account,j,td,event,code,testName);
					break;
				case "Amended New Business":
					generalAmount = calculateGeneralTS_Rewind(recipient,account,j,td,event,code,testName);
					break;
				case "Renewal":
					generalAmount = calculateGeneralTS_Renewal(recipient,account,j,td,event,code,testName);
					break;
				case "Amended Renewal":
					generalAmount = calculateGeneralTS_RenewalRewind(recipient,account,j,td,event,code,testName);
					break;
				case "Cancel":
					generalAmount = calculateGeneralTS_Cancel(recipient,account,j,td,event,code,testName);
					break;
				}
				
			
		
				return generalAmount;
			}	
			else if(account.equalsIgnoreCase("R096"))
			{
				double XOEAmount = 0.0;
				switch (type) {
				case "New Business":
					XOEAmount = calculateXOETS(recipient,account,j,td);
					break;
				case "Amended New Business":
					XOEAmount = calculateXOETS_Rewind(recipient,account,j,td);
					break;
				case "Renewal":
					XOEAmount = calculateXOETS_Renewal(recipient,account,j,td);
					break;
				case "Amended Renewal":
					XOEAmount = calculateXOETS_RenewalRewind(recipient,account,j,td);
					break;
				case "Cancel":
					XOEAmount = calculateXOETS_CAN(recipient,account,j,td);
					break;
				}
				
				return XOEAmount;
			}
			else if(recipient.equalsIgnoreCase("Brokerage Account") && account.equalsIgnoreCase("Z001"))
			{
				if(product.equalsIgnoreCase("SPI")){
					BrokerageAmount = Math.abs(Double.parseDouble(driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+3)+"]")).getText()));
				}else{
					switch (type) {
					case "New Business":
						BrokerageAmount = calculateBrokeageAmountTS(recipient,account,j,td);
						break;
					case "Amended New Business":
						BrokerageAmount = calculateBrokeageAmountTS_Rewind(recipient,account,j,td);
						break;
					case "Renewal":
						BrokerageAmount = calculateBrokeageAmountTS_Renewal(recipient,account,j,td);
						break;
					case "Amended Renewal":
						BrokerageAmount = calculateBrokeageAmountTS_RenewalRewind(recipient,account,j,td);
						break;
					case "Cancel":
						BrokerageAmount = calculateBrokeageAmountTS_Cancel(recipient,account,j,td);
						break;
					
					}
				}
				
				return BrokerageAmount;
			
		}
	}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Other premiums calculation on Transaction summary page. \n", t);
			return 0;
		}
		return 0;

	}
	public double calculateEmployersLiabilityTS(String fileName,String testName,String code ,int j,int td){
		try{
			Map<Object,Object> data_map = null;
			
   			switch (common.currentRunningFlow) {
   			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "Cancel":
 				data_map = common.CAN_excel_data_map;
 				break;
			}
			
			String EmployersLiabilityPremium = (String)data_map.get("PS_EmployersLiability_GP");
			String EmployersLiabilityIPT = (String)data_map.get("PS_EmployersLiability_GT");
			String part1= "//*[@id='table0']/tbody";		
			String recipient = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td-5)+"]")).getText();
			if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc"))
			{
				String actualEmployersLiabilityPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String actualEmployersLiabilityIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String actualEmployersLiabilityDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				double EmployersLiabilityRSAPremium =  Double.parseDouble(EmployersLiabilityPremium) * 0.64 * 0.6;
				double EmployersLiabilityRSAIPT =  Double.parseDouble(EmployersLiabilityIPT) * 0.6;
				String EmployersLiabilityPremiumVal = common.roundedOff(Double.toString(EmployersLiabilityRSAPremium));
				compareValues(Double.parseDouble(actualEmployersLiabilityPremium), Double.parseDouble(EmployersLiabilityPremiumVal), "Employers Liability RSA Amount ");
				compareValues(Double.parseDouble(actualEmployersLiabilityIPT), Double.parseDouble(common.roundedOff(Double.toString(EmployersLiabilityRSAIPT))), "Employers Liability RSA IPT ");
				double EmployersPremiumRSADue=EmployersLiabilityRSAPremium + EmployersLiabilityRSAIPT;
				compareValues(Double.parseDouble(actualEmployersLiabilityDue), Double.parseDouble(common.roundedOff(Double.toString(EmployersPremiumRSADue))), "Employers Liability RSA Due Amount");
				return  EmployersPremiumRSADue;
			}
			else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited"))
			{
				String actualEmployersLiabilityPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String actualEmployersLiabilityIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String actualEmployersLiabilityDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				double EmployersLiabilityAJGPremium = Double.parseDouble(EmployersLiabilityPremium) * 0.62 * 0.4;
				double EmployersLiabilityAJGIPT =  Double.parseDouble(EmployersLiabilityIPT) * 0.4;
				String EmployersPremium = common.roundedOff(Double.toString(EmployersLiabilityAJGPremium));
				compareValues(Double.parseDouble(actualEmployersLiabilityPremium), Double.parseDouble(EmployersPremium), "Employers Liability AJG Amount ");
				compareValues(Double.parseDouble(actualEmployersLiabilityIPT), Double.parseDouble(common.roundedOff(Double.toString(EmployersLiabilityAJGIPT))), "Employers Liability AJG IPT ");
				double EmployersLiabilityAJGDue=EmployersLiabilityAJGPremium + EmployersLiabilityAJGIPT;
				compareValues(Double.parseDouble(actualEmployersLiabilityDue), Double.parseDouble(common.roundedOff(Double.toString(EmployersLiabilityAJGDue))), "Employers Liability AJG Due Amount ");
				return  EmployersLiabilityAJGDue; 
			}
			return 0;
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Employers Liability ammount.  \n", t);
			return 0;
		}

	}
	public double calculateProductsLiabilityTS(String fileName,String testName,String code ,int j,int td){
		try{
			Map<Object,Object> data_map = null;
		
			switch (common.currentRunningFlow) {
			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "Cancel":
 				data_map = common.CAN_excel_data_map;
 				break;
			}
			String ProductsLiabilityPremium = (String)data_map.get("PS_ProductsLiability_GP");
			String ProductsLiabilityIPT = (String)data_map.get("PS_ProductsLiability_GT");
			String part1= "//*[@id='table0']/tbody";		
			String recipient = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td-5)+"]")).getText();

			String actualProductsLiabilityPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualProductsLiabilityIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualProductsLiabilityDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc"))
			{
				double ProductsLiabilityRSAPremium =  Double.parseDouble(ProductsLiabilityPremium) * 0.64 * 0.6;
				double ProductsLiabilityRSAIPT =  Double.parseDouble(ProductsLiabilityIPT) * 0.6;
				String ProductsLiabilityPremiumVal = common.roundedOff(Double.toString(ProductsLiabilityRSAPremium));
				compareValues(Double.parseDouble(actualProductsLiabilityPremium), Double.parseDouble(ProductsLiabilityPremiumVal), "Products Liability RSA Amount ");
				compareValues(Double.parseDouble(actualProductsLiabilityIPT), Double.parseDouble(common.roundedOff(Double.toString(ProductsLiabilityRSAIPT))), "Products Liability RSA IPT ");
				double ProductsPremiumRSADue=ProductsLiabilityRSAPremium + ProductsLiabilityRSAIPT;
				compareValues(Double.parseDouble(actualProductsLiabilityDue), Double.parseDouble(common.roundedOff(Double.toString(ProductsPremiumRSADue))), "Products Liability RSA Due Amount");
				return  ProductsPremiumRSADue;
			}
			else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited"))
			{
				double ProductsLiabilityAJGPremium = Double.parseDouble(ProductsLiabilityPremium) * 0.62 * 0.4;
				double ProductsLiabilityAJGIPT =  Double.parseDouble(ProductsLiabilityIPT) * 0.4;
				String ProductsPremium = common.roundedOff(Double.toString(ProductsLiabilityAJGPremium));
				compareValues(Double.parseDouble(actualProductsLiabilityPremium), Double.parseDouble(ProductsPremium), "Employers Liability AJG Amount ");
				compareValues(Double.parseDouble(actualProductsLiabilityIPT), Double.parseDouble(common.roundedOff(Double.toString(ProductsLiabilityAJGIPT))), "Products Liability AJG IPT ");
				double ProductsLiabilityAJGDue=ProductsLiabilityAJGPremium + ProductsLiabilityAJGIPT;
				compareValues(Double.parseDouble(actualProductsLiabilityDue), Double.parseDouble(common.roundedOff(Double.toString(ProductsLiabilityAJGDue))), "Products Liability AJG Due Amount ");
				return  ProductsLiabilityAJGDue; 
			}
			return 0;
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Products Liability ammount.  \n", t);
			return 0;
		}

	}
	public double calculatePublicLiabilityTS(String fileName,String testName,String code ,int j,int td){
		try{
			
			Map<Object,Object> data_map = null;
			
			switch (common.currentRunningFlow) {
			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "Cancel":
 				data_map = common.CAN_excel_data_map;
 				break;
			}
			
			String PublicLiabilityPremium = (String)data_map.get("PS_PublicLiability_GP");
			String PublicLiabilityIPT = (String)data_map.get("PS_PublicLiability_GT");
			String part1= "//*[@id='table0']/tbody";		
			String recipient = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td-5)+"]")).getText();
			if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc"))
			{
				String actualPubLiabilityPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String actualPubLiabilityIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String actualPubLiabilityDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				double PublicLiabilityRSAPremium =  Double.parseDouble(PublicLiabilityPremium) * 0.64 * 0.6;
				double PublicLiabilityRSAIPT =  Double.parseDouble(PublicLiabilityIPT) * 0.6;
				String publicLiabilityPremiumVal = common.roundedOff(Double.toString(PublicLiabilityRSAPremium));
				compareValues(Double.parseDouble(actualPubLiabilityPremium), Double.parseDouble(publicLiabilityPremiumVal), "Public Liability RSA Amount ");
				compareValues(Double.parseDouble(actualPubLiabilityIPT), Double.parseDouble(common.roundedOff(Double.toString(PublicLiabilityRSAIPT))), "Public Liability RSA IPT ");
				double PublicPremiumRSADue=PublicLiabilityRSAPremium + PublicLiabilityRSAIPT;
				compareValues(Double.parseDouble(actualPubLiabilityDue), Double.parseDouble(common.roundedOff(Double.toString(PublicPremiumRSADue))), "Public Liability RSA Due Amount");
				return  PublicPremiumRSADue;
			}
			else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited"))
			{
				String actualPublicLiabilityPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String actualPublicLiabilityIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String actualPublicLiabilityDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				double PublicLiabilityAJGPremium = Double.parseDouble(PublicLiabilityPremium) * 0.62 * 0.4;
				double PublicLiabilityAJGIPT =  Double.parseDouble(PublicLiabilityIPT) * 0.4;
				String PublicPremium = common.roundedOff(Double.toString(PublicLiabilityAJGPremium));
				compareValues(Double.parseDouble(actualPublicLiabilityPremium), Double.parseDouble(PublicPremium), "Public Liability AJG Amount ");
				compareValues(Double.parseDouble(actualPublicLiabilityIPT), Double.parseDouble(common.roundedOff(Double.toString(PublicLiabilityAJGIPT))), "Public Liability AJG IPT ");
				double PublicLiabilityAJGDue=PublicLiabilityAJGPremium + PublicLiabilityAJGIPT;
				compareValues(Double.parseDouble(actualPublicLiabilityDue), Double.parseDouble(common.roundedOff(Double.toString(PublicLiabilityAJGDue))), "Public Liability AJG Due Amount ");
				return  PublicLiabilityAJGDue; 
			}
			return 0;
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Public Liability ammount.  \n", t);
			return 0;
		}

	}
	public double calculatePropertyOwnersLiabilityTS(String fileName,String testName,String code ,int j,int td){
		try{
			
			Map<Object,Object> data_map = null;
			
   			switch (common.currentRunningFlow) {
   			case "NB":
				data_map = common.NB_excel_data_map;
				break;
			case "MTA":
				data_map = common.MTA_excel_data_map;
				break;
			case "Renewal":
				data_map = common.Renewal_excel_data_map;
				break;
			case "Cancel":
 				data_map = common.CAN_excel_data_map;
 				break;
			}
			
			String POLPremium = (String)data_map.get("PS_PropertyOwnersLiability_GP");
			String POLIPT = (String)data_map.get("PS_PropertyOwnersLiability_GT");
			String part1= "//*[@id='table0']/tbody";		
			String recipient = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td-5)+"]")).getText();
			if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc"))
			{
				String actualPOLPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String actualPOLIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String actualPOLDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				double POLRSAPremium =  Double.parseDouble(POLPremium) * 0.64 * 0.6;
				double POLRSAIPT =  Double.parseDouble(POLIPT) * 0.6;
				String POLPremiumVal = common.roundedOff(Double.toString(POLRSAPremium));
				compareValues(Double.parseDouble(actualPOLPremium), Double.parseDouble(POLPremiumVal), "Property Owners Liability RSA Amount ");
				compareValues(Double.parseDouble(actualPOLIPT), Double.parseDouble(common.roundedOff(Double.toString(POLRSAIPT))), "Property Owners Liability RSA IPT");
				double POLRSADue=POLRSAPremium + POLRSAIPT;
				compareValues(Double.parseDouble(actualPOLDue), Double.parseDouble(common.roundedOff(Double.toString(POLRSADue))), "Property Owners Liability RSA Due Amount");
				return  POLRSADue;
			}
			else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited"))
			{
				String actualPOLPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String actualPOLIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String actualPOLDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				double POLAJGPremium = Double.parseDouble(POLPremium) * 0.62 * 0.4;
				double POLAJGIPT =  Double.parseDouble(POLIPT) * 0.4;
				String POLPremiumVal = common.roundedOff(Double.toString(POLAJGPremium));
				compareValues(Double.parseDouble(actualPOLPremium), Double.parseDouble(POLPremiumVal), "Property Owners Liability AJG Amount");
				compareValues(Double.parseDouble(actualPOLIPT), Double.parseDouble(common.roundedOff(Double.toString(POLAJGIPT))), "Property Owners Liability AJG IPT");
				double POLAJGDue=POLAJGPremium + POLAJGIPT;
				compareValues(Double.parseDouble(actualPOLDue), Double.parseDouble(common.roundedOff(Double.toString(POLAJGDue))), "Property Owners Liability AJG Due Amount");
				return  POLAJGDue;
			}
			return 0;
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Property Owners Liability ammount.  \n", t);
			return 0;
		}

	}
	public double calculateGeneralPremium(String grossPremium, String brokeCommision){
		try{
			double commRSARate = 36 - Double.parseDouble(brokeCommision) - 0.25;
			double commAIGRate = 38 - Double.parseDouble(brokeCommision) - 0.25;
			double mateialDamageRSA = Double.parseDouble(grossPremium) * 0.55 * (commRSARate/100);
			double mateialDamageAJG = Double.parseDouble(grossPremium) * 0.25 * (commAIGRate/100);
			double mateialDamageAIG = Double.parseDouble(grossPremium) * 0.2 * (commAIGRate/100);
			
			return(mateialDamageRSA + mateialDamageAJG + mateialDamageAIG);
			
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
	}
	public double calculateXOEGeneralPremium(String grossPremium, String brokeCommision){
		try{
			double commRSARate = 36 - Double.parseDouble(brokeCommision) - 0.25;
			
			double XOEGeneralAmount = Double.parseDouble(grossPremium) * (commRSARate/100);
			
			return(XOEGeneralAmount);
			
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for XOE covers \n", t);
			return 0;
		}
	}
	public double calculateGeneralPremiumConB(String grossPremium, String brokeCommision){
		try{
			
			Map<Object,Object> data_map = null;
			
   			switch (common.currentRunningFlow) {
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
			
			String splitRateRSA ="1.00";
			String splitRateNovae ="1.00";
			String tradeCodeCat = (String)data_map.get("PD_TCS_TradeCode_HazardGroup");
			switch(tradeCodeCat){
			case "1" : 	
					splitRateRSA = (String)data_map.get("TS_PHG1RSA");
					splitRateNovae = (String)data_map.get("TS_PHG1Novae");
				break;
			case "2" : 	
				splitRateRSA = (String)data_map.get("TS_PHG2RSA");
				splitRateNovae = (String)data_map.get("TS_PHG2Novae");
					break;
			case "3" : 	
					splitRateRSA = (String)data_map.get("TS_PHG3RSA");
					splitRateNovae = (String)data_map.get("TS_PHG3Novae");
				break;
			case "4" : 	
					splitRateRSA = (String)data_map.get("TS_PHG4RSA");
					splitRateNovae = (String)data_map.get("TS_PHG4Novae");
				break;
			case "5" : 
					splitRateRSA = (String)data_map.get("TS_PHG5RSA");
					splitRateNovae = (String)data_map.get("TS_PHG5Novae");
				break;
			case "6" : 	
					splitRateRSA = (String)data_map.get("TS_PHG6RSA");
					splitRateNovae = (String)data_map.get("TS_PHG6Novae");
					
				break;
			default :
				splitRateRSA = (String)data_map.get("TS_PHGDDefaultRSA");
				splitRateNovae = (String)data_map.get("TS_PHGDDefaultNovae");	
				break;
			}	
			double commRSARate = 36 - Double.parseDouble(brokeCommision) - 0.25;
			double commAIGRate = 38 - Double.parseDouble(brokeCommision) - 0.25;
			double mateialDamageRSA = Double.parseDouble(grossPremium) * Double.parseDouble(splitRateRSA) * (commRSARate/100);
			double mateialDamageAJG = Double.parseDouble(grossPremium) * Double.parseDouble(splitRateNovae) * (commAIGRate/100);
			return(mateialDamageRSA + mateialDamageAJG);
			
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
	}
	public double calculateGeneralTS(String recipient,String account,int j, int td, String event,String code,String fileName){
		try{
				String terrorPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_GP");
				String terrorIPT = (String)common.NB_excel_data_map.get("PS_Terrorism_GT");
				String cyberPremium = (String)common.NB_excel_data_map.get("PS_CyberandDataSecurity_GP");
				String cyberIPT = (String)common.NB_excel_data_map.get("PS_CyberandDataSecurity_GT");
				String LegalExpensePremium = (String)common.NB_excel_data_map.get("PS_LegalExpenses_GP");
				String LegalExpenseIPT = (String)common.NB_excel_data_map.get("PS_LegalExpenses_GT");
				String DirectorsGrossPremium = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String directorsIPT = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_GT");
				String MarineCargoGrossPremium = (String)common.NB_excel_data_map.get("PS_MarineCargo_GP");
				String MarineCargoIPT = (String)common.NB_excel_data_map.get("PS_MarineCargo_GT");
				String EmpLbtGrossPremium = (String)common.NB_excel_data_map.get("PS_EmployersLiability_GP");
				String EmplbtCargoIPT = (String)common.NB_excel_data_map.get("PS_EmployersLiability_GT");
				String ProductLbtGrossPremium = (String)common.NB_excel_data_map.get("PS_ProductsLiability_GP");
				String ProductLbtIPT = (String)common.NB_excel_data_map.get("PS_ProductsLiability_GT");
				String PublicLbtGrossPremium = (String)common.NB_excel_data_map.get("PS_PublicLiability_GP");
				String PubliclbtIPT = (String)common.NB_excel_data_map.get("PS_PublicLiability_GT");
				String POLPremium = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String POLIPT = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_GT");
				String product = common.product;
				
				String totalGrossPremium = (String)common.NB_excel_data_map.get("PS_Total_GP");
				String totalGrossIPT = (String)common.NB_excel_data_map.get("PS_Total_GT");
				String part1= "//*[@id='table0']/tbody";
				
				String general= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String generalInsTax= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String generalDue= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				
				
				if(terrorPremium == null) {terrorPremium = "0.00";}
				if(terrorIPT == null) {terrorIPT = "0.00";}
				if(cyberPremium == null) {cyberPremium = "0.00";}
				if(cyberIPT == null) {cyberIPT = "0.00";}
				if(LegalExpensePremium == null) {LegalExpensePremium = "0.00";}
				if(LegalExpenseIPT == null) {LegalExpenseIPT = "0.00";}
				if(DirectorsGrossPremium == null) {DirectorsGrossPremium = "0.00";}
				if(directorsIPT == null) {directorsIPT = "0.00";}
				if(MarineCargoGrossPremium == null) {MarineCargoGrossPremium = "0.00";}
				if(MarineCargoIPT == null) {MarineCargoIPT = "0.00";}
				if(EmpLbtGrossPremium == null) {EmpLbtGrossPremium = "0.00";}
				if(EmplbtCargoIPT == null) {EmplbtCargoIPT = "0.00";}
				if(ProductLbtGrossPremium == null) {ProductLbtGrossPremium = "0.00";}
				if(ProductLbtIPT == null) {ProductLbtIPT = "0.00";}
				if(PublicLbtGrossPremium == null) {PublicLbtGrossPremium = "0.00";}
				if(PubliclbtIPT == null) {PubliclbtIPT = "0.00";}
				if(POLPremium == null) {POLPremium = "0.00";}
				if(POLIPT == null) {POLIPT = "0.00";}
							
				
				if(((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("No") ||(String)common.NB_excel_data_map.get("CD_Terrorism")== null )
				{
					terrorPremium="0.00";
					terrorIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_CyberandDataSecurity")== null || ((String)common.NB_excel_data_map.get("CD_CyberandDataSecurity")).equals("No"))
				{
					cyberPremium="0.00";
					cyberIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_LegalExpenses")== null ||((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("No"))
				{
					LegalExpensePremium="0.00";
					LegalExpenseIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")== null ||((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("No"))
				{
					DirectorsGrossPremium="0.00";
					directorsIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_MarineCargo")== null ||((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("No"))
				{
					MarineCargoGrossPremium="0.00";
					MarineCargoIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_Liability")== null || ((String)common.NB_excel_data_map.get("CD_Liability")).equals("No"))
				{
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}
				if(product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("CTA")){
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}
				
				double generalPremium = Double.parseDouble(totalGrossPremium) - (Double.parseDouble(terrorPremium) 
																				+ Double.parseDouble(cyberPremium) 
																				+ Double.parseDouble(LegalExpensePremium)
																				+ Double.parseDouble(DirectorsGrossPremium)
																				+ Double.parseDouble(MarineCargoGrossPremium)
																				+ Double.parseDouble(EmpLbtGrossPremium)
																				+ Double.parseDouble(ProductLbtGrossPremium)
																				+ Double.parseDouble(PublicLbtGrossPremium)
																				+ Double.parseDouble(POLPremium));		
				double generalIPT = Double.parseDouble(totalGrossIPT) - (Double.parseDouble(terrorIPT) 
																		+ Double.parseDouble(cyberIPT) 
																		+ Double.parseDouble(LegalExpenseIPT)
																		+ Double.parseDouble(directorsIPT)
																		+ Double.parseDouble(MarineCargoIPT)
																		+ Double.parseDouble(POLIPT)
																		+ Double.parseDouble(EmplbtCargoIPT)
																		+ Double.parseDouble(ProductLbtIPT)
																		+ Double.parseDouble(PubliclbtIPT)
																		);
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066"))
				{
					
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General RAS Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT");
					double actualDue = values[0] + values[1];
					String dueAmmout =  common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(generalDue), "General RAS Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AJG Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AJG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("AIG Europe Ltd") && account.equalsIgnoreCase("AA750"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AIG Amount ");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AIG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AIG Due Amount ");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("Z906"))
				{
					String generalAJG= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();			
					double generalAJGPremium =  (generalPremium * 0.0025) 
												+ (Double.parseDouble(terrorPremium) * 0.0025) 
												+ (Double.parseDouble(DirectorsGrossPremium) * 0.0025)
												+ (Double.parseDouble(MarineCargoGrossPremium) * 0.0025)
												+ (Double.parseDouble(EmpLbtGrossPremium) * 0.0025)
												+ (Double.parseDouble(POLPremium) * 0.0025);
				
					
					String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
					compareValues(Double.parseDouble(generalammount), Double.parseDouble(generalAJG), "General AIG Amount ");
					customAssert.assertTrue(WriteDataToXl(event+"_"+code, "Transaction Summary", (String)common.NB_excel_data_map.get("Automation Key"), "TS_AIGAmount", generalammount,common.NB_excel_data_map),"Error while writing AIG Ammount data to excel .");
					
					return Double.parseDouble(generalammount);
				}
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066") &&
						(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{				
							double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
							compareValues(values[0], Double.parseDouble(general), "General RAS Amount ");
							compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT  ");
							double actualDue = values[0] + values[1];
							String dueAmmout =  common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General RAS Due Amount ");
							return Double.parseDouble(dueAmmout);
						}
						else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324") &&
								(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{
						double generalAJGPremium =  generalPremium *  0.4 * 0.62;
							double actualgeneralAJGIPT = generalIPT * 0.4;
							String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
							compareValues(Double.parseDouble(generalammount), Double.parseDouble(general), "General AJG Amount ");
							compareValues(Double.parseDouble(generalInsTax), Double.parseDouble(common.roundedOff(Double.toString(actualgeneralAJGIPT))), "General AJG IPT ");
							double actualDue = generalAJGPremium + actualgeneralAJGIPT;
							String dueAmmout = common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Ammount ");
							return Double.parseDouble(dueAmmout);
						}
				return 0.00;
		
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
}
	
	/**
	 * 
	 * 
	 * 
	 * @param recipient
	 * @param account
	 * @param j
	 * @param td
	 * @return
	 */
	
	public double calculateGeneralTS_Rewind(String recipient,String account,int j, int td, String event,String code,String fileName){
		try{
				String terrorPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_GP");
				String terrorIPT = (String)common.NB_excel_data_map.get("PS_Terrorism_GT");
				String cyberPremium = (String)common.NB_excel_data_map.get("PS_CyberandDataSecurity_GP");
				String cyberIPT = (String)common.NB_excel_data_map.get("PS_CyberandDataSecurity_GT");
				String LegalExpensePremium = (String)common.NB_excel_data_map.get("PS_LegalExpenses_GP");
				String LegalExpenseIPT = (String)common.NB_excel_data_map.get("PS_LegalExpenses_GT");
				String DirectorsGrossPremium = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String directorsIPT = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_GT");
				String MarineCargoGrossPremium = (String)common.NB_excel_data_map.get("PS_MarineCargo_GP");
				String MarineCargoIPT = (String)common.NB_excel_data_map.get("PS_MarineCargo_GT");
				String EmpLbtGrossPremium = (String)common.NB_excel_data_map.get("PS_EmployersLiability_GP");
				String EmplbtCargoIPT = (String)common.NB_excel_data_map.get("PS_EmployersLiability_GT");
				String ProductLbtGrossPremium = (String)common.NB_excel_data_map.get("PS_ProductsLiability_GP");
				String ProductLbtIPT = (String)common.NB_excel_data_map.get("PS_ProductsLiability_GT");
				String PublicLbtGrossPremium = (String)common.NB_excel_data_map.get("PS_PublicLiability_GP");
				String PubliclbtIPT = (String)common.NB_excel_data_map.get("PS_PublicLiability_GT");
				String POLPremium = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String POLIPT = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_GT");
				String product = common.product;
				
				String totalGrossPremium = (String)common.NB_excel_data_map.get("PS_Total_GP");
				String totalGrossIPT = (String)common.NB_excel_data_map.get("PS_Total_GT");
				String part1= "//*[@id='table0']/tbody";
				
				String general= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String generalInsTax= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String generalDue= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				
				
				if(terrorPremium == null) {terrorPremium = "0.00";}
				if(terrorIPT == null) {terrorIPT = "0.00";}
				if(cyberPremium == null) {cyberPremium = "0.00";}
				if(cyberIPT == null) {cyberIPT = "0.00";}
				if(LegalExpensePremium == null) {LegalExpensePremium = "0.00";}
				if(LegalExpenseIPT == null) {LegalExpenseIPT = "0.00";}
				if(DirectorsGrossPremium == null) {DirectorsGrossPremium = "0.00";}
				if(directorsIPT == null) {directorsIPT = "0.00";}
				if(MarineCargoGrossPremium == null) {MarineCargoGrossPremium = "0.00";}
				if(MarineCargoIPT == null) {MarineCargoIPT = "0.00";}
				if(EmpLbtGrossPremium == null) {EmpLbtGrossPremium = "0.00";}
				if(EmplbtCargoIPT == null) {EmplbtCargoIPT = "0.00";}
				if(ProductLbtGrossPremium == null) {ProductLbtGrossPremium = "0.00";}
				if(ProductLbtIPT == null) {ProductLbtIPT = "0.00";}
				if(PublicLbtGrossPremium == null) {PublicLbtGrossPremium = "0.00";}
				if(PubliclbtIPT == null) {PubliclbtIPT = "0.00";}
				if(POLPremium == null) {POLPremium = "0.00";}
				if(POLIPT == null) {POLIPT = "0.00";}
							
				//if(((((String)mdata.get("CD_LossOfRentalIncome")).equals("Yes") && !((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("No"))  || ((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("Yes")))
				if((String)common.NB_excel_data_map.get("CD_Terrorism")== null 
					|| (((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("No") && ((String)common.NB_excel_data_map.get("CD_Add_Terrorism")).equals("No")) 
					|| (((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes") && ((String)common.NB_excel_data_map.get("CD_Add_Terrorism")).equals("No"))
					)
					{
						terrorPremium="0.00";
						terrorIPT="0.00";
					}
				if((String)common.NB_excel_data_map.get("CD_CyberandDataSecurity")== null  
					|| (((String)common.NB_excel_data_map.get("CD_CyberandDataSecurity")).equals("No") && ((String)common.NB_excel_data_map.get("CD_Add_CyberandDataSecurity")).equals("No")) 
					|| (((String)common.NB_excel_data_map.get("CD_CyberandDataSecurity")).equals("Yes") && ((String)common.NB_excel_data_map.get("CD_Add_CyberandDataSecurity")).equals("No"))
					)
					{
						cyberPremium="0.00";
						cyberIPT="0.00";
					}
				if((String)common.NB_excel_data_map.get("CD_LegalExpenses")== null 
					|| (((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("No") && ((String)common.NB_excel_data_map.get("CD_Add_LegalExpenses")).equals("No")) 
					|| (((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("Yes") && ((String)common.NB_excel_data_map.get("CD_Add_LegalExpenses")).equals("No"))
					)
					{
						LegalExpensePremium="0.00";
						LegalExpenseIPT="0.00";
					}
			
				/*if((String)common.NB_excel_data_map.get("CD_LegalExpenses")== null ||((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("No"))
				{
					LegalExpensePremium="0.00";
					LegalExpenseIPT="0.00";
				}*/
				if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")== null 
					|| (((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("No") && ((String)common.NB_excel_data_map.get("CD_Add_DirectorsandOfficers")).equals("No")) 
					|| (((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("Yes") && ((String)common.NB_excel_data_map.get("CD_Add_DirectorsandOfficers")).equals("No"))
					)
					{
						DirectorsGrossPremium="0.00";
						directorsIPT="0.00";
					}
				
				/*if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")== null ||((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("No"))
				{
					DirectorsGrossPremium="0.00";
					directorsIPT="0.00";
				}*/
				if( (String)common.NB_excel_data_map.get("CD_MarineCargo")== null 
					|| (((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("No") && ((String)common.NB_excel_data_map.get("CD_Add_MarineCargo")).equals("No")) 
					|| (((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("Yes") && ((String)common.NB_excel_data_map.get("CD_Add_MarineCargo")).equals("No"))
					 )
					{
						MarineCargoGrossPremium="0.00";
						MarineCargoIPT="0.00";
					}
				
				/*if((String)common.NB_excel_data_map.get("CD_MarineCargo")== null ||((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("No"))
				{
					MarineCargoGrossPremium="0.00";
					MarineCargoIPT="0.00";
				}*/
				if((String)common.NB_excel_data_map.get("CD_Liability")== null 
					|| (((String)common.NB_excel_data_map.get("CD_Liability")).equals("No") && ((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("No")) 
					|| (((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes") && ((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("No"))
					)
					{
					EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
					}
				
				/*if((String)common.NB_excel_data_map.get("CD_Liability")== null || ((String)common.NB_excel_data_map.get("CD_Liability")).equals("No"))
				{
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}*/
				
				if(product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("CTA")){
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}
				
				double generalPremium = Double.parseDouble(totalGrossPremium) - (Double.parseDouble(terrorPremium) 
																				+ Double.parseDouble(cyberPremium) 
																				+ Double.parseDouble(LegalExpensePremium)
																				+ Double.parseDouble(DirectorsGrossPremium)
																				+ Double.parseDouble(MarineCargoGrossPremium)
																				+ Double.parseDouble(EmpLbtGrossPremium)
																				+ Double.parseDouble(ProductLbtGrossPremium)
																				+ Double.parseDouble(PublicLbtGrossPremium)
																				+ Double.parseDouble(POLPremium));		
				double generalIPT = Double.parseDouble(totalGrossIPT) - (Double.parseDouble(terrorIPT) 
																		+ Double.parseDouble(cyberIPT) 
																		+ Double.parseDouble(LegalExpenseIPT)
																		+ Double.parseDouble(directorsIPT)
																		+ Double.parseDouble(MarineCargoIPT)
																		+ Double.parseDouble(POLIPT)
																		+ Double.parseDouble(EmplbtCargoIPT)
																		+ Double.parseDouble(ProductLbtIPT)
																		+ Double.parseDouble(PubliclbtIPT)
																		);
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066"))
				{
					
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General RAS Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT");
					double actualDue = values[0] + values[1];
					String dueAmmout =  common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(generalDue), "General RAS Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AJG Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AJG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("AIG Europe Ltd") && account.equalsIgnoreCase("AA750"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AIG Amount ");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AIG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AIG Due Amount ");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("Z906"))
				{
					String generalAJG= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();			
					double generalAJGPremium =  (generalPremium * 0.0025) 
												+ (Double.parseDouble(terrorPremium) * 0.0025) 
												+ (Double.parseDouble(DirectorsGrossPremium) * 0.0025)
												+ (Double.parseDouble(MarineCargoGrossPremium) * 0.0025)
												+ (Double.parseDouble(EmpLbtGrossPremium) * 0.0025)
												+ (Double.parseDouble(POLPremium) * 0.0025);
				
					
					String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
					compareValues(Double.parseDouble(generalammount), Double.parseDouble(generalAJG), "General AIG Amount ");
					customAssert.assertTrue(WriteDataToXl(event+"_"+code, "Transaction Summary", (String)common.NB_excel_data_map.get("Automation Key"), "TS_AIGAmount", generalammount,common.NB_excel_data_map),"Error while writing AIG Ammount data to excel .");
					
					return Double.parseDouble(generalammount);
				}
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066") &&
						(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{				
							double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
							compareValues(values[0], Double.parseDouble(general), "General RAS Amount ");
							compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT  ");
							double actualDue = values[0] + values[1];
							String dueAmmout =  common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General RAS Due Amount ");
							return Double.parseDouble(dueAmmout);
						}
						else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324") &&
								(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{
						double generalAJGPremium =  generalPremium *  0.4 * 0.62;
							double actualgeneralAJGIPT = generalIPT * 0.4;
							String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
							compareValues(Double.parseDouble(generalammount), Double.parseDouble(general), "General AJG Amount ");
							compareValues(Double.parseDouble(generalInsTax), Double.parseDouble(common.roundedOff(Double.toString(actualgeneralAJGIPT))), "General AJG IPT ");
							double actualDue = generalAJGPremium + actualgeneralAJGIPT;
							String dueAmmout = common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Ammount ");
							return Double.parseDouble(dueAmmout);
						}
				return 0.00;
		
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
}
	/**
	 * 
	 * END of Calculation of General function for transaction summary.
	 * 
	 * 
	 */
	
	public double calculateBrokeageAmountTS(String recipient,String account,int j, int td){
		double materialDamageFP = 0.00, businessInteruptionFP=0.00, EmployersLiabiliyFP=0.00, PublicLiabilityFP=0.00, ContractorAllRisksFP=0.00;
		double ProductLiability =0.00, ComputersAndElectronicRiskFP=0.00, MoneyFP =0.00, GoodsInTansitFP=0.00,FidilityGuaranteFP=0.00;
		double LegalExpensesFP=0.00, terrorismFP=0.00, DirectorsAndOfficersFP=0.00, SpecifiedRisksFP=0.00, generalPremium;
		double MarineCargoFP=0.00, FrozenFoodFP=0.00, LossofLicenceFP=0.00, PropertyOwnersLiability=0.00, LossOfRentalIncomeFP=0.00;
		double PropertyExcessofLossFP=0.00;
		String part1= "//*[@id='table0']/tbody";
		try{
			String brokerageAccount= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			if((String)common.NB_excel_data_map.get("CD_MaterialDamage")!= null && ((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_MaterialDamage_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_MaterialDamage_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
				materialDamageFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					materialDamageFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}
			}
			if((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")!= null && ((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_LossOfRentalIncome_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_LossOfRentalIncome_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossOfRentalIncomeFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossOfRentalIncomeFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")!= null && ((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_CR");
				if(product.equalsIgnoreCase("XOE"))
				{
					PropertyExcessofLossFP = calculateXOEGeneralPremium(GrossPremium,BrokerCommission);
				}
													
			}
			if((String)common.NB_excel_data_map.get("CD_BusinessInterruption")!= null && ((String)common.NB_excel_data_map.get("CD_BusinessInterruption")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_BusinessInterruption_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_BusinessInterruption_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					businessInteruptionFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					businessInteruptionFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_EmployersLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_EmployersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					EmployersLiabiliyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					EmployersLiabiliyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_PublicLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PublicLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					PublicLiabilityFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")){
					PublicLiabilityFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}
			if((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")!= null && ((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_ContractorsAllRisks_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ContractorsAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ContractorAllRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					ContractorAllRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")!= null && ((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_SpecifiedAllRisks_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_SpecifiedAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					SpecifiedRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					SpecifiedRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_ProductsLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ProductsLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					ProductLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG"))
				{
					ProductLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes")
					&& (product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("POC")))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					PropertyOwnersLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("POC")){
					PropertyOwnersLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")!= null && ((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_ComputersandElectronicRisks_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ComputersandElectronicRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}
			if((String)common.NB_excel_data_map.get("CD_Money")!= null && ((String)common.NB_excel_data_map.get("CD_Money")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_Money_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_Money_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					MoneyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					MoneyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}
			if((String)common.NB_excel_data_map.get("CD_GoodsInTransit")!= null && ((String)common.NB_excel_data_map.get("CD_GoodsInTransit")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_GoodsInTransit_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_GoodsInTransit_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					GoodsInTansitFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					GoodsInTansitFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")!= null && ((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_FidelityGuarantee_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_FidelityGuarantee_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FidilityGuaranteFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FidilityGuaranteFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.NB_excel_data_map.get("CD_FrozenFood")!= null && ((String)common.NB_excel_data_map.get("CD_FrozenFood")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_FrozenFoods_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_FrozenFoods_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FrozenFoodFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FrozenFoodFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.NB_excel_data_map.get("CD_LossofLicence")!= null && ((String)common.NB_excel_data_map.get("CD_LossofLicence")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_LossOfLicence_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_LossOfLicence_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossofLicenceFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossofLicenceFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.NB_excel_data_map.get("CD_LegalExpenses")!= null && ((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("Yes"))
			{					
				String NetPremium = (String)common.NB_excel_data_map.get("PS_LegalExpenses_NP");
				String annualCarrier = (String)common.NB_excel_data_map.get("LE_AnnualCarrierPremium");
				LegalExpensesFP = Double.parseDouble(NetPremium) - Double.parseDouble(annualCarrier);
			}
			if((String)common.NB_excel_data_map.get("CD_Terrorism")!= null && ((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_GP");
				String NetPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_NP");
				terrorismFP = (Double.parseDouble(NetPremium)*(10.00/100))	- (Double.parseDouble(GrossPremium)*(0.25/100));
				if(((String)common.NB_excel_data_map.get("TER_CedeComm")).equalsIgnoreCase("Yes")){
					terrorismFP= -(Double.parseDouble((String)common.NB_excel_data_map.get("TS_AIGAmount")));
				}
			}
			if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")!= null && ((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String brokerCommision = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_CR");
				DirectorsAndOfficersFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}
			if((String)common.NB_excel_data_map.get("CD_MarineCargo")!= null && ((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_MarineCargo_GP");
				String brokerCommision = (String)common.NB_excel_data_map.get("PS_MarineCargo_CR");
				MarineCargoFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}
								
			generalPremium= materialDamageFP + LossOfRentalIncomeFP +businessInteruptionFP + EmployersLiabiliyFP 
					+ PublicLiabilityFP + PropertyOwnersLiability + ContractorAllRisksFP + SpecifiedRisksFP + ProductLiability 
					+ ComputersAndElectronicRiskFP + MoneyFP + GoodsInTansitFP + FidilityGuaranteFP + LegalExpensesFP
					+terrorismFP + DirectorsAndOfficersFP + MarineCargoFP + LossofLicenceFP + FrozenFoodFP+PropertyExcessofLossFP;
				String generalammount = decim.format(generalPremium);
		//	String actualgeneralPremium = decim.format(generalPremium);
				compareValues(Double.parseDouble(generalammount), Double.parseDouble(brokerageAccount), "General Brokerage Amount ");
				return Double.parseDouble(generalammount);
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Brokerage ammout. \n", t);
			return 0;
		}
}

	
	public double calculateBrokeageAmountTS_Rewind(String recipient,String account,int j, int td){
		double materialDamageFP = 0.00, businessInteruptionFP=0.00, EmployersLiabiliyFP=0.00, PublicLiabilityFP=0.00, ContractorAllRisksFP=0.00;
		double ProductLiability =0.00, ComputersAndElectronicRiskFP=0.00, MoneyFP =0.00, GoodsInTansitFP=0.00,FidilityGuaranteFP=0.00;
		double LegalExpensesFP=0.00, terrorismFP=0.00, DirectorsAndOfficersFP=0.00, SpecifiedRisksFP=0.00, generalPremium;
		double MarineCargoFP=0.00, FrozenFoodFP=0.00, LossofLicenceFP=0.00, PropertyOwnersLiability=0.00, LossOfRentalIncomeFP=0.00;
		double PropertyExcessofLossFP=0.00;
		String part1= "//*[@id='table0']/tbody";
		try{
			String brokerageAccount= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			
			if((String)common.NB_excel_data_map.get("CD_MaterialDamage")!= null && 
				((((((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_MaterialDamage")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_MaterialDamage")).equals("Yes")))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_MaterialDamage_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_MaterialDamage_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
				materialDamageFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					materialDamageFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}
			}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_MaterialDamage")!= null && ((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_MaterialDamage_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_MaterialDamage_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
				materialDamageFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					materialDamageFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_LossOfRentalIncome")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_LossOfRentalIncome")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_LossOfRentalIncome_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_LossOfRentalIncome_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						LossOfRentalIncomeFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						LossOfRentalIncomeFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")!= null && ((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_LossOfRentalIncome_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_LossOfRentalIncome_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossOfRentalIncomeFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossOfRentalIncomeFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_PropertyExcessofLoss")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_PropertyExcessofLoss")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_CR");
					if(product.equalsIgnoreCase("XOE"))
					{
						PropertyExcessofLossFP = calculateXOEGeneralPremium(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")!= null && ((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_CR");
				if(product.equalsIgnoreCase("XOE"))
				{
					PropertyExcessofLossFP = calculateXOEGeneralPremium(GrossPremium,BrokerCommission);
				}
													
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_BusinessInterruption")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_BusinessInterruption")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_BusinessInterruption")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_BusinessInterruption")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_BusinessInterruption_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_BusinessInterruption_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						businessInteruptionFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						businessInteruptionFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_BusinessInterruption")!= null && ((String)common.NB_excel_data_map.get("CD_BusinessInterruption")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_BusinessInterruption_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_BusinessInterruption_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					businessInteruptionFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					businessInteruptionFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}*/
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_EmployersLiability_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_EmployersLiability_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						EmployersLiabiliyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						EmployersLiabiliyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}	
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_EmployersLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_EmployersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					EmployersLiabiliyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					EmployersLiabiliyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}*/
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_PublicLiability_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PublicLiability_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
					{
						PublicLiabilityFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")){
						PublicLiabilityFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}	
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_PublicLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PublicLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					PublicLiabilityFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")){
					PublicLiabilityFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_ContractorsAllRisks")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_ContractorsAllRisks")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_ContractorsAllRisks_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ContractorsAllRisks_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						ContractorAllRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						ContractorAllRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")!= null && ((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_ContractorsAllRisks_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ContractorsAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ContractorAllRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					ContractorAllRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_SpecifiedAllRisks")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_SpecifiedAllRisks")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_SpecifiedAllRisks_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_SpecifiedAllRisks_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						SpecifiedRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						SpecifiedRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")!= null && ((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_SpecifiedAllRisks_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_SpecifiedAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					SpecifiedRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					SpecifiedRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_ProductsLiability_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ProductsLiability_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
					{
						ProductLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG"))
					{
						ProductLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_ProductsLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ProductsLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					ProductLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG"))
				{
					ProductLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}*/
			
			
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && 
					(((((((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_Liability")).equals("Yes")))
					&& (product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("POC")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						PropertyOwnersLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("POC")){
						PropertyOwnersLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes")
					&& (product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("POC")))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					PropertyOwnersLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("POC")){
					PropertyOwnersLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_ComputersandElectronicRisks")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_ComputersandElectronicRisks")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_ComputersandElectronicRisks_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ComputersandElectronicRisks_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						ComputersAndElectronicRiskFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC"))
					{
						ComputersAndElectronicRiskFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}	
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")!= null && ((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_ComputersandElectronicRisks_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ComputersandElectronicRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_Money")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_Money")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_Money")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_Money")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_Money_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_Money_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						MoneyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						MoneyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Money")!= null && ((String)common.NB_excel_data_map.get("CD_Money")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_Money_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_Money_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					MoneyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					MoneyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}*/
			if((String)common.NB_excel_data_map.get("CD_GoodsInTransit")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_GoodsInTransit")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_GoodsInTransit")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_GoodsInTransit")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_GoodsInTransit_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_GoodsInTransit_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						GoodsInTansitFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						GoodsInTansitFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_GoodsInTransit")!= null && ((String)common.NB_excel_data_map.get("CD_GoodsInTransit")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_GoodsInTransit_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_GoodsInTransit_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					GoodsInTansitFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					GoodsInTansitFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_FidelityGuarantee")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_FidelityGuarantee")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_FidelityGuarantee_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_FidelityGuarantee_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						FidilityGuaranteFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						FidilityGuaranteFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")!= null && ((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_FidelityGuarantee_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_FidelityGuarantee_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FidilityGuaranteFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FidilityGuaranteFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_FrozenFood")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_FrozenFood")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_FrozenFood")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_FrozenFood")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_FrozenFoods_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_FrozenFoods_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						FrozenFoodFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						FrozenFoodFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_FrozenFood")!= null && ((String)common.NB_excel_data_map.get("CD_FrozenFood")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_FrozenFoods_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_FrozenFoods_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FrozenFoodFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FrozenFoodFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_LossofLicence")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_LossofLicence")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_LossofLicence")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_LossofLicence")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_LossOfLicence_GP");
					String BrokerCommission = (String)common.NB_excel_data_map.get("PS_LossOfLicence_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						LossofLicenceFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						LossofLicenceFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_LossofLicence")!= null && ((String)common.NB_excel_data_map.get("CD_LossofLicence")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_LossOfLicence_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_LossOfLicence_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossofLicenceFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossofLicenceFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_LegalExpenses")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_LegalExpenses")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_LegalExpenses")).equals("Yes")))
				{					 
					String NetPremium = (String)common.NB_excel_data_map.get("PS_LegalExpenses_NP");
					String annualCarrier = (String)common.NB_excel_data_map.get("LE_AnnualCarrierPremium");
					LegalExpensesFP = Double.parseDouble(NetPremium) - Double.parseDouble(annualCarrier);
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_LegalExpenses")!= null && ((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("Yes"))
			{					
				String NetPremium = (String)common.NB_excel_data_map.get("PS_LegalExpenses_NP");
				String annualCarrier = (String)common.NB_excel_data_map.get("LE_AnnualCarrierPremium");
				LegalExpensesFP = Double.parseDouble(NetPremium) - Double.parseDouble(annualCarrier);
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_Terrorism")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes")) && !((String)common.NB_excel_data_map.get("CD_Add_Terrorism")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_Terrorism")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_GP");
					String NetPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_NP");
					terrorismFP = (Double.parseDouble(NetPremium)*(10.00/100))	- (Double.parseDouble(GrossPremium)*(0.25/100));
					if(((String)common.NB_excel_data_map.get("TER_CedeComm")).equalsIgnoreCase("Yes")){
						terrorismFP= -(Double.parseDouble((String)common.NB_excel_data_map.get("TS_AIGAmount")));
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Terrorism")!= null && ((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_GP");
				String NetPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_NP");
				terrorismFP = (Double.parseDouble(NetPremium)*(10.00/100))	- (Double.parseDouble(GrossPremium)*(0.25/100));
				if(((String)common.NB_excel_data_map.get("TER_CedeComm")).equalsIgnoreCase("Yes")){
					terrorismFP= -(Double.parseDouble((String)common.NB_excel_data_map.get("TS_AIGAmount")));
				}
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("Yes"))  && !((String)common.NB_excel_data_map.get("CD_Add_DirectorsandOfficers")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_DirectorsandOfficers")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_GP");
					String brokerCommision = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_CR");
					DirectorsAndOfficersFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")!= null && ((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String brokerCommision = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_CR");
				DirectorsAndOfficersFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}*/
			
			if((String)common.NB_excel_data_map.get("CD_MarineCargo")!= null && 
					((((((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("Yes"))  && !((String)common.NB_excel_data_map.get("CD_Add_MarineCargo")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_MarineCargo")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.NB_excel_data_map.get("PS_MarineCargo_GP");
					String brokerCommision = (String)common.NB_excel_data_map.get("PS_MarineCargo_CR");
					MarineCargoFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_MarineCargo")!= null && ((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_MarineCargo_GP");
				String brokerCommision = (String)common.NB_excel_data_map.get("PS_MarineCargo_CR");
				MarineCargoFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}*/
								
			generalPremium= materialDamageFP + LossOfRentalIncomeFP +businessInteruptionFP + EmployersLiabiliyFP 
					+ PublicLiabilityFP + PropertyOwnersLiability + ContractorAllRisksFP + SpecifiedRisksFP + ProductLiability 
					+ ComputersAndElectronicRiskFP + MoneyFP + GoodsInTansitFP + FidilityGuaranteFP + LegalExpensesFP
					+terrorismFP + DirectorsAndOfficersFP + MarineCargoFP + LossofLicenceFP + FrozenFoodFP+PropertyExcessofLossFP;
				String generalammount = decim.format(generalPremium);
		//	String actualgeneralPremium = decim.format(generalPremium);
				compareValues(Double.parseDouble(generalammount), Double.parseDouble(brokerageAccount), "General Brokerage Amount ");
				return Double.parseDouble(generalammount);
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Brokerage ammout. \n", t);
			return 0;
		}
}

	////////// -----------------------------------------------------------------------//////
	////////// -----------------------------------------------------------------------//////
	////////// ---OLD CommonFunction Methods Refactored According to New Framework----//////
	////////// -----------------------------------------------------------------------//////
	////////// -----------------------------------------------------------------------//////
	/**
	 * This method is to check if client is already exist or not.
	 */

	public boolean checkClient(Map<Object, Object> data_map, String code, String event) {

		try {
			if (data_map.get("CC_CreateClient").equals("Yes")) {
				customAssert.assertTrue(common.createClient(data_map, code, event), "Unable to Create Client.");
			} else {
				customAssert.assertTrue(common.searchClient(data_map, code, event), "Unable to Search Client with given data . ");
			}

			return true;

		} catch (Throwable t) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in " + methodName + " function");
			k.reportErr("Failed in " + methodName + " function", t);
			Assert.fail("Unable to Check client .  \n", t);
			return false;
		}

	}

	/**
	 * This method is to search client if already exist
	 */

	public boolean searchClient(Map<Object, Object> map_data, String code, String event) {
		boolean retvalue = true;
		try {
			
			customAssert.assertTrue(k.Click("client_menu"), "Client Menu option does not exist.");
			String client_Search_Pref = (String)map_data.get("NB_SearchClientBy");
			switch(client_Search_Pref){
			
				case "Client Name" :
					customAssert.assertTrue(k.Input("CCF_NB_ClientName", (String) map_data.get("NB_ClientName")),"Unable to enter Client Id.");
					reportStatus("Searching with "+client_Search_Pref+" =  <b>"+(String) map_data.get("NB_ClientName")+"</b> in search page ", "Info", true);
					break;
				case "Client Number" :
					customAssert.assertTrue(k.Input("CCF_NB_ClientId", (String) map_data.get("NB_ClientId")),"Unable to enter Client Id.");
					reportStatus("Searching with "+client_Search_Pref+" =  <b>"+(String) map_data.get("NB_ClientId")+"</b> in search page ", "Info", true);
					break;
				case "Company Registration" :
					customAssert.assertTrue(k.Input("CCF_NB_CompanyReg", (String) map_data.get("NB_CompanyReg")),"Unable to enter Client Id.");
					reportStatus("Searching with "+client_Search_Pref+" =  <b>"+(String) map_data.get("NB_ClientId")+"</b> in search page ", "Info", true);
					break;
				case "Legacy Client Number" :
					customAssert.assertTrue(k.Input("CCF_NB_LegacyClientNo", (String) map_data.get("NB_LegacyClientNo")),"Unable to enter Client Id.");
					reportStatus("Searching with "+client_Search_Pref+" =  <b>"+(String) map_data.get("NB_ClientId")+"</b> in search page ", "Info", true);
					break;
				case "Client Post Code" :
					customAssert.assertTrue(k.Input("CCF_NB_LegacyClientNo", (String) map_data.get("NB_LegacyClientNo")),"Unable to enter Client Id.");
					reportStatus("Searching with "+client_Search_Pref+" =  <b>"+(String) map_data.get("NB_ClientId")+"</b> in search page ", "Info", true);
					break;
				default:
					System.out.println("**** Given Client Search Preference is not valid ****");
					reportStatus("Given Client Search Preference is not valid ", "Info", false);
					break;
				
			}
			
			customAssert.assertTrue(k.Click("search_btn"), "Unable to click on search button.");

			boolean displayed = driver.findElement(By.xpath(".//*[@id='table0']/tbody/tr[1]/td[2]/a")).isDisplayed();
       		
       		if(!displayed){
       			reportStatus("Client Search Criterion doesn't return any values .", "Info", true);
       			retvalue=false;
       		}else{
       			reportStatus("Client Search result displayed correctly.", "Info", true);
       			customAssert.assertTrue(k.Click("client_name"), "Unable to click on Client Name.");
       		}
			

			return retvalue;

		} catch (Throwable t) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in " + methodName + " function");
			k.reportErr("Failed in " + methodName + " function", t);
			Assert.fail("Unable to Search client .  \n", t);
			return false;
		}
	}

	/**
	 * This method create the Client from Client details
	 */

	public boolean createClient(Map<Object, Object> map_data, String code, String event) {

		boolean retvalue = true;
		String testName = (String) map_data.get("Automation Key");
		try {

			String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
			// System.out.println(timeStamp);
			customAssert.assertTrue(k.Click("client_menu"), "Client Menu option does not exist.");
			customAssert.assertTrue(k.Click("client_add"), "Add Client button does not exist.");
			customAssert.assertTrue(k.Input("CCF_CC_CompanyName", "Auto_C_" + timeStamp),	"Unable to enter value in CompanyName field ");
			customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_CC_CompanyName", "value"),"Company Name Field Should Contain Valid Value on Client Details  .");
			customAssert.assertTrue(k.Input("CCF_CC_TradingName", (String)map_data.get("CC_TradingName")),	"Unable to enter value in Trading Name field .");
			customAssert.assertTrue(k.Input("CCF_Address_CC_Address", (String) map_data.get("CC_Address")),"Unable to enter value in Address field. ");
			customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_Address_CC_Address", "value"),"Address Field Should Contain Valid Value on Client Details .");
			customAssert.assertTrue(k.Input("CCF_Address_CC_line2", (String) map_data.get("CC_line2")),"Unable to enter value in Address field line 2 . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_line3", (String) map_data.get("CC_line3")),"Unable to enter value in Address field line 3 . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_Town", (String) map_data.get("CC_Town")),"Unable to enter value in Town field . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_County", (String) map_data.get("CC_County")),"Unable to enter value in County  . ");
			customAssert.assertTrue(k.Input("CCF_Address_CC_Postcode", (String) map_data.get("CC_Postcode")),"Unable to enter value in PostCode field .");
			customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_Address_CC_Postcode", "value"),"Postcode Field Should Contain Valid Value on Client Details .");
			customAssert.assertTrue(common.validatePostCode((String)map_data.get("CC_Postcode")),"Post Code is not in Correct format .");
			customAssert.assertTrue(k.DropDownType("CCF_Address_CC_Country", (String) map_data.get("CC_Country")),"Unable to select Country name from dropdown .");
			TestUtil.reportStatus("Client creation page with all data.", "Info", true);

			customAssert.assertTrue(k.Click("save_btn"), "Save button does not exist.");

			String client_name = k.getText("client_name_value");

			WriteDataToXl(code + "_" + event, "NB", testName, "NB_ClientName", client_name, map_data);

			reportStatus("Client <b>" + client_name + " </b> created successfully.", "Info", true);

			return retvalue;

		} catch (Throwable t) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in " + methodName + " function");
			k.reportErr("Failed in " + methodName + " function", t);
			Assert.fail("Unable to create Client \n", t);
			return false;
		}
	}

	public boolean createNewQuote(Map<Object, Object> map_data, String code, String event) {

		Boolean retvalue = true;
		df = new SimpleDateFormat("dd/MM/yyyy");
		String testName = (String) map_data.get("Automation Key");
		
		String quote_Start_Date = null,inception_date=null,deadline_date=null;
		String quote_End_Date = null;
		java.util.Date s_date=null,e_date=null;
		try{
	
		if(((String)map_data.get("QC_isDefaultQuoteDates")).equals("No")){
			quote_Start_Date = (String)map_data.get("QC_InceptionDate");
			quote_End_Date = (String)map_data.get("QC_DeadlineDate");
			try {
				s_date = df.parse(quote_Start_Date);
				e_date = df.parse(quote_End_Date);
			} catch (ParseException e) {
				System.out.println("Error while parsing quote dates . "+e);
			}
		
			inception_date = common.daysIncrementWithOutFormation(df.format(s_date), 0);
			deadline_date = common.daysIncrementWithOutFormation(df.format(e_date), 0);
		}else{
			inception_date = common.daysIncrementWithOutFormation(df.format(currentDate), 0);
			deadline_date = common.daysIncrementWithOutFormation(df.format(currentDate), 0);
			WriteDataToXl(code + "_" + event, "QuoteCreation", testName, "QC_InceptionDate", inception_date, map_data);
			WriteDataToXl(code + "_" + event, "QuoteCreation", testName, "QC_DeadlineDate", deadline_date, map_data);
		}
		}catch(NullPointerException npe){
			inception_date = common.daysIncrementWithOutFormation(df.format(currentDate), 0);
			deadline_date = common.daysIncrementWithOutFormation(df.format(currentDate), 0);
			WriteDataToXl(code + "_" + event, "QuoteCreation", testName, "QC_InceptionDate", inception_date, map_data);
			WriteDataToXl(code + "_" + event, "QuoteCreation", testName, "QC_DeadlineDate", deadline_date, map_data);
		}
		
		String cname_timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());

		try {
			
			if (map_data.get("CC_CreateClient").equals("Yes")) {
				
				customAssert.assertTrue(k.Click("new_quote"), "Unable to click on New quote link");
				customAssert.assertTrue(k.Click("inception_date"), "Unable to enter inception date.");
				customAssert.assertTrue(k.Input("inception_date", inception_date),"Unable to Enter inception date.");
				customAssert.assertTrue(k.Click("calander_btn"), "Unable to click on done button in calander.");
				customAssert.assertTrue(!k.getAttributeIsEmpty("inception_date", "value"),"inception_date Field Should Contain Valid Value on New Quote page .");
				customAssert.assertTrue(k.Click("deadline_date"), "Unable to enter deadline date.");
				customAssert.assertTrue(k.Input("deadline_date", deadline_date),"Unable to Enter deadline date.");
				customAssert.assertTrue(k.Click("calander_btn"), "Unable to click on done button in calander.");
				customAssert.assertTrue(!k.getAttributeIsEmpty("deadline_date", "value"),"deadline_date Field Should Contain Valid Value on New Quote page .");
				customAssert.assertTrue(k.Input("premium", Keys.chord(Keys.CONTROL, "a")),"Unable to select Premium field . ");
				customAssert.assertTrue(k.Input("premium", (String) map_data.get("QC_TargetPemium")),"Unable to enter Target Premium .");
				customAssert.assertTrue(k.DropDownSelection("product_dropdown", CommonFunction_GUS.product),
						"Unable to select Product_Name from dropdown .");
				k.WaitUntilClickable("agency_dropdown", 30);
				if (driver.findElement(By.xpath("//*[contains(@name,'agency')]")).isEnabled()) {
					if (Environment.contains("EVO")) {
						customAssert.assertTrue(k.DropDownType("agency_dropdown", "Broker One"),
								"Unable to select Agency name from dropdown.");
					} else {
						k.DropDownSelectionByIndex("agency_dropdown", 1);
					}

					String AgencyName = k.GetDropDownSelectedValue("agency_dropdown");
					WriteDataToXl(code + "_" + event, "QuoteCreation", testName, "QC_AgencyName", AgencyName, map_data);
					
					k.WaitUntilClickable("broker_dropdown", 30);
					
					if (driver.findElement(By.xpath("//*[contains(@name,'broker')]")).isEnabled()) {
						if (Environment.contains("EVO")) {
							Assert.assertTrue(k.DropDownSelection("broker_dropdown", (String) map_data.get("Broker_Name")),
									"Unable to select Broker name from dropdown.");
						} else {
							k.DropDownSelectionByIndex("broker_dropdown", 1);
						}
						k.waitTwoSeconds();
						String BrokerName = k.GetDropDownSelectedValue("broker_dropdown");
						WriteDataToXl(code + "_" + event, "QuoteCreation", testName, "QC_BrokerName", BrokerName, map_data);
				
						TestUtil.reportStatus("Verifying all the entered value to create a new quote", "Info", true);
						customAssert.assertTrue(k.Click("create_btn"), "Unable to Click on Create button.");
						TestUtil.reportStatus("New quote has been created successfully.", "Info", true);
					} else {
						k.Click("quote_back_btn");
						customAssert.assertTrue(createNewQuote(map_data, code, event),
								"Create new quote function is having issue(S)");
					}
				} else {
					k.Click("quote_back_btn");
					customAssert.assertTrue(createNewQuote(map_data, code, event),
							"Create new quote function is having issue(S)");
				}

			} else {

				customAssert.assertTrue(k.Click("edit_btn"), "Unable to click on Edit button.");
				k.waitTwoSeconds();
				customAssert.assertTrue(k.Input("CCF_CC_CompanyName", Keys.chord(Keys.CONTROL, "a")),"Unable to select Company name field");
				String client_name = "Auto_C_" + cname_timeStamp;
				customAssert.assertTrue(k.Input("CCF_CC_CompanyName", client_name), "Unable to enter value in CompanyName field ");
				customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_CC_CompanyName", "value"),"Company Name Field Should Contain Valid Value on Client Details  .");
				customAssert.assertTrue(k.Input("CCF_CC_TradingName", Keys.chord(Keys.CONTROL, "a")),"Unable to select Company name field");
				customAssert.assertTrue(k.Input("CCF_CC_TradingName", (String)map_data.get("CC_TradingName")),	"Unable to enter value in Trading Name field .");
				customAssert.assertTrue(k.Input("CCF_Address_CC_Address", Keys.chord(Keys.CONTROL, "a")),"Unable to select Address field");
				customAssert.assertTrue(k.Input("CCF_Address_CC_Address", (String) map_data.get("CC_Address")),"Unable to enter value in Address field. ");
				customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_Address_CC_Address", "value"),"Address Field Should Contain Valid Value on Client Details .");
				customAssert.assertTrue(k.Input("CCF_Address_CC_line2", Keys.chord(Keys.CONTROL, "a")),"Unable to select Line 2 field");
				customAssert.assertTrue(k.Input("CCF_Address_CC_line2", (String) map_data.get("CC_line2")),"Unable to enter value in Address field line 2 . ");
				customAssert.assertTrue(k.Input("CCF_Address_CC_line3", Keys.chord(Keys.CONTROL, "a")),"Unable to select Line 3 field");
				customAssert.assertTrue(k.Input("CCF_Address_CC_line3", (String) map_data.get("CC_line3")),"Unable to enter value in Address field line 3 . ");
				customAssert.assertTrue(k.Input("CCF_Address_CC_Town", Keys.chord(Keys.CONTROL, "a")),"Unable to select Town field");
				customAssert.assertTrue(k.Input("CCF_Address_CC_Town", (String) map_data.get("CC_Town")),"Unable to enter value in Town field . ");
				customAssert.assertTrue(k.Input("CCF_Address_CC_County", Keys.chord(Keys.CONTROL, "a")),"Unable to select County field");
				customAssert.assertTrue(k.Input("CCF_Address_CC_County", (String) map_data.get("CC_County")),"Unable to enter value in County  . ");
				customAssert.assertTrue(k.Input("CCF_Address_CC_Postcode", Keys.chord(Keys.CONTROL, "a")),"Unable to select Post Code field");
				customAssert.assertTrue(k.Input("CCF_Address_CC_Postcode", (String) map_data.get("CC_Postcode")),"Unable to enter value in PostCode field .");
				customAssert.assertTrue(!k.getAttributeIsEmpty("CCF_Address_CC_Postcode", "value"),"Postcode Field Should Contain Valid Value on Client Details .");
				customAssert.assertTrue(common.validatePostCode((String)map_data.get("CC_Postcode")),"Post Code is not in Correct format .");
				customAssert.assertTrue(k.DropDownType("CCF_Address_CC_Country", (String) map_data.get("CC_Country")),"Unable to select Country name from dropdown .");

				WriteDataToXl(code + "_" + event, "NB", testName, "NB_ClientName", client_name, map_data);
				
				customAssert.assertTrue(k.Click("save_btn"), "Unable to click on Save button.");
				customAssert.assertTrue(k.Click("new_quote"), "Unable to click on New quote link");
		
				customAssert.assertTrue(k.Click("inception_date"), "Unable to enter inception date.");
				customAssert.assertTrue(k.Input("inception_date", inception_date),"Unable to Enter inception date.");
				customAssert.assertTrue(k.Click("calander_btn"), "Unable to click on done button in calander.");
				customAssert.assertTrue(k.Click("deadline_date"), "Unable to enter deadline date.");
				customAssert.assertTrue(k.Input("deadline_date", deadline_date),"Unable to Enter deadline date.");
				customAssert.assertTrue(k.Click("calander_btn"), "Unable to click on done button in calander.");
				customAssert.assertTrue(k.Input("premium", Keys.chord(Keys.CONTROL, "a")),"Unable to select Premium field . ");
				customAssert.assertTrue(k.Input("premium", (String) map_data.get("QC_TargetPemium")),"Unable to enter Target Premium .");
				customAssert.assertTrue(k.DropDownSelection("product_dropdown", CommonFunction_GUS.product),
						"Unable to select Product_Name from dropdown.");
				k.WaitUntilClickable("agency_dropdown", 30);
				if (driver.findElement(By.xpath("//*[contains(@name,'agency')]")).isEnabled()) {
					if (Environment.contains("EVO")) {
						Assert.assertTrue(k.DropDownType("agency_dropdown", "Broker One"),
								"Unable to select Agency name from dropdown.");
					} else {
						k.DropDownSelectionByIndex("agency_dropdown", 1);
					}
					String AgencyName = k.GetDropDownSelectedValue("agency_dropdown");

					WriteDataToXl(code + "_" + event, "QuoteCreation", testName, "QC_AgencyName", AgencyName, map_data);
				
					k.WaitUntilClickable("broker_dropdown", 30);
					if (driver.findElement(By.xpath("//*[contains(@name,'broker')]")).isEnabled()) {
						if (Environment.contains("EVO")) {
							Assert.assertTrue(k.DropDownSelection("broker_dropdown", (String) map_data.get("Broker_Name")),
									"Unable to select Broker name from dropdown.");
						} else {
							k.DropDownSelectionByIndex("broker_dropdown", 1);
						}
						String BrokerName = k.GetDropDownSelectedValue("broker_dropdown");

						WriteDataToXl(code + "_" + event, "QuoteCreation", testName, "QC_BrokerName", BrokerName, map_data);
						
						TestUtil.reportStatus("Verifying all the entered value to create a new quote", "Info", true);
						customAssert.assertTrue(k.Click("create_btn"), "Unable to Click on Create button.");
						TestUtil.reportStatus("New quote has been created successfully.", "Info", true);
					} else {
						k.Click("quote_back_btn");
						Assert.assertTrue(createNewQuote(map_data, code, event),"Create new quote function is having issue(S)");
					}
				} else {
					k.Click("quote_back_btn");
					Assert.assertTrue(createNewQuote(map_data, code, event),"Create new quote function is having issue(S)");
				}

			}

			return retvalue;

		} catch (Throwable t) {
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in " + methodName + " function");
			k.reportErr("Failed in " + methodName + " function", t);
			Assert.fail("Unable to click on create - > new quote \n", t);
			return false;
		}
	}

	/**
   	 * This method verify quote number and then selects the same to proceed further.
   	 */
   	  
   	       public boolean selectLatestQuote(Map<Object, Object> map_data, String code, String event){
   		    	boolean retvalue = true;
   		    	String testName = (String) map_data.get("Automation Key");
   			try{
   				
   				WebElement table = Keywords.getObject("table");
   				List<WebElement> list_row = table.findElements(By.tagName("tr"));
   				int total_row = list_row.size();
   				
   				for(int l=1;l<=total_row;l++){
   					driver.findElement(By.xpath("html/body")).sendKeys(Keys.PAGE_DOWN);
   					String client_name_val = driver.findElement(By.xpath("//*[@id='table0']/tbody/tr["+l+"]/td[2]")).getText();
   					String client_name = k.getText("client_name_value");
   						if(client_name.equalsIgnoreCase(client_name_val)){
   							String QuoteNumber = driver.findElement(By.xpath("//*[@id='table0']/tbody/tr["+l+"]/td[1]/a")).getText();
   							WriteDataToXl(code + "_" + event, "NB", testName, "NB_QuoteNumber", QuoteNumber, map_data);
   							driver.findElement(By.xpath("//*[@id='table0']/tbody/tr["+l+"]/td[1]/a")).click();
   						    TestUtil.reportStatus("Created Quote <b>"+QuoteNumber+ "</b> has been Selected for Policy Creation .", "Info", true);
   							}
   				}
   				if(((String)common.NB_excel_data_map.get("Automation Key")).contains("SPI")){
   					customAssert.assertTrue(common.funcPageNavigation("Policy General", ""),"Unable to Navigate to Policy General After selection of quote .");
   				}else if(((String)common.NB_excel_data_map.get("Automation Key")).contains("XOE")){
   					customAssert.assertTrue(common.funcPageNavigation("General", ""),"Unable to Navigate to General After selection of quote .");
   				}else{
   					customAssert.assertTrue(common.funcPageNavigation("Policy Details", ""),"Unable to Navigate to Policy Details After selection of quote .");
   				}
   				customAssert.assertTrue(selectClientId(map_data,code,event,testName),"Unable to capture / Write client ID .");
   					
   			 } catch(Throwable t) {
              	  String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
                    TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
                    k.reportErr("Failed in "+methodName+" function", t);
                    Assert.fail("Unable to select the Created Quote .  \n", t);
                    return false;
             }
			return retvalue;
   	       }
   	       
   	       	/**
		      * This method is to select Client Id and same will be written in excel file.  
		      */
		       
		       public boolean selectClientId(Map<Object, Object> map_data,String code, String event,String testName){
		    	   boolean retvalue = true;
		    	   try{
		     		   String client_id = k.getText("client_id_selection");
		    		   WriteDataToXl(code + "_" + event, "NB", testName, "NB_ClientId", client_id, map_data);
		    		   TestUtil.reportStatus("Client Id has been selected successfully.", "Info", false);
		    		   
		              } catch(Throwable t) {
		            	  String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		                  TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
		                  k.reportErr("Failed in "+methodName+" function", t);
		                  Assert.fail("Failed to capture / Write client ID\n", t);
		                  return false;
		           }
		    	   return retvalue;
    	       }
			       
		       public boolean funcPageNavigation(String HeaderName,String btnName){
		           boolean retValue = true;
		           
		           if (!(HeaderName=="")){
		                 String strHeader= k.getText("Page_Header");
		                  if(strHeader.contentEquals(HeaderName)==false){
		                 	 TestUtil.reportStatus("Expected page <b>" +HeaderName+"</b> was unable to load, Actual Page displayed is -<b>"+strHeader+"</b>","fail",true);
		             	     return false;
		                  }else {
		                         TestUtil.reportStatus("Expected page <b>" +HeaderName+"</b> is successfully loaded","info",true);
		                        
		                  }
		           }
		          
		           if (!(btnName=="")){
		                 if(btnName.equalsIgnoreCase("Next")){
		                         TestUtil.reportStatus("Clicked on Next Button","info",false);
		                         k.Click("nextBtn");
		                         
		                                      
		                  }else if(btnName.equalsIgnoreCase("Back")){
		                         TestUtil.reportStatus("Clicked on Back Button","info",false);
		                         k.Click("backBtn");
		                  }else if(btnName.equalsIgnoreCase("Quote")){
		                         TestUtil.reportStatus("Clicked on Quote Button", "info", false);
		                         k.Click("quoteBtn");
		                         }
		                  else if(btnName.equalsIgnoreCase("Proceed")){
		                      TestUtil.reportStatus("Clicked on Proceed Button", "info", false);
		                      k.Click("proceedBtn");
		                      }
		                  else if(btnName.equalsIgnoreCase("Go On Cover")){
		                      TestUtil.reportStatus("Clicked on 'Go On Cover' Button", "info", false);
		                      k.Click("goOnCoverBtn");
		                  }
		                  //Draft Documents
		                  else if(btnName.equalsIgnoreCase("Draft Documents")){
		                      TestUtil.reportStatus("Clicked on 'Go On Cover' Button", "info", false);
		                      k.Click("draftDocBtn");
		                  }
		                 //Save Button
		                  else if(btnName.equalsIgnoreCase("Save")){
		                      TestUtil.reportStatus("Clicked on 'Save' Button", "info", false);
		                      k.Click("saveBtn");
		                  }
		                  else if(btnName.equalsIgnoreCase("Set to Quoted")){
		                      TestUtil.reportStatus("Clicked on 'Set to Quoted' Button", "info", false);
		                      k.Click("setToQuoteBtn");
		                  }
		 				 else if(btnName.equalsIgnoreCase("Assign Underwriter")){
		                      TestUtil.reportStatus("Clicked on 'Assign Underwriter' Button", "info", false);
		                      k.Click("assignUWBtn");
		                  }
		 				 else if(btnName.equalsIgnoreCase("Add Property")){
		                      TestUtil.reportStatus("Clicked on 'Add Property' Button", "info", false);
		                      k.Click("IP_AddProp");
		                  }
		 				 else if(btnName.equalsIgnoreCase("Create Endorsement")){
		                      TestUtil.reportStatus("Clicked on 'Create Endorsement' Button", "info", false);
		                      k.Click("createEndor");
		                  }
		 				 else if(btnName.equalsIgnoreCase("Submit Endorsement")){
		                      TestUtil.reportStatus("Clicked on 'Submit Endorsement' Button", "info", false);
		                      k.Click("submitEndorbtn");
		                  }
		 				 else if(btnName.equalsIgnoreCase("Decline")){
		                      TestUtil.reportStatus("Clicked on 'Decline' Button", "info", false);
		                      k.Click("CPA_decline_btn");
		                  }
		 				 else if(btnName.equalsIgnoreCase("Send to Underwriter")){
		                      TestUtil.reportStatus("Clicked on 'Decline' Button", "info", false);
		                      k.Click("sendUWbtn");
		                  }else if(btnName.equalsIgnoreCase("NTU")){
		                      TestUtil.reportStatus("Clicked on 'Decline' Button", "info", false);
		                      k.Click("CPA_NTU_reneawl_btn");
		                  }else if(btnName.equalsIgnoreCase("Documents")){
		                      TestUtil.reportStatus("Clicked on 'Documents' Button", "info", false);
		                      k.Click("docs_btn");
		                  }else if(btnName.equalsIgnoreCase("Accept Material Facts")){
		                      TestUtil.reportStatus("Clicked on 'Documents' Button", "info", false);
		                      k.Click("accept_material_facts_btn");
		                  
		                  }else if(btnName.equalsIgnoreCase("Put Endorsement On Cover")){
		               TestUtil.reportStatus("Clicked on 'Put Endorsement On Cover' Button", "info", false);
		               k.Click("ccd_endorsementONCover");
		                  }
		 			 else if(btnName.equalsIgnoreCase("Rewind")){
		               TestUtil.reportStatus("Clicked on 'Rewind' Button", "info", false);
		               k.Click("ccd_rwndBtn");
		           }
		 			 else if(btnName.equalsIgnoreCase("Rewind Endorsement")){
		               TestUtil.reportStatus("Clicked on 'Rewind Endorsement' Button", "info", false);
		               k.Click("ccd_rwndEndorsementBtn");
		           }
		 			 else if(btnName.equalsIgnoreCase("Put Rewind On Cover")){
		               TestUtil.reportStatus("Clicked on 'Put Rewind On Cover' Button", "info", false);
		               k.Click("ccd_rwndOnCoverBtn");
		           }

		           else {
		                  TestUtil.reportStatus("Unable to parse Button"+btnName+" name on page " +HeaderName,"fail",true);
		                  return false;
		           }
		    }
				return retValue;
		       }
		       
		     //Menu and submenu selection : 
	       public boolean funcMenuSelection(String menuName,String subMenuName) throws AWTException{
		       	   Boolean retvalue = true;
		       	   
		       	   java.util.List<WebElement> list = driver.findElements(By.xpath("//*[@id='nb-menu']/ul/li"));
		       	   String first_part = "//a[contains(text(),'";
		       	   String second_part = "')]";
		       	   String menuxpath = first_part+subMenuName+second_part;
		       	   
		       	   for(int i=0;i<list.size();i++){
		       	          String name = list.get(i).getText();
		       	          
		       	          if(subMenuName==""){
		       	                 if(name.equalsIgnoreCase(menuName)){
		       	                       list.get(i).click();
		       	                       //list = driver.findElements(By.xpath("//*[@id='nb-menu']/ul/li"));
		       	                       TestUtil.reportStatus("Clicked on [<b>  "+menuName+" </b>]  button.", "Info", false);
		       	                       break;
		       	                 }
		       	          }else{
		       	                 if(name.equalsIgnoreCase(menuName)){
		       	                	   
		       	                	   Actions builder=new Actions(driver);
		       	               		   Point coordinate=list.get(i).getLocation();
		       	               		   Robot robot=new Robot();
		       	               		   builder.moveToElement(list.get(i), 5,5).build().perform();
		       	               		   builder.moveByOffset(1, 1).build().perform();
		       	               		   robot.mouseMove(coordinate.getX()+8,coordinate.getY()+60);
		       	               		   
		       	                	   list.get(i).click();
		       	                       k.waitTwoSeconds();
		       	                       WebDriverWait wait = new WebDriverWait(driver, 50); 
		       	                       WebElement menuItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(menuxpath)));  // until this submenu is found
		       	                       if(menuItem.isDisplayed()){
		       	                      	 //identify menu option from the resulting menu display and click
		       	                      	 WebElement menuOption = driver.findElement(By.xpath(menuxpath)); 
		       	                      	 menuOption.click(); 
		       	                      	 robot.mouseMove(500, 500);
		       	                      	 break;
		       	                       }else{
		       	                    	   funcMenuSelection(menuName,subMenuName);
		       	                       }
		       	                        	list = driver.findElements(By.xpath("//*[@id='nb-menu']/ul/li"));
		       	                 }
		       	          }
		       	          
		       	   }
		       	   return retvalue;

		       }

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
		// System.out.println("Original Date is : " + sdf.format(c1.getTime()));
		c1.add(Calendar.DATE, days);
		// System.out.println("New date is : " + sdf.format(c1.getTime()));

		return sdf.format(c1.getTime());
	}
	
	public Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
	
	/**
	 * This method selects the specified covers from cover Page
	 */
	public boolean selectCover(String coverNameWithLocator,Map<Object, Object> map_data){
		 
		 boolean result=true;
		 String c_locator = null;
		 String coverName = null;
		try{
				coverName = coverNameWithLocator.split("_")[0];	
				c_locator = coverNameWithLocator.split("_")[1];
				k.waitTwoSeconds();
				if(c_locator.equals("md")){
					if (!driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).isSelected()){
						driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).click();        
						TestUtil.reportStatus("Cover  <b>"+coverName+"</b> is selected ", "Info", false);
					}else{
						TestUtil.reportStatus("Cover: <b>"+coverName+"</b> is checked by default.", "Info", false);
					}
				}else if(c_locator.equals("PEL")){
					
				}else{
					if (!driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).isSelected()){
						JavascriptExecutor j_exe = (JavascriptExecutor) driver;
						j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")));
						driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).click();        
						TestUtil.reportStatus("Cover: <b>"+coverName+"</b> is selected ", "Info", false);
					}else{
						TestUtil.reportStatus("Cover: <b>"+coverName+"</b> is checked by default.", "Info", false);
					}
		 }
		}catch(Throwable t){
			
			System.out.println("Error while selecting Cover - "+t.getMessage());
			result=false;
		}
		return result;
	 }
	
	 
	 public boolean deSelectCovers(String coverNameWithLocator,Map<Object, Object> map_data){
		 boolean result=true;
		 String c_locator = null;
		 String coverName = null;
		 try{
				coverName = coverNameWithLocator.split("_")[0];	
				c_locator = coverNameWithLocator.split("_")[1];
				if(c_locator.equals("md")){
					if (driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).isSelected()){
						driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).click();  
						k.waitTwoSeconds();
			           	Assert.assertTrue(k.AcceptPopup(), "Unable to locate popup box.");
						TestUtil.reportStatus("Cover  <b>"+coverName+"</b> is Unchecked ", "Info", false);
					}else{
						TestUtil.reportStatus("Cover: <b>"+coverName+"</b> is already not selected.", "Info", false);
					}
				}else if(c_locator.equals("PEL")){
					
				}else{
					if (driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).isSelected()){
						JavascriptExecutor j_exe = (JavascriptExecutor) driver;
						j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")));
						driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).click();        
						k.waitTwoSeconds();
			           	Assert.assertTrue(k.AcceptPopup(), "Unable to locate popup box.");
						TestUtil.reportStatus("Cover  <b>"+coverName+"</b> is Unchecked ", "Info", false);
					}else{
						TestUtil.reportStatus("Cover: <b>"+coverName+"</b> is already not selected.", "Info", false);
					}
				}
			                        
				return result;
		           
		    } catch(Throwable t) {
		    	return false;
		}
	} 
	 
	 	/** 
		 * 
		 * Rewind Flow Covers Check and Uncheck.
		 * 
		 * 
		 */
		
	public boolean funcRewindCoversCheck(Map<Object, Object> map_data){
			//CoversDetails_data_list.clear();
		    boolean retvalue = true;
		    CoversDetails_data_list = new ArrayList<>();
		    try {
		    	if(CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
		    		customAssert.assertTrue(common.funcPageNavigation("Additional Covers", ""),"Cover page is having issue(S)");
		    	}else{
		    		customAssert.assertTrue(common.funcPageNavigation("Covers", ""),"Cover page is having issue(S)");
		    	}
		 	 
		     String all_cover = ObjectMap.properties.getProperty(CommonFunction_GUS.product+"_CD_AllCovers");
		 	 String[] split_all_covers = all_cover.split(",");
		 	 for(String coverWithLocator : split_all_covers){
		 		 String coverWithoutLocator = coverWithLocator.split("_")[0];
		 		 try{
		 			 if(((String)map_data.get("CD_Add_"+coverWithoutLocator)).equals("Yes")){
			 			CoversDetails_data_list.add(coverWithoutLocator);
			 			customAssert.assertTrue(selectCover(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
			 		}else if(((String)map_data.get("CD_Add_"+coverWithoutLocator)).equals("No")){
			 			customAssert.assertTrue(deSelectCovers(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
			 		}
		 		 }catch(Throwable tt){
		 			 System.out.println("Error while selecting Cover - "+coverWithoutLocator);
		 			 break;
		 			}
		 		}
		 	 
		 	  customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Covers Screen .");
		      TestUtil.reportStatus("All specified covers are selected successfully  .", "Info", true);
		      return retvalue;
		           
		    } catch(Throwable t) {
		    	return false;
		 }
	}
	public boolean funcMTACoversCheck(Map<Object, Object> map_data){
		CoversDetails_data_list.clear();
	    boolean retvalue = true;
	    CoversDetails_data_list = new ArrayList<>();
	    try {
	    	if(CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
	    		customAssert.assertTrue(common.funcPageNavigation("Additional Covers", ""),"Cover page is having issue(S)");
	    	}else{
	    		customAssert.assertTrue(common.funcPageNavigation("Covers", ""),"Cover page is having issue(S)");
	    	}
	 	 
	     String all_cover = ObjectMap.properties.getProperty(CommonFunction_GUS.product+"_CD_AllCovers");
	 	 String[] split_all_covers = all_cover.split(",");
	 	 for(String coverWithLocator : split_all_covers){
	 		 String coverWithoutLocator = coverWithLocator.split("_")[0];
	 		 try{
	 			 if(((String)map_data.get("CD_AR"+coverWithoutLocator)).equals("Yes")){
		 			CoversDetails_data_list.add(coverWithoutLocator);
		 			customAssert.assertTrue(selectCover(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
		 		}else if(((String)map_data.get("CD_AR"+coverWithoutLocator)).equals("No")){
		 			customAssert.assertTrue(deSelectCovers(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
		 		}
	 		 }catch(Throwable tt){
	 			 System.out.println("Error while selecting Cover - "+coverWithoutLocator);
	 			 break;
	 			}
	 		}
	 	 
	 	  customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Covers Screen .");
	      TestUtil.reportStatus("All specified covers are selected successfully  .", "Info", true);
	      return retvalue;
	           
	    } catch(Throwable t) {
	    	return false;
	 }
}

	public boolean funcRewindCoversUnCheck(Map<Object, Object> map_data){
		
	    boolean retvalue = true;
	    CoversDetails_data_list = new ArrayList<>();
	    try {
	    	if(CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
	    		customAssert.assertTrue(common.funcPageNavigation("Additional Covers", ""),"Cover page is having issue(S)");
	    	}else{
	    		customAssert.assertTrue(common.funcPageNavigation("Covers", ""),"Cover page is having issue(S)");
	    	}
	 	 
	     String all_cover = ObjectMap.properties.getProperty(CommonFunction_GUS.product+"_CD_AllCovers");
	 	 String[] split_all_covers = all_cover.split(",");
	 	 for(String coverWithLocator : split_all_covers){
	 		 String coverWithoutLocator = coverWithLocator.split("_")[0];
	 		 try{if(((String)map_data.get("CD_Remove_"+coverWithoutLocator)).equals("Yes")){
		 			CoversDetails_data_list.add(coverWithoutLocator);
		 			customAssert.assertTrue(deSelectCovers(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
		 		}else{
		 			continue;
		 		}
	 		 }catch(Throwable tt){
	 			 System.out.println("Error while unchecking Cover - "+coverWithoutLocator);
	 			 break;
	 			}
	 		}
	 	 
	 	  customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Covers Screen .");
	      TestUtil.reportStatus("All specified covers are selected successfully  .", "Info", true);
	      
	      return retvalue;
	           
	    } catch(Throwable t) {
	    	return false;
	 }
	}
	 
	/**
	 * This method selects the specified covers from cover Page
	 */
	    
	public boolean funcCovers(Map<Object, Object> map_data){
		
	    boolean retvalue = true;
	    CoversDetails_data_list = new ArrayList<>();
	    try {
	    	if(CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
	    		customAssert.assertTrue(common.funcPageNavigation("Additional Covers", ""),"Cover page is having issue(S)");
	    	}else{
	    		customAssert.assertTrue(common.funcPageNavigation("Covers", ""),"Cover page is having issue(S)");
	    	}
	 	 k.pressDownKeyonPage();
	 	 String all_cover = ObjectMap.properties.getProperty(CommonFunction_GUS.product+"_CD_AllCovers");
	 	 String[] split_all_covers = all_cover.split(",");
	 	 for(String coverWithLocator : split_all_covers){
	 		 String coverWithoutLocator = coverWithLocator.split("_")[0];
	 		 try{
	 		if(((String)map_data.get("CD_"+coverWithoutLocator)).equals("Yes")){
	 			CoversDetails_data_list.add(coverWithoutLocator);
	 			customAssert.assertTrue(selectCover(coverWithLocator,map_data), "Select covers function is having issue(S) . ");
	 		}else{
	 			continue;
	 		}
	 		 }catch(Throwable tt){
	 			 System.out.println("Error while selecting Cover - "+coverWithoutLocator);
	 			 break;
	 			}
	 		}
	 	 
	 	  customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Covers Screen .");
	      TestUtil.reportStatus("All specified covers are selected successfully  .", "Info", true);
	      return retvalue;
	           
	    } catch(Throwable t) {
	      String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
	        k.reportErr("Failed in "+methodName+" function", t);
	        Assert.fail("Unable to select specified covers . ", t);
	        return false;
	 }
	    }
	
	/**
	 * This method selects the specified Perils from Specified Perils Page
	 */
	 public boolean selectPerils(String perilNameWithLocator,Map<Object, Object> map_data){
		 
		 boolean result=true;
		 String p_locator = null;
		 String perilName = null;
		try{
				perilName = perilNameWithLocator.split("_")[0];	
				
				 if(perilName.contains("Malicious") || perilName.contains("AccidentalEscapeOfWater") ||  perilName.contains("PhysicalDamage")){
				 
					 p_locator = perilNameWithLocator.split("_")[1] + "_"+perilNameWithLocator.split("_")[2];
				 }else{
					 p_locator = perilNameWithLocator.split("_")[1];
				 }
				
				//k.waitTwoSeconds();
				if(perilName.equals("RCCSLOW") || perilName.contains("AttemptedTheft") || perilName.contains("FuelOil")){
					
					if (!driver.findElement(By.name(p_locator+"_selected_pcc")).isSelected()){
						driver.findElement(By.name(p_locator+"_selected_pcc")).click();        
						TestUtil.reportStatus("Peril: <b>"+perilName+"</b> is selected ", "Info", false);
					}else{
						TestUtil.reportStatus("Peril: <b>"+perilName+"</b> is checked by default.", "Info", false);
					}
				
				}else{
				
					if (!driver.findElement(By.name(p_locator+"_selected")).isSelected()){
						driver.findElement(By.name(p_locator+"_selected")).click();        
						TestUtil.reportStatus("Peril: <b>"+perilName+"</b> is selected ", "Info", false);
					}else{
						TestUtil.reportStatus("Peril: <b>"+perilName+"</b> is checked by default.", "Info", false);
					}
				}
		 
		}catch(Throwable t){
			
			System.out.println("Error while selecting Peril - "+t.getMessage());
			result=false;
		}
		return result;
	 }
	 
	 /**
		 * This method de-selects the specified Perils from Specified Perils Page
		 */
		 public boolean deSelectPerils(String perilNameWithLocator,Map<Object, Object> map_data){
			 
			 boolean result=true;
			 String p_locator = null;
			 String perilName = null;
			try{
					perilName = perilNameWithLocator.split("_")[0];	
					
					 if(perilName.contains("Malicious") || perilName.contains("AccidentalEscapeOfWater") ||  perilName.contains("PhysicalDamage")){
						 
						 p_locator = perilNameWithLocator.split("_")[1] + "_"+perilNameWithLocator.split("_")[2];
					 }else{
						 p_locator = perilNameWithLocator.split("_")[1];
					 }
					//k.waitTwoSeconds();
					 if(perilName.equals("RCCSLOW") || perilName.contains("AttemptedTheft") || perilName.contains("FuelOil")){
					
						if (driver.findElement(By.name(p_locator+"_selected_pcc")).isSelected()){
							driver.findElement(By.name(p_locator+"_selected_pcc")).click();        
							TestUtil.reportStatus("Peril: <b>"+perilName+"</b> is de-selected ", "Info", false);
						}else{
							TestUtil.reportStatus("Peril: <b>"+perilName+"</b> is un-checked by default.", "Info", false);
						}
					 }else{
						 if (driver.findElement(By.name(p_locator+"_selected")).isSelected()){
								driver.findElement(By.name(p_locator+"_selected")).click();        
								TestUtil.reportStatus("Peril: <b>"+perilName+"</b> is de-selected ", "Info", false);
							}else{
								TestUtil.reportStatus("Peril: <b>"+perilName+"</b> is un-checked by default.", "Info", false);
							}
					 }
					 
			 
			 
			}catch(Throwable t){
				
				System.out.println("Error while selecting Peril - "+t.getMessage());
				result=false;
			}
			return result;
		 }
	
	/**
	 * This method selects the specified perils from Specified Perils Page
	 */
	    
	public boolean funcSpecifiedPerils(Map<Object, Object> map_data){
		
	    boolean retvalue = true;
	    String perilWithoutLocator=null;
	  
	    try {
	    customAssert.assertTrue(common.funcPageNavigation("Specified Perils", ""),"Specified Perils page is having issue(S)");
	 	 k.pressDownKeyonPage();
	  	 String all_peril = ObjectMap.properties.getProperty(CommonFunction_GUS.product+"_SP_AllPerils");
	 	 String[] split_all_perils = all_peril.split(",");
	 	 for(String perilWithLocator : split_all_perils){
	 		 
	 		perilWithoutLocator = perilWithLocator.split("_")[0];
	 		
	 		try{
	 			if(((String)map_data.get("SP_"+perilWithoutLocator)).equals("Yes")){
	 				customAssert.assertTrue(selectPerils(perilWithLocator,map_data), "Select perils function is having issue(S) . ");
	 			}else{
	 				customAssert.assertTrue(deSelectPerils(perilWithLocator,map_data), "DeSelect perils function is having issue(S) . ");
	 				//continue;
	 			}
	 		 }catch(Throwable tt){
	 			 System.out.println("Error while selecting Peril - "+perilWithoutLocator);
	 			 break;
	 			}
	 		}
	 	 
	 	  customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Covers Screen .");
	      TestUtil.reportStatus("All specified Perils are selected successfully  .", "Info", true);
	      return retvalue;
	           
	    } catch(Throwable t) {
	      String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	        TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
	        k.reportErr("Failed in "+methodName+" function", t);
	        Assert.fail("Unable to select specified perils . ", t);
	        return false;
	 }
   }
	
	
	public static boolean PremiumSummarytableCalculation(Hashtable<String,String> tabledata,String section ){
		//MaterialDamage_nipt, MaterialDamage_gprem, MaterialDamage_nprem, MaterialDamage_gipt, MaterialDamage_comm,MaterialDamage_comr
		try{ 	
		if(section.equals("Total")){ return true;}
			DecimalFormat f = new DecimalFormat("00.00");
			String secName = section.replaceAll(" ", "");
			String cSection = null;
			double grossPrem = Double.parseDouble(tabledata.get("PS_"+secName+"_GP"));
			double commisn   = Double.parseDouble(tabledata.get("PS_"+secName+"_GC")); 
			double commRate  = Double.parseDouble(tabledata.get("PS_"+secName+"_CR"));
			double netPrem 	 = Double.parseDouble(tabledata.get("PS_"+secName+"_NP"));
			double grossIPT  = Double.parseDouble(tabledata.get("PS_"+secName+"_GT"));
			double netIPT    = Double.parseDouble(tabledata.get("PS_"+secName+"_NPIPT"));
			
			
			if(secName.contains("Frozen")){
				cSection = "FrozenFoods";
			}else if(secName.contains("Licence")){
				cSection = "LossOfLicence";
			}else if(secName.contains("Total")){
				cSection = "Total";
			}else{
				cSection = secName.replaceAll(" ", "");
			}
			
			double IPT =  Double.parseDouble(TestUtil.getStringfromMap("PS_"+cSection+"_IPT",""));
			TestUtil.reportStatus("Calculation for cover <b> "+section+"</b> as per IPT rate :"+IPT+" %", "Info", false);
			//System.out.println("\nCalculation Cover Name :"+section);// Added to the reporting & Logfile }
			//TestUtil.reportStatus("\nCalculation for Cover : <b>"+section, "Info", true);
			double denominator = (1.00-(commRate/100));
			double calcltdComm = (netPrem/denominator)*(commRate/100);
			//System.out.println("calculated Gross Commission :"+f.format(calcltdComm));// Added to the reporting & Logfile }
			TestUtil.reportStatus("Calculated Gross Commission :<b>"+f.format(calcltdComm), "Info", false);
			double calcltdGprem = calcltdComm + netPrem;
			//System.out.println("Calculated Gross Premium :"+f.format(calcltdGprem));// Added to the reporting & Logfile }
			TestUtil.reportStatus("Calculated Gross Premium :<b>"+f.format(calcltdGprem), "Info", false);
			double calcltdGIPT = calcltdGprem *(IPT/100);
			//System.out.println("Calculated Gross IPT :"+f.format(calcltdGIPT));// Added to the reporting & Logfile }
			TestUtil.reportStatus("Calculated Gross IPT :<b>"+f.format(calcltdGIPT), "Info", false);
			double calcltdNIPT = netPrem*(IPT/100);
			//System.out.println("Calculated Net IPT :"+f.format(calcltdNIPT));// Added to the reporting & Logfile }
			TestUtil.reportStatus("Calculated Net IPT :<b>"+f.format(calcltdNIPT), "Info", false);	
			customAssert.assertTrue(compareValues(calcltdGprem,grossPrem,"Gross Premium "),"Mismatched Gross Premium Values");
			customAssert.assertTrue(compareValues(calcltdComm,commisn,"Gross Commission "),"Mismatched Gross Commission Values");
			customAssert.assertTrue(compareValues(calcltdGIPT,grossIPT,"Gross IPT value "),"Mismatched Gross IPT Values");
			customAssert.assertTrue(compareValues(calcltdNIPT,netIPT,"Net IPT Values "),"Mimatched Net IPT Values");
			return true;
		}catch(Throwable t){
			return false;
		}
		}
	@SuppressWarnings("finally")
	public static boolean compareValues(double ExpectedValue, double ActualValue, String val) throws Exception{
		String eMsg="";
		boolean iret =true;
		try{
		
		 DecimalFormat f = new DecimalFormat("00.00");
		 String df1 = f.format(ExpectedValue);
		 String df2 = f.format(ActualValue);
		 double diffrence = Double.parseDouble(df1) - Double.parseDouble(df2);
		 if (diffrence<=5.05 && diffrence>=-5.05)
		 {	//System.out.println("Values have been matched for "+val+" Expected:"+df1+" with Actual value :"+df2);
			 String tMsg="Values have been matched for <b>"+val+"</b> Expected:<b>"+df1+" </b> with Actual value :<b>"+df2;
			 TestUtil.reportStatus(tMsg, "Pass", false);	 
		 // Added to the reporting & Logfile }
		 }
		 else
		 {  //System.out.println("Values have not been matched for "+val+" Expected:"+df1+" with Actual value :"+df2);
			 eMsg="Values have not been matched for <b>"+val+"</b> Expected:<b>"+df1+"</b> with Actual value :<b>"+df2; 
			 TestUtil.reportStatus(eMsg, "Fail", true);
			 throw new Exception(eMsg);
		// Added to the reporting & Logfile }
		 }
		}catch(Throwable t){
			iret = false;
			ErrorUtil.addVerificationFailure(t);
		}
		finally{
			return iret;}	 
	 }
	 
	 /**
	     * This method logouts from Stingray Application
	   */
	     
	     public boolean StingrayLogout(){
	            Boolean retvalue = true;
	            try {
	                 
	                   Assert.assertTrue(k.Click("Logout_Button"),"Unable to click Logout Button");
	                   TestUtil.reportStatus("User : "+loginUserName+" has been Logout.", "Info", true);
	                   return retvalue;
	                   
	            } catch(Throwable t) {
	          	  String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	                TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
	                Assert.fail("Unable to click on Logout Button \n", t);
	                return false;
	         }
	            }

	     public boolean funcStatusHandling(Map<Object, Object> map_data , String code , String event){
				
			 boolean ret_value = true;
			 String p_Status = null;
			 if(TestBase.businessEvent.equals("NB")){
			 	p_Status = (String)map_data.get("NB_Status");
			 }else if(TestBase.businessEvent.equals("MTA")){
				 if(common.currentRunningFlow.equals("NB")){
					 p_Status = (String)common.NB_excel_data_map.get("NB_Status");
				 }else{
					 p_Status = (String)common.MTA_excel_data_map.get("MTA_Status");
				 }
			 }else if(TestBase.businessEvent.equals("CAN"))
				 if(common.currentRunningFlow.equals("NB")){
					 p_Status = (String)common.NB_excel_data_map.get("NB_Status");
				 }else{
					 p_Status = (String)common.CAN_excel_data_map.get("CAN_Status");
				 }
				try{
					
					switch (p_Status) {
					case "Submitted":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,(String)map_data.get("NB_Status")), "Verify Policy Status (Submitted) function is having issue(S) . ");
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("NB_Status")+"  ]</b> status. ", "Info", true);
						
						break;
					case "Quoted":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Submitted"), "Verify Policy Status (Submitted) function is having issue(S) . ");
						Assert.assertTrue(common.funcQuoteCheck(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,(String)map_data.get("NB_Status")), "Verify Policy Status (Quoted) function is having issue(S) . ");
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Draft Documents"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("NB_Status")+"  ]</b> status. ", "Info", true);
						break;
					case "On Cover":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Submitted"), "Verify Policy Status (Submitted) function is having issue(S) . ");
						Assert.assertTrue(common.funcQuoteCheck(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Quoted"), "Verify Policy Status (Quoted) function is having issue(S) . ");
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Draft Documents"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.funcGoOnCover(common.NB_excel_data_map), "Go On Cover function is having issue(S) . ");
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						if(TestBase.product.equals("SPI")){
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover (Unconfirmed)"), "Verify Policy Status (On Cover (Unconfirmed)) function is having issue(S) . ");
							customAssert.assertTrue(common_SPI.func_Confirm_Policy(), "Error while changing SPI policy Status from Unconfirmed to Confirmed . ");
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,(String)map_data.get("NB_Status")), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}else{
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,(String)map_data.get("NB_Status")), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Documents"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.transactionSummary((String)common.NB_excel_data_map.get("Automation Key"), "", code, event), "Transaction Summary function is having issue(S) . ");
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("NB_Status")+"  ]</b> status. ", "Info", true);
						break;
					case "Declined":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Submitted"), "Verify Policy Status (Submitted) function is having issue(S) . ");
						Assert.assertTrue(common.funcDecline(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Declined"), "Verify Policy Status (Declined) function is having issue(S) . ");
						//funcVerifyDeclineNTUstatus
						customAssert.assertTrue(common.funcVerifyDeclineNTUstatus(common.NB_excel_data_map), "Verify Policy Status (Decline Page) function is having issue(S) . ");
						break;
						
					case "NTU":
						//Not Taken Up
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Submitted"), "Verify Policy Status (Submitted) function is having issue(S) . ");
						Assert.assertTrue(common.funcQuoteCheck(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Quoted"), "Verify Policy Status (Quoted) function is having issue(S) . ");
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Draft Documents"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						Assert.assertTrue(common.funcNTU(common.NB_excel_data_map));
						
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Not Taken Up"), "Verify Policy Status (NTU) function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyNTUstatus(common.NB_excel_data_map), "Verify Policy Status (NTU Page) function is having issue(S) . ");
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("NB_Status")+"  ]</b> status. ", "Info", true);
						break;
						
					case "Indicate":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Submitted"), "Verify Policy Status (Submitted) function is having issue(S) . ");
						// Indicate
						customAssert.assertTrue(funcButtonSelection("Indicate"),"Unable to click on Indicate button on premium summary page");
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Indicated"), "Verify Policy Status (Indicated) function is having issue(S) . ");
						// Indication Accept
						customAssert.assertTrue(funcButtonSelection("Indication Accept"),"Unable to click on Indication Accept button on premium summary page");
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Indication Accepted"), "Verify Policy Status (Indication Accepted) function is having issue(S) . ");
						//Quote Creation
						Assert.assertTrue(common.funcQuoteCheck(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Quoted"), "Verify Policy Status (Quoted) function is having issue(S) . ");
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Draft Documents"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.funcGoOnCover(common.NB_excel_data_map), "Go On Cover function is having issue(S) . ");
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						if(TestBase.product.equals("SPI")){
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover (Unconfirmed)"), "Verify Policy Status (On Cover (Unconfirmed)) function is having issue(S) . ");
							customAssert.assertTrue(common_SPI.func_Confirm_Policy(), "Error while changing SPI policy Status from Unconfirmed to Confirmed . ");
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}else{
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}	if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Documents"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						//customAssert.assertTrue(common.transactionSummary((String)common.NB_excel_data_map.get("Automation Key"), "", code, event), "Transaction Summary function is having issue(S) . ");
						TestUtil.reportStatus("Policy has been created with mentioned Status :<b>[ Submitted->Indicate->Indication Accepted->Quoted->On Cover ]</b>", "Info", true);
						
						break;
					case "Rewind":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Submitted"), "Verify Policy Status (Submitted) function is having issue(S) . ");
						Assert.assertTrue(common.funcQuoteCheck(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Quoted"), "Verify Policy Status (Quoted) function is having issue(S) . ");
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Draft Documents"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.funcGoOnCover(common.NB_excel_data_map), "Go On Cover function is having issue(S) . ");
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						if(TestBase.product.equals("SPI")){
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover (Unconfirmed)"), "Verify Policy Status (On Cover (Unconfirmed)) function is having issue(S) . ");
							customAssert.assertTrue(common_SPI.func_Confirm_Policy(), "Error while changing SPI policy Status from Unconfirmed to Confirmed . ");
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}else{
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Documents"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.transactionSummary((String)common.NB_excel_data_map.get("Automation Key"), "", code, event), "Transaction Summary function is having issue(S) . ");
						customAssert.assertTrue(common.funcMenuSelection("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
						customAssert.assertTrue(common.funcRewind());
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Submitted (Rewind)"), "Verify Policy Status (Submitted (Rewind)) function is having issue(S) . ");
						customAssert.assertTrue(common.decideRewindMethod());
						customAssert.assertTrue(common.transactionSummary((String)common.NB_excel_data_map.get("Automation Key"), "", CommonFunction_GUS.product,CommonFunction_GUS.businessEvent), "Transaction Summary function is having issue(S) after Rewind  . ");
						
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("NB_Status")+"  ]</b> status. ", "Info", true);
						break;
					case "Cancelled":
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.CAN_excel_data_map,code,event,"Cancelled"), "Verify Policy Status (Cancelled) function is having issue(S) . ");
						customAssert.assertTrue(common.transactionSummary((String)common.CAN_excel_data_map.get("Automation Key"), "", code, event), "Transaction Summary function is having issue(S) . ");
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)common.CAN_excel_data_map.get("CAN_Status")+"  ]</b> status. ", "Info", true);
						break;
						
					case "Endorsement Submitted":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Submitted"), "Verify Policy Status (Endorsement Submitted) function is having issue(S) . ");
						if(TestBase.product.equals("SPI")){
							customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"),"Unable to Click on Assign Underwriter button . ");
							customAssert.assertTrue(common_SPI.funcAssignPolicyToUW(), "Error while assigning Policy to Underwriter.");
						}
						
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("MTA_Status")+"  ]</b> status. ", "Info", true);
						break;
						
					case "Endorsement Quoted":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Submitted"), "Verify Policy Status (Endorsement Submitted) function is having issue(S) . ");
						if(TestBase.product.equals("SPI")|| TestBase.product.equals("CCF")){
							customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"),"Unable to Click on Assign Underwriter button . ");
							customAssert.assertTrue(common_SPI.funcAssignPolicyToUW(), "Error while assigning Policy to Underwriter.");
						}
						Assert.assertTrue(common.funcQuoteCheck(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Quoted"), "Verify Policy Status (Endorsement Quoted) function is having issue(S) . ");
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Draft Documents"), "Verify Policy Status (Endorsement Quoted) function is having issue(S) . ");
						}
						
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("MTA_Status")+"  ]</b> status. ", "Info", true);
						break;
						
					case "Endorsement On Cover":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Submitted"), "Verify Policy Status (Endorsement Submitted) function is having issue(S) . ");
						if(TestBase.product.equals("SPI")||TestBase.product.equals("CCF")){
							customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"),"Unable to Click on Assign Underwriter button . ");
							customAssert.assertTrue(common_SPI.funcAssignPolicyToUW(), "Error while assigning Policy to Underwriter.");
						}
						Assert.assertTrue(common.funcQuoteCheck(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Quoted"), "Verify Policy Status (Endorsement Quoted) function is having issue(S) . ");
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Draft Documents"), "Error in PDF Verification (Draft Documents) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.funcGoOnCover_Endorsement(common.NB_excel_data_map), "GoOnCover_Endorsement function is having issue(S) . ");
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						if(TestBase.product.equals("SPI")){
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement On Cover (Unconfirmed)"), "Verify Policy Status (Endorsement On Cover  (Unconfirmed)) function is having issue(S) . ");
							customAssert.assertTrue(common_SPI.func_Confirm_Policy(), "Error while changing SPI policy Status from Unconfirmed to Confirmed . ");
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,(String)map_data.get("MTA_Status")), "Verify Policy Status (Endorsement On Cover) function is having issue(S) . ");
						}else{
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,(String)map_data.get("MTA_Status")), "Verify Policy Status (Endorsement On Cover) function is having issue(S) . ");
						}
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Documents"), "Error in PDF Verification (Documents)(Endorsement On Cover) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.transactionSummary((String)common.MTA_excel_data_map.get("Automation Key"), "", code, event), "MTA Transaction Summary function is having issue(S) . ");
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("MTA_Status")+"  ]</b> status. ", "Info", true);
						break;
						
					case "Endorsement Declined":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Submitted"), "Verify Policy Status (Endorsement Submitted) function is having issue(S) . ");
						if(TestBase.product.equals("SPI")){
							customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"),"Unable to Click on Assign Underwriter button . ");
							customAssert.assertTrue(common_SPI.funcAssignPolicyToUW(), "Error while assigning Policy to Underwriter.");
						}
						Assert.assertTrue(common.funcDecline(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						//funcVerifyDeclineNTUstatus
						//customAssert.assertTrue(common.funcVerifyDeclineNTUstatus(common.NB_excel_data_map), "Verify Policy Status (Decline Page) function is having issue(S) . ");
					
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("MTA_Status")+"  ]</b> status. ", "Info", true);
						break;
						
					case "Endorsement NTU":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Submitted"), "Verify Policy Status (Endorsement Submitted) function is having issue(S) . ");
						if(TestBase.product.equals("SPI") || TestBase.product.equals("CCF")){
							customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"),"Unable to Click on Assign Underwriter button . ");
							customAssert.assertTrue(common_SPI.funcAssignPolicyToUW(), "Error while assigning Policy to Underwriter.");
						}
						Assert.assertTrue(common.funcQuoteCheck(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Quoted"), "Verify Policy Status (Endorsement Quoted) function is having issue(S) . ");
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Draft Documents"), "Error in PDF Verification (Draft Documents) function is having issue(S) . ");
						}
						Assert.assertTrue(common.funcNTU(common.NB_excel_data_map));
						
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover"), "Verify Policy Status (On Cover) function is having issue(S) . ");
						//customAssert.assertTrue(common.funcVerifyNTUstatus(common.NB_excel_data_map), "Verify Policy Status (NTU Page) function is having issue(S) . ");
								TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("MTA_Status")+"  ]</b> status. ", "Info", true);
						break;
						
					case "Endorsement Rewind":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Submitted"), "Verify Policy Status (Endorsement Submitted) function is having issue(S) . ");
						if(TestBase.product.equals("SPI")||TestBase.product.equals("CCF")){
							customAssert.assertTrue(common.funcMenuSelection("Navigate", "Premium Summary"), "Navigation problem to Transaction Summary page .");
							customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"),"Unable to Click on Assign Underwriter button . ");
							customAssert.assertTrue(common_SPI.funcAssignPolicyToUW(), "Error while assigning Policy to Underwriter.");
						}
						Assert.assertTrue(common.funcQuoteCheck(common.NB_excel_data_map));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Quoted"), "Verify Policy Status (Endorsement Quoted) function is having issue(S) . ");
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Draft Documents"), "Error in PDF Verification (Draft Documents) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.funcGoOnCover_Endorsement(common.NB_excel_data_map), "GoOnCover_Endorsement function is having issue(S) . ");
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						if(TestBase.product.equals("SPI")){
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement On Cover (Unconfirmed)"), "Verify Policy Status (Endorsement On Cover  (Unconfirmed)) function is having issue(S) . ");
							customAssert.assertTrue(common_SPI.func_Confirm_Policy(), "Error while changing SPI policy Status from Unconfirmed to Confirmed . ");
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement On Cover"), "Verify Policy Status (Endorsement On Cover) function is having issue(S) . ");
						}else{
							customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement On Cover"), "Verify Policy Status (Endorsement On Cover) function is having issue(S) . ");
						}
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Documents"), "Error in PDF Verification (Documents)(Endorsement On Cover) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.transactionSummary((String)common.MTA_excel_data_map.get("Automation Key"), "", code, event), "MTA Transaction Summary function is having issue(S) . ");
						
						customAssert.assertTrue(common.funcMenuSelection("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
						customAssert.assertTrue(common.funcRewind());
						customAssert.assertTrue(common.funcMenuSelection("Policies", ""));
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Submitted (Rewind)"), "Verify Policy Status (Endorsement Submitted (Rewind)) function is having issue(S) . ");
						customAssert.assertTrue(common.decideRewindMethod());
						if(!CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							customAssert.assertTrue(funcPDFdocumentVerification("Documents"), "Error in PDF Verification (Documents)(Endorsement On Cover after MTA Rewind) function is having issue(S) . ");
						}
						customAssert.assertTrue(common.transactionSummary((String)common.MTA_excel_data_map.get("Automation Key"), "", CommonFunction_GUS.product,CommonFunction_GUS.businessEvent), "Transaction Summary function is having issue(S) after MTA Rewind  . ");
						
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("MTA_Status")+"  ]</b> status. ", "Info", true);
						break;
						
					case "Endorsement Discard":
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"Endorsement Submitted"), "Verify Policy Status (Endorsement Submitted) function is having issue(S) . ");
						if(TestBase.product.equals("SPI") || TestBase.product.equals("CCF")){
							customAssert.assertTrue(common.funcButtonSelection("Assign Underwriter"),"Unable to Click on Assign Underwriter button . ");
							customAssert.assertTrue(common_SPI.funcAssignPolicyToUW(), "Error while assigning Policy to Underwriter.");
						}
						
						Assert.assertTrue(common.funcDiscardMTA(common.MTA_excel_data_map));
						
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.NB_excel_data_map,code,event,"On Cover"), "Verify Policy Status (On Cover) function is having issue(S) . ");
					
						
						TestUtil.reportStatus("Current Flow is restricted to <b>[  "+(String)map_data.get("MTA_Status")+"  ]</b> status. ", "Info", true);
						break;
					case "Reinstate":
						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.CAN_excel_data_map,code,event,"Cancelled"), "Verify Policy Status (Cancelled) function is having issue(S) . ");
						customAssert.assertTrue(common.transactionSummary((String)common.CAN_excel_data_map.get("Automation Key"), "", code, event), "Transaction Summary function is having issue(S) . ");
						// Reinstate Function
						customAssert.assertTrue(common.ReinstatePolicy(common.NB_excel_data_map));
//						customAssert.assertTrue(common.funcSearchPolicy(common.NB_excel_data_map), "Policy Search function is having issue(S) . ");
//						customAssert.assertTrue(common.funcVerifyPolicyStatus(common.CAN_excel_data_map,code,event,"On Cover"), "Verify Policy Status (Re-Instate) function is having issue(S) . ");
					
						// Cancellation CODE will appear over here...
						
						TestUtil.reportStatus("Current Flow is for <b> 'Reinstate' the Cancelled policy", "Info", true);
						break;
					default:
						break;
					}
				}catch(Throwable t){
					ret_value = false;;
				}
				
				return ret_value;
			}
	 
	     public boolean funcQuoteCheck(Map<Object, Object> map_data){
				
			 boolean retvalue = true;
			 int counter = 0;
				try {     
					
					customAssert.assertTrue(k.Click("Quote_btn"),"Unable to click on Quote button.");
					
					switch (CommonFunction_GUS.product) {
					case "POB":
						customAssert.assertTrue(common.funcPageNavigation("Quote Check",""), "Quote Check page does not open . ");
						
						if(!((String)common.NB_excel_data_map.get("PD_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
							TestUtil.reportStatus("Policy Trade Code has not been selected. ", "FAIL", true);
							counter++;
						}
						break;
					case "XOE":
						if(((String)common.NB_excel_data_map.get("CD_Terrorism")).equalsIgnoreCase("Yes")){
							customAssert.assertTrue(common.funcPageNavigation("Policy Check",""), "Policy Check page does not open . ");
							
							if(!((String)common.NB_excel_data_map.get("PD_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
								TestUtil.reportStatus("Policy Trade Code has not been selected. ", "FAIL", true);
								counter++;
							}
						}
						break;
					case "POC":
						customAssert.assertTrue(common.funcPageNavigation("Quote Check",""), "Quote Check page does not open . ");
						
						if(!((String)common.NB_excel_data_map.get("PD_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
							TestUtil.reportStatus("Policy Trade Code has not been selected. ", "FAIL", true);
							counter++;
						}
						break;
					case "CCF":
						customAssert.assertTrue(common.funcPageNavigation("Quote Check",""), "Quote Check page does not open . ");
						
						if(((String)common.NB_excel_data_map.get("PD_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
							if(((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes")){
								int noOfProperties = 0;
								if(common.no_of_inner_data_sets.get("Property Details")==null){
									noOfProperties = 0;
								}else{
									noOfProperties = common.no_of_inner_data_sets.get("Property Details");
								}
								
								if(noOfProperties==0){
									TestUtil.reportStatus("Material Damage Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
							if(((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes")){
								if(!((String)common.NB_excel_data_map.get("CAR_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
									TestUtil.reportStatus("Contractors All Risks Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
						}else{
							
							if(((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes")){
								int noOfProperties = 0;
								if(common.no_of_inner_data_sets.get("Property Details")==null){
									noOfProperties = 0;
								}else{
									noOfProperties = common.no_of_inner_data_sets.get("Property Details");
								}
								
								if(noOfProperties==0){
									TestUtil.reportStatus("Material Damage Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
							
							if(((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes")){
								if(((String)common.NB_excel_data_map.get("CAR_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
								}else{
									TestUtil.reportStatus("Contractors All Risks Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
							
							TestUtil.reportStatus("Policy Trade Code has not been selected. ", "FAIL", true);
							counter++;
						}
						
						break;
					case "CTA":
						customAssert.assertTrue(common.funcPageNavigation("Quote Check",""), "Quote Check page does not open . ");
						
						if(((String)common.NB_excel_data_map.get("PD_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
							if(((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes")){
								int noOfProperties = 0;
								if(common.no_of_inner_data_sets.get("Property Details")==null){
									noOfProperties = 0;
								}else{
									noOfProperties = common.no_of_inner_data_sets.get("Property Details");
								}
								
								if(noOfProperties==0){
									TestUtil.reportStatus("Material Damage Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
							if(((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes")){
								if(!((String)common.NB_excel_data_map.get("CAR_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
									TestUtil.reportStatus("Contractors All Risks Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
						}else{
							
							if(((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes")){
								int noOfProperties = 0;
								if(common.no_of_inner_data_sets.get("Property Details")==null){
									noOfProperties = 0;
								}else{
									noOfProperties = common.no_of_inner_data_sets.get("Property Details");
								}
								
								if(noOfProperties==0){
									TestUtil.reportStatus("Material Damage Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
							
							if(((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes")){
								if(((String)common.NB_excel_data_map.get("CAR_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
								}else{
									TestUtil.reportStatus("Contractors All Risks Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
							
							TestUtil.reportStatus("Policy Trade Code has not been selected. ", "FAIL", true);
							counter++;
						}
						
						break;
					case "CCG":
						customAssert.assertTrue(common.funcPageNavigation("Quote Check",""), "Quote Check page does not open . ");
						
						if(((String)common.NB_excel_data_map.get("PD_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
							if(((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes")){
								int noOfProperties = 0;
								if(common.no_of_inner_data_sets.get("Property Details")==null){
									noOfProperties = 0;
								}else{
									noOfProperties = common.no_of_inner_data_sets.get("Property Details");
								}
								
								if(noOfProperties==0){
									TestUtil.reportStatus("Material Damage Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
							if(((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes")){
								if(!((String)common.NB_excel_data_map.get("CAR_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
									TestUtil.reportStatus("Contractors All Risks Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
						}else{
							
							if(((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes")){
								int noOfProperties = 0;
								if(common.no_of_inner_data_sets.get("Property Details")==null){
									noOfProperties = 0;
								}else{
									noOfProperties = common.no_of_inner_data_sets.get("Property Details");
								}
								
								if(noOfProperties==0){
									TestUtil.reportStatus("Material Damage Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
							
							if(((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes")){
								if(((String)common.NB_excel_data_map.get("CAR_TCS_TradeCode_Button")).equalsIgnoreCase("Yes")){
								}else{
									TestUtil.reportStatus("Contractors All Risks Trade Code has not been selected. ", "FAIL", true);
									counter++;
								}
							}
							
							TestUtil.reportStatus("Policy Trade Code has not been selected. ", "FAIL", true);
							counter++;
						}
						
						break;
					default:
						break;
					}
					
					if(counter==0){
						if(CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
							if(((String)common.NB_excel_data_map.get("CD_Terrorism")).equalsIgnoreCase("Yes")){
								customAssert.assertTrue(k.Click("XOE_IgnoreButon"),"Unable to click on Ignore button.");
							}
						}else{
							customAssert.assertTrue(k.Click("Quote_proceed_btn"),"Unable to click on Proceed button.");
						}
						TestUtil.reportStatus("Quote Check details filled successfully . ", "Info", true);
						return retvalue;
					}else{
						return false;
					}
					
				} catch(Throwable t) {
					return false;
				}
			}
	 
	 /**
		 * 
	   	 * This method is for to Search Policy and select that searched Policy .
	  	 */
		public boolean funcSearchPolicy(Map<Object, Object> map_data)
		{
			boolean ret_value = true;
			String cl_name = (String)map_data.get("NB_ClientName");
			try{
				customAssert.assertTrue(k.getText("searchPoliciesPage_Header").equalsIgnoreCase("Search Policies"), "Search policies Page is not loaded . ");
				customAssert.assertTrue(k.Click("comm_clear"),"Unable to Clear Search Policies Filter Data .");
				customAssert.assertTrue(k.Input("Policy_Number_txtBox", (String)map_data.get("NB_QuoteNumber")), "Unable to enter policy number.");
				customAssert.assertTrue(k.Click("comm_search"), "Unable to click on search button.");
				customAssert.assertTrue(k.getText("Policy_Client_Name").contains(cl_name),"Policy Client Name issue after searching policy . ");
				//customAssert.assertTrue(cl_name.equalsIgnoreCase(k.getText("Policy_Client_Name")),"Policy Client Name issue after searching policy . ");
				TestUtil.reportStatus("Policy: "+(String)map_data.get("NB_QuoteNumber")+" successfully searched . ", "Info", true);
				
				k.pressDownKeyonPage();
				customAssert.assertTrue(k.Click("Policy_Client_Name"), "Unable to click on client name . ");
			}catch(Throwable t)
			{
				String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	            TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
	            k.reportErr("Failed in "+methodName+" function", t);
	            ret_value = false;;
			}
			
			return ret_value;
		}
		
		public boolean funcVerifyPolicyStatus(Map<Object, Object> map_data,String code,String event,String exp_Policy_Status)
		{
			boolean ret_value = true;
			String policy_status_actual=null;
			String testName = (String)map_data.get("Automation Key");
			try{
				
				policy_status_actual = k.getText("Policy_status_header");
				customAssert.assertEquals(policy_status_actual, exp_Policy_Status,"Mismatch in Policy Status - Expected: <b>"+exp_Policy_Status+"</b> Actual: <b>"+policy_status_actual+"</b> on Premium Summary Page . ");
				if(common.currentRunningFlow.equals("NB"))
					customAssert.assertTrue(WriteDataToXl(code+"_"+event, "NB", testName, "NB_CurrentPolicyStatus", exp_Policy_Status,common.NB_excel_data_map),"Error while writing data to excel for field >NB_CurrentPolicyStatus<");
				else if(common.currentRunningFlow.equals("MTA")){
					testName = (String)common.MTA_excel_data_map.get("Automation Key");
					customAssert.assertTrue(WriteDataToXl(code+"_"+common.currentRunningFlow, "MTA", testName, "MTA_CurrentPolicyStatus", exp_Policy_Status,common.MTA_excel_data_map),"Error while writing data to excel for field >MTA_CurrentPolicyStatus<");
				}
				if(exp_Policy_Status.equals("On Cover")){
					String policy_number = k.getText("Header_Policy_Number");
					if(common.currentRunningFlow.equals("MTA")){
						customAssert.assertTrue(WriteDataToXl(code+"_"+event, "MTA", testName, "MTA_PolicyNumber", policy_number,common.MTA_excel_data_map),"Error while writing data to excel for field >MTA_PolicyNumber<");
					}else if(common.currentRunningFlow.equals("NB")){
						customAssert.assertTrue(WriteDataToXl(code+"_"+event, "NB", testName, "NB_PolicyNumber", policy_number,common.NB_excel_data_map),"Error while writing data to excel for field >NB_PolicyNumber<");
					}
					TestUtil.reportStatus("Policy Number :  <b>"+policy_number+"</b> created successfully . ", "Info", true);
				}
				if(exp_Policy_Status.equals("Endorsement On Cover")){
					String policy_number = k.getText("Header_Policy_Number");
					customAssert.assertTrue(WriteDataToXl(code+"_"+common.currentRunningFlow, "MTA", testName, "MTA_PolicyNumber", policy_number,common.MTA_excel_data_map),"Error while writing data to excel for field >MTA_PolicyNumber<");
					TestUtil.reportStatus("Policy Number :  <b>"+policy_number+"</b> created successfully . ", "Info", true);
				}
				TestUtil.reportStatus("Verified Policy Status as <b>"+exp_Policy_Status+"</b> successfully . ", "Info", true);
				}catch(Throwable t){
				String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	            TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
	            k.reportErr("Failed in "+methodName+" function", t);
	            ret_value = false;;
				}
			
			return ret_value;
		}

		public boolean funcGoOnCover(Map<Object, Object> map_data){
			
			 boolean retvalue = true;
				try {   
					customAssert.assertTrue(common.funcPageNavigation("Premium Summary", ""), "Navigation problem to Premium Summary page .");
					customAssert.assertTrue(funcButtonSelection("Go On Cover"), "Unable to click on Go on cover button .");
					customAssert.assertTrue(k.Click("On_cover_accept"), "Unable to Click Go On Cover button on Go On Cover page .");
					return retvalue;

				} catch(Throwable t) {
					String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
					TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
					k.reportErr("Failed in "+methodName+" function", t);
					Assert.fail("Unable to enter details in Go On Cover", t);
					return false;
				}
			}
		

		public boolean funcButtonSelection(String buttonName){
			
			boolean retvalue = true;
			try{
			java.util.List<WebElement> list = driver.findElements(By.xpath("html/body/div[3]/form/div/div[1]/div/a"));
				for(int i=0;i<list.size();i++){
					String name = list.get(i).getText();
					if(name.equalsIgnoreCase(buttonName)){
						list.get(i).click();
						/*if(name.equalsIgnoreCase("Go On Cover")){
							k.waitTwoSeconds();
							customAssert.assertTrue(k.AcceptPopup(), "Unable to accept Go On Cover Pop up .");
						}*/
						break;
						//list = driver.findElements(By.xpath("html/body/div[3]/form/div/div[1]/div/a"));
					}
					
				}
				TestUtil.reportStatus("Clicked on [<b>  "+buttonName+" </b>]  button.", "Info", false);	
			}catch(Throwable t){
				return false;
			}
				return retvalue;
		}
		
public boolean funcPremiumSummaryTable(String code,String event,Map<Object, Object> map_data) throws Exception{
			
			String testName = (String)map_data.get("Automation Key");
			WebDriverWait wait;
			wait = new WebDriverWait(driver, 5);
			Hashtable<String,String> coverlist = new Hashtable<String,String>();
			Hashtable<String,String> premSmryData = new Hashtable<String,String>();
			Hashtable<String,String> transSmryData = new Hashtable<String,String>();
			Hashtable<String,String> cHash = new Hashtable<String,String>();
			boolean transPremFlag =false;
			String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
			String[] prem_header = {"GrossPremium","CommissionRate","Commission","NetPremium","GrossIPT","NetIPT"};
			//System.out.println(prem_col(0));
			if(CommonFunction_GUS.product.equalsIgnoreCase("POB")|| CommonFunction_GUS.product.equalsIgnoreCase("POC")){
				coverlist.put("Material Damage","md8");
				coverlist.put("Loss Of Rental Income","bi3");
				coverlist.put("Property Owners Liability","pl3");
				coverlist.put("Terrorism","tr3");
			}else if(CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
				coverlist.put("Property Excess of Loss","xo1");
				coverlist.put("Terrorism","tr2");
			}else{
				coverlist.put("Material Damage","md7");
				coverlist.put("Business Interruption","bi2");
				coverlist.put("Terrorism","tr2");
			}
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
			if(code.equalsIgnoreCase("CTA")){
				coverlist.put("Directors and Officers","do_pct");
			}else{
				coverlist.put("Directors and Officers","do2");
				}
			coverlist.put("Frozen Foods","ff2");
			coverlist.put("Loss of Licence","ll2");
			coverlist.put("Fidelity Guarantee","fg");
			coverlist.put("Legal Expenses","lg2");
			coverlist.put("Total","tot");
			
			cHash.put("gprem","GP");
			cHash.put("comr","CR");
			cHash.put("comm","GC");
			cHash.put("nprem","NP");
			cHash.put("gipt","GT");
			cHash.put("nipt","NPIPT");
			cHash.put("com","GC");
			
			if (driver.findElement(By.xpath("//*[@id='main']/form/p")).getText().contains("Premium Summary")==false){
				Assert.fail("Premium summary Page couldn't displayed");
			}
			String stDate = null;
			String endDate =null;
			try{
				k.ImplicitWaitOff();
				stDate = driver.findElement(By.xpath("//*[@name='start_date']")).getAttribute("value");
			}catch(Exception e){
				stDate = driver.findElement(By.xpath("//*[@id='start_date']")).getText() ;//getAttribute("value");
			}
			try{
				k.ImplicitWaitOff();
				endDate = driver.findElement(By.xpath("//*[@name='end_date']")).getAttribute("value");
			}catch(Exception e){
				 endDate = driver.findElement(By.xpath("//*[@id='end_date']")).getText() ;//.getAttribute("value");
			}
			String duration = driver.findElement(By.xpath("//*[@id='duration']")).getText();
			//System.out.println("Policy Start Date " + stDate );
			TestUtil.reportStatus("Policy Start Date " + stDate, "Info", false);
			//System.out.println("Policy End Date " + endDate );
			TestUtil.reportStatus("Policy End Date " + endDate, "Info", false);
			//System.out.println("Policy Duration "+duration);
			TestUtil.reportStatus("Policy Duration "+duration, "Info", false);
			
			List<WebElement> col=driver.findElements(By.xpath("html/body/div[3]/form/div/table[2]/tbody/tr[1]/td"));
			//System.out.println(col.size());
			int cCount = col.size();
			
			List<WebElement> row = driver.findElements(By.xpath("html/body/div[3]/form/div/table[2]/tbody/tr"));
			//System.out.println(row.size());
			int rcount = row.size();
			String Status = driver.findElement(By.xpath("//*[@id='headbox']/tbody/tr[1]/td[6]")).getText();
			//System.out.println("Policy Status - "+ Status); 
			TestUtil.reportStatus("Current Policy Status - "+ Status, "Info", false);
			String tXpath;
			String keyName;
			String CellValue;
			switch(Status){
			
			case "Submitted":  		// Submitted State
				//System.out.println("Covers Found -:"+ (rcount-1));
				customAssert.assertTrue(PremiumSummaryDataTraverse(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation");
				customAssert.assertTrue(common.insuranceTaxAdjustmentHandling(code,event),"Insurance tax adjustment is having issue(S).");
				TestUtil.reportStatus("Premium Summary data verification after Insurance Tax Adjustment", "Info", true);
				if(((String)map_data.get("PS_DefaultStartEndDate")).equalsIgnoreCase("No") && ((String)map_data.get("PS_Duration"))!="365"){
					TransactionPremiumTable(rcount,coverlist,cHash,code,event,true);
				}
				customAssert.assertTrue(PremiumSummaryDataTraverse(rcount,coverlist,cHash,code,event,false),"Failed in Premium Summary Calculation after Insurance adjustment");
				
				break;
			case "Submitted (Rewind)":  		
				customAssert.assertTrue(PremiumSummaryDataTraverse(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation");
				//if(!(flowName.equalsIgnoreCase("RewindRemoveCover"))){
				customAssert.assertTrue(common.insuranceTaxAdjustmentHandling(code,event),"Insurance tax adjustment is having issue(S).");
				//}
				TestUtil.reportStatus("Premium Summary data verification after Insurance Tax Adjustment", "Info", true);
				customAssert.assertTrue(PremiumSummaryDataTraverse(rcount,coverlist,cHash,code,event,false),"Failed in Premium Summary Calculation after Insurance adjustment");
				
				break;	
			case "Renewal Pending": 
				
				customAssert.assertTrue(funcGetPremiums(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation");
				/*customAssert.assertTrue(common.insuranceTaxAdjustmentHandling(code,event),"Insurance tax adjustment is having issue(S).");
				TestUtil.reportStatus("Premium Summary data verification after Insurance Tax Adjustment", "Info", true);
				customAssert.assertTrue(PremiumSummaryDataTraverse(rcount,coverlist,cHash,code,event,false),"Failed in Premium Summary Calculation after Insurance adjustment");
				*/
				break;	
			case "Renewal Submitted": 
				customAssert.assertTrue(PremiumSummaryDataTraverse(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation");
				customAssert.assertTrue(common.insuranceTaxAdjustmentHandling(code,event),"Insurance tax adjustment is having issue(S).");
				TestUtil.reportStatus("Premium Summary data verification after Insurance Tax Adjustment", "Info", true);
				customAssert.assertTrue(PremiumSummaryDataTraverse(rcount,coverlist,cHash,code,event,false),"Failed in Premium Summary Calculation after Insurance adjustment");
				break;
			case "Endorsement Submitted":  		
				customAssert.assertTrue(PremiumSummaryDataTraverseMTA(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation");
				//if(!(flowName.equalsIgnoreCase("RewindRemoveCover"))){
				customAssert.assertTrue(common.insuranceTaxAdjustmentHandling(code,event),"Insurance tax adjustment is having issue(S).");
				//}
				TestUtil.reportStatus("Premium Summary data verification after Insurance Tax Adjustment", "Info", true);
				customAssert.assertTrue(PremiumSummaryDataTraverseMTA(rcount,coverlist,cHash,code,event,false),"Failed in Premium Summary Calculation after Insurance adjustment");
				
				break;	
			case "Quoted":  //Quoted State
			
				//System.out.println("Covers Found -:"+ (rcount-1));
				TestUtil.reportStatus("Covers Found -:"+ (rcount-1), "info", true);
				tXpath = "//*[@class='matrix']/tbody/tr";
				for(int i =1;i<=rcount;i++){
					String sectionXpath = tXpath+"["+i+"]/td[1]";
					String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
					for(int j = 2; j<=cCount;j++){
						String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
						//System.out.print(driver.findElement(By.xpath(ColumnValues)).getText()+"\t");
						CellValue = driver.findElement(By.xpath(ColumnValues)).getText().replaceAll(",","");;
						premSmryData.put("PS_"+sec_name.replaceAll(" ", "")+"_"+cHash.get(prem_col[j-2]), CellValue);
					}
					PremiumSummarytableCalculation(premSmryData,sec_name);
					
				}
				break;
			case  "On Cover" :  //On Cover State
				
				//System.out.println("Covers Found -:"+ (rcount-1));
				TestUtil.reportStatus("Covers Found -:"+ (rcount-1), "info", true);
				tXpath = "//*[@class='matrix']/tbody/tr";
				for(int i =1;i<=rcount;i++){
					String sectionXpath = tXpath+"["+i+"]/td[1]";
					String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
					//System.out.print(sec_name+"\t\t\t");
					//TestUtil.reportStatus(sec_name+"\t\t\t", "Info", false);
					for(int j = 2; j<=cCount;j++){
						String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
						CellValue = driver.findElement(By.xpath(ColumnValues)).getText().replaceAll(",","");;
						//System.out.print(CellValue +  "\t");
						//TestUtil.reportStatus(CellValue +  "\t", "Info", false);
						premSmryData.put("PS_"+sec_name.replaceAll(" ", "")+"_"+cHash.get(prem_col[j-2]), CellValue);
						
						}
					PremiumSummarytableCalculation(premSmryData,sec_name);	
					
				}
				break;
			}
			
			
			double FinalPremium = 0.0 , Total_GrossPremium , Total_GrossTax , calcFinalPremium = 0.0;
			switch (common.currentRunningFlow) {
			case "NB":
				FinalPremium =Double.parseDouble(k.getTextByXpath("//*[@class='gttext']").replaceAll(",",""));
				Total_GrossPremium =Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GP",""));// premSmryData.get("PS_Total_GP"));
				Total_GrossTax=Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GT",""));// premSmryData.get("PS_Total_GT"));
				calcFinalPremium = Total_GrossPremium +Total_GrossTax;
				break;
			case "CAN":
				FinalPremium =Double.parseDouble(k.getTextByXpath("//*[@class='gttext']").replaceAll(",",""));
				Total_GrossPremium =Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GP",""));// premSmryData.get("PS_Total_GP"));
				Total_GrossTax=Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GT",""));// premSmryData.get("PS_Total_GT"));
				calcFinalPremium = Total_GrossPremium +Total_GrossTax;
				break;
			case "MTA":
				FinalPremium =Double.parseDouble(k.getTextByXpath("//*[@class='gttext']").replaceAll(",",""));
				Total_GrossPremium =Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GP",CommonFunction_GUS.businessEvent));// premSmryData.get("PS_Total_GP"));
				Total_GrossTax=Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GT",CommonFunction_GUS.businessEvent));// premSmryData.get("PS_Total_GT"));
				calcFinalPremium = Total_GrossPremium +Total_GrossTax;
				break;
			case "Renewal":
				FinalPremium =Double.parseDouble(k.getTextByXpath("//*[@class='gttext']").replaceAll(",",""));
				Total_GrossPremium =Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GP",CommonFunction_GUS.businessEvent));// premSmryData.get("PS_Total_GP"));
				Total_GrossTax=Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GT",CommonFunction_GUS.businessEvent));// premSmryData.get("PS_Total_GT"));
				calcFinalPremium = Total_GrossPremium +Total_GrossTax;
				break;
			
			default:
				break;
			}
			
			compareValues(calcFinalPremium,FinalPremium,"Policy Premium ");
			TestUtil.reportStatus("Final Policy Premium  :<b>"+calcFinalPremium, "Info", false);

			k.ImplicitWaitOn();
			return true;
			
		}
public static boolean PremiumSummaryDataTraverseMTA(int rcount, Hashtable<String,String> coverlist,Hashtable<String,String> cHash,String code, String event,boolean xlWrite){
	
	try{
		common.GrosspremSmryData.clear();
		String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
		String sec_name_withoutSpace = null;
		String CellValue;
		String testName = (String)common.MTA_excel_data_map.get("Automation Key");
		Hashtable<String,String> premSmryData = new Hashtable<String,String>();
		
		TestUtil.reportStatus("Covers Found -:"+ (rcount-1), "info", true);
		String tXpath = "//*[@class='matrix']/tbody/tr";
		String Trax_table = "html/body/div[3]/form/div/table[2]/tbody/tr";
		List<WebElement> ls = driver.findElements(By.xpath(Trax_table));
		
		 for(int i =1;i<=rcount;i++){
			 String sectionXpath = tXpath+"["+i+"]/td[1]";
			 String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
			
			if(sec_name.contains("Frozen Food")){
				sec_name_withoutSpace = "FrozenFoods";
			}else if(sec_name.contains("Licence")){
				sec_name_withoutSpace = "LossOfLicence";
			}else if(sec_name.contains("Total")){
				sec_name = "Total";
				sec_name_withoutSpace = "Total";
			}else{
				sec_name_withoutSpace = sec_name.replaceAll(" ", "");
			}
			
			String policy_status_actual = k.getText("Policy_status_header");
			
			if(policy_status_actual.contains("Endorsement Submitted")){
				if(sec_name_withoutSpace.contains("Total")){
					if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
						TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.MTA_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",""), common.MTA_excel_data_map);
						
						//System.out.print(sec_name+"\t\t\t");
						TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
						for(int j=0;j<prem_col.length;j++){
							String xpathVal;
							xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
							 String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
							
							if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
								CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
								customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.MTA_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
							}
							if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
								System.out.print(" ___ \t ");
							}else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
								if(xlWrite){
									k.scrollInViewByXpath(xpathVal);
									driver.findElement(By.xpath(xpathVal)).click();
									driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",""));
									driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
								}
								CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
								if(xlWrite){
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}else if(ls.size()==0) {
									compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR","")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
								}
								keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
								CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
								if(xlWrite){
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
									common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
									//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}	
							}else{
							    	CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
							    	if(xlWrite){
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
							    	}else if(ls.size()==0){
							    		
									compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,"")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
							    	}
							    keyName=null;
							    }
						}
							//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
						if(ls.size()==0){
							PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
				}else{
					
					if(sec_name.contains("Liability")){
						sec_name_withoutSpace = "Liability";
					}
					if(sec_name.contains("Frozen")){
						sec_name_withoutSpace = "FrozenFood";
					}
					if(sec_name.contains("Licence")){
						sec_name_withoutSpace = "LossofLicence";
					}
					
					if((((String)common.MTA_excel_data_map.get("CD_Add_"+sec_name_withoutSpace)).equalsIgnoreCase("Yes") &&
							((String)common.NB_excel_data_map.get("CD_"+sec_name_withoutSpace)).equalsIgnoreCase("No"))){
						
						if(sec_name.contains("Employers")){
							sec_name_withoutSpace = "EmployersLiability";
						}
						if(sec_name.contains("Public")){
							sec_name_withoutSpace = "PublicLiability";
						}
						if(sec_name.contains("Product")){
							sec_name_withoutSpace = "ProductsLiability";
						}
						if(sec_name.contains("Owners")){
							sec_name_withoutSpace = "PropertyOwnersLiability";
						}
						if(sec_name.contains("Frozen")){
							sec_name_withoutSpace = "FrozenFoods";
						}
						if(sec_name.contains("Licence")){
							sec_name_withoutSpace = "LossOfLicence";
						}
						
						if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
							TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.MTA_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",""), common.MTA_excel_data_map);
							
							//System.out.print(sec_name+"\t\t\t");
							TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
							for(int j=0;j<prem_col.length;j++){
								String xpathVal;
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
								 String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
								
								if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
									xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.MTA_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}
								if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
									System.out.print(" ___ \t ");
								}else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
									if(xlWrite){
										k.scrollInViewByXpath(xpathVal);
										driver.findElement(By.xpath(xpathVal)).click();
										driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",""));
										driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
									}
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
										//common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
									}else if(ls.size()==0) {
										compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR","")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									}
									keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
									xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
									}	
								}else{
								    	CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
								    	if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								    	}else if(ls.size()==0){
								    		
										compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,"")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
								    	}
								    keyName=null;
								    }
							}
								//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
							if(ls.size()==0){
								PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
							
					}else if((((String)common.MTA_excel_data_map.get("CD_Add_"+sec_name_withoutSpace)).equalsIgnoreCase("No") &&
							((String)common.NB_excel_data_map.get("CD_"+sec_name_withoutSpace)).equalsIgnoreCase("Yes"))){
						
						if(sec_name.contains("Employers")){
							sec_name_withoutSpace = "EmployersLiability";
						}
						if(sec_name.contains("Public")){
							sec_name_withoutSpace = "PublicLiability";
						}
						if(sec_name.contains("Product")){
							sec_name_withoutSpace = "ProductsLiability";
						}
						if(sec_name.contains("Owners")){
							sec_name_withoutSpace = "PropertyOwnersLiability";
						}
						if(sec_name.contains("Frozen")){
							sec_name_withoutSpace = "FrozenFoods";
						}
						if(sec_name.contains("Licence")){
							sec_name_withoutSpace = "LossOfLicence";
						}
						
						if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
							//TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate"), common.NB_excel_data_map);
							
							//System.out.print(sec_name+"\t\t\t");
							TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
							for(int j=0;j<prem_col.length;j++){
								String xpathVal;
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
								 String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
								
								if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
									xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}
								if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
									System.out.print(" ___ \t ");
								}else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
									if(xlWrite){
										k.scrollInViewByXpath(xpathVal);
										//driver.findElement(By.xpath(xpathVal)).click();
										//driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR"));
										//driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
									}
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
										//common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
									}else if(ls.size()==0) {
										compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR","")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									}
									keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
									xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
									}	
								}else{
								    	CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
								    	if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								    	}else if(ls.size()==0){
								    		
										compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,"")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
								    	}
								    keyName=null;
								    }
							}
								//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
							if(ls.size()==0){
								PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
					}
					else if((((String)common.MTA_excel_data_map.get("CD_Add_"+sec_name_withoutSpace)).equalsIgnoreCase("Yes") &&
							((String)common.NB_excel_data_map.get("CD_"+sec_name_withoutSpace)).equalsIgnoreCase("Yes"))){
						
						if(sec_name.contains("Employers")){
							sec_name_withoutSpace = "EmployersLiability";
						}
						if(sec_name.contains("Public")){
							sec_name_withoutSpace = "PublicLiability";
						}
						if(sec_name.contains("Product")){
							sec_name_withoutSpace = "ProductsLiability";
						}
						if(sec_name.contains("Owners")){
							sec_name_withoutSpace = "PropertyOwnersLiability";
						}
						if(sec_name.contains("Frozen")){
							sec_name_withoutSpace = "FrozenFoods";
						}
						if(sec_name.contains("Licence")){
							sec_name_withoutSpace = "LossOfLicence";
						}
						
						if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
							//TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate"), common.NB_excel_data_map);
							
							//System.out.print(sec_name+"\t\t\t");
							TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
							for(int j=0;j<prem_col.length;j++){
								String xpathVal;
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
								 String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
								
								if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
									xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.MTA_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}
								if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
									System.out.print(" ___ \t ");
								}else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
									if(xlWrite){
										k.scrollInViewByXpath(xpathVal);
										//driver.findElement(By.xpath(xpathVal)).click();
										//driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR"));
										//driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
									}
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
										//common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
									}else if(ls.size()==0) {
										compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR","")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									}
									keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
									xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
									}	
								}else{
								    	CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
								    	if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								    	}else if(ls.size()==0){
								    		
										compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,"")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
								    	}
								    keyName=null;
								    }
							}
								//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
							if(ls.size()==0){
								PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
					}
				}
				
			}else{
				if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.MTA_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",""), common.MTA_excel_data_map);
					
					//System.out.print(sec_name+"\t\t\t");
					TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
					for(int j=0;j<prem_col.length;j++){
						String xpathVal;
						xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
						 String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
						
						if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
							xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
							CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
							customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.MTA_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
						}
						if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
							System.out.print(" ___ \t ");
						}else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
							if(xlWrite){
								k.scrollInViewByXpath(xpathVal);
								driver.findElement(By.xpath(xpathVal)).click();
								driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",""));
								driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
							}
							CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
							if(xlWrite){
								premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
								//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
							}else if(ls.size()==0) {
								compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR","")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
								premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
							}
							keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
							xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
							CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
							if(xlWrite){
								premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
								common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
								//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
							}	
						}else{
						    	CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
						    	if(xlWrite){
								premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
								//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
						    	}else if(ls.size()==0){
						    		
								compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,"")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
								premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
						    	}
						    keyName=null;
						    }
					}
						//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
					if(ls.size()==0){
						PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
			}
			
		 }
		 customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Premium Summary .");
		 
		 
		 if(ls.size()>0){ 
		 TransactionDetailsTable(rcount,coverlist,cHash,code,event,true);
		 }else if(xlWrite){
			 Set<String> pKeys=premSmryData.keySet();
			 	for(String pkey:pKeys){
			 		customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName,pkey,premSmryData.get(pkey) ,common.MTA_excel_data_map),"Error while writing Premium Summary data to excel ."+pkey) ;
			 	}
		 }
		
		 return true;
	}catch(Throwable t){
			k.ImplicitWaitOn();
			return false;
	}
  
 }

		 
public static boolean PremiumSummaryDataTraverse(int rcount, Hashtable<String,String> coverlist,Hashtable<String,String> cHash,String code, String event,boolean xlWrite){
    
    try{
           common.GrosspremSmryData.clear();
           String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
           String sec_name_withoutSpace = null;
           String CellValue;
           String testName = (String)common.NB_excel_data_map.get("Automation Key");
           Hashtable<String,String> premSmryData = new Hashtable<String,String>();
           
           TestUtil.reportStatus("Covers Found -:"+ (rcount-1), "info", true);
           String tXpath = "//*[@class='matrix']/tbody/tr";
           String Trax_table = "html/body/div[3]/form/div/table[3]/tbody/tr";
           List<WebElement> ls = driver.findElements(By.xpath(Trax_table));
           
           for(int i =1;i<=rcount;i++){
                  String sectionXpath = tXpath+"["+i+"]/td[1]";
                  String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
                  
                  if(sec_name.contains("Frozen Food")){
                        sec_name_withoutSpace = "FrozenFoods";
                  }else if(sec_name.contains("Licence")){
                        sec_name_withoutSpace = "LossOfLicence";
                  }else if(sec_name.contains("Total")){
                        sec_name = "Total";
                        sec_name_withoutSpace = "Total";
                  }else{
                        sec_name_withoutSpace = sec_name.replaceAll(" ", "");
                  }
                  
                  String policy_status_actual = k.getText("Policy_status_header");
                  
                  if(policy_status_actual.contains("Rewind")){
                        if(sec_name_withoutSpace.contains("Total")){
                               if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
                                      TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",CommonFunction_GUS.businessEvent), common.NB_excel_data_map);
                                      
                                      //System.out.print(sec_name+"\t\t\t");
                                      TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
                                      for(int j=0;j<prem_col.length;j++){
                                             String xpathVal;
                                             xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
                                             String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
                                             
                                             if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
                                                    xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
                                                    CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                    customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                             }
                                             if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
                                                    System.out.print(" ___ \t ");
                                             }else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
                                                    if(xlWrite){
                                                           k.scrollInViewByXpath(xpathVal);
                                                           driver.findElement(By.xpath(xpathVal)).click();
                                                           driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent));
                                                           driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
                                                    }
                                                    CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                    if(xlWrite){
                                                           premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                           //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                    }else if(ls.size()==0) {
                                                           compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                           premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                    }
                                                    keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
                                                    xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
                                                    CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                    if(xlWrite){
                                                           premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                           common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                           //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                    }      
                                             }else{
                                                    CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
                                                    if(xlWrite){
                                                           premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                           //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                    }else if(ls.size()==0){
                                                           
                                                           compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                           premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                    }
                                                 keyName=null;
                                                 }
                                      }
                                             //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
                                      if(ls.size()==0){
                                             PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
                        }else{
                               
                               if(sec_name.contains("Liability")){
                                      sec_name_withoutSpace = "Liability";
                               }
                               if(sec_name.contains("Frozen")){
                                      sec_name_withoutSpace = "FrozenFood";
                               }
                               if(sec_name.contains("Licence")){
                                      sec_name_withoutSpace = "LossofLicence";
                               }
                               
                               if((((String)common.NB_excel_data_map.get("CD_Add_"+sec_name_withoutSpace)).equalsIgnoreCase("Yes") &&
                                             ((String)common.NB_excel_data_map.get("CD_"+sec_name_withoutSpace)).equalsIgnoreCase("No"))){
                                      
                                      if(sec_name.contains("Employers")){
                                             sec_name_withoutSpace = "EmployersLiability";
                                      }
                                      if(sec_name.contains("Public")){
                                             sec_name_withoutSpace = "PublicLiability";
                                      }
                                      if(sec_name.contains("Product")){
                                             sec_name_withoutSpace = "ProductsLiability";
                                      }
                                      if(sec_name.contains("Owners")){
                                             sec_name_withoutSpace = "PropertyOwnersLiability";
                                      }
                                      if(sec_name.contains("Frozen")){
                                             sec_name_withoutSpace = "FrozenFoods";
                                      }
                                      if(sec_name.contains("Licence")){
                                             sec_name_withoutSpace = "LossOfLicence";
                                      }
                                      
                                      if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
                                             if(((String)common.NB_excel_data_map.get("PS_InsuranceTaxButton")).equalsIgnoreCase("Yes")){
                                                    TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", "00.00", common.NB_excel_data_map);
                                             }else{
                                                    TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",CommonFunction_GUS.businessEvent), common.NB_excel_data_map);
                                             }
                                      
                                             //System.out.print(sec_name+"\t\t\t");
                                             TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
                                             for(int j=0;j<prem_col.length;j++){
                                                    String xpathVal;
                                                    xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
                                                    String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
                                                    
                                                    if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
                                                           xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                           customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                    }
                                                    if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
                                                           System.out.print(" ___ \t ");
                                                    }else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
                                                           if(xlWrite){
                                                                  k.scrollInViewByXpath(xpathVal);
                                                                  driver.findElement(By.xpath(xpathVal)).click();
                                                                  driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent));
                                                                  driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
                                                           }
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                           if(xlWrite){
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                                  //common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                                  //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                           }else if(ls.size()==0) {
                                                                  compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                           }
                                                           keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
                                                           xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                           if(xlWrite){
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                                  common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                                  //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                           }      
                                                    }else{
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
                                                           if(xlWrite){
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                                  //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                           }else if(ls.size()==0){
                                                                  
                                                                  compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                           }
                                                        keyName=null;
                                                        }
                                             }
                                                    //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
                                             if(ls.size()==0){
                                                    PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
                                             
                               }else if((((String)common.NB_excel_data_map.get("CD_Add_"+sec_name_withoutSpace)).equalsIgnoreCase("No") &&
                                             ((String)common.NB_excel_data_map.get("CD_"+sec_name_withoutSpace)).equalsIgnoreCase("Yes"))){
                                      
                                      if(sec_name.contains("Employers")){
                                             sec_name_withoutSpace = "EmployersLiability";
                                      }
                                      if(sec_name.contains("Public")){
                                             sec_name_withoutSpace = "PublicLiability";
                                      }
                                      if(sec_name.contains("Product")){
                                             sec_name_withoutSpace = "ProductsLiability";
                                      }
                                      if(sec_name.contains("Owners")){
                                             sec_name_withoutSpace = "PropertyOwnersLiability";
                                      }
                                      if(sec_name.contains("Frozen")){
                                             sec_name_withoutSpace = "FrozenFoods";
                                      }
                                      if(sec_name.contains("Licence")){
                                             sec_name_withoutSpace = "LossOfLicence";
                                      }
                                      
                                      if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
                                             //TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate"), common.NB_excel_data_map);
                                             
                                             //System.out.print(sec_name+"\t\t\t");
                                             TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
                                             for(int j=0;j<prem_col.length;j++){
                                                    String xpathVal;
                                                    xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
                                                    String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
                                                    
                                                    if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
                                                           xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                           customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                    }
                                                    if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
                                                           System.out.print(" ___ \t ");
                                                    }else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
                                                           if(xlWrite){
                                                                  k.scrollInViewByXpath(xpathVal);
                                                                  //driver.findElement(By.xpath(xpathVal)).click();
                                                                  //driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR"));
                                                                  //driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
                                                           }
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                           if(xlWrite){
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                                  //common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                                  //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                           }else if(ls.size()==0) {
                                                                  compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                           }
                                                           keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
                                                           xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                           if(xlWrite){
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                                  common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                                  //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                           }      
                                                    }else{
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
                                                           if(xlWrite){
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                                  //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                           }else if(ls.size()==0){
                                                                  
                                                                  compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                           }
                                                        keyName=null;
                                                        }
                                             }
                                                    //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
                                             if(ls.size()==0){
                                                    PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
                               }
                               else if((((String)common.NB_excel_data_map.get("CD_Add_"+sec_name_withoutSpace)).equalsIgnoreCase("Yes") &&
                                             ((String)common.NB_excel_data_map.get("CD_"+sec_name_withoutSpace)).equalsIgnoreCase("Yes"))){
                                      
                                      if(sec_name.contains("Employers")){
                                             sec_name_withoutSpace = "EmployersLiability";
                                      }
                                      if(sec_name.contains("Public")){
                                             sec_name_withoutSpace = "PublicLiability";
                                      }
                                      if(sec_name.contains("Product")){
                                             sec_name_withoutSpace = "ProductsLiability";
                                      }
                                      if(sec_name.contains("Owners")){
                                             sec_name_withoutSpace = "PropertyOwnersLiability";
                                      }
                                      if(sec_name.contains("Frozen")){
                                             sec_name_withoutSpace = "FrozenFoods";
                                      }
                                      if(sec_name.contains("Licence")){
                                             sec_name_withoutSpace = "LossOfLicence";
                                      }
                                      
                                      if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
                                             //TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate"), common.NB_excel_data_map);
                                             
                                             //System.out.print(sec_name+"\t\t\t");
                                             TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
                                             for(int j=0;j<prem_col.length;j++){
                                                    String xpathVal;
                                                    xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
                                                    String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
                                                    
                                                    if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
                                                           xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                           customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                    }
                                                    if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
                                                           System.out.print(" ___ \t ");
                                                    }else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
                                                           if(xlWrite){
                                                                  k.scrollInViewByXpath(xpathVal);
                                                                  //driver.findElement(By.xpath(xpathVal)).click();
                                                                  //driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR"));
                                                                  //driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
                                                           }
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                           if(xlWrite){
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                                  //common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                                  //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                           }else if(ls.size()==0) {
                                                                  compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                           }
                                                           keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
                                                           xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                                           if(xlWrite){
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                                  common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                                  //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                           }      
                                                    }else{
                                                           CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
                                                           if(xlWrite){
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                                  //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                                           }else if(ls.size()==0){
                                                                  
                                                                  compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                                  premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                           }
                                                        keyName=null;
                                                        }
                                             }
                                                    //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
                                             if(ls.size()==0){
                                                    PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
                               }
                        }
                        
                  }else{
                        if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
                        	if(((String)common.NB_excel_data_map.get("PS_InsuranceTaxButton")).equalsIgnoreCase("Yes")){
								TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", "00.00", common.NB_excel_data_map);
							}else{
								TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",CommonFunction_GUS.businessEvent), common.NB_excel_data_map);
							}
                               
                               //System.out.print(sec_name+"\t\t\t");
                               TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
                               for(int j=0;j<prem_col.length;j++){
                                      String xpathVal;
                                      xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
                                      String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
                                      
                                      if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
                                             xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
                                             CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                             customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                      }
                                      if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
                                             System.out.print(" ___ \t ");
                                      }else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
                                             if(xlWrite){
                                                    k.scrollInViewByXpath(xpathVal);
                                                    driver.findElement(By.xpath(xpathVal)).click();
                                                    driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent));
                                                    driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
                                             }
                                             CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                             if(xlWrite){
                                                    premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                    //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                             }else if(ls.size()==0) {
                                                    compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                    premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                             }
                                             keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
                                             xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
                                             CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
                                             if(xlWrite){
                                                    premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                    common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
                                                    //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                             }      
                                      }else{
                                             CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
                                             if(xlWrite){
                                                    premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                                    //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
                                             }else if(ls.size()==0){
                                                    
                                                    compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,"")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
                                                    premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
                                             }
                                          keyName=null;
                                          }
                               }
                                      //customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
                               if(ls.size()==0){
                                      PremiumSummarytableCalculation(premSmryData,sec_name_withoutSpace);}
                  }
                  
           }
           customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Premium Summary .");
           
            
            if(ls.size()>0){ 
            TransactionPremiumTable(rcount,coverlist,cHash,code,event,xlWrite);
           }else if(xlWrite){
                  Set<String> pKeys=premSmryData.keySet();
                        for(String pkey:pKeys){
                               customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName,pkey,premSmryData.get(pkey) ,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+pkey) ;
                        }
           }
           
           return true;
    }catch(Throwable t){
                  k.ImplicitWaitOn();
                  return false;
    }

}

public static void TransactionPremiumTable(int rcount, Hashtable<String,String> coverlist,Hashtable<String,String> cHash,String code, String event, boolean xlWrite){
	//Transaction Premium Table
		try{
			k.ImplicitWaitOff();
			
			String Trax_table = "html/body/div[3]/form/div/table[3]";
			String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
			String testName = (String)common.NB_excel_data_map.get("Automation Key");
			List<WebElement> col=driver.findElements(By.xpath("html/body/div[3]/form/div/table[3]/tbody/tr[1]/td"));
			//System.out.println(col.size());
			int cCount = col.size();
			if(driver.findElement(By.xpath(Trax_table)).isDisplayed()){
				
				Hashtable<String,String> transSmryData = new Hashtable<String,String>();
				String tXpath =Trax_table+"/tbody/tr";
				String CellValue;
				//System.out.println("Transaction Premium Table exist on premium summary page");
				TestUtil.reportStatus("Transaction Premium Table exist on premium summary page", "Info", true);
				//System.out.println("Covers Found -:"+ (rcount-1));
				TestUtil.reportStatus("Covers Found -:"+ (rcount-1), "Info", false);
				
				for(int i =1;i<=rcount;i++){
					String sectionXpath = tXpath+"["+i+"]/td[1]";
					String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
					//System.out.print(sec_name+"\t\t\t");
					if(sec_name.contains("Frozen Food")){
						sec_name = "FrozenFoods";
					}else if(sec_name.contains("Licence")){
						sec_name = "LossOfLicence";
					}else if(sec_name.contains("Total")){
						sec_name = "Total";
						sec_name = "Total";
					}else{
						sec_name = sec_name.replaceAll(" ", "");
					}
					
					TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
					
					for(int j = 2; j<=cCount;j++){
						//transSmryData
						String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
						CellValue = driver.findElement(By.xpath(ColumnValues)).getText().replaceAll(",","");;
						//System.out.print(CellValue +  "\t");	
						//TestUtil.reportStatus(CellValue +  "\t", "Info", false);
						if(cHash.get(prem_col[j-2]).equalsIgnoreCase("CR") && sec_name.contains("Total"))
						{}else{
						transSmryData.put("PS_"+sec_name.replaceAll(" ", "")+"_"+cHash.get(prem_col[j-2]), CellValue);}
					}
					PremiumSummarytableCalculation(transSmryData,sec_name);
					//System.out.println("\n___");
				}
			
			// Writing Data to Excel Sheet from Map
				Set<String> pKeys=transSmryData.keySet();
			 	for(String pkey:pKeys){
			 		if(xlWrite){
			 			customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName,pkey,transSmryData.get(pkey) ,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+pkey) ;
			 		}else{
			 			compareValues(Double.parseDouble(transSmryData.get(pkey)), Double.parseDouble(TestUtil.getStringfromMap(transSmryData.get(pkey),"")), pkey);
			 		}
			 	}
			}
		}
		catch(Throwable t ){
			k.ImplicitWaitOn();
			System.out.println("--"); 
		}
		
		//System.out.println("Gross Premium for Material Damage Cover in Transaction Premium table is :"+transSmryData.get("MaterialDamage_gprem"));
		 TestUtil.reportStatus("Values have been stored from Transaction Premium table", "info", true);
}

	public static void TransactionDetailsTable(int rcount, Hashtable<String,String> coverlist,Hashtable<String,String> cHash,String code, String event, boolean xlWrite){
		//Transaction Premium Table
			try{
				k.ImplicitWaitOff();
				List<WebElement> row = driver.findElements(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr"));
				//System.out.println(row.size());
				int rcount1 = row.size();
				String Trax_table = "html/body/div[3]/form/div/table[4]";
				String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
				String testName = (String)common.MTA_excel_data_map.get("Automation Key");
				List<WebElement> col=driver.findElements(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr[1]/td"));
				//System.out.println(col.size());
				int cCount = col.size();
				if(driver.findElement(By.xpath(Trax_table)).isDisplayed()){
					
					Hashtable<String,String> transSmryData = new Hashtable<String,String>();
					String tXpath =Trax_table+"/tbody/tr";
					String CellValue;
					//System.out.println("Transaction Premium Table exist on premium summary page");
					TestUtil.reportStatus("Transaction Details Table exist on premium summary page", "Info", true);
					//System.out.println("Covers Found -:"+ (rcount-1));
					TestUtil.reportStatus("Covers Found -:"+ (rcount1-1), "Info", false);
					
					for(int i =1;i<=rcount1;i++){
						String sectionXpath = tXpath+"["+i+"]/td[1]";
						String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
						//System.out.print(sec_name+"\t\t\t");
						if(sec_name.contains("Frozen Food")){
							sec_name = "FrozenFoods";
						}else if(sec_name.contains("Licence")){
							sec_name = "LossOfLicence";
						}else if(sec_name.contains("Total")){
							sec_name = "Total";
							sec_name = "Total";
						}else{
							sec_name = sec_name.replaceAll(" ", "");
						}
						
						TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
						
						for(int j = 2; j<=cCount;j++){
							//transSmryData
							String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
							CellValue = driver.findElement(By.xpath(ColumnValues)).getText().replaceAll(",","");;
							//System.out.print(CellValue +  "\t");	
							//TestUtil.reportStatus(CellValue +  "\t", "Info", false);
							if(cHash.get(prem_col[j-2]).equalsIgnoreCase("CR") && sec_name.contains("Total"))
							{
								
							}else{
							transSmryData.put("PS_"+sec_name.replaceAll(" ", "")+"_"+cHash.get(prem_col[j-2]), CellValue);}
						}
						PremiumSummarytableCalculation(transSmryData,sec_name);
						//System.out.println("\n___");
					}
				
				// Writing Data to Excel Sheet from Map
					Set<String> pKeys=transSmryData.keySet();
				 	for(String pkey:pKeys){
				 		if(xlWrite){
				 			customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName,pkey,transSmryData.get(pkey) ,common.MTA_excel_data_map),"Error while writing Premium Summary data to excel ."+pkey) ;
				 		}else{
				 			compareValues(Double.parseDouble(transSmryData.get(pkey)), Double.parseDouble(TestUtil.getStringfromMap(transSmryData.get(pkey),"")), pkey);
				 		}
				 	}
				}
			}
			catch(Throwable t ){
				k.ImplicitWaitOn();
				System.out.println("--"); 
			}
			
			//System.out.println("Gross Premium for Material Damage Cover in Transaction Premium table is :"+transSmryData.get("MaterialDamage_gprem"));
			 TestUtil.reportStatus("Values have been stored from Transaction Details table", "info", true);
	}
		
    /*
     * ------------------------------------------------------------
     * PDF Verification Handling function. START
     * ------------------------------------------------------------
     */
		 
	public boolean funcPDFdocumentVerification(String docType){
		boolean retvalue = true;
		int doc_fail_count = 0;
		err_count=0;
		Map<Object,Object> data_map = null;
		if(common.currentRunningFlow.equals("NB")){
			data_map = common.NB_excel_data_map;}
		else if(common.currentRunningFlow.equals("MTA")){
			data_map = common.MTA_excel_data_map;
		}
		else if(common.currentRunningFlow.equals("Renewal")){
			data_map = common.Renewal_excel_data_map;
		}
		
		
		try{
			if(((String)data_map.get("DocumentVerification")).equals("No")){
				TestUtil.reportStatus("<b> PDF document verification is 'No' hence skipped verification . ", "Info", false);
				TestUtil.reportStatus("<b> Total count of document verification is : [ 0 ]</b>", "Info", false);
			}else{
				customAssert.assertTrue(common.funcButtonSelection(docType) , "Unable to click on <b>[  "+docType+"  ]</b>.");
				doc_fail_count = doc_fail_count + iteratePDFDocuments(docType);
				customAssert.SoftAssertEquals(doc_fail_count, 0,"Verification failure in "+docType);
				final_err_pdf_count = final_err_pdf_count + doc_fail_count;
				TestUtil.reportStatus(docType+" verification is Completed .", "Info", true);
				customAssert.assertTrue(k.Click("Tax_adj_BackBtn"), "Unable to click on back button.");
			}
			
			
			return retvalue;
		}
		catch(Throwable t) {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
            Assert.fail("Unable to do verification for documents. \n", t);
            return false;
		}
		
	}
				
	/*
	 * PDF verification related functions:
	 * 
	 *  @Param docType - Either "Draft Documents" OR "Documents"
	 *  @Variable o - Number of documents
	 *  @Variable count - Documents data verification failure count
	 *  @Variable counter - incremental count for downloading same file (Used in PDFFileHandling method)
	 */
	
	public int iteratePDFDocuments(String docType) throws ParseException, IOException, InterruptedException{
		try{
			List<WebElement> l_row = driver.findElements(By.xpath("html/body/div[3]/form/div/div[2]/table/tbody/tr/td/a/span"));
			int row_size = l_row.size();
			
			Map<Object,Object> data_map = null;
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
			
			if(row_size>0){
				for(int r=0;r<row_size;r++){
					String doc_name = l_row.get(r).getText();
					if((String)data_map.get("pdf_"+doc_name)!=null && ((String)data_map.get("pdf_"+doc_name)).equals("Yes")){
						l_row.get(r).click();
						k.waitFiveSeconds();
						TestUtil.reportStatus("Document -"+doc_name+" is present.", "info", true);
						counter = 0;
						pdf_count++;
						err_count = err_count + PDFFileHandling(doc_name,docType);
					
						l_row = driver.findElements(By.xpath("html/body/div[3]/form/div/div[2]/table/tbody/tr"));
					}else{
						continue;
					}
				}
			}
			TestUtil.reportStatus("Total count of <b>[  "+docType+" is : "+pdf_count+"  ]</b>", "Info", false);
			}catch(Throwable t)
			{
				String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
				TestUtil.reportFunctionFailed("Failed in "+methodName+" function");   
				TestUtil.reportStatus("<p style='color:red'> Error in iteratePDFDocuments Method </p>", "Fail", true);
			 	return 1;
			}
			return err_count;
	}
			
	public int PDFFileHandling(String fileName,String docType) throws IOException, ParseException, InterruptedException{
		String file_Name=null;
		String PDFCodePath = null;
		String fileCode=null;
		int dataVerificationFailureCount = 0;
		String code = CommonFunction_GUS.product;
		try{
			//TestUtil.reportStatus(fileName+" document verification is started for product - [<b>"+code+"</b>] ", "Info", false);
			String PDFPath= workDir+"\\src\\com\\selenium\\Execution_Report\\Report\\PDF";
			PDFCodePath = PDFPath+"\\"+code;
			File pdfFldr = new File(PDFPath);
			File pdfCodeFldr=new File(PDFCodePath);
			if(!pdfFldr.exists() && !pdfFldr.isDirectory()){
				pdfFldr.mkdir();
				}
			if(!pdfCodeFldr.exists() && !pdfCodeFldr.isDirectory()){
				pdfCodeFldr.mkdir();
				
			}
			
			fileCode = downloadPDF(code,fileName);
			Thread.sleep(4000);
			file_Name = PDFCodePath+"\\"+fileCode+".pdf";
			//System.out.println(file_Name);
			
			File file = new File(file_Name);
			FileInputStream fis = new FileInputStream(file);
			TestUtil.reportStatus(fileName+" file is downloaded to the specified folder and ready for verification.", "Info", false);
			
			dataVerificationFailureCount = dataVerificationFailureCount + PDFDataVerification(fis,fileName,docType);
			TestUtil.reportStatus("<b> Total count of document verification is : [ "+pdf_count+" ]</b>", "Info", false);
			
			}
			
			// Below code will handle PDF failure up to 3 chance.
			catch(FileNotFoundException fnf)
			{
				
				if(counter==3){
					TestUtil.reportStatus("<b>Due to some reason , Not able to downalod -[  "+fileName+"  ]. 3 times tried to download his file .</b>", "Info", false);
				}else{
					counter++;
					TestUtil.reportStatus("Due to some reason , Not able to downalod - "+fileName+" . Retried downloading.", "Info", false);
					PDFFileHandling(fileName,docType);
				}
			return 0;
			}
			catch(NullPointerException npe)
			{
						TestUtil.reportStatus("Data Issue while verification . ", "Fail", false);
						return 1;
			}
				
			
			catch(Exception ex)
			{
				if(counter==3){
					TestUtil.reportStatus("<b>Due to some reason , Not able to downalod -[  "+fileName+"  ]. 3 times tried to download his file .</b>", "Info", false);
			}else{
				counter++;
				TestUtil.reportStatus("Due to some reason , Not able to downalod - "+fileName+" . Retried downloading.", "Info", false);
				PDFFileHandling(fileName,docType);
			}
		    return 0;
			}
		
		customAssert.assertTrue(fileDeletion(PDFCodePath) , "Unable to delete extra pdf files from folder : "+PDFCodePath);
		
		return dataVerificationFailureCount;
			
	}
			
	/**
	 * @param code
	 * @param fileName
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String downloadPDF(String code,String fileName) throws InterruptedException, IOException {
	    
	    String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
	    try{
	     
	    	WebDriverWait wait = new WebDriverWait(driver, 50); 
	    	WebElement menuItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='mainpanel']/div[3]/object")));  // until this submenu is found
	     
		     if(menuItem!=null && menuItem.isDisplayed()){
		           
		           driver.findElement(By.xpath("//*[@id='mainpanel']/div[3]/object")).click();
		           Actions action = new Actions(driver);
		           Thread.sleep(10000);
		           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
		           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
		           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
		           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
		           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
		           action.sendKeys(Keys.ENTER).perform();
		           k.waitTenSeconds();
		           
		           String fileCode = code+"_"+fileName+"_"+timeStamp;
		           
		           String[] parms = {"wscript", workDir+"\\src\\BatFiles\\CloseDialog.vbs", workDir+"\\src\\com\\selenium\\Execution_Report\\Report\\PDF\\"+code+"\\"+fileCode};
		           Runtime.getRuntime().exec(parms);
		           return fileCode;
		     }else{
		           
		           return "";
		     }
	    }catch(Exception e){
	           String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	           TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
	           TestUtil.reportStatus("<p style='color:red'> PDF Document is not visible for download </p>", "Fail", true);
	           return "";
	    }
	}
			
	/**
	 * @param fis - Downloaded file referance
	 * @param fileName - e.g. Policy Schedule
	 * @param docType - Draft Documents/Documents
	 */
	
public int PDFDataVerification(FileInputStream fis,String fileName,String docType) throws IOException, ParseException, InterruptedException {
		
		String parsedText=null;
		int fail_count=0;
		PDFParser parser = new PDFParser(fis);
		parser.parse();
		COSDocument cosDoc = parser.getDocument();
	    PDDocument pdDoc = new PDDocument(cosDoc);
	    PDFTextStripper pdfStripper = new PDFTextStripper();
	    
		parsedText = pdfStripper.getText(pdDoc);
		int count = pdDoc.getNumberOfPages();
		pdfStripper.setStartPage(1);
		pdfStripper.setEndPage(count);
		Map<Object,Object> mdata =  null;
		
		switch (common.currentRunningFlow) {
			case "Renewal":
				mdata = common.Renewal_excel_data_map;
			break;
			default:
				mdata=common.NB_excel_data_map;
			break;
		
		}
		//System.out.println(parsedText);
		switch(fileName){
		
			case "Policy Schedule":
				
				DecimalFormat formatter = new DecimalFormat("#,###,###.##");
				int incrementalDays = Integer.parseInt(CONFIG.getProperty("DaysIncrementNumber"));
				int policyDuration = Integer.parseInt((String)mdata.get("PS_Duration"));
				fail_count=0;
						
				if(docType.contains("Draft")){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains(" QUOTATION SUMMARY"), "Document : QUOTATION SUMMARY", fileName);
				}else{
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains(" POLICY SCHEDULE"), "Document : POLICY SCHEDULE", fileName);
				}
				if(TestBase.product.equals("SPI")){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("SOLICITORS PROFESSIONAL"), "Product name : SOLICITORS PROFESSIONAL" , fileName);
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INDEMNITY"), "Product name : INDEMNITY" , fileName);
				}else{
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("pdf_ProductName")), "Product name : "+(String)mdata.get("pdf_ProductName") , fileName);
				}
				if(common.currentRunningFlow.equals("Renewal")){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)common.Renewal_excel_data_map.get(common.currentRunningFlow+"_ClientName")), "Insured Name : "+(String)common.Renewal_excel_data_map.get(common.currentRunningFlow+"_ClientName") , fileName);
				}else{
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)mdata.get("NB_ClientName")), "Insured Name : "+(String)mdata.get("NB_ClientName") , fileName);
				}
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("QC_AgencyName")), "Agency name : "+(String)mdata.get("QC_AgencyName") , fileName);
				if(TestBase.product.equals("SPI")){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Correspondence Address: "+(String)mdata.get("PG_Address")), "Correspondence Address : "+(String)mdata.get("PD_Address") , fileName);
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Postcode: "+(String)mdata.get("PG_Postcode")), "Postcode: "+(String)mdata.get("PG_Postcode") , fileName);
					
				}else{
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Correspondence Address: "+(String)mdata.get("PD_Address")), "Correspondence Address : "+(String)mdata.get("PD_Address") , fileName);
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Postcode: "+(String)mdata.get("PD_Postcode")), "Postcode: "+(String)mdata.get("PD_Postcode") , fileName);
					
				}
					
				if(docType.contains("Draft")){
					if(!common.currentRunningFlow.equalsIgnoreCase("Renewal")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quote Reference: "+(String)mdata.get("NB_QuoteNumber")) ,"Quote Reference : "+mdata.get("NB_QuoteNumber"),fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quotation Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays)) ,"Quote Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays),fileName);
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quote Reference: "+(String)common.Renewal_excel_data_map.get(common.currentRunningFlow+"_QuoteNumber")) ,"Quote Reference : "+common.Renewal_excel_data_map.get(common.currentRunningFlow+"_QuoteNumber"),fileName);
					}
				}else{
					if(!common.currentRunningFlow.equalsIgnoreCase("Renewal")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Number: "+(String)mdata.get("NB_PolicyNumber")) ,"Policy Number : "+mdata.get("NB_PolicyNumber"),fileName);
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Number: "+(String)mdata.get(common.currentRunningFlow+"_PolicyNumber")) ,"Policy Number : "+mdata.get(common.currentRunningFlow+"_PolicyNumber"),fileName);
					}
					if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
						if(common.currentRunningFlow.equals("MTA")){
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)common.MTA_excel_data_map.get("MTA_EffectiveDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)common.MTA_excel_data_map.get("MTA_EffectiveDate"),0),fileName);
						}else{
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0),fileName);
						}
					}else{
						if(common.currentRunningFlow.equals("MTA")){
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)common.MTA_excel_data_map.get("MTA_EffectiveDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)common.MTA_excel_data_map.get("MTA_EffectiveDate"),0),fileName);
						}else{
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0),fileName);
						}
					}
					
				}
				if(!common.currentRunningFlow.equals("Renewal")){
					if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0) , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1) , fileName);
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0) , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1) , fileName);
		
					}
				}
				if(!TestBase.product.equals("SPI")){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business: "+(String)mdata.get("PD_BusinessDesc")), "Business: "+(String)mdata.get("PD_BusinessDesc") , fileName);
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sections Section Insured"), "Sections Section Insured" , fileName);
					
					if(((String)mdata.get("CD_MaterialDamage")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Material Damage Insured"), "Material Damage Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MATERIAL DAMAGE SECTION"), "APPENDIX TO MATERIAL DAMAGE SECTION" , fileName);
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Material Damage Not Insured"), "Material Damage Not Insured" , fileName);
					}
				}
				
				
				
				switch (CommonFunction_GUS.product) {
				case "POB":
					if(((String)mdata.get("CD_LossOfRentalIncome")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Rental Income Insured"), "Loss of Rental Income Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LOSS OF RENTAL INCOME SECTION"), "APPENDIX TO LOSS OF RENTAL INCOME SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Rental Income Not Insured"), "Loss of Rental Income Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Liability")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
						String EL_LOI = formatter.format(Double.parseDouble((String)mdata.get("EL_LimitOfIndemnity")));
						String POL_LOI = formatter.format(Double.parseDouble((String)mdata.get("POL_IndemnityLimit")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Property Owners Liability �"+POL_LOI), "Property Owners Liability &pound;"+POL_LOI , fileName);
						
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Terrorism")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
						if(((String)mdata.get("CD_MaterialDamage")).equals("Yes")){
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
						}
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_LegalExpenses")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
						
						String LE_LimitOfLiability = (String)common.NB_excel_data_map.get("LE_LimitOfLiability");
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
						
							
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
					}
					break;
				case "POC":
					if(((String)mdata.get("CD_LossOfRentalIncome")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Rental Income Insured"), "Loss of Rental Income Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LOSS OF RENTAL INCOME SECTION"), "APPENDIX TO LOSS OF RENTAL INCOME SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Rental Income Not Insured"), "Loss of Rental Income Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Liability")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
						String EL_LOI = formatter.format(Double.parseDouble((String)mdata.get("EL_LimitOfIndemnity")));
						String POL_LOI = formatter.format(Double.parseDouble((String)mdata.get("POL_IndemnityLimit")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Property Owners Liability �"+POL_LOI), "Property Owners Liability &pound;"+POL_LOI , fileName);
						
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Terrorism")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
						if(((String)mdata.get("CD_MaterialDamage")).equals("Yes")){
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
						}
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_LegalExpenses")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
						
						String LE_LimitOfLiability = (String)mdata.get("LE_LimitOfLiability");
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
						
							
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
					}
					break;	
				case "CCF":
					if(((String)mdata.get("CD_BusinessInterruption")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Insured"), "Business Interruption Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO BUSINESS INTERRUPTION SECTION"), "APPENDIX TO BUSINESS INTERRUPTION SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Not Insured"), "Business Interruption Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Liability")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
						String EL_LOI = formatter.format(Double.parseDouble((String)mdata.get("EL_LimitOfIndemnity")));
						String PL_LOI = formatter.format(Double.parseDouble((String)mdata.get("PL_IndemnityLimit")));
						String PRL_LOI = formatter.format(Double.parseDouble((String)mdata.get("PRL_IndemnityLimit")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Public Liability �"+PL_LOI), "Public Liability &pound;"+PL_LOI , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Products Liability �"+PRL_LOI), "Products Liability &pound;"+PRL_LOI , fileName);
						
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_SpecifiedAllRisks")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Insured"), "Specified All Risks Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO SPECIFIED ALL RISKS SECTION"), "APPENDIX TO SPECIFIED ALL RISKS SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Not Insured"), "Specified All Risks Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_ContractorsAllRisks")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Insured"), "Contractors All Risks Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CONTRACTORS ALL RISKS SECTION"), "APPENDIX TO CONTRACTORS ALL RISKS SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Not Insured"), "Contractors All Risks Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_ComputersandElectronicRisks")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Insured"), "Computers & Electronic Risks Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION"), "APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Computers"), "Subsection: Computers" , fileName);
						
						String CER_Sum_Ins = formatter.format(Double.parseDouble((String)mdata.get("CER_Computers_SumInsured")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Sum_Ins), "Sum Insured &pound;"+CER_Sum_Ins , fileName);
						
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: E Risk: Virus & Hacking"), "Subsection: E Risk: Virus & Hacking" , fileName);
						
						String CER_Erisk_Virus_LOI = formatter.format(Double.parseDouble((String)mdata.get("CER_Erisk_VirusHacking")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+CER_Erisk_Virus_LOI), "Limit of Liability &pound;"+CER_Erisk_Virus_LOI , fileName);
				
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Additional Expenditure"), "Subsection: Additional Expenditure" , fileName);
						
						String CER_Additional_Sum_Ins = formatter.format(Double.parseDouble((String)mdata.get("CER_AdditionalExp_SumInsured")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Additional_Sum_Ins), "Sum Insured &pound;"+CER_Additional_Sum_Ins , fileName);
				
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Not Insured"), "Computers & Electronic Risks Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Money")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Insured"), "Money Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MONEY SECTION"), "APPENDIX TO MONEY SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Bodily Injury Benefits"), "Bodily Injury Benefits" , fileName);
						
						String M_LossOfLimbs = formatter.format(Double.parseDouble((String)mdata.get("M_LossOfLimbs")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of limbs �"+M_LossOfLimbs), "Loss of limbs &pound;"+M_LossOfLimbs , fileName);
						
						String M_LossOfSight = formatter.format(Double.parseDouble((String)mdata.get("M_LossOfSight")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of sight �"+M_LossOfSight), "Loss of sight &pound;"+M_LossOfSight , fileName);
		
						String M_PermanentTotalDis = formatter.format(Double.parseDouble((String)mdata.get("M_PermanentTotalDis")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Permanent Total Disablement �"+M_PermanentTotalDis), "Permanent Total Disablement &pound;"+M_PermanentTotalDis , fileName);
		
						String M_TempTotalDisablement = formatter.format(Double.parseDouble((String)mdata.get("M_TempTotalDisablement")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Temporary Total Disablement (Up to 104 weeks) �"+M_TempTotalDisablement), "Temporary Total Disablement (Up to 104 weeks) &pound;"+M_TempTotalDisablement , fileName);
		
					
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Not Insured"), "Money Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_GoodsInTransit")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Insured"), "Goods In Transit Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO GOODS IN TRANSIT SECTION"), "APPENDIX TO GOODS IN TRANSIT SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Conveyance Limit of Liability"), "Conveyance Limit of Liability" , fileName);
						
						String GIT_AnyOnePostalPackage = formatter.format(Double.parseDouble((String)mdata.get("GIT_AnyOnePostalPackage")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one postal package �"+GIT_AnyOnePostalPackage), "Any one postal package &pound;"+GIT_AnyOnePostalPackage , fileName);
						
						String GIT_AnyOneConsignment = formatter.format(Double.parseDouble((String)mdata.get("GIT_AnyOneConsignment")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one consignment by rail or road �"+GIT_AnyOneConsignment), "Any one consignment by rail or road &pound;"+GIT_AnyOneConsignment , fileName);
		
					
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Not Insured"), "Goods In Transit Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_MarineCargo")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Marine Cargo Insured"), "Marine Cargo Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MARINE CARGO SECTION"), "APPENDIX TO MARINE CARGO SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Marine Cargo Not Insured"), "Marine Cargo Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_DirectorsandOfficers")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Insured"), "Directors & Officers Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION"), "APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Applicable Excess"), "Applicable Excess" , fileName);
						
						String DO_Excess = formatter.format(Double.parseDouble((String)mdata.get("DO_Excess")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors and Officers Liability �"+DO_Excess), "Directors and Officers Liability &pound;"+DO_Excess , fileName);
						
						String DO_CorporateLiabilityExcess = formatter.format(Double.parseDouble((String)mdata.get("DO_CorporateLiabilityExcess")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Corporate Liability �"+DO_CorporateLiabilityExcess), "Corporate Liability &pound;"+DO_CorporateLiabilityExcess , fileName);
						
						String DO_EPL_Excess = formatter.format(Double.parseDouble((String)mdata.get("DO_EPL_Excess")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employment Practices Liability �"+DO_EPL_Excess), "Employment Practices Liability &pound;"+DO_EPL_Excess , fileName);
					
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Not Insured"), "Directors & Officers Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_FrozenFood")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Frozen Foods Insured"), "Frozen Foods Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FROZEN FOODS SECTION"), "APPENDIX TO FROZEN FOODS SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Frozen Foods Not Insured"), "Frozen Foods Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_LossofLicence")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Licence Insured"), "Loss of Licence Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LOSS OF LICENCE SECTION"), "APPENDIX TO LOSS OF LICENCE SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Licence Not Insured"), "Loss of Licence Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_FidelityGuarantee")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Insured"), "Fidelity Guarantee Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FIDELITY GUARANTEE SECTION"), "APPENDIX TO FIDELITY GUARANTEE SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Not Insured"), "Fidelity Guarantee Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Terrorism")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
						if(((String)mdata.get("CD_MaterialDamage")).equals("Yes")){
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
						}
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_LegalExpenses")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
						
						String LE_LimitOfLiability = (String)mdata.get("LE_LimitOfLiability");
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
						
							
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
					}
					break;
				case "CCG":
					if(((String)mdata.get("CD_BusinessInterruption")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Insured"), "Business Interruption Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO BUSINESS INTERRUPTION SECTION"), "APPENDIX TO BUSINESS INTERRUPTION SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Not Insured"), "Business Interruption Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Liability")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
						String EL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("EL_LimitOfIndemnity")));
						String PL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PL_IndemnityLimit")));
						String PRL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PRL_IndemnityLimit")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Public Liability �"+PL_LOI), "Public Liability &pound;"+PL_LOI , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Products Liability �"+PRL_LOI), "Products Liability &pound;"+PRL_LOI , fileName);
						
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_SpecifiedAllRisks")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Insured"), "Specified All Risks Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO SPECIFIED ALL RISKS SECTION"), "APPENDIX TO SPECIFIED ALL RISKS SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Not Insured"), "Specified All Risks Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_ContractorsAllRisks")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Insured"), "Contractors All Risks Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CONTRACTORS ALL RISKS SECTION"), "APPENDIX TO CONTRACTORS ALL RISKS SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Not Insured"), "Contractors All Risks Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_ComputersandElectronicRisks")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Insured"), "Computers & Electronic Risks Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION"), "APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Computers"), "Subsection: Computers" , fileName);
						
						String CER_Sum_Ins = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_Computers_SumInsured")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Sum_Ins), "Sum Insured &pound;"+CER_Sum_Ins , fileName);
						
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: E Risk: Virus & Hacking"), "Subsection: E Risk: Virus & Hacking" , fileName);
						
						String CER_Erisk_Virus_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_Erisk_VirusHacking")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+CER_Erisk_Virus_LOI), "Limit of Liability &pound;"+CER_Erisk_Virus_LOI , fileName);
				
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Additional Expenditure"), "Subsection: Additional Expenditure" , fileName);
						
						String CER_Additional_Sum_Ins = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_AdditionalExp_SumInsured")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Additional_Sum_Ins), "Sum Insured &pound;"+CER_Additional_Sum_Ins , fileName);
				
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Not Insured"), "Computers & Electronic Risks Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Money")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Insured"), "Money Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MONEY SECTION"), "APPENDIX TO MONEY SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Bodily Injury Benefits"), "Bodily Injury Benefits" , fileName);
						
						String M_LossOfLimbs = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_LossOfLimbs")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of limbs �"+M_LossOfLimbs), "Loss of limbs &pound;"+M_LossOfLimbs , fileName);
						
						String M_LossOfSight = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_LossOfSight")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of sight �"+M_LossOfSight), "Loss of sight &pound;"+M_LossOfSight , fileName);
		
						String M_PermanentTotalDis = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_PermanentTotalDis")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Permanent Total Disablement �"+M_PermanentTotalDis), "Permanent Total Disablement &pound;"+M_PermanentTotalDis , fileName);
		
						String M_TempTotalDisablement = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_TempTotalDisablement")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Temporary Total Disablement (Up to 104 weeks) �"+M_TempTotalDisablement), "Temporary Total Disablement (Up to 104 weeks) &pound;"+M_TempTotalDisablement , fileName);
		
					
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Not Insured"), "Money Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_GoodsInTransit")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Insured"), "Goods In Transit Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO GOODS IN TRANSIT SECTION"), "APPENDIX TO GOODS IN TRANSIT SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Conveyance Limit of Liability"), "Conveyance Limit of Liability" , fileName);
						
						String GIT_AnyOnePostalPackage = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("GIT_AnyOnePostalPackage")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one postal package �"+GIT_AnyOnePostalPackage), "Any one postal package &pound;"+GIT_AnyOnePostalPackage , fileName);
						
						String GIT_AnyOneConsignment = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("GIT_AnyOneConsignment")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one consignment by rail or road �"+GIT_AnyOneConsignment), "Any one consignment by rail or road &pound;"+GIT_AnyOneConsignment , fileName);
		
					
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Not Insured"), "Goods In Transit Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_MarineCargo")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Marine Cargo Insured"), "Marine Cargo Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MARINE CARGO SECTION"), "APPENDIX TO MARINE CARGO SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Marine Cargo Not Insured"), "Marine Cargo Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_DirectorsandOfficers")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Insured"), "Directors & Officers Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION"), "APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Applicable Excess"), "Applicable Excess" , fileName);
						
						String DO_Excess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_Excess")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors and Officers Liability �"+DO_Excess), "Directors and Officers Liability &pound;"+DO_Excess , fileName);
						
						String DO_CorporateLiabilityExcess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_CorporateLiabilityExcess")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Corporate Liability �"+DO_CorporateLiabilityExcess), "Corporate Liability &pound;"+DO_CorporateLiabilityExcess , fileName);
						
						String DO_EPL_Excess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_EPL_Excess")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employment Practices Liability �"+DO_EPL_Excess), "Employment Practices Liability &pound;"+DO_EPL_Excess , fileName);
					
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Not Insured"), "Directors & Officers Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_FrozenFood")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Frozen Foods Insured"), "Frozen Foods Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FROZEN FOODS SECTION"), "APPENDIX TO FROZEN FOODS SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Frozen Foods Not Insured"), "Frozen Foods Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_LossofLicence")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Licence Insured"), "Loss of Licence Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LOSS OF LICENCE SECTION"), "APPENDIX TO LOSS OF LICENCE SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Licence Not Insured"), "Loss of Licence Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_FidelityGuarantee")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Insured"), "Fidelity Guarantee Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FIDELITY GUARANTEE SECTION"), "APPENDIX TO FIDELITY GUARANTEE SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Not Insured"), "Fidelity Guarantee Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Terrorism")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
						if(((String)mdata.get("CD_MaterialDamage")).equals("Yes")){
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
						}
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_LegalExpenses")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
						
						String LE_LimitOfLiability = (String)common.NB_excel_data_map.get("LE_LimitOfLiability");
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
						
							
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
					}
					break;
				case "CTA":
					if(((String)mdata.get("CD_BusinessInterruption")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Insured"), "Business Interruption Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO BUSINESS INTERRUPTION SECTION"), "APPENDIX TO BUSINESS INTERRUPTION SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Not Insured"), "Business Interruption Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Liability")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
						String EL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("EL_LimitOfIndemnity")));
						String PL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PL_IndemnityLimit")));
						String PRL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PRL_IndemnityLimit")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Public Liability �"+PL_LOI), "Public Liability &pound;"+PL_LOI , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Products Liability �"+PRL_LOI), "Products Liability &pound;"+PRL_LOI , fileName);
						
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_SpecifiedAllRisks")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Insured"), "Specified All Risks Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO SPECIFIED ALL RISKS SECTION"), "APPENDIX TO SPECIFIED ALL RISKS SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Not Insured"), "Specified All Risks Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_ContractorsAllRisks")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Insured"), "Contractors All Risks Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CONTRACTORS ALL RISKS SECTION"), "APPENDIX TO CONTRACTORS ALL RISKS SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Not Insured"), "Contractors All Risks Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_ComputersandElectronicRisks")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Insured"), "Computers & Electronic Risks Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION"), "APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Computers"), "Subsection: Computers" , fileName);
						
						String CER_Sum_Ins = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_Computers_SumInsured")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Sum_Ins), "Sum Insured &pound;"+CER_Sum_Ins , fileName);
						
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: E Risk: Virus & Hacking"), "Subsection: E Risk: Virus & Hacking" , fileName);
						
						String CER_Erisk_Virus_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_Erisk_VirusHacking")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+CER_Erisk_Virus_LOI), "Limit of Liability &pound;"+CER_Erisk_Virus_LOI , fileName);
				
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Additional Expenditure"), "Subsection: Additional Expenditure" , fileName);
						
						String CER_Additional_Sum_Ins = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_AdditionalExp_SumInsured")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Additional_Sum_Ins), "Sum Insured &pound;"+CER_Additional_Sum_Ins , fileName);
				
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Not Insured"), "Computers & Electronic Risks Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_Money")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Insured"), "Money Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MONEY SECTION"), "APPENDIX TO MONEY SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Bodily Injury Benefits"), "Bodily Injury Benefits" , fileName);
						
						String M_LossOfLimbs = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_LossOfLimbs")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of limbs �"+M_LossOfLimbs), "Loss of limbs &pound;"+M_LossOfLimbs , fileName);
						
						String M_LossOfSight = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_LossOfSight")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of sight �"+M_LossOfSight), "Loss of sight &pound;"+M_LossOfSight , fileName);
		
						String M_PermanentTotalDis = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_PermanentTotalDis")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Permanent Total Disablement �"+M_PermanentTotalDis), "Permanent Total Disablement &pound;"+M_PermanentTotalDis , fileName);
		
						String M_TempTotalDisablement = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_TempTotalDisablement")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Temporary Total Disablement (Up to 104 weeks) �"+M_TempTotalDisablement), "Temporary Total Disablement (Up to 104 weeks) &pound;"+M_TempTotalDisablement , fileName);
		
					
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Not Insured"), "Money Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_GoodsInTransit")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Insured"), "Goods In Transit Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO GOODS IN TRANSIT SECTION"), "APPENDIX TO GOODS IN TRANSIT SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Conveyance Limit of Liability"), "Conveyance Limit of Liability" , fileName);
						
						String GIT_AnyOnePostalPackage = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("GIT_AnyOnePostalPackage")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one postal package �"+GIT_AnyOnePostalPackage), "Any one postal package &pound;"+GIT_AnyOnePostalPackage , fileName);
						
						String GIT_AnyOneConsignment = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("GIT_AnyOneConsignment")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one consignment by rail or road �"+GIT_AnyOneConsignment), "Any one consignment by rail or road &pound;"+GIT_AnyOneConsignment , fileName);
		
					
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Not Insured"), "Goods In Transit Not Insured" , fileName);
					}
					
									
					if(((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_DirectorsandOfficers")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Insured"), "Directors & Officers Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION"), "APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Applicable Excess"), "Applicable Excess" , fileName);
						
						String DO_Excess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_Excess")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors and Officers Liability �"+DO_Excess), "Directors and Officers Liability &pound;"+DO_Excess , fileName);
						
						String DO_CorporateLiabilityExcess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_CorporateLiabilityExcess")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Corporate Liability �"+DO_CorporateLiabilityExcess), "Corporate Liability &pound;"+DO_CorporateLiabilityExcess , fileName);
						
						String DO_EPL_Excess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_EPL_Excess")));
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employment Practices Liability �"+DO_EPL_Excess), "Employment Practices Liability &pound;"+DO_EPL_Excess , fileName);
					
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Not Insured"), "Directors & Officers Not Insured" , fileName);
					}
					
					
										
					if(((String)mdata.get("CD_Terrorism")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
						if(((String)mdata.get("CD_MaterialDamage")).equals("Yes")){
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
						}
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
					}
					
					if(((String)mdata.get("CD_LegalExpenses")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
						
						String LE_LimitOfLiability = (String)common.NB_excel_data_map.get("LE_LimitOfLiability");
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
						
							
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
					}
					if(((String)mdata.get("CD_FidelityGuarantee")).equals("Yes")){
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Insured"), "Fidelity Guarantee Insured" , fileName);
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FIDELITY GUARANTEE SECTION"), "APPENDIX TO FIDELITY GUARANTEE SECTION" , fileName);
						
					}else{
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Not Insured"), "Fidelity Guarantee Not Insured" , fileName);
					}
					break;
					//SPI
				case "SPI":
					
					//For SPI MTA Flow 
					if(common.currentRunningFlow.equals("MTA")){
						
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Reason for Issue: "+(String)common.MTA_excel_data_map.get("MTA_Reason_for_Endorsement")), "Reason for Issue: "+(String)common.MTA_excel_data_map.get("MTA_Reason_for_Endorsement") , fileName);
						String Policy_Fee = (String)mdata.get("SP_AdminFeeSP");
						String formated_Policy_Fee = formatter.format(Double.parseDouble(Policy_Fee));
						
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Fee �"+formated_Policy_Fee), "Policy Fee = &pound;"+formated_Policy_Fee , fileName);
						
						if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Add")){
							try{
							String g_premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors PI").get("Gross Premium"));
							g_premium = g_premium.replaceAll(",", "");
							common_SPI.PI_pdf_GrossPremium = Double.parseDouble(g_premium);
							}catch(NullPointerException npe){
								common_SPI.PI_pdf_GrossPremium = 0.0;
							}
							
						}else if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Increase")){
							try{
								String g_premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors PI").get("Gross Premium"));
								g_premium = g_premium.replaceAll(",", "");
								common_SPI.PI_pdf_GrossPremium = Double.parseDouble(g_premium);
								}catch(NullPointerException npe){
									common_SPI.PI_pdf_GrossPremium = 0.0;
								}
						}else if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Remove")){
							try{
								String g_premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors PI").get("Gross Premium"));
								g_premium = g_premium.replaceAll(",", "");
								common_SPI.PI_pdf_GrossPremium = Double.parseDouble(g_premium);
								}catch(NullPointerException npe){
									common_SPI.PI_pdf_GrossPremium = 0.0;
								}
						}
							
							
						String formated_G_premium = formatter.format(common_SPI.PI_pdf_GrossPremium);
						
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Additional Premium �"+formated_G_premium), "Additional Premium = &pound;"+formated_G_premium , fileName);
					
						if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Add")){
							
							try{
							String ipt_tax = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors PI").get("Insurance Tax"));
							ipt_tax = ipt_tax.replaceAll(",", "");
							common_SPI.PI_pdf_InsuranceTax = Double.parseDouble(ipt_tax);
							}catch(NullPointerException npe){
								common_SPI.PI_pdf_InsuranceTax = 0.0;
							}
						}else if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Increase")){
							try{
								String ipt_tax = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors PI").get("Insurance Tax"));
								ipt_tax = ipt_tax.replaceAll(",", "");
								common_SPI.PI_pdf_InsuranceTax = Double.parseDouble(ipt_tax);
								}catch(NullPointerException npe){
									common_SPI.PI_pdf_InsuranceTax = 0.0;
								}
						}else if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Remove")){
							try{
								String ipt_tax = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors PI").get("Insurance Tax"));
								ipt_tax = ipt_tax.replaceAll(",", "");
								common_SPI.PI_pdf_InsuranceTax = Double.parseDouble(ipt_tax);
								}catch(NullPointerException npe){
									common_SPI.PI_pdf_InsuranceTax = 0.0;
								}
						}
						
						String formated_InsuranceTax = formatter.format(common_SPI.PI_pdf_InsuranceTax);
						
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Insurance Premium Tax �"+formated_InsuranceTax), "Insurance Premium Tax = &pound;"+formated_InsuranceTax , fileName);
						double total_p = Double.parseDouble(Policy_Fee) + common_SPI.PI_pdf_GrossPremium + common_SPI.PI_pdf_InsuranceTax;
						
						String total_Premium = formatter.format(total_p);
						String final_total_Premium = total_Premium.replaceAll(",", "");
						String formated_total_Premium = formatter.format(Double.parseDouble(final_total_Premium));
						
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("TOTAL �"+formated_total_Premium), "TOTAL &pound;"+formated_total_Premium , fileName);
						
					}else{
					
						if(((String)mdata.get("CD_SolicitorsPI")).equals("Yes")){
							fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Solicitors Professional Indemnity Primary Layer"), "Solicitors Professional Indemnity Primary Layer" , fileName);
						}
					
						String Policy_Fee = (String)mdata.get("SP_AdminFeeSP");
						String formated_Policy_Fee = formatter.format(Double.parseDouble(Policy_Fee));
					
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Fee �"+formated_Policy_Fee), "Policy Fee = &pound;"+formated_Policy_Fee , fileName);
					
						if(Integer.parseInt((String)mdata.get("PS_Duration"))!=365){
							String g_premium = Double.toString(common.transaction_Premium_Values.get("Solicitors PI").get("Gross Premium"));
							g_premium = g_premium.replaceAll(",", "");
							common_SPI.PI_pdf_GrossPremium = Double.parseDouble(g_premium);
						}
						
						String formated_G_premium = formatter.format(common_SPI.PI_pdf_GrossPremium);
					
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Premium �"+formated_G_premium), "Premium = &pound;"+formated_G_premium , fileName);
				
						if(Integer.parseInt((String)mdata.get("PS_Duration"))!=365){
							String ipt_tax = Double.toString(common.transaction_Premium_Values.get("Solicitors PI").get("Insurance Tax"));
							ipt_tax = ipt_tax.replaceAll(",", "");
							common_SPI.PI_pdf_InsuranceTax = Double.parseDouble(ipt_tax);
						}
					
						String formated_InsuranceTax = formatter.format(common_SPI.PI_pdf_InsuranceTax);
					
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Insurance Premium Tax �"+formated_InsuranceTax), "Insurance Premium Tax = &pound;"+formated_InsuranceTax , fileName);
						double total_p = Double.parseDouble(Policy_Fee) + common_SPI.PI_pdf_GrossPremium + common_SPI.PI_pdf_InsuranceTax;
					
						String total_Premium = formatter.format(total_p);
						String final_total_Premium = total_Premium.replaceAll(",", "");
						String formated_total_Premium = formatter.format(Double.parseDouble(final_total_Premium));
					
						fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("TOTAL �"+formated_total_Premium), "TOTAL &pound;"+formated_total_Premium , fileName);
					}
					break;
				default:
					break;
				}
				if(!TestBase.product.equals("SPI")){
				String total_Gross_Premium = (String)mdata.get("PS_Total_GP");
				String formated_G_premium = formatter.format(Double.parseDouble(total_Gross_Premium));
				
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Premium �"+formated_G_premium), "Premium = &pound;"+formated_G_premium , fileName);
				
				
				String total_I_tax = (String)mdata.get("PS_Total_GT");
				String formated_I_tax = formatter.format(Double.parseDouble(total_I_tax));
				
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Insurance Premium Tax �"+formated_I_tax), "Insurance Premium Tax = &pound;"+formated_I_tax , fileName);
				double total_p=(Double.parseDouble((String)mdata.get("PS_Total_GP"))) + (Double.parseDouble((String)mdata.get("PS_Total_GT")));
				
				String total_Premium = formatter.format(total_p);
				String final_total_Premium = total_Premium.replaceAll(",", "");
				String formated_total_Premium = formatter.format(Double.parseDouble(final_total_Premium));
				
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("TOTAL �"+formated_total_Premium), "TOTAL &pound;"+formated_total_Premium , fileName);
				}
				break;
				
				//**//
				//****************SPI**************************//
								//For SPI
							case "Quote Schedule":
								
								formatter = new DecimalFormat("#,###,###.##");
								incrementalDays = Integer.parseInt(CONFIG.getProperty("DaysIncrementNumber"));
								policyDuration = Integer.parseInt((String)mdata.get("PS_Duration"));
								fail_count=0;
										
								if(docType.contains("Draft")){
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains(" QUOTATION SCHEDULE"), "Document : QUOTATION SCHEDULE", fileName);
								}else{
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains(" POLICY SCHEDULE"), "Document : POLICY SCHEDULE", fileName);
								}
								
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("SOLICITORS PROFESSIONAL"), "Product name : SOLICITORS PROFESSIONAL" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INDEMNITY"), "Product name : INDEMNITY" , fileName);
								if(common.currentRunningFlow.equals("Renewal")){
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)mdata.get(common.currentRunningFlow+"_ClientName")), "Insured Name : "+(String)mdata.get(common.currentRunningFlow+"_ClientName") , fileName);
								}else{
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)mdata.get("NB_ClientName")), "Insured Name : "+(String)mdata.get("NB_ClientName") , fileName);
								}
								
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("QC_AgencyName")), "Agency name : "+(String)mdata.get("QC_AgencyName") , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Correspondence Address: "+(String)mdata.get("PG_Address")), "Correspondence Address : "+(String)mdata.get("PG_Address") , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Postcode: "+(String)mdata.get("PG_Postcode")), "Postcode: "+(String)mdata.get("PG_Postcode") , fileName);
								if(docType.contains("Draft")){
									if(common.currentRunningFlow.equals("Renewal")){
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quote Reference: "+(String)mdata.get(common.currentRunningFlow+"_QuoteNumber")) ,"Quote Reference : "+mdata.get(common.currentRunningFlow+"_QuoteNumber"),fileName);
									}else{
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quote Reference: "+(String)mdata.get("NB_QuoteNumber")) ,"Quote Reference : "+mdata.get("NB_QuoteNumber"),fileName);
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quotation Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays)) ,"Quote Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays),fileName);
									}
								}else{
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Number: "+(String)mdata.get("NB_PolicyNumber")) ,"Policy Number : "+mdata.get("NB_PolicyNumber"),fileName);
									if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0),fileName);
									}else{
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0),fileName);
										
									}
									
								}
								
								if(!common.currentRunningFlow.equals("Renewal")){
									if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0) , fileName);
									//	fail_count = fail_count + CommonFunction.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1) , fileName);
									}else{
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0) , fileName);
									//fail_count = fail_count + CommonFunction.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1) , fileName);
						
									}
								}
								//fail_count = fail_count + CommonFunction.verification(parsedText.contains("Business: "+(String)mdata.get("PD_BusinessDesc")), "Business: "+(String)mdata.get("PD_BusinessDesc") , fileName);
								//fail_count = fail_count + CommonFunction.verification(parsedText.contains("Sections Section Insured"), "Sections Section Insured" , fileName);
								
								
								
								
								/*total_Gross_Premium = (String)mdata.get("PS_Total_GP");
								formated_G_premium = formatter.format(Double.parseDouble(total_Gross_Premium));
								
								fail_count = fail_count + CommonFunction.verification(parsedText.contains("Premium �"+formated_G_premium), "Premium = &pound;"+formated_G_premium , fileName);
								
								
								total_I_tax = (String)mdata.get("PS_Total_GT");
								formated_I_tax = formatter.format(Double.parseDouble(total_I_tax));
								
								fail_count = fail_count + CommonFunction.verification(parsedText.contains("Insurance Premium Tax �"+formated_I_tax), "Insurance Premium Tax = &pound;"+formated_I_tax , fileName);
								total_p=(Double.parseDouble((String)mdata.get("PS_Total_GP"))) + (Double.parseDouble((String)mdata.get("PS_Total_GT")));
								
								total_Premium = formatter.format(total_p);
								final_total_Premium = total_Premium.replaceAll(",", "");
								formated_total_Premium = formatter.format(Double.parseDouble(final_total_Premium));
								
								fail_count = fail_count + CommonFunction.verification(parsedText.contains("TOTAL �"+formated_total_Premium), "TOTAL &pound;"+formated_total_Premium , fileName);*/
								break;
								
								//For SPI
							case "Order Form":
								
								formatter = new DecimalFormat("#,###,###.##");
								incrementalDays = Integer.parseInt(CONFIG.getProperty("DaysIncrementNumber"));
								policyDuration = Integer.parseInt((String)mdata.get("PS_Duration"));
								fail_count=0;
										
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains(" ORDER FORM"), "Document : ORDER FORM", fileName);
								
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("SOLICITORS"), "Product name : SOLICITORS" , fileName);
								if(common.currentRunningFlow.equals("Renewal"))
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Name of Firm: "+(String)mdata.get("Renewal_ClientName")), "Name of Firm : "+(String)mdata.get("Renewal_ClientName") , fileName);
								else
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Name of Firm: "+(String)mdata.get("NB_ClientName")), "Name of Firm : "+(String)mdata.get("NB_ClientName") , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Solicitors Regulation Authority:"), "Solicitors Regulation Authority:" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Professional Indemnity Primary Layer"), "Professional Indemnity Primary Layer" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Professional Indemnity Excess Layer"), "Professional Indemnity Excess Layer" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("STATEMENT OF FACT"), "STATEMENT OF FACT" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Management Liability (inc. Directors and Officers) Quotation Acceptance"), "Management Liability (inc. Directors and Officers) Quotation Acceptance" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Acceptance and Declaration"), "Acceptance and Declaration" , fileName);
								
								if(docType.contains("Draft")){
									if(common.currentRunningFlow.equals("Renewal")){
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quotation Ref: "+(String)mdata.get(common.currentRunningFlow+"_QuoteNumber")), "Quotation Ref: "+(String)mdata.get(common.currentRunningFlow+"_QuoteNumber"), fileName);
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quote Reference: "+(String)mdata.get(common.currentRunningFlow+"_QuoteNumber")) ,"Quote Reference : "+mdata.get(common.currentRunningFlow+"_QuoteNumber"),fileName);
									}else{
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quotation Ref: "+(String)mdata.get("NB_QuoteNumber")), "Quotation Ref: "+(String)mdata.get("NB_QuoteNumber"), fileName);
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quote Reference: "+(String)mdata.get("NB_QuoteNumber")) ,"Quote Reference : "+mdata.get("NB_QuoteNumber"),fileName);
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quotation Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays)) ,"Quote Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays),fileName);
									}
									//fail_count = fail_count + CommonFunction.verification(parsedText.contains("Quote Reference: "+(String)mdata.get("NB_QuoteNumber")) ,"Quote Reference : "+mdata.get("NB_QuoteNumber"),fileName);
									//fail_count = fail_count + CommonFunction.verification(parsedText.contains("Quotation Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays)) ,"Quote Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays),fileName);
								}
								
								
								break;
								
								//For SPI
							case "Financial Form":
								
								formatter = new DecimalFormat("#,###,###.##");
								incrementalDays = Integer.parseInt(CONFIG.getProperty("DaysIncrementNumber"));
								policyDuration = Integer.parseInt((String)mdata.get("PS_Duration"));
								fail_count=0;
										
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Premium Finance"), "Document : Financial Form", fileName);
								
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPLICATION FORM"), "Financial Form : APPLICATION FORM" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("What will it cost:"), "What will it cost:" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("What Pen Underwriting will do for you:"), "What Pen Underwriting will do for you:" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("What Close Brothers Premium Finance will do:"), "What Close Brothers Premium Finance will do:", fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("1 Firm Details"), "1 Firm Details" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Name of Firm"), "Name of Firm" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contact"), "Contact" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Address"), "Address" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Postcode"), "Postcode" , fileName);
								
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("2 Bank Details"), "2 Bank Details" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Account No"), "Account No" , fileName);
								fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Name of account holder"), "Name of account holder" , fileName);
								
								break;
								//SPI
							case "Policy Schedule Excess Layer":
								
								formatter = new DecimalFormat("#,###,###.##");
								incrementalDays = Integer.parseInt(CONFIG.getProperty("DaysIncrementNumber"));
								policyDuration = Integer.parseInt((String)mdata.get("PS_Duration"));
								
								fail_count=0;
										
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains(" POLICY SCHEDULE"), "Document : POLICY SCHEDULE", fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("SOLICITORS PROFESSIONAL"), "Product name : SOLICITORS PROFESSIONAL" , fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INDEMNITY � EXCESS LAYER"), "Product name : INDEMNITY � EXCESS LAYER" , fileName);
									if(common.currentRunningFlow.equals("Renewal"))
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)mdata.get(common.currentRunningFlow+"_ClientName")), "Insured Name : "+(String)mdata.get(common.currentRunningFlow+"_ClientName") , fileName);
									else
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)mdata.get("NB_ClientName")), "Insured Name : "+(String)mdata.get("NB_ClientName") , fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("QC_AgencyName")), "Agency name : "+(String)mdata.get("QC_AgencyName") , fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Correspondence Address: "+(String)mdata.get("PG_Address")), "Correspondence Address : "+(String)mdata.get("PD_Address") , fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Postcode: "+(String)mdata.get("PG_Postcode")), "Postcode: "+(String)mdata.get("PG_Postcode") , fileName);
									if(!common.currentRunningFlow.equals("Renewal")){
									if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0),fileName);
									}else{
										if(common.currentRunningFlow.equals("MTA")){
											fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)common.MTA_excel_data_map.get("MTA_EffectiveDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)common.MTA_excel_data_map.get("MTA_EffectiveDate"),0),fileName);
										}else{
											fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0),fileName);
										}
									}
									
									if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0) , fileName);
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1) , fileName);
									}else{
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0) , fileName);
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1) , fileName);
									}
									}
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Underlying Insurance Limit: "+(String)mdata.get("SEL_UILimit")), "Underlying Insurance Limit: "+(String)mdata.get("SEL_UILimit") , fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Underlying Insurance Company: "+(String)mdata.get("SEL_UICompany")), "Underlying Insurance Company: "+(String)mdata.get("SEL_UICompany") , fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Underlying Insurance Policy Number: "+(String)mdata.get("SEL_UIPolicyNumbe")), "Underlying Insurance Policy Number: "+(String)mdata.get("SEL_UIPolicyNumbe") , fileName);
									
									if(common.currentRunningFlow.equals("MTA")){
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Reason for Issue: "+(String)common.MTA_excel_data_map.get("MTA_Reason_for_Endorsement")), "Reason for Issue: "+(String)common.MTA_excel_data_map.get("MTA_Reason_for_Endorsement") , fileName);
										String Policy_Fee = (String)mdata.get("SEL_DefaultAdminFeeSEL");
										String formated_Policy_Fee = formatter.format(Double.parseDouble(Policy_Fee));
										
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Fee �"+formated_Policy_Fee), "Policy Fee = &pound;"+formated_Policy_Fee , fileName);
										
										if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Add")){
											try{
												String g_premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors excess layer").get("Gross Premium"));
												g_premium = g_premium.replaceAll(",", "");
												common_SPI.SEL_pdf_GrossPremium = Double.parseDouble(g_premium);
												}catch(NullPointerException npe){
													common_SPI.SEL_pdf_GrossPremium = 0.0;
												}
											
										}else if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Increase")){
											try{
											String g_premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors excess layer").get("Gross Premium"));
											g_premium = g_premium.replaceAll(",", "");
											common_SPI.SEL_pdf_GrossPremium = Double.parseDouble(g_premium);
											}catch(NullPointerException npe){
												common_SPI.SEL_pdf_GrossPremium = 0.0;
											}
										}else if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Remove")){
											try{
												String g_premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors excess layer").get("Gross Premium"));
												g_premium = g_premium.replaceAll(",", "");
												common_SPI.SEL_pdf_GrossPremium = Double.parseDouble(g_premium);
												}catch(NullPointerException npe){
													common_SPI.SEL_pdf_GrossPremium = 0.0;
												}
										}
											
										String formated_G_premium = formatter.format(common_SPI.SEL_pdf_GrossPremium);
										
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Additional Premium �"+formated_G_premium), "Additional Premium = &pound;"+formated_G_premium , fileName);
										
										//if(Integer.parseInt((String)common.NB_excel_data_map.get("PS_Duration"))!=365){
											//String ipt_tax = Double.toString(common_SPI.transaction_Premium_Values.get("Solicitors excess layer").get("Insurance Tax"));
											//ipt_tax = ipt_tax.replaceAll(",", "");
										if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Add")){
											try{
												String g_premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors excess layer").get("Insurance Tax"));
												g_premium = g_premium.replaceAll(",", "");
												common_SPI.SEL_pdf_InsuranceTax = Double.parseDouble(g_premium);
												}catch(NullPointerException npe){
													common_SPI.SEL_pdf_InsuranceTax = 0.0;
												}
											
										}else if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Increase")){
											try{
											String g_premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors excess layer").get("Insurance Tax"));
											g_premium = g_premium.replaceAll(",", "");
											common_SPI.SEL_pdf_InsuranceTax = Double.parseDouble(g_premium);
											}catch(NullPointerException npe){
												common_SPI.SEL_pdf_InsuranceTax = 0.0;
											}
										}else if(((String)common.MTA_excel_data_map.get("CD_MTAAddRemoveCover")).equals("Remove")){
											try{
												String g_premium = Double.toString(common.transaction_Details_Premium_Values.get("Solicitors excess layer").get("Insurance Tax"));
												g_premium = g_premium.replaceAll(",", "");
												common_SPI.SEL_pdf_InsuranceTax = Double.parseDouble(g_premium);
												}catch(NullPointerException npe){
													common_SPI.SEL_pdf_InsuranceTax = 0.0;
												}
										}
										//}
										
										String formated_InsuranceTax = formatter.format(common_SPI.SEL_pdf_InsuranceTax);
										
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Insurance Premium Tax �"+formated_InsuranceTax), "Insurance Premium Tax = &pound;"+formated_InsuranceTax , fileName);
										double total_p = Double.parseDouble(Policy_Fee) + common_SPI.SEL_pdf_GrossPremium + common_SPI.SEL_pdf_InsuranceTax;
										
										String total_Premium = formatter.format(total_p);
										String final_total_Premium = total_Premium.replaceAll(",", "");
										String formated_total_Premium = formatter.format(Double.parseDouble(final_total_Premium));
										
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("TOTAL �"+formated_total_Premium), "TOTAL &pound;"+formated_total_Premium , fileName);
										
									}else{
										String Policy_Fee = (String)mdata.get("SEL_AdminFeeSEL");
										String formated_Policy_Fee = formatter.format(Double.parseDouble(Policy_Fee));
									
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Fee �"+formated_Policy_Fee), "Policy Fee = &pound;"+formated_Policy_Fee , fileName);
									
										if(Integer.parseInt((String)mdata.get("PS_Duration"))!=365){
											String g_premium = Double.toString(common.transaction_Premium_Values.get("Solicitors excess layer").get("Gross Premium"));
											g_premium = g_premium.replaceAll(",", "");
											common_SPI.SEL_pdf_GrossPremium = Double.parseDouble(g_premium);
										}
									
										String formated_G_premium = formatter.format(common_SPI.SEL_pdf_GrossPremium);
									
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Premium �"+formated_G_premium), "Premium = &pound;"+formated_G_premium , fileName);
									
										if(Integer.parseInt((String)mdata.get("PS_Duration"))!=365){
											String ipt_tax = Double.toString(common.transaction_Premium_Values.get("Solicitors excess layer").get("Insurance Tax"));
											ipt_tax = ipt_tax.replaceAll(",", "");
											common_SPI.SEL_pdf_InsuranceTax = Double.parseDouble(ipt_tax);
										}
									
										String formated_InsuranceTax = formatter.format(common_SPI.SEL_pdf_InsuranceTax);
									
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Insurance Premium Tax �"+formated_InsuranceTax), "Insurance Premium Tax = &pound;"+formated_InsuranceTax , fileName);
										double total_p = Double.parseDouble(Policy_Fee) + common_SPI.SEL_pdf_GrossPremium + common_SPI.SEL_pdf_InsuranceTax;
									
										String total_Premium = formatter.format(total_p);
										String final_total_Premium = total_Premium.replaceAll(",", "");
										String formated_total_Premium = formatter.format(Double.parseDouble(final_total_Premium));
									
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("TOTAL �"+formated_total_Premium), "TOTAL &pound;"+formated_total_Premium , fileName);
									
									}
								break;
								//SPI
							case "Certificate of Insurance":
								
								formatter = new DecimalFormat("#,###,###.##");
								incrementalDays = Integer.parseInt(CONFIG.getProperty("DaysIncrementNumber"));
								policyDuration = Integer.parseInt((String)mdata.get("PS_Duration"));
								
								fail_count=0;
										
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("CERTIFICATE OF INSURANCE"), "Document : CERTIFICATE OF INSURANCE", fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("CERTIFICATE OF QUALIFYING INSURANCE"), "CERTIFICATE OF QUALIFYING INSURANCE" , fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INDEMNITY YEAR"), "INDEMNITY YEAR" , fileName);
									fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("PRINCIPAL ADDRESS OF "+(String)mdata.get("PG_Address")), "PRINCIPAL ADDRESS OF "+(String)mdata.get("PG_Address") , fileName);
									if(common.currentRunningFlow.equals("Renewal")){
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("NAME OF INSURED FIRM: "+(String)mdata.get(common.currentRunningFlow+"_ClientName")), "NAME OF INSURED FIRM: "+(String)mdata.get(common.currentRunningFlow+"_ClientName") , fileName);
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("POLICY NUMBER: "+(String)mdata.get(common.currentRunningFlow+"_PolicyNumber")) ,"POLICY NUMBER: "+mdata.get(common.currentRunningFlow+"_PolicyNumber"),fileName);
								
									}else{
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("NAME OF INSURED FIRM: "+(String)mdata.get("NB_ClientName")), "NAME OF INSURED FIRM: "+(String)mdata.get("NB_ClientName") , fileName);
										fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("POLICY NUMBER: "+(String)mdata.get("NB_PolicyNumber")) ,"POLICY NUMBER: "+mdata.get("NB_PolicyNumber"),fileName);
									}
									
								
								break;
		
			case "Statement of Fact":
				
				fail_count=0;
				
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("pdf_ProductName")), "Product name :"+(String)mdata.get("pdf_ProductName") , fileName);
				/*if(!common.product.equalsIgnoreCase("CCF")){*/
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)mdata.get(common.currentRunningFlow+"_ClientName")), "Insured Name : "+(String)mdata.get(common.currentRunningFlow+"_ClientName") , fileName);
				/*}*/
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("QC_AgencyName")), "Agency name : "+(String)mdata.get("QC_AgencyName") , fileName);
				break;
				
			case "Policy Wording":
				
				TestUtil.reportStatus(fileName+" Verification Not in Scope . ", "Info", true);
				break;
				
			case "Employers Liability Certificate":
				
				TestUtil.reportStatus(fileName+" Verification Not in Scope . ", "Info", true);
				break;
		
		}
		
		return fail_count;
		
	}
		

/**
 * 
 * PDF Verifications Functions for REWIND Flow.
 * 
 * 
 * 
 */


public boolean funcPDFdocumentVerification_Rewind(String docType){
	boolean retvalue = true;
	int doc_fail_count = 0;
	err_count=0;
	Map<Object,Object> mdata =  null;
	switch (common.currentRunningFlow) {
	case "Renewal":
		mdata = common.Renewal_excel_data_map;
	break;
	default:
		mdata=common.NB_excel_data_map;
	break;

	}
	
	try{
		if(((String)mdata.get("DocumentVerification")).equals("No")){
			TestUtil.reportStatus("<b> PDF document verification is 'No' hence skipped verification . ", "Info", false);
			TestUtil.reportStatus("<b> Total count of document verification is : [ 0 ]</b>", "Info", false);
		}else{
			customAssert.assertTrue(common.funcButtonSelection(docType) , "Unable to click on <b>[  "+docType+"  ]</b>.");
			doc_fail_count = doc_fail_count + iteratePDFDocuments_Rewind(docType);
			customAssert.SoftAssertEquals(doc_fail_count, 0,"Verification failure in "+docType);
			final_err_pdf_count = final_err_pdf_count + doc_fail_count;
			TestUtil.reportStatus(docType+" verification is Completed .", "Info", true);
			customAssert.assertTrue(k.Click("Tax_adj_BackBtn"), "Unable to click on back button.");
		}
		
		
		return retvalue;
	}
	catch(Throwable t) {
	    String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
	    Assert.fail("Unable to do verification for documents. \n", t);
	    return false;
	}

}
		
/*
* PDF verification related functions:
* 
*  @Param docType - Either "Draft Documents" OR "Documents"
*  @Variable o - Number of documents
*  @Variable count - Documents data verification failure count
*  @Variable counter - incremental count for downloading same file (Used in PDFFileHandling method)
*/

public int iteratePDFDocuments_Rewind(String docType) throws ParseException, IOException, InterruptedException{
try{
	Map<Object,Object> mdata =  null;
	switch (common.currentRunningFlow) {
	case "Renewal":
		mdata = common.Renewal_excel_data_map;
	break;
	default:
		mdata=common.NB_excel_data_map;
	break;
	}
	List<WebElement> l_row = driver.findElements(By.xpath("html/body/div[3]/form/div/div[2]/table/tbody/tr"));
	int row_size = l_row.size();
	if(row_size>0){
		for(int r=0;r<row_size;r++){
			String doc_name = l_row.get(r).getText();
			if(((String)mdata.get("pdf_"+doc_name)).equals("Yes")){
				l_row.get(r).click();
				k.waitFiveSeconds();
				TestUtil.reportStatus("Document -"+doc_name+" is present.", "info", true);
				counter = 0;
				pdf_count++;
				err_count = err_count + PDFFileHandling_Rewind(doc_name,docType);
			
				l_row = driver.findElements(By.xpath("html/body/div[3]/form/div/div[2]/table/tbody/tr"));
			}else{
				continue;
			}
		}
	}
	TestUtil.reportStatus("Total count of <b>[  "+docType+" is : "+pdf_count+"  ]</b>", "Info", false);
	}catch(Throwable t)
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TestUtil.reportFunctionFailed("Failed in "+methodName+" function");   
		TestUtil.reportStatus("<p style='color:red'> Error in iteratePDFDocuments Method </p>", "Fail", true);
	 	return 1;
	}
	return err_count;
}
	
public int PDFFileHandling_Rewind(String fileName,String docType) throws IOException, ParseException, InterruptedException{
String file_Name=null;
String PDFCodePath = null;
String fileCode=null;
int dataVerificationFailureCount = 0;
String code = CommonFunction_GUS.product;
try{
	//TestUtil.reportStatus(fileName+" document verification is started for product - [<b>"+code+"</b>] ", "Info", false);
	String PDFPath= workDir+"\\src\\com\\selenium\\Execution_Report\\Report\\PDF";
	PDFCodePath = PDFPath+"\\"+code;
	File pdfFldr = new File(PDFPath);
	File pdfCodeFldr=new File(PDFCodePath);
	if(!pdfFldr.exists() && !pdfFldr.isDirectory()){
		pdfFldr.mkdir();
		}
	if(!pdfCodeFldr.exists() && !pdfCodeFldr.isDirectory()){
		pdfCodeFldr.mkdir();
		
	}
	
	fileCode = downloadPDF(code,fileName);
	Thread.sleep(4000);
	file_Name = PDFCodePath+"\\"+fileCode+".pdf";
	//System.out.println(file_Name);
	
	File file = new File(file_Name);
	FileInputStream fis = new FileInputStream(file);
	TestUtil.reportStatus(fileName+" file is downloaded to the specified folder and ready for verification.", "Info", false);
	
	dataVerificationFailureCount = dataVerificationFailureCount + PDFDataVerification_Rewind(fis,fileName,docType);
	TestUtil.reportStatus("<b> Total count of document verification is : [ "+pdf_count+" ]</b>", "Info", false);
	
	}
	
	// Below code will handle PDF failure up to 3 chance.
	catch(FileNotFoundException fnf)
	{
		
		if(counter==3){
			TestUtil.reportStatus("<b>Due to some reason , Not able to downalod -[  "+fileName+"  ]. 3 times tried to download his file .</b>", "Info", false);
		}else{
			counter++;
			TestUtil.reportStatus("Due to some reason , Not able to downalod - "+fileName+" . Retried downloading.", "Info", false);
			PDFFileHandling(fileName,docType);
		}
	return 0;
	}
	catch(NullPointerException npe)
	{
				TestUtil.reportStatus("Data Issue while verification . ", "Fail", false);
				return 1;
	}
		
	
	catch(Exception ex)
	{
		if(counter==3){
			TestUtil.reportStatus("<b>Due to some reason , Not able to downalod -[  "+fileName+"  ]. 3 times tried to download his file .</b>", "Info", false);
	}else{
		counter++;
		TestUtil.reportStatus("Due to some reason , Not able to downalod - "+fileName+" . Retried downloading.", "Info", false);
		PDFFileHandling(fileName,docType);
	}
    return 0;
	}

customAssert.assertTrue(fileDeletion(PDFCodePath) , "Unable to delete extra pdf files from folder : "+PDFCodePath);

return dataVerificationFailureCount;
	
}
	
/**
* @param code
* @param fileName
* @return
* @throws InterruptedException
* @throws IOException
*//*
public String downloadPDF(String code,String fileName) throws InterruptedException, IOException {

String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
try{
 
	WebDriverWait wait = new WebDriverWait(driver, 50); 
	WebElement menuItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='mainpanel']/div[3]/object")));  // until this submenu is found
 
     if(menuItem!=null && menuItem.isDisplayed()){
           
           driver.findElement(By.xpath("//*[@id='mainpanel']/div[3]/object")).click();
           Actions action = new Actions(driver);
           Thread.sleep(10000);
           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
           action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
           action.sendKeys(Keys.ENTER).perform();
           k.waitTenSeconds();
           
           String fileCode = code+"_"+fileName+"_"+timeStamp;
           
           String[] parms = {"wscript", workDir+"\\src\\BatFiles\\CloseDialog.vbs", workDir+"\\src\\com\\selenium\\Execution_Report\\Report\\PDF\\"+code+"\\"+fileCode};
           Runtime.getRuntime().exec(parms);
           return fileCode;
     }else{
           
           return "";
     }
}catch(Exception e){
       String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
       TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
       TestUtil.reportStatus("<p style='color:red'> PDF Document is not visible for download </p>", "Fail", true);
       return "";
}
}*/
	
/**
* @param fis - Downloaded file referance
* @param fileName - e.g. Policy Schedule
* @param docType - Draft Documents/Documents
*/

public int PDFDataVerification_Rewind(FileInputStream fis,String fileName,String docType) throws IOException, ParseException, InterruptedException {

String parsedText=null;
int fail_count=0;
PDFParser parser = new PDFParser(fis);
parser.parse();
COSDocument cosDoc = parser.getDocument();
PDDocument pdDoc = new PDDocument(cosDoc);
PDFTextStripper pdfStripper = new PDFTextStripper();

parsedText = pdfStripper.getText(pdDoc);
int count = pdDoc.getNumberOfPages();
pdfStripper.setStartPage(1);
pdfStripper.setEndPage(count);
Map<Object,Object> mdata =  null;

switch (common.currentRunningFlow) {
	case "Renewal":
		mdata = common.Renewal_excel_data_map;
	break;
	default:
		mdata=common.NB_excel_data_map;
	break;

}
//System.out.println(parsedText);
switch(fileName){

	case "Policy Schedule":
		
		DecimalFormat formatter = new DecimalFormat("#,###,###.##");
		int incrementalDays = Integer.parseInt(CONFIG.getProperty("DaysIncrementNumber"));
		int policyDuration = Integer.parseInt((String)mdata.get("PS_Duration"));
		fail_count=0;
		
		
		if(docType.contains("Draft")){
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains(" QUOTATION SUMMARY"), "Document : QUOTATION SUMMARY", fileName);
		}else{
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains(" POLICY SCHEDULE"), "Document : POLICY SCHEDULE", fileName);
		}
		if(TestBase.product.equals("SPI")){
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("SOLICITORS PROFESSIONAL"), "Product name : SOLICITORS PROFESSIONAL" , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INDEMNITY"), "Product name : INDEMNITY" , fileName);
		}else{
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("pdf_ProductName")), "Product name : "+(String)mdata.get("pdf_ProductName") , fileName);
		}
		if(common.currentRunningFlow.equals("Renewal")){
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)common.Renewal_excel_data_map.get(common.currentRunningFlow+"_ClientName")), "Insured Name : "+(String)common.Renewal_excel_data_map.get(common.currentRunningFlow+"_ClientName") , fileName);
		}else{
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)mdata.get("NB_ClientName")), "Insured Name : "+(String)mdata.get("NB_ClientName") , fileName);
		}
		fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("QC_AgencyName")), "Agency name : "+(String)mdata.get("QC_AgencyName") , fileName);
		if(TestBase.product.equals("SPI")){
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Correspondence Address: "+(String)mdata.get("PG_Address")), "Correspondence Address : "+(String)mdata.get("PD_Address") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Postcode: "+(String)mdata.get("PG_Postcode")), "Postcode: "+(String)mdata.get("PG_Postcode") , fileName);
			
		}else{
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Correspondence Address: "+(String)mdata.get("PD_Address")), "Correspondence Address : "+(String)mdata.get("PD_Address") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Postcode: "+(String)mdata.get("PD_Postcode")), "Postcode: "+(String)mdata.get("PD_Postcode") , fileName);
			
		}
			
		if(docType.contains("Draft")){
			if(!common.currentRunningFlow.equalsIgnoreCase("Renewal")){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quote Reference: "+(String)mdata.get("NB_QuoteNumber")) ,"Quote Reference : "+mdata.get("NB_QuoteNumber"),fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quotation Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays)) ,"Quote Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), -incrementalDays),fileName);
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Quote Reference: "+(String)common.Renewal_excel_data_map.get(common.currentRunningFlow+"_QuoteNumber")) ,"Quote Reference : "+common.Renewal_excel_data_map.get(common.currentRunningFlow+"_QuoteNumber"),fileName);
			}
		}else{
			if(!common.currentRunningFlow.equalsIgnoreCase("Renewal")){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Number: "+(String)mdata.get("NB_PolicyNumber")) ,"Policy Number : "+mdata.get("NB_PolicyNumber"),fileName);
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Number: "+(String)mdata.get(common.currentRunningFlow+"_PolicyNumber")) ,"Policy Number : "+mdata.get(common.currentRunningFlow+"_PolicyNumber"),fileName);
			}
			if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0),fileName);
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0),fileName);
			}
		}
		
		if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0) , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1) , fileName);
		}else{
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0) , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1) , fileName);

		}
		if(!TestBase.product.equals("SPI")){
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business: "+(String)mdata.get("PD_BusinessDesc")), "Business: "+(String)mdata.get("PD_BusinessDesc") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sections Section Insured"), "Sections Section Insured" , fileName);
			
			if(((((String)mdata.get("CD_MaterialDamage")).equals("Yes") && !((String)mdata.get("CD_Add_MaterialDamage")).equals("No")) || ((String)mdata.get("CD_Add_MaterialDamage")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Material Damage Insured"), "Material Damage Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MATERIAL DAMAGE SECTION"), "APPENDIX TO MATERIAL DAMAGE SECTION" , fileName);
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Material Damage Not Insured"), "Material Damage Not Insured" , fileName);
			}
		}
		
		
		switch (CommonFunction_GUS.product) {
		case "POB":
			if(((((String)mdata.get("CD_LossOfRentalIncome")).equals("Yes") && !((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("No"))  || ((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Rental Income Insured"), "Loss of Rental Income Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LOSS OF RENTAL INCOME SECTION"), "APPENDIX TO LOSS OF RENTAL INCOME SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Rental Income Not Insured"), "Loss of Rental Income Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Liability")).equals("Yes") && !((String)mdata.get("CD_Add_Liability")).equals("No"))   || ((String)mdata.get("CD_Add_Liability")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
				String EL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("EL_LimitOfIndemnity")));
				String POL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("POL_IndemnityLimit")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Property Owners Liability �"+POL_LOI), "Property Owners Liability &pound;"+POL_LOI , fileName);
				
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes") && !((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("No"))    || ((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Terrorism")).equals("Yes") && !((String)mdata.get("CD_Add_Terrorism")).equals("No")) || ((String)mdata.get("CD_Add_Terrorism")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
				if(((((String)mdata.get("CD_MaterialDamage")).equals("Yes") && !((String)mdata.get("CD_Add_MaterialDamage")).equals("No")) || ((String)mdata.get("CD_Add_MaterialDamage")).equals("Yes"))){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
				}
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_LegalExpenses")).equals("Yes") && !((String)mdata.get("CD_Add_LegalExpenses")).equals("No"))|| ((String)mdata.get("CD_Add_LegalExpenses")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
				
				String LE_LimitOfLiability = (String)common.NB_excel_data_map.get("LE_LimitOfLiability");
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
				
					
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
			}
			break;
		case "POC":
			if(((((String)mdata.get("CD_LossOfRentalIncome")).equals("Yes") && !((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("No"))|| ((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Rental Income Insured"), "Loss of Rental Income Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LOSS OF RENTAL INCOME SECTION"), "APPENDIX TO LOSS OF RENTAL INCOME SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Rental Income Not Insured"), "Loss of Rental Income Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Liability")).equals("Yes") && !((String)mdata.get("CD_Add_Liability")).equals("No"))|| ((String)mdata.get("CD_Add_Liability")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
				String EL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("EL_LimitOfIndemnity")));
				String POL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("POL_IndemnityLimit")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Property Owners Liability �"+POL_LOI), "Property Owners Liability &pound;"+POL_LOI , fileName);
				
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes")&& !((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("No"))|| ((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Terrorism")).equals("Yes")&& !((String)mdata.get("CD_Add_Terrorism")).equals("No"))|| ((String)mdata.get("CD_Add_Terrorism")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
				if(((((String)mdata.get("CD_MaterialDamage")).equals("Yes")&& !((String)mdata.get("CD_Add_MaterialDamage")).equals("No"))|| ((String)mdata.get("CD_Add_MaterialDamage")).equals("Yes"))){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
				}
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_LegalExpenses")).equals("Yes")&& !((String)mdata.get("CD_Add_LegalExpenses")).equals("No"))|| ((String)mdata.get("CD_Add_LegalExpenses")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
				
				String LE_LimitOfLiability = (String)common.NB_excel_data_map.get("LE_LimitOfLiability");
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
				
					
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
			}
			break;	
		case "CCF":
			if(((((String)mdata.get("CD_BusinessInterruption")).equals("Yes")&& !((String)mdata.get("CD_Add_BusinessInterruption")).equals("No"))|| ((String)mdata.get("CD_Add_BusinessInterruption")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Insured"), "Business Interruption Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO BUSINESS INTERRUPTION SECTION"), "APPENDIX TO BUSINESS INTERRUPTION SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Not Insured"), "Business Interruption Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Liability")).equals("Yes")&& !((String)mdata.get("CD_Add_Liability")).equals("No"))|| ((String)mdata.get("CD_Add_Liability")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
				String EL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("EL_LimitOfIndemnity")));
				String PL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PL_IndemnityLimit")));
				String PRL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PRL_IndemnityLimit")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Public Liability �"+PL_LOI), "Public Liability &pound;"+PL_LOI , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Products Liability �"+PRL_LOI), "Products Liability &pound;"+PRL_LOI , fileName);
				
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_SpecifiedAllRisks")).equals("Yes")&& !((String)mdata.get("CD_Add_SpecifiedAllRisks")).equals("No"))|| ((String)mdata.get("CD_Add_SpecifiedAllRisks")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Insured"), "Specified All Risks Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO SPECIFIED ALL RISKS SECTION"), "APPENDIX TO SPECIFIED ALL RISKS SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Not Insured"), "Specified All Risks Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_ContractorsAllRisks")).equals("Yes")&& !((String)mdata.get("CD_Add_ContractorsAllRisks")).equals("No"))|| ((String)mdata.get("CD_Add_ContractorsAllRisks")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Insured"), "Contractors All Risks Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CONTRACTORS ALL RISKS SECTION"), "APPENDIX TO CONTRACTORS ALL RISKS SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Not Insured"), "Contractors All Risks Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_ComputersandElectronicRisks")).equals("Yes")&& !((String)mdata.get("CD_Add_ComputersandElectronicRisks")).equals("No"))|| ((String)mdata.get("CD_Add_ComputersandElectronicRisks")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Insured"), "Computers & Electronic Risks Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION"), "APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Computers"), "Subsection: Computers" , fileName);
				
				String CER_Sum_Ins = formatter.format(Double.parseDouble((String)mdata.get("CER_Computers_SumInsured")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Sum_Ins), "Sum Insured &pound;"+CER_Sum_Ins , fileName);
				
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: E Risk: Virus & Hacking"), "Subsection: E Risk: Virus & Hacking" , fileName);
				
				String CER_Erisk_Virus_LOI = formatter.format(Double.parseDouble((String)mdata.get("CER_Erisk_VirusHacking")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+CER_Erisk_Virus_LOI), "Limit of Liability &pound;"+CER_Erisk_Virus_LOI , fileName);
		
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Additional Expenditure"), "Subsection: Additional Expenditure" , fileName);
				
				String CER_Additional_Sum_Ins = formatter.format(Double.parseDouble((String)mdata.get("CER_AdditionalExp_SumInsured")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Additional_Sum_Ins), "Sum Insured &pound;"+CER_Additional_Sum_Ins , fileName);
		
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Not Insured"), "Computers & Electronic Risks Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Money")).equals("Yes")&& !((String)mdata.get("CD_Add_Money")).equals("No"))|| ((String)mdata.get("CD_Add_Money")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Insured"), "Money Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MONEY SECTION"), "APPENDIX TO MONEY SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Bodily Injury Benefits"), "Bodily Injury Benefits" , fileName);
				
				String M_LossOfLimbs = formatter.format(Double.parseDouble((String)mdata.get("M_LossOfLimbs")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of limbs �"+M_LossOfLimbs), "Loss of limbs &pound;"+M_LossOfLimbs , fileName);
				
				String M_LossOfSight = formatter.format(Double.parseDouble((String)mdata.get("M_LossOfSight")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of sight �"+M_LossOfSight), "Loss of sight &pound;"+M_LossOfSight , fileName);

				String M_PermanentTotalDis = formatter.format(Double.parseDouble((String)mdata.get("M_PermanentTotalDis")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Permanent Total Disablement �"+M_PermanentTotalDis), "Permanent Total Disablement &pound;"+M_PermanentTotalDis , fileName);

				String M_TempTotalDisablement = formatter.format(Double.parseDouble((String)mdata.get("M_TempTotalDisablement")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Temporary Total Disablement (Up to 104 weeks) �"+M_TempTotalDisablement), "Temporary Total Disablement (Up to 104 weeks) &pound;"+M_TempTotalDisablement , fileName);

			
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Not Insured"), "Money Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_GoodsInTransit")).equals("Yes")&& !((String)mdata.get("CD_Add_GoodsInTransit")).equals("No"))|| ((String)mdata.get("CD_Add_GoodsInTransit")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Insured"), "Goods In Transit Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO GOODS IN TRANSIT SECTION"), "APPENDIX TO GOODS IN TRANSIT SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Conveyance Limit of Liability"), "Conveyance Limit of Liability" , fileName);
				
				String GIT_AnyOnePostalPackage = formatter.format(Double.parseDouble((String)mdata.get("GIT_AnyOnePostalPackage")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one postal package �"+GIT_AnyOnePostalPackage), "Any one postal package &pound;"+GIT_AnyOnePostalPackage , fileName);
				
				String GIT_AnyOneConsignment = formatter.format(Double.parseDouble((String)mdata.get("GIT_AnyOneConsignment")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one consignment by rail or road �"+GIT_AnyOneConsignment), "Any one consignment by rail or road &pound;"+GIT_AnyOneConsignment , fileName);

			
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Not Insured"), "Goods In Transit Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_MarineCargo")).equals("Yes")&& !((String)mdata.get("CD_Add_MarineCargo")).equals("No"))|| ((String)mdata.get("CD_Add_MarineCargo")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Marine Cargo Insured"), "Marine Cargo Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MARINE CARGO SECTION"), "APPENDIX TO MARINE CARGO SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Marine Cargo Not Insured"), "Marine Cargo Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes")&& !((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("No"))|| ((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_DirectorsandOfficers")).equals("Yes")&& !((String)mdata.get("CD_Add_DirectorsandOfficers")).equals("No"))|| ((String)mdata.get("CD_Add_DirectorsandOfficers")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Insured"), "Directors & Officers Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION"), "APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Applicable Excess"), "Applicable Excess" , fileName);
				
				String DO_Excess = formatter.format(Double.parseDouble((String)mdata.get("DO_Excess")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors and Officers Liability �"+DO_Excess), "Directors and Officers Liability &pound;"+DO_Excess , fileName);
				
				String DO_CorporateLiabilityExcess = formatter.format(Double.parseDouble((String)mdata.get("DO_CorporateLiabilityExcess")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Corporate Liability �"+DO_CorporateLiabilityExcess), "Corporate Liability &pound;"+DO_CorporateLiabilityExcess , fileName);
				
				String DO_EPL_Excess = formatter.format(Double.parseDouble((String)mdata.get("DO_EPL_Excess")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employment Practices Liability �"+DO_EPL_Excess), "Employment Practices Liability &pound;"+DO_EPL_Excess , fileName);
			
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Not Insured"), "Directors & Officers Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_FrozenFood")).equals("Yes")&& !((String)mdata.get("CD_Add_FrozenFood")).equals("No"))|| ((String)mdata.get("CD_Add_FrozenFood")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Frozen Foods Insured"), "Frozen Foods Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FROZEN FOODS SECTION"), "APPENDIX TO FROZEN FOODS SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Frozen Foods Not Insured"), "Frozen Foods Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_LossofLicence")).equals("Yes")&& !((String)mdata.get("CD_Add_LossofLicence")).equals("No"))|| ((String)mdata.get("CD_Add_LossofLicence")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Licence Insured"), "Loss of Licence Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LOSS OF LICENCE SECTION"), "APPENDIX TO LOSS OF LICENCE SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Licence Not Insured"), "Loss of Licence Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_FidelityGuarantee")).equals("Yes")&& !((String)mdata.get("CD_Add_FidelityGuarantee")).equals("No"))|| ((String)mdata.get("CD_Add_FidelityGuarantee")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Insured"), "Fidelity Guarantee Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FIDELITY GUARANTEE SECTION"), "APPENDIX TO FIDELITY GUARANTEE SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Not Insured"), "Fidelity Guarantee Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Terrorism")).equals("Yes")&& !((String)mdata.get("CD_Add_Terrorism")).equals("No"))|| ((String)mdata.get("CD_Add_Terrorism")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
				if(((((String)mdata.get("CD_MaterialDamage")).equals("Yes")&& !((String)mdata.get("CD_Add_MaterialDamage")).equals("No"))|| ((String)mdata.get("CD_Add_MaterialDamage")).equals("Yes"))){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
				}
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_LegalExpenses")).equals("Yes")&& !((String)mdata.get("CD_Add_LegalExpenses")).equals("No"))|| ((String)mdata.get("CD_Add_LegalExpenses")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
				
				String LE_LimitOfLiability = (String)common.NB_excel_data_map.get("LE_LimitOfLiability");
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
				
					
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
			}
			break;
		case "CCG":
			if(((((String)mdata.get("CD_BusinessInterruption")).equals("Yes")&& !((String)mdata.get("CD_Add_BusinessInterruption")).equals("No"))|| ((String)mdata.get("CD_Add_BusinessInterruption")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Insured"), "Business Interruption Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO BUSINESS INTERRUPTION SECTION"), "APPENDIX TO BUSINESS INTERRUPTION SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Not Insured"), "Business Interruption Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Liability")).equals("Yes")&& !((String)mdata.get("CD_Add_Liability")).equals("No"))|| ((String)mdata.get("CD_Add_Liability")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
				String EL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("EL_LimitOfIndemnity")));
				String PL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PL_IndemnityLimit")));
				String PRL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PRL_IndemnityLimit")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Public Liability �"+PL_LOI), "Public Liability &pound;"+PL_LOI , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Products Liability �"+PRL_LOI), "Products Liability &pound;"+PRL_LOI , fileName);
				
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_SpecifiedAllRisks")).equals("Yes")&& !((String)mdata.get("CD_Add_SpecifiedAllRisks")).equals("No"))|| ((String)mdata.get("CD_Add_SpecifiedAllRisks")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Insured"), "Specified All Risks Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO SPECIFIED ALL RISKS SECTION"), "APPENDIX TO SPECIFIED ALL RISKS SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Not Insured"), "Specified All Risks Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_ContractorsAllRisks")).equals("Yes")&& !((String)mdata.get("CD_Add_ContractorsAllRisks")).equals("No"))|| ((String)mdata.get("CD_Add_ContractorsAllRisks")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Insured"), "Contractors All Risks Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CONTRACTORS ALL RISKS SECTION"), "APPENDIX TO CONTRACTORS ALL RISKS SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Not Insured"), "Contractors All Risks Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_ComputersandElectronicRisks")).equals("Yes")&& !((String)mdata.get("CD_Add_ComputersandElectronicRisks")).equals("No"))|| ((String)mdata.get("CD_Add_ComputersandElectronicRisks")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Insured"), "Computers & Electronic Risks Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION"), "APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Computers"), "Subsection: Computers" , fileName);
				
				String CER_Sum_Ins = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_Computers_SumInsured")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Sum_Ins), "Sum Insured &pound;"+CER_Sum_Ins , fileName);
				
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: E Risk: Virus & Hacking"), "Subsection: E Risk: Virus & Hacking" , fileName);
				
				String CER_Erisk_Virus_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_Erisk_VirusHacking")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+CER_Erisk_Virus_LOI), "Limit of Liability &pound;"+CER_Erisk_Virus_LOI , fileName);
		
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Additional Expenditure"), "Subsection: Additional Expenditure" , fileName);
				
				String CER_Additional_Sum_Ins = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_AdditionalExp_SumInsured")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Additional_Sum_Ins), "Sum Insured &pound;"+CER_Additional_Sum_Ins , fileName);
		
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Not Insured"), "Computers & Electronic Risks Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Money")).equals("Yes")&& !((String)mdata.get("CD_Add_Money")).equals("No"))|| ((String)mdata.get("CD_Add_Money")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Insured"), "Money Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MONEY SECTION"), "APPENDIX TO MONEY SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Bodily Injury Benefits"), "Bodily Injury Benefits" , fileName);
				
				String M_LossOfLimbs = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_LossOfLimbs")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of limbs �"+M_LossOfLimbs), "Loss of limbs &pound;"+M_LossOfLimbs , fileName);
				
				String M_LossOfSight = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_LossOfSight")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of sight �"+M_LossOfSight), "Loss of sight &pound;"+M_LossOfSight , fileName);

				String M_PermanentTotalDis = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_PermanentTotalDis")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Permanent Total Disablement �"+M_PermanentTotalDis), "Permanent Total Disablement &pound;"+M_PermanentTotalDis , fileName);

				String M_TempTotalDisablement = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_TempTotalDisablement")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Temporary Total Disablement (Up to 104 weeks) �"+M_TempTotalDisablement), "Temporary Total Disablement (Up to 104 weeks) &pound;"+M_TempTotalDisablement , fileName);

			
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Not Insured"), "Money Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_GoodsInTransit")).equals("Yes")&& !((String)mdata.get("CD_Add_GoodsInTransit")).equals("No"))|| ((String)mdata.get("CD_Add_GoodsInTransit")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Insured"), "Goods In Transit Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO GOODS IN TRANSIT SECTION"), "APPENDIX TO GOODS IN TRANSIT SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Conveyance Limit of Liability"), "Conveyance Limit of Liability" , fileName);
				
				String GIT_AnyOnePostalPackage = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("GIT_AnyOnePostalPackage")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one postal package �"+GIT_AnyOnePostalPackage), "Any one postal package &pound;"+GIT_AnyOnePostalPackage , fileName);
				
				String GIT_AnyOneConsignment = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("GIT_AnyOneConsignment")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one consignment by rail or road �"+GIT_AnyOneConsignment), "Any one consignment by rail or road &pound;"+GIT_AnyOneConsignment , fileName);

			
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Not Insured"), "Goods In Transit Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_MarineCargo")).equals("Yes")&& !((String)mdata.get("CD_Add_MarineCargo")).equals("No"))|| ((String)mdata.get("CD_Add_MarineCargo")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Marine Cargo Insured"), "Marine Cargo Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MARINE CARGO SECTION"), "APPENDIX TO MARINE CARGO SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Marine Cargo Not Insured"), "Marine Cargo Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes")&& !((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("No"))|| ((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_DirectorsandOfficers")).equals("Yes")&& !((String)mdata.get("CD_Add_DirectorsandOfficers")).equals("No"))|| ((String)mdata.get("CD_Add_DirectorsandOfficers")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Insured"), "Directors & Officers Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION"), "APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Applicable Excess"), "Applicable Excess" , fileName);
				
				String DO_Excess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_Excess")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors and Officers Liability �"+DO_Excess), "Directors and Officers Liability &pound;"+DO_Excess , fileName);
				
				String DO_CorporateLiabilityExcess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_CorporateLiabilityExcess")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Corporate Liability �"+DO_CorporateLiabilityExcess), "Corporate Liability &pound;"+DO_CorporateLiabilityExcess , fileName);
				
				String DO_EPL_Excess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_EPL_Excess")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employment Practices Liability �"+DO_EPL_Excess), "Employment Practices Liability &pound;"+DO_EPL_Excess , fileName);
			
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Not Insured"), "Directors & Officers Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_FrozenFood")).equals("Yes")&& !((String)mdata.get("CD_Add_FrozenFood")).equals("No"))|| ((String)mdata.get("CD_Add_FrozenFood")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Frozen Foods Insured"), "Frozen Foods Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FROZEN FOODS SECTION"), "APPENDIX TO FROZEN FOODS SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Frozen Foods Not Insured"), "Frozen Foods Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_LossofLicence")).equals("Yes")&& !((String)mdata.get("CD_Add_LossofLicence")).equals("No"))|| ((String)mdata.get("CD_Add_LossofLicence")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Licence Insured"), "Loss of Licence Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LOSS OF LICENCE SECTION"), "APPENDIX TO LOSS OF LICENCE SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of Licence Not Insured"), "Loss of Licence Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_FidelityGuarantee")).equals("Yes")&& !((String)mdata.get("CD_Add_FidelityGuarantee")).equals("No"))|| ((String)mdata.get("CD_Add_FidelityGuarantee")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Insured"), "Fidelity Guarantee Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FIDELITY GUARANTEE SECTION"), "APPENDIX TO FIDELITY GUARANTEE SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Not Insured"), "Fidelity Guarantee Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Terrorism")).equals("Yes")&& !((String)mdata.get("CD_Add_Terrorism")).equals("No"))|| ((String)mdata.get("CD_Add_Terrorism")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
				if(((((String)mdata.get("CD_MaterialDamage")).equals("Yes")&& !((String)mdata.get("CD_Add_MaterialDamage")).equals("No"))|| ((String)mdata.get("CD_Add_MaterialDamage")).equals("Yes"))){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
				}
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_LegalExpenses")).equals("Yes")&& !((String)mdata.get("CD_Add_LegalExpenses")).equals("No"))|| ((String)mdata.get("CD_Add_LegalExpenses")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
				
				String LE_LimitOfLiability = (String)common.NB_excel_data_map.get("LE_LimitOfLiability");
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
				
					
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
			}
			break;
		case "CTA":
			if(((((String)mdata.get("CD_BusinessInterruption")).equals("Yes")&& !((String)mdata.get("CD_Add_BusinessInterruption")).equals("No"))|| ((String)mdata.get("CD_Add_BusinessInterruption")).equals("Yes"))){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Insured"), "Business Interruption Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO BUSINESS INTERRUPTION SECTION"), "APPENDIX TO BUSINESS INTERRUPTION SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Business Interruption Not Insured"), "Business Interruption Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Liability")).equals("Yes")&& !((String)mdata.get("CD_Add_Liability")).equals("No"))|| ((String)mdata.get("CD_Add_Liability")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Insured"), "Liability Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LIABILITY SECTION"), "APPENDIX TO LIABILITY SECTION" , fileName);
				String EL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("EL_LimitOfIndemnity")));
				String PL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PL_IndemnityLimit")));
				String PRL_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("PRL_IndemnityLimit")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employers' Liability �"+EL_LOI), "Employers' Liability &pound;"+EL_LOI , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Public Liability �"+PL_LOI), "Public Liability &pound;"+PL_LOI , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Products Liability �"+PRL_LOI), "Products Liability &pound;"+PRL_LOI , fileName);
				
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Liability Not Insured"), "Liability Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_SpecifiedAllRisks")).equals("Yes")&& !((String)mdata.get("CD_Add_SpecifiedAllRisks")).equals("No"))|| ((String)mdata.get("CD_Add_SpecifiedAllRisks")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Insured"), "Specified All Risks Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO SPECIFIED ALL RISKS SECTION"), "APPENDIX TO SPECIFIED ALL RISKS SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Specified All Risks Not Insured"), "Specified All Risks Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_ContractorsAllRisks")).equals("Yes")&& !((String)mdata.get("CD_Add_ContractorsAllRisks")).equals("No"))|| ((String)mdata.get("CD_Add_ContractorsAllRisks")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Insured"), "Contractors All Risks Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CONTRACTORS ALL RISKS SECTION"), "APPENDIX TO CONTRACTORS ALL RISKS SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Contractors All Risks Not Insured"), "Contractors All Risks Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_ComputersandElectronicRisks")).equals("Yes")&& !((String)mdata.get("CD_Add_ComputersandElectronicRisks")).equals("No"))|| ((String)mdata.get("CD_Add_ComputersandElectronicRisks")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Insured"), "Computers & Electronic Risks Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION"), "APPENDIX TO COMPUTERS & ELECTRONIC RISKS SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Computers"), "Subsection: Computers" , fileName);
				
				String CER_Sum_Ins = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_Computers_SumInsured")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Sum_Ins), "Sum Insured &pound;"+CER_Sum_Ins , fileName);
				
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: E Risk: Virus & Hacking"), "Subsection: E Risk: Virus & Hacking" , fileName);
				
				String CER_Erisk_Virus_LOI = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_Erisk_VirusHacking")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+CER_Erisk_Virus_LOI), "Limit of Liability &pound;"+CER_Erisk_Virus_LOI , fileName);
		
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Subsection: Additional Expenditure"), "Subsection: Additional Expenditure" , fileName);
				
				String CER_Additional_Sum_Ins = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("CER_AdditionalExp_SumInsured")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Sum Insured �"+CER_Additional_Sum_Ins), "Sum Insured &pound;"+CER_Additional_Sum_Ins , fileName);
		
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Computers & Electronic Risks Not Insured"), "Computers & Electronic Risks Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_Money")).equals("Yes")&& !((String)mdata.get("CD_Add_Money")).equals("No"))|| ((String)mdata.get("CD_Add_Money")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Insured"), "Money Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO MONEY SECTION"), "APPENDIX TO MONEY SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Bodily Injury Benefits"), "Bodily Injury Benefits" , fileName);
				
				String M_LossOfLimbs = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_LossOfLimbs")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of limbs �"+M_LossOfLimbs), "Loss of limbs &pound;"+M_LossOfLimbs , fileName);
				
				String M_LossOfSight = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_LossOfSight")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Loss of sight �"+M_LossOfSight), "Loss of sight &pound;"+M_LossOfSight , fileName);

				String M_PermanentTotalDis = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_PermanentTotalDis")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Permanent Total Disablement �"+M_PermanentTotalDis), "Permanent Total Disablement &pound;"+M_PermanentTotalDis , fileName);

				String M_TempTotalDisablement = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("M_TempTotalDisablement")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Temporary Total Disablement (Up to 104 weeks) �"+M_TempTotalDisablement), "Temporary Total Disablement (Up to 104 weeks) &pound;"+M_TempTotalDisablement , fileName);

			
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Money Not Insured"), "Money Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_GoodsInTransit")).equals("Yes")&& !((String)mdata.get("CD_Add_GoodsInTransit")).equals("No"))|| ((String)mdata.get("CD_Add_GoodsInTransit")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Insured"), "Goods In Transit Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO GOODS IN TRANSIT SECTION"), "APPENDIX TO GOODS IN TRANSIT SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Conveyance Limit of Liability"), "Conveyance Limit of Liability" , fileName);
				
				String GIT_AnyOnePostalPackage = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("GIT_AnyOnePostalPackage")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one postal package �"+GIT_AnyOnePostalPackage), "Any one postal package &pound;"+GIT_AnyOnePostalPackage , fileName);
				
				String GIT_AnyOneConsignment = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("GIT_AnyOneConsignment")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Any one consignment by rail or road �"+GIT_AnyOneConsignment), "Any one consignment by rail or road &pound;"+GIT_AnyOneConsignment , fileName);

			
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Goods In Transit Not Insured"), "Goods In Transit Not Insured" , fileName);
			}
			
							
			if(((((String)mdata.get("CD_CyberandDataSecurity")).equals("Yes")&& !((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("No"))|| ((String)mdata.get("CD_Add_CyberandDataSecurity")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Insured"), "Cyber & Data Security Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO CYBER & DATA SECURITY SECTION"), "APPENDIX TO CYBER & DATA SECURITY SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Cyber & Data Security Not Insured"), "Cyber & Data Security Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_DirectorsandOfficers")).equals("Yes")&& !((String)mdata.get("CD_Add_DirectorsandOfficers")).equals("No"))|| ((String)mdata.get("CD_Add_DirectorsandOfficers")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Insured"), "Directors & Officers Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION"), "APPENDIX TO DIRECTORS & OFFICERS LIABILITY SECTION" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Applicable Excess"), "Applicable Excess" , fileName);
				
				String DO_Excess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_Excess")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors and Officers Liability �"+DO_Excess), "Directors and Officers Liability &pound;"+DO_Excess , fileName);
				
				String DO_CorporateLiabilityExcess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_CorporateLiabilityExcess")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Corporate Liability �"+DO_CorporateLiabilityExcess), "Corporate Liability &pound;"+DO_CorporateLiabilityExcess , fileName);
				
				String DO_EPL_Excess = formatter.format(Double.parseDouble((String)common.NB_excel_data_map.get("DO_EPL_Excess")));
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Employment Practices Liability �"+DO_EPL_Excess), "Employment Practices Liability &pound;"+DO_EPL_Excess , fileName);
			
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Directors & Officers Not Insured"), "Directors & Officers Not Insured" , fileName);
			}
			
			
								
			if(((((String)mdata.get("CD_Terrorism")).equals("Yes")&& !((String)mdata.get("CD_Add_Terrorism")).equals("No"))|| ((String)mdata.get("CD_Add_Terrorism")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Insured"), "Terrorism Insured" , fileName);
				
				if(((((String)mdata.get("CD_MaterialDamage")).equals("Yes")&& !((String)mdata.get("CD_Add_MaterialDamage")).equals("No"))|| ((String)mdata.get("CD_Add_MaterialDamage")).equals("Yes"))){
					fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO TERRORISM SECTION"), "APPENDIX TO TERRORISM SECTION" , fileName);
				}
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Terrorism Not Insured"), "Terrorism Not Insured" , fileName);
			}
			
			if(((((String)mdata.get("CD_LegalExpenses")).equals("Yes")&& !((String)mdata.get("CD_Add_LegalExpenses")).equals("No"))|| ((String)mdata.get("CD_Add_LegalExpenses")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Insured"), "Legal Expenses Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO LEGAL EXPENSES SECTION"), "APPENDIX TO LEGAL EXPENSES SECTION" , fileName);
				
				String LE_LimitOfLiability = (String)common.NB_excel_data_map.get("LE_LimitOfLiability");
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Limit of Liability �"+LE_LimitOfLiability), "Limit of Liability &pound;"+LE_LimitOfLiability , fileName);
				
					
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Legal Expenses Not Insured"), "Legal Expenses Not Insured" , fileName);
			}
			if(((((String)mdata.get("CD_FidelityGuarantee")).equals("Yes")&& !((String)mdata.get("CD_Add_FidelityGuarantee")).equals("No"))|| ((String)mdata.get("CD_Add_FidelityGuarantee")).equals("Yes"))){

				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Insured"), "Fidelity Guarantee Insured" , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("APPENDIX TO FIDELITY GUARANTEE SECTION"), "APPENDIX TO FIDELITY GUARANTEE SECTION" , fileName);
				
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Fidelity Guarantee Not Insured"), "Fidelity Guarantee Not Insured" , fileName);
			}
			break;
		case "SPI":
			if(((String)mdata.get("CD_SolicitorsPI")).equals("Yes")){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Solicitors Professional Indemnity Primary Layer"), "Solicitors Professional Indemnity Primary Layer" , fileName);
			}
			
			String Policy_Fee = (String)mdata.get("SP_AdminFeeSP");
			String formated_Policy_Fee = formatter.format(Double.parseDouble(Policy_Fee));
			
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Fee �"+formated_Policy_Fee), "Policy Fee = &pound;"+formated_Policy_Fee , fileName);
			
			String formated_G_premium = formatter.format(common_SPI.PI_pdf_GrossPremium);
			
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Premium �"+formated_G_premium), "Premium = &pound;"+formated_G_premium , fileName);
		
			
			String formated_InsuranceTax = formatter.format(common_SPI.PI_pdf_InsuranceTax);
			
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Insurance Premium Tax �"+formated_InsuranceTax), "Insurance Premium Tax = &pound;"+formated_InsuranceTax , fileName);
			double total_p = Double.parseDouble(Policy_Fee) + common_SPI.PI_pdf_GrossPremium + common_SPI.PI_pdf_InsuranceTax;
			
			String total_Premium = formatter.format(total_p);
			String final_total_Premium = total_Premium.replaceAll(",", "");
			String formated_total_Premium = formatter.format(Double.parseDouble(final_total_Premium));
			
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("TOTAL �"+formated_total_Premium), "TOTAL &pound;"+formated_total_Premium , fileName);
			break;
		default:
			break;
		}
		if(!TestBase.product.equals("SPI")){
		String total_Gross_Premium = (String)mdata.get("PS_Total_GP");
		String formated_G_premium = formatter.format(Double.parseDouble(total_Gross_Premium));
		
		fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Premium �"+formated_G_premium), "Premium = &pound;"+formated_G_premium , fileName);
		
		
		String total_I_tax = (String)mdata.get("PS_Total_GT");
		String formated_I_tax = formatter.format(Double.parseDouble(total_I_tax));
		
		fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Insurance Premium Tax �"+formated_I_tax), "Insurance Premium Tax = &pound;"+formated_I_tax , fileName);
		double total_p=(Double.parseDouble((String)mdata.get("PS_Total_GP"))) + (Double.parseDouble((String)mdata.get("PS_Total_GT")));
		
		String total_Premium = formatter.format(total_p);
		String final_total_Premium = total_Premium.replaceAll(",", "");
		String formated_total_Premium = formatter.format(Double.parseDouble(final_total_Premium));
		
		fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("TOTAL �"+formated_total_Premium), "TOTAL &pound;"+formated_total_Premium , fileName);
		}
		break;
		//SPI
	case "Policy Schedule Excess Layer":
		
		formatter = new DecimalFormat("#,###,###.##");
		incrementalDays = Integer.parseInt(CONFIG.getProperty("DaysIncrementNumber"));
		policyDuration = Integer.parseInt((String)mdata.get("PS_Duration"));
		
		fail_count=0;
				
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains(" POLICY SCHEDULE"), "Document : POLICY SCHEDULE", fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("SOLICITORS PROFESSIONAL"), "Product name : SOLICITORS PROFESSIONAL" , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INDEMNITY � EXCESS LAYER"), "Product name : INDEMNITY � EXCESS LAYER" , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)mdata.get("NB_ClientName")), "Insured Name : "+(String)mdata.get("NB_ClientName") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("QC_AgencyName")), "Agency name : "+(String)mdata.get("QC_AgencyName") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Correspondence Address: "+(String)mdata.get("PG_Address")), "Correspondence Address : "+(String)mdata.get("PD_Address") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Postcode: "+(String)mdata.get("PG_Postcode")), "Postcode: "+(String)mdata.get("PG_Postcode") , fileName);
			if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0),fileName);
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Effective Date: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)) ,"Effective Date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0),fileName);
			}
			
			if(((String)mdata.get("PS_DefaultStartEndDate")).equals("No")){
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), 0) , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("PS_PolicyStartDate"), policyDuration-1) , fileName);
			}else{
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Period of Insurance: From: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0)), "Insurance Start date : "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), 0) , fileName);
				fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("To: "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1)), "Insurance End date :  "+common.daysIncrement((String)mdata.get("QC_InceptionDate"), policyDuration-1) , fileName);
			}
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Underlying Insurance Limit: "+(String)mdata.get("SEL_UILimit")), "Underlying Insurance Limit: "+(String)mdata.get("SEL_UILimit") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Underlying Insurance Company: "+(String)mdata.get("SEL_UICompany")), "Underlying Insurance Company: "+(String)mdata.get("SEL_UICompany") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Underlying Insurance Policy Number: "+(String)mdata.get("SEL_UIPolicyNumbe")), "Underlying Insurance Policy Number: "+(String)mdata.get("SEL_UIPolicyNumbe") , fileName);
			
			String Policy_Fee = (String)mdata.get("SEL_AdminFeeSEL");
			String formated_Policy_Fee = formatter.format(Double.parseDouble(Policy_Fee));
			
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Policy Fee �"+formated_Policy_Fee), "Policy Fee = &pound;"+formated_Policy_Fee , fileName);
			
			String formated_G_premium = formatter.format(common_SPI.SEL_pdf_GrossPremium);
			
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Premium �"+formated_G_premium), "Premium = &pound;"+formated_G_premium , fileName);
		
			
			String formated_InsuranceTax = formatter.format(common_SPI.SEL_pdf_InsuranceTax);
			
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("Insurance Premium Tax �"+formated_InsuranceTax), "Insurance Premium Tax = &pound;"+formated_InsuranceTax , fileName);
			double total_p = Double.parseDouble(Policy_Fee) + common_SPI.SEL_pdf_GrossPremium + common_SPI.SEL_pdf_InsuranceTax;
			
			String total_Premium = formatter.format(total_p);
			String final_total_Premium = total_Premium.replaceAll(",", "");
			String formated_total_Premium = formatter.format(Double.parseDouble(final_total_Premium));
			
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("TOTAL �"+formated_total_Premium), "TOTAL &pound;"+formated_total_Premium , fileName);
			
		
		break;
		//SPI
	case "Certificate of Insurance":
		
		formatter = new DecimalFormat("#,###,###.##");
		incrementalDays = Integer.parseInt(CONFIG.getProperty("DaysIncrementNumber"));
		policyDuration = Integer.parseInt((String)mdata.get("PS_Duration"));
		
		fail_count=0;
				
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("CERTIFICATE OF INSURANCE"), "Document : CERTIFICATE OF INSURANCE", fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("CERTIFICATE OF QUALIFYING INSURANCE"), "CERTIFICATE OF QUALIFYING INSURANCE" , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INDEMNITY YEAR"), "INDEMNITY YEAR" , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("NAME OF INSURED FIRM: "+(String)mdata.get("NB_ClientName")), "NAME OF INSURED FIRM: "+(String)mdata.get("NB_ClientName") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("PRINCIPAL ADDRESS OF "+(String)mdata.get("PG_Address")), "PRINCIPAL ADDRESS OF "+(String)mdata.get("PG_Address") , fileName);
			fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("POLICY NUMBER: "+(String)mdata.get("NB_PolicyNumber")) ,"POLICY NUMBER: "+mdata.get("NB_PolicyNumber"),fileName);
			
		
		break;

	case "Statement of Fact":
		
		fail_count=0;
		
		fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("pdf_ProductName")), "Product name :"+(String)mdata.get("pdf_ProductName") , fileName);
		fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains("INSURED NAME - "+(String)mdata.get(common.currentRunningFlow+"_ClientName")), "Insured Name : "+(String)mdata.get(common.currentRunningFlow+"_ClientName") , fileName);
		fail_count = fail_count + CommonFunction_GUS.verification(parsedText.contains((String)mdata.get("QC_AgencyName")), "Agency name : "+(String)mdata.get("QC_AgencyName") , fileName);
		break;
		
	case "Policy Wording":
		
		TestUtil.reportStatus(fileName+" Verification Not in Scope . ", "Info", true);
		break;
		
	case "Employers Liability Certificate":
		
		TestUtil.reportStatus(fileName+" Verification Not in Scope . ", "Info", true);
		break;

}

return fail_count;

}



/**
 * 
 * 
 * 
 *  END of PDF verifications functions for Rewind flows.
 * 
 * 
 */


	/*
	 * This function will delete unnecessary 
	 * PDF files files from mentioned Path
	 * 
	 * @param filePath - folder path from where extra files needs to be deleted
	 */
	
	public boolean fileDeletion(String filePath) {
		
		File f = new File(filePath);
		File[] listOfFiles = f.listFiles();
		int size = listOfFiles.length;
		//System.out.println(size);
		
		for(int i=0;i<size;i++){
			if (listOfFiles[i].isFile()) {
				
				int position = listOfFiles[i].getName().lastIndexOf(".");
				if(position!=-1){
					/*
					 *  @Variable fileName - it will return fileName without extention
					 */
					String fileName = listOfFiles[i].getName().substring(0,position);  
					if(fileName.contains("pdf")){
						listOfFiles[i].delete();
					}
				}
			}
		}
		return true;
	}
			
	public static int verification(boolean result,String data , String fileName) {
		
		if(result){
			TestUtil.reportStatus("Verified [<b> "+data+" </b>] in "+fileName+" document.", "Pass", false);
			return 0;
		}else{
			TestUtil.reportStatus("<p style='color:red'> PDF Document: "+fileName+" does not contain [<b> "+data+" </b>]</p>", "Fail", false);
			return 1;
		}

	}

	/*
     * ------------------------------------------------------------
     * PDF Verification Handling function. END
     * ------------------------------------------------------------
     */
	
	// Count difference of days from 2 dates.
	public double countDays(Hashtable<String, String> mdata){
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		Date d1 = null;
		Date d2 = null;
		Date d3 = null;
		
		try {
			d1 = format.parse(mdata.get("Inception_Date"));
			d2 = format.parse(mdata.get("Deadline_Date"));
			d3 = format.parse(mdata.get("Create_Endorsment_Date"));
			
			//in milliseconds
			double diff = d2.getTime() - d1.getTime();
			double diff_1 = d3.getTime() - d1.getTime();
			double diffDays = ((diff / (24 * 60 * 60 * 1000))) - (diff_1 / (24 * 60 * 60 * 1000));
			
			return diffDays+1;
		}
		catch(Throwable t){
			return 0;
		}
		
	}

	//Created by Jainil : 
	public String daysIncrement(String date,int days) throws ParseException{
		
		final String OLD_FORMAT = "dd/MM/yyyy";
		final String NEW_FORMAT = "dd/MMMM/yyyy";
		// August 12, 2010
		//String oldDateString = "09/01/2016";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = sdf.parse(date);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d); 
		sdf.applyPattern(NEW_FORMAT);
		//System.out.println("Original Date is : " + sdf.format(c1.getTime()));
		c1.add(Calendar.DATE,days);
		//System.out.println("New date is : " + sdf.format(c1.getTime()).replaceAll("/", " "));
		
		return sdf.format(c1.getTime()).replaceAll("/", " ");
	}

	@SuppressWarnings("unused")
	public String DateDiff(String date,String date1) throws ParseException{
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		  
		Date d1 = null;
		Date d2 = null;
		  
		try {
		  d1 = df.parse(date);
		  d2 = df.parse(date1);
		  
		   //in milliseconds
		  long diff = d1.getTime() - d2.getTime();
		  
		  long diffDays = (diff / (24 * 60 * 60 * 1000))+1;
		  
		  System.out.print(diffDays + " days, ");
		  
		  return String.valueOf(diffDays);
		}
		catch (Throwable t)
		{
		  t.printStackTrace();
		  return null;
		}
	}
		
	// -----------------------------------------------------------------------------------------------------------------------------------------
    
	// All Cover specific Functions :	     

		// property Details Table Handling Functions:
		     
		 public static boolean PropertyDetails_HandleTables(Map<Object, Object> map_data, String Main_Cover, int count) throws Throwable, Exception{
			 String coverName;
			 double dTotal_Rate = 0.00, dAdjusted_Rate = 0.00, d_MD_Premium = 0.00, d_BI_Premium = 0.00, d_MD_TotalPremium = 0.00, d_BI_TotalPremium = 0.00;
			 int startCountCol;
			 boolean bVal =true;
			 			 
			 String table_Path = "html/body/div[3]/form/div/table[";
			 
			 if(Main_Cover.contains("MD")){
				 //table_Path = "html/body/div[3]/form/div/table[4]";
				 table_Path= "html/body/div[3]/form/div/table["+k.getTableIndex("Declared Value", "xpath", "html/body/div[3]/form/div/table") +"]";
				 
			 }else{
				 //table_Path = "html/body/div[3]/form/div/table[5]";
				 table_Path= "html/body/div[3]/form/div/table["+k.getTableIndex("Ind Period", "xpath", "html/body/div[3]/form/div/table") +"]";
			 }
			 
			 //Put data to table :
			 
				 func_PD_InputTo_Table(map_data, Main_Cover, table_Path, count);
					
				 customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Insured Properties .");
				 
				// Comparisons : 
				
				 
				 WebElement table= driver.findElement(By.xpath(table_Path));
				 int numOfRow = table.findElements(By.tagName("tr")).size();
				 
				 k.pressDownKeyonPage();
				 k.pressDownKeyonPage();
				 
				 for(int j=0;j < numOfRow-2;j++){		
					 coverName = driver.findElement(By.xpath(table_Path + "/tbody/tr["+(j+1)+"]/td[1]")).getText();
					 
					 // Read Values from table :
					 
					 if(Main_Cover.contains("MD")){
						 dTotal_Rate = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td[9]/input"));
						 dAdjusted_Rate = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td[12]/input"));
						 d_MD_Premium = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td[13]/input"));
						 d_MD_TotalPremium = d_MD_TotalPremium + d_MD_Premium;
					 }else{
						 dTotal_Rate = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td[10]/input"));
						 dAdjusted_Rate = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td[13]/input"));				 
						 d_BI_Premium = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td[14]/input"));
						 d_BI_TotalPremium = d_BI_TotalPremium + d_BI_Premium;
					 }
					 
					// Calculate Values for total rate, adjusted rate, MD_Premium and BI Premium :
					 
					 if(Main_Cover.contains("MD")){
						 startCountCol = 4 ;		 		
					 }else{
						 startCountCol = 5 ;
					 }
						 PD_TotalRate = Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td["+startCountCol+"]/input"))
							 		+ Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td["+(startCountCol+1)+"]/input"))
							 		+ Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td["+(startCountCol+2)+"]/input"))
							 		+ Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td["+(startCountCol+3)+"]/input"))
							 		+ Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td["+(startCountCol+4)+"]/input"));
					 
						 PD_AdjustedRate = func_PD_CalculateAdjustedRate(map_data,PD_TotalRate, Main_Cover, coverName, count);
					 
						 if(Main_Cover.contains("MD")){
							 PD_MD_Premium = (( Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td[2]/input").replace(",", ""))* PD_AdjustedRate)/100);
							 PD_MD_TotalPremium = PD_MD_TotalPremium + PD_MD_Premium;
							
						 }else{
							 PD_BI_Premium = (( Double.parseDouble(k.GetText_DynamicXpathWebDriver(driver, table_Path + "/tbody/tr["+(j+1)+"]/td[2]/input").replace(",", ""))* PD_AdjustedRate)/100);
							 PD_BI_TotalPremium = PD_BI_TotalPremium + PD_BI_Premium;
							
						 }				 
						 
						 // Compare values :
						 
						 //customAssert.assertTrue(compareValues(dTotal_Rate,PD_TotalRate,"Total Rate"),"Mismatched Total Rate Values"); 
						 //customAssert.assertTrue(compareValues(dAdjusted_Rate,PD_AdjustedRate,"Adjusted Rate"),"Mismatched Adjusted Rate Values");
						 if(Main_Cover.contains("MD")){
							 customAssert.assertTrue(compareValues(d_MD_Premium,Double.parseDouble(common.roundedOff(Double.toString(PD_MD_Premium))),"MD premium"),"Mismatched MD premium Values");		 		
						 }else{
							 customAssert.assertTrue(compareValues(d_BI_Premium,Double.parseDouble(common.roundedOff(Double.toString(PD_BI_Premium))),"BI Premium"),"Mismatched BI Premium Values");
						 } 						  
				 }					
				 	
				 
				 if(Main_Cover.contains("MD")){
					 TestUtil.WriteDataToXl_innerSheet(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Property Details", common.NB_Structure_of_InnerPagesMaps.get("Property Details").get(count).get("Automation Key"), "MD_TotalPremium", String.valueOf(PD_MD_TotalPremium),common.NB_Structure_of_InnerPagesMaps.get("Property Details").get(count));							 
				 }else{
					 TestUtil.WriteDataToXl_innerSheet(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Property Details",  common.NB_Structure_of_InnerPagesMaps.get("Property Details").get(count).get("Automation Key"), "BI_TotalPremium", String.valueOf(PD_BI_TotalPremium),common.NB_Structure_of_InnerPagesMaps.get("Property Details").get(count));
				 }
			 
			 return bVal;
		 }
		 
		 public static double func_PD_CalculateAdjustedRate(Map<Object, Object> map_data, double pdTotalRate, String CoverName, String sectionName, int count){
			 double adjustedRate = 0.00;
			 double fisrtFactor = 0.00, sFactor = 0.00;
			 String Abvr = "";
			 //int count = 1;
			 
			 if(sectionName.contains("-")){
				 if(CoverName.contains("MD")){
					 Abvr = "BSI_MD_";
				 }
				 else{
					 Abvr = "BSI_BI_";
				 }				 
			 }else{
				 Abvr = func_GetAbrrivation(CoverName, sectionName);
			 }
			 
			 fisrtFactor = ((((Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Property Details").get(count).get(Abvr + "TechAdjust"))) * pdTotalRate)/ 100 ) + pdTotalRate);
			 sFactor = (((Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get("Property Details").get(count).get(Abvr + "CommAdjust"))) * fisrtFactor)/ 100 ); 
			 adjustedRate = fisrtFactor + sFactor;
			 
			 return adjustedRate;
		 }
		 
				 
		 public static boolean func_PD_InputTo_Table(Map<Object, Object> map_data, String CoverName, String tablePath, int count){
			 boolean bVal =true;
			 String sectionName;
			 String Abvr;
			 int FixRows, totalColumns;
			 int dynamicRow_Start;
			 String absXpath;
			 
			 WebElement table= driver.findElement(By.xpath(tablePath));
			 
			//Get number of rows in table 
			    int numOfRow = table.findElements(By.tagName("tr")).size();
			    
			    if(CoverName.contains("MD")){
					 FixRows = 4;		
					 dynamicRow_Start = 5;
					 totalColumns = 12;
				 }else{
					 FixRows = 6;
					 dynamicRow_Start = 7;
					 totalColumns = 13;
				 }
			   			   
			    for(int j=0;j<dynamicRow_Start-1;j++){							 
			    	sectionName = driver.findElement(By.xpath(tablePath + "/tbody/tr["+(j+1)+"]/td[1]")).getText();
			    	Abvr = func_GetAbrrivation(CoverName, sectionName);
			    	
			    	if(CoverName.contains("MD")){
			    		//customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[2]/input", common.NB_excel_data_map , "Property Details", count , Abvr , "DeclaredValue", "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[2]/input",common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "DeclaredValue", sectionName,"Input"),"BAC");
			    		//customAssert.assertTrue(k.DynamicXpathWebDriver(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[3]/input", (String)map_data.get(Abvr + "DayOne"), "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[4]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "FireRate",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[5]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "PerilsRate",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[6]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "SprinkRate",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[7]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "ADRate", sectionName,"Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[8]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "SubsRate",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[10]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "TechAdjust",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[11]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "CommAdjust", sectionName,"Input"),"BAC");
			    	}else{
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[2]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "DeclaredValue",sectionName, "Input"),"BAC");
			    		//customAssert.assertTrue(k.DynamicXpathWebDriver(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[3]/input", (String)map_data.get(Abvr + "DeclarationUplift "), "Input"),"BAC");
			    		//customAssert.assertTrue(k.DynamicXpathWebDriver(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[4]/input", (String)map_data.get(Abvr + "IndPeriod"), "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[5]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "FireRate",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[6]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "PerilsRate",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[7]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "SprinkRate",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[8]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "ADRate",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[9]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "SubsRate",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[11]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "TechAdjust",sectionName, "Input"),"BAC");
			    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(j+1)+"]/td[12]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "CommAdjust", sectionName,"Input"),"BAC");
			    	}
			    	
			    }
			    	
			    for(int l=FixRows;l<dynamicRow_Start;l++){							 
				    	sectionName = driver.findElement(By.xpath(tablePath + "/tbody/tr["+(l+1)+"]/td[1]")).getText();
				   					    	
				    	if(CoverName.contains("MD")){
				    		Abvr = "BSI_MD_";
				    		//customAssert.assertTrue(k.DynamicXpathWebDriver(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[2]/input", (String)map_data.get(Abvr + "DeclaredValue"), "Input"),"BAC");
				    		//customAssert.assertTrue(k.DynamicXpathWebDriver(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[3]/input", (String)map_data.get(Abvr + "DayOne"), "Input"),"BAC");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[4]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "FireRate",sectionName, "Input"),"Unable to enter FireRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[5]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "PerilsRate",sectionName, "Input"),"Unable to enter PerilsRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[6]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "SprinkRate",sectionName, "Input"),"Unable to enter SprinkRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[7]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "ADRate",sectionName, "Input"),"Unable to enter ADRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[8]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "SubsRate",sectionName, "Input"),"Unable to enter SubsRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[10]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "TechAdjust",sectionName, "Input"),"Unable to enter TechAdjust");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[11]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "CommAdjust", sectionName,"Input"),"Unable to enter CommAdjust");
				    	}else{
				    		Abvr = "BSI_BI_";
				    		//customAssert.assertTrue(k.DynamicXpathWebDriver(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[2]/input", (String)map_data.get(Abvr + "DeclaredValue"), "Input"),"BAC");
				    		//customAssert.assertTrue(k.DynamicXpathWebDriver(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[3]/input", (String)map_data.get(Abvr + "DeclarationUplift "), "Input"),"BAC");
				    		//customAssert.assertTrue(k.DynamicXpathWebDriver(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[4]/input", (String)map_data.get(Abvr + "IndPeriod"), "Input"),"BAC");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[5]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "FireRate",sectionName, "Input"),"Unable to enter FireRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[6]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "PerilsRate",sectionName, "Input"),"Unable to enter PerilsRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[7]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "SprinkRate",sectionName, "Input"),"Unable to enter SprinkRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[8]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "ADRate",sectionName, "Input"),"Unable to enter ADRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[9]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "SubsRate",sectionName, "Input"),"Unable to enter SubsRate");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[11]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "TechAdjust",sectionName, "Input"),"Unable to enter TechAdjust");
				    		customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, tablePath + "/tbody/tr["+(l+1)+"]/td[12]/input", common.NB_Structure_of_InnerPagesMaps , "Property Details", count , Abvr , "CommAdjust",sectionName, "Input"),"Unable to enter CommAdjust");
				    	}
				
			     }
			 
			 return bVal;
		 }
		 
		 // End of Property Details Table handling functions	 
		 
		 
		 // All Cover (Except Terrorism) Table Handling :
		
		 
		 public static double func_GIT_HandleTables(Map<Object, Object> map_data, String sCoverAbvr, String sSheetName, String sInnerSheetName) throws Throwable{
			 String SectionName, sFinalMap = "", Abvr = "", sTablePath = "";
			 double sAdjustedRate = 0.00, sPremium = 0.00, sTotalPremium = 0.00;
			 double dBookRate = 0.00;
			 int dynamicRow, count = 0;
			 boolean bVal =true;
			 
			 WebElement table= driver.findElement(By.className("matrix"));
			 int numOfRow = table.findElements(By.tagName("tr")).size();
			 
			 sTablePath = "html/body/div[3]/form/div/table[4]/tbody/tr[";
			 //Put data to table :
			 
			 if(sCoverAbvr.contains("EL") || sCoverAbvr.contains("M")|| sCoverAbvr.contains("FG") || sCoverAbvr.contains("CDS") || sCoverAbvr.contains("FF") || sCoverAbvr.contains("LOL")){
				 dynamicRow = 2;
			 }else if(sCoverAbvr.contains("SAR")){
				 dynamicRow = 1;
			 }else if(sCoverAbvr.contains("CAR")){
				 dynamicRow = 7;
			 }else if(sCoverAbvr.contains("CER")|| sCoverAbvr.contains("GIT")){
				 dynamicRow = 3;
			 }else if(sCoverAbvr.contains("MC")){
				 dynamicRow = 9;
			 }else{
				 dynamicRow = 4;
			 }
			 
			 for(int j=0; j<numOfRow-2; j++){							 
				 SectionName = driver.findElement(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[1]")).getText();
				 
				 if(j < (dynamicRow-1)){
								 
					 Abvr = sCoverAbvr +"_" + func_GetAbrrivation(sCoverAbvr, SectionName);
					
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[2]/input", common.NB_excel_data_map , sSheetName, 0 , Abvr , "SumInsured", SectionName, "Input"),"Unable to enter sum insured value in "+ sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[3]/input", common.NB_excel_data_map , sSheetName, 0 , Abvr , "BookRate",SectionName, "Input"),"Unable to enter BookRate value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[4]/input", common.NB_excel_data_map , sSheetName, 0 , Abvr , "TechAdjust",SectionName, "Input"),"Unable to enter TechAdjust value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[5]/input", common.NB_excel_data_map , sSheetName, 0 , Abvr , "CommAdjust", SectionName,"Input"),"Unable to enter CommAdjust value in " + sCoverAbvr +" Table for : " + SectionName);	 
				 }else{
					 if(sCoverAbvr.contains("FG")){
						 Abvr = "AD_SOE_";
					 }else{
						 Abvr = "AD_" + sCoverAbvr +"_";
					 }
					 
					 //customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[2]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, count , Abvr , "SumInsured", "Input"),"Unable to enter sum insured value in "+ sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[3]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, count , Abvr , "BookRate", SectionName,"Input"),"Unable to enter BookRate value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[4]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, count , Abvr , "TechAdjust",SectionName, "Input"),"Unable to enter TechAdjust value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[5]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, count , Abvr , "CommAdjust",SectionName, "Input"),"Unable to enter CommAdjust value in " + sCoverAbvr +" Table for : " + SectionName);
					 
				 }
			 }
			 customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Employers Liability .");
			 
			// Read, Calculate and Compare Values from table :
			 
			 Cover_TotalPremium = 0.00;
			 
			for(int j=0; j<numOfRow-2; j++){	
				 
				 SectionName = driver.findElement(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[1]")).getText();	
				 
				 if(j < (dynamicRow-1)){
					 Abvr = sCoverAbvr +"_" + func_GetAbrrivation(sCoverAbvr, SectionName);
					 sFinalMap = "StringMap";
				 }else{
					 if(sCoverAbvr.contains("FG")){
						 Abvr = "AD_SOE_";
					 }else{
						 Abvr = "AD_" + sCoverAbvr +"_";
					 }
					 sFinalMap = "InnerPageMap";
					 
				 }
				 
				 // -- reading Value from table :
				 
				 dBookRate = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[3]/input")).getAttribute("value").replaceAll(" ", ""));
				 sAdjustedRate = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[6]/input")).getAttribute("value").replaceAll(" ", ""));
				 sPremium = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[7]/input")).getAttribute("value").replaceAll(" ", ""));
				 
				 if(j == (numOfRow - 1)){
					 sTotalPremium = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr["+(numOfRow)+"]/td[7]/input")).getAttribute("value").replaceAll(" ", ""));
				 }
				 
				 // -- calculation :
				 
				 Cover_AdjustedRate = func_AllCovers_CalculateAdjustedRate(map_data,dBookRate, Abvr, SectionName, sFinalMap, count, sInnerSheetName);
				 String sPVal = driver.findElement(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr["+(j+1)+"]/td[2]/input")).getAttribute("value").replaceAll(",", "");
				 Cover_Premium = ((Cover_AdjustedRate * Double.parseDouble(sPVal))/100);
				 
				 
				 Cover_TotalPremium = Cover_TotalPremium + Cover_Premium;
							 
				// Compare values :
				 
				 //customAssert.assertTrue(compareValues(sAdjustedRate, Cover_AdjustedRate,"Adjusted Rate"),"Mismatched Adjusted Rate Values for secion : " + SectionName + " In Cover : "+ sCoverAbvr); 
				 customAssert.assertTrue(compareValues(sPremium, Cover_Premium, "Premium"),"Mismatched Premium Values for secion : " + SectionName + " In Cover : "+ sCoverAbvr);					 
				 
			 }			
			 
			 return Cover_TotalPremium;
		 }
		 
		 
		 
		 public static double func_CAR_HandleTables(Map<Object, Object> map_data, String sCoverAbvr, String sSheetName, String sInnerSheetName) throws Throwable, Exception{
			 String SectionName, sFinalMap = "", Abvr = "", sTablePath = "";
			 double sAdjustedRate = 0.00, sPremium = 0.00, sTotalPremium = 0.00;
			 double dBookRate = 0.00;
			 int dynamicRow, count = 0;
			 boolean bVal =true;
			 
			 WebElement table= driver.findElement(By.className("matrix"));
			 int numOfRow = table.findElements(By.tagName("tr")).size();
			 
			 sTablePath = "html/body/div[3]/form/div/table[10]/tbody/tr[";
			 //Put data to table :
			 
			 if(sCoverAbvr.equals("EL") || sCoverAbvr.equals("Money") || sCoverAbvr.contains("CDS") || sCoverAbvr.contains("FF") || sCoverAbvr.contains("LOL")){
				 dynamicRow = 2;
			 }else if(sCoverAbvr.contains("SAR")){
				 dynamicRow = 1;
			 }else if(sCoverAbvr.contains("CAR")){
				 dynamicRow = 7;
			 }else if(sCoverAbvr.contains("CER")|| sCoverAbvr.contains("GIT") || sCoverAbvr.contains("FG")){
				 dynamicRow = 3;
			 }else if(sCoverAbvr.contains("MC")){
				 dynamicRow = 9;
			 }else{
				 dynamicRow = 4;
			 }
			 
			 for(int j=0; j<numOfRow-2; j++){							 
				 SectionName = driver.findElement(By.xpath("html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[1]")).getText();
				 
				 if(j < (dynamicRow-1)){
								 
					 Abvr = sCoverAbvr +"_" + func_GetAbrrivation(sCoverAbvr, SectionName);
					
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[2]/input", common.NB_excel_data_map , sSheetName, 0 , Abvr , "SumInsured", SectionName,"Input"),"Unable to enter sum insured value in "+ sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[3]/input", common.NB_excel_data_map , sSheetName, 0 , Abvr , "BookRate", SectionName,"Input"),"Unable to enter BookRate value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[4]/input", common.NB_excel_data_map , sSheetName, 0 , Abvr , "TechAdjust",SectionName, "Input"),"Unable to enter TechAdjust value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[5]/input", common.NB_excel_data_map , sSheetName, 0 , Abvr , "CommAdjust", SectionName,"Input"),"Unable to enter CommAdjust value in " + sCoverAbvr +" Table for : " + SectionName);	 
				 }else{
					 
					 Abvr = "AD_" + sCoverAbvr +"_";
					 //customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[2]/input", common.NB_Structure_of_InnerPagesMaps , sSheetName, 0 , Abvr , "SumInsured", "Input"),"Unable to enter sum insured value in "+ sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[3]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, 0 , Abvr , "BookRate", SectionName , "Input"),"Unable to enter BookRate value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[4]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, 0 , Abvr , "TechAdjust",SectionName ,  "Input"),"Unable to enter TechAdjust value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[5]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, 0 , Abvr , "CommAdjust",SectionName, "Input"),"Unable to enter CommAdjust value in " + sCoverAbvr +" Table for : " + SectionName);
					 
				 }
			 }
			 customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Employers Liability .");
			 
			// Read, Calculate and Compare Values from table :
			 
			 Cover_TotalPremium = 0.00;
			 
			for(int j=0; j<numOfRow-2; j++){	
				 
				 SectionName = driver.findElement(By.xpath("html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[1]")).getText();	
				 
				 if(j < (dynamicRow-1)){
					 Abvr = sCoverAbvr +"_" + func_GetAbrrivation(sCoverAbvr, SectionName);
					 sFinalMap = "StringMap";
				 }else{
					 Abvr = "AD_" + sCoverAbvr +"_";
					 sFinalMap = "InnerPageMap";
					 
				 }
				 
				 // -- reading Value from table :
				 
				 dBookRate = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[3]/input")).getAttribute("value").replaceAll(" ", ""));
				 sAdjustedRate = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[6]/input")).getAttribute("value").replaceAll(" ", ""));
				 sPremium = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[7]/input")).getAttribute("value").replaceAll(" ", ""));
				 
				 if(j == (numOfRow - 1)){
					 sTotalPremium = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[10]/tbody/tr["+(numOfRow)+"]/td[7]/input")).getAttribute("value").replaceAll(" ", ""));
				 }
				 
				 // -- calculation :
				 
				 Cover_AdjustedRate = func_AllCovers_CalculateAdjustedRate(map_data,dBookRate, Abvr, SectionName, sFinalMap, count, sInnerSheetName);
				 String sPVal = driver.findElement(By.xpath("html/body/div[3]/form/div/table[10]/tbody/tr["+(j+1)+"]/td[2]/input")).getAttribute("value").replaceAll(",", "");
				 Cover_Premium = ((Cover_AdjustedRate * Double.parseDouble(sPVal))/100);
				 
				 
				 Cover_TotalPremium = Cover_TotalPremium + Cover_Premium;
							 
				// Compare values :
				 
				 //customAssert.assertTrue(compareValues(sAdjustedRate, Cover_AdjustedRate,"Adjusted Rate"),"Mismatched Adjusted Rate Values for secion : " + SectionName + " In Cover : "+ sCoverAbvr); 
				 customAssert.assertTrue(compareValues(sPremium, Double.parseDouble(common.roundedOff(Double.toString(Cover_Premium))), "Premium"),"Mismatched Premium Values for secion : " + SectionName + " In Cover : "+ sCoverAbvr);					 
				 
			 }			
			 
			 return Cover_TotalPremium;
		 }
		 
		 
		 
		 public static double func_AllCvers_HandleTables(Map<Object, Object> map_data, String sCoverAbvr, String sSheetName, String sInnerSheetName) throws Throwable, Exception{
			 String SectionName, sFinalMap = "", Abvr = "", sTablePath = "";
			 double sAdjustedRate = 0.00, sPremium = 0.00, sTotalPremium = 0.00;
			 double dBookRate = 0.00;
			 int dynamicRow, count = 0;
			 boolean bVal =true;
			 
			 WebElement table= driver.findElement(By.className("matrix"));
			 int numOfRow = table.findElements(By.tagName("tr")).size();
			 
			 sTablePath = "//*[@id='table0']/tbody/";
			 //Put data to table :
			 
			 if(sCoverAbvr.equals("MC")){
				 dynamicRow = 9;
			 }else if(sCoverAbvr.contains("EL") ||sCoverAbvr.contains("M")|| sCoverAbvr.contains("POL")||sCoverAbvr.contains("CDS") || sCoverAbvr.contains("FF") || sCoverAbvr.contains("LOL")){
				 dynamicRow = 2;
			 }else if(sCoverAbvr.contains("SAR")){
				 dynamicRow = 1;
			 }else if(sCoverAbvr.contains("CAR")){
				 dynamicRow = 7;
			 }else if(sCoverAbvr.contains("CER")|| sCoverAbvr.contains("GIT") || sCoverAbvr.contains("FG")){
				 dynamicRow = 3;
			 }else{
				 dynamicRow = 4;
			 }
			 
			 for(int j=0; j<numOfRow-2; j++){							 
				 SectionName = table.findElement(By.xpath("//*[@id='table0']/tbody/tr["+(j+1)+"]/td[1]")).getText();
				 
				 if(j < (dynamicRow-1)){
								 
					 Abvr = sCoverAbvr +"_" + func_GetAbrrivation(sCoverAbvr, SectionName);
					
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[2]/input", map_data , sSheetName , 0 , Abvr ,"SumInsured",SectionName, "Input"),"Unable to enter sum insured value in "+ sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[3]/input", map_data , sSheetName , 0 , Abvr, "BookRate", SectionName,"Input"),"Unable to enter BookRate value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[4]/input", map_data , sSheetName , 0 , Abvr ,"TechAdjust",SectionName, "Input"),"Unable to enter TechAdjust value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[5]/input", map_data , sSheetName , 0 , Abvr ,"CommAdjust", SectionName,"Input"),"Unable to enter CommAdjust value in " + sCoverAbvr +" Table for : " + SectionName);	 
				 }else{
					 
					 Abvr = "AD_" + sCoverAbvr +"_";
					 //customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[2]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, count , Abvr , "SumInsured", "Input"),"Unable to enter sum insured value in "+ sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[3]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, count , Abvr , "BookRate",SectionName ,"Input"),"Unable to enter BookRate value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[4]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, count , Abvr , "TechAdjust",SectionName, "Input"),"Unable to enter TechAdjust value in " + sCoverAbvr +" Table for : " + SectionName);
					 customAssert.assertTrue(k.DynamicXpathWebDriver_Inner(driver, "//*[@id='table0']/tbody/tr["+(j+1)+"]/td[5]/input", common.NB_Structure_of_InnerPagesMaps , sInnerSheetName, count , Abvr , "CommAdjust",SectionName, "Input"),"Unable to enter CommAdjust value in " + sCoverAbvr +" Table for : " + SectionName);
				 }
			 }
			 customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Employers Liability .");
			 
			// Read, Calculate and Compare Values from table :
			 
			Cover_TotalPremium = 0.00;
			
			for(int j=0; j<numOfRow-2; j++){	
				 
				 SectionName = driver.findElement(By.xpath("//*[@id='table0']/tbody/tr["+(j+1)+"]/td[1]")).getText();	
				 
				 if(j < (dynamicRow-1)){
					 Abvr = sCoverAbvr +"_" + func_GetAbrrivation(sCoverAbvr, SectionName);
					 sFinalMap = "StringMap";
				 }else{
					 Abvr = "AD_" + sCoverAbvr +"_";
					 sFinalMap = "InnerPageMap";
					 
				 }
				 
				 // -- reading Value from table :
				 
				 dBookRate = Double.parseDouble(driver.findElement(By.xpath("//*[@id='table0']/tbody/tr["+(j+1)+"]/td[3]/input")).getAttribute("value").replaceAll(" ", ""));
				 sAdjustedRate = Double.parseDouble(driver.findElement(By.xpath("//*[@id='table0']/tbody/tr["+(j+1)+"]/td[6]/input")).getAttribute("value").replaceAll(" ", ""));
				 sPremium = Double.parseDouble(driver.findElement(By.xpath("//*[@id='table0']/tbody/tr["+(j+1)+"]/td[7]/input")).getAttribute("value").replaceAll(" ", ""));
				 
				 if(j == (numOfRow - 1)){
					 sTotalPremium = Double.parseDouble(driver.findElement(By.xpath("//*[@id='table0']/tbody/tr["+(numOfRow)+"]/td[7]/input")).getAttribute("value").replaceAll(" ", ""));
				 }
				 
				 // -- calculation :
				 
				 Cover_AdjustedRate = func_AllCovers_CalculateAdjustedRate(map_data,dBookRate, Abvr, SectionName, sFinalMap, count, sInnerSheetName);
				 String sPVal = driver.findElement(By.xpath("//*[@id='table0']/tbody/tr["+(j+1)+"]/td[2]/input")).getAttribute("value").replaceAll(",", "");
				 Cover_Premium = ((Cover_AdjustedRate * Double.parseDouble(sPVal))/100);
				 
				 
				 Cover_TotalPremium = Cover_TotalPremium + Cover_Premium;
							 
				// Compare values :
				 
				 //customAssert.assertTrue(compareValues(sAdjustedRate, Cover_AdjustedRate,"Adjusted Rate"),"Mismatched Adjusted Rate Values for secion : " + SectionName + " In Cover : "+ sCoverAbvr); 
				 customAssert.assertTrue(compareValues(sPremium, Double.parseDouble(common.roundedOff(Double.toString(Cover_Premium))), "Premium"),"Mismatched Premium Values for secion : " + SectionName + " In Cover : "+ sCoverAbvr);					 
				 
			 }			
			 
			 return Cover_TotalPremium;
		 }
		 
		 public static double func_AllCovers_CalculateAdjustedRate(Map<Object, Object> map_data, double dBookRate, String Abvr, String sectionName, String sMap, int count, String sSheetName){
				 double adjustedRate = 0.00;
				 double fisrtFactor = 0.00, sFactor = 0.00;
				 
				 
				 if(sMap.contains("InnerPageMap")){
					 fisrtFactor = ((((Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get(sSheetName).get(count).get(Abvr + "TechAdjust"))) * dBookRate)/ 100 ) + dBookRate );
					 sFactor = (((Double.parseDouble(common.NB_Structure_of_InnerPagesMaps.get(sSheetName).get(count).get(Abvr + "CommAdjust")))* fisrtFactor)/100);
				 }else{
					 fisrtFactor = ((((Double.parseDouble((String)map_data.get(Abvr + "TechAdjust"))) * dBookRate)/ 100 ) + dBookRate );
					 sFactor = (((Double.parseDouble((String)map_data.get(Abvr + "CommAdjust")))* fisrtFactor)/100);
				 }
				 
				 adjustedRate = fisrtFactor + sFactor ;
		 
				 return adjustedRate;
		 }
		 
		 // End of  All Cover (Except Terrorism) Table Handling functions
		 	
		 // Terrorism Table handling :
		 
		 public static boolean func_Terrorism_HandleTables(Map<Object, Object> map_data, String sCoverAbvr) throws Throwable, Exception{
			 String SectionName, Abvr = "";
			 double sPremium = 0.00, sTotalPremium = 0.00;
			 double dBookRate = 0.00;
			 boolean bVal =true;
			 
			 WebElement table= driver.findElement(By.className("matrix"));
			 int numOfRow = table.findElements(By.tagName("tr")).size();
			 		 		 
			 for(int j=0; j<numOfRow-2; j++){	
				 
				 SectionName = driver.findElement(By.xpath("html/body/div[3]/form/div/table[1]/tbody/tr["+(j+1)+"]/td[1]")).getText();	
				 Abvr = sCoverAbvr +"_" + func_GetAbrrivation(sCoverAbvr, SectionName);
				 
				 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[1]/tbody/tr["+(j+1)+"]/td[2]/input", map_data , "Terrorism" , 0 , Abvr , "SumInsured", SectionName , "Input"),"Unable to enter sum insured value in "+ sCoverAbvr +" Table for : " + SectionName);
				 customAssert.assertTrue(k.DynamicXpathWebDriver(driver, "html/body/div[3]/form/div/table[1]/tbody/tr["+(j+1)+"]/td[3]/input", map_data , "Terrorism" , 0 , Abvr , "BookRate", SectionName , "Input"),"Unable to enter BookRate value in " + sCoverAbvr +" Table for : " + SectionName);
				
			 }
			 
			 customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Terrorism .");
			 
			// Read, Calculate and Compare Values from table :
			 
			 for(int j=0; j<numOfRow-2; j++){	
				 
				 SectionName = driver.findElement(By.xpath("html/body/div[3]/form/div/table[1]/tbody/tr["+(j+1)+"]/td[1]")).getText();	
				 Abvr = sCoverAbvr +"_" + func_GetAbrrivation(sCoverAbvr, SectionName);
							 
				 // -- reading Value from table :
				 
				 dBookRate = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[1]/tbody/tr["+(j+1)+"]/td[3]/input")).getAttribute("value").replaceAll(" ", ""));			
				 sPremium = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[1]/tbody/tr["+(j+1)+"]/td[4]/input")).getAttribute("value").replaceAll(" ", ""));
				 
				 if((j+1) == (numOfRow-1)){
					 sTotalPremium = Double.parseDouble(table.findElement(By.xpath("html/body/div[3]/form/div/table[1]/tbody/tr["+(numOfRow)+"]/td["+7+"]/input")).getAttribute("value").replaceAll(" ", ""));
				 }
				 
				 // -- calculation :			 
				 double sVal = Double.parseDouble(driver.findElement(By.xpath("html/body/div[3]/form/div/table[1]/tbody/tr["+(j+1)+"]/td[2]/input")).getAttribute("value").replaceAll(",", ""));
				 Cover_Premium = ((sVal*dBookRate)/100);
				 Cover_TotalPremium = Cover_TotalPremium + Cover_Premium;
							 
				// Compare values :
						 
				 if((j+1) == (numOfRow-1)){
					 customAssert.assertTrue(compareValues(sTotalPremium, Double.parseDouble(common.roundedOff(Double.toString(Cover_TotalPremium))),"Total premium"),"Mismatched Total premium Values for secion : " + SectionName + " In Cover : "+ sCoverAbvr );
					 TestUtil.WriteDataToXl(common.product+"_"+common.businessEvent, "Premium Summary",(String)common.NB_excel_data_map.get("Automation Key"), "PS_Terrorism_NP", String.valueOf(Cover_TotalPremium), map_data);
				 }		 
			 }	
			 
			 return bVal;
		 }
		 		 	 
		 // End of Terrorism table handling unctions
		 
		 public static String func_GetAbrrivation(String CoverName, String SecionName){
				String Abrrivation = "";
				
				if(SecionName.length() < 1){
					Abrrivation = "";
				}else{
					
				switch(CoverName){
				 
				 	case "MD":
				 		
				 		switch(SecionName){
					 		case "Buildings":
						 		Abrrivation = "BLD_";
						 		break;
						 	case "Machinery":
						 		Abrrivation = "MCH_";
						 		break;
						 	case "Stock":
						 		Abrrivation = "Stock_";
						 		break;
						 	case "Contents":
						 		Abrrivation = "CO_";
						 		break;
						 	case "Computer Equipment":
						 		Abrrivation = "CEP_";
						 		break;
						 		
						 	default :
						 		Abrrivation = "";	
				 		}
				 		
				 		break;
				 
				 	case "BI":
				 		
				 			switch(SecionName){
					 			case "Gross Profit":
							 		Abrrivation = "GPT_";
							 		break;
							 	case "Revenue":
							 		Abrrivation = "RVN_";
							 		break;
							 	case "Rent Receivable":
							 		Abrrivation = "RentRCV_";
							 		break;
							 	case "Rent Payable":
							 		Abrrivation = "RentP_";
							 		break;
							 	case "Increased Cost of Working":
							 		Abrrivation = "ICW_";
							 		break;
							 	case "Additional Increased Cost of Working":
							 		Abrrivation = "AICW_";
							 		break;
							 	default :
							 		Abrrivation = "";
				 			}
				 			
				 		break;
				 	case "LOI":
				 		
			 			switch(SecionName){
				 			case "Rent Receivable":
						 		Abrrivation = "RentRCV_";
						 		break;
						 	default :
						 		Abrrivation = "";
			 			}
			 			
			 		break;
				 	case "EL":
				 		Abrrivation = "CW_";
			 			break;
			 		
				 	case "PL":
				 		
			 			switch(SecionName){
				 			case "Turnover":
						 		Abrrivation = "Turnover_";
						 		break;
						 	case "Work Away Wages":
						 		Abrrivation = "WAW_";
						 		break;
						 	case "Payments to Bona Fide Subcontractors":
						 		Abrrivation = "PBFS_";
						 		break;				 		
						 	default :
						 		Abrrivation = "";
			 			}
			 			break;

				 	case "PRL":
				 		
			 			switch(SecionName){
				 			case "Turnover UK Only":
						 		Abrrivation = "TurnoverUK_";
						 		break;
						 	case "Turnover North America":
						 		Abrrivation = "TurnoverNA_";
						 		break;
						 	case "Turnover Rest Of World":
						 		Abrrivation = "TurnoverROW_";
						 		break;				 		
						 	default :
						 		Abrrivation = "";
			 			}
			 			break;
				 	case "POL":
				 		
			 			switch(SecionName){
				 			case "Buildings Declared Value":
						 		Abrrivation = "BuildingsDeclaredValue_";
						 		break;
						 	default :
						 		Abrrivation = "";
			 			}
			 			break;	
				 	case "SAR":
				 			Abrrivation = "";
				 			break;
				 			
				 	case "CAR":
				 		
			 			switch(SecionName){
				 			case "Contracting Turnover":
						 		Abrrivation = "CT_";
						 		break;
						 	case "Contract Value":
						 		Abrrivation = "CV_";
						 		break;
						 	case "Constructional Plant Tools & Equipment":
						 		Abrrivation = "CPTE_";
						 		break;				 		
						 	case "Temporary Buildings":
						 		Abrrivation = "TB_";
						 		break;
						 	case "Hired In Property Charges":
						 		Abrrivation = "HIPC_";
						 		break;
						 	case "Employees Personal Property":
						 		Abrrivation = "EPP_";
						 		break;
						 	default :
						 		Abrrivation = "";
			 			}
			 			break;
			 			
				 	case "CER":
				 		
			 			switch(SecionName){
				 			case "Computers":
						 		Abrrivation = "Computers_";
						 		break;
						 	case "Additional Expenditure":
						 		Abrrivation = "AdditionalExp_";
						 		break;
						 	default :
						 		Abrrivation = "";
			 			}
			 			break;
			 			
				 	case "M":
				 		Abrrivation = "EAC_";
			 			break;
			 			
				 	case "GIT":
				 		
			 			switch(SecionName){
				 			case "Estimated Annual Sendings":
						 		Abrrivation = "EAS_";
						 		break;
						 	case "Total Sum Insured for Your vehicles":
						 		Abrrivation = "TSIFYV_";
						 		break;
						 	default :
						 		Abrrivation = "";
			 			}
			 			break;
			 			
				 	case "MC":
				 		
			 			switch(SecionName){
				 			case "Zone 1 Carryings":
						 		Abrrivation = "Zone1_";
						 		break;
						 	case "Zone 2 Carryings":
						 		Abrrivation = "Zone2_";
						 		break;
						 	case "Zone 3 Carryings":
						 		Abrrivation = "Zone3_";
						 		break;				 		
						 	case "Zone 4 Carryings":
						 		Abrrivation = "Zone4_";
						 		break;
						 	case "Zone 5 Carryings":
						 		Abrrivation = "Zone5_";
						 		break;
						 	case "Zone 6 Carryings":
						 		Abrrivation = "Zone6_";
						 		break;
						 	case "Zone 7 Carryings":
						 		Abrrivation = "Zone7_";
						 		break;
						 	case "Zone 8 Carryings":
						 		Abrrivation = "Zone8_";
						 		break;
						 	default :
						 		Abrrivation = "";
			 			}
			 			break;
			 			
				 	case "CDS":
				 		Abrrivation = "ALOL_";
			 			break;
			 		
				 	case "DO":		 		
			 				 		
			 			switch(SecionName){
				 			case "Directors and Officers Limit":
						 		Abrrivation = "DOL_";
						 		break;
						 	case "Corporate Liability Limit":
						 		Abrrivation = "CLL_";
						 		break;
						 	case "Employment Practices Liability Limit":
						 		Abrrivation = "EPLL_";
						 		break;				 		
						 	default :
						 		Abrrivation = "";
			 			}
			 			break;
			 			
				 	case "FF":
				 		Abrrivation = "";
			 			break;
			 			
				 	case "LOL":
				 		Abrrivation = "LL_";
			 			break;
			 		
				 	case "FG":
				 		Abrrivation = "AYE_";
			 			break;
			 			
				 	case "TER":
				 		
			 			switch(SecionName){
				 			case "Zone A":
						 		Abrrivation = "ZoneA_";
						 		break;
						 	case "Zone B":
						 		Abrrivation = "ZoneB_";
						 		break;
						 	case "Zone C":
						 		Abrrivation = "ZoneC_";
						 		break;				 		
						 	case "Zone D":
						 		Abrrivation = "ZoneD_";
						 		break;
						 	case "Average Items":
						 		Abrrivation = "AverageItems_";
						 		break;
						 	case "Business Interruption":
						 		Abrrivation = "BI_";
						 		break;
						 	case "Loss of Income":
						 		Abrrivation = "LOI_";
						 		break;
						 	default :
						 		Abrrivation = "";
			 			}
			 			break;
				 }
			
				}
				 
				 return Abrrivation;
			 }

		 
	// --------------------- Cover Specific Functions Ends here ---------------------------------------------
		
		 
 public boolean funcPremiumSummary(Map<Object, Object> map_data,String code,String event,String flowName){
		
		boolean r_value= true;
		Date currentDate = new Date();
		String testName = (String)map_data.get("Automation Key");
	String customPolicyDuration=null;
	SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
	//String inception_date = common.daysIncrementWithOutFormation(df.format(currentDate), 0);
	try{
		customAssert.assertTrue(common.funcPageNavigation("Premium Summary", ""),"Premium Summary page navigations issue(S)");
		
		int policy_Duration = Integer.parseInt((String)map_data.get("PS_Duration"));
		boolean negativeFlag = policy_Duration < 0 ?false:true;
		customAssert.assertTrue(negativeFlag , "Policy Duration should be non-negative value . ");
		boolean Months18_Flag = policy_Duration < 550 ?true:false;
		customAssert.assertTrue(Months18_Flag , "Policy Duration Should be less than 18 Months ( <= 549 Days) . ");
		policy_Duration--;
		String Policy_End_Date = common.daysIncrementWithOutFormation((String)map_data.get("PS_PolicyStartDate"), policy_Duration);
		
		if(((String)map_data.get("PS_DefaultStartEndDate")).equals("No")){
			customAssert.assertTrue(k.Click("Policy_Start_Date"), "Unable to Click Policy_Start_Date date picker .");
			customAssert.assertTrue(k.Input("Policy_Start_Date", (String)map_data.get("PS_PolicyStartDate")),"Unable to Enter Policy_Start_Date .");
			customAssert.assertTrue(k.Click("calander_btn"), "Unable to click on done button in calander.");
			
			customAssert.assertTrue(k.Click("Policy_End_Date"), "Unable to Click Policy_End_Date date picker .");
			customAssert.assertTrue(k.Input("Policy_End_Date", Policy_End_Date),"Unable to Enter Policy_End_Date .");
			customAssert.assertTrue(k.Click("calander_btn"), "Unable to click on done button in calander.");
			
		}	
		k.waitTwoSeconds();
		customAssert.assertTrue(k.SelectRadioBtn("Payment_Warranty_rules", (String)map_data.get("PS_PaymentWarrantyRules")),"Unable to Select Payment_Warranty_rules radio button . ");
		
		if(((String)map_data.get("PS_PaymentWarrantyRules")).equals("Yes") && !common.currentRunningFlow.equalsIgnoreCase("MTA")){
			
			customAssert.assertTrue(k.Click("Payment_Warranty_Due_Date"), "Unable to Click Payment_Warranty_Due_Date date picker .");
			customAssert.assertTrue(k.Input("Payment_Warranty_Due_Date", (String)map_data.get("PS_PaymentWarrantyDueDate")),"Unable to Enter Payment_Warranty_Due_Date .");
			customAssert.assertTrue(k.Click("calander_btn"), "Unable to click on done button in calander.");
		}
		customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Premium Summary .");
		customAssert.assertTrue(common.funcPremiumSummaryTable(code, event, map_data),"Premium summary Calculation function failed");		
		customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Premium Summary .");
		customPolicyDuration = k.getText("Policy_Duration");
		customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,map_data),"Error while writing Policy Duration data to excel .");
		TestUtil.reportStatus("Policy Duration = "+customPolicyDuration+" Days . ", "Info", true);
		
		TestUtil.reportStatus("Premium Summary details are filled sucessfully . ", "Info", true);
		
	}catch(Throwable t){
		return false;
	}
	
	return r_value;
	}
 
 public int countDecimalPlaces(String d_Number){
	 
	 if(d_Number!=null){
	 	int integerPlaces = d_Number.indexOf('.');
		int decimalPlaces = d_Number.length() - integerPlaces - 1;
		if(integerPlaces==-1){
			return 0;
		}
	 
		return decimalPlaces;
	 }else {
		 return 0;
	 }
}
 public boolean decideRewindMethod(){
	 
		
		try {
			
			
			switch (CommonFunction_GUS.product) {
			case "CCF":
				if(common.currentRunningFlow.equals("NB")){
					customAssert.assertTrue(common_CCF.funcRewindOperation(),"Error in function Rewind Operation .");
				}else if(common.currentRunningFlow.equals("MTA")){
					customAssert.assertTrue(common_CCF.funcRewindOperationMTA(),"Error in function Rewind Operation .");
				}
				
				break;
			case "CCG":
				customAssert.assertTrue(common_CCF.funcRewindOperation(),"Error in function Rewind Operation .");
				break;
			case "CTA":
				customAssert.assertTrue(common_CTA.funcRewindOperation(),"Error in function Rewind Operation .");
				break;
			case "POB":
				customAssert.assertTrue(common_POB.funcRewindOperation(),"Error in function Rewind Operation .");
				break;
			case "POC":
				customAssert.assertTrue(common_POB.funcRewindOperation(),"Error in function Rewind Operation .");
				break;
			case "XOE":
				customAssert.assertTrue(common_XOE.funcRewindOperation(),"Error in function Rewind Operation .");
				break;
			case "SPI":
				if(common.currentRunningFlow.equals("NB")){
					customAssert.assertTrue(common_SPI.funcRewindOperation(),"Error in function Rewind Operation .");
				}else if(common.currentRunningFlow.equals("MTA")){
					customAssert.assertTrue(common_SPI.funcMTARewindOperation(),"Error in function MTA Rewind Operation .");
				}
				else if(common.currentRunningFlow.equals("Renewal")){
					customAssert.assertTrue(common_SPI.funcRenewalRewindOperation(),"Error in function Renewal Rewind Operation .");
				}
				break;

			}
			
			
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
		
	}
 public boolean decideMTAMethod(){
	 
		
		try {
			
			
			switch (CommonFunction_GUS.product) {
			case "CCF":
				common_CCF.funcMTAOperation();
				break;
			case "CCG":
				
				break;
			case "CTA":
				common_CTA.funcRewindOperation();
				break;
			case "POB":
				common_POB.funcRewindOperation();
				
				break;
			case "POC":
				common_POB.funcRewindOperation();
				break;
			case "XOE":
				common_XOE.funcRewindOperation();
				break;
			case "SPI":
				customAssert.assertTrue(common_SPI.funcRewindOperation(),"Error in function Rewind Operation .");
				break;

			}
			
			
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
		
	}
 
 public boolean funcRewind(){
	 
		
		try {
			
			customAssert.assertTrue(common.funcButtonSelection("Rewind"));
			customAssert.assertTrue(common.funcPageNavigation("Rewind Policy", ""),"Rewind Policy page navigations issue(S)");
			
			if(common.currentRunningFlow.equals("MTA")){
				customAssert.assertTrue(k.Input("RewindReason", (String)common.MTA_excel_data_map.get("MTA_RewindReason")));
				customAssert.assertTrue(common.funcButtonSelection("Rewind Endorsement"));
			}else if(common.currentRunningFlow.equals("NB")){
				customAssert.assertTrue(k.Input("RewindReason", (String)common.NB_excel_data_map.get("NB_RewindReason")));
				customAssert.assertTrue(common.funcButtonSelection("Rewind"));
			}else if(common.currentRunningFlow.equals("Renewal")){
				customAssert.assertTrue(k.Input("RewindReason", (String)common.Renewal_excel_data_map.get("Renewal_RewindReason")));
				customAssert.assertTrue(common.funcButtonSelection("Rewind"));
			}
			//customAssert.assertTrue(common.funcButtonSelection("Rewind"));
			customAssert.assertTrue(common.funcPageNavigation("Premium Summary", ""),"Rewind Policy page navigations issue(S)");
			//customAssert.assertTrue(common.funcButtonSelection("Put Rewind On Cover"));
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
		
	}
 
 @SuppressWarnings({ "static-access", "unused" })
	public boolean insuranceTaxVerification() {
		

		
		try {
			common.funcButtonSelection("Insurance Tax");
			
			customAssert.assertTrue(funcPageNavigation("Tax Adjustment", ""),"Unable to land on Tax adjustment screen.");
			String sectionName;
			switch ((String)common.NB_excel_data_map.get("PS_InsuranceTaxButton")) {
			case "Yes":
				String value = null;
				List<WebElement> list = k.findElements("insuranceTaxExemptionRadioButton");
				for(int i=0;i<list.size();i++){
					
					boolean selectedStatus =  list.get(i).isSelected();
					if(selectedStatus){
						value = list.get(i).getAttribute("Value");	
					}
				}
				
				taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY"); 
				List<WebElement> listOfCovers = taxTable_tBody.findElements(By.tagName("tr"));
				countOfCovers = listOfCovers.size();
				
				for(int j=0;j<countOfCovers-1;j++){
					
					sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText().replaceAll(" ", "");
					if(sectionName.contains("Excess of Loss")){
						sectionName = "PropertyExcessofLoss";
					}
					
					if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
						continue;
					}else{
						
						String actGP =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText();
						String actGT =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText();
						
						String expGP = (String)common.NB_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
						String expGT = (String)common.NB_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
						
						if(verification(actGP,expGP,sectionName,"Gross Premium") && 
								verification(actGT,expGT,sectionName,"Gross Tax")){
							
							TestUtil.reportStatus("[<b>  "+sectionName+"  </b>]  cover --- Verified on Insurance Tax screen. Gross Premium is :  <b>[ "+expGP+" ]</b> , Gross Tax is :  <b>[ "+expGT+" ]</b> ", "PASS", false);
						}
						
					}
					taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
					List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
					countOfCovers = list3.size();
					
				}
				
				TestUtil.reportStatus("<b>Policy Exempt from insurance tax radio button is selected as 'Yes' Hence skipped adjustment operation for all covers.</b>", "Info", false);
				break;
				
			case "No":
				
				TestUtil.reportStatus("<b> Tax adjustment Verificatiion is started after removing cover. </b>", "Info", false);
				taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY"); 
				List<WebElement> list2 = taxTable_tBody.findElements(By.tagName("tr"));
				countOfCovers = list2.size();
				
				
				for(int j=0;j<countOfCovers-1;j++){
					
					taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
					sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
					if(sectionName.contains("Excess of Loss")){
						sectionName = "Property Excess of Loss";
					}
					
					if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
						continue;
					}else{
						
						String actGP =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText();
						String actGT =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText();
						
						String expGP = (String)common.NB_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
						String expGT = (String)common.NB_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
						
						if(verification(actGP,expGP,sectionName,"Gross Premium") && 
								verification(actGT,expGT,sectionName,"Gross Tax")){
							
							TestUtil.reportStatus("[<b>  "+sectionName+"  </b>]  cover --- Verified on Insurance Tax screen. Gross Premium is :  <b>[ "+expGP+" ]</b> , Gross Tax is :  <b>[ "+expGT+" ]</b> ", "PASS", false);
						}
						
					}
					taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
					List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
					countOfCovers = list3.size();
				}
				TestUtil.reportStatus("<b> Tax adjustment operatios is completed. </b>", "Info", false);
				break;
			}
			
			common.funcPageNavigation("", "Save");
			k.Click("Tax_adj_BackBtn");
				
			return true;
		}catch (Throwable t) {
			return false;
		}
		
	}
 
 /****
  * 
  * Variable - tradeCodeValue ---- > TradeCode from Excel Sheet
  * Variable - pageName ---- > Trade code selection Page. E.G - Property Details/Policy Details
  * Variable - currentPropertyIndex ---- > This variable is only used for Inner pages to get current index. For non Inner pass as "0"
  * 
  */
 	@SuppressWarnings("static-access")
	public boolean tradeCodeSelection(String tradeCodeValue , String pageName , int currentPropertyIndex) {
		
 		try{
 			customAssert.assertTrue(k.Click("CCF_Btn_SelectTradeCode"), "Unable to click on Select Trade Code button in Policy Details .");
 			customAssert.assertTrue(common.funcPageNavigation("Trade Code Selection", ""), "Navigation problem to Trade Code Selection page .");
 			String a_selectedTradeCode = null;
 			String a_selectedTradeCode_desc = null;
 	 		List<WebElement> list = k.findElements("TradeCodeName");/*driver.findElements(By.xpath("//*[contains(@class,'std')]/tBody/tr/td/a"));*/
 	 		String flag = null;
 	 		String v_exp_selectedTradeCode_Desc = null;
 	 		for(int i=0;i<list.size();i++){
 	 			
 	 			String tradeCode = list.get(i).getText();
 	 			if(tradeCodeValue.equalsIgnoreCase(tradeCode)){
 	 				JavascriptExecutor j_exe = (JavascriptExecutor) driver;
 					j_exe.executeScript("arguments[0].scrollIntoView(true);", list.get(i));
 					v_exp_selectedTradeCode_Desc = driver.findElement(By.xpath("//*[contains(@class,'std')]/tBody/tr["+(i+1)+"]/td[2]")).getText();
 	 				list.get(i).click();
 	 				flag = "found";
 	 				break;
 	 			}else{
 	 				flag = "Not Found";
 	 			}
 	 		}
 	 		
 	 		if(!flag.equalsIgnoreCase("found")){
 	 			
 	 			if((String)common.NB_excel_data_map.get("PD_TCS_TradeCode")==null || ((String)common.NB_excel_data_map.get("PD_TCS_TradeCode")).equalsIgnoreCase("")){
 	 				TestUtil.reportStatus("Trade code value is empty. Please check your datasheet. Trade code is mandatory to proceed further. Path : "+common.product+"_"+common.businessEvent+".xlsx > "+(String)common.NB_excel_data_map.get("Automation Key")+" > Policy Details Sheet > Field Name : TCS_TradeCode ", "Fail", true);
 	 			}else{
 	 				TestUtil.reportStatus("Trade code <b>[  "+(String)common.NB_excel_data_map.get("PD_TCS_TradeCode")+"  ]</b> is not present in the selection list. Please enter valid Trade code. Trade code is mandatory to proceed further. Path to update value : "+common.product+"_"+common.businessEvent+".xlsx > "+(String)common.NB_excel_data_map.get("Automation Key")+" > Policy Details Sheet > Field Name : TCS_TradeCode ", "Fail", true);
 	 			}
 	 			return false;
 	 		}else{
 	 			
 	 			switch (pageName) {
				case "Policy Details":
					
					switch (CommonFunction_GUS.product) {
					case "CCG":
						TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Policy Details" , (String)common.NB_excel_data_map.get("Automation Key"), "PD_TCS_TradeCode_HazardGroup", k.getText("CCF_Gray_PD_TradeCode_HazardGroup"), common.NB_excel_data_map);
						break;
					case "POC":
						TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Policy Details" , (String)common.NB_excel_data_map.get("Automation Key"), "PD_TCS_TradeCode_HazardGroup", k.getText("CCF_Gray_PD_TradeCode_HazardGroup"), common.NB_excel_data_map);
						break;
					case "XOE":
						TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Policy Details" , (String)common.NB_excel_data_map.get("Automation Key"), "PD_TCS_TradeCode_HazardGroup", k.getText("CCF_Gray_PD_TradeCode_HazardGroup"), common.NB_excel_data_map);
						break;	
					}	
					a_selectedTradeCode = k.getText("CCF_Gray_PD_TradeCode");
					customAssert.SoftAssertEquals(a_selectedTradeCode, tradeCodeValue,"Trade Code on Policy Details Screen is incorrect - Expected [ <b>"+tradeCodeValue+"</b>] and Actual [<b>"+a_selectedTradeCode+"</b>]  .");
	 	 			TestUtil.reportStatus("Trade Code on Policy Details Screen is Correct.", "Pass", true);
	 	 			
	 	 			a_selectedTradeCode_desc = k.getText("CCF_Gray_PD_TradeCode_Desc");
	 	 			customAssert.SoftAssertEquals(a_selectedTradeCode_desc, v_exp_selectedTradeCode_Desc,"Trade Code Description on Policy Details Screen is incorrect- Expected [ <b>"+v_exp_selectedTradeCode_Desc+"</b>] and Actual [<b>"+a_selectedTradeCode+"</b>]  . ");
	 	 			TestUtil.reportStatus("Trade Code Description on Policy Details Screen is Correct.", "Pass", true);
	 	 			
					break;
				case "Property Details":
					a_selectedTradeCode = k.getText("CCF_Gray_PD_TradeCode");
	 	 			customAssert.SoftAssertEquals(a_selectedTradeCode, tradeCodeValue,"Trade Code on Policy Details Screen is incorrect - Expected [ <b>"+tradeCodeValue+"</b>] and Actual [<b>"+a_selectedTradeCode+"</b>]  .");
	 	 			TestUtil.reportStatus("Trade Code on Policy Details Screen is Correct.", "Pass", true);
	 	 			
	 	 			a_selectedTradeCode_desc = k.getText("CCF_Gray_PD_TradeCode_Desc");
	 	 			customAssert.SoftAssertEquals(a_selectedTradeCode_desc, v_exp_selectedTradeCode_Desc,"Trade Code Description on Policy Details Screen is incorrect- Expected [ <b>"+v_exp_selectedTradeCode_Desc+"</b>] and Actual [<b>"+a_selectedTradeCode+"</b>]  . ");
	 	 			TestUtil.reportStatus("Trade Code Description on Policy Details Screen is Correct.", "Pass", true);
	 	 			
					break;
				case "Contractors All Risks":
					a_selectedTradeCode = k.getText("CCF_Gray_PD_TradeCode_CAR");
	 	 			customAssert.SoftAssertEquals(a_selectedTradeCode, tradeCodeValue,"Trade Code on Policy Details Screen is incorrect - Expected [ <b>"+tradeCodeValue+"</b>] and Actual [<b>"+a_selectedTradeCode+"</b>]  .");
	 	 			TestUtil.reportStatus("Trade Code on Policy Details Screen is Correct.", "Pass", true);
	 	 			
	 	 			a_selectedTradeCode_desc = k.getText("CCF_Gray_PD_TradeCode_Desc_CAR");
	 	 			customAssert.SoftAssertEquals(a_selectedTradeCode_desc, v_exp_selectedTradeCode_Desc,"Trade Code Description on Policy Details Screen is incorrect- Expected [ <b>"+v_exp_selectedTradeCode_Desc+"</b>] and Actual [<b>"+a_selectedTradeCode+"</b>]  . ");
	 	 			TestUtil.reportStatus("Trade Code Description on Policy Details Screen is Correct.", "Pass", true);
	 	 			
					break;

				default:
					break;
				}
 	 			
 	 			return true;
 	 		}
 		}catch(Throwable t){
 			return false;
 		}
	}
 	
    public boolean funcDecline(Map<Object, Object> map_data){
		
		 boolean retvalue = true;
		 int counter = 0;
			try {     
				
				
				customAssert.assertTrue(funcButtonSelection("Decline"),"Unable to click on Decline button.");
				customAssert.assertTrue(common.funcPageNavigation("Decline Reason", ""), "Navigation problem to Decline Reason page .");
				// Click on Decline button
				
				map_data.put("Decline_Reason",  k.GetDropDownSelectedValue("Decline_ReasonList"));
				customAssert.assertTrue(k.Input("Decline_AdditionalInfo", "Test Decline"),"Unable to enter additional info on Decline Reason page");
				map_data.put("Decline_AdditionalInfo",  k.getAttribute("Decline_AdditionalInfo","value"));
				if(common.currentRunningFlow.equals("MTA")){
					customAssert.assertTrue(funcButtonSelection("Decline Endorsement"),"Unable to click on Decline Endorsement button on Decline page.");
				}else if(common.currentRunningFlow.equals("NB")){
					customAssert.assertTrue(funcButtonSelection("Decline"),"Unable to click on Decline button on Decline page.");
				}else if(common.currentRunningFlow.equals("Renewal")){
					customAssert.assertTrue(funcButtonSelection("Decline Renewal"),"Unable to click on Decline button on Decline page.");
				}
				
				return retvalue;	
			}catch(Throwable t){
				return false;
			}
			
	}
    //verify decline status -     NTU / Decline Details
    public boolean funcVerifyDeclineNTUstatus(Map<Object, Object> map_data){
    	try{
    		//NTU / Decline Details
    		customAssert.assertTrue(common.funcMenuSelection("Navigate", "NTU / Decline Details"),"Unable to navigate page - NTU / Decline Details");
    		customAssert.assertTrue(common.funcPageNavigation("NTU / Decline Details", ""), "Navigation problem to NTU / Decline Details page .");
    		String DeclineReason = k.getText("DeclineReason");
    		String AdditionalInfo=k.getText("DeclineAdditionalInfo");
    		String RevisitQuote=k.getText("DeclineRevisitQuote");
    		String SendDeclineEmailToBrkr=k.getText("DeclineEmail");
    		if(map_data.get("Decline_Reason").equals(DeclineReason)){
	    		TestUtil.reportStatus("Decline Reason: "+DeclineReason, "Info", false);
	    		TestUtil.reportStatus("Additional Info: "+AdditionalInfo, "Info", false);
	    		TestUtil.reportStatus("Revisit Quote: "+RevisitQuote, "Info", false);
	    		TestUtil.reportStatus("Send Decline Mail to Broker: "+SendDeclineEmailToBrkr, "Info", false);
	    		return true;
    		}else{TestUtil.reportStatus("Decline Reason Mismatched with Expected :"+map_data.get("Decline_Reason")+"|| Actual value is "+DeclineReason, "Fail", true);
    		throw new Exception();
    		}
    	}catch(Throwable t){
    		TestUtil.reportStatus("Failed In Decline Details Page " , "Fail", false);
    		ErrorUtil.addVerificationFailure(t);
    		return false;
    		}
    }
    	   	
    	
    public boolean funcNTU(Map<Object, Object> map_data){
		
		 boolean retvalue = true;
			try {     
				
				customAssert.assertTrue(funcButtonSelection("NTU"),"Unable to click on NTU button.");
				customAssert.assertTrue(common.funcPageNavigation("NTU Reason", ""), "Navigation problem to NTU Reason page .");
				// Click on Decline button
				
				map_data.put("NTU_Reason",  k.GetDropDownSelectedValue("NTU_ReasonList"));
				customAssert.assertTrue(k.Input("NTU_AdditionalInfo","Test NTU"),"Unable to enter additional info on NTU page");
				map_data.put("NTU_AdditionalInfo",  k.getAttribute("NTU_AdditionalInfo","value"));
				if(common.currentRunningFlow.equals("MTA")){
					customAssert.assertTrue(funcButtonSelection("NTU Endorsement"),"Unable to click on NTU Endorsement button on NTU Reason page.");
				}else if(common.currentRunningFlow.equals("NB")){
					customAssert.assertTrue(funcButtonSelection("NTU"),"Unable to click on NTU button on NTU Reason page.");
				}else if(common.currentRunningFlow.equals("Renewal")){
					customAssert.assertTrue(funcButtonSelection("NTU Renewal"),"Unable to click on NTU button on NTU Reason page.");
				} 
				
				
				return retvalue;	
			}catch(Throwable t){
				return false;
			}
			
			}
   //verify decline status -     NTU / Decline Details
   public boolean funcVerifyNTUstatus(Map<Object, Object> map_data){
   	try{
   		//NTU / Decline Details
   		customAssert.assertTrue(common.funcMenuSelection("Navigate", "NTU / Decline Details"),"Unable to navigate page - NTU / Decline Details");
   		customAssert.assertTrue(common.funcPageNavigation("NTU / Decline Details", ""), "Navigation problem to NTU / Decline Details page .");
   		String NTUReason = k.getText("NTUReason");
   		String AdditionalInfo=k.getText("NTUAdditionalInfo");
   		String RevisitQuote=k.getText("NTURevisitQuote");
   		
   		if(map_data.get("NTU_Reason").equals(NTUReason)){
	    		TestUtil.reportStatus("NTU Reason: "+NTUReason, "Info", false);
	    		TestUtil.reportStatus("Additional Info: "+AdditionalInfo, "Info", false);
	    		TestUtil.reportStatus("Revisit Quote: "+RevisitQuote, "Info", false);
	    		
	    		return true;
   		}else{TestUtil.reportStatus("NTU Reason Mis-matched with Expected :"+map_data.get("NTU_Reason")+"|| Actual value is "+NTUReason, "Fail", true);
   		throw new Exception();
   		}
   	}catch(Throwable t){
   		TestUtil.reportStatus("Failed In NTU Details Page " , "Fail", false);
   		ErrorUtil.addVerificationFailure(t);
   		return false;
   		}
   }
    
   public boolean createEndorsement(Map<Object, Object> map_data){
	   try{
		   customAssert.assertTrue(common.funcMenuSelection("Navigate","Premium Summary"),"Issue while Navigating to Premium Summary screen . ");
		   customAssert.assertTrue(funcButtonSelection("Create Endorsement"),"Unable to click on Create Endorsement button.");
			customAssert.assertTrue(common.funcPageNavigation("Create Endorsement", ""), "Navigation problem to Create Endorsement Reason page .");
			customAssert.assertTrue(k.Input("Endorsement_AdditionalInfo","Test Endorsement"),"Unable to enter additional info on Create Endorsement page");
//			map_data.put("NTU_AdditionalInfo",  k.getAttribute("NTU_AdditionalInfo","value"));
			customAssert.assertTrue(funcButtonSelection("Create Endorsement"),"Unable to click on Create Endorsement button on Create Endorsement page.");
			
		   return true;
	   }
	   catch(Throwable t){
	   		TestUtil.reportStatus("Failed In Create Endorsement function. " , "Fail", false);
	   		ErrorUtil.addVerificationFailure(t);
	   		return false;
	   		}
   }
   
   public boolean funcGoOnCover_Endorsement(Map<Object, Object> map_data){
		
		 boolean retvalue = true;
			try {   
				customAssert.assertTrue(common.funcPageNavigation("Premium Summary", ""), "Navigation problem to Premium Summary page .");
				customAssert.assertTrue(funcButtonSelection("Go On Cover"), "Unable to click on Go on cover button .");
				customAssert.assertTrue(k.Click("Endorsement_On_cover_accept"), "Unable to Click Put Endorsement On Cover button on Go On Cover page .");
				return retvalue;

			} catch(Throwable t) {
				String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
				TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
				k.reportErr("Failed in "+methodName+" function", t);
				Assert.fail("Unable to enter details in Go On Cover", t);
				return false;
			}
		}
   
   public boolean funcDiscardMTA(Map<Object, Object> map_data){
		
		 boolean retvalue = true;
			try {     
				
				customAssert.assertTrue(funcButtonSelection("Discard Endorsement"),"Unable to click on Decline button.");
				customAssert.assertTrue(k.AcceptPopup(),"Error while accepting discard pop up");
				
				return retvalue;	
			}catch(Throwable t){
				return false;
			}
			
	}
   public void CancellationPremiumTables( String code, String event, String tableName, boolean re_Calc){
		//Transaction Premium Table
			try{
				k.ImplicitWaitOff();
				Hashtable<String,String> coverlist = new Hashtable<String,String>();
				Hashtable<String,String> cHash = new Hashtable<String,String>();
				String[] prem_col = {"gprm","comr","comm","nprem","gipt","nipt"};
				String testName = (String)common.CAN_excel_data_map.get("Automation Key");
				
				coverlist.put("Material Damage","md7");
				coverlist.put("Business Interruption","bi2");
				coverlist.put("Terrorism","tr2");
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
				coverlist.put("Legal Expenses","lg2");
				coverlist.put("Total","tot");
				
				cHash.put("gprm","GP");
				cHash.put("comr","CR");
				cHash.put("comm","GC");
				cHash.put("nprem","NP");
				cHash.put("gipt","GT");
				cHash.put("nipt","NPIPT");
				cHash.put("com","GC");
				//html/body/div[3]/form/div/table[3]/tbody/tr
				switch (tableName){
				
					case "AP":
						String Trax_table = "html/body/div[3]/form/div/table[3]";
						int rcount = driver.findElements(By.xpath(Trax_table+"/tbody/tr")).size();
						
						
						List<WebElement> col=driver.findElements(By.xpath("html/body/div[3]/form/div/table[3]/tbody/tr[1]/td"));
						//System.out.println(col.size());
						int cCount = col.size();
						Hashtable<String,String> transSmryData = new Hashtable<String,String>();
						String tXpath =Trax_table+"/tbody/tr";
						String CellValue;
						//System.out.println("Transaction Premium Table exist on premium summary page");
						TestUtil.reportStatus("Annual Premium Table exist on premium summary page", "Info", true);
						//System.out.println("Covers Found -:"+ (rcount-1));
						TestUtil.reportStatus("Covers Found -:"+ (rcount-1), "Info", false);
							
							for(int i =1;i<=rcount;i++){
								String sectionXpath = tXpath+"["+i+"]/td[1]";
								String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
								//System.out.print(sec_name+"\t\t\t");
								if(sec_name.contains("Frozen Food")){
									sec_name = "FrozenFoods";
								}else if(sec_name.contains("Licence")){
									sec_name = "LossOfLicence";
								}else if(sec_name.contains("Total")){
									sec_name = "Total";
									sec_name = "Total";
								}else{
									sec_name = sec_name.replaceAll(" ", "");
								}
								
								TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
								
								for(int j = 2; j<=cCount;j++){
									//Annual Premium Data
									String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
									CellValue = driver.findElement(By.xpath(ColumnValues)).getText().replaceAll(",","");;
									if(cHash.get(prem_col[j-2]).equalsIgnoreCase("CR") && sec_name.contains("Total"))
									{}else{
									transSmryData.put("PS_"+sec_name.replaceAll(" ", "")+"_"+cHash.get(prem_col[j-2]), CellValue);}
								}
								PremiumSummarytableCalculation(transSmryData,sec_name);
							
							} 
						
						// Writing Data to Excel Sheet from Map
							Set<String> pKeys=transSmryData.keySet();
						 	for(String pkey:pKeys){
//						 		if(re_Calc){
//						 			customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName,pkey,transSmryData.get(pkey) ,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+pkey) ;
//						 		}else{
						 			compareValues(Double.parseDouble(TestUtil.getStringfromMap(pkey,"NB")),Double.parseDouble(transSmryData.get(pkey)), pkey);
//						 		}
						 	}
						
						
						
						 	 TestUtil.reportStatus("Values have been stored from Annual Premium table", "info", true);
						
						break;
						
					case "CP":
						String CAN_table = "html/body/div[3]/form/div/table[4]";
						int crowcount = driver.findElements(By.xpath(CAN_table+"/tbody/tr")).size();
						int cColCount = driver.findElements(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr[1]/td")).size();
						Hashtable<String,String> CanSmryData = new Hashtable<String,String>();
						String cXpath =CAN_table+"/tbody/tr";
						String cCellValue;
						
						String rPath  = "html/body/div[3]/form/div/table[4]/tbody/tr[";
						String rPath2 = "]/td[";
						String cPath  = "]/input";
						
						// html/body/div[3]/form/div/table[4]/tbody/tr[1]/td[2]/input
						for(int l =1; l<=crowcount;l++){
							String sectionXpath = rPath+l+"]/td[1]";
							String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
							//System.out.print(sec_name+"\t\t\t");
							if(sec_name.contains("Frozen Food")){
								sec_name = "FrozenFoods";
							}else if(sec_name.contains("Licence")){
								sec_name = "LossOfLicence";
							}else if(sec_name.contains("Total")){
								sec_name = "Total";
								sec_name = "Total";
							}else{
								sec_name = sec_name.replaceAll(" ", "");
							}
							
							for(int m =2;m<=cColCount;m++){
								String cXPath =rPath+l+"]/td["+m+"]";
								String cellVariable = "PS_"+sec_name+"_"+cHash.get(prem_col[m-2]);
								if(cHash.get(prem_col[m-2]).equals("GP") && m==2 && !sec_name.contains("Total")){
									String gpXPath =rPath+l+"]/td["+m+"]/input";
									String gPrem =null;
									if(re_Calc){
										String cGP;
										gPrem = driver.findElement(By.xpath(gpXPath)).getAttribute("value").replaceAll(",", "");
										String changeGP = (String)common.CAN_excel_data_map.get("CAN_"+sec_name+"_"+cHash.get(prem_col[m-2]));
										if(Integer.parseInt(changeGP)!=0){
											double New_GP = Double.parseDouble(gPrem) - Double.parseDouble(changeGP);
											cGP = f.format(New_GP);
											 k.scrollInViewByXpath(gpXPath);
											driver.findElement(By.xpath(gpXPath)).clear();
											driver.findElement(By.xpath(gpXPath)).sendKeys(cGP);
											driver.findElement(By.xpath(gpXPath)).sendKeys(Keys.TAB);
											k.Click("cancelRecalc");
											gPrem = driver.findElement(By.xpath(gpXPath)).getAttribute("value").replaceAll(",", "");
											
											TestUtil.reportStatus("Section :"+sec_name+" change value for Gross Premium is :<b>"+changeGP, "Info", false);
											TestUtil.reportStatus("Section :"+sec_name+" updated Gross Premium is :<b>"+New_GP, "Info", false);
										}else{
											 k.scrollInViewByXpath(gpXPath);
											gPrem = driver.findElement(By.xpath(gpXPath)).getAttribute("value").replaceAll(",", "");
										}
										
									}else {
										 k.scrollInViewByXpath(gpXPath);
										gPrem = driver.findElement(By.xpath(gpXPath)).getAttribute("value").replaceAll(",", "");
									}
									
									CanSmryData.put(cellVariable, gPrem);
									continue;
								}
								 k.scrollInViewByXpath(cXPath);
								String cellvalue = driver.findElement(By.xpath(cXPath)).getText().replaceAll(",", "");
								CanSmryData.put(cellVariable, cellvalue);
							}
							PremiumSummarytableCalculation(CanSmryData,sec_name);
												
						}
						// Writing Data to Excel Sheet from Map
						Set<String> cKeys=CanSmryData.keySet();
					 	for(String pkey:cKeys){
//					 		if(re_Calc){
					 			//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName,pkey,CanSmryData.get(pkey) ,common.CAN_excel_data_map),"Error while writing Premium Summary data to excel"+pkey) ;
					 		if(pkey.equalsIgnoreCase("PS_Total_CR")==false){
					 			common.CAN_excel_data_map.put(pkey, CanSmryData.get(pkey));}
//					 		}else{
					 			//compareValues(Double.parseDouble(TestUtil.getStringfromMap(pkey,"NB")),Double.parseDouble(CanSmryData.get(pkey)), pkey);
//					 		}
					 	}
						
						 TestUtil.reportStatus("Values have been Verified from <b> Cancellation Premium table", "info", true);					
						break;
						
					default:
						break;
						
				}
				
			} 
			catch(Throwable t ){
				k.ImplicitWaitOn();
				ErrorUtil.addVerificationFailure(t);
				 
			}
			
			//System.out.println("Gross Premium for Material Damage Cover in Transaction Premium table is :"+transSmryData.get("MaterialDamage_gprem"));
			
	}
  
   
   /**
    * 
    * -----------------------------------Renewal flow Functions : Starting------------------------- 
    * 
    */
   
   
   public boolean funcSearchPolicy_Renewal(Map<Object, Object> map_data)
	{
		boolean ret_value = true;
		//String cl_name = (String)map_data.get("NB_ClientName");
		try{
			
			customAssert.assertTrue(k.getText("searchPoliciesPage_Header").equalsIgnoreCase("Search Policies"), "Search policies Page is not loaded . ");
			customAssert.assertTrue(k.Click("comm_clear"),"Unable to Clear Search Policies Filter Data .");
			customAssert.assertTrue(k.Input("Policy_Number_txtBox", (String)map_data.get("Renewal_PolicyNumber")), "Unable to enter policy number.");
			customAssert.assertTrue(k.Click("comm_search"), "Unable to click on search button.");
			TestUtil.reportStatus("Policy: "+(String)map_data.get("Renewal_PolicyNumber")+" successfully searched . ", "Info", true);
			TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Renewal",(String)common.Renewal_excel_data_map.get("Automation Key"), "Renewal_ClientName", k.getText("Policy_Client_Name"), common.Renewal_excel_data_map);
			if(!TestBase.product.equals("SPI"))
				TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Policy Details",(String)common.Renewal_excel_data_map.get("Automation Key"), "PD_ProposerName", k.getText("Policy_Client_Name"), common.Renewal_excel_data_map);
			else
				TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "PolicyGeneral",(String)common.Renewal_excel_data_map.get("Automation Key"), "PG_InsuredName", k.getText("Policy_Client_Name"), common.Renewal_excel_data_map);
			customAssert.assertTrue(k.Click("Policy_Client_Name"), "Unable to click on client name . ");
		
		}catch(Throwable t){
		   String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
           TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     
           k.reportErr("Failed in "+methodName+" function", t);
           ret_value = false;;
		}
		
		return ret_value;
	}
   
   
   public boolean funcVerifyPolicyStatus_Renewal(Map<Object, Object> map_data,String code,String event,String exp_Policy_Status)
	{
		boolean ret_value = true;
		String policy_status_actual=null;
		String testName = (String)map_data.get("Automation Key");
		try{
			
			policy_status_actual = k.getText("Policy_status_header");
			customAssert.assertEquals(policy_status_actual, exp_Policy_Status,"Mismatch in Policy Status - Expected: <b>"+exp_Policy_Status+"</b> Actual: <b>"+policy_status_actual+"</b> on Premium Summary Page . ");
			customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Renewal", testName, "Renewal_CurrentPolicyStatus", exp_Policy_Status,common.Renewal_excel_data_map),"Error while writing data to excel for field >Renewal_CurrentPolicyStatus<");
			
			TestUtil.reportStatus("Verified Policy Status as <b>"+exp_Policy_Status+"</b> successfully . ", "Info", true);
		}catch(Throwable t){
			ret_value = false;
		}
		
		return ret_value;
	}
   
   
   public boolean funcUpdateCoverDetails(Map<Object, Object> map_data){
	   
	try {
			customAssert.assertTrue(common.funcMenuSelection("Navigate", "Covers"),"Cover page is having issue(S)");
			customAssert.assertTrue(common.funcPageNavigation("Covers", ""),"Cover page is having issue(S)");
			String coverName = null;
			String c_locator = null;
			k.pressDownKeyonPage();
			String all_cover = ObjectMap.properties.getProperty(CommonFunction_GUS.product+"_CD_AllCovers");
			String[] split_all_covers = all_cover.split(",");
			for(String coverWithLocator : split_all_covers){
				String coverWithoutLocator = coverWithLocator.split("_")[0];
				try{
					//CoversDetails_data_list.add(coverWithoutLocator);
					coverName = coverWithLocator.split("_")[0];	
					c_locator = coverWithLocator.split("_")[1];
					k.waitTwoSeconds();
					if(c_locator.equals("md")){
						
						/*if ( (!driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).isSelected()) && ((String)common.Renewal_excel_data_map.get("CD_"+coverName)).equalsIgnoreCase("No") ){
							continue;
						}else{
							TestUtil.WriteDataToXl(CommonFunction.product+"_"+CommonFunction.businessEvent, "CoverDetails",(String)map_data.get("Automation Key"), "CD_"+coverName, "No", common.Renewal_excel_data_map);
							continue;
						}
						
						if ( (driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).isSelected()) && ((String)common.Renewal_excel_data_map.get("CD_"+coverName)).equalsIgnoreCase("Yes") ){
							continue;
						}
						else{
							TestUtil.WriteDataToXl(CommonFunction.product+"_"+CommonFunction.businessEvent, "CoverDetails",(String)map_data.get("Automation Key"), "CD_"+coverName, "No", common.Renewal_excel_data_map);
						}*/
						
						
						if (!driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).isSelected()){
							if(((String) common.Renewal_excel_data_map.get("CD_"+coverName)).equalsIgnoreCase("No"))
								continue;
							else
								TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "CoverDetails",(String)map_data.get("Automation Key"), "CD_"+coverName, "No", common.Renewal_excel_data_map);
						}else{
							if(((String) common.Renewal_excel_data_map.get("CD_"+coverName)).equalsIgnoreCase("Yes"))
								continue;
							else
								TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "CoverDetails",(String)map_data.get("Automation Key"), "CD_"+coverName, "Yes", common.Renewal_excel_data_map);
						}
						
						
						
						
					
					}else if(c_locator.equals("PEL")){
						
					}else{
						if (!driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")).isSelected()){
							JavascriptExecutor j_exe = (JavascriptExecutor) driver;
							j_exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[contains(@name,'"+c_locator+"_selected')]")));
							
								if(((String) common.Renewal_excel_data_map.get("CD_"+coverName)).equalsIgnoreCase("No"))
									continue;
								else
									TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "CoverDetails",(String)map_data.get("Automation Key"), "CD_"+coverName, "No", common.Renewal_excel_data_map);
							
							}else{
								if(((String) common.Renewal_excel_data_map.get("CD_"+coverName)).equalsIgnoreCase("Yes"))
									continue;
								else
									TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "CoverDetails",(String)map_data.get("Automation Key"), "CD_"+coverName, "Yes", common.Renewal_excel_data_map);
							}
					
					}	
					
				}catch(Throwable tt){
					System.out.println("Error while Updating Cover data for renewal - "+coverWithoutLocator);
					break;
				}
	 		}
 	 
	 	  return true;
		} catch (Exception e) {
			return false;
		}
	   
   }
   
   
   @SuppressWarnings("static-access")
public boolean funcGetPremiums(int rcount, Hashtable<String,String> coverlist,Hashtable<String,String> cHash,String code, String event,boolean xlWrite){
	   
	   
	   try{
		   
		   customAssert.assertTrue(common.funcPageNavigation("Premium Summary", ""),"Premium Summary page is having issue(S)");
		   String flag = "false";
		   k.ImplicitWaitOff();
		   if(k.isDisplayed("CCF_AdjustmentInfo")){
			   flag = "true";
		   }
		   String sectionName = "";
		   
		   String quoteNumber = k.getText("Quote_Number");
		   TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Renewal",(String)common.Renewal_excel_data_map.get("Automation Key"), "Renewal_QuoteNumber", quoteNumber, common.Renewal_excel_data_map);
		   String startDate = k.getAttribute("Policy_Start_Date", "value");
		   String endDate = k.getAttribute("Policy_End_Date", "value");
		   TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_PolicyStartDate", startDate, common.Renewal_excel_data_map);
		   TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "QuoteCreation",(String)common.Renewal_excel_data_map.get("Automation Key"), "QC_InceptionDate", startDate, common.Renewal_excel_data_map);
		   TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_PolicyEndDate", endDate , common.Renewal_excel_data_map);
		   
		   
		   //boolean AdjustmentInfo = k.isDisplayed("CCF_AdjustmentInfo");
		   
		   //To write data from application to Excel for Renewal flow.
		   customAssert.assertTrue(PremiumSummaryDataTraverse_Renewal(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation");

		   switch (flag) {
			   case "true":
				  common.funcButtonSelection("Insurance Tax");
				  customAssert.assertTrue(funcPageNavigation("Tax Adjustment", ""),"Unable to land on Tax adjustment screen.");
				  common.totalGrossPremium = 0.0;
				  common.totalGrossTax = 0.0;
				  common.totalNetPremiumTax = 0.0;
				  taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
				  List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
					for(int j=0;j<list3.size()-1;j++){
						
						//taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							//String actGP =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText();
							//String actGT =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText();
							String actIPT =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[4]")).getText();
							
							//TestUtil.WriteDataToXl(CommonFunction.product+"_"+CommonFunction.businessEvent, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_"+sectionName.replaceAll(" ", "")+"_GP", actGP, common.Renewal_excel_data_map);
							//TestUtil.WriteDataToXl(CommonFunction.product+"_"+CommonFunction.businessEvent, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_"+sectionName.replaceAll(" ", "")+"_GT", actGT, common.Renewal_excel_data_map);
							TestUtil.WriteDataToXl(CommonFunction_GUS.product+"_"+CommonFunction_GUS.businessEvent, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_"+sectionName.replaceAll(" ", "")+"_IPT", actIPT, common.Renewal_excel_data_map);
							
							//common.totalGrossPremium = common.totalGrossPremium + Double.parseDouble(actGP);
							//common.totalGrossTax = common.totalGrossTax + Double.parseDouble(actGT);
							//common.totalNetPremiumTax = common.totalNetPremiumTax + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						//list3 = taxTable_tBody.findElements(By.tagName("tr"));
						//countOfCovers = list3.size();
					}
				
				//TestUtil.reportStatus("<b> Tax adjustment operatios is completed. </b>", "Info", false);
				//TestUtil.WriteDataToXl(CommonFunction.product+"_"+CommonFunction.businessEvent, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_Total_GP", common.roundedOff(Double.toString(totalGrossPremium)), common.Renewal_excel_data_map);
				//TestUtil.WriteDataToXl(CommonFunction.product+"_"+CommonFunction.businessEvent, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_Total_GT", common.roundedOff(Double.toString(totalGrossTax)), common.Renewal_excel_data_map);
				//TestUtil.WriteDataToXl(CommonFunction.product+"_"+CommonFunction.businessEvent, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_Total_NPIPT", common.roundedOff(Double.toString(totalNetPremiumTax)), common.Renewal_excel_data_map);
				common.funcPageNavigation("", "Save");
				k.Click("Tax_adj_BackBtn");
				break;
		   }
		   
		   return true;
		   
	   }catch(Exception e){
		   return false;
	   }
   }
   
   
   public boolean decideRENEWALFlow(Map<Object ,Object> map_data){
		 
		
		try {
			
			switch (CommonFunction_GUS.product) {
			case "CCF":
				
				switch ((String)common.Renewal_excel_data_map.get("Renewal_Status")) {
				case "Endorsment On Cover":
					customAssert.assertTrue(common_CCF.funcEndorsmentOnCoverOperation(map_data));
					break;
				case "Renewal On Cover":
					customAssert.assertTrue(common_CCF.funcRenewalOnCoveOperation(map_data));
					break;
				case "Renewal NTU":
					customAssert.assertTrue(common_CCF.funcRenewalNTUOperation(map_data));
					break;
				case "Renewal Declined":
					customAssert.assertTrue(common_CCF.funcRenewalDeclinedOperation(map_data));
					break;
				case "Renewal Rewind":
					customAssert.assertTrue(common_CCF.funcRenewalRewindOperation(map_data));
					break;
				
				}
				
				break;
			case "SPI":
				
				switch ((String)common.Renewal_excel_data_map.get("Renewal_Status")) {
				case "Endorsment On Cover":
					customAssert.assertTrue(common_SPI.funcEndorsmentOnCoverOperation(map_data));
					break;
				case "Renewal On Cover":
					customAssert.assertTrue(common_CCF.funcRenewalOnCoveOperation(map_data));
					break;
				case "Renewal NTU":
					customAssert.assertTrue(common_CCF.funcRenewalNTUOperation(map_data));
					break;
				case "Renewal Declined":
					customAssert.assertTrue(common_CCF.funcRenewalDeclinedOperation(map_data));
					break;
				case "Renewal Rewind":
					customAssert.assertTrue(common_SPI.funcRenewalRewindOnCover(map_data));
					break;
				
				}
				
				break;

			}
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
		
	}
   
   
   public boolean funcAssignUnderWriter(String loginUser){
	   Boolean retvalue = false;
         try {               
        	    Assert.assertTrue(common.funcPageNavigation("Assign Underwriter",""), "Assign Underwriter Page does not open.");
                String tablexpath ="html/body/div[3]/form/div/table";
                WebElement table= driver.findElement(By.xpath(tablexpath));
        		//Get number of rows in table 
        		int numOfRow = table.findElements(By.tagName("tr")).size();
        	
        		//divided Xpath In three parts to pass Row_count and Col_count values.
        		String first_part = tablexpath+"/tbody/tr[";
        		String second_part = "]/td[";
        		String third_part = "]";
        		
        		for(int i=1;i<=numOfRow;i++){
        			
 				 String sName_path = first_part+i+second_part+"1"+third_part;	        				 
 			     String sName = driver.findElement(By.xpath(sName_path)).getText();	        			  
        			 if(loginUser.equalsIgnoreCase(sName)){
        				 retvalue=true;
        				 String sAssign_path = first_part+i+second_part+"4"+third_part+"/a";
        				 k.scrollInViewByXpath(sAssign_path);
        				 driver.findElement(By.xpath(sAssign_path)).click();	        				
        				 return retvalue;
        			 }
        		}
        		
        		if(retvalue = false){
        			TestUtil.reportStatus("Underwriter "+loginUser+" is not present", "Fail", true);
					return retvalue;
        		}
                return true;
         }catch(Throwable t) {
        	 return false;
         }
	}
   
   
   public boolean funcButtonVerification(String btnName){
       boolean retValue = true;
       
       	if (!(btnName=="")){
             if(btnName.equalsIgnoreCase("Save")){
                     TestUtil.reportStatus("Save button is present on page.","info",false);
                     k.isDisplayed("saveBtn");
              }else if(btnName.equalsIgnoreCase("Next")){
                     TestUtil.reportStatus("Next button is present on page.","info",false);
                     k.isDisplayed("nextBtn");
              }else if(btnName.equalsIgnoreCase("Back")){
          	 	TestUtil.reportStatus("Back button is present on page.","info",false);
          	 	k.isDisplayed("backBtn");
           }else if(btnName.equalsIgnoreCase("Quote")){
          	 	TestUtil.reportStatus("Quote button is present on page.","info",false);
          	 	k.isDisplayed("quoteBtn");
           }else if(btnName.equalsIgnoreCase("Assign Underwriter")){
                     TestUtil.reportStatus("Assign Underwriter button is present on page.", "info", false);
                     k.isDisplayed("assignUWBtn");
                     }
              else if(btnName.equalsIgnoreCase("Create Endorsement")){
                  TestUtil.reportStatus("Create Endorsement button is present on page.", "info", false);
                  k.isDisplayed("createEndor");
                  }
              else if(btnName.equalsIgnoreCase("Send to Underwriter")){
                  TestUtil.reportStatus("Send to Underwriter button is present on page.", "info", false);
                  k.isDisplayed("sendUWbtn");
              }
              else if(btnName.equalsIgnoreCase("Indication Accept")){
                  TestUtil.reportStatus("Indication Accept button is present on page.", "info", false);
                  k.isDisplayed("indicateAcceptBtn");
              }
              else if(btnName.equalsIgnoreCase("NTU")){
                  TestUtil.reportStatus("NTU button is present on page.", "info", false);
                  k.isDisplayed("ntuBtn");
              }else if(btnName.equalsIgnoreCase("Refer Details")){
                  TestUtil.reportStatus("Refer Details button is present on page.", "info", false);
                  k.isDisplayed("refer_detailsbtn");
              }else if(btnName.equalsIgnoreCase("Rewind")){
                  TestUtil.reportStatus("Rewind Button is present on page", "info", false);
                  k.isDisplayed("rewind_btn");
              }else if(btnName.equalsIgnoreCase("Cancel Policy")){
                  TestUtil.reportStatus("Cancel Policy Button is present on page", "info", false);
                  k.isDisplayed("cancelPolicy");
              }else if(btnName.equalsIgnoreCase("Documents")){
                  TestUtil.reportStatus("Documents Button is present on page", "info", false);
                  k.isDisplayed("docs_btn");
              } else if(btnName.equalsIgnoreCase("Excesses")){
                  TestUtil.reportStatus("Excesses Button is present on page", "info", false);
                  k.isDisplayed("excesses_btn");
              }else {
                     TestUtil.reportStatus("Unable to parse Button <b>"+btnName+"</b> name on page " ,"fail",true);
                     return false;
              }
       }
       return retValue;
     }
   
   
   public static boolean PremiumSummaryDataTraverse_Renewal(int rcount, Hashtable<String,String> coverlist,Hashtable<String,String> cHash,String code, String event,boolean xlWrite){
		
		try{
			common.GrosspremSmryData.clear();
			String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
			String sec_name_withoutSpace = null;
			String CellValue;
			String testName = (String)common.Renewal_excel_data_map.get("Automation Key");
			Hashtable<String,String> premSmryData = new Hashtable<String,String>();
			
			TestUtil.reportStatus("Covers Found -:"+ (rcount-1), "info", true);
			String tXpath = "//*[@class='matrix']/tbody/tr";
			String Trax_table = "//p[text()=' Transaction Premium']//following-sibling::table[@id='table0']";
			String Trax_details_table = "//p[text()=' Transaction Details ']//following-sibling::table[@id='table0']";
			List<WebElement> ls = driver.findElements(By.xpath(Trax_table));
			List<WebElement> ls1 = driver.findElements(By.xpath(Trax_details_table));
			String policy_status_actual = "";
			 for(int i =1;i<=rcount;i++){
				 String sectionXpath = tXpath+"["+i+"]/td[1]";
				 String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
				
				if(sec_name.contains("Frozen Food")){
					sec_name_withoutSpace = "FrozenFoods";
				}else if(sec_name.contains("Licence")){
					sec_name_withoutSpace = "LossOfLicence";
				}else if(sec_name.contains("Total")){
					sec_name = "Total";
					sec_name_withoutSpace = "Total";
				}else{
					sec_name_withoutSpace = sec_name.replaceAll(" ", "");
				}
				
				policy_status_actual = k.getText("Policy_status_header");
				if(policy_status_actual.contains("Endorsement Submitted")){
						if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
							//TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",""), common.MTA_excel_data_map);
							
							//System.out.print(sec_name+"\t\t\t");
							TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
							for(int j=0;j<prem_col.length;j++){
								String xpathVal;
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
								 String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
								
								if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
									xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.Renewal_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}
								if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
									System.out.print(" ___ \t ");
								}else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
									if(xlWrite){
										k.scrollInViewByXpath(xpathVal);
										//driver.findElement(By.xpath(xpathVal)).click();
										//driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",""));
										//driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
									}
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
									}else if(ls.size()==0) {
										compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR","")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									}
									keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
									xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
									CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
									if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
									}	
								}else{
								    	CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
								    	if(xlWrite){
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
										//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								    	}else if(ls.size()==0){
								    		
										compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,"")),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
										premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
								    	}
								    keyName=null;
								    }
							}
								//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
							if(ls.size()==0){
								PremiumSummarytableCalculation_Renewal(premSmryData,sec_name_withoutSpace);
							}
					
				}else if(policy_status_actual.contains("Renewal Submitted")){
					if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
						if(policy_status_actual.contains("Renewal Submitted")){
							//TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",CommonFunction.businessEvent), common.Renewal_excel_data_map);
						}else{
							TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",CommonFunction_GUS.businessEvent), common.Renewal_excel_data_map);
						}
						
						//System.out.print(sec_name+"\t\t\t");
						//TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
						for(int j=0;j<prem_col.length;j++){
							String xpathVal;
							xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
							String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
							
							if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
								CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
								customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.Renewal_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
							}
							if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
								System.out.print(" ___ \t ");
							}else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
								if(xlWrite){
									k.scrollInViewByXpath(xpathVal);
									driver.findElement(By.xpath(xpathVal)).click();
									driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent));
									driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
								}
								CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
								if(xlWrite){
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}else if(ls.size()==0) {
									compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
								}
								keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
								CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
								if(xlWrite){
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
									common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
									//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}	
							}else{
							    	CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
							    	if(xlWrite){
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
							    	}else if(ls.size()==0){
							    		
									compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
							    	}
							    keyName=null;
							    }
						}
							//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
						if(policy_status_actual.contains("Rewind")){
							if(ls.size()==0){
								PremiumSummarytableCalculation_Renewal(premSmryData,sec_name_withoutSpace);}
						}else if(policy_status_actual.contains("Renewal Submitted")){
							if(ls.size()==0){
								PremiumSummarytableCalculation_Renewal(premSmryData,sec_name_withoutSpace);}
						}
						
				}else{
					if(sec_name_withoutSpace.contains("Total")==false && xlWrite==true )
						if(policy_status_actual.contains("Rewind")){
							//TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",CommonFunction.businessEvent), common.Renewal_excel_data_map);
						}else{
							TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)common.Renewal_excel_data_map.get("Automation Key"), "PS_"+sec_name_withoutSpace+"_IPT", TestUtil.getStringfromMap("PS_IPTRate",CommonFunction_GUS.businessEvent), common.Renewal_excel_data_map);
						}
						
						//System.out.print(sec_name+"\t\t\t");
						//TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
						for(int j=0;j<prem_col.length;j++){
							String xpathVal;
							xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j]+"']";
							String keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]);
							
							if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comm")){
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_com']";
								CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
								customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.Renewal_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
							}
							if(coverlist.get(sec_name).equals("tot") && prem_col[j].equals("comr")){
								System.out.print(" ___ \t ");
							}else if(prem_col[j].equals("comr") && (coverlist.get(sec_name).equals("tot"))==false){
								if(xlWrite){
									k.scrollInViewByXpath(xpathVal);
									driver.findElement(By.xpath(xpathVal)).click();
									driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.chord(Keys.CONTROL, "a"),TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent));
									driver.findElement(By.xpath(xpathVal)).sendKeys(Keys.TAB);
								}
								CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
								if(xlWrite){
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}else if(ls.size()==0) {
									compareValues(Double.parseDouble(TestUtil.getStringfromMap("PS_"+sec_name_withoutSpace+"_CR",CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
								}
								keyName = "PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]);
								xpathVal = "//*[@id='"+coverlist.get(sec_name)+"_"+prem_col[j-1]+"']";
								CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");;
								if(xlWrite){
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
									common.GrosspremSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j-1]), CellValue);
									//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
								}	
							}else{
							    	CellValue = driver.findElement(By.xpath(xpathVal)).getAttribute("value").replaceAll(",","");
							    	if(xlWrite){
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
									//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, keyName, CellValue,common.NB_excel_data_map),"Error while writing Premium Summary data to excel ."+keyName);
							    	}else if(ls.size()==0){
							    		
									compareValues(Double.parseDouble(TestUtil.getStringfromMap(keyName,CommonFunction_GUS.businessEvent)),Double.parseDouble(CellValue),cHash.get(prem_col[j]));
									premSmryData.put("PS_"+sec_name_withoutSpace+"_"+cHash.get(prem_col[j]), CellValue);
							    	}
							    keyName=null;
							    }
						}
							//customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,common.NB_excel_data_map),"Error while writing Policy Duration data to excel .");
						if(policy_status_actual.contains("Rewind")){
							if(ls.size()==0){
								PremiumSummarytableCalculation_Renewal(premSmryData,sec_name_withoutSpace);}
						}
						
				}
			}
				
				
			 customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Premium Summary .");
			 
			 
			 if(ls.size()>0){ 
				 TransactionPremiumTable_Renewal(rcount,coverlist,cHash,code,event,xlWrite);
			 }
			 else if(ls1.size()>0){
				 TransactionDetailsTable_Renewal(rcount,coverlist,cHash,code,event,xlWrite);
			 }
			 else if(xlWrite){	 
				 Set<String> pKeys=premSmryData.keySet();
				 	for(String pkey:pKeys){
				 		customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName,pkey,premSmryData.get(pkey) ,common.Renewal_excel_data_map),"Error while writing Premium Summary data to excel ."+pkey) ;
				 	}
			 }
			
			 return true;
		}catch(Throwable t){
				k.ImplicitWaitOn();
				return false;
		}
	  
	 }
   public static void TransactionPremiumTable_Renewal(int rcount, Hashtable<String,String> coverlist,Hashtable<String,String> cHash,String code, String event, boolean xlWrite){
		//Transaction Premium Table
			try{
				k.ImplicitWaitOff();
				String Trax_table = "html/body/div[3]/form/div/table[4]";
				String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
				String testName = (String)common.Renewal_excel_data_map.get("Automation Key");
				List<WebElement> col=driver.findElements(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr[1]/td"));
				//System.out.println(col.size());
				int cCount = col.size();
				if(driver.findElement(By.xpath(Trax_table)).isDisplayed()){
					
					Hashtable<String,String> transSmryData = new Hashtable<String,String>();
					String tXpath =Trax_table+"/tbody/tr";
					String CellValue;
					//System.out.println("Transaction Premium Table exist on premium summary page");
					TestUtil.reportStatus("Transaction Premium Table exist on premium summary page", "Info", true);
					//System.out.println("Covers Found -:"+ (rcount-1));
					TestUtil.reportStatus("Covers Found -:"+ (rcount-1), "Info", false);
					
					for(int i =1;i<=rcount;i++){
						String sectionXpath = tXpath+"["+i+"]/td[1]";
						String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
						//System.out.print(sec_name+"\t\t\t");
						if(sec_name.contains("Frozen Food")){
							sec_name = "FrozenFoods";
						}else if(sec_name.contains("Licence")){
							sec_name = "LossOfLicence";
						}else if(sec_name.contains("Total")){
							sec_name = "Total";
							sec_name = "Total";
						}else{
							sec_name = sec_name.replaceAll(" ", "");
						}
						
						TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
						
						for(int j = 2; j<=cCount;j++){
							//transSmryData
							String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
							CellValue = driver.findElement(By.xpath(ColumnValues)).getText().replaceAll(",","");;
							//System.out.print(CellValue +  "\t");	
							//TestUtil.reportStatus(CellValue +  "\t", "Info", false);
							if(cHash.get(prem_col[j-2]).equalsIgnoreCase("CR") && sec_name.contains("Total"))
							{}else{
							transSmryData.put("PS_"+sec_name.replaceAll(" ", "")+"_"+cHash.get(prem_col[j-2]), CellValue);}
						}
						PremiumSummarytableCalculation(transSmryData,sec_name);
						//System.out.println("\n___");
					}
				
				// Writing Data to Excel Sheet from Map
					Set<String> pKeys=transSmryData.keySet();
				 	for(String pkey:pKeys){
				 		if(xlWrite){
				 			customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName,pkey,transSmryData.get(pkey) ,common.Renewal_excel_data_map),"Error while writing Premium Summary data to excel ."+pkey) ;
				 		}else{
				 			compareValues(Double.parseDouble(transSmryData.get(pkey)), Double.parseDouble(TestUtil.getStringfromMap(transSmryData.get(pkey),CommonFunction_GUS.businessEvent)), pkey);
				 		}
				 	}
				}
			}
			catch(Throwable t ){
				k.ImplicitWaitOn();
				System.out.println("--"); 
			}
			
			//System.out.println("Gross Premium for Material Damage Cover in Transaction Premium table is :"+transSmryData.get("MaterialDamage_gprem"));
			 TestUtil.reportStatus("Values have been stored from Transaction Premium table", "info", true);
	}
  
  public double calculateGeneralTS_Renewal(String recipient,String account,int j, int td, String event,String code,String fileName){
		try{
				String terrorPremium = (String)common.Renewal_excel_data_map.get("PS_Terrorism_GP");
				String terrorIPT = (String)common.Renewal_excel_data_map.get("PS_Terrorism_GT");
				String cyberPremium = (String)common.Renewal_excel_data_map.get("PS_CyberandDataSecurity_GP");
				String cyberIPT = (String)common.Renewal_excel_data_map.get("PS_CyberandDataSecurity_GT");
				String LegalExpensePremium = (String)common.Renewal_excel_data_map.get("PS_LegalExpenses_GP");
				String LegalExpenseIPT = (String)common.Renewal_excel_data_map.get("PS_LegalExpenses_GT");
				String DirectorsGrossPremium = (String)common.Renewal_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String directorsIPT = (String)common.Renewal_excel_data_map.get("PS_DirectorsandOfficers_GT");
				String MarineCargoGrossPremium = (String)common.Renewal_excel_data_map.get("PS_MarineCargo_GP");
				String MarineCargoIPT = (String)common.Renewal_excel_data_map.get("PS_MarineCargo_GT");
				String EmpLbtGrossPremium = (String)common.Renewal_excel_data_map.get("PS_EmployersLiability_GP");
				String EmplbtCargoIPT = (String)common.Renewal_excel_data_map.get("PS_EmployersLiability_GT");
				String ProductLbtGrossPremium = (String)common.Renewal_excel_data_map.get("PS_ProductsLiability_GP");
				String ProductLbtIPT = (String)common.Renewal_excel_data_map.get("PS_ProductsLiability_GT");
				String PublicLbtGrossPremium = (String)common.Renewal_excel_data_map.get("PS_PublicLiability_GP");
				String PubliclbtIPT = (String)common.Renewal_excel_data_map.get("PS_PublicLiability_GT");
				String POLPremium = (String)common.Renewal_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String POLIPT = (String)common.Renewal_excel_data_map.get("PS_PropertyOwnersLiability_GT");
				String product = common.product;
				
				String totalGrossPremium = (String)common.Renewal_excel_data_map.get("PS_Total_GP");
				String totalGrossIPT = (String)common.Renewal_excel_data_map.get("PS_Total_GT");
				String part1= "//*[@id='table0']/tbody";
				
				String general= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String generalInsTax= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String generalDue= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				
				
				if(terrorPremium == null) {terrorPremium = "0.00";}
				if(terrorIPT == null) {terrorIPT = "0.00";}
				if(cyberPremium == null) {cyberPremium = "0.00";}
				if(cyberIPT == null) {cyberIPT = "0.00";}
				if(LegalExpensePremium == null) {LegalExpensePremium = "0.00";}
				if(LegalExpenseIPT == null) {LegalExpenseIPT = "0.00";}
				if(DirectorsGrossPremium == null) {DirectorsGrossPremium = "0.00";}
				if(directorsIPT == null) {directorsIPT = "0.00";}
				if(MarineCargoGrossPremium == null) {MarineCargoGrossPremium = "0.00";}
				if(MarineCargoIPT == null) {MarineCargoIPT = "0.00";}
				if(EmpLbtGrossPremium == null) {EmpLbtGrossPremium = "0.00";}
				if(EmplbtCargoIPT == null) {EmplbtCargoIPT = "0.00";}
				if(ProductLbtGrossPremium == null) {ProductLbtGrossPremium = "0.00";}
				if(ProductLbtIPT == null) {ProductLbtIPT = "0.00";}
				if(PublicLbtGrossPremium == null) {PublicLbtGrossPremium = "0.00";}
				if(PubliclbtIPT == null) {PubliclbtIPT = "0.00";}
				if(POLPremium == null) {POLPremium = "0.00";}
				if(POLIPT == null) {POLIPT = "0.00";}
							
				
				if(((String)common.Renewal_excel_data_map.get("CD_Terrorism")).equals("No") ||(String)common.Renewal_excel_data_map.get("CD_Terrorism")== null )
				{
					terrorPremium="0.00";
					terrorIPT="0.00";
				}
				if((String)common.Renewal_excel_data_map.get("CD_CyberandDataSecurity")== null || ((String)common.Renewal_excel_data_map.get("CD_CyberandDataSecurity")).equals("No"))
				{
					cyberPremium="0.00";
					cyberIPT="0.00";
				}
				if((String)common.Renewal_excel_data_map.get("CD_LegalExpenses")== null ||((String)common.Renewal_excel_data_map.get("CD_LegalExpenses")).equals("No"))
				{
					LegalExpensePremium="0.00";
					LegalExpenseIPT="0.00";
				}
				if((String)common.Renewal_excel_data_map.get("CD_DirectorsandOfficers")== null ||((String)common.Renewal_excel_data_map.get("CD_DirectorsandOfficers")).equals("No"))
				{
					DirectorsGrossPremium="0.00";
					directorsIPT="0.00";
				}
				if((String)common.Renewal_excel_data_map.get("CD_MarineCargo")== null ||((String)common.Renewal_excel_data_map.get("CD_MarineCargo")).equals("No"))
				{
					MarineCargoGrossPremium="0.00";
					MarineCargoIPT="0.00";
				}
				if((String)common.Renewal_excel_data_map.get("CD_Liability")== null || ((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("No"))
				{
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}
				if(product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("CTA")){
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}
				
				double generalPremium = Double.parseDouble(totalGrossPremium) - (Double.parseDouble(terrorPremium) 
																				+ Double.parseDouble(cyberPremium) 
																				+ Double.parseDouble(LegalExpensePremium)
																				+ Double.parseDouble(DirectorsGrossPremium)
																				+ Double.parseDouble(MarineCargoGrossPremium)
																				+ Double.parseDouble(EmpLbtGrossPremium)
																				+ Double.parseDouble(ProductLbtGrossPremium)
																				+ Double.parseDouble(PublicLbtGrossPremium)
																				+ Double.parseDouble(POLPremium));		
				double generalIPT = Double.parseDouble(totalGrossIPT) - (Double.parseDouble(terrorIPT) 
																		+ Double.parseDouble(cyberIPT) 
																		+ Double.parseDouble(LegalExpenseIPT)
																		+ Double.parseDouble(directorsIPT)
																		+ Double.parseDouble(MarineCargoIPT)
																		+ Double.parseDouble(POLIPT)
																		+ Double.parseDouble(EmplbtCargoIPT)
																		+ Double.parseDouble(ProductLbtIPT)
																		+ Double.parseDouble(PubliclbtIPT)
																		);
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066"))
				{
					
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General RAS Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT");
					double actualDue = values[0] + values[1];
					String dueAmmout =  common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(generalDue), "General RAS Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AJG Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AJG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("AIG Europe Ltd") && account.equalsIgnoreCase("AA750"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AIG Amount ");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AIG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AIG Due Amount ");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("Z906"))
				{
					String generalAJG= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();			
					double generalAJGPremium =  (generalPremium * 0.0025) 
												+ (Double.parseDouble(terrorPremium) * 0.0025) 
												+ (Double.parseDouble(DirectorsGrossPremium) * 0.0025)
												+ (Double.parseDouble(MarineCargoGrossPremium) * 0.0025)
												+ (Double.parseDouble(EmpLbtGrossPremium) * 0.0025)
												+ (Double.parseDouble(POLPremium) * 0.0025);
				
					
					String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
					compareValues(Double.parseDouble(generalammount), Double.parseDouble(generalAJG), "General AIG Amount ");
					customAssert.assertTrue(WriteDataToXl(event+"_"+code, "Transaction Summary", (String)common.Renewal_excel_data_map.get("Automation Key"), "TS_AIGAmount", generalammount,common.Renewal_excel_data_map),"Error while writing AIG Ammount data to excel .");
					
					return Double.parseDouble(generalammount);
				}
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066") &&
						(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{				
							double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
							compareValues(values[0], Double.parseDouble(general), "General RAS Amount ");
							compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT  ");
							double actualDue = values[0] + values[1];
							String dueAmmout =  common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General RAS Due Amount ");
							return Double.parseDouble(dueAmmout);
						}
						else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324") &&
								(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{
						double generalAJGPremium =  generalPremium *  0.4 * 0.62;
							double actualgeneralAJGIPT = generalIPT * 0.4;
							String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
							compareValues(Double.parseDouble(generalammount), Double.parseDouble(general), "General AJG Amount ");
							compareValues(Double.parseDouble(generalInsTax), Double.parseDouble(common.roundedOff(Double.toString(actualgeneralAJGIPT))), "General AJG IPT ");
							double actualDue = generalAJGPremium + actualgeneralAJGIPT;
							String dueAmmout = common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Ammount ");
							return Double.parseDouble(dueAmmout);
						}
				return 0.00;
		
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
  }
  
  public double calculateXOETS_Renewal(String recipient,String account,int j, int td){
		try{
			double terrorAmount = 0.0;
			String PropertyExcessofLossGP = (String)common.Renewal_excel_data_map.get("PS_PropertyExcessofLoss_GP");
			String cedeCommission = (String)common.Renewal_excel_data_map.get("TER_CedeComm"); 	
			String PenGrossIPT = (String)common.Renewal_excel_data_map.get("PS_Total_GT"); 
			String TerrorisamNP = common.Renewal_excel_data_map.get("PS_Terrorism_NP").toString();
			String part1= "//*[@id='table0']/tbody";
			
			String actualXOEPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualXOEIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualXOEDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			
			if(((String)common.Renewal_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{	
			if(cedeCommission.equalsIgnoreCase("No")){terrorAmount = (Double.parseDouble(TerrorisamNP) * 0.9);}
			else{terrorAmount = Double.parseDouble(TerrorisamNP);}
			}
			double GeneralAmount =  (Double.parseDouble(PropertyExcessofLossGP) * 0.64) + terrorAmount;
			compareValues(GeneralAmount, Double.parseDouble(actualXOEPremium), "XOE Amount ");
			compareValues(Double.parseDouble(PenGrossIPT), Double.parseDouble(actualXOEIPT), "XOE  IPT ");
			double XOEDue = GeneralAmount + Double.parseDouble(PenGrossIPT);
			compareValues(Double.parseDouble(actualXOEDue), Double.parseDouble(common.roundedOff(Double.toString(XOEDue))), "XOE Due Amount ");
			return Double.parseDouble(common.roundedOff(Double.toString(XOEDue)));	
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Ammout for Marine Cargo. \n", t);
			return 0;
		}
	}
  
  public double calculateBrokeageAmountTS_Renewal(String recipient,String account,int j, int td){
		double materialDamageFP = 0.00, businessInteruptionFP=0.00, EmployersLiabiliyFP=0.00, PublicLiabilityFP=0.00, ContractorAllRisksFP=0.00;
		double ProductLiability =0.00, ComputersAndElectronicRiskFP=0.00, MoneyFP =0.00, GoodsInTansitFP=0.00,FidilityGuaranteFP=0.00;
		double LegalExpensesFP=0.00, terrorismFP=0.00, DirectorsAndOfficersFP=0.00, SpecifiedRisksFP=0.00, generalPremium;
		double MarineCargoFP=0.00, FrozenFoodFP=0.00, LossofLicenceFP=0.00, PropertyOwnersLiability=0.00, LossOfRentalIncomeFP=0.00;
		double PropertyExcessofLossFP=0.00;
		String part1= "//*[@id='table0']/tbody";
		try{
			String brokerageAccount= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			if((String)common.Renewal_excel_data_map.get("CD_MaterialDamage")!= null && ((String)common.Renewal_excel_data_map.get("CD_MaterialDamage")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_MaterialDamage_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_MaterialDamage_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
				materialDamageFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					materialDamageFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}
			}
			if((String)common.Renewal_excel_data_map.get("CD_LossOfRentalIncome")!= null && ((String)common.Renewal_excel_data_map.get("CD_LossOfRentalIncome")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_LossOfRentalIncome_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_LossOfRentalIncome_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossOfRentalIncomeFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossOfRentalIncomeFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)common.Renewal_excel_data_map.get("CD_PropertyExcessofLoss")!= null && ((String)common.Renewal_excel_data_map.get("CD_PropertyExcessofLoss")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_PropertyExcessofLoss_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_PropertyExcessofLoss_CR");
				if(product.equalsIgnoreCase("XOE"))
				{
					PropertyExcessofLossFP = calculateXOEGeneralPremium(GrossPremium,BrokerCommission);
				}
													
			}
			if((String)common.Renewal_excel_data_map.get("CD_BusinessInterruption")!= null && ((String)common.Renewal_excel_data_map.get("CD_BusinessInterruption")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_BusinessInterruption_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_BusinessInterruption_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					businessInteruptionFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					businessInteruptionFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)common.Renewal_excel_data_map.get("CD_Liability")!= null && ((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_EmployersLiability_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_EmployersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					EmployersLiabiliyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					EmployersLiabiliyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)common.Renewal_excel_data_map.get("CD_Liability")!= null && ((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_PublicLiability_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_PublicLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					PublicLiabilityFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")){
					PublicLiabilityFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}
			if((String)common.Renewal_excel_data_map.get("CD_ContractorsAllRisks")!= null && ((String)common.Renewal_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_ContractorsAllRisks_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_ContractorsAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ContractorAllRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					ContractorAllRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)common.Renewal_excel_data_map.get("CD_SpecifiedAllRisks")!= null && ((String)common.Renewal_excel_data_map.get("CD_SpecifiedAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_SpecifiedAllRisks_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_SpecifiedAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					SpecifiedRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					SpecifiedRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)common.Renewal_excel_data_map.get("CD_Liability")!= null && ((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_ProductsLiability_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_ProductsLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					ProductLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG"))
				{
					ProductLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}
			if((String)common.Renewal_excel_data_map.get("CD_Liability")!= null && ((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("Yes")
					&& (product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("POC")))
			{
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_PropertyOwnersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					PropertyOwnersLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("POC")){
					PropertyOwnersLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.Renewal_excel_data_map.get("CD_ComputersandElectronicRisks")!= null && ((String)common.Renewal_excel_data_map.get("CD_ComputersandElectronicRisks")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_ComputersandElectronicRisks_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_ComputersandElectronicRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}
			if((String)common.Renewal_excel_data_map.get("CD_Money")!= null && ((String)common.Renewal_excel_data_map.get("CD_Money")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_Money_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_Money_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					MoneyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					MoneyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}
			if((String)common.Renewal_excel_data_map.get("CD_GoodsInTransit")!= null && ((String)common.Renewal_excel_data_map.get("CD_GoodsInTransit")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_GoodsInTransit_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_GoodsInTransit_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					GoodsInTansitFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					GoodsInTansitFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)common.Renewal_excel_data_map.get("CD_FidelityGuarantee")!= null && ((String)common.Renewal_excel_data_map.get("CD_FidelityGuarantee")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_FidelityGuarantee_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_FidelityGuarantee_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FidilityGuaranteFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FidilityGuaranteFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.Renewal_excel_data_map.get("CD_FrozenFood")!= null && ((String)common.Renewal_excel_data_map.get("CD_FrozenFood")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_FrozenFoods_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_FrozenFoods_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FrozenFoodFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FrozenFoodFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.Renewal_excel_data_map.get("CD_LossofLicence")!= null && ((String)common.Renewal_excel_data_map.get("CD_LossofLicence")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_LossOfLicence_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_LossOfLicence_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossofLicenceFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossofLicenceFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.Renewal_excel_data_map.get("CD_LegalExpenses")!= null && ((String)common.Renewal_excel_data_map.get("CD_LegalExpenses")).equals("Yes"))
			{					
				String NetPremium = (String)common.Renewal_excel_data_map.get("PS_LegalExpenses_NP");
				String annualCarrier = (String)common.Renewal_excel_data_map.get("LE_AnnualCarrierPremium");
				LegalExpensesFP = Double.parseDouble(NetPremium) - Double.parseDouble(annualCarrier);
			}
			if((String)common.Renewal_excel_data_map.get("CD_Terrorism")!= null && ((String)common.Renewal_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_Terrorism_GP");
				String NetPremium = (String)common.Renewal_excel_data_map.get("PS_Terrorism_NP");
				terrorismFP = (Double.parseDouble(NetPremium)*(10.00/100))	- (Double.parseDouble(GrossPremium)*(0.25/100));
				if(((String)common.Renewal_excel_data_map.get("TER_CedeComm")).equalsIgnoreCase("Yes")){
					terrorismFP= -(Double.parseDouble((String)common.Renewal_excel_data_map.get("TS_AIGAmount")));
				}
			}
			if((String)common.Renewal_excel_data_map.get("CD_DirectorsandOfficers")!= null && ((String)common.Renewal_excel_data_map.get("CD_DirectorsandOfficers")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String brokerCommision = (String)common.Renewal_excel_data_map.get("PS_DirectorsandOfficers_CR");
				DirectorsAndOfficersFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}
			if((String)common.Renewal_excel_data_map.get("CD_MarineCargo")!= null && ((String)common.Renewal_excel_data_map.get("CD_MarineCargo")).equals("Yes"))
			{					
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_MarineCargo_GP");
				String brokerCommision = (String)common.Renewal_excel_data_map.get("PS_MarineCargo_CR");
				MarineCargoFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}
								
			generalPremium= materialDamageFP + LossOfRentalIncomeFP +businessInteruptionFP + EmployersLiabiliyFP 
					+ PublicLiabilityFP + PropertyOwnersLiability + ContractorAllRisksFP + SpecifiedRisksFP + ProductLiability 
					+ ComputersAndElectronicRiskFP + MoneyFP + GoodsInTansitFP + FidilityGuaranteFP + LegalExpensesFP
					+terrorismFP + DirectorsAndOfficersFP + MarineCargoFP + LossofLicenceFP + FrozenFoodFP+PropertyExcessofLossFP;
				String generalammount = decim.format(generalPremium);
		//	String actualgeneralPremium = decim.format(generalPremium);
				compareValues(Double.parseDouble(generalammount), Double.parseDouble(brokerageAccount), "General Brokerage Amount ");
				return Double.parseDouble(generalammount);
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Brokerage ammout. \n", t);
			return 0;
		}
  }
  
  public boolean funcPremiumSummary_Renewal(Map<Object, Object> map_data,String code,String event,String flowName){
		
		boolean r_value= true;
		String testName = (String)map_data.get("Automation Key");
		String customPolicyDuration=null;
		
		try{
			customAssert.assertTrue(common.funcPageNavigation("Premium Summary", ""),"Premium Summary page navigations issue(S)");
			
			customAssert.assertTrue(k.SelectRadioBtn("Payment_Warranty_rules", (String)map_data.get("PS_PaymentWarrantyRules")),"Unable to Select Payment_Warranty_rules radio button . ");
			
			if(((String)map_data.get("PS_PaymentWarrantyRules")).equals("Yes")){
				
				customAssert.assertTrue(k.Click("Payment_Warranty_Due_Date"), "Unable to Click Payment_Warranty_Due_Date date picker .");
				customAssert.assertTrue(k.Input("Payment_Warranty_Due_Date", (String)map_data.get("PS_PaymentWarrantyDueDate")),"Unable to Enter Payment_Warranty_Due_Date .");
				customAssert.assertTrue(k.Click("calander_btn"), "Unable to click on done button in calander.");
			}
			customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Premium Summary .");
			customAssert.assertTrue(common.funcPremiumSummaryTable_Renewal(code, event, map_data),"Premium summary Calculation function failed");		
			customAssert.assertTrue(k.Click("CCF_Btn_Save"), "Unable to click on Save Button on Premium Summary .");
			customPolicyDuration = k.getText("Policy_Duration");
			customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName, "PS_Duration", customPolicyDuration,map_data),"Error while writing Policy Duration data to excel .");
			TestUtil.reportStatus("Policy Duration = "+customPolicyDuration+" Days . ", "Info", true);
			
			TestUtil.reportStatus("Premium Summary details are filled sucessfully . ", "Info", true);
			
		}catch(Throwable t){
			return false;
		}
	
	return r_value;
	}
  
  public boolean funcPremiumSummaryTable_Renewal(String code,String event,Map<Object, Object> map_data) throws Exception{
		
		String testName = (String)map_data.get("Automation Key");
		WebDriverWait wait;
		wait = new WebDriverWait(driver, 5);
		Hashtable<String,String> coverlist = new Hashtable<String,String>();
		Hashtable<String,String> premSmryData = new Hashtable<String,String>();
		Hashtable<String,String> transSmryData = new Hashtable<String,String>();
		Hashtable<String,String> cHash = new Hashtable<String,String>();
		boolean transPremFlag =false;
		String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
		String[] prem_header = {"GrossPremium","CommissionRate","Commission","NetPremium","GrossIPT","NetIPT"};
		//System.out.println(prem_col(0));
		if(CommonFunction_GUS.product.equalsIgnoreCase("POB")|| CommonFunction_GUS.product.equalsIgnoreCase("POC")){
			coverlist.put("Material Damage","md8");
			coverlist.put("Loss Of Rental Income","bi3");
			coverlist.put("Property Owners Liability","pl3");
			coverlist.put("Terrorism","tr3");
		}else if(CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
			coverlist.put("Property Excess of Loss","xo1");
			coverlist.put("Terrorism","tr2");
		}else{
			coverlist.put("Material Damage","md7");
			coverlist.put("Business Interruption","bi2");
			coverlist.put("Terrorism","tr2");
		}
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
		if(code.equalsIgnoreCase("CTA")){
			coverlist.put("Directors and Officers","do_pct");
		}else{
			coverlist.put("Directors and Officers","do2");
			}
		coverlist.put("Frozen Foods","ff2");
		coverlist.put("Loss of Licence","ll2");
		coverlist.put("Fidelity Guarantee","fg");
		coverlist.put("Legal Expenses","lg2");
		coverlist.put("Total","tot");
		
		cHash.put("gprem","GP");
		cHash.put("comr","CR");
		cHash.put("comm","GC");
		cHash.put("nprem","NP");
		cHash.put("gipt","GT");
		cHash.put("nipt","NPIPT");
		cHash.put("com","GC");
		
		if (driver.findElement(By.xpath("//*[@id='main']/form/p")).getText().contains("Premium Summary")==false){
			Assert.fail("Premium summary Page couldn't displayed");
		}
		String stDate = null;
		String endDate =null;
		try{
			k.ImplicitWaitOff();
			stDate = driver.findElement(By.xpath("//*[@name='start_date']")).getAttribute("value");
		}catch(Exception e){
			stDate = driver.findElement(By.xpath("//*[@id='start_date']")).getText() ;//getAttribute("value");
		}
		try{
			k.ImplicitWaitOff();
			endDate = driver.findElement(By.xpath("//*[@name='end_date']")).getAttribute("value");
		}catch(Exception e){
			 endDate = driver.findElement(By.xpath("//*[@id='end_date']")).getText() ;//.getAttribute("value");
		}
		String duration = driver.findElement(By.xpath("//*[@id='duration']")).getText();
		List<WebElement> col=driver.findElements(By.xpath("html/body/div[3]/form/div/table[2]/tbody/tr[1]/td"));
		int cCount = col.size();
		
		List<WebElement> row = driver.findElements(By.xpath("html/body/div[3]/form/div/table[2]/tbody/tr"));
		//System.out.println(row.size());
		int rcount = row.size();
		String Status = driver.findElement(By.xpath("//*[@id='headbox']/tbody/tr[1]/td[6]")).getText();
		//System.out.println("Policy Status - "+ Status); 
		TestUtil.reportStatus("Current Policy Status - "+ Status, "Info", false);
		switch(Status){
		
		case "Endorsement Submitted":  		
			customAssert.assertTrue(PremiumSummaryDataTraverse_Renewal(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation");
			customAssert.assertTrue(common.insuranceTaxAdjustmentHandling_RenewalEndorsment(code,event),"Insurance tax adjustment is having issue(S).");
			TestUtil.reportStatus("Premium Summary data verification after Insurance Tax Adjustment", "Info", true);
			customAssert.assertTrue(PremiumSummaryDataTraverse_Renewal(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation after Insurance adjustment");
			break;	
		case "Renewal Submitted (Rewind)":  		
			customAssert.assertTrue(PremiumSummaryDataTraverse_Renewal(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation");
			customAssert.assertTrue(common.insuranceTaxAdjustmentHandling_RenewalEndorsment(code,event),"Insurance tax adjustment is having issue(S).");
			TestUtil.reportStatus("Premium Summary data verification after Insurance Tax Adjustment", "Info", true);
			customAssert.assertTrue(PremiumSummaryDataTraverse_Renewal(rcount,coverlist,cHash,code,event,false),"Failed in Premium Summary Calculation after Insurance adjustment");
			break;	
		case "Renewal Submitted":  		
			customAssert.assertTrue(PremiumSummaryDataTraverse_Renewal(rcount,coverlist,cHash,code,event,true),"Failed in Premium Summary Calculation");
			customAssert.assertTrue(common.insuranceTaxAdjustmentHandling_RenewalEndorsment(code,event),"Insurance tax adjustment is having issue(S).");
			TestUtil.reportStatus("Premium Summary data verification after Insurance Tax Adjustment", "Info", true);
			customAssert.assertTrue(PremiumSummaryDataTraverse_Renewal(rcount,coverlist,cHash,code,event,false),"Failed in Premium Summary Calculation after Insurance adjustment");
			break;
		}
		
		
		double FinalPremium = 0.0 , Total_GrossPremium , Total_GrossTax , calcFinalPremium = 0.0;
		switch (common.currentRunningFlow) {
		case "NB":
			FinalPremium =Double.parseDouble(k.getTextByXpath("//*[@class='gttext']").replaceAll(",",""));
			Total_GrossPremium =Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GP",""));// premSmryData.get("PS_Total_GP"));
			Total_GrossTax=Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GT",""));// premSmryData.get("PS_Total_GT"));
			calcFinalPremium = Total_GrossPremium +Total_GrossTax;
			break;
		case "CAN":
			FinalPremium =Double.parseDouble(k.getTextByXpath("//*[@class='gttext']").replaceAll(",",""));
			Total_GrossPremium =Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GP",""));// premSmryData.get("PS_Total_GP"));
			Total_GrossTax=Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GT",""));// premSmryData.get("PS_Total_GT"));
			calcFinalPremium = Total_GrossPremium +Total_GrossTax;
			break;
		case "MTA":
			FinalPremium =Double.parseDouble(k.getTextByXpath("//*[@class='gttext']").replaceAll(",",""));
			Total_GrossPremium =Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GP",""));// premSmryData.get("PS_Total_GP"));
			Total_GrossTax=Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GT",""));// premSmryData.get("PS_Total_GT"));
			calcFinalPremium = Total_GrossPremium +Total_GrossTax;
			break;
		case "Renewal":
			FinalPremium =Double.parseDouble(k.getTextByXpath("//*[@class='gttext']").replaceAll(",",""));
			Total_GrossPremium =Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GP",CommonFunction_GUS.businessEvent));// premSmryData.get("PS_Total_GP"));
			Total_GrossTax=Double.parseDouble(TestUtil.getStringfromMap("PS_Total_GT",CommonFunction_GUS.businessEvent));// premSmryData.get("PS_Total_GT"));
			calcFinalPremium = Total_GrossPremium +Total_GrossTax;
			break;
		
		default:
			break;
		}
		
		compareValues(calcFinalPremium,FinalPremium,"Policy Premium ");
		TestUtil.reportStatus("Final Policy Premium  :<b>"+calcFinalPremium, "Info", false);

		k.ImplicitWaitOn();
		return true;
		
	}
  
  @SuppressWarnings("static-access")
	public boolean insuranceTaxAdjustmentHandling_RenewalEndorsment(String code , String event){
	 	Map<Object, Object> map_to_update=common.Renewal_excel_data_map;
		try {
			
			common.funcButtonSelection("Insurance Tax");
			customAssert.assertTrue(funcPageNavigation("Tax Adjustment", ""),"Unable to land on Tax adjustment screen.");
			String sectionName;
			
			//customAssert.assertTrue(verifyCoverDetails(),"PremiumValue verification is causing issue(S).");
			//customAssert.assertTrue(verifyGrossPremiumValues(),"PremiumValue verification is causing issue(S).");
			String policy_status_actual = k.getText("Policy_status_header");
			
			switch ((String)map_to_update.get("PS_InsuranceTaxButton")) {
			case "Yes":
				String value = null;
				List<WebElement> list = k.findElements("insuranceTaxExemptionRadioButton");
				for(int i=0;i<list.size();i++){
					
					boolean selectedStatus =  list.get(i).isSelected();
					if(selectedStatus){
						value = list.get(i).getAttribute("Value");	
					}
				}
				
				if(CommonFunction_GUS.product.equalsIgnoreCase("XOE")){
					if(value.equalsIgnoreCase("No")){
						customAssert.assertTrue(k.SelectRadioBtn("insuranceTaxExemptionRadioButton", (String)map_to_update.get("PS_InsuranceTaxButton")));
						k.waitTwoSeconds();
						customAssert.assertTrue(k.AcceptPopup(), "Unable to accept alert box.");
					}
				}else{
					if(((String)map_to_update.get("PD_TaxExempt")).equalsIgnoreCase("No") && value.equalsIgnoreCase("No")){
						customAssert.assertTrue(k.SelectRadioBtn("insuranceTaxExemptionRadioButton", (String)map_to_update.get("PS_InsuranceTaxButton")));
						k.waitTwoSeconds();
						customAssert.assertTrue(k.AcceptPopup(), "Unable to accept alert box.");
					}
				}
				
				taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY"); 
				List<WebElement> listOfCovers = taxTable_tBody.findElements(By.tagName("tr"));
				countOfCovers = listOfCovers.size();
				
				for(int j=0;j<countOfCovers-1;j++){
					
					sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText().replaceAll(" ", "");
					if(sectionName.contains("ExcessofLoss")){
						sectionName = "PropertyExcessofLoss";
					}
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_"+sectionName+"_GT", common.roundedOff("00.00"), map_to_update);
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_"+sectionName+"_IPT", common.roundedOff("00.00"), map_to_update);
					TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_"+sectionName+"_NPIPT", common.roundedOff("00.00"), map_to_update);
					totalGrossPremium = totalGrossPremium + Double.parseDouble(common.roundedOff((String)map_to_update.get("PS_"+sectionName+"_GP")));
				}
				
				if(policy_status_actual.contains("Endorsment Submitted")){
					common.totalGrossPremium = 0.0;
					common.totalGrossTax = 0.0;
					common.totalNetPremiumTax = 0.0;
					
					for(int j=0;j<countOfCovers-1;j++){
						
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						if(sectionName.contains("Excess of Loss")){
							sectionName = "Property Excess of Loss";
						}
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							String expGP = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
							String expGT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
							String expNPIT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_NPIPT");
							
							common.totalGrossPremium = common.totalGrossPremium + Double.parseDouble(expGP);
							common.totalGrossTax = common.totalGrossTax + Double.parseDouble(expGT);
							common.totalNetPremiumTax = common.totalNetPremiumTax + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
						countOfCovers = list3.size();
					}
				}
				if(policy_status_actual.contains("Renewal Submitted")){
					common.totalGrossPremium = 0.0;
					common.totalGrossTax = 0.0;
					common.totalNetPremiumTax = 0.0;
					
					for(int j=0;j<countOfCovers-1;j++){
						
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						if(sectionName.contains("Excess of Loss")){
							sectionName = "Property Excess of Loss";
						}
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							String expGP = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
							String expGT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
							String expNPIT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_NPIPT");
							
							common.totalGrossPremium = common.totalGrossPremium + Double.parseDouble(expGP);
							common.totalGrossTax = common.totalGrossTax + Double.parseDouble(expGT);
							common.totalNetPremiumTax = common.totalNetPremiumTax + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
						countOfCovers = list3.size();
					}
				}
				if(policy_status_actual.contains("Rewind")){
					common.totalGrossPremium = 0.0;
					common.totalGrossTax = 0.0;
					common.totalNetPremiumTax = 0.0;
					
					for(int j=0;j<countOfCovers-1;j++){
						
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						if(sectionName.contains("Excess of Loss")){
							sectionName = "Property Excess of Loss";
						}
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							/*String actGP =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText();
							String actGT =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText();
							*/
							String expGP = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
							String expGT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
							String expNPIT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_NPIPT");
							
							common.totalGrossPremium = common.totalGrossPremium + Double.parseDouble(expGP);
							common.totalGrossTax = common.totalGrossTax + Double.parseDouble(expGT);
							common.totalNetPremiumTax = common.totalNetPremiumTax + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
						countOfCovers = list3.size();
					}
				}
				TestUtil.reportStatus("<b> Tax adjustment operatios is completed. </b>", "Info", false);
				
				TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GP", common.roundedOff(Double.toString(totalGrossPremium)), map_to_update);
				TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GT", common.roundedOff("00.00"), map_to_update);
				TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_NPIPT", common.roundedOff("00.00"), map_to_update);
				TestUtil.reportStatus("<b>Policy Exempt from insurance tax radio button is selected as 'Yes' Hence skipped adjustment operation for all covers.</b>", "Info", false);
				break;
				
			case "No":
				
				TestUtil.reportStatus("<b> Tax adjustment operatios is started. </b>", "Info", false);
				customAssert.assertTrue(k.SelectRadioBtn("insuranceTaxExemptionRadioButton", (String)common.Renewal_excel_data_map.get("PS_InsuranceTaxButton")));
				//taxTable_tHead = k.getObject("inssuranceTaxMainTableHEAD");
				taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY"); 
				List<WebElement> list2 = taxTable_tBody.findElements(By.tagName("tr"));
				countOfCovers = list2.size();
				
				
				for(int j=0;j<countOfCovers-1;j++){
					
					taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
					sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
					if(sectionName.contains("Excess of Loss")){
						sectionName = "Property Excess of Loss";
					}
					
					String grossPremium =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText();
					//String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[4]")).getText();
					//String grossTax =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText();
					
					if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
						continue;
					}else{
						
						customAssert.assertTrue(adjustInsuranceTax_Renewal(grossPremium,sectionName,code,event),"Adjust insurance Tax function is causing issue(S).");
						customAssert.assertTrue(verifyAdjustedTaxValues_Renewal(sectionName,code,event),"Verify adjusted Tax function is having issue(S).");
					}
						
					taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
					List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
					countOfCovers = list3.size();
				}
				/*if(policy_status_actual.contains("Endorsement Submitted")){
					common.totalGrossPremiumMTA = 0.0;
					common.totalGrossTaxMTA = 0.0;
					common.totalNetPremiumTaxMTA = 0.0;
					
					for(int j=0;j<countOfCovers-1;j++){
						
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						if(sectionName.contains("Excess of Loss")){
							sectionName = "Property Excess of Loss";
						}
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							String expGP = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
							String expGT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
							String expNPIT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_NPIPT");
							
							common.totalGrossPremiumMTA = common.totalGrossPremiumMTA + Double.parseDouble(expGP);
							common.totalGrossTaxMTA = common.totalGrossTaxMTA + Double.parseDouble(expGT);
							common.totalNetPremiumTaxMTA = common.totalNetPremiumTaxMTA + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
						countOfCovers = list3.size();
					}
				}*/
				if(policy_status_actual.contains("Renewal Submitted")){
					common.totalGrossPremiumMTA = 0.0;
					common.totalGrossTaxMTA = 0.0;
					common.totalNetPremiumTaxMTA = 0.0;
					
					for(int j=0;j<countOfCovers-1;j++){
						
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						if(sectionName.contains("Excess of Loss")){
							sectionName = "Property Excess of Loss";
						}
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							String expGP = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
							String expGT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
							String expNPIT = (String)map_to_update.get("PS_"+sectionName.replaceAll(" ", "")+"_NPIPT");
							
							common.totalGrossPremiumMTA = common.totalGrossPremiumMTA + Double.parseDouble(expGP);
							common.totalGrossTaxMTA = common.totalGrossTaxMTA + Double.parseDouble(expGT);
							common.totalNetPremiumTaxMTA = common.totalNetPremiumTaxMTA + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
						countOfCovers = list3.size();
					}
				}
				if(policy_status_actual.contains("Rewind")){
					common.totalGrossPremium = 0.0;
					common.totalGrossTax = 0.0;
					common.totalNetPremiumTax = 0.0;
					
					for(int j=0;j<countOfCovers-1;j++){
						
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						sectionName = taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText();
						if(sectionName.contains("Excess of Loss")){
							sectionName = "Property Excess of Loss";
						}
						
						if(sectionName.equalsIgnoreCase("") || sectionName==null || sectionName.equalsIgnoreCase("Totals")){
							continue;
						}else{
							
							String expGP = (String)common.Renewal_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_GP");
							String expGT = (String)common.Renewal_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_GT");
							String expNPIT = (String)common.Renewal_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_NPIPT");
							
							common.totalGrossPremium = common.totalGrossPremium + Double.parseDouble(expGP);
							common.totalGrossTax = common.totalGrossTax + Double.parseDouble(expGT);
							common.totalNetPremiumTax = common.totalNetPremiumTax + Double.parseDouble(expNPIT);
							
						}
						taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
						List<WebElement> list3 = taxTable_tBody.findElements(By.tagName("tr"));
						countOfCovers = list3.size();
					}
				}
				TestUtil.reportStatus("<b> Tax adjustment operatios is completed. </b>", "Info", false);
				TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GP", common.roundedOff(Double.toString(totalGrossPremium)), map_to_update);
				TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_GT", common.roundedOff(Double.toString(totalGrossTax)), map_to_update);
				TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",(String)map_to_update.get("Automation Key"), "PS_Total_NPIPT", common.roundedOff(Double.toString(totalNetPremiumTax)), map_to_update);
			
				break;
			default:
				break;
			}
			
			common.funcPageNavigation("", "Save");
			k.Click("Tax_adj_BackBtn");
				
			return true;
		}catch (Throwable t) {
			k.ImplicitWaitOn();
			return false;
		}
	}
	
  @SuppressWarnings("static-access")
	public static boolean adjustInsuranceTax_Renewal(String grossPremium,String sectionName,String code,String event){
		try{
			variableTaxAdjustmentDataMaps = new LinkedHashMap<>();
			variableTaxAdjustmentVerificationMaps = new LinkedHashMap<>();
			variableTaxAdjustmentIDs = new HashMap<>();
			grossTaxValues_Map = new HashMap<>();
			headerNameStorage = new ArrayList<>();
			inputarraylist = new ArrayList<>();
			inputarraylistMTA = new ArrayList<>();
			Adjusted_Premium_map = new HashMap<>();
			
			adjustedPremium = Double.parseDouble(common.roundedOff((String)common.GrosspremSmryData.get("PS_"+sectionName.replaceAll(" ", "")+"_GP")));
			if(sectionName.contains("Property Excess of Loss")){
				sectionName = "Excess of Loss";
			}
			
			int count = common.no_of_inner_data_sets.get("Variable Tax Adjustment");
					
			int counter = 0;
			String coverName = sectionName;
			String policy_status_actual = k.getText("Policy_status_header");
			for(int l=0;l<count;l++){
				if(policy_status_actual.contains("Endorsement Submitted")){
					if(sectionName.contains("Liability")){
						coverName = "Liability";
					}
					if(sectionName.contains("Licence")){
						coverName = "Loss of Licence";
					}
					if(sectionName.contains("Frozen")){
						coverName = "Frozen Food";
					}
					if(sectionName.contains("Excess")){
						coverName = "Property Excess of Loss";
					}
					if((((String)common.Renewal_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes"))){
						
						if(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName").equalsIgnoreCase(sectionName) && 
								common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key").contains("Endorsement")){
								Assert.assertTrue(k.Click("insuranceTaxAddAdjustmentButton"),"Unable to click on Add adustment button on Insurance Tax screen.");
								
								WebElement adjustmentTax = k.getObject("insuranceTaxAddAdjustmentTable");
								Select select = new Select(adjustmentTax.findElement(By.xpath("//*[contains(@name,'section')]")));
								String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
								String description = null;
								description = common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description");
								
								if(!description.contains("_")){
									description = common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description")+"_"+timeStamp;
									TestUtil.WriteDataToXl_innerSheet(code+"_"+event, "Variable Tax Adjustment", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), "VTA_Description", description, common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l));
								}
								select.selectByVisibleText(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentPremium", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium") ), "Unable to enter Premium on insurance Tax adjustment.");
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentTaxRate", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate") ), "Unable to enter Premium on insurance Tax adjustment.");
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentDescription", description ), "Unable to enter Premium on insurance Tax adjustment.");
								
								//System.out.println(variableTaxAdjustmentDataMaps);
								
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Section Name", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Premium", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Tax Rate", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate"));
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Description", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description"));
								
								headerNameStorage.add(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"));
								//variableTaxAdjustmentVerificationMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), variableTaxAdjustmentDataMaps);
								
								List<WebElement> listOfButtons = adjustmentTax.findElements(By.tagName("a"));
								//System.out.println("****************Total present button "+listOfButtons.size()+"********************");
								
								for(int k=0;k<listOfButtons.size();k++){
									String buttonName = listOfButtons.get(k).getText();
									if(buttonName.equalsIgnoreCase("Save")){
										listOfButtons.get(k).click();
										counter++;
										if(!validationOnAdjustedPremium_Renewal(sectionName,l)){
											break;
										}
										adjustedPremium = adjustedPremium - Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
										break;
									}
								}
							}
						}
					}
				if(policy_status_actual.contains("Rewind")){
					if(sectionName.contains("Liability")){
						coverName = "Liability";
					}
					if(sectionName.contains("Licence")){
						coverName = "Loss of Licence";
					}
					if(sectionName.contains("Frozen")){
						coverName = "Frozen Food";
					}
					if(sectionName.contains("Excess")){
						coverName = "Property Excess of Loss";
					}
					if((((String)common.Renewal_excel_data_map.get("CD_Add_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes") ||
							((String)common.Renewal_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes"))){
						
						if(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName").equalsIgnoreCase(sectionName) && 
								common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key").contains("Rewind")){
								Assert.assertTrue(k.Click("insuranceTaxAddAdjustmentButton"),"Unable to click on Add adustment button on Insurance Tax screen.");
								
								WebElement adjustmentTax = k.getObject("insuranceTaxAddAdjustmentTable");
								Select select = new Select(adjustmentTax.findElement(By.xpath("//*[contains(@name,'section')]")));
								String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
								String description = null;
								description = common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description");
								
								if(!description.contains("_")){
									description = common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description")+"_"+timeStamp;
									TestUtil.WriteDataToXl_innerSheet(code+"_"+event, "Variable Tax Adjustment", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), "VTA_Description", description, common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l));
								}
								select.selectByVisibleText(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentPremium", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium") ), "Unable to enter Premium on insurance Tax adjustment.");
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentTaxRate", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate") ), "Unable to enter Premium on insurance Tax adjustment.");
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentDescription", description ), "Unable to enter Premium on insurance Tax adjustment.");
								
								//System.out.println(variableTaxAdjustmentDataMaps);
								
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Section Name", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Premium", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Tax Rate", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate"));
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Description", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description"));
								
								headerNameStorage.add(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"));
								//variableTaxAdjustmentVerificationMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), variableTaxAdjustmentDataMaps);
								
								List<WebElement> listOfButtons = adjustmentTax.findElements(By.tagName("a"));
								//System.out.println("****************Total present button "+listOfButtons.size()+"********************");
								
								for(int k=0;k<listOfButtons.size();k++){
									String buttonName = listOfButtons.get(k).getText();
									if(buttonName.equalsIgnoreCase("Save")){
										listOfButtons.get(k).click();
										counter++;
										if(!validationOnAdjustedPremium_Renewal(sectionName,l)){
											break;
										}
										adjustedPremium = adjustedPremium - Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
										break;
									}
								}
							}
						}
					}
				
				if(policy_status_actual.contains("Renewal Submitted")){
					if(sectionName.contains("Liability")){
						coverName = "Liability";
					}
					if(sectionName.contains("Licence")){
						coverName = "Loss of Licence";
					}
					if(sectionName.contains("Frozen")){
						coverName = "Frozen Food";
					}
					if(sectionName.contains("Excess")){
						coverName = "Property Excess of Loss";
					}
					if((((String)common.Renewal_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes"))){
						
						if(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName").equalsIgnoreCase(sectionName)){
								Assert.assertTrue(k.Click("insuranceTaxAddAdjustmentButton"),"Unable to click on Add adustment button on Insurance Tax screen.");
								
								WebElement adjustmentTax = k.getObject("insuranceTaxAddAdjustmentTable");
								Select select = new Select(adjustmentTax.findElement(By.xpath("//*[contains(@name,'section')]")));
								String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
								String description = null;
								description = common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description");
								
								if(!description.contains("_")){
									description = common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description")+"_"+timeStamp;
									TestUtil.WriteDataToXl_innerSheet(code+"_"+event, "Variable Tax Adjustment", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), "VTA_Description", description, common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l));
								}
								select.selectByVisibleText(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentPremium", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium") ), "Unable to enter Premium on insurance Tax adjustment.");
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentTaxRate", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate") ), "Unable to enter Premium on insurance Tax adjustment.");
								customAssert.assertTrue(k.Input("InsuranceTaxAdjustmentDescription", description ), "Unable to enter Premium on insurance Tax adjustment.");
								
								//System.out.println(variableTaxAdjustmentDataMaps);
								
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Section Name", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_SectionName"));
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Premium", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Tax Rate", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_TaxRate"));
								variableTaxAdjustmentDataMaps.put(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key")+"_Description", common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Description"));
								
								headerNameStorage.add(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"));
								//variableTaxAdjustmentVerificationMaps.put(common.NB_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("Automation Key"), variableTaxAdjustmentDataMaps);
								
								List<WebElement> listOfButtons = adjustmentTax.findElements(By.tagName("a"));
								//System.out.println("****************Total present button "+listOfButtons.size()+"********************");
								
								for(int k=0;k<listOfButtons.size();k++){
									String buttonName = listOfButtons.get(k).getText();
									if(buttonName.equalsIgnoreCase("Save")){
										listOfButtons.get(k).click();
										counter++;
										if(!validationOnAdjustedPremium_Renewal(sectionName,l)){
											break;
										}
										adjustedPremium = adjustedPremium - Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(l).get("VTA_Premium"));
										break;
									}
								}
							}
						}
					}
				
			}
			variableTaxAdjustmentIDs.put(sectionName, counter);
			return true;
			
		}
		catch (Throwable t) {
			
			return false;
		}
	}
  
  @SuppressWarnings({ "static-access", "unused" })
	public static boolean verifyAdjustedTaxValues_Renewal(String sectionName,String code , String event){
		
		String coverName = sectionName;
		adjustedTotalTax = 0.0;
		adjustedTotalPremium = 0.0;
		unAdjustedTotalTax = 0.0;
		inputarraylist.clear();
		inputarraylistMTA.clear();
		
		try {
			taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
			List<WebElement> list2 = taxTable_tBody.findElements(By.tagName("tr"));
			countOfTableRows = list2.size();
			//System.out.println("Rows in table unde tBody : "+list2.size());
			
			Outer:
			for(int j=0;j<countOfCovers-1;j++){
				if(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
					//break Outer;
					break Outer;
				}
				if(sectionName.contains("Property Excess of Loss")){
					sectionName = "Excess of Loss";
				}
				String policy_status_actual = k.getText("Policy_status_header");
				if(policy_status_actual.contains("Endorsement Submitted")){
					if(sectionName.contains("Liability")){
						coverName = "Liability";
					}
					if(sectionName.contains("Frozen")){
						coverName = "FrozenFood";
					}
					if(sectionName.contains("Licence")){
						coverName = "LossofLicence";
					}
					if((((String)common.Renewal_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes"))){
						
						if(sectionName.equalsIgnoreCase(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText())){
							Inner :
							for(int m=0;m<=variableTaxAdjustmentIDs.get(sectionName)+1;m++){
								if(taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
									//break Outer;
									break Inner;
								}
								taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
								//Verify Adjusted Values
								errorVal = errorVal + verifyAdjustedTaxCalculation_Renewal(sectionName,j,m);
							}
							
							//Verify Unadjusted Values
							errorVal = errorVal + verifyUnAdjustedTaxCalculation_Renewal(sectionName,j);
							//Verify Gross Tax values
							errorVal = errorVal + verifyGrossTaxCalculation_Renewal(sectionName,j,code,event);
							break;
						}
						
					}
				}
				if(policy_status_actual.contains("Renewal Submitted")){
					if(sectionName.contains("Liability")){
						coverName = "Liability";
					}
					if(sectionName.contains("Frozen")){
						coverName = "FrozenFood";
					}
					if(sectionName.contains("Licence")){
						coverName = "LossofLicence";
					}
					if((((String)common.Renewal_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes"))){
						
						if(sectionName.equalsIgnoreCase(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText())){
							Inner :
							for(int m=0;m<=variableTaxAdjustmentIDs.get(sectionName)+1;m++){
								if(taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
									//break Outer;
									break Inner;
								}
								taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
								//Verify Adjusted Values
								errorVal = errorVal + verifyAdjustedTaxCalculation_Renewal(sectionName,j,m);
							}
							
							//Verify Unadjusted Values
							errorVal = errorVal + verifyUnAdjustedTaxCalculation_Renewal(sectionName,j);
							//Verify Gross Tax values
							errorVal = errorVal + verifyGrossTaxCalculation_Renewal(sectionName,j,code,event);
							break;
						}
						
					}
				}
				if(policy_status_actual.contains("Rewind")){
					if(sectionName.contains("Liability")){
						coverName = "Liability";
					}
					if(sectionName.contains("Frozen")){
						coverName = "FrozenFood";
					}
					if(sectionName.contains("Licence")){
						coverName = "LossofLicence";
					}
					if((((String)common.Renewal_excel_data_map.get("CD_Add_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes") ||
							((String)common.Renewal_excel_data_map.get("CD_"+coverName.replaceAll(" ", ""))).equalsIgnoreCase("Yes"))){
						
						if(sectionName.equalsIgnoreCase(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[1]")).getText())){
							Inner :
							for(int m=0;m<=variableTaxAdjustmentIDs.get(sectionName)+1;m++){
								if(taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
									//break Outer;
									break Inner;
								}
								taxTable_tBody = k.getObject("inssuranceTaxMainTableBODY");
								//Verify Adjusted Values
								errorVal = errorVal + verifyAdjustedTaxCalculation_Renewal(sectionName,j,m);
							}
							
							//Verify Unadjusted Values
							errorVal = errorVal + verifyUnAdjustedTaxCalculation_Renewal(sectionName,j);
							//Verify Gross Tax values
							errorVal = errorVal + verifyGrossTaxCalculation_Renewal(sectionName,j,code,event);
							break;
						}
						
					}
				}
			}
		} catch (Throwable t) {
			
			return false;
		} 
		
		return true;
	}
  
  public static int verifyAdjustedTaxCalculation_Renewal(String sectionName,int j,int m){
		
		try{
			String coverName = taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText();
			String adjustedPremium =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[3]")).getText();
			String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[4]")).getText();
			String adjustedTax =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[6]")).getText();
			String description =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[7]")).getText();
			String adjustedBy =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[8]")).getText();
			
			int index = k.getText("BannerName").indexOf("\n");
			String adjustedByName = k.getText("BannerName").substring(0, index);
			//int count = 0;
			int noOfVariableTax = common.no_of_inner_data_sets.get("Variable Tax Adjustment");
			
			if((coverName==null || coverName.isEmpty() || coverName.equalsIgnoreCase("")) && !(description.equalsIgnoreCase("Unadjusted Premium"))){
				for(int p=0;p<variableTaxAdjustmentIDs.get(sectionName);p++){
					if(!inputarraylist.contains(p)){
						int count = 0;
						while(count < noOfVariableTax){
							//int columIndex = NB_Suite_TC_Xls.getColumnIndex("Variable Tax Adjustment",(String)headerNameStorage.get(p));
							//System.out.println("Index of "+(String)headerNameStorage.get(p)+"   is : : "+columIndex);
							
							if(description.equalsIgnoreCase((String) variableTaxAdjustmentDataMaps.get(headerNameStorage.get(p)+"_Description"))){
								if(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(count).get("Automation Key").equalsIgnoreCase((String)headerNameStorage.get(p))){
									
									double adjustedPremium_calc = Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(count).get("VTA_Premium"));
									double iptRate_calc = Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(count).get("VTA_TaxRate"));
									String adjustedTax_calc = common.roundedOff(Double.toString((adjustedPremium_calc * iptRate_calc ) / 100.0));
									
									if(verification(common.roundedOff(adjustedPremium), common.roundedOff((String) variableTaxAdjustmentDataMaps.get(headerNameStorage.get(p)+"_Premium")), sectionName, "Adjusted Premium") &&
									   /*verification(common.roundedOff(iptRate), common.roundedOff((String) variableTaxAdjustmentDataMaps.get(headerNameStorage.get(p)+"_Tax Rate")), sectionName, "IPT Rate") &&*/
									   verification(common.roundedOff(adjustedTax), adjustedTax_calc, sectionName, "Adjusted Tax") &&
									   verification(description, (String) variableTaxAdjustmentDataMaps.get(headerNameStorage.get(p)+"_Description"), sectionName, "Description") &&
									   verification(adjustedBy, adjustedByName, sectionName, "Adjusted By")){
									   
										   inputarraylist.add(p);
										   adjustedTotalTax = adjustedTotalTax + Double.parseDouble(adjustedTax_calc);
										   adjustedTotalPremium = adjustedTotalPremium + adjustedPremium_calc;
										   return 0;
									
									}else{
									   return 1;
									}
								}
								
							}
							count++;
						}
						
					}
				}
			}
			return 0;
		}catch (Throwable t) {
			
			return 1;
		} 
	}
  
  
  public static int verifyUnAdjustedTaxCalculation_Renewal(String sectionName,int j){
		
		try{
			
			for(int m=0;m<=variableTaxAdjustmentIDs.get(sectionName)+1;m++){
				
				if(taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[1]")).getText().equalsIgnoreCase("Totals")){
					//break Outer;
					break;
				}
				
				String adjustedPremium =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[3]")).getText();
				String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[4]")).getText();
				String adjustedTax =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[6]")).getText();
				String description =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[7]")).getText();
				String adjustedBy =  taxTable_tBody.findElement(By.xpath("tr["+(j+(m+1))+"]/td[8]")).getText();
				
				int index = k.getText("BannerName").indexOf("\n");
				String adjustedByName = k.getText("BannerName").substring(0, index);
				
				if(description.equalsIgnoreCase("Unadjusted Premium")){
					if(sectionName.contains("Excess of Loss")){
						sectionName = "Property Excess of Loss";
					}
					String unAdjustedPremium = common.roundedOff(Double.toString(Double.parseDouble((String)common.GrosspremSmryData.get("PS_"+sectionName.replaceAll(" ", "")+"_GP")) - adjustedTotalPremium));
					String iptRate_calc =  common.roundedOff(Double.toString(Double.parseDouble((String)common.Renewal_excel_data_map.get("PS_IPTRate"))));
					String adjustedTax_calc = common.roundedOff(Double.toString((Double.parseDouble(unAdjustedPremium) * Double.parseDouble(iptRate_calc) ) / 100.0));
					unAdjustedTotalTax = Double.parseDouble(adjustedTax_calc);
					
					if(verification(common.roundedOff(adjustedPremium), unAdjustedPremium, sectionName, "Un Adjusted Premium") &&
					   /*verification(common.roundedOff(iptRate), iptRate_calc, sectionName, "IPT Rate") &&*/
					   verification(common.roundedOff(adjustedTax), adjustedTax_calc, sectionName, "Un Adjusted Tax") &&
					   verification(description, "Unadjusted Premium", sectionName, "Description") &&
					   verification(adjustedByName, adjustedBy, sectionName, "Adjusted By")){
						
						return 0;
					
					}else{
						return 1;
					}
				}
			}
			return 0;
		}catch (Throwable t) {
			
			return 1;
		}
	}
  
  public static int verifyGrossTaxCalculation_Renewal(String sectionName,int j,String code , String event){
		
		
	   String grossPremium =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[2]")).getText());
		String iptRate =  taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[4]")).getText();
		String grossTax =  common.roundedOff(taxTable_tBody.findElement(By.xpath("tr["+(j+1)+"]/td[5]")).getText());
		String colName = (String)common.Renewal_excel_data_map.get("Automation Key");
		if(sectionName.contains("Excess of Loss")){
			sectionName = "Property Excess of Loss";
		}
		String trimmedSectionName = sectionName.replaceAll(" ", "");
		
		String finalGrossPremium = common.roundedOff((String)common.GrosspremSmryData.get("PS_"+trimmedSectionName+"_GP"));
		String finalGrossTax,finalIPTRate,finalNetPremiumTax = null;
		if(inputarraylist.size()!=0){
			finalGrossTax = common.roundedOff(Double.toString(unAdjustedTotalTax +  adjustedTotalTax));
			finalIPTRate = Double.toString(((Double.parseDouble(finalGrossTax) /Double.parseDouble(finalGrossPremium)) * 100.0 ));
		}else{
			finalGrossTax = common.roundedOff(Double.toString((Double.parseDouble((String)common.GrosspremSmryData.get("PS_"+trimmedSectionName+"_GP")) * ((Double.parseDouble((String)common.Renewal_excel_data_map.get("PS_IPTRate")) / 100.0)))));
			finalIPTRate = (String)common.Renewal_excel_data_map.get("PS_IPTRate");
		}
		
		common.totalGrossTax = common.totalGrossTax + Double.parseDouble(finalGrossTax);
		common.totalGrossPremium = common.totalGrossPremium + Double.parseDouble(finalGrossPremium);
		
		
		TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",colName, "PS_"+sectionName.replaceAll(" ", "")+"_GT", finalGrossTax, common.Renewal_excel_data_map);
		TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",colName, "PS_"+sectionName.replaceAll(" ", "")+"_IPT", finalIPTRate, common.Renewal_excel_data_map);
		
		finalNetPremiumTax = common.roundedOff(Double.toString((Double.parseDouble((String)common.Renewal_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_NP")) * Double.parseDouble((String)common.Renewal_excel_data_map.get("PS_"+sectionName.replaceAll(" ", "")+"_IPT"))) / 100.0 ));
		
		common.totalNetPremiumTax = common.totalNetPremiumTax + Double.parseDouble(finalNetPremiumTax);
		TestUtil.WriteDataToXl(code+"_"+event, "Premium Summary",colName, "PS_"+sectionName.replaceAll(" ", "")+"_NPIPT", finalNetPremiumTax, common.Renewal_excel_data_map);
		
		grossTaxValues_Map.put(sectionName, Double.parseDouble(finalGrossTax));
		
		if(verification(grossPremium,finalGrossPremium,sectionName,"Gross Premium") && 
				verification(grossTax,finalGrossTax,sectionName,"Gross Tax") 
				/*&& verification(iptRate,finalIPTRate,sectionName,"IPT Rate")*/){
			TestUtil.reportStatus("[<b>  "+sectionName+"  </b>]  cover --- Verified Gross Premium is :  <b>[ "+finalGrossPremium+" ]</b> , IPT Rate is :  <b>[ "+finalIPTRate+" ]</b> , Gross Tax is :  <b>[ "+finalGrossTax+" ]</b> ", "PASS", false);
			return 0;
		}else{
			return 1;
		}
	}
  
  public static boolean PremiumSummarytableCalculation_Renewal(Hashtable<String,String> tabledata,String section ){
		//MaterialDamage_nipt, MaterialDamage_gprem, MaterialDamage_nprem, MaterialDamage_gipt, MaterialDamage_comm,MaterialDamage_comr
		try{ 	
		if(section.equals("Total")){ return true;}
			DecimalFormat f = new DecimalFormat("00.00");
			String secName = section.replaceAll(" ", "");
			String cSection = null;
			double grossPrem = Double.parseDouble(tabledata.get("PS_"+secName+"_GP"));
			double commisn   = Double.parseDouble(tabledata.get("PS_"+secName+"_GC")); 
			double commRate  = Double.parseDouble(tabledata.get("PS_"+secName+"_CR"));
			double netPrem 	 = Double.parseDouble(tabledata.get("PS_"+secName+"_NP"));
			double grossIPT  = Double.parseDouble(tabledata.get("PS_"+secName+"_GT"));
			double netIPT    = Double.parseDouble(tabledata.get("PS_"+secName+"_NPIPT"));
			
			
			if(secName.contains("Frozen")){
				cSection = "FrozenFoods";
			}else if(secName.contains("Licence")){
				cSection = "LossOfLicence";
			}else if(secName.contains("Total")){
				cSection = "Total";
			}else{
				cSection = secName.replaceAll(" ", "");
			}
			
			double IPT =  Double.parseDouble(TestUtil.getStringfromMap("PS_"+cSection+"_IPT",CommonFunction_GUS.businessEvent));
			TestUtil.reportStatus("Calculation for cover <b> "+section+"</b> as per IPT rate :"+IPT+" %", "Info", false);
			//System.out.println("\nCalculation Cover Name :"+section);// Added to the reporting & Logfile }
			//TestUtil.reportStatus("\nCalculation for Cover : <b>"+section, "Info", true);
			double denominator = (1.00-(commRate/100));
			double calcltdComm = (netPrem/denominator)*(commRate/100);
			//System.out.println("calculated Gross Commission :"+f.format(calcltdComm));// Added to the reporting & Logfile }
			TestUtil.reportStatus("Calculated Gross Commission :<b>"+f.format(calcltdComm), "Info", false);
			double calcltdGprem = calcltdComm + netPrem;
			//System.out.println("Calculated Gross Premium :"+f.format(calcltdGprem));// Added to the reporting & Logfile }
			TestUtil.reportStatus("Calculated Gross Premium :<b>"+f.format(calcltdGprem), "Info", false);
			double calcltdGIPT = calcltdGprem *(IPT/100);
			//System.out.println("Calculated Gross IPT :"+f.format(calcltdGIPT));// Added to the reporting & Logfile }
			TestUtil.reportStatus("Calculated Gross IPT :<b>"+f.format(calcltdGIPT), "Info", false);
			double calcltdNIPT = netPrem*(IPT/100);
			//System.out.println("Calculated Net IPT :"+f.format(calcltdNIPT));// Added to the reporting & Logfile }
			TestUtil.reportStatus("Calculated Net IPT :<b>"+f.format(calcltdNIPT), "Info", false);	
			customAssert.assertTrue(compareValues(calcltdGprem,grossPrem,"Gross Premium "),"Mismatched Gross Premium Values");
			customAssert.assertTrue(compareValues(calcltdComm,commisn,"Gross Commission "),"Mismatched Gross Commission Values");
			customAssert.assertTrue(compareValues(calcltdGIPT,grossIPT,"Gross IPT value "),"Mismatched Gross IPT Values");
			customAssert.assertTrue(compareValues(calcltdNIPT,netIPT,"Net IPT Values "),"Mimatched Net IPT Values");
			return true;
		}catch(Throwable t){
			return false;
		}
		}
  
  
  public static boolean validationOnAdjustedPremium_Renewal(String sectionName,int index){
		
		try {
			
			if(Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")) > 0.0 && 
			   Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")) <= adjustedPremium &&
			   Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")) <= 100.0 &&
			   Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")) >= 0.0
					){
				return true;
			}else{
				if(!(Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")) > 0.0)){
					TestUtil.reportStatus("<p style='color:black'> Gross Premium should be <b> > 0.0 </b> .Entered Gross Premium is : <b>[  "+common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")+"  ]</b>. Skipped Tax adjustment for Data ID is : [<b>  "+common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("Automation Key")+"  </b>] AND Cover Name is :  <b>[  "+sectionName+"  ]</b> </p>", "Info", false);
				}else if(!(Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")) <= adjustedPremium)){
					TestUtil.reportStatus("<p style='color:black'> Gross Premium limit is either completed or higher than Gross Premium. Available Gross Premium is : <b>[  "+adjustedPremium+"  ]</b> And Entered Gross Premium is : <b>[  "+common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_Premium")+"  ]</b>. Skipped Tax adjustment for Data ID is : [<b>  "+common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("Automation Key")+"  </b>] AND Cover Name is :  <b>[  "+sectionName+"  ]</b> </p>", "Info", false);
				}else if(!(Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")) <= 100.0)){
					TestUtil.reportStatus("<p style='color:black'> Tax rate should be <b> <= 100.0 </b>. Entered Tax rate is : <b>[  "+common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")+"  ]</b>. Skipped Tax adjustment for Data ID is : [<b>  "+common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("Automation Key")+"  </b>] AND Cover Name is :  <b>[  "+sectionName+"  ]</b> </p>", "Info", false);
				}else if(!(Double.parseDouble(common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")) >= 0.0)){
					TestUtil.reportStatus("<p style='color:black'> Tax rate should be <b> >= 0.0 </b>.Entered Tax rate is : <b>[  "+common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("VTA_TaxRate")+"  ]</b> Skipped Tax adjustment for Data ID is : [<b>  "+common.Renewal_Structure_of_InnerPagesMaps.get("Variable Tax Adjustment").get(index).get("Automation Key")+"  </b>] AND Cover Name is :  <b>[  "+sectionName+"  ]</b> </p>", "Info", false);
				}
				
				@SuppressWarnings("static-access")
				WebElement adjustmentTax = k.getObject("insuranceTaxAddAdjustmentTable");
				customAssert.assertTrue(k.SelectBtnWebElement(adjustmentTax, "insuranceTaxAddAdjustmentSaveCancleButton", "Cancel"), "Unable to select Cancel button.");
				
				return false;
			}
			
		} catch (Throwable t) {
			
			return false;
		} 

	}
  
  
  public double calculateGeneralTS_RenewalRewind(String recipient,String account,int j, int td, String event,String code,String fileName){
		try{
				String terrorPremium = (String)common.Renewal_excel_data_map.get("PS_Terrorism_GP");
				String terrorIPT = (String)common.Renewal_excel_data_map.get("PS_Terrorism_GT");
				String cyberPremium = (String)common.Renewal_excel_data_map.get("PS_CyberandDataSecurity_GP");
				String cyberIPT = (String)common.Renewal_excel_data_map.get("PS_CyberandDataSecurity_GT");
				String LegalExpensePremium = (String)common.Renewal_excel_data_map.get("PS_LegalExpenses_GP");
				String LegalExpenseIPT = (String)common.Renewal_excel_data_map.get("PS_LegalExpenses_GT");
				String DirectorsGrossPremium = (String)common.Renewal_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String directorsIPT = (String)common.Renewal_excel_data_map.get("PS_DirectorsandOfficers_GT");
				String MarineCargoGrossPremium = (String)common.Renewal_excel_data_map.get("PS_MarineCargo_GP");
				String MarineCargoIPT = (String)common.Renewal_excel_data_map.get("PS_MarineCargo_GT");
				String EmpLbtGrossPremium = (String)common.Renewal_excel_data_map.get("PS_EmployersLiability_GP");
				String EmplbtCargoIPT = (String)common.Renewal_excel_data_map.get("PS_EmployersLiability_GT");
				String ProductLbtGrossPremium = (String)common.Renewal_excel_data_map.get("PS_ProductsLiability_GP");
				String ProductLbtIPT = (String)common.Renewal_excel_data_map.get("PS_ProductsLiability_GT");
				String PublicLbtGrossPremium = (String)common.Renewal_excel_data_map.get("PS_PublicLiability_GP");
				String PubliclbtIPT = (String)common.Renewal_excel_data_map.get("PS_PublicLiability_GT");
				String POLPremium = (String)common.Renewal_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String POLIPT = (String)common.Renewal_excel_data_map.get("PS_PropertyOwnersLiability_GT");
				String product = common.product;
				
				String totalGrossPremium = (String)common.Renewal_excel_data_map.get("PS_Total_GP");
				String totalGrossIPT = (String)common.Renewal_excel_data_map.get("PS_Total_GT");
				String part1= "//*[@id='table0']/tbody";
				
				String general= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String generalInsTax= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String generalDue= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				
				
				if(terrorPremium == null) {terrorPremium = "0.00";}
				if(terrorIPT == null) {terrorIPT = "0.00";}
				if(cyberPremium == null) {cyberPremium = "0.00";}
				if(cyberIPT == null) {cyberIPT = "0.00";}
				if(LegalExpensePremium == null) {LegalExpensePremium = "0.00";}
				if(LegalExpenseIPT == null) {LegalExpenseIPT = "0.00";}
				if(DirectorsGrossPremium == null) {DirectorsGrossPremium = "0.00";}
				if(directorsIPT == null) {directorsIPT = "0.00";}
				if(MarineCargoGrossPremium == null) {MarineCargoGrossPremium = "0.00";}
				if(MarineCargoIPT == null) {MarineCargoIPT = "0.00";}
				if(EmpLbtGrossPremium == null) {EmpLbtGrossPremium = "0.00";}
				if(EmplbtCargoIPT == null) {EmplbtCargoIPT = "0.00";}
				if(ProductLbtGrossPremium == null) {ProductLbtGrossPremium = "0.00";}
				if(ProductLbtIPT == null) {ProductLbtIPT = "0.00";}
				if(PublicLbtGrossPremium == null) {PublicLbtGrossPremium = "0.00";}
				if(PubliclbtIPT == null) {PubliclbtIPT = "0.00";}
				if(POLPremium == null) {POLPremium = "0.00";}
				if(POLIPT == null) {POLIPT = "0.00";}
							
				//if(((((String)mdata.get("CD_LossOfRentalIncome")).equals("Yes") && !((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("No"))  || ((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("Yes")))
				if((String)common.Renewal_excel_data_map.get("CD_Terrorism")== null 
					|| (((String)common.Renewal_excel_data_map.get("CD_Terrorism")).equals("No") && ((String)common.Renewal_excel_data_map.get("CD_Add_Terrorism")).equals("No")) 
					|| (((String)common.Renewal_excel_data_map.get("CD_Terrorism")).equals("Yes") && ((String)common.Renewal_excel_data_map.get("CD_Add_Terrorism")).equals("No"))
					)
					{
						terrorPremium="0.00";
						terrorIPT="0.00";
					}
				if((String)common.Renewal_excel_data_map.get("CD_CyberandDataSecurity")== null  
					|| (((String)common.Renewal_excel_data_map.get("CD_CyberandDataSecurity")).equals("No") && ((String)common.Renewal_excel_data_map.get("CD_Add_CyberandDataSecurity")).equals("No")) 
					|| (((String)common.Renewal_excel_data_map.get("CD_CyberandDataSecurity")).equals("Yes") && ((String)common.Renewal_excel_data_map.get("CD_Add_CyberandDataSecurity")).equals("No"))
					)
					{
						cyberPremium="0.00";
						cyberIPT="0.00";
					}
				if((String)common.Renewal_excel_data_map.get("CD_LegalExpenses")== null 
					|| (((String)common.Renewal_excel_data_map.get("CD_LegalExpenses")).equals("No") && ((String)common.Renewal_excel_data_map.get("CD_Add_LegalExpenses")).equals("No")) 
					|| (((String)common.Renewal_excel_data_map.get("CD_LegalExpenses")).equals("Yes") && ((String)common.Renewal_excel_data_map.get("CD_Add_LegalExpenses")).equals("No"))
					)
					{
						LegalExpensePremium="0.00";
						LegalExpenseIPT="0.00";
					}
			
				/*if((String)common.NB_excel_data_map.get("CD_LegalExpenses")== null ||((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("No"))
				{
					LegalExpensePremium="0.00";
					LegalExpenseIPT="0.00";
				}*/
				if((String)common.Renewal_excel_data_map.get("CD_DirectorsandOfficers")== null 
					|| (((String)common.Renewal_excel_data_map.get("CD_DirectorsandOfficers")).equals("No") && ((String)common.Renewal_excel_data_map.get("CD_Add_DirectorsandOfficers")).equals("No")) 
					|| (((String)common.Renewal_excel_data_map.get("CD_DirectorsandOfficers")).equals("Yes") && ((String)common.Renewal_excel_data_map.get("CD_Add_DirectorsandOfficers")).equals("No"))
					)
					{
						DirectorsGrossPremium="0.00";
						directorsIPT="0.00";
					}
				
				/*if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")== null ||((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("No"))
				{
					DirectorsGrossPremium="0.00";
					directorsIPT="0.00";
				}*/
				if( (String)common.Renewal_excel_data_map.get("CD_MarineCargo")== null 
					|| (((String)common.Renewal_excel_data_map.get("CD_MarineCargo")).equals("No") && ((String)common.Renewal_excel_data_map.get("CD_Add_MarineCargo")).equals("No")) 
					|| (((String)common.Renewal_excel_data_map.get("CD_MarineCargo")).equals("Yes") && ((String)common.Renewal_excel_data_map.get("CD_Add_MarineCargo")).equals("No"))
					 )
					{
						MarineCargoGrossPremium="0.00";
						MarineCargoIPT="0.00";
					}
				
				/*if((String)common.NB_excel_data_map.get("CD_MarineCargo")== null ||((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("No"))
				{
					MarineCargoGrossPremium="0.00";
					MarineCargoIPT="0.00";
				}*/
				if((String)common.Renewal_excel_data_map.get("CD_Liability")== null 
					|| (((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("No") && ((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("No")) 
					|| (((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("Yes") && ((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("No"))
					)
					{
					EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
					}
				
				/*if((String)common.NB_excel_data_map.get("CD_Liability")== null || ((String)common.NB_excel_data_map.get("CD_Liability")).equals("No"))
				{
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}*/
				
				if(product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("CTA")){
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}
				
				double generalPremium = Double.parseDouble(totalGrossPremium) - (Double.parseDouble(terrorPremium) 
																				+ Double.parseDouble(cyberPremium) 
																				+ Double.parseDouble(LegalExpensePremium)
																				+ Double.parseDouble(DirectorsGrossPremium)
																				+ Double.parseDouble(MarineCargoGrossPremium)
																				+ Double.parseDouble(EmpLbtGrossPremium)
																				+ Double.parseDouble(ProductLbtGrossPremium)
																				+ Double.parseDouble(PublicLbtGrossPremium)
																				+ Double.parseDouble(POLPremium));		
				double generalIPT = Double.parseDouble(totalGrossIPT) - (Double.parseDouble(terrorIPT) 
																		+ Double.parseDouble(cyberIPT) 
																		+ Double.parseDouble(LegalExpenseIPT)
																		+ Double.parseDouble(directorsIPT)
																		+ Double.parseDouble(MarineCargoIPT)
																		+ Double.parseDouble(POLIPT)
																		+ Double.parseDouble(EmplbtCargoIPT)
																		+ Double.parseDouble(ProductLbtIPT)
																		+ Double.parseDouble(PubliclbtIPT)
																		);
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066"))
				{
					
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General RAS Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT");
					double actualDue = values[0] + values[1];
					String dueAmmout =  common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(generalDue), "General RAS Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AJG Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AJG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("AIG Europe Ltd") && account.equalsIgnoreCase("AA750"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AIG Amount ");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AIG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AIG Due Amount ");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("Z906"))
				{
					String generalAJG= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();			
					double generalAJGPremium =  (generalPremium * 0.0025) 
												+ (Double.parseDouble(terrorPremium) * 0.0025) 
												+ (Double.parseDouble(DirectorsGrossPremium) * 0.0025)
												+ (Double.parseDouble(MarineCargoGrossPremium) * 0.0025)
												+ (Double.parseDouble(EmpLbtGrossPremium) * 0.0025)
												+ (Double.parseDouble(POLPremium) * 0.0025);
				
					
					String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
					compareValues(Double.parseDouble(generalammount), Double.parseDouble(generalAJG), "General AIG Amount ");
					customAssert.assertTrue(WriteDataToXl(event+"_"+code, "Transaction Summary", (String)common.Renewal_excel_data_map.get("Automation Key"), "TS_AIGAmount", generalammount,common.Renewal_excel_data_map),"Error while writing AIG Ammount data to excel .");
					
					return Double.parseDouble(generalammount);
				}
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066") &&
						(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{				
							double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
							compareValues(values[0], Double.parseDouble(general), "General RAS Amount ");
							compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT  ");
							double actualDue = values[0] + values[1];
							String dueAmmout =  common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General RAS Due Amount ");
							return Double.parseDouble(dueAmmout);
						}
						else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324") &&
								(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{
						double generalAJGPremium =  generalPremium *  0.4 * 0.62;
							double actualgeneralAJGIPT = generalIPT * 0.4;
							String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
							compareValues(Double.parseDouble(generalammount), Double.parseDouble(general), "General AJG Amount ");
							compareValues(Double.parseDouble(generalInsTax), Double.parseDouble(common.roundedOff(Double.toString(actualgeneralAJGIPT))), "General AJG IPT ");
							double actualDue = generalAJGPremium + actualgeneralAJGIPT;
							String dueAmmout = common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Ammount ");
							return Double.parseDouble(dueAmmout);
						}
				return 0.00;
		
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
}
  
  public double calculateXOETS_RenewalRewind(String recipient,String account,int j, int td){
		try{
			double terrorAmount = 0.0;
			String PropertyExcessofLossGP = (String)common.Renewal_excel_data_map.get("PS_PropertyExcessofLoss_GP");
			String cedeCommission = (String)common.Renewal_excel_data_map.get("TER_CedeComm"); 	
			String PenGrossIPT = (String)common.Renewal_excel_data_map.get("PS_Total_GT"); 
			String TerrorisamNP = common.Renewal_excel_data_map.get("PS_Terrorism_NP").toString();
			String part1= "//*[@id='table0']/tbody";
			
			String actualXOEPremium= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			String actualXOEIPT = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
			String actualXOEDue = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
			
			/*if(((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{*/	
			if((String)common.Renewal_excel_data_map.get("CD_Terrorism")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_Terrorism")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_Terrorism")).equals("No"))) || ((String)common.NB_excel_data_map.get("CD_Add_Terrorism")).equals("Yes")))
				{	
				
					if(cedeCommission.equalsIgnoreCase("No")){terrorAmount = (Double.parseDouble(TerrorisamNP) * 0.9);}
					else{terrorAmount = Double.parseDouble(TerrorisamNP);}
				}
			double GeneralAmount =  (Double.parseDouble(PropertyExcessofLossGP) * 0.64) + terrorAmount;
			compareValues(GeneralAmount, Double.parseDouble(actualXOEPremium), "XOE Amount ");
			compareValues(Double.parseDouble(PenGrossIPT), Double.parseDouble(actualXOEIPT), "XOE  IPT ");
			double XOEDue = GeneralAmount + Double.parseDouble(PenGrossIPT);
			compareValues(Double.parseDouble(actualXOEDue), Double.parseDouble(common.roundedOff(Double.toString(XOEDue))), "XOE Due Amount ");
			return Double.parseDouble(common.roundedOff(Double.toString(XOEDue)));	
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Ammout for Marine Cargo. \n", t);
			return 0;
		}
	}
  
  
  public double calculateBrokeageAmountTS_RenewalRewind(String recipient,String account,int j, int td){
		double materialDamageFP = 0.00, businessInteruptionFP=0.00, EmployersLiabiliyFP=0.00, PublicLiabilityFP=0.00, ContractorAllRisksFP=0.00;
		double ProductLiability =0.00, ComputersAndElectronicRiskFP=0.00, MoneyFP =0.00, GoodsInTansitFP=0.00,FidilityGuaranteFP=0.00;
		double LegalExpensesFP=0.00, terrorismFP=0.00, DirectorsAndOfficersFP=0.00, SpecifiedRisksFP=0.00, generalPremium;
		double MarineCargoFP=0.00, FrozenFoodFP=0.00, LossofLicenceFP=0.00, PropertyOwnersLiability=0.00, LossOfRentalIncomeFP=0.00;
		double PropertyExcessofLossFP=0.00;
		String part1= "//*[@id='table0']/tbody";
		try{
			String brokerageAccount= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			
			if((String)common.Renewal_excel_data_map.get("CD_MaterialDamage")!= null && 
				((((((String)common.Renewal_excel_data_map.get("CD_MaterialDamage")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_MaterialDamage")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_MaterialDamage")).equals("Yes")))
			{					 
				String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_MaterialDamage_GP");
				String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_MaterialDamage_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
				materialDamageFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					materialDamageFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}
			}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_MaterialDamage")!= null && ((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_MaterialDamage_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_MaterialDamage_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
				materialDamageFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					materialDamageFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_LossOfRentalIncome")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_LossOfRentalIncome")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_LossOfRentalIncome")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_LossOfRentalIncome")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_LossOfRentalIncome_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_LossOfRentalIncome_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						LossOfRentalIncomeFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						LossOfRentalIncomeFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")!= null && ((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_LossOfRentalIncome_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_LossOfRentalIncome_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossOfRentalIncomeFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossOfRentalIncomeFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_PropertyExcessofLoss")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_PropertyExcessofLoss")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_PropertyExcessofLoss")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_PropertyExcessofLoss")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_PropertyExcessofLoss_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_PropertyExcessofLoss_CR");
					if(product.equalsIgnoreCase("XOE"))
					{
						PropertyExcessofLossFP = calculateXOEGeneralPremium(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")!= null && ((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PropertyExcessofLoss_CR");
				if(product.equalsIgnoreCase("XOE"))
				{
					PropertyExcessofLossFP = calculateXOEGeneralPremium(GrossPremium,BrokerCommission);
				}
													
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_BusinessInterruption")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_BusinessInterruption")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_BusinessInterruption")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_BusinessInterruption")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_BusinessInterruption_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_BusinessInterruption_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						businessInteruptionFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						businessInteruptionFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_BusinessInterruption")!= null && ((String)common.NB_excel_data_map.get("CD_BusinessInterruption")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_BusinessInterruption_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_BusinessInterruption_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					businessInteruptionFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					businessInteruptionFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}*/
			if((String)common.Renewal_excel_data_map.get("CD_Liability")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_EmployersLiability_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_EmployersLiability_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						EmployersLiabiliyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						EmployersLiabiliyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}	
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_EmployersLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_EmployersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					EmployersLiabiliyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					EmployersLiabiliyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}*/
			if((String)common.Renewal_excel_data_map.get("CD_Liability")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_PublicLiability_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_PublicLiability_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
					{
						PublicLiabilityFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")){
						PublicLiabilityFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}	
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_PublicLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PublicLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					PublicLiabilityFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")){
					PublicLiabilityFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_ContractorsAllRisks")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_ContractorsAllRisks")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_ContractorsAllRisks")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_ContractorsAllRisks_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_ContractorsAllRisks_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						ContractorAllRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						ContractorAllRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")!= null && ((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_ContractorsAllRisks_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ContractorsAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ContractorAllRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					ContractorAllRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_SpecifiedAllRisks")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_SpecifiedAllRisks")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_SpecifiedAllRisks")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_SpecifiedAllRisks")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_SpecifiedAllRisks_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_SpecifiedAllRisks_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						SpecifiedRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						SpecifiedRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")!= null && ((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_SpecifiedAllRisks_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_SpecifiedAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					SpecifiedRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					SpecifiedRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_Liability")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_ProductsLiability_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_ProductsLiability_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
					{
						ProductLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG"))
					{
						ProductLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_ProductsLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ProductsLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					ProductLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG"))
				{
					ProductLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}*/
			
			
			if((String)common.Renewal_excel_data_map.get("CD_Liability")!= null && 
					(((((((String)common.Renewal_excel_data_map.get("CD_Liability")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_Liability")).equals("Yes")))
					&& (product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("POC")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_PropertyOwnersLiability_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_PropertyOwnersLiability_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						PropertyOwnersLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("POC")){
						PropertyOwnersLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes")
					&& (product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("POC")))
			{
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_PropertyOwnersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					PropertyOwnersLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("POC")){
					PropertyOwnersLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_ComputersandElectronicRisks")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_ComputersandElectronicRisks")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_ComputersandElectronicRisks")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_ComputersandElectronicRisks")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_ComputersandElectronicRisks_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_ComputersandElectronicRisks_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						ComputersAndElectronicRiskFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC"))
					{
						ComputersAndElectronicRiskFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}	
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")!= null && ((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_ComputersandElectronicRisks_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_ComputersandElectronicRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_Money")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_Money")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_Money")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_Money")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_Money_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_Money_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						MoneyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						MoneyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Money")!= null && ((String)common.NB_excel_data_map.get("CD_Money")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_Money_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_Money_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					MoneyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					MoneyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}*/
			if((String)common.Renewal_excel_data_map.get("CD_GoodsInTransit")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_GoodsInTransit")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_GoodsInTransit")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_GoodsInTransit")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_GoodsInTransit_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_GoodsInTransit_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						GoodsInTansitFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						GoodsInTansitFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_GoodsInTransit")!= null && ((String)common.NB_excel_data_map.get("CD_GoodsInTransit")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_GoodsInTransit_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_GoodsInTransit_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					GoodsInTansitFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					GoodsInTansitFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_FidelityGuarantee")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_FidelityGuarantee")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_FidelityGuarantee")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_FidelityGuarantee")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_FidelityGuarantee_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_FidelityGuarantee_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						FidilityGuaranteFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						FidilityGuaranteFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")!= null && ((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_FidelityGuarantee_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_FidelityGuarantee_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FidilityGuaranteFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FidilityGuaranteFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_FrozenFood")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_FrozenFood")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_FrozenFood")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_FrozenFood")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_FrozenFoods_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_FrozenFoods_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						FrozenFoodFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						FrozenFoodFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_FrozenFood")!= null && ((String)common.NB_excel_data_map.get("CD_FrozenFood")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_FrozenFoods_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_FrozenFoods_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FrozenFoodFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FrozenFoodFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_LossofLicence")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_LossofLicence")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_LossofLicence")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_LossofLicence")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_LossOfLicence_GP");
					String BrokerCommission = (String)common.Renewal_excel_data_map.get("PS_LossOfLicence_CR");
					if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
					{
						LossofLicenceFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
					}
					else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
						LossofLicenceFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
					}
				}
			
			
			/*if((String)common.NB_excel_data_map.get("CD_LossofLicence")!= null && ((String)common.NB_excel_data_map.get("CD_LossofLicence")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_LossOfLicence_GP");
				String BrokerCommission = (String)common.NB_excel_data_map.get("PS_LossOfLicence_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossofLicenceFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossofLicenceFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_LegalExpenses")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_LegalExpenses")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_LegalExpenses")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_LegalExpenses")).equals("Yes")))
				{					 
					String NetPremium = (String)common.Renewal_excel_data_map.get("PS_LegalExpenses_NP");
					String annualCarrier = (String)common.Renewal_excel_data_map.get("LE_AnnualCarrierPremium");
					LegalExpensesFP = Double.parseDouble(NetPremium) - Double.parseDouble(annualCarrier);
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_LegalExpenses")!= null && ((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("Yes"))
			{					
				String NetPremium = (String)common.NB_excel_data_map.get("PS_LegalExpenses_NP");
				String annualCarrier = (String)common.NB_excel_data_map.get("LE_AnnualCarrierPremium");
				LegalExpensesFP = Double.parseDouble(NetPremium) - Double.parseDouble(annualCarrier);
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_Terrorism")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_Terrorism")).equals("Yes")) && !((String)common.Renewal_excel_data_map.get("CD_Add_Terrorism")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_Terrorism")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_Terrorism_GP");
					String NetPremium = (String)common.Renewal_excel_data_map.get("PS_Terrorism_NP");
					terrorismFP = (Double.parseDouble(NetPremium)*(10.00/100))	- (Double.parseDouble(GrossPremium)*(0.25/100));
					if(((String)common.Renewal_excel_data_map.get("TER_CedeComm")).equalsIgnoreCase("Yes")){
						terrorismFP= -(Double.parseDouble((String)common.Renewal_excel_data_map.get("TS_AIGAmount")));
					}
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_Terrorism")!= null && ((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_GP");
				String NetPremium = (String)common.NB_excel_data_map.get("PS_Terrorism_NP");
				terrorismFP = (Double.parseDouble(NetPremium)*(10.00/100))	- (Double.parseDouble(GrossPremium)*(0.25/100));
				if(((String)common.NB_excel_data_map.get("TER_CedeComm")).equalsIgnoreCase("Yes")){
					terrorismFP= -(Double.parseDouble((String)common.NB_excel_data_map.get("TS_AIGAmount")));
				}
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_DirectorsandOfficers")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_DirectorsandOfficers")).equals("Yes"))  && !((String)common.Renewal_excel_data_map.get("CD_Add_DirectorsandOfficers")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_DirectorsandOfficers")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_DirectorsandOfficers_GP");
					String brokerCommision = (String)common.Renewal_excel_data_map.get("PS_DirectorsandOfficers_CR");
					DirectorsAndOfficersFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")!= null && ((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String brokerCommision = (String)common.NB_excel_data_map.get("PS_DirectorsandOfficers_CR");
				DirectorsAndOfficersFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}*/
			
			if((String)common.Renewal_excel_data_map.get("CD_MarineCargo")!= null && 
					((((((String)common.Renewal_excel_data_map.get("CD_MarineCargo")).equals("Yes"))  && !((String)common.Renewal_excel_data_map.get("CD_Add_MarineCargo")).equals("No"))) || ((String)common.Renewal_excel_data_map.get("CD_Add_MarineCargo")).equals("Yes")))
				{					 
					String GrossPremium = (String)common.Renewal_excel_data_map.get("PS_MarineCargo_GP");
					String brokerCommision = (String)common.Renewal_excel_data_map.get("PS_MarineCargo_CR");
					MarineCargoFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
				}
			
			/*if((String)common.NB_excel_data_map.get("CD_MarineCargo")!= null && ((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("Yes"))
			{					
				String GrossPremium = (String)common.NB_excel_data_map.get("PS_MarineCargo_GP");
				String brokerCommision = (String)common.NB_excel_data_map.get("PS_MarineCargo_CR");
				MarineCargoFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}*/
								
			generalPremium= materialDamageFP + LossOfRentalIncomeFP +businessInteruptionFP + EmployersLiabiliyFP 
					+ PublicLiabilityFP + PropertyOwnersLiability + ContractorAllRisksFP + SpecifiedRisksFP + ProductLiability 
					+ ComputersAndElectronicRiskFP + MoneyFP + GoodsInTansitFP + FidilityGuaranteFP + LegalExpensesFP
					+terrorismFP + DirectorsAndOfficersFP + MarineCargoFP + LossofLicenceFP + FrozenFoodFP+PropertyExcessofLossFP;
				String generalammount = decim.format(generalPremium);
		//	String actualgeneralPremium = decim.format(generalPremium);
				compareValues(Double.parseDouble(generalammount), Double.parseDouble(brokerageAccount), "General Brokerage Amount ");
				return Double.parseDouble(generalammount);
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Brokerage ammout. \n", t);
			return 0;
		}
  }
   
  public static void TransactionDetailsTable_Renewal(int rcount, Hashtable<String,String> coverlist,Hashtable<String,String> cHash,String code, String event, boolean xlWrite){
		//Transaction Premium Table
			try{
				k.ImplicitWaitOff();
				List<WebElement> row = driver.findElements(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr"));
				//System.out.println(row.size());
				int rcount1 = row.size();
				String Trax_table = "html/body/div[3]/form/div/table[4]";
				String[] prem_col = {"gprem","comr","comm","nprem","gipt","nipt"};
				String testName = (String)common.Renewal_excel_data_map.get("Automation Key");
				List<WebElement> col=driver.findElements(By.xpath("html/body/div[3]/form/div/table[4]/tbody/tr[1]/td"));
				//System.out.println(col.size());
				int cCount = col.size();
				if(driver.findElement(By.xpath(Trax_table)).isDisplayed()){
					
					Hashtable<String,String> transSmryData = new Hashtable<String,String>();
					String tXpath =Trax_table+"/tbody/tr";
					String CellValue;
					//System.out.println("Transaction Premium Table exist on premium summary page");
					TestUtil.reportStatus("Transaction Details Table exist on premium summary page", "Info", true);
					//System.out.println("Covers Found -:"+ (rcount-1));
					TestUtil.reportStatus("Covers Found -:"+ (rcount1-1), "Info", false);
					
					for(int i =1;i<=rcount1;i++){
						String sectionXpath = tXpath+"["+i+"]/td[1]";
						String sec_name = driver.findElement(By.xpath(sectionXpath)).getText();
						//System.out.print(sec_name+"\t\t\t");
						if(sec_name.contains("Frozen Food")){
							sec_name = "FrozenFoods";
						}else if(sec_name.contains("Licence")){
							sec_name = "LossOfLicence";
						}else if(sec_name.contains("Total")){
							sec_name = "Total";
							sec_name = "Total";
						}else{
							sec_name = sec_name.replaceAll(" ", "");
						}
						
						TestUtil.reportStatus("---------------"+sec_name+"-----------------","Info",false);
						
						for(int j = 2; j<=cCount;j++){
							//transSmryData
							String ColumnValues = tXpath+"["+i+"]/td["+j+"]";
							CellValue = driver.findElement(By.xpath(ColumnValues)).getText().replaceAll(",","");;
							//System.out.print(CellValue +  "\t");	
							//TestUtil.reportStatus(CellValue +  "\t", "Info", false);
							if(cHash.get(prem_col[j-2]).equalsIgnoreCase("CR") && sec_name.contains("Total"))
							{
								
							}else{
							transSmryData.put("PS_"+sec_name.replaceAll(" ", "")+"_"+cHash.get(prem_col[j-2]), CellValue);}
						}
						PremiumSummarytableCalculation_Renewal(transSmryData,sec_name);
						//System.out.println("\n___");
					}
				
				// Writing Data to Excel Sheet from Map
					Set<String> pKeys=transSmryData.keySet();
				 	for(String pkey:pKeys){
				 		if(xlWrite){
				 			customAssert.assertTrue(WriteDataToXl(code+"_"+event, "Premium Summary", testName,pkey,transSmryData.get(pkey) ,common.Renewal_excel_data_map),"Error while writing Premium Summary data to excel ."+pkey) ;
				 		}else{
				 			compareValues(Double.parseDouble(transSmryData.get(pkey)), Double.parseDouble(TestUtil.getStringfromMap(transSmryData.get(pkey),"")), pkey);
				 		}
				 	}
				}
			}
			catch(Throwable t ){
				k.ImplicitWaitOn();
				System.out.println("--"); 
			}
			
			//System.out.println("Gross Premium for Material Damage Cover in Transaction Premium table is :"+transSmryData.get("MaterialDamage_gprem"));
			 TestUtil.reportStatus("Values have been stored from Transaction Details table", "info", true);
	}

/**
 * 
 * Transaction Summary for Endorsment : START
 * 
 * 
 */


public boolean transactionSummary_Endorsment(String fileName,String testName,String event,String code){
		Boolean retvalue = true;  
		try{
			customAssert.assertTrue(common.funcMenuSelection("Navigate", "Transaction Summary"), "Navigation problem to Transaction Summary page .");
			
			Assert.assertEquals(k.getText("Page_Header"),"Transaction Summary", "Not on Transaction Summary Page.");
			String part1= "//*[@id='table0']/tbody";
			String covername = null,exit = "";
			int td=0;
			WebElement table = driver.findElement(By.xpath(part1));
			List<WebElement> list = table.findElements(By.tagName("tr"));
			Map<Object,Object> data_map = null;
			
 			switch (common.currentRunningFlow) {
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
			
			outer:
			for(int i=1;i<list.size();i++){
				String val = driver.findElement(By.xpath(part1+"/tr["+i+"]/td[1]")).getText();
				double Total =0.00;
				switch (val) {
				case "Endorsement" : 
					
					TestUtil.reportStatus("Verification Started on Transaction Summary page for Amended New Business . ", "PASS", false);
	   			   
					
					for(int j=i;!exit.equalsIgnoreCase("Total");j++){
						String transactSumVal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[1]")).getText();
						exit = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[2]")).getText();
						
						if(transactSumVal.isEmpty()){
							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[6]")).getText();
							td=8;
						}else{ 
							covername= driver.findElement(By.xpath(part1+"/tr["+j+"]/td[9]")).getText();
							td=11;
						}
						if(covername.equalsIgnoreCase("Cyber and Data Security") && ((String)data_map.get("CDS_ChangeValue")).equals("Yes")){
							double CyberAndDataSecurity= calculateCyberTS(code,testName,"New Bussiness",j,td);	
							Total = Total + CyberAndDataSecurity;
						}
						else if(covername.equalsIgnoreCase("Terrorism") && ((String)data_map.get("TER_ChangeValue")).equals("Yes")){
							double Terrorism = calculateTerrorismTS(code,testName,"New Bussiness",j,td);
							Total = Total + Terrorism;
						}
						else if(covername.equalsIgnoreCase("Marine Cargo") && ((String)data_map.get("MC_ChangeValue")).equals("Yes")){
							double MarineCargo = calculateMarineCargoTS(code,testName,"New Bussiness",j,td);
							Total = Total + MarineCargo;
						}
						else if(covername.equalsIgnoreCase("Directors and Officers") && ((String)data_map.get("DO_ChangeValue")).equals("Yes")){
							double DirectorsAndOffices = calculateDirectorsOfficersTS(code,testName,"New Bussiness",j,td);
							Total = Total + DirectorsAndOffices;
						}
						else if(covername.equalsIgnoreCase("Legal Expenses") && ((String)data_map.get("LE_ChangeValue")).equals("Yes")){
							double LegalExpense = calculateLegalExpensesTS(code,testName,"New Bussiness",j,td);	
							Total = Total + LegalExpense;
						}
						else if(covername.equalsIgnoreCase("Employers Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG")) && ((String)data_map.get("EL_ChangeValue")).equals("Yes")){
							double EmployersLiability = calculateEmployersLiabilityTS(code,testName,"New Bussiness",j,td);	
							Total = Total + EmployersLiability;
						}
						else if(covername.equalsIgnoreCase("Products Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG")) && ((String)data_map.get("PRL_ChangeValue")).equals("Yes")){
							double EmployersLiability = calculateProductsLiabilityTS(code,testName,"New Bussiness",j,td);	
							Total = Total + EmployersLiability;
						}
						else if(covername.equalsIgnoreCase("Public Liability") && (product.equalsIgnoreCase("POC") || product.equalsIgnoreCase("CCG")) && ((String)data_map.get("PRL_ChangeValue")).equals("Yes")){
							double EmployersLiability = calculatePublicLiabilityTS(code,testName,"New Bussiness",j,td);	
							Total = Total + EmployersLiability;
						}
						else if(covername.isEmpty()){
							double general = calculateOtherTS_Endorsment(testName,code,j,td,event,val);	
							Total = Total + general;
						}
						
						if(exit.equalsIgnoreCase("Total")){
	   						i=j;
	   						String actualTotal = driver.findElement(By.xpath(part1+"/tr["+j+"]/td[4]")).getText();  
	   						compareValues(Double.parseDouble(actualTotal), Double.parseDouble(common.roundedOff(Double.toString(Total))), "Transaction Summary Total");
	   						customAssert.assertTrue(WriteDataToXl(event+"_"+code, "Transaction Summary", (String)data_map.get("Automation Key"), "TS_TransactionSummaryTotal", actualTotal,data_map),"Error while writing Transaction Summary data to excel .");

	   						break outer;
						
						}

					}
						
					
					break;
					
				default :
						//System.out.println("In Default");
				}

			}
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Transaction Summary \n", t);
			return false;
		}
		
		TestUtil.reportStatus("Verification Completed successful on Transaction Summary page . ", "Info", false);
		
		return retvalue;

	}

public double calculateOtherTS_Endorsment(String testName,String code ,int j,int td,String event,String type){
		String part1= "//*[@id='table0']/tbody";
		double BrokerageAmount = 0.00;
		
	try{
			String recipient = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td-5)+"]")).getText();
			String account = driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td-4)+"]")).getText();
			if(account.equalsIgnoreCase("R066")||account.equalsIgnoreCase("A324")||account.equalsIgnoreCase("AA750")||account.equalsIgnoreCase("Z906")){
				
				double generalAmount = 0.0;
				switch (type) {
				case "Endorsement":
					generalAmount = calculateGeneralTS_Endorsement(recipient,account,j,td,event,code,testName);
					break;
				}
				return generalAmount;
			}	
			else if(recipient.equalsIgnoreCase("Brokerage Account") && account.equalsIgnoreCase("Z001"))
			{
				if(product.equalsIgnoreCase("SPI")){
					BrokerageAmount = Math.abs(Double.parseDouble(driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+3)+"]")).getText()));
				}else{
					switch (type) {
					case "Endorsement":
						BrokerageAmount = calculateBrokeageAmountTS_Endorsement(recipient,account,j,td);
						break;
					}
				}
				
				return BrokerageAmount;
			}
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Other premiums calculation on Transaction summary page. \n", t);
			return 0;
		}
		return 0;

	}

public double calculateGeneralTS_Endorsement(String recipient,String account,int j, int td, String event,String code,String fileName){
		try{
			Map<Object,Object> data_map = null;
			
 			switch (common.currentRunningFlow) {
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
			
				String terrorPremium = (String)data_map.get("PS_Terrorism_GP");
				String terrorIPT = (String)data_map.get("PS_Terrorism_GT");
				String cyberPremium = (String)data_map.get("PS_CyberandDataSecurity_GP");
				String cyberIPT = (String)data_map.get("PS_CyberandDataSecurity_GT");
				String LegalExpensePremium = (String)data_map.get("PS_LegalExpenses_GP");
				String LegalExpenseIPT = (String)data_map.get("PS_LegalExpenses_GT");
				String DirectorsGrossPremium = (String)data_map.get("PS_DirectorsandOfficers_GP");
				String directorsIPT = (String)data_map.get("PS_DirectorsandOfficers_GT");
				String MarineCargoGrossPremium = (String)data_map.get("PS_MarineCargo_GP");
				String MarineCargoIPT = (String)data_map.get("PS_MarineCargo_GT");
				String EmpLbtGrossPremium = (String)data_map.get("PS_EmployersLiability_GP");
				String EmplbtCargoIPT = (String)data_map.get("PS_EmployersLiability_GT");
				String ProductLbtGrossPremium = (String)data_map.get("PS_ProductsLiability_GP");
				String ProductLbtIPT = (String)data_map.get("PS_ProductsLiability_GT");
				String PublicLbtGrossPremium = (String)data_map.get("PS_PublicLiability_GP");
				String PubliclbtIPT = (String)data_map.get("PS_PublicLiability_GT");
				/*String POLPremium = (String)data_map.get("PS_PropertyOwnersLiability_GP");
				String POLIPT = (String)data_map.get("PS_PropertyOwnersLiability_GT");
				*/String product = common.product;
				
				String totalGrossPremium = (String)data_map.get("PS_Total_GP");
				String totalGrossIPT = (String)data_map.get("PS_Total_GT");
				String part1= "//*[@id='table0']/tbody";
				
				String general= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String generalInsTax= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String generalDue= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				
				
				if(terrorPremium == null || ((String)data_map.get("TER_ChangeValue")).equals("No")) {terrorPremium = "0.00";}
				if(terrorIPT == null || ((String)data_map.get("TER_ChangeValue")).equals("No")) {terrorIPT = "0.00";}
				if(cyberPremium == null || ((String)data_map.get("CDS_ChangeValue")).equals("No")) {cyberPremium = "0.00";}
				if(cyberIPT == null  || ((String)data_map.get("CDS_ChangeValue")).equals("No")) {cyberIPT = "0.00";}
				if(LegalExpensePremium == null || ((String)data_map.get("LE_ChangeValue")).equals("No")){LegalExpensePremium = "0.00";}
				if(LegalExpenseIPT == null || ((String)data_map.get("LE_ChangeValue")).equals("No")) {LegalExpenseIPT = "0.00";}
				if(DirectorsGrossPremium == null || ((String)data_map.get("DO_ChangeValue")).equals("No")) {DirectorsGrossPremium = "0.00";}
				if(directorsIPT == null || ((String)data_map.get("DO_ChangeValue")).equals("No")) {directorsIPT = "0.00";}
				if(MarineCargoGrossPremium == null || ((String)data_map.get("MC_ChangeValue")).equals("No")) {MarineCargoGrossPremium = "0.00";}
				if(MarineCargoIPT == null || ((String)data_map.get("MC_ChangeValue")).equals("No")) {MarineCargoIPT = "0.00";}
				if(EmpLbtGrossPremium == null || ((String)data_map.get("EL_ChangeValue")).equals("No")) {EmpLbtGrossPremium = "0.00";}
				if(EmplbtCargoIPT == null || ((String)data_map.get("EL_ChangeValue")).equals("No")) {EmplbtCargoIPT = "0.00";}
				if(ProductLbtGrossPremium == null || ((String)data_map.get("PRL_ChangeValue")).equals("No")) {ProductLbtGrossPremium = "0.00";}
				if(ProductLbtIPT == null || ((String)data_map.get("PRL_ChangeValue")).equals("No")) {ProductLbtIPT = "0.00";}
				if(PublicLbtGrossPremium == null || ((String)data_map.get("PL_ChangeValue")).equals("No")) {PublicLbtGrossPremium = "0.00";}
				if(PubliclbtIPT == null || ((String)data_map.get("PL_ChangeValue")).equals("No")) {PubliclbtIPT = "0.00";}
				/*if(POLPremium == null || ((String)data_map.get("POL_ChangeValue")).equals("No")) {POLPremium = "0.00";}
				if(POLIPT == null || ((String)data_map.get("POL_ChangeValue")).equals("No")) {POLIPT = "0.00";}
				*/			
				//if(((((String)mdata.get("CD_LossOfRentalIncome")).equals("Yes") && !((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("No"))  || ((String)mdata.get("CD_Add_LossOfRentalIncome")).equals("Yes")))
				
				if(((String)data_map.get("CD_Terrorism")).equals("No") ||(String)data_map.get("CD_Terrorism")== null )
				{
					terrorPremium="0.00";
					terrorIPT="0.00";
				}
				if((String)data_map.get("CD_CyberandDataSecurity")== null || ((String)data_map.get("CD_CyberandDataSecurity")).equals("No"))
				{
					cyberPremium="0.00";
					cyberIPT="0.00";
				}
				if((String)data_map.get("CD_LegalExpenses")== null ||((String)data_map.get("CD_LegalExpenses")).equals("No"))
				{
					LegalExpensePremium="0.00";
					LegalExpenseIPT="0.00";
				}
				if((String)data_map.get("CD_DirectorsandOfficers")== null ||((String)data_map.get("CD_DirectorsandOfficers")).equals("No"))
				{
					DirectorsGrossPremium="0.00";
					directorsIPT="0.00";
				}
				if((String)data_map.get("CD_MarineCargo")== null ||((String)data_map.get("CD_MarineCargo")).equals("No"))
				{
					MarineCargoGrossPremium="0.00";
					MarineCargoIPT="0.00";
				}
				if((String)data_map.get("CD_Liability")== null || ((String)data_map.get("CD_Liability")).equals("No"))
				{
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 /*POLPremium="0.00";
					 POLIPT="0.00";*/
				}
				if(product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("CTA")){
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					/* POLPremium="0.00";
					 POLIPT="0.00";*/
				}
				
				double generalPremium = Double.parseDouble(totalGrossPremium) - (Double.parseDouble(terrorPremium) 
																				+ Double.parseDouble(cyberPremium) 
																				+ Double.parseDouble(LegalExpensePremium)
																				+ Double.parseDouble(DirectorsGrossPremium)
																				+ Double.parseDouble(MarineCargoGrossPremium)
																				+ Double.parseDouble(EmpLbtGrossPremium)
																				+ Double.parseDouble(ProductLbtGrossPremium)
																				+ Double.parseDouble(PublicLbtGrossPremium)
																				/*+ Double.parseDouble(POLPremium)*/);		
				double generalIPT = Double.parseDouble(totalGrossIPT) - (Double.parseDouble(terrorIPT) 
																		+ Double.parseDouble(cyberIPT) 
																		+ Double.parseDouble(LegalExpenseIPT)
																		+ Double.parseDouble(directorsIPT)
																		+ Double.parseDouble(MarineCargoIPT)
																		/*+ Double.parseDouble(POLIPT)*/
																		+ Double.parseDouble(EmplbtCargoIPT)
																		+ Double.parseDouble(ProductLbtIPT)
																		+ Double.parseDouble(PubliclbtIPT)
																		);
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066"))
				{
					
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General RAS Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT");
					double actualDue = values[0] + values[1];
					String dueAmmout =  common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(generalDue), "General RAS Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AJG Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AJG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("AIG Europe Ltd") && account.equalsIgnoreCase("AA750"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AIG Amount ");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AIG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AIG Due Amount ");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("Z906"))
				{
					String generalAJG= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();			
					double generalAJGPremium =  (generalPremium * 0.0025) 
												+ (Double.parseDouble(terrorPremium) * 0.0025) 
												+ (Double.parseDouble(DirectorsGrossPremium) * 0.0025)
												+ (Double.parseDouble(MarineCargoGrossPremium) * 0.0025)
												+ (Double.parseDouble(EmpLbtGrossPremium) * 0.0025)
												/*+ (Double.parseDouble(POLPremium) * 0.0025)*/;
				
					
					String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
					compareValues(Double.parseDouble(generalammount), Double.parseDouble(generalAJG), "General AIG Amount ");
					customAssert.assertTrue(WriteDataToXl(event+"_"+code, "Transaction Summary", (String)data_map.get("Automation Key"), "TS_AIGAmount", generalammount,data_map),"Error while writing AIG Ammount data to excel .");
					
					return Double.parseDouble(generalammount);
				}
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066") &&
						(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{				
							double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
							compareValues(values[0], Double.parseDouble(general), "General RAS Amount ");
							compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT  ");
							double actualDue = values[0] + values[1];
							String dueAmmout =  common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General RAS Due Amount ");
							return Double.parseDouble(dueAmmout);
						}
						else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324") &&
								(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{
						double generalAJGPremium =  generalPremium *  0.4 * 0.62;
							double actualgeneralAJGIPT = generalIPT * 0.4;
							String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
							compareValues(Double.parseDouble(generalammount), Double.parseDouble(general), "General AJG Amount ");
							compareValues(Double.parseDouble(generalInsTax), Double.parseDouble(common.roundedOff(Double.toString(actualgeneralAJGIPT))), "General AJG IPT ");
							double actualDue = generalAJGPremium + actualgeneralAJGIPT;
							String dueAmmout = common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Ammount ");
							return Double.parseDouble(dueAmmout);
						}
				return 0.00;
		
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
}


public double calculateBrokeageAmountTS_Endorsement(String recipient,String account,int j, int td){
		double materialDamageFP = 0.00, businessInteruptionFP=0.00, EmployersLiabiliyFP=0.00, PublicLiabilityFP=0.00, ContractorAllRisksFP=0.00;
		double ProductLiability =0.00, ComputersAndElectronicRiskFP=0.00, MoneyFP =0.00, GoodsInTansitFP=0.00,FidilityGuaranteFP=0.00;
		double LegalExpensesFP=0.00, terrorismFP=0.00, DirectorsAndOfficersFP=0.00, SpecifiedRisksFP=0.00, generalPremium;
		double MarineCargoFP=0.00, FrozenFoodFP=0.00, LossofLicenceFP=0.00, PropertyOwnersLiability=0.00, LossOfRentalIncomeFP=0.00;
		double PropertyExcessofLossFP=0.00;
		String part1= "//*[@id='table0']/tbody";
		
		Map<Object,Object> data_map = null;
		
			switch (common.currentRunningFlow) {
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
		
		try{
			String brokerageAccount= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			
			if((String)data_map.get("CD_MaterialDamage")!= null && ((String)data_map.get("CD_MaterialDamage")).equals("Yes") && ((String)data_map.get("MD_ChangeValue")).equals("Yes"))
			{					 
				String GrossPremium = (String)data_map.get("PS_MaterialDamage_GP");
				String BrokerCommission = (String)data_map.get("PS_MaterialDamage_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
				materialDamageFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					materialDamageFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}
			}
			/*if((String)data_map.get("CD_LossOfRentalIncome")!= null && ((String)data_map.get("CD_LossOfRentalIncome")).equals("Yes") && ((String)data_map.get("CD_Liability")).equals("Yes"))
			{					 
				String GrossPremium = (String)data_map.get("PS_LossOfRentalIncome_GP");
				String BrokerCommission = (String)data_map.get("PS_LossOfRentalIncome_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossOfRentalIncomeFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossOfRentalIncomeFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}*/
			/*if((String)data_map.get("CD_PropertyExcessofLoss")!= null && ((String)data_map.get("CD_PropertyExcessofLoss")).equals("Yes") && ((String)data_map.get("CD_Liability")).equals("Yes"))
			{					 
				String GrossPremium = (String)data_map.get("PS_PropertyExcessofLoss_GP");
				String BrokerCommission = (String)data_map.get("PS_PropertyExcessofLoss_CR");
				if(product.equalsIgnoreCase("XOE"))
				{
					PropertyExcessofLossFP = calculateXOEGeneralPremium(GrossPremium,BrokerCommission);
				}
													
			}*/
			if((String)data_map.get("CD_BusinessInterruption")!= null && ((String)data_map.get("CD_BusinessInterruption")).equals("Yes") && ((String)data_map.get("BI_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_BusinessInterruption_GP");
				String BrokerCommission = (String)data_map.get("PS_BusinessInterruption_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					businessInteruptionFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					businessInteruptionFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)data_map.get("CD_Liability")!= null && ((String)data_map.get("CD_Liability")).equals("Yes") && ((String)data_map.get("EL_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_EmployersLiability_GP");
				String BrokerCommission = (String)data_map.get("PS_EmployersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					EmployersLiabiliyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					EmployersLiabiliyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)data_map.get("CD_Liability")!= null && ((String)data_map.get("CD_Liability")).equals("Yes") && ((String)data_map.get("PL_ChangeValue")).equals("Yes"))
			{
				String GrossPremium = (String)data_map.get("PS_PublicLiability_GP");
				String BrokerCommission = (String)data_map.get("PS_PublicLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					PublicLiabilityFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")){
					PublicLiabilityFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}
			if((String)data_map.get("CD_ContractorsAllRisks")!= null && ((String)data_map.get("CD_ContractorsAllRisks")).equals("Yes") && ((String)data_map.get("CAR_ChangeValue")).equals("Yes"))
			{
				String GrossPremium = (String)data_map.get("PS_ContractorsAllRisks_GP");
				String BrokerCommission = (String)data_map.get("PS_ContractorsAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ContractorAllRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					ContractorAllRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)data_map.get("CD_SpecifiedAllRisks")!= null && ((String)data_map.get("CD_SpecifiedAllRisks")).equals("Yes") && ((String)data_map.get("SAR_ChangeValue")).equals("Yes"))
			{
				String GrossPremium = (String)data_map.get("PS_SpecifiedAllRisks_GP");
				String BrokerCommission = (String)data_map.get("PS_SpecifiedAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					SpecifiedRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					SpecifiedRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)data_map.get("CD_Liability")!= null && ((String)data_map.get("CD_Liability")).equals("Yes") && ((String)data_map.get("PRL_ChangeValue")).equals("Yes"))
			{
				String GrossPremium = (String)data_map.get("PS_ProductsLiability_GP");
				String BrokerCommission = (String)data_map.get("PS_ProductsLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					ProductLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG"))
				{
					ProductLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}
			/*if((String)data_map.get("CD_Liability")!= null && ((String)data_map.get("CD_Liability")).equals("Yes")
					&& (product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("POC"))  && ((String)data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)data_map.get("PS_PropertyOwnersLiability_GP");
				String BrokerCommission = (String)data_map.get("PS_PropertyOwnersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					PropertyOwnersLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("POC")){
					PropertyOwnersLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}*/
			if((String)data_map.get("CD_ComputersandElectronicRisks")!= null && ((String)data_map.get("CD_ComputersandElectronicRisks")).equals("Yes") && ((String)data_map.get("CER_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_ComputersandElectronicRisks_GP");
				String BrokerCommission = (String)data_map.get("PS_ComputersandElectronicRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}
			if((String)data_map.get("CD_Money")!= null && ((String)data_map.get("CD_Money")).equals("Yes") && ((String)data_map.get("M_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_Money_GP");
				String BrokerCommission = (String)data_map.get("PS_Money_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					MoneyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					MoneyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}
			if((String)data_map.get("CD_GoodsInTransit")!= null && ((String)data_map.get("CD_GoodsInTransit")).equals("Yes") && ((String)data_map.get("GIT_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_GoodsInTransit_GP");
				String BrokerCommission = (String)data_map.get("PS_GoodsInTransit_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					GoodsInTansitFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					GoodsInTansitFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)data_map.get("CD_FidelityGuarantee")!= null && ((String)data_map.get("CD_FidelityGuarantee")).equals("Yes") && ((String)data_map.get("FG_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_FidelityGuarantee_GP");
				String BrokerCommission = (String)data_map.get("PS_FidelityGuarantee_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FidilityGuaranteFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FidilityGuaranteFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)data_map.get("CD_FrozenFood")!= null && ((String)data_map.get("CD_FrozenFood")).equals("Yes") && ((String)data_map.get("FF_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_FrozenFoods_GP");
				String BrokerCommission = (String)data_map.get("PS_FrozenFoods_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FrozenFoodFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FrozenFoodFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)data_map.get("CD_LossofLicence")!= null && ((String)data_map.get("CD_LossofLicence")).equals("Yes") && ((String)data_map.get("LOL_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_LossOfLicence_GP");
				String BrokerCommission = (String)data_map.get("PS_LossOfLicence_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossofLicenceFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossofLicenceFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)data_map.get("CD_LegalExpenses")!= null && ((String)data_map.get("CD_LegalExpenses")).equals("Yes") && ((String)data_map.get("LE_ChangeValue")).equals("Yes"))
			{					
				String NetPremium = (String)data_map.get("PS_LegalExpenses_NP");
				String annualCarrier = (String)data_map.get("LE_AnnualCarrierPremium");
				LegalExpensesFP = Double.parseDouble(NetPremium) - Double.parseDouble(annualCarrier);
			}
			if((String)data_map.get("CD_Terrorism")!= null && ((String)data_map.get("CD_Terrorism")).equals("Yes") && ((String)data_map.get("TER_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_Terrorism_GP");
				String NetPremium = (String)data_map.get("PS_Terrorism_NP");
				terrorismFP = (Double.parseDouble(NetPremium)*(10.00/100))	- (Double.parseDouble(GrossPremium)*(0.25/100));
				if(((String)data_map.get("TER_CedeComm")).equalsIgnoreCase("Yes")){
					terrorismFP= -(Double.parseDouble((String)data_map.get("TS_AIGAmount")));
				}
			}
			if((String)data_map.get("CD_DirectorsandOfficers")!= null && ((String)data_map.get("CD_DirectorsandOfficers")).equals("Yes") && ((String)data_map.get("DO_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_DirectorsandOfficers_GP");
				String brokerCommision = (String)data_map.get("PS_DirectorsandOfficers_CR");
				DirectorsAndOfficersFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}
			if((String)data_map.get("CD_MarineCargo")!= null && ((String)data_map.get("CD_MarineCargo")).equals("Yes") && ((String)data_map.get("MC_ChangeValue")).equals("Yes"))
			{					
				String GrossPremium = (String)data_map.get("PS_MarineCargo_GP");
				String brokerCommision = (String)data_map.get("PS_MarineCargo_CR");
				MarineCargoFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}
								
			generalPremium= materialDamageFP + LossOfRentalIncomeFP +businessInteruptionFP + EmployersLiabiliyFP 
					+ PublicLiabilityFP + PropertyOwnersLiability + ContractorAllRisksFP + SpecifiedRisksFP + ProductLiability 
					+ ComputersAndElectronicRiskFP + MoneyFP + GoodsInTansitFP + FidilityGuaranteFP + LegalExpensesFP
					+terrorismFP + DirectorsAndOfficersFP + MarineCargoFP + LossofLicenceFP + FrozenFoodFP+PropertyExcessofLossFP;
				String generalammount = decim.format(generalPremium);
		//	String actualgeneralPremium = decim.format(generalPremium);
				compareValues(Double.parseDouble(generalammount), Double.parseDouble(brokerageAccount), "General Brokerage Amount ");
				return Double.parseDouble(generalammount);
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Brokerage ammout. \n", t);
			return 0;
		}
}


/**
 * 
 * Transation Summary for Endorsement - END
 * 
 * 
 */
 
   public boolean ReinstatePolicy(Map<Object, Object> mdata)
  	{
  		boolean retvalue = true;

		try{

  			customAssert.assertTrue(common.funcMenuSelection("Tools", "Reinstate Policy"),"Unable to Navigate to Reinstate Policy Screen from Tools Menu.");
  			TestUtil.reportStatus("Navigated to <b>Reinstate Policy</b> page", "Info", false);
  			
  			/*Assert.assertTrue(common.funcNavigateTo("Reinstate Policy"), "Unable to navigate to Reinstate Policy Page ");
  			k.waitTwoSeconds();*/
  			customAssert.assertTrue(k.getText("ReinstatePolicyHeader").equalsIgnoreCase("Reinstate Policy"), "Reinstate Policy Page is not loaded");
  			
  			customAssert.assertTrue(k.Input("CCF_rewindTxtArea","Test Automation Re-instate"), "Unable to enter text in Reason for Rewind field");
  			customAssert.assertTrue(driver.findElement(OR.getLocator("CCF_reinstatePolicy_btn")).isEnabled(), "Reinstate Policy button is not Enabled . ");
  			customAssert.assertTrue(k.Click("CCF_reinstatePolicy_btn"), "Unable to Click on Reinstate Policy Button");
//  			k.waitTwoSeconds();
  			customAssert.assertTrue(k.getText("searchPoliciesPage_Header").equalsIgnoreCase("Search Policies"), "After Clicking on Reinstate Policy - Search Policies Page is not loaded");
  			customAssert.assertTrue(k.getText("comm_SearchedPolicyStatus").equalsIgnoreCase("On Cover"), "<p style='color:red'>After Reinstate Policy - Status of Policy is not <b>On Cover</b> .</p>");

  			TestUtil.reportStatus("Reinstate Successfully for Cancelled Policy with Status as <b> On Cover </b> as Expected . ","Pass", false);
  			

		} catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
			Assert.fail("Failed in Reinstate Policy Method\n",t);
			return false;
		}
		return retvalue;
	}
   
   public double calculateBrokeageAmountTS_Cancel(String recipient,String account,int j, int td){
		double materialDamageFP = 0.00, businessInteruptionFP=0.00, EmployersLiabiliyFP=0.00, PublicLiabilityFP=0.00, ContractorAllRisksFP=0.00;
		double ProductLiability =0.00, ComputersAndElectronicRiskFP=0.00, MoneyFP =0.00, GoodsInTansitFP=0.00,FidilityGuaranteFP=0.00;
		double LegalExpensesFP=0.00, terrorismFP=0.00, DirectorsAndOfficersFP=0.00, SpecifiedRisksFP=0.00, generalPremium;
		double MarineCargoFP=0.00, FrozenFoodFP=0.00, LossofLicenceFP=0.00, PropertyOwnersLiability=0.00, LossOfRentalIncomeFP=0.00;
		double PropertyExcessofLossFP=0.00;
		String part1= "//*[@id='table0']/tbody";
		try{
			String brokerageAccount= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
			if((String)common.NB_excel_data_map.get("CD_MaterialDamage")!= null && ((String)common.NB_excel_data_map.get("CD_MaterialDamage")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_MaterialDamage_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_MaterialDamage_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
				materialDamageFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					materialDamageFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}
			}
			if((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")!= null && ((String)common.NB_excel_data_map.get("CD_LossOfRentalIncome")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_LossOfRentalIncome_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_LossOfRentalIncome_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossOfRentalIncomeFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossOfRentalIncomeFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")!= null && ((String)common.NB_excel_data_map.get("CD_PropertyExcessofLoss")).equals("Yes"))
			{					 
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_PropertyExcessofLoss_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_PropertyExcessofLoss_CR");
				if(product.equalsIgnoreCase("XOE"))
				{
					PropertyExcessofLossFP = calculateXOEGeneralPremium(GrossPremium,BrokerCommission);
				}
													
			}
			if((String)common.NB_excel_data_map.get("CD_BusinessInterruption")!= null && ((String)common.NB_excel_data_map.get("CD_BusinessInterruption")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_BusinessInterruption_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_BusinessInterruption_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					businessInteruptionFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					businessInteruptionFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_EmployersLiability_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_EmployersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					EmployersLiabiliyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					EmployersLiabiliyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_PublicLiability_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_PublicLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					PublicLiabilityFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")){
					PublicLiabilityFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}
			if((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")!= null && ((String)common.NB_excel_data_map.get("CD_ContractorsAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_ContractorsAllRisks_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_ContractorsAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ContractorAllRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					ContractorAllRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")!= null && ((String)common.NB_excel_data_map.get("CD_SpecifiedAllRisks")).equals("Yes"))
			{
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_SpecifiedAllRisks_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_SpecifiedAllRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					SpecifiedRisksFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					SpecifiedRisksFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}															
			}
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes"))
			{
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_ProductsLiability_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_ProductsLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF"))
				{
					ProductLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG"))
				{
					ProductLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}
			if((String)common.NB_excel_data_map.get("CD_Liability")!= null && ((String)common.NB_excel_data_map.get("CD_Liability")).equals("Yes")
					&& (product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("POC")))
			{
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_PropertyOwnersLiability_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					PropertyOwnersLiability = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("POC")){
					PropertyOwnersLiability = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")!= null && ((String)common.NB_excel_data_map.get("CD_ComputersandElectronicRisks")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_ComputersandElectronicRisks_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_ComputersandElectronicRisks_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC"))
				{
					ComputersAndElectronicRiskFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}												
			}
			if((String)common.NB_excel_data_map.get("CD_Money")!= null && ((String)common.NB_excel_data_map.get("CD_Money")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_Money_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_Money_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					MoneyFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					MoneyFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}										
			}
			if((String)common.NB_excel_data_map.get("CD_GoodsInTransit")!= null && ((String)common.NB_excel_data_map.get("CD_GoodsInTransit")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_GoodsInTransit_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_GoodsInTransit_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					GoodsInTansitFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					GoodsInTansitFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}									
			}
			if((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")!= null && ((String)common.NB_excel_data_map.get("CD_FidelityGuarantee")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_FidelityGuarantee_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_FidelityGuarantee_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FidilityGuaranteFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FidilityGuaranteFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.NB_excel_data_map.get("CD_FrozenFood")!= null && ((String)common.NB_excel_data_map.get("CD_FrozenFood")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_FrozenFoods_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_FrozenFoods_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					FrozenFoodFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					FrozenFoodFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.NB_excel_data_map.get("CD_LossofLicence")!= null && ((String)common.NB_excel_data_map.get("CD_LossofLicence")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_LossOfLicence_GP");
				String BrokerCommission = (String)common.CAN_excel_data_map.get("PS_LossOfLicence_CR");
				if(product.equalsIgnoreCase("CTA")||product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB"))
				{
					LossofLicenceFP = calculateGeneralPremium(GrossPremium,BrokerCommission);
				}
				else if(product.equalsIgnoreCase("CCG")||product.equalsIgnoreCase("POC")){
					LossofLicenceFP = calculateGeneralPremiumConB(GrossPremium,BrokerCommission);
				}													
			}
			if((String)common.NB_excel_data_map.get("CD_LegalExpenses")!= null && ((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("Yes"))
			{					
				String NetPremium = (String)common.CAN_excel_data_map.get("PS_LegalExpenses_NP");
				String annualCarrier = (String)common.NB_excel_data_map.get("LE_AnnualCarrierPremium");
				LegalExpensesFP = Double.parseDouble(NetPremium) - Double.parseDouble(annualCarrier);
			}
			if((String)common.NB_excel_data_map.get("CD_Terrorism")!= null && ((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_Terrorism_GP");
				String NetPremium = (String)common.CAN_excel_data_map.get("PS_Terrorism_NP");
				terrorismFP = (Double.parseDouble(NetPremium)*(10.00/100))	- (Double.parseDouble(GrossPremium)*(0.25/100));
				if(((String)common.NB_excel_data_map.get("TER_CedeComm")).equalsIgnoreCase("Yes")){
					terrorismFP= -(Double.parseDouble((String)common.NB_excel_data_map.get("TS_AIGAmount")));
				}
			}
			if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")!= null && ((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String brokerCommision = (String)common.CAN_excel_data_map.get("PS_DirectorsandOfficers_CR");
				DirectorsAndOfficersFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}
			if((String)common.NB_excel_data_map.get("CD_MarineCargo")!= null && ((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("Yes"))
			{					
				String GrossPremium = (String)common.CAN_excel_data_map.get("PS_MarineCargo_GP");
				String brokerCommision = (String)common.CAN_excel_data_map.get("PS_MarineCargo_CR");
				MarineCargoFP = Double.parseDouble(GrossPremium) * ((36 - Double.parseDouble(brokerCommision) - 0.25)/100);
			}
								
			generalPremium= materialDamageFP + LossOfRentalIncomeFP +businessInteruptionFP + EmployersLiabiliyFP 
					+ PublicLiabilityFP + PropertyOwnersLiability + ContractorAllRisksFP + SpecifiedRisksFP + ProductLiability 
					+ ComputersAndElectronicRiskFP + MoneyFP + GoodsInTansitFP + FidilityGuaranteFP + LegalExpensesFP
					+terrorismFP + DirectorsAndOfficersFP + MarineCargoFP + LossofLicenceFP + FrozenFoodFP+PropertyExcessofLossFP;
				String generalammount = decim.format(-generalPremium);
		//	String actualgeneralPremium = decim.format(generalPremium);
				compareValues(Double.parseDouble(generalammount), Double.parseDouble(brokerageAccount), "General Brokerage Amount ");
				return Double.parseDouble(generalammount);
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Brokerage ammout. \n", t);
			return 0;
		}
}
 
   public double calculateGeneralTS_Cancel(String recipient,String account,int j, int td, String event,String code,String fileName){
		try{
				String terrorPremium = (String)common.CAN_excel_data_map.get("PS_Terrorism_GP");
				String terrorIPT = (String)common.CAN_excel_data_map.get("PS_Terrorism_GT");
				String cyberPremium = (String)common.CAN_excel_data_map.get("PS_CyberandDataSecurity_GP");
				String cyberIPT = (String)common.CAN_excel_data_map.get("PS_CyberandDataSecurity_GT");
				String LegalExpensePremium = (String)common.CAN_excel_data_map.get("PS_LegalExpenses_GP");
				String LegalExpenseIPT = (String)common.CAN_excel_data_map.get("PS_LegalExpenses_GT");
				String DirectorsGrossPremium = (String)common.CAN_excel_data_map.get("PS_DirectorsandOfficers_GP");
				String directorsIPT = (String)common.CAN_excel_data_map.get("PS_DirectorsandOfficers_GT");
				String MarineCargoGrossPremium = (String)common.CAN_excel_data_map.get("PS_MarineCargo_GP");
				String MarineCargoIPT = (String)common.CAN_excel_data_map.get("PS_MarineCargo_GT");
				String EmpLbtGrossPremium = (String)common.CAN_excel_data_map.get("PS_EmployersLiability_GP");
				String EmplbtCargoIPT = (String)common.CAN_excel_data_map.get("PS_EmployersLiability_GT");
				String ProductLbtGrossPremium = (String)common.CAN_excel_data_map.get("PS_ProductsLiability_GP");
				String ProductLbtIPT = (String)common.CAN_excel_data_map.get("PS_ProductsLiability_GT");
				String PublicLbtGrossPremium = (String)common.CAN_excel_data_map.get("PS_PublicLiability_GP");
				String PubliclbtIPT = (String)common.CAN_excel_data_map.get("PS_PublicLiability_GT");
				String POLPremium = (String)common.CAN_excel_data_map.get("PS_PropertyOwnersLiability_GP");
				String POLIPT = (String)common.CAN_excel_data_map.get("PS_PropertyOwnersLiability_GT");
				String product = common.product;
				
				String totalGrossPremium = (String)common.CAN_excel_data_map.get("PS_Total_GP");
				String totalGrossIPT = (String)common.CAN_excel_data_map.get("PS_Total_GT");
				String part1= "//*[@id='table0']/tbody";
				
				String general= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();
				String generalInsTax= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+1)+"]")).getText();
				String generalDue= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+(td+2)+"]")).getText();
				
				
				if(terrorPremium == null) {terrorPremium = "0.00";}
				if(terrorIPT == null) {terrorIPT = "0.00";}
				if(cyberPremium == null) {cyberPremium = "0.00";}
				if(cyberIPT == null) {cyberIPT = "0.00";}
				if(LegalExpensePremium == null) {LegalExpensePremium = "0.00";}
				if(LegalExpenseIPT == null) {LegalExpenseIPT = "0.00";}
				if(DirectorsGrossPremium == null) {DirectorsGrossPremium = "0.00";}
				if(directorsIPT == null) {directorsIPT = "0.00";}
				if(MarineCargoGrossPremium == null) {MarineCargoGrossPremium = "0.00";}
				if(MarineCargoIPT == null) {MarineCargoIPT = "0.00";}
				if(EmpLbtGrossPremium == null) {EmpLbtGrossPremium = "0.00";}
				if(EmplbtCargoIPT == null) {EmplbtCargoIPT = "0.00";}
				if(ProductLbtGrossPremium == null) {ProductLbtGrossPremium = "0.00";}
				if(ProductLbtIPT == null) {ProductLbtIPT = "0.00";}
				if(PublicLbtGrossPremium == null) {PublicLbtGrossPremium = "0.00";}
				if(PubliclbtIPT == null) {PubliclbtIPT = "0.00";}
				if(POLPremium == null) {POLPremium = "0.00";}
				if(POLIPT == null) {POLIPT = "0.00";}
							
				
				if(((String)common.NB_excel_data_map.get("CD_Terrorism")).equals("No") ||(String)common.NB_excel_data_map.get("CD_Terrorism")== null )
				{
					terrorPremium="0.00";
					terrorIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_CyberandDataSecurity")== null || ((String)common.NB_excel_data_map.get("CD_CyberandDataSecurity")).equals("No"))
				{
					cyberPremium="0.00";
					cyberIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_LegalExpenses")== null ||((String)common.NB_excel_data_map.get("CD_LegalExpenses")).equals("No"))
				{
					LegalExpensePremium="0.00";
					LegalExpenseIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")== null ||((String)common.NB_excel_data_map.get("CD_DirectorsandOfficers")).equals("No"))
				{
					DirectorsGrossPremium="0.00";
					directorsIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_MarineCargo")== null ||((String)common.NB_excel_data_map.get("CD_MarineCargo")).equals("No"))
				{
					MarineCargoGrossPremium="0.00";
					MarineCargoIPT="0.00";
				}
				if((String)common.NB_excel_data_map.get("CD_Liability")== null || ((String)common.NB_excel_data_map.get("CD_Liability")).equals("No"))
				{
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}
				if(product.equalsIgnoreCase("CCF")||product.equalsIgnoreCase("POB")||product.equalsIgnoreCase("CTA")){
					 EmpLbtGrossPremium = "0.00";
					 EmplbtCargoIPT = "0.00";
					 ProductLbtGrossPremium = "0.00";
					 ProductLbtIPT ="0.00";
					 PublicLbtGrossPremium = "0.00";
					 PubliclbtIPT = "0.00";
					 POLPremium="0.00";
					 POLIPT="0.00";
				}
				
				double generalPremium = Double.parseDouble(totalGrossPremium) - (Double.parseDouble(terrorPremium) 
																				+ Double.parseDouble(cyberPremium) 
																				+ Double.parseDouble(LegalExpensePremium)
																				+ Double.parseDouble(DirectorsGrossPremium)
																				+ Double.parseDouble(MarineCargoGrossPremium)
																				+ Double.parseDouble(EmpLbtGrossPremium)
																				+ Double.parseDouble(ProductLbtGrossPremium)
																				+ Double.parseDouble(PublicLbtGrossPremium)
																				+ Double.parseDouble(POLPremium));		
				double generalIPT = Double.parseDouble(totalGrossIPT) - (Double.parseDouble(terrorIPT) 
																		+ Double.parseDouble(cyberIPT) 
																		+ Double.parseDouble(LegalExpenseIPT)
																		+ Double.parseDouble(directorsIPT)
																		+ Double.parseDouble(MarineCargoIPT)
																		+ Double.parseDouble(POLIPT)
																		+ Double.parseDouble(EmplbtCargoIPT)
																		+ Double.parseDouble(ProductLbtIPT)
																		+ Double.parseDouble(PubliclbtIPT)
																		);
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066"))
				{
					
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General RAS Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT");
					double actualDue = values[0] + values[1];
					String dueAmmout =  common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(generalDue), "General RAS Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AJG Amount");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AJG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Amount");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("AIG Europe Ltd") && account.equalsIgnoreCase("AA750"))
				{
					double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
					compareValues(values[0], Double.parseDouble(general), "General AIG Amount ");
					compareValues(Double.parseDouble(generalInsTax), values[1], "General AIG IPT ");
					double actualDue = values[0] + values[1];
					String dueAmmout = common.roundedOff(Double.toString(actualDue));
					compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AIG Due Amount ");
					return Double.parseDouble(dueAmmout);
				}
				else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("Z906"))
				{
					String generalAJG= driver.findElement(By.xpath(part1+"/tr["+j+"]/td["+td+"]")).getText();			
					double generalAJGPremium =  (generalPremium * 0.0025) 
												+ (Double.parseDouble(terrorPremium) * 0.0025) 
												+ (Double.parseDouble(DirectorsGrossPremium) * 0.0025)
												+ (Double.parseDouble(MarineCargoGrossPremium) * 0.0025)
												+ (Double.parseDouble(EmpLbtGrossPremium) * 0.0025)
												+ (Double.parseDouble(POLPremium) * 0.0025);
				
					
					String generalammount = "-"+common.roundedOff(Double.toString(generalAJGPremium));
					compareValues(Double.parseDouble(generalammount), Double.parseDouble(generalAJG), "General AIG Amount ");
					customAssert.assertTrue(WriteDataToXl(event+"_"+code, "Transaction Summary", (String)common.NB_excel_data_map.get("Automation Key"), "TS_AIGAmount", generalammount,common.NB_excel_data_map),"Error while writing AIG Ammount data to excel .");
					
					return Double.parseDouble(generalammount);
				}
				if(recipient.equalsIgnoreCase("Royal & Sun Alliance Insurance Plc") && account.equalsIgnoreCase("R066") && 
						(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{				
							double[] values = OtherCalculation(recipient,Double.toString(generalPremium), Double.toString(generalIPT));
							compareValues(values[0], Double.parseDouble(general), "General RAS Amount ");
							compareValues(Double.parseDouble(generalInsTax), values[1], "General RAS IPT  ");
							double actualDue = values[0] + values[1];
							String dueAmmout =  common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General RAS Due Amount ");
							return Double.parseDouble(dueAmmout);
						}
						else if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited") && account.equalsIgnoreCase("A324") &&
								(product.equalsIgnoreCase("POC")||product.equalsIgnoreCase("CCG")))
						{
						double generalAJGPremium =  generalPremium *  0.4 * 0.62;
							double actualgeneralAJGIPT = generalIPT * 0.4;
							String generalammount = common.roundedOff(Double.toString(generalAJGPremium));
							compareValues(Double.parseDouble(generalammount), Double.parseDouble(general), "General AJG Amount ");
							compareValues(Double.parseDouble(generalInsTax), Double.parseDouble(common.roundedOff(Double.toString(actualgeneralAJGIPT))), "General AJG IPT ");
							double actualDue = generalAJGPremium + actualgeneralAJGIPT;
							String dueAmmout = common.roundedOff(Double.toString(actualDue));
							compareValues(Double.parseDouble(generalDue), Double.parseDouble(dueAmmout), "General AJG Due Ammount ");
							return Double.parseDouble(dueAmmout);
						}
				return 0.00;
		
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
}
	// End of CommonFunction.java


}
