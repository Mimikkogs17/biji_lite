package org.example.biji_lite.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteVO {

    private Long id;
    private Long userId;
    private Long categoryId;
    private String categoryName;
    private String title;
    private String content;
    private Integer isPublic;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}