package pl.reaktor.blogapplication.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.reaktor.blogapplication.model.User;
import pl.reaktor.blogapplication.repository.UserRepository;
import pl.reaktor.blogapplication.service.BlogService;
import pl.reaktor.blogapplication.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

private UserService userService;
private BlogService blogService;

    @Autowired
    public UserController(UserService userService, BlogService blogService) {

        this.userService = userService;
        this.blogService = blogService;
    }

    @GetMapping("/user/register")
    public String registration(Model model, Authentication authentication, UserService userService){
        model.addAttribute("email",userService.getEmail(authentication));
        model.addAttribute("user", new User());
        return "addUser";
    }
    @PostMapping("/user/register")
    public String registration(@ModelAttribute @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "addUser";
        }
       blogService.sendMailing(user.getEmail(), "Dziękujemy za rejestracje",
               "Dziękujemy za rejestracje, " + user.getName() +" "+ user.getLastname() + "\nPozdrawiamy\n\nZespół bloga");
       userService.saveUser(user);
        return "redirect:/index";
    }
    @GetMapping("user/login")
    public String login (){
        System.out.print("logowanie");
        return "loginForm";
    }

}
