package com.pdworld.server.em.pub;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pdworld.pub.pub.Parameter;
import com.pdworld.pub.unit.Company;
import com.pdworld.pub.unit.User;
import com.pdworld.server.em.dao.factory.UserDaoFactory;
import com.pdworld.server.em.service.ClientThread;
import com.pdworld.server.em.service.MessageServer;

/**
 * 服务公共信息类
 */
public class PubValue {

    /**
     * 公司
     */
    public static Company company = new Company("湖南平地信息技术", "系统管理");

    /**
     * 记录所有在线用户的连接线程
     */
    private static Map onLineUser = new HashMap();

    /**
     * 消息服务器
     */
    private static MessageServer messageServer;

    /**
     * 取得消息服务器的引用
     * @return
     */
    public static MessageServer getMessageServer(){
        return messageServer;
    }

    /**
     * 设置消息服务器
     * @param messageServer
     */
    public static void setMessageServer(MessageServer messageServer){
        PubValue.messageServer = messageServer;
    }

    /**
     * 取得所有在线用户的线程迭代器
     * @return
     */
    public static Iterator getOnLineUserThread(){
        return onLineUser.values().iterator();
    }

    /**
     * 根据某个用户的ID,取得相应的线程
     * @param id
     * @return
     */
    public static ClientThread getUserThread(String id){
        ClientThread clientThread = (ClientThread)onLineUser.get(id);
        return clientThread;
    }

    /**
     * 根据用户ID,与相应的线程,添加到在线用记线程列表中
     * @param id
     * @param thread
     */
    public static void addUserThread(String id, Thread thread){
        onLineUser.put(id,thread);
    }

    /**
     * 根据用户ID,从在线用户列表中删除相应的线程
     * @param id
     */
    public static void deleteUserThread(String id){
        onLineUser.remove(id);
    }

    /**
     * 清除所有在线用户的线程列表
     *
     */
    public static void removeAll(){
        onLineUser.clear();
    }

    /**
     * 让所有在线用户下线
     *
     */
    public static void setAllUserDown(){
        HashMap hm = new HashMap();
        hm.put("U_ISONLINE", "" + 1);
        List list = UserDaoFactory.getUserDao().select(hm);
        Iterator it = list.iterator();
        while(it.hasNext()){
            User user = (User)it.next();
            UserDaoFactory.getUserDao().setOnline(user.getId(), Parameter.NOTONLINED);
        }
    }

}
