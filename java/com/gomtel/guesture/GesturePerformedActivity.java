package com.gomtel.guesture;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class GesturePerformedActivity extends Activity {

	private final File mStoreFile = new File(
			Environment.getExternalStorageDirectory(), "gestures");

	// ���ƿ�
	GestureLibrary mGestureLib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.gesture_perform);
		// ���ƻ���
		GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures_overlay);

		gestures.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);

		gestures.setFadeOffset(2000); // ��ʻ�ÿ}�εļ��ʱ��
		gestures.setGestureColor(Color.CYAN);// ������ɫ
		gestures.setGestureStrokeWidth(6);// ���ʴ�ϸֵ

		// ����ʶ��ļ�����
		gestures.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
			@Override
			public void onGesturePerformed(GestureOverlayView overlay,
					Gesture gesture) {
				// �����ƿ��в�ѯƥ������ݣ�ƥ��Ľ����ܰ�(������ƵĽ��ƥ��ȸߵĽ�������ǰ��
				ArrayList<Prediction> predictions = mGestureLib
						.recognize(gesture); 	
				if (predictions.size() > 0) {
					Prediction prediction = (Prediction) predictions.get(0);
					// ƥ�������
					if (prediction.score > 1.0) { // Խƥ��score��ֵԽ�����Ϊ10
						Toast.makeText(GesturePerformedActivity.this,
								prediction.name, Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		if (mGestureLib == null) {
			mGestureLib = GestureLibraries.fromFile(mStoreFile);
			mGestureLib.load();
		}
	}

}
