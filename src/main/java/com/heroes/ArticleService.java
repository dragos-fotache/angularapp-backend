package com.heroes;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public enum ArticleService {

	INSTANCE;

	private static final String JDBC_CONNECTOR = "com.mysql.jdbc.Driver";
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/drugs?user=root&password=1234";

	ArticleService() {
		try {
			Class.forName(JDBC_CONNECTOR);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<Article> getArticles() throws SQLException, ClassNotFoundException {
		Connection c = null;
		c = DriverManager.getConnection(CONNECTION_STRING);

		List<Article> result = getArticles(c);
		c.close();

		return result;
	}

	public List<Article> getArticles(Connection c) throws SQLException, ClassNotFoundException {

		List<Article> result = new ArrayList<Article>();

		Statement ps = c.createStatement();
		String sql = "SELECT * FROM ARTICLE;";

		ResultSet rs = ps.executeQuery(sql);

		while (rs.next()) {
			int id = rs.getInt(1);
			String pzn = rs.getString(2);
			String name = rs.getString(3);
			String provider = rs.getString(4);
			String dosage = rs.getString(5);
			String packaging = rs.getString(6);
			Double sellingPrice = rs.getDouble(7);
			Double purchasingPrice = rs.getDouble(8);
			Boolean narcotic = rs.getBoolean(9);
			Integer stock = rs.getInt(10);

			result.add(new Article(id, pzn, name, provider, dosage, packaging, sellingPrice, purchasingPrice, narcotic, stock));
		}

		rs.close();
		ps.close();

		return result;
	}

	public ArticleSliceAndCount getArticlesSliceAndCount(int first, int rows, String sortField, boolean desc, String searchString) throws SQLException {

		ArticleSliceAndCount articleSliceAndCount = null;

		try (Connection c = DriverManager.getConnection(CONNECTION_STRING)) {

			List<Article> articles = getArticlesSlice(first, rows, sortField, desc, searchString, c);
			int count = getArticlesCount(searchString, c);

			articleSliceAndCount = new ArticleSliceAndCount(articles, count);
		}

		return articleSliceAndCount;
	}

	public List<Article> getArticlesSlice(int first, int rows, String sortField, boolean desc, String searchString, Connection c) throws SQLException {

		List<Article> result = new ArrayList<Article>();

		String sqlAsc = "SELECT * FROM ARTICLE WHERE name like '%%%s%%' ORDER BY %s LIMIT ?, ?";
		String sqlDesc = "SELECT * FROM ARTICLE WHERE name like '%%%s%%' ORDER BY %s DESC LIMIT ?, ?";

		String sql = desc ? sqlDesc : sqlAsc;

		sql = String.format(sql, searchString, sortField);

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = c.prepareStatement(sql);

			ps.setInt(1, first);
			ps.setInt(2, rows);

			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				String pzn = rs.getString(2);
				String name = rs.getString(3);
				String provider = rs.getString(4);
				String dosage = rs.getString(5);
				String packaging = rs.getString(6);
				Double sellingPrice = rs.getDouble(7);
				Double purchasingPrice = rs.getDouble(8);
				Boolean narcotic = rs.getBoolean(9);
				Integer stock = rs.getInt(10);

				result.add(new Article(id, pzn, name, provider, dosage, packaging, sellingPrice, purchasingPrice, narcotic, stock));
			}
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}

		return result;
	}

	public int getArticlesCount(String searchString, Connection c) throws SQLException {

		int result = 0;

		String sql = "SELECT COUNT(*) FROM ARTICLE WHERE name like '%%%s%%'";
		sql = String.format(sql, searchString);
		Statement st = null;
		ResultSet rs = null;

		try {
			st = c.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				result = rs.getInt(1);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
		}

		return result;
	}

	public Article findById(Connection c, int id) throws SQLException {

		Article result = null;

		String sql = "SELECT * FROM ARTICLE WHERE id = ?";
		
		ResultSet rs = null;
		
		try(PreparedStatement st = c.prepareStatement(sql);) {
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			while (rs.next()) {
				String pzn = rs.getString(2);
				String name = rs.getString(3);
				String provider = rs.getString(4);
				String dosage = rs.getString(5);
				String packaging = rs.getString(6);
				Double sellingPrice = rs.getDouble(7);
				Double purchasingPrice = rs.getDouble(8);
				Boolean narcotic = rs.getBoolean(9);
				Integer stock = rs.getInt(10);
				
				result = new Article(id, pzn, name, provider, dosage, packaging, sellingPrice, purchasingPrice, narcotic, stock);
			}
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}

	public void newArticle(Article article) throws SQLException {

		String sql = "INSERT INTO `drugs`.`ARTICLE` " + "(pzn, name, provider, dosage, packaging, selling_price, purchase_price, narcotic, stock) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection c = DriverManager.getConnection(CONNECTION_STRING); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, article.getPzn());
			ps.setString(2, article.getName());
			ps.setString(3, article.getProvider());
			ps.setString(4, article.getDosage());
			ps.setString(5, article.getPackaging());

			if (article.getSellingPrice() == null) {
				ps.setNull(6, Types.DOUBLE);
			} else {
				ps.setDouble(6, article.getSellingPrice());
			}

			if (article.getPurchasingPrice() == null) {
				ps.setNull(7, Types.DOUBLE);
			} else {
				ps.setDouble(7, article.getPurchasingPrice());
			}

			if (article.getNarcotic() == null) {
				ps.setNull(8, Types.BOOLEAN);
			} else {
				ps.setBoolean(8, article.getNarcotic());
			}

			if (article.getStock() == null) {
				ps.setNull(9, Types.INTEGER);
			} else {
				ps.setInt(9, article.getStock());
			}
			ps.executeUpdate();
		}

	}

	public void updateArticle(Article article) throws SQLException {

		String sql = "UPDATE ARTICLE SET " + 
					 "pzn = ?, name = ?, provider = ?, dosage = ?, packaging = ?, selling_price = ?, purchase_price = ?, narcotic = ?, stock = ? " + 
				     "WHERE id = ?";

		try (Connection c = DriverManager.getConnection(CONNECTION_STRING); PreparedStatement ps = c.prepareStatement(sql)) {
			
			Article oldArticle = findById(c, article.getId());
			
			if (article.getPzn() != null) {
				ps.setString(1, article.getPzn());
			} else {
				ps.setString(1, oldArticle.getPzn());
			}

			if (article.getName() != null) {
				ps.setString(2, article.getName());
			} else {
				ps.setString(2, oldArticle.getName());
			}

			if (article.getProvider() != null) {
				ps.setString(3, article.getProvider());
			} else {
				ps.setString(3, oldArticle.getProvider());
			}

			if (article.getDosage() != null) {
				ps.setString(4, article.getDosage());
			} else {
				ps.setString(4, oldArticle.getDosage());
			}

			if (article.getPackaging() != null) {
				ps.setString(5, article.getPackaging());
			} else {
				ps.setString(5, oldArticle.getPackaging());
			}

			if (article.getSellingPrice() != null) {
				ps.setDouble(6, article.getSellingPrice());
			} else {
				ps.setDouble(6, oldArticle.getSellingPrice());
			}

			if (article.getPurchasingPrice() != null) {
				ps.setDouble(7, article.getPurchasingPrice());
			} else {
				ps.setDouble(7, oldArticle.getPurchasingPrice());
			}

			if (article.getNarcotic() != null) {
				ps.setBoolean(8, article.getNarcotic());
			} else {
				ps.setBoolean(8, oldArticle.getNarcotic());
			}

			if (article.getStock() != null) {
				ps.setInt(9, article.getStock());
			} else {
				ps.setInt(9, oldArticle.getStock());
			}

			ps.setInt(10, article.getId());

			ps.executeUpdate();
		}
	}

	public void deleteArticle(int id) throws SQLException {
		String sql = "DELETE FROM ARTICLE WHERE id = ?";
		
		try (Connection c = DriverManager.getConnection(CONNECTION_STRING); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		}
	}
}
