package morirain.dev.jgit.utils;

import android.os.Environment;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class JGit {

    private JGit() {}

    public static JGit newTask() {
        return new JGit();
    }

    /*public JGit to(String toDir, Boolean isExternal) {
        if (!toDir.isEmpty()) {
            if (isExternal) {
                mToDir = new File(Environment.getExternalStorageDirectory(), toDir);
                if(!mToDir.exists()) {
                    mToDir.mkdirs();
                }
                return this;
            }
        }
    }*/

    public JGit clone(String remoteUrl) {
        try {
            Git git = Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(new File(Environment.getExternalStorageDirectory(), "repo"))
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return this;
    }


}
