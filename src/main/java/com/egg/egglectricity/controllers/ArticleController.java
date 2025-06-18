package com.egg.egglectricity.controllers;

import com.egg.egglectricity.entities.Article;
import com.egg.egglectricity.entities.Factory;
import com.egg.egglectricity.exceptions.InvalidInputException;
import com.egg.egglectricity.exceptions.ResourceNotFoundException;
import com.egg.egglectricity.services.ArticleService;
import com.egg.egglectricity.services.FactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FactoryService factoryService;

    @GetMapping("/list")
    public String list(ModelMap model) {
        try {
            List<Article> articles = articleService.list();
            model.put("articles", articles);
            model.put("success", "Articles successfully retrieved.");
            logger.info("Successfully retrieved {} articles.", articles.size());
        } catch (Exception e) {
            logger.error("Error retrieving articles: {}", e.getMessage(), e);
            model.put("error", "Error retrieving articles. Please try again later.");
        }
        return "articles-list.html";
    }

    @GetMapping("/add")
    public String addForm(ModelMap model){
        List<Factory> factories = factoryService.list();
        model.put("factories", factories);
        return "article-form.html";
    }

    @PostMapping("/add")
    public String addArticle(@RequestParam String name, @RequestParam String description, @RequestParam(required = false) Long factoryId, ModelMap model){
        try{
            Article article = articleService.create(name, description, factoryId);
            model.put("success", "Article successfully created");
            List<Factory> factories = factoryService.list();
            model.put("factories", factories);
            return "article-form.html";
        } catch (InvalidInputException | ResourceNotFoundException e) {
            logger.error("Error creating articles: {}", e.getMessage(), e);
            model.put("error", e.getMessage());
            model.put("name", name);
            model.put("description", description);
            model.put("factoryId", factoryId);
            List<Factory> factories = factoryService.list();
            model.put("factories", factories);
            return "article-form.html";
        }
    }
}
