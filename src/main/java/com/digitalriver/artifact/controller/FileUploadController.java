package com.digitalriver.artifact.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileUploadController.class);

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
	String handleFileUpload(
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "file", required = true) MultipartFile file) {

		try {

			String newFileName = "artifacts" + File.separator + fileName;
			File dir = new File(newFileName);

			System.out.println(dir.getAbsolutePath());

			InputStream in = file.getInputStream();

			Files.copy(in, dir.toPath(), StandardCopyOption.REPLACE_EXISTING);

			return "good";
		} catch (RuntimeException e) {
			LOGGER.error("Error while uploading.", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error while uploading.", e);
			throw new RuntimeException(e);
		}
	}

}
