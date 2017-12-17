package com.whatsmars.shardingjdbc.repository;

import com.whatsmars.shardingjdbc.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemRepository {
    
    void createIfNotExistsTable();
    
    void truncateTable();
    
    Long insert(OrderItem model);
    
    void delete(Long orderItemId);
    
    List<OrderItem> selectAll();
    
    void dropTable();
}
