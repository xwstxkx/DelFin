package org.xwstxkx.service.crud;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.CategoryEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.CategoryModel;
import org.xwstxkx.repository.CategoryRepository;
import org.xwstxkx.service.security.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesCRUDService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    private UserEntity getUser() {
        return userService.getCurrentUser();
    }

    public String saveCategory(CategoryModel model) throws ObjectNotFound {
        if (!model.getName().equals("Общая категория")) {
            categoryRepository.save(CategoryModel.toEntity(model));
            log.info("Категория сохранена успешно");
            return "Категория сохранена успешно";
        } else return "Категория с таким названием уже существует";
    }

    public CategoryModel getCategory(Long id) {
        CategoryEntity category = categoryRepository.findByIdAndUser(id, getUser());
        log.info("Категория была найдена");
        return CategoryModel.toModel(category);
    }

    public CategoryModel getCategoryByName(String name) {
        CategoryEntity category = categoryRepository.findByNameAndUser(name, getUser());
        log.info("Категория была найдена");
        return CategoryModel.toModel(category);
    }

    public List<CategoryModel> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAllByUser(getUser());
        log.info("Все категории были успешно найдены");
        return CategoryModel.toListModel(categories);
    }

    public String deleteCategory(Long id) {
        categoryRepository.deleteByIdAndUser(id, getUser());
        log.info("Категория удалена успешно");
        return "Категория удалена успешно";
    }
}
