package org.example.service;

import org.example.pojo.Result;
import org.springframework.stereotype.Service;

@Service
public interface ProductInventoryChangeService {

    Result getInventoryChangeByMinute();

    Result getInventoryChangeLimitFive();

    Result getTopThreeSale();
}
