package util;

import java.sql.Connection;
import java.sql.DriverManager;

//���ݿ⹤����
public class DButil {
	private String dbUrl="jdbc:mysql://localhost:3306/db_book";//���ݿ����ӵ�ַ
	private String dbUserName="root";//�û���
	private String dbPassword="123456";//����
	private String jdbcName="com.mysql.jdbc.Driver";//�������ƣ�JDBC�������ݿ�
	
	//��ȡ���ݿ�����
	public Connection getCon() throws Exception{
		Class.forName(jdbcName);
		Connection con= DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
		return con;
	}
	//�ر����ݿ�����
	public void closeCon(Connection con) throws Exception{
		if(con!=null) {
			con.close();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			DButil dButil=new DButil();
			System.out.println("���ݿ����ӳɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���ݿ�����ʧ��");
		}
		
	}
}
