package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.vo.UserId;

import java.util.List;

public interface SubscribedCreatorIdsQueryPort {
    List<Long> getSubscribedCreatorIds(UserId userId);
}
