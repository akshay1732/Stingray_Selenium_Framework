package RoughWork;

import java.text.DecimalFormat;

public class Test {
	
	
	/*duration is number of days a policy will be on cover, we enter in NB
	 * initial_prem is the premium amt generated in NB or we can say previous premium
	 * new_prem is the premium amt generated in MTA or we can say revised premium
	 * days_before_endorsement is effective Date of MTA - policy start date
	 * days_remaining is policy end date- effective date of MTA, we can get it from page also*/
	
	public static double checkprem(int duration,double initial_prem, double new_prem,int days_before_endorsement, int days_remaining)
	{
		DecimalFormat tw= new DecimalFormat("#.##");
		

		//payable is amount that has to be paid by client to the insurer for enjoying particular policy perks until new endorsement, as per previous policy
		double payable=Double.valueOf(tw.format((initial_prem*days_before_endorsement/365)));
		
		
		
		
		//payable is amount that has to be paid by client to the insurer for remaining days as per the endorsement
		double payable_after_endorsement=Double.valueOf(tw.format(new_prem*days_remaining/365));
		
		
		int misc_days=duration-365;
		
		
		//misc is change extra premium if policy is not of 365 days
		double misc=0;
		
		misc=(initial_prem/365)*misc_days;
		
		
		//modi_prem is the amount generated if the policy is not of 365 days
		double modi_prem=0;
		modi_prem=initial_prem+misc;
		
		
		//change is the difference between previous policy and new policy that will be reflecting premiumSummary page
		double change=Double.valueOf(tw.format((payable+payable_after_endorsement-modi_prem)));
		
		System.out.println(change);
		return change;
	}

	public static void main(String[] args) {
		
		checkprem(349,5026.92,4056.62,29,350);
		int duration=349;
		double initial_prem=5026.92;
		double new_prem=4056.62;
		int days_before_endorsement=29;
		int days_remaining=350;
		/*double changeP = (duration,initial_prem,new_prem,days_before_endorsement,days_remaining)->{
			double payable=Double.valueOf(tw.format((initial_prem*days_before_endorsement/365)));
		
			//payable is amount that has to be paid by client to the insurer for remaining days as per the endorsement
			double payable_after_endorsement=Double.valueOf(tw.format(new_prem*days_remaining/365));
			
			
			int misc_days=duration-365;
			
			
			//misc is change extra premium if policy is not of 365 days
			double misc=0;
			
			misc=(initial_prem/365)*misc_days;
			
			
			//modi_prem is the amount generated if the policy is not of 365 days
			double modi_prem=0;
			modi_prem=initial_prem+misc;
			
			
			//change is the difference between previous policy and new policy that will be reflecting premiumSummary page
			double change=Double.valueOf(tw.format((payable+payable_after_endorsement-modi_prem)));
			return change;
		};
		System.out.println(changeP);*/

	}

}
