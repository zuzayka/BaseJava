package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();
    private String newName;
    private static Boolean requestToSave = false;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.getResume(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContactType().remove(type);
            }
        }
//        for (SectionType type : SectionType.values()) {
//            String value = request.getParameter(type.name());
//            if (value != null && value.trim().length() != 0) {
//                r.addSection(type, null);
//            } else {
//                r.getSectionType().remove(type);
//            }
//        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid;
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r = null;
        switch (action) {
            case "delete":
                uuid = request.getParameter("uuid");
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                uuid = UUID.randomUUID().toString();
                newName = request.getParameter("fullName");
                r = new Resume(uuid, newName);
                storage.save(r);
                break;
            case "view":
            case "edit":
                uuid = request.getParameter("uuid");
                r = storage.getResume(uuid);
                break;
        }
        request.setAttribute("resume",r);
        request.getRequestDispatcher(
                "view".equals(action) ? "WEB-INF/jsp/view.jsp" : "edit".equals(action) ?
                        "WEB-INF/jsp/edit.jsp" : "WEB-INF/jsp/add.jsp"
        ).forward(request, response);
    }
}