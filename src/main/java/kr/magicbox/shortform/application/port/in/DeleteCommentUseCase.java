package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.command.DeleteCommentCommand;

public interface DeleteCommentUseCase {
    void deleteComment(DeleteCommentCommand command);
}
