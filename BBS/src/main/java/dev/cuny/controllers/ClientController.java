package dev.cuny.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import dev.cuny.entities.Client;
import dev.cuny.exceptions.ClientAlreadyExistedException;
import dev.cuny.services.ClientService;

@Component
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClientController {
	private static Logger logger = LoggerFactory.getLogger(ClientController.class);
	@Autowired
	ClientService cs;

	@ResponseBody
	@RequestMapping(value = "/clients", method = RequestMethod.POST)
	public Client signup(@RequestBody Client client) {
		try {
			logger.info("Client was created: " + client.toString());
			return cs.createClient(client);
		} catch (ClientAlreadyExistedException e) {
			logger.info("Unable to create the client: " + client.toString());
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/clients/login", method = RequestMethod.GET)
	public Client login(@RequestParam String username, @RequestParam String password) {
		return cs.getClientByUsernameAndPassword(username, password);
	}

	@ResponseBody
	@RequestMapping(value = "/clients", method = RequestMethod.PUT)
	public Client updateClient(@RequestBody Client client) {
		logger.info("Client was updated: " + client.toString());
		return cs.updateClient(client);
	}

	@ResponseBody
	@RequestMapping(value = "/query/clients", method = RequestMethod.GET)
	public Client query(@RequestParam(required = false) String username, @RequestParam(required = false) String email) {

		
		if (username != null) {
			return cs.getClientByUsername(username);
		}

		if (email != null) {
			return cs.getClientByEmail(email);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
	public Client getClientById(@PathVariable int id) {
		try {
			return cs.getClientById(id);
		} catch (NoSuchElementException e) {
			logger.error("Unable to find a client with id: " + id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public List<Client> getAllClients() {
		logger.info("A list of all clients was accessed.");
		return cs.getAllClients();
	}

	@ResponseBody
	@RequestMapping(value = "/clients/points", method = RequestMethod.GET)
	public int getClientsPoints(@RequestParam int id) {
		logger.info("Client with id: " + id + " has " + cs.getClientPoints(id) + " points");
		return cs.getClientPoints(id);
	}

	@ResponseBody
	@RequestMapping(value = "/clients/leaderboard/username", method = RequestMethod.GET)
	public List<String> getLeaderboardusernames() {
		logger.info("Leaderboard usernames are: " + cs.leaderboardusername());
		return cs.leaderboardusername();
	}

	@ResponseBody
	@RequestMapping(value = "/clients/leaderboard/points", method = RequestMethod.GET)
	public List<Integer> getLeaderboardpoints() {
		logger.info("Leaderboard points are: " + cs.leaderboardpoints());
		return cs.leaderboardpoints();

	}

//	@ResponseBody
//	@RequestMapping(value = "query/clients", method = RequestMethod.GET)
//	public Client getClientByEmail(@RequestParam String email) {
//		return cs.getClientByEmail(email);
//	}

//	@ResponseBody
//	@GetMapping(value ="/clients", params = { "email" })
//	public Client getClientByEmail(@RequestParam("email") String email) {
//		return cs.getClientByEmail(email);
//	}

}
