package co.edu.udea.peopleManager.util;

public final class Utilities {
	
    public static boolean isNumeric(String str){
    	return str.matches("-?\\d+(\\.\\d+)?");
    }
    
    public static boolean isEmail(String str){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(str);
        return m.matches();
    }
}
