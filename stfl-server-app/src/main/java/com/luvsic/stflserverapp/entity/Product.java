package com.luvsic.stflserverapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 20:18
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("product")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 3660859211127510415L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String specModel;
    private Integer quantity;
    private BigDecimal price;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class StockChange {
        private Long productId;
        private Integer quantity;
        @JsonIgnore
        private Boolean isStockIn;

        public StockChange setStockIn(Boolean stockIn) {
            this.isStockIn = stockIn;
            return this;
        }
    }
}
