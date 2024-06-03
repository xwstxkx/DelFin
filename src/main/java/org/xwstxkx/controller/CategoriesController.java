package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.CategoryModel;
import org.xwstxkx.service.crud.CategoriesCRUDService;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Категории")
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoriesCRUDService categoriesCRUDService;

    @GetMapping("/getCategory/{id}")
    @Operation(summary = "Нахождение категории")
    public ResponseEntity<CategoryModel> getCategory(@PathVariable Long id)
            throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoriesCRUDService.getCategory(id));
    }

    @PostMapping("/saveCategory")
    @Operation(summary = "Сохранение категории")
    public String saveCategory(@RequestBody CategoryModel categoryModel)
            throws ObjectNotFound {
        return categoriesCRUDService.saveCategory(categoryModel);
    }

    @PutMapping("/changeCategory")
    @Operation(summary = "Сохранение категории")
    public String changeCategory(@RequestBody CategoryModel categoryModel)
            throws ObjectNotFound {
        return categoriesCRUDService.saveCategory(categoryModel);
    }

    @DeleteMapping("/deleteCategory/{id}")
    @Operation(summary = "Удаление категории")
    public String saveCategory(@PathVariable Long id) {
        return categoriesCRUDService.deleteCategory(id);
    }

    @GetMapping("/getAllCategories")
    @Operation(summary = "Получение всех категорий")
    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoriesCRUDService.getAllCategories());
    }
}


