package com.pdworld.server.em.ui.serverui.logui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TodayLogUI extends JPanel {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private JTextArea todayLogText = new JTextArea();


    //private JButton saveBtn = new JButton("保存");

    public TodayLogUI() {
        init();
    }

    public void init() {
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(todayLogText);
        this.todayLogText.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add(jsp, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        //btnPanel.add(saveBtn);


        this.add(btnPanel, BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.WHITE), "当今日志"));

    }

    public void addTodayLog(String log) {
        if (log != null) {
            todayLogText.setText(todayLogText.getText() + log);
        }
    }

    public String getTodayLog() {
        return todayLogText.getText();
    }

    public void clearLog(){
        todayLogText.setText("");
    }

//	public void addButtonActionListener(ActionListener a) {
//		saveBtn.addActionListener(a);
//		saveBtn.setActionCommand(LogUIConfig.SAVEBTN);
//	}
}
