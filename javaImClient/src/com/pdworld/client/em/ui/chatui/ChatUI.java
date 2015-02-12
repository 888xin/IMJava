package com.pdworld.client.em.ui.chatui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.pdworld.client.em.filetrans.TransFileStock;
import com.pdworld.client.em.filetrans.TransTask;
import com.pdworld.client.em.filetrans.ui.TransFileUI;
import com.pdworld.client.em.pub.ChatLog;
import com.pdworld.client.em.pub.PubValue;
import com.pdworld.client.em.ui.chatui.faceui.FaceIconUI;
import com.pdworld.client.em.ui.chatui.faceui.FaceUI;
import com.pdworld.client.em.ui.images.GetImage;
import com.pdworld.client.em.ui.mainui.MainUI;
import com.pdworld.pub.pack.PackOper;
import com.pdworld.pub.pub.PubToolkit;
import com.pdworld.pub.unit.Company;
import com.pdworld.pub.unit.Department;
import com.pdworld.pub.unit.MessagePack;
import com.pdworld.pub.unit.User;


/**
 * 聊天对话框
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ChatUI extends JFrame implements ActionListener {

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    private static String messCompart = "\u0009";

    private Object object;

    private TopPanel topPanel = new TopPanel();

    private JTextPane chatMessage = new JTextPane();

    private JTextPane sendMessage = new JTextPane();

    private FunctionButtonPanel funBtnPanel = new FunctionButtonPanel();

    private BottomPanel bottomPanel = new BottomPanel();

    private ChatLogWindow chatLogWindow = new ChatLogWindow();

    private RightPanel rightPanel = new RightPanel();

    private FaceUI faceUI = new FaceUI();

    public ChatUI(Object object) {
        this.setObject(object);
        init();
    }

    /**
     * 加载聊天记录
     *
     */
    private void loadChatLog(){
        if(this.object instanceof Company){
            this.chatLogWindow.setMessage(ChatLog.getChatLog(ChatLog.COMPANY, ""));
        }else if(this.object instanceof Department){
            this.chatLogWindow.setMessage(ChatLog.getChatLog(ChatLog.DEPARTMENT,((Department)object).getId()));
        }else if(this.object instanceof User){
            this.chatLogWindow.setMessage(ChatLog.getChatLog(ChatLog.USER,((User)object).getId()));
        }
    }
    /**
     * 初始化
     *
     */
    public void init() {
        this.chatMessage.setEditable(false);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(new JScrollPane(chatMessage),
                BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(400, 150));
        southPanel.setLayout(new BorderLayout());
        southPanel.add(funBtnPanel, BorderLayout.NORTH);
        southPanel.add(new JScrollPane(sendMessage), BorderLayout.CENTER);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        this.getContentPane().add(mainPanel, BorderLayout.CENTER);


        chatLogWindow.setSize(new Dimension(this.getWidth(), 150));
        chatLogWindow.setBounds(this.getX(),
                this.getY() + this.getHeight() + 1, this.getWidth(), 150);
        this.addComponentListener(new ChatUIComponent(this));
        this.addWindowFocusListener(new ChatUIWindowFocusListener(this));
        bottomPanel.addButtonActionListener(this);
        funBtnPanel.addButtonActionListener(this);

        faceUI.addMouseListener(new FaceUIMouse(this));
        faceUI.addWindowListener(new FaceUIWindow(this));
        this.addButtonActionListener(new ChatUIButtonActionListener(this));
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                PubValue.removeChatUI(ChatUI.this);
                ChatUI.this.dispose();
            }
        });
        this.setSize(750, 500);
        loadChatLog();
    }

    /**
     * 设置聊天对象
     * @param object
     */
    public void setObject(Object object) {
        this.object = object;
        topPanel.setObject(object);

        if (object instanceof User) {
            User user = (User) object;
            this.setTitle("你正与" + user.getName() + "(" + user.getId()
                    + ")聊天中...");
            this.funBtnPanel.setFileBtn(true);
            this.rightPanel.setVisible(true);
        } else if (object instanceof Department) {
            Department department = (Department) object;
            this.setTitle("部门:" + department.getName() + "("
                    + department.getId() + ")");
            this.funBtnPanel.setFileBtn(false);
            this.rightPanel.setVisible(false);
        } else if (object instanceof Company) {
            this.setTitle("公司:" + ((Company) object).getName());
            this.funBtnPanel.setFileBtn(false);
            this.rightPanel.setVisible(false);
        }

    }

    /**
     * 添加文件传送面板
     * @param transFileUI
     */
    public void addFileUI(TransFileUI transFileUI){
        this.rightPanel.addTransFileUI(transFileUI);

    }
    /**
     * 删除文件传送面板
     * @param transFileUI
     */
    public void removeFileUI(TransFileUI transFileUI){
        this.rightPanel.removeTransFileUI(transFileUI);
    }

    /**
     * 取得聊天对象
     * @return
     */
    public Object getObject() {
        return this.object;
    }

    /**
     * 添加消息
     * @param msgPack
     */
    public void addMessage(MessagePack msgPack) {
        String nowDateTime = PubToolkit.getDateTime();
        User fromUser = MainUI.getInstance().getUserTree().findUser(
                msgPack.getFrom());
        if(this.object instanceof Company){
            ChatLog.saveChatLog(ChatLog.COMPANY, "", fromUser.getName(), msgPack.getFrom(), nowDateTime, msgPack.getMessage());
        }else if(this.object instanceof Department){
            ChatLog.saveChatLog(ChatLog.DEPARTMENT,((Department)object).getId(), fromUser.getName(), msgPack.getFrom(), nowDateTime, msgPack.getMessage());
        }else if(this.object instanceof User){
            ChatLog.saveChatLog(ChatLog.USER, ((User)object).getId(), fromUser.getName(), msgPack.getFrom(), nowDateTime, msgPack.getMessage());
        }

        this.chatMessage.setEditable(true);
        Document chatDocument = chatMessage.getDocument();


        try {

            chatDocument.insertString(chatDocument.getLength(), fromUser
                    .getName()
                    + "("
                    + msgPack.getFrom()
                    + ")  "
                    + nowDateTime + "\n", null);


            unbindMessage(msgPack.getMessage());
            this.chatMessage.setEditable(false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对消息进行解包
     * @param str
     */
    private void unbindMessage(String str) {
        Document chatDocument = chatMessage.getDocument();
        String[] mess = str.split(messCompart);
        int start = 0;
        StyledDocument styledDocument = this.chatMessage.getStyledDocument();
        try {
            styledDocument.insertString(chatDocument.getLength(), "  ", null);
        } catch (BadLocationException e1) {
            //e1.printStackTrace();
        }
        for (int i = 0; i < mess.length; i++) {
            this.chatMessage.setCaretPosition(styledDocument.getLength());
            if (mess[i].indexOf("[MESSAGE=") >= 0) {
                start = mess[i].indexOf("[MESSAGE=") + "[MESSAGE=".length();
                try {
                    styledDocument.insertString(chatDocument.getLength(),
                            mess[i].substring(start, mess[i]
                                    .indexOf("]", start)), null);
                } catch (BadLocationException e) {
                    //e.printStackTrace();
                }
            } else if (mess[i].indexOf("[IMAGE=") >= 0) {
                start = mess[i].indexOf("[IMAGE=") + "[IMAGE=".length();
                ImageIcon imageIcon = GetImage.getFace(mess[i].substring(start,
                        mess[i].indexOf("]", start)).trim());
                if (imageIcon != null)
                    chatMessage.insertIcon(imageIcon);
            }
        }
    }

    /**
     * 取得要发送的消息
     *
     * @return
     */
    public String getMessage() {
        StringBuffer strBuf = new StringBuffer();
        this.getContent(this.sendMessage.getDocument().getDefaultRootElement(),
                strBuf);
        this.sendMessage.setText("");
        return strBuf.toString();

    }

    /**
     * 拼装要发送的信息
     *
     * @param e
     * @param sb
     */
    private void getContent(Element e, StringBuffer sb) {
        if (!e.isLeaf()) {
            for (int i = 0; i < e.getElementCount(); i++) {
                getContent(e.getElement(i), sb);
            }
        } else {
            AttributeSet as = e.getAttributes().copyAttributes();
            if (e.getName().equals("content")) {
                int start = e.getStartOffset();
                int end = e.getEndOffset();
                String s;
                try {
                    s = e.getDocument().getText(start, end - start);
                    sb.append("[MESSAGE=" + s + "]" + messCompart);
                } catch (BadLocationException e1) {
                    //e1.printStackTrace();
                }

            } else if (e.getName().equals("icon")) {
                Icon icon = StyleConstants.CharacterConstants.getIcon(as);

                String fileName = icon.toString();
                sb.append("[IMAGE="
                        + fileName.substring(fileName.lastIndexOf("/") + 1)
                        + "]" + messCompart);
            }

        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getActionCommand().equals(ChatUIConfig.CHATBTN)) {
                chatLogWindow.setVisible(!chatLogWindow.isVisible());
            }
            if (button.getActionCommand().equals(ChatUIConfig.FACEBTN)) {
                faceUI.setLocation((int) button.getLocationOnScreen().getX(),
                        (int) button.getLocationOnScreen().getY()
                                - faceUI.getHeight());
                faceUI.setVisible(!faceUI.isVisible());
                faceUI.requestFocus();

            }
        }
    }

    /**
     * 插入ICON图标
     * @param iconId
     */
    public void insertIcon(int iconId) {
        ImageIcon imageIcon = GetImage.getFace(iconId);
        if (imageIcon != null) {
            sendMessage.insertIcon(imageIcon);
        }
    }

    /**
     * 取得表情面板
     * @return FaceUI
     */
    public FaceUI getFaceUI() {
        return faceUI;
    }

    /**
     * 取得聊天记录窗口
     * @return ChatLogWindow
     */
    public ChatLogWindow getChatLogWindow() {
        return chatLogWindow;
    }

    /**
     * 添加按钮监听
     * @param actionListener
     */
    public void addButtonActionListener(ActionListener actionListener) {
        bottomPanel.addButtonActionListener(actionListener);
        funBtnPanel.addButtonActionListener(actionListener);
    }

}
/**
 * 聊天窗口按钮监听类
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
class ChatUIButtonActionListener implements ActionListener {

    private ChatUI chatUI;

    public ChatUIButtonActionListener(ChatUI chatUI) {
        this.chatUI = chatUI;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String command = e.getActionCommand();
            if (command.equals(ChatUIConfig.SENDBTN)) {
                MessagePack msgPack = new MessagePack();

                Object object = chatUI.getObject();
                msgPack.setMessage(chatUI.getMessage());
                if (object instanceof Company) {
                    msgPack.setType(PackOper.CHAT_ALL);
                } else if (object instanceof Department) {
                    msgPack.setType(PackOper.CHAT_DEPARTMENT);
                    msgPack.setTo(((Department) object).getId());
                } else if (object instanceof User) {
                    msgPack.setType(PackOper.CHAT_USER);
                    msgPack.setTo(((User) object).getId());
                }
                // System.out.println("---------------Chat---------------");
                // System.out.println("Type: "+msgPack.getType());
                // System.out.println("from :"+msgPack.getFrom());
                // System.out.println("to :"+msgPack.getTo());
                // System.out.println("message: "+msgPack.getMessage());
                // System.out.println("---------------over---------------");
                msgPack.setFrom(PubValue.getUser().getId());
                chatUI.addMessage(msgPack);
                msgPack.setFrom(PubValue.getUser().getId());
                PubValue.getClientThread().sendMessage(msgPack);

            } else if (command.equals(ChatUIConfig.CLOSEBTN)) {
                chatUI.setVisible(false);
                PubValue.removeChatUI(chatUI);
                chatUI.dispose();
            } else if(command.equals(ChatUIConfig.FILEBTN)){
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(chatUI);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
//			       System.out.println(chooser.getSelectedFile());
                    TransTask transTask = TransFileStock.CreateNewSendTrans(this.chatUI, chooser.getSelectedFile(), ((User)this.chatUI.getObject()).getId());
                    //发送"发送文件邀请包"
                    if(transTask == null){
                        PubToolkit.showYes(chatUI, "此文件已经在传送任务中...");
                        return;
                    }
                    MessagePack msgPack = new MessagePack();
                    msgPack.setType(PackOper.UPFILE);
                    msgPack.setFrom(PubValue.getUser().getId());
                    msgPack.setTo(((User)chatUI.getObject()).getId());
                    msgPack.setMessage(transTask.toString());
                    PubValue.getClientThread().sendMessage(msgPack);
                }

            }
        }
    }

}

class FaceUIMouse extends MouseAdapter {
    private ChatUI chatUI;

    public FaceUIMouse(ChatUI chatUI) {
        this.chatUI = chatUI;
    }

    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof FaceIconUI) {
            FaceIconUI faceIcon = (FaceIconUI) e.getSource();
            chatUI.insertIcon(faceIcon.getIconId());
            chatUI.getFaceUI().setVisible(false);
        }
    }
}

class FaceUIWindow extends WindowAdapter {
    private ChatUI chatUI;

    public FaceUIWindow(ChatUI chatUI) {
        this.chatUI = chatUI;
    }

    public void windowDeactivated(WindowEvent e) {
        this.chatUI.getFaceUI().setVisible(false);
    }

    public void windowClosing(WindowEvent e) {
        chatUI.setVisible(false);
        PubValue.removeChatUI(chatUI);
        chatUI.dispose();
    }
}

class ChatUIComponent extends ComponentAdapter {
    private ChatUI chatUI;

    private ChatLogWindow chatLogWindow;

    public ChatUIComponent(ChatUI chatUI) {
        this.chatUI = chatUI;
        this.chatLogWindow = chatUI.getChatLogWindow();
    }

    public void componentResized(ComponentEvent e) {
        chatLogWindow.setBounds(chatUI.getX(), chatUI.getY()
                + chatUI.getHeight() + 1, chatUI.getWidth(), chatLogWindow
                .getHeight());
        chatLogWindow.validate();
    }

    public void componentMoved(ComponentEvent e) {
        chatLogWindow.setBounds(chatUI.getX(), chatUI.getY()
                + chatUI.getHeight() + 1, chatUI.getWidth(), chatLogWindow
                .getHeight());
    }
}

class ChatUIWindowFocusListener implements WindowFocusListener {

    private ChatUI chatUI;

    private boolean isShow;

    public ChatUIWindowFocusListener(ChatUI chatUI) {
        this.chatUI = chatUI;
    }

    public void windowGainedFocus(WindowEvent e) {
        chatUI.getChatLogWindow().setVisible(isShow);
    }

    public void windowLostFocus(WindowEvent e) {
        isShow = chatUI.getChatLogWindow().isVisible();
        chatUI.getChatLogWindow().setVisible(false);
    }
}