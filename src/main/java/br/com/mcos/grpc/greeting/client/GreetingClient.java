package br.com.mcos.grpc.greeting.client;

import br.com.mcos.greet.*;
import br.com.mcos.proto.dummy.DummyServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC client");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        System.out.println("Creating stub");

//        old and dummy
//        DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);

        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
//        Unary
////        created a protocol buffer greeting message
//        Greeting greeting = Greeting.newBuilder()
//                .setFirstName("Matheus")
//                .setLastName("Santos")
//                .build();
//
////        do the same for a GreetRequest
//        GreetRequest greetRequest = GreetRequest.newBuilder()
//                .setGreeting(greeting)
//                .build();
//
////        call the RPC and get back a GreetResponse (protocol buffers)
//        GreetResponse greetResponse = greetClient.greet(greetRequest);
//
//        System.out.println(greetResponse.getResult());


//        Server Streaming
        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Stephane"))
                .build();

//        we stream the responses (in a blocking manner)
        greetClient.greetManyTimes(greetManyTimesRequest)
                .forEachRemaining(greetManyTimesResponse -> {
                    System.out.println(greetManyTimesResponse.getResult());
        });

//        do something
        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
