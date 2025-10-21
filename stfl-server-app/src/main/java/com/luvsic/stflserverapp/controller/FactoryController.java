package com.luvsic.stflserverapp.controller;

import com.luvsic.stflserverapp.custom.R;
import com.luvsic.stflserverapp.entity.Factory;
import com.luvsic.stflserverapp.service.FactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 19:42
 * @description
 */
@RestController
@RequestMapping("/factory")
@RequiredArgsConstructor
public class FactoryController {
    private final FactoryService factoryService;

    @GetMapping("/list")
    public R<List<Factory>> list(@RequestParam(required = false) String key) {
        return R.success(factoryService.listFactory(key));
    }

    @PostMapping()
    public R<String> addFactory(@RequestBody Factory factory) {
        factory.setId(null);
        factoryService.addFactory(factory);
        return R.success("factory added");
    }

    @PutMapping
    public R<String> updateFactory(@RequestBody Factory factory) {
        factoryService.updateFactory(factory);
        return R.success("factory updated");
    }

    @DeleteMapping("/{id}")
    public R<String> deleteFactory(@PathVariable Long id) {
        factoryService.deleteFactory(id);
        return R.success("factory deleted");
    }
}
