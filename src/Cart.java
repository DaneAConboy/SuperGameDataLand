/**
 * This User class only has the username field in this example.
 * <p>
 * However, in the real project, this User class can contain many more things,
 * for example, the user's shopping cart items.
 */
import java.util.*;
public class Cart {

    private ArrayList<String> cart;

    public Cart() {
    	this.cart = new ArrayList<String>(50);
    }

    public void addToCart(String title) {
    	cart.add(title);
    }
    public void addToCart(String title,int amount) {
    	for(int i = 0; i < amount; i++)
    		cart.add(title);
    }
    
    public void remove(String title) {
    	cart.remove(title);
    }
    public void remove(String title, int amount) {
    	for(int i = 0; i < amount; i++)
    		cart.remove(title);
    }
    
    public int amount(String title) {
    	int counter = 0;
    	for(String i : cart) {
    		if(i.equals(title))
    			counter++;
    	}
    	return counter;
    }
    public ArrayList<String> getCart() {
        return this.cart;
    }

}