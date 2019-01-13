package Tools.Crawler;

import java.util.List;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

public class HtmlunitTools
{
	public static WebClient getwebClient()
	{
    	// WebClient 代表一個瀏覽器
    	WebClient webClient = new WebClient();
    	ProxyConfig proxyConfig = new ProxyConfig("proxy.hinet.net", 80);
    	webClient.getOptions().setProxyConfig(proxyConfig);
    	webClient.setAjaxController(new NicelyResynchronizingAjaxController()); //設定等ajax做完再回傳
    	
    	return webClient;
	}
	
	public static HtmlPage getPage(WebClient webClient, String url)
	{
		HtmlPage page = null;
        try {

        	// 取得HTML結果，若連線失敗，會測試五次，仍失敗就會變成 page == null
        	for(int i=0; page==null && i<5; i++) {
        		try {
        			page = webClient.getPage(url);
        		}catch (Exception e) {
					Thread.sleep(5000);
					System.out.println("Connecting failed：[" + url + "]. Reconnect : " + (i + 1));
				}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return page;
	}
	
	
	
	// 關閉所有瀏覽視窗
    public static void closePage(WebClient webClient){
        try {
			webClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }	
	
	public static void setCheckbox(String checkboxName, String value, boolean isChecked, HtmlForm form) {
		List<HtmlInput> checkboxs = form.getInputsByName(checkboxName);
		for(HtmlInput checkboxVal : checkboxs) {
			String val = checkboxVal.getAttribute("value");
			if(val.equalsIgnoreCase(value)) {
				checkboxVal.setChecked(isChecked);
			}else {
				checkboxVal.setChecked(isChecked==true?false:true);
			}
		}
	}
	
	public static void setTextField(String filedName, String value, HtmlForm form)
	{
		HtmlInput textField = form.getInputByName(filedName);
		textField.setValueAttribute(value);
	}
	
	public static void setSelect(String selectName, String value, boolean isSelected, HtmlForm form)
	{
		HtmlSelect selectVal = form.getSelectByName(selectName);
		selectVal.setSelectedAttribute(value, isSelected);
	}
	

}
