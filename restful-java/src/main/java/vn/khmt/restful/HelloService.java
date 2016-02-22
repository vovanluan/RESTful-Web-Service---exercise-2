package vn.khmt.restful;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sun.misc.BASE64Decoder;
import vn.khmt.db.ConnectToSQL;
/**
 * REST Web Service
 *
 * @author Luan
 */
@Path("user")
public class HelloService {

    @Context 
    private HttpServletRequest request;
    
    @GET
    @Path("/{param}")

    public Response getUser(@PathParam("param") String id) throws IOException{
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        String result = conn.getUser(Integer.parseInt(id));
        String decoded;

        // Get the Authorisation Header from Request
        String header = request.getHeader("authorization");

        String data = header.substring(header.indexOf(" ") +1 );

        byte[] bytes = new BASE64Decoder().decodeBuffer(data);
        decoded = new String(bytes);
        int pos = decoded.indexOf(":");
        String username = decoded.substring(0, pos);
        String password = decoded.substring(pos+1);
        //Read username password from database
        JsonReader jsonReader = Json.createReader(new StringReader(result));
        JsonObject object = jsonReader.readObject();
        
        String usernameFromDB = object.getString("username"); 
        String passwordFromDB = object.getString("password");
        System.out.println(username);
        System.out.println(password);
        System.out.println(usernameFromDB);
        System.out.println(passwordFromDB);
        jsonReader.close();
        
        if (username.equals(usernameFromDB) && password.equals(passwordFromDB))
            return Response.status(200).entity(result).build();
        else 
            return Response.status(401).entity("Wrong username or password").build();

    }
    @GET
    @Path("/all")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserList() {
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        ArrayList<String> result = conn.getUserList();
        String show = "";
        for(int i = 0; i < result.size(); i++){
            show += result.get(i) +"\n";
        }
        return Response.status(200).entity(show).build();
    }
    
    @PUT
    @Path("/update")
    public Response updateName(@QueryParam("id") int id,
                               @QueryParam("name") String name) {
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        return Response.status(200).entity(conn.updateUserName(id, name)).build();
    }

}
