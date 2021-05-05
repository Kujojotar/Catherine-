package core.servlet;

import java.io.OutputStream;
import java.io.PrintWriter;

public interface ServletResponse {
    public PrintWriter getPrintWriter();

    public OutputStream getOutputStream();

    public void println(String str);

    public void setHeader(String header,String content);

    public void sendRedirect(String destination);
}
