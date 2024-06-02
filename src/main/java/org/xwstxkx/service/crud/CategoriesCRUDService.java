package org.xwstxkx.service.crud;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.CategoryEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.CategoryModel;
import org.xwstxkx.repository.CategoryRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesCRUDService {

    private final CategoryRepository categoryRepository;

    public String saveCategory(CategoryModel model) throws ObjectNotFound {
        if (!model.getName().equals("Общая категория")) {
            categoryRepository.save(CategoryModel.toEntity(model));
            log.info("Категория сохранена успешно");
            return "Категория сохранена успешно";
        } else return "Категория с таким названием уже существует";
    }

    public CategoryModel getCategory(Long id) throws ObjectNotFound {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(ObjectNotFound::new);
        log.info("Категория была найдена");
        return CategoryModel.toModel(category);
    }

    public CategoryModel getCategoryByName(String name) {
        CategoryEntity category = categoryRepository.findByName(name);
        log.info("Категория была найдена");
        return CategoryModel.toModel(category);
    }

    public List<CategoryModel> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        log.info("Все категории были успешно найдены");
        return CategoryModel.toListModel(categories);
    }

    public String deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        log.info("Категория удалена успешно");
        return "Категория удалена успешно";
    }
}
