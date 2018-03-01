package com.example.muhammadali.emosend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.VideoView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.List;

public class emoActivity extends AppCompatActivity implements Detector.ImageListener{

    CameraDetector detector;
    VideoView cameraPreview;
    EditText new_message;
    ImageButton emo;
    String name; int check=1;
    String address;
    ImageButton send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emo);

        //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        emo=(ImageButton) findViewById(R.id.emo);
        cameraPreview=(VideoView) findViewById(R.id.sv1);
        new_message=(EditText)  findViewById(R.id.new_message);
        new_message.setMovementMethod(new ScrollingMovementMethod());
        new_message.requestFocus();
        Intent intent2=getIntent();
        String msgtxt=intent2.getStringExtra("msgtext").toString();
        if (!(msgtxt.isEmpty())) {
            new_message.setText(msgtxt);
            int position = new_message.length();
            new_message.setSelection(position);
        }
        detector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, cameraPreview, 1, Detector.FaceDetectorMode.LARGE_FACES);
        detector.setImageListener(this);
        detector.setDetectSmile(true);
        detector.setDetectAnger(true);
        detector.setDetectSurprise(true);
        detector.setDetectSadness(true);
        detector.setDetectJoy(true);
        detector.setDetectFear(true);
        detector.setDetectDisgust(true);
        detector.setMaxProcessRate(5);
        detector.start();

        emo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detector.stop();
                Intent intent2=getIntent();
                String msgtxt=intent2.getStringExtra("msgtext").toString();
                EditText editText=(EditText) findViewById(R.id.new_message);
                String msg=editText.getText().toString();
                if (!(msg.isEmpty())) {
                    intent2.putExtra("msgWithEmoji", msg);
                }
                else
                {
                    intent2.putExtra("msgWithEmoji", "");
                }
                setResult(1,intent2);
                finish();
            }
        });

        /*if (check==1) {
                    detector.start();
                    cameraPreview.setVisibility(View.VISIBLE);
                    //new_message.setEnabled(false);

                }
                else
                {
                    detector.stop();
                    cameraPreview.setVisibility(View.GONE);
                    new_message.setEnabled(true);

                }*/


    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        //Changes 'back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            Intent intent2=getIntent();
            String msgtxt=intent2.getStringExtra("msgtext").toString();
            EditText editText=(EditText) findViewById(R.id.new_message);
            String msg=editText.getText().toString();
            if (!(msg.isEmpty())) {
                intent2.putExtra("msgWithEmoji", msg);
            }
            else
            {
                intent2.putExtra("msgWithEmoji", "");
            }
            setResult(1,intent2);
            finish();
        }
        return true;
    }


    public void onImageResults(List<Face> faces, Frame frame, float v) {
        EditText editText=(EditText) findViewById(R.id.new_message);
        String smstext = new_message.getText().toString();

        if(faces.size() == 0)
        {
            editText.setText(smstext);

        }
        else
        {
            String abc="";
            //int smily1=0x1F642;
            int anger1=0x1F620;
            int surprise1=0x1F62E;
            int joy1=0x1F603;    //not
            int fear1=0x1F628;
            int disgust1=0x1F922; //not
            int sadness1=0x1F61E;
            int lipPucker=0x1F617;
            int lipStretch=0x1F610;
            int browFurrow=0x1F623;
            int browRaised_lipCornerDepresser=0x1F61E;
            int browFurrow_lipCornerDepresser=0x1F61F;
            int dimpler=0x1F60A;
            int eyeCloser=0x1F637;

            Face face=faces.get(0);
            if(( face.emotions.getAnger()) >=30)
            {
                abc= new String(Character.toChars(anger1));
            }
            else if(( face.emotions.getSurprise()) >=25)
            {
                abc= new String(Character.toChars(surprise1));
            }
            else if(( face.emotions.getJoy()) >=57)
            {
                abc= new String(Character.toChars(joy1));
            }
            else  if(( face.emotions.getFear()) >=10)
            {
                abc= new String(Character.toChars(fear1));
            }
            else if(( face.emotions.getDisgust()) >=10)
            {
                abc= new String(Character.toChars(disgust1));
            }
            else if(( face.emotions.getSadness()) >=6)
            {
                abc= new String(Character.toChars(sadness1));
            }
            /*else if(( face.expressions.getLipPucker()) >=5)
            {
                abc= new String(Character.toChars(lipPucker));
            }
            else if(( face.expressions.getLipStretch()) >=5)
            {
                abc= new String(Character.toChars(lipStretch));
            }
            else if(( face.expressions.getBrowFurrow()) >=5)
            {
                abc= new String(Character.toChars(browFurrow));
            }
            else if((( face.expressions.getBrowRaise()) >=20) && (( face.expressions.getLipCornerDepressor()) >=20))
            {
                abc= new String(Character.toChars(browRaised_lipCornerDepresser));
            }
            else if((( face.expressions.getBrowFurrow()) >=20) && (( face.expressions.getLipCornerDepressor()) >=20))
            {
                abc= new String(Character.toChars(browFurrow_lipCornerDepresser));
            }
            else if(( face.expressions.getDimpler()) >=5)
            {
                abc= new String(Character.toChars(dimpler));
            }
            else if(( face.expressions.getEyeClosure()) >=5)
            {
                abc= new String(Character.toChars(eyeCloser));
            }*/
            editText.setText(smstext+abc);

        }


    }

}
