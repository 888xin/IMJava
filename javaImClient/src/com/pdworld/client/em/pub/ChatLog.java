package com.pdworld.client.em.pub;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 聊天记录操作
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ChatLog {

    public static final String SEPARATOR = "\u0000";

    public static final String ENDSEPARATOR = "\u0001";

    public static String COMPANY = "COMPANY";

    public static String DEPARTMENT = "DEPARTMENT";

    public static String USER = "USER";

    private static String chatLogFileName = "ChatLog.dat";

    private ChatLog() {
    }

    /**
     * 根据聊天类型与聊天ID,返回其聊天记录列表
     *
     * @return
     */
    public static List getChatLog(String chatType, String chatId) {
        List chatLogList = new ArrayList();
        FileReader fileReader = null;
        BufferedReader bufReader = null;

        try {
            fileReader = new FileReader(PubValue.getUser() + "/"
                    + chatLogFileName);
            bufReader = new BufferedReader(fileReader);
            String bufStr = null;

            if (chatType.equals(ChatLog.COMPANY)) {
                while ((bufStr = readOneChatLog(bufReader)) != null) {
                    if (bufStr.indexOf("[" + chatType + "]") >=0) {
                        chatLogList.add(bufStr);
                    }
                }
            } else if (chatType.equals(ChatLog.DEPARTMENT)
                    || chatType.equals(ChatLog.USER)) {
                while ((bufStr = readOneChatLog(bufReader)) != null) {
                    if (bufStr.indexOf("[" + chatType + "=" + chatId + "]") >= 0) {
                        chatLogList.add(bufStr);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e1) {
                    //	e1.printStackTrace();
                }
            }

            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e1) {
                    //e1.printStackTrace();
                }
            }
        }

        return chatLogList;
    }

    /**
     * 读取聊天记录中的一条
     * @param bufReader
     * @return
     * @throws IOException
     */
    private static String readOneChatLog(BufferedReader bufReader) throws IOException {
        StringBuffer buf = new StringBuffer();
        int ch = '\u0000';
        while ((ch = bufReader.read()) != -1) {

            if(ch == ChatLog.SEPARATOR.charAt(0)){
                while ((ch = bufReader.read()) != -1) {
                    if(ch != ChatLog.ENDSEPARATOR.charAt(0) ){
                        buf.append((char)ch);
                    }else {
//						System.out.println("ReadOne:"+buf.toString());
                        if(buf.length() == 0)
                            return null;
                        else
                            return buf.toString();
                    }
                }
            }
        }
        return null;

    }

    /**
     * 保存聊天记录
     * @param chatType 聊天类型:公司,部门,用户
     * @param chatId   聊天对象的ID
     * @param speakeName 发言人
     * @param speakId	发言人ID
     * @param date	日期
     * @param chatMsg	内容
     * @return
     */
    public static boolean saveChatLog(String chatType, String chatId,
                                      String speakeName, String speakId, String date, String chatMsg) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(PubValue.getUser() + "/"
                    + chatLogFileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            if (chatType.equals(ChatLog.COMPANY)) {
                bufferedWriter.write(SEPARATOR + "[" + chatType
                        + "][SPEAKNAME=" + speakeName + "][SPEAKID=" + speakId
                        + "][DATE=" + date + "][MESSAGE=" + chatMsg + "]"+ChatLog.ENDSEPARATOR);
                bufferedWriter.flush();
            } else if (chatType.equals(ChatLog.USER)
                    || chatType.equals(ChatLog.DEPARTMENT)) {
                bufferedWriter.write(SEPARATOR + "[" + chatType + "=" + chatId
                        + "][SPEAKNAME=" + speakeName + "][SPEAKID=" + speakId
                        + "][DATE=" + date + "][MESSAGE=" + chatMsg + "]"
                        + ChatLog.ENDSEPARATOR);

                bufferedWriter.flush();
            }

            return true;
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            //e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e1) {
                    //					e1.printStackTrace();
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e1) {
                    //					e1.printStackTrace();
                }
            }
        }

        return false;
    }
}