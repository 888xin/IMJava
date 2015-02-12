package com.pdworld.server.em.ui.serverui.logui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.server.em.pub.LogOper;


/**
 * 日志面板
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class LogUI extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主窗口
     */
    //private JFrame mainFrame;

    /**
     * 历史记录查询面板
     */
    private LogHistoryUI logHistoryUI = new LogHistoryUI();

    /**
     * 当前日志面板
     */
    private TodayLogUI todayLogUI = new TodayLogUI();

    /**
     * 日志面板构造函数
     * @param mainFrame
     */
    public LogUI(JFrame mainFrame) {
        //this.mainFrame = mainFrame;
        init();
    }

    /**
     * 初始化
     *
     */
    public void init() {
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                todayLogUI, logHistoryUI);
        jsp.setOneTouchExpandable(true);
        jsp.setDividerLocation(390);
        this.setLayout(new BorderLayout());
        this.add(jsp, BorderLayout.CENTER);
        this.addButtonActionListener(new LogUIButtonActionListener(this));
        this.addLog(LogOper.getInstance().getLog(PubToolkit.getDate(), null).toString());
    }

    /**
     * 添加按钮监听
     * @param a
     */
    public void addButtonActionListener(ActionListener a) {
        logHistoryUI.addButtonActionListener(a);
    }

    /**
     * 设置历史查询记录
     * @param logs
     */
    public void setLogText(String logs) {
        this.logHistoryUI.setLogText(logs);
    }

    /**
     * 取得当天的日志
     * @return
     */
    public String getLog() {
        return todayLogUI.getTodayLog();
    }

    /**
     * 添加当天的日志
     * @param log
     */
    public void addLog(String log) {
        todayLogUI.addTodayLog(log+"\n");
    }

    /**
     * 取得查询的日期
     * @return
     */
    public String getDate() {
        return logHistoryUI.getDate();
    }

    /**
     * 清空日志
     *
     */
    public void clearLog() {
        todayLogUI.clearLog();
    }

    /**
     * 取得查询的用户ID
     * @return
     */
    public String getChangeUserId(){
        return this.logHistoryUI.getChangeUserId();
    }
}
/**
 * 日志面板的按钮监听类
 * @author Administrator
 *
 */
class LogUIButtonActionListener implements ActionListener {

    private LogUI logUI;

    public LogUIButtonActionListener(LogUI logUI) {
        this.logUI = logUI;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String command = e.getActionCommand();

			/*if (command.equals(LogUIConfig.SAVEBTN)) {
				if (LogOper.getInstance().saveLog(logUI.getLog())) {
					logUI.clearLog();
					PubToolkit.showInformation("恭喜,保存日志成功!");
				} else {
					PubToolkit.showInformation("对不起,保存日志失败!");
				}
				return;
			} else*/ if (command.equals(LogUIConfig.SEARCHBTN)) {
                logUI.setLogText("");
                StringBuffer logs = LogOper.getInstance().getLog(
                        logUI.getDate(), logUI.getChangeUserId());
                if(logs.length()<=0){
                    logUI.setLogText("对不起,没有找到相关记录!");
                    return;
                }
                logUI.setLogText(logs.toString());
            } else if (command.equals(LogUIConfig.CHANGEBTN)) {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(logUI);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    LogOper.getInstance().setLogFileName(chooser.getSelectedFile().getPath());
                }

            }
        }
    }
}
