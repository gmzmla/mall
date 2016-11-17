package com.ruobilin.mall.admin.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class RequestParser {
	private final Logger log = LoggerFactory.getLogger(RequestParser.class);
    private static String FILENAME_PARAM = "qqfile";

    private String filename;
    private FileItem uploadItem;

    private RequestParser()
    {
    }

    //2nd param is null unless a MPFR
    public static RequestParser getInstance(HttpServletRequest request, MultipartUploadParser multipartUploadParser) throws Exception
    {
        RequestParser requestParser = new RequestParser();

        if (multipartUploadParser != null)
        {
            requestParser.uploadItem = multipartUploadParser.getFirstFile();
            requestParser.filename = multipartUploadParser.getFirstFile().getName();
        }
        else
        {
            requestParser.filename = request.getParameter(FILENAME_PARAM);
        }

        //grab other params here...

        return requestParser;
    }

    public String getFilename()
    {
        return filename;
    }

    //only non-null for MPFRs
    public FileItem getUploadItem()
    {
        return uploadItem;
    }

	public File writeToFile(InputStream in, File out, Long expectedFileSize) throws IOException {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(out);

			IOUtils.copy(in, fos);

			if (expectedFileSize != null) {
				Long bytesWrittenToDisk = out.length();
				if (!expectedFileSize.equals(bytesWrittenToDisk)) {
					log.warn(
							"Expected file {} to be {} bytes; file on disk is {} bytes",
							new Object[] { out.getAbsolutePath(),
									expectedFileSize, 1 });
					throw new IOException(
							String.format(
									"Unexpected file size mismatch. Actual bytes %s. Expected bytes %s.",
									bytesWrittenToDisk, expectedFileSize));
				}
			}

			return out;
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}
}
