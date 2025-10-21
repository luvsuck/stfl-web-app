package com.luvsic.stflserverapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luvsic.stflserverapp.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDAO extends BaseMapper<Product> {
}
