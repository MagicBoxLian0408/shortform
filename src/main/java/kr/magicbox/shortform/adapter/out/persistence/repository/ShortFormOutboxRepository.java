package kr.magicbox.shortform.adapter.out.persistence.repository;

import kr.magicbox.shortform.adapter.out.persistence.entity.ShortFormOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortFormOutboxRepository extends JpaRepository<ShortFormOutboxEntity, Long> {
}
