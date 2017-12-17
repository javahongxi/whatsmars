package com.whatsmars.shardingjdbc.repository;

import com.whatsmars.shardingjdbc.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRepository {
    
    void createIfNotExistsTable();
    
    void truncateTable();
    
    Long insert(Order model);
    
    void delete(Long orderId);
    
    void dropTable();
}
