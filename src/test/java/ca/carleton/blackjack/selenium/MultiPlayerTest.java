package ca.carleton.blackjack.selenium;

import ca.carleton.blackjack.game.Deck;
import ca.carleton.blackjack.game.entity.card.Card;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Tests surrounding multiple uesrs.
 * <p/>
 * Created by Mike on 11/8/2015.
 */
//@SeleniumTest
public class MultiPlayerTest extends AbstractSeleniumTest {

    @Test
    public void canMultiplePeopleConnect() {
        this.indexPage.connect();
        this.indexPage.setNumberPlayers(2);
        this.delay(5);
        this.indexPage.open.click();
        this.delay(5);

        // Lets connect a second player
        final WebDriver second = this.quickConnectSecondUser();
        this.delay(3);

        //START GAME FROM P1
        this.indexPage.start.click();
        this.delay(3);

        //assertThat(this.indexPage.hasText("The game is now ready to begin. Press start when ready."), is(true));
        this.indexPage.disconnect();
        this.disconnectSecondUser(second);
    }

    @Test
    //top card is KC and player1 plays KH
    public void line50() throws Exception {

        this.indexPage.connect();
        this.indexPage.setNumberPlayers(2);
        this.delay(5);
        this.indexPage.open.click();
        this.delay(5);

        // Lets connect a second player
        final WebDriver second = this.quickConnectSecondUser();
        this.delay(3);

        //START GAME FROM P1
        this.indexPage.start.click();
        this.delay(3);

        //CREATE CARD
        Card card = null;

////        Deck deck = new Deck();
////        deck.reset();
//
//        //MIMICK DRAW
//        //card = deck.draw();
//
//        //SET CARD TO BE SPECIFIC
//        this.delay(3);
//        //card = deck.getSpecificCard("k", "hearts");
//        this.indexPage.setPlayerCards("hearts");
//        this.delay(3);

        //top card is KC

        //player1 plays KH

        //assertThat(this.indexPage.hasText("The game is now ready to begin. Press start when ready."), is(true));
        this.indexPage.disconnect();
        this.disconnectSecondUser(second);

    }

    @Test
    public void canSecondPlayerDisconnectInProgress() {
        this.indexPage.connect();
        this.indexPage.setNumberPlayers(2);
        this.indexPage.open.click();
        this.delay(3);
        final WebDriver second = this.quickConnectSecondUser();
        // We're now ready to play
        this.indexPage.start.click();
        this.delay(3);
        // Lets disconnect the second user.
        this.disconnectSecondUser(second);
        assertThat(this.indexPage.hasText("has disconnected from the game. He will be replaced by an AI"), is(true));
        this.indexPage.stay.click();
        assertThat(this.indexPage.hasText("To start another round, press the start button."), is(true));
        this.indexPage.disconnect();
    }

    @Test
    public void canAdminDisconnectGracefully() {
        this.indexPage.connect();
        this.indexPage.setNumberPlayers(2);
        this.indexPage.open.click();
        final WebDriver second = this.quickConnectSecondUser();
        // We're now ready to play
        this.indexPage.start.click();
        // Lets disconnect the admin
        this.indexPage.disconnect();
        final List<WebElement> result = second.findElements(By.xpath("//*[contains(text(),'" + "The administrator has left. Current sessions will be disconnected" + "')]"));
        assertThat(result.size(), is(greaterThan(0)));
        this.disconnectSecondUser(second);
    }

}
