package com.studia.digital.verify.service.neoged;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NeoGedServletCaller {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NeoGedServletCaller.class);

	private static final String PROTOCOL_HTTP = "http";

	private static final String PROTOCOL_HTTPS = "https";

	private static final String HTTP_METHOD = "POST";

	public NeoGedServletCaller() {
		super();
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> call(final List<Map<String,Object>> payload, final String url)
			throws ClassNotFoundException, IOException, NoSuchAlgorithmException, KeyManagementException {

		URL gwtServlet = new URL(url);
		HttpURLConnection servletConnection = null;

		if (PROTOCOL_HTTPS.equals(gwtServlet.getProtocol())) {
			TrustManager[] trustAllCerts = new X509TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			// Install the all-trusting trust manager
			final SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

			servletConnection = (HttpsURLConnection) gwtServlet.openConnection();
		} else if (PROTOCOL_HTTP.equals(gwtServlet.getProtocol())) {
			servletConnection = (HttpURLConnection) gwtServlet.openConnection();
		}

		servletConnection.setRequestMethod(HTTP_METHOD);
		servletConnection.setDoOutput(true);
		servletConnection.setDoInput(true);
		servletConnection.setUseCaches(false);
		servletConnection.setRequestProperty("Content-Type", "application/x-java-serialized-object");

		try (OutputStream output = servletConnection.getOutputStream();
				ObjectOutputStream objOut = new ObjectOutputStream(output)) {

			objOut.writeObject(payload);
			objOut.flush();
		}

		try (InputStream response = servletConnection.getInputStream();
				ObjectInputStream inputFromServlet = new ObjectInputStream(response)) {

			return (Map<String, String>) inputFromServlet.readObject();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> lecture(HttpServletRequest request) {

		try (InputStream in = request.getInputStream();
				ObjectInputStream inputFromApplet = new ObjectInputStream(in);) {

			return (Map<String, Object>) inputFromApplet.readObject();

		} catch (IOException | ClassNotFoundException e) {
			LOGGER.error(e.getStackTrace().toString());
			return Collections.EMPTY_MAP;
		}
	}

	public static void ecriture(HttpServletResponse response, Map<String, Object> result) {
		response.setContentType("application/x-java-serialized-object");
		response.setCharacterEncoding("UTF-8");

		try (OutputStream outstr = response.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(outstr);) {

			oos.writeObject(result);
			oos.flush();

		} catch (IOException e) {
			LOGGER.error(e.getStackTrace().toString());
		}
	}
}
