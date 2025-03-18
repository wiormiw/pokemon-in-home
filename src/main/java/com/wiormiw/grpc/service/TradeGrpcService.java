package com.wiormiw.grpc.service;

import com.wiormiw.exception.PokemonException;
import com.wiormiw.service.TradeService;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;

@GrpcService
public class TradeGrpcService extends TradeServiceGrpc.TradeServiceImplBase {
    @Inject
    TradeService tradeService;

    @Override
    public void requestTrade(TradeRequest req, StreamObserver<TradeResponse> responseObserver) {
        try {
            tradeService.requestTrade(req.getRequesterId(), req.getTargetId(), req.getRequesterPokemonId(), req.getTargetPokemonId());
            responseObserver.onNext(TradeResponse.newBuilder().setStatus("PENDING").build());
        } catch (PokemonException e) {
            responseObserver.onNext(TradeResponse.newBuilder().setStatus("ERROR").setMessage(e.getMessage()).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void approveTrade(TradeApproval req, StreamObserver<TradeResponse> responseObserver) {
        try {
            tradeService.approveTrade(req.getTradeId(), req.getTargetId(), req.getApproved());
            String status = req.getApproved() ? "ACCEPTED" : "REJECTED";
            responseObserver.onNext(TradeResponse.newBuilder().setStatus(status).build());
        } catch (PokemonException e) {
            responseObserver.onNext(TradeResponse.newBuilder().setStatus("ERROR").setMessage(e.getMessage()).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void cancelTrade(TradeCancel req, StreamObserver<TradeResponse> responseObserver) {
        try {
            tradeService.cancelTrade(req.getTradeId(), req.getRequesterId());
            responseObserver.onNext(TradeResponse.newBuilder().setStatus("CANCELLED").build());
        } catch (PokemonException e) {
            responseObserver.onNext(TradeResponse.newBuilder().setStatus("ERROR").setMessage(e.getMessage()).build());
        }
        responseObserver.onCompleted();
    }
}
