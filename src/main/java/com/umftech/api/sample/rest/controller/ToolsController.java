/*
 * file name   : ToolsController.java 
 * <br>copyright   : Copyright (c) 2017
 * <br>description : for api doc sign tools
 * <br>modified    : 
 * @author      <a href="mailto:zhangming@umpay.com">Thomas Zhang</a>
 * @version     1.0
 * @date        2017年8月18日 下午3:13:20
 */ 

package com.umftech.api.sample.rest.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Base64.Decoder;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.umf.base.rest.APIContext;
import com.umf.base.util.RSAUtils;
import com.umftech.api.sample.rest.util.APIContextHelper;

/*************************************************************************
 * description : for api doc sign tools
 * @author      <a href="mailto:zhangming@umpay.com">Thomas Zhang</a>
 * @date        2017年8月18日 下午3:13:20
 * @version     1.0             
 *************************************************************************/
@RestController
@RequestMapping(value = "/tools", produces = "text/plain;charset=UTF-8")
public class ToolsController{
	private static final Logger logger = LoggerFactory.getLogger(ToolsController.class);

	@RequestMapping(value = "/sign", method = { RequestMethod.POST })
	@ResponseBody
	public String createPayment(HttpServletRequest req, @RequestBody String reqBody) throws UnsupportedEncodingException, URISyntaxException {
		logger.info(String.format("Request body=%s", reqBody));
		reqBody=java.net.URLDecoder.decode(reqBody, "UTF-8");
		reqBody = reqBody.substring(1,reqBody.length()-1);  
		APIContext apiContext = APIContextHelper.getAPIContext();
		new RSAUtils(apiContext.getCrtPath(), apiContext.getP8Path());
		String signature = RSAUtils.createSign(reqBody);
		logger.info(String.format("signature=%s", signature));
		return signature;
	}

}
