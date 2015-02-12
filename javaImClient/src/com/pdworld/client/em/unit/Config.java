package com.pdworld.client.em.unit;

/**
 * 登录窗口配置信息
 *
 * @author Administrator
 */

public class Config {

    /**
     * 服务器IP
     */
    private String serverIP = "";

    /**
     * 服务器端口
     */
    private int port = -1;

    /**
     * @return 返回 port。
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port
     *            要设置的 port。
     */
    public void setPort(int port) {
        this.port =  -1;
        if (port >= 0 && port <= 65535)
            this.port = port;

    }

    /**
     * @return 返回 serverIP。
     */
    public String getServerIP() {
        return serverIP;
    }

    /**
     * @param serverIP
     *            要设置的 serverIP。
     */
    public void setServerIP(String serverIP) {
        if (serverIP == null) {
            this.serverIP = "";
            return;
        }
        String ip[] = serverIP.split("\\.");
        boolean isOK = true;
        if (ip.length == 4) {
            int temp = -1;
            for (int i = 0; i < ip.length; i++) {
                try {
                    temp = Integer.parseInt(ip[i]);
                    if (!(temp >= 0 && temp < 256)) {
                        isOK = false;
                        break;
                    }
                } catch (Exception e) {
                    isOK = false;
                    break;
                }
            }
            if(isOK){
                this.serverIP = serverIP;
            }
        }
    }
}