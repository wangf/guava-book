package bbejeck.guava.chapter7.subscriber;

import bbejeck.guava.chapter7.EventBusTestBase;
import bbejeck.guava.chapter7.subscriber.complex.TradeBuyAuditor;
import bbejeck.guava.chapter7.subscriber.complex.TradeSellAuditor;
import bbejeck.guava.chapter7.subscriber.simple.SimpleTradeAuditor;

import com.google.common.eventbus.EventBus;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * User: Bill Bejeck
 * Date: 4/28/13
 * Time: 8:49 PM
 */
public class TradeBuySellAuditorTest extends EventBusTestBase  {

    private TradeBuyAuditor buyAuditor;
    private TradeSellAuditor sellAuditor;
    private EventBus eventBus;
    private SimpleTradeAuditor tradeAuditor;
    @Before
    public void setUp(){
        eventBus = getEventBus();
        buyAuditor = new TradeBuyAuditor(eventBus);
        sellAuditor = new TradeSellAuditor(eventBus);
        tradeAuditor = new SimpleTradeAuditor(eventBus);
    }

    @Test
    public void sellOnlyTest(){
        eventBus.post(sellEventBuilder().build());
        assertThat(sellAuditor.getSellEvents().size(),is(1));
        assertThat(tradeAuditor.getTradeEvents().size(),is(1));
        assertThat(buyAuditor.getBuyEvents().isEmpty(),is(true));
    }

    @Test
    public void buyOnlyTest(){
        eventBus.post(buyEventBuilder().build());
        assertThat(sellAuditor.getSellEvents().isEmpty(),is(true));
        assertThat(buyAuditor.getBuyEvents().size(),is(1));
        assertThat(tradeAuditor.getTradeEvents().size(),is(1));
    }
}
