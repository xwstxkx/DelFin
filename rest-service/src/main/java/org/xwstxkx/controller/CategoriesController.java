package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.model.entity.CategoryModel;
import org.xwstxkx.service.RestUserService;
import org.xwstxkx.service.crud.CategoriesCRUDService;
import org.xwstxkx.user.RestUser;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Категории")
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoriesCRUDService categoriesCRUDService;
    private final RestUserService restUserService;

    private RestUser getUser() {
        return restUserService.getCurrentUser();
    }

    @GetMapping("/getCategory/{id}")
    @Operation(summary = "Нахождение категории")
    public ResponseEntity<CategoryModel> getCategory(@PathVariable Long id)
            throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoriesCRUDService.getCategory(id,
                        UserModel.toModel(getUser()) ));
    }

    @PostMapping("/saveCategory")
    @Operation(summary = "Сохранение категории")
    public String saveCategory(@RequestBody CategoryModel categoryModel)
            throws ObjectNotFound {
        return categoriesCRUDService.saveCategory(categoryModel,
                UserModel.toModel(getUser()));
    }

    @PutMapping("/changeCategory")
    @Operation(summary = "Сохранение категории")
    public String changeCategory(@RequestBody CategoryModel categoryModel)
            throws ObjectNotFound {
        return categoriesCRUDService.saveCategory(categoryModel,
                UserModel.toModel(getUser()));
    }

    @DeleteMapping("/deleteCategory/{id}")
    @Operation(summary = "Удаление категории")
    public String deleteCategory(@PathVariable Long id) throws ObjectNotFound {
        return categoriesCRUDService.deleteCategory(id, UserModel.toModel(getUser()));
    }

    @GetMapping("/getAllCategories")
    @Operation(summary = "Получение всех категорий")
    public ResponseEntity<List<CategoryModel>> getAllCategories() throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoriesCRUDService.getAllCategories(UserModel.toModel(getUser())));
    }
}


