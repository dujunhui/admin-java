package com.djh.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.djh.admin.service.EMSService;
import com.djh.admin.uitls.HttpPost;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/23.
 */
@RestController
@Slf4j
@RequestMapping("EMS")
public class EMSController {

    //2.普通运单导入接口（有运单号，支持一票多件）
    @RequestMapping(value = "/emsHttpTest2")
    public String emsHttpTest2() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tsStr = sdf.format(ts);
        String version = "V0.1";
        String method = "ems.inland.waybill.create.normal";
        String format = "json";
        String app_key = "ff3edb9e3ccd5329128e00e69b08c452";
        String charset = "UTF-8";
        String authorization = "e5751eb89b6760ba146e786fc2926367"; // 授权码
        String appSecret = "1c70d09c96761e04f8f1c6d36e3cd007";

        Map<String, String> params = new HashMap<>() ;

        //系统级参数
        params.put("method", method);
        params.put("authorization", authorization);
        params.put("timestamp", tsStr);
        params.put("version", version);
        params.put("format", format);
        params.put("app_key", app_key);

        //接口级参数
        Map<String, Object> map1 = new HashMap<>();
        map1.put("txLogisticID", "lyz220160318101530");
        map1.put("mailNum", "11098991333199");
        map1.put("orderType", 3);
        map1.put("serviceType", 0);
        map1.put("insuredAmount", 1200);
        map1.put("revertMailNo", "1109899133133");
        map1.put("postage", 2200);

        Map<String, Object> sender = new HashMap<>();
        sender.put("name","寄件人");
        sender.put("postCode","100000");
        sender.put("phone","12345678");
        sender.put("mobile","15187654321");
        sender.put("prov","湖南省");
        sender.put("city","长沙市");
        sender.put("address","湖北仙桃");
        map1.put("sender", sender);

        Map<String, Object> receiver = new HashMap<>();
        receiver.put("name", "收件人");
        receiver.put("postCode", "450000");
        receiver.put("phone", "12345678");
        receiver.put("mobile", "15112345678");
        receiver.put("prov", "吉林省");
        receiver.put("city", "吉林市");
        receiver.put("address", "吉林延边");
        map1.put("receiver", receiver);

        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("name","测试1");
        item1.put("number",3);
        item1.put("value",2000);
        Map<String, Object> item2 = new HashMap<>();
        item2.put("name","测试2");
        item2.put("number",4);
        item2.put("value",1000);
        Map<String, Object> item3 = new HashMap<>();
        item3.put("name","测试3");
        item3.put("number",4);
        item3.put("value",1000);
        items.add(item1);items.add(item2);items.add(item3);
        map1.put("items", items);

//map2
        Map<String, Object> map2 = new HashMap<>();
        map2.put("txLogisticID", "lyz220160318101530");
        map2.put("mailNum", "11098991333199");
        map2.put("orderType", 3);
        map2.put("serviceType", 0);
        map2.put("insuredAmount", 1200);
        map2.put("revertMailNo", "1109899133133");
        map2.put("postage", 2200);

        Map<String, Object> sender2 = new HashMap<>();
        sender2.put("name","寄件人");
        sender2.put("postCode","100000");
        sender2.put("phone","12345678");
        sender2.put("mobile","15187654321");
        sender2.put("prov","湖南省");
        sender2.put("city","长沙市");
        sender2.put("address","湖北仙桃");
        Map<String, Object> receiver2 = new HashMap<>();
        receiver2.put("name", "收件人");
        receiver2.put("postCode", "450000");
        receiver2.put("phone", "12345678");
        receiver2.put("mobile", "15112345678");
        receiver2.put("prov", "吉林省");
        receiver2.put("city", "吉林市");
        receiver2.put("address", "吉林延边");

        map2.put("sender", sender2);
        map2.put("receiver", receiver2);
        map2.put("items", items);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map1);
        list.add(map2);
        Map<String, Object> waybills = new HashMap<>();
        waybills.put("waybills", list);

        String waybillsStr= JSONObject.toJSONString(waybills);

        params.put("waybill", waybillsStr);
        params.put("size", "2");

        String content = EMSService.getSortParams(params) + appSecret;
        String sign = EMSService.sign(content, charset);

        String url="http://211.156.193.124/api/getaway";

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //系统级参数
        nvps.add(new BasicNameValuePair("sign", sign));
        nvps.add(new BasicNameValuePair("method", method));
        nvps.add(new BasicNameValuePair("authorization", authorization));
        nvps.add(new BasicNameValuePair("timestamp", tsStr));
        nvps.add(new BasicNameValuePair("version", version));
        nvps.add(new BasicNameValuePair("format", format));
        nvps.add(new BasicNameValuePair("app_key", app_key));
        //接口级参数
        nvps.add(new BasicNameValuePair("waybill", waybillsStr));
        nvps.add(new BasicNameValuePair("size", "2"));

        try {
            String output = HttpPost.sendPost(url, nvps);
            log.info(output);
            return output;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }


    //7.揽收范围查询接口
    //OK
    @RequestMapping(value = "/emsHttpTest7")
    public String emsHttpTest7() {

        Map<String, String> params = new HashMap<>() ;

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tsStr = sdf.format(ts);

        String version = "V0.1";
        String method = "ems.inland.collection.range";
        String format = "json";
        String app_key = "ff3edb9e3ccd5329128e00e69b08c452";
        String charset = "UTF-8";
        String authorization = "e5751eb89b6760ba146e786fc2926367"; // 授权码
        String appSecret = "1c70d09c96761e04f8f1c6d36e3cd007";

        //系统级参数
        params.put("timestamp", tsStr);
        params.put("version", version);
        params.put("format", format);
        params.put("app_key", app_key);
        params.put("method", method);
        //接口级参数
        params.put("prov", "山东省");
        params.put("city", "威海市");
        params.put("county", "环翠区");

        String content = EMSService.getSortParams(params) + appSecret;
        String sign = EMSService.sign(content, charset);

        String url="http://211.156.193.124/api/getaway";

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        //系统级参数
        nvps.add(new BasicNameValuePair("sign", sign));
        nvps.add(new BasicNameValuePair("method", method));
        nvps.add(new BasicNameValuePair("timestamp", tsStr));
        nvps.add(new BasicNameValuePair("version", version));
        nvps.add(new BasicNameValuePair("format", format));
        nvps.add(new BasicNameValuePair("app_key", app_key));
        //接口级参数
        nvps.add(new BasicNameValuePair("prov", "山东省"));
        nvps.add(new BasicNameValuePair("city", "威海市"));
        nvps.add(new BasicNameValuePair("county", "环翠区"));

        try {
            String output = HttpPost.sendPost(url, nvps);
           log.info(output);
           return output;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }


    //8.名址匹配地址辅助录入接口
    //OK
    @RequestMapping(value = "/emsHttpTest8")
    public String emsHttpTest8() {

        Map<String, String> params = new HashMap<>() ;

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tsStr = sdf.format(ts);

        String version = "V0.1";
        String method = "ems.inland.detail.matching";
        String format = "json";
        String app_key = "ff3edb9e3ccd5329128e00e69b08c452";
        String charset = "UTF-8";
        String authorization = "e5751eb89b6760ba146e786fc2926367"; // 授权码
        String appSecret = "1c70d09c96761e04f8f1c6d36e3cd007";

        //系统级参数
        params.put("method", method);
        params.put("timestamp", tsStr);
        params.put("version", version);
        params.put("format", format);
        params.put("app_key", app_key);
        //接口级参数
        params.put("prov", "山东省");
        params.put("city", "威海市");
        params.put("county", "环翠区");
        params.put("detailInfo", "青岛南路1号");

        String content = EMSService.getSortParams(params) + appSecret;
        String sign = EMSService.sign(content, charset);

        String url="http://211.156.193.124/api/getaway";

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        //系统级参数
        nvps.add(new BasicNameValuePair("sign", sign));
        nvps.add(new BasicNameValuePair("method", method));
        nvps.add(new BasicNameValuePair("timestamp", tsStr));
        nvps.add(new BasicNameValuePair("version", version));
        nvps.add(new BasicNameValuePair("format", format));
        nvps.add(new BasicNameValuePair("app_key", app_key));
        //接口级参数
        nvps.add(new BasicNameValuePair("prov", "山东省"));
        nvps.add(new BasicNameValuePair("city", "威海市"));
        nvps.add(new BasicNameValuePair("county", "环翠区"));
        nvps.add(new BasicNameValuePair("detailInfo", "青岛南路1号"));

        try {
            String output = HttpPost.sendPost(url, nvps);
            log.info(output);
            return output;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //9.名址匹配接入接口(新增-2014-12-2)
    //java.lang.NullPointerException
    @RequestMapping(value = "/emsHttpTest9")
    public String emsHttpTest9() {

        Map<String, String> params = new HashMap<>() ;

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tsStr = sdf.format(ts);

        String version = "V0.1";
        String method = "ems.inland.detail.access";
        String format = "json";
        String app_key = "ff3edb9e3ccd5329128e00e69b08c452";
        String charset = "UTF-8";
        String authorization = "e5751eb89b6760ba146e786fc2926367"; // 授权码
        String appSecret = "1c70d09c96761e04f8f1c6d36e3cd007";

        //系统级参数
        params.put("method", method);
        params.put("timestamp", tsStr);
        params.put("version", version);
        params.put("format", format);
        params.put("app_key", app_key);
        //接口级参数
        params.put("addressId", "11");
        params.put("provinceCode", "000");
        params.put("cityCode", "000");
        params.put("countyCode", "000");
        params.put("addressDesc", "000");

        String content = EMSService.getSortParams(params) + appSecret;
        String sign = EMSService.sign(content, charset);

        String url="http://211.156.193.124/api/getaway";

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        //系统级参数
        nvps.add(new BasicNameValuePair("sign", sign));
        nvps.add(new BasicNameValuePair("method", method));
        nvps.add(new BasicNameValuePair("timestamp", tsStr));
        nvps.add(new BasicNameValuePair("version", version));
        nvps.add(new BasicNameValuePair("format", format));
        nvps.add(new BasicNameValuePair("app_key", app_key));
        //接口级参数
        nvps.add(new BasicNameValuePair("addressId", "11"));
        nvps.add(new BasicNameValuePair("provinceCode", "000"));
        nvps.add(new BasicNameValuePair("cityCode", "000"));
        nvps.add(new BasicNameValuePair("countyCode", "000"));
        nvps.add(new BasicNameValuePair("addressDesc", "000"));

        try {
            String output = HttpPost.sendPost(url, nvps);
            log.info(output);
            return output;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //10.大客户固定地址维护接口
    //OK
    @RequestMapping(value = "/emsHttpTest10")
    public String emsHttpTest10() {

        Map<String, String> params = new HashMap<>() ;

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tsStr = sdf.format(ts);

        String version = "V1.1";
        String method = "ems.inland.waybill.collect.address";
        String format = "json";
        String app_key = "ff3edb9e3ccd5329128e00e69b08c452";
        String charset = "UTF-8";
        String authorization = "e5751eb89b6760ba146e786fc2926367"; // 授权码
        String appSecret = "1c70d09c96761e04f8f1c6d36e3cd007";

        //系统级参数
        params.put("method", method);
        params.put("authorization", authorization);
        params.put("timestamp", tsStr);
        params.put("version", version);
        params.put("format", format);
        params.put("app_key", app_key);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("addressId", "11");
        map1.put("excuType", 1);
        map1.put("custName", "aaaa");
        map1.put("postCode", "421003");
        map1.put("prov", "湖北省");
        map1.put("city", "武汉市");
        map1.put("county", "武昌区");
        map1.put("address", "学院路11号");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("addressId", "12");
        map2.put("excuType", 1);
        map2.put("custName", "aaaa");
        map2.put("postCode", "421003");
        map2.put("prov", "湖北省");
        map2.put("city", "武汉市");
        map2.put("county", "武昌区");
        map2.put("address", "学院路11号");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map1);
        list.add(map2);
        Map<String, Object> collectInfo = new HashMap<>();
        collectInfo.put("collectInfos", list);

        String collectInfoStr= JSONObject.toJSONString(collectInfo);

        params.put("collectInfo", collectInfoStr);
        params.put("size", "2");

        String content = EMSService.getSortParams(params) + appSecret;
        String sign = EMSService.sign(content, charset);

        String url="http://211.156.193.124/api/getaway";

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        //系统级参数
        nvps.add(new BasicNameValuePair("sign", sign));
        nvps.add(new BasicNameValuePair("method", method));
        nvps.add(new BasicNameValuePair("authorization", authorization));
        nvps.add(new BasicNameValuePair("timestamp", tsStr));
        nvps.add(new BasicNameValuePair("version", version));
        nvps.add(new BasicNameValuePair("format", format));
        nvps.add(new BasicNameValuePair("app_key", app_key));
        //接口级参数
        nvps.add(new BasicNameValuePair("collectInfo", collectInfoStr));
        nvps.add(new BasicNameValuePair("size", "2"));

        try {
            String output = HttpPost.sendPost(url, nvps);
            log.info(output);
            return output;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //1.应用刷新用户授权接口(根据授权码刷新授权码)
    // oldAuthorization参数出错
    @RequestMapping(value = "/emsHttpTest1")
    public String emsHttpTest1() {

        Map<String, String> params = new HashMap<>() ;

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tsStr = sdf.format(ts);

        String version = "V0.1";
        String method = "ems.permission.user.permit.refresh";
        String format = "json";
        String app_key = "ff3edb9e3ccd5329128e00e69b08c452";
        String charset = "UTF-8";
        String oldAuthorization = "e5751eb89b6760ba146e786fc2926367"; // 授权码
        String customerCode = "90000002990886 "; // 授权码
        String appSecret = "1c70d09c96761e04f8f1c6d36e3cd007";

        //系统级参数
        params.put("method", method);
        params.put("timestamp", tsStr);
        params.put("version", version);
        params.put("format", format);
        params.put("app_key", app_key);
        //接口级参数
        params.put("oldAuthorization", oldAuthorization);
        params.put("customerCode", customerCode);

        String content = EMSService.getSortParams(params) + appSecret;
        String sign = EMSService.sign(content, charset);

        String url="http://211.156.193.124/api/getaway";

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        //系统级参数
        nvps.add(new BasicNameValuePair("sign", sign));
        nvps.add(new BasicNameValuePair("method", method));
        nvps.add(new BasicNameValuePair("timestamp", tsStr));
        nvps.add(new BasicNameValuePair("version", version));
        nvps.add(new BasicNameValuePair("format", format));
        nvps.add(new BasicNameValuePair("app_key", app_key));
        //接口级参数
        nvps.add(new BasicNameValuePair("oldAuthorization", oldAuthorization));
        nvps.add(new BasicNameValuePair("customerCode", customerCode));

        try {
            String output = HttpPost.sendPost(url, nvps);
            log.info(output);
            return output;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }


}
