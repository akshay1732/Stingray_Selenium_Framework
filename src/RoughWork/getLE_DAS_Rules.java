package RoughWork;

import com.selenium.commonfiles.base.*;

public class getLE_DAS_Rules extends TestBase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//CommonFunction cf = new CommonFunction();
		//TestBase base = new TestBase();
		try {
			TestBase.Initialize();
			TestBase.Initialize_Common_Functions();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String product="CTA";
		String LimitOfLiability="250,000";
		String turnover="1500";
		String wages="-150";
		String Annual_Carrier_permium_value;
		
		Annual_Carrier_permium_value = common.getLEAnnualCarrierPremium(product,LimitOfLiability, turnover, wages);
		
		System.out.println(Annual_Carrier_permium_value);

	}

}
