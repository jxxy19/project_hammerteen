package org.fullstack4.hammerteen.util;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


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

    //    /**
//     * POST 요청 보내기
//     * @param String paramUrl // 요청 url
//     * @param Map<String, String> paramMap // 요청 파라미터
//     * @return Strin
//     */
    public static String postConnection(String paramUrl, Map<String, String> paramMap) {
        try{
            if(parseString(paramUrl).isEmpty()){
                throw new Exception("URL is null!!");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

            if(!paramMap.isEmpty()){
                Set<String> key = paramMap.keySet();
                for (Object obj : key) {
                    String keyName = (String) obj;
                    String valueName = paramMap.get(keyName);

                    params.add(keyName, valueName);
                }
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    paramUrl,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            // 응답
            return response.getBody();

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String postConnection(String paramUrl, String token, Map<String, String> paramMap) {
        try{
            if(parseString(paramUrl).isEmpty()){
                throw new Exception("URL is null!!");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            headers.add("Authorization", token);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

            if(!paramMap.isEmpty()){
                Set<String> key = paramMap.keySet();
                for (Object obj : key) {
                    String keyName = (String) obj;
                    String valueName = paramMap.get(keyName);

                    params.add(keyName, valueName);
                }
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    paramUrl,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            // 응답
            return response.getBody();

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //    /**
//     * GET 요청 보내기
//     * @param String paramUrl // 요청 url
//     * @param Map<String, String> paramMap // 요청 파라미터
//     * @return Strin
//     */
    public static String getConnection(String paramUrl, Map<String, String> paramMap) {
        try{
            if(parseString(paramUrl).isEmpty()){
                throw new Exception("URL is null!!");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            if(!paramMap.isEmpty()){
                Set<String> key = paramMap.keySet();
                for (Object obj : key) {
                    String keyName = (String) obj;
                    String valueName = paramMap.get(keyName);

                    paramUrl += "&" + keyName + "=" + valueName;
                }
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    paramUrl,
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            );

            // 응답
            return response.getBody();

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject stringToJSON(String str) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String getResponse(String responseBody){
        String response = "";
        try {
            JSONObject jsonResp = CommonUtil.stringToJSON(responseBody);
            response = jsonResp.getString("response");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String getAccessToken(String responseBody){
        String accessToken = "";
        try {
            JSONObject jsonResp = CommonUtil.stringToJSON(responseBody);
            JSONObject jsonResp2 = CommonUtil.stringToJSON(jsonResp.getString("response"));
            accessToken = jsonResp2.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public static String comma(String str) {
        return String.format("%,d", parseInt(str));
    }
}
