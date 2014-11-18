package com.digitalriver.artifact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.digitalriver.artifact.controller.FileUploadController;

public class ArchiveServiceClient {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileUploadController.class);

	String protocol = "http";
	String hostname = "192.168.59.103";
	// String hostname = "localhost";

	Integer port = 8080;
	String baseUrl = "artifact";

	RestTemplate restTemplate;

	public static void main(String[] args) {

		File file = new File("clientartifacts");

		String fileName = "cloud-app-simple.jar";
		// String fileName = "eureka.war";
		// String fileName = "spring-cloud-config-server.jar";

		File artifact = new File(file, fileName);

		System.out.println("dir exists: " + file.exists());
		System.out.println("dir isDir: " + file.isDirectory());
		System.out.println("artifact exists: " + artifact.exists());

		ArchiveServiceClient client = new ArchiveServiceClient();
		client.save(fileName, artifact);
	}

	public String save(String fileName, File file) {
		try {
			return doSave(fileName, file);
		} catch (RuntimeException e) {
			LOGGER.error("Error while uploading file", e);
			throw e;
		} catch (IOException e) {
			LOGGER.error("Error while uploading file", e);
			throw new RuntimeException("Error while uploading file", e);
		}

	}

	private String doSave(String fileName, File file) throws IOException,
			FileNotFoundException {
		// String tempFilePath = writeDocumentToTempFile(document);
		MultiValueMap<String, Object> parts = createMultipartFileParam(file
				.getAbsolutePath());

		Object request = parts;
		Object uriVariables = null;
		String url = getServiceUrl() + "upload?fileName=" + fileName;

		System.out.println(url);

		String response = getRestTemplate().postForObject(url, request,
				String.class, uriVariables);

		return response;
	}

	private MultiValueMap<String, Object> createMultipartFileParam(
			String tempFilePath) {
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("file", new FileSystemResource(tempFilePath));
		return parts;
	}

	/*
	 * private String writeDocumentToTempFile(Document document) throws
	 * IOException, FileNotFoundException { Path path; path =
	 * Files.createTempDirectory("testtempdir");//document.getUuid() String
	 * tempDirPath = path.toString(); File file = new
	 * File(tempDirPath,document.getFileName()); FileOutputStream fo = new
	 * FileOutputStream(file); fo.write(document.getFileData()); fo.close();
	 * return file.getPath(); }
	 */
	public String getServiceUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(getProtocol()).append("://");
		sb.append(getHostname());
		if (getPort() != null) {
			sb.append(":").append(getPort());
		}
		sb.append("/").append(getBaseUrl()).append("/");
		return sb.toString();
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public RestTemplate getRestTemplate() {
		if (restTemplate == null) {
			restTemplate = createRestTemplate();
		}
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private RestTemplate createRestTemplate() {
		restTemplate = new RestTemplate();
		return restTemplate;
	}

}
