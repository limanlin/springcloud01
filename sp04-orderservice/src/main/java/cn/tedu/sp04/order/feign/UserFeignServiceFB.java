package cn.tedu.sp04.order.feign;

import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class UserFeignServiceFB implements UserFeignService{
    @Override
    public JsonResult<User> getUser(Integer userId) {
        if (Math.random()<0.4){
            return JsonResult.ok().data(
                    Arrays.asList(new User(userId,"缓存用户"+userId,"缓存密码"+userId))
            );
        }
        return JsonResult.err("无法获取用户信息");
    }

    @Override
    public JsonResult addScore(Integer userId, Integer Score) {
        return JsonResult.err("无法为用户添加积分");
    }
}
