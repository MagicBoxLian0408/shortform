package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.ShortFormId;

import java.util.List;
import java.util.Optional;

public interface ShortFormRepositoryPort {
    Long save(ShortForm shortForm);
    void update(ShortForm shortForm);
    Optional<ShortForm> findById(ShortFormId id);
    List<ShortForm> findAllByCursor(Long cursorId, int size);
    List<ShortForm> findByCreatorIdByCursor(CreatorId creatorId, Long cursorId, int size);
    List<ShortForm> findAllByCreatorId(CreatorId creatorId);

    List<ShortForm> findByCreatorIdInByCursor(List<Long> creatorIds, Long cursorId, int size);
    void softDeleteByCreatorId(CreatorId creatorId);
    void incrementLikeCount(ShortFormId id);
    void decrementLikeCount(ShortFormId id);
    void incrementCommentCount(ShortFormId id);
    void decrementCommentCount(ShortFormId id);
}
