package kr.magicbox.shortform.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shortform_like", uniqueConstraints = {
        @UniqueConstraint(name = "uk_shortform_like", columnNames = {"shortform_id", "user_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ShortFormLikeEntity extends BaseEntity {

    @Column(name = "shortform_id", nullable = false)
    private Long shortFormId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public ShortFormLikeEntity(Long shortFormId, Long userId) {
        this.shortFormId = shortFormId;
        this.userId = userId;
    }
}
