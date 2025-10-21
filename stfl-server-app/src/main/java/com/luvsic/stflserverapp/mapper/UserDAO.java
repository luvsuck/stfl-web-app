package com.luvsic.stflserverapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luvsic.stflserverapp.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 15:36
 * @description
 */
@Mapper
public interface UserDAO extends BaseMapper<User> {
}
