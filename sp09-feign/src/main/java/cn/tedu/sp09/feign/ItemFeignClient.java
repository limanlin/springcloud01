package cn.tedu.sp09.feign;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "item-service")
public interface ItemFeignClient {

    @GetMapping("/{orderId}")
     JsonResult<List<Item>> getItems(@PathVariable String orderId);
    @PostMapping("/decreaseNumber")
    JsonResult decrease(@RequestBody List<Item> items);
}
