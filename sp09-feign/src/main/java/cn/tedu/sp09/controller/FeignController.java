package cn.tedu.sp09.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp09.feign.ItemFeignClient;
import cn.tedu.sp09.feign.OrderFeignClient;
import cn.tedu.sp09.feign.UserFeignClient;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class FeignController {
    @Autowired
    private ItemFeignClient itemFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private OrderFeignClient orderFeignClient;
    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItem(@PathVariable String orderId) {
        log.info("远程调用商品服务，获得商品列表");
        return itemFeignClient.getItems(orderId);
    }

    @PostMapping("/item-service/decreaseNumber")
    public JsonResult decrease(@RequestBody List<Item> items) {
        log.info("远程调用商品服务，减少商品库存");
        return itemFeignClient.decrease(items);
    }

    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId) {
        log.info("远程调用用户服务，获取用户信息");
        return userFeignClient.getUser(userId);
    }

    @GetMapping("/user-service/{userId}/score")
    public JsonResult addScore(@PathVariable Integer userId, @RequestParam Integer score) {
        log.info("远程调用用户服务，为用户添加积分");
        return userFeignClient.addScore(userId, score);
    }

    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        log.info("远程调用订单服务，获取订单信息");
        return  orderFeignClient.getOrder(orderId);
    }
    @GetMapping("/order-service")
    public JsonResult addOrder(){
        log.info("远程调用订单服务，添加订单");
        return orderFeignClient.add();
    }
}
