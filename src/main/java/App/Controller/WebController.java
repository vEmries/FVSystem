package App.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @GetMapping(value = "/")
    public String showIndex() { return "welcome"; }

    @GetMapping(value = "/login")
    public String showLogin() { return "login"; }

}
