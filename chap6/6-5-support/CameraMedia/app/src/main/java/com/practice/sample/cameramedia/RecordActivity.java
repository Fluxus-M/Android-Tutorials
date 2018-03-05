package com.practice.sample.cameramedia;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordActivity extends Activity {

    private Size previewSize;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder previewBuilder;
    private CameraCaptureSession previewSession;

    private TextureView textureView;
    private Button recordButton;

    private MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        textureView = (TextureView) findViewById(R.id.preview);
        recordButton = (Button) findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording()) {
                    stopRecording(true);
                } else {
                    startRecording();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRecording(false);
    }

    private void startPreview() {
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    private void stopPreview() {
        if (previewSession != null) {
            previewSession.close();
            previewSession = null;
        }
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);

        try {
            String backCameraId = null;

            for (final String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

                int cameraOrientation = characteristics.get(CameraCharacteristics.LENS_FACING);

                if (cameraOrientation == CameraCharacteristics.LENS_FACING_BACK) {
                    backCameraId = cameraId;
                    break;
                }
            }

            if (backCameraId == null) {
                return;
            }

            CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics(backCameraId);

            StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            previewSize = streamConfigurationMap.getOutputSizes(SurfaceTexture.class)[0];

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            manager.openCamera(backCameraId, deviceStateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void showPreview() {
        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        Surface surface = new Surface(surfaceTexture);

        try {
            previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            previewBuilder.addTarget(surface);

            cameraDevice.createCaptureSession(Arrays.asList(surface), captureStateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        try {
            previewSession.setRepeatingRequest(previewBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean isRecording() {
        return mediaRecorder != null;
    }

    private void startRecording() {
        recordButton.setText("중지");

        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
        }

        String recordFilePath = getOutputMediaFile().getAbsolutePath();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);

        // 현재 앱이 실행되는 카메라가 지원하는
        // 최대 화질의 설정 프로필을 구한다.
        // 안드로이드 기기에 탑재되는 카메라에 따라
        // 결정되는 녹화 사이즈 등은 모두 달라진다.
        CamcorderProfile camcorderProfile
                = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);

        // 일부 기종에서 최대 화질 프로필 과
        // 카메라 하드웨어 가 지원하는 최대 사이즈가 다른 문제가 발생한다.
        // 실제 카메라 하드웨어로 부터 구해진 previewSize 와
        // 최대 화질 프로필 API 를 통해 구해진 최대 지원 사이즈를 비교해서
        // 더 작은 쪽으로 설정을 바꾼다.
        if (camcorderProfile.videoFrameWidth > previewSize.getWidth()
                || camcorderProfile.videoFrameHeight > previewSize.getHeight()) {
            camcorderProfile.videoFrameWidth = previewSize.getWidth();
            camcorderProfile.videoFrameHeight = previewSize.getHeight();
        }

        mediaRecorder.setProfile(camcorderProfile);
        mediaRecorder.setOutputFile(recordFilePath);
        mediaRecorder.setOrientationHint(90);

        // 넥서스 5x 의 OS 버전 8.1.0 에서는
        // 다른 기종들과는 반대로 화면을 회전해야 하는 문제가 발생한다.
        // 테스트폰이 넥서스 5x 라면 다음 줄을 주석 해제 한다.
//        mediaRecorder.setOrientationHint(270);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<Surface> surfaces = new ArrayList<>();

        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        Surface previewSurface = new Surface(surfaceTexture);
        surfaces.add(previewSurface);

        Surface mediaRecorderSurface = mediaRecorder.getSurface();
        surfaces.add(mediaRecorderSurface);

        try {
            previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);

            previewBuilder.addTarget(previewSurface);
            previewBuilder.addTarget(mediaRecorderSurface);

            cameraDevice.createCaptureSession(surfaces, captureStateCallback, null);

            mediaRecorder.start();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording(boolean showPreview) {
        recordButton.setText("녹화");

        stopPreview();

        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (showPreview) {
            startPreview();
        }
    }

    private File getOutputMediaFile(){
        String recordPath = getExternalCacheDir().getAbsolutePath();
        File mediaFile = new File(recordPath + File.separator + "record.mp4");
        return mediaFile;
    }

    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
        {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private CameraDevice.StateCallback deviceStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            showPreview();
        }

        @Override
        public void onClosed(@NonNull CameraDevice camera) {
            super.onClosed(camera);
            stopRecording(false);
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

        }

        @Override
        public void onError(CameraDevice camera, int error) {

        }
    };

    private CameraCaptureSession.StateCallback captureStateCallback = new CameraCaptureSession.StateCallback() {

        @Override
        public void onConfigured(CameraCaptureSession session) {
            previewSession = session;
            updatePreview();
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    };
}
