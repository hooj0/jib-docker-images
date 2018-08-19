package io.github.hooj0.example;

import java.net.URI;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jib docker images application main
 * 
 * @author hoojo
 * @createDate 2018年8月18日 下午3:14:50
 * @file App.java
 * @package io.github.hooj0.example
 * @project jib-docker-images
 * @blog http://hoojo.cnblogs.com
 * @email hoojo_@126.com
 * @version 1.0
 */
public class App {

	private final static Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		log.info("args: {}", new Object[] { args });

		for (String arg : args) {
			System.out.println("arg: " + arg);
		}

		String requestURL = System.getenv("ENV_REQUEST_URL");
		if (requestURL == null) {
			System.out.println("ENV_REQUEST_URL empty.");
		}
		
		if (requestURL.contains(",")) {
			String[] requestURLs = requestURL.split(",");
			for (String url : requestURLs) {
				httpPost(url);
			}
		} else {
			//httpPost(requestURL);
		}
	}

	private static void httpPost(String requestURL) {
		log.info("request url: {}", requestURL);

		try {
			URI uri = URI.create(requestURL);
			
			HttpPost post = new HttpPost(uri);
			HttpClient httpClient = HttpClientBuilder.create().build();
			
			StringEntity entity = new StringEntity("http post time: " + System.currentTimeMillis(), Charset.forName("UTF-8"));
			entity.setContentEncoding("UTF-8");
			//post.setEntity(entity);
			
			HttpResponse response = httpClient.execute(post);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				log.error("request failure, status: {}", statusCode);
			} else {
				for (Header header : response.getAllHeaders()) {
					log.debug("header {} -> {}", header.getName(), header.getValue());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
