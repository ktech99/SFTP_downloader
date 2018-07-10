/*
* Method to upload Zip files in FTPUnzipped folder to hdfs server
* */
package com.mmt.sem.SEM_WORKFLOW_APP;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Upload {


    @RequestMapping("upload")
    @ResponseBody
    public BaseResponse startUpload() throws IOException {
    return startUpload("hdfs://0.0.0.0:19000","TEMP_UNZIPPED\\");
    }
        public BaseResponse startUpload(String hdfs_url, String unzip_dir) throws IOException {
		// TODO write method for distributed system
            BaseResponse response = new BaseResponse();
            response.setFunction("Upload");
            try {
			//Use this for hdfs configured on a single node
			URI url = new URI(hdfs_url);
			Configuration conf = new Configuration();
			FileSystem file1 = FileSystem.get(url, conf);
			System.out.println(file1.getWorkingDirectory());
			String home = System.getProperty("user.home");
			URL destPath = ResourceUtils.getURL("classpath:"+ unzip_dir);
			File folder = new File(String.valueOf(Paths.get(destPath.toURI()).toFile()));
			File[] listOfFiles = folder.listFiles();
			File dir = new File(String.valueOf(Paths.get(destPath.toURI()).toFile()));
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".csv")) {
				    response.addFileName(listOfFiles[i].getName());
					System.out.println("uploading File: " + listOfFiles[i].getName());
					Path src = new Path(String.valueOf(Paths.get(destPath.toURI()).toFile()) +"\\" + listOfFiles[i].getName());
					Path dst = new Path(file1.getWorkingDirectory()+"/" + listOfFiles[i].getName());
					file1.copyFromLocalFile(src, dst);
					System.out.println("upload success");
				}
			}
		} catch (Exception e) {
                response.setStatus(400);
                response.setMessage(e.toString());
			    e.printStackTrace();
		}
		return response;
	}
}
