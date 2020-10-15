package cn.tedu.sp04.order.feign;

import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",fallback = UserFeignServiceFB.class)
public interface UserFeignService {
    @GetMapping("/{userId}")
    JsonResult<User> getUser(@PathVariable Integer userId);
    @PostMapping("/{userId}/score")
    JsonResult addScore(@PathVariable Integer userId,@RequestParam Integer Score);
}