package com.yupi.yuapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.yuapiclientsdk.model.User;
import com.yupi.yuapiclientsdk.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 75654
 * @date 2024/1/29 16:33
 */
public class YuApiClient {
    private String accessKey;
    private String secretKey;

    public YuApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println("Hou::" + result);
        return result;
    }

    public String getNameByPost(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println("Hou::" + result);
        return result;
    }

    public String getUsernameByPost(User user) {
//        转换JSON
        String url = "http://localhost:8123/api/name/user/";
        String jsonStr = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(url)
                .addHeaders(getHeadMap(jsonStr))
                .body(jsonStr) // 将JSON字符串设置为请求体
                .execute(); // 执行请求
//
        System.out.println(httpResponse.getStatus());
        String body = httpResponse.body();
        System.out.println("Hou::" + body);

        return body;
    }

    private Map<String, String> getHeadMap(String body) {
//        构造请求头
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
//        注意：禁止直接发送密钥
//        hashMap.put("secretKey", secretKey);

//        随机生成数
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
//        请求体内容
        hashMap.put("body", body);
//        当前时间戳 秒
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
//        密钥
        hashMap.put("sign", SignUtils.genSign(body, secretKey));

        return hashMap;
    }

}
