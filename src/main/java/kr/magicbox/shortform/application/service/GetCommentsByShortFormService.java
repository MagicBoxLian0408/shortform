package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetCommentsByShortFormQuery;
import kr.magicbox.shortform.application.dto.result.CommentResult;
import kr.magicbox.shortform.application.port.in.GetCommentsByShortFormUseCase;
import kr.magicbox.shortform.application.port.out.CommentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCommentsByShortFormService implements GetCommentsByShortFormUseCase {

    private final CommentRepositoryPort commentRepositoryPort;

    @Transactional(readOnly = true)
    @Override
    public List<CommentResult> getCommentsByShortForm(GetCommentsByShortFormQuery query) {
        return commentRepositoryPort.findByShortFormIdByCursor(query.shortFormId(), query.cursorId(), query.size() + 1)
                .stream()
                .map(comment -> CommentResult.builder()
                        .id(comment.getId())
                        .userId(comment.getUserId())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .toList();
    }
}
