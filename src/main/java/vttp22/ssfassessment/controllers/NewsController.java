package vttp22.ssfassessment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp22.ssfassessment.models.News;
import vttp22.ssfassessment.services.NewsService;

@Controller
@RequestMapping(path={"/", "/index.html"})
public class NewsController {

    @Autowired
    private NewsService newsSvc;

    @GetMapping
    public String getArticles(Model model, @RequestParam String id) {
        List<News> news = newsSvc.getArticles(id);
        model.addAttribute("id", id);
        model.addAttribute("news", news);
        return "index";
    }

    @PostMapping
	public String postCart(@RequestBody MultiValueMap<String, String> form
				, Model model) {

		String id = form.getFirst("id");
		if ((null == id) || (id.trim().length() <= 0))
			id = "anonymous";

		model.addAttribute("id", id);

		return "index";
    }
    
}
