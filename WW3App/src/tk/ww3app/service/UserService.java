package tk.ww3app.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import tk.ww3app.facade.UserFacade;
import tk.ww3app.model.User;

@Path("/us")
@ApplicationPath("/")

public class UserService extends Application{
	
	@EJB 
	UserFacade FacadeInjection;
	
	Logger logger = Logger.getLogger(UserService.class.getName());
	
	@GET
	@Path("/listusers")
	@Produces("application/json")
	public List<User> findAll(){
		return FacadeInjection.findAll();
	}
	
	@GET
    @Path("/getuser")
    @Produces("application/json")
    public User find(@QueryParam("id") Integer id) {
        return FacadeInjection.find(id);
    }
	
	@GET
	@Path("/checkuser/{user}/{pass}")
	@Produces("application/json")
	public JsonObject check(@PathParam("user") String user, @PathParam("pass") String pass){
		JsonObjectBuilder builder = Json.createObjectBuilder();
		boolean check =FacadeInjection.checkUser(user, pass);
		return builder.add("check",true).build();
	}
	

}
