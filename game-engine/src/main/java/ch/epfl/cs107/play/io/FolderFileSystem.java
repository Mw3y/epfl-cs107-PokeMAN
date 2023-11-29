package ch.epfl.cs107.play.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Implementation of file system, mirroring actual files on disk.
 */
public class FolderFileSystem implements FileSystem {

    private FileSystem fallback;
    private File folder;

    /**
     * Creates a new file system using specified folder.
     * @param fallback (FileSystem): secondary file system used on error, not null
     * @param folder (File): root, may be null
     */
    public FolderFileSystem(FileSystem fallback, File folder) {
        if (fallback == null )
            throw new NullPointerException();
        this.fallback = fallback;
        this.folder = folder;
    }

    /**
     * Creates a new file system using current working directory.
     * @param fallback (FileSystem): secondary file system used on error, not null
     */
    public FolderFileSystem(FileSystem fallback) {
        this(fallback, new File("."));
    }


    @Override
    public InputStream read(String name) throws IOException {
        // If parent is null then the new File instance is created as if by invoking the single-argument
        // File constructor on the given child pathname string.
        File file = new File(folder, name);
        System.out.println(file.getAbsolutePath());
        if (file.canRead()) {
            return new FileInputStream(file);
        }
        return fallback.read(name);
    }

    @Override
    public OutputStream write(String name) throws IOException {
        // If parent is null then the new File instance is created as if by invoking the single-argument
        // File constructor on the given child pathname string.
        File file = new File(folder, name);
        if (file.canWrite())
            return new FileOutputStream(file);
        return fallback.write(name);
    }
}
