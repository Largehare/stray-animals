package com.example.strayanimals.ui.post;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.strayanimals.R;
import com.example.strayanimals.data.Post;
import com.example.strayanimals.databinding.ActivityAnimaldetailBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class AnimalDetailActivity extends AppCompatActivity {
    private PostSQLHelper sqlHelper;
    private ActivityAnimaldetailBinding binding;
    private LocationManager locationManager;
    private Location location;

    private Post post;

    private void initSQL() {
        sqlHelper = new PostSQLHelper(this);
        try {
            sqlHelper.getReadableDatabase().query("post", null, null, null, null, null, null);
        } catch (Exception e) { //表单异常
            finish();
        }
    }

    private void showPost(Post post) {
        TextView author = binding.author;
        TextView timeLoc = binding.timeLoc;
        TextView content = binding.content;
        TextView reposts = binding.reposts;
        TextView comments = binding.comments;
        TextView likes = binding.likes;

        author.setText(post.getAuthor());

        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        timeLoc.setText(format.format(post.getCreateTime().getTime()) + " " + post.getAddress());

        //Toast.makeText(this, "地理位置: " + String.valueOf(post.getLocation().getLongitude()) + ", " + String.valueOf(post.getLocation().getLatitude()), Toast.LENGTH_LONG).show();
        //Log.i("Loc", "Read Location: " + post.getLocation().toString());

        content.setText(post.getContent());
        reposts.setText("转发 " + post.getReposts());
        comments.setText("评论 " + post.getComments());
        likes.setText("赞 " + post.getLikes());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_animaldetail);

        binding = ActivityAnimaldetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initSQL();

        int index = getIntent().getIntExtra("index", 0);
        post = sqlHelper.readSQL().get(index);
        showPost(post);

        ImageView backDetail = binding.backDetail;
        backDetail.setOnClickListener(view -> {
            //Intent intent = new Intent(this, MainActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //startActivityIfNeeded(intent, 0);
            finish();
        });

        LinearLayout repostComplex = binding.repostComplex;
        LinearLayout commentComplex = binding.commentComplex;
        LinearLayout likeComplex = binding.likeComplex;
        repostComplex.setOnClickListener(view -> {
            Post _post = (Post) post.clone();
            post.addRepost();
            sqlHelper.modifyPost(post, _post);
            showPost(post);
        });
        commentComplex.setOnClickListener(view -> {
            Post _post = (Post) post.clone();
            post.addComment();
            sqlHelper.modifyPost(post, _post);
            showPost(post);
        });
        likeComplex.setOnClickListener(view -> {
            Post _post = (Post) post.clone();
            post.addLike();
            sqlHelper.modifyPost(post, _post);
            showPost(post);
        });
    }
}