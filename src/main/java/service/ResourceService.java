package service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ResourceService {

    public static Optional<String> findResource(String folderPath){
        try (Stream<Path> paths = Files.list(Paths.get(folderPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .findFirst()
                    .map(file -> file.toAbsolutePath().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
