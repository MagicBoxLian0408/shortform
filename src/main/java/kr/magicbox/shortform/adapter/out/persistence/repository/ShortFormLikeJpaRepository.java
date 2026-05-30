package kr.magicbox.shortform.adapter.out.persistence.repository;

import kr.magicbox.shortform.adapter.out.persistence.entity.ShortFormLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortFormLikeJpaRepository extends JpaRepository<ShortFormLikeEntity, Long> {

    boolean existsByShortFormIdAndUserId(Long shortFormId, Long userId);

    Optional<ShortFormLikeEntity> findByShortFormIdAndUserId(Long shortFormId, Long userId);
}
