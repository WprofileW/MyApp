package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.ProductInventoryMapper;
import org.example.pojo.PageBean;
import org.example.pojo.ProductInventory;
import org.example.pojo.Result;
import org.example.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {
    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Override
    public Result addProduct(ProductInventory productInventory) {
        productInventoryMapper.insertProductInventory(productInventory);
        return Result.success();
    }

    @Override
    public Result updateProduct(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
        return Result.success();
    }

    @Override
    public Result updateProductByName(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventoryByName(productInventory);
        return Result.success();
    }

    @Override
    public Result deleteProduct(ProductInventory productInventory) {
        productInventoryMapper.deleteProductInventoryById(productInventory.getInventoryId());
        return Result.success();
    }

    @Override
    public <T> Result getProductByName(Map<T, T> params) {
        return Result.success(
                productInventoryMapper.getProductInventoryByName(
                        (String) params.get("productName")));
    }

    @Override
    public <T> Result getAllProducts(Map<T, T> params) {
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        //1.创建PageBean对象
        PageBean<ProductInventory> pb = new PageBean<>();
        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);
        //3.调用mapper
        List<ProductInventory> as = productInventoryMapper.getAllProductInventories();
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<ProductInventory> p = (Page<ProductInventory>) as;
        //把数据填充到PageBean对象中
        pb.setTotal((int) p.getTotal());
        pb.setItems(p.getResult());
        return Result.success(pb);
    }
}