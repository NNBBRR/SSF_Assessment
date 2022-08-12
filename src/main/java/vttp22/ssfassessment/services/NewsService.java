package vttp22.ssfassessment.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vttp22.ssfassessment.models.News;
import vttp22.ssfassessment.repositories.NewsRepository;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


@Service
public class NewsService {

    private static final String URL = "https://min-api.cryptocompare.com/data/v2/news";

    @Value("${API_KEY}")
    private String key;

    @Autowired
    private NewsRepository newsRepo;

    public Optional<News> getNewsById(String id) {
        Optional<String> result = newsRepo.get(id);
        if (null == result)
            return Optional.empty();

        return Optional.of(News.create(result));
    }

    public List<News> getArticles(String id) {

        Optional<String> opt = newsRepo.get(id);
        String payload;

        try { 
                RequestEntity<Void> req = RequestEntity.build();
                RestTemplate template = new RestTemplate();
                ResponseEntity<String> resp;

                resp = template.exchange(req, String.class);
                payload = resp.getBody();
                newsRepo.save(id, payload);

            } catch (Exception ex) {
                System.err.printf("Error: %s\n", ex.getMessage());
                return Collections.emptyList();
            }


        Reader strReader = new StringReader(payload);
       
        JsonReader jsonReader = Json.createReader(strReader);
        
        JsonObject newsResult = jsonReader.readObject();
        JsonArray data = newsResult.getJsonArray("data");
        List<News> list = new LinkedList<>();
        for (int i = 0; i < data.size(); i++) {
           
            JsonObject jo = data.getJsonObject(i);
            list.add(News.create(jo));
        }
        return list;

    } 
        
}

    
    

