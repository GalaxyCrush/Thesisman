package pt.ul.fc.css.thesisman.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.ul.fc.css.thesisman.dtos.CandidaturaDTO;
import pt.ul.fc.css.thesisman.dtos.EntregaDTO;
import pt.ul.fc.css.thesisman.dtos.RespostaLoginAluno;
import pt.ul.fc.css.thesisman.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.exceptions.AlunoTeseException;
import pt.ul.fc.css.thesisman.exceptions.CancelarCandidaturaException;
import pt.ul.fc.css.thesisman.exceptions.CriarCandidaturaException;
import pt.ul.fc.css.thesisman.exceptions.CriarEntregaException;
import pt.ul.fc.css.thesisman.exceptions.EntidadeNotFoundException;
import pt.ul.fc.css.thesisman.exceptions.InvalidCandidaturaException;
import pt.ul.fc.css.thesisman.exceptions.LoginException;
import pt.ul.fc.css.thesisman.exceptions.MestradoIncompativelException;
import pt.ul.fc.css.thesisman.exceptions.ParametroException;
import pt.ul.fc.css.thesisman.exceptions.TeseEstadoException;
import pt.ul.fc.css.thesisman.services.CancelarCandidaturaService;
import pt.ul.fc.css.thesisman.services.CriarCandidaturaService;
import pt.ul.fc.css.thesisman.services.ListarCandidaturasService;
import pt.ul.fc.css.thesisman.services.ListarTemasService;
import pt.ul.fc.css.thesisman.services.LoginService;
import pt.ul.fc.css.thesisman.services.PopulateService;
import pt.ul.fc.css.thesisman.services.SubmissaoDocumentoService;

@RestController()
@RequestMapping("/api")
public class RestAPIController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private ListarTemasService listarTemasService;
    @Autowired
    private CriarCandidaturaService criarCandidaturaService;
    @Autowired
    private SubmissaoDocumentoService submissaoDocumentoService;
    @Autowired
    private CancelarCandidaturaService cancelarCandidaturaService;
    @Autowired
    private PopulateService populateService;
    @Autowired
    private ListarCandidaturasService listarCandidaturasService;

    @Operation(summary = "Efetuar login", description = "Efetuar login de aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RespostaLoginAluno.class))}),
            @ApiResponse(responseCode = "400", description = "Campos não preenchidos corretamente",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "502", description = "Erro no servidor",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = {@Content(mediaType = "text/plain")})})
    @PostMapping("/login")
    ResponseEntity<?> login(@Parameter(description = "Um JSON com as informações dos campos de login da parte do frontend", schema = @Schema(implementation = LoginDTO.class)) @RequestBody String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(json);
            String email = node.get("email").asText();
            String password = node.get("password").asText();
            RespostaLoginAluno resposta = loginService.loginAluno(email, password);
            return new ResponseEntity<>(resposta, HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (ParametroException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @Operation(summary = "Popular a BD", description = "Popular a base de dados com dados de teste")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Base de dados populada com sucesso",
                    content = {@Content(mediaType = "text/plain")})})
    @GetMapping("/populate")
    ResponseEntity<?> populate() {
        System.out.println("Populating");
        populateService.populate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Listar Temas", description = "Listar temas disponíveis para um dado ano letivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temas listados com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TemaDTO.class)))})})
    @GetMapping("/temas")
    ResponseEntity<?> listarTemasDisponivelAno(@Parameter(description = "Ano letivo para a qual queremos listar os Temas disponiveis") @RequestParam String ano) {
        return new ResponseEntity<>(listarTemasService.listarTemasAnoLetivo(ano), HttpStatus.OK);
    }

    @Operation(summary = "Listar candidaturas de aluno", description = "Listar candidaturas de um dado aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidaturas foram listadas com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CandidaturaDTO.class)))})})
    @GetMapping("/candidaturas/{alunoId}")
    ResponseEntity<?> listarCandidaturasAluno(@Parameter(description = "Id do aluno que queremos listar as candidaturas") @PathVariable Long alunoId) {
        return new ResponseEntity<>(
                listarCandidaturasService.listarCandidaturasAluno(alunoId), HttpStatus.OK);
    }

    @Operation(summary = "Criar uma candidatura", description = "Criar uma candidatura para um dado aluno e tema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidatura efetuada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(type = "string"))}),
            @ApiResponse(responseCode = "412", description = "Aluno já se candidatou a este tema",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "412", description = "Aluno excedeu o limite de candidaturas",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "405", description = "Aluno já se candidatou a esse tema",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "303", description = "Aluno não encontrado",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "400", description = "Mestrado do aluno não é compatível com o tema",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "502", description = "Erro no servidor",
                    content = {@Content(mediaType = "text/plain")})
    })
    @PostMapping("/candidatura")
    ResponseEntity<?> criarCandidatura(@Parameter(description = "Um JSON com o ID do aluno que está a fazer a candidatura e o ID do Tema pretendido", schema = @Schema(implementation = CandidaturaOperationDTO.class)) @RequestBody String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(json);
            Long aId = node.get("alunoId").asLong();

            Long temaId = node.get("temaId").asLong();
            criarCandidaturaService.criarCandidatura(aId, temaId);
            return new ResponseEntity<>("Candidatura criada com sucesso", HttpStatus.OK);
        } catch (CriarCandidaturaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
        } catch (InvalidCandidaturaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        } catch (EntidadeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SEE_OTHER);
        } catch (MestradoIncompativelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @Operation(summary = "Cancelar candidatura", description = "Cancelar uma candidatura de um aluno a um tema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CandidaturaDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Candidatura não encontrada",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "400", description = "Erro ao cancelar candidatura",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "502", description = "Erro no servidor",
                    content = {@Content(mediaType = "text/plain")})
    })
    @DeleteMapping("/candidatura/{candidaturaId}")
    ResponseEntity<?> cancelarCandidatura(@Parameter(description = "O ID da candidatura que queremos cancelar") @PathVariable Long candidaturaId) {
        try {
            List<CandidaturaDTO> candidaturas = cancelarCandidaturaService.cancelarCandidatura(candidaturaId);
            return new ResponseEntity<>(candidaturas, HttpStatus.OK);
        } catch (EntidadeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CancelarCandidaturaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @Operation(summary = "Submeter um documento de Proposta de tese", description = "Submeter um documento de proposta de tese para um dado aluno e tese")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento submetido com sucesso",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EntregaDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Erro ao submeter documento",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "400", description = "Aluno não tem tese associada",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "400", description = "Tese não está no estado correto",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "400", description = "Erro ao criar entrega",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível",
                    content = {@Content(mediaType = "text/plain")})
    })
    @PostMapping("/tese/{id}/propostas")
    public ResponseEntity<?> submeterDocumentoPropostaTese(
            @Parameter(description = "Id do aluno a mandar o documento") @PathVariable Long id,
            @Parameter(description = "O ficheiro que o aluno entregou para a proposta", schema = @Schema(type = "string", format = "binary")) @RequestPart("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(
                    submissaoDocumentoService.submeterDocumentoPropostaTese(id, file.getBytes(), file.getName()),
                    HttpStatus.OK);
        } catch (ParametroException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntidadeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlunoTeseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TeseEstadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CriarEntregaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
    }

    @Operation(summary = "Submeter um documento final de tese", description = "Submeter um documento final de tese para um dado aluno e tese")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento submetido com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EntregaDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao submeter documento",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "400", description = "Aluno não tem tese associada",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "400", description = "Tese não está no estado correto",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "400", description = "Erro ao criar entrega",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível",
                    content = {@Content(mediaType = "text/plain")})
    })
    @PostMapping("/tese/{id}/final")
    ResponseEntity<?> submeterDocumentoTese(@Parameter(description = "Id do aluno a mandar o documento") @PathVariable Long id,
                                            @Parameter(description = "O ficheiro que o aluno entregou a final da Tese", schema = @Schema(type = "string", format = "binary")) @RequestPart("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(submissaoDocumentoService.submeterDocumentoFinalTese(id, file.getBytes(),
                    file.getOriginalFilename()), HttpStatus.OK);
        } catch (ParametroException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntidadeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlunoTeseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TeseEstadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CriarEntregaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
    }

    private static class LoginDTO {
        private String email;
        private String password;

        public LoginDTO(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    private static class CandidaturaOperationDTO {
        private Long alunoId;
        private Long temaId;

        public CandidaturaOperationDTO(Long alunoId, Long temaId) {
            this.alunoId = alunoId;
            this.temaId = temaId;
        }

        public Long getAlunoId() {
            return alunoId;
        }

        public Long getTemaId() {
            return temaId;
        }
    }
}
