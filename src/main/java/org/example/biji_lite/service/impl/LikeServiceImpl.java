package org.example.biji_lite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.biji_lite.common.BusinessException;
import org.example.biji_lite.common.UserContext;
import org.example.biji_lite.entity.LikeRecord;
import org.example.biji_lite.entity.Note;
import org.example.biji_lite.mapper.LikeRecordMapper;
import org.example.biji_lite.mapper.NoteMapper;
import org.example.biji_lite.service.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRecordMapper likeRecordMapper;
    private final NoteMapper noteMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void like(Long noteId) {
        Long userId = UserContext.getUserId();

        Note note = noteMapper.selectById(noteId);
        if (note == null) {
            throw new BusinessException(404, "笔记不存在");
        }

        LambdaQueryWrapper<LikeRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LikeRecord::getUserId, userId);
        queryWrapper.eq(LikeRecord::getNoteId, noteId);
        Long count = likeRecordMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(400, "您已点赞过该笔记");
        }

        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setUserId(userId);
        likeRecord.setNoteId(noteId);
        likeRecordMapper.insert(likeRecord);

        note.setLikeCount(note.getLikeCount() + 1);
        noteMapper.updateById(note);

        log.info("用户 {} 点赞笔记 {} 成功", userId, noteId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlike(Long noteId) {
        Long userId = UserContext.getUserId();

        LambdaQueryWrapper<LikeRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LikeRecord::getUserId, userId);
        queryWrapper.eq(LikeRecord::getNoteId, noteId);
        LikeRecord likeRecord = likeRecordMapper.selectOne(queryWrapper);
        if (likeRecord == null) {
            throw new BusinessException(400, "您尚未点赞该笔记");
        }

        likeRecordMapper.deleteById(likeRecord.getId());

        Note note = noteMapper.selectById(noteId);
        if (note != null && note.getLikeCount() > 0) {
            note.setLikeCount(note.getLikeCount() - 1);
            noteMapper.updateById(note);
        }

        log.info("用户 {} 取消点赞笔记 {} 成功", userId, noteId);
    }

    @Override
    public boolean isLiked(Long userId, Long noteId) {
        LambdaQueryWrapper<LikeRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LikeRecord::getUserId, userId);
        queryWrapper.eq(LikeRecord::getNoteId, noteId);
        return likeRecordMapper.selectCount(queryWrapper) > 0;
    }
}