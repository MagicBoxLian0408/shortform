package kr.magicbox.shortform.domain.aggregate;

import kr.magicbox.shortform.domain.exception.InvalidFieldException;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.ShortFormLikeId;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShortFormLike {

    private final ShortFormLikeId id;
    private final ShortFormId shortFormId;
    private final UserId userId;

    @Builder(builderMethodName = "createBuilder", builderClassName = "CreateBuilder")
    public ShortFormLike(ShortFormId shortFormId, UserId userId) {
        if (shortFormId == null) throw new InvalidFieldException("숏폼 ID는 필수입니다.");
        if (userId == null) throw new InvalidFieldException("사용자 ID는 필수입니다.");
        this.id = null;
        this.shortFormId = shortFormId;
        this.userId = userId;
    }

    @Builder(builderMethodName = "reconstructBuilder", builderClassName = "ReconstructBuilder")
    public ShortFormLike(ShortFormLikeId id, ShortFormId shortFormId, UserId userId) {
        if (id == null) throw new InvalidFieldException("좋아요 ID는 필수입니다.");
        if (shortFormId == null) throw new InvalidFieldException("숏폼 ID는 필수입니다.");
        if (userId == null) throw new InvalidFieldException("사용자 ID는 필수입니다.");
        this.id = id;
        this.shortFormId = shortFormId;
        this.userId = userId;
    }
}
