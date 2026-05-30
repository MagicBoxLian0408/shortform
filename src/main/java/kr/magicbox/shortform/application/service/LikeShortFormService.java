package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.command.LikeShortFormCommand;
import kr.magicbox.shortform.application.port.in.LikeShortFormUseCase;
import kr.magicbox.shortform.application.port.out.ShortFormLikeRepositoryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortFormLike;
import kr.magicbox.shortform.domain.exception.ShortFormLikeAlreadyExistsException;
import kr.magicbox.shortform.domain.exception.ShortFormNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeShortFormService implements LikeShortFormUseCase {

    private final ShortFormLikeRepositoryPort shortFormLikeRepositoryPort;
    private final ShortFormRepositoryPort shortFormRepositoryPort;

    @Transactional
    @Override
    public void likeShortForm(LikeShortFormCommand command) {
        shortFormRepositoryPort.findById(command.shortFormId())
                .orElseThrow(ShortFormNotFoundException::new);

        if (shortFormLikeRepositoryPort.existsByShortFormIdAndUserId(command.shortFormId(), command.userId())) {
            throw new ShortFormLikeAlreadyExistsException();
        }

        ShortFormLike like = ShortFormLike.createBuilder()
                .shortFormId(command.shortFormId())
                .userId(command.userId())
                .build();

        shortFormLikeRepositoryPort.save(like);
        shortFormRepositoryPort.incrementLikeCount(command.shortFormId());
    }
}
