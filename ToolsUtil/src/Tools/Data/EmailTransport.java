package Tools.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EmailTransport {
	
	String fromAddress;
	String subject;
	String body; 
	HashMap<String, byte[]> filesInputStream;
	ArrayList<byte[]> filesInputStream_list;
	String[] recipients;
	String[] ccAddress;
	String[] bccAddress;
	String username; 
	String password; 
	String host;
	int port;
	@Override
	public String toString() {
		return "EmailTransport [fromAddress=" + fromAddress + ", subject=" + subject + ", body=" + body
				+ ", recipients=" + Arrays.toString(recipients) + ", ccAddress=" + Arrays.toString(ccAddress)
				+ ", bccAddress=" + Arrays.toString(bccAddress) + ", username=" + username + ", password=" + password
				+ ", host=" + host + ", port=" + port + "]";
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String[] getRecipients() {
		return recipients;
	}
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}
	public String[] getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(String[] ccAddress) {
		this.ccAddress = ccAddress;
	}
	public String[] getBccAddress() {
		return bccAddress;
	}
	public void setBccAddress(String[] bccAddress) {
		this.bccAddress = bccAddress;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public HashMap<String, byte[]> getFilesInputStream() {
		return filesInputStream;
	}
	public void setFilesInputStream(HashMap<String, byte[]> filesInputStream) {
		this.filesInputStream = filesInputStream;
	}
	public ArrayList<byte[]> getFilesInputStream_list()
	{
		return filesInputStream_list;
	}
	public void setFilesInputStream_list(ArrayList<byte[]> filesInputStream_list)
	{
		this.filesInputStream_list = filesInputStream_list;
	}

}
