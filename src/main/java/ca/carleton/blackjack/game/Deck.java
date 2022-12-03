package ca.carleton.blackjack.game;

import ca.carleton.blackjack.game.entity.card.Card;
import ca.carleton.blackjack.game.entity.card.Rank;
import ca.carleton.blackjack.game.entity.card.Suit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.shuffle;

/**
 * Represents a card deck.
 * <p/>
 * Created by Mike on 11/3/2015.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Deck {

    private static final Logger LOG = LoggerFactory.getLogger(Deck.class);

    private List<Card> cards;

    @PostConstruct
    public void init() {
        this.cards = new LinkedList<>();

        // Remove ACE_LOW by default.
        final List<Rank> ranks = Arrays.asList(Rank.values()).stream()
                .filter(rank -> rank != Rank.ACE_LOW)
                .collect(Collectors.toList());

        for (final Rank rank : ranks) {
            for (final Suit suit : Suit.values()) {
                this.cards.add(new Card(rank, suit, false));
            }
        }

        if (this.cards.size() != 52) {
            LOG.error("ERROR - Invalid amount of cards created.");
            throw new IllegalStateException("Illegal amount of cards.");
        }

        shuffle(this.cards);
    }

    /**
     * Reset the deck.
     */
    public void reset() {
        this.init();
    }

    /**
     * Draw from the deck.
     *
     * @return the card, or null if no cards are left.
     */
    public Card draw() {
        return this.cards.size() >= 1 ? this.cards.remove(0) : null;
    }

    public Card getCard(String rankSuit) {
        System.out.println("get a card function has: " + rankSuit);

        String rank = "";

        if(rankSuit.length() == 2) {
            rank = rankSuit.substring (0, 1);
        } else if(rankSuit.length() == 3) {
            rank = rankSuit.substring (0, 2);
        }

        String suit = String.valueOf(rankSuit.charAt(rankSuit.length() - 1));

        //DETERMINE SUIT
        if(suit.equalsIgnoreCase("h")) {
            suit = "hearts";
        } else if(suit.equalsIgnoreCase("c")) {
            suit = "clubs";
        } else if(suit.equalsIgnoreCase("d")) {
            suit = "diamonds";
        } else if(suit.equalsIgnoreCase("s")) {
            suit = "spades";
        }


        //FOR LOOP
        //LOOK FOR MATCHING RANK AND SUIT
        for(int i =0; i<cards.size(); i++) {
            if (cards.get(i).getSuit().toString().equalsIgnoreCase(suit) && cards.get(i).getRank().getHtml().equalsIgnoreCase(rank)) {
               return cards.get(i);
            }
        }
        return null;
    }

}
