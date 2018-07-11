package com.morirain.flowmemo.utils;

import android.os.Build;
import android.os.Environment;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.Millisecond;
import org.ocpsoft.prettytime.units.Second;

import java.io.File;
import java.util.Locale;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/30
 */

public class ApplicationConfig {
    public final static File EXTERNAL_STORAGE = Environment.getExternalStorageDirectory();

    public final static String FLOWMEMO_ROOT_PATH_NAME = "FlowMemo";

    public final static File FLOWMEMO_ROOT_PATH = new File(EXTERNAL_STORAGE, FLOWMEMO_ROOT_PATH_NAME);

    public final static String NEW_NOTE_DEFALUT_LABEL = "未命名标题";

    public final static int NOTE_LABEL_LENGTH = 8;

    public final static int NOTE_CONTENT_PREVIEW_LENGTH = 128;

    public final static boolean SYSTEM_BELOW_API21 = Build.VERSION.SDK_INT < 21;

    public final static PrettyTime PRETTY_TIME = new PrettyTime(Locale.ENGLISH);

    static{
        PRETTY_TIME.removeUnit(Second.class);
        PRETTY_TIME.removeUnit(Millisecond.class);
    }
}
