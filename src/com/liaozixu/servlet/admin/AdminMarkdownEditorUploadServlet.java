package com.liaozixu.servlet.admin;

import com.liaozixu.system.Config;
import com.liaozixu.util.GatewayUtils;
import com.liaozixu.util.GsonUtils;
import com.liaozixu.util.QcloudUtils;
import com.liaozixu.util.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;

@MultipartConfig(maxFileSize = 1024 * 1024 * 100)
@WebServlet(name = "AdminMarkdownEditorUploadServlet", urlPatterns = {"/admin/markdownEditor/upload"})
public class AdminMarkdownEditorUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Config config = new Config();
        HashMap<String, Part> partMap = new HashMap<>();
        try {
            request.setCharacterEncoding("utf-8");
            for (Part part : request.getParts()) {
                partMap.put(part.getName(), part);
            }
        } catch (Exception e) {
            GatewayUtils.showJson(false, 100001, null, e.getMessage(), response);
            return;
        }
        Part upfile = partMap.get("editormd-image-file");
        if (upfile == null || upfile.getSize() == 0) {
            GatewayUtils.showJson(false, 100001, null, response);
            return;
        }
        String prefix = "." + upfile.getSubmittedFileName().substring(upfile.getSubmittedFileName().lastIndexOf(".") + 1);
        String uploadPath = QcloudUtils.cosPutObject(prefix, upfile.getInputStream(), upfile.getSize(),
                upfile.getContentType());
        if (uploadPath == null) {
            GatewayUtils.showJson(false, 100002, null, response);
            return;
        }
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("success", 1);
        returnMap.put("url", config.get("qcloudCosWebPath") + uploadPath);
        ServletUtils.responseString(response, GsonUtils.toJson(returnMap));
    }
}
