package com.pdworld.server.em.ui.serverui.userui;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField idText = new JTextField();

    private JTextField nameText = new JTextField();

    private JComboBox dptCom = new JComboBox();

    private JLabel idLabel = new JLabel("编号");

    private JLabel nameLabel = new JLabel("姓名");

    private JLabel dptLabel = new JLabel("部门");

    private JButton searchBtn = new JButton("查询");

    public SearchPanel() {
        init();
    }

    public void init() {

        this.add(idLabel);
        this.add(idText);
        this.add(nameLabel);
        this.add(nameText);
        this.add(dptLabel);
        this.add(dptCom);
        this.add(searchBtn);

        Dimension size = new Dimension(50, 25);
        idLabel.setPreferredSize(size);
        nameLabel.setPreferredSize(size);
        dptLabel.setPreferredSize(size);

        size = new Dimension(150, 25);
        idText.setPreferredSize(size);
        nameText.setPreferredSize(size);
        dptCom.setPreferredSize(size);

        searchBtn.setPreferredSize(new Dimension(80, 25));

    }

    public void setDptCom(String[] dpts) {
        this.dptCom.addItem("");
        if (dpts != null) {
            for (int i = 0; i < dpts.length; i++) {
                this.dptCom.addItem(dpts[i]);
            }
        }
    }

    public HashMap getSearch() {
        HashMap hm = new HashMap();
        hm.put("U_ID", idText.getText());
        hm.put("U_NAME", nameText.getText());
        hm.put("D_NAME", (String) dptCom.getSelectedItem());
        return hm;
    }

    public void addButtonActionListener(ActionListener actionListener) {
        searchBtn.addActionListener(actionListener);
        searchBtn.setActionCommand(UserUIConfig.SEARCHBTN);
    }
}