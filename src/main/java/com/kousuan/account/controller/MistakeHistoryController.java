package com.kousuan.account.controller;

import com.kousuan.account.entity.MistakeHistoryEntity;
import com.kousuan.account.service.IMistakeHistoryServiece;
import com.kousuan.common.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 崔光怀
 * @CreateTime: 2025-05-30
 * @Description: 错误记录controller
 * @Version: 1.0
 */

@RestController
@RequestMapping("/api/v1/wrongQuestionTraining")
@RequiredArgsConstructor
public class MistakeHistoryController {

    private  final IMistakeHistoryServiece mistakeHistoryService;


     @GetMapping("/getMistakeHistoryByUserId")
      public Result<List<MistakeHistoryEntity>> getMistakeHistoryByUserId(HttpServletRequest request)
     {
         String userIdStr = (String) request.getAttribute("userId");
         Integer userId = Integer.parseInt(userIdStr);
         return mistakeHistoryService.getMistakeHistoryByUserId(userId);
     }


}
