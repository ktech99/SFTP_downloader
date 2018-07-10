/*This class downloads all the zip files from the given SFTP server and path to the FTPZIP folder*/
package com.mmt.sem.SEM_WORKFLOW_APP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.Session;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller

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
    private String zipDir;


    //Method to connect to the host
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

    //method to disconnect from the server
    public void disconnect() {
        System.out.println("disconnecting...");
        sftpChannel.disconnect();
        channel.disconnect();
        session.disconnect();
    }


    /* method that downloads all the zip files from the server
     * @param
     * 		Baseresponse object
     * */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse download(@RequestBody BaseResponse resp) {
        this.host = resp.getSFTP();
        this.port = resp.getSFTPPort();
        this.user = resp.getSFTPUsername();
        this.password = resp.getSFTPPassword();
        this.SFTPWORKINGDIR = resp.getSFTPPath();
        this.zipDir = resp.getZipDir();

        byte[] buffer = new byte[1024];
        BufferedInputStream bis;
        connect();

        resp.setFunction("Download:");
        try {
            for (int i = 0; i < Filelist.size(); i++) {
                LsEntry entry = (LsEntry) Filelist.get(i);
                File file = new File(entry.getFilename());
                //only files that end with .zip
                if (file.getName().endsWith(".zip")) {
                    resp.addFileName(entry.getFilename());
                    System.out.println("downloading: " + entry.getFilename());
                    bis = new BufferedInputStream(sftpChannel.get(file.getName()));
                    URL filePath = ResourceUtils.getURL("classpath:" + zipDir);
                    //checking for file already existing and create a new file if it doesn't
                    File newFile = new File(String.valueOf(Paths.get(filePath.toURI()).toFile()) + "\\" +
                            file.getName());
                    if (newFile.createNewFile()) {
                        System.out.println("File is created!");
                    } else {
                        System.out.println("File already exists.");
                    }
                    OutputStream os = new FileOutputStream(newFile);
                    BufferedOutputStream bos = new BufferedOutputStream(os);
                    int readCount;
                    //writing to the file
                    while ((readCount = bis.read(buffer)) > 0) {
                        bos.write(buffer, 0, readCount);
                    }
                    bis.close();
                    bos.close();
                }
            }
        } catch (Exception e) {
            resp.setMessage(e.toString());
            resp.setStatus(400);
        }
        disconnect();
        return resp;
    }

}