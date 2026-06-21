package org.example.biji_lite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteUpdateDTO {

    @NotNull(message = "笔记ID不能为空")
    private Long id;

    @NotBlank(message = "笔记标题不能为空")
    private String title;

    @NotBlank(message = "笔记内容不能为空")
    private String content;

    private Long categoryId;

    private Integer isPublic = 0;
}