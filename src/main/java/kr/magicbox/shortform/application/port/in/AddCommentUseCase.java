package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.command.AddCommentCommand;

public interface AddCommentUseCase {
    void addComment(AddCommentCommand command);
}
