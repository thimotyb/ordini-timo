package it.corso.ordini;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import it.corso.ordini.bo.OrderBOImpl;
import it.corso.ordini.bo.OrderDAO;
import it.corso.ordini.dao.OrderDAOImpl;
import it.corso.ordini.dto.Order;
import it.corso.ordini.view.OrderBO;

/***
 * 1) Inserimento nuovo ordine (positivo)
 * 2) Inserimento nuovo ordine (negativo)
 * 3) Annullamento ordine esistente (positivo)
 * 4) Annullamento ordine esistente (negativo)
 * ....
 * 
 * @author thimo
 *
 */
public class OrdiniTest {

	@Mock
	OrderDAO orderDAO;
	private OrderBO orderBO;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		orderBO = new OrderBOImpl();
		((OrderBOImpl)orderBO).setOrderDAO(orderDAO);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldCreateNewOrder() {
		

		
		Order myOrder = new Order();
		myOrder.setCustomerName("Thimoty");
		myOrder.setProductName("PC");
		myOrder.setQty(1);		
		
		// Mockito Expectations
		when(orderDAO.save(any(Order.class))).thenReturn(1234);
		when(orderDAO.read(1234)).thenReturn(myOrder);
		
		String customerName = "Thimoty";
		String productName = "PC";
		int qty = 1;
		Date delivery = new Date();
		
		Order op = ((OrderBOImpl)orderBO).prepareOrder(customerName, delivery, productName, qty);
		
		assertTrue("La delivery e il cusotomer non è nulla", op.getDelivery()!=null && op.getCustomerName()!=null);
		
		int idOrdine = orderBO.createOrder(customerName, productName, qty, delivery);
		
		// Assert First!
		assertTrue("Ho un numero d'ordine > 0", idOrdine > 0);
		
		// Triangulate: Riprendo l'ordine
		Order ordinePrelevato = orderBO.getOrder(idOrdine);
		assertNotNull(ordinePrelevato);
		assertEquals("E' il mio ordine!", "Thimoty", ordinePrelevato.getCustomerName());
		
		// Controlli Spy
		verify(orderDAO).save(any(Order.class));
		verify(orderDAO).read(1234);
		
	}
	
	
	@Test
	public void shouldCancelOrder() {
		
		int orderId = 1234;
		
		Order myOrder = new Order();
		when(orderDAO.read(orderId)).thenReturn(myOrder);
		when(orderDAO.update(myOrder)).thenReturn(myOrder);
		
		boolean canceled = orderBO.cancelOrder(orderId);
		
		// Assert First!
		assertTrue("Son riuscito ad annullare", canceled);
		
		// Triangulate
		Order checkCanceled = orderBO.getOrder(orderId);
		assertEquals("E' annullato", "CANCELED", checkCanceled.getProductName());
		
	}
	

}
