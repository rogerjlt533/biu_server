package com.zuosuo.demo.controller;

import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.demo.dto.TestDTO;
import com.zuosuo.demo.factory.RedisFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {

    @Autowired
    private BiuDbFactory biuDbFactory;

    @Autowired
    private RedisFactory redisFactory;

    @GetMapping("test")
    public String index(HttpServletRequest request, @Validated TestDTO dto, BindingResult bindingResult) {
        System.out.println(request.getParameter("test"));
        System.out.println(dto.getName());
//        redisFactory.getBiuRedisFactory().getBiuRedisTool().getValueOperator().set("test", "test", 1000);
//        if (bindingResult.hasErrors()) {
//            biuDbFactory.getCommonDbFactory().getBiuAreaImpl().find(1);
//            return bindingResult.getFieldError().getDefaultMessage();
//        } else {
//            return dto.getName();
//        }
////        System.out.println(System.currentTimeMillis());
//        System.out.println(DataSourceOption.getDataBaseItem());
//        officeUserImpl.find(1);
//        System.out.println(DataSourceOption.getDataBaseItem());
//        return officeUserImpl.find(1);
        return "";
    }
}
