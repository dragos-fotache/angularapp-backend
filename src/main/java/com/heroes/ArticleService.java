package com.heroes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public enum ArticleService {

	INSTANCE;

	public List<Article> getArticles() throws SQLException, ClassNotFoundException {
		List<Article> result = new ArrayList<Article>();

		Connection c = null;
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:test.db");

		Statement ps = c.createStatement();
		String sql = "SELECT * FROM ARTICLE;";
		
		ResultSet rs = ps.executeQuery(sql);

		while (rs.next()) {
			int id = rs.getInt(1);
			String pzn = rs.getString(2);
			String name = rs.getString(3);
			String supplier = rs.getString(4);
			String packing = rs.getString(5);
			
			result.add(new Article(id, pzn, name, supplier, packing));
		}
		
		rs.close();
		ps.close();
		c.close();

		return result;
	}
	
	public List<Article> getArticlesSlice(int first, int rows, String sortField, boolean desc) throws SQLException, ClassNotFoundException {
		List<Article> result = new ArrayList<Article>();
		
		Connection c = null;
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:test.db");
		
		String sqlAsc = "SELECT * FROM ARTICLE ORDER BY %s LIMIT ? OFFSET ?";
		String sqlDesc = "SELECT * FROM ARTICLE ORDER BY %s DESC LIMIT ? OFFSET ?";
		
		String sql = String.format(desc ? sqlDesc : sqlAsc, sortField);
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setInt(1, rows);
		ps.setInt(2, first);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt(1);
			String pzn = rs.getString(2);
			String name = rs.getString(3);
			String supplier = rs.getString(4);
			String packing = rs.getString(5);
			
			result.add(new Article(id, pzn, name, supplier, packing));
		}
		
		rs.close();
		ps.close();
		c.close();
		
		return result;
	}
}
