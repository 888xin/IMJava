package com.pdworld.client.em.filetrans.ui;

import javax.swing.JPanel;
/**
 * 传送文件面板
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class TransFileUI extends JPanel{

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -1998373283323693024L;

    //拒绝
    public void setClose(){}
    //传送成功
    public void setTransFinish(){
    }
    //设置进度
    public void setPlan(int plan){
    }
    //设置开始
    public void setStart(){
    }
    //异常失败,出错
    public void setTransFail(String errorMsg){
    }
    //是否取消传送
    public boolean isCancel(){
        return false;
    }
    //取消传送
    public void setCancel(){
    }

    //设置最大传送数值
    public void setMaxValue(long maxValue){
    }

}
