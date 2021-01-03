package org.learn.something.io;

import java.io.IOException;
import java.nio.file.*;

public class GlobInJava {

    public static final FileSystem DEFAULT_FILE_SYSTEM = FileSystems.getDefault();
    private static final String BASIC_PATH = "E:/DevelopmentKit/idea-workspace/java-workspace/javatest/src/main/java/org/learn/something";

    public static void main(String[] args) {
        // pathMatch();
        dirStream();
    }

    public static void pathMatch() {
        PathMatcher pathMatcher = DEFAULT_FILE_SYSTEM.getPathMatcher("glob:**/*.java");
        final String path1 = "learn/something/extend/RandomEclipse.java";
        System.out.println(pathMatcher.matches(Paths.get(path1)));
    }

    public static void dirStream() {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get(BASIC_PATH), "*")) {
            for (Path path : paths) {
                System.out.println("path.getFileName() = " + path.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
