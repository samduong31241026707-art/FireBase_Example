package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
  private ImageView imgArticle;
  private TextView txtId, txtTitle, txtContent;
  private ArticleAdapter adapter;

  public ArticleViewHolder(@NonNull View itemView, ArticleAdapter adapter) {
    super(itemView);
    imgArticle = itemView.findViewById(R.id.img_article);
    txtId = itemView.findViewById(R.id.txt_id);
    txtTitle = itemView.findViewById(R.id.txt_title);
    txtContent = itemView.findViewById(R.id.txt_content);
    this.adapter = adapter;
  }

  public ImageView getImgArticle() {
    return imgArticle;
  }

  public TextView getTxtId() {
    return txtId;
  }

  public TextView getTxtTitle() {
    return txtTitle;
  }

  public TextView getTxtContent() {
    return txtContent;
  }
}