package org.example.biji_lite.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.biji_lite.vo.SquareNoteDetailVO;
import org.example.biji_lite.vo.SquareNoteVO;

public interface SquareService {

    Page<SquareNoteVO> listPublicNotes(int page, int size, Long categoryId, Long tagId);

    Page<SquareNoteVO> searchPublicNotes(String keyword, int page, int size);

    SquareNoteDetailVO getPublicNoteDetail(Long id);
}