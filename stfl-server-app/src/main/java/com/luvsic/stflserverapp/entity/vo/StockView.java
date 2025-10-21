package com.luvsic.stflserverapp.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 21:39
 * @description
 */
@Setter
@Getter
@NoArgsConstructor
public class StockView implements Serializable {
    @Serial
    private static final long serialVersionUID = 589178209072655908L;
    @TableId(type = IdType.AUTO)
    private Long productId;
    private String productName;
    private BigDecimal totalAmount;
    private Integer quantity;
    private Integer stockInQuantity;
    private Integer stockOutQuantity;
}
