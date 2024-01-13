package service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ResourceService {
    private static final Logger LOGGER = Logger.getLogger(ResourceService.class.getName());
    private ResourceService() {}
    public static Optional<String> findResource(String folderPath){
        try (Stream<Path> paths = Files.list(Paths.get(folderPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .findFirst()
                    .map(file -> file.toAbsolutePath().toString());
        } catch (Exception e) {
            LOGGER.severe("Error while reading resource folder: " + folderPath);
        }
        return Optional.empty();
    }


}
