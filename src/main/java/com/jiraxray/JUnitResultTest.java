package com.jiraxray;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class JUnitResultTest {
	public static String URL_JUNIT_XML_IMPORT_MULTIPART = "https://xray.cloud.getxray.app/api/v1/import/execution/junit/multipart";
	public static String URL_JUNIT_XML_IMPORT = "https://xray.cloud.getxray.app/api/v1/import/execution/junit";
	public static String CONTENT_TYPE_XML = "text/xml";
	public static String PROJECT_POB_QUALITY = "POBQUALITY" ;
	
	public static String junitXMLExports(String token) throws URISyntaxException, IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();

		File file = new File("C:\\@work\\MainFrame_Framework\\demandas-noesis\\Mainframe Framework\\output\\testejunit5.xml");
		Path path = file.toPath();
		HttpRequest request = HttpRequest.newBuilder()
				.header("Content-Type", CONTENT_TYPE_XML)
				.header("Authorization", "Bearer " + token)
				.uri(URI.create(URL_JUNIT_XML_IMPORT+"?projectKey="+ PROJECT_POB_QUALITY+"&testExecKey="+"POBQUALITY-2009"))
				.POST(HttpRequest.BodyPublishers.ofFile(path))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return "";
	}
	
//	public static void main(String[] args) {
//		
//	}
//	
//	public static String junitXMLExportsMultipart(String token) throws URISyntaxException, IOException, InterruptedException {
//		HttpClient client = HttpClient.newHttpClient();
//
//		HttpRequest request = HttpRequest.newBuilder().
//				header("Content-Type", CONTENT_TYPE_XML)
//				.uri(URI.create(URL_JUNIT_XML_EXPORT_MULTIPART)).
//				POST(HttpRequest.BodyPublishers.ofString(body)).
//				build();
//
//		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//		
//		return "";
//	}
}
