package vn.edu.ueh.thanhdnh.firebase_example;

public class Article {
  private String id;
  private String title;
  private String content;
  private String imageUrl;
  private long views;

  // BẮT BUỘC: Firestore cần constructor rỗng để dùng toObject()
  public Article() {
  }

  public Article(String id, String title, String content, String imageUrl) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.imageUrl = imageUrl;
    this.views = 0; // bài mới bắt đầu với 0 lượt xem
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public long getViews() {
    return views;
  }

  public void setViews(long views) {
    this.views = views;
  }

  @Override
  public String toString() {
    return "Article{" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", views=" + views +
            '}';
  }
}