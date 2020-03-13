package jp.co.strrugleforlife.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Item {
  private Integer id;

  @NotBlank(message="商品名を記入してください。")
  private String name;

  @Min(value=10, message="10以上の数値を入力してください。")
  @Max(value=10000, message="10000以下の数値を入力してください。")
  private Integer price;

  @Size(max=50, message="ベーダー名は50文字を超えないでください。")
  private String vendor;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }
}