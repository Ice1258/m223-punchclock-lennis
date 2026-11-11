package ch.zli.m223.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

import ch.zli.m223.category.Category;
import ch.zli.m223.service.CategoryService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.PathParam;



@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @GET
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Category create(Category category) {
        return categoryService.create(category);
    }


    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Category update(@PathParam("id") Long id, Category category) {
        return categoryService.update(id, category);
    }

    @Path("/{id}")
    @DELETE
    
    public void delete(@PathParam("id") Long id) {
        categoryService.delete(id);
    }

}
