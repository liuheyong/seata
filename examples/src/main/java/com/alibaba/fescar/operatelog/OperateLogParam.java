package com.alibaba.fescar.operatelog;

/**
 * 调用新增操作日志传参
 */
public class OperateLogParam<T, E> {

    /**
     * 操作类型/行为
     */
    private Integer operateType;

    /**
     * 操作模块
     */
    private String operatorModule;

    /**
     * 操作子模块
     */
    private String operatorChildModule;

    /**
     * 操作员id
     */
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 修改前实体
     */
    private T beforeObject;

    /**
     * 修改后实体
     */
    private E afterObject;

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getOperatorModule() {
        return operatorModule;
    }

    public void setOperatorModule(String operatorModule) {
        this.operatorModule = operatorModule;
    }

    public String getOperatorChildModule() {
        return operatorChildModule;
    }

    public void setOperatorChildModule(String operatorChildModule) {
        this.operatorChildModule = operatorChildModule;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public T getBeforeObject() {
        return beforeObject;
    }

    public void setBeforeObject(T beforeObject) {
        this.beforeObject = beforeObject;
    }

    public E getAfterObject() {
        return afterObject;
    }

    public void setAfterObject(E afterObject) {
        this.afterObject = afterObject;
    }
}