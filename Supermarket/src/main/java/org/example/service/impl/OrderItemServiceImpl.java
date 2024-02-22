package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.OrderItemMapper;
import org.example.pojo.OrderItem;
import org.example.pojo.PageBean;
import org.example.pojo.Result;
import org.example.service.OrderItemService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Result addOrderItem(List<OrderItem> orderItemList) {
        System.out.println(orderItemList);
        orderItemList.forEach(orderItem -> orderItemMapper.insertOrderItem(orderItem));
        return Result.success();
    }

    @Override
    public Result updateOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public Result deleteOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public <T> Result getAllOrders(Map<T, T> params) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        //1.创建PageBean对象
        PageBean<OrderItem> pb = new PageBean<>();
        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);
        //3.调用mapper
        List<OrderItem> as = orderItemMapper.getOrderItemsByUsername(username);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<OrderItem> p = (Page<OrderItem>) as;
        //把数据填充到PageBean对象中
        pb.setTotal((int) p.getTotal());
        pb.setItems(p.getResult());
        return Result.success(pb);
    }
}
