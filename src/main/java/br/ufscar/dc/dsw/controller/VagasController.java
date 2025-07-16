package br.ufscar.dc.dsw.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufscar.dc.dsw.domain.Vaga;
import br.ufscar.dc.dsw.service.spec.IVagaService;


	
@Controller
@RequestMapping("/home")
public class VagasController {
    
    @Autowired
    private IVagaService service;

    @GetMapping
    public String home(ModelMap model) {
		
		List<Vaga> vagasAbertas = new ArrayList<>();
		List<Vaga> allVagas = service.buscarTodos();

		for (Vaga vaga : allVagas) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        	// Converter a string para LocalDate
        	LocalDate parsedDate = LocalDate.parse(vaga.getDataLimite(), formatter);
        	// Data atual
        	LocalDate currentDate = LocalDate.now();
			if (!parsedDate.isBefore(currentDate)) {
                vagasAbertas.add(vaga);
            }
		}

        // Adiciona a lista de vagas ao modelo
        model.addAttribute("vagas", vagasAbertas);
		
        // Retorna a página de entrada
        return "home"; // Nome do arquivo HTML sem a extensão
    }


	@PostMapping("/buscar")
	public String listarVagasPorCidade(@RequestParam("cidade") String cidade, ModelMap model) {
		System.out.println("vagasFiltradas");
		List<Vaga> vagasFiltradas = service.buscarVagasCidade(cidade);
		List<Vaga> vagasAbertas = new ArrayList<>();

		for (Vaga vaga : vagasFiltradas) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        	// Converter a string para LocalDate
        	LocalDate parsedDate = LocalDate.parse(vaga.getDataLimite(), formatter);
        	// Data atual
        	LocalDate currentDate = LocalDate.now();
			if (!parsedDate.isBefore(currentDate)) {
                vagasAbertas.add(vaga);
            }
		}
		
		model.addAttribute("vagas", vagasAbertas);
		return "home"; // A página onde você deseja exibir as vagas filtradas
	}

}


	// @GetMapping("/cadastrar")
	// public String cadastrar(Compra compra) {
	// 	String data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	// 	compra.setUsuario(this.getUsuario());
	// 	compra.setData(data);
	// 	return "compra/cadastro";
	// }
	
	// private Usuario getUsuario() {
	// 	UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	// 	return usuarioDetails.getUsuario();
	// }
	
	// @PostMapping("/salvar")
	// public String salvar(@Valid Compra compra, BindingResult result, RedirectAttributes attr) {
		
	// 	if (result.hasErrors()) {
	// 		return "compra/cadastro";
	// 	}
		
	// 	service.salvar(compra);
	// 	attr.addFlashAttribute("sucess", "compra.create.sucess");
	// 	return "redirect:/compras/listar";
	// }
	
	// @ModelAttribute("livros")
	// public List<Livro> listaLivros() {
	// 	return livroService.buscarTodos();
	// }
