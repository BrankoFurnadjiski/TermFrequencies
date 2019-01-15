package WordFrequency;

import java.util.*;

public class Paragraph {
    private Map<String, Integer> words;

    public Paragraph(String row, List<String> stopWords){
        words = new HashMap<>();

        String[] parts = row.split("\\s+");

        Arrays.stream(parts)
                .map(String::toLowerCase)
                .map(string -> {
                    String s = string;
                    if(s.length() == 0)
                        return s;
                    if(!Character.isLetter(s.charAt(s.length()-1)) && !Character.isDigit(s.charAt(s.length()-1)))
                        s = s.substring(0, s.length()-1);
                    if(s.length() == 0)
                        return s;
                    if(!Character.isLetter(s.charAt(0)) && !Character.isDigit(s.charAt(0)))
                        s = s.substring(1);
                    return s;
                }).filter(string -> string.compareTo("") != 0)
                .filter(string -> !stopWords.contains(string))
                .forEach(string -> {
                    if(!words.containsKey(string))
                        words.put(string, 0);
                    int newValue = words.get(string) + 1;
                    words.put(string, newValue);
        });

    }

    public Map<String, Integer> getWords() {
        return this.words;
    }

    public int getWordsInParagraph() {
        return words.values().stream().mapToInt(Integer::intValue).sum();
    }
}
