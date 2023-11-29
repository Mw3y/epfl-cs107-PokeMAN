package ch.epfl.cs107.play.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Read-only implementation of file system, providing access to a ZIP archive.
 */
public class ZipFileSystem implements FileSystem {

    private FileSystem fallback;
    private ZipFile zip;

    /**
     * Creates a new ZIP file system.
     * @param fallback (FileSystem): secondary file system used on error, not null
     * @param zip (ZipFile): zip file, not null
     */
    public ZipFileSystem(FileSystem fallback, ZipFile zip) {
        if (fallback == null || zip == null)
            throw new NullPointerException();
        this.fallback = fallback;
        this.zip = zip;
    }
    
    @Override
    public InputStream read(String name) throws IOException {
        ZipEntry entry = zip.getEntry(name);
        if (entry != null)
            return zip.getInputStream(entry);
        return fallback.read(name);
    }

    @Override
    public OutputStream write(String name) throws IOException {
        return fallback.write(name);
    }
    
}
