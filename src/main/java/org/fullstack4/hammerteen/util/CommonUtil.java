package org.fullstack4.hammerteen.util;

import java.util.HashMap;
import java.util.Map;


public class CommonUtil {
    public static Map<String, String> setPageType(String menu1,String menu2) {
        Map<String,String> pageType = new HashMap<>();
        pageType.put("menu1", menu1);
        pageType.put("menu2", menu2);
        return pageType;
    }

    public static String parseString(Object obj) {
        return (obj != null) ? (String)obj : "";
    }
    public static int parseInt(Object obj) {
        int result = 0;
        try {
            String str = parseString(obj);
            result = Integer.parseInt(str);
        } catch (Exception ignored) {}
        return result;
    }

    public static String comma(String str) {
        return String.format("%,d", parseInt(str));
    }
}
