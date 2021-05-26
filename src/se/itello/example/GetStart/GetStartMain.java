package src.se.itello.example.GetStart;

import src.se.itello.example.registration.DataFileRegistrator;
import src.se.itello.example.registration.FileFormatNotSupportedException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GetStartMain {

    public static void main(String [] args) {
        DataFileRegistrator dfr = new DataFileRegistrator();

        List<Path> toRegister = new ArrayList<>();

        toRegister.add(Paths.get("Exempelfil_betalningsservice.txt"));
        toRegister.add(Paths.get("Exempelfil_inbetalningstjansten.txt"));

        for (Path path : toRegister) {
            try {
                System.out.println("\nGetStart.main() Try to register: " + path.getFileName());
                dfr.register(path);
            } catch (FileFormatNotSupportedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
