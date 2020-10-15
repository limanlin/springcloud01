package cn.tedu.sp04.order.feign;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ItemFeignServiceFB implements ItemFeignService {
    @Override
    public JsonResult<List<Item>> getItem(String orderId) {
        if (Math.random()<0.5){
            return JsonResult.ok().data(
                    Arrays.asList(new Item[]{
                                new Item(1,"缓存aaa",2),
                            new Item(2,"缓存bbb",5),
                            new Item(3,"缓存ccc",7),
                            new Item(4,"缓存ddd",6),
                            new Item(5,"缓存eee",4),
                            new Item(6,"缓存fff",3),
                    })
            );
        }
        return JsonResult.err("无法获取订单商品信息");
    }

    @Override
    public JsonResult decreaseNumber(List<Item> items) {
        return JsonResult.err("无法减少库存");
    }
}
