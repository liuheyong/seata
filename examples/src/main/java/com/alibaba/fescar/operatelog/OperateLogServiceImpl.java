//package com.yiche.sapi.opservice.impl.sys;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.google.common.collect.Lists;
//import com.yiche.sapi.common.enums.OperateTypeEnums;
//import com.yiche.sapi.common.enums.ResultEnum;
//import com.yiche.sapi.common.exception.SapiServiceException;
//import com.yiche.sapi.common.result.SapiResult;
//import com.yiche.sapi.common.utils.LoggerUtil;
//import com.yiche.sapi.model.entity.SysUser;
//import com.yiche.sapi.model.entity.TOperateLog;
//import com.yiche.sapi.model.operatelog.param.AddOperateLogParam;
//import com.yiche.sapi.model.operatelog.param.QueryOperateLogParam;
//import com.yiche.sapi.opservice.service.sys.OperateLogService;
//import com.yiche.sapi.opservice.utils.SpringContextUtil;
//import com.yiche.sapi.opservice.utils.SysUserUtil;
//import com.yiche.sapi.repo.service.TOperateLogService;
//import org.apache.commons.collections4.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.util.*;
//
///**
// * @date: 2020/9/15 16:31
// * @description: 操作日志记录
// */
//@Service
//public class OperateLogServiceImpl implements OperateLogService {
//
//    @Autowired
//    private TOperateLogService operateLogService;
//    private static final BigDecimal ZEROBIGDECIMAL = new BigDecimal(0.00);
//
//    @Override
//    public Page<TOperateLog> getOperateLogList(QueryOperateLogParam queryOperateLogParam) {
//        return operateLogService.getOperateLogList(queryOperateLogParam);
//    }
//
//    @Override
//    public SapiResult addOperateLogWithObj(AddOperateLogParam addOperateLogParam) {
//        try {
//            Optional.ofNullable(addOperateLogParam.getOperateType()).orElseThrow(() -> new SapiServiceException("操作类型为空"));
//            Object beforeObject = addOperateLogParam.getBeforeObject();
//            Object afterObject = addOperateLogParam.getAfterObject();
//            SysUser loginUser = SysUserUtil.getLoginUser();
//            //1、新增 改之前为空，改之后有值
//            List<TOperateLog> list = Lists.newArrayList();
//            if (OperateTypeEnums.INSERT == addOperateLogParam.getOperateType()) {
//                //if (addOperateLogParam.getModuleType() == null
//                //        || addOperateLogParam.getChildModuleType() == null
//                //        || addOperateLogParam.getAfterObject() == null) {
//                //    throw new SapiServiceException("操作记录参数缺失");
//                //}
//                Map objectMap = getObjectMap(afterObject);
//                objectMap.forEach((k, v) -> {
//                    //空字段不记录
//                    if (null != v) {
//                        TOperateLog operateLog = new TOperateLog();
//                        operateLog.setChangeField(k.toString());
//                        operateLog.setAfterChange(String.valueOf(v));
//                        operateLog.setOperatorId(loginUser.getId());
//                        operateLog.setOperatorName(loginUser.getUsername());
//                        operateLog.setOperatorModule(String.valueOf(addOperateLogParam.getModuleType()));
//                        operateLog.setOperatorChildModule(String.valueOf(addOperateLogParam.getChildModuleType()));
//                        operateLog.setOperatorHehavior(String.valueOf(addOperateLogParam.getOperateType().getOperateType()));
//                        list.add(operateLog);
//                    }
//                });
//            } else if (OperateTypeEnums.UPDATE == addOperateLogParam.getOperateType()) {
//                //2、修改
//                //if (addOperateLogParam.getId() == null
//                //        || addOperateLogParam.getServiceName() == null
//                //        || addOperateLogParam.getAfterObject() == null
//                //        || addOperateLogParam.getModuleType() == null
//                //        || addOperateLogParam.getChildModuleType() == null) {
//                //    throw new SapiServiceException("操作记录参数缺失");
//                //}
//                beforeObject = getObjectById(addOperateLogParam);
//                Map<String, Object> afterMap = getObjectMap(afterObject);
//                Map<String, Object> beforeMap = getObjectMap(beforeObject);
//                //map 比较
//                for (Map.Entry<String, Object> entry : beforeMap.entrySet()) {
//                    //校验业务值  beforeMap里面的k、v都不可能为null
//                    if (null != afterMap.get(entry.getKey()) && checkValueNotEquals(entry.getValue(), afterMap.get(entry.getKey()))) {
//                        //需要记录
//                        TOperateLog operateLog = new TOperateLog();
//                        operateLog.setChangeField(entry.getKey());
//                        operateLog.setBeforeChange(entry.getValue().toString());
//                        operateLog.setOperatorId(loginUser.getId());
//                        operateLog.setOperatorName(loginUser.getUsername());
//                        operateLog.setOperatorModule(addOperateLogParam.getModuleType().toString());
//                        operateLog.setOperatorChildModule(addOperateLogParam.getChildModuleType().toString());
//                        operateLog.setOperatorHehavior(String.valueOf(addOperateLogParam.getOperateType().getOperateType()));
//                        operateLog.setAfterChange(afterMap.get(entry.getKey()).toString());
//                        list.add(operateLog);
//                    }
//                }
//            } else {
//                //3、删除
//                //if (addOperateLogParam.getId() == null
//                //        || addOperateLogParam.getServiceName() == null
//                //        || addOperateLogParam.getModuleType() == null
//                //        || addOperateLogParam.getChildModuleType() == null) {
//                //    throw new SapiServiceException("操作记录参数缺失");
//                //}
//                beforeObject = getObjectById(addOperateLogParam);
//                Map beforeObjectMap = getObjectMap(beforeObject);
//                beforeObjectMap.forEach((k, v) -> {
//                    TOperateLog operateLog = new TOperateLog();
//                    operateLog.setChangeField(k.toString());
//                    operateLog.setBeforeChange(String.valueOf(v));
//                    operateLog.setOperatorId(loginUser.getId());
//                    operateLog.setOperatorName(loginUser.getUsername());
//                    operateLog.setOperatorModule(String.valueOf(addOperateLogParam.getModuleType()));
//                    operateLog.setOperatorChildModule(String.valueOf(addOperateLogParam.getChildModuleType()));
//                    operateLog.setOperatorHehavior(String.valueOf(addOperateLogParam.getOperateType().getOperateType()));
//                    list.add(operateLog);
//                });
//            }
//            if (CollectionUtils.isNotEmpty(list)) {
//                operateLogService.addOperateLogBatch(list);
//            }
//        } catch (Exception e) {
//            LoggerUtil.logger().error("异常e={}", e);
//        }
//        return new SapiResult(ResultEnum.SUCCESS.getCode(), "success");
//    }
//
//    /**
//     * @Date: 2021-01-23
//     * @Description: 校验value值不相等返回true
//     */
//    public Boolean checkValueNotEquals(Object object1, Object object2) {
//        if (object1 instanceof BigDecimal && object2 instanceof BigDecimal) {
//            BigDecimal bigDecimal1 = (BigDecimal) object1;
//            BigDecimal bigDecimal2 = (BigDecimal) object2;
//            return bigDecimal1.compareTo(bigDecimal2) != 0;
//        } else {
//            return !object1.equals(object2);
//        }
//    }
//
//    /**
//     * @Date: 2021-01-23
//     * @Description: 获取Object实例
//     */
//    public Object getObjectById(AddOperateLogParam addOperateLogParam) {
//        IService service = (IService) SpringContextUtil.getBean(addOperateLogParam.getServiceName());
//        if (service == null) {
//            LoggerUtil.logger().error("bean不存在：{}", addOperateLogParam.getServiceName());
//            throw new SapiServiceException(ResultEnum.ERROR.getCode(), "bean不存在");
//        }
//        return service.getById(addOperateLogParam.getId());
//    }
//
//    /**
//     * @Date: 2021-01-23
//     * @Description: 对象转map【属性名称 -> 属性值】
//     */
//    private static Map<String, Object> getObjectMap(Object object) throws Exception {
//        if (null == object) {
//            return Collections.EMPTY_MAP;
//        }
//        HashMap<String, Object> objectMap = new HashMap<>();
//        Class clazz = object.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            String fieldName = field.getName();
//            if ("id".equals(fieldName) || "operatorName".equals(fieldName)
//                    || "operatorId".equals(fieldName) || "createTime".equals(fieldName)
//                    || "updateTime".equals(fieldName) || "serialVersionUID".equals(fieldName)) {
//                continue;
//            }
//            objectMap.put(fieldName, field.get(object));
//        }
//        return objectMap;
//    }
//
//    /**
//     * 校验非默认值
//     *
//     * @param object
//     * @return Boolean
//     */
//    private Boolean checkBusinessField(Object object) {
//        if (null == object) {
//            return false;
//        }
//        if (object instanceof String) {
//            return !"".equals(object);
//        } else if (object instanceof Integer) {
//            return 0 != ((Integer) object).intValue();
//        } else if (object instanceof Long) {
//            return 0 != ((Long) object).longValue();
//        } else if (object instanceof BigDecimal) {
//            return ZEROBIGDECIMAL.compareTo(((BigDecimal) object)) != 0;
//        }
//        return false;
//    }
//
//}
