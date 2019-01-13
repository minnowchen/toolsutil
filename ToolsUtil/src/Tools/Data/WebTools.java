package Tools.Data;

import javax.servlet.http.HttpServletResponse;

public class WebTools {
	
	public static void setResponseNoCache(HttpServletResponse  response){
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
	    long time = System.currentTimeMillis();
	    response.setDateHeader("Last-Modified", time);
	    response.setDateHeader("Date", time);
	    response.setDateHeader("Expires", 0);
	    
	}
}
