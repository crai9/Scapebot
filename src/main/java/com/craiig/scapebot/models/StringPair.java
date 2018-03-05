package com.craiig.scapebot.models;

/**
 * Created by Craig on 05/03/2018, 03:06.
 */
public class StringPair
{
    private final String key;
    private final String value;

    public StringPair(String aKey, String aValue)
    {
        key   = aKey;
        value = aValue;
    }

    public String key()   { return key; }
    public String value() { return value; }
}
