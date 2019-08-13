package com.example.alphamobilecolombia.mvp.activity.ExtractDocument;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.PermissionChecker;

import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.alphamobilecolombia.R;
import com.journeyapps.barcodescanner.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CamaraActivity extends AppCompatActivity {

    private RelativeLayout cameraLayout;
    private android.hardware.Camera mCamera;
    private FrameLayout preview;
    private FrameLayout pnlFlash;
    private CameraPreview mPreview;
    private ImageButton cancelar;
    private ImageButton flash;
    private ImageButton capturar;
    private RelativeLayout botones;
    private String[] perms = {"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};
    boolean fotos = false;
    private boolean supportContinuousPicture = false;
    private String tipoFoto = "";
    private Context context;
    private String stringTitulo = "";
    private int selector = 0;
    private android.hardware.Camera.Size pictureSize;
    private boolean hasFlash = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        Intent intent = this.getIntent();

        stringTitulo = intent.getStringExtra("name");
        fotos  = intent.getBooleanExtra("fotos", false);
        tipoFoto = intent.getStringExtra("tipoFoto");


        context = this;

        hasFlash = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);



        int permsRequestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permission = PermissionChecker.checkSelfPermission(context, "android.permission.CAMERA");


            if (permission == PermissionChecker.PERMISSION_GRANTED) {

            } else {
                requestPermissions(perms, permsRequestCode);
            }


        }

        cameraLayout = findViewById(R.id.cameraLayout);
        preview = findViewById(R.id.camera_preview);
        pnlFlash = findViewById(R.id.pnlFlash);
        tomarFoto();





        botones = findViewById(R.id.botones);
        cancelar = findViewById(R.id.button_cancel);
        flash = findViewById(R.id.button_flash);

        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mCamera.setPreviewCallback(null);
                        mCamera.stopPreview();
                        mCamera.release();
                        mCamera = null;
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                        //cameraLayout.setVisibility(View.GONE);
                        //preview.removeAllViews();
                        //preview.setVisibility(View.GONE);

                    }
                }

        );


        flash.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(hasFlash) {

                            if (selector == 0) {
                                flash.setImageResource(R.drawable.ic_flash_on_white_24dp);
                                selector = 1;
                                Camera.Parameters p = mCamera.getParameters();
                                p.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                                mCamera.setParameters(p);
                                //mCamera.startPreview();


                            } else if (selector == 1) {
                                flash.setImageResource(R.drawable.ic_flash_auto_white_24dp);
                                selector = 2;
                                Camera.Parameters p = mCamera.getParameters();
                                p.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                                mCamera.setParameters(p);


                            } else {
                                flash.setImageResource(R.drawable.ic_flash_off_white_24dp);
                                selector = 0;
                                Camera.Parameters p = mCamera.getParameters();
                                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                                mCamera.setParameters(p);


                            }


                        }
                    }
                }

        );


        capturar = findViewById(R.id.button_capture);

        capturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPicture);
                capturar.setVisibility(View.INVISIBLE);
                pnlFlash.setVisibility(View.VISIBLE);

                AlphaAnimation fade = new AlphaAnimation(1, 0);
                fade.setDuration(500);
                fade.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation anim) {
                        pnlFlash.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                pnlFlash.startAnimation(fade);
            }
        });



        fixRotation(context.getResources().getConfiguration());


    }


    public void tomarFoto(){


        /** Check if this device has a camera */

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera


            // Create an instance of Camara
            mCamera = getCameraInstance();



            //set camera to continually auto-focus
            android.hardware.Camera.Parameters params = mCamera.getParameters();
//*EDIT*//params.setFocusMode("continuous-picture");
//It is better to use defined constraints as opposed to String, thanks to AbdelHady
            List<String> focusModes = params.getSupportedFocusModes();


            for(int i = 0; i < focusModes.size(); i ++){

                if (focusModes.get(i).equals("continuous-picture")){

                    supportContinuousPicture = true;

                }

            }


            if (supportContinuousPicture){

                params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

            }
            else{
                params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_AUTO);

            }


            params.set("orientation", "portrait");
            mCamera.setDisplayOrientation(90);
            params.setRotation(90);

            params.set("jpeg-quality", 70);
            params.setPictureFormat(PixelFormat.JPEG);


            List<android.hardware.Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
            List<android.hardware.Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();

            android.hardware.Camera.Size previewSize = previewSizes.get(0);
            pictureSize = pictureSizes.get(pictureSizes.size()-1);
            //params.setPictureSize(1280, 720);

            // You need to choose the most appropriate previewSize for your app
            // .... select one of previewSizes here

            for (int i = 0 ; i< pictureSizes.size(); i++){

                android.hardware.Camera.Size pictureSizeInternal = pictureSizes.get(i);

                if(pictureSizeInternal.height > 700 && pictureSizeInternal.height < 800){

                    pictureSize =  pictureSizeInternal;

                }


            }

            if (previewSizes.size() >1){
                previewSize = previewSizes.get(previewSizes.size()-2);
            }
            else if (previewSizes.size() >0){
                previewSize = previewSizes.get(previewSizes.size()-1);
            }

            params.setPreviewSize(previewSize.width, previewSize.height);
            params.setPictureSize(pictureSize.width,pictureSize.height);


            mCamera.setParameters(params);


            if (mCamera != null) {

                // Create our Preview view and set it as the content of our activity.
                mPreview = new CameraPreview(context, (AttributeSet) mCamera);

                //preview = (FrameLayout) context.findViewById(R.id.camera_preview);
                preview.addView(mPreview);

                cameraLayout.setVisibility(View.VISIBLE);
                //cameraLayout.bringToFront();
                //cameraLayout.invalidate();

                preview.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {




                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            float x = event.getX();
                            float y = event.getY();
                            float touchMajor = event.getTouchMajor();
                            float touchMinor = event.getTouchMinor();

                            Rect touchRect = new Rect(
                                    (int) (x - touchMajor / 2),
                                    (int) (y - touchMinor / 2),
                                    (int) (x + touchMajor / 2),
                                    (int) (y + touchMinor / 2));


                            touchFocus(touchRect);

                        }




                        return true;





                    }
                });

            }




        } else {
            // no camera on this device


        }
    }


    private android.hardware.Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {





            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE, fotos, tipoFoto,stringTitulo);
            if (pictureFile == null){
                //Log.d(TAG, "Error creating media file, check storage permissions: " +  e.getMessage());
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                //cameraLayout.setVisibility(View.GONE);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;

                capturar.setVisibility(View.VISIBLE);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","OK");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();


            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };





    public void touchFocus(final Rect tfocusRect){

        //buttonTakePicture.setEnabled(false);

        // mCamera.stopFaceDetection();

        //Convert from View's width and height to +/- 1000
        final Rect targetFocusRect = new Rect(
                tfocusRect.left * 2000/preview.getWidth() - 1000,
                tfocusRect.top * 2000/preview.getHeight() - 1000,
                tfocusRect.right * 2000/preview.getWidth() - 1000,
                tfocusRect.bottom * 2000/preview.getHeight() - 1000);

        List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();


        meteringAreas.add(new Camera.Area(targetFocusRect, 1000));

        final List<android.hardware.Camera.Area> focusList = new ArrayList<android.hardware.Camera.Area>();
        android.hardware.Camera.Area focusArea = new android.hardware.Camera.Area(targetFocusRect, 1000);
        focusList.add(focusArea);

        if(mCamera == null){

            mCamera = getCameraInstance();
        }



        android.hardware.Camera.Parameters para = mCamera.getParameters();
        para.setFocusAreas(focusList);

        if (para.getMaxNumMeteringAreas() > 0) {
            para.setMeteringAreas(meteringAreas);

        }


        List<android.hardware.Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        List<android.hardware.Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();

        android.hardware.Camera.Size previewSize = previewSizes.get(0);
        pictureSize = pictureSizes.get(pictureSizes.size()-1);
        //params.setPictureSize(1280, 720);

        // You need to choose the most appropriate previewSize for your app
        // .... select one of previewSizes here

        for (int i = 0 ; i< pictureSizes.size(); i++){

            android.hardware.Camera.Size pictureSizeInternal = pictureSizes.get(i);

            if(pictureSizeInternal.height > 700 && pictureSizeInternal.height < 800){

                pictureSize =  pictureSizeInternal;

            }


        }

        if (previewSizes.size() >1){
            previewSize = previewSizes.get(previewSizes.size()-2);
        }
        else if (previewSizes.size() >0){
            previewSize = previewSizes.get(previewSizes.size()-1);
        }

        para.set("jpeg-quality", 70);
        para.setPictureFormat(PixelFormat.JPEG);

        para.setPreviewSize(previewSize.width, previewSize.height);
        para.setPictureSize(pictureSize.width,pictureSize.height);


        try {
            mCamera.setParameters(para);
            Log.d(TAG, "done");
        } catch (RuntimeException e) {
            // just in case something has gone wrong

            Log.d(TAG, "failed to set parameters");
            e.printStackTrace();
        }



        try{

            mCamera.cancelAutoFocus();

            mCamera.autoFocus(myAutoFocusCallback);
        }
        catch (Exception e){


            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            e.printStackTrace();
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }


        //preview.setHaveTouch(true, tfocusRect);
        preview.invalidate();
    }



    /** A safe way to get an instance of the Camara object. */
    public static android.hardware.Camera getCameraInstance(){
        android.hardware.Camera c = null;
        try {
            c = android.hardware.Camera.open(); // attempt to get a Camara instance
        }
        catch (Exception e){
            // Camara is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }



    android.hardware.Camera.AutoFocusCallback myAutoFocusCallback = new android.hardware.Camera.AutoFocusCallback(){

        @Override
        public void onAutoFocus(boolean arg0, android.hardware.Camera arg1) {
            // TODO Auto-generated method stub
            if (!arg0){
                //buttonTakePicture.setEnabled(true);
                mCamera.cancelAutoFocus();
            }

            float focusDistances[] = new float[3];
            arg1.getParameters().getFocusDistances(focusDistances);
            // prompt.setText("Optimal Focus Distance(meters): "
            // + focusDistances[Camara.Parameters.FOCUS_DISTANCE_OPTIMAL_INDEX]);

        }};

    android.hardware.Camera.ShutterCallback myShutterCallback = new android.hardware.Camera.ShutterCallback(){

        @Override
        public void onShutter() {
            // TODO Auto-generated method stub

        }};


    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type, Boolean fotos, String tipoFoto, String stringTitulo){
        return Uri.fromFile(getOutputMediaFile(type, fotos, tipoFoto, stringTitulo));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type, Boolean fotos, String tipoFoto, String stringTitulo){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.


        File mediaStorageDir  = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Rvisor");


        // Create the storage directory if it does not exist

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Rvisor", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){

            if (fotos == true) {

                if (tipoFoto.equals("frontal")) {

                    mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                            stringTitulo + tipoFoto + ".jpg");
                    // "IMG_frontal.jpg");
                }
                else{

                    mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                            //  "IMG_trasera.jpg");
                            stringTitulo+ tipoFoto + ".jpg");

                }

            }
            else{

                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        stringTitulo + ".jpg");
                // "IMG_" + timeStamp + ".jpg");
            }


        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        fixRotation(newConfig);
    }

    public void fixRotation(Configuration newConfig){


        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();


            //cameraLayout.setOrientation(RelativeLayout.HORIZONTAL);
            RelativeLayout.LayoutParams parameters = new  RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            parameters.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            parameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            cancelar.setLayoutParams(parameters);

            parameters = new  RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            parameters.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            parameters.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            flash.setLayoutParams(parameters);


            parameters =new  RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            parameters.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            parameters.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            botones.setLayoutParams(parameters);




            final float scale = getResources().getDisplayMetrics().density;

            int padding_dp_side = (int) (25 * scale + 0.5f);

            //preview.setPadding(padding_dp_side,0,padding_dp_side,0);

            if (mCamera != null){

                int rotation =  getWindowManager().getDefaultDisplay().getRotation();

                if (rotation == 1) {

                    android.hardware.Camera.Parameters params = mCamera.getParameters();
                    if (supportContinuousPicture){

                        params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

                    }
                    else{
                        params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_AUTO);

                    }
                    params.set("orientation", "landscape");
                    mCamera.setDisplayOrientation(0);
                    params.setRotation(0);
                    params.set("jpeg-quality", 70);
                    params.setPictureFormat(PixelFormat.JPEG);

                    List<android.hardware.Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
                    List<android.hardware.Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();

                    android.hardware.Camera.Size previewSize = previewSizes.get(0);
                    pictureSize = pictureSizes.get(pictureSizes.size()-1);
                    //params.setPictureSize(1280, 720);

                    // You need to choose the most appropriate previewSize for your app
                    // .... select one of previewSizes here

                    for (int i = 0 ; i< pictureSizes.size(); i++){
                        android.hardware.Camera.Size pictureSizeInternal = pictureSizes.get(i);
                        if(pictureSizeInternal.height > 700 && pictureSizeInternal.height < 800){
                            pictureSize =  pictureSizeInternal;
                        }
                    }

                    if (previewSizes.size() >1){
                        previewSize = previewSizes.get(previewSizes.size()-2);
                    }
                    else if (previewSizes.size() >0){
                        previewSize = previewSizes.get(previewSizes.size()-1);
                    }
                    params.setPreviewSize(previewSize.width, previewSize.height);
                    params.setPictureSize(pictureSize.width,pictureSize.height);
                    mCamera.setParameters(params);
                }
                else{
                    android.hardware.Camera.Parameters params = mCamera.getParameters();
                    if (supportContinuousPicture){

                        params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

                    }
                    else{
                        params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_AUTO);

                    }
                    params.set("orientation", "landscape");
                    mCamera.setDisplayOrientation(180);
                    params.setRotation(180);
                    params.set("jpeg-quality", 70);
                    params.setPictureFormat(PixelFormat.JPEG);


                    List<android.hardware.Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
                    List<android.hardware.Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();

                    android.hardware.Camera.Size previewSize = previewSizes.get(0);
                    pictureSize = pictureSizes.get(pictureSizes.size()-1);
                    //params.setPictureSize(1280, 720);

                    // You need to choose the most appropriate previewSize for your app
                    // .... select one of previewSizes here

                    for (int i = 0 ; i< pictureSizes.size(); i++){
                        android.hardware.Camera.Size pictureSizeInternal = pictureSizes.get(i);
                        if(pictureSizeInternal.height > 700 && pictureSizeInternal.height < 800){
                            pictureSize =  pictureSizeInternal;
                        }
                    }

                    if (previewSizes.size() >1){
                        previewSize = previewSizes.get(previewSizes.size()-2);
                    }
                    else if (previewSizes.size() >0){
                        previewSize = previewSizes.get(previewSizes.size()-1);
                    }
                    params.setPreviewSize(previewSize.width, previewSize.height);
                    params.setPictureSize(pictureSize.width,pictureSize.height);

                    mCamera.setParameters(params);
                }

            }


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();



            final float scale = getResources().getDisplayMetrics().density;
            int padding_dp_top = (int) (10 * scale + 0.5f);
            int padding_dp_bottom = (int) (5 * scale + 0.5f);
            int padding_dp_side = (int) (15 * scale + 0.5f);



            //cameraLayout.setOrientation(LinearLayout.HORIZONTAL);
            RelativeLayout.LayoutParams parameters = new  RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            parameters.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            parameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            cancelar.setLayoutParams(parameters);

            parameters = new  RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            parameters.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            parameters.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            flash.setLayoutParams(parameters);


            parameters =new  RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            parameters.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            parameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            botones.setLayoutParams(parameters);

            //preview.setPadding(padding_dp_side,padding_dp_top,padding_dp_side,padding_dp_bottom);


            if (mCamera != null){

                int rotation =  getWindowManager().getDefaultDisplay().getRotation();

                if (rotation == 0) {

                    android.hardware.Camera.Parameters params = mCamera.getParameters();
                    if (supportContinuousPicture){

                        params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

                    }
                    else{
                        params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_AUTO);

                    }
                    params.set("orientation", "portrait");
                    mCamera.setDisplayOrientation(90);
                    params.setRotation(90);
                    params.set("jpeg-quality", 70);
                    params.setPictureFormat(PixelFormat.JPEG);

                    List<android.hardware.Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
                    List<android.hardware.Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();

                    android.hardware.Camera.Size previewSize = previewSizes.get(0);
                    pictureSize = pictureSizes.get(pictureSizes.size()-1);
                    //params.setPictureSize(1280, 720);

                    // You need to choose the most appropriate previewSize for your app
                    // .... select one of previewSizes here

                    for (int i = 0 ; i< pictureSizes.size(); i++){
                        android.hardware.Camera.Size pictureSizeInternal = pictureSizes.get(i);
                        if(pictureSizeInternal.height > 700 && pictureSizeInternal.height < 800){
                            pictureSize =  pictureSizeInternal;
                        }
                    }

                    if (previewSizes.size() >1){
                        previewSize = previewSizes.get(previewSizes.size()-2);
                    }
                    else if (previewSizes.size() >0){
                        previewSize = previewSizes.get(previewSizes.size()-1);
                    }
                    params.setPreviewSize(previewSize.width, previewSize.height);
                    params.setPictureSize(pictureSize.width,pictureSize.height);



                    mCamera.setParameters(params);

                }
                else{


                    android.hardware.Camera.Parameters params = mCamera.getParameters();
                    if (supportContinuousPicture){

                        params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

                    }
                    else{
                        params.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_AUTO);

                    }
                    params.set("orientation", "portrait");
                    mCamera.setDisplayOrientation(270);
                    params.setRotation(270);
                    params.set("jpeg-quality", 70);
                    params.setPictureFormat(PixelFormat.JPEG);


                    List<android.hardware.Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
                    List<android.hardware.Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();

                    android.hardware.Camera.Size previewSize = previewSizes.get(0);
                    pictureSize = pictureSizes.get(pictureSizes.size()-1);
                    //params.setPictureSize(1280, 720);

                    // You need to choose the most appropriate previewSize for your app
                    // .... select one of previewSizes here

                    for (int i = 0 ; i< pictureSizes.size(); i++){
                        android.hardware.Camera.Size pictureSizeInternal = pictureSizes.get(i);
                        if(pictureSizeInternal.height > 700 && pictureSizeInternal.height < 800){
                            pictureSize =  pictureSizeInternal;
                        }
                    }

                    if (previewSizes.size() >1){
                        previewSize = previewSizes.get(previewSizes.size()-2);
                    }
                    else if (previewSizes.size() >0){
                        previewSize = previewSizes.get(previewSizes.size()-1);
                    }
                    params.setPreviewSize(previewSize.width, previewSize.height);
                    params.setPictureSize(pictureSize.width,pictureSize.height);


                    mCamera.setParameters(params);



                }


            }

        }



    }

    @Override
    public void onBackPressed() {

        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();

        //Do my thing
    }










}
