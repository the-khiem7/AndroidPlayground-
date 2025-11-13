package trinc.com.lab1.lab5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import trinc.com.lab1.R;

public class Lab51Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab5_1);

        recyclerView = findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        userList.add(new User("TriNC", "Nguyen Cao Tri", "trincse181729@ftp.edu.vn"));
        userList.add(new User("Antv", "Tran Van An", "antv@gmail.com"));
        userList.add(new User("BangTT", "Tran Thanh Bang", "bangtt@gmail.com"));
        userList.add(new User("KhangTT", "Tran Thanh Khang", "khangtt@gmail.com"));
        userList.add(new User("BaoNT", "Nguyen Thanh Bao", "baont@gmail.com"));
        userList.add(new User("HungVH", "Vo Huy Hung", "hungvh@gmail.com"));

        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
    }
}
