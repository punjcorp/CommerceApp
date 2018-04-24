/**
 * 
 */
package com.punj.app.ecommerce.models.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author admin
 *
 */
public class CommerceMultipartFile implements MultipartFile {

	private final byte[] fileContent;

	private String fileName;

	private String contentType;

	private File file;

	private String destPath = System.getProperty("java.io.tmpdir");

	private FileOutputStream fileOutputStream;

	public CommerceMultipartFile(byte[] fileData, String name, String contentType) {
		this.fileContent = fileData;
		this.fileName = name;
		this.contentType=contentType;
		file = new File(destPath + fileName);

	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		fileOutputStream = new FileOutputStream(dest);
		fileOutputStream.write(fileContent);
	}

	public void clearOutStreams() throws IOException {
		if (null != fileOutputStream) {
			fileOutputStream.flush();
			fileOutputStream.close();
			file.deleteOnExit();
		}
	}

	@Override
	public byte[] getBytes() throws IOException {
		return fileContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(fileContent);
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public String getName() {
		return this.fileName;
	}

	@Override
	public String getOriginalFilename() {
		return this.fileName;
	}

	@Override
	public long getSize() {
		return this.fileContent.length;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
