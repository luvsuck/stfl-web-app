package com.luvsic.stflserverapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 15:32
 * @description
 */
@Getter
@Setter
@TableName("user")
@ToString
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 4571117696642554213L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
