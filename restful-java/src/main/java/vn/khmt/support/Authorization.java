/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.khmt.support;

import vn.khmt.entity.User;
import java.util.Base64;
import javax.ws.rs.core.HttpHeaders;
import vn.khmt.db.ConnectToSQL;

/**
 *
 * @author Luan
 */
public class Authorization {

    public static User authorized(HttpHeaders headers) {
        try {
            String authorizationHeader = headers.getRequestHeader("Authorization").get(0);
            String data = authorizationHeader.substring(authorizationHeader.indexOf(" ") +1 );

            byte[] bytes = com.sun.jersey.core.util.Base64.decode(data);
            String decoded = new String(bytes);
            int pos = decoded.indexOf(":");
            String username = decoded.substring(0, pos);
            String password = decoded.substring(pos+1);
            
            try {
                ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
                return conn.getUser(username, password);
            } catch(Exception e) {
                //return empty User object
                return new User();
            }
        } catch(Exception e) {
            //return empty User object
            return new User();
        }
    }
}
