/*
 *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.alibaba.fescar.dubbo.service.impl;

import com.alibaba.fescar.dubbo.service.AccountService;
import com.alibaba.fescar.dubbo.service.BusinessService;
import com.alibaba.fescar.dubbo.service.OrderService;
import com.alibaba.fescar.dubbo.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Please add the follow VM arguments:
 * <pre>
 *     -Djava.net.preferIPv4Stack=true
 * </pre>
 */
public class BusinessServiceImpl implements BusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessService.class);

    private StorageService storageService;
    private OrderService orderService;
    private AccountService accountService;

    @Override
    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount) {
        LOGGER.info("purchase begin ... xid: " + RootContext.getXID());
        // TODO ①、扣减库存
        storageService.deduct(commodityCode, orderCount);
        // TODO ②、从账户扣款
        // 计算订单金额
        int orderMoney = calculate(orderCount);
        // 从账户扣款
        accountService.debit(userId, orderMoney);
        // TODO ③、创建订单
        orderService.create(userId, commodityCode, orderCount, orderMoney);
        // TODO RuntimeException
        throw new RuntimeException("xxx");
    }

    private int calculate(int orderCount) {
        return 200 * orderCount;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-business.xml"});
        final BusinessService business = (BusinessService) context.getBean("business");
        business.purchase("100", "321", 2);
    }
}
