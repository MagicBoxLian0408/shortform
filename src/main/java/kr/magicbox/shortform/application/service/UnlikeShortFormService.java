package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.command.UnlikeShortFormCommand;
import kr.magicbox.shortform.application.port.in.UnlikeShortFormUseCase;
import kr.magicbox.shortform.application.port.out.ShortFormLikeRepositoryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortFormLike;
import kr.magicbox.shortform.domain.exception.ShortFormLikeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnlikeShortFormService implements UnlikeShortFormUseCase {

    private final ShortFormLikeRepositoryPort shortFormLikeRepositoryPort;
    private final ShortFormRepositoryPort shortFormRepositoryPort;

    @Transactional
    @Override
    public void unlikeShortForm(UnlikeShortFormCommand command) {
        ShortFormLike like = shortFormLikeRepositoryPort.findByShortFormIdAndUserId(command.shortFormId(), command.userId())
                .orElseThrow(ShortFormLikeNotFoundException::new);

        shortFormLikeRepositoryPort.delete(like);
        shortFormRepositoryPort.decrementLikeCount(command.shortFormId());
    }
}
