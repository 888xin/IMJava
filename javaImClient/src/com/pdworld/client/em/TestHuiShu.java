package com.pdworld.client.em;

public class TestHuiShu {
    public static void main(String args[]) {
        TestHuiShu a = new TestHuiShu();
        String in = "123321";
        if (a.getchar(in))
            System.out.println(in+"是一个回数");
        else
            System.out.println(in+"不是一个回数");
    }

    public boolean getchar(String in) {
        for (int i = 0; i < in.length() / 2; i++)
            if (in.charAt(i) != in.charAt(in.length() - 1 - i))
                return false;
        return true;
    }
}
