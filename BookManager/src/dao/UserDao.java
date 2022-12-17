package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

//�û�dao��
public class UserDao {
	//��½��֤
	public User login(Connection con,User user)throws Exception{
		User resultUser=null;
		String sql="select * from t_user where userName=? and password=?";//Ԥ����
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassWord());
		ResultSet rs=pstmt.executeQuery();//ִ��sql���
		if(rs.next()) {//����ܲ�ѯ�������ã�����Ϊ��
			resultUser=new User();
			resultUser.setId(rs.getInt("id"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassWord(rs.getString("password"));
		}
		
		return resultUser;
	}
}
