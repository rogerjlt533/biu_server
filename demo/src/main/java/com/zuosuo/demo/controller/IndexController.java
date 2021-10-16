package com.zuosuo.demo.controller;

import com.hyva.demo.factory.RedisFactory;
import com.hyva.officedb.factory.OfficeDbFactory;
import com.zuosuo.demo.dto.TestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {

    @Autowired
    private OfficeDbFactory officeDbFactory;

    @Autowired
    private RedisFactory redisFactory;

    @GetMapping("test")
    public String index(HttpServletRequest request, @Validated TestDTO dto, BindingResult bindingResult) {
//        System.out.println(request.getParameter("name"));
        redisFactory.getOffice().getOfficeRedisTool().getValueOperator().set("test", "test", 1000);
        if (bindingResult.hasErrors()) {
            officeDbFactory.getOfficeUserDbFactory().getOfficeUserImpl().find(1);
            return bindingResult.getFieldError().getDefaultMessage();
        } else {
            return dto.getName();
        }
////        System.out.println(System.currentTimeMillis());
//        System.out.println(DataSourceOption.getDataBaseItem());
//        officeUserImpl.find(1);
//        System.out.println(DataSourceOption.getDataBaseItem());
//        return officeUserImpl.find(1);
    }
}
