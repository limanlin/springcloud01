package cn.tedu.sp06.controller;

import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;

@RestController
public class RibbonController {
    @Autowired
    private RestTemplate rt;

    @GetMapping("/item-service/{orderId}")
    @HystrixCommand(fallbackMethod = "getItemsFB")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId) {
        //向指定微服务地址发送 get 请求，并获得该服务的返回结果
        //{1} 占位符，用 orderId 填充
        return rt.getForObject("http://item-service/{1}", JsonResult.class, orderId);
    }

    public JsonResult<List<Item>> getItemsFB(String orderId) {
        return JsonResult.err().msg("获取商品列表失败");
    }

    @HystrixCommand(fallbackMethod = "decreaseNumberFB")
    @PostMapping("/item-service/decreaseNumber")
    public JsonResult decreaseNumber(@RequestBody List<Item> items) {
        //发送 post 请求
        return rt.postForObject("http://item-service/decreaseNumber", items, JsonResult.class);
    }

    @HystrixCommand(fallbackMethod = "getUserFB")
    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId) {
        return rt.getForObject("http://user-service/{1}", JsonResult.class, userId);
    }

    @HystrixCommand(fallbackMethod = "addScoreFB")
    @GetMapping("/user-service/{userId}/score")
    public JsonResult addScore(
            @PathVariable Integer userId, Integer score) {
        return rt.getForObject("http://user-service/{1}/score?score={2}", JsonResult.class, userId, score);
    }

    @HystrixCommand(fallbackMethod = "getOrderFB")
    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId) {
        return rt.getForObject("http://order-service/{1}", JsonResult.class, orderId);
    }

    @HystrixCommand(fallbackMethod = "addOrderFB")
    @GetMapping("/order-service")
    public JsonResult addOrder() {
        return rt.getForObject("http://order-service/", JsonResult.class);
    }

    /////////////////////////////////////////////////////////////////////////


    public JsonResult decreaseNumberFB(List<Item> items) {
        //发送 post 请求
        return JsonResult.err().msg("减少库存失败");
    }


    public JsonResult<User> getUserFB(Integer userId) {
        return JsonResult.err().msg("获取用户信息失败");
    }


    public JsonResult addScoreFB(Integer userId, Integer score) {
        return JsonResult.err().msg("增加积分失败");
    }


    public JsonResult<Order> getOrderFB(String orderId) {
        return JsonResult.err().msg("获取订单失败");
    }

    public JsonResult addOrderFB() {
        return JsonResult.err().msg("保存订单失败");
    }
}

