package com.heroes;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class RestService {
	@GET
	@Produces("application/json")
	@Path("/articles")
	public Response getAllArticles() throws ClassNotFoundException, SQLException {
		Collection<Article> output = ArticleService.INSTANCE.getArticles();
		
		return Response
					.status(200)
					.header("Access-Control-Allow-Origin", "*")
					.entity(output)
					.build();
	}
	
	@GET
	@Produces("application/json")
	@Path("/articles/{first}/{rows}/{sortField}/{sortOrder}")
	public Response getArticlesSliceAndCount(@PathParam("first") int first, 
											  @PathParam("rows") int rows,
											  @PathParam("sortField") String sortFieldParam,
											  @PathParam("sortOrder") String sortOrder) throws ClassNotFoundException, SQLException {
		
		String sortField;
		if ("undefined".equals(sortFieldParam)) {
			sortField = "id";
		} else {
			sortField = sortFieldParam;
		}
		List<Article> articles = ArticleService.INSTANCE.getArticlesSlice(first, rows, sortField, "desc".equals(sortOrder) ? true : false);
		int count = ArticleService.INSTANCE.getArticlesCount();
		
		ArticleSliceAndCount asc = new ArticleSliceAndCount(articles, count);
		
		return Response
				.status(200)
				.header("Access-Control-Allow-Origin", "*")
				.entity(asc)
				.build();
	}
	
	@GET
	@Produces("application/json")
	@Path("articles/count")
	public Response getArticleCount() throws SQLException {
		
		int count = ArticleService.INSTANCE.getArticlesCount();
		
		return Response
				.status(200)
				.header("Access-Control-Allow-Origin", "*")
				.entity(count)
				.build();
	}
}
