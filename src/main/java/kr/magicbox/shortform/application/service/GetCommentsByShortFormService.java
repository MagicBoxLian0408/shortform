package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetCommentsByShortFormQuery;
import kr.magicbox.shortform.application.dto.result.CommentResult;
import kr.magicbox.shortform.application.port.in.GetCommentsByShortFormUseCase;
import kr.magicbox.shortform.application.port.out.CommentRepositoryPort;
import kr.magicbox.shortform.application.port.out.UserNicknameQueryPort;
import kr.magicbox.shortform.domain.aggregate.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetCommentsByShortFormService implements GetCommentsByShortFormUseCase {

    private final CommentRepositoryPort commentRepositoryPort;
    private final UserNicknameQueryPort userNicknameQueryPort;

    @Transactional(readOnly = true)
    @Override
    public List<CommentResult> getCommentsByShortForm(GetCommentsByShortFormQuery query) {
        List<Comment> comments = commentRepositoryPort.findByShortFormIdByCursor(
                query.shortFormId(), query.cursorId(), query.size() + 1);

        List<Long> userIds = comments.stream()
                .map(comment -> comment.getUserId().value())
                .distinct()
                .toList();

        Map<Long, String> nicknames = userNicknameQueryPort.getNicknamesBatch(userIds).join();

        return comments.stream()
                .map(comment -> CommentResult.builder()
                        .id(comment.getId())
                        .userId(comment.getUserId())
                        .userNickname(nicknames.getOrDefault(comment.getUserId().value(), ""))
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .toList();
    }
}
