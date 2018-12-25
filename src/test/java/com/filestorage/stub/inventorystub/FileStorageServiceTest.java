package com.filestorage.stub.inventorystub;

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
	private static final String END_POINT = "http://localhost:8080";
	private static final String PATH = "/tmp";
	private String fileName = "263ed79a065d4274b041d9e5cc64f99d.png";
	
	FileStorageServiceAPI api;
	
	@Before
	public void beforeClass() throws Exception {
	}

	@Test
	public void test1Upload() throws JsonProcessingException {
		File file = new File(PATH + File.separator + "dashboard.png");
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
		Path destinationFile = Paths.get(PATH, fileName);
		Files.write(destinationFile, decodedImg);
	}
}
