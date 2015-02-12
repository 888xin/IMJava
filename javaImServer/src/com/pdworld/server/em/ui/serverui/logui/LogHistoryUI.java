package com.pdworld.server.em.ui.serverui.logui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.pdworld.pub.unit.User;
import com.pdworld.server.em.dao.factory.UserDaoFactory;


public class LogHistoryUI extends JPanel implements ItemListener {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private JComboBox yearCom = new JComboBox();

    private JComboBox monthCom = new JComboBox();

    private JComboBox dayCom = new JComboBox();

    private JLabel yearLabel = new JLabel("年");

    private JLabel monthLabel = new JLabel("月");

    private JLabel dayLabel = new JLabel("日");

    private JButton searchBtn = new JButton("查询");

    private JButton changeBtn = new JButton("选择");

    private JTextArea logText = new JTextArea();

    private JComboBox userIdCom = new JComboBox();

    public LogHistoryUI() {
        init();
    }

    public void init() {
        this.logText.setEditable(false);
        JPanel btnPanel = new JPanel();
        Box box = Box.createHorizontalBox();

        box.add(yearCom);
        box.add(yearLabel);
        box.add(monthCom);
        box.add(monthLabel);
        box.add(dayCom);
        box.add(dayLabel);
        box.add(userIdCom);
        box.add(searchBtn);
        box.add(changeBtn);

        Dimension size = new Dimension(60, 25);
        yearLabel.setPreferredSize(size);
        monthLabel.setPreferredSize(size);
        dayLabel.setPreferredSize(size);

        size = new Dimension(80, 25);
        yearCom.setPreferredSize(size);
        monthCom.setPreferredSize(size);
        dayCom.setPreferredSize(size);

        btnPanel.setLayout(new BorderLayout());
        btnPanel.add(box, BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
        this.add(btnPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(logText), BorderLayout.CENTER);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.WHITE), "历史日志"));

        this.initData();
        this.initUserId();
        this.monthCom.addItemListener(this);
        this.yearCom.addItemListener(this);
    }

    private void initData() {
        for (int i = 2000; i <= 2020; i++) {
            yearCom.addItem("" + i);
        }
        monthCom.addItem("");
        for (int i = 1; i <= 12; i++) {
            monthCom.addItem("" + i);
        }
        dayCom.addItem("");
        for (int i = 1; i <= 31; i++) {
            dayCom.addItem("" + i);
        }
    }

    private void initUserId() {
        Iterator it = UserDaoFactory.getUserDao().select(null).iterator();
        User user;
        Object object;
        userIdCom.addItem("");
        while (it.hasNext()) {
            object = it.next();
            if (object instanceof User) {
                user = (User) object;
                userIdCom.addItem(user.getId());
            }
        }
    }

    public String getChangeUserId() {
        return (String) userIdCom.getSelectedItem();
    }

    public void setLogText(String logText) {
        if (logText == null) {
            logText = "";
        }
        this.logText.setText(logText);
    }

    public void addButtonActionListener(ActionListener a) {
        searchBtn.addActionListener(a);
        searchBtn.setActionCommand(LogUIConfig.SEARCHBTN);
        changeBtn.addActionListener(a);
        changeBtn.setActionCommand(LogUIConfig.CHANGEBTN);
    }

    public void itemStateChanged(ItemEvent e) {
        int year = Integer.parseInt((String) yearCom.getSelectedItem());
        int day = 31;
        try {
            int month = Integer.parseInt((String) monthCom.getSelectedItem());

            if (month <= 7 && month % 2 == 1) {
                day = 31;
            } else if (month <= 7 && month % 2 == 0) {
                day = 30;
            }
            if (month == 2) {
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                    day = 29;
                } else {
                    day = 28;
                }
            }
        } catch (Exception e1) {
        }
        dayCom.removeAllItems();
        dayCom.addItem("");
        for (int i = 1; i <= day; i++) {
            dayCom.addItem(i + "");
        }

    }

    public String getYear() {
        return (String) yearCom.getSelectedItem();
    }

    public String getMonth() {
        return (String) monthCom.getSelectedItem();
    }

    public String getDay() {
        return (String) dayCom.getSelectedItem();
    }

    public String getDate() {
        String month = (String) monthCom.getSelectedItem();
        if (month.length() == 1)
            month = "0" + month;
        String day = (String) dayCom.getSelectedItem();
        if (day.length() == 1)
            day = "0" + day;
        if (((String) monthCom.getSelectedItem()).equals("")) {
            return (String) yearCom.getSelectedItem();
        } else if (((String) dayCom.getSelectedItem()).equals("")) {
            return (String) yearCom.getSelectedItem() + "-" + month;
        } else
            return (String) yearCom.getSelectedItem() + "-" + month + "-" + day;
    }
}

