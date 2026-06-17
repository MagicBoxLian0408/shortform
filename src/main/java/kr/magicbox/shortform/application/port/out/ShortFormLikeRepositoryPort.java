package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.aggregate.ShortFormLike;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ShortFormLikeRepositoryPort {
    void save(ShortFormLike like);
    boolean existsByShortFormIdAndUserId(ShortFormId shortFormId, UserId userId);
    Optional<ShortFormLike> findByShortFormIdAndUserId(ShortFormId shortFormId, UserId userId);
    void delete(ShortFormLike like);
    Set<Long> findLikedShortFormIds(List<ShortFormId> shortFormIds, UserId userId);
}
