package kr.magicbox.shortform.adapter.out.persistence.repository;

import kr.magicbox.shortform.adapter.out.persistence.entity.ShortFormEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortFormJpaRepository extends JpaRepository<ShortFormEntity, Long> {

    Optional<ShortFormEntity> findByIdAndIsDeletedFalse(Long id);

    List<ShortFormEntity> findAllByIsDeletedFalseOrderByIdDesc(Pageable pageable);

    List<ShortFormEntity> findByIdLessThanAndIsDeletedFalseOrderByIdDesc(Long cursorId, Pageable pageable);

    List<ShortFormEntity> findByCreatorIdAndIsDeletedFalseOrderByIdDesc(Long creatorId, Pageable pageable);

    List<ShortFormEntity> findByCreatorIdAndIdLessThanAndIsDeletedFalseOrderByIdDesc(Long creatorId, Long cursorId, Pageable pageable);

    List<ShortFormEntity> findAllByCreatorIdAndIsDeletedFalse(Long creatorId);

    List<ShortFormEntity> findByCreatorIdInAndIsDeletedFalseOrderByIdDesc(List<Long> creatorIds, Pageable pageable);

    @Query("SELECT s FROM ShortFormEntity s WHERE s.creatorId IN :creatorIds AND s.id < :cursorId AND s.isDeleted = false ORDER BY s.id DESC")
    List<ShortFormEntity> findByCreatorIdInAndIdLessThanAndIsDeletedFalseOrderByIdDesc(@Param("creatorIds") List<Long> creatorIds, @Param("cursorId") Long cursorId, Pageable pageable);

    @Modifying
    @Query("UPDATE ShortFormEntity s SET s.isDeleted = true WHERE s.creatorId = :creatorId AND s.isDeleted = false")
    void softDeleteByCreatorId(@Param("creatorId") Long creatorId);
}
