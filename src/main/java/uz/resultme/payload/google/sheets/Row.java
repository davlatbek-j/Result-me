package uz.resultme.payload.google.sheets;

import java.util.List;

public class Row
{
    private List<String> value;

    public Row(List<String> value)
    {
        this.value = value;
    }

    public List<String> getValue()
    {
        return value;
    }

    public void setValue(List<String> value)
    {
        this.value = value;
    }
}
