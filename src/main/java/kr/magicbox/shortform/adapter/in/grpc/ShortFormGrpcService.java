package kr.magicbox.shortform.adapter.in.grpc;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import kr.magicbox.shortform.application.dto.query.GetShortFormsByCreatorIdQuery;
import kr.magicbox.shortform.application.port.in.GetShortFormsByCreatorIdUseCase;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.grpc.shortform.GetShortformsByCreatorIdRequest;
import kr.magicbox.shortform.grpc.shortform.GetShortformsByCreatorIdResponse;
import kr.magicbox.shortform.grpc.shortform.ShortformServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class ShortFormGrpcService extends ShortformServiceGrpc.ShortformServiceImplBase {

    private final GetShortFormsByCreatorIdUseCase getShortFormsByCreatorIdUseCase;

    @Override
    public void getShortformsByCreatorId(GetShortformsByCreatorIdRequest request,
                                         StreamObserver<GetShortformsByCreatorIdResponse> responseObserver) {
        List<ShortForm> shortForms = getShortFormsByCreatorIdUseCase.getShortFormsByCreatorId(
                GetShortFormsByCreatorIdQuery.of(CreatorId.of(request.getCreatorId()))
        );

        List<kr.magicbox.shortform.grpc.shortform.Shortform> grpcShortForms = shortForms.stream()
                .map(sf -> kr.magicbox.shortform.grpc.shortform.Shortform.newBuilder()
                        .setShortformId(sf.getId().value())
                        .setTitle(sf.getTitle())
                        .setThumbnailUuid(sf.getThumbnailUuid())
                        .setVideoUuid(sf.getVideoUuid())
                        .setViewCount(sf.getViewCount())
                        .setCreatedAt(Timestamp.newBuilder()
                                .setSeconds(sf.getCreatedAt().getEpochSecond())
                                .setNanos(sf.getCreatedAt().getNano())
                                .build())
                        .build())
                .toList();

        responseObserver.onNext(GetShortformsByCreatorIdResponse.newBuilder()
                .addAllShortforms(grpcShortForms)
                .build());
        responseObserver.onCompleted();
    }
}
