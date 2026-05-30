package kr.magicbox.shortform.domain.aggregate;

import kr.magicbox.shortform.domain.enums.MagicGenre;
import kr.magicbox.shortform.domain.enums.Visibility;
import kr.magicbox.shortform.domain.exception.InvalidFieldException;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShortForm {

    private final ShortFormId id;
    private final CreatorId creatorId;
    private String title;
    private String description;
    private String videoUuid;
    private String thumbnailUuid;
    private MagicGenre genre;
    private Visibility visibility;
    private Long likeCount;
    private Long commentCount;
    private Long viewCount;
    private boolean isDeleted;

    @Builder(builderMethodName = "createBuilder", builderClassName = "CreateBuilder")
    public ShortForm(CreatorId creatorId, String title, String description, String videoUuid,
                     String thumbnailUuid, MagicGenre genre, Visibility visibility) {
        validateCreate(creatorId, title, videoUuid, thumbnailUuid, genre, visibility);
        this.id = null;
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

    @Builder(builderMethodName = "reconstructBuilder", builderClassName = "ReconstructBuilder")
    public ShortForm(ShortFormId id, CreatorId creatorId, String title, String description,
                     String videoUuid, String thumbnailUuid, MagicGenre genre, Visibility visibility,
                     Long likeCount, Long commentCount, Long viewCount, boolean isDeleted) {
        validateReconstruct(id, creatorId, title, videoUuid, thumbnailUuid, genre, visibility);
        this.id = id;
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.videoUuid = videoUuid;
        this.thumbnailUuid = thumbnailUuid;
        this.genre = genre;
        this.visibility = visibility;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.isDeleted = isDeleted;
    }

    public void update(String title, String description, String videoUuid, String thumbnailUuid,
                       MagicGenre genre, Visibility visibility) {
        if (title != null && !title.isBlank()) this.title = title;
        if (description != null) this.description = description;
        if (videoUuid != null && !videoUuid.isBlank()) this.videoUuid = videoUuid;
        if (thumbnailUuid != null && !thumbnailUuid.isBlank()) this.thumbnailUuid = thumbnailUuid;
        if (genre != null) this.genre = genre;
        if (visibility != null) this.visibility = visibility;
    }

    public void delete() {
        this.isDeleted = true;
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

    public void incrementViewCount() {
        this.viewCount++;
    }

    private void validateCreate(CreatorId creatorId, String title, String videoUuid,
                                String thumbnailUuid, MagicGenre genre, Visibility visibility) {
        if (creatorId == null) throw new InvalidFieldException("크리에이터 ID는 필수입니다.");
        if (title == null || title.isBlank()) throw new InvalidFieldException("제목은 필수입니다.");
        if (title.length() > 100) throw new InvalidFieldException("제목은 100자 이하여야 합니다.");
        if (videoUuid == null || videoUuid.isBlank()) throw new InvalidFieldException("동영상 UUID는 필수입니다.");
        if (thumbnailUuid == null || thumbnailUuid.isBlank()) throw new InvalidFieldException("썸네일 UUID는 필수입니다.");
        if (genre == null) throw new InvalidFieldException("장르는 필수입니다.");
        if (visibility == null) throw new InvalidFieldException("공개 여부는 필수입니다.");
    }

    private void validateReconstruct(ShortFormId id, CreatorId creatorId, String title, String videoUuid,
                                     String thumbnailUuid, MagicGenre genre, Visibility visibility) {
        if (id == null) throw new InvalidFieldException("숏폼 ID는 필수입니다.");
        validateCreate(creatorId, title, videoUuid, thumbnailUuid, genre, visibility);
    }
}
