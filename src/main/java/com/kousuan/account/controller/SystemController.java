package com.kousuan.account.controller;

import com.kousuan.account.entity.dto.SystemDTO;
import com.kousuan.account.service.ISystemService;
import com.kousuan.common.util.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/setting")
public class SystemController {

    @Resource
    private ISystemService settingsService;

    @PostMapping("/update") // 修改路径为update
    public Result<Boolean> updateSettings(@RequestBody SystemDTO settingsDTO,
                                          HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return settingsService.updateSettings(Integer.valueOf(userId), settingsDTO);
    }

    @GetMapping("/get")
    public Result<SystemDTO> getSettings(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return settingsService.getSettingsByUserId(Integer.valueOf(userId));
    }
}
