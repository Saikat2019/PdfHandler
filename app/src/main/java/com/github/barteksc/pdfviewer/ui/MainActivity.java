package com.github.barteksc.pdfviewer.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PdfHandler.PDFView;
import com.github.barteksc.pdfviewer.R;
import com.github.barteksc.pdfviewer.PdfHandler.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.PdfHandler.listener.OnTapListener;

public class MainActivity extends AppCompatActivity {

    private Button btnSelect;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfView = findViewById(R.id.pdf_view);

        btnSelect = findViewById(R.id.btn_select_pdf);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");

                startActivityForResult(intent,101);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode==RESULT_OK){
            pdfView.fromUri(data.getData())
                    .onDraw(new OnDrawListener() {
                        @Override
                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                            Log.d("XXXTAG", "onLayerDrawn: "+displayedPage);
                        }
                    })
                    .onTap(new OnTapListener() {
                        @Override
                        public boolean onTap(MotionEvent e) {
                            return false;
                        }
                    })
                    .spacing(5)
                    .load();
        }
    }


}
