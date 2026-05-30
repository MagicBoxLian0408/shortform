package kr.magicbox.shortform.adapter.out.persistence;

import kr.magicbox.shortform.adapter.out.persistence.entity.CommentEntity;
import kr.magicbox.shortform.adapter.out.persistence.mapper.CommentMapper;
import kr.magicbox.shortform.adapter.out.persistence.repository.CommentJpaRepository;
import kr.magicbox.shortform.application.port.out.CommentRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.Comment;
import kr.magicbox.shortform.domain.vo.CommentId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentJpaAdapter implements CommentRepositoryPort {

    private final CommentJpaRepository commentJpaRepository;
    private final CommentMapper commentMapper;

    @Override
    public Long save(Comment comment) {
        CommentEntity entity = commentJpaRepository.save(commentMapper.toEntity(comment));
        return entity.getId();
    }

    @Override
    public Optional<Comment> findById(CommentId id) {
        return commentJpaRepository.findByIdAndIsDeletedFalse(id.value())
                .map(commentMapper::toDomain);
    }

    @Override
    public List<Comment> findByShortFormIdByCursor(ShortFormId shortFormId, Long cursorId, int size) {
        List<CommentEntity> entities = cursorId == null
                ? commentJpaRepository.findByShortFormIdAndIsDeletedFalseOrderByIdDesc(shortFormId.value(), PageRequest.of(0, size))
                : commentJpaRepository.findByShortFormIdAndIdLessThanAndIsDeletedFalseOrderByIdDesc(shortFormId.value(), cursorId, PageRequest.of(0, size));
        return entities.stream().map(commentMapper::toDomain).toList();
    }
}
