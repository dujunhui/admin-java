package com.djh.admin.controller;

import com.djh.admin.model.Xsfpmx;
import com.djh.admin.service.XsfpmxService;

import javax.jws.WebService;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.namespace.QName;
import org.apache.axis.encoding.XMLType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.codehaus.xfire.client.Client;

import java.util.List;

/**
 * Created by Administrator on 2018/8/23.
 */
@RestController
@Slf4j
@RequestMapping("EMS")

@WebService(targetNamespace = "com.htxx.service")
public class YDController {

    @Autowired
    XsfpmxService xsfpmxService;

    @RequestMapping(value = "/SendData")
    public String sendData() {
        List<Xsfpmx> allXsfpmx = xsfpmxService.getAllXsfpmx();
        String mxStrs = "";
        for(Xsfpmx mx : allXsfpmx){
            String cpmc = mx.getCpmc();
            String xh = mx.getXh();
            String infoDetailStr =  "<InfoDetail>"
                    + "<GoodsName>"+ cpmc +"</GoodsName>"
                    + "<Standard>" + xh + "</Standard>"
                    + "<Unit>盒</Unit>"
                    + "<Num>1</Num>"
                    + "<Price>0.01</Price>"
                    + "<Amount>0.01</Amount>"
                    + "<TaxRate>0.16</TaxRate>"
                    + "<TaxAmount>0</TaxAmount>"
                    + "<Aigo>0</Aigo>"
                    + "<AigoTax>0</AigoTax>"
                    + "<GoodsGroup></GoodsGroup>"
                    + "<GoodsNoVer>33.0</GoodsNoVer>"
                    + "<GoodsTaxNo>1070302050000000000</GoodsTaxNo>"
                    + "<GoodsTaxName>化学药品制剂</GoodsTaxName>"
                    + "<TaxPre>0</TaxPre>"
                    + "<TaxPreCon></TaxPreCon>"
                    + "<ZeroTax></ZeroTax>"
                    + "<RowId></RowId>"
                    + "<Remark1></Remark1> <Remark2></Remark2>"
                    + "</InfoDetail>";
            mxStrs = mxStrs + infoDetailStr;
        }

        String endpoint = "http://css.sdaisino.com:9999/FPGLXT_FW/CXF/WbService?wsdl";// 远程请求访问wsdl文件
        String xmlBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><InfoMaster>"
                + "<Number>262436</Number>"
                + "<BusNo>262436</BusNo>"
                + "<Organ>1001</Organ>"
                + "<ClientName>威海迪沙xxx</ClientName>"
                + "<ClientTaxCode>91320509138274354R</ClientTaxCode>"
                + "<ClientBankAccount>中国民生银行吴江支行 630495103</ClientBankAccount>"
                + "<ClientAddressPhone>威海青岛南路 0512-62312623</ClientAddressPhone>"
                + "<ClientPhone>15612345678</ClientPhone>"
                + "<ClientMail>123@qq.com</ClientMail>"
                + "<BillType>1</BillType>"
                + "<InfoKind>51</InfoKind>"
                + "<Notes>备注</Notes>"
                + "<InvoiceCode></InvoiceCode>"
                + "<InvoiceNo></InvoiceNo>"
                + "<Invoicer>管理员</Invoicer>"
                + "<Checker>复核B</Checker>"
                + "<Cashier>收款A</Cashier>"
                + "<InvoicerCode></InvoicerCode>"
                + "<SumMoney>20</SumMoney>"
                + "<Times>2019-08-27</Times>"
                + "<SpecialInvoice></SpecialInvoice>"
                + "<Remark1></Remark1> <Remark2></Remark2>"
                +mxStrs+"</InfoMaster>";
        String returnData;
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            //call.setTargetEndpointAddress(endpoint);// 远程调用路径
            call.setTargetEndpointAddress(endpoint);
            //call.setOperationName("sendData");//WSDL里面描述的接口名称
            call.setOperationName(new QName("com.htxx.service", "sendData"));
            // 设置参数名: 参数名,参数类型,参数模式
            call.addParameter("arg0", XMLType.XSD_STRING, ParameterMode.IN);
            //call.addParameter("xmlBody", XMLType.XSD_STRING, ParameterMode.IN);
            call.setReturnType(XMLType.XSD_STRING);// 设置被调用方法的返回值类型
            returnData = (String) call.invoke(new Object[] { xmlBody });// 远程调用
            System.out.println("result is " + returnData);
            return returnData;
        } catch (Exception e) {
            System.err.println(e.toString());
            return e.toString();
        }

    }


}
