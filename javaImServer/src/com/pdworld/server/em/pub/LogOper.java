package com.pdworld.server.em.pub;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.User;

/**
 * 日志操作类
 * @author Administrator
 */
public class LogOper {
    /**
     * 采用单一模式
     */
    private static LogOper logOper;

    /**
     * 日志默认保存文件名
     */
    private String logFileName = "server.log";

    /**
     * 受保护的日志类构造函数
     *
     */
    private LogOper() {
    }

    /**
     * 单一模式取得实例的静态方法
     * @return LogOper
     */
    public static LogOper getInstance() {
        if (logOper == null)
            logOper = new LogOper();
        return logOper;
    }

    /**
     * 添加某用户上线信息
     * @param user
     * @return
     */
    public String insertOnLineLog(User user) {
        String str = "[" + PubToolkit.getDateTime() + "]" + "ID:"
                + user.getId() + "用户:" + user.getName() + " 上线";
        this.saveLog(str);
        return str;
    }

    /**
     * 添加某用户下线信息
     * @param user
     * @return
     */
    public String insertDownLineLog(User user) {
        String str = "[" + PubToolkit.getDateTime() + "]" + "ID:"
                + user.getId() + "用户:" + user.getName() + " 下线";
        this.saveLog(str);
        return str;
    }

    /**
     * 添加某用户被强制下线信息
     * @param user
     * @return
     */
    public String insertForceDownLineLog(User user) {
        String str = "[" + PubToolkit.getDateTime() + "]" + "ID:"
                + user.getId() + "用户:" + user.getName() + " 被强制下线";
        this.saveLog(str);
        return str;
    }

    /**
     * 添加消息服务器启动信息
     * @return
     */
    public String insertStartServerLog() {
        String str = "[" + PubToolkit.getDateTime() + "]" + " 消息服务器启动成功.";
        this.saveLog(str);
        return str;
    }

    /**
     * 添加消息服务器关闭信息
     * @return
     */
    public String insertStopServerLog() {
        String str = "[" + PubToolkit.getDateTime() + "]" + " 消息服务器关闭成功.";
        this.saveLog(str);
        return str;
    }

    /**
     * 添加消息服务器启动失败信息
     * @return
     */
    public String insertStartServerErrorLog() {
        String str = "[" + PubToolkit.getDateTime() + "]" + " 消息服务器启动失败.";
        this.saveLog(str);
        return str;
    }

    /**
     * 添加消息服务器关闭失败信息
     * @return
     */
    public String insertStopServerErrorLog() {
        String str = "[" + PubToolkit.getDateTime() + "]" + " 消息服务器关闭失败.";
        this.saveLog(str);
        return str;
    }

    /**
     * 查询日志
     * @param data 日期
     * @param id   某用户ID,为空则查询"DATA"所指日期的所有记录
     * @return
     */
    public StringBuffer getLog(String data, String id) {
        StringBuffer strBuf = new StringBuffer();
        FileReader reader = null;
        BufferedReader bufReader = null;
        String temp = null;
        if(id == null)
            id = "";
        if(data == null)
            return strBuf;
        try {
            reader = new FileReader(this.logFileName);
            bufReader = new BufferedReader(reader);
            while ((temp = bufReader.readLine()) != null) {
                if (temp.indexOf(data) >= 0 && temp.indexOf(id) >= 0) {
                    strBuf.append(temp + "\n");
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                }
                bufReader = null;
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                reader = null;
            }
        }
        return strBuf;
    }

    /**
     * 保存日志
     * @param log
     * @return
     */
    public boolean saveLog(String log) {
        FileWriter writer = null;
        BufferedWriter bufWriter = null;

        try {
            writer = new FileWriter(logFileName, true);
            bufWriter = new BufferedWriter(writer);
            bufWriter.write(log + "\n");
            bufWriter.flush();
            return true;
        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            if (bufWriter != null) {
                try {
                    bufWriter.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                bufWriter = null;
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                writer = null;
            }
        }
        return false;
    }

    /**
     * 设置保存日志的文件
     * @param logFileName
     */
    public void setLogFileName(String logFileName) {
        File file = new File(logFileName);
        if (file.isFile()) {
            this.logFileName = logFileName;
        }
    }

}
