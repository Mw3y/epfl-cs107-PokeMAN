package ch.epfl.cs107.play.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Read-only implementation of file system, using native resources bundled with binaries.
 */
public class ResourceFileSystem implements FileSystem {

    private FileSystem fallback;
    private ClassLoader loader;

    /**
     * Creates a new resource file system using specified binaries.
     * @param fallback (FileSystem): secondary file system used on error, not null
     * @param loader (ClassLoader): specific binary to use, not null
     */
    public ResourceFileSystem(FileSystem fallback, ClassLoader loader) {
        if (fallback == null || loader == null)
            throw new NullPointerException();
        this.fallback = fallback;
        this.loader = loader;
    }

    /**
     * Creates a new resource file system using core binaires.
     * @param fallback (FileSystem): secondary file system used on error, not null
     */
    public ResourceFileSystem(FileSystem fallback) {
        this(fallback, FileSystem.class.getClassLoader());
    }
    
    @Override
    public InputStream read(String name) throws IOException {
        InputStream input = loader.getResourceAsStream(name);
        if (input != null) {
            return input;
        }
        return fallback.read(name);
    }

    @Override
    public OutputStream write(String name) throws IOException {
        return fallback.write(name);
    }
    
}
