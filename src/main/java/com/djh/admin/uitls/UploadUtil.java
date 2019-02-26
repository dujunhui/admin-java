package com.djh.admin.uitls;


import com.djh.admin.model.sys.CommonResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Component
@Data
@Slf4j
public class UploadUtil {

    @Value("${file.localUploadPath}")
    private String localUploadPath; // 注入普通字符串

    Set<String> ext_set;

    //设置文件扩展名
    public UploadUtil( ){
        this.ext_set= new HashSet<>();
        ext_set.add("bmp");
        ext_set.add("jpeg");
        ext_set.add("jpg");
        ext_set.add("gif");
        ext_set.add("png");
    }

    //返回图片路径 upload/pay/201808/30/
    public String getUploadPath(String base){
        String path = "upload/"+ base+"/"+ new DateTime().toString("yyyyMM") +"/"+new DateTime().toString("dd")+"/";
        return  path;
    }

    //返回合法的扩展名，如果扩展名不合法返回null
    public String getPicFileExt(String fileName){
        String substring = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        if(ext_set.contains(substring)) {
            return  substring;
        }
        return  null;
    }



    public CommonResult saveFile(MultipartFile file, String catetgory, Integer uid) {
        CommonResult commonResult = CommonResultFactory.failed();
        //获取文件名和扩展名
        String fileName = file.getOriginalFilename();
        //返回合法的扩展名
        String picExt = this.getPicFileExt(fileName);
        if(picExt == null){
            commonResult.setMessage("图片扩展名不正确!");
            return  commonResult;
        }
        String picPath = this.getUploadPath(catetgory);
        File dir = new File(this.localUploadPath+picPath);

        File f = new File(this.localUploadPath+ picPath,String.valueOf(System.currentTimeMillis())+ "."+ picExt);
        try {
            if (!dir.exists()){//检查文件或目录是否存在
                dir.mkdirs();
            }
            //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
            Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.5f).toFile(f);

        } catch (IOException e) {
            log.error(e.toString());
            commonResult.setMessage("文件保存失败 , 请重试"+e.getMessage());
            return  commonResult;
        }
        commonResult.setSuccess(true);
        commonResult.setData(picPath+ f.getName());
        commonResult.setMessage("文件上传成功");
        return  commonResult;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名带路径
     */
    public CommonResult deleteFile(String fileName) {
        CommonResult commonResult = CommonResultFactory.failed();
        //返回合法的扩展名
        String picExt = this.getPicFileExt(fileName);
        if(picExt == null){
            commonResult.setMessage("图片扩展名不正确!");
            return  commonResult;
        }
        File file = new File(this.localUploadPath + fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                commonResult.setSuccess(true);
                commonResult.setMessage("删除单个文件" + fileName + "成功！");
                return commonResult;
            } else {
                commonResult.setMessage("删除单个文件" + fileName + "失败！");
                return commonResult;
            }
        } else {
            commonResult.setMessage("删除单个文件失败：" + fileName + "不存在！");
            return commonResult;
        }
    }
}
