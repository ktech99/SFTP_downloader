package com.mmt.sem.SEM_WORKFLOW_APP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Service
public class Download {

	@Autowired
	SEMConfigs semConfigs;

	private String host;
	private Integer port;
	private String user;
	private String password;

	private Session session;
	private Channel channel;
	private ChannelSftp sftpChannel;
	private Vector Filelist;
	private String SFTPWORKINGDIR;

	public Download(String path, String user, String pass) {
		
	}

	public void connect() {

		System.out.println("connecting..." + host);
		try {
			session = semConfigs.jsch().getSession(user, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.connect();

			channel = session.openChannel("sftp");
			channel.connect();
			sftpChannel = (ChannelSftp) channel;
			sftpChannel.cd(SFTPWORKINGDIR);
			Filelist = sftpChannel.ls(SFTPWORKINGDIR);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void disconnect() {
		System.out.println("disconnecting...");
		sftpChannel.disconnect();
		channel.disconnect();
		session.disconnect();
	}

	public void download(String host, int port, String user, String pass, String path) {
		//initialising variables
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = pass;
		this.SFTPWORKINGDIR = path;
		
		byte[] buffer = new byte[1024];
		BufferedInputStream bis;
		connect();
		try {
			System.out.println("Files in directory:");
			for (int i = 0; i < Filelist.size(); i++) {
				LsEntry entry = (LsEntry) Filelist.get(i);
				System.out.println(entry.getFilename());
				File file = new File(entry.getFilename());

				if (file.getName().endsWith(".txt")) {
					bis = new BufferedInputStream(sftpChannel.get(file.getName()));
					String home = System.getProperty("user.home");
					new File(home+"/Downloads/FTPZIP/").mkdir();
					File newFile = new File(home+"/Downloads/FTPZIP/" + file.getName()); 
					//File newFile = new File(file.getName());
					if (newFile.createNewFile()){
						System.out.println("File is created!");
						}else{
						System.out.println("File already exists.");
						}
					OutputStream os = new FileOutputStream(newFile);
					BufferedOutputStream bos = new BufferedOutputStream(os);
					int readCount;
					while ((readCount = bis.read(buffer)) > 0) {
						bos.write(buffer, 0, readCount);
					}
					bis.close();
					bos.close();
					System.out.println("File downloaded successfully - " + newFile.getAbsolutePath());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		disconnect();
	}

}