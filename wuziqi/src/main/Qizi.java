package main;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import common.ImageValue;

public class Qizi {
	private int x=0;//x坐标
	private int y=0;//y坐标
	private int h=36;//高
	private int type=1;//1表示白棋，2表示黑棋

	private boolean last =false;//是否最后一步棋子
	
	//构造方法
	public Qizi(int x,int y,int type)
	{
		this.x=x;
		this.y=y;
		this.type=type;
	}
	
	//图片绘制方法
	public void draw(Graphics g) {
		if(type==1) {
			g.drawImage(ImageValue.whiteImage,x-h/2,y-h/2,h,h,null);
		}else {
			g.drawImage(ImageValue.blackImage,x-h/2,y-h/2,h,h,null);
		}
		//如果是最后一步
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
