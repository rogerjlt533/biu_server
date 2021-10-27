package com.zuosuo.treehole.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DataSourceAspect {

    @Before("within(com.zuosuo.biudb.impl.*)")
    public void listenBiuSource() {
        if (DataSourceOption.getDataBaseType() != DataSourceOption.DataBaseType.DATA_BIU) {
            System.err.println("切换" + DataSourceOption.getDataBaseType() + "=>" + DataSourceOption.DataBaseType.DATA_BIU + "业务");
            DataSourceOption.setDataBaseType(DataSourceOption.DataBaseType.DATA_BIU);
        }
    }

//    @Before("within(com.zuosuo.demo.tool.*)")
//    public void listenTestSource() {
//        if (DataSourceOption.getDataBaseType() != DataSourceOption.DataBaseType.DATA_TEST) {
//            System.err.println("切换" + DataSourceOption.getDataBaseType() + "=>" + DataSourceOption.DataBaseType.DATA_TEST + "业务");
//            DataSourceOption.setDataBaseType(DataSourceOption.DataBaseType.DATA_TEST);
//        }
//    }

    //
    @Before("@annotation(org.apache.ibatis.annotations.SelectProvider) || @annotation(org.apache.ibatis.annotations.Select)")
    public void changeRead() {
        if (DataSourceOption.getOperateType() != DataSourceOption.OperateType.READ) {
            System.err.println("切换" + DataSourceOption.getOperateType() + "=>" + DataSourceOption.OperateType.READ + "方式");
            DataSourceOption.setOperateType(DataSourceOption.OperateType.READ);
        }
    }

    //
    @Before("@annotation(org.apache.ibatis.annotations.InsertProvider) || @annotation(org.apache.ibatis.annotations.Insert) || @annotation(org.apache.ibatis.annotations.UpdateProvider) || @annotation(org.apache.ibatis.annotations.Update) || @annotation(org.apache.ibatis.annotations.DeleteProvider) || @annotation(org.apache.ibatis.annotations.Delete)")
    public void changeWrite() {
        if (DataSourceOption.getOperateType() != DataSourceOption.OperateType.WRITE) {
            System.err.println("切换" + DataSourceOption.getOperateType() + "=>" + DataSourceOption.OperateType.WRITE + "方式");
            DataSourceOption.setOperateType(DataSourceOption.OperateType.WRITE);
        }
    }
}
