package pl.reaktor.blogapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.reaktor.blogapplication.model.Comment;
import pl.reaktor.blogapplication.repository.CommentRepository;
import pl.reaktor.blogapplication.service.CommentService;
import pl.reaktor.blogapplication.service.UserService;

import javax.validation.Valid;

@Controller
public class CommentController {

    UserService userService;
    CommentService commentService;
    CommentRepository commentRepository;

    @Autowired
    public CommentController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/comment/delete/{id_m}")
    public String deleteComment(@PathVariable Long id_m){
        Long deleted_id_m = commentService.getPostId(id_m);
        commentService.deleteComment(id_m);
        return"redirect:/blog/post/"+deleted_id_m;
    }

    @GetMapping("/comment/modify/{id_m}")
    public String modifyComment(@PathVariable Long id_m,
                                Model model,
                                Authentication authentication){
        model.addAttribute("comment", commentService.getComment(id_m));
        model.addAttribute("email", userService.getEmail(authentication));
        return"modifyComment";
    }
    @PostMapping("/comment/modify/{id_m}")
    public String modifyComment(@PathVariable Long id_m,
                                @ModelAttribute @Valid Comment comment,
                                BindingResult bindingResult,
                                Model model,
                                Authentication authentication){
        if(bindingResult.hasErrors()){
            return "modifyComment";
        }
        commentService.modifyComment(id_m, comment);
        model.addAttribute("email", userService.getEmail(authentication));
        return "redirect:/blog/post/"+ commentService.getPostId(id_m);
    }
}
