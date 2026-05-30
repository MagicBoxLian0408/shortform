package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.command.AddCommentCommand;
import kr.magicbox.shortform.application.port.in.AddCommentUseCase;
import kr.magicbox.shortform.application.port.out.CommentRepositoryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.Comment;
import kr.magicbox.shortform.domain.exception.ShortFormNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddCommentService implements AddCommentUseCase {

    private final CommentRepositoryPort commentRepositoryPort;
    private final ShortFormRepositoryPort shortFormRepositoryPort;

    @Transactional
    @Override
    public void addComment(AddCommentCommand command) {
        shortFormRepositoryPort.findById(command.shortFormId())
                .orElseThrow(ShortFormNotFoundException::new);

        Comment comment = Comment.createBuilder()
                .shortFormId(command.shortFormId())
                .userId(command.userId())
                .content(command.content())
                .build();

        commentRepositoryPort.save(comment);
        shortFormRepositoryPort.incrementCommentCount(command.shortFormId());
    }
}
