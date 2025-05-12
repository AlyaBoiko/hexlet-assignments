package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public List<PostDTO> getAllPosts() {
        var posts = postRepository.findAll();

        return posts.stream().map(post -> {
            var postDto = new PostDTO();
            postDto.setId(post.getId());
            postDto.setTitle(post.getTitle());
            postDto.setBody(post.getBody());

            var comments = commentRepository.findByPostId(post.getId()).stream().map(comment -> {
                var commentDto = new CommentDTO();
                commentDto.setId(comment.getId());
                commentDto.setBody(comment.getBody());
                return commentDto;
            }).toList();

            postDto.setComments(comments);
            return postDto;
        }).toList();
    }

    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        var postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setBody(post.getBody());

        var comments = commentRepository.findByPostId(post.getId()).stream().map(comment -> {
            var commentDto = new CommentDTO();
            commentDto.setId(comment.getId());
            commentDto.setBody(comment.getBody());
            return commentDto;
        }).toList();

        postDto.setComments(comments);

        return postDto;
    }
}
// END
