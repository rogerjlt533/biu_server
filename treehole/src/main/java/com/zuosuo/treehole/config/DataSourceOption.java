package com.zuosuo.treehole.config;

import com.zuosuo.component.tool.JsonTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DataSourceOption {

    // 内部枚举类，用于选择特定的数据类型
    public enum DataBaseType {
        DATA_BIU
    }

    public enum DataBaseItem {
        DATA_BIU
    }

    public enum OperateType {
        WRITE, READ
    }

    public static class DatabaseSet {
        private List<DataBaseItem> master;
        private List<DataBaseItem> slaves;

        public DatabaseSet() {
            master = new ArrayList<>();
            slaves = new ArrayList<>();
        }

        public List<DataBaseItem> getMaster() {
            return master;
        }

        public void addMasterItem(DataBaseItem item) {
            master.add(item);
        }

        public void setMaster(List<DataBaseItem> master) {
            this.master = master;
        }

        public List<DataBaseItem> getSlaves() {
            return slaves;
        }

        public void addSlaveItem(DataBaseItem item) {
            slaves.add(item);
        }

        public void setSlaves(List<DataBaseItem> slaves) {
            this.slaves = slaves;
        }
    }

    // 使用ThreadLocal保证线程安全
    private static final ThreadLocal<DataBaseType> TYPE = new ThreadLocal<DataBaseType>(){};
    private static final ThreadLocal<OperateType> OPERATE_TYPE = new ThreadLocal<OperateType>();
    private static Map<DataBaseType, DatabaseSet> DATA_SET;

    public static final Map<DataBaseType, DatabaseSet> getItemMap() {
        if (DATA_SET == null) {
            DATA_SET = new HashMap<>();
            DatabaseSet biuSet = new DatabaseSet();
            biuSet.addMasterItem(DataBaseItem.DATA_BIU);
//            biuSet.addSlaveItem(DataBaseItem.DATA_BIU_READ1);
            DATA_SET.put(DataBaseType.DATA_BIU, biuSet);
        }
        return DATA_SET;
    }

    // 往当前线程里设置数据源类型
    public static void setDataBaseType(DataBaseType dataBaseType) {
        if (dataBaseType == null) {
            throw new NullPointerException();
        }
        TYPE.set(dataBaseType);
    }

    // 分会数据源类型
    public static DataBaseType getDataBaseType() {
        DataBaseType dataBaseType = TYPE.get();
        if (dataBaseType == null) {
            TYPE.set(DataBaseType.DATA_BIU);
        }
        return TYPE.get();
    }

    // 获取数据源
    public static DataBaseItem getDataBaseItem() {
        OperateType operateType = getOperateType();
        DatabaseSet itemSet = getItemMap().get(getDataBaseType());
        List<DataBaseItem> itemList = null;
        if(operateType == OperateType.READ) {
//            itemList = itemSet.getSlaves();
            itemList = itemSet.getMaster();
        } else {
            itemList = itemSet.getMaster();
        }
        if (itemList.size() == 0) {
            itemList = itemSet.getMaster();
        }
        return itemList.get(ThreadLocalRandom.current().nextInt(itemList.size()) % itemList.size());
    }

    public static void setOperateType(OperateType operateType) {
        if (operateType == null) {
            throw new NullPointerException();
        }
        OPERATE_TYPE.set(operateType);
    }

    // 获取数据源操作类型
    public static OperateType getOperateType() {
        OperateType operateType = OPERATE_TYPE.get();
        return operateType;
    }
}



