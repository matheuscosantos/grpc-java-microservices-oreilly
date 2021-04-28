package br.com.mcos.grpc.calculator.client;

import br.com.mcos.proto.calculator.CalculatorServiceGrpc;
import br.com.mcos.proto.calculator.SumRequest;
import br.com.mcos.proto.calculator.SumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        SumRequest request = SumRequest.newBuilder()
                .setFirstNumber(10)
                .setSecondNumber(25)
                .build();

        SumResponse response = stub.sum(request);

        System.out.println(request.getFirstNumber() + " + " + request.getSecondNumber() + " = " + response.getSumResult());
    }
}
