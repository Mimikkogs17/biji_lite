package org.example.biji_lite.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.biji_lite.common.Result;
import org.example.biji_lite.dto.NoteCreateDTO;
import org.example.biji_lite.dto.NoteUpdateDTO;
import org.example.biji_lite.service.NoteService;
import org.example.biji_lite.vo.NoteVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/list")
    public Result<Page<NoteVO>> listNotes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long categoryId) {
        Page<NoteVO> notes = noteService.listNotes(page, size, categoryId);
        return Result.success(notes);
    }

    @GetMapping("/{id}")
    public Result<NoteVO> getNoteById(@PathVariable Long id) {
        NoteVO note = noteService.getNoteById(id);
        return Result.success(note);
    }

    @PostMapping
    public Result<Long> createNote(@RequestBody @Valid NoteCreateDTO createDTO) {
        Long noteId = noteService.createNote(createDTO);
        return Result.success("创建成功", noteId);
    }

    @PutMapping("/{id}")
    public Result<Void> updateNote(@PathVariable Long id,
                                   @RequestBody @Valid NoteUpdateDTO updateDTO) {
        updateDTO.setId(id);
        noteService.updateNote(updateDTO);
        return Result.success("更新成功", null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return Result.success("删除成功", null);
    }
}