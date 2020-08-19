package com.oracle.notificationconsumer.config;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.ResourceUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonWriter;

public class JSONBuilder {
	
	public static String nameRead;	
	
	public void writeIntoFile(com.oracle.common.model.Notification rabbitNots) {
		
		try {
			 
			 Object obj = JsonParser.parseReader(new FileReader("D:\\software\\eclipse\\oracle_project_workspace\\notificationconsumer\\src\\main\\webapp\\notification.json"));
	         JsonObject jsonObject = (JsonObject) obj;
	         JsonArray msg = (JsonArray)jsonObject.get("notification");
	         Iterator<JsonElement> iterator = msg.iterator();
	         while(iterator.hasNext()) {
	            nameRead = iterator.next().toString();
	         }
	         Notification not = new Notification();
	         not.setId(rabbitNots.getId());
	         not.setName(rabbitNots.getName());
	         not.setDescription(rabbitNots.getDescription());
	         Gson gson = new Gson();

	         FileWriter file = new FileWriter("D:\\software\\eclipse\\oracle_project_workspace\\notificationconsumer\\src\\main\\webapp\\notification.json", false);
	         JsonWriter jw = new JsonWriter(file);
	         iterator = msg.iterator();
	         Notifications nots = new Notifications();
	         while(iterator.hasNext()) {
	        	 nots.addNotification((gson.fromJson(iterator.next().toString(), Notification.class)));
	          }
	         nots.addNotification(not);
	         gson.toJson(nots, Notifications.class, jw);
	         file.close();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	   
		
	}

}

class Notification {
	
	@Expose
	private String id;	
	
	@Expose
	private String name;
	
	@Expose
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
	

}

class Notifications {
	
	@Expose
	List<Notification> notification = new ArrayList<>();
	
	public List<Notification> getNotification() {
	      return notification;
	   }
	   public void addNotification(Notification not) {
	      this.notification.add(not);
	   }

}


