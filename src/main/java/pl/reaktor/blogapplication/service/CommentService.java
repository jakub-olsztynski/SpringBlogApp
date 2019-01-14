package pl.reaktor.blogapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.reaktor.blogapplication.model.Comment;
import pl.reaktor.blogapplication.model.Post;
import pl.reaktor.blogapplication.repository.CommentRepository;
import pl.reaktor.blogapplication.repository.PostRepository;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    CommentRepository commentRepository;
    PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository){

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void addComment(Long id_b, Comment comment){
        System.out.println("XX");
        Post post = postRepository.getOne(id_b);
        List<Comment> comments = post.getComments();
        comments.add(comment);
        comment.setPost(post);
        post.setComments(comments);
        postRepository.save(post);
    }
    public void deleteComment(Long id_m){
        Comment deletedComment = commentRepository.getOne(id_m);
        Post post = deletedComment.getPost();
        List<Comment> comments=post.getComments();
        comments.remove(deletedComment);
        commentRepository.delete(deletedComment);
    }
    public Long getPostId(Long id_m){
        return commentRepository.getOne(id_m).getPost().getId_b();
    }
    public void modifyComment(Long id_m, Comment modifiedComment){
        Comment currentComment = commentRepository.getOne(id_m);
//        Post post = currentComment.getPost();
//        List<Comment> comments = post.getComments();
        currentComment.setMessage(modifiedComment.getMessage());
        currentComment.setDate_added(new Date());
        commentRepository.save(currentComment);
    }
    public Comment getComment(Long id_m){
        return commentRepository.getOne(id_m);
    }

}
