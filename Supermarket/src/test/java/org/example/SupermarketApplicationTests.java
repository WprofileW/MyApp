package org.example;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


class SupermarketApplicationTests {

    @Test
    void jsonTest() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        String jsonStr = JSON.toJSONString(map);
        System.out.println(jsonStr);
    }

}