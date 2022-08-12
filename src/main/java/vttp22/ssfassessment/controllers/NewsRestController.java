package vttp22.ssfassessment.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp22.ssfassessment.models.News;
import vttp22.ssfassessment.services.NewsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(path="/id", produces=MediaType.APPLICATION_JSON_VALUE)
public class NewsRestController {

    @Autowired
    private NewsService newsSvc;

    @GetMapping(value="{id}")
    public ResponseEntity<String> getNews(@PathVariable String id) {
        Optional<News> opt = newsSvc.getNewsById(id);

        if (opt.isEmpty()) {
            JsonObject err = Json.createObjectBuilder()
                .add("error", "Id %s not found".formatted(id))
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(err.toString());
        }

        News news = opt.get();
        return ResponseEntity.ok(news.toJson().toString());
    }
    
}
