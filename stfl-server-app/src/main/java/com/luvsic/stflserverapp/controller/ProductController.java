package com.luvsic.stflserverapp.controller;

import com.luvsic.stflserverapp.custom.R;
import com.luvsic.stflserverapp.entity.Product;
import com.luvsic.stflserverapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 20:27
 * @description
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
class ProductController {
    private final ProductService productService;

    @PostMapping
    public R<String> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return R.success("Product added");
    }

    @GetMapping("/list")
    public R<List<Product>> getAllProducts(@RequestParam(required = false) String key) {
        return R.success(productService.listProducts(key));
    }

    @DeleteMapping("/{id}")
    public R<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return R.success("Product deleted");
    }

    @PostMapping("/stock/in")
    public R<String> stockIn(@RequestBody Product.StockChange stockChange) {
        productService.stockIn(stockChange);
        return R.success("Stock changed");
    }

    @PostMapping("/stock/out")
    public R<String> stockOut(@RequestBody Product.StockChange stockChange) {
        productService.stockOut(stockChange);
        return R.success("Stock changed");
    }


}
