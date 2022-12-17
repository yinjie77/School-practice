package util;

import java.sql.Connection;
import java.sql.DriverManager;

//数据库工具类
public class DButil {
	private String dbUrl="jdbc:mysql://localhost:3306/db_book";//数据库连接地址
	private String dbUserName="root";//用户名
	private String dbPassword="123456";//密码
	private String jdbcName="com.mysql.jdbc.Driver";//驱动名称，JDBC连接数据库
	
	//获取数据库连接
	public Connection getCon() throws Exception{
		Class.forName(jdbcName);
		Connection con= DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
		return con;
	}
	//关闭数据库连接
	public void closeCon(Connection con) throws Exception{
		if(con!=null) {
			con.close();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			DButil dButil=new DButil();
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
		
	}
}
