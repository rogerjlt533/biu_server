package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.*;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.component.time.TimeTool;
import com.zuosuo.mybatis.provider.CheckStatusEnum;
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
        if (file.trim().isEmpty()) {
            return "";
        }
        file = file.trim();
        if (!file.startsWith("upload/")) {
            return file;
        }
        return qiniuTool.getLink(file);
    }

    public List<UserInterestResult> getUserInterests(long userId) {
        List<UserInterestResult> result = new ArrayList<>();
        List<BiuUserInterestEntity> list = biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().list(new ProviderOption(new ArrayList<String>(){{
            add("user_id=" + userId);
            add("use_type=" + BiuUserInterestEntity.USE_TYPE_SELF );
        }}));
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> relates = list.stream().map(item -> String.valueOf(item.getInterestId())).collect(Collectors.toList());
        List<BiuInterestEntity> interests = biuDbFactory.getUserDbFactory().getBiuInterestImpl().list(new ProviderOption(new ArrayList<String>(){{
            add("id in ("  + String.join(",", relates) +")");
        }}));
        interests.forEach(item -> {
            result.add(new UserInterestResult(item.getId(), item.getTag(), 1));
        });
        return result;
    }

    public List<String> getUserInterestSimpleList(long userId) {
        List<UserInterestResult> list = getUserInterests(userId);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().map(UserInterestResult::getName).collect(Collectors.toList());
    }

    public List<UserInterestResult> getUserInterestList(long userId) {
        List<UserInterestResult> interestList = getUserInterests(userId);
        List<Long> interests = interestList.stream().map(UserInterestResult::getId).collect(Collectors.toList());
        List<BiuInterestEntity> list = biuDbFactory.getUserDbFactory().getBiuInterestImpl().list(new ProviderOption());
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserInterestResult> result = new ArrayList<>();
        list.forEach(item -> {
            result.add(new UserInterestResult(item.getId(), item.getTag(), interests.contains(item.getId()) ? 1 : 0));
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
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().destroy(option);
    }

    public BiuUserImageEntity getUserImage(long userId, String file) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("file", file);
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public BiuUserImageEntity getUserImage(long userId, int type, String file, CheckStatusEnum status) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        option.addCondition("file", file);
        option.setStatus(status.getValue());
        return biuDbFactory.getUserDbFactory().getBiuUserImageImpl().single(option);
    }

    public void setUserImage(long userId, int type, List<String> files) {
        clearUserImage(userId, type);
        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                setUserImage(userId, type, files.get(i), i + 1);
            }
        }
    }

    public BiuUserImageEntity setUserImage(long userId, int type, String file, int sort) {
        boolean isQiniu = false;
        if (file.contains("/upload")) {
            file = file.substring(file.indexOf("/upload") + 1);
            isQiniu = true;
        }
        BiuUserImageEntity image = getUserImage(userId, type, file, CheckStatusEnum.DELETED);
        if (image == null) {
            image = new BiuUserImageEntity();
            image.setUserId(userId);
            image.setUseType(type);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().insert(image);
        } else {
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().restore(image);
        }
        if(isQiniu) {
            BiuUserImageEntity relate = getUserImage(userId, file);
            image.setFile(relate.getFile());
            image.setUseType(type);
            image.setHashCode(relate.getHashCode());
            image.setSortIndex(sort);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().update(image);
            return image;
        } else {
            image.setFile(file);
            image.setUseType(type);
            image.setHashCode("");
            image.setSortIndex(sort);
            biuDbFactory.getUserDbFactory().getBiuUserImageImpl().update(image);
            return image;
        }
    }

    public long clearUserCommunicate(long userId, int type) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        return biuDbFactory.getUserDbFactory().getBiuUserCommunicateImpl().destroy(option);
    }

    public void setUserCommunicate(long userId, int type, List<Integer> communicates) {
        clearUserCommunicate(userId, type);
        if (!communicates.isEmpty()) {
            for (int i = 0; i < communicates.size(); i++) {
                setUserCommunicate(userId, type, communicates.get(i));
            }
        }
    }

    public void setUserCommunicate(long userId, int type, int communicate) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", type);
        option.addCondition("com_method", communicate);
        option.setStatus(CheckStatusEnum.ALL.getValue());
        BiuUserCommunicateEntity entity = biuDbFactory.getUserDbFactory().getBiuUserCommunicateImpl().single(option);
        if (entity == null) {
            entity = new BiuUserCommunicateEntity();
            entity.setUserId(userId);
            entity.setUseType(type);
            entity.setComMethod(communicate);
            biuDbFactory.getUserDbFactory().getBiuUserCommunicateImpl().insert(entity);
        } else if(entity.getDeletedAt() != null) {
            biuDbFactory.getUserDbFactory().getBiuUserCommunicateImpl().restore(entity);
        }
    }

    public long clearUserSex(long userId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        return biuDbFactory.getUserDbFactory().getBiuUserSexImpl().destroy(option);
    }

    public void setUserSexes(long userId, List<Integer> sexes) {
        clearUserSex(userId);
        if (!sexes.isEmpty()) {
            for (int i = 0; i < sexes.size(); i++) {
                setUserSex(userId, sexes.get(i));
            }
        }
    }

    public void setUserSex(long userId, int sex) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("sex", sex);
        option.setStatus(CheckStatusEnum.ALL.getValue());
        BiuUserSexEntity entity = biuDbFactory.getUserDbFactory().getBiuUserSexImpl().single(option);
        if (entity == null) {
            entity = new BiuUserSexEntity();
            entity.setUserId(userId);
            entity.setSex(sex);
            biuDbFactory.getUserDbFactory().getBiuUserSexImpl().insert(entity);
        } else if(entity.getDeletedAt() != null) {
            biuDbFactory.getUserDbFactory().getBiuUserSexImpl().restore(entity);
        }
    }

    public long clearUserInterest(long userId) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", BiuUserInterestEntity.USE_TYPE_SELF);
        return biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().destroy(option);
    }

    public void setUserInterest(long userId, List<Long> interests) {
        clearUserInterest(userId);
        if (!interests.isEmpty()) {
            for (int i = 0; i < interests.size(); i++) {
                setUserInterest(userId, interests.get(i));
            }
        }
    }

    public void setUserInterest(long userId, long interest) {
        ProviderOption option = new ProviderOption();
        option.addCondition("user_id", userId);
        option.addCondition("use_type", BiuUserInterestEntity.USE_TYPE_SELF);
        option.addCondition("interest_id", interest);
        option.setStatus(CheckStatusEnum.ALL.getValue());
        BiuUserInterestEntity entity = biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().single(option);
        if (entity == null) {
            entity = new BiuUserInterestEntity();
            entity.setUserId(userId);
            entity.setUseType(BiuUserInterestEntity.USE_TYPE_SELF);
            entity.setInterestId(interest);
            biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().insert(entity);
        } else if(entity.getDeletedAt() != null) {
            biuDbFactory.getUserDbFactory().getBiuUserInterestImpl().restore(entity);
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
        List<String> interests = list.stream().filter(item -> item.getChecked() > 0).map(UserInterestResult::getName).collect(Collectors.toList());
        return String.join("/", interests);
    }
}
