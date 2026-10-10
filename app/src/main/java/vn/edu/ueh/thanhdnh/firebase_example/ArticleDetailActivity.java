package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ArticleDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ARTICLE_ID = "article_id";

    FirebaseFirestore db;
    ImageView imgDetail;
    TextView txtDetailTitle, txtDetailViews, txtDetailContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgDetail = findViewById(R.id.imgDetail);
        txtDetailTitle = findViewById(R.id.txtDetailTitle);
        txtDetailViews = findViewById(R.id.txtDetailViews);
        txtDetailContent = findViewById(R.id.txtDetailContent);

        String articleId = getIntent().getStringExtra(EXTRA_ARTICLE_ID);
        if (articleId == null || articleId.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy bài viết", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("articles").document(articleId);

        // Tăng views thêm 1. Chỉ làm khi mở mới (savedInstanceState == null),
        // tránh bị cộng thêm khi xoay màn hình.
        if (savedInstanceState == null) {
            docRef.update("views", FieldValue.increment(1))
                    .addOnFailureListener(e ->
                            Log.e("ArticleDetail", "Increment views failed", e));
        }

        // Lắng nghe realtime để hiển thị dữ liệu và số views mới nhất
        docRef.addSnapshotListener(this, (snapshot, error) -> {
            if (error != null) {
                Log.e("ArticleDetail", "Listen failed", error);
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                Article article = snapshot.toObject(Article.class);
                if (article != null) {
                    txtDetailTitle.setText(article.getTitle());
                    txtDetailContent.setText(article.getContent());
                    txtDetailViews.setText("👁 " + article.getViews() + " lượt xem");
                    Glide.with(this)
                            .load(article.getImageUrl())
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .error(android.R.drawable.ic_delete)
                            .into(imgDetail);
                }
            }
        });
    }
}