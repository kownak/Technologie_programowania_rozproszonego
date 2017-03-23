package zad1;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Created by ikownacki on 23.03.2017.
 */
public class MFileVisitor extends SimpleFileVisitor<Path> {

    private List<Map<String,String>> dictionariesMapsList;

    public MFileVisitor() {
        this.dictionariesMapsList = new ArrayList<>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        List<String> fileLines = new ArrayList<>();

        Files.lines(file)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .forEach(fileLines::add);

        Map<String,String> dictionaryMap = new HashMap<>();

        for(String line :fileLines){
            String[] valueKay = line.split(":");
            dictionaryMap.put(valueKay[0],valueKay[1]);
        }

        dictionariesMapsList.add(dictionaryMap);

        return CONTINUE;
    }

    public List<Map<String, String>> getDictionariesMapsList() {
        return dictionariesMapsList;
    }
}
