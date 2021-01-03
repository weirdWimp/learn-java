package org.learn.something.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FileTest {

    public static void main(String[] args) throws FileNotFoundException {
        // makeFile();
        // nioTest();
        // channelTest();
        // dirWatch();
        // asyncIOTest();
        final String decode = URLDecoder.decode("urn%3Aietf%3Aparams%3Aoauth%3Agrant-type%3Ajwt-bearer");
        System.out.println("decode = " + decode);
    }

    public static void makeFile() {
        try {
            File tempFile = File.createTempFile("my-app", ".temp");
            tempFile.deleteOnExit();

            //文件系统所有的根目录
            File[] files = File.listRoots();
            for (File file : files) {
                System.out.println("file = " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        READ:
        while (true) {
            continue READ;
        }
    }

    public static void nioTest() {

        Path path1 = Paths.get("E:\\test2.txt");
        Path path2 = Paths.get("E:\\Life_is_Beautiful_1997_1080p_BluRay_x264_DTS-WiKi.chs.srt");
        Set<PosixFilePermission> filePermissions = PosixFilePermissions.fromString("rw-------");
        FileAttribute<Set<PosixFilePermission>> setFileAttribute = PosixFilePermissions.asFileAttribute(filePermissions);

        //Windows与posix不兼容，因此，不能传递一个FileAttribute对象
        //可以使用File类进行权限的控制
        try {
            // Path file = Files.createFile(path1);
            long size = Files.size(path2);

            Files.copy(Paths.get("E:\\test2.txt"), Paths.get("E:\\test3.txt"), StandardCopyOption.REPLACE_EXISTING);

            Map<String, Object> stringObjectMap = Files.readAttributes(path2, "*");
            System.out.println("stringObjectMap = " + stringObjectMap);

            System.out.println("size = " + size);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void channelTest() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("E:\\Motherless.Brooklyn.2019.1080p.AMZN.WEB-DL.DDP5.1.H.264-NTG.mkv");
        boolean fileOK = true;
        try (FileChannel fchan = fis.getChannel()) {
            ByteBuffer buffy = ByteBuffer.allocateDirect(16 * 1024 * 1024);
            int a;
            while ((a = fchan.read(buffy)) != -1 || buffy.position() > 0 || fileOK) {
                System.out.println("buffy remaining = " + buffy.remaining());
                System.out.println("buffy position = " + buffy.position());
                System.out.println("a = " + a);
                buffy.compact();
            }
        } catch (IOException e) {
            System.out.println("Exception in I/O");
        }
    }

    public static void dirWatch() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = FileSystems.getDefault().getPath("E:\\");
            WatchKey key = dir.register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            while (true) {
                System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
                key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    Object o = event.context();
                    if (o instanceof Path) {
                        System.out.println("Path altered: " + o);
                    }
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void asyncIOTest() {
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("E:\\Motherless.Brooklyn.2019.1080p.AMZN.WEB-DL.DDP5.1.H.264-NTG.mkv"))) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1000 * 1024 * 1024);

            Future<Integer> read = channel.read(byteBuffer, 0);

            while(!read.isDone()) {
                System.out.println("not done");
            }

            System.out.println("read = " + read.get());

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


}
