package miv.item;

public class MenuItemInfo {
	String title;
	String name;
	int img;
	
	
	
	public MenuItemInfo(String title, String name, int img) {
		this.title = title;
		this.name = name;
		this.img = img;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getImg() {
		return img;
	}
	public void setImg(int img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
