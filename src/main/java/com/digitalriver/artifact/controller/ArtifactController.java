package com.digitalriver.artifact.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.digitalriver.artifact.FileDetail;

/**
 * @author PRISM Team test commit #2
 */
@RestController
public class ArtifactController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ArtifactController.class);

	/**
	 * Gets the current status of the service
	 * 
	 * @return the current status as serialized JSON
	 */
	// {fileName:.+}
	@RequestMapping(value = "/file/{fileName}/{extension}", method = RequestMethod.GET, produces = "application/octet-stream")
	public FileSystemResource getServiceStatus(@PathVariable String fileName,
			@PathVariable String extension) {

		String newFileName = "artifacts" + File.separator + fileName + "."
				+ extension;

		File file = new File(newFileName);

		LOGGER.info("file path: " + file.getAbsolutePath());
		LOGGER.info("file exists: " + file.exists());

		if (file.exists()) {
			FileSystemResource fsr = new FileSystemResource(file);
			return fsr;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/file", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<FileDetail> getFiles() {
		List<FileDetail> list = new ArrayList<FileDetail>();

		File dir = new File("artifacts");

		for (File file : dir.listFiles()) {
			FileDetail detail = new FileDetail();
			detail.setFileName(file.getName());
			detail.setLength(file.length());
			detail.setModifiedDate(new Date(file.lastModified()).toString());

			list.add(detail);
		}

		return list;
	}

	@PostConstruct
	public void dod() {

		File dir = new File("artifacts");
		dir.mkdir();

		LOGGER.info("\n\n\n" + dir.getAbsolutePath() + "\n\n\n");
		LOGGER.info("isDir" + dir.isDirectory());
	}

}
