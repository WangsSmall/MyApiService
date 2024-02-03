package com.yupi.yuapiinterface.controller;

import cn.hutool.core.util.RandomUtil;
import com.yupi.yuapiinterface.model.User;
import com.yupi.yuapiinterface.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Api名称
 *
 * @author 75654
 * @date 2024/1/29 14:50
 */
@RestController
@RequestMapping("name")
public class NameController {
    @GetMapping("/")
    public String getNameByGet(String name) {
        return "GET 你的名字是" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "Post 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
//        签名 密码
        String accessKey = request.getHeader("accessKey");
//        随机生成数
        String nonce = request.getHeader("nonce");
//        请求体内容
        String body = request.getHeader("body");
//        当前时间戳 秒
        String timestamp = request.getHeader("timestamp");
//        密钥
        String sign = request.getHeader("sign");


        // todo 实际情况应该是去数据库中查是否已分配给用户
        // 校验
        if (!accessKey.equals("yupi")){
            throw new RuntimeException("无权限");
        }
        // 校验随机数，模拟一下，直接判断nonce是否大于10000
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }

        // todo 时间和当前时间不能超过5分钟
//        if (timestamp) {}

        // todo 这里的密钥需要访问数据库获取
        String serverSign = SignUtils.genSign(body, "abcdefgh");
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("无权限");
        }

        return "Post 用户名字是" + user.getUsername();
    }
}
