package kr.magicbox.shortform.adapter.out.persistence;

import kr.magicbox.shortform.adapter.out.persistence.entity.ShortFormLikeEntity;
import kr.magicbox.shortform.adapter.out.persistence.repository.ShortFormLikeJpaRepository;
import kr.magicbox.shortform.application.port.out.ShortFormLikeRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortFormLike;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.ShortFormLikeId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShortFormLikeJpaAdapter implements ShortFormLikeRepositoryPort {

    private final ShortFormLikeJpaRepository shortFormLikeJpaRepository;

    @Override
    public void save(ShortFormLike like) {
        shortFormLikeJpaRepository.save(ShortFormLikeEntity.builder()
                .shortFormId(like.getShortFormId().value())
                .userId(like.getUserId().value())
                .build());
    }

    @Override
    public boolean existsByShortFormIdAndUserId(ShortFormId shortFormId, UserId userId) {
        return shortFormLikeJpaRepository.existsByShortFormIdAndUserId(shortFormId.value(), userId.value());
    }

    @Override
    public Optional<ShortFormLike> findByShortFormIdAndUserId(ShortFormId shortFormId, UserId userId) {
        return shortFormLikeJpaRepository.findByShortFormIdAndUserId(shortFormId.value(), userId.value())
                .map(entity -> ShortFormLike.reconstructBuilder()
                        .id(ShortFormLikeId.of(entity.getId()))
                        .shortFormId(ShortFormId.of(entity.getShortFormId()))
                        .userId(UserId.of(entity.getUserId()))
                        .build());
    }

    @Override
    public void delete(ShortFormLike like) {
        shortFormLikeJpaRepository.deleteById(like.getId().value());
    }
}
