package com.example.Product.repository;

import com.example.Product.model.Product;
import com.example.Product.model.Category; // Import the new enum
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Product> findProductsByFilters(Category categoryName, String brand, Double minPrice, Double maxPrice, Double minRating) {
        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();

        if (categoryName != null) {
            criteria.add(Criteria.where("categoryName").is(categoryName));
        }

        if (brand != null && !brand.isEmpty()) {
            criteria.add(Criteria.where("brand").is(brand));
        }

        if (minPrice != null && maxPrice != null) {
            criteria.add(Criteria.where("price").gte(minPrice).lte(maxPrice));
        } else if (minPrice != null) {
            criteria.add(Criteria.where("price").gte(minPrice));
        } else if (maxPrice != null) {
            criteria.add(Criteria.where("price").lte(maxPrice));
        }

        if (minRating != null) {
            criteria.add(Criteria.where("rating").gte(minRating));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return mongoTemplate.find(query, Product.class);
    }
}
