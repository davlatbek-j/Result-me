package uz.resultme.exception;

import java.io.IOException;

public class PhotoNotFoundExcpetion extends RuntimeException
{
    public PhotoNotFoundExcpetion(String message)
    {
        super(message);
    }
}
