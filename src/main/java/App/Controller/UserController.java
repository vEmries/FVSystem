package App.Controller;

import App.Model.User;
import App.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public List<User> getUsers() { return userService.getAllUsers(); }

    @RequestMapping(value = "/admin/user", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User tmpUser) {
        userService.createUser(tmpUser.getUsername(), tmpUser.getPassword(), tmpUser.getRole());

        logger.info("Dodano użytkownika : " + tmpUser.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/user", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User tmpUser) {
        userService.updateUser(tmpUser);

        logger.info("Edytowano użytkownika : " + tmpUser.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/user/{ID}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable(name = "ID") Integer ID) {
        userService.deleteUser(ID);

        logger.info("Usunięto użytkownika");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
