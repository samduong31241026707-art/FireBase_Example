package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  FirebaseFirestore db;
  Button btAdd, btShow;
  EditText etId, etTitle, etContent, etImageUrl;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    FirebaseApp.initializeApp(this);
    db = FirebaseFirestore.getInstance();

    btAdd = findViewById(R.id.btAdd);
    btShow = findViewById(R.id.btShow);
    etId = findViewById(R.id.etId);
    etTitle = findViewById(R.id.etTitle);
    etContent = findViewById(R.id.etContent);
    etImageUrl = findViewById(R.id.etImageUrl);

    btAdd.setOnClickListener(this);
    btShow.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.btAdd) {
      addArticle();
    } else if (view.getId() == R.id.btShow) {
      Intent intent = new Intent(getBaseContext(), ShowDataActivity.class);
      startActivity(intent);
    }
  }

  private void addArticle() {
    String id = etId.getText().toString().trim();
    String title = etTitle.getText().toString().trim();
    String content = etContent.getText().toString().trim();
    String imageUrl = etImageUrl.getText().toString().trim();

    if (id.isEmpty() || title.isEmpty()) {
      Toast.makeText(this, "Id và Title không được để trống", Toast.LENGTH_SHORT).show();
      return;
    }
    // Id dùng làm document ID trong Firestore nên không được chứa dấu "/"
    if (id.contains("/")) {
      Toast.makeText(this, "Id không được chứa ký tự /", Toast.LENGTH_SHORT).show();
      return;
    }

    Article article = new Article(id, title, content, imageUrl);

    // Collection "articles", document ID = id bài viết. Trùng id thì ghi đè.
    db.collection("articles")
            .document(id)
            .set(article)
            .addOnSuccessListener(unused -> {
              Toast.makeText(this, "Đã lưu bài viết", Toast.LENGTH_SHORT).show();
              etId.setText("");
              etTitle.setText("");
              etContent.setText("");
              etImageUrl.setText("");
            })
            .addOnFailureListener(e ->
                    Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
  }
}