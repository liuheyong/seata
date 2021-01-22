package com.alibaba.fescar.operatelog;


import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhousheng
 * @date: 2020/9/15 16:31
 * @description: 操作日志记录
 */
@Service
public class OperateLogServiceImpl {

    public SapiResult addOperateLog(OperateLogParam operateLogParam) {
        try {
            if (objectFieldExistEmpty(operateLogParam)) {
                return new SapiResult("1005", "参数缺失");
            }
            Object beforeObject = operateLogParam.getBeforeObject();
            Object afterObject = operateLogParam.getAfterObject();
            if (OperateTypeEnums.DELETE.getOperateType() == operateLogParam.getOperateType()) {
                //如果是删除
                Class clazz = beforeObject.getClass();
                doAddOperateLog(afterObject, beforeObject, clazz, operateLogParam);
            } else {
                //如果是新增、修改
                Class clazz = afterObject.getClass();
                doAddOperateLog(beforeObject, afterObject, clazz, operateLogParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SapiResult("-1", "操作失败");
        }
        return new SapiResult("1", "success");
    }

    /**
     * @Date: 2021-01-23
     * @Description: 开始记录操作日志
     */
    private void doAddOperateLog(Object object1, Object object2, Class clazz, OperateLogParam operateLogParam) {
        List<TOperateLog> operateLogList = new ArrayList<>();
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                TOperateLog operateLog = new TOperateLog();
                if (field.get(object2) != null && field.get(object2) != getObjectMap(object1).get(field.getName())) {
                    if (OperateTypeEnums.DELETE.getOperateType() == operateLogParam.getOperateType()) {
                        //如果是删除
                        operateLog.setAfterChange(null);
                        operateLog.setBeforeChange(field.get(object2).toString());
                    } else {
                        //如果是新增、修改
                        operateLog.setAfterChange(field.get(object2).toString());
                        operateLog.setBeforeChange(getObjectMap(object1).get(field.getName()) == null ? null :
                                getObjectMap(object1).get(field.getName()).toString());
                    }
                }
                setOperateLog(operateLog, field, operateLogParam);
                operateLogList.add(operateLog);
            }
            // TODO 入库
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TOperateLog setOperateLog(TOperateLog operateLog, Field field, OperateLogParam operateLogParam) {
        operateLog.setOperatorModule(operateLogParam.getOperatorModule());
        operateLog.setOperatorChildModule(operateLogParam.getOperatorChildModule());
        operateLog.setOperatorHehavior(OperateTypeEnums.getTextByType(operateLogParam.getOperateType()));
        operateLog.setChangeField(field.getName());
        operateLog.setOperatorId(operateLogParam.getOperatorId().intValue());
        operateLog.setOperatorName(operateLogParam.getOperatorName());
        operateLog.setCreateTime(LocalDateTime.now());
        return operateLog;
    }

    /**
     * @Date: 2021-01-23
     * @Description: 对象转map【属性名称->属性值】
     */
    private static Map getObjectMap(Object object) {
        HashMap<String, Object> objectMap = new HashMap<>();
        try {
            if (null == object) {
                return objectMap;
            }
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                objectMap.put(field.getName(), field.get(object));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectMap;
    }

    /**
     * @param object 暂不考虑obj中字段含有基本类型
     * @Description: 判断object中字段是否全部为空
     */
    public boolean objectFieldExistEmpty(Object object) {
        if (object == null) {
            return true;
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.get(object) == null) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}