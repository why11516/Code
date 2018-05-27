package com.why.readydemo.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.why.readydemo.R;

import java.util.Arrays;

public class CameraFragmentJava extends Fragment {
    TextureView mTextureView;
    String  mCameraId;
    CaptureRequest.Builder mBuilder;
    Handler mBackgroundHandler;
    HandlerThread mBackgroundThread;
    CameraManager mCameraManager;
    CameraDevice.StateCallback cameraStateCallback;
    CameraCaptureSession.StateCallback sessionStateCallback;
    TextureView.SurfaceTextureListener surfaceLinstener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextureView =(TextureView) view.findViewById(R.id.texture);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mBackgroundThread = new HandlerThread("CameraBackground");
            mBackgroundThread.start();
            mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
            mCameraManager = (CameraManager)getActivity().getSystemService(Context.CAMERA_SERVICE);
            cameraStateCallback = new CameraDevice.StateCallback(){
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    try {
                        mBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    SurfaceTexture texture = mTextureView.getSurfaceTexture();
                    texture.setDefaultBufferSize(1920, 1080);
                    Surface surface = new Surface(texture);
                    mBuilder.addTarget(surface);
                    try {
                        camera.createCaptureSession(
                                Arrays.asList(surface),
                                sessionStateCallback, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    camera.close();
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    camera.close();
                }
            };
            sessionStateCallback = new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                    mBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                            CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                    try {
                        session.setRepeatingRequest(mBuilder.build(), null, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Log.e("相机定义失败","");
                }
            };
            surfaceLinstener = new TextureView.SurfaceTextureListener(){
                @SuppressWarnings("MissingPermission")
                @SuppressLint("MissingPermission")
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    //获得后置摄像头
                    try {
                        for(String i : mCameraManager.getCameraIdList()){
                            if(mCameraManager.getCameraCharacteristics(i).get(CameraCharacteristics.LENS_FACING) !=CameraCharacteristics.LENS_FACING_FRONT ){
                                mCameraId = i;
                                break;
                            }
                        }
                        CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(mCameraId);
                        StreamConfigurationMap map  = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                        Size[] sizes = map.getOutputSizes(SurfaceTexture.class);
                        mCameraManager.openCamera(mCameraId,cameraStateCallback,mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                    //Ignored, Camera does all the work for us
                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }

            };
            mTextureView.setSurfaceTextureListener(surfaceLinstener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
