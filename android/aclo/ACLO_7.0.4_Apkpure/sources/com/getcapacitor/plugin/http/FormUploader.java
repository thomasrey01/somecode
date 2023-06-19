package com.getcapacitor.plugin.http;

import com.getcapacitor.JSObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.UUID;
import org.json.JSONException;
/* loaded from: classes.dex */
public class FormUploader {
    private final String boundary;
    private final OutputStream outputStream;
    private final PrintWriter prWriter;
    private final String LINE_FEED = "\r\n";
    private final String charset = "UTF-8";

    public FormUploader(HttpURLConnection connection) throws IOException {
        String uuid = UUID.randomUUID().toString();
        this.boundary = uuid;
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + uuid);
        OutputStream outputStream = connection.getOutputStream();
        this.outputStream = outputStream;
        this.prWriter = new PrintWriter((Writer) new OutputStreamWriter(outputStream, "UTF-8"), true);
    }

    public void addFormField(String name, String value) {
        this.prWriter.append((CharSequence) "\r\n").append((CharSequence) "--").append((CharSequence) this.boundary).append((CharSequence) "\r\n").append((CharSequence) "Content-Disposition: form-data; name=\"").append((CharSequence) name).append((CharSequence) "\"").append((CharSequence) "\r\n").append((CharSequence) "Content-Type: text/plain; charset=").append((CharSequence) "UTF-8").append((CharSequence) "\r\n").append((CharSequence) "\r\n").append((CharSequence) value).append((CharSequence) "\r\n").append((CharSequence) "--").append((CharSequence) this.boundary).append((CharSequence) "--").append((CharSequence) "\r\n");
        this.prWriter.flush();
    }

    private void appendFieldToWriter(String name, String value) {
        this.prWriter.append((CharSequence) "\r\n").append((CharSequence) "--").append((CharSequence) this.boundary).append((CharSequence) "\r\n").append((CharSequence) "Content-Disposition: form-data; name=\"").append((CharSequence) name).append((CharSequence) "\"").append((CharSequence) "\r\n").append((CharSequence) "Content-Type: text/plain; charset=").append((CharSequence) "UTF-8").append((CharSequence) "\r\n").append((CharSequence) "\r\n").append((CharSequence) value);
    }

    public void addFilePart(String fieldName, File uploadFile, JSObject data) throws IOException {
        String name = uploadFile.getName();
        this.prWriter.append((CharSequence) "\r\n").append((CharSequence) "--").append((CharSequence) this.boundary).append((CharSequence) "\r\n").append((CharSequence) "Content-Disposition: form-data; name=\"").append((CharSequence) fieldName).append((CharSequence) "\"; filename=\"").append((CharSequence) name).append((CharSequence) "\"").append((CharSequence) "\r\n").append((CharSequence) "Content-Type: ").append((CharSequence) URLConnection.guessContentTypeFromName(name)).append((CharSequence) "\r\n").append((CharSequence) "\r\n");
        this.prWriter.flush();
        FileInputStream fileInputStream = new FileInputStream(uploadFile);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read == -1) {
                break;
            }
            this.outputStream.write(bArr, 0, read);
        }
        this.outputStream.flush();
        fileInputStream.close();
        if (data != null) {
            Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                try {
                    Object obj = data.get(next);
                    if (obj instanceof String) {
                        appendFieldToWriter(next, obj.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        this.prWriter.append((CharSequence) "\r\n").append((CharSequence) "--").append((CharSequence) this.boundary).append((CharSequence) "--").append((CharSequence) "\r\n");
        this.prWriter.flush();
    }

    public void addHeaderField(String name, String value) {
        this.prWriter.append((CharSequence) name).append((CharSequence) ": ").append((CharSequence) value).append((CharSequence) "\r\n");
        this.prWriter.flush();
    }

    public void finish() {
        this.prWriter.append((CharSequence) "\r\n");
        this.prWriter.flush();
        this.prWriter.append((CharSequence) "--").append((CharSequence) this.boundary).append((CharSequence) "--").append((CharSequence) "\r\n");
        this.prWriter.close();
    }
}
