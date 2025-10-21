package com.luvsic.stflserverapp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.luvsic.stflserverapp.entity.Factory;
import com.luvsic.stflserverapp.mapper.FactoryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 19:40
 * @description
 */
@Service
@RequiredArgsConstructor
public class FactoryService {
    private final FactoryDAO factoryDAO;

    public List<Factory> listFactory(String key) {
        LambdaQueryWrapper<Factory> lqw = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(key)) {
            lqw.like(Factory::getName, key)
                    .or().like(Factory::getDescription, key)
                    .or().like(Factory::getLocation, key);
        }
        return factoryDAO.selectList(lqw);
    }

    public void addFactory(Factory factory) {
        factoryDAO.insert(factory);
    }

    public void deleteFactory(Long id) {
        factoryDAO.deleteById(id);
    }

    public void updateFactory(Factory factory) {
        factoryDAO.updateById(factory);
    }
}
