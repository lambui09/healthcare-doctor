package com.lambui.healthcare_doctor.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.apt7.rxpermissions.Permission;
import com.apt7.rxpermissions.PermissionObservable;

import io.reactivex.observers.DisposableObserver;

public class PermissionUtil {
    private static final String[] mArrayPermissionsPhoto = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] mArrayPermissionsStorage = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] mArrayPermissionsRecord = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

//    private static final String[] mArrayPermissionsVideo = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA
//    };

    private static final String[] mArrayPermissionsCamera = {
            Manifest.permission.CAMERA
    };

    private static final String[] mArrayPermissionsLocation = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final String[] mArrayPermissionsCall = {
            Manifest.permission.RECORD_AUDIO
    };

    private static final String[] mArrayPermissionsCallVideo = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    private static final String[] mArrayPermissionsVideo = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] mArrayPermissionsSms = {
            Manifest.permission.SEND_SMS,
    };

    private static final String[] mArrayPermissionsTakeAvatar = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };

    /**
     * simple permission callback
     */
    public interface PermissionCallbackSuccess {
        void onPermissionComplete();
    }

    /**
     * simple permission callback
     */
    public interface PermissionCallback {
        void onPermissionComplete();

        void onPermissionError();
    }

    /**
     * multi permission callback
     */
    public interface MultiPermissionCallback {
        void onPermissionComplete();

        void onPermissionError();

        void onPermissionNext(String permission, boolean isGrant);
    }

    /**
     * Check all permission on Android M
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check each permission on Android M
     */
    public static boolean hasPermissions(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permission != null) {
            return ActivityCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static void checkPermission(final Context context, final String permission, final PermissionCallback callback) {
        PermissionObservable.getInstance().request(context, permission).subscribe(new DisposableObserver<Permission>() {
            @Override
            public void onNext(Permission value) {

            }

            @Override
            public void onError(Throwable e) {
                callback.onPermissionError();
            }

            @Override
            public void onComplete() {
                if (hasPermissions(context, permission)) {
                    callback.onPermissionComplete();
                } else {
                    callback.onPermissionError();
                }
            }
        });
    }

    public static void checkPermission(Context context, final MultiPermissionCallback callback, String... permission) {
        PermissionObservable.getInstance().request(context, permission).subscribe(new DisposableObserver<Permission>() {
            @Override
            public void onNext(Permission value) {
                callback.onPermissionNext(value.getName(), value.getGranted() != 0);
            }

            @Override
            public void onError(Throwable e) {
                callback.onPermissionError();
            }

            @Override
            public void onComplete() {
                callback.onPermissionComplete();
            }
        });
    }

    public static void checkPermissionPhoto(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsPhoto)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsPhoto);
    }

    public static void checkPermissionPhoto(final Context context, final PermissionCallback callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsPhoto)) {
                    callback.onPermissionComplete();
                } else {
                    callback.onPermissionError();
                }
            }

            @Override
            public void onPermissionError() {
                callback.onPermissionError();
            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsPhoto);
    }


    public static void checkPermissionVideo(final Context context, final PermissionCallback callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsVideo)) {
                    callback.onPermissionComplete();
                } else {
                    callback.onPermissionError();
                }
            }

            @Override
            public void onPermissionError() {
                callback.onPermissionError();
            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsPhoto);
    }

    public static void checkPermissionCamera(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsCamera)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsCamera);
    }

    public static void checkPermissionRecord(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsRecord)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsRecord);
    }

    public static void checkPermissionLocation(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsLocation)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsLocation);
    }

    public static void checkPermissionCall(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsCall)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsCall);
    }

    public static void checkPermissionCall(final Context context, final PermissionCallback callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsCall)) {
                    callback.onPermissionComplete();
                } else {
                    callback.onPermissionError();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {
            }
        }, mArrayPermissionsCall);
    }

    public static void checkPermissionCallVideo(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsCallVideo)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {
            }
        }, mArrayPermissionsCallVideo);
    }

    public static void checkPermissionCallVideo(final Context context, final PermissionCallback callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsCallVideo)) {
                    callback.onPermissionComplete();
                } else {
                    callback.onPermissionError();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {
            }
        }, mArrayPermissionsCallVideo);
    }

    public static void checkPermissionVideo(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsVideo)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsVideo);
    }

    public static void checkPermissionSms(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsSms)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsSms);
    }

    public static void checkPermissionStorage(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsStorage)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsStorage);
    }

    public static void checkPermissionTakeAvatar(final Context context, final PermissionCallbackSuccess callback) {
        PermissionUtil.checkPermission(context, new MultiPermissionCallback() {
            @Override
            public void onPermissionComplete() {
                if (PermissionUtil.hasPermissions(context, mArrayPermissionsTakeAvatar)) {
                    callback.onPermissionComplete();
                }
            }

            @Override
            public void onPermissionError() {

            }

            @Override
            public void onPermissionNext(String permission, boolean isGrant) {

            }
        }, mArrayPermissionsTakeAvatar);
    }
}
