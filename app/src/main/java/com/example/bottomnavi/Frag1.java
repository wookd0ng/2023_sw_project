package com.example.bottomnavi;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class Frag1 extends Fragment {

    private DatabaseReference databaseReference;
    private ImageView imageView;
    private TextView textView, textView2;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1, container, false);
        // 툴바 제목
        View toolbarView = view.findViewById(R.id.tb_frag1);
        TextView toolbarTitle = toolbarView.findViewById(R.id.textView_toolbar_title);
        toolbarTitle.setText("홈 화면");

        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        textView2 = view.findViewById(R.id.textView2);
        button = view.findViewById(R.id.button);

        // "posts" 아래에 있는 특정 폴더를 지정
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "posts" 아래의 데이터를 orderByKey().limitToLast(1)를 사용하여 가장 최근 데이터 1개만 가져오기
                Query lastQuery = databaseReference.orderByKey().limitToLast(5);
                ((Query) lastQuery).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            if (snapshot.exists()) {
                                // 가장 최근 데이터만 가져오기
                                DataSnapshot latestPost = snapshot.getChildren().iterator().next();
                                String imageUrl = latestPost.child("imageUrl").getValue(String.class);
                                String text = latestPost.child("text").getValue(String.class);
                                String location = latestPost.child("location").getValue(String.class);

                                if (imageUrl != null && text != null) {
                                    // 이미지를 표시
                                    Picasso.get().load(imageUrl).into(new Target() {


                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            imageView.setImageBitmap(bitmap);
                                        }

                                        @Override
                                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                            Log.e("에러", "이미지 로딩 실패: " + e.getMessage());
                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                                            // 이미지 로딩 전에 수행할 작업을 정의할 수 있습니다.
                                        }
                                    });
                                    // 텍스트를 표시
                                    textView.setText(text);
                                    textView2.setText(location);
                                    Log.v("이미지URL", imageUrl);
                                    Log.v("텍스트", text);
                                    Log.v("텍스트", location);
                                } else {
                                    Log.v("에러", "데이터가 null입니다.");
                                }
                            } else {
                                Log.v("에러", "데이터가 존재하지 않습니다.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("에러", "데이터 가져오기 중 오류 발생");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("에러", "데이터베이스 쿼리 취소됨: " + error.getMessage());
                    }
                });
            }
        });

        return view;
    }
}
