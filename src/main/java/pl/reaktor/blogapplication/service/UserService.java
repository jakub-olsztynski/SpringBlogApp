package pl.reaktor.blogapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.reaktor.blogapplication.model.User;
import pl.reaktor.blogapplication.repository.UserRepository;


@Service
public class UserService {

    UserRepository userRepository;
    @Autowired
    public  UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public String getEmail(Authentication authentication){
        if(authentication != null){
            UserDetails prinicpal = (UserDetails) authentication.getPrincipal();
            String email = prinicpal.getUsername();
            return email;
        }
        return null;
    }
    public Boolean isAdmin(Authentication authentication){
        if(authentication != null){
            UserDetails principal = (UserDetails)authentication.getPrincipal();
            String email = principal.getUsername();
            User user = userRepository.findFirstByEmail(email);
            if (user.getPermission().equals("ROLE_ADMIN")){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }
    public void saveUser(User user){
        String password = user.getPassword();
        String password_encoded = new BCryptPasswordEncoder().encode(password);
        System.out.println(password_encoded);
        user.setPassword(password_encoded);
        userRepository.save(user);
    }

}
