package src.se.itello.example.registration.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//superklass till handledares av div filerstyp
public abstract class FileHandler {

    //hjälpklass som används för att skilja ut dara ur textrad.
    protected class DataRowSection {
        private final int beginIndex;
        private final int endIndex;

        //Definerar en datapost i testred med samma start och slutposition
        //som nages i beskrivninig av filformat
        public DataRowSection(int beginIndex, int endIndex) {
            this.beginIndex = beginIndex - 1;
            this.endIndex = endIndex;
        }

        public String getSectionFrom(String dataRaw) {
            return dataRaw.substring(beginIndex, endIndex);
        }
    }

    private static final Charset DEFAULT_CHARSET = Charset.forName("ISO_8859_1");
    private Charset charset;

    public FileHandler() {
        charset = DEFAULT_CHARSET;
    }

    protected void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void dispatchFileData(Path path) {
        List<String> dataRows = readFile(path);
        parse(dataRows);
        registerData();
    }


    private List<String> readFile(Path path) {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        return result;
    }

    //Avskedd att implimentras av subklass. Skall hämta relevant data ut textrader.
     abstract protected void parse(List<String> dataRows);

    //Avskedd att implimenteras av subklass. Skall skicka data tillämplig.
    //receiver.
     abstract protected void registerData();

}

















