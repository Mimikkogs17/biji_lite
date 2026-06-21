package org.example.biji_lite.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.biji_lite.dto.NoteCreateDTO;
import org.example.biji_lite.dto.NoteUpdateDTO;
import org.example.biji_lite.vo.NoteVO;

public interface NoteService {

    Page<NoteVO> listNotes(int page, int size, Long categoryId);

    NoteVO getNoteById(Long id);

    Long createNote(NoteCreateDTO createDTO);

    void updateNote(NoteUpdateDTO updateDTO);

    void deleteNote(Long id);
}