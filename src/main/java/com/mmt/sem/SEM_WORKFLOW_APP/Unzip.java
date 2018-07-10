/*Class to unzip folders that we just downloaded from the SFTP server*/
package com.mmt.sem.SEM_WORKFLOW_APP;

import ch.qos.logback.core.util.COWArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
public class Unzip {

    private String zipFilePath;
    private String destDir;


    @RequestMapping("unzip")
    @ResponseBody
    public BaseResponse unzipping(BaseResponse response)
    {
        String zipDir = response.getZipDir();
        String unzipDir = response.getUnZipDir();
        ArrayList<String> fileArray= response.getFileName();
        response.setFunction("Unzip");
        System.out.print("Unzipping");
        try {
            zipFilePath = zipDir;
            destDir = unzipDir;
            URL destPath = ResourceUtils.getURL("classpath:"+ destDir);
            File dir = new File(String.valueOf(Paths.get(destPath.toURI()).toFile()));
            // create output directory if it doesn't exist
            if (!dir.exists())
                dir.mkdirs();
            FileInputStream fis;
            // buffer for read and write data to file
            byte[] buffer = new byte[1024];

            URL filePath = ResourceUtils.getURL("classpath:"+ zipDir);
            File folder = new File(String.valueOf(Paths.get(filePath.toURI()).toFile()));

            try {
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".zip") && fileArray.contains(listOfFiles[i].getName())) {
                        System.out.println("File " + listOfFiles[i].getName());
                        fis = new FileInputStream(String.valueOf(Paths.get(filePath.toURI()).toFile()) + "\\"+listOfFiles[i].getName());
                        ZipInputStream zis = new ZipInputStream(fis);
                        ZipEntry ze = zis.getNextEntry();
                        while (ze != null) {
                            String fileName = ze.getName();
                            File newFile = new File(String.valueOf(Paths.get(destPath.toURI()).toFile())+"\\" +
                                    fileName);
                            if (!newFile.isDirectory()) {
                                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                                // create directories for sub directories in zip
                                if (newFile.createNewFile()) {
                                    System.out.println("File is created!");
                                } else {
                                    System.out.println("File already exists.");
                                }
                                //TODO
                                response.removeFiles(fileName);
                                response.addFileName(newFile.getName());
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
            }catch(NullPointerException f){
                System.out.println("No files to unzip");
            }
        }catch (Exception a){
            response.setStatus(400);
            response.setMessage(a.toString());
            a.printStackTrace();
        }
        return response;
    }
}
