package RoughWork;

import org.apache.log4j.Logger;

public class LoggerDemo {

	static Logger log = Logger.getLogger("devpinoyLogger");
	public static void main(String[] args) {
		
		log.debug("** debug message **");
		log.info("** info message **");
		log.warn("** warn message **");
		
	}

}
