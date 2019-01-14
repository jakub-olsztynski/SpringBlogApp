package pl.reaktor.blogapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.reaktor.blogapplication.component.AutoMailingService;
import pl.reaktor.blogapplication.model.Contact;
import pl.reaktor.blogapplication.model.Post;
import pl.reaktor.blogapplication.repository.ContactRepository;
import pl.reaktor.blogapplication.repository.PostRepository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    ContactRepository contactRepository;
    PostRepository postRepository;
    AutoMailingService autoMailingService;

    @Autowired
    public BlogService(PostRepository postRepository, ContactRepository contactRepository, AutoMailingService autoMailingService){
        this.postRepository = postRepository;
        this.contactRepository = contactRepository;
        this.autoMailingService = autoMailingService;
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();

    }
    public void savePost(Post post){
        postRepository.save(post);
    }

    public void modifyPost(Post post){
        postRepository.save(post);
    }
    public void saveContact(Contact contact){
        contactRepository.save(contact);
    }

    public Post getPost(Long id_b){
        Post post = postRepository.getOne(id_b);
        return post;
    }
    public void deletePost(Long id_b){
        Post post = postRepository.getOne(id_b);
        postRepository.delete(post);
    }
    public Post getPostToModify(Long id_b){
        Post modifiedPost = postRepository.getOne(id_b);
        return modifiedPost;
    }
    public void modifyPost(Long id, Post modifiedPost){
        Post originalPost = postRepository.getOne(id);
        originalPost.setTitle(modifiedPost.getTitle());
        originalPost.setContent(modifiedPost.getContent());
        originalPost.setAuthor(modifiedPost.getAuthor());
        originalPost.setDate_added(modifiedPost.getDate_added());
        postRepository.save(modifiedPost);
    }
    public void sendMailing(String to, String subject, String message){
        autoMailingService.sendSimpleMessage(to, subject, message);
    }

}
