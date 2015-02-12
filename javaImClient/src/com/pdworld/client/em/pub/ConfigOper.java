package com.pdworld.client.em.pub;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.pdworld.client.em.unit.Config;


/**
 * 客户端配置文件操作类
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ConfigOper {

    /**
     * 默认配置信息存储文件名
     */
    private static String fileName = "Config.ini" ;


    /**
     * 受保护的构造函数
     *
     */
    private ConfigOper() {

    }

    /**
     * 保存配置信息
     * @param config
     * @return
     */
    public static boolean saveConfig(Config config) {
        FileOutputStream fos = null;
        Properties prop = new Properties();
        try {
            fos = new FileOutputStream(fileName);
            prop.setProperty("ServerIP", config.getServerIP());
            prop.setProperty("Port", "" + config.getPort());
            prop.store(fos, "");
            return true;
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {

                }
            }
        }
        return false;
    }

    /**
     * 提取配置信息
     * @return
     */
    public static Config getConfig() {
        Config config = new Config();
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
            config.setServerIP(prop.getProperty("ServerIP"));

            System.out.println("Port : "+prop.getProperty("Port"));
            config.setPort(Integer.parseInt(prop.getProperty("Port")));
        } catch (Exception e) {
            return config;
        }
        return config;
    }

}