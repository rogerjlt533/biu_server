package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.component.tool.CommonTool;
import com.zuosuo.mybatis.provider.ProviderOption;
import com.zuosuo.treehole.result.UserInterestResult;
import com.zuosuo.treehole.tool.QiniuTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("UserService")
public class UserService {

    @Autowired
    private QiniuTool qiniuTool;
    @Autowired
    private BiuDbFactory biuDbFactory;

    public BiuUserEntity getUserByOpenid(String openid) {
        ProviderOption option = new ProviderOption();
        option.addCondition("openid", openid);
        return biuDbFactory.getUserDbFactory().getBiuUserImpl().single(option);
    }

    public BiuUserEntity find(long id) {
        return biuDbFactory.getUserDbFactory().getBiuUserImpl().find(id);
    }

    // 获取用户视图
    public BiuUserViewEntity getUserView(long id) {
        return biuDbFactory.getUserDbFactory().getBiuUserViewImpl().find(id);
    }

    // 设置用户更新时间
    public void setUserSortTime(long id) {
        ProviderOption option = new ProviderOption();
        option.addCondition("id", id);
        option.addCondition("DATE_FORMAT(NOW(),'%Y-%m-%d')!=(IF(sort_time,DATE_FORMAT(sort_time,'%Y-%m-%d'),''))");
        option.setAttribute("sort_time", TimeTool.formatDate(new Date()));
        biuDbFactory.getUserDbFactory().getBiuUserImpl().modify(option);
    }

    public List<String> getUserImageList(long userId, int type) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        option.addOrderby("sort_index asc");
        List<BiuUserImageEntity> list = biuDbFactory.getUserDbFactory().getBiuUserImageImpl().list(option);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().map(item -> parseImage(item.getFile())).collect(Collectors.toList());
    }

    public String parseImage(String file) {
        if (file == null) {
            return "";
        }
        if (!file.contains("/upload")) {
            return file;
        }
        return qiniuTool.getLink(file);
    }

    public List<BiuUserInterestEntity> getUserInterests(long userId) {
        List<BiuUserInterestEntity> list = biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().list(new ProviderOption(new ArrayList<String>(){{
            add("user_id=" + userId);
            add("use_type=" + BiuUserInterestEntity.USE_TYPE_SELF );
        }}));
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }

    public List<String> getUserInterestSimpleList(long userId) {
        List<BiuUserInterestEntity> list = getUserInterests(userId);
        List<String> relates = list.stream().map(item -> String.valueOf(item.getInterestId())).collect(Collectors.toList());
        List<BiuInterestEntity> interests = biuDbFactory.getUserDbFactory().getBiuInterestImpl().list(new ProviderOption(new ArrayList<String>(){{
            add("id in ("  + String.join(",", relates) +")");
        }}));
        if (interests.isEmpty()) {
            return new ArrayList<>();
        }
        return interests.stream().map(BiuInterestEntity::getTag).collect(Collectors.toList());
    }

    public List<UserInterestResult> getUserInterestList(long userId) {
        List<UserInterestResult> result = new ArrayList<>();
        List<BiuUserInterestEntity> userInterests = getUserInterests(userId);
        List<Long> myInterests = userInterests.stream().map(BiuUserInterestEntity::getInterestId).collect(Collectors.toList());
        List<BiuInterestEntity> list = biuDbFactory.getUserDbFactory().getBiuInterestImpl().list(new ProviderOption());
        if (list.isEmpty()) {
            return result;
        }
        list.forEach(item -> {
            int checked = myInterests.contains(item.getId()) ? 1 : 0;
            result.add(new UserInterestResult(item.getId(), item.getTag(), checked));
        });
        return result;
    }

    public BiuUserImageEntity getHashImage(long userId, String hash) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("hash_code", hash);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public BiuUserImageEntity initEmptyHashImage(long userId, String file, String hash) {
        BiuUserImageEntity image = new BiuUserImageEntity();
        image.setUserId(userId);
        image.setFile(file);
        image.setHashCode(hash);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().insert(image);
    }

    public long clearUserImage(long userId, int type) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        option.setAttribute("user_type", 0);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().modify(option);
    }

    public BiuUserImageEntity getUserImage(long userId, String file) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("file", file);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public BiuUserImageEntity getUserImage(long userId, int type) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public BiuUserImageEntity setUserImage(long userId, int type, String file, int sort) {
        boolean isQiniu = false;
        if (file.contains("/upload")) {
            file = file.substring(file.indexOf("/upload") + 1);
            isQiniu = true;
        }
        BiuUserImageEntity image = getUserImage(userId, type);
        if (image == null) {
            image = new BiuUserImageEntity();
            image.setUserId(userId);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().insert(image);
        }
        if (image.getFile().equals(file)) {
            return image;
        } else if(isQiniu) {
            BiuUserImageEntity relate = getUserImage(userId, file);
            image.setFile(relate.getFile());
            image.setHashCode(relate.getHashCode());
            image.setSortIndex(sort);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().update(image);
            return image;
        } else {
            image.setFile(file);
            image.setHashCode("");
            image.setSortIndex(sort);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().update(image);
            return image;
        }
    }

    public String parseSexes(List<Integer> list) {
        List<String> sexes = list.stream().map(item -> {
            String sex;
            if (item == BiuUserEntity.USER_SEX_MAN) {
                sex = BiuUserEntity.LABEL_USER_SEX_MAN;
            } else if(item == BiuUserEntity.USER_SEX_WOMEN) {
                sex = BiuUserEntity.LABEL_USER_SEX_WOMEN;
            } else {
                sex = "";
            }
            return sex;
        }).collect(Collectors.toList());
        return String.join("/", sexes);
    }

    public String parseCommunicates(List<Integer> list) {
        List<String> communicates = list.stream().map(item -> {
            String name;
            if (item == BiuUserCommunicateEntity.COM_METHOD_EMAIL) {
                name = BiuUserCommunicateEntity.LABEL_COM_METHOD_EMAIL;
            } else if(item == BiuUserCommunicateEntity.COM_METHOD_LETTER) {
                name = BiuUserCommunicateEntity.LABEL_COM_METHOD_LETTER;
            } else {
                name = "";
            }
            return name;
        }).collect(Collectors.toList());
        return String.join("/", communicates);
    }

    public String parseInterests(List<UserInterestResult> list) {
        List<String> interests = list.stream().map(item -> item.getName()).collect(Collectors.toList());
        return String.join("/", interests);
    }
}
