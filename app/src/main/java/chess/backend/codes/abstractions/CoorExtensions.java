package chess.backend.codes.abstractions;

import java.util.HashMap;
import java.util.Map;

public class CoorExtensions {
    private static final Map<Character, Integer> dictionary = new HashMap<>();

    static {
        dictionary.put('a', 1);
        dictionary.put('b', 2);
        dictionary.put('c', 3);
        dictionary.put('d', 4);
        dictionary.put('e', 5);
        dictionary.put('f', 6);
        dictionary.put('g', 7);
        dictionary.put('h', 8);
    }


    public static int get(char f) {
        return dictionary.getOrDefault(f, -1);
    }

    public static char get(int num) {
        for (Map.Entry<Character, Integer> entry : dictionary.entrySet()) {
            if (entry.getValue() == num) {
                return entry.getKey();
            }
        }
        return ' ';
    }


    public static String get(int x, int y) {
        char f = get(x);
        return String.valueOf(f)  + String.valueOf(y) ;
    }


    public static String get(ICoordinate from, ICoordinate to) {
        String v1 = get(from.get_X(),from.get_Y());
        String v2 = get(to.get_X(),to.get_Y());
        return  v1+"-"+v2;

    }


    public static int[] get(String f3) {
        char f = f3.charAt(0);
        int n = Character.getNumericValue(f3.charAt(1));
        int a = get(f);
        return  new int[]{a,n};

    }}