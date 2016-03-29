package vn.khmt.restful;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import vn.khmt.db.ConnectToSQL;
import vn.khmt.entity.User;
import vn.khmt.support.Authorization;

/**
 * REST Web Service
 *
 * @author Luan
 */
@Path("user")
public class UserService {
    public static final Integer ADMIN_STATUS = 3;
    
    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("param") String id, @Context HttpHeaders headers) throws IOException, SQLException{
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        User user = conn.getUser(Integer.parseInt(id));
        User authenticated = Authorization.authorized(headers);
//        System.out.println(user.getUsername());
//        System.out.println(authenticated.getUsername());
        if ((authenticated.getUsername().equals("admin") && authenticated.getPassword().equals("admin")) || (authenticated.getUsername().equals(user.getUsername()) && authenticated.getPassword().equals(user.getPassword()))) {
            System.out.println("Won Oscar");
            return Response.status(200).entity(user).build();
        }
        else {
            System.out.println("Lost again");
            return Response.status(401).entity("").build();
        }
    }
    
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@QueryParam("username") String username,
                            @QueryParam("password") String password) {
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        User user = conn.getUser(username, password);
        if (user == null) {
            return Response.status(404).entity(new User()).build();
        }
        return Response.status(200).entity(user).build();
    }
    
    @GET
    @Path("/all")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserList(@Context HttpHeaders headers) {
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        User autheticated = Authorization.authorized(headers);
        if (autheticated.getStatus() == ADMIN_STATUS) {
            ArrayList<User> result = conn.getUserList();
            return Response.status(200).entity(new GenericEntity<ArrayList<User>>(result){}).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(null).build();        
    }
    
    @PUT
    @Path("/rename")
    public Response updateName(@QueryParam("id") int id,
                               @QueryParam("name") String name) {
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        return Response.status(200).entity(conn.updateUserName(id, name)).build();
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addName(User u) {
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");     
        boolean result = conn.addUser(u);
        if(result) {
            return Response.status(200).entity("Success").build();
        }        
        return Response.status(Response.Status.CONFLICT).entity("Failed").build();
    }

}
