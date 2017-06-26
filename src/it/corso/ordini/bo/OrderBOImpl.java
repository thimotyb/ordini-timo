package it.corso.ordini.bo;

import java.util.Date;

import it.corso.ordini.dto.Order;
import it.corso.ordini.view.OrderBO;

public class OrderBOImpl implements OrderBO {

	private OrderDAO orderDAO;
	
	public Order prepareOrder(String customerName, Date delivery, String productName, int qty) {
		Order o = new Order();
		o.setCustomerName(customerName);
		// TODO : la delivery non può essere precedente alla data odierna
		o.setDelivery(delivery);
		o.setProductName(productName);
		o.setQty(qty);
		return o;
	}
	
	@Override
	public int createOrder(String customerName, String productName, int qty, Date delivery) {
		Order o = prepareOrder(customerName, delivery, productName, qty);
		//return 1234;
		return orderDAO.save(o);
	}

	@Override
	public Order getOrder(int idOrdine) {
		return orderDAO.read(idOrdine);
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public boolean cancelOrder(int idOrdine) {
		
		// Obvious implementation (non tiene conto degli errori)
		Order o = orderDAO.read(idOrdine);
		o.setProductName("CANCELED");
		o = orderDAO.update(o);
		if (o!=null) {
			return true;
		} else {
			return false;
		}
		
	}

}
