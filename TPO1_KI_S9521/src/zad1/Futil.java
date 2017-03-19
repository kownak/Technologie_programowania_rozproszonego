package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by ikownacki on 04.03.2017.
 */
public class Futil extends SimpleFileVisitor<Path> {
    private FileChannel outputFileChannel;

    private Charset charsAtInput = Charset.forName("Cp1250");
    private Charset charsAtOutput = Charset.forName("UTF-8");

    public Futil() {
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        FileChannel inputFileChannel = FileChannel.open(file);
        this.doThings(inputFileChannel, (int) attrs.size());

        return CONTINUE;

    }

    public static void processDir(String dirName, String resultFileName) {
        Path startFolder = Paths.get(dirName);
        Path resultFile = Paths.get(resultFileName);

        try {
            Futil futil = new Futil();
            futil.outputFileChannel = FileChannel.open(resultFile, EnumSet.of(APPEND, CREATE));
            futil.outputFileChannel.truncate(0);

            Files.walkFileTree(startFolder, futil);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doThings(FileChannel inputFileChannel, int attrsSize) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(attrsSize);

        inputFileChannel.read(byteBuffer);
        byteBuffer.flip();

        CharBuffer charBuffer = charsAtInput.decode(byteBuffer);
        ByteBuffer byteBufferToOutput = charsAtOutput.encode(charBuffer);

        outputFileChannel.write(byteBufferToOutput);

    }

}
