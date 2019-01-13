package Tools.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseHelper {
	
	public ParseHelper(){
	}
	
	public static List<String> parseFileText(StringBuffer text,String pattren) throws Exception{
		Pattern ptn = Pattern.compile(pattren);
		Matcher mch = ptn.matcher(text);
		List<String> result = new ArrayList<String>();
		while (mch.find()) {
			int start = mch.start();
			int end = mch.end();
			String numtemp = text.substring(text.indexOf("第",end)+1, text.indexOf("行:",end));
			String ptnResult = numtemp+":"+text.substring(start, end);
//			System.out.println("ptnResult:"+ptnResult);
			result.add(ptnResult);
		}
		return result;
	}
	public static boolean isMatchPtn(String str,String pattren){ 
	    Pattern pattern = Pattern.compile(pattren); 
	    return pattern.matcher(str).matches();   
	}
	public static String returnBlankIfNull (String x) {          
		String result= "";
		if(x!=null&&!x.isEmpty()){
			result=x;
		}
		return result;
	}
	public static boolean isMatchIdMember(String str){ 
		boolean result=false;
		if(isMatchPtn(str, "[A-Za-z0-9]{6,12}")&&isMatchPtn(str, ".*[A-Za-z]+.*")&&isMatchPtn(str, ".*[0-9]+.*")){
			result=true;
		}
		return result;   
	}
	public static void main(String[] args) {
//		String str ="AAAAAA";
//		System.out.println(isMatchPtn(str, "[A-Za-z0-9]{6,12}"));
		System.out.println(isMatchPtn("2010/09/8","20[0-9]{2}/[0-9]{1,2}/[0-9]{1,2}"));
		System.out.println(isMatchPtn("2010/09/08","20[0-9]{2}/[0-9]{1,2}/[0-9]{1,2}"));
//		System.out.println(isMatchPtn(str, ".*[A-Za-z]+.*"));
//		System.out.println(isMatchPtn(str, ".*[0-9]+.*"));
//		System.out.println(isMatchIdMember(str));
	}
}
