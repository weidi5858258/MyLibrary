package com.c1tech.dress.net;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;


public class CustomMultipartRequestEntity extends MultipartRequestEntity {
	private final ProgressListener listener;

	public CustomMultipartRequestEntity(Part[] parts, HttpMethodParams params, ProgressListener listener) {
		super(parts, params);
		this.listener = listener;
	}

	@Override
	public void writeRequest(OutputStream out) throws IOException {
		super.writeRequest(new CountingOutputStream(out, this.listener));
	}

	public interface ProgressListener {
		void transferred(long num);
	}

	
	
	
	
	
	
	public class CountingOutputStream extends FilterOutputStream {
		private final ProgressListener listener;
		private long transferred;

		public CountingOutputStream(final OutputStream out, final ProgressListener listener) {
			super(out);
			this.listener = listener;
			this.transferred = 0;
		}

		public void write(byte[] b, int off, int len) throws IOException {
			out.write(b, off, len);
			this.transferred += len;
			this.listener.transferred(this.transferred);
		}

		public void write(int b) throws IOException {
			out.write(b);
			this.transferred++;
			this.listener.transferred(this.transferred);
		}
	}
	
	
	
	
	
}
