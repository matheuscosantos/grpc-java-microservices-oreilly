package br.com.mcos.grpc.calculator.server;

import br.com.mcos.proto.calculator.CalculatorServiceGrpc;
import br.com.mcos.proto.calculator.SumRequest;
import br.com.mcos.proto.calculator.SumResponse;
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
}
