package com.mmt.sem.SEM_WORKFLOW_APP;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.*;
import org.springframework.stereotype.Service;

@Service
public class Upload {

	public Upload() {

	}

//	public void startUpload() throws IOException {
//
//		FileSystem hdfs = FileSystem.get(new Configuration());
//
//		// Print the home directory
//
//		System.out.println("Home folder -" + hdfs.getHomeDirectory());
//
//		// Create & Delete Directories
//		
//
//		Path workingDir = hdfs.getWorkingDirectory();
//		//
//		Path newFolderPath = new Path("/MyDataFolder");
//		//
//	
//		System.out.println("1");
//
//		if (hdfs.exists(newFolderPath))
//		{
//
//			// Delete existing Directory
//
//			hdfs.delete(newFolderPath, true);
//
//			System.out.println("Existing Folder Deleted.");
//
//		}
//
//		hdfs.mkdirs(newFolderPath); // Create new Directory
//		//
//		// System.out.println("Folder Created.");
//
//		// Copying File from local to HDFS
//		System.out.println("2");
//
//		String home = System.getProperty("user.home");
//
//		Path localFilePath = new Path(home + "/Downloads/FTP2/color.epf");
//		System.out.println("4");
//
//		Path hdfsFilePath = new Path(hdfs.getWorkingDirectory() + "/dataFile1.epf");
//		System.out.println(localFilePath);
//
//		hdfs.copyFromLocalFile(localFilePath, hdfsFilePath);
//
//		System.out.println("File copied from local to HDFS.");
//
//	}

	public void startUpload2() throws IOException {
		// TODO code application logic here
		// InetSocketAddress add = new InetSocketAddress("192.168.20.12", 9000);
		// //-------> Use this if you are using DistributedFileSystem Class(For
		// hadoop configured as distributed)
		try {
			URI url = new URI("hdfs://0.0.0.0:19000"); // -------> (url where
															// hdfs located-for
															// detail look
															// hadoop
															// configuration
															// )Use this if you
															// are using
															// FileSystem
															// Class(For hadoop
															// configured on a
															// single system)
			Configuration conf = new Configuration();
			// DistributedFileSystem ffs = new DistributedFileSystem(add, conf);
			FileSystem file1 = FileSystem.get(url, conf);
			System.out.print(file1.getWorkingDirectory());
			String home = System.getProperty("user.home");
			File folder = new File(home + "/Downloads/FTP2/");
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".csv")) {
					System.out.println("File " + listOfFiles[i].getName());
					Path src = new Path(home + "/Downloads/FTP2/" + listOfFiles[i].getName());
					Path dst = new Path(file1.getWorkingDirectory()+"/" + listOfFiles[i].getName());
					System.out.println(dst);
					// fs.copyFromLocalFile(b1, b2, src, dst);
					file1.copyFromLocalFile(src, dst);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
