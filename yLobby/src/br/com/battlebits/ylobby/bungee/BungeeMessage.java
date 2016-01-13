package br.com.battlebits.ylobby.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeMessage {

	private ByteArrayDataOutput dataOutput;
	private String messageArgs;

	public BungeeMessage(String... args) {
		dataOutput = ByteStreams.newDataOutput();
		messageArgs = "";
		for (String str : args) {
			dataOutput.writeUTF(str);
			if (messageArgs.length() > 0) {
				messageArgs = messageArgs + "#";
			}
			messageArgs = messageArgs + str;
		}
	}

	public ByteArrayDataOutput getDataOutput() {
		return dataOutput;
	}

	public String getMessageArgs() {
		return messageArgs;
	}

}
