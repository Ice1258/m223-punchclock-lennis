package ch.zli.m223.repository;

import jakarta.enterprise.context.ApplicationScoped;
import ch.zli.m223.category.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;


@ApplicationScoped 
public class CategoryRepository
    implements PanacheRepository<Category> {}