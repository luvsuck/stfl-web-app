package com.luvsic.stflserverapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 20:59
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
public class StockRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = -4920064261663785385L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    /**
     * isStockIn 是否入库
     */
    private Boolean isStockIn;
    private Integer quantity;
}
