package br.ufscar.dc.dsw.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Inscricao;
import br.ufscar.dc.dsw.domain.Vaga;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.spec.IEmpresaService;
import br.ufscar.dc.dsw.service.spec.IInscricaoService;
import br.ufscar.dc.dsw.service.spec.IVagaService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    // ========= INJEÇÃO DE DEPENDÊNCIAS =========

    @Autowired
    private IEmpresaService service;

    @Autowired
    private IVagaService vagaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IInscricaoService inscricaoService;

    private final RestTemplate restTemplate;

    public EmpresaController() {
        this.restTemplate = new RestTemplate();
    }

    // ========= MÉTODO AUXILIAR =========

    private Empresa getEmpresaLogada() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Empresa) ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
    }

    // ========= CRUD DE EMPRESA =========

    @GetMapping("/cadastrar")
    public String cadastrar(Empresa empresa) {
        return "empresa/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Empresa empresa, BCryptPasswordEncoder encoder, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "empresa/cadastro";
        }

        empresa.setPassword(encoder.encode(empresa.getPassword()));
        service.salvar(empresa);
        attr.addFlashAttribute("sucess", "empresa.create.sucess");
        return "redirect:/empresas/listar";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("empresas", service.buscarTodos());
        return "empresa/lista";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("empresa", service.buscarPorId(id));
        return "empresa/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Empresa empresa, BindingResult result, BCryptPasswordEncoder encoder, RedirectAttributes attr) {
        if (result.getFieldErrorCount() > 2) {
            return "empresa/cadastro";
        }

        // Buscar dados existentes para manter a senha atual
        Empresa empresaExistente = service.buscarPorId(empresa.getId());
        empresa.setPassword(encoder.encode(empresaExistente.getPassword()));

        service.salvar(empresa);
        attr.addFlashAttribute("sucess", "empresa.edit.sucess");
        return "redirect:/empresas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model) {
        if (service.empresaTemVagas(id)) {
            model.addAttribute("fail", "empresa.delete.fail");
        } else {
            service.excluir(id);
            model.addAttribute("sucess", "empresa.delete.sucess");
        }
        return listar(model);
    }

    // ========= CRUD DE VAGA =========

    @GetMapping("/vagas")
    public String listarVagas(ModelMap model) {
        Empresa empresaLogada = getEmpresaLogada();
        model.addAttribute("vagas", vagaService.buscarVagasEmpresa(empresaLogada.getId()));
        return "empresa/listaVagas";
    }
    @GetMapping("/cadastrarVaga")
	public String cadastrarVaga(ModelMap model) {
		Vaga vaga = new Vaga();
		vaga.setEmpresa(getEmpresaLogada());
		model.addAttribute("vaga", vaga);
		return "empresa/cadastroVaga";
	}
    @PostMapping("/salvarVaga")
	public String salvarVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attr) {

		Empresa empresaLogada = getEmpresaLogada();
		vaga.setEmpresa(empresaLogada);
		System.out.println("ID da empresa na vaga SALVAR VAGA: " + vaga.getEmpresa());

		// if (result.hasErrors()) {
		// 
		// 	System.out.println("Erros de validação: " + result.getAllErrors() );
		// 	return "empresa/cadastroVaga";
		// }
	
		vagaService.salvar(vaga);
		attr.addFlashAttribute("sucess", "vaga.create.sucess");
		return "redirect:/empresas/vagas";
	}
    @GetMapping("/inscricoes/{id}")
	public String listarIncricoes(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("inscricoes", inscricaoService.buscarTodosPorVaga(id));
		return "empresa/listaInscricoes";
	}
    

    @PostMapping("/resultado/{id}")
public String resultado(@PathVariable("id") Long id, @RequestParam("resul") String resul, RedirectAttributes attr) {
    try {
        Inscricao inscricao = inscricaoService.buscarPorId(id);

        switch (resul) {
            case "Nao":
                inscricao.setResultado("NÃO SELECIONADO");
                break;
            case "Entrevista":
                inscricao.setResultado("ENTREVISTA");

                // Criar objeto EmailRequest corretamente estruturado
                Map<String, String> emailRequest = new HashMap<>();
                emailRequest.put("fromEmail", "nataly.cristina@estudante.ufscar.br");
                emailRequest.put("fromName", inscricao.getVaga().getEmpresa().getName());
                emailRequest.put("toEmail", inscricao.getProfissional().getEmail());
                emailRequest.put("toName", inscricao.getProfissional().getName());
                emailRequest.put("subject", "Chamada para a entrevista");
                emailRequest.put("body", "Parabéns, " + inscricao.getProfissional().getName()
                        + ", você foi selecionado para a entrevista!\n"
                        + "Link: https://meet.google.com/buw-bpod-qbn\n"
                        + "Telefone: +55 19 4560-9551 | PIN: 343 321 949#\n"
                        + "Mais números: https://tel.meet/buw-bpod-qbn?hs=5");

                // URL correta: nome do serviço no Docker + endpoint correto
                String emailServiceUrl = "http://email-service:8081/api/email/send";
                
                try {
                    System.out.println("=== ENVIANDO EMAIL ===");
                    System.out.println("URL: " + emailServiceUrl);
                    System.out.println("Dados: " + emailRequest);
                    
                    // Configurar headers para JSON
                    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                    
                    org.springframework.http.HttpEntity<Map<String, String>> entity = 
                        new org.springframework.http.HttpEntity<>(emailRequest, headers);
                    
                    org.springframework.http.ResponseEntity<String> response = 
                        restTemplate.postForEntity(emailServiceUrl, entity, String.class);
                    
                    System.out.println("✅ Email enviado com sucesso: " + response.getBody());
                    
                } catch (Exception emailException) {
                    System.err.println("❌ Erro ao enviar email: " + emailException.getMessage());
                    emailException.printStackTrace();
                    
                    // Tentar health check para diagnóstico
                    try {
                        String healthUrl = "http://email-service:8081/api/email/health";
                        String healthResponse = restTemplate.getForObject(healthUrl, String.class);
                        System.out.println("🔍 Health check: " + healthResponse);
                    } catch (Exception healthException) {
                        System.err.println("🔴 Email service não está acessível: " + healthException.getMessage());
                    }
                    
                    // Não interrompe o fluxo - continua sem o email
                    System.out.println("⚠️ Continuando sem envio de email...");
                }
                break;
            default:
                inscricao.setResultado("ANÁLISE");
                break;
        }

        inscricaoService.salvar(inscricao);
        Long vagaId = inscricao.getVaga().getId();
        attr.addFlashAttribute("success", "Inscrição analisada com sucesso.");
        return "redirect:/empresas/inscricoes/" + vagaId;

    } catch (Exception e) {
        System.err.println("❌ Erro geral: " + e.getMessage());
        e.printStackTrace();
        
        attr.addFlashAttribute("fail", "Erro ao processar inscrição: " + e.getMessage());
        
        try {
            Inscricao inscricao = inscricaoService.buscarPorId(id);
            Long vagaId = inscricao.getVaga().getId();
            return "redirect:/empresas/inscricoes/" + vagaId;
        } catch (Exception idException) {
            return "redirect:/empresas/vagas";
        }
    }
}
}