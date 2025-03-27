package com.egg.egglectricity.services;
import com.egg.egglectricity.entities.Article;
import com.egg.egglectricity.entities.Factory;
import com.egg.egglectricity.exceptions.InvalidInputException;
import com.egg.egglectricity.exceptions.ResourceNotFoundException;
import com.egg.egglectricity.repositories.ArticleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ArticleService{

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    FactoryService factoryService;

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);
    @PostConstruct
    public void init() {
        Integer maxNumber = articleRepository.findMaxArticleNumber();
        atomicInteger.set(maxNumber != null ? maxNumber : 0);
    }

    @Transactional
    public Article create(String name, String description, Long factoryId) throws InvalidInputException, ResourceNotFoundException {
        Integer number = atomicInteger.incrementAndGet();
        validateData(number,name,description,factoryId);
        Factory factory = factoryService.findById(factoryId);
        Article article = new Article(
                number,
                name,
                description,
                factory
        );
        return articleRepository.save(article);
    }

    @Transactional
    public Article update(Long id,Integer number, String name, String description, Long factoryId) throws ResourceNotFoundException, InvalidInputException {
        // check if the Article exists if not, a ResourceNotFoundException is thrown.
        Article article = findById(id);

        validateData(number,name,description,factoryId);

        // Call the factory service to get the factory by id, if it does not exist, a ResourceNotFoundException is thrown.
        Factory factory = factoryService.findById(factoryId);

        // we use the setters instead of the constructor to avoid creating a new instance of the object.
        article.setNumber(number);
        article.setName(name);
        article.setDescription(description);
        article.setFactory(factory);

        return articleRepository.save(article);
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        Article articleToDelete = findById(id);
        articleRepository.delete(articleToDelete);
    }

    public List<Article> list() {
        return articleRepository.findAll();
    }

    public Article findById(Long id) throws ResourceNotFoundException {
        Optional<Article> articleResult = articleRepository.findById(id);
        if(articleResult.isPresent()){
            return articleResult.get();
        }
        throw new ResourceNotFoundException("Article with id "+ id +" not found");
    }

    private void validateData(Integer number, String name, String description, Long factoryId) throws InvalidInputException {
        if (number == null || number <= 0) {
            throw new InvalidInputException("Article number cannot be negative or null.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Article Name cannot be null or empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidInputException("Description cannot be null or empty.");
        }
        if (factoryId == null || factoryId <= 0) {
            throw new InvalidInputException("Factory Id cannot be negative or null.");
        }
    }
}
