package com.djh.admin.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Administrator on 2018/8/24.
 */
@Slf4j
@Service
public class EMSService {

    //参数排序并拼装成字符串
    public static String getSortParams(Map<String, String> params) {
        // 删掉sign参数
        params.remove("sign");
        String contnt = "";
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        for (String key : keySet) {
            String value = params.get(key);
            // 将值为空的参数排除
//            if (!StringUtil.isNull()) {
//                keyList.add(key);
//            }
            if (value!=null){
                keyList.add(key);
            }
        }
        Collections.sort(keyList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int length = Math.min(o1.length(), o2.length());
                for (int i = 0; i < length; i++) {
                    char c1 = o1.charAt(i);
                    char c2 = o2.charAt(i);
                    int r = c1 - c2;
                    if (r != 0) {
                        // char值小的排前边
                        return r;
                    }
                }
                // 2个字符串关系是str1.startsWith(str2)==true
                // 取str2排前边
                return o1.length() - o2.length();
            }
        });

        //将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            contnt += key + params.get(key);
        }
        return contnt;
    }

    //得到“生成签名的字符串”
    //String content = getSortParams(params) + "1c70d09c96761e04f8f1c6d36e3cd007";

    //对“生成签名的字符串”进行MD5，并进行BASE64操作，根据请求编码得到签名
    public static String sign(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            charset = "UTF-8";
        }
        String sign = "";
        try {
            // content = new String(content.getBytes(), charset);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder encoder = Base64.getEncoder();
            // sign = BASE64Encoder.encode(md5.digest(content.getBytes(charset)));
            byte[] textByte = content.getBytes(charset);
            sign = encoder.encodeToString(md5.digest(textByte));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sign;
    }

}
