package sc.jfort.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

    public static void main(String[] args) {

        List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP");

        for (String l : languages){
            System.out.println("Хочу выучить " + l);
        }
    }
}