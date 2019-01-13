package Tools.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolsUtil{
	
	public static String trim(String inString){
		return inString == null ? "" : inString.trim();
	}
	
	public static boolean isEmpty(String inString){
		return trim(inString).isEmpty();
	}
	
	public static int parseInt(String inString){
		int result = 0;
		try {
			if(inString==null || inString.trim().length()==0){
				return result;
			}
			result = Integer.valueOf(inString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("字串中含有非數字");
		}
		return result;
	}
	
	public static boolean isNumeric(String inString){
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher isDouble = pattern.matcher(inString);
		if(!isDouble.matches()){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isDouble(String inString){
		Pattern pattern = Pattern.compile("[0-9]+.[0-9]+");
		Matcher isDouble = pattern.matcher(inString);
		if(!isDouble.matches()){
			return false;
		}else{
			return true;
		}
	}
	
	//轉換取小數點1 
	public static String parseNumToFinancial(Object number, String pattern){ //"###,###,##0.00"
		DecimalFormat nf = new java.text.DecimalFormat(pattern);
		String value= "";
		
		/* number可能是String，也可能是double
		 * String  有可能輸入','在字串內，所以要replace。再轉成Double
		 * double  直接轉換 */
		
		if(number!=null && String.valueOf(number).trim().length()!=0){
			
			if(number instanceof String){
				number = Double.valueOf(((String) number).replace(",", ""));
			}

			try {
				value = nf.format(number);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("parseNumToFinancial錯誤, number="+ String.valueOf(number));
			}
		}
		return value;
	}
	
	//轉換取小數點2	(是失敗的）
	public static String getInputCarry(String str , int count)
	{
        double b = new BigDecimal(str).setScale(count, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(b);
	}
	
	
	

	//將用固定符號分隔的字串，變成 String[]後傳回
	public static ArrayList<String> getValueToList(String value, String split){
		
		ArrayList<String> list = null;
		if(value.indexOf(split) == -1){
			list = new ArrayList<>( Arrays.asList( new String[]{value} ) );
		}else{
			list = new ArrayList<>( Arrays.asList( value.split(split) ) );
		}

		return list;
	}
	
	//將 Array 或 List 用分隔符號串成字串，Value送回
	public static String getListToValue(ArrayList<String> list, String split)
	{
		StringBuilder sb = new StringBuilder();
		if(list != null){
			for(String value : list){
				if(sb.length() > 0){
					sb.append(split);
				}
				sb.append(value);
			}
		}

		return sb.toString();
	}
	
	
	//若遇到純文字欄，有機會被輸入惡意程式碼，就將以下敏感符號在DAO存進DB時換成相似的全形符號
	public static String escapeXssSymbols(String inString){
		return trim(inString)
				.replace(">", "＞")
				.replace("<", "＜")
				.replace("'", "\uff07")
				.replace("\"", "\uff02");
	}
	
	/* Make string valid for SQL commands. */
	public static String dbstring(String inString){
		return trim(inString).replaceAll("'", "''");
	}	

	
	public static void main(String[] args) throws ParseException{
//		Test test1 = new Test();
//		test1.setDate(ToolsUtil.dateToChange("2016/09/26 13:00", "yyyy/MM/dd HH:mm"));
//		test1.setArriveTime(ToolsUtil.dateToChange("2016/09/26 14:00", "yyyy/MM/dd HH:mm"));
//		test1.setSchedule("test1");
//		
//		Test test2 = new Test();
//		test2.setDate(ToolsUtil.dateToChange("2016/09/01 13:00", "yyyy/MM/dd HH:mm"));
//		test2.setArriveTime(ToolsUtil.dateToChange("2016/09/01 14:00", "yyyy/MM/dd HH:mm"));
//		test2.setSchedule("test2");
//		Set<Test> sets = new HashSet<>();
//		sets.add(test1);
//		sets.add(test2);
//		System.out.println(sets);
//		
//		Set<String> sets1 = new HashSet<>();
//		sets1.add("1");
//		sets1.add("");
//		sets1.add("3");
//		sets1.add("1");
//		sets1.add("3");
//		sets1.add("2");
//		System.out.println(sets1);
//		
//		Set<Date> sets2 = new HashSet<>();
//		sets2.add(ToolsUtil.dateToChange("2016/09/26 13:00", "yyyy/MM/dd HH:mm"));
//		sets2.add(ToolsUtil.dateToChange("2016/09/26 09:00", "yyyy/MM/dd HH:mm"));
//		System.out.println(sets2);
//		Iterator<Date> dates = sets2.iterator();
//		while(dates.hasNext()){
//			System.out.println(ToolsUtil.dateToChange(dates.next(), "HH:mm"));
//		}
		
		Map<String, String> list = new HashMap<>();
		list.put("400005", "");
		list.put("400003", "");
		list.put("400004", "");
		list.put("400001", "");
		list.put("400002", "");
		
		String[] ary = list.keySet().toArray(new String[0]); 
//		String[] ary = {"400005","400003","400004","400001","400002"};
		Arrays.sort(ary);
		System.out.println(Arrays.toString(ary));
		
		
		
//		String string = "【C】100萬以下";
//		System.out.println(string.substring(3));
		
		
//		String[] num = {"3","9","5","19","49","1","25","28","11","42","12","21","46","26","29","32","38","40","31","44","04","14","23"};
//		StringBuilder num2 = new StringBuilder();
//		ArrayList<Integer> used = new ArrayList<>();
//		for(int i=0; i<6; i++){
//			int x = (int)(Math.random()*num.length);
//			if(used.contains(x)){
//				--i;
//				continue;
//			}
//			used.add(x);
//			System.out.println(num[x] + ", x="+x);
//			
//			if(num2.length()>0){
//				num2.append(" ");
//			}
//			num2.append((int)(Math.random()*42+1));
//		}
		
//		System.out.println("num2=" + num2.toString());
		
		
//		System.out.println(isTodayAfterWhen(dateToChange("2017-03-02", "yyyy-MM-dd")));
//		
//		float x = 1+2;
//		System.out.println(x);
//		float y = x*100;
//		int z = (int)y;
//
//		double aa = 98.513f;
//		System.out.println( (int)aa );
//		System.out.println( parseNumToFinancial(aa, "#,##0"));
//		
//		Date xDate = dateToChange("2016-09-05", "yyyy-MM-dd");
//		Date yDate = dateToChange("2016-09-05 23:59:59", "yyyy-MM-dd HH:mm:ss");
//		System.out.println(yDate.after(xDate));
//		String day = "2016-09-09";
//		Date date = ToolsUtil.dateToChange(day, "yyyy-MM-dd");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd EEE", Locale.CHINESE);
//		System.out.println(sdf.format(date)); 
		
		System.out.println("lrs.hcm@gmail.com".matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$"));
		
		System.out.println("A="+new StringBuilder());
		
		
		
	}
	


	
}
