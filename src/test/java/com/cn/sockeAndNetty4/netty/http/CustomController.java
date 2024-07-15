package com.cn.sockeAndNetty4.netty.http;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 何立森
 * @Date: 2024/07/15/9:08
 * @Description:
 */

public class CustomController {

    @RequestMapping(value = "/order/getOrderInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOrderInfo(@RequestBody Map<String, Object> objectMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderCode", "D123456");
        map.put("productName", "帆布鞋");
        map.put("num", 5);
        map.put("orderAmount", 500.5d);
        map.put("accountId", objectMap.get("accountId"));
        return map;
    }

    @RequestMapping(value = "/student/getInfo", method = RequestMethod.GET)
    public String getStudent(@RequestParam(required = false) Long accountId, @RequestParam(required = false) String name) {
        return "租户id为：" + accountId + "，名称为：" + name;
    }


}
