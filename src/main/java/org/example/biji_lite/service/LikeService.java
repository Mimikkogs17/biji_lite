package org.example.biji_lite.service;

public interface LikeService {

    void like(Long noteId);

    void unlike(Long noteId);

    boolean isLiked(Long userId, Long noteId);
}