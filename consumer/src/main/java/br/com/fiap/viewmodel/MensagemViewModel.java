package br.com.fiap.viewmodel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class MensagemViewModel {

  private String id;
  private String msg;

  public MensagemViewModel(String id, String msg) {
    this.id = id;
    this.msg = msg;
  }

  public static List<MensagemViewModel> parse(Map<String, String> map) {
    return map.entrySet().stream().map(e -> new MensagemViewModel(e.getKey(), e.getValue())).collect(Collectors.toList());
  }
}
