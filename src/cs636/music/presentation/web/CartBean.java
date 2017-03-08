package cs636.music.presentation.web;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import cs636.music.domain.*;

public class CartBean{
	
	private int cartid = 1;
	HashSet<LineItem> items;
	
	public CartBean()
	{
		items = new HashSet<LineItem>();
	}
	
	public int getCartId(){
		return cartid;
	}
	
	public void setUserId(int cartid){
		this.cartid = cartid;
	}
	
	public void addItem(LineItem item) {
		int prodId = (int) item.getProduct().getId();
		int quantity = item.getQuantity();
		for (LineItem l : items) {

			if (l.getProduct().getId() == prodId) {
				l.setQuantity(quantity);
				return;
			}
		}
		items.add(item);
	}
	
	
	public void removeItem(Product product) {
		int prodId = (int) product.getId(); 
		for (LineItem l : items) {
			if (l.getProduct().getId() == prodId) {
				items.remove(l);
				return;
			}
		}
	}
	public BigDecimal getTotalAmount() {
		BigDecimal total= new BigDecimal(0.00);
		for (LineItem l : items) {
			total=total.add(l.calculateItemTotal());
			}
		
		return total;
	}
	public Set<LineItem> getItems() {
		return items;
	}
}