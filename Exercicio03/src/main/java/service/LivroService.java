package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.File;
import java.util.List;
import dao.LivroDAO;
import model.Livro;
import spark.Request;
import spark.Response;

public class LivroService {
    
    private LivroDAO livroDAO = new LivroDAO();
    private String form;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_TITULO = 2;
    private final int FORM_ORDERBY_AUTOR = 3;
    private final int FORM_ORDERBY_PRECO = 4;
    
    public LivroService() {
        makeForm();
    }

    public void makeForm() {
        makeForm(FORM_INSERT, new Livro(), FORM_ORDERBY_TITULO);
    }

    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Livro(), orderBy);
    }

    public void makeForm(int tipo, Livro livro, int orderBy) {
        String nomeArquivo = "form.html";
        form = "";
        try {
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while(entrada.hasNext()) {
                form += (entrada.nextLine() + "\n");
            }
            entrada.close();
        } catch (Exception e) { 
            System.out.println(e.getMessage()); 
        }
        
        String umLivro = "";
        if(tipo != FORM_INSERT) {
            umLivro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/livro/list/1\">Novo Livro</a></b></font></td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t</table>";
            umLivro += "\t<br>";
        }
        
        if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
            String action = "/livro/";
            String name, titulo, autor, isbn, buttonLabel;
            if (tipo == FORM_INSERT) {
                action += "insert";
                name = "Cadastrar Livro";
                titulo = "Dom Casmurro";
                autor = "Machado de Assis";
                isbn = "978-85-254-0000-0";
                buttonLabel = "Cadastrar";
            } else {
                action += "update/" + livro.getId();
                name = "Atualizar Livro (ID " + livro.getId() + ")";
                titulo = livro.getTitulo();
                autor = livro.getAutor();
                isbn = livro.getIsbn();
                buttonLabel = "Atualizar";
            }
            
            umLivro += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
            umLivro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td>&nbsp;Titulo: <input class=\"input--register\" type=\"text\" name=\"titulo\" value=\""+ titulo +"\"></td>";
            umLivro += "\t\t\t<td>Autor: <input class=\"input--register\" type=\"text\" name=\"autor\" value=\""+ autor +"\"></td>";
            umLivro += "\t\t\t<td>ISBN: <input class=\"input--register\" type=\"text\" name=\"isbn\" value=\""+ isbn +"\"></td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td>&nbsp;Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ livro.getPreco() +"\"></td>";
            umLivro += "\t\t\t<td>Paginas: <input class=\"input--register\" type=\"text\" name=\"paginas\" value=\""+ livro.getPaginas() +"\"></td>";
            umLivro += "\t\t\t<td>Data Publicacao: <input class=\"input--register\" type=\"date\" name=\"dataPublicacao\" value=\""+ livro.getDataPublicacao().toString() + "\"></td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td colspan=\"2\">&nbsp;</td>";
            umLivro += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t</table>";
            umLivro += "\t</form>";
        } else if (tipo == FORM_DETAIL) {
            umLivro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhes do Livro (ID " + livro.getId() + ")</b></font></td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td>&nbsp;Titulo: "+ livro.getTitulo() +"</td>";
            umLivro += "\t\t\t<td>Autor: "+ livro.getAutor() +"</td>";
            umLivro += "\t\t\t<td>ISBN: "+ livro.getIsbn() +"</td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t\t<tr>";
            umLivro += "\t\t\t<td>&nbsp;Preco: R$ "+ livro.getPreco() +"</td>";
            umLivro += "\t\t\t<td>Paginas: "+ livro.getPaginas() +"</td>";
            umLivro += "\t\t\t<td>Data Publicacao: "+ livro.getDataPublicacao().toString() + "</td>";
            umLivro += "\t\t</tr>";
            umLivro += "\t</table>";
        }
        
        // CORREÇÃO: Usar replace ao invés de replaceFirst para evitar problemas com caracteres especiais
        form = form.replace("<UM-LIVRO>", umLivro);
        
        String list = "<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">";
        list += "\n<tr><td colspan=\"7\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Biblioteca - Catalogo de Livros</b></font></td></tr>\n" +
                "\n<tr><td colspan=\"7\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" + 
                "\t<td><a href=\"/livro/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/livro/list/" + FORM_ORDERBY_TITULO + "\"><b>Titulo</b></a></td>\n" +
                "\t<td><a href=\"/livro/list/" + FORM_ORDERBY_AUTOR + "\"><b>Autor</b></a></td>\n" +
                "\t<td><a href=\"/livro/list/" + FORM_ORDERBY_PRECO + "\"><b>Preco</b></a></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Detalhes</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Editar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
                "</tr>\n";
        
        List<Livro> livros;
        if (orderBy == FORM_ORDERBY_ID) {
            livros = livroDAO.getOrderByID();
        } else if (orderBy == FORM_ORDERBY_TITULO) {
            livros = livroDAO.getOrderByTitulo();
        } else if (orderBy == FORM_ORDERBY_AUTOR) {
            livros = livroDAO.getOrderByAutor();
        } else if (orderBy == FORM_ORDERBY_PRECO) {
            livros = livroDAO.getOrderByPreco();
        } else {
            livros = livroDAO.get();
        }

        int i = 0;
        String bgcolor = "";
        for (Livro l : livros) {
            bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
                    "\t<td>" + l.getId() + "</td>\n" +
                    "\t<td>" + l.getTitulo() + "</td>\n" +
                    "\t<td>" + l.getAutor() + "</td>\n" +
                    "\t<td>R$ " + l.getPreco() + "</td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/livro/" + l.getId() + "\">Ver</a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/livro/update/" + l.getId() + "\">Editar</a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteLivro('" + l.getId() + "', '" + escaparAspas(l.getTitulo()) + "', '" + escaparAspas(l.getAutor()) + "');\">Excluir</a></td>\n" +
                    "</tr>\n";
        }
        list += "</table>";
        
        // CORREÇÃO: Usar replace ao invés de replaceFirst
        form = form.replace("<LISTAR-LIVRO>", list);
    }
    
    // Método auxiliar para escapar aspas em strings JavaScript
    private String escaparAspas(String texto) {
        if (texto == null) return "";
        return texto.replace("'", "\\'").replace("\"", "\\\"");
    }
    
    public Object insert(Request request, Response response) {
        String titulo = request.queryParams("titulo");
        String autor = request.queryParams("autor");
        String isbn = request.queryParams("isbn");
        float preco = Float.parseFloat(request.queryParams("preco"));
        int paginas = Integer.parseInt(request.queryParams("paginas"));
        LocalDate dataPublicacao = LocalDate.parse(request.queryParams("dataPublicacao"));
        
        String resp = "";
        
        Livro livro = new Livro(-1, titulo, autor, isbn, preco, paginas, dataPublicacao, LocalDateTime.now());
        
        if(livroDAO.insert(livro)) {
            resp = "Livro (" + titulo + ") cadastrado!";
            response.status(201);
        } else {
            resp = "Livro (" + titulo + ") nao foi cadastrado!";
            response.status(404);
        }
            
        makeForm();
        return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", 
                           "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Livro livro = livroDAO.get(id);
        
        if (livro != null) {
            response.status(200);
            makeForm(FORM_DETAIL, livro, FORM_ORDERBY_TITULO);
        } else {
            response.status(404);
            String resp = "Livro " + id + " nao encontrado.";
            makeForm();
            form = form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", 
                              "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
        }

        return form;
    }

    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Livro livro = livroDAO.get(id);
        
        if (livro != null) {
            response.status(200);
            makeForm(FORM_UPDATE, livro, FORM_ORDERBY_TITULO);
        } else {
            response.status(404);
            String resp = "Livro " + id + " nao encontrado.";
            makeForm();
            form = form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", 
                              "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
        }

        return form;
    }
    
    public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeForm(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return form;
    }

    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Livro livro = livroDAO.get(id);
        String resp = "";

        if (livro != null) {
            livro.setTitulo(request.queryParams("titulo"));
            livro.setAutor(request.queryParams("autor"));
            livro.setIsbn(request.queryParams("isbn"));
            livro.setPreco(Float.parseFloat(request.queryParams("preco")));
            livro.setPaginas(Integer.parseInt(request.queryParams("paginas")));
            livro.setDataPublicacao(LocalDate.parse(request.queryParams("dataPublicacao")));
            livroDAO.update(livro);
            response.status(200);
            resp = "Livro (ID " + livro.getId() + ") atualizado!";
        } else {
            response.status(404);
            resp = "Livro (ID " + id + ") nao encontrado!";
        }
        makeForm();
        return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", 
                           "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }

    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Livro livro = livroDAO.get(id);
        String resp = "";

        if (livro != null) {
            livroDAO.delete(id);
            response.status(200);
            resp = "Livro (" + id + ") excluido!";
        } else {
            response.status(404);
            resp = "Livro (" + id + ") nao encontrado!";
        }
        makeForm();
        return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", 
                           "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }
}