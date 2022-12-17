package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;



//��������
public class AI {
	//������һ��
	public static boolean next(GamePanel panel) {
		boolean flag=go(panel)||luoziRandom(panel);
//		boolean flag=go(panel);
		return flag;
	}
	static boolean has5(Pointer pointer1,GamePanel panel) {
		List<Data> datas=new ArrayList<Data>();
		
		//ѭ���ҳ����壬�жϴ�����4�������Ƿ������ӵ����
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
				//ѭ���ĸ�����
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
		//����
		Collections.sort(datas,new DataCount());
		
		if(datas.size()>0) {
			Data data=datas.get(0);
			if(data.getCount()==100) {
				return true;
			}
		}
		return false;
	}
	//����ͨ��������һ��
		private static boolean go(GamePanel panel) {
 /*
 		1 ѭ��ָʾ��
 		2 ѭ��4������
 		3 �ֱ��������ң����ҵ��󣬲�����Ч�ķ��ؽ���Ž�������
 		4 ��Ȩ�طֽ����������������ŵ���ǰ��
 		5 ȡ��һ��Ԫ����Ϊ���ӵĵط�������true
  */
		List<Data> datas =new ArrayList<Data>();
		Pointer [][] pointers = panel.pointers;
		//1 ѭ��ָʾ��
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
					//��ȡȨ�ط�1
					data=getData(dir,1,panel,pointer);
					if(data.getCount()!=0&&data.getCount()!=-1) {
						//��ӵ�������
						datas.add(data);
					}
					
					//��ȡȨ�ط�2
					data=getData(dir,2,panel,pointer);
					if(data.getCount()!=0&&data.getCount()!=-1) {
						//��ӵ�������
						datas.add(data);
					}
					
				}
			}
		}
		//����
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
	//��ȡȨ�ط�
	private static Data getData(int dir, int type, GamePanel panel, Pointer pointer) {
		//���ؽ��
		Data resData=new Data();
		int i=pointer.getI();
		int j=pointer.getJ();
		
		Pointer [][] pointers=panel.pointers;
		Pointer temPointer;
		
		
		int num=1;//��������õ�
		int num2=1;//�ۼ���ͬ��num
		boolean breakFlag=false;//�Ƿ��ж�
		boolean lClosed=false;//��ر�
		boolean rClosed=false;//�ҹر�
		//����	
		if(dir==1)
		{
			//��������
			if(type==1) {
				for (int k = j+1; k < panel.COWS; k++) {
					temPointer=pointers[i][k];//ѭ����ÿһ��ָʾ��Ԫ��
					//�����������
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==panel.COWS-1) {
							rClosed=true;
						}
					}else if (temPointer.getType()==0) {//�հ�
						if(breakFlag) {
							//�ж�ǰһ�����Ƿ��ǿհ�
							if(pointers[i][k-1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(i);
						resData.setJ(k);
					}else { //��������
						rClosed=true;
						break;
					}
					
					//������ر�
					if(j==0)
					{
						lClosed=true;
					}else {
						if(pointers[i][j-1].getType()!=0) {
							lClosed=true;
						}
					}
				}
			}else {//��������
				
				for (int k = j-1; k >=0; k--) {
					temPointer=pointers[i][k];//ѭ����ÿһ��ָʾ��Ԫ��
					//�����������
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==0) {
							lClosed=true;
						}
					}else if (temPointer.getType()==0) {//�հ�
						if(breakFlag) {
							//�ж�ǰһ�����Ƿ��ǿհ�
							if(pointers[i][k+1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(i);
						resData.setJ(k);
					}else { //��������
						lClosed=true;
						break;
					}
					
					//�����ҹر�
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
			
		}else if(dir==2) {//����
			if(type==1) {
				for (int k = i+1; k < panel.COWS; k++) {
					temPointer=pointers[k][j];//ѭ����ÿһ��ָʾ��Ԫ��
					//�����������
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==panel.ROWS-1) {
							rClosed=true;
						}
					}else if (temPointer.getType()==0) {//�հ�
						if(breakFlag) {
							//�ж�ǰһ�����Ƿ��ǿհ�
							if(pointers[k-1][j].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						
						//�жϵ�
						resData.setI(k);
						resData.setJ(j);
					}else { //��������
						rClosed=true;
						break;
					}
					
					//������ر�
					if(i==0)
					{
						lClosed=true;
					}else {
						if(pointers[i-1][j].getType()!=0) {
							lClosed=true;
						}
					}
				}
			}else {//��������
				
				for (int k = i-1; k >=0; k--) {
					temPointer=pointers[k][j];//ѭ����ÿһ��ָʾ��Ԫ��
					//�����������
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==0) {
							lClosed=true;
						}
					}else if (temPointer.getType()==0) {//�հ�
						if(breakFlag) {
							//�ж�ǰһ�����Ƿ��ǿհ�
							if(pointers[k+1][j].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(k);
						resData.setJ(j);
					}else { //��������
						lClosed=true;
						break;
					}
					
					//�����ҹر�
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
		}else if (dir==3) {//��������
			int tempi=i;
			if(type==1) {
				for (int k = j+1; k < panel.COWS; k++) {
					tempi++;
					if(tempi>panel.COWS-1) {
						rClosed=true;
						break;
					}
					
					temPointer=pointers[tempi][k];//ѭ����ÿһ��ָʾ��Ԫ��
					//�����������
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==panel.COWS-1) {
							rClosed=true;
						}
					}else if (temPointer.getType()==0) {//�հ�
						if(breakFlag) {
							//�ж�ǰһ�����Ƿ��ǿհ�
							if(pointers[tempi-1][k-1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}else { //��������
						rClosed=true;
						break;
					}
					
					//������ر�
					if(j==0||i==0)
					{
						lClosed=true;
					}else {
						if(pointers[i-1][j-1].getType()!=0) {
							lClosed=true;
						}
					}
				}
			}else {//��������
				
				for (int k = j-1; k >=0; k--) {
					tempi--;
					if(tempi<0) {
						lClosed=true;
						break;
					}
					temPointer=pointers[tempi][k];//ѭ����ÿһ��ָʾ��Ԫ��
					//�����������
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==0) {
							lClosed=true;
						}
					}else if (temPointer.getType()==0) {//�հ�
						if(breakFlag) {
							//�ж�ǰһ�����Ƿ��ǿհ�
							if(pointers[tempi+1][k+1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}else { //��������
						lClosed=true;
						break;
					}
					
					//�����ҹر�
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
					
					temPointer=pointers[tempi][k];//ѭ����ÿһ��ָʾ��Ԫ��
					//�����������
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==panel.COWS-1) {
							rClosed=true;
						}
					}else if (temPointer.getType()==0) {//�հ�
						if(breakFlag) {
							//�ж�ǰһ�����Ƿ��ǿհ�
							if(pointers[tempi+1][k-1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}else { //��������
						rClosed=true;
						break;
					}
					
					//������ر�
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
			}else {//��������
				
				for (int k = j-1; k >=0; k--) {
					tempi++;
					if(tempi>panel.ROWS-1) {
						lClosed=true;
						break;
					}
					temPointer=pointers[tempi][k];//ѭ����ÿһ��ָʾ��Ԫ��
					//�����������
					if(temPointer.getType()==pointer.getType()) {
						num++;
						num2++;
						if(k==0) {
							lClosed=true;
						}
					}else if (temPointer.getType()==0) {//�հ�
						if(breakFlag) {
							//�ж�ǰһ�����Ƿ��ǿհ�
							if(pointers[tempi-1][k+1].getType()==0) {
								breakFlag=false;
							}
							break;
						}
						num++;
						breakFlag=true;
						resData.setI(tempi);
						resData.setJ(k);
					}else { //��������
						lClosed=true;
						break;
					}
					
					//�����ҹر�
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
		
		//�趨����
		setCount(resData,i,j,dir,type,num,num2,breakFlag,lClosed,rClosed,panel);
		return resData;
	}
	//�趨����
	private static void setCount(Data resData,int i,int j, int dir, int type, int num, int num2, boolean breakFlag, boolean lClosed,
			boolean rClosed, GamePanel panel) {
		//����ķ���
		int count =0;
		if(num<=2) {
			return;
		}
		//���������趨
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
		//��������������趨Ϊ100
		if(num2>=5&&!breakFlag) {
			resData.setCount(100);
			return;
		}
		if(breakFlag) {//�ж�
			if(lClosed&&rClosed) {
				count=-1;
			}
		}else {//����
			if(lClosed&&rClosed) {
				count=-1;
			}else if (!lClosed) {
				count+=2;
				//����
				if(dir==1) {
					//������
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
				
				//����
				if(dir==1) {
					//������
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
	//�������
	private static boolean luoziRandom(GamePanel panel) {
		//�����ȡָʾ����λ��
		Pointer pointer = getRandomPointer(panel);
		//����λ�ý�������
		luozi(pointer,1,panel);
		
		return false;
	}
	
	//����λ�ý�������
	private static void luozi(Pointer pointer, int type, GamePanel panel) {
		//�������Ӷ���
		Qizi qizi =new Qizi(pointer.getX(), pointer.getY(), type);
		//��������ӵ�������
		qizi.setLast(true);
		panel.qizis.add(qizi);
		//��ָʾ����������
		pointer.setType(type);
		
		
		//�ػ�
		panel.repaint();
		
		if(has5(pointer, panel)) {
			//ʧ��
			panel.gameOver();
		}
		
	}
	//�����ȡָʾ����λ��
	private static Pointer getRandomPointer(GamePanel panel) {
		Random random =new Random();
		//0-14
		int i = random.nextInt(panel.ROWS);
		int j = random.nextInt(panel.COWS);
		//��ȡ��Ӧλ�õ�ָʾ������
		Pointer pointer= panel.pointers[i][j];
		//������ǿհ����»�ȡ
		if(pointer.getType()!=0)
		{
			return getRandomPointer(panel);
		}
		return pointer;
	}
	//�Զ���������
	static class DataCount implements Comparator<Data>{

		@Override
		public int compare(Data o1, Data o2) {
			return o2.getCount()-o1.getCount();
		}
		
	}
}
