package com.pdworld.client.em.filetrans.ui;

public interface FileUIInterface {

    public static final String SERVERIPERROR = "SERVERIPERROR";

    public static final String FILENOEXISTS = "FILENOEXISTS";

    public static final String RECEIVEDEFUSE = "RECEIVEDEFUSE";

    public static final String EXCEPTIONERROR = "EXCEPTIONERROR";

    //传送成功
    public void setTransFinish();
    //设置进度
    public void setPlan(int plan);
    //不进行传送
    public void setClose();
    //设置开始
    public void setStart();
    //异常失败,出错
    public void setTransFail(String errorMsg);
    //是否取消传送
    public boolean isCancel();
    //取消传送
    public void setCancel();


}
