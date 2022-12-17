package main;
//ָʾ����

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Pointer {
	//��ά�����±�
	private int i=0; 
	private int j=0;
	
	//x y����
	private int x=0;
	private int y=0;

	//ָʾ���ĸ�
	private int h=40;
	//�Ƿ�չʾ
	private boolean isShow=false;
	//0��ʾ�հף�1�ǰ��壬2�Ǻ���
	private int type=0;
	
	//����
	public Pointer(int i,int j,int x,int y) {
		this.setI(i);
		this.setJ(j);
		this.x=x;
		this.y=y;
	}
	
	//����ָʾ��
	public void draw(Graphics g) {
		g.setColor(new Color(255,0,0));
		if(isShow)
		{
			//g.drawRect(x-h/2, y-h/2, h, h);
			//����ָʾ��
			drawPointer(g);
		}
		
	}
	//����ָʾ��
	private void drawPointer(Graphics g) {
		//ת����2d����
		Graphics2D g2d=(Graphics2D)g;
		//���û��ʵĴ�ϸ
		g2d.setStroke(new BasicStroke(2.0f));
		
		
		int x1=0;
		int y1=0;
		int x2=0;
		int y2=0;

		//���Ͻ�
		 x1 = this.x-h/2;
		 y1 = this.y-h/2;
		//���һ���
		 x2 = x1+h/4;
		 y2 = y1;
		g2d.drawLine(x1, y1, x2, y2);
		
		//���»���
		x2 = x1;
		y2 = y1+h/4;
		g2d.drawLine(x1, y1, x2, y2);
		
		//���Ͻ�
		x1 = this.x+h/2;
		y1 = this.y-h/2;
		//������
		x2 = x1-h/4;
		y2 = y1;
		g2d.drawLine(x1, y1, x2, y2);
		
		//���»���
		x2 = x1;
		y2 = y1+h/4;
		g2d.drawLine(x1, y1, x2, y2);
		
		//���½�
		x1 = this.x+h/2;
		y1 = this.y+h/2;
		//������
		x2 = x1-h/4;
		y2 = y1;
		g2d.drawLine(x1, y1, x2, y2);
		
		//���ϻ���
		x2 = x1;
		y2 = y1-h/4;
		g2d.drawLine(x1, y1, x2, y2);
		
		//���½�
		x1 = this.x-h/2;
		y1 = this.y+h/2;
		//���һ���
		x2 = x1+h/4;
		y2 = y1;
		g2d.drawLine(x1, y1, x2, y2);
		
		//���ϻ���
		x2 = x1;
		y2 = y1-h/4;
		g2d.drawLine(x1, y1, x2, y2);
	}

	//�ж��Ƿ���ָʾ����Χ��
	public boolean isPoint(int x,int y) {
		//���Ͻ�
		int x1=this.x-h/2;
		int y1=this.y-h/2;
		//���½�
		int x2=this.x+h/2;
		int y2=this.y+h/2;
		
		return x>x1&&y>y1&&x<x2&&y<y2;
	
	}
	public boolean ishow() {
		return isShow;
	}
	public void setShow (boolean isShow) {
		this.isShow=isShow;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

}
