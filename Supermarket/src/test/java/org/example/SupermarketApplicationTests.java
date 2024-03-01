package org.example;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @Test
    void timeTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = sdf.format(Calendar.getInstance().getTime());
        System.out.println(currentTime);
    }
}