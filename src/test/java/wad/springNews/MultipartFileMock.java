package wad.springNews;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileMock implements MultipartFile {

    private String name;
    private String originalFilename;
    private String contentType;
    private Long size;
    private byte[] bytes;

    public MultipartFileMock(String name, String originalFilename, String contentType, Long size, byte[] bytes) {
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.size = size;
        this.bytes = bytes;
    }



    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return getSize()==0;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}