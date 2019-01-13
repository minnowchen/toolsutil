package Tools.Data;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

public class EmailUtil
{
	
	public static void main(String[] args) throws ParseException{
		
		//1.從context取出連線資料版
		/* 從<context-param>取出連線帳密
		ServletContext context = request.getServletContext();
		String host = context.getInitParameter("host"); //"smtp.gmail.com"
		int port = Integer.valueOf(context.getInitParameter("port")); // SSL 的Port號: 465
		String username = context.getInitParameter("username");
		String password = context.getInitParameter("password"); //your password"sakura7163ow"
		
		HashMap<String, String> parameters = new HashMap<>();
		HashMap<String, byte[]> files = new HashMap<>();
		if(ServletFileUpload.isMultipartContent(request)){
			ServletFileUpload sfu = new ServletFileUpload();
			sfu.setHeaderEncoding("UTF-8");
			FileItemIterator iterator;
			try {
				iterator = sfu.getItemIterator(request);
				//一個一個項目檢查
				while(iterator.hasNext()){
					FileItemStream fis = iterator.next();
					InputStream inputStream = fis.openStream();
					if(fis.isFormField()){
						parameters.put(fis.getFieldName(), Streams.asString(inputStream,"UTF-8"));
					}else{
//						System.out.println("filename="+fis.getName());
						if(fis.getName()==null || fis.getName().trim().length()==0){
							files = null;
						}else{
							files.put(fis.getName(), IOUtils.toByteArray(inputStream));
						}
					}
					inputStream.close();
				}
				
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}
		*/
		
		//2.普通連線版
		String host = "smtp.gmail.com";
		int port = 465; // SSL 的Port號: 465
		String username = "xxxxxxxx@gmail.com";
		String password = "password";
		HashMap<String, String> parameters = new HashMap<>();
		HashMap<String, byte[]> files = new HashMap<>();
		
		
		
		String email_subject = parameters.get("email_subject");
		String email_body = parameters.get("email_body");
		//收件者
		String email_TO = parameters.get("email_TO");
		String[] recipients = null;
		if(email_TO.indexOf(",")!=-1){
			recipients = email_TO.split(",");
//			System.out.println("收件者="+Arrays.toString(recipients));
		}else if(email_TO.indexOf(";")!=-1){
			recipients = email_TO.split(";");
//			System.out.println("收件者="+Arrays.toString(recipients));
		}else if(email_TO.indexOf(",")==-1 && email_TO.trim().length()!=0 ){
			recipients = new String[]{email_TO};
//			System.out.println("收件者="+Arrays.toString(recipients));
		}
		
		//CC
		String email_CC = parameters.get("email_CC");
		String[] email_CCS = null;
		if(email_CC.indexOf(",")!=-1){
			email_CCS = email_CC.split(",");
//			System.out.println("副本="+Arrays.toString(email_CCS));
		}else if(email_CC.indexOf(";")!=-1){
			email_CCS = email_CC.split(";");
//			System.out.println("副本="+Arrays.toString(email_CCS));
		}else if(email_CC.indexOf(",")==-1 && email_CC.trim().length()!=0){
			email_CCS = new String[]{email_CC};
//			System.out.println("副本="+Arrays.toString(email_CCS));
		}
		
		//BCC
		String email_BCC = parameters.get("email_BCC");
		String[] email_BCCS = null;
		if(email_BCC.indexOf(",")!=-1){
			email_BCCS = email_BCC.split(",");
//			System.out.println("密件副本="+Arrays.toString(email_BCCS));
		}else if(email_BCC.indexOf(";")!=-1){
			email_BCCS = email_BCC.split(";");
//			System.out.println("密件副本="+Arrays.toString(email_BCCS));
		}else if(email_BCC.indexOf(",")==-1 && email_BCC.trim().length()!=0){
			email_BCCS = new String[]{email_BCC};
//			System.out.println("密件副本="+Arrays.toString(email_BCCS));
		}
		
		EmailTransport bean = new EmailTransport();
		bean.setSubject(email_subject);
		bean.setBody(email_body);
		bean.setFilesInputStream(files);
		bean.setRecipients(recipients);
		bean.setCcAddress(email_CCS);
		bean.setBccAddress(email_BCCS);
		bean.setFromAddress(username);
		bean.setHost(host);
		bean.setPort(port);
		bean.setUsername(username);
		bean.setPassword(password);
		
		
		EmailUtil emailUtil = new EmailUtil();
		try {
			String result = emailUtil.semdEmail(bean);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/***********************************************************************************************************/
	/*  發送email:
	 *  fromAddress、recipients、ccAddress、bccAddress、msgSubject、msgBody
	 */
	public String semdEmail(EmailTransport bean) throws IOException{
		
	/* 1.設定通訊協定  =============================================================================================*/
		Properties props = new Properties();
		//props.put("mail.smtp.host", bean.getHost());
		//props.put("mail.smtp.port", bean.getPort()); // TLS/STARTTLS 的Port號: 587
		//props.put("mail.smtp.starttls.enable", "true"); //使用身份驗證: Yes

		props.put("mail.smtps.host", bean.getHost());
		props.put("mail.smtps.port", bean.getPort()); // SSL 的Port號: 465
		props.put("mail.smtp.starttls.enable", "true"); 
		
		props.put("mail.smtp.auth", "true"); //使用身份驗證:No
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");		
		
		Session session = Session.getInstance(props, null);
		//Session session = Session.getInstance(props,  
		//	new Authenticator(){ protected PasswordAuthentication getPasswordAuthentication() 
		//			{ return new PasswordAuthentication(bean.getUsername(), bean.getPassword()); }
		//});
		
		
		
		/* 2.建立message - 放入通訊協定、寄件者、收件者、主旨 =====================================================================*/				
		try {
			//取得一Mime的Message
//			Message message = new MimeMessage(session); 
			MimeMessage message = new MimeMessage(session);
			
			// 寄件者
			message.setFrom(new InternetAddress(bean.getFromAddress(), "中華經濟研究院 區域發展研究中心", "UTF-8"));
			//message.setReplyTo(new InternetAddress[]{new InternetAddress("aaa@bbb", "testuser", "utf-8")});

			// 收件者 （預設複數）
			String[] recipients = bean.getRecipients();
			InternetAddress[] address = new InternetAddress[recipients.length];
			for(int i=0; i<recipients.length; i++){
				if(recipients[i].matches(
				        "^[A-Za-z0-9_]+((-[A-Za-z0-9_]+)|(.[A-Za-z0-9_]+))*@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+)*.[A-Za-z]+$")){
					address[i] = new InternetAddress(recipients[i].trim());
				}else{
					if(recipients[i].toString().indexOf(",") != -1){
						return "E-mail含有半形逗點「,」，煩請改成用半形分號「;」隔開。";
					}
				}
			}
			message.setRecipients(Message.RecipientType.TO, address);
				
			// CC （預設複數）
			String[] ccAddress = bean.getCcAddress();
			if(ccAddress!=null){
				InternetAddress[] CC = new InternetAddress[ccAddress.length];
				for(int i=0; i<ccAddress.length; i++){
					if(ccAddress[i].matches(
					        "^[A-Za-z0-9_]+((-[A-Za-z0-9_]+)|(.[A-Za-z0-9_]+))*@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+)*.[A-Za-z]+$")){
						CC[i] = new InternetAddress(ccAddress[i].trim());
					}
				}
				message.setRecipients(Message.RecipientType.CC, CC);
			}
			
			// BCC （預設複數）
			String[] bccAddress = bean.getBccAddress();
			if(bccAddress!=null){
				System.out.println("BCC="+bccAddress.length + ", " + Arrays.toString(bccAddress));
				InternetAddress[] BCC = new InternetAddress[bccAddress.length];
				
				for(int i=0; i<bccAddress.length; i++){
					if(bccAddress[i].matches(
					        "^[A-Za-z0-9_]+((-[A-Za-z0-9_]+)|(.[A-Za-z0-9_]+))*@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+)*.[A-Za-z]+$")){
						BCC[i] = new InternetAddress(bccAddress[i].trim());
					}
				}
				message.setRecipients(Message.RecipientType.BCC, BCC);
			}

			// 主旨，使用UTF8編碼，解決中文亂碼
			message.setSubject(bean.getSubject(), "UTF-8");

			
			
			
			
		/* 3.建立message - 本文：信件內容+圖片來源			
		 *   純文字         使用message.setText(bean.getBody());
		 *   有複雜內文  使用message.setContent(htmlText, "text/html; charset=\"utf-8\"");
		 */
			//建立 MimeMultipart，裝載各種BodyPart的容器物件
			Multipart multipart = new MimeMultipart("mixed"); //裝內文、內文圖片、附檔
	        
			
	        /* 新版 正確
	         * content
	         * 	 mutiPart
	         * 		bodyPart(內文、圖片) 父
	         * 			mutiPart1 子
	         * 				bodyPart1(內文)
	         * 				bodyPart2(圖片)
	         * 		bodyPart(附檔)
	         * */
			
			//bodyPart父母  裝上  一個mutipart孩子（裡面有內文bodypart1 & 內文圖片bodypart2）
			BodyPart part1_parent_bodyPart = new MimeBodyPart(); //bodyPart父母
	        Multipart part1_child_mutiPart = new MimeMultipart("related"); //mutiPart孩子
	        
	        //內文bodypart1
	        BodyPart bodymsg1 = new MimeBodyPart(); 
	        ArrayList<byte[]> files = bean.getFilesInputStream_list();
	        String htmlText = "<H1 style='font-family:微軟正黑體;'>"+bean.getBody()+"</H1>";
	        
	        /* 因為iPhone會把圖檔以ID名稱暫存起來，導致一直顯示舊圖，所以用UUID來產生唯一的ID，不讓iPhone擷取暫存 */
	        ArrayList<String> uuids = new ArrayList<>();
	        for(int n=1; n<=files.size(); n++){
	        	String uuid = UUID.randomUUID().toString();
	        	uuids.add(uuid);
	        	htmlText = htmlText + "<img style='width:100%;' src=\"cid:"+uuid+"\">"; //<img src="cid:image1">
	        }
	        bodymsg1.setContent(htmlText, "text/html; charset=\"utf-8\"");
	        part1_child_mutiPart.addBodyPart(bodymsg1); //內文bodypart1 放入 mutiPart孩子
	        
	        
	        //內文圖片bodypart
	        if(files!=null){
	        	for(int i=1; i<=files.size(); i++){
	        		BodyPart bodymsg2 = new MimeBodyPart();
	        		DataSource data = new ByteArrayDataSource(files.get(i-1), "image/jpeg"); //因為這裡只有圖檔
	        		/*嵌入內文的圖檔*/
	        		bodymsg2 = new MimeBodyPart(); 
	        		bodymsg2.setDataHandler(new DataHandler(data));
	        		bodymsg2.setHeader("Content-ID", "<"+uuids.get(i-1)+">");
	        		part1_child_mutiPart.addBodyPart(bodymsg2); //內文圖片bodypart2 放入 mutiPart孩子
	        	}
	        }
	        part1_parent_bodyPart.setContent(part1_child_mutiPart); //這個bodyPart父母  裝上 mutiPart孩子
	        multipart.addBodyPart(part1_parent_bodyPart); //這個bodypart 內有一個mutipart（裡面有bodypart 含 內文&內文圖片）
	        
	        /* 新版 正確
	         * content
	         * 	 mutiPart父
	         * 		bodyPart(內文、圖片)子
	         * 			mutiPart1
	         * 				bodyPart1(內文)
	         * 				bodyPart2(圖片)
	         * 		bodyPart(附檔)
	         * */
	        
	        /* 舊版 錯誤
	         * content
	         * 	 mutiPart父
	         * 		bodyPart(內文、圖片)子
	         * 		bodyPart(附檔)
	         * */
	        
	        
	        
		/* 4.建立message - 本文：附檔  ================================================================================*/
	        
        	HashMap<String, byte[]> pdfFiles = bean.getFilesInputStream();
        	if(pdfFiles != null){
        		for(String key :pdfFiles.keySet()){
        			DataSource pdfFile = new ByteArrayDataSource(pdfFiles.get(key), "application/octet-stream");
        			MimeBodyPart filePart = new MimeBodyPart();
        			filePart.setDataHandler(new DataHandler(pdfFile));
        			filePart.setHeader("Content-Type",  "application/octet-stream; charset=\"utf-8\"");// 要顯示的檔名，檔名使用UTF-8編碼
        			filePart.setFileName(MimeUtility.encodeText(key, "UTF-8", "B"));
        			multipart.addBodyPart(filePart); //第N個附檔
        			/* "Q"= QP編碼 
        			 * 		the legal values for "encoding" are "Q" and "B"... 
        			 * 		The "Q" encoding is recommended for use when most of the characters to be
        			 * 		encoded are in the ASCII character set;
        			 * 
        			 * "B"= Base64編碼
        			 * 		otherwise, the "B" encoding should be used.
        			 */
        		}
        	}
	        

        	
	        
		/* 5.建立message - 把本文都裝完後，放入message中			 
	     * 	 transport 寄信
	     */
	        message.setContent(multipart);
	        
	        Transport transport = session.getTransport("smtps");
			transport.connect(bean.getHost(), bean.getUsername(), bean.getPassword());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			
			//Transport transport = session.getTransport("smtp");
			//transport.connect(bean.getHost(), bean.getPort(), bean.getUsername(), bean.getPassword());
			//Transport.send(message);

			/* 也可以如此做
			 * msg.saveChanges(); 
			 * Transport transport = session.getTransport("smtp");
			 * transport.connect(“mail.ncku.edu.tw”, “UID”, “PWD”);
			 * transport.sendMessage(msg, msg.getAllRecipients());
			 * transport.close();
			 */
			
			return "郵件寄送成功！";
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return "郵件寄送失敗！請洽管理員處理，謝謝。";
		}
		
	}
}
