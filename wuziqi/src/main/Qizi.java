package main;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import common.ImageValue;

public class Qizi {
	private int x=0;//x����
	private int y=0;//y����
	private int h=36;//��
	private int type=1;//1��ʾ���壬2��ʾ����

	private boolean last =false;//�Ƿ����һ������
	
	//���췽��
	public Qizi(int x,int y,int type)
	{
		this.x=x;
		this.y=y;
		this.type=type;
	}
	
	//ͼƬ���Ʒ���
	public void draw(Graphics g) {
		if(type==1) {
			g.drawImage(ImageValue.whiteImage,x-h/2,y-h/2,h,h,null);
		}else {
			g.drawImage(ImageValue.blackImage,x-h/2,y-h/2,h,h,null);
		}
		//��������һ��
		if(last) {
			Graphics2D g2d=(Graphics2D)g;
			g2d.setStroke(new BasicStroke(1.6f));
			
			g2d.drawRect(x-3, y-3, 6, 6);
		}
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
