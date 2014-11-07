package com.lewtsu.webbrowser.thread;

import java.awt.Color;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lewtsu.webbrowser.WebBrowser;
import com.teamdev.jxbrowser.chromium.Browser;

public class HandlerUrl extends Thread {

	private WebBrowser webBrowser;
	private Browser browser;
	private Color color;
	private String url;
	private String tName;
	private boolean isLoad;

	public HandlerUrl(WebBrowser wb, Browser b, Color c, String u, String tn, boolean il) {
		url = u.toLowerCase();
		if(!(url.startsWith("http://") || url.startsWith("https://")))
			url = "http://" + url + "/";
		webBrowser = wb;
		browser = b;
		color = c;
		url = u;
		tName = tn;
		isLoad = il;
		webBrowser.logStatus(tName, "NEW", color);
		if(isLoad) {
			browser.loadURL(url);
		}
	}

	@Override
	public void run() {
		//if(!isLoad)
			webBrowser.logHeader(tName, getHeader(), color);
		while(browser.isLoading()) {
			webBrowser.logStatus(tName, "RUNNING...", color);
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		webBrowser.logStatus(tName, "TERMINATED", color);
	}

	public String getHeader(){ 
		String str = "";
		webBrowser.logHeader(tName, "Get Header Url: " + url + "\r\n", color);
		try { 
			URLConnection conn = new URL(url).openConnection(); 
			Map<String, List<String>> map = conn.getHeaderFields(); 
			for (Entry<String, List<String>> m : map.entrySet()) { 
				if(m.getKey() == null) {
					str +=  m.getValue() + "\r\n";
				} else {
					str +=  m.getKey() + ": " + m.getValue() + "\r\n";
				}
			}
			List<String> contentLength = map.get("Content-Length"); 
			if (contentLength == null) { 
				str += "Content-Length doesn't present in Header!\r\n"; 
			} else { 
				for (String header : contentLength) {
					str += "Content-Lenght: " + header + "\r\n";
				} 
			}
		} catch (Exception e) { 
			for(StackTraceElement ste :e.getStackTrace()) {
				str += ste.toString() + "\r\n";
			}
		}
		return str;
	}


}
