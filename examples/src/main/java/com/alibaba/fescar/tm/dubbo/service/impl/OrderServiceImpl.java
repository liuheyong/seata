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

package com.alibaba.fescar.tm.dubbo.service.impl;

import com.alibaba.fescar.test.common.ApplicationKeeper;
import com.alibaba.fescar.tm.dubbo.dto.Order;
import com.alibaba.fescar.tm.dubbo.service.OrderService;
import io.seata.core.context.RootContext;
import org.apache.dubbo.qos.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Please add the follow VM arguments:
 * <pre>
 *     -Djava.net.preferIPv4Stack=true
 * </pre>
 */
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private JdbcTemplate jdbcTemplate;

    @Override
    public Order create(String userId, String commodityCode, int orderCount, int orderMoney) {
        LOGGER.info("Order Service Begin ... xid: " + RootContext.getXID());

        String insertSql = "insert into order_tbl (user_id, commodity_code, count, money) values (" +  userId + ", " + commodityCode + ", " + orderCount + ", " + orderMoney + ")";
        int rows = jdbcTemplate.update(insertSql);

        final Order order = new Order();
        order.userId = userId;
        order.commodityCode = commodityCode;
        order.count = orderCount;
        order.money = orderMoney;

        LOGGER.info("Order Service SQL: insert into order_tbl (user_id, commodity_code, count, money) values ({}, {}, {}, {})",
                userId, commodityCode, orderCount, orderMoney);

        LOGGER.info("Order Service End ... Created " + order);

        return order;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-order-service.xml"});
        context.getBean("service");
        //关闭QOS服务
        Server.getInstance().stop();
        new ApplicationKeeper(context).keep();
    }
}
