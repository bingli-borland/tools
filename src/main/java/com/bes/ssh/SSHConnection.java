package com.bes.ssh;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHConnection {

	private String address;
	private int port = 22;
	private String user;
	private String password;
	private JSch jsch;

	public SSHConnection(String address, int port, String user, String password) {
		this.address = address;
		this.port = port;
		this.user = user;
		this.password = password;
		this.jsch = new JSch();
	}

	public SSHConnection(String address, String user, String password) {
		this.address = address;
		this.user = user;
		this.password = password;
		this.jsch = new JSch();
	}

	public SSHSession createSession() throws SSHException {
		try {
			Session session = jsch.getSession(user, address, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			return new SSHSession(session);
		} catch (JSchException e) {
			throw new SSHException(e);
		}
		
	}
}
