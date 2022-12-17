package main;


import java.awt.Color;

import javax.swing.JFrame;
//窗体类
public class GameFrame extends JFrame {
	public GameFrame() {
		setTitle("五子棋");//设置标题
		setSize(620, 670);//窗体大小
		getContentPane().setBackground(new Color(245,245,245));//添加背景色
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击关闭按钮是关闭程序
		setLocationRelativeTo(null);//居中
		setResizable(false);//不能变大变小
		
	}
}
