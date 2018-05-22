package yangyd.kloud.model;

import java.util.Date;

public class BackendInquiryRequest {
  private String tag;
  private Date date;

  public BackendInquiryRequest(String tag) {
    this.tag = tag;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }
}
