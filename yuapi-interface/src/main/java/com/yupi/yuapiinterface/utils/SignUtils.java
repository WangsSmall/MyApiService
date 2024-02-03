package com.yupi.yuapiinterface.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @author 75654
 * @date 2024/1/29 21:35
 */
public class SignUtils {
    /**
     * 生成签名
     * @param body 包含需要的签名的哈希映射
     * @param secretKey secretKey 密钥
     * @return 生成的签名字符串
     */
    public static String genSign(String body, String secretKey) {
//        使用SHA256算法
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
//        签名内容
        String content = body + "." + secretKey;
//        计算签名摘要并返回 以十六进制表示形式
        return md5.digestHex(content);
    }
}
