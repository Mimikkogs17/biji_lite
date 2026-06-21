package org.example.biji_lite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.example.biji_lite.common.BusinessException;
import org.example.biji_lite.common.UserContext;
import org.example.biji_lite.dto.NoteCreateDTO;
import org.example.biji_lite.dto.NoteUpdateDTO;
import org.example.biji_lite.entity.Category;
import org.example.biji_lite.entity.Note;
import org.example.biji_lite.mapper.CategoryMapper;
import org.example.biji_lite.mapper.NoteMapper;
import org.example.biji_lite.service.NoteService;
import org.example.biji_lite.vo.NoteVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteMapper noteMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<NoteVO> listNotes(int page, int size, Long categoryId) {
        Long userId = UserContext.getUserId();

        LambdaQueryWrapper<Note> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Note::getUserId, userId);
        if (categoryId != null) {
            queryWrapper.eq(Note::getCategoryId, categoryId);
        }
        queryWrapper.orderByDesc(Note::getUpdateTime);

        Page<Note> notePage = noteMapper.selectPage(new Page<>(page, size), queryWrapper);

        Page<NoteVO> voPage = new Page<>(notePage.getCurrent(), notePage.getSize(), notePage.getTotal());
        List<NoteVO> voList = notePage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public NoteVO getNoteById(Long id) {
        Note note = noteMapper.selectById(id);
        if (note == null) {
            throw new BusinessException(404, "笔记不存在");
        }
        return convertToVO(note);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createNote(NoteCreateDTO createDTO) {
        Long userId = UserContext.getUserId();

        Note note = new Note();
        note.setUserId(userId);
        note.setTitle(createDTO.getTitle());
        note.setContent(createDTO.getContent());
        note.setCategoryId(createDTO.getCategoryId());
        note.setIsPublic(createDTO.getIsPublic());
        note.setViewCount(0);
        note.setLikeCount(0);

        noteMapper.insert(note);
        return note.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNote(NoteUpdateDTO updateDTO) {
        Long userId = UserContext.getUserId();

        Note existingNote = noteMapper.selectById(updateDTO.getId());
        if (existingNote == null) {
            throw new BusinessException(404, "笔记不存在");
        }
        if (!existingNote.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权修改他人的笔记");
        }

        Note note = new Note();
        note.setId(updateDTO.getId());
        note.setTitle(updateDTO.getTitle());
        note.setContent(updateDTO.getContent());
        note.setCategoryId(updateDTO.getCategoryId());
        note.setIsPublic(updateDTO.getIsPublic());

        noteMapper.updateById(note);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNote(Long id) {
        Long userId = UserContext.getUserId();

        Note note = noteMapper.selectById(id);
        if (note == null) {
            throw new BusinessException(404, "笔记不存在");
        }
        if (!note.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权删除他人的笔记");
        }

        noteMapper.deleteById(id);
    }

    private NoteVO convertToVO(Note note) {
        NoteVO vo = new NoteVO();
        vo.setId(note.getId());
        vo.setUserId(note.getUserId());
        vo.setCategoryId(note.getCategoryId());
        vo.setTitle(note.getTitle());
        vo.setContent(note.getContent());
        vo.setIsPublic(note.getIsPublic());
        vo.setViewCount(note.getViewCount());
        vo.setLikeCount(note.getLikeCount());
        vo.setCreateTime(note.getCreateTime());
        vo.setUpdateTime(note.getUpdateTime());

        if (note.getCategoryId() != null) {
            Category category = categoryMapper.selectById(note.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getName());
            }
        }

        return vo;
    }
}