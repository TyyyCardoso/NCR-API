package ipt.lei.dam.ncrapi.controller.authentication;

import ipt.lei.dam.ncrapi.database.entities.User;
import ipt.lei.dam.ncrapi.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthBO {

    public static boolean validateUserEmail(String email){
        if(StringUtils.isStringNullOrEmpty(email)){
           return false;
        }
        if(!StringUtils.isValidEmail(email)){
            return false;
        }

        return true;
    }

    public static List<String> getUserPasswordPositions(String password){
        return new ArrayList<String>(Arrays.asList("1","3","5"));
    }

    public static List<String> getUserDefaultPasswordPositions(){
        return new ArrayList<String>(Arrays.asList("1","3","5"));
    }
}
