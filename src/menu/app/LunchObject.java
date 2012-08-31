package menu.app;

import java.sql.Date;

public class LunchObject {

	private Date date; // SQL type date
	private String main;
	private String vege;
	private String soup;
	private String salad;
	private String alacarte;
	private String extra;

	public LunchObject(Date date, String main, String vege, String soup, String salad, String alacarte){
		this.date = date;
		this.main = main;
		this.vege = vege;
		this.soup = soup;
		this.salad = salad;
		this.alacarte = alacarte;
	}
	
	public LunchObject(String extra){
		this.extra = extra;
	}

	public void setExtra(String extra){
		this.extra = extra;
	}

	public String getMain() {
		return main;
	}

	public String getVege() {
		return vege;
	}

	public String getSoup() {
		return soup;
	}

	public String getSalad() {
		return salad;
	}

	public String getAlacarte() {
		return alacarte;
	}

	public String getExtra() {
		return extra;
	}

	public String getDate() {
		return this.date.toString();
	}
}
