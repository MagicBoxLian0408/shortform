package kr.magicbox.shortform.adapter.out.persistence;

import kr.magicbox.shortform.adapter.out.persistence.entity.ShortFormEntity;
import kr.magicbox.shortform.adapter.out.persistence.mapper.ShortFormMapper;
import kr.magicbox.shortform.adapter.out.persistence.repository.ShortFormJpaRepository;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.exception.ShortFormNotFoundException;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShortFormJpaAdapter implements ShortFormRepositoryPort {

    private final ShortFormJpaRepository shortFormJpaRepository;
    private final ShortFormMapper shortFormMapper;

    @Override
    public Long save(ShortForm shortForm) {
        ShortFormEntity entity = shortFormJpaRepository.save(shortFormMapper.toEntity(shortForm));
        return entity.getId();
    }

    @Override
    public void update(ShortForm shortForm) {
        ShortFormEntity entity = shortFormJpaRepository.findByIdAndIsDeletedFalse(shortForm.getId().value())
                .orElseThrow(ShortFormNotFoundException::new);
        entity.updateFromDomain(shortForm);
    }

    @Override
    public Optional<ShortForm> findById(ShortFormId id) {
        return shortFormJpaRepository.findByIdAndIsDeletedFalse(id.value())
                .map(shortFormMapper::toDomain);
    }

    @Override
    public List<ShortForm> findAllByCursor(Long cursorId, int size) {
        List<ShortFormEntity> entities = cursorId == null
                ? shortFormJpaRepository.findAllByIsDeletedFalseOrderByIdDesc(PageRequest.of(0, size))
                : shortFormJpaRepository.findByIdLessThanAndIsDeletedFalseOrderByIdDesc(cursorId, PageRequest.of(0, size));
        return entities.stream().map(shortFormMapper::toDomain).toList();
    }

    @Override
    public List<ShortForm> findByCreatorIdByCursor(CreatorId creatorId, Long cursorId, int size) {
        List<ShortFormEntity> entities = cursorId == null
                ? shortFormJpaRepository.findByCreatorIdAndIsDeletedFalseOrderByIdDesc(creatorId.value(), PageRequest.of(0, size))
                : shortFormJpaRepository.findByCreatorIdAndIdLessThanAndIsDeletedFalseOrderByIdDesc(creatorId.value(), cursorId, PageRequest.of(0, size));
        return entities.stream().map(shortFormMapper::toDomain).toList();
    }

    @Override
    public List<ShortForm> findAllByCreatorId(CreatorId creatorId) {
        return shortFormJpaRepository.findAllByCreatorIdAndIsDeletedFalse(creatorId.value()).stream()
                .map(shortFormMapper::toDomain)
                .toList();
    }

    @Override
    public void softDeleteByCreatorId(CreatorId creatorId) {
        shortFormJpaRepository.softDeleteByCreatorId(creatorId.value());
    }

    @Override
    public void incrementLikeCount(ShortFormId id) {
        shortFormJpaRepository.findByIdAndIsDeletedFalse(id.value())
                .orElseThrow(ShortFormNotFoundException::new)
                .incrementLikeCount();
    }

    @Override
    public void decrementLikeCount(ShortFormId id) {
        shortFormJpaRepository.findByIdAndIsDeletedFalse(id.value())
                .orElseThrow(ShortFormNotFoundException::new)
                .decrementLikeCount();
    }

    @Override
    public void incrementCommentCount(ShortFormId id) {
        shortFormJpaRepository.findByIdAndIsDeletedFalse(id.value())
                .orElseThrow(ShortFormNotFoundException::new)
                .incrementCommentCount();
    }

    @Override
    public void decrementCommentCount(ShortFormId id) {
        shortFormJpaRepository.findByIdAndIsDeletedFalse(id.value())
                .orElseThrow(ShortFormNotFoundException::new)
                .decrementCommentCount();
    }
}
