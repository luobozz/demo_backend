package com.shtf.edu.utils.responseMessage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RequestMessage class
 *
 * @author zhouliang&chenlingyu
 * @date 2020/4/14 9:56
 */
@Component
@Data
@ConfigurationProperties(prefix = "codemsg")
public class ResponseMessageHandle {
	private Map<Integer,String> codes;

	public ResponseMessage code(int code){
		ResponseMessage responseMessage=new ResponseMessage();
		responseMessage.setCode(code);
		responseMessage.setMessage(codes.get(code));
		checkCodes(responseMessage,code);
		return responseMessage;
	}

	public String msg(int code){
		if(codes.get(code)==null){
			return "无效code";
		}else{
			String msg=codes.get(code);
			msg=defaultMsg(code);
			return msg;
		}
	}

	private void checkCodes(ResponseMessage responseMessage,int code) {
		int successCode=200,noneCode=404,errorCode=500,noAuthCode=403;
		if(code==successCode||code==noneCode||code==errorCode||code==noAuthCode){
			responseMessage.setMessage(defaultMsg(code));
			return;
		}else if(codes.get(code)==null){
			responseMessage.setMessage("无效code");
			responseMessage.setCode(-1);
			return;
		}
	}

	private String defaultMsg(int code){
		switch (code){
			case 200:
				return "操作成功";
			case 403:
				return "无效访问";
			case 413:
				return "强制退出";
			case 404:
				return "没有数据";
			case 500:
				return "系统错误";
			default:
				return "操作失败";
		}
	}
}
