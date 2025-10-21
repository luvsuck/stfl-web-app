package com.luvsic.stflserverapp.controller;

import com.luvsic.stflserverapp.custom.R;
import com.luvsic.stflserverapp.entity.vo.StockView;
import com.luvsic.stflserverapp.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 21:55
 * @description
 */
@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/report")
    public R<List<StockView>> getStockReport() {
        return R.success(stockService.stockReports());
    }
}
