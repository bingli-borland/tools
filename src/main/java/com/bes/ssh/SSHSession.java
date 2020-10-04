package com.bes.ssh;
import java.io.InputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class SSHSession {

	private Session delegate;
	
	private InputStreamHandler defaultHandler = new EmptyHandler();
	
	public SSHSession(Session delegate) {
		this.delegate = delegate;
	}

	public void exeCommand(String command) throws SSHException {
		exeCommand(command, defaultHandler);
	}
	public void exeCommand(String command, InputStreamHandler handler) throws SSHException {
		ChannelExec channel = null;
		try {
			channel = (ChannelExec) delegate.openChannel("exec");
			channel.setCommand(command);
			channel.connect();
			//System.out.println("channel connected=" + channel.isConnected());
			//System.out.println("channel inputstream available=" + channel.getInputStream().available());
			handler.handler(channel.getInputStream());
		} catch (Exception e) {
			throw new SSHException(e);
		} finally {
			if (channel != null ) {
				channel.disconnect();
			}
		}
	}

	public void close() {
		delegate.disconnect();
	}

	class EmptyHandler implements InputStreamHandler{

		public void handler(InputStream is) {
			
		}
		
	}
}

