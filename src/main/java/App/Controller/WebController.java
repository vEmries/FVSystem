package App.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // Mapowania dla *.html z /templates

    @GetMapping(value = "/")
    public String showIndex() { return "profile"; }

    @GetMapping(value = "/login")
    public String showLogin() { return "login"; }

    @GetMapping(value = "/403")
    public String show403() { return "403"; }

}
