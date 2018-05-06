package com.xlong.loserlivegame;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SLP on 2017/3/22.
 */

public class Tools {
    public static int getStringNum(String string) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(string);
        return Integer.parseInt(m.replaceAll("".trim()));
    }
}
