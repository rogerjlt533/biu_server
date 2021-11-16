package com.zuosuo.component.tool;

import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileTool {

    public static String fileMD5(String path) {
        String md5 = null;
        try (FileInputStream stream = new FileInputStream(ResourceUtils.getFile(path))) {
            md5 = DigestUtils.md5DigestAsHex(stream);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return md5;
    }

    public static String fileExt(String name) {
        if (name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0) {
            return name.substring(name.lastIndexOf(".") + 1);
        }
        return "";
    }
}
