package org.xwstxkx.service.crud;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.CategoryEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.repository.UserRepository;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.entity.CategoryModel;
import org.xwstxkx.repository.CategoryRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesCRUDService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private UserEntity getUser(Long id) throws ObjectNotFound {
        return userRepository.findById(id).orElseThrow(ObjectNotFound::new);
    }

    public String saveCategory(CategoryModel model, UserModel user) throws ObjectNotFound {
        if (!model.getName().equals("Общая категория")) {
            CategoryEntity entity = CategoryModel.toEntity(model);
            entity.setUser(getUser(user.getId()));
            categoryRepository.save(entity);
            log.info("Категория сохранена успешно");
            return "Категория сохранена успешно";
        } else return "Категория с таким названием уже существует";
    }

    public CategoryModel getCategory(Long id, UserModel user) throws ObjectNotFound {
        CategoryEntity category = categoryRepository.findByIdAndUser(id, getUser(user.getId()));
        log.info("Категория была найдена");
        return CategoryModel.toModel(category);
    }

    public CategoryModel getCategoryByName(String name, UserModel user) throws ObjectNotFound {
        CategoryEntity category = categoryRepository.findByNameAndUser(name, getUser(user.getId()));
        log.info("Категория была найдена");
        return CategoryModel.toModel(category);
    }

    public List<CategoryModel> getAllCategories(UserModel user) throws ObjectNotFound {
        List<CategoryEntity> categories = categoryRepository.findAllByUser(getUser(user.getId()));
        log.info("Все категории были успешно найдены");
        return CategoryModel.toListModel(categories);
    }

    @Transactional
    public String deleteCategory(Long id, UserModel user) throws ObjectNotFound {
        categoryRepository.deleteByIdAndUser(id, getUser(user.getId()));
        log.info("Категория удалена успешно");
        return "Категория удалена успешно";
    }
}
