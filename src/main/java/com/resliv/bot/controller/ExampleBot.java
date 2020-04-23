package com.resliv.bot.controller;

import com.resliv.bot.entity.City;
import com.resliv.bot.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
@PropertySource("classpath:telegram.properties")
public class ExampleBot extends TelegramLongPollingBot {

	private static final Logger logger = LoggerFactory.getLogger(ExampleBot.class);
	private final CityService service;
	private final String sorryMessage = "Sorry, but we don't now this city!";

	@Value("${bot.token}")
	private String token;

	@Value("${bot.username}")
	private String username;

	public ExampleBot(CityService service) {
		this.service = service;
	}

	@Override
	public String getBotToken() {
		return token;
	}

	@Override
	public String getBotUsername() {
		return username;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			SendMessage response = new SendMessage();

			Long chatId = message.getChatId();
			response.setChatId(chatId);
			String text = message.getText();
			List<City> cities = service.findByName(text);
			logger.info("List cities: " + cities);
			if (cities.isEmpty()) {
				response.setText(sorryMessage);
			} else {
				response.setText(cities.get(0).getDescription());
			}
			try {
				execute(response);
				logger.info("Sent message \"{}\" to {}", text, chatId);
			} catch (TelegramApiException e) {
				logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
			}
		}
	}

	@PostConstruct
	public void start() {
		logger.info("username: {}, token: {}", username, token);
	}

}
