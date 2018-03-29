package co.edu.udea.peopleManager.util;

public final class Utilities {
	
    public static boolean isNumeric(String str)
    {
    	return str.matches("-?\\d+(\\.\\d+)?");
    }
}
