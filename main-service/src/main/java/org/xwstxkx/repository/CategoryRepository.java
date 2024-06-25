package org.xwstxkx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.CategoryEntity;
import org.xwstxkx.entity.UserEntity;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Page<CategoryEntity> findAllByUser(UserEntity user, Pageable pageable);

    List<CategoryEntity> findAllByUser(UserEntity user);

    CategoryEntity findByIdAndUser(Long id, UserEntity user);

    void deleteByIdAndUser(Long id, UserEntity user);

    CategoryEntity findByNameAndUser(String name, UserEntity user);
}
