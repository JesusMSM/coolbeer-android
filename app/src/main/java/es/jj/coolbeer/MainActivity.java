package es.jj.coolbeer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    Button libraryButton, takePictureButton, sendPicButton;
    ImageView photoImageView;
    ProgressBar progressBar;

    private static final int SELECT_IMAGE = 1;
    private static final int CAMERA_PIC_REQUEST = 2;

    private static final int MY_REQUEST_CODE = 1;

    Bitmap image;

    RequestParams params = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        libraryButton = (Button) findViewById(R.id.library_button);
        takePictureButton = (Button) findViewById(R.id.picture_button);
        sendPicButton = (Button) findViewById(R.id.send_button);
        photoImageView = (ImageView) findViewById(R.id.photo);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        SpannableString s = new SpannableString("Coolbeer");
        s.setSpan(new ForegroundColorSpan(getColor(R.color.primary_text)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
            }
        });

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,CAMERA_PIC_REQUEST);
            }
        });

        sendPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadImage().execute("http://192.168.1.135:8080/coolbeer-server/MainServlet");

        }}) ;


        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_REQUEST_CODE);
        }


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {
                        image = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        photoImageView.setImageBitmap(image);

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAMERA_PIC_REQUEST) {
            image = (Bitmap) data.getExtras().get("data");
            photoImageView.setImageBitmap(image);
        }
    }


    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
            }
            else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }

    private class UploadImage extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
        }

        protected String doInBackground(String... urls) {

            try {
                Log.d("Pruebas", "Paso 1");
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                Log.d("Pruebas", "Paso 2");
                byte[] bitmapByte = outputStream.toByteArray();
                String stringEncodedImage = Base64.encodeToString(bitmapByte, 0);
                params.put("image", stringEncodedImage);

                return urls[0];



                /*Log.d("Pruebas", "Paso 3");
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urls[0]);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("image", stringEncodedImage);
                Log.d("Pruebas", jsonObject.toString());
                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.addHeader("Content-Type", "application/json");
                httpPost.setEntity(stringEntity);

                HttpResponse response = httpClient.execute(httpPost);
                Log.d("Pruebas", response.getStatusLine().toString());*/


            }catch (Exception e){
                e.printStackTrace();
               // Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            AsyncHttpClient client = new AsyncHttpClient();
            // Don't forget to change the IP address to your LAN address. Port no as well.
            client.post(s,
                    params, new AsyncHttpResponseHandler() {
                        // When the response returned by REST has Http
                        // response code '200'

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            // When Http response code is '404'
                            if (statusCode == 404) {
                                Toast.makeText(getApplicationContext(),
                                        "Requested resource not found",
                                        Toast.LENGTH_LONG).show();
                            }
                            // When Http response code is '500'
                            else if (statusCode == 500) {
                                Toast.makeText(getApplicationContext(),
                                        "Something went wrong at server end",
                                        Toast.LENGTH_LONG).show();
                            }
                            // When Http response code other than 404, 500
                            else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                                + statusCode, Toast.LENGTH_LONG)
                                        .show();
                            }
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String stringJson = new String(responseBody, StandardCharsets.UTF_8);
                            Toast.makeText(getApplicationContext(), "Success",
                                    Toast.LENGTH_LONG).show();

                            progressBar.setVisibility(View.GONE);
                            photoImageView.setVisibility(View.VISIBLE);


                            Intent i = new Intent(getApplicationContext(), ResultsActivity.class);
                            i.putExtra("beer",stringJson);
                            startActivity(i);
                        }

                    });
        }

    }
}
