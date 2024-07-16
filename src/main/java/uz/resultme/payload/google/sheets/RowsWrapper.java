package uz.resultme.payload.google.sheets;

import java.util.List;


public class RowsWrapper
{
    private List<Row> row;

    public RowsWrapper(List<Row> row)
    {
        this.row = row;
    }

    public List<Row> getRow()
    {
        return row;
    }

    public void setRow(List<Row> row)
    {
        this.row = row;
    }
}


