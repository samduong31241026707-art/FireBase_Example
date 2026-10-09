package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
  private LayoutInflater mInflater;
  private List<Article> articles;

  public ArticleAdapter(Context context, List<Article> articles) {
    this.mInflater = LayoutInflater.from(context);
    this.articles = articles;
  }

  public void update(List<Article> articles) {
    this.articles = articles;
  }

  @NonNull
  @Override
  public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View customView = mInflater.inflate(R.layout.article_item, parent, false);
    return new ArticleViewHolder(customView, this);
  }

  @Override
  public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
    Article current = articles.get(position);
    holder.getTxtId().setText("ID: " + current.getId());
    holder.getTxtTitle().setText(current.getTitle());
    holder.getTxtContent().setText(current.getContent());

    // Load ảnh từ URL bằng Glide
    Glide.with(holder.itemView.getContext())
            .load(current.getImageUrl())
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_delete)
            .into(holder.getImgArticle());
  }

  @Override
  public int getItemCount() {
    return articles.size();
  }
}