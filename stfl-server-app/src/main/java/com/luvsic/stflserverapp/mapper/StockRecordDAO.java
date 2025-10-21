package com.luvsic.stflserverapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luvsic.stflserverapp.entity.StockRecord;
import com.luvsic.stflserverapp.entity.vo.StockView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockRecordDAO extends BaseMapper<StockRecord> {
    List<StockView> stockViewReport();
}
