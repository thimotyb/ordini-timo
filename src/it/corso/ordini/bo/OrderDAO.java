package it.corso.ordini.bo;

import it.corso.ordini.dto.Order;

public interface OrderDAO {

	int save(Order o);
	Order read(int orderId);
	Order update(Order o);
	int delete(int orderId);

}
