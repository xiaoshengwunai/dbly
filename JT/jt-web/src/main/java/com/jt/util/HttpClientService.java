package com.jt.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {
	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig requestConfig;

	/**
	 * 目的:用户传递参数,工具api发起请求,将返回值交换用户
	 * @param param key=value
	 */
	public String doGet(String url, Map<String, String> params, String charset) {
		// 1. 校验字符集编码格式是否为null
		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		/**
		 * 判断用户是否传递了参数!
		 * 2. Get请求的url分为带参和不带
		 */
		if (params != null) {			// 不为null,url需要拼接
			url += "?";					// 把问号先拼上
			for (Map.Entry<String, String> entry : params.entrySet()) {	// 遍历map集合,实际是遍历map底层的entry对象
				String key = entry.getKey();
				String value = entry.getValue();
				url += key + "=" + value + "&";
				
			}
			url = url.substring(0, url.length() - 1);	// 干掉最后一个多余的"&"
		}
		// 3. 定义get请求对象
		HttpGet httpGet = new HttpGet(url);
		// 定义请求超时时间
		httpGet.setConfig(requestConfig);
		// 发起请求
		String result = null;
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);	//执行请求
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), charset); 
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//  以下全是复制的
	
	public String doGet(String url){

		return doGet(url, null, null);
	}

	public String doGet(String url,Map<String,String> params){

		return doGet(url, params, null);
	}

	public String doGet(String url,String charset){

		return doGet(url, null, charset);
	}

	//实现httpClient POST提交
	public String doPost(String url,Map<String,String> params,String charset){
		String result = null;

		//1.定义请求类型
		HttpPost post = new HttpPost(url);
		post.setConfig(requestConfig);  	//定义超时时间

		//2.判断字符集是否为null
		if(StringUtils.isEmpty(charset)){

			charset = "UTF-8";
		}

		//3.判断用户是否传递参数
		if(params !=null){
			//3.2准备List集合信息
			List<NameValuePair> parameters = 
					new ArrayList<>();

			//3.3将数据封装到List集合中
			for (Map.Entry<String,String> entry : params.entrySet()) {

				parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			//3.1模拟表单提交
			try {
				UrlEncodedFormEntity formEntity = 
						new UrlEncodedFormEntity(parameters,charset); //采用u8编码

				//3.4将实体对象封装到请求对象中
				post.setEntity(formEntity);
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
		}

		//4.发送请求
		try {
			CloseableHttpResponse response = 
					httpClient.execute(post);

			//4.1判断返回值状态
			if(response.getStatusLine().getStatusCode() == 200) {

				//4.2表示请求成功
				result = EntityUtils.toString(response.getEntity(),charset);
			}else{
				System.out.println("获取状态码信息:"+response.getStatusLine().getStatusCode());
				throw new RuntimeException();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}



	public String doPost(String url){

		return doPost(url, null, null);
	}

	public String doPost(String url,Map<String,String> params){

		return doPost(url, params, null);
	}

	public String doPost(String url,String charset){

		return doPost(url, null, charset);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * (重写练手)
	 */
	public String doGet2(String url, Map<String, String> params, String charset) {
		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		/**
		 * www.jt.com?id=100&name=zhangsan
		 */
		if (params != null) {	//如果用户传了参(不为null),则需要把params拼接到url中
			url += "?";
			for (Map.Entry<String, String> entry : params.entrySet()) {	// 其实是遍历其中的entrySet
				String key = entry.getKey();
				String value = entry.getValue();
				url += key + "=" + value + "&";	// 但最终会多出一个&符号
			}
			url = url.substring(0,url.length() - 1);	// 调用多余的符号 &
		}
		
		return null;
	}
	
}
