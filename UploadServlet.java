import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*15)   // 15MB
public class UploadServlet extends HttpServlet {

    private final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);

        if (!fileName.endsWith(".py")) {
            request.setAttribute("message", "يرجى رفع ملف بايثون فقط (.py)");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + UPLOAD_DIR;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String filePath = uploadPath + File.separator + fileName;
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, new File(filePath).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            request.setAttribute("message", "خطأ في حفظ الملف: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // تشغيل الملف باستخدام python3
        try {
            ProcessBuilder pb = new ProcessBuilder("python3", filePath);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // قراءة الناتج
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("<br>");
            }
            process.waitFor();

            request.setAttribute("message", "تم رفع الملف وتشغيله بنجاح!<br>الناتج:<br>" + output.toString());
        } catch (Exception e) {
            request.setAttribute("message", "حدث خطأ أثناء تشغيل الملف: " + e.getMessage());
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }
}
