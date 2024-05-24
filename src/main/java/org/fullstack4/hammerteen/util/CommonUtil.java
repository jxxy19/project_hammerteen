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
}
