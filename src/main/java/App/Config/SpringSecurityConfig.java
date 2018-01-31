package App.Config;

import App.Model.User;
import App.Model.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.Properties;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers(("/**")).permitAll()
//                .antMatchers("/profile", "/403", "/mainPage.html" ,"/fvPage.html", "/revisionPage.html", "/paymentPage.html", "/archivePage.html").hasAnyRole("user", "payer", "admin")
//                .antMatchers("/admin/**").hasAnyRole("admin")
//                .antMatchers(HttpMethod.GET, "/fv/**", "/fvr/**", "/payment/**", "/archive/**", "/contractor/**").hasAnyRole("user", "payer", "admin")
//                .antMatchers(HttpMethod.POST, "/fv/**", "/fvr/**", "/archive/**", "/contractor/**").hasAnyRole("user", "admin")
//                .antMatchers(HttpMethod.POST, "/payment/**").hasAnyRole("user", "payer", "admin")
//                .antMatchers(HttpMethod.PUT, "/fv/**", "/fvr/**", "/archive/**", "/contractor/**").hasAnyRole("user", "admin")
//                .antMatchers(HttpMethod.PUT, "/payment/**").hasAnyRole("user", "payer", "admin")
//                .antMatchers(HttpMethod.DELETE, "/fv/**", "/fvr/**", "/archive/**", "/contractor/**").hasAnyRole("user", "admin")
//                .antMatchers(HttpMethod.DELETE, "/payment/**").hasAnyRole("user", "payer", "admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.userDetailsService(inMemoryUserDetailsManager());
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        Properties users = new Properties();

        for (User u : userRepo.findAll()) {
            String role = u.getPassword() + ", " + u.getRole() + ", enabled";
            users.put(u.getUsername(), role);
        }

        return new InMemoryUserDetailsManager(users);
    }

}
