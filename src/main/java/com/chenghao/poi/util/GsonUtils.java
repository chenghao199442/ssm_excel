package com.chenghao.poi.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonUtils {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm").create();

    public static String object2json(Object o) {
        return gson.toJson(o);
    }

    public static Object json2object(String s, Class c) {
        return gson.fromJson(s, c);
    }

    public static List json2list(String s, TypeToken tt) {
        return gson.fromJson(s, tt.getType());
    }
}
