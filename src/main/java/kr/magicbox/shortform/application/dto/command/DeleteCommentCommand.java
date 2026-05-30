package kr.magicbox.shortform.application.dto.command;

import kr.magicbox.shortform.domain.vo.CommentId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import kr.magicbox.shortform.domain.vo.UserId;

public record DeleteCommentCommand(ShortFormId shortFormId, CommentId commentId, UserId userId) {

    public static DeleteCommentCommand of(ShortFormId shortFormId, CommentId commentId, UserId userId) {
        return new DeleteCommentCommand(shortFormId, commentId, userId);
    }
}
