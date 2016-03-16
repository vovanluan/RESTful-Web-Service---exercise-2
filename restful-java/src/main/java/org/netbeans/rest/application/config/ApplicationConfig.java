package org.netbeans.rest.application.config;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import vn.khmt.restful.UserService;
import vn.khmt.support.CORSResponseFilter;
<<<<<<< HEAD
=======



>>>>>>> 9f54e67764b38a9565d1094f3e410b92a772d7ce
/**
 *
 * @author The Nhan
 */
@ApplicationPath("")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        
        s.add(CORSResponseFilter.class);
        s.add(UserService.class);
        return s;
    }
}
