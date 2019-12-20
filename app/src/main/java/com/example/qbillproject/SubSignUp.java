package com.example.qbillproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SubSignUp extends AppCompatActivity {

    EditText Et_age;
    EditText Et_phone;
    EditText Et_gender;
    EditText Et_kinds;
    EditText Et_identityNum;
    Button btn_real_signup;

    String age;
    String phone;
    String gender;
    String kinds;
    String identityNum;
    String uno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_sign_up);
        Intent intent1 = getIntent(); /*데이터 수신*/
        uno = intent1.getExtras().getString("uno");
        Log.d("uno",uno);

        Et_age = (EditText)findViewById(R.id.age);
        Et_phone = (EditText)findViewById(R.id.phone);
        Et_gender = (EditText)findViewById(R.id.gender);
        Et_kinds = (EditText)findViewById(R.id.kinds);
        Et_identityNum = (EditText)findViewById(R.id.identityNum);

        btn_real_signup = (Button)findViewById(R.id.btn_real_signup);
        btn_real_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                age = Et_age.getText().toString();
                phone = Et_phone.getText().toString();
                gender = Et_gender.getText().toString();
                kinds = Et_kinds.getText().toString();
                identityNum = Et_identityNum.getText().toString();
                new SubSignUp.JSONTask().execute("http://192.168.43.190:3000/login2");
            }
        });
    }
    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("uno", uno);
                jsonObject.accumulate("age", age);
                jsonObject.accumulate("phone", phone);
                jsonObject.accumulate("gender", gender);
                jsonObject.accumulate("kinds", kinds);
                jsonObject.accumulate("identityNum", identityNum);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();

                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(SubSignUp.this, "회원가입이 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent2);
        }
    }
}
