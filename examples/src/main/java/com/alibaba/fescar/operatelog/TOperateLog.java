package com.alibaba.fescar.operatelog;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 操作记录
 * </p>
 *
 * @author lzy
 * @since 2021-01-22
 */
public class TOperateLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 操作模块
     */
    private String operatorModule;

    /**
     * 操作子模块
     */
    private String operatorChildModule;

    /**
     * 操作行为
     */
    private String operatorHehavior;

    /**
     * 修改字段
     */
    private String changeField;

    /**
     * 修改前
     */
    private String beforeChange;

    /**
     * 修改后
     */
    private String afterChange;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 操作人
     */
    private Integer operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOperatorHehavior() {
        return operatorHehavior;
    }

    public void setOperatorHehavior(String operatorHehavior) {
        this.operatorHehavior = operatorHehavior;
    }

    public String getChangeField() {
        return changeField;
    }

    public void setChangeField(String changeField) {
        this.changeField = changeField;
    }

    public String getBeforeChange() {
        return beforeChange;
    }

    public void setBeforeChange(String beforeChange) {
        this.beforeChange = beforeChange;
    }

    public String getAfterChange() {
        return afterChange;
    }

    public void setAfterChange(String afterChange) {
        this.afterChange = afterChange;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Override
    public String toString() {
        return "TOperateLog{" +
                "id=" + id +
                ", operatorModule=" + operatorModule +
                ", operatorChildModule=" + operatorChildModule +
                ", operatorHehavior=" + operatorHehavior +
                ", changeField=" + changeField +
                ", beforeChange=" + beforeChange +
                ", afterChange=" + afterChange +
                ", createTime=" + createTime +
                ", operatorId=" + operatorId +
                ", operatorName=" + operatorName +
                "}";
    }
}
