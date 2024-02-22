package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.ShoppingCartItemMapper;
import org.example.pojo.PageBean;
import org.example.pojo.Result;
import org.example.pojo.ShoppingCartItem;
import org.example.service.ShoppingCartService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @Override
    public Result addCartItem(ShoppingCartItem shoppingCartItem) {
        System.out.println(shoppingCartItem);
        Map<String, Object> map = ThreadLocalUtil.get();
        System.out.println(map);
        List<Integer> shoppingCartItemIdList = (List<Integer>) map.get("shoppingCartItemIdList");
        Integer index = shoppingCartItemIdList.indexOf(shoppingCartItem.getCartItemId());
        if (index != -1) {
            updateCartItem(shoppingCartItem);
        } else {
            shoppingCartItemMapper.insertShoppingCartItem(shoppingCartItem);
        }
        return Result.success();
    }

    @Override
    public Result updateCartItem(ShoppingCartItem shoppingCartItem) {
        shoppingCartItemMapper.updateShoppingCartItem(shoppingCartItem);
        return Result.success();
    }

    @Override
    public Result deleteCartItem(ShoppingCartItem shoppingCartItem) {
        shoppingCartItemMapper.deleteShoppingCartItemById(shoppingCartItem.getCartItemId());
        return Result.success();
    }

    @Override
    public <T> Result getAllCartItems(Map<T, T> params) {
        //从token获取username
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        //1.创建PageBean对象
        PageBean<ShoppingCartItem> pb = new PageBean<>();
        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);
        //3.调用mapper
        List<ShoppingCartItem> as = shoppingCartItemMapper.getShoppingCartItemsByUsername(username);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<ShoppingCartItem> p = (Page<ShoppingCartItem>) as;
        //把数据填充到PageBean对象中
        pb.setTotal((int) p.getTotal());
        pb.setItems(p.getResult());
        return Result.success(pb);
    }

    @Override
    public Result deleteAllCartItems() {
        //从token获取username
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        shoppingCartItemMapper.deleteShoppingCartItemByUsername(username);
        return Result.success();
    }
}
