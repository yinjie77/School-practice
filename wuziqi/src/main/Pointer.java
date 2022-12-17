package main;
//指示器类

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Pointer {
	//二维数组下标
	private int i=0; 
	private int j=0;
	
	//x y坐标
	private int x=0;
	private int y=0;

	//指示器的高
	private int h=40;
	//是否展示
	private boolean isShow=false;
	//0表示空白，1是白棋，2是黑棋
	private int type=0;
	
	//构造
	public Pointer(int i,int j,int x,int y) {
		this.setI(i);
		this.setJ(j);
		this.x=x;
		this.y=y;
	}
	
	//绘制指示器
	public void draw(Graphics g) {
		g.setColor(new Color(255,0,0));
		if(isShow)
		{
			//g.drawRect(x-h/2, y-h/2, h, h);
			//绘制指示器
			drawPointer(g);
		}
		
	}
	//绘制指示器
	private void drawPointer(Graphics g) {
		//转换成2d画笔
		Graphics2D g2d=(Graphics2D)g;
		//设置画笔的粗细
		g2d.setStroke(new BasicStroke(2.0f));
		
		
		int x1=0;
		int y1=0;
		int x2=0;
		int y2=0;

		//左上角
		 x1 = this.x-h/2;
		 y1 = this.y-h/2;
		//向右画线
		 x2 = x1+h/4;
		 y2 = y1;
		g2d.drawLine(x1, y1, x2, y2);
		
		//向下画线
		x2 = x1;
		y2 = y1+h/4;
		g2d.drawLine(x1, y1, x2, y2);
		
		//右上角
		x1 = this.x+h/2;
		y1 = this.y-h/2;
		//向左画线
		x2 = x1-h/4;
		y2 = y1;
		g2d.drawLine(x1, y1, x2, y2);
		
		//向下画线
		x2 = x1;
		y2 = y1+h/4;
		g2d.drawLine(x1, y1, x2, y2);
		
		//右下角
		x1 = this.x+h/2;
		y1 = this.y+h/2;
		//向左画线
		x2 = x1-h/4;
		y2 = y1;
		g2d.drawLine(x1, y1, x2, y2);
		
		//向上画线
		x2 = x1;
		y2 = y1-h/4;
		g2d.drawLine(x1, y1, x2, y2);
		
		//左下角
		x1 = this.x-h/2;
		y1 = this.y+h/2;
		//向右画线
		x2 = x1+h/4;
		y2 = y1;
		g2d.drawLine(x1, y1, x2, y2);
		
		//向上画线
		x2 = x1;
		y2 = y1-h/4;
		g2d.drawLine(x1, y1, x2, y2);
	}

	//判断是否在指示器范围内
	public boolean isPoint(int x,int y) {
		//左上角
		int x1=this.x-h/2;
		int y1=this.y-h/2;
		//右下角
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
