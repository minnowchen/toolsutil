package Compartor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GroupNameComparator implements Comparator<Object>{

	Map<String, String> list = null;
	
	public GroupNameComparator(){
		list = new HashMap<>();
		list.put("甲", "01");
		list.put("乙", "02");
		list.put("丙", "03");
		list.put("丁", "04");
		list.put("戊", "05");
		list.put("己", "06");
		list.put("庚", "07");
		list.put("辛", "08");
		list.put("壬", "09");
		list.put("癸", "10");
	}
	
	/**
	 * @return o1小於、等於或大於o2，分别返回負整數、零或正整數。
	 */
	@Override
	public int compare(Object o1, Object o2) {
		return dateLong(o1).compareTo(dateLong(o2));
	}

	private String dateLong(Object obj)  {
		String name = String.valueOf(obj);
		return list.get(name) ;
	}
	
	
	
}
