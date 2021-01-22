package com.alibaba.fescar.operatelog;

/**
 * @author liuxiaoyu
 * @date 2020/9/7$
 */
public enum OperateTypeEnums {

    INSERT(10, "插入操作"),

    UPDATE(20, "修改操作"),

    DELETE(30, "删除操作"),

    UP_AND_DOWN(40, "下架/上架状态");

    private int operateType;

    private String text;

    OperateTypeEnums(int operateType, String text) {
        this.operateType = operateType;
        this.text = text;
    }

    public int getOperateType() {
        return operateType;
    }

    public static OperateTypeEnums getEnumByType(int type) {
        for (OperateTypeEnums bt : values()) {
            if (bt.operateType == type) {
                return bt;
            }
        }
        return null;
    }

    public static String getTextByType(Integer type) {
        if (type == null) {
            return "";
        }
        OperateTypeEnums[] groupEnumsArray = OperateTypeEnums.values();
        for (OperateTypeEnums groupEnums : groupEnumsArray) {
            if (groupEnums.getOperateType() == type.intValue()) {
                return groupEnums.text;
            }
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(OperateTypeEnums.INSERT.getOperateType());
    }

}
