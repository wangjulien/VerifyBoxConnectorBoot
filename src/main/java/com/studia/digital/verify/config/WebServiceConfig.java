package com.studia.digital.verify.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;

import com.studia.digital.verify.service.VerifyBoxServiceClient;

/**
 * Configuration pour SOAP WS : 
 * - A marshaller which takes into account the Class produced by Jaxb2 
 * - A bean VerifyBoxServiceClient which use WebServiceTemplate for SOAP communication 
 * - A RestTemplate which will send HTTP (Pseudo REST) request for NeoGed. 
 * 		A "permit all policy" is used for HTTPS certificate verification
 * 
 * @author jwang
 *
 */
@Configuration
public class WebServiceConfig {

	@Value("${app.url.verify-host:http://172.19.33.79:8080/LP7VerifyBox-LP7VerifyBoxServer/LP7VerifyBoxBean}")
	private String verifyHostUrl;

	@Bean
	public MarshallingPayloadMethodProcessor methodProcessor() {
		return new MarshallingPayloadMethodProcessor(marshaller());
	}

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths("com.lexpersona.lp7verifybox.server.jaxb", "com.lexpersona.lp7verifybox.server.ws");
		return marshaller;
	}

	@Bean
	public VerifyBoxServiceClient verifyServiceClient(Jaxb2Marshaller marshaller) {
		VerifyBoxServiceClient client = new VerifyBoxServiceClient();
		client.setDefaultUri(verifyHostUrl);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(null, acceptingTrustStrategy).build();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory()).register("https", sslsf).build();

		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm)
				.build();

		return builder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient)).build();
	}
}
