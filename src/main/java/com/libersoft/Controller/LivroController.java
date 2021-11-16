package com.libersoft.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.libersoft.DAO.LivroDAO;
import com.libersoft.Model.Livro;

@Controller
public class LivroController {
	long oldIsbn = 0;
    
	@Autowired
	private LivroDAO livroDAO;

	@ModelAttribute("livros")
	public List<Livro> getLista() {
		return this.livroDAO.findAll();
	}

	@GetMapping("/aluno/livros")
	public String listarLivrosAluno() {
		return "livros";
	}
	
	@GetMapping("/bibliotecario/listarLivros")
	public String listarLivros() {
		return "listarLivros";
	}
	
	@GetMapping("/bibliotecario/cadastroLivro")
	public String exibirFormLivro(Livro livro) {
		return "cadastroLivro";
	}

	@PostMapping("/bibliotecario/cadastroLivro")
	public String cadastrarLivro(@Valid Livro livro, BindingResult result) {
		
		String erros = "";
		
		/* CONFERE SE OS CAMPOS DO TIPO ÚNICO JÁ
		 * EXISTEM CADASTRADOS NO BANCO DE DADOS */
		if (livroDAO.existsByIsbn(livro.getIsbn())) {
			erros += "isbn$livro$ISBN já cadastrado$";
		}
		
		/* TRATANDO OS ERROS PARA REGISTRAR ELES NO
		 * OBJETO 'RESULT', QUE ARMAZENA TODOS OS ERROS
		 * DOS CAMPOS DE CADASTRO */
		if (!erros.isEmpty()) {
			String[] listaErros = erros.split("\\$");
			for (int i = 0; i < listaErros.length; i += 3) {
				result.rejectValue(listaErros[i], listaErros[i + 1], listaErros[i + 2]);
			}
		}
		
		if (result.hasErrors()) {
			return "cadastroLivro";
		}
		
		this.livroDAO.save(livro);
		return "redirect:listarLivros";
		
	}

	@GetMapping("/bibliotecario/editarLivro")
	public String getEditarLivro(Integer idLivro, Model model) {
		Livro liv = this.livroDAO.getById(idLivro);
		model.addAttribute("livro", liv);
		
		oldIsbn = liv.getIsbn();
		
		return "editarLivro";
	}
	
	@PostMapping("/bibliotecario/editarLivro")
	public String postEditarLivro(@Valid Livro livro, BindingResult result) {
		String erros = "";
		
		long isbn = livro.getIsbn();
		
		/* VERIFICA SE O CAMPO DE EMAIL E CPF
         * É IGUAL AO REGISTRO NO ESTADO PASSADO.
         * SE NÃO FOR IGUAL, IRÁ VERIFICAR SE EXISTE
         * OUTRO ISBN IGUAL PARA NÃO
         * HAVER ERROS NO CADASTRO */
		if(oldIsbn != isbn) {
			if(livroDAO.existsByIsbn(isbn)) {
				erros += "isbn$livro$ISBN já cadastrado";
			}
		}
		
		/* TRATANDO OS ERROS PARA REGISTRAR ELES NO
         * OBJETO 'RESULT', QUE ARMAZENA TODOS OS ERROS
         * DOS CAMPOS DE CADASTRO */
        if (!erros.isEmpty()) {         
            System.out.println(erros);
            String[] listaErros = erros.split("\\$");
            for (int i = 0; i < listaErros.length; i += 3) {
                result.rejectValue(listaErros[i], listaErros[i + 1], listaErros[i + 2]);
            }
        }
		
		if (result.hasErrors()) {
			return "editarLivro";
		}
		
		this.livroDAO.save(livro);
		return "redirect:listarLivros";
		
	}

	@GetMapping("/bibliotecario/excluirLivro")
	public String excluirLivro(Integer idLivro) {
		if (!(idLivro == null)) {
			this.livroDAO.deleteById(idLivro);
		}
		return "redirect:listarLivros";
	}

	
	//tudo de livro relacionado ao ADM
	

	@GetMapping("/adm/listarLivros")
	public String listarLivrosAdm() {
		return "listarLivrosAdm";
	}
	
	@GetMapping("/adm/cadastroLivro")
	public String exibirFormLivroAdm(Livro livro) {
		return "cadastroLivro";
	}

	@PostMapping("/adm/cadastroLivro")
	public String cadastrarLivroAdm(@Valid Livro livro, BindingResult result) {
		
		String erros = "";
		
		/* CONFERE SE OS CAMPOS DO TIPO ÚNICO JÁ
		 * EXISTEM CADASTRADOS NO BANCO DE DADOS */
		if (livroDAO.existsByIsbn(livro.getIsbn())) {
			erros += "isbn$livro$ISBN já cadastrado$";
		}
		
		/* TRATANDO OS ERROS PARA REGISTRAR ELES NO
		 * OBJETO 'RESULT', QUE ARMAZENA TODOS OS ERROS
		 * DOS CAMPOS DE CADASTRO */
		if (!erros.isEmpty()) {
			String[] listaErros = erros.split("\\$");
			for (int i = 0; i < listaErros.length; i += 3) {
				result.rejectValue(listaErros[i], listaErros[i + 1], listaErros[i + 2]);
			}
		}
		
		if (result.hasErrors()) {
			return "cadastroLivro";
		}
		
		this.livroDAO.save(livro);
		return "redirect:listarLivrosAdm";
		
	}

	@GetMapping("/adm/editarLivro")
	public String getEditarLivroAdm(Integer idLivro, Model model) {
		Livro liv = this.livroDAO.getById(idLivro);
		model.addAttribute("livro", liv);
		
		oldIsbn = liv.getIsbn();
		
		return "editarLivro";
	}
	
	@PostMapping("/adm/editarLivro")
	public String postEditarLivroAdm(@Valid Livro livro, BindingResult result) {
		String erros = "";
		
		long isbn = livro.getIsbn();
		
		/* VERIFICA SE O CAMPO DE EMAIL E CPF
         * É IGUAL AO REGISTRO NO ESTADO PASSADO.
         * SE NÃO FOR IGUAL, IRÁ VERIFICAR SE EXISTE
         * OUTRO ISBN IGUAL PARA NÃO
         * HAVER ERROS NO CADASTRO */
		if(oldIsbn != isbn) {
			if(livroDAO.existsByIsbn(isbn)) {
				erros += "isbn$livro$ISBN já cadastrado";
			}
		}
		
		/* TRATANDO OS ERROS PARA REGISTRAR ELES NO
         * OBJETO 'RESULT', QUE ARMAZENA TODOS OS ERROS
         * DOS CAMPOS DE CADASTRO */
        if (!erros.isEmpty()) {         
            System.out.println(erros);
            String[] listaErros = erros.split("\\$");
            for (int i = 0; i < listaErros.length; i += 3) {
                result.rejectValue(listaErros[i], listaErros[i + 1], listaErros[i + 2]);
            }
        }
		
		if (result.hasErrors()) {
			return "editarLivro";
		}
		
		this.livroDAO.save(livro);
		return "redirect:listarLivrosAdm";
	
	}

	@GetMapping("/adm/excluirLivro")
	public String excluirLivroAdm(Integer idLivro) {
		if (!(idLivro == null)) {
			this.livroDAO.deleteById(idLivro);
		}
		return "redirect:listarLivrosAdm";
	}

}
