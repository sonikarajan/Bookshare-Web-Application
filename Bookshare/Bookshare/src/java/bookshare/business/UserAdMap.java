package bookshare.business;

import java.io.Serializable;

public class UserAdMap implements Serializable {

	int mapId;
	String bookName;
	int price;
	String name;
	String status;
	int userId;
        int adId;

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public UserAdMap() {
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAdId() {
		return adId;
	}

}
