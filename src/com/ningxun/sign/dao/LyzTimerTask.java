package com.ningxun.sign.dao;

import java.util.List;
import java.util.TimerTask;
import java.util.Date;

/**
*<li>技术支持:河北凝讯科技有限公司<br/>
*<li>项目名称: strut2ajaxDemo<br/>
*<li>文件名: LyzTimerTask.java<br/>
*<li>创建时间: 2017年7月27日 下午1:53:06<br/>
*
*@author 纪梦强
*@version [v1.00]
*/ 
public class LyzTimerTask extends TimerTask {
    private signDao signDao = new signDao();
    public void run() {  
        try {  
             //在这里写你要执行的内容  
            //System.out.println("执行当前时间"+new Date().getTime());
            List<Integer> findUserId = signDao.findUserId();
            Date date = new Date();
            for (int i = 0; i < findUserId.size(); i++) {
            	signDao.countSign(findUserId.get(i),date);
			}
        } catch (Exception e) {  
            System.out.println("-------------解析信息发生异常--------------");
        }  
    }  
       
} 
