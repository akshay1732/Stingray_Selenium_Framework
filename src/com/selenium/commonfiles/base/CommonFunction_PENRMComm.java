package com.selenium.commonfiles.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.selenium.commonfiles.util.TestUtil;

public class CommonFunction_PENRMComm extends TestBase {
	public boolean isFlatCyber = false, isFlatTerrorism= false, isFlatLegalExpense= false, isFlatPublicLiability= false, isFlatDirectors =false;
	public boolean isFlatEmployers = false, isFlatProduct =false, isFlatPublic =false, isFlatProperty = false , isFlatMarineCargo = false;
	public Map<String,Map<String,Double>> CAN_CCD_ReturnP_Values_Map = new HashMap<>();

	@SuppressWarnings("unused")
	public boolean transactionSummary(String fileName,String testName,String event,String code){
		Boolean retvalue = true;  
		//Entries for flat permium
		isFlatCyber = false;
		isFlatTerrorism= false;
		isFlatLegalExpense= false;
		isFlatDirectors =false;
		isFlatMarineCargo= false;
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
			case "Requote":
				data_map = common.Requote_excel_data_map;
				break;
			case "CAN":
				data_map = common.CAN_excel_data_map;
				break;
			case "Rewind":
				data_map = common.Rewind_excel_data_map;
				break;
			}
			customAssert.assertTrue(common.funcMenuSelection("Navigate", "Transaction Summary"), "Navigation problem to Transaction Summary page .");

			Assert.assertEquals(k.getText("Page_Header"),"Transaction Summary", "Not on Transaction Summary Page.");

			String tableXpath= "//*[@id='table0']/tbody";
			String Recipient = null, covername= null, exit = "";
			int columnNumber=0, count =0;
			String ActualDueDate , ExpecteTransactionDate , ActualTransationDate;

			WebElement table = driver.findElement(By.xpath(tableXpath));
			List<WebElement> list = table.findElements(By.tagName("tr"));

			outer:
				for(int i=1;i<list.size();i++){
					String trasacSummaryType = driver.findElement(By.xpath(tableXpath+"/tr["+i+"]/td[1]")).getText();
					double Total =0.00;
					String ExpecteDueDate = "";
					switch (trasacSummaryType) {
					case "New Business" :

						TestUtil.reportStatus("Verification Started on Transaction Summary page "+trasacSummaryType+" . ", "PASS", false);
						ActualDueDate = driver.findElement(By.xpath(tableXpath+"/tr["+i+"]/td[4]")).getText();
						ExpecteDueDate = common.getLastDayOfMonth((String)data_map.get("QuoteDate"), 1);
						// Verification of Due Date
						if(ActualDueDate.equalsIgnoreCase(ExpecteDueDate)){
							String tMsg="Actual Due Date : <b>[  "+ActualDueDate+"  ]</b> has been matched with Expected Due Date : <b>[  "+ExpecteDueDate+"  ]</b>";
							TestUtil.reportStatus(tMsg, "Pass", false);
						}
						else{
							String tMsg="Actual Due Date : <b>[  "+ActualDueDate+"  ]</b> does not matche with Expected Due Date : <b>[   "+ExpecteDueDate+"  ]</b>";
							TestUtil.reportStatus(tMsg, "Fail", false);
						}

						//Verification of Transaction Date
						ActualTransationDate = driver.findElement(By.xpath(tableXpath+"/tr["+i+"]/td[3]")).getText();
						ExpecteTransactionDate = (String)data_map.get("QuoteDate");
						if(ActualTransationDate.equalsIgnoreCase(ExpecteTransactionDate)){
							String tMsg="Actual Transaction Date : <b>[  "+ActualTransationDate+"  ]</b> has been matched with Expected Transaction Date : <b>[  "+ExpecteTransactionDate+"  ]</b>";
							TestUtil.reportStatus(tMsg, "Pass", false);
						}
						else{
							String tMsg="Actual Transaction Date : <b>[  "+ActualTransationDate+"  ]</b> does not matche with Expected Transaction Date : <b>[  "+ExpecteTransactionDate+"  ]</b>";
							TestUtil.reportStatus(tMsg, "Fail", false);
						}
						break;
					case "Endorsement":
						TestUtil.reportStatus("Verification Started on Transaction Summary page "+trasacSummaryType+" . ", "PASS", false);
						ActualDueDate = driver.findElement(By.xpath(tableXpath+"/tr["+i+"]/td[4]")).getText();

						ExpecteTransactionDate = "";

						if(((String)data_map.get("PS_PaymentWarrantyRules")).equals("Yes")){
							ExpecteDueDate = (String)data_map.get("PS_PaymentWarrantyDueDate");
						}else{

							switch(TestBase.businessEvent) {
							case "MTA":

								if(((String)common.MTA_excel_data_map.get("MTA_ExistingPolicy")).equalsIgnoreCase("Yes") && ((String)common.MTA_excel_data_map.get("MTA_Status")).equalsIgnoreCase("Endorsement Rewind")) {
									ExpecteDueDate = common.getLastDayOfMonth((String)common.NB_excel_data_map.get("EffectiveDate"), 1);
									ExpecteTransactionDate = (String)common.Rewind_excel_data_map.get("QuoteDate");
								}else {
									ExpecteDueDate = common.getLastDayOfMonth((String)common.MTA_excel_data_map.get("MTA_EffectiveDate"), 1);
									ExpecteTransactionDate = (String)data_map.get("QuoteDate");
								}
								break;
							case "Rewind":
								if(((String)common.Rewind_excel_data_map.get("Rewind_ExistingPolicy")).equalsIgnoreCase("Yes")) {
									ExpecteDueDate = common.getLastDayOfMonth((String)common.NB_excel_data_map.get("EffectiveDate"), 1);
								}
								ExpecteTransactionDate = (String)common.Rewind_excel_data_map.get("QuoteDate");
								break;
							case "Renewal":
								if(common.currentRunningFlow.equalsIgnoreCase("MTA")){
									ExpecteDueDate = common.getLastDayOfMonth((String)common.MTA_excel_data_map.get("MTA_EffectiveDate"), 1);
									ExpecteTransactionDate = (String)data_map.get("QuoteDate");
								}else{
									ExpecteDueDate = common.getLastDayOfMonth((String)data_map.get("QuoteDate"), 1);
									ExpecteTransactionDate = (String)data_map.get("QuoteDate");
								}
								
								break;
							}   						

						}   					
						if(ActualDueDate.equalsIgnoreCase(ExpecteDueDate)){
							String tMsg="Actual Due Date : <b>[  "+ActualDueDate+"  ]</b> has been matched with Expected Due Date : <b>[  "+ExpecteDueDate+"  ]</b>";
							TestUtil.reportStatus(tMsg, "Pass", false);
						}
						else{
							String tMsg="Actual Due Date : <b>[  "+ActualDueDate+"  ]</b> does not matche with Expected Due Date : <b>[   "+ExpecteDueDate+"  ]</b>";
							TestUtil.reportStatus(tMsg, "Fail", false);
						}

						ActualTransationDate = driver.findElement(By.xpath(tableXpath+"/tr["+i+"]/td[3]")).getText();

						if(ActualTransationDate.equalsIgnoreCase(ExpecteTransactionDate)){
							String tMsg="Actual Transaction Date : <b>[  "+ActualTransationDate+"  ]</b> has been matched with Expected Transaction Date : <b>[  "+ExpecteTransactionDate+"  ]</b>";
							TestUtil.reportStatus(tMsg, "Pass", false);
						}
						else{
							String tMsg="Actual Transaction Date : <b>[  "+ActualTransationDate+"  ]</b> does not matche with Expected Transaction Date : <b>[  "+ExpecteTransactionDate+"  ]</b>";
							TestUtil.reportStatus(tMsg, "Fail", false);
						}
						break;
					case "Cancel" : //MTA

						TestUtil.reportStatus("Verification Started on Transaction Summary page for Cancellation . ", "PASS", false);
						ActualDueDate = driver.findElement(By.xpath(tableXpath+"/tr["+i+"]/td[4]")).getText();

						if(((String)common.NB_excel_data_map.get("PS_PaymentWarrantyRules")).equals("Yes") && !common.currentRunningFlow.equalsIgnoreCase("MTA")){
							ExpecteDueDate = (String)common.NB_excel_data_map.get("PS_PaymentWarrantyDueDate");
						}else{
							ExpecteDueDate = common.getLastDayOfMonth((String)common.CAN_excel_data_map.get("CP_CancellationDate"), 1);
						}

						if(ActualDueDate.equalsIgnoreCase(ExpecteDueDate)){
							String tMsg="Actual Due Date : <b> [ "+ActualDueDate+" ] </b> has been matched with Expected Due Date : <b> [ "+ExpecteDueDate+" ] </b>";
							TestUtil.reportStatus(tMsg, "Pass", false);
						}
						else{
							String tMsg="Actual Due Date : <b> [ "+ActualDueDate+" ] </b> does not matche with Expected Due Date : <b> [ "+ExpecteDueDate+" ] </b>";
							TestUtil.reportStatus(tMsg, "Fail", false);
						}
						ActualTransationDate = driver.findElement(By.xpath(tableXpath+"/tr["+i+"]/td[3]")).getText();
						ExpecteTransactionDate = (String)common.NB_excel_data_map.get("QuoteDate");
						if(ActualTransationDate.equalsIgnoreCase(ExpecteTransactionDate)){
							String tMsg="Actual Transaction Date : <b> [ "+ActualTransationDate+" ] </b> has been matched with Expected Transaction Date : <b> [ "+ExpecteTransactionDate+" ] </b>";
							TestUtil.reportStatus(tMsg, "Pass", false);
						}
						else{
							String tMsg="Actual Transaction Date : <b> [ "+ActualTransationDate+" ] </b> does not matche with Expected Transaction Date : <b> [ "+ExpecteTransactionDate+" ] </b>";
							TestUtil.reportStatus(tMsg, "Fail", false);
						}
						break;
					}
					for(int rowNumber=i;!exit.equalsIgnoreCase("Total");rowNumber++){
						String transactSumVal = driver.findElement(By.xpath(tableXpath+"/tr["+rowNumber+"]/td[1]")).getText();
						exit = driver.findElement(By.xpath(tableXpath+"/tr["+rowNumber+"]/td[2]")).getText();

						if(exit.equalsIgnoreCase("Total")){
							i=rowNumber;
							String actualTotal = driver.findElement(By.xpath(tableXpath+"/tr["+rowNumber+"]/td[4]")).getText();  
							CommonFunction.compareValues(Double.parseDouble(actualTotal), Double.parseDouble(common.roundedOff(Double.toString(Total))), "Transaction Summary Total");
							break outer;
						}

						if(transactSumVal.equalsIgnoreCase("")){
							Recipient= driver.findElement(By.xpath(tableXpath+"/tr["+rowNumber+"]/td[3]")).getText();
							covername = driver.findElement(By.xpath(tableXpath+"/tr["+rowNumber+"]/td[6]")).getText();
							columnNumber=8;
						}else{
							Recipient= driver.findElement(By.xpath(tableXpath+"/tr["+rowNumber+"]/td[6]")).getText();
							covername = driver.findElement(By.xpath(tableXpath+"/tr["+rowNumber+"]/td[9]")).getText();
							columnNumber=11;
						}

						if(covername.equalsIgnoreCase("Cyber and Data Security")){
							double CyberAndDataSecurity= CyberandDataSecurityTransSummary(data_map,rowNumber,columnNumber);	
							Total = Total + CyberAndDataSecurity;
						}
						else if(covername.equalsIgnoreCase("Terrorism")){
							double Terrorism = TerrorismTransSummary(data_map,rowNumber,columnNumber);
							count++;
							if(count==3 && !TestBase.product.equals("POH") && !TestBase.product.equals("CCL")){
								isFlatTerrorism= true;
							}else if(count==3){
								isFlatTerrorism= true;
							}
							Total = Total + Terrorism;
						}
						else if(covername.equalsIgnoreCase("Marine Cargo")){
							double MarineCargo = MarineCargoTransSummary(data_map,rowNumber,columnNumber);
							Total = Total + MarineCargo;
						}
						else if(covername.equalsIgnoreCase("Directors and Officers")){
							double DirectorsAndOffices = DirectorsandOfficersTransSummary(data_map,rowNumber,columnNumber);
							Total = Total + DirectorsAndOffices;
						}
						else if(covername.equalsIgnoreCase("Legal Expenses")){
							double LegalExpense = LegalExpensesTransSummary(data_map,rowNumber,columnNumber);	
							isFlatLegalExpense= true;
							Total = Total + LegalExpense;
						}
						else if(covername.isEmpty()){
							double general = OtherCalculationTransSummary(data_map,rowNumber,columnNumber);	
							Total = Total + general;
						}				

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
	public double CyberandDataSecurityTransSummary(Map<Object, Object> map_data, int rownum, int columnum){
		try{
			double cyberPremium=0.00 , cyberIPT = 0.0;
			double[] expectdvalue =null;
			String tableXpath= "//*[@id='table0']/tbody";
			if(!isFlatCyber){
				expectdvalue = getPremiumsAndIPT("CyberandDataSecurity");
				isFlatCyber = true;
			}else{
				expectdvalue = getPremiumsAndIPT("CyberandDataSecurity_FP");
			}
			cyberPremium = expectdvalue[1];
			cyberIPT = expectdvalue[2];
			double[] actualvalues = getActualValues(tableXpath, rownum, columnum);
			CommonFunction.compareValues(cyberPremium, actualvalues[0], "Cyber and data Security Amount");
			CommonFunction.compareValues(cyberIPT, actualvalues[1], "Cyber and data Security IPT");
			double CDSDue = cyberPremium+ cyberIPT;
			CommonFunction.compareValues(CDSDue, actualvalues[2], "Cyber and data Security Due ");
			return CDSDue;

		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Cyber and Data Security ammount. \n", t);
			return 0;
		}
	}
	public double TerrorismTransSummary(Map<Object, Object> map_data, int rownum, int columnum){
		try{

			double terrorPremium=0.00, terrorIPT =0.00;
			double[] expectdvalue = null;
			String tabelXpath= "//*[@id='table0']/tbody";		
			String recipient = driver.findElement(By.xpath(tabelXpath+"/tr["+rownum+"]/td["+(columnum-5)+"]")).getText();
			double[] actualvalues = getActualValues(tabelXpath, rownum, columnum);
			if(!isFlatTerrorism){
				expectdvalue = getPremiumsAndIPT("Terrorism");
			}else{
				expectdvalue = getPremiumsAndIPT("Terrorism_FP");
			}
			terrorPremium = expectdvalue[1];
			terrorIPT = expectdvalue[2];
			if(common.currentRunningFlow.equalsIgnoreCase("CAN")){
				if(((String)common.NB_excel_data_map.get("TER_CedeComm")).equals("No")){terrorPremium = terrorPremium * 0.9;} //We take 90% of Net premium if Cede Commission is No on Terrorism page.
			}else{
				if(((String)map_data.get("TER_CedeComm")).equals("No")){terrorPremium = terrorPremium * 0.9;} //We take 90% of Net premium if Cede Commission is No on Terrorism page.
			}
			
			double[] values = TerrorismCalculation(recipient,terrorPremium, terrorIPT);
			CommonFunction.compareValues(values[0],actualvalues[0], "Terrorism "+recipient+" Amount ");
			CommonFunction.compareValues(values[1], actualvalues[1], "Terrorism "+recipient+" IPT ");
			double terrorDue=values[0] + values[1];
			CommonFunction.compareValues(terrorDue, actualvalues[2], "Terrorism "+recipient+" Due ");
			return  terrorDue;

		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			t.printStackTrace();
			Assert.fail("Failed in Calculate Terrorisam ammount.  \n", t);
			return 0;
		}

	}
	public double[] TerrorismCalculation(String recipient,double premiumAmt, double ipt){
		try{

			double[] splitandCommissionRates= getSplitandCommissionRates(recipient);
			double Premium =  premiumAmt * splitandCommissionRates[0];
			double IPT = ipt * splitandCommissionRates[0];
			return new double[] {Premium, IPT};			
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			t.printStackTrace();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Terrorisam ammount according to Split.  \n", t);
			return new double[] {0, 0};
		}
	}
	public double[] getActualValues(String tableXpath,int rowNum, int columnNum){
		try{
			double actualIPT =0;
			double actualPremium = Double.parseDouble(driver.findElement(By.xpath(tableXpath+"/tr["+rowNum+"]/td["+columnNum+"]")).getText());
			double actualDue = Double.parseDouble(driver.findElement(By.xpath(tableXpath+"/tr["+rowNum+"]/td["+(columnNum+2)+"]")).getText());
			try{
				actualIPT = Double.parseDouble(driver.findElement(By.xpath(tableXpath+"/tr["+rowNum+"]/td["+(columnNum+1)+"]")).getText());
			}
			catch(NumberFormatException npe){
				actualIPT = 0.00;
			}
			return new double[] {actualPremium, actualIPT, actualDue};	
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");   
			t.printStackTrace();
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Getting Actual value of Transaction Summary for "+rowNum+" row.  \n", t);
			return new double[] {0, 0, 0};
		}
	}
	public double[] getSplitandCommissionRates(String recipient){
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
			case "Requote":
				data_map = common.Requote_excel_data_map;
				break;
			case "CAN":
				data_map = common.CAN_excel_data_map;
				break;
			case "Rewind":
				data_map = common.Rewind_excel_data_map;
				break;
			}
			double splitRate = 0.00, commRate =0.00;
			if(recipient.contains("Covea Insurance Plc")){recipient = "Covea";}
			if(recipient.contains("Arthur J. Gallagher (UK) Ltd")){recipient = "Axis";}
			if(recipient.contains("AIG")){recipient = "AIG";}
			if(recipient.contains("Axa Insurance UK Plc")){recipient = "AXA";}
			if(recipient.contains("Arthur J Gallagher (UK) Limited")){recipient = "AJG";}
			try{
				splitRate = Double.parseDouble((String)data_map.get("TS_"+recipient+"SplitRate"))/100;	
			}catch(NullPointerException npe){
				splitRate = 0;
			}
			try{
				commRate = Double.parseDouble((String)data_map.get("TS_"+recipient+"CommRate"));
			}catch(NullPointerException npe){
				commRate = 0;
			}

			return new double[] {splitRate, commRate};
		}catch(Throwable t){
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");    
			t.printStackTrace();
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Get Split Rate.  \n", t);
			return new double[] {0, 0};
		}
	}
	public double MarineCargoTransSummary(Map<Object, Object> map_data, int rownum, int columnum){
		try{
			double MarineCargoPremium=0.00 , MarineCargoIPT = 0.0;	
			String tabelXpath= "//*[@id='table0']/tbody";
			double[] expectdvalue = getPremiumsAndIPT("MarineCargo");
			MarineCargoPremium = expectdvalue[0];
			MarineCargoIPT = expectdvalue[2];
			MarineCargoPremium = MarineCargoPremium * ((100 - Double.parseDouble((String)map_data.get("TS_BeazleyCommRate")))/100);
			double[] actualvalues = getActualValues(tabelXpath, rownum, columnum);
			CommonFunction.compareValues(MarineCargoPremium, actualvalues[0], "Marine Cargo Amount");
			CommonFunction.compareValues(MarineCargoIPT, actualvalues[1], "Marine Cargo IPT");
			double directorsDue = MarineCargoPremium+ MarineCargoIPT;
			String directorsDueamt= common.roundedOff(Double.toString(directorsDue)) ;
			CommonFunction.compareValues(Double.parseDouble(directorsDueamt), actualvalues[2], "Marine Cargo Due ");
			return directorsDue;

		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function"); 
			t.printStackTrace();
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Ammout for Marine Cargo. \n", t);
			return 0;
		}
	}

	public double DirectorsandOfficersTransSummary(Map<Object, Object> map_data, int rownum, int columnum){
		try{
			double DirectorsandOfficersPremium=0.00 , DirectorsandOfficersIPT = 0.0;	
			String tabelXpath= "//*[@id='table0']/tbody";
			
			double[] expectdvalue = getPremiumsAndIPT("DirectorsandOfficers");
			DirectorsandOfficersPremium = expectdvalue[0];
			DirectorsandOfficersIPT = expectdvalue[2];
			DirectorsandOfficersPremium = DirectorsandOfficersPremium * ((100 - Double.parseDouble((String)map_data.get("TS_AIGCommRate")))/100);
			double[] actualvalues = getActualValues(tabelXpath, rownum, columnum);
			CommonFunction.compareValues(DirectorsandOfficersPremium, actualvalues[0], " Directors and Officers Amount");
			CommonFunction.compareValues(DirectorsandOfficersIPT, actualvalues[1], " Directors and Officers IPT");
			double DirectorsandOfficersDue = DirectorsandOfficersPremium+ DirectorsandOfficersIPT;
			String directorsDueamt= common.roundedOff(Double.toString(DirectorsandOfficersDue)) ;
			CommonFunction.compareValues(Double.parseDouble(directorsDueamt), actualvalues[2], " Directors and Officers Due ");
			return DirectorsandOfficersDue;

		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function"); 
			t.printStackTrace();
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Ammout for Directors and Officers. \n", t);
			return 0;
		}
	}
	public double LegalExpensesTransSummary(Map<Object, Object> map_data, int rownum, int columnum){
		
		switch (common.currentRunningFlow) {
		case "CAN":
			map_data = common.NB_excel_data_map;
			break;
		}
		
		try{
			String tableXpath= "//*[@id='table0']/tbody";
			double LegalExpenseCarrier = 0.00, LegalExpenseIPT=0.00;

			LegalExpenseCarrier=Double.parseDouble((String)map_data.get("LE_AnnualCarrierPremium"));
			if(TestBase.businessEvent.equalsIgnoreCase("Rewind") ||common.currentRunningFlow.equals("NB") || common.currentRunningFlow.equals("Renewal") || (TestBase.businessEvent.equalsIgnoreCase("Renewal") && common.currentRunningFlow.equalsIgnoreCase("Rewind"))){
				double PolicyDuration = Double.parseDouble((String)map_data.get("PS_Duration"));
				LegalExpenseCarrier = (LegalExpenseCarrier/365)*(PolicyDuration);
				if(!((String)map_data.get("PS_Duration")).equalsIgnoreCase("365")){
					LegalExpenseIPT = common_PEN.transaction_Premium_Values.get("Legal Expenses").get("Gross IPT (GBP)");
				}else{
					LegalExpenseIPT = Double.parseDouble((String)map_data.get("PS_LegalExpenses_GT"));
				}


			}else if(common.currentRunningFlow.equalsIgnoreCase("MTA") || 
					(TestBase.businessEvent.equalsIgnoreCase("MTA") && common.currentRunningFlow.equalsIgnoreCase("Rewind"))){
				try{
					if(!isFlatLegalExpense){
						LegalExpenseIPT = common.transaction_Details_Premium_Values.get("Legal Expenses").get("Gross IPT (GBP)");
					}else{
						LegalExpenseIPT = common.transaction_Details_Premium_Values.get("Legal Expenses_FP").get("Gross IPT (GBP)");
					}
					double MTADuration = Double.parseDouble((String)common.MTA_excel_data_map.get("PS_Duration")) - Double.parseDouble((String)common.MTA_excel_data_map.get("MTA_EndorsementPeriod"));
					common.MTA_excel_data_map.put("MTA_Duration", MTADuration);
					LegalExpenseCarrier = (LegalExpenseCarrier/365)*(MTADuration);
				}catch(NullPointerException npe){
					LegalExpenseIPT= 0;
				}
			}else if(common.currentRunningFlow.equals("CAN")){
				LegalExpenseIPT = -common_PEN.CAN_CCD_ReturnP_Values_Map.get("Legal Expenses").get("Gross IPT (GBP)");
				double MTADuration = Double.parseDouble((String)common.NB_excel_data_map.get("PS_Duration")) - Double.parseDouble((String)common.CAN_excel_data_map.get("CP_AddDifference"));
				LegalExpenseCarrier = -(LegalExpenseCarrier/365)*(MTADuration);
			}
			double[] actualvalues = getActualValues(tableXpath, rownum, columnum);
			CommonFunction.compareValues(LegalExpenseCarrier, actualvalues[0], "Legal Expense Amount ");		
			CommonFunction.compareValues(LegalExpenseIPT, actualvalues[1], "Legal Expense IPT ");
			double LegalExpenseDue = LegalExpenseCarrier+ LegalExpenseIPT;	
			CommonFunction.compareValues(LegalExpenseDue, actualvalues[2], "Legal Expense Due Amount ");
			return LegalExpenseDue;	

		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			t.printStackTrace();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed inLegal Expenses Transaction Summary. \n", t);
			return 0;
		}
	}	
	public double OtherCalculationTransSummary(Map<Object, Object> map_data, int rownum, int columnum){

		String tabelXpath= "//*[@id='table0']/tbody";

		double generalAmount = 0.0;
		try{
			String recipient = driver.findElement(By.xpath(tabelXpath+"/tr["+rownum+"]/td["+(columnum-5)+"]")).getText();
			String account = driver.findElement(By.xpath(tabelXpath+"/tr["+rownum+"]/td["+(columnum-4)+"]")).getText();			

			if(account.equalsIgnoreCase("AA750")||account.equalsIgnoreCase("AA507")||account.equalsIgnoreCase("A324")||account.equalsIgnoreCase("R066") ||account.equalsIgnoreCase("Z906")||account.equalsIgnoreCase("AE049")){
				generalAmount = calculateCarrierComm(recipient,rownum,columnum);
			}else if((recipient.equalsIgnoreCase("Brokerage Account")) && account.equalsIgnoreCase("Z001")){	
				generalAmount = calculateBrokeageAmount(recipient,rownum,columnum);
			}
			return generalAmount;
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Other premiums calculation on Transaction summary page. \n", t);
			return 0;
		}

	}	
	public double calculateCarrierComm(String recipient,int rowNumber, int columnNumber){
		try{
			String tableXpath= "//*[@id='table0']/tbody";
			double expectedDue = 0.00;
			double[] actualvalues = getActualValues(tableXpath, rowNumber, columnNumber);
			double[] calculationValues = getCalculationValues(recipient);
			double generalPremium = calculationValues[0];
			double generalIPT = calculationValues[1];
			String Account = driver.findElement(By.xpath(tableXpath+"/tr["+rowNumber+"]/td["+(columnNumber-4)+"]")).getText();
			if(Account.equalsIgnoreCase("Z906")){
				double[] splitRate = getSplitandCommissionRates(recipient);
				expectedDue = generalPremium * (splitRate[1]/100);
				CommonFunction.compareValues(expectedDue, actualvalues[0], recipient+" Premium");
				CommonFunction.compareValues(expectedDue, actualvalues[2], recipient+" Due Amount");
			}else{
				double[] expectedvalues = OtherCalculation(recipient,generalPremium, generalIPT);
				CommonFunction.compareValues(expectedvalues[0], actualvalues[0], recipient+" Premium");
				CommonFunction.compareValues(expectedvalues[1], actualvalues[1], recipient+" IPT");
				expectedDue = expectedvalues[0] + expectedvalues[1];
				CommonFunction.compareValues(expectedDue ,actualvalues[2], recipient+" Due Amount");
			}
			return expectedDue;

		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");
			t.printStackTrace();
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
	}	
	public double[] getCalculationValues(String recipient){
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
			case "Requote":
				data_map = common.Requote_excel_data_map;
				break;
			case "CAN":
				data_map = common.CAN_excel_data_map;
				break;
			case "Rewind":
				data_map = common.Rewind_excel_data_map;
				break;
			}
			double terrorismPremium =0.00, terrorismIPT = 0.00;
			double CDSPremium = 0.00, CDSIPT = 0.00;
			double LegalExpensePremium = 0.00, LegalExpenseIPT = 0.00;		
			double DAOPremium =  0.00, DAOIPT =0.00;
			double MarineCargoPremium =  0.00,MarineCargoIPT =0.00;
			double totalGrossPremium =0.00, totalGrossIPT =0.00;
			if(TestBase.businessEvent.equalsIgnoreCase("Rewind") || common.currentRunningFlow.equals("NB") || common.currentRunningFlow.equals("Renewal") || (TestUtil.businessEvent.equalsIgnoreCase("Renewal") && common.currentRunningFlow.equalsIgnoreCase("Rewind"))){
				//	Iterator CoverName = common.CoversDetails_data_list.iterator();
				common.CoversDetails_data_list.get(0);
				for (String SectionCover : common.CoversDetails_data_list){
					if(SectionCover.equalsIgnoreCase("CyberandDataSecurity")){
						if(!((String)data_map.get("PS_Duration")).equalsIgnoreCase("365")) {
							CDSPremium =  common_PEN.transaction_Premium_Values.get("Cyber and Data Security").get("Gross Premium (GBP)");
							CDSIPT = common_PEN.transaction_Premium_Values.get("Cyber and Data Security").get("Gross IPT (GBP)");
						}else {
							CDSPremium =  Double.parseDouble((String)data_map.get("PS_CyberandDataSecurity_GP"));
							CDSIPT = Double.parseDouble((String)data_map.get("PS_CyberandDataSecurity_GT"));
						}
						
					}
					if(SectionCover.equalsIgnoreCase("Terrorism")){
						if(!((String)data_map.get("PS_Duration")).equalsIgnoreCase("365")) {
							terrorismPremium =  common_PEN.transaction_Premium_Values.get("Terrorism").get("Gross Premium (GBP)");
							terrorismIPT = common_PEN.transaction_Premium_Values.get("Terrorism").get("Gross IPT (GBP)");
						}else {
							terrorismPremium =  Double.parseDouble((String)data_map.get("PS_Terrorism_GP"));
							terrorismIPT = Double.parseDouble((String)data_map.get("PS_Terrorism_GT"));
						}
						
					}
					if(SectionCover.equalsIgnoreCase("LegalExpenses")){
						if(!((String)data_map.get("PS_Duration")).equalsIgnoreCase("365")) {
							LegalExpensePremium =  common_PEN.transaction_Premium_Values.get("Legal Expenses").get("Gross Premium (GBP)");
							LegalExpenseIPT = common_PEN.transaction_Premium_Values.get("Legal Expenses").get("Gross IPT (GBP)");
						}else {
							LegalExpensePremium =  Double.parseDouble((String)data_map.get("PS_LegalExpenses_GP"));
							LegalExpenseIPT = Double.parseDouble((String)data_map.get("PS_LegalExpenses_GT"));
						}
						
					}
					if(SectionCover.equalsIgnoreCase("DirectorsandOfficers")){
						if(!((String)data_map.get("PS_Duration")).equalsIgnoreCase("365")) {
							DAOPremium =  common_PEN.transaction_Premium_Values.get("Directors and Officers").get("Gross Premium (GBP)");
							DAOIPT = common_PEN.transaction_Premium_Values.get("Directors and Officers").get("Gross IPT (GBP)");
						}else {
							DAOPremium =  Double.parseDouble((String)data_map.get("PS_DirectorsandOfficers_GP"));
							DAOIPT = Double.parseDouble((String)data_map.get("PS_DirectorsandOfficers_GT"));
						}
						
					}
					if(SectionCover.equalsIgnoreCase("MarineCargo")){
						if(!((String)data_map.get("PS_Duration")).equalsIgnoreCase("365")) {
							MarineCargoPremium =  common_PEN.transaction_Premium_Values.get("Marine Cargo").get("Gross Premium (GBP)");
							MarineCargoIPT = common_PEN.transaction_Premium_Values.get("Marine Cargo").get("Gross IPT (GBP)");
						}else {
							MarineCargoPremium =  Double.parseDouble((String)data_map.get("PS_MarineCargo_GP"));
							MarineCargoIPT = Double.parseDouble((String)data_map.get("PS_MarineCargo_GT"));
						}
						
					}
				}
				if(!((String)data_map.get("PS_Duration")).equalsIgnoreCase("365")) {
					totalGrossPremium =  common_PEN.transaction_Premium_Values.get("Totals").get("Gross Premium (GBP)");
					totalGrossIPT = common_PEN.transaction_Premium_Values.get("Totals").get("Gross IPT (GBP)");
				}else {
					totalGrossPremium = Double.parseDouble((String)data_map.get("PS_Total_GP"));
					totalGrossIPT = Double.parseDouble((String)data_map.get("PS_Total_GT"));
				}
				
				
			}else if((TestBase.businessEvent.equals("MTA") && common.currentRunningFlow.equals("Rewind")) || common.currentRunningFlow.equalsIgnoreCase("MTA")){
				Iterator collectiveDataIT = common.transaction_Details_Premium_Values.entrySet().iterator();
				while(collectiveDataIT.hasNext()){
					Map.Entry collectiveDataMapValue = (Map.Entry)collectiveDataIT.next();
					String SectionCover = collectiveDataMapValue.getKey().toString();
					if(SectionCover.equalsIgnoreCase("Cyber and Data Security")){
						CDSPremium =  common.transaction_Details_Premium_Values.get(SectionCover).get("Gross Premium (GBP)");
						CDSIPT = common.transaction_Details_Premium_Values.get(SectionCover).get("Gross IPT (GBP)");
					}
					if(SectionCover.equalsIgnoreCase("Terrorism")){
						terrorismPremium =  common.transaction_Details_Premium_Values.get(SectionCover).get("Gross Premium (GBP)");
						terrorismIPT = common.transaction_Details_Premium_Values.get(SectionCover).get("Gross IPT (GBP)");
					}
					if(SectionCover.equalsIgnoreCase("Legal Expenses")){
						LegalExpensePremium =  common.transaction_Details_Premium_Values.get(SectionCover).get("Gross Premium (GBP)");
						LegalExpenseIPT = common.transaction_Details_Premium_Values.get(SectionCover).get("Gross IPT (GBP)");
					}
					if(SectionCover.equalsIgnoreCase("Directors and Officers")){
						DAOPremium =  common.transaction_Details_Premium_Values.get(SectionCover).get("Gross Premium (GBP)");
						DAOIPT = common.transaction_Details_Premium_Values.get(SectionCover).get("Gross IPT (GBP)");
					}
					if(SectionCover.equalsIgnoreCase("Marine Cargo")){
						MarineCargoPremium =  common.transaction_Details_Premium_Values.get(SectionCover).get("Gross Premium (GBP)");
						MarineCargoIPT = common.transaction_Details_Premium_Values.get(SectionCover).get("Gross IPT (GBP)");
					}
				}
				totalGrossPremium = common.transaction_Details_Premium_Values.get("Totals").get("Gross Premium (GBP)");
				totalGrossIPT = 	common.transaction_Details_Premium_Values.get("Totals").get("Gross IPT (GBP)");
			}else if(common.currentRunningFlow.equals("CAN")){
				Iterator collectiveDataIT = common_PEN.CAN_CCD_ReturnP_Values_Map.entrySet().iterator();
				while(collectiveDataIT.hasNext()){
					Map.Entry collectiveDataMapValue = (Map.Entry)collectiveDataIT.next();
					String SectionCover = collectiveDataMapValue.getKey().toString();
					if(SectionCover.equalsIgnoreCase("Cyber and Data Security")){
						CDSPremium = -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross Premium (GBP)");
						CDSIPT = -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross IPT (GBP)");
					}
					if(SectionCover.equalsIgnoreCase("Terrorism")){
						terrorismPremium =  -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross Premium (GBP)");
						terrorismIPT = -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross IPT (GBP)");
					}
					if(SectionCover.equalsIgnoreCase("Legal Expenses")){
						LegalExpensePremium =  -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross Premium (GBP)");
						LegalExpenseIPT = -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross IPT (GBP)");
					}
					if(SectionCover.equalsIgnoreCase("Directors and Officers")){
						DAOPremium =  -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross Premium (GBP)");
						DAOIPT = -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross IPT (GBP)");
					}
					if(SectionCover.equalsIgnoreCase("Marine Cargo")){
						MarineCargoPremium =  -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross Premium (GBP)");
						MarineCargoIPT = -common_PEN.CAN_CCD_ReturnP_Values_Map.get(SectionCover).get("Gross IPT (GBP)");
					}
				}
				totalGrossPremium = -common_PEN.CAN_CCD_ReturnP_Values_Map.get("Totals").get("Gross Premium (GBP)");
				totalGrossIPT = -common_PEN.CAN_CCD_ReturnP_Values_Map.get("Totals").get("Gross IPT (GBP)");
			}

			double generalPremium = (totalGrossPremium) - (terrorismPremium + CDSPremium + LegalExpensePremium	+ DAOPremium	+ MarineCargoPremium);
			double generalIPT = (totalGrossIPT) - (terrorismIPT +CDSIPT + LegalExpenseIPT + DAOIPT	+ MarineCargoIPT);
			if(recipient.equalsIgnoreCase("Arthur J Gallagher (UK) Limited")){
				generalPremium = totalGrossPremium - (CDSPremium + LegalExpensePremium);
				totalGrossIPT = (totalGrossIPT) - (CDSIPT + LegalExpenseIPT);
			}
			return new double[] {generalPremium, generalIPT};
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");   
			t.printStackTrace();
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Getting Calculation value of Transaction Summary for "+common.currentRunningFlow+" Flow.  \n", t);
			return new double[] {0, 0};
		}
	}
	public double[] OtherCalculation(String recipient,double premiumAmt, double ipt){
		try{

			double[] splitandCommissionRates= getSplitandCommissionRates(recipient);
			double commRate = (100 - splitandCommissionRates[1])/100;
			double Premium =  premiumAmt * splitandCommissionRates[0] * commRate;
			double IPT =  ipt * splitandCommissionRates[0];

			return new double[] {Premium, IPT};
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function"); 
			t.printStackTrace();
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Other ammount according "+recipient+" to Split.  \n", t);
			return new double[] {0, 0};
		}
	}
	public double calculateBrokeageAmount(String recipient,int rowNum, int columnNum){
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
			case "Requote":
				data_map = common.Requote_excel_data_map;
				break;
			case "CAN":
				data_map = common.NB_excel_data_map;
				break;
			case "Rewind":
				data_map = common.Rewind_excel_data_map;
				break;
			}
			String tableXpath= "//*[@id='table0']/tbody";
			double[] actualvalues = getActualValues(tableXpath, rowNum, columnNum);
			double[] AJGcommRate = null, calculationvalues =null;
			double penCommission=0.00, GrossPremium = 0, NetPremium = 0, BrokerCommission = 0;

			for (String SectionCover : common.CoversDetails_data_list){
				if(!SectionCover.contains("Liability")){
					calculationvalues = getPremiumsAndIPT(SectionCover);
					GrossPremium = calculationvalues[0];
					NetPremium = calculationvalues[1];
					BrokerCommission = calculationvalues[3];
				}
				if(SectionCover.equals("CyberandDataSecurity")){continue;}
				else{
					switch(SectionCover){
					case "Terrorism":
						double[] AJGSplitRate = getSplitandCommissionRates("AJG");
						penCommission = penCommission + ((NetPremium*(10.00/100)) - (GrossPremium*(AJGSplitRate[1]/100)));
						if(((String)data_map.get("TER_CedeComm")).equalsIgnoreCase("Yes")){
							penCommission= penCommission - (Double.parseDouble((String)data_map.get("TS_AIGAmount")));
						}
						break;
					case "LegalExpenses":
						String annualCarrier = "";
						if(!((String)data_map.get("PS_Duration")).equalsIgnoreCase("365")){
							annualCarrier = Double.toString((Double.parseDouble((String)data_map.get("LE_AnnualCarrierPremium"))/365) * Double.parseDouble((String)data_map.get("PS_Duration")));
						}else{
							annualCarrier = (String)data_map.get("LE_AnnualCarrierPremium");
						}
						
						double expLegexpense = Double.parseDouble(annualCarrier);
						if(common.currentRunningFlow.equalsIgnoreCase("MTA")||(TestBase.businessEvent.equalsIgnoreCase("MTA") && common.currentRunningFlow.equalsIgnoreCase("Rewind"))){
							double MTADuration = Double.parseDouble((String)data_map.get("PS_Duration")) - Double.parseDouble((String)common.MTA_excel_data_map.get("MTA_EndorsementPeriod"));
							data_map.put("MTA_Duration", MTADuration);
							penCommission = penCommission+(NetPremium - (expLegexpense/365)*(MTADuration));
						}else if(common.currentRunningFlow.equalsIgnoreCase("CAN")){
							double cancellationDuration = Double.parseDouble((String)common.NB_excel_data_map.get("PS_Duration")) - Double.parseDouble((String)common.CAN_excel_data_map.get("CP_AddDifference"));
							expLegexpense = -(expLegexpense/365)*(cancellationDuration);
							penCommission = penCommission+ (NetPremium - expLegexpense);

						}else{
							penCommission = penCommission + (NetPremium - (expLegexpense));
						}

						break;

					case "MarineCargo":
						double[] BeazleycommRate = getSplitandCommissionRates("Beazley");
						AJGcommRate = getSplitandCommissionRates("AJG");
						penCommission = penCommission + (GrossPremium * ((BeazleycommRate[1] - BrokerCommission - AJGcommRate[1])/100));
						break;
					case "Liability":
						calculationvalues = getPremiumsAndIPT("EmployersLiability");
						GrossPremium = calculationvalues[0];
						NetPremium = calculationvalues[1];
						BrokerCommission = calculationvalues[3];
						penCommission = penCommission + calculateGeneralPremium(GrossPremium,BrokerCommission);

						if(TestBase.product.equals("CCL") || TestBase.product.equals("CCK") || TestBase.product.equals("CTC")){
							calculationvalues = getPremiumsAndIPT("PublicLiability");
							GrossPremium = calculationvalues[0];
							NetPremium = calculationvalues[1];
							BrokerCommission = calculationvalues[3];
							penCommission = penCommission + calculateGeneralPremium(GrossPremium,BrokerCommission);
							calculationvalues = getPremiumsAndIPT("ProductsLiability");
							GrossPremium = calculationvalues[0];
							NetPremium = calculationvalues[1];
							BrokerCommission = calculationvalues[3];
							penCommission = penCommission + calculateGeneralPremium(GrossPremium,BrokerCommission);
						}else{
							calculationvalues = getPremiumsAndIPT("PropertyOwnersLiability");
							GrossPremium = calculationvalues[0];
							NetPremium = calculationvalues[1];
							BrokerCommission = calculationvalues[3];
							penCommission = penCommission + calculateGeneralPremium(GrossPremium,BrokerCommission);
						}
						break;
					case "DirectorsandOfficers":
						double[] AIGcommRate = getSplitandCommissionRates("AIG");
						AJGcommRate = getSplitandCommissionRates("AJG");
						penCommission = penCommission + (GrossPremium * ((AIGcommRate[1] - BrokerCommission - AJGcommRate[1])/100));
						break;
					default:
						penCommission = penCommission + calculateGeneralPremium(GrossPremium,BrokerCommission);
						break;
					}	
				}
			}
			CommonFunction.compareValues(penCommission, actualvalues[0], "Brokerage Account Premium");
			CommonFunction.compareValues(penCommission, actualvalues[2], "Brokerage Account Due");
			return penCommission;
		}
		catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");     k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate Brokerage ammout. \n", t);
			return 0;
		}
	}
	public double calculateGeneralPremium(double grossPremium, double brokerCommision){
		try{
			double[] AJGCommRate = getSplitandCommissionRates("AJG");
			double[] splitandCommRates = null;
			if(TestBase.product.equals("POG") || TestBase.product.equals("CTC") || TestBase.product.equals("CCK")){
				splitandCommRates = getSplitandCommissionRates("Covea");
				double coveaCommRate = (splitandCommRates[1] - brokerCommision - AJGCommRate[1])/100;
				double coveaPenCommission = grossPremium * splitandCommRates[0] * coveaCommRate;

				splitandCommRates = getSplitandCommissionRates("AIG");
				double AIGCommRate = (splitandCommRates[1] - brokerCommision - AJGCommRate[1])/100;
				double AIGPenCommission = grossPremium * splitandCommRates[0] * AIGCommRate;

				splitandCommRates = getSplitandCommissionRates("Axis");
				double AxisCommRate = (splitandCommRates[1] - brokerCommision - AJGCommRate[1])/100;
				double AxisPenCommission = grossPremium * splitandCommRates[0] * AxisCommRate;

				return (coveaPenCommission + AIGPenCommission + AxisPenCommission);
			}else if(TestBase.product.equals("POH") || TestBase.product.equals("CCL")){
				splitandCommRates = getSplitandCommissionRates("Axa Insurance UK Plc");
				double AxaCommRate = (splitandCommRates[1] - brokerCommision - AJGCommRate[1])/100;
				double AxaPenCommission = grossPremium * splitandCommRates[0] * AxaCommRate;
				return (AxaPenCommission);
			}
			return 0;
		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			t.printStackTrace();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");   
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Calculate General preimum for genral covers \n", t);
			return 0;
		}
	}	
	public double[] getPremiumsAndIPT(String covername){
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
			case "Requote":
				data_map = common.Requote_excel_data_map;
				break;
			case "CAN":
				data_map = common.CAN_excel_data_map;
				break;
			case "Rewind":
				data_map = common.Rewind_excel_data_map;
				break;
			}
			double GrossPremium =0.00, BrokerComm = 0.00, NetPremium = 0.00, Commission =0.00;
			if(covername.equalsIgnoreCase("FrozenFood")){covername	= "FrozenFoods";}
			if(covername.equalsIgnoreCase("LossofLicence")){covername	= "LossOfLicence";}
			switch(TestBase.businessEvent){
			case "NB":
				if(((String)data_map.get("PS_Duration")).equals("365")){
					GrossPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GP"));
					NetPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_NP"));
					Commission =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GT"));
					BrokerComm = Double.parseDouble((String)data_map.get("PS_"+covername+"_CR"));
				}else{
					covername = common_PEN.getCoverName(covername);
					GrossPremium = common_PEN.transaction_Premium_Values.get(covername).get("Gross Premium (GBP)");
					NetPremium = common_PEN.transaction_Premium_Values.get(covername).get("Net Premium (GBP)");
					Commission = common_PEN.transaction_Premium_Values.get(covername).get("Gross IPT (GBP)");
					BrokerComm = common_PEN.transaction_Premium_Values.get(covername).get("Com. Rate (%)");
				}

				break;
			case "MTA":
				double GrossFP=0, NetFP =0;
				if(common.currentRunningFlow.equals("NB")){
					if(((String)data_map.get("PS_Duration")).equals("365")){
						GrossPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GP"));
						NetPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_NP"));
						Commission =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GT"));
						BrokerComm = Double.parseDouble((String)data_map.get("PS_"+covername+"_CR"));
					}else{
						covername = common_PEN.getCoverName(covername);
						GrossPremium = common_PEN.transaction_Premium_Values.get(covername).get("Gross Premium (GBP)");
						NetPremium = common_PEN.transaction_Premium_Values.get(covername).get("Net Premium (GBP)");
						Commission = common_PEN.transaction_Premium_Values.get(covername).get("Gross IPT (GBP)");
						BrokerComm = common_PEN.transaction_Premium_Values.get(covername).get("Com. Rate (%)");
					}
				}else if(common.currentRunningFlow.equals("MTA") || common.currentRunningFlow.equals("Rewind")){
					covername = common_PEN.getCoverName(covername);
					try{
						GrossPremium =  common.transaction_Details_Premium_Values.get(covername).get("Gross Premium (GBP)");
						NetPremium =  common.transaction_Details_Premium_Values.get(covername).get("Net Premium (GBP)");
						Commission =  common.transaction_Details_Premium_Values.get(covername).get("Gross IPT (GBP)");
						BrokerComm = common.transaction_Details_Premium_Values.get(covername).get("Com. Rate (%)");
						try{
							GrossFP = common.transaction_Details_Premium_Values.get(covername+"_FP").get("Gross Premium");
							NetFP = common.transaction_Details_Premium_Values.get(covername+"_FP").get("Net Premium");
							GrossPremium = GrossPremium + GrossFP;
							NetPremium = NetPremium + NetFP;
						}catch(NullPointerException npe){
							GrossFP =0.00;
							NetFP=0;
						}
					}catch(NullPointerException npe){
						return new double[] {0, 0, 0, 0};
					}
				}
				break;
			case "CAN":
				if(common.currentRunningFlow.equals("NB")){
					GrossPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GP"));
					NetPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_NP"));
					Commission =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GT"));
					BrokerComm = Double.parseDouble((String)data_map.get("PS_"+covername+"_CR"));
				}else if(common.currentRunningFlow.equals("CAN")){
					covername = common_PEN.getCoverName(covername);
					GrossPremium = -common_PEN.CAN_CCD_ReturnP_Values_Map.get(covername).get("Gross Premium (GBP)");
					NetPremium = -common_PEN.CAN_CCD_ReturnP_Values_Map.get(covername).get("Net Premium (GBP)");
					Commission = -common_PEN.CAN_CCD_ReturnP_Values_Map.get(covername).get("Gross IPT (GBP)");
					BrokerComm = common_PEN.CAN_CCD_ReturnP_Values_Map.get(covername).get("Com. Rate (%)");
				}
				break;
			case "Rewind":
				GrossPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GP"));
				NetPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_NP"));
				Commission =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GT"));
				BrokerComm = Double.parseDouble((String)data_map.get("PS_"+covername+"_CR"));
				break;
			case "Renewal":
				if(common.currentRunningFlow.equals("Renewal") || (TestUtil.businessEvent.equalsIgnoreCase("Renewal") && common.currentRunningFlow.equalsIgnoreCase("Rewind"))){
					GrossPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GP"));
					NetPremium =  Double.parseDouble((String)data_map.get("PS_"+covername+"_NP"));
					Commission =  Double.parseDouble((String)data_map.get("PS_"+covername+"_GT"));
					BrokerComm = Double.parseDouble((String)data_map.get("PS_"+covername+"_CR"));
				}else if(common.currentRunningFlow.equals("MTA") || common.currentRunningFlow.equals("Rewind")){
					covername = common_PEN.getCoverName(covername);
					try{
						GrossPremium =  common.transaction_Details_Premium_Values.get(covername).get("Gross Premium (GBP)");
						NetPremium =  common.transaction_Details_Premium_Values.get(covername).get("Net Premium (GBP)");
						Commission =  common.transaction_Details_Premium_Values.get(covername).get("Gross IPT (GBP)");
						BrokerComm = common.transaction_Details_Premium_Values.get(covername).get("Com. Rate (%)");
					}catch(Exception e){
						GrossPremium =  0.0;
						NetPremium =  0.0;
						Commission =  0.0;
						BrokerComm = 0.0;
					}
					
				}
				break;
			}
	
			return new double[] {GrossPremium, NetPremium, Commission, BrokerComm};


		}catch(Throwable t) {
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			TestUtil.reportFunctionFailed("Failed in "+methodName+" function");   
			t.printStackTrace();
			k.reportErr("Failed in "+methodName+" function", t);
			Assert.fail("Failed in Getting Premium and IPT value of Transaction Summary for "+covername+".  \n", t);
			return new double[] {0, 0, 0, 0};
		}
	}
}
