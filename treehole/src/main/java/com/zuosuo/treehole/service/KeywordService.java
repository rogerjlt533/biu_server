package com.zuosuo.treehole.service;

import com.zuosuo.biudb.entity.BiuKeywordEntity;
import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.mybatis.provider.ProviderOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("KeywordService")
public class KeywordService {

    @Autowired
    private BiuDbFactory biuDbFactory;

    public List<String> getKeywordList() {
        ProviderOption option = new ProviderOption();
        option.setColumns("keyword");
        List<BiuKeywordEntity> list = biuDbFactory.getCommonDbFactory().getBiuKeywordImpl().list(option);
        return list.stream().map(item -> item.getKeyword()).collect(Collectors.toList());
    }

    /**
     * 验证关键词
     * @param content
     * @return
     */
    public boolean verifyKeyword(String content) {
        return true;
//        List<String> keywords = getKeywordList();
//        if (keywords.isEmpty()) {
//            return true;
//        }
//        for (String keyword: keywords) {
//            if (content.contains(keyword)) {
//                return false;
//            }
//        }
//        return true;
    }
}
