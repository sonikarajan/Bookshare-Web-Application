package bookshare.business;

import java.io.Serializable;

public class Ad implements Serializable {

	private int adId;
	private String bookName;
	private String subject;
	private String url;
	private String image;
	private int price;
	private String rentOrSell;
	private String status;
	private int ownerId;

	public Ad() {

	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getRentOrSell() {
		return rentOrSell;
	}

	public void setRentOrSell(String rentOrSell) {
		this.rentOrSell = rentOrSell;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}
