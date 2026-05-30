package kr.magicbox.shortform.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentEntity extends BaseEntity {

    @Column(name = "shortform_id", nullable = false)
    private Long shortFormId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Builder
    public CommentEntity(Long shortFormId, Long userId, String content) {
        this.shortFormId = shortFormId;
        this.userId = userId;
        this.content = content;
        this.isDeleted = false;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
