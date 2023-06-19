package com.getcapacitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.MimeTypeMap;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.getcapacitor.util.PermissionHelper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
/* loaded from: classes.dex */
public class BridgeWebChromeClient extends WebChromeClient {
    private ActivityResultLauncher activityLauncher;
    private ActivityResultListener activityListener;
    private Bridge bridge;
    private ActivityResultLauncher permissionLauncher;
    private PermissionListener permissionListener;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface ActivityResultListener {
        void onActivityResult(ActivityResult result);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface PermissionListener {
        void onPermissionSelect(Boolean isGranted);
    }

    public BridgeWebChromeClient(Bridge bridge) {
        this.bridge = bridge;
        this.permissionLauncher = bridge.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$aDsnURRJ0KQWG2RSDn6wJRKZdgk
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BridgeWebChromeClient.this.lambda$new$0$BridgeWebChromeClient((Map) obj);
            }
        });
        this.activityLauncher = bridge.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$nK4HrLIPc8JbAwJyF2CGTpDio8A
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BridgeWebChromeClient.this.lambda$new$1$BridgeWebChromeClient((ActivityResult) obj);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$BridgeWebChromeClient(Map map) {
        if (this.permissionListener != null) {
            boolean z = true;
            for (Map.Entry entry : map.entrySet()) {
                if (!((Boolean) entry.getValue()).booleanValue()) {
                    z = false;
                }
            }
            this.permissionListener.onPermissionSelect(Boolean.valueOf(z));
        }
    }

    public /* synthetic */ void lambda$new$1$BridgeWebChromeClient(ActivityResult activityResult) {
        ActivityResultListener activityResultListener = this.activityListener;
        if (activityResultListener != null) {
            activityResultListener.onActivityResult(activityResult);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        callback.onCustomViewHidden();
        super.onShowCustomView(view, callback);
    }

    @Override // android.webkit.WebChromeClient
    public void onHideCustomView() {
        super.onHideCustomView();
    }

    @Override // android.webkit.WebChromeClient
    public void onPermissionRequest(final PermissionRequest request) {
        boolean z = Build.VERSION.SDK_INT >= 23;
        ArrayList arrayList = new ArrayList();
        if (Arrays.asList(request.getResources()).contains("android.webkit.resource.VIDEO_CAPTURE")) {
            arrayList.add("android.permission.CAMERA");
        }
        if (Arrays.asList(request.getResources()).contains("android.webkit.resource.AUDIO_CAPTURE")) {
            arrayList.add("android.permission.MODIFY_AUDIO_SETTINGS");
            arrayList.add("android.permission.RECORD_AUDIO");
        }
        if (!arrayList.isEmpty() && z) {
            this.permissionListener = new PermissionListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$uSg5LUphBiUxekYbKx9b25nMKM8
                @Override // com.getcapacitor.BridgeWebChromeClient.PermissionListener
                public final void onPermissionSelect(Boolean bool) {
                    BridgeWebChromeClient.lambda$onPermissionRequest$2(request, bool);
                }
            };
            this.permissionLauncher.launch((String[]) arrayList.toArray(new String[0]));
            return;
        }
        request.grant(request.getResources());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onPermissionRequest$2(PermissionRequest permissionRequest, Boolean bool) {
        if (bool.booleanValue()) {
            permissionRequest.grant(permissionRequest.getResources());
        } else {
            permissionRequest.deny();
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        if (this.bridge.getActivity().isFinishing()) {
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(message).setTitle("Alert").setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$7ljA4eogrDEnaM9WO4oOTFYMyuo
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BridgeWebChromeClient.lambda$onJsAlert$3(result, dialogInterface, i);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$mk9-2NuGnOkjeqWxEztC2hewL-w
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                BridgeWebChromeClient.lambda$onJsAlert$4(result, dialogInterface);
            }
        });
        builder.create().show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onJsAlert$3(JsResult jsResult, DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        jsResult.confirm();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onJsAlert$4(JsResult jsResult, DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        jsResult.cancel();
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        if (this.bridge.getActivity().isFinishing()) {
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(message).setTitle("Confirm").setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$kLXophullsRSK5E23R5XMgRxhlU
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BridgeWebChromeClient.lambda$onJsConfirm$5(result, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$ti5zgZ4kCYupmeb9GTKd9EFWWWE
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BridgeWebChromeClient.lambda$onJsConfirm$6(result, dialogInterface, i);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$oyRhjwzdYXBJ_Jfyon_HIC2th3g
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                BridgeWebChromeClient.lambda$onJsConfirm$7(result, dialogInterface);
            }
        });
        builder.create().show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onJsConfirm$5(JsResult jsResult, DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        jsResult.confirm();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onJsConfirm$6(JsResult jsResult, DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        jsResult.cancel();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onJsConfirm$7(JsResult jsResult, DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        jsResult.cancel();
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
        if (this.bridge.getActivity().isFinishing()) {
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        final EditText editText = new EditText(view.getContext());
        builder.setMessage(message).setTitle("Prompt").setView(editText).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$Boo0AidPqgq3MbQVSaZTtY7KFic
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BridgeWebChromeClient.lambda$onJsPrompt$8(editText, result, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$bU-fiXHslTkvYXDAS3FkAk9Bqk4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BridgeWebChromeClient.lambda$onJsPrompt$9(result, dialogInterface, i);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$Jhp9seRbu10S9VrdB4ZzvcEYFEU
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                BridgeWebChromeClient.lambda$onJsPrompt$10(result, dialogInterface);
            }
        });
        builder.create().show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onJsPrompt$8(EditText editText, JsPromptResult jsPromptResult, DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        jsPromptResult.confirm(editText.getText().toString().trim());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onJsPrompt$9(JsPromptResult jsPromptResult, DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        jsPromptResult.cancel();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onJsPrompt$10(JsPromptResult jsPromptResult, DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        jsPromptResult.cancel();
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
        super.onGeolocationPermissionsShowPrompt(origin, callback);
        Logger.debug("onGeolocationPermissionsShowPrompt: DOING IT HERE FOR ORIGIN: " + origin);
        String[] strArr = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
        if (!PermissionHelper.hasPermissions(this.bridge.getContext(), strArr)) {
            this.permissionListener = new PermissionListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$IPvgdTO2QL5NIYSI9EL89dJA2n4
                @Override // com.getcapacitor.BridgeWebChromeClient.PermissionListener
                public final void onPermissionSelect(Boolean bool) {
                    BridgeWebChromeClient.this.lambda$onGeolocationPermissionsShowPrompt$11$BridgeWebChromeClient(callback, origin, bool);
                }
            };
            this.permissionLauncher.launch(strArr);
            return;
        }
        callback.invoke(origin, true, false);
        Logger.debug("onGeolocationPermissionsShowPrompt: has required permission");
    }

    public /* synthetic */ void lambda$onGeolocationPermissionsShowPrompt$11$BridgeWebChromeClient(GeolocationPermissions.Callback callback, String str, Boolean bool) {
        if (bool.booleanValue()) {
            callback.invoke(str, true, false);
            return;
        }
        String[] strArr = {"android.permission.ACCESS_COARSE_LOCATION"};
        if (Build.VERSION.SDK_INT >= 31 && PermissionHelper.hasPermissions(this.bridge.getContext(), strArr)) {
            callback.invoke(str, true, false);
        } else {
            callback.invoke(str, false, false);
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onShowFileChooser(WebView webView, final ValueCallback<Uri[]> filePathCallback, final WebChromeClient.FileChooserParams fileChooserParams) {
        List asList = Arrays.asList(fileChooserParams.getAcceptTypes());
        boolean isCaptureEnabled = fileChooserParams.isCaptureEnabled();
        final boolean z = false;
        boolean z2 = isCaptureEnabled && asList.contains("image/*");
        if (isCaptureEnabled && asList.contains("video/*")) {
            z = true;
        }
        if (z2 || z) {
            if (isMediaCaptureSupported()) {
                showMediaCaptureOrFilePicker(filePathCallback, fileChooserParams, z);
            } else {
                this.permissionListener = new PermissionListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$cqgVTY7-f_znGCJaW0WM3NGJZxc
                    @Override // com.getcapacitor.BridgeWebChromeClient.PermissionListener
                    public final void onPermissionSelect(Boolean bool) {
                        BridgeWebChromeClient.this.lambda$onShowFileChooser$12$BridgeWebChromeClient(filePathCallback, fileChooserParams, z, bool);
                    }
                };
                this.permissionLauncher.launch(new String[]{"android.permission.CAMERA"});
            }
        } else {
            showFilePicker(filePathCallback, fileChooserParams);
        }
        return true;
    }

    public /* synthetic */ void lambda$onShowFileChooser$12$BridgeWebChromeClient(ValueCallback valueCallback, WebChromeClient.FileChooserParams fileChooserParams, boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            showMediaCaptureOrFilePicker(valueCallback, fileChooserParams, z);
            return;
        }
        Logger.warn(Logger.tags("FileChooser"), "Camera permission not granted");
        valueCallback.onReceiveValue(null);
    }

    private boolean isMediaCaptureSupported() {
        return PermissionHelper.hasPermissions(this.bridge.getContext(), new String[]{"android.permission.CAMERA"}) || !PermissionHelper.hasDefinedPermission(this.bridge.getContext(), "android.permission.CAMERA");
    }

    private void showMediaCaptureOrFilePicker(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams, boolean isVideo) {
        boolean showImageCapturePicker;
        boolean z = Build.VERSION.SDK_INT >= 24;
        if (isVideo && z) {
            showImageCapturePicker = showVideoCapturePicker(filePathCallback);
        } else {
            showImageCapturePicker = showImageCapturePicker(filePathCallback);
        }
        if (showImageCapturePicker) {
            return;
        }
        Logger.warn(Logger.tags("FileChooser"), "Media capture intent could not be launched. Falling back to default file picker.");
        showFilePicker(filePathCallback, fileChooserParams);
    }

    private boolean showImageCapturePicker(final ValueCallback<Uri[]> filePathCallback) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(this.bridge.getActivity().getPackageManager()) == null) {
            return false;
        }
        try {
            final Uri createImageFileUri = createImageFileUri();
            intent.putExtra("output", createImageFileUri);
            this.activityListener = new ActivityResultListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$Jx4RidJYN1n7djvJpBMGicbOtpE
                @Override // com.getcapacitor.BridgeWebChromeClient.ActivityResultListener
                public final void onActivityResult(ActivityResult activityResult) {
                    filePathCallback.onReceiveValue(r3.getResultCode() == -1 ? new Uri[]{createImageFileUri} : null);
                }
            };
            this.activityLauncher.launch(intent);
            return true;
        } catch (Exception e) {
            Logger.error("Unable to create temporary media capture file: " + e.getMessage());
            return false;
        }
    }

    private boolean showVideoCapturePicker(final ValueCallback<Uri[]> filePathCallback) {
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        if (intent.resolveActivity(this.bridge.getActivity().getPackageManager()) == null) {
            return false;
        }
        this.activityListener = new ActivityResultListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$3QefVzIpSUdOMpwv1wjh2HPH658
            @Override // com.getcapacitor.BridgeWebChromeClient.ActivityResultListener
            public final void onActivityResult(ActivityResult activityResult) {
                filePathCallback.onReceiveValue(r3.getResultCode() == -1 ? new Uri[]{activityResult.getData().getData()} : null);
            }
        };
        this.activityLauncher.launch(intent);
        return true;
    }

    private void showFilePicker(final ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        Intent createIntent = fileChooserParams.createIntent();
        if (fileChooserParams.getMode() == 1) {
            createIntent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        }
        if (fileChooserParams.getAcceptTypes().length > 1 || createIntent.getType().startsWith(".")) {
            String[] validTypes = getValidTypes(fileChooserParams.getAcceptTypes());
            createIntent.putExtra("android.intent.extra.MIME_TYPES", validTypes);
            if (createIntent.getType().startsWith(".")) {
                createIntent.setType(validTypes[0]);
            }
        }
        try {
            this.activityListener = new ActivityResultListener() { // from class: com.getcapacitor.-$$Lambda$BridgeWebChromeClient$K2q_hX7VVU3kdrpTHIyaUpWfpEc
                @Override // com.getcapacitor.BridgeWebChromeClient.ActivityResultListener
                public final void onActivityResult(ActivityResult activityResult) {
                    BridgeWebChromeClient.lambda$showFilePicker$15(filePathCallback, activityResult);
                }
            };
            this.activityLauncher.launch(createIntent);
        } catch (ActivityNotFoundException unused) {
            filePathCallback.onReceiveValue(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showFilePicker$15(ValueCallback valueCallback, ActivityResult activityResult) {
        Uri[] parseResult;
        Intent data = activityResult.getData();
        if (activityResult.getResultCode() == -1 && data.getClipData() != null && data.getClipData().getItemCount() > 1) {
            int itemCount = data.getClipData().getItemCount();
            parseResult = new Uri[itemCount];
            for (int i = 0; i < itemCount; i++) {
                parseResult[i] = data.getClipData().getItemAt(i).getUri();
            }
        } else {
            parseResult = WebChromeClient.FileChooserParams.parseResult(activityResult.getResultCode(), data);
        }
        valueCallback.onReceiveValue(parseResult);
    }

    private String[] getValidTypes(String[] currentTypes) {
        ArrayList arrayList = new ArrayList();
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        for (String str : currentTypes) {
            if (str.startsWith(".")) {
                String mimeTypeFromExtension = singleton.getMimeTypeFromExtension(str.substring(1));
                if (mimeTypeFromExtension != null && !arrayList.contains(mimeTypeFromExtension)) {
                    arrayList.add(mimeTypeFromExtension);
                }
            } else if (!arrayList.contains(str)) {
                arrayList.add(str);
            }
        }
        Object[] array = arrayList.toArray();
        return (String[]) Arrays.copyOf(array, array.length, String[].class);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String tags = Logger.tags("Console");
        if (consoleMessage.message() != null && isValidMsg(consoleMessage.message())) {
            String format = String.format("File: %s - Line %d - Msg: %s", consoleMessage.sourceId(), Integer.valueOf(consoleMessage.lineNumber()), consoleMessage.message());
            String name = consoleMessage.messageLevel().name();
            if ("ERROR".equalsIgnoreCase(name)) {
                Logger.error(tags, format, null);
            } else if ("WARNING".equalsIgnoreCase(name)) {
                Logger.warn(tags, format);
            } else if ("TIP".equalsIgnoreCase(name)) {
                Logger.debug(tags, format);
            } else {
                Logger.info(tags, format);
            }
        }
        return true;
    }

    public boolean isValidMsg(String msg) {
        return (msg.contains("%cresult %c") || msg.contains("%cnative %c") || msg.equalsIgnoreCase("[object Object]") || msg.equalsIgnoreCase("console.groupEnd")) ? false : true;
    }

    private Uri createImageFileUri() throws IOException {
        AppCompatActivity activity = this.bridge.getActivity();
        File createImageFile = createImageFile(activity);
        return FileProvider.getUriForFile(activity, this.bridge.getContext().getPackageName() + ".fileprovider", createImageFile);
    }

    private File createImageFile(Activity activity) throws IOException {
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return File.createTempFile("JPEG_" + format + "_", ".jpg", activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }
}
