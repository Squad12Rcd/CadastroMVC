package com.carros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.util.List;
import com.carros.model.Carro;
import com.carros.repository.CarroRepository;


@Controller
@RequestMapping("/carros")
public class CarroController {
	@Autowired CarroRepository carroRepository;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("carros/listar.html");
		List<Carro> carros = carroRepository.findAll();
		modelAndView.addObject("carros", carros);
		
		return modelAndView;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView modelAndView = new ModelAndView("carros/cadastro.html");
		modelAndView.addObject("carro", new Carro());
		return modelAndView;
	}
	
	@PostMapping("/cadastrar")
	public ModelAndView cadastrar(Carro carro, @RequestParam("fileCarro") MultipartFile file) throws IOException {
		try {
			carro.setImagem(file.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView modelAndView = new ModelAndView("redirect:/carros");
		carroRepository.save(carro);
		
		return modelAndView;
	}
	
	@GetMapping("/imagem/{id}") 
	@ResponseBody
	public byte[] exibirImagen(@PathVariable("id") Long id) {
		Carro carro = this.carroRepository.getReferenceById(id);
		return carro.getImagem();	
	}
	
	@GetMapping("/{id}/editar")
	public ModelAndView editar(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("carros/editar");
		
		Carro carro = carroRepository.getReferenceById(id);
		modelAndView.addObject("carro", carro);
		
		return modelAndView;
	}
	
	@PostMapping("/{id}/editar")
	public ModelAndView editar(Carro carro, @RequestParam("fileCarro") MultipartFile file) {
		try {
			carro.setImagem(file.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView modelAndView = new ModelAndView("redirect:/carros");
		carroRepository.save(carro);
		
		return modelAndView;
	}
	
	@GetMapping("/{id}/excluir")
	public ModelAndView excluir(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carros");
		carroRepository.deleteById(id);
		return modelAndView;
	}
	
	@GetMapping("/{id}/detalhar")
	public ModelAndView detalhar(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("carros/detalhar.html");
		
		Carro carro= carroRepository.getReferenceById(id);
		modelAndView.addObject("carro", carro);
		
		return modelAndView;
	}
}
