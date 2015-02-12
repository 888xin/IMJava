package com.pdworld.server.em.ui.serverui.userui;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.pdworld.server.em.ui.serverui.image.GetImage;


public class IconCombobox extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		if (value != null) {
			ImageIcon imageIcon = GetImage.getBigHead(Integer.parseInt((String)value));
			if(imageIcon != null){
				this.setIcon(imageIcon);
			}
		}
		if (isSelected) {
			this.setBackground(list.getSelectionBackground());
			this.setForeground(list.getSelectionForeground());
		} else {
			this.setBackground(list.getBackground());
			this.setForeground(list.getForeground());
		}

		this.setBorder(BorderFactory.createEmptyBorder(3, 3, 0, 3));
		this.setOpaque(true);

		return this;
	}

}