package ru.rococo.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.transaction.annotation.Transactional;
import ru.rococo.data.UserEntity;
import ru.rococo.data.repository.UserdataRepository;
import ru.rococo.ex.UserdataNotFoundException;
import ru.rococo.grpc.RococoUserdataServiceGrpc;
import ru.rococo.grpc.UserRequest;
import ru.rococo.grpc.UserResponse;
import ru.rococo.model.UserJson;

import java.nio.charset.StandardCharsets;

@GrpcService
public class UserdataService extends RococoUserdataServiceGrpc.RococoUserdataServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(UserdataService.class);

    private final UserdataRepository userdataRepository;

    @Autowired
    public UserdataService(UserdataRepository userdataRepository) {
        this.userdataRepository = userdataRepository;
    }

    public static boolean isPhotoString(String photo) {
        return photo != null && photo.startsWith("data:image");
    }

    @Transactional
    @KafkaListener(topics = "users", groupId = "userdata")
    public void listener(@Payload UserJson user, ConsumerRecord<String, UserJson> cr) {
        userdataRepository.findByUsername(user.username())
                          .ifPresentOrElse(
                                  u -> LOG.info("### User already exist in DB, kafka event will be skipped: {}", cr.toString()),
                                  () -> {
                                      LOG.info("### Kafka consumer record: {}", cr.toString());

                                      UserEntity userEntity = new UserEntity();
                                      userEntity.setUsername(user.username());
                                      UserEntity savedUserEntity = userdataRepository.save(userEntity);

                                      LOG.info(
                                              "### User '{}' successfully saved to database with id: {}",
                                              user.username(),
                                              savedUserEntity.getId()
                                      );
                                  }
                          );
    }

    @Override
    public void getUser(UserRequest userRequest, StreamObserver<UserResponse> responseObserver) {
        String username = userRequest.getUsername();
        UserResponse response = userdataRepository.findByUsername(username)
                                                  .map(UserEntity::toUserResponse)
                                                  .orElseThrow(() -> new UserdataNotFoundException(
                                                          "Username: `" + username + "` not found")
                                                  );

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UserRequest userRequest, StreamObserver<UserResponse> responseObserver) {
        String username = userRequest.getUsername();
        UserEntity userEntity = userdataRepository.findByUsername(username)
                                                  .orElseThrow(() -> new UserdataNotFoundException(
                                                          "Username: `" + username + "` not found")
                                                  );
        userEntity.setFirstname(userRequest.getFirstname());
        userEntity.setLastname(userRequest.getLastname());
        String avatar = userRequest.getAvatar();
        if (isPhotoString(avatar)) {
            userEntity.setAvatar(avatar.getBytes(StandardCharsets.UTF_8));
        }

        userdataRepository.save(userEntity);

        responseObserver.onNext(userEntity.toUserResponse());
        responseObserver.onCompleted();
    }
}
