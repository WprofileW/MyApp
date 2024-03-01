package org.example.service.impl;

import org.example.mapper.ProductInventoryChangeMapper;
import org.example.pojo.Result;
import org.example.service.ProductInventoryChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class ProductInventoryChangeServiceImpl implements ProductInventoryChangeService {
    @Autowired
    private ProductInventoryChangeMapper productInventoryChangeMapper;

    @Override
    public Result getInventoryChangeByMinute() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String currentTime = sdf.format(Calendar.getInstance().getTime());
        return Result.success(productInventoryChangeMapper.findByTimestamp(Long.parseLong(currentTime)));
    }

    @Override
    public Result getInventoryChangeLimitFive() {
        return Result.success(productInventoryChangeMapper.findLatestFiveChanges());
    }

    @Override
    public Result getTopThreeSale() {
        return null;
    }


}
