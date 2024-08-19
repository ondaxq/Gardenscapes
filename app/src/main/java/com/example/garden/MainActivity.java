package com.example.garden;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int[] fr = {
            R.drawable.hoa,
            R.drawable.le,
            R.drawable.nho,
            R.drawable.nuoc,
            R.drawable.tao,
    };
    int widthofblock, noofblocks = 8, widthofscreen;
    GridLayout gridLayout;
    ArrayList<ImageView> fruit =  new ArrayList<>();
    int fruittobedragged,fruittobereplaced;
    int notFruit = R.drawable.a;
    Handler mhandler;
    int interval=100;
    int score = 0;
    int turn = 0;
    TextView tvturn;
    TextView tvscore;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = findViewById(R.id.board);
        tvscore = findViewById(R.id.tvscore);
        tvturn = findViewById(R.id.tvturn);
        ImageView imv = findViewById(R.id.imv);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthofscreen = displayMetrics.widthPixels;
        int heightofscreen = displayMetrics.heightPixels;
        widthofblock = widthofscreen / noofblocks;
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turn = 0;
                score = 0;
                tvscore.setText(String.valueOf(score));
                tvturn.setText(String.valueOf(turn));
                gridLayout.removeAllViews();
                fruit.clear();
                createBoard();
                mhandler.removeCallbacks(repeatchecker);
                startrepeat();
                Tuoch();


            }
        });
        createBoard();
        Tuoch();

            mhandler = new Handler();
            startrepeat();

    }

    private boolean checkColumn3() {
        boolean found = false;
        for (int i = 0; i < 47; i++) {
            int chosedfr = (int) fruit.get(i).getTag();
            boolean isBlank = (int) fruit.get(i).getTag() == notFruit;

            int x = i;
            if ((int) fruit.get(x).getTag() == chosedfr && !isBlank &&
                    (int) fruit.get(x + noofblocks).getTag() == chosedfr &&
                    (int) fruit.get(x + 2 * noofblocks).getTag() == chosedfr) {
                score += 3;
                tvscore.setText(String.valueOf(score));

                fruit.get(x).setImageResource(notFruit);
                fruit.get(x).setTag(notFruit);
                x = x + noofblocks;
                fruit.get(x).setImageResource(notFruit);
                fruit.get(x).setTag(notFruit);
                x = x + noofblocks;
                fruit.get(x).setImageResource(notFruit);
                fruit.get(x).setTag(notFruit);

                found = true;
            }
        }

        movedown();
        return found;
    }

    private boolean checkrow3() {
        boolean found = false;

        for (int i = 0; i < 62; i++) {
            int chosedfr = (int) fruit.get(i).getTag();
            boolean isBlank = chosedfr == notFruit;
            Integer[] notValid = {6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55};
            List<Integer> list = Arrays.asList(notValid);

            if (!list.contains(i)) {
                int x = i;
                if ((int) fruit.get(x++).getTag() == chosedfr && !isBlank &&
                        (int) fruit.get(x++).getTag() == chosedfr &&
                        (int) fruit.get(x).getTag() == chosedfr) {

                    score += 3;
                    tvscore.setText(String.valueOf(score));

                    fruit.get(x).setImageResource(notFruit);
                    fruit.get(x).setTag(notFruit);
                    x--;
                    fruit.get(x).setImageResource(notFruit);
                    fruit.get(x).setTag(notFruit);
                    x--;
                    fruit.get(x).setImageResource(notFruit);
                    fruit.get(x).setTag(notFruit);

                    found = true;
                }
            }
        }
        movedown();
        return found;
    }
    private boolean checkcolumn4() {
        boolean found = false;

        for (int i = 0; i < 40; i++) { // 40 vì 8x8 - 4 + 1 = 40
            int chosenFruit = (int) fruit.get(i).getTag();
            boolean isBlank = chosenFruit == notFruit;
            int x = i;
            if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                    (int) fruit.get(i + noofblocks).getTag() == chosenFruit &&
                    (int) fruit.get(i + 2 * noofblocks).getTag() == chosenFruit &&
                    (int) fruit.get(i + 3 * noofblocks).getTag() == chosenFruit) {
                score += 4;
                tvscore.setText(String.valueOf(score));
                for (int j = 0; j < 4; j++) {
                    fruit.get(x + j * noofblocks).setImageResource(notFruit);
                    fruit.get(x + j * noofblocks).setTag(notFruit);
                }

                found = true;
            }
        }
        movedown();
        return found;
    }
    private boolean checkrow4() {
        boolean found = false;

        for (int i = 0; i < 64; i++) {
            int chosenFruit = (int) fruit.get(i).getTag();
            boolean isBlank = chosenFruit == notFruit;
            Integer[] notValid = {6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55};
            List<Integer> list = Arrays.asList(notValid);

            if (!list.contains(i)) {
                if (i + 3 < fruit.size()) {
                    int x = i;
                    if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                            (int) fruit.get(i + 1).getTag() == chosenFruit &&
                            (int) fruit.get(i + 2).getTag() == chosenFruit &&
                            (int) fruit.get(i + 3).getTag() == chosenFruit) {
                        score += 4;
                        tvscore.setText(String.valueOf(score));
                        for (int j = 0; j < 4; j++) {
                            fruit.get(x + j).setImageResource(notFruit);
                            fruit.get(x + j).setTag(notFruit);
                        }

                        found = true;
                    }
                }
            }
        }
        movedown();
        return found;
    }
    private boolean checkrow5() {
        boolean found = false;

        for (int i = 0; i < 60; i++) {
            int chosenFruit = (int) fruit.get(i).getTag();
            boolean isBlank = chosenFruit == notFruit;
            Integer[] notValid = {4, 5, 12, 13, 20, 21, 28, 29, 36, 37, 44, 45, 52, 53, 60, 61};
            List<Integer> list = Arrays.asList(notValid);

            if (!list.contains(i)) {
                if (i + 4 < fruit.size()) {
                    int x = i;
                    if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                            (int) fruit.get(i + 1).getTag() == chosenFruit &&
                            (int) fruit.get(i + 2).getTag() == chosenFruit &&
                            (int) fruit.get(i + 3).getTag() == chosenFruit &&
                            (int) fruit.get(i + 4).getTag() == chosenFruit) {
                        score += 5;
                        tvscore.setText(String.valueOf(score));
                        for (int j = 0; j < 5; j++) {
                            fruit.get(x + j).setImageResource(notFruit);
                            fruit.get(x + j).setTag(notFruit);
                        }

                        found = true;
                    }
                }
            }
        }
        movedown();
        return found;
    }
    private boolean checkcolumn5() {
        boolean found = false;

        for (int i = 0; i < 24; i++) { // 24 vì 8x8 - 5 + 1 = 24
            int chosenFruit = (int) fruit.get(i).getTag();
            boolean isBlank = chosenFruit == notFruit;
       if (i + 4 * noofblocks < fruit.size()) {
                if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                        (int) fruit.get(i + noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2 * noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + 3 * noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + 4 * noofblocks).getTag() == chosenFruit) {
                    score += 5;
                    tvscore.setText(String.valueOf(score));
                    for (int j = 0; j < 5; j++) {
                        fruit.get(i + j * noofblocks).setImageResource(notFruit);
                        fruit.get(i + j * noofblocks).setTag(notFruit);
                    }

                    found = true;
                }
            }
        }
        movedown();
        return found;
    }

    private boolean checkTShape() {
        boolean found = false;
        List<Integer> removedIndexes = new ArrayList<>();

        for (int i = 0; i < fruit.size(); i++) {
            int chosenFruit = (int) fruit.get(i).getTag();
            boolean isBlank = chosenFruit == notFruit;
            if (i % noofblocks < noofblocks - 2 && i + noofblocks < fruit.size() && i + 2 * noofblocks < fruit.size()) {
                if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                        (int) fruit.get(i + 1).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks + 1).getTag() == chosenFruit) {
                    score += 5;
                    tvscore.setText(String.valueOf(score));
                    removedIndexes.add(i);
                    removedIndexes.add(i + 1);
                    removedIndexes.add(i + 2);
                    removedIndexes.add(i + noofblocks);
                    removedIndexes.add(i + noofblocks + 1);

                    found = true;
                }
            }
            if (i % noofblocks < noofblocks - 2 && i + noofblocks + 1 < fruit.size() && i - noofblocks >= 0) {
                if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                        (int) fruit.get(i + 1).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2).getTag() == chosenFruit &&
                        (int) fruit.get(i - noofblocks + 1).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks + 1).getTag() == chosenFruit) {
                    score += 5;
                    tvscore.setText(String.valueOf(score));
                    removedIndexes.add(i);
                    removedIndexes.add(i + 1);
                    removedIndexes.add(i + 2);
                    removedIndexes.add(i - noofblocks + 1);
                    removedIndexes.add(i + noofblocks + 1);

                    found = true;
                }
            }
            if (i + noofblocks + 2 < fruit.size() && i % noofblocks < noofblocks - 1) {
                if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                        (int) fruit.get(i + noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks + 1).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks + 2).getTag() == chosenFruit &&
                        (int) fruit.get(i + 1).getTag() == chosenFruit) {
                    score += 5;
                    tvscore.setText(String.valueOf(score));

                    // Thêm các ô vào danh sách đã xóa
                    removedIndexes.add(i);
                    removedIndexes.add(i + noofblocks);
                    removedIndexes.add(i + noofblocks + 1);
                    removedIndexes.add(i + noofblocks + 2);
                    removedIndexes.add(i + 1);

                    found = true;
                }
            }
            if (i + noofblocks + 2 < fruit.size() && i % noofblocks > 0) {
                if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                        (int) fruit.get(i + noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks + 1).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks + 2).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks - 1).getTag() == chosenFruit) {

                    // Cập nhật điểm số
                    score += 5;
                    tvscore.setText(String.valueOf(score));
                    removedIndexes.add(i);
                    removedIndexes.add(i + noofblocks);
                    removedIndexes.add(i + noofblocks + 1);
                    removedIndexes.add(i + noofblocks + 2);
                    removedIndexes.add(i + noofblocks - 1);

                    found = true;
                }
            }
        }
        for (int index : removedIndexes) {
            if (index < fruit.size()) {
                fruit.get(index).setImageResource(notFruit);
                fruit.get(index).setTag(notFruit);
            }
        }

        movedown();
        return found;
    }
    private boolean checkLShape() {
        boolean found = false;

        for (int i = 0; i < fruit.size(); i++) {
            int chosenFruit = (int) fruit.get(i).getTag();
            boolean isBlank = chosenFruit == notFruit;
            if (i % noofblocks < noofblocks - 2 && i + 3 * noofblocks < fruit.size()) {
                if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                        (int) fruit.get(i + 1).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks + 2).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2 * noofblocks + 2).getTag() == chosenFruit) {
                    score += 5;
                    tvscore.setText(String.valueOf(score));

                    // Xóa các ô trong hình chữ "L"
                    fruit.get(i).setImageResource(notFruit);
                    fruit.get(i).setTag(notFruit);
                    fruit.get(i + 1).setImageResource(notFruit);
                    fruit.get(i + 1).setTag(notFruit);
                    fruit.get(i + 2).setImageResource(notFruit);
                    fruit.get(i + 2).setTag(notFruit);
                    fruit.get(i + noofblocks + 2).setImageResource(notFruit);
                    fruit.get(i + noofblocks + 2).setTag(notFruit);
                    fruit.get(i + 2 * noofblocks + 2).setImageResource(notFruit);
                    fruit.get(i + 2 * noofblocks + 2).setTag(notFruit);

                    found = true;
                }
            }

            // Kiểm tra hình chữ "L" dạng 1-2-3-11-21
            if (i % noofblocks < noofblocks - 2 && i - 2 * noofblocks >= 0) {
                if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                        (int) fruit.get(i + 1).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2).getTag() == chosenFruit &&
                        (int) fruit.get(i - noofblocks + 2).getTag() == chosenFruit &&
                        (int) fruit.get(i - 2 * noofblocks + 2).getTag() == chosenFruit) {
                    score += 5;
                    tvscore.setText(String.valueOf(score));

                    // Xóa các ô trong hình chữ "L"
                    fruit.get(i).setImageResource(notFruit);
                    fruit.get(i).setTag(notFruit);
                    fruit.get(i + 1).setImageResource(notFruit);
                    fruit.get(i + 1).setTag(notFruit);
                    fruit.get(i + 2).setImageResource(notFruit);
                    fruit.get(i + 2).setTag(notFruit);
                    fruit.get(i - noofblocks + 2).setImageResource(notFruit);
                    fruit.get(i - noofblocks + 2).setTag(notFruit);
                    fruit.get(i - 2 * noofblocks + 2).setImageResource(notFruit);
                    fruit.get(i - 2 * noofblocks + 2).setTag(notFruit);

                    found = true;
                }
            }
            if (i % noofblocks < noofblocks - 1 && i + 2 * noofblocks < fruit.size()) {
                if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                        (int) fruit.get(i + noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2 * noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2 * noofblocks + 1).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2 * noofblocks + 2).getTag() == chosenFruit) {

                    // Cập nhật điểm số
                    score += 5;
                    tvscore.setText(String.valueOf(score));

                    // Xóa các ô trong hình chữ "L"
                    fruit.get(i).setImageResource(notFruit);
                    fruit.get(i).setTag(notFruit);
                    fruit.get(i + noofblocks).setImageResource(notFruit);
                    fruit.get(i + noofblocks).setTag(notFruit);
                    fruit.get(i + 2 * noofblocks).setImageResource(notFruit);
                    fruit.get(i + 2 * noofblocks).setTag(notFruit);
                    fruit.get(i + 2 * noofblocks + 1).setImageResource(notFruit);
                    fruit.get(i + 2 * noofblocks + 1).setTag(notFruit);
                    fruit.get(i + 2 * noofblocks + 2).setImageResource(notFruit);
                    fruit.get(i + 2 * noofblocks + 2).setTag(notFruit);

                    found = true;
                }
            }
            if (i % noofblocks > 1 && i + 2 * noofblocks < fruit.size()) {
                if ((int) fruit.get(i).getTag() == chosenFruit && !isBlank &&
                        (int) fruit.get(i + 1).getTag() == chosenFruit &&
                        (int) fruit.get(i + noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2 * noofblocks).getTag() == chosenFruit &&
                        (int) fruit.get(i + 2 * noofblocks - 1).getTag() == chosenFruit) {
                    score += 5;
                    tvscore.setText(String.valueOf(score));

                    // Xóa các ô trong hình chữ "L"
                    fruit.get(i).setImageResource(notFruit);
                    fruit.get(i).setTag(notFruit);
                    fruit.get(i + 1).setImageResource(notFruit);
                    fruit.get(i + 1).setTag(notFruit);
                    fruit.get(i + noofblocks).setImageResource(notFruit);
                    fruit.get(i + noofblocks).setTag(notFruit);
                    fruit.get(i + 2 * noofblocks).setImageResource(notFruit);
                    fruit.get(i + 2 * noofblocks).setTag(notFruit);
                    fruit.get(i + 2 * noofblocks - 1).setImageResource(notFruit);
                    fruit.get(i + 2 * noofblocks - 1).setTag(notFruit);

                    found = true;
                }
            }
        }

        movedown();
        return found;
    }


    Runnable repeatchecker = new Runnable() {
        @Override
        public void run() {
            try {

                checkTShape();
                checkLShape();
                checkcolumn5();
                checkrow5();
                checkcolumn4();
                checkrow4();
                checkrow3();
                checkColumn3();
                movedown();
            }
            finally {
                mhandler.postDelayed(repeatchecker,interval);
            }
        }
    };
    private void movedown(){
        Integer[] firstrow = {0,1,2,3,4,5,6,7};
        List<Integer> list = Arrays.asList(firstrow);
        for(int i = 55; i>=0 ; i--){
            if((int) fruit.get(i+noofblocks).getTag()==notFruit){
                fruit.get(i+noofblocks).setImageResource((int) fruit.get(i).getTag());
                fruit.get(i+noofblocks).setTag(fruit.get(i).getTag());
                fruit.get(i).setImageResource(notFruit);
                fruit.get(i).setTag(notFruit);
                if(list.contains(i) && (int) fruit.get(i).getTag() == notFruit){
                    int randomcolor = (int) Math.floor(Math.random()* fr.length);
                    fruit.get(i).setImageResource(fr[randomcolor]);
                    fruit.get(i).setTag(fr[randomcolor]);
                }
            }
        }
        for(int i = 0 ; i<8;i++){
            if ((int) fruit.get(i).getTag()==notFruit){
                int randomColor = (int) Math.floor((Math.random()*fr.length));
                fruit.get(i).setImageResource(fr[randomColor]);
                fruit.get(i).setTag(fr[randomColor]);
            }
        }
    }
    void  startrepeat(){
        repeatchecker.run();
    }
    private void fruitinterchange(){
        int background = (int) fruit.get(fruittobereplaced).getTag();
        fruit.get(fruittobereplaced).setImageResource((int) fruit.get(fruittobedragged).getTag());
        fruit.get(fruittobereplaced).setTag(fruit.get(fruittobedragged).getTag());
        fruit.get(fruittobedragged).setImageResource(background);
        fruit.get(fruittobedragged).setTag(background);
        if (checkTShape() || checkLShape() || checkcolumn5() || checkrow5() || checkcolumn4() || checkrow4() || checkColumn3() || checkrow3()  ) {
            turn++;
            tvturn.setText(String.valueOf(turn));
        }
            else{
            fruit.get(fruittobedragged).setImageResource((int) fruit.get(fruittobereplaced).getTag());
            fruit.get(fruittobedragged).setTag(fruit.get(fruittobereplaced).getTag());
            fruit.get(fruittobereplaced).setImageResource(background);
            fruit.get(fruittobereplaced).setTag(background);
        }


    }
    public void Tuoch() {
        for (ImageView imageView : fruit) {
            imageView.setOnTouchListener(new swipe(this) {

                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();

                    fruittobedragged = imageView.getId();
                    fruittobereplaced = fruittobedragged - 1;
                    fruitinterchange();

                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();

                    fruittobedragged = imageView.getId();
                    fruittobereplaced = fruittobedragged + 1;
                    fruitinterchange();
                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();

                    fruittobedragged = imageView.getId();
                    fruittobereplaced = fruittobedragged - noofblocks;
                    fruitinterchange();
                }

                @Override
                void onSwipeBot() {
                    super.onSwipeBot();

                    fruittobedragged = imageView.getId();
                    fruittobereplaced = fruittobedragged + noofblocks;
                    fruitinterchange();
                }
            });
        }
    }

        private void createBoard() {
        gridLayout.setRowCount(noofblocks);
        gridLayout.setColumnCount(noofblocks);
        gridLayout.getLayoutParams().width = widthofscreen;
        gridLayout.getLayoutParams().height = widthofscreen;

        for (int i = 0; i < noofblocks * noofblocks; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(widthofblock, widthofblock));
            imageView.setMaxHeight(widthofblock);
            imageView.setMaxWidth(widthofblock);

            int random = (int) Math.floor(Math.random() * fr.length);
            imageView.setImageResource(fr[random]);
            imageView.setTag(fr[random]);
            fruit.add(imageView);
            gridLayout.addView(imageView);
        }
    }
}