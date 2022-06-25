package br.com.fiap.config.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SwaggerController {

  @GetMapping
  public RedirectView get() {
    return new RedirectView("/swagger-ui/index.html");
  }

}
