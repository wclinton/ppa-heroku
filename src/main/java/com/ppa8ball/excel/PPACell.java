package com.ppa8ball.excel;
import java.sql.Date;


public interface PPACell
{
	public boolean IsDate();
	public boolean IsString();
	public boolean IsNumeric();
	public Date getDateValue();
	public String getStringValue();
	public int getIntValue();
	public PPACell getCellBelow();
	public PPACell getNextTopColumn();
	public int getRowIndex();
	public int getColumnIndex();
}
