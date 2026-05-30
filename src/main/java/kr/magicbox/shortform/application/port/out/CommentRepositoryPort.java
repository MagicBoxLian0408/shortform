package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.aggregate.Comment;
import kr.magicbox.shortform.domain.vo.CommentId;
import kr.magicbox.shortform.domain.vo.ShortFormId;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryPort {
    Long save(Comment comment);
    Optional<Comment> findById(CommentId id);
    List<Comment> findByShortFormIdByCursor(ShortFormId shortFormId, Long cursorId, int size);
}
