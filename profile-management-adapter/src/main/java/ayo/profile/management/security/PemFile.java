package ayo.profile.management.security;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Created by Aifheli Maganya on 2022/05/19.
 */
public class PemFile {
    private PemObject pemObject;

    public PemFile(String filename) throws IOException {
        PemReader pemReader = new PemReader(new InputStreamReader(new FileInputStream(ResourceUtils.getFile(filename))));
        try {
            this.pemObject = pemReader.readPemObject();
        } finally {
            pemReader.close();
        }
    }

    public PemObject getPemObject() {
        return pemObject;
    }
}