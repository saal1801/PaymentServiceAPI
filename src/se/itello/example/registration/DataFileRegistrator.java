package src.se.itello.example.registration;

import src.se.itello.example.registration.internal.FileHandler;
import src.se.itello.example.registration.internal.PaymentFileHandlerBetalningsService;
import src.se.itello.example.registration.internal.PaymentFileHandlerInbetalningstjansten;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

//API public interface
public class DataFileRegistrator {

    //Definerar vila format som stödjas
    private enum FileFormat{
        BETALNINGSSERVICE("Betalningsservice"), INBETALNINGSTJANSTEN("Inbetalningstjänsten");

        private final String nameAsString;

         FileFormat(String nameAsString){
            this.nameAsString = nameAsString;

        }
        public String toString(){

             return nameAsString;
        }
    }
    private final Map<String, FileFormat> extensionToFormat;
    private final Map<FileFormat, FileHandler> formatToHandler;

    public DataFileRegistrator(){
        extensionToFormat = new HashMap<>();
        extensionToFormat.put("_betalningsservice.txt", FileFormat.BETALNINGSSERVICE);
        extensionToFormat.put("_inbetalningstjansten.txt", FileFormat.INBETALNINGSTJANSTEN);

        formatToHandler = new HashMap<>();
        formatToHandler.put(FileFormat.BETALNINGSSERVICE, new PaymentFileHandlerBetalningsService());
        formatToHandler.put(FileFormat.INBETALNINGSTJANSTEN, new PaymentFileHandlerInbetalningstjansten());
    }

    public void register(Path dataFilePath) throws  FileFormatNotSupportedException {
        FileFormat format = findFormat(dataFilePath);

        if (formatToHandler.containsKey(format)){
            formatToHandler.get(format).dispatchFileData(dataFilePath);
        }else {
            throw  new FileFormatNotSupportedException(
                    "File format can not be registred. " +
                            "Supported file formats are : " + formatToHandler.keySet());
        }
    }

    private FileFormat findFormat(Path path) {
        String fileName = path.getFileName().toString();
        int extensionIndex = fileName.lastIndexOf("_");
        String extension = fileName.substring(extensionIndex);
        return extensionToFormat.get(extension);
    }
}













