package me.vexinglemons.hexicon.util;

public class Methods {
    public static int findIndex(String array[], String t)
    {

        if (array == null) {
            return -1;
        }

        int length = array.length;
        int i = 0;

        while (i < length) {
            if (array[i].equalsIgnoreCase(t))
                {
                    return i;
                }
            else
                {
                    i = i + 1;
                }

        }
        return -1;
    }
}
