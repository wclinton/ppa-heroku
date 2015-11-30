package com.ppa8ball.excel;

import java.sql.Date;

public interface PPACell
{
	public boolean IsDate();
	public boolean IsString();
	public boolean IsNumeric();
	public boolean hasData();
	public Date getDateValue();
	public String getStringValue();
	public int getIntValue();
	public double getDoubleValue();
	public PPACell getCellBelow();
	public PPACell getNextTopColumn();
	public PPACell getCellToRight();
	public int getRowIndex();
	public int getColumnIndex();
}
