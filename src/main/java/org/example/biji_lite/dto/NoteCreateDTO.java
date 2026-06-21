package org.example.biji_lite.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoteCreateDTO {

    @NotBlank(message = "笔记标题不能为空")
    private String title;

    @NotBlank(message = "笔记内容不能为空")
    private String content;

    private Long categoryId;

    private Integer isPublic = 0;
}