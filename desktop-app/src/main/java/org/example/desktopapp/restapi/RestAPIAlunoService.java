package org.example.desktopapp.restapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.Alert;
import org.example.desktopapp.dtos.CandidaturaDTO;
import org.example.desktopapp.dtos.RespostaLoginAluno;
import org.example.desktopapp.dtos.TemaDTO;
import org.example.desktopapp.dtos.EntregaDTO;
import org.example.desktopapp.utils.Utils;

public class RestAPIAlunoService {

  private RespostaLoginAluno resposta;
  private List<TemaDTO> temas;
  private List<CandidaturaDTO> candidaturas;
  private static RestAPIAlunoService instance;
  private static final String URL = "http://localhost:8080/api";
  private final URL CSS = Objects.requireNonNull(getClass().getResource("/org/example/desktopapp/css/alert.css"));
  private RestAPIAlunoService() {}

  public static RestAPIAlunoService getInstance() {
    if (instance == null) {
      instance = new RestAPIAlunoService();
    }
    return instance;
  }

  public void populate(){
    try {
      URL url = new URL(URL + "/populate");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);
      int code = conn.getResponseCode();
      if (code != HttpURLConnection.HTTP_OK) {
        Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
      }
    } catch (Exception e) {
      Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
      System.out.println("Erro: " + e.getMessage());
    }
  }

  public boolean login(String mail, String password) {
    try {
      URL url = new URL(URL + "/login");
      System.out.println("URL: " + url);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);

      String pedido = "{\"email\":\"" + mail + "\",\"password\":\"" + password + "\"}";
      System.out.println("Pedido: " + pedido);
      OutputStream out = conn.getOutputStream();
      out.write(pedido.getBytes(StandardCharsets.UTF_8));
      out.close();

      int code = conn.getResponseCode();
      System.out.println("Code: " + code);
      if (code == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        ObjectMapper mapper = new ObjectMapper();
        this.resposta = mapper.readValue(content.toString(), RespostaLoginAluno.class);
        Utils.showAlertDialog("Sucesso", "Login efetuado com sucesso", null, Alert.AlertType.INFORMATION, CSS);
        return true;
      } else {
        Utils.showAlertDialog("Erro", handleErros(conn), null, Alert.AlertType.ERROR, CSS);
        return false;
      }
    } catch (Exception e) {
      Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
      System.out.println("Erro: " + e.getMessage());
      return false;
    }
  }

  public void logout() {
    this.resposta = null;
  }

  public List<TemaDTO> listarTemasDisponivelAno(String ano) {
    try {
      URL url = new URL(URL + "/temas?ano=" + URLEncoder.encode(ano, StandardCharsets.UTF_8));
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);
      int code = conn.getResponseCode();
      System.out.println("Code: " + code);
      if (code == HttpURLConnection.HTTP_OK) {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
        br.close();
        conn.disconnect();
        ObjectMapper mapper = new ObjectMapper();
        this.temas = mapper.readValue(response.toString(), new TypeReference<List<TemaDTO>>() {});
        return this.temas;
      } else {
        Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
        System.out.println("Erro: " + conn.getResponseMessage());
      }
    } catch (Exception e) {
      Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
      System.out.println("Erro: " + e.getMessage());
    }
    return null;
  }

  public void criarCandidatura(Long temaId) {
    try {
      URL url = new URL(URL + "/candidatura");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);
      String pedido = "{\"alunoId\":\"" + this.resposta.getAluno().getId() + "\",\"temaId\":" + temaId + "}";
      System.out.println("Pedido: " + pedido);
      OutputStream out = conn.getOutputStream();
      out.write(pedido.getBytes(StandardCharsets.UTF_8));
      out.close();

      int code = conn.getResponseCode();
      System.out.println("Code: " + code);
      if (code == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        //ObjectMapper mapper = new ObjectMapper();
        //this.candidaturas = mapper.readValue(content.toString(), new TypeReference<List<CandidaturaDTO>>() {});
        Utils.showAlertDialog("Sucesso", content.toString(), null, Alert.AlertType.INFORMATION, CSS);
      } else {
        Utils.showAlertDialog("Erro", handleErros(conn), null, Alert.AlertType.ERROR, CSS);
        System.out.println("Erro: " + conn.getResponseMessage());
      }
    } catch (Exception e) {
      Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
      System.out.println("Erro: " + e.getMessage());
    }
  }

  public void listarCandidaturas() {
    try {
      URL url = new URL(URL + "/candidaturas/" + this.resposta.getAluno().getId());
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);

      int code = conn.getResponseCode();
      if (code == HttpURLConnection.HTTP_OK) {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
        br.close();
        conn.disconnect();
        ObjectMapper mapper = new ObjectMapper();
        this.candidaturas = mapper.readValue(response.toString(), new TypeReference<List<CandidaturaDTO>>() {});
        System.out.println("Candidaturas: " + this.candidaturas);
      } else {
        Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
        System.out.println("Erro: " + conn.getResponseMessage());
      }
    } catch (Exception e) {
      Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
      System.out.println("Erro: " + e.getMessage());
    }
  }

  public void deleteCandidatura(Long candidaturaId) {
    try {
      URL url = new URL(URL + "/candidatura/" + candidaturaId);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("DELETE");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);

      System.out.println(url);
      int code = conn.getResponseCode();
      System.out.println("Code: " + code);
      if (code == HttpURLConnection.HTTP_OK) {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
        br.close();
        conn.disconnect();
        ObjectMapper mapper = new ObjectMapper();
        this.candidaturas = mapper.readValue(response.toString(), new TypeReference<List<CandidaturaDTO>>() {});
      } else {
        Utils.showAlertDialog("Erro", handleErros(conn), null, Alert.AlertType.ERROR, CSS);
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public List<EntregaDTO> sendProposta(Long alunoId, File file) {
    String boundary = Long.toHexString(System.currentTimeMillis());
    String CRLF = "\r\n";
    try {
      URL url = new URL(URL + "/tese/" + alunoId + "/propostas");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
      OutputStream output = conn.getOutputStream();
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true);

      String fileName = file.getName();
      writer.append("--" + boundary).append(CRLF);
      writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"").append(CRLF);
      writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(CRLF);
      writer.append("Content-Transfer-Encoding: binary").append(CRLF);
      writer.append(CRLF).flush();

      Files.copy(file.toPath(), output);
      output.flush();
      writer.append(CRLF).flush();
      writer.append("--" + boundary + "--").append(CRLF).flush();

      int responseCode = conn.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        ObjectMapper mapper = new ObjectMapper();
        Utils.showAlertDialog("Sucesso", "Proposta enviada com sucesso", null, Alert.AlertType.INFORMATION, CSS);
        return mapper.readValue(content.toString(), new TypeReference<List<EntregaDTO>>() {});
      } else {
        Utils.showAlertDialog("Erro", handleErros(conn), null, Alert.AlertType.ERROR, CSS);
      }
    } catch (IOException e) {
      Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
      System.out.println("Erro: " + e.getMessage());
    }
    return null;
  }

  public EntregaDTO sendPropostaFinal(Long alunoId, File file) {
    String boundary = Long.toHexString(System.currentTimeMillis());
    String CRLF = "\r\n";

    try {
      URL url = new URL(URL + "/tese/" + alunoId + "/final");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
      OutputStream output = conn.getOutputStream();
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true);
      String fileName = file.getName();
      writer.append("--" + boundary).append(CRLF);
      writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"").append(CRLF);
      writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(CRLF);
      writer.append("Content-Transfer-Encoding: binary").append(CRLF);
      writer.append(CRLF).flush();
      Files.copy(file.toPath(), output);
      output.flush();
      writer.append(CRLF).flush();
      writer.append("--" + boundary + "--").append(CRLF).flush();
      int responseCode = conn.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        ObjectMapper mapper = new ObjectMapper();
        Utils.showAlertDialog("Sucesso", "Entrega final enviada com sucesso", null, Alert.AlertType.INFORMATION,CSS );
        return mapper.readValue(content.toString(), new TypeReference<EntregaDTO>() {});
      } else {
        Utils.showAlertDialog("Erro", handleErros(conn), null, Alert.AlertType.ERROR, CSS);
      }
    } catch (Exception e) {
      e.printStackTrace();
      Utils.showAlertDialog("Erro", "Erro a fazer o pedido", null, Alert.AlertType.ERROR, CSS);
    }
    return null;
  }

  public RespostaLoginAluno getRespostaLoginAluno() {
    return this.resposta;
  }

  public List<CandidaturaDTO> getCandidaturas() {
    return candidaturas;
  }

  public List<TemaDTO> getTemas() {
        return temas;
  }

  private String handleErros(HttpURLConnection conn) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    String inputLine;
    StringBuilder content = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();
    return content.toString();
  }
}
