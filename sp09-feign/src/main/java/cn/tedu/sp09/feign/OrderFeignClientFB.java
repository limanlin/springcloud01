package cn.tedu.sp09.feign;

import cn.tedu.sp01.pojo.Order;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

@Component
public class OrderFeignClientFB implements OrderFeignClient{
    @Override
    public JsonResult<Order> getOrder(String orderId) {
        return JsonResult.err("无法获取订单信息");
    }

    @Override
    public JsonResult add() {
        return JsonResult.err("无法添加订单");
    }
}
