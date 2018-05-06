package com.xlong.loserlivegame;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xlong.loserlivegame.sqlitehelper.DBForGoods;
import com.xlong.loserlivegame.sqlitehelper.DBForPerson;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

public class StartActivity extends AppCompatActivity {
    private Button readBtn;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_start);
        linearLayout.addView(new MyAnimationView(this));
        readBtn = (Button) findViewById(R.id.read_game);
        startBtn = (Button) findViewById(R.id.start_game);
        readBtn.setOnClickListener(new View.OnClickListener() {
            DBForPerson dbPerson = new DBForPerson(StartActivity.this);
            DBForGoods dbGoods = new DBForGoods(StartActivity.this);

            @Override
            public void onClick(View v) {
                if (dbPerson.getPerson() != null && dbGoods.getGoods() != null) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    intent.putExtra("readgame", true);
                    startActivity(intent);
                } else {
                    Toast.makeText(StartActivity.this, "没有游戏进度！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });
    }

    public class MyAnimationView extends View {
        public MyAnimationView(Context context) {
            super(context);
            ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", RED, BLUE, WHITE, GREEN);
            colorAnim.setDuration(2000);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setRepeatCount(ValueAnimator.INFINITE);
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);
            colorAnim.start();
        }
    }
}
