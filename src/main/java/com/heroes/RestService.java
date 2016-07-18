package com.heroes;

import java.sql.SQLException;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class RestService extends org.glassfish.jersey.server.ResourceConfig {
	
	@GET
	@Produces("application/json")
	@Path("/articles")
	public Response getAllArticles() throws ClassNotFoundException, SQLException {
		Collection<Article> output = ArticleService.INSTANCE.getArticles();
		
		return Response
					.status(200)
					.entity(output)
					.build();
	}
	
	@GET
	@Produces("application/json")
	@Path("/articles/{first}/{rows}/{sortField}/{sortOrder}/{searchString}")
	public Response getArticlesSliceAndCount(@PathParam("first") int first, 
											  @PathParam("rows") int rows,
											  @PathParam("sortField") String sortFieldParam,
											  @PathParam("sortOrder") String sortOrder,
											  @PathParam("searchString") String searchStringParam
											  ) throws ClassNotFoundException, SQLException {
		
		String sortField;
		if ("undefined".equals(sortFieldParam)) {
			sortField = "id";
		} else {
			sortField = sortFieldParam;
		}
		String searchString;
		if ("*".equals(searchStringParam)) {
			searchString = "%";
		} else {
			searchString = searchStringParam;
		}
		ArticleSliceAndCount result = 
				ArticleService.INSTANCE.getArticlesSliceAndCount(first, 
																 rows, 
																 sortField, 
																 "desc".equals(sortOrder) ? true : false,
																 searchString);
		
		return Response
				.status(200)
				.entity(result)
				.build();
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/articles/new")
	public Response newArticle(Article article) throws SQLException {
		
		ArticleService.INSTANCE.newArticle(article);
		
		return Response
				.status(200)
				.build();
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/articles/update")
	public Response updateArticle(Article article) throws SQLException {
		
		ArticleService.INSTANCE.updateArticle(article);
		
		return Response
				.status(200)
				.build();
	}
	
	@POST
	@Produces("application/json")
	@Path("/articles/delete/{id}")
	public Response deleteArticle(@PathParam("id") int id) throws SQLException {
		
		ArticleService.INSTANCE.deleteArticle(id);
		
		return Response
				.status(200)
				.build();
	}

}
