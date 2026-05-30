package kr.magicbox.shortform.adapter.out.persistence.mapper;

import kr.magicbox.shortform.adapter.out.persistence.entity.ShortFormEntity;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import org.springframework.stereotype.Component;

@Component
public class ShortFormMapper {

    public ShortFormEntity toEntity(ShortForm domain) {
        return ShortFormEntity.builder()
                .creatorId(domain.getCreatorId().value())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .videoUuid(domain.getVideoUuid())
                .thumbnailUuid(domain.getThumbnailUuid())
                .genre(domain.getGenre())
                .visibility(domain.getVisibility())
                .build();
    }

    public ShortForm toDomain(ShortFormEntity entity) {
        return ShortForm.reconstructBuilder()
                .id(ShortFormId.of(entity.getId()))
                .creatorId(CreatorId.of(entity.getCreatorId()))
                .title(entity.getTitle())
                .description(entity.getDescription())
                .videoUuid(entity.getVideoUuid())
                .thumbnailUuid(entity.getThumbnailUuid())
                .genre(entity.getGenre())
                .visibility(entity.getVisibility())
                .likeCount(entity.getLikeCount())
                .commentCount(entity.getCommentCount())
                .viewCount(entity.getViewCount())
                .isDeleted(entity.isDeleted())
                .build();
    }
}
