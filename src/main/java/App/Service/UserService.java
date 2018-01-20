package App.Service;

import App.Model.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    InMemoryUserDetailsManager inMemoryUserDetailsManager;

    private Collection<? extends GrantedAuthority> createAutohrities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }

    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }

    public void createUser(String username, String password, String role) {
        App.Model.User newUser = new App.Model.User(username, password, role);
        userRepo.save(newUser);

        inMemoryUserDetailsManager.createUser(new User(username, password, createAutohrities(role)));
    }

    public void checkUser(String userName) {
        System.out.println(inMemoryUserDetailsManager.loadUserByUsername("user1"));
        System.out.println(inMemoryUserDetailsManager.loadUserByUsername("user1").getAuthorities());
    }
}
