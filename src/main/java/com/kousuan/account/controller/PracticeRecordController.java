package com.kousuan.account.controller;

import com.kousuan.account.entity.Dto.PracticeRequestDTO;
import com.kousuan.account.entity.MistakeHistoryEntity;
import com.kousuan.account.entity.PracticeDetailEntity;
import com.kousuan.account.entity.PracticeHistoryEntity;
import com.kousuan.account.service.PracticeHistoryService;
import com.kousuan.account.service.PracticeRecordService;
import com.kousuan.common.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 崔光怀
 * @CreateTime: 2025-06-01
 * @Description: 记录做完题目的详情
 * @Version: 1.0
 */

@RestController
@RequestMapping("/api/v1/PracticeRecord")
@RequiredArgsConstructor
public class PracticeRecordController {
     private final PracticeRecordService practiceRecordService;
     private final PracticeHistoryService practiceHistoryService;


     @PostMapping("/submitPractice")
    public Result<String> submitPractice(@RequestBody PracticeRequestDTO dto, HttpServletRequest request) {
    // 获取 userId
    String userIdStr = (String) request.getAttribute("userId");
    Integer userId = Integer.parseInt(userIdStr);

    // 设置 userId 到 practiceHistoryEntity
    PracticeHistoryEntity historyEntity = new PracticeHistoryEntity();
    historyEntity.setUserId(userId);
    historyEntity.setLabel(dto.getLabel());
    historyEntity.setDateTime(dto.getDateTime());
    historyEntity.setTimeSpent(dto.getTimeSpent());
    historyEntity.setQuestionNums(dto.getQuestionNums());
    historyEntity.setCorrectNums(dto.getCorrectNums());
    historyEntity.setType(dto.getType());
    historyEntity.setIcon(dto.getIcon());
    historyEntity.setDifficulty(dto.getDifficulty());

    // 1️⃣ 添加练习历史记录
    Result<String> historyResult = practiceRecordService.addPracticeHistory(historyEntity);
    if (historyResult.getCode()==500) {
        return historyResult;
    }

    // 获取刚插入的 historyId（关键步骤）
    Integer historyId = practiceHistoryService.getIdByUserIdAndDateTime(userId, dto.getDateTime());
    if (historyId == null) {
        return Result.fail(500, "获取练习历史ID失败");
    }

    // 2️⃣ 处理 data 列表：添加练习详情 + 筛选错题
    List<PracticeDetailEntity> detailEntities = new ArrayList<>();
    List<MistakeHistoryEntity> mistakeEntities = new ArrayList<>();

    for (PracticeRequestDTO.DataItem item : dto.getData()) {
        // 封装 PracticeDetailEntity
        PracticeDetailEntity detail = new PracticeDetailEntity();
        detail.setHistoryId(historyId);
        detail.setQuestion(item.getQuestion());
        detail.setUserAnswer(item.getUserAnswer());
        detail.setSuccessAnswer(item.getSuccessAnswer());
        detail.setAnswerStatus(
            item.getUserAnswer().equals(item.getSuccessAnswer()) ? 1 : 0
        );
        detail.setType(item.getType());

        detailEntities.add(detail);

        // 如果答案不一致，加入错题记录
        if (!item.getUserAnswer().equals(item.getSuccessAnswer())) {
            MistakeHistoryEntity mistake = new MistakeHistoryEntity();
            mistake.setUserId(item.getUserId());
            mistake.setQuestion(item.getQuestion());
            mistake.setUserAnswer(item.getUserAnswer());
            mistake.setSuccessAnswer(item.getSuccessAnswer());
            mistake.setType(item.getType());
            mistake.setDateTime(item.getDateTime());
            mistake.setAnalyse(item.getAnalyse());
            mistake.setErrorNums(item.getErrorNums());
            mistakeEntities.add(mistake);
        }
    }

    // 插入练习详情
    Result<String> detailResult = practiceRecordService.addPracticeDetail(detailEntities);
    if (detailResult.getCode()==500) {
        return detailResult;
    }

    // 插入错题记录（如果有的话）
    if (!mistakeEntities.isEmpty()) {
        Result<String> mistakeResult = practiceRecordService.addMistakeHistory(mistakeEntities);
        if (mistakeResult.getCode()==500) {
            return mistakeResult;
        }
    }

    return Result.success("提交成功");
}


    @PostMapping("/addPracticeHistory")
    public Result<String> addPracticeHistory(@RequestBody PracticeHistoryEntity practiceHistoryEntity, HttpServletRequest request) {
        //获取用户id
        String userIdStr = (String) request.getAttribute("userId");
        Integer userId = Integer.parseInt(userIdStr);

        //把uid封装到 practiceHistoryEntity
        practiceHistoryEntity.setUserId(userId);

        return practiceRecordService.addPracticeHistory(practiceHistoryEntity);
    }


     @PostMapping("/addPracticeDetail")
     public Result<String> addPracticeDetail(@RequestBody List<PracticeDetailEntity> practiceDetailEntities, HttpServletRequest request) {
            //获取用户id
         String userIdStr = (String) request.getAttribute("userId");
         Integer userId = Integer.parseInt(userIdStr);



        return practiceRecordService.addPracticeDetail(practiceDetailEntities);
    }

    @PostMapping("/addMistakeHistory")
    public Result<String> addMistakeHistory(@RequestBody List<MistakeHistoryEntity> mistakeHistoryEntities) {
        return practiceRecordService.addMistakeHistory(mistakeHistoryEntities);
    }

}
