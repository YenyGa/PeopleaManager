package co.edu.udea.peopleManager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Product")
public class Product {

	@Id
	private String id;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private int amount;
	@NotNull
	private int price;

	public Product(){}

	public Product(String id, String name, String description, int amount, int price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	};
	

}
