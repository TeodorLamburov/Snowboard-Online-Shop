package onlineshop.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {


    public static String getUser(){
        String name;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        name = auth.getName();
        if ("anonymousUser".equals(name)){
            name = null;
        }
        return name;
    }

}
