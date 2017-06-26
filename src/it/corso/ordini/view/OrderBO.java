package it.corso.ordini.view;

import java.util.Date;

import it.corso.ordini.dto.Order;

public interface OrderBO {

	int createOrder(String customerName, String productName, int qty, Date delivery);

	Order getOrder(int idOrdine);
	
	/**
	 * 1) Read dell'idOrdine e ritorna un oggetto
	 * 2) Nell'oggetto metti "CANCELED" nel prodotto
	 * 3) Fai update dell'oggetto
	 * 4) se riesce update ritorna true
	 * @param idOrdine
	 * @return
	 */
	boolean cancelOrder(int idOrdine);

}
