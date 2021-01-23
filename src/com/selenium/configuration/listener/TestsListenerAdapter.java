package com.selenium.configuration.listener;

import java.util.List;
import java.util.Random;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;
import com.selenium.commonfiles.base.TestBase;
import com.selenium.commonfiles.util.ErrorUtil;
import com.selenium.commonfiles.util.screenCaptureUtil;

public class TestsListenerAdapter implements IInvokedMethodListener {
	public void afterInvocation(IInvokedMethod method,ITestResult result){
		Reporter.setCurrentTestResult(result);
		if(method.isTestMethod()){
			@SuppressWarnings("unchecked")
			List<Throwable> verificationFailures = ErrorUtil.getVerificationFailures();
			if(verificationFailures.size()!=0){
				result.setStatus(ITestResult.FAILURE);
				if(result.getThrowable() != null){
					verificationFailures.add(result.getThrowable());
				}
				
				int size = verificationFailures.size();
				if (size==1){
					result.setThrowable(verificationFailures.get(0));
				} else{
					StringBuffer failureMessage = new StringBuffer("Multiple");
					for (int i = 0;i <size-1; i++){
						failureMessage.append("Failure").append(i+1).append(true);
						Throwable t = verificationFailures.get(i);
						String[] fullStackTrace = Utils.stackTrace(t, false);
						failureMessage.append(fullStackTrace).append("null");
								}
					Throwable last = verificationFailures.get(size -1);
					failureMessage.append("Failure ").append(size).append(true);
					failureMessage.append(last.toString());
					
					//set merged throwable
					Throwable merged = new Throwable(failureMessage.toString());
					merged.setStackTrace(last.getStackTrace());
					result.setThrowable(merged);
				
					
					
				}
//				String file = System.getProperty("user.dir")+TestBase.screnshtpth+(new Random().nextInt())+".png";
//				try{
//					screenCaptureUtil.takeScreenshot(TestBase.driver, file);
//				}catch(Throwable t1){
//					t1.printStackTrace();
//					ErrorUtil.addVerificationFailure(t1);
//				}
				//O strMsg = "<a target=\"_blank\" href=\"file:///"+file+"\"><img width=\"50\" heigth=\"60\" src=\"file:///"+file+"\"/>"+result.getName()+"_error link </a>";
				//result.toString(strMsg);
				
			}
		}
	}

	@Override
	public void beforeInvocation(IInvokedMethod method,ITestResult result) {

			/*try {
				Runtime.getRuntime().exec("cmd /c start Date");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/


	}

}
