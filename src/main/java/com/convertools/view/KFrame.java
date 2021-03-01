package com.convertools.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author fangkun
 * @date 2021/2/27 14:54
 * @description:
 */
public class KFrame extends WindowAdapter {
    Frame f = new Frame();
    public void display(){
        f.setTitle("MyFrame");
        f.setSize(480,200);
        f.setLocation (200,400);
        f.setBackground (Color.lightGray);
        f.addWindowListener (this);  //窗体f－－注册窗体事件监听器
        f.setVisible(true);
    }
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
}
