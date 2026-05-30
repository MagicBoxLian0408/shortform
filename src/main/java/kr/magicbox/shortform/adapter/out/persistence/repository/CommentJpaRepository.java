package kr.magicbox.shortform.adapter.out.persistence.repository;

import kr.magicbox.shortform.adapter.out.persistence.entity.CommentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findByIdAndIsDeletedFalse(Long id);

    List<CommentEntity> findByShortFormIdAndIsDeletedFalseOrderByIdDesc(Long shortFormId, Pageable pageable);

    List<CommentEntity> findByShortFormIdAndIdLessThanAndIsDeletedFalseOrderByIdDesc(Long shortFormId, Long cursorId, Pageable pageable);
}
