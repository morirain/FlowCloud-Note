package morirain.dev.jgit.utils.bean;


import org.eclipse.jgit.api.GitCommand;

import java.io.File;

/**
 * Created by morirain on 2018/5/20.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class JGitCommandBean {

    private GitCommand gitCommand;

    private File toDir;

    public JGitCommandBean(GitCommand gitCommand, File toDir) {
        this.gitCommand = gitCommand;
        this.toDir = toDir;
    }

    public GitCommand getGitCommand() {
        return gitCommand;
    }

    public void setGitCommand(GitCommand gitCommand) {
        this.gitCommand = gitCommand;
    }

    public File getToDir() {
        return toDir;
    }

    public void setToDir(File toDir) {
        this.toDir = toDir;
    }
}
