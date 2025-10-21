package com.luvsic.stflserverapp.service;

import com.luvsic.stflserverapp.entity.Product;
import com.luvsic.stflserverapp.entity.StockRecord;
import com.luvsic.stflserverapp.entity.vo.StockView;
import com.luvsic.stflserverapp.mapper.ProductDAO;
import com.luvsic.stflserverapp.mapper.StockRecordDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 21:06
 * @description
 */
@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRecordDAO stockRecordDAO;
    private final ProductDAO productDAO;

    public void stockChange(Product.StockChange stockChange) {
        StockRecord record = new StockRecord();
        record.setProductId(stockChange.getProductId());
        record.setQuantity(stockChange.getQuantity());
        record.setIsStockIn(stockChange.getIsStockIn());
        stockRecordDAO.insert(record);
    }

    public List<StockView> stockReports() {
        List<StockView> stockView = stockRecordDAO.stockViewReport();
        return stockView;
    }
}
