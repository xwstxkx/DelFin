package org.xwstxkx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.LoanEntity;
import org.xwstxkx.entity.UserEntity;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
//    Page<LoanEntity> findAllByUser(UserEntity user, Pageable pageable);

    List<LoanEntity> findAllByUser(UserEntity user);

    LoanEntity findByIdAndUser(Long id, UserEntity user);

    void deleteByIdAndUser(Long id, UserEntity user);
}
