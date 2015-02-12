package com.pdworld.client.em.filetrans;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.pdworld.client.em.filetrans.ui.ReceiveFileUI;
import com.pdworld.client.em.filetrans.ui.SendFileUI;
import com.pdworld.client.em.pub.PubValue;
import com.pdworld.client.em.ui.chatui.ChatUI;


/**
 * 文件任务队伍
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class TransFileStock {

    /**
     * 发送文件任务队伍
     */
    private static Map sendTransMap = new HashMap();

    /**
     * 接收文件任务队伍
     */
    private static Map receiveTransMap = new HashMap();


    /**
     * 通过发送文件任务标识,取得这个标识的实例
     * @param trans
     * @return TransTask
     */
    public static TransTask getSendFileUI(String trans) {
        return (TransTask) sendTransMap.get(trans);
    }

    /**
     * 通过接收文件任务标识,取得这个标识的实例
     * @param trans
     * @return TransTask
     */
    public static TransTask getReceiveFileUI(String trans) {
        return (TransTask) receiveTransMap.get(trans);
    }

    /**
     * 通过任务唯一标识删除一个发送文件任务
     * @param trans
     */
    public static void removeSendTrans(String trans) {
        sendTransMap.remove(trans);
    }

    /**
     * 通过任务唯一标识删除一个接收文件任务
     * @param trans
     */
    public static void removeReceiveTrans(String trans) {
        receiveTransMap.remove(trans);
    }


    /**
     * 创建一个发送文件任务
     * @param chatUI
     * @param sendFile
     * @param receiveId
     * @return TransTask
     */
    public static TransTask CreateNewSendTrans(ChatUI chatUI, File sendFile,
                                               String receiveId) {
        long fileLength = getFileLength(sendFile);
        if (fileLength == -1)
            return null;
        TransTask transTask = new TransTask(sendFile, fileLength, PubValue.getUser().getId(), receiveId);
        ((SendFileUI) transTask.getFileUI()).setChatUI(chatUI);
        if(sendTransMap.get(transTask.toString())!=null){
            return null;
        }
        sendTransMap.put(transTask.toString(), transTask);
        chatUI.addFileUI(transTask.getFileUI());

        return transTask;
    }

    /**
     *  创建一个接受文件任务
     * @param chatUI
     * @param trans
     * @return TransTask
     */
    public static TransTask CreateNewReceiveTrans(ChatUI chatUI, String trans) {
        TransTask transTask = new TransTask(trans);
        ((ReceiveFileUI) transTask.getFileUI()).setChatUI(chatUI);
        receiveTransMap.put(transTask.toString(), transTask);
        chatUI.addFileUI(transTask.getFileUI());
        return transTask;
    }

    /**
     * 取得文件的长度
     * @param file
     * @return
     * 			-1 表示无此文件
     */
    private static long getFileLength(File file) {
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return -1;
    }

}
