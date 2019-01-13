package Tools.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

public class DataConvert {
	public DataConvert(){
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static java.sql.Timestamp convertDate(String date) {
		String data = date.replace("/", "-");
		java.sql.Timestamp result = null;
		Date dat = new Date();

		if (data != null) {
			try {
				dat = (java.util.Date) sdf.parse(data);
				result = new java.sql.Timestamp(dat.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				result = new java.sql.Timestamp(new java.util.Date().getTime());
			}
		}
		return result;
	}
	
	public static String getStrUDate(){
		Date temp = new java.util.Date();
		return sdf.format(temp);
	}
	
	public static String timestamp2Str(java.sql.Timestamp date) {
		return sdf.format(date);
	}
	
	public static double convertDouble(String data) {
		double result = -1000;
		if (data != null&&!data.isEmpty()) {
			try {
				result = Double.parseDouble(data);
			} catch (NumberFormatException e) {
//				e.printStackTrace();
			}
		}
		return result;
	}

	public static int convertInt(String data) {
		int result = -1000;
		if (data != null&&!data.isEmpty()) {
			try {
				result = Integer.parseInt(data);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	public static String D2I2Str (double data) {
		String temp = Double.toString(data);
		if(temp.endsWith(".0")){
			temp=temp.replace(".0","");
		}
		return temp;
	}

	public static boolean isInputNotNull(String value) {
		if (value != null && value.trim().length() != 0)
			return true;
		else
			return false;
	}

	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左補0
				// sb.append(str).append("0");//右補0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
	public static String addRigthZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append(str).append("0");//右補0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
	public static String addRigthNineForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append(str).append("9");//右補0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	public static List<String> splitTimestamp(String data) {
		List<String> list = new ArrayList<String>();
		String txtYy=null;
		String txtMm=null;
		String txtDd=null;
		if (data != null) {
			txtYy = data.substring(0, 4);
			txtMm = data.substring(5, 7);
			txtDd = data.substring(8, 10);
			list.add(txtYy);
			list.add(txtMm);
			list.add(txtDd);
			System.out.println(txtYy+":"+txtMm+":"+txtDd);
		}
		return list;
	}
	public static java.sql.Timestamp getNowTimestamp() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}
	
	public static java.util.Date nextMonth(Date currentDate, int amount) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.MONTH, amount);
		return cal.getTime();
	}
	
	public static java.sql.Timestamp util2TsDate(java.util.Date date) {
		return new java.sql.Timestamp(date.getTime());
	}
	
//	trim
	public static String trim(String inString){
		if(inString == null){
			inString = "";
		}else{
			inString = inString.trim();
		}
		return inString;
	}

//  trim，如果超過特定長度時，取到len後，用...省略後續的文字
	public static String trim(String inString, int len){
		if(inString == null){
			inString = "";
		}else{
			inString = inString.trim();
		}
		if(inString.length() > len){
			inString = inString.substring(0, len) + "...";
		}
		return inString;
	}
	
//	轉換為BigDecimal
	public static BigDecimal convertBigdecimal(String data) {
		java.math.BigDecimal result = new BigDecimal("0");
		if (data != null&&!data.isEmpty()) {
			try {
				result = new BigDecimal(data);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
//	將暫存的數字和本次新增的相加後，傳回字串，必須確認資料來源皆為整數時，才可以使用
	public static  String StringPlus(String old,String tempNew){
		double oldNo = convertDouble(old);
		double newNo = convertDouble(tempNew);
		if(oldNo==-1000){oldNo=0;}
		if(newNo==-1000){newNo=0;}
		double sumResult = oldNo+newNo;
//		return Double.toString(sumResult);
		return D2I2Str(sumResult);
	}
	
//	將暫存的數字和本次新增的相加後，傳回字串
	public static  String StringPlusWithBigDecimal(String old,String tempNew){
		BigDecimal oldNo = convertBigdecimal(old);
		BigDecimal newNo = convertBigdecimal(tempNew);
		BigDecimal sumResult = oldNo.add(newNo);
		return String.valueOf(sumResult);
	}
	
//	將暫存的數字和本次新增的相乘後，傳回字串
	public static  BigDecimal StringMultiplyWithBigDecimal(String data,String param){
		BigDecimal v1 = convertBigdecimal(data);
		BigDecimal v2 = convertBigdecimal(param);
		return v1.multiply(v2);
	}
	
//	將暫存的數字和本次新增的相除後，四捨五入傳回字串
	public static  String StringDivideWithBigDecimal(String topValue,String downValue,int scale){
		String result = "";
		double xb=convertDouble(downValue);
		double xt=convertDouble(topValue);
		if(xb!=-1000&&xt!=-1000){
			if(!topValue.isEmpty()&&!downValue.isEmpty()){
				BigDecimal v1 = convertBigdecimal(topValue);
				BigDecimal v2 = convertBigdecimal(downValue);
				result = v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP).toString();
			}
		}
		return result;
	}
	
//	傳回百分比格式的字串
	private static String BigDecimalFormatToPercent(String data,int scale,String token){
		String result = "";
		if(!data.isEmpty()){
			BigDecimal v1 = convertBigdecimal(data);
			BigDecimal v2 = convertBigdecimal("1");
			if(token.equalsIgnoreCase("Y")){
				result = v1.divide(v2,scale,BigDecimal.ROUND_HALF_UP).toString()+"%";
			}else if(token.equalsIgnoreCase("N")){
				result = v1.divide(v2,scale,BigDecimal.ROUND_HALF_UP).toString();
			}
		}
		return result;
	}

//	format相除後的結果，傳回百分比格式的字串
	public static String getStrPercent(String topValue,String downValue,int finalScale,String token){
		String result="";
		double xb=convertDouble(downValue);
		double xt=convertDouble(topValue);
		
		if(xb!=-1000&&xt!=-1000){
			if(!topValue.isEmpty()&&!downValue.isEmpty()){
				String divide = StringDivideWithBigDecimal(topValue,downValue,finalScale+10);
				String temp = StringMultiplyWithBigDecimal(divide,"100").toString();
				result=DataConvert.BigDecimalFormatToPercent(temp,finalScale,token);
			}
		}
		return result;
	}

	
//	format相除後的結果，傳回百分比格式的進口成長率
	public static String getGrowthRateStrPercent(String topValue,String downValue,int finalScale,String token){
		String result="";
		double xb=convertDouble(downValue);
		double xt=convertDouble(topValue);
		
		if(xb!=-1000&&xt!=-1000){
			if(!topValue.isEmpty()&&!downValue.isEmpty()){
				String divide = StringDivideWithBigDecimal(topValue,downValue,finalScale+10);
				String minusOne = StringPlusWithBigDecimal(divide,"-1");
				String temp = StringMultiplyWithBigDecimal(minusOne,"100").toString();
				result=BigDecimalFormatToPercent(temp,finalScale,token);
			}
		}
		return result;
	}
//	format相除後的結果，傳回百分比格式的長期進口成長率
	public static String getLongTermGthStrPercent(String thisValue,String baseValue,int finalScale,String token,String year){
		String result="";
		String yearGap="";
		int yearNew=Integer.valueOf(year);
		double xb=convertDouble(baseValue);
		double xt=convertDouble(thisValue);
		
		if(xb!=-1000&&xt!=-1000){
			if(yearNew>2010){
				if(!thisValue.isEmpty()&&!baseValue.isEmpty()){
					yearGap=String.valueOf(yearNew-2010);
					String divide1 = StringDivideWithBigDecimal(thisValue,baseValue,finalScale+10);
					String divide2 = StringDivideWithBigDecimal("1",yearGap,finalScale+10);
					String divide = String.valueOf(Math.pow(DataConvert.convertDouble(divide1), DataConvert.convertDouble(divide2)));
					String minusOne = StringPlusWithBigDecimal(divide,"-1");
					String temp = StringMultiplyWithBigDecimal(minusOne,"100").toString();
					result=BigDecimalFormatToPercent(temp,finalScale,token);
				}
			}
		}
		return result;
	}
//	public static String getLongTermGthStrPercent(String thisValue,String baseValue,int finalScale,String token,String year){
//		String result="";
//		String yearGap="";
//		int yearNew=Integer.valueOf(year);
//		double xb=convertDouble(baseValue);
//		double xt=convertDouble(thisValue);
//		
//		if(xb!=-1000&&xt!=-1000){
//			if(yearNew>2007){
//				if(!thisValue.isEmpty()&&!baseValue.isEmpty()){
//					yearGap=String.valueOf(yearNew-2007);
//					String divide1 = StringDivideWithBigDecimal(thisValue,baseValue,finalScale+10);
//					String divide2 = StringDivideWithBigDecimal("1",yearGap,finalScale+10);
//					String divide = String.valueOf(Math.pow(DataConvert.convertDouble(divide1), DataConvert.convertDouble(divide2)));
//					String minusOne = StringPlusWithBigDecimal(divide,"-1");
//					String temp = StringMultiplyWithBigDecimal(minusOne,"100").toString();
//					result=BigDecimalFormatToPercent(temp,finalScale,token);
//				}
//			}
//		}
//		return result;
//	}

//	將暫存的數字和本次新增的相減後，傳回字串
	public static  String StringMinusWithBigDecimal(String old,String tempNew){
		BigDecimal oldNo = convertBigdecimal(old);
		BigDecimal newNo = convertBigdecimal(tempNew);
		BigDecimal sumResult = oldNo.add(newNo.negate());
		return String.valueOf(sumResult);
	}
	
	public static String getSeasonByMonth(String month){
		String season = "";
		double m = convertInt(month);
		if(m>0&&m<13){
			if(m>6){
				if(m>9){
					season="4";
				}else{
					season="3";
				}
			}else{
				if(m>3){
					season="2";
				}else{
					season="1";
				}
			}
		}
		return season;
	}
	
	public static String getMaxMonthInSeason(String season){
		String month = "";
		int m = convertInt(season);
		if(m==1){
			month="3";
		}else if(m==2){
			month="6";
		}else if(m==3){
			month="9";
		}else if(m==4){
			month="12";
		}else if(m==0){
			month="0";
		}
		return month;
	}
	public static String getSeasonName(String season){
		String sName = "";
		int m = convertInt(season);
		if(m==1){
			sName="第一季";
		}else if(m==2){
			sName="第二季";
		}else if(m==3){
			sName="第三季";
		}else if(m==4){
			sName="第四季";
		}
		return sName;
	}
	
	public static double convertData2Million(String num){
		double x = convertDouble(num);
		if(x==-1000){
			return 0;
		}
		double temp = convertDouble(num)/(100*10000);
		return temp;
	}
	public static String convertData2Million(String topValue,String scale){
		if(convertDouble(topValue)==-1000){
			return "";
		}
		String downValue = String.valueOf(100*10000);
		return DataConvert.StringDivideWithBigDecimal(topValue, downValue, Integer.valueOf(scale));
	}
	
	public static String getTermBySeason(String Season){
		String str2="";
		if(Season.equals("1")){
			str2 ="and Month between 1 and 3 ";
		}else if(Season.equals("2")){
			str2 ="and Month between 4 and 6 ";
		}else if(Season.equals("3")){
			str2 ="and Month between 7 and 9 ";
		}else if(Season.equals("4")){
			str2 ="and Month between 10 and 12 ";
		}else if(Season.equals("0")){
			str2 ="";
		}
		return str2;
	}
	
	public static boolean isPercentStr(String num){
		boolean result=false;
		if(num.endsWith("%")&&!num.isEmpty()){
			num = num.substring(0, num.lastIndexOf("%"));
			if(ParseHelper.isMatchPtn(num, "[0-9]+(?:\\.[0-9]*)?")){
				result = true;
			}
		}else if(ParseHelper.isMatchPtn(num, "[0-9]+(?:\\.[0-9]*)?")){
			result = true;
		}
		return result;
	}
	public static String removePercent(String num){
		String result="";
		if(num.endsWith("%")){
			result = num.substring(0, num.lastIndexOf("%"));
//			result = convertDouble(num);
		}else if(convertDouble(num)!=-1000){
			result = StringDivideWithBigDecimal(StringMultiplyWithBigDecimal(num, "100").toString(),"1",2);
		}
		return result;
	}
	
	public static void main(String[] args) {
/*		String x = "2012/04/25 00:00:000";
		java.sql.Timestamp result = convertDate(x);
		System.out.println("result :"+result);
		List<String> result2 = splitTimestamp(x);
		System.out.println("result2 :"+result2);*/
//		String y = "1200.000000000001";
//		String y1 = "-"+"1200.000000000001";
//		System.out.println(StringMinusWithBigDecimal(y,y));
//		System.out.println(StringPlus(y,y1));
//		System.out.println(getSeasonByMonth("01"));
//		getLongTermGthStrPercent(String topValue,String downValue,int finalScale,String year)
		
	}
	
}
