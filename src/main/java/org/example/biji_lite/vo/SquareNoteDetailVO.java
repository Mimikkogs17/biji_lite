package org.example.biji_lite.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SquareNoteDetailVO {

    private Long id;
    private Long userId;
    private Long categoryId;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String authorName;
    private String authorAvatar;
    private String categoryName;
}