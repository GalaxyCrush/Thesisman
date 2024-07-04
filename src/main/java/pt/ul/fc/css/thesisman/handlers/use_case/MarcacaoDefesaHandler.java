package pt.ul.fc.css.thesisman.handlers.use_case;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.entities.*;
import pt.ul.fc.css.thesisman.enums.Status;
import pt.ul.fc.css.thesisman.enums.TipoEntrega;
import pt.ul.fc.css.thesisman.exceptions.EntidadeNotFoundException;
import pt.ul.fc.css.thesisman.exceptions.InvalidDefesaException;
import pt.ul.fc.css.thesisman.repositories.CriadorTemaRepositorio;
import pt.ul.fc.css.thesisman.repositories.DefesaRepositorio;
import pt.ul.fc.css.thesisman.repositories.EntregaRepositorio;
import pt.ul.fc.css.thesisman.repositories.SalaRepositorio;

@Component
public class MarcacaoDefesaHandler {

  @Autowired private DefesaRepositorio defesaRepositorio;

  @Autowired private EntregaRepositorio entregaRepositorio;

  @Autowired private SalaRepositorio salaRepositorio;

  @Autowired private CriadorTemaRepositorio criadorTemaRepositorio;

  @Autowired private EntityManager entityManager;

  @Transactional
  public void marcarDefesaProposta(
      Long teseId,
      Long salaId,
      String data,
      String horaInicio,
      Long orientadorId,
      Long arguenteId) {
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate dataDefesa = LocalDate.parse(data, formatterDate);

    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime horaInicioDefesa = LocalTime.parse(horaInicio, formatterTime);

    List<Entrega> entregas = entregaRepositorio.findByTeseIdAndTipo(teseId, TipoEntrega.PROPOSTA);

    Optional<Entrega> entrega = entregas.stream().max(Comparator.comparing(Entrega::getId));

    if (entrega.isEmpty()) {
      throw new RuntimeException("Tese não tem entrega proposta válida");
    }

    Docente orientador =
        entityManager.find(Docente.class, orientadorId, LockModeType.PESSIMISTIC_WRITE);
    if (orientador == null) {
      throw new EntidadeNotFoundException("Utilizador não é orientador da tese");
    }

    Docente arguente =
        entityManager.find(Docente.class, arguenteId, LockModeType.PESSIMISTIC_WRITE);
    if (arguente == null) {
      throw new EntidadeNotFoundException("Arguente inválido");
    }

    long duracao = entrega.get().getTipoEntrega().getDuracao();

    if (!isDocenteDisponivel(orientador, dataDefesa, horaInicioDefesa, duracao))
      throw new InvalidDefesaException("Orientador indisponível nessa data a essa hora");

    if (!isDocenteDisponivel(arguente, dataDefesa, horaInicioDefesa, duracao))
      throw new InvalidDefesaException("Arguente indisponível nessa data a essa hora");

    Sala sala = null;
    if (salaId != -1) {
      sala = entityManager.find(Sala.class, salaId, LockModeType.PESSIMISTIC_WRITE);
      if (sala == null) {
        throw new EntidadeNotFoundException("Sala inválida");
      }
    }

    Defesa defesa =
        new Defesa(entrega.get(), sala, dataDefesa, horaInicioDefesa, orientador, arguente, null);
    defesaRepositorio.save(defesa);
  }

  @Transactional
  public void marcarDefesaFinal(
      Long teseId,
      Long salaId,
      String data,
      String horaInicio,
      Long orientadorId,
      Long arguenteId,
      Long presidenteJuriId) {

    if (arguenteId.equals(presidenteJuriId)) {
      throw new RuntimeException("Arguente e presidente júri não podem ser a mesma pessoa");
    }

    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate dataDefesa = LocalDate.parse(data, formatterDate);

    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime horaInicioDefesa = LocalTime.parse(horaInicio, formatterTime);

    List<Entrega> entregas = entregaRepositorio.findByTeseIdAndTipo(teseId, TipoEntrega.FINAL);

    if (entregas.isEmpty()) {
      throw new RuntimeException("Tese não tem entrega final válida");
    }

    Entrega entrega = entregas.get(entregas.size() - 1);

    Docente orientador =
        entityManager.find(Docente.class, orientadorId, LockModeType.PESSIMISTIC_WRITE);
    if (orientador == null) {
      throw new EntidadeNotFoundException("Utilizador não é orientador da tese");
    }

    Docente arguente =
        entityManager.find(Docente.class, arguenteId, LockModeType.PESSIMISTIC_WRITE);
    if (arguente == null) {
      throw new EntidadeNotFoundException("Arguente inválido");
    }

    Docente presidente =
        entityManager.find(Docente.class, presidenteJuriId, LockModeType.PESSIMISTIC_WRITE);
    if (presidente == null) {
      throw new EntidadeNotFoundException("Presidente júri inválido");
    }

    long duracao = entrega.getTipoEntrega().getDuracao();

    if (!isDocenteDisponivel(orientador, dataDefesa, horaInicioDefesa, duracao))
      throw new InvalidDefesaException("Orientador indisponível nessa data a essa hora");

    if (!isDocenteDisponivel(arguente, dataDefesa, horaInicioDefesa, duracao))
      throw new InvalidDefesaException("Arguente indisponível nessa data a essahora");

    if (!isDocenteDisponivel(presidente, dataDefesa, horaInicioDefesa, duracao))
      throw new InvalidDefesaException("Presidente júri indisponível nessa data a essa hora");

    Sala sala = null;
    if (salaId != -1) {
      sala = entityManager.find(Sala.class, salaId, LockModeType.PESSIMISTIC_WRITE);
      if (sala == null) {
        throw new EntidadeNotFoundException("Sala inválida");
      }
    }

    Defesa defesa =
        new Defesa(entrega, sala, dataDefesa, horaInicioDefesa, orientador, arguente, presidente);
    defesaRepositorio.save(defesa);
  }

  @Transactional
  public List<Sala> listarSalas() {
    return salaRepositorio.findAll();
  }

  @Transactional(readOnly = true)
  public List<AbsTese> listarTesesDeOrientador(Long orientadorId) {

    System.out.println("\n\n\norientadorId: " + orientadorId);

    Docente orientador =
        (Docente)
            criadorTemaRepositorio
                .findDocenteById(orientadorId)
                .orElseThrow(() -> new EntidadeNotFoundException("Docente não encontrado"));

    List<AbsTese> orientadas = orientador.getTesesOrientadas();

    System.out.println("\n\n\norientadas: " + orientadas);

    System.out.println(
        "\n\n\norientadas filtrada: "
            + orientadas.stream()
                .filter(
                    t ->
                        t.getStatus().equals(Status.PROPOSTA_ENTREGUE)
                            || t.getStatus().equals(Status.FINAL_ENTREGUE))
                .toList());

    return orientadas.stream()
        .filter(
            t ->
                t.getStatus().equals(Status.PROPOSTA_ENTREGUE)
                    || t.getStatus().equals(Status.FINAL_ENTREGUE))
        .toList();
  }

  private boolean isDocenteDisponivel(
      Docente docente, LocalDate dataDefesa, LocalTime horaInicioDefesa, long duracaoDefesa) {

    Docente lockedDocente =
        entityManager.find(Docente.class, docente.getId(), LockModeType.PESSIMISTIC_WRITE);

    List<Defesa> combinedList = new ArrayList<>();
    combinedList.addAll(docente.getDefesasArguente());
    combinedList.addAll(docente.getDefesasOrientador());
    combinedList.addAll(docente.getDefesasPresidente());

    LocalTime horaFimDefesa = horaInicioDefesa.plusMinutes(duracaoDefesa);

    for (Defesa d : combinedList) {
      LocalDate data = d.getData();
      LocalTime horaInicio = d.getHoraInicio();
      int duracao = d.getDuracao();

      LocalTime horaFim = horaInicio.plusMinutes(duracao);

      if (!dataDefesa.equals(data)) {
        continue;
      }

      if (!horaFimDefesa.isBefore(horaInicio) && !horaInicioDefesa.isAfter(horaFim)) {
        return false;
      }
    }
    return true;
  }
}
