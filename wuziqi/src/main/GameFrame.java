package main;


import java.awt.Color;

import javax.swing.JFrame;
//������
public class GameFrame extends JFrame {
	public GameFrame() {
		setTitle("������");//���ñ���
		setSize(620, 670);//�����С
		getContentPane().setBackground(new Color(245,245,245));//��ӱ���ɫ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����رհ�ť�ǹرճ���
		setLocationRelativeTo(null);//����
		setResizable(false);//���ܱ���С
		
	}
}
