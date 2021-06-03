package com.example.firebase.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebase.R;
import com.example.firebase.databinding.FragmentHomeBinding;
import com.example.firebase.ui.notifications.NotificationsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;


public class HomeFragment extends Fragment implements View.OnClickListener{
    Button plus,minus,multiply,divide,calc,finish;
    TextView score,one,two,sign;
    EditText ans;
    int x,y,points,sum=0,trueAns,giveAns;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        plus = root.findViewById(R.id.plus);
        minus = root.findViewById(R.id.minus);
        multiply = root.findViewById(R.id.multiply);
        divide = root.findViewById(R.id.divide);
        calc = root.findViewById(R.id.calc);
        finish = root.findViewById(R.id.finish);
        score = root.findViewById(R.id.score);
        sign = root.findViewById(R.id.sign);
        one = root.findViewById(R.id.one);
        two = root.findViewById(R.id.two);
        ans = root.findViewById(R.id.ans);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
        calc.setOnClickListener(this);
        finish.setOnClickListener(this);

        String email= mAuth.getCurrentUser().getEmail().toString();
        User user = new User("Nativ",0);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        View z = v;
        if (v == plus) {
            sign.setText("+");
            Random r = new Random();
            x = r.nextInt(100);
            y = r.nextInt(100);
            while (x + y > 100) {
                x = r.nextInt(100);
                y = r.nextInt(100);
            }
            one.setText(String.valueOf(x));
            two.setText(String.valueOf(y));
            points = 5;
            trueAns = x + y;
        }
        if (v == minus) {
            sign.setText("-");
            Random r = new Random();
            x = r.nextInt(100);
            y = r.nextInt(100);
            while (x < y) {
                x = r.nextInt(100);
                y = r.nextInt(100);
            }
            one.setText(String.valueOf(x));
            two.setText(String.valueOf(y));
            points = 5;
            trueAns = x - y;
        }
        if (v == multiply) {
            sign.setText("x");
            Random r = new Random();
            x = r.nextInt(10);
            y = r.nextInt(10);
            one.setText(String.valueOf(x));
            two.setText(String.valueOf(y));
            points = 10;
            trueAns = x * y;
        }
        if (v == divide) {
            sign.setText(":");
            Random r = new Random();
            x = r.nextInt(100);
            y = r.nextInt(10);

            while (y==0 ||x < y || x % y != 0 ) {
                x = r.nextInt(100);
                y = r.nextInt(10);
            }
            one.setText(String.valueOf(x));
            two.setText(String.valueOf(y));
            points = 10;
            trueAns = x / y;
        }
        if (v == calc) {
            if(!ans.getText().toString().equals("")) {
                giveAns = Integer.parseInt(ans.getText().toString());

                if (trueAns == giveAns) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Nicely Done ☺!", Toast.LENGTH_SHORT);
                    toast.show();
                    z.setOnClickListener(this);
                    sum += points;
                    one.setText("");
                    two.setText("");
                    ans.setText("");
                    score.setText("score: " + sum);

                } else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Wrong Answer , Please Try Again", Toast.LENGTH_SHORT);
                     toast.show();

                }
            }else{
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please Insert An Answer", Toast.LENGTH_SHORT);
                toast.show();

        }
    }

        if(v==finish){
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Hope You Have Had A Great Time! , Your Score Is:" + sum, Toast.LENGTH_SHORT);
            toast.show();
            //Intent intent = new Intent(getActivity(), NotificationsFragment.class);
            //startActivity(intent);

            sum=0;
        }

    }

    private class User {
        public String email;
        public int score;

        public User(){}

        public User(String email, int score){
            this.email=email;
            this.score=score;
        }
    }
}