package WordFrequency;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class TermFrequency {
    private Map<String, Integer> words;

    public TermFrequency(InputStream inputStream, String[] stopWords){
        words = new TreeMap<>();
        List<String> stopwords = Arrays.asList(stopWords);
        List<String> uncleaned = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        bufferedReader.lines()
                        .filter(row -> row.compareTo("") != 0)
                        .forEach(paragraph -> {
                            String[] parts = paragraph.split("\\s+");
                            uncleaned.addAll(Arrays.asList(parts));
                        });

        uncleaned.stream()
                .map(word -> word.toLowerCase())
                .filter(word -> word.compareTo("") != 0)
                .map(word -> {
                    char last = word.charAt(word.length()-1);
                    if(last == '.' || last == ',')
                        return word.substring(0,word.length()-1);
                    return word;
                })
                .filter(word -> word.compareTo("") != 0)
                .filter(word -> !stopwords.contains(word))
                .forEach(word -> {
                    if(!words.containsKey(word))
                        words.put(word, 0);
                    int value = words.get(word) + 1;
                    words.replace(word, value);
                });
    }

    public int countTotal() {
        return words.values().stream()
                        .mapToInt(Integer::intValue)
                        .sum();
    }

    public int countDistinct() {
        return words.size();
    }

    public List<String> mostOften(int k) {
        return words.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(k)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
    }
}
