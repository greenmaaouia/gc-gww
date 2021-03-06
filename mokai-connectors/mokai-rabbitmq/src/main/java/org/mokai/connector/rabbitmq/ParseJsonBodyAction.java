package org.mokai.connector.rabbitmq;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;
import java.util.Set;
import org.mokai.Action;
import org.mokai.Message;

/**
 *
 * @author Alejandro Riveros Cruz <lariverosc@gmail.com>
 */
public class ParseJsonBodyAction implements Action {

	@Override
	public void execute(Message message) throws Exception {
		String body = (String) message.getProperty("body");
		JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
		Set<Map.Entry<String, JsonElement>> jsonEntrySet = jsonObject.entrySet();
		for (Map.Entry<String, JsonElement> jsonEntry : jsonEntrySet) {
			message.setProperty(jsonEntry.getKey(), jsonEntry.getValue().getAsString());
		}
	}
}
