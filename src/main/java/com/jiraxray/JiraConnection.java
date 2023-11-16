package com.jiraxray;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.log4j.Logger;

import com.google.api.client.http.HttpHeaders;

public class JiraConnection {
	public static String URI_XRAY = "https://xray.cloud.getxray.app/api/v1/authenticate";
	public static String CLIENT_ID = "DD1CBEA5FA2F41848A2A22A66BF5D84E";
	public static String CLIENT_SECRET = "f53d555034150a2715498a9dc92bf47b5d9aa4528123df5f45ffb1e723366302";
	public static int STATUS_CODE_200_OK = 200;
	public static String TOKEN="";
	
	public static String CONTENT_TYPE_JSON = "application/json";

	public static String URL_JUNIT_XML_EXPORT_MULTIPART = "https://xray.cloud.getxray.app/api/v1/import/execution/junit/multipart";
	public static String URL_JUNIT_XML_EXPORT = "https://xray.cloud.getxray.app/api/v1/import/execution/junit";
	public static String CONTENT_TYPE_XML = "text/xml";
	public static String PROJECT_POB_QUALITY = "POBQUALITY" ;
	static final Logger logger = Logger.getLogger(JiraConnection.class.getName());

	
	public static String connect() throws URISyntaxException, IOException, InterruptedException {
		if (TOKEN.isEmpty()) {
			String body = "{\"client_id\":\"" + CLIENT_ID + "\"," + "\"client_secret\":\"" + CLIENT_SECRET + "\"}";
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = HttpRequest.newBuilder().header("Content-Type", CONTENT_TYPE_JSON)
					.uri(URI.create(URI_XRAY)).POST(HttpRequest.BodyPublishers.ofString(body)).build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == STATUS_CODE_200_OK) {
				logger.info("A conexão com JIRA/XRAY foi bem-sucedida.");
				TOKEN = response.body().replace("\"","");
				logger.info(request.toString());
			}

			else
				logger.error("Falha na conexão com JIRA/XRAY.");
		}
		
		return TOKEN;
	}
	
	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		String token = connect();
		JUnitResultTest.junitXMLExports(token);
	}

}
