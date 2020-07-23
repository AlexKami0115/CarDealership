package com.revature.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.revature.beans.Offers;

public class UnitTest {
	
	@Test
	public void testMonthlyPaymentCalc(){
		
		// CREATING OFFER TO TEST
		Offers offer = new Offers();
		
		// TOTAL FOR CAR ALONG WITH DOWN PAYMENT
		offer.setTotal(15000);
		offer.setDownPayment(2000);
		
		// SHOULD EQUAL $1084 A MONTH, IF FINANCED FOR (12) MONTHS
		assertEquals(1084, Offers.monthlyPaymentCalc(12), 1);		
	}

}
