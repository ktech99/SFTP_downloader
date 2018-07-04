package com.mmt.sem.SEM_WORKFLOW_APP;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class MainController {
	
	@Autowired
	SEMConfigs semConfigs;
	
	@Autowired
	Unzip unzip;
	
	@GetMapping("/files")
    public String files(@RequestParam(name="path", required=true, defaultValue="path") String paths,
    			@RequestParam(name="username", required=true, defaultValue="path") String user, 
    			@RequestParam(name="password", required=true, defaultValue="pass") String pass, 
    			Model model) {
		 model.addAttribute("paths", paths);
		// download(paths, user, pass);
		 String localPath = "C:/temp/";
			String remotePath = "/";
			
//			Download ftp = new Download("demo", "password");
			//Currently hardCoded
			semConfigs.download().download("test.rebex.net", 22, "demo", "password", "/");
			unzip.unzipping();
	        return "files";
    }


	
}