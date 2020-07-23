package com.revature.data;

import com.revature.beans.Offers;

public interface OfferDAO {
	
	 public static void acceptOffer(int o) {};
	 
	 public static void rejectOffer(int o) {};
	 
	 public static void retrieveOffers() {};
	 
	 public static void addOffers(Offers o) {};
	
}
