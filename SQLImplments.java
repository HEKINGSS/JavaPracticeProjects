package com.king.isql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.king.jdbc.DBUtils;
import com.king.model.CountBookInfo;
import com.king.model.ImportBookInfo;
import com.king.model.ReturnBookInfo;
import com.king.model.SaleBookInfo;

public class SQLImplments implements SQLImpl {

	@Override
	public List<ImportBookInfo> IAllbook() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		List<ImportBookInfo> list = new ArrayList<>();
		String sql = "select bookname,ibookprice,ibooknum,inventory from booktable";
		try {	
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rSet = ps.executeQuery();
			while (rSet.next()) {
				ImportBookInfo importBookInfo = new ImportBookInfo();
				importBookInfo.setBookName(rSet.getString(1));
				importBookInfo.setiBookPrice(rSet.getFloat(2));
				importBookInfo.setiBookNum(rSet.getInt(3));
				importBookInfo.setInventory(rSet.getInt(4));
				list.add(importBookInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ImportBookInfo IFindBookByName(String bookName) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select ibookprice,ibooknum,inventory from booktable where bookname = ?";
		ImportBookInfo importBookInfo = null;	
		try {
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, bookName);;
			rs=ps.executeQuery();
			if(rs.next()){
				importBookInfo = new ImportBookInfo();
				importBookInfo.setBookName(bookName);
				importBookInfo.setiBookPrice(rs.getFloat(1));
				importBookInfo.setiBookNum(rs.getInt(2));
				importBookInfo.setInventory(rs.getInt(3));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				throw new SQLException("根据书名查询失败");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			DBUtils.close(conn, ps, rs);
		}

		return importBookInfo;
	}

	@Override
	public boolean IUpdate(String bookName, int iBookNum) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		String sql="update booktable set ibooknum = ?, inventory = ? where bookname = ?";
			ImportBookInfo importBookInfo =IFindBookByName(bookName);
			if (importBookInfo==null) {
				return false;
			}
		try {
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, importBookInfo.getiBookNum() + iBookNum);
			ps.setInt(2, importBookInfo.getInventory() + iBookNum);
			ps.setString(3, bookName);			
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				throw new SQLException("数据修改失败");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			DBUtils.close(conn, null, null);
		}

		return true;
	}

	@Override
	public List<ReturnBookInfo> RAllbook() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		List<ReturnBookInfo> list = new ArrayList<>();
		String sql = "select bookname,salenum,rbooknum,inventory,saleroom,sbookprice from booktable";
		try {	
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rSet = ps.executeQuery();
			while (rSet.next()) {
				ReturnBookInfo returnBookInfo = new ReturnBookInfo();
				returnBookInfo.setBookName(rSet.getString(1));
				returnBookInfo.setsBookNum(rSet.getInt(2));
				returnBookInfo.setrBookNum(rSet.getInt(3));
				returnBookInfo.setInventory(rSet.getInt(4));
				returnBookInfo.setSaleRoom(rSet.getFloat(5));
				returnBookInfo.setsBookPrice(rSet.getFloat(6));
				list.add(returnBookInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ReturnBookInfo RFindBookByName(String bookName) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select salenum,rbooknum,inventory,saleroom,sbookprice from booktable where bookname = ?";
		ReturnBookInfo returnBookInfo = null;
		try {
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, bookName);;
			rs=ps.executeQuery();
			if(rs.next()){
				returnBookInfo = new ReturnBookInfo();
				returnBookInfo.setBookName(bookName);
				returnBookInfo.setsBookNum(rs.getInt(1));
				returnBookInfo.setrBookNum(rs.getInt(2));
				returnBookInfo.setInventory(rs.getInt(3));
				returnBookInfo.setSaleRoom(rs.getFloat(4));
				returnBookInfo.setsBookPrice(rs.getFloat(5));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				throw new SQLException("根据书名查询失败");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			DBUtils.close(conn, ps, rs);
		}

		return returnBookInfo;
	}

	@Override
	public boolean RUpdate(String bookName, int rBookNum) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		String sql="update booktable set salenum = ?, rbooknum = ?, inventory = ?, saleroom = ? where bookname = ?";
		ReturnBookInfo returnBookInfo = RFindBookByName(bookName);
		if (returnBookInfo == null || returnBookInfo.getsBookNum() - rBookNum < 0 || rBookNum < 0) {
			
			return false;
		}
		try {
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, returnBookInfo.getsBookNum() - rBookNum);
			ps.setInt(2, returnBookInfo.getrBookNum() + rBookNum);
			ps.setInt(3, returnBookInfo.getInventory() + rBookNum);
			ps.setFloat(4, returnBookInfo.getSaleRoom() - returnBookInfo.getsBookPrice()*rBookNum);
			ps.setString(5, bookName);			
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				throw new SQLException("数据修改失败");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			DBUtils.close(conn, null, null);
		}

		return true;
	}

	@Override
	public List<SaleBookInfo> SAllbook() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		List<SaleBookInfo> list = new ArrayList<>();
		String sql = "select bookname,salenum,sbookprice,inventory,saleroom from booktable";
		try {	
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rSet = ps.executeQuery();
			while (rSet.next()) {
				SaleBookInfo saleBookInfo = new SaleBookInfo();
				saleBookInfo.setBookName(rSet.getString(1));
				saleBookInfo.setsBookNum(rSet.getInt(2));
				saleBookInfo.setsBookPrice(rSet.getFloat(3));
				saleBookInfo.setInventory(rSet.getInt(4));
				saleBookInfo.setSaleRoom(rSet.getFloat(5));
				list.add(saleBookInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public SaleBookInfo SFindBookByName(String bookName) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select inventory, sbookprice, salenum, saleroom from booktable where bookname = ?";
		SaleBookInfo saleBookInfo = null;
		try {
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, bookName);;
			rs=ps.executeQuery();
			if(rs.next()){
				saleBookInfo = new SaleBookInfo();
				saleBookInfo.setBookName(bookName);
				saleBookInfo.setInventory(rs.getInt(1));
				saleBookInfo.setsBookPrice(rs.getFloat(2));
				saleBookInfo.setsBookNum(rs.getInt(3));
				saleBookInfo.setSaleRoom(rs.getFloat(4));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				throw new SQLException("根据书名查询失败");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			
			DBUtils.close(conn, ps, rs);
		}

		return saleBookInfo;
	}

	@Override
	public boolean SUpdate(String bookName, int sBookNum) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement ps = null;
		String sql="update booktable set salenum = ?, inventory = ?, saleroom = ? where bookname = ?";
		SaleBookInfo saleBookInfo = SFindBookByName(bookName);
		if (saleBookInfo == null || saleBookInfo.getInventory() - sBookNum < 0 || sBookNum < 0) {
			
			return false;
		}
		try {
			
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, saleBookInfo.getsBookNum() + sBookNum);
			ps.setInt(2, saleBookInfo.getInventory() - sBookNum);
			ps.setFloat(3, saleBookInfo.getSaleRoom() + sBookNum*saleBookInfo.getsBookPrice());
			ps.setString(4, bookName);			
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				throw new SQLException("销售数据修改失败");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			
			DBUtils.close(conn, null, null);
		}

		return true;
	}

	@Override
	public List<CountBookInfo> CAllbook() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		List<CountBookInfo> list = new ArrayList<>();
		String sql = "select bookname,salenum,rbooknum,saleroom,inventory from booktable order by salenum desc";
		try {	
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rSet = ps.executeQuery();
			while (rSet.next()) {
				CountBookInfo countBookInfo = new CountBookInfo();
				countBookInfo.setBookName(rSet.getString(1));
				countBookInfo.setsBookNum(rSet.getInt(2));
				countBookInfo.setrBookNum(rSet.getInt(3));
				countBookInfo.setSaleRoom(rSet.getFloat(4));
				countBookInfo.setInventory(rSet.getInt(5));
				list.add(countBookInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	

}
