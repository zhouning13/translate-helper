package club.gorpg.helper.model;

import java.io.Serializable;

public class TranslateResult implements Serializable {
	private static final long serialVersionUID = -1669161574660128947L;

	private int allTotal;
	private int allForeignWords;
	private int allTransWords;
	private int allTrans;

	private int total;
	private int trans;
	private int foreignWords;
	private int transWords;

	public TranslateResult() {
		super();
	}

	public TranslateResult(int allTotal, int allForeignWords, int allTransWords, int allTrans, int total, int trans,
			int foreignWords, int transWords) {
		super();
		this.allTotal = allTotal;
		this.allForeignWords = allForeignWords;
		this.allTransWords = allTransWords;
		this.allTrans = allTrans;
		this.total = total;
		this.trans = trans;
		this.foreignWords = foreignWords;
		this.transWords = transWords;
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

	public int getAllForeignWords() {
		return allForeignWords;
	}

	public void setAllForeignWords(int allForeignWords) {
		this.allForeignWords = allForeignWords;
	}

	public int getAllTransWords() {
		return allTransWords;
	}

	public void setAllTransWords(int allTransWords) {
		this.allTransWords = allTransWords;
	}

	public int getAllTrans() {
		return allTrans;
	}

	public void setAllTrans(int allTrans) {
		this.allTrans = allTrans;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTrans() {
		return trans;
	}

	public void setTrans(int trans) {
		this.trans = trans;
	}

	public int getForeignWords() {
		return foreignWords;
	}

	public void setForeignWords(int foreignWords) {
		this.foreignWords = foreignWords;
	}

	public int getTransWords() {
		return transWords;
	}

	public void setTransWords(int transWords) {
		this.transWords = transWords;
	}

}
