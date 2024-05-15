package ru.otus.numbers;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import ru.otus.numbers.NumberGeneratorOuterClass.NumberRequest;
import ru.otus.numbers.NumberGeneratorOuterClass.NumberResponse;

@Slf4j
public class NumbersServer {
    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new NumberGeneratorImpl())
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.error("*** shutting down gRPC server since JVM is shutting down");
            try {
                NumbersServer.this.stop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Server shutdown interrupted", e);
            }
            log.error("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class NumberGeneratorImpl extends NumberGeneratorGrpc.NumberGeneratorImplBase {
        @Override
        public void generateNumbers(NumberRequest req, StreamObserver<NumberResponse> responseObserver) {
            int firstValue = req.getFirstValue();
            int lastValue = req.getLastValue();

            new Thread(() -> {
                        for (int i = firstValue + 1; i <= lastValue; i++) {
                            NumberResponse response =
                                    NumberResponse.newBuilder().setNumber(i).build();
                            responseObserver.onNext(response);
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                log.error("Thread interrupted", e);
                            }
                        }
                        responseObserver.onCompleted();
                    })
                    .start();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final NumbersServer server = new NumbersServer();
        server.start();
        server.blockUntilShutdown();
    }
}
