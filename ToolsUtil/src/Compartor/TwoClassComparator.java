package Compartor;
import java.util.Comparator;
import java.util.Date;

import Tools.Data.DateUtil;

public class TwoClassComparator implements Comparator<Object>{

	/**
	 * @return o1小於、等於或大於o2，分别返回負整數、零或正整數。
	 */
	@Override
	public int compare(Object o1, Object o2) {
		return dateLong(o1).compareTo(dateLong(o2));
	}

	private String dateLong(Object obj)  {
		String dateLong = "";
		if(obj instanceof ClassA){
			ClassA bean = (ClassA)obj;
			dateLong = DateUtil.dateToChangeTime(bean.getStart_time(), "HH:mm", "EN");
		}
		else if(obj instanceof ClassB){
			ClassB bean = (ClassB)obj;
			dateLong = DateUtil.dateToChangeTime(bean.getStart_time(), "HH:mm", "EN");
		}
		return dateLong;
	}
}

class ClassA{
	public Date getStart_time()
	{
		return new Date();
	}
}

class ClassB{
	public Date getStart_time()
	{
		return new Date();
	}
}
