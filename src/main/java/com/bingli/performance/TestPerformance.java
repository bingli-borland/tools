package com.bingli.performance;

import com.bingli.tools.ArticleBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.bingli.tools.ParserTool.parse;


@WebServlet(name = "TestPerformance", urlPatterns = "/TestPerformance")
public class TestPerformance extends HttpServlet {

    private AtomicLong count = new AtomicLong(0L);
    private long lastCount = 0L;
    private long lastOutputTime = System.currentTimeMillis();
    private int outputInterval = 120000;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM-dd HH:mm:ss S Z");
    private List<String> outputMsgs = new LinkedList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long currentCount = this.count.incrementAndGet();
        outputCountMsg(currentCount);
        if ("y".equals(req.getParameter("tps"))) {
            String output = "\n";
            for (String msg : outputMsgs) {
                output = output + msg;
            }
            resp.getWriter().append(output);
        }
    }

    private void outputCountMsg(long currentCount) {
        if (this.lastOutputTime + this.outputInterval < System.currentTimeMillis()) {
            synchronized (this) {
                if (this.lastOutputTime + this.outputInterval < System.currentTimeMillis()) {
                    long currentTime = System.currentTimeMillis();
                    long thisTimeConsume = currentTime - this.lastOutputTime;
                    this.lastOutputTime = currentTime;
                    long thisTimeCount = currentCount - this.lastCount;
                    synchronized (outputMsgs) {
                        String msg = this.sdf.format(new Date())
                                + " - invoke total " + currentCount + " times, and invoke "
                                + thisTimeCount + " at this time, " + " consume " + thisTimeConsume +"ms. "
                                + " tps " + thisTimeCount / (thisTimeConsume / 1000.0D) + "\n";
                        this.outputMsgs.add(msg);
                        if (this.outputMsgs.size() > 10) {
                            this.outputMsgs.remove(0);
                        }
                    }
                    this.lastCount = currentCount;
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
}
