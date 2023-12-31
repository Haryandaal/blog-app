package com.harr.blog.service;

import com.harr.blog.dtos.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto commentRequest);
    void deleteComment(long postId, long commentId);
}
