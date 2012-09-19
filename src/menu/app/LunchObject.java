package menu.app;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a lunch at the restaurant.
 * 
 * @author Oskar Ehnstrom
 * 
 */

public class LunchObject {

	private Date date; 

	private String main;
	private String vege;
	private String soup;
	private String salad;
	private String alacarte;
	private String extra;

	/**
	 *  Create the lunch for the given date.
	 *  
	 * @param date java.util.Date
	 */
	public LunchObject(Date date) {
		this.date = date;
		this.main = "";
		this.vege = "";
		this.soup = "";
		this.salad = "";
		this.alacarte = "";
		this.extra = "";
	}

	/**
	 * 
	 * @return main lunch or empty string
	 */
	public String getMain() {
		return main;
	}

	/**
	 * Set the main
	 */
	public void setMain(String main) {
		this.main = main;
	}

	/**
	 * 
	 * @return vegetarian of empty string
	 */
	public String getVege() {
		return vege;
	}

	/**
	 * Set the vegetarian
	 */
	public void setVege(String vege) {
		this.vege = vege;
	}

	/**
	 * 
	 * @return soup or empty string
	 */
	public String getSoup() {
		return soup;
	}

	/**
	 * Set the soup
	 */
	public void setSoup(String soup) {
		this.soup = soup;
	}

	/**
	 * 
	 * @return salad or empty string
	 */
	public String getSalad() {
		return salad;
	}

	/**
	 * Set the salad
	 */
	public void setSalad(String salad) {
		this.salad = salad;
	}

	/**
	 * 
	 * @return ala carte or empty string
	 */
	public String getAlacarte() {
		return alacarte;
	}

	/**
	 * Set alacarte
	 */
	public void setAlacarte(String alacarte) {
		this.alacarte = alacarte;
	}

	/**
	 * 
	 * @return any extras as a string or empty string
	 */
	public String getExtra() {
		return extra;
	}

	/**
	 * Set any extra information
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}

	/**
	 * Return the date of the menu
	 * 
	 * @return Date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Return the string formatting of the date
	 * 
	 * @return String of date in SQL format
	 */
	public String getDateInSQL() {
		java.sql.Date sqlDate = new java.sql.Date(this.date.getTime());
		return sqlDate.toString();
	}

	/**
	 * Returns a string with the dd.mm format of the date.
	 * 
	 * @return
	 */
	public String getDateShort() {
		// TODO: Fix deprecated methods.
		return this.date.getDate() + "." + this.date.getMonth();
	}

	public String getMenuAsString() {
		return this.main + "\n" + this.vege + "\n" + this.salad + "\n"
				+ this.soup + "\n" + this.alacarte + "\n" + this.extra;
	}
}
