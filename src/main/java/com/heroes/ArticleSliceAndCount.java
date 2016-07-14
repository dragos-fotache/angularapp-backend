package com.heroes;

import java.util.List;

public class ArticleSliceAndCount {
	
	private List<Article> articles;
	
	private int count;
	
	public ArticleSliceAndCount(List<Article> articles, int count) {
		this.articles = articles;
		this.count = count;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public int getCount() {
		return count;
	}
	
}
