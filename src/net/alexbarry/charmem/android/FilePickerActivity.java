package net.alexbarry.charmem.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import net.alexbarry.charmem.R;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class FilePickerActivity extends FragmentActivity {

	private static final int PICKFILE_RESULT_CODE = 0;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
       Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("gagt/sdf");
        try {
            startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
		
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Fix no activity available
        if (data == null)
            return;
        switch (requestCode) {
        case PICKFILE_RESULT_CODE:
            if (resultCode == RESULT_OK) {
            	this.pickFile(data);
            }
        break;
        }
	}

	private void pickFile(Intent data) {
        String filePath = data.getData().getPath();
        FileInputStream fileInputStream;
        
        try {
			fileInputStream = openFileInput( filePath );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
        
        
	}

//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}



}
