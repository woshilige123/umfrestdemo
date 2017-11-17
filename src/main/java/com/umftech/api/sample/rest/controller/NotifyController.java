package com.umftech.api.sample.rest.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.umftech.api.sample.rest.util.SignUtil;

@RestController
@RequestMapping(value = "/demo", produces = "text/plain;charset=UTF-8")
public class NotifyController{

	private static final Logger logger = LoggerFactory.getLogger(NotifyController.class);

	/**
	 * Receive payment result from UMF RESTful API
	 * 
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping(value = "/notifyResultRest", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String notifyResultRestListener(HttpServletRequest req, @RequestBody String reqBody) throws IOException{
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHMMSS");
	    String time = format.format(new Date());
	    StringBuilder retStr = new StringBuilder();
	    StringBuilder reqStr = new StringBuilder();
	    reqStr.append("\n");

	    logger.info(String.format("time:%s, response:%s", time,reqBody));

	    Enumeration headerNames = req.getHeaderNames();
	    while(headerNames.hasMoreElements()) {
	      String headerName = (String)headerNames.nextElement();
	      
	      reqStr.append(headerName);
	      reqStr.append("=");
	      reqStr.append(req.getHeader(headerName));
	      reqStr.append("\n");
	    }

//	    String path = SignUtil.UMF_PUBLIC_KEY;
//	    boolean isVerified = SignUtil.verifySign(reqBody, req.getHeader("Signature"));
//	    if(!isVerified){
//	    	return "failed to verify the signature";
//	    }
		try {
			String signature=req.getHeader("Signature");
			reqStr.append(reqBody);
			boolean isVerified=SignUtil.verifySign(reqBody, signature);
			reqStr.append("Verified Result ="+isVerified);
		    reqStr.append("\n");
            String retMsg = writeNotifyRecord(reqStr.toString());
            if("".equals(retMsg)){
            	retStr.append("ret_code=0000&");
    			retStr.append("ret_msg= Notification data received successfully.");
            }else{
            	retStr = new StringBuilder(retMsg);
            }
		} catch (Exception e) {
			e.printStackTrace();
			retStr.append(e.getMessage());
			retStr.append("\n");
			retStr.append(time);
		}
		return retStr.toString();
	}
	
	private String writeNotifyRecord(String content){
		// TODO Auto-generated method stub
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		try{
			String name = System.getProperty("user.home")+File.separator+"notify"+File.separator+ formatDate.format(new Date()) + "_notify.txt";
	    	File writename = new File(name);
	    	if(!writename.exists()){
	        	writename.createNewFile(); // 创建新文件  
	        }
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));
	        
	        out.write(content +"\r\n"); // \r\n即为换行  
	        out.flush(); // 把缓存区内容压入文件  
	        out.close(); // 最后记得关闭文件 
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }
		return "";
	}
}
