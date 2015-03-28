package bbejeck.guava.chapter8.streams;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;

import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 5/5/13
 * Time: 9:04 PM
 */
public class CharStreamsTest {

    @Test
    public void joinTest() throws Exception {
        File f1 = new File("src/main/resources/sampleTextFileOne.txt");
        File f2 = new File("src/main/resources/sampleTextFileTwo.txt");
        File f3 = new File("src/main/resources/lines.txt");
        File joinedOutput = new File("src/test/resources/joined.txt");
        joinedOutput.deleteOnExit();

        List<CharSource> inputSuppliers = getInputSuppliers(f1,f2,f3);

        CharSource joinedSupplier = CharSource.concat(inputSuppliers);
        CharSink outputSupplier = Files.asCharSink(joinedOutput, Charsets.UTF_8, FileWriteMode.APPEND);
        String expectedOutputString = joinFiles(f1,f2,f3);

        joinedSupplier.copyTo(outputSupplier);
        String joinedOutputString  = joinFiles(joinedOutput);
        assertThat(joinedOutputString,is(expectedOutputString));
    }

    private String joinFiles(File ...files) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (File file : files) {
             builder.append(Files.toString(file,Charsets.UTF_8));
        }
        return builder.toString();
    }

    private List<CharSource> getInputSuppliers(File ...files){
        List<CharSource> list  = Lists.newArrayList();
        for (File file : files) {
            list.add(Files.asCharSource(file,Charsets.UTF_8));
        }
       return list;
    }


}
