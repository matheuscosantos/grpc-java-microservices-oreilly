package br.com.mcos.grpc.calculator.server;

import br.com.mcos.calculator.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
        SumResponse sumResponse = SumResponse.newBuilder()
                .setSumResult(request.getFirstNumber() + request.getSecondNumber())
                .build();

        responseObserver.onNext(sumResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void primeNumberDecomposition(PrimeNumberDecompositionRequest request, StreamObserver<PrimeNumberDecompositionResponse> responseObserver) {
        Integer number = request.getNumber();
        Integer divisor = 2;

        while (number > 1) {
            if (number % divisor == 0) {
                number = number / divisor;
                responseObserver.onNext(PrimeNumberDecompositionResponse.newBuilder()
                        .setPrimeFactor(divisor)
                        .build());
            } else {
                divisor = divisor + 1;
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ComputeAverageRequest> computeAverage(StreamObserver<ComputeAverageResponse> responseObserver) {

        StreamObserver<ComputeAverageRequest> requestObserver = new StreamObserver<ComputeAverageRequest>() {
            //            running sum and count
            int sum = 0;
            int count = 0;

            @Override
            public void onNext(ComputeAverageRequest value) {
                sum += value.getNumber();
                count += 1;
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                double average = (double) sum / count;
                responseObserver.onNext(
                        ComputeAverageResponse.newBuilder()
                                .setAverage(average)
                                .build()
                );
                responseObserver.onCompleted();
            }
        };

        return requestObserver;
    }

    @Override
    public StreamObserver<FindMaximumRequest> findMaximum(StreamObserver<FindMaximumResponse> responseObserver) {
        return new StreamObserver<FindMaximumRequest>() {

            int currentMaximum = 0;

            @Override
            public void onNext(FindMaximumRequest value) {
                int currentNumber = value.getNumber();

                if (currentNumber > currentMaximum) {
                    currentMaximum = currentNumber;
                    responseObserver.onNext(
                            FindMaximumResponse.newBuilder()
                                    .setMaximum(currentNumber)
                                    .build()
                    );
                } else {
                    // nothing
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onCompleted();
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(
                        FindMaximumResponse.newBuilder()
                                .setMaximum(currentMaximum)
                                .build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void squareRoot(SquareRootRequest request, StreamObserver<SquareRootResponse> responseObserver) {
        // Extract information from Request as it is received.
        int inputNumber = request.getNumber();

        if(inputNumber > 0) {
            // Send back the response
            double squareRootNumber = Math.sqrt(inputNumber);
            responseObserver.onNext(SquareRootResponse.newBuilder()
                    .setNumberRoot(squareRootNumber).build());
        } else {
            // Send back the response not possible.
            responseObserver
                    .onError(Status.INVALID_ARGUMENT.withDescription("Number is Not positive.").asRuntimeException());
        }
        // complete the call
        responseObserver.onCompleted();

    }
}
