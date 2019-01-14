package pl.reaktor.blogapplication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/blog/addPost").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                .antMatchers("/blog/delete/*").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/blog/modify/*").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/comment/delete/*").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/user/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login-process")
                .defaultSuccessUrl("/index")
                .and()
                .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/index");
    }
    // autoryzacja w oparciu o DB
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("SELECT u.email, u.password, u.active FROM user u WHERE u.email = ?")
                .authoritiesByUsernameQuery("SELECT u.email, u.permission FROM user u WHERE u.email = ?")
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
