package com.example.translatortest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

public class MainActivity extends AppCompatActivity {
    EditText sourceEt;
    TextView englishText;
    Button button;
    String sourceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceEt = (EditText)findViewById(R.id.source_et);
        englishText = (TextView) findViewById(R.id.textView);
        button = (Button)findViewById(R.id.translateBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identifyLanguage();
            }
        });
        // Create an English-German translator:
    }
    public void identifyLanguage()
    {
        sourceText = sourceEt.getText().toString();

        FirebaseLanguageIdentification identifier  = FirebaseNaturalLanguage.getInstance().getLanguageIdentification();
        identifier.identifyLanguage(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s.equals("und"))
                {
                    Toast.makeText(MainActivity.this,"Language Not identified",Toast.LENGTH_SHORT);
                }
                else
                {
                    getLanguagecode(s);
                }
            }
        });
    }
    public void getLanguagecode(String s)
    {
        int langCode = 22;
        translateText(langCode);
    }
    public void translateText(int langCode)
    {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(36).setTargetLanguage(11).build();
        final FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                translator.translate(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        englishText.setText(s);
                    }
                });

            }
        });
    }
}