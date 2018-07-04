package com.mmt.sem.SEM_WORKFLOW_APP;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class Unzip {

	private String zipFilePath;
	private String destDir;

	public Unzip() {
		System.out.print("Unzipping");

		String home = System.getProperty("user.home");
		zipFilePath = home + "/Downloads/FTPTest/";
		destDir = home + "/Downloads/FTP2/";
	}

	// public void main(String[] args) {
	// String zipFilePath = "/Users/pankaj/tmp.zip";
	//
	// String destDir = "/Users/pankaj/output";
	//
	// unzip(zipFilePath, destDir);
	// }

	public void unzipping() {
		System.out.print("Unzipping");
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			File folder = new File(zipFilePath);
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".zip")) {
					System.out.println("File " + listOfFiles[i].getName());
					fis = new FileInputStream(zipFilePath + listOfFiles[i].getName());
					ZipInputStream zis = new ZipInputStream(fis);
					ZipEntry ze = zis.getNextEntry();
					while (ze != null) {
						String fileName = ze.getName();

						File newFile = new File(destDir + fileName);
						if (!newFile.isDirectory()) {
							System.out.println("Unzipping to " + newFile.getAbsolutePath());
							// create directories for sub directories in zip
							if (newFile.createNewFile()){
								System.out.println("File is created!");
								}else{
								System.out.println("File already exists.");
								}
							new File(newFile.getAbsolutePath()).mkdirs();
							FileOutputStream fos = new FileOutputStream(newFile);
							int len;
							while ((len = zis.read(buffer)) > 0) {
								fos.write(buffer, 0, len);
							}
							fos.close();
						}
						// close this ZipEntry
						zis.closeEntry();
						ze = zis.getNextEntry();
					}
					zis.closeEntry();
					zis.close();
					fis.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
