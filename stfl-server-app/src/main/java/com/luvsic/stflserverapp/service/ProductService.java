package com.luvsic.stflserverapp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.luvsic.stflserverapp.entity.Product;
import com.luvsic.stflserverapp.mapper.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 20:19
 * @description
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    private final StockService stockService;

    public void addProduct(Product product) {
        productDAO.insert(product);
    }

    public void deleteProduct(Long id) {
        productDAO.deleteById(id);
    }

    public List<Product> listProducts(String key) {
        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(key)) {
            lqw.like(Product::getName, key)
                    .or().like(Product::getDescription, key)
                    .or().like(Product::getSpecModel, key);
        }
        return productDAO.selectList(lqw);
    }

    @Transactional(rollbackFor = Exception.class)
    public void stockIn(Product.StockChange stockChange) {
        Product product = productDAO.selectById(stockChange.getProductId());
        Integer quantity = stockChange.getQuantity() + product.getQuantity();
        product.setQuantity(quantity);
        stockService.stockChange(stockChange.setStockIn(true));
        productDAO.updateById(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void stockOut(Product.StockChange stockChange) {
        Product product = productDAO.selectById(stockChange.getProductId());
        Integer quantity = product.getQuantity() - stockChange.getQuantity();
        product.setQuantity(quantity);
        stockService.stockChange(stockChange.setStockIn(false));
        productDAO.updateById(product);
    }

}
