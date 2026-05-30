package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.command.DeleteCommentCommand;
import kr.magicbox.shortform.application.port.in.DeleteCommentUseCase;
import kr.magicbox.shortform.application.port.out.CommentRepositoryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.Comment;
import kr.magicbox.shortform.domain.exception.CommentNotFoundException;
import kr.magicbox.shortform.domain.exception.CommentUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCommentService implements DeleteCommentUseCase {

    private final CommentRepositoryPort commentRepositoryPort;
    private final ShortFormRepositoryPort shortFormRepositoryPort;

    @Transactional
    @Override
    public void deleteComment(DeleteCommentCommand command) {
        Comment comment = commentRepositoryPort.findById(command.commentId())
                .orElseThrow(CommentNotFoundException::new);

        if (!comment.getUserId().equals(command.userId())) {
            throw new CommentUnauthorizedException();
        }

        comment.delete();
        shortFormRepositoryPort.decrementCommentCount(command.shortFormId());
    }
}
