package top.km.testgitdemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {


    private ShineProgress shineProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        shineProgress = (ShineProgress)findViewById(R.id.shinePro);


    }

    public void click(View view){
        shineProgress.release();
    }

    public void click2(View view){
        shineProgress.done();
    }

}
