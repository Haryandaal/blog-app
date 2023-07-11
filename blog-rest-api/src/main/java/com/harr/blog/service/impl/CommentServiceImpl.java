package com.harr.blog.service.impl;

import com.harr.blog.dtos.CommentDto;
import com.harr.blog.entity.Comment;
import com.harr.blog.entity.Post;
import com.harr.blog.exception.BlogAPIException;
import com.harr.blog.exception.ResourceNotFoundException;
import com.harr.blog.repository.CommentRepository;
import com.harr.blog.repository.PostRepository;
import com.harr.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper mapper) {
        this.mapper = mapper;
        this.commentRepository = commentRepository;
    }
    @Autowired
    private PostRepository postRepository;

//    public CommentServiceImpl(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        //retrieve post entity by id
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //set Post to comment entity
        comment.setPost(post);

        //comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
//        Retrieve comment by post id
        List<Comment> comments = commentRepository.findByPostId(postId);

//        convert list of comment entity to list of comment DTO
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
//        Retrieve post entity by id
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

//        Retrieve comment entity by id
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);

    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
//        Retrieve post entity by id
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

//        Retrieve comment entity by id
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
//        Retrieve post entity by id
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

//        Retrieve comment entity by id
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }


    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
