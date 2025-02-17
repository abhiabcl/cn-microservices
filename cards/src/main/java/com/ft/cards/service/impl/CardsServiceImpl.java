package com.ft.cards.service.impl;

import com.ft.cards.constants.CardsConstants;
import com.ft.cards.dto.CardsDto;
import com.ft.cards.entity.Cards;
import com.ft.cards.exception.ResourceNotFoundException;
import com.ft.cards.mapper.CardsMapper;
import com.ft.cards.repository.CardsRepository;
import com.ft.cards.service.ICardsService;
import com.ft.cards.exception.CardAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);
        if (cards.isPresent()) {
            throw new CardAlreadyExistsException("Card is already register with given Mobile Number "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {

        Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);
        if (cards.isPresent()) {
            return CardsMapper.mapToCardsDto(cards.get(), new CardsDto());
        } else
            throw new ResourceNotFoundException("Card", "mobileNumber", mobileNumber);

    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));

        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return true;
    }
}
