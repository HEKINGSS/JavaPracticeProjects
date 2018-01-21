package com.hello.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hello.dao.UserDao;
import com.hello.entity.User;
import com.hello.utils.DBUtil;

public class UserDaoImpl implements UserDao {

	public void addUser(User user) throws Exception {
		Connection conn=null;
		PreparedStatement ps=null;
		
		try {
			conn=DBUtil.getConnection();
			ps=conn.prepareStatement("insert into fuser(username,pwd,email) values(?,?,?)");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPwd());
			ps.setString(3, user.getEmail());
			int i=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(null, ps, conn);
		}
		
	}

}
