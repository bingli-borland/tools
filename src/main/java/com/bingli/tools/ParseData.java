package com.bingli.tools;

import static com.bingli.tools.ParserTool.parse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet for upload file
 */
@WebServlet(name = "ParseData", urlPatterns = "/ParseData")
public class ParseData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String datafile = ParseData.class.getResource("/data.txt").getPath();
        List<ArticleBean> rs = new ArrayList<ArticleBean>();
        BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(datafile),"UTF-8"));  
        String line = br.readLine();
        while (line != null) {
            parse(line, rs);
            line = br.readLine();
        }
        Collections.sort(rs);
        req.getSession().setAttribute("beans", rs);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
}
