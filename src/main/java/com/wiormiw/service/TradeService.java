package com.wiormiw.service;

import com.wiormiw.entity.TradeRequest;
import com.wiormiw.entity.UserPokemon;
import com.wiormiw.exception.PokemonException;
import com.wiormiw.repository.FriendsRepository;
import com.wiormiw.repository.TradeRequestRepository;
import com.wiormiw.repository.UserPokemonRepository;
import com.wiormiw.util.NotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TradeService {
    @Inject
    NotificationService notificationService;
    @Inject
    TradeRequestRepository tradeRequestRepository;
    @Inject
    FriendsRepository friendsRepository;
    @Inject
    UserPokemonRepository userPokemonRepository;

    public void requestTrade(Long requesterId, Long targetId, Long requesterPokemonId, Long targetPokemonId) {
        if (!friendsRepository.areFriends(requesterId, targetId)) {
            throw new PokemonException("You must be friends to trade!");
        }
        if (ownsPokemon(requesterId, requesterPokemonId)) {
            throw new PokemonException("You don’t own the Pokémon you’re offering!");
        }
        if (ownsPokemon(targetId, targetPokemonId)) {
            throw new PokemonException("The target doesn’t own the requested Pokémon!");
        }

        TradeRequest trade = new TradeRequest();
        trade.requesterId = requesterId;
        trade.targetId = targetId;
        trade.requesterPokemonId = requesterPokemonId;
        trade.targetPokemonId = targetPokemonId;
        trade.status = "PENDING";
        trade.persist();
        notificationService.sendNotification(targetId, "New trade request from user " + requesterId);
    }

    public void approveTrade(Long tradeId, Long approvingUserId, boolean approved) {
        TradeRequest trade = tradeRequestRepository.findById(tradeId);
        if (trade == null || !trade.targetId.equals(approvingUserId)) {
            throw new PokemonException("Trade not found or you’re not authorized to approve it!");
        }
        if (!trade.status.equals("PENDING")) {
            throw new PokemonException("Trade is no longer pending!");
        }

        if (approved) {
            if (ownsPokemon(trade.requesterId, trade.requesterPokemonId) || ownsPokemon(trade.targetId, trade.targetPokemonId)) {
                trade.status = "CANCELLED";
                trade.persist();
                notificationService.sendNotification(trade.requesterId, "Trade cancelled: One of the Pokémon is no longer available.");
                cancelConflictingTrades(trade.targetId, trade.targetPokemonId);
                return;
            }

            swapPokemon(trade.requesterId, trade.targetId, trade.requesterPokemonId, trade.targetPokemonId);
            trade.status = "ACCEPTED";
            notificationService.sendNotification(trade.requesterId, "Trade accepted!");
            notificationService.sendNotification(trade.targetId, "Trade completed!");
            cancelConflictingTrades(trade.targetId, trade.targetPokemonId);
        } else {
            trade.status = "REJECTED";
            notificationService.sendNotification(trade.requesterId, "Trade rejected.");
        }
        trade.persist();
    }

    public void cancelTrade(Long tradeId, Long requesterId) {
        TradeRequest trade = tradeRequestRepository.findById(tradeId);
        if (trade == null || !trade.requesterId.equals(requesterId)) {
            throw new PokemonException("Trade not found or you’re not authorized to cancel it!");
        }
        if (!trade.status.equals("PENDING")) {
            throw new PokemonException("Trade is no longer pending!");
        }
        trade.status = "CANCELLED";
        trade.persist();
        notificationService.sendNotification(trade.targetId, "Trade request from user " + requesterId + " was cancelled.");
    }

    private boolean ownsPokemon(Long userId, Long pokemonId) {
        return !userPokemonRepository.isOwningPokemon(userId, pokemonId);
    }

    private void swapPokemon(Long userAId, Long userBId, Long pokemonAId, Long pokemonBId) {
        userPokemonRepository.deleteToSwap(userAId, pokemonAId);
        userPokemonRepository.deleteToSwap(userBId, pokemonBId);
        UserPokemon userAOwnsB = new UserPokemon();
        userAOwnsB.userId = userAId;
        userAOwnsB.pokemonId = pokemonBId;
        userPokemonRepository.save(userAOwnsB);
        UserPokemon userBOwnsA = new UserPokemon();
        userBOwnsA.userId = userBId;
        userBOwnsA.pokemonId = pokemonAId;
        userPokemonRepository.save(userBOwnsA);
    }

    private void cancelConflictingTrades(Long userId, Long pokemonId) {
        List<TradeRequest> conflictingTrades = tradeRequestRepository.findConflictingTrades(userId, pokemonId);
        for (TradeRequest trade : conflictingTrades) {
            trade.status = "CANCELLED";
            trade.persist();
            notificationService.sendNotification(trade.requesterId, "Trade cancelled: The requested Pokémon is no longer available.");
        }
    }
}
