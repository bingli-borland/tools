package com.bingli.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.bes.common.util.admin.ExecutorServiceHelper;
//import com.bes.common.util.admin.Task;
//import com.bes.ssh.InputStreamHandler;
//import com.bes.ssh.SSHConnection;
//import com.bes.ssh.SSHException;
//import com.bes.ssh.SSHSession;


/**
 * Servlet for shell command
 */
@WebServlet(name = "ShellCommand", urlPatterns = "/ShellCommand")
public class ShellCommand extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int count = Integer.parseInt(req.getParameter("count"));
        String cmd= req.getParameter("cmd");
        int ts= Integer.parseInt(req.getParameter("ts"));
        resp.getWriter().println("Usage: count=2&cmd=pwd&ts=2");
//        List<Task> tasks = new ArrayList<Task>(ts);
//        for (int i = 0; i < ts; i++) {
//            TestThread t = new TestThread("name" + i, cmd, count);
//            tasks.add(t);
//        }
//        ExecutorServiceHelper.execute(tasks, ts);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
}
//
//class TestThread extends Task {
//
//    private String cmd;
//    private int count;
//
//    public TestThread(String name,String cmd, int count) {
//        super(name);
//        this.cmd = cmd;
//        this.count = count;
//    }
//
//    @Override
//    public void doRun() {
//        String user = "bes";
//        String passwd = "password";
//        String host = "192.168.32.128";
//        SSHConnection connection = new SSHConnection(host, user, passwd);
//        SSHSession session = null;
//        MyHandler handler = new MyHandler();
//        try {
//            session = connection.createSession();
//            session.exeCommand("ls -l", handler);
//            session.exeCommand("ll", handler);
//            session.exeCommand("pwd", handler);
//            session.exeCommand("sh /home/bes/libing/tomcat7/bin/version.sh", handler);
//            session.exeCommand("uname -a", handler);
//            session.exeCommand(cmd, handler);
//
//        } catch (SSHException e) {
//            e.printStackTrace();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//
//        }
//        System.out.println(Thread.currentThread() + " result= " + handler.getResult());
//    }
//}
//
//class MyHandler implements InputStreamHandler {
//
//    private StringBuffer sb = new StringBuffer();
//
//    public void handler(InputStream is) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        String line = null;
//        try {
//            while ((line = br.readLine()) != null) {
//                sb.append(line).append("\r\n");
//            }
//            System.out.println(line == null ? line : line.length());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getResult() {
//        return sb.toString();
//    }
//
//}
