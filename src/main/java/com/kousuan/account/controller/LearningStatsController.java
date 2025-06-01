package com.kousuan.account.controller;

import com.kousuan.account.entity.dto.LearningStatsDTO;
import com.kousuan.account.service.ILearningStatsService;
import com.kousuan.common.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class LearningStatsController {

    @Autowired
    private ILearningStatsService learningStatsService;

    @GetMapping("/user")
    public Result<LearningStatsDTO> getUserStats(HttpServletRequest request) {
        String userIdStr = (String) request.getAttribute("userId");
        if (userIdStr == null) {
            return Result.fail(401, "未获取到用户ID");
        }

        try {
            Integer userId = Integer.parseInt(userIdStr);
            return learningStatsService.getUserStats(userId);
        } catch (NumberFormatException e) {
            return Result.fail(400, "用户ID格式错误");
        }
    }
}
