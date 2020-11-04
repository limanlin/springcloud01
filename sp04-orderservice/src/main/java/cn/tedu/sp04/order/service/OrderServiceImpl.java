package cn.tedu.sp04.order.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp04.order.feign.ItemFeignService;
import cn.tedu.sp04.order.feign.UserFeignService;
import cn.tedu.web.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.service.OrderService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemFeignService itemFeignService;

    @Autowired
    private UserFeignService userFeignService;
    @Override
    public Order getOrder(String orderId) {
        //TODO: 调用user-service获取用户信息
        JsonResult<User> user = userFeignService.getUser(7);
        //TODO: 调用item-service获取商品信息
        JsonResult<List<Item>> item = itemFeignService.getItem(orderId);
        Order order = new Order();
        order.setId(orderId);
        order.setUser(user.getData());
        order.setItems(item.getData());
        return order;
    }

    @Override
    public void addOrder(Order order) {
        //TODO: 调用item-service减少商品库存
        itemFeignService.decreaseNumber(order.getItems());
        //TODO: 调用user-service增加用户积分
        userFeignService.addScore(order.getUser().getId(),1000);
        log.info("保存订单："+order);
    }

}
