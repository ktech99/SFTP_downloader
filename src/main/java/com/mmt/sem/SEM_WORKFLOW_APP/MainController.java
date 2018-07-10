/*This class is a controller which is mapped to the /file url
 * It takes the sftp path, username, and password as parameters from index.html
 * It downloads all zip files in that directory
 * It unzips those files
 * It uploads those files to a specified hdfs server
 * */
package com.mmt.sem.SEM_WORKFLOW_APP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@Autowired
	SEMConfigs semConfigs;

	@Autowired
	Unzip unzip;

	@Autowired
	Upload upload;




    @GetMapping("/files")
	public String files(@RequestParam(name = "path", required = true, defaultValue = "path") String paths,
			@RequestParam(name = "username", required = true, defaultValue = "path") String user,
			@RequestParam(name = "password", required = true, defaultValue = "pass") String pass, Model model,
			@RequestParam(name = "host", required = true, defaultValue = "path") String host)
			throws IOException {
		model.addAttribute("paths", paths);

		String HDFS_URL = "";
		int port = 22; //default
		String zipDir = "";
		String unzipDir = "";
		File configFile =  ResourceUtils.getFile("classpath:config.properties");
//        String fileName = "config.properties";
//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//        File configFile = new File(classLoader.getResource(fileName).getFile());
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			HDFS_URL = props.getProperty("HDFS_URL");
			zipDir = props.getProperty("TEMP_ZIP");
			unzipDir = props.getProperty("TEMP_UNZIPPED");
			port = Integer.parseInt(props.getProperty("SFTP_PORT"));
			reader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		} catch (IOException ex) {
			// I/O error
		}
		//For testing ("test.rebex.net", 22, "demo", "password", "/")
		semConfigs.download().download("test.rebex.net", port, "demo", "password", "/", zipDir);
		//semConfigs.download().download(host, port, user, pass, paths);
		unzip.unzipping(zipDir, unzipDir);
		upload.startUpload(HDFS_URL, unzipDir);
		return "files";
	}

}
