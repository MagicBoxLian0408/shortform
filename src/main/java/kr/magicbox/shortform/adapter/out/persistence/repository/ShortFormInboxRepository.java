package kr.magicbox.shortform.adapter.out.persistence.repository;

import kr.magicbox.shortform.adapter.out.persistence.entity.ShortFormInboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortFormInboxRepository extends JpaRepository<ShortFormInboxEntity, Long> {

    boolean existsByEventId(Long eventId);

    Optional<ShortFormInboxEntity> findByTopicAndPartitionAndOffset(String topic, Integer partition, Long offset);
}
