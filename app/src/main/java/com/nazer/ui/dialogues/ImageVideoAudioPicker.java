package com.nazer.ui.dialogues;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.nazer.util.PrintLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class ImageVideoAudioPicker {

    private static ImageVideoAudioPicker imageVideoAudioPicker = null;

    public static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_IMAGE_REQUEST = 2;

    public static final int GALLERY_VIDEO_REQUEST = 3;
    public static final int CAMERA_VIDEO_REQUEST = 4;

    public static final int GALLERY_AUDIO_REQUEST = 5;
    private static final String APP_DIRECTORY = "/MyProject";


//    private ImageVideoAudioPicker() {
//
//    }
//
//    public static ImageVideoAudioPicker getInstance() {
//        if (imageVideoAudioPicker == null)
//            imageVideoAudioPicker = new ImageVideoAudioPicker();
//        return imageVideoAudioPicker;
//    }


    public void showImagePickerDialogue(Dialogues dialogues,Activity activity) {
        dialogues.showImagePickerDialogue(this,activity);
    }


    /**
     * =======================================================================
     * ==================================== Pick Image From Gallery
     * =======================================================================
     *
     * @param activity
     */
    public void pickImageFromGalleryIntent(Activity activity) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galleryIntent, GALLERY_IMAGE_REQUEST);
    }

    /**
     * ====================================================================
     * ==================================== Pick Image From Camera
     * ====================================================================
     *
     * @param activity
     */
    public void pickImageFromCameraIntent(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
    }

    /*
    ============================================================================================
           ======================================================= get Application Directory
           ========================================================================================
     */
    public File getProjectDirecory() {
        File directory = new File(Environment.getExternalStorageDirectory().toString() + APP_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    /*
    ============================================================
    ======================= Get Bitmap From Actviity Result
    ============================================================
     */
    public Bitmap getBitmapFromResult(Activity activity, int requestCode, int resultCode, Intent data) throws IOException {
        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            return MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            return (Bitmap) data.getExtras().get("data");
        }
        return null;
    }

    /*=================================================================
    =============================== Save Bitmap and get Path
    =====================================================================
     */
    public String saveImageAndGetPath(Context context, Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File ProjectDirectory = getProjectDirecory();
        try {
            File f = new File(ProjectDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
//            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            PrintLog.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * =========================================== Video Picker Dialogue
     * @param activity
     */
    public void showVideoPickerDialogue(Dialogues dialogues,Activity activity) {
        dialogues.showVideoPickerDialogue(this,activity);
    }


    public void pickVideoFromGallery(Activity activity) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galleryIntent, GALLERY_VIDEO_REQUEST);
    }

    public void pickVideoFromCamera(Activity activity) {
        String videoUri = getProjectDirecory() + "/recordingVideo.mp4";
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        val videoUri = Uri.fromFile(mediaFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        activity.startActivityForResult(intent, CAMERA_VIDEO_REQUEST);
    }

    public Uri getVideoFromActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_VIDEO_REQUEST || requestCode == CAMERA_VIDEO_REQUEST) {
            if (data != null) {
                return data.getData();
            }
        }
        return null;
    }


    /**
     * get RealPathFromUri
     */
    public String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String proj[] = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
