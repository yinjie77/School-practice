package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;



//电脑下棋
public class AI {
	//电脑下一步
	public static boolean next(GamePanel panel) {
		boolean flag=go(panel)||luoziRandom(panel);
//		boolean flag=go(panel);
		return flag;
	}
	static boolean has5(Pointer pointer1,GamePanel panel) {
		List<Data> datas=new ArrayList<Data>();
		
		//循环找出黑棋，判断此棋子4个方向是否有四子的情况
		Pointer pointer;
		for (int i = 0; i < panel.ROWS; i++) {
			for (int j = 0; j < panel.COWS; j++) {
				pointer = panel.pointers[i][j];
				if(pointer==null)continue;
				if(pointer.getType()==0) {
					continue;
				}
				if(pointer1.getType()!=pointer.getType()) {
					continue;
				}
				//循环四个方向
				int dir=1;
				for (int k = 1; k <=4; k++) {
					dir=k;
					Data data=getData(dir, 1, panel, pointer);
					if(data.getCount()!=-1&&data.getCount()!=0) {
						datas.add(data);
					}
					data=getData(dir, 1, panel, pointer);
					if(data.getCount()!=-1&&data.getCount()!=0) {
						datas.add(data);
					}
					
				}
			}
		}
		//排序
		Collections.sort(datas,new DataCount());
		
		if(datas.size()>0) {
			Data data=datas.get(0);
			if(data.getCount()==100) {
				return true;
			}
		}
		return false;
	}
	//电脑通过计算下一步
		private static boolean go(GamePanel panel) {
 /*
 		1 循环指示器
 		2 循环4个方向
 		3 分别计算从左到右，从右到左，并将有效的返回结果放进集合中
 		4 对权重分进行排序处理，把最大的排到最前面
 		5 取第一个元素作为落子的地方，返回true
  */
		List<Data> datas =new ArrayList<Data>();
		Pointer [][] pointers = panel.pointers;
		//1 循环指示器
		Pointer pointer;
		Data data;
		for (int i = 0; i < panel.ROWS; i++) {
			for (int j = 0; j < panel.COWS; j++) {
				pointer = pointers[i][j];
				if(pointer==null||pointer.getType()==0) {
					continue;
				}
				int dir=0;
				for (int k = 1; k <=4; k++) {
					dir=k;
					//获取权重分1
					data=getData(dir,1,panel,pointer);
					if(data.getCount()!=0&&data.getCount()!=-1) {
						//添加到集合中
						datas.add(data);
					}
					
					//获取权重分2
					data=getData(dir,2,panel,pointer);
					if(data.getCount()!=0&&data.getCount()!=-1) {
						//添加到集合中
						datas.add(data);
					}
					
				}
			}
		}
		//排序
		Collections.sort(datas, new DataCount());
		for (int i = 0; i < datas.size(); i++) {
			System.out.println(datas.get(i).getCount());
		}
		System.out.println("\n");
		
		if(datas.size()>0) {
			Data data2=datas.get(0);
			Pointer pointer1=pointers[data2.getI()][data2.getJ()];
			luozi(pointer1, 1, panel);
			return true;
		}
		
			return false;
		}
	//获取权重分
	private static Data getData(int dir, int type, GamePanel panel, Pointer pointer) {
		//返回结果
		Data resData=new Data();
		int i=pointer.getI();
		int j=pointer.getJ();
		
		Pointer [][] pointers=panel.pointers;
		Pointer temPointer;
		
		
		int num=1;//计算分数用的
		int num2=1;//累计相同的num
		boolean breakFlag=false;//是否中断
		boolean lClosed=false;//左关闭
		boolean rClosed=false;//右关闭
		//横向	
		if(dir==1)
		{
			//从左往右
			if(type==1) {
				for (int k = j+1; k < panel.COWS; k++) {
					temPointer=pointers[i][k];//循环的每一个指示器元素
					//如果是连续的
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==panel.COWS-1) {
							rClosed=true;
						}
					}else if (temPointer.getType()==0) {//空白
						if(breakFlag) {
							//判断前一个子是否是空白
							if(pointers[i][k-1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(i);
						resData.setJ(k);
					}else { //对立棋子
						rClosed=true;
						break;
					}
					
					//处理左关闭
					if(j==0)
					{
						lClosed=true;
					}else {
						if(pointers[i][j-1].getType()!=0) {
							lClosed=true;
						}
					}
				}
			}else {//从右往左
				
				for (int k = j-1; k >=0; k--) {
					temPointer=pointers[i][k];//循环的每一个指示器元素
					//如果是连续的
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==0) {
							lClosed=true;
						}
					}else if (temPointer.getType()==0) {//空白
						if(breakFlag) {
							//判断前一个子是否是空白
							if(pointers[i][k+1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(i);
						resData.setJ(k);
					}else { //对立棋子
						lClosed=true;
						break;
					}
					
					//处理右关闭
					if(j==panel.COWS-1)
					{
						rClosed=true;
					}else {
						if(pointers[i][j+1].getType()!=0) {
							rClosed=true;
						}
					}
				}
			
			}
			
		}else if(dir==2) {//竖向
			if(type==1) {
				for (int k = i+1; k < panel.COWS; k++) {
					temPointer=pointers[k][j];//循环的每一个指示器元素
					//如果是连续的
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==panel.ROWS-1) {
							rClosed=true;
						}
					}else if (temPointer.getType()==0) {//空白
						if(breakFlag) {
							//判断前一个子是否是空白
							if(pointers[k-1][j].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						
						//中断的
						resData.setI(k);
						resData.setJ(j);
					}else { //对立棋子
						rClosed=true;
						break;
					}
					
					//处理左关闭
					if(i==0)
					{
						lClosed=true;
					}else {
						if(pointers[i-1][j].getType()!=0) {
							lClosed=true;
						}
					}
				}
			}else {//从下往上
				
				for (int k = i-1; k >=0; k--) {
					temPointer=pointers[k][j];//循环的每一个指示器元素
					//如果是连续的
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==0) {
							lClosed=true;
						}
					}else if (temPointer.getType()==0) {//空白
						if(breakFlag) {
							//判断前一个子是否是空白
							if(pointers[k+1][j].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(k);
						resData.setJ(j);
					}else { //对立棋子
						lClosed=true;
						break;
					}
					
					//处理右关闭
					if(i==panel.ROWS-1)
					{
						rClosed=true;
					}else {
						if(pointers[i+1][j].getType()!=0) {
							rClosed=true;
						}
					}
				}
			
			}
		}else if (dir==3) {//左上右下
			int tempi=i;
			if(type==1) {
				for (int k = j+1; k < panel.COWS; k++) {
					tempi++;
					if(tempi>panel.COWS-1) {
						rClosed=true;
						break;
					}
					
					temPointer=pointers[tempi][k];//循环的每一个指示器元素
					//如果是连续的
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==panel.COWS-1) {
							rClosed=true;
						}
					}else if (temPointer.getType()==0) {//空白
						if(breakFlag) {
							//判断前一个子是否是空白
							if(pointers[tempi-1][k-1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}else { //对立棋子
						rClosed=true;
						break;
					}
					
					//处理左关闭
					if(j==0||i==0)
					{
						lClosed=true;
					}else {
						if(pointers[i-1][j-1].getType()!=0) {
							lClosed=true;
						}
					}
				}
			}else {//从右往左
				
				for (int k = j-1; k >=0; k--) {
					tempi--;
					if(tempi<0) {
						lClosed=true;
						break;
					}
					temPointer=pointers[tempi][k];//循环的每一个指示器元素
					//如果是连续的
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==0) {
							lClosed=true;
						}
					}else if (temPointer.getType()==0) {//空白
						if(breakFlag) {
							//判断前一个子是否是空白
							if(pointers[tempi+1][k+1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}else { //对立棋子
						lClosed=true;
						break;
					}
					
					//处理右关闭
					if(j==panel.COWS-1||i==panel.ROWS-1)
					{
						rClosed=true;
					}else {
						if(pointers[i+1][j+1].getType()!=0) {
							rClosed=true;
						}
					}
				}
			
			}
		}else if (dir==4) {
			int tempi=i;
			if(type==1) {
				for (int k = j+1; k < panel.ROWS; k++) {
					tempi--;
					if(tempi<0) {
						rClosed=true;
						break;
					}
					
					temPointer=pointers[tempi][k];//循环的每一个指示器元素
					//如果是连续的
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==panel.COWS-1) {
							rClosed=true;
						}
					}else if (temPointer.getType()==0) {//空白
						if(breakFlag) {
							//判断前一个子是否是空白
							if(pointers[tempi+1][k-1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}else { //对立棋子
						rClosed=true;
						break;
					}
					
					//处理左关闭
					if(j==0||i==0)
					{
						lClosed=true;
					}else {
						if(i==panel.ROWS-1)
						{
							lClosed=true;
						}else{
							if(pointers[i+1][j-1].getType()!=0)
							lClosed=true;
						}
					}
				}
			}else {//从右往左
				
				for (int k = j-1; k >=0; k--) {
					tempi++;
					if(tempi>panel.ROWS-1) {
						lClosed=true;
						break;
					}
					temPointer=pointers[tempi][k];//循环的每一个指示器元素
					//如果是连续的
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==0) {
							lClosed=true;
						}
					}else if (temPointer.getType()==0) {//空白
						if(breakFlag) {
							//判断前一个子是否是空白
							if(pointers[tempi-1][k+1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}else { //对立棋子
						lClosed=true;
						break;
					}
					
					//处理右关闭
					if(j==panel.COWS-1||i==panel.ROWS-1)
					{
						rClosed=true;
					}else {
						if(i==0) {
							rClosed=true;
						}
						else {
							if(pointers[i-1][j+1].getType()!=0) {
								rClosed=true;
							}
						}
					}
				}
			
			}
		}
		
		//设定分数
		setCount(resData,i,j,dir,type,num,num2,breakFlag,lClosed,rClosed,panel);
		return resData;
	}
	//设定分数
	private static void setCount(Data resData,int i,int j, int dir, int type, int num, int num2, boolean breakFlag, boolean lClosed,
			boolean rClosed, GamePanel panel) {
		//计算的分数
		int count =0;
		if(num<=2) {
			return;
		}
		//分数初步设定
		if(num==3)
		{
			count=30;
		}
		else if (num==4) {
			count=40;
		}
		else if (num==5) {
			count=50;
		}
		//五子情况，分数设定为100
		if(num2>=5&&!breakFlag) {
			resData.setCount(100);
			return;
		}
		if(breakFlag) {//中断
			if(lClosed&&rClosed) {
				count=-1;
			}
		}else {//连续
			if(lClosed&&rClosed) {
				count=-1;
			}else if (!lClosed) {
				count+=2;
				//横向
				if(dir==1) {
					//从左到右
					if(type==1) {
						resData.setI(i);
						resData.setJ(j-1);
					}else {
						resData.setI(i);
						resData.setJ(j-num+1);
					}
				}else if (dir==2) {
					if(type==1) {
						resData.setI(i-1);
						resData.setJ(j);
					}else {
						resData.setI(i-num+1);
						resData.setJ(j);
					}
				}else if (dir==3) {
					if(type==1) {
						resData.setI(i-1);
						resData.setJ(j-1);
					}else {
						resData.setI(i-num+1);
						resData.setJ(j-num+1);
					}
				}else if (dir==4) {
					if(type==1) {
						resData.setI(i+1);
						resData.setJ(j-1);
					}else {
						resData.setI(i+num-1);
						resData.setJ(j-num+1);
					}
				}
			}else if (!rClosed) {
				count+=1;
				
				//横向
				if(dir==1) {
					//从左到右
					if(type==1) {
						resData.setI(i);
						resData.setJ(j+num-1);
					}else {
						resData.setI(i);
						resData.setJ(j+1);
					}
				}else if (dir==2) {
					if(type==1) {
						resData.setI(i+num-1);
						resData.setJ(j);
					}else {
						resData.setI(i+1);
						resData.setJ(j);
					}
				}else if (dir==3) {
					if(type==1) {
						resData.setI(i+num-1);
						resData.setJ(j+num-1);
					}else {
						resData.setI(i+1);
						resData.setJ(j+1);
					}
				}else if (dir==4) {
					if(type==1) {
						resData.setI(i-num+1);
						resData.setJ(j+num-1);
					}else {
						resData.setI(i-1);
						resData.setJ(j+1);
					}
				}
			}
		}
	
		resData.setCount(count);
		
	}
	//随机落子
	private static boolean luoziRandom(GamePanel panel) {
		//随机获取指示器的位置
		Pointer pointer = getRandomPointer(panel);
		//根据位置进行落子
		luozi(pointer,1,panel);
		
		return false;
	}
	
	//根据位置进行落子
	private static void luozi(Pointer pointer, int type, GamePanel panel) {
		//创建棋子对象
		Qizi qizi =new Qizi(pointer.getX(), pointer.getY(), type);
		//讲棋子添加到集合中
		qizi.setLast(true);
		panel.qizis.add(qizi);
		//给指示器设置类型
		pointer.setType(type);
		
		
		//重绘
		panel.repaint();
		
		if(has5(pointer, panel)) {
			//失败
			panel.gameOver();
		}
		
	}
	//随机获取指示器的位置
	private static Pointer getRandomPointer(GamePanel panel) {
		Random random =new Random();
		//0-14
		int i = random.nextInt(panel.ROWS);
		int j = random.nextInt(panel.COWS);
		//获取对应位置的指示器对象
		Pointer pointer= panel.pointers[i][j];
		//如果不是空白重新获取
		if(pointer.getType()!=0)
		{
			return getRandomPointer(panel);
		}
		return pointer;
	}
	//自定义排序类
	static class DataCount implements Comparator<Data>{

		@Override
		public int compare(Data o1, Data o2) {
			return o2.getCount()-o1.getCount();
		}
		
	}
}
