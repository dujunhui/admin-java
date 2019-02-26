package com.djh.admin.uitls;


import com.djh.admin.model.sys.CommonResult;

/**
 * Created by Administrator on 2018/5/5.
 */
public class CommonResultFactory {
    public static CommonResult success(){
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(true);
        commonResult.setCode(20000);
        return  commonResult;
    }
    public  static CommonResult success(String msg){
        CommonResult commonResult = CommonResultFactory.success();
        commonResult.setMessage(msg);
        return commonResult;
    }

    public static CommonResult failed(){
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(false);
        return  commonResult;
    }
    public static CommonResult failed(String msg){
        CommonResult commonResult = CommonResultFactory.failed();
        commonResult.setMessage(msg);
        return  commonResult;
    }
}
