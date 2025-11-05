package readvault.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import readvault.service.MangaService;

@Controller
public class HomeController {

	@Autowired
	private MangaService mangaService;
	
	@GetMapping("/home")
	public String showHome() throws ExecutionException, InterruptedException {
		
		return "home/home";
	}
}
