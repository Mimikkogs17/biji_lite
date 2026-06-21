package org.example.biji_lite.controller;

import lombok.RequiredArgsConstructor;
import org.example.biji_lite.common.Result;
import org.example.biji_lite.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    /**
     * 点赞笔记
     * POST /api/like/{noteId}
     */
    @PostMapping("/{noteId}")
    public Result<Void> like(@PathVariable Long noteId) {
        likeService.like(noteId);
        return Result.success("点赞成功", null);
    }

    /**
     * 取消点赞
     * DELETE /api/like/{noteId}
     */
    @DeleteMapping("/{noteId}")
    public Result<Void> unlike(@PathVariable Long noteId) {
        likeService.unlike(noteId);
        return Result.success("取消点赞成功", null);
    }
}