package org.example.biji_lite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.example.biji_lite.common.BusinessException;
import org.example.biji_lite.entity.Category;
import org.example.biji_lite.entity.Note;
import org.example.biji_lite.entity.User;
import org.example.biji_lite.mapper.CategoryMapper;
import org.example.biji_lite.mapper.NoteMapper;
import org.example.biji_lite.mapper.UserMapper;
import org.example.biji_lite.service.SquareService;
import org.example.biji_lite.vo.SquareNoteDetailVO;
import org.example.biji_lite.vo.SquareNoteVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SquareServiceImpl implements SquareService {

    private final NoteMapper noteMapper;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    @Override
    public Page<SquareNoteVO> listPublicNotes(int page, int size, Long categoryId, Long tagId) {
        LambdaQueryWrapper<Note> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Note::getIsPublic, 1);

        if (categoryId != null) {
            queryWrapper.eq(Note::getCategoryId, categoryId);
        }

        queryWrapper.orderByDesc(Note::getCreateTime);

        Page<Note> notePage = noteMapper.selectPage(new Page<>(page, size), queryWrapper);

        Page<SquareNoteVO> voPage = new Page<>(notePage.getCurrent(), notePage.getSize(), notePage.getTotal());
        List<SquareNoteVO> voList = notePage.getRecords().stream()
                .map(this::convertToSquareVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public Page<SquareNoteVO> searchPublicNotes(String keyword, int page, int size) {
        LambdaQueryWrapper<Note> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Note::getIsPublic, 1);

        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Note::getTitle, keyword.trim());
        }

        queryWrapper.orderByDesc(Note::getCreateTime);

        Page<Note> notePage = noteMapper.selectPage(new Page<>(page, size), queryWrapper);

        Page<SquareNoteVO> voPage = new Page<>(notePage.getCurrent(), notePage.getSize(), notePage.getTotal());
        List<SquareNoteVO> voList = notePage.getRecords().stream()
                .map(this::convertToSquareVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SquareNoteDetailVO getPublicNoteDetail(Long id) {
        Note note = noteMapper.selectById(id);
        if (note == null) {
            throw new BusinessException(404, "笔记不存在");
        }

        if (note.getIsPublic() != 1) {
            throw new BusinessException(403, "该笔记未公开");
        }

        note.setViewCount(note.getViewCount() + 1);
        noteMapper.updateById(note);

        return convertToDetailVO(note);
    }

    private SquareNoteVO convertToSquareVO(Note note) {
        SquareNoteVO vo = new SquareNoteVO();
        vo.setId(note.getId());
        vo.setUserId(note.getUserId());
        vo.setCategoryId(note.getCategoryId());
        vo.setTitle(note.getTitle());

        String content = note.getContent();
        vo.setSummary(content != null && content.length() > 200
                ? content.substring(0, 199) + "..."
                : content);

        vo.setViewCount(note.getViewCount());
        vo.setLikeCount(note.getLikeCount());
        vo.setCreateTime(note.getCreateTime());
        vo.setUpdateTime(note.getUpdateTime());

        User author = userMapper.selectById(note.getUserId());
        if (author != null) {
            vo.setAuthorName(author.getNickname() != null ? author.getNickname() : author.getUsername());
            vo.setAuthorAvatar(author.getAvatar());
        }

        if (note.getCategoryId() != null) {
            Category category = categoryMapper.selectById(note.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getName());
            }
        }

        return vo;
    }

    private SquareNoteDetailVO convertToDetailVO(Note note) {
        SquareNoteDetailVO vo = new SquareNoteDetailVO();
        vo.setId(note.getId());
        vo.setUserId(note.getUserId());
        vo.setCategoryId(note.getCategoryId());
        vo.setTitle(note.getTitle());
        vo.setContent(note.getContent());
        vo.setViewCount(note.getViewCount());
        vo.setLikeCount(note.getLikeCount());
        vo.setCreateTime(note.getCreateTime());
        vo.setUpdateTime(note.getUpdateTime());

        User author = userMapper.selectById(note.getUserId());
        if (author != null) {
            vo.setAuthorName(author.getNickname() != null ? author.getNickname() : author.getUsername());
            vo.setAuthorAvatar(author.getAvatar());
        }

        if (note.getCategoryId() != null) {
            Category category = categoryMapper.selectById(note.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getName());
            }
        }

        return vo;
    }
}