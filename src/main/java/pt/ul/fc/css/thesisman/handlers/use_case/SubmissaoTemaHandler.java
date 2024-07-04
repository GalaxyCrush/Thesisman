package pt.ul.fc.css.thesisman.handlers.use_case;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.entities.AbsTema;
import pt.ul.fc.css.thesisman.entities.CriadorTema;
import pt.ul.fc.css.thesisman.entities.Docente;
import pt.ul.fc.css.thesisman.entities.Empresarial;
import pt.ul.fc.css.thesisman.entities.Mestrado;
import pt.ul.fc.css.thesisman.entities.TemaDissertacao;
import pt.ul.fc.css.thesisman.entities.TemaProjeto;
import pt.ul.fc.css.thesisman.exceptions.CriarTemaException;
import pt.ul.fc.css.thesisman.exceptions.EntidadeNotFoundException;
import pt.ul.fc.css.thesisman.exceptions.ParametroException;
import pt.ul.fc.css.thesisman.exceptions.TemaFoundException;
import pt.ul.fc.css.thesisman.repositories.CriadorTemaRepositorio;
import pt.ul.fc.css.thesisman.repositories.MestradoRepositorio;
import pt.ul.fc.css.thesisman.repositories.TemaRepositorio;

@Component
public class SubmissaoTemaHandler {

  @Autowired private TemaRepositorio temaRepositorio;
  @Autowired private CriadorTemaRepositorio criadorTemaRepositorio;
  @Autowired private MestradoRepositorio mestradoRepositorio;

  // FIXME - verificar o q passar como identificador do CriadroTema(ID vs NOME vs
  // EMAIL?) e TipoTese(ENUM vs STRING?)
  // FIXME - verifcar tipos dos parametros estao corretos
  // FIXME - maybe devolver o Tema apos save
  @Transactional
  public AbsTema submeterTemaDissertacao(
      String titulo,
      String descricao,
      Double renumeracaoMensal,
      List<Long> mestradosCompativeisIds,
      Long criadorTemaId) {

    if (!isFilled(titulo)) {
      throw new ParametroException("Titulo tema inválido");
    }

    if (!isFilled(descricao)) {
      throw new ParametroException("Descrição tema inválida");
    }

    if (renumeracaoMensal == null) {
      throw new ParametroException("Remuneração mensal inválida");
    }

    if (mestradosCompativeisIds == null) {
      throw new ParametroException("Tema tem de ser compativel com pelo menos um mestrado");
    }

    List<Mestrado> mestradosCompativeis = getMestradosCompativeis(mestradosCompativeisIds);

    if (mestradosCompativeis.isEmpty()) {
      throw new EntidadeNotFoundException("Mestrados não encontrados");
    }

    CriadorTema criadorTema =
        criadorTemaRepositorio
            .findDocenteById(criadorTemaId)
            .orElseThrow(() -> new EntidadeNotFoundException("Docente não encontrado"));

    if (temaRepositorio.existsByTitulo(titulo)) {
      throw new TemaFoundException("Já existe um Tema com este titulo");
    }

    AbsTema newTema =
        new TemaDissertacao(
            titulo, descricao, renumeracaoMensal, mestradosCompativeis, (Docente) criadorTema);

    try {
      criadorTemaRepositorio.save(criadorTema);
    } catch (OptimisticLockingFailureException e) {
      throw new CriarTemaException("Erro a criar tema. Por favor tente outra vez");
    }

    return temaRepositorio.save(newTema);
  }

  public AbsTema submeterTemaProjeto(
      String titulo,
      String descricao,
      Double renumeracaoMensal,
      List<Long> mestradosCompativeisIds,
      Long criadorTemaId,
      Long docenteId) {

    if (!isFilled(titulo)) throw new ParametroException("Titulo tema inválido");

    if (!isFilled(descricao)) throw new ParametroException("Descrição tema inválida");

    if (renumeracaoMensal == null) throw new ParametroException("Remuneração mensal inválida");

    if (mestradosCompativeisIds == null) {
      throw new ParametroException("Tema tem de ser compativel com pelo menos um mestrado");
    }

    List<Mestrado> mestradosCompativeis = getMestradosCompativeis(mestradosCompativeisIds);

    if (mestradosCompativeis.isEmpty()) {
      throw new EntidadeNotFoundException("Mestrados não encontrados");
    }

    Optional<CriadorTema> criadorTema = criadorTemaRepositorio.findEmpresarialById(criadorTemaId);

    if (criadorTema.isEmpty()) throw new EntidadeNotFoundException("Empresarial não encontrado");

    if (temaRepositorio.existsByTitulo(titulo))
      throw new TemaFoundException("Já existe um Tema com este titulo");

    Optional<CriadorTema> docenteEncarregueOpt = criadorTemaRepositorio.findDocenteById(docenteId);

    AbsTema newTema;
    newTema =
        docenteEncarregueOpt
            .map(
                tema ->
                    new TemaProjeto(
                        titulo,
                        descricao,
                        renumeracaoMensal,
                        mestradosCompativeis,
                        (Empresarial) criadorTema.get(),
                        (Docente) tema))
            .orElseGet(
                () ->
                    new TemaProjeto(
                        titulo,
                        descricao,
                        renumeracaoMensal,
                        mestradosCompativeis,
                        criadorTema.get(),
                        null));

    return temaRepositorio.save(newTema);
  }

  @Transactional
  public List<Mestrado> listarMestrados() {
    return mestradoRepositorio.findAll();
  }

  private boolean isFilled(String params) {
    return params != null && !params.isEmpty();
  }

  private List<Mestrado> getMestradosCompativeis(List<Long> mestradosCompativeisIds) {

    return mestradosCompativeisIds.stream()
        .map(mestradoRepositorio::findById)
        .map(
            optionalMestrado ->
                optionalMestrado.orElseThrow(
                    () -> new EntidadeNotFoundException("Mestrado não encontrado")))
        .collect(Collectors.toList());
  }
}
