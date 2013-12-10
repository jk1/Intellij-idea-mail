package github.jk1.smtpidea.server.util;

/**
 * @author Evgeny Naumenko
 */
public interface ChunkWriter {

    /**
     * create a new chunk at position i (zero indexed) with the entire
     * byte array in data
     */
    void newChunk(int i, byte[] data);

}