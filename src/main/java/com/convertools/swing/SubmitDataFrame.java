package com.convertools.swing;

import com.convertools.entity.DocNoGen;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * @author fangkun
 * @date 2021/4/22 21:58
 * @description:
 */
public class SubmitDataFrame extends JFrame implements ActionListener {

    JPanel panel;
    JLabel label;
    JButton saveButton;
    JTextField jTextField;



    static int width = 800;
    static  int height = 640;

    // 全屏
    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        height = screenSize.height * 80/100;
        width = screenSize.width* 80/100;
    }
    private DocNoGen docNoGen;

    private CountDownLatch countDownLatch;
    public SubmitDataFrame(DocNoGen docNoGen, CountDownLatch countDownLatch) {
        this.docNoGen = docNoGen;
        this.countDownLatch = countDownLatch;
        this.setTitle("请输入委托单号!【" + docNoGen.getMdbName() + "】");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        // 仅仅关闭当前窗口
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setAlwaysOnTop(true);
        panel = new JPanel();
        panel.setLayout(new FlowLayout());//设置为流式布局
        label = new JLabel("请输入委托单号:");
        saveButton = new JButton("保存");
        saveButton.addActionListener(this);//监听事件
        jTextField = new JTextField(40);//设置文本框的长度
        jTextField.setPreferredSize(new Dimension(60, 40));
        jTextField.setFocusable(true);
        panel.add(label);//把组件添加到面板panel
        panel.add(jTextField);
        panel.add(saveButton);
        this.setResizable(false);// 不允许最大化最小化
        this.add(panel);//实现面板panel
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);//设置可见
    }

    @SuppressWarnings("deprecation")
    @Override
    public void actionPerformed(ActionEvent e) {//处理事件
        // TODO Auto-generated method stub
        // 保存
        if (e.getSource() == saveButton) {
            String wtdh = jTextField.getText();
            if (StringUtils.isEmpty(wtdh.trim())) {
                JOptionPane.showMessageDialog(this,"请输入委托单号！" );
            } else {
                // 保存数据 到数据库，保存成功关闭窗口
                docNoGen.setDocNo(wtdh);
                this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.countDownLatch.countDown();
                this.dispose();
            }
        }
    }

}

