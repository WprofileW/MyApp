package org.example.service;

import org.example.pojo.Result;
import org.springframework.stereotype.Service;

@Service
public interface TopProductService  {
    Result getTopThreeSale();
}
