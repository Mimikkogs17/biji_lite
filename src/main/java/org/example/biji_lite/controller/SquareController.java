package org.example.biji_lite.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.example.biji_lite.common.Result;
import org.example.biji_lite.service.SquareService;
import org.example.biji_lite.vo.SquareNoteDetailVO;
import org.example.biji_lite.vo.SquareNoteVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/square")
@RequiredArgsConstructor
public class SquareController {

    private final SquareService squareService;

    /**
     * 获取公开笔记列表（分页）
     * GET /api/square/list?page=1&size=10&categoryId=&tagId=
     */
    @GetMapping("/list")
    public Result<Page<SquareNoteVO>> listPublicNotes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long tagId) {
        Page<SquareNoteVO> notes = squareService.listPublicNotes(page, size, categoryId, tagId);
        return Result.success(notes);
    }

    /**
     * 搜索公开笔记
     * GET /api/square/search?keyword=xxx&page=1&size=10
     */
    @GetMapping("/search")
    public Result<Page<SquareNoteVO>> searchPublicNotes(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SquareNoteVO> notes = squareService.searchPublicNotes(keyword, page, size);
        return Result.success(notes);
    }

    /**
     * 获取公开笔记详情（同时增加浏览量）
     * GET /api/square/{id}
     */
    @GetMapping("/{id}")
    public Result<SquareNoteDetailVO> getPublicNoteDetail(@PathVariable Long id) {
        SquareNoteDetailVO note = squareService.getPublicNoteDetail(id);
        return Result.success(note);
    }
}