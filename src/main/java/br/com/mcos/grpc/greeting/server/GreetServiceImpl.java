package br.com.mcos.grpc.greeting.server;

import br.com.mcos.greet.GreetRequest;
import br.com.mcos.greet.GreetResponse;
import br.com.mcos.greet.GreetServiceGrpc;
import br.com.mcos.greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
//        Extract the field we need
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();

//        create the response
        String result = "Hello " + firstName;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

//        send the response
        responseObserver.onNext(response);

//        complete the RPC call
        responseObserver.onCompleted();
    }
}
