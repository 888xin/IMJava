package com.pdworld.client.em.filetrans;

import java.io.File;

import com.pdworld.client.em.filetrans.ui.ReceiveFileUI;
import com.pdworld.client.em.filetrans.ui.SendFileUI;
import com.pdworld.client.em.filetrans.ui.TransFileUI;
import com.pdworld.client.em.pub.PubValue;


/**
 * 文件传送任务
 */
public class TransTask {

    /**
     * 文件传送任务任务:发送
     */
    private static final String SEND = "SEND";

    /**
     * 文件传送任务类型:接受
     */
    private static final String RECEIVE = "RECEIVE";

    /**
     * 文件传送类型,发送或者接受
     */
    private String taskType;

    /**
     * 文件传送控制界面
     */
    private TransFileUI fileUI;

    /**
     * 发送文件
     */
    private File sendFile;

    /**
     * 接受文件
     */
    private File receiveFile;

    /**
     * 文件长度
     */
    private long fileLength;

    /**
     * 发送方ID
     */
    private String sendId;

    /**
     * 接受方ID
     */
    private String receiveId;

    /**
     * 生成一个发送文件任务
     * @param sendFile 		要发送的文件
     * @param fileLength	文件的长度
     * @param sendId		发送方ID
     * @param receiveId		接受方ID
     */
    public TransTask(File sendFile,	long fileLength, String sendId, String receiveId) {
        this.sendFile = sendFile;
        this.fileLength = fileLength;
        this.sendId = sendId;
        this.receiveId = receiveId;
        this.taskType = TransTask.SEND;
        this.createFileUI();
        this.fileUI.setMaxValue(this.fileLength);
    }

    /**
     * 生成一个接爱文件任务
     * @param take 发送文件标识
     */
    public TransTask(String take) {
        this.unbindTake(take);
        this.taskType = TransTask.RECEIVE;
        this.createFileUI();
        this.fileUI.setMaxValue(this.fileLength);
    }

    /**
     * 生成一个文件传送控制界面
     */
    private void createFileUI(){
        if(this.taskType.equals(TransTask.SEND)){
            this.fileUI = new SendFileUI(this);
        }else if(this.taskType.equals(TransTask.RECEIVE)){
            this.fileUI = new ReceiveFileUI(this);
        }
    }

    /**
     * 解析任务
     * @param take
     */
    private void unbindTake(String take) {
        int start = take.indexOf("SENDID=") + "SENDID=".length();
        this.sendId = take.substring(start, take.indexOf("]", start));
        start = take.indexOf("RECEIVEID=") + "RECEIVEID=".length();
        this.receiveId = take.substring(start, take.indexOf("]", start));
        start = take.indexOf("FILENAME=") + "FILENAME=".length();
        this.sendFile = new File(take.substring(start, take.indexOf("]", start)));
        start = take.indexOf("FILELENGTH=") + "FILELENGTH=".length();
        this.fileLength = Integer.parseInt(take.substring(start, take.indexOf(
                "]", start)));
        this.receiveFile = new File((new File(PubValue.getUser().getId()).getAbsoluteFile())+"/"+sendFile.getName());
    }

    public String toString() {
        return "[SENDID=" + sendId + "][RECEIVEID=" + receiveId + "][FILENAME="
                + sendFile                                                                                            + "][FILELENGTH=" + fileLength + "]";
    }

    public int hashCode(){
        return this.toString().hashCode();
    }

    public boolean equals(Object object) {
        return this.toString().equals(object.toString());
    }

    /**
     * @return 返回 fileUI。
     */
    public TransFileUI getFileUI() {
        return fileUI;
    }

    /**
     * @param fileUI 要设置的 fileUI。
     */
    public void setFileUI(TransFileUI fileUI) {
        this.fileUI = fileUI;
    }

    /**
     * @return 返回 sendFile。
     */
    public File getSendFile() {
        return sendFile;
    }

    /**
     * @param sendFile 要设置的 sendFile。
     */
    public void setSendFile(File sendFile) {
        this.sendFile = sendFile;
    }

    /**
     * @return 返回 receiveFile。
     */
    public File getReceiveFile() {
        return receiveFile;
    }

    /**
     * @param receiveFile 要设置的 receiveFile。
     */
    public void setReceiveFile(File receiveFile) {
        this.receiveFile = receiveFile;
    }

    /**
     * @return 返回 fileLength。
     */
    public long getFileLength() {
        return fileLength;
    }

    /**
     * @param fileLength 要设置的 fileLength。
     */
    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    /**
     * @return 返回 sendId。
     */
    public String getSendId() {
        return sendId;
    }

    /**
     * @param sendId 要设置的 sendId。
     */
    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    /**
     * @return 返回 receiveId。
     */
    public String getReceiveId() {
        return receiveId;
    }

    /**
     * @param receiveId 要设置的 receiveId。
     */
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }


}
