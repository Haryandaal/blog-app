package com.harr.blog.service.impl;

import com.harr.blog.dtos.PostDto;
import com.harr.blog.dtos.PostResponse;
import com.harr.blog.entity.Post;
import com.harr.blog.exception.ResourceNotFoundException;
import com.harr.blog.repository.PostRepository;
import com.harr.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.mapper = mapper;
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

//        Convert DTO to Entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

//        Convert Entity to DTO
        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

//        create pageable instance
        Pageable page = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(page);

//        get content for page object
        List<Post> listOfPosts = posts.getContent();

       List<PostDto> content =  listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
//        get post by id from database
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post postResponse = postRepository.save(post);
        return mapToDto(postResponse);

    }

    @Override
    public void deletePostById(long id) {
//        get post by id from database
        Post post = postRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);
    }

    //    convert Entity into DTO
    private PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    //    convert DTO into Entity
    private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}


// COMMENT GA MUNCUL DI POST