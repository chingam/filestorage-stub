package com.filestorage.stub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.filestorage.stub.model.FileResponse;
import com.filestorage.stub.service.FileStorageServiceAPI;

import lombok.extern.slf4j.Slf4j;

/**
 * Unit test for API service
 */
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileStorageServiceTest {
	private static final String END_POINT = "http://localhost:8082";
	private static final String PATH = System.getProperty("user.home");
	private String fileName = "707a67a1229745bab69c53d2d18c7ff8.pdf";
	
	FileStorageServiceAPI api;
	
	@Before
	public void beforeClass() throws Exception {
	}

	@Test
	public void test1Upload() throws JsonProcessingException {
		File file = new File(PATH + File.separator + "img" + File.separator + "pic1.jpg");
		FileResponse fileResponse = FileStorageServiceAPI.connect(END_POINT).upload(file);
		assertNotNull(fileResponse);
		assertEquals("success", fileResponse.getStatus());
		fileName = fileResponse.getFile();
	}
	
	@Test
	public void test2Download() throws IOException {
		FileResponse response = FileStorageServiceAPI.connect(END_POINT).download(fileName);
		assertNotNull(response);
		assertEquals("success", response.getStatus());
		log.info(response.getFile());
		byte[] decodedImg = Base64.getDecoder().decode(response.getFile().getBytes(StandardCharsets.UTF_8));
		Path destinationFile = Paths.get(PATH + File.separator + "img" + File.separator, fileName);
		Files.write(destinationFile, decodedImg);
	}
}
