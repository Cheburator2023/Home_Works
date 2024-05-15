package ru.otus.numbers;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import ru.otus.numbers.NumberGeneratorOuterClass.NumberRequest;
import ru.otus.numbers.NumberGeneratorOuterClass.NumberResponse;

@Slf4j
public class NumbersClient {
    private final ManagedChannel channel;
    private final NumberGeneratorGrpc.NumberGeneratorStub asyncStub;

    public NumbersClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    NumbersClient(ManagedChannel channel) {
        this.channel = channel;
        asyncStub = NumberGeneratorGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void generateNumbers(int firstValue, int lastValue) throws InterruptedException {
        CountDownLatch finishLatch = new CountDownLatch(1);
        AtomicInteger lastNumberFromServer = new AtomicInteger();

        StreamObserver<NumberResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(NumberResponse numberResponse) {
                log.info("new value:" + numberResponse.getNumber());
                lastNumberFromServer.set(numberResponse.getNumber());
            }

            @Override
            public void onError(Throwable t) {
                log.error("Error occurred: ", t);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                log.info("request completed");
                finishLatch.countDown();
            }
        };

        asyncStub.generateNumbers(
                NumberRequest.newBuilder()
                        .setFirstValue(firstValue)
                        .setLastValue(lastValue)
                        .build(),
                responseObserver);

        AtomicInteger currentValue = new AtomicInteger(0);
        for (int i = 0; i <= 50; i++) {
            int lastNumber = lastNumberFromServer.getAndSet(0);
            if (lastNumber != 0) {
                currentValue.addAndGet(lastNumber);
            }
            currentValue.incrementAndGet();
            log.info("currentValue: {}", currentValue.get());
            TimeUnit.SECONDS.sleep(1);
        }

        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            log.warn("The finish latch timed out");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NumbersClient client = new NumbersClient("localhost", 50051);
        try {
            client.generateNumbers(0, 30);
        } finally {
            client.shutdown();
        }
    }
}
