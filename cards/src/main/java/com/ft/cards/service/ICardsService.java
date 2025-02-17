package com.ft.cards.service;

import com.ft.cards.dto.CardsDto;

import javax.smartcardio.Card;

public interface ICardsService {
    void createCard(String mobileNumber);
    CardsDto fetchCard(String mobileNumber);
    boolean deleteCard(String mobileNumber);
    boolean updateCard(CardsDto cardsDto);
}
