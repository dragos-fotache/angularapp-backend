package com.heroes;

import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/")
public class RestService {
	
	@GET
	@Produces("application/json")
	@Path("/articles/login")
	public Response login(@HeaderParam("Authorization") String auth,
						  @Context HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		try {
			if (auth == null) {
				return Response
							.status(Response.Status.UNAUTHORIZED)
							.build();
			} else {
				boolean authorized = AuthenticationService.INSTANCE.checkAuthorization(auth);
				if (authorized) {
					session.setAttribute("authorized", true);
					return Response
							.status(Response.Status.OK)
							.entity("Login accepted")
							.build();
				} else {
					return Response
							.status(Response.Status.UNAUTHORIZED)
							.entity("Wrong login")
							.build();
				}
			}
		} catch (Exception e) {
			return Response
						.status(Response.Status.INTERNAL_SERVER_ERROR)
						.build();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/articles/logout")
	public Response logout(@Context HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("authorized");
		
		return Response
				.status(Response.Status.OK)
				.entity("")
				.build();
		
	}
	
	@GET
	@Produces("application/json")
	@Path("/articles/test")
	public Response test(@Context HttpServletRequest request) {
		
		boolean sessionAuthorized = AuthenticationService.INSTANCE.sessionAuthorized(request);
		
		if (sessionAuthorized) {
			return Response
					.status(Response.Status.OK)
					.entity("Call accepted")
					.build();
		} else {
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.entity("Session is unauthorized!")
					.build();
		}
		
	}
	
	
	@GET
	@Produces("application/json")
	@Path("/articles")
	public Response getAllArticles(@Context HttpServletRequest request) throws ClassNotFoundException, SQLException {

		boolean sessionAuthorized = AuthenticationService.INSTANCE.sessionAuthorized(request);
		
		if (sessionAuthorized) {
		
			Collection<Article> output = ArticleService.INSTANCE.getArticles();
		
			return Response
						.status(Response.Status.OK)
						.entity(output)
						.build();
		} else {
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.build();
		}
	}

	@GET
	@Produces("application/json")
	@Path("/articles/{first}/{rows}/{sortField}/{sortOrder}/{searchString}")
	public Response getArticlesSliceAndCount(@PathParam("first") int first, 
											  @PathParam("rows") int rows,
											  @PathParam("sortField") String sortFieldParam,
											  @PathParam("sortOrder") String sortOrder,
											  @PathParam("searchString") String searchStringParam,
											  @Context HttpServletRequest request
											  ) throws ClassNotFoundException, SQLException {
		
		boolean sessionAuthorized = AuthenticationService.INSTANCE.sessionAuthorized(request);
		
		if (sessionAuthorized) {
		
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
					.status(Response.Status.OK)
					.entity(result)
					.build();
		} else {
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.build();
		}
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/articles/new")
	public Response newArticle(Article article,
							  @Context HttpServletRequest request) throws SQLException {
		
		boolean sessionAuthorized = AuthenticationService.INSTANCE.sessionAuthorized(request);
		
		if (sessionAuthorized) {
		
			ArticleService.INSTANCE.newArticle(article);
			
			return Response
					.status(Response.Status.OK)
					.build();
		} else {
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.build();
		}
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/articles/update")
	public Response updateArticle(Article article,
								  @Context HttpServletRequest request) throws SQLException {
		
		boolean sessionAuthorized = AuthenticationService.INSTANCE.sessionAuthorized(request);
		
		if (sessionAuthorized) {
		
			ArticleService.INSTANCE.updateArticle(article);
			
			return Response
					.status(Response.Status.OK)
					.build();
		} else {
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.build();
		}
	}
	
	@POST
	@Produces("application/json")
	@Path("/articles/delete/{id}")
	public Response deleteArticle(@PathParam("id") int id,
								  @Context HttpServletRequest request) throws SQLException {
		boolean sessionAuthorized = AuthenticationService.INSTANCE.sessionAuthorized(request);
		
		if (sessionAuthorized) {
			ArticleService.INSTANCE.deleteArticle(id);
			
			return Response
					.status(Response.Status.OK)
					.build();
		} else {
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.build();
		}
	}

}
