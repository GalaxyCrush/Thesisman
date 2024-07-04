package pt.ul.fc.css.thesisman.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.thesisman.entities.*;
import pt.ul.fc.css.thesisman.enums.TipoEntrega;
import pt.ul.fc.css.thesisman.repositories.*;

@Service
public class PopulateService {

    @Autowired
    private AlunoRepositorio alunoRepositorio;
    @Autowired
    private CandidaturaRepositorio candidaturaRepositorio;
    @Autowired
    private CriadorTemaRepositorio criadorTemaRepositorio;
    @Autowired
    private DefesaRepositorio defesaRepositorio;
    @Autowired
    private DissertacaoRepositorio dissertacaoRepositorio;
    @Autowired
    private EntregaRepositorio entregaRepositorio;
    @Autowired
    private MestradoRepositorio mestradoRepositorio;
    @Autowired
    private ProjetoRepositorio projetoRepositorio;
    @Autowired
    private SalaRepositorio salaRepositorio;
    @Autowired
    private TemaRepositorio temaRepositorio;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PopulateService() {
        super();
    }

    @Transactional
    public void populate() {
        // System.out.println("\\\\\\\1\\\\\\\\\\\\");
        // candidaturaRepositorio.deleteAll();
        // System.out.println("\\\\\\\2\\\\\\\\\\\\");
        // dissertacaoRepositorio.deleteAll();
        // for (AbsTema t : temaRepositorio.findAll()) {
        // t.cleanMestrados();
        // }
        // System.out.println("\\\\\\\3\\\\\\\\\\\\");
        // temaRepositorio.deleteAll();
        // System.out.println("\\\\\\\4\\\\\\\\\\\\");
        // entregaRepositorio.deleteAll();
        // System.out.println("\\\\\\\5\\\\\\\\\\\\");
        // defesaRepositorio.deleteAll();
        // System.out.println("-----------------8----------------- ");
        // alunoRepositorio.deleteAll();
        // System.out.println("-----------------9----------------- ");
        // mestradoRepositorio.deleteAll();
        // System.out.println("-----------------10----------------- ");
        // criadorTemaRepositorio.deleteAll();
        // System.out.println("----------------11----------------- ");
        // salaRepositorio.deleteAll();

        // --------------- criar salas --------------- //
        System.out.println("\n\n\n--------------- Criar salas ---------------");

        Sala s1 = new Sala("C1.1.1");
        Sala s2 = new Sala("C1.1.2");
        Sala s3 = new Sala("C1.1.3");
        Sala s4 = new Sala("C1.1.4");
        Sala s5 = new Sala("C1.1.5");
        s1 = salaRepositorio.save(s1);
        s2 = salaRepositorio.save(s2);
        s3 = salaRepositorio.save(s3);
        s4 = salaRepositorio.save(s4);
        s5 = salaRepositorio.save(s5);

        // --------------- criar docentes --------------- //
        System.out.println("\n\n\n--------------- Criar docentes ---------------");

        Docente d1 = new Docente("Docente1", "docente1@gmail.com", "12345", "Docente de Informática");
        Docente d2 = new Docente("Docente2", "docente2@gmail.com", "12345", "Docente de Biologia");
        Docente d3 = new Docente("Docente3", "docente3@gmail.com", "12345", "Docente de Fisica");
        Docente d4 = new Docente("Docente4", "docente4@gmail.com", "12345", "Docente de Psicologia");
        Empresarial e1 = new Empresarial("Empresa 1", "empresarial@gmail.com", "12345", "Empresa de Informática");
        d1 = criadorTemaRepositorio.save(d1);
        d2 = criadorTemaRepositorio.save(d2);
        d3 = criadorTemaRepositorio.save(d3);
        d4 = criadorTemaRepositorio.save(d4);
        e1 = criadorTemaRepositorio.save(e1);

        // --------------- criar mestrados --------------- //
        System.out.println("\n\n\n--------------- Criar mestrados ---------------");

        Mestrado m1 = new Mestrado("Engenharia Informática", d1, "DI");
        Mestrado m2 = new Mestrado("Biologia", d2, "DB");
        Mestrado m3 = new Mestrado("Fisica", d3, "DF");
        Mestrado m4 = new Mestrado("Psicologia", d4, "DP");
        m1 = mestradoRepositorio.save(m1);
        m2 = mestradoRepositorio.save(m2);
        m3 = mestradoRepositorio.save(m3);
        m4 = mestradoRepositorio.save(m4);
        List<Mestrado> mestradosCompativeis = mestradoRepositorio.findAll();

        // --------------- criar alunos --------------- //
        System.out.println("\n\n\n--------------- Criar alunos ---------------");

        Aluno a1 = new Aluno("Aluno1", "aluno1@gmail.com", "12345", 15.0, m1);
        a1 = alunoRepositorio.save(a1);

        Aluno a2 = new Aluno("Aluno2", "aluno2@gmail.com", "12345", 15.0, m1);
        a2 = alunoRepositorio.save(a2);

        Aluno a3 = new Aluno("Aluno3", "aluno3@gmail.com", "12345", 15.0, m1);
        a3 = alunoRepositorio.save(a3);

        Aluno a4 = new Aluno("Aluno4", "aluno4@gmail.com", "12345", 15.0, m1);
        a4 = alunoRepositorio.save(a4);

        Aluno a5 = new Aluno("Aluno5", "aluno5@gmail.com", "12345", 15.0, m1);
        a5 = alunoRepositorio.save(a5);

        Aluno semTema = new Aluno("Aluno6", "aluno6@gmail.com", "12345", 15.0, m1);
        semTema = alunoRepositorio.save(semTema);

        // --------------- criar temas --------------- //
        System.out.println("\n\n\n--------------- Criar temas ---------------");

        AbsTema tema1 = new TemaDissertacao("Tema 1", "Descrição 1", 1100.0, mestradosCompativeis, d1);
        AbsTema tema2 = new TemaProjeto("Tema 2", "Descrição 2", 1200.0, mestradosCompativeis, e1, d1);
        AbsTema tema3 = new TemaDissertacao("Tema 3", "Descrição 3", 1000.0, mestradosCompativeis, d2);
        AbsTema tema4 = new TemaProjeto("Tema 4", "Descrição 4", 1300.0, mestradosCompativeis, e1, d4);
        AbsTema tema5 = new TemaDissertacao("Tema 5", "Descrição 5", 1400.0, mestradosCompativeis, d3);
        AbsTema tema6 = new TemaProjeto("Tema 6", "Descrição 6", 1500.0, mestradosCompativeis, e1, null);

        tema1 = temaRepositorio.save(tema1);
        tema2 = temaRepositorio.save(tema2);
        tema3 = temaRepositorio.save(tema3);
        tema4 = temaRepositorio.save(tema4);
        tema5 = temaRepositorio.save(tema5);
        tema6 = temaRepositorio.save(tema6);
        d1 = (Docente) criadorTemaRepositorio.save(d1);
        d2 = (Docente) criadorTemaRepositorio.save(d2);
        d3 = (Docente) criadorTemaRepositorio.save(d3);
        d4 = (Docente) criadorTemaRepositorio.save(d4);
        e1 = (Empresarial) criadorTemaRepositorio.save(e1);
        m1 = mestradoRepositorio.save(m1);
        m2 = mestradoRepositorio.save(m2);
        m3 = mestradoRepositorio.save(m3);
        m4 = mestradoRepositorio.save(m4);

        // --------------- caso 1 = aluno com tese em progresso --------------- //
        System.out.println(
                "\n\n\n--------------- Caso 1 = aluno com tese em progresso ---------------");

        Candidatura c1 = new Candidatura(tema1, a1);
        c1 = candidaturaRepositorio.save(c1);

        AbsTese tese1 = new Dissertacao(a1, (TemaDissertacao) tema1);
        tese1 = dissertacaoRepositorio.save((Dissertacao) tese1);
        tema1 = temaRepositorio.save(tema1);
        a1 = alunoRepositorio.save(a1);

        // --------------- caso 2 = aluno com tese com proposta entrega e para marcar
        // defesa
        // --------------- //
        System.out.println(
                "\n\n\n--------------- Caso 2 = aluno com tese com proposta entrega e para marcar defesa ---------------");

        Candidatura c2 = new Candidatura(tema2, a2);
        c2 = candidaturaRepositorio.save(c2);

        AbsTese tese2 = new Projeto(a2, (TemaProjeto) tema2);
        tese2 = projetoRepositorio.save((Projeto) tese2);
        tema2 = temaRepositorio.save(tema2);
        a2 = alunoRepositorio.save(a2);

        byte[] fc = new byte[10];

        Entrega en1 = new Entrega(tese2, TipoEntrega.PROPOSTA, fc, "entrega_proposta_aluno2.txt");
        en1 = entregaRepositorio.save(en1);
        tese2 = projetoRepositorio.save((Projeto) tese2);

        // --------------- caso 3 = aluno com defesa para avaliar --------------- //
        System.out.println(
                "\n\n\n--------------- Caso 3 = aluno com defesa para avaliar ---------------");

        Candidatura c3 = new Candidatura(tema3, a3);
        c3 = candidaturaRepositorio.save(c3);

        AbsTese tese3 = new Dissertacao(a3, (TemaDissertacao) tema3);
        tese3 = dissertacaoRepositorio.save((Dissertacao) tese3);
        tema3 = temaRepositorio.save(tema3);
        a3 = alunoRepositorio.save(a3);

        Entrega en2 = new Entrega(tese3, TipoEntrega.PROPOSTA, fc, "entrega_proposta_aluno3.txt");
        en2 = entregaRepositorio.save(en2);
        tese3 = dissertacaoRepositorio.save((Dissertacao) tese3);

        Defesa defesa1 = new Defesa(en2, s1, LocalDate.of(2024, 5, 1), LocalTime.of(14, 0), d2, d1, null);
        defesa1 = defesaRepositorio.save(defesa1);
        d1 = (Docente) criadorTemaRepositorio.save(d1);
        d2 = (Docente) criadorTemaRepositorio.save(d2);
        en2 = entregaRepositorio.save(en2);
        tese3 = dissertacaoRepositorio.save((Dissertacao) tese3);
        s1 = salaRepositorio.save(s1);

        // --------------- caso 4 = aluno com defesa final para marcar ---------------
        // //
        System.out.println(
                "\n\n\n--------------- Caso 4 = aluno com defesa final para marcar ---------------");

        Candidatura c4 = new Candidatura(tema4, a4);
        c3 = candidaturaRepositorio.save(c3);

        AbsTese tese4 = new Projeto(a4, (TemaProjeto) tema4);
        tese4 = projetoRepositorio.save((Projeto) tese4);
        tema4 = temaRepositorio.save(tema4);
        a4 = alunoRepositorio.save(a4);

        Entrega en3 = new Entrega(tese4, TipoEntrega.PROPOSTA, fc, "entrega_proposta_aluno3.txt");
        en3 = entregaRepositorio.save(en3);
        tese4 = projetoRepositorio.save((Projeto) tese4);

        Defesa defesa2 = new Defesa(en3, s2, LocalDate.of(2024, 5, 1), LocalTime.of(14, 0), d4, d2, null);
        defesa2 = defesaRepositorio.save(defesa2);
        d4 = (Docente) criadorTemaRepositorio.save(d4);
        d2 = (Docente) criadorTemaRepositorio.save(d2);
        en2 = entregaRepositorio.save(en2);
        tese4 = projetoRepositorio.save((Projeto) tese4);
        s2 = salaRepositorio.save(s2);

        defesa2.setNota(18);
        defesa2 = defesaRepositorio.save(defesa2);
        tese4 = projetoRepositorio.save((Projeto) tese4);

        Entrega en3_f = new Entrega(tese4, TipoEntrega.FINAL, fc, "entrega_final_aluno4.txt");
        en3_f = entregaRepositorio.save(en3_f);
        tese4 = projetoRepositorio.save((Projeto) tese4);

        // --------------- caso 5 = aluno com defesa final avaliada --------------- //
        System.out.println(
                "\n\n\n--------------- Caso 5 = aluno com defesa final avaliada ---------------");

        Candidatura c5 = new Candidatura(tema5, a5);
        c5 = candidaturaRepositorio.save(c5);

        AbsTese tese5 = new Dissertacao(a5, (TemaDissertacao) tema5);
        tese5 = dissertacaoRepositorio.save((Dissertacao) tese5);
        tema5 = temaRepositorio.save(tema5);
        a5 = alunoRepositorio.save(a5);

        Entrega en4 = new Entrega(tese5, TipoEntrega.PROPOSTA, fc, "entrega_proposta_aluno5.txt");
        en4 = entregaRepositorio.save(en4);
        tese5 = dissertacaoRepositorio.save((Dissertacao) tese5);

        Defesa defesa3 = new Defesa(en4, s3, LocalDate.of(2024, 5, 1), LocalTime.of(14, 0), d3, d2, null);
        defesa3 = defesaRepositorio.save(defesa3);
        d3 = (Docente) criadorTemaRepositorio.save(d3);
        d2 = (Docente) criadorTemaRepositorio.save(d2);
        en4 = entregaRepositorio.save(en4);
        tese5 = dissertacaoRepositorio.save((Dissertacao) tese5);
        s3 = salaRepositorio.save(s3);

        defesa3.setNota(16);
        defesa3 = defesaRepositorio.save(defesa3);
        tese5 = dissertacaoRepositorio.save((Dissertacao) tese5);

        Entrega en4_f = new Entrega(tese5, TipoEntrega.FINAL, fc, "entrega_final_aluno5.txt");
        en4_f = entregaRepositorio.save(en4_f);
        tese5 = dissertacaoRepositorio.save((Dissertacao) tese5);

        Defesa defesa3_f = new Defesa(en4_f, s3, LocalDate.of(2024, 10, 10), LocalTime.of(14, 0), d3, d2, d4);
        defesa3_f = defesaRepositorio.save(defesa3_f);
        d3 = (Docente) criadorTemaRepositorio.save(d3);
        d2 = (Docente) criadorTemaRepositorio.save(d2);
        d4 = (Docente) criadorTemaRepositorio.save(d4);
        en4_f = entregaRepositorio.save(en4_f);
        tese5 = dissertacaoRepositorio.save((Dissertacao) tese5);
        s3 = salaRepositorio.save(s3);

        defesa3_f.setNota(18);
        defesa3_f = defesaRepositorio.save(defesa3_f);
        tese5 = dissertacaoRepositorio.save((Dissertacao) tese5);
    }
}
