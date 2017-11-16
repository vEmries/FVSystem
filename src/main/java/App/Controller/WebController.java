package App.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = "/")
    public String showIndex() { return "profile"; }

    @GetMapping(value = "/login")
    public String showLogin() { return "login"; }

}
