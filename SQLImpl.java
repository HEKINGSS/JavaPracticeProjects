package com.king.isql;

import java.util.List;

import com.king.model.CountBookInfo;
import com.king.model.ImportBookInfo;
import com.king.model.ReturnBookInfo;
import com.king.model.SaleBookInfo;

public interface SQLImpl {
	
	public abstract List<ImportBookInfo> IAllbook();
	public abstract ImportBookInfo IFindBookByName(String bookName);
	public abstract boolean IUpdate(String bookName, int iBookNum);
	
	public abstract List<ReturnBookInfo> RAllbook();
	public abstract ReturnBookInfo RFindBookByName(String bookName);
	public abstract boolean RUpdate(String bookName, int rBookNum);

	public abstract List<SaleBookInfo> SAllbook();
	public abstract SaleBookInfo SFindBookByName(String bookName);
	public abstract boolean SUpdate(String bookName, int sBookNum);
	
	public abstract List<CountBookInfo> CAllbook();
}
