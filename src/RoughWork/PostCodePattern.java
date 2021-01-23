package RoughWork;
import java.util.regex.*;
public class PostCodePattern {

	public static void main(String[] args) {
		// Pattern p = Pattern.compile("\\S\\S\\d");
		  //  Matcher m = p.matcher("1 a12 234b");
		String postCode = "M16 0CD";
		 System.out.println(validatePostCode(postCode));
		    //System.out.println("\nsource: " + args[1]);;
		    //System.out.println(" index: 01234567890123456\n");
		    //System.out.println(Pattern.matches("[a-zA-Z]{2}[0-9]{2} [0-9]{1}[a-zA-Z]{2}", postCode));//true  
		  /*  System.out.println("pattern: " + m.pattern());
		    while(m.find()) {
		      System.out.println(m.start() + " " + m.group());
		    }
		    System.out.println("");*/

	}
	
	 public static boolean validatePostCode(String postCode){
  	   
  	   boolean result=false;
  	   try{
  		   //ME16 0CD
  		   result = Pattern.matches("[a-zA-Z]{2}[0-9]{2} [0-9]{1}[a-zA-Z]{2}", postCode);
  	   }catch(Throwable t){
  		   System.out.println("Error while validating Post Code"+t.getMessage());
  		   result=false;
  	   }   
  	   
  	   return result;
     }
	

}



