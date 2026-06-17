package kr.magicbox.shortform.adapter.out.persistence.repository;

import kr.magicbox.shortform.adapter.out.persistence.entity.ShortFormLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ShortFormLikeJpaRepository extends JpaRepository<ShortFormLikeEntity, Long> {

    boolean existsByShortFormIdAndUserId(Long shortFormId, Long userId);

    Optional<ShortFormLikeEntity> findByShortFormIdAndUserId(Long shortFormId, Long userId);

    @Query("SELECT l.shortFormId FROM ShortFormLikeEntity l WHERE l.shortFormId IN :shortFormIds AND l.userId = :userId")
    Set<Long> findLikedShortFormIds(@Param("shortFormIds") List<Long> shortFormIds, @Param("userId") Long userId);
}
