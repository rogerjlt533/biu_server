package com.zuosuo.treehole.action.common;

import com.zuosuo.biudb.entity.BiuUserImageEntity;
import com.zuosuo.component.response.FuncResult;
import com.zuosuo.component.response.JsonDataResult;
import com.zuosuo.component.tool.FileTool;
import com.zuosuo.qiniu.tool.QiniuResult;
import com.zuosuo.treehole.action.BaseAction;
import com.zuosuo.treehole.processor.UserProcessor;
import com.zuosuo.treehole.task.ProcessWechatFilterTask;
import com.zuosuo.treehole.tool.QiniuTool;
import com.zuosuo.treehole.tool.WechatTool;
import net.coobird.thumbnailator.Thumbnails;
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
    private WechatTool wechatTool;

    public UploadAction(HttpServletRequest request, UserProcessor userProcessor, QiniuTool qiniuTool, WechatTool wechatTool, MultipartFile file) {
        super(request);
        this.userProcessor = userProcessor;
        this.qiniuTool = qiniuTool;
        this.wechatTool = wechatTool;
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
        try {
            Thumbnails.of(dest).size(750, 1000).toFile(dest);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        FuncResult filterResult = wechatTool.asyncFilterMedia(destFile);
        if (!filterResult.isStatus()) {
            destFile.delete();
            return new JsonDataResult<>(503, "输入信息违规");
        }
        String fileHash = FileTool.fileMD5(dest);
        Map<String, String> result = new HashMap<>();
        BiuUserImageEntity image = userProcessor.getUserService().getHashImage(getLoginInfoBean().getUserId(), fileHash);
        if (image != null) {
            result.put("key", image.getFile());
            result.put("hash", fileHash);
            result.put("url", qiniuTool.getLink(image.getFile()));
            destFile.delete();
//            userProcessor.getUserService().filterByWechat(ProcessWechatFilterTask.FILTER_MEDIA, getLoginInfoBean().getUserId(), ProcessWechatFilterTask.MEDIA_IMAGE_TYPE, image.getId());
            return JsonDataResult.success(result);
        }
        String key = dest.substring(dest.lastIndexOf("upload"));
        QiniuResult qnResult = qiniuTool.upload(dest, key);
        if (qnResult.isStatus()) {
            result.put("key", key);
            result.put("hash", fileHash);
            result.put("url", qiniuTool.getLink(key));
            image = userProcessor.getUserService().initEmptyHashImage(getLoginInfoBean().getUserId(), key, fileHash);
            destFile.delete();
//            userProcessor.getUserService().filterByWechat(ProcessWechatFilterTask.FILTER_MEDIA, getLoginInfoBean().getUserId(), ProcessWechatFilterTask.MEDIA_IMAGE_TYPE, image.getId());
            return JsonDataResult.success(result);
        } else {
            destFile.delete();
            return new JsonDataResult<>("上传失败!");
        }
    }
}
