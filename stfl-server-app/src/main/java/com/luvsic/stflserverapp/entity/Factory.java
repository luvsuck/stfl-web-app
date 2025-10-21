package com.luvsic.stflserverapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 19:38
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("factory")
public class Factory implements Serializable {
    @Serial
    private static final long serialVersionUID = -6363212734769169112L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String location;
    private Integer level;
}
