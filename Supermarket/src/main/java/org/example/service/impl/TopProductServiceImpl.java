package org.example.service.impl;

import org.example.mapper.TopProductMapper;
import org.example.pojo.Result;
import org.example.service.TopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopProductServiceImpl implements TopProductService {
    @Autowired
    private TopProductMapper topProductMapper;

    @Override
    public Result getTopThreeSale() {
        return Result.success(topProductMapper.findLatestFourTopSale());
    }
}
