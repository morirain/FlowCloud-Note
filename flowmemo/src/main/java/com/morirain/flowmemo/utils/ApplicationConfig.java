package com.morirain.flowmemo.utils;


import android.os.Environment;

import java.io.File;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/30
 */

public class ApplicationConfig {
    public final static File EXTERNAL_STORAGE = Environment.getExternalStorageDirectory();

    public final static File FLOWMEMO_ROOT_PATH = new File(EXTERNAL_STORAGE, "FlowMemo");

    public final static int NOTE_CONTENT_PREVIEW_LENGTH = 255;
}
