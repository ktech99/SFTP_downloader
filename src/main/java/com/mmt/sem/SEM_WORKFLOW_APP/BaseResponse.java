package com.mmt.sem.SEM_WORKFLOW_APP;


import java.util.ArrayList;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BaseResponse {
    private int status = 200;
    private String msg = "ok";
    private ArrayList<String> fileNames = new ArrayList<>();
    private String function;
    private String SFTPURL;
    private String SFTPUSERNAME;
    private String SFTPPASSWORD;
    private String SFTPPATH;
    private String zipDir;
    private String unzipDir;
    private String HDFSURL;
    private int SFTPPORT;


/*   Return Status for the last operation
     Return:
           400 for Not ok
           200 for ok*/
    public int getStatus() {
        return status;
    }

/*
* Set status for last operation
* 400 for not ok (can't be changed to 200 once set)
* 200 for ok(default)
* @param : status
* */
    public void setStatus(int status) {
        if(this.status != 400){
            this.status = status;
        }
    }

/*
* returns the message for last operation
*   Return:
*           ok for ok
*           %error messqage% for not ok
* */
    public String getMessage() {
        return msg;
    }
/*
* Sets the message for last operation
* ok if ok
* %error message% if not ok (can't be changed once set)
* */
    public void setMessage(String msg) {
        if(this.msg.equals("ok"))
            this.msg = msg;
    }

/*
*Returns list of file names acted on by operation
*/
    public ArrayList<String> getFileName(){
        return fileNames;
    }

/*
* Adds file name acted on by operation
* @param:
*       filename acted on by operation
* */
    public void addFileName(String fileName){
        fileNames.add(fileName);
    }

 /*
 * Removes filename acted on by previous operation
 * @param:
  *       filename acted on by previous operation
 * */
    public void removeFiles(String name){
        fileNames.remove(name);
    }

/*
* Returns the last operation that was run
* */
    public String getFunction(){
        return function;
    }

/*
 * Sets the last operation that was run
 * @param
 *      Operation name
 * */
    public void setFunction(String function){
        this.function = function;
    }

/*
 * Returns the SFTP URL
 * */
    public String getSFTP(){
        return SFTPURL;
    }

/*
 * Sets the SFTP URL
 * @Param
 *      SFTP URL
 * */
    public void setSFTP(String url){
        SFTPURL = url;
    }

/*
 * Returns the SFTP username
 * */
    public String getSFTPUsername(){
        return SFTPUSERNAME;
    }

/*
 * Sets SFTP username
 * @param
 *      SFTP username
 * */
    public void setSFTPUsername(String username){
        SFTPUSERNAME = username;
    }

/*
 * Returns the SFTP password
 * */
    public String getSFTPPassword(){
        return SFTPPASSWORD;
    }

/*
 * Sets the SFTP password
 * @param
 *      SFTP password
 * */
    public void setSFTPPassword(String Password){
        SFTPPASSWORD = Password;
    }

/*
 * Returns the SFTP path
 * */
    public String getSFTPPath(){
        return SFTPPATH;
    }

/*
 * Sets the SFTP path
 * @Param
 *       SFTP path
 * */
    public void setSFTPPath(String SFTPpath){
        SFTPPATH = SFTPpath;
    }

/*
 * Returns the SFTP port
 * */
    public int getSFTPPort(){
        return SFTPPORT;
    }

/*
 * Sets the SFTP port
 * @Param
 *       SFTP port
 * */
    public void setSFTPPort(int port){
        SFTPPORT = port;
    }

/*
 * Returns the directory where the zip files are stored
 * */
    public String getZipDir(){
        return zipDir;
    }

/*
 * Sets the directory where zip files are stored
 * @Param
 *       zip directory path
 * */
    public void setZipDir(String dir){
        zipDir = dir;
    }

/*
 * Returns the directory where the unzipped files are stored
 * */
    public String getUnZipDir(){
        return unzipDir;
    }

/*
 * Sets the directory where unzipped files are stored
 * @Param
 *       unzipped directory path
 * */
    public void setUnZipDir(String dir){
        unzipDir = dir;
    }

/*
 * Returns the HDFS URL
 * */
    public String getHDFSURL(){
        return HDFSURL;
    }

/*
 * Sets the HDFS URL
 * @Param
 *       HDFS URL
 * */
    public void setHDFSURL(String url){
        HDFSURL = url;
    }
}
