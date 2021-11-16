package com.zuosuo.treehole.action.common;

import com.zuosuo.biudb.entity.BiuUserImageEntity;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.tool.FileTool;
import com.zuosuo.qiniu.tool.QiniuResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.tool.QiniuTool;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadAction extends BaseAction {

    private QiniuTool qiniuTool;
    private MultipartFile file;
    private UserProcessor userProcessor;

    public UploadAction(HttpServletRequest request, UserProcessor userProcessor, QiniuTool qiniuTool, MultipartFile file) {
        super(request);
        this.userProcessor = userProcessor;
        this.qiniuTool = qiniuTool;
        this.file = file;
    }

    @Override
    public JsonDataResult<Map> run() {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String ext = FileTool.fileExt(file.getOriginalFilename());
        File dir = new File("upload");
        if (!dir.exists()) {
            dir.mkdir();
        }
        String dest = String.join(".", dir.getAbsolutePath() + "/" + uuid, ext);
        File destFile = new File(dest);
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            destFile.delete();
            return new JsonDataResult<>("上传失败");
        }
        String fileHash = FileTool.fileMD5(dest);
        Map<String, String> result = new HashMap<>();
        BiuUserImageEntity image = userProcessor.getHashImage(getLoginInfoBean().getUserId(), fileHash);
        if (image != null) {
            result.put("key", image.getFile());
            result.put("hash", fileHash);
            result.put("url", qiniuTool.getLink(image.getFile()));
            destFile.delete();
            return JsonDataResult.success(result);
        }
        String key = dest.substring(dest.lastIndexOf("upload"));
        QiniuResult qnResult = qiniuTool.upload(dest, key);
        if (qnResult.isStatus()) {
            result.put("key", key);
            result.put("hash", fileHash);
            result.put("url", qiniuTool.getLink(key));
            userProcessor.initEmptyHashImage(getLoginInfoBean().getUserId(), key, fileHash);
            destFile.delete();
            return JsonDataResult.success(result);
        } else {
            destFile.delete();
            return new JsonDataResult<>("上传失败!");
        }
    }
}
