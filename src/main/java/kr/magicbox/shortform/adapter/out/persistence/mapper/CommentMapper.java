package kr.magicbox.shortform.adapter.out.persistence.mapper;

import kr.magicbox.shortform.adapter.out.persistence.entity.CommentEntity;
import kr.magicbox.shortform.domain.aggregate.Comment;
import kr.magicbox.shortform.domain.vo.CommentId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentEntity toEntity(Comment domain) {
        return CommentEntity.builder()
                .shortFormId(domain.getShortFormId().value())
                .userId(domain.getUserId().value())
                .content(domain.getContent())
                .build();
    }

    public Comment toDomain(CommentEntity entity) {
        return Comment.reconstructBuilder()
                .id(CommentId.of(entity.getId()))
                .shortFormId(ShortFormId.of(entity.getShortFormId()))
                .userId(UserId.of(entity.getUserId()))
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .isDeleted(entity.isDeleted())
                .build();
    }
}
