package org.supermarket.tools;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeInterceptor implements Interceptor {
    //声明一个存放事件的集合
    private List<Event> addHeaderEvents;

    @Override

    public void initialize() {
        //初始化存放事件的集合
        addHeaderEvents = new ArrayList<>();
    }

    //单个事件拦截
    @Override
    public Event intercept(Event event) {
        //1.获取事件中的头信息
        Map<String, String> headers = event.getHeaders();
        //2.获取事件中的 body 信息
        String body = new String(event.getBody());
        //3.根据 body 中是否有"CustomizedLogs"来决定添加怎样的头信息
        if (body.contains("CustomizedLogs")) {
            //4.添加头信息
            headers.put("log_type", "CustomizedLogs");
        } else {
            //4.添加头信息
            headers.put("log_type", "RuntimeLogs");
        }
        return event;
    }

    //批量事件拦截
    @Override
    public List<Event> intercept(List<Event> events) {
        //1.清空集合
        addHeaderEvents.clear();
        //2.遍历 events
        for (Event event : events) {
            //3.给每一个事件添加头信息
            addHeaderEvents.add(intercept(event));
        }
        //4.返回结果
        return addHeaderEvents;
    }

    @Override
    public void close() {
    }








    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new TypeInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }

}