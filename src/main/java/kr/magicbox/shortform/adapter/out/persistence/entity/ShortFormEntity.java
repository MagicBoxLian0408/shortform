package kr.magicbox.shortform.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.enums.MagicGenre;
import kr.magicbox.shortform.domain.enums.Visibility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shortform")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ShortFormEntity extends BaseEntity {

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "video_uuid", nullable = false)
    private String videoUuid;

    @Column(name = "thumbnail_uuid", nullable = false)
    private String thumbnailUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private MagicGenre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @Column(name = "comment_count", nullable = false)
    private Long commentCount;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Version
    private Integer version;

    @Builder
    public ShortFormEntity(Long creatorId, String title, String description, String videoUuid,
                           String thumbnailUuid, MagicGenre genre, Visibility visibility) {
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.videoUuid = videoUuid;
        this.thumbnailUuid = thumbnailUuid;
        this.genre = genre;
        this.visibility = visibility;
        this.likeCount = 0L;
        this.commentCount = 0L;
        this.viewCount = 0L;
        this.isDeleted = false;
    }

    public void updateFromDomain(ShortForm domain) {
        this.title = domain.getTitle();
        this.description = domain.getDescription();
        this.videoUuid = domain.getVideoUuid();
        this.thumbnailUuid = domain.getThumbnailUuid();
        this.genre = domain.getGenre();
        this.visibility = domain.getVisibility();
        this.isDeleted = domain.isDeleted();
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) this.likeCount--;
    }

    public void incrementCommentCount() {
        this.commentCount++;
    }

    public void decrementCommentCount() {
        if (this.commentCount > 0) this.commentCount--;
    }
}
