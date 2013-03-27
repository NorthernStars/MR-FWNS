package essentials.logging;

public class ExceptionUtilities {

	public static String getStackTraceAsString( Exception aException ){
		
		String vStackTrace = "\t" + aException.toString() + "\n";
		
		for( StackTraceElement vElement : aException.getStackTrace() ){
			
			vStackTrace += "\t\t" + vElement.toString() + "\n";
			
		}
		
		return vStackTrace;
		
	}
	
}
