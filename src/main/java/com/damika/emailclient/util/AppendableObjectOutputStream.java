package com.damika.emailclient.util;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.NonNull;

public class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(@NonNull OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // Do NOT write a header — needed for appending
        reset();
    }
}
