package com.pdworld.client.em.pub;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.pdworld.client.em.thread.ClientThread;
import com.pdworld.client.em.ui.chatui.ChatUI;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.unit.Company;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.User;

/**
 * 公共参数类
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class PubValue {

    /**
     * 聊天窗口列表
     */
    private static ArrayList chatUIList = new ArrayList();

    /**
     * 客户消息服务线程
     */
    private static ClientThread clientThread;

    /**
     * 用户信息
     */
    private static User user;

    /**
     * 设置用户信息
     * @param user
     */
    public static void setUser(User user) {
        PubValue.user = user;
        createUserDir(user.getId());
    }

    /**
     * 创建用户目录
     * @param dir
     * @return
     */
    private static boolean createUserDir(String dir){
        File file = new File(dir);
        System.out.println("Dir="+file.getAbsolutePath());
        if(!file.exists()){
            file.mkdir();
            return true;
        }
        return false;
    }

    /**
     * 取得用户信息
     * @return User
     */
    public static User getUser() {
        return PubValue.user;
    }

    /**
     * 取得消息服务线程
     * @return
     */
    public static ClientThread getClientThread() {
        return clientThread;
    }

    /**
     * 设置消息服务线程
     * @param clientThread
     */
    public static void setClientThread(ClientThread clientThread) {
        PubValue.clientThread = clientThread;
    }

    /**
     * 添加聊天窗口引用
     * @param chatUI
     */
    public static void addChatUI(ChatUI chatUI) {
        chatUIList.add(chatUI);
    }

    /**
     * 删除聊天窗口引用
     * @param chatUI
     */
    public static void removeChatUI(ChatUI chatUI) {
        Iterator it = chatUIList.iterator();
        while (it.hasNext()) {
            if (it.next() == chatUI) {
                it.remove();
                return;
            }
        }
    }

    // 通过聊天对象找到相应对话框,也就是双击了树节点,如果这个节点的窗口已经打开就不必新开一个,而是让这个窗口至前
    /**
     * 根据聊天对象类型取得聊天窗口引用
     */
    public static ChatUI getChatUIForObject(Object object) {
        Iterator it = chatUIList.iterator();
        while (it.hasNext()) {
            ChatUI chatUI = (ChatUI) it.next();
            if (chatUI.getObject().equals(object)
                    && object.getClass().getName().equals(
                    chatUI.getObject().getClass().getName())) {
                return chatUI;
            }
        }
        return null;
    }

    /**
     * 根据用户ID,查找相应的对话窗口
     * @param id
     * @return
     */
    public static ChatUI getChatUIForId(String id){
        Iterator it = chatUIList.iterator();
        while (it.hasNext()) {
            ChatUI chatUI = (ChatUI) it.next();
            Object object = chatUI.getObject();
            if (object instanceof User && ((User)object).getId().equals(id)) {
                return chatUI;
            }
        }
        return null;
    }


    // 通过消息找到相应的对话框
    /**
     * 聊天类型:公司,部门,用户 与相应的ID号,取得窗口引用
     */
    public static ChatUI getChatUIForMsg(String e, String id) {
        if (e.equals(PackOper.CHAT_ALL)) {
            Iterator it = chatUIList.iterator();
            while (it.hasNext()) {
                ChatUI chatUI = (ChatUI) it.next();
                Object object = chatUI.getObject();
                if (object instanceof Company) {
                    return chatUI;
                }
            }
        } else if (e.equals(PackOper.CHAT_DEPARTMENT)) {
            Iterator it = chatUIList.iterator();
            while (it.hasNext()) {
                ChatUI chatUI = (ChatUI) it.next();
                Object object = chatUI.getObject();
                if (object instanceof Department) {
                    if (((Department) object).getId().equals(id))
                        return chatUI;
                }
            }
        } else if (e.equals(PackOper.CHAT_USER)) {
            Iterator it = chatUIList.iterator();
            while (it.hasNext()) {
                ChatUI chatUI = (ChatUI) it.next();
                Object object = chatUI.getObject();
                if (object instanceof User) {
                    if (((User) object).getId().equals(id))
                        return chatUI;
                }
            }
        }
        return null;
    }

    /**
     * 关闭所有的聊天窗口
     *
     */
    public static void closeAllChatUI() {
        Iterator it = chatUIList.iterator();
        while (it.hasNext()) {
            ChatUI chatUI = (ChatUI) it.next();
            chatUI.setVisible(false);
            chatUI.dispose();
        }
    }

}
