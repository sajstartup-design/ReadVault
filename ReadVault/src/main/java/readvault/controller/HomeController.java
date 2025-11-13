package readvault.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import readvault.object.MMMData;
import readvault.service.MangaService;

@Controller
public class HomeController {

	@Autowired
	private MangaService mangaService;
	
	@GetMapping()
	public String showHome(Model model) throws ExecutionException, InterruptedException {
		
		List<MMMData> data = mangaService.getAllManga();
		
		model.addAttribute("mmm", data);
		
		return "home/home";
	}
	
	@PostMapping("/add")
	public String addMMM(@ModelAttribute MMMData data) throws ExecutionException, InterruptedException {
		
		System.out.println(data.getTitle());
		System.out.println(data.getImage());
		System.out.println(data.getGenre());
		System.out.println(data.getRating());
		
		mangaService.saveManga(data);
		
		return "redirect:/";
	}
}
