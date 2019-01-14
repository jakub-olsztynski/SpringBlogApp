package pl.reaktor.blogapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import pl.reaktor.blogapplication.model.Comment;
import pl.reaktor.blogapplication.model.Contact;
import pl.reaktor.blogapplication.model.Post;
import pl.reaktor.blogapplication.service.BlogService;
import pl.reaktor.blogapplication.service.CommentService;
import pl.reaktor.blogapplication.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BlogController {
    private UserService userService;
    private BlogService blogService;
    private CommentService commentService;
    @Autowired
    public BlogController(UserService userService, BlogService blogService, CommentService commentService) {
        this.userService = userService;
        this.blogService = blogService;
        this.commentService = commentService;
    }


    @GetMapping("/index")
    public String index(Model model, Authentication authentication){
        List<Post> posts = blogService.getAllPosts();
        model.addAttribute("isAdmin",userService.isAdmin(authentication));
        model.addAttribute("email", userService.getEmail(authentication));
        model.addAttribute("posts", posts);
        return "index";
    }
    @PostMapping("/index")
    public String index(@ModelAttribute @Valid Post post,
                        BindingResult bindingResult,
                        Authentication authentication,
                        Model model){
        model.addAttribute("email", userService.getEmail(authentication));
        if(bindingResult.hasErrors()){
            return "addPost";
        }
        blogService.savePost(post);
        return "redirect:/index";
    }
    @GetMapping("/blog/post/{id_b}")
    public String post(@PathVariable Long id_b,
                       Model model,
                       Authentication authentication){
        Post post = blogService.getPost(id_b);
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        model.addAttribute("post", post);
        model.addAttribute("email", userService.getEmail(authentication));
        model.addAttribute("admin", userService.isAdmin(authentication));
        return "postPage";
    }

    @GetMapping("/blog/addPost")
    public String post(Model model, Authentication authentication){
        model.addAttribute("email", userService.getEmail(authentication));
        Post post = new Post();
        model.addAttribute("post",post);
        return "addPost";
    }
    @GetMapping("/blog/contact")
    public String contact(Model model,
                          Authentication authentication){
        model.addAttribute("email", userService.getEmail(authentication));
        model.addAttribute("contact", new Contact());
        return "contactForm";
    }
    @PostMapping("/blog/contact")
    public String contact(@ModelAttribute @Valid Contact contact,
                          BindingResult bindingResult,
                          Model model,
                          Authentication authentication) {
        if(bindingResult.hasErrors()){
            return "contactForm";
        }
        blogService.saveContact(contact);
        blogService.sendMailing(contact.getEmail(), "dziekujemy za wypełnienie formularza",
                "Niebawem skontaktujemy się z państwem \nPozdrawiamy\nZespół bloga");
        model.addAttribute("email", userService.getEmail(authentication));
        return "redirect:/index";
    }
    @GetMapping("/blog/delete/{id_b}")
    public String delete(@PathVariable Long id_b){
        blogService.deletePost(id_b);
        return "redirect:/index";
    }
    @GetMapping("/blog/modify/{id_b}")
    public String modify(@PathVariable Long id_b,
                         Model model,
                         Authentication authentication){
        Post post = blogService.getPostToModify(id_b);
        model.addAttribute("post",post);
        model.addAttribute("email", userService.getEmail(authentication));
        return "modifyPost";
    }
    @PostMapping("/blog/modify/{id_b}")
    public String modify(@PathVariable Long id_b,
                         @ModelAttribute @Valid Post post,
                         BindingResult bindingResult,
                         Model model,
                         Authentication authentication){
        if(bindingResult.hasErrors()){
            return "modifyPost";
        }
        blogService.modifyPost(id_b, post);
        model.addAttribute("email", userService.getEmail(authentication));
        return "redirect:/index";
    }
    @PostMapping("/blog/post/{id_b}")
    public String message(@PathVariable Long id_b,
                          @ModelAttribute
                          @Valid Comment comment,
                          BindingResult bindingResult,
                          Model model,
                          Authentication authentication){
        if(bindingResult.hasErrors()){
            return "redirect:/blog/post/"+id_b;
        }
        if(authentication!=null){
            comment.setAuthor(userService.getEmail(authentication));
        }
        commentService.addComment(id_b, comment);
        model.addAttribute("email", userService.getEmail(authentication));
        return "redirect:/blog/post/"+id_b;
    }

}
