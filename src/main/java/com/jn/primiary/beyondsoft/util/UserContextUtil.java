package com.jn.primiary.beyondsoft.util;

import com.jn.primiary.beyondsoft.entity.SysUser;

public class UserContextUtil {
    private static ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();
    private static ThreadLocal<String> tokenHolder = new ThreadLocal<String>();

    public static void setUser(SysUser user) {
        userHolder.set(user);
    }
    public static SysUser getUser() {
        return userHolder.get();
    }
    public static String getUserName() {
        return userHolder.get().getUsername();
    }

    public static void removeUser(){
        userHolder.remove();
    }


    public static void setSaiSiToken(String token) {
        tokenHolder.set(token);
    }
    public static String getToken() {
        return tokenHolder.get();
    }
    public static void removeToken(){
        tokenHolder.remove();
    }

}
